/**
 * 
 */
package bg.Weather.service;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import bg.Weather.dataset.Dataset;
import bg.Weather.exception.FilterErrorException;
import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;
import bg.Weather.model.Box;
import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;
import bg.Weather.util.APIKey;

import bg.Weather.util.stats.*; //da lasciare per il cast a una classe generica di questo package

/**
 * Classe che gestisce tutte le chiamate del controller
 * @author Luca Guidi
 * @author Christopher Buratti
 */
@Service
public class WeatherServiceImpl implements WeatherService {

	private DownloadJSON fileJSON = new DownloadJSON();
	private Dataset dataset;
	private String nomeFile;
	private Box b = new Box();
	
	/**
	 * Inizializza il dataset. Se è presente un file di tipo "capLengthXWidth.json" viene letto e aggiornato il dataset.
	 * Effettua inoltre una chiamata API ad OpenWeatherMap e aggiorna il dataset con i dati della chiamata in tempo reale
	 * @param cap nome della capitale
	 * @param ub JSONObject contenente le dimensioni in km del box
	 */
	@Override
	public void initialize(String cap, JSONObject ub)throws UserErrorException,InternalServerException{
		dataset = new Dataset();
		CityInfo verifica = new CityInfo();
		CityData capital = new CityData();
		String nomeCap;
		
		if(!verifica.verifyCap(cap))
			throw new UserErrorException("The city is not a capital");
		nomeCap = cap.toUpperCase();
		
		capital.setNome(cap);
		verifica.getCoord(capital);//Metodo di VerifyCap che aggiunge le coordinate all' attributo capital
		
		BoxCalculator bc = new BoxCalculator(capital.getLatitudine(), capital.getLongitudine());
		
		try {
			Number l = (Number)ub.get("length");
			Number w = (Number)ub.get("width");
			
			if(l == null)
				throw new UserErrorException("Parametro 'length' mancante");
			
			if(w == null)
				throw new UserErrorException("Parametro 'width' mancante");
			
			b = bc.generaBox(l.doubleValue(), w.doubleValue());
			
			if(l instanceof Double)
				l = l.doubleValue();
			else
				l = l.intValue();
			
			if(w instanceof Double)
				w = w.doubleValue();
			else
				w = w.intValue();
			
			nomeFile = nomeCap.replace(" ","_") + l.toString() + "x" + w.toString() + ".json";
			this.leggiDT();
		}catch(ClassCastException ex) {
			throw new UserErrorException("The body format is incorrect");	
		}
	}
	
	/**
	 * Effettua la chiamata all'API in tempo reale 
	 * @return lista non ordinata delle citta della chiamata all' API in tempo reale
	 */
	public HashSet<CityData> getCities() throws InternalServerException {
		
		String url;
		APIKey ak = new APIKey();
		HourCities cities = new HourCities();
		JSONWeatherParser jwp = new JSONWeatherParser();
		
		url = "http://api.openweathermap.org/data/2.5/box/city?bbox=" + b.getLonSx() + "," + b.getLatDown() + "," + b.getLonDx() + 
				"," + b.getLatUp() + ",10&appid=" + ak.getAPIKey();
		
			
			JSONObject jo = new JSONObject();
			jo = this.fileJSON.chiamataAPIObj(url);//viene chiamata l'api e viene salvato il json object 
			
			jwp.parseBox(jo, cities);//viene parsato il JSONObject e inseriti i dati su cities
			
			dataset.aggiornaDataset(cities);//viene aggiornato il database
			return cities.getHourCities();
	}
	
	
	/**
	 * Ritorna l' intero dataset
	 * @return il dataset
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getData() {
		JSONArray tot = new JSONArray();
			
		for (HashSet<HourCities> hs : this.dataset.getDataset()) {
			for (HourCities hourc : hs) {
				JSONArray cittaOrarie = new JSONArray();
				for (CityData cd : hourc.getHourCities()) {
					cittaOrarie.add(cd.getAllHashMap());
				}
				tot.add(cittaOrarie);
			}
		}
		return tot;
	}
	
	/**
	 * Ritorna le chiamate che sono state salvate nel dataset nel periodo di tempo specificato in jo
	 * @param jo Contiene il periodo di tempo sul quale si vogliono le chiamate
	 * @return le chiamate nel periodo di tempo
	 * @throws UserErrorException Se l' utente sbaglia ad inserire il formato della data
	 * @throws InternalServerException
	 */
	@SuppressWarnings("unchecked")
	public JSONArray postData(JSONObject jo) throws UserErrorException,InternalServerException {
		
		String from = (String)jo.get("from");
		String to = (String)jo.get("to");
		try {

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
			df.setLenient(false);
			
			Date fromDate = df.parse(from);
			Calendar fromCal = Calendar.getInstance();
			fromCal.setTime(fromDate);
			
			Date toDate = df.parse(to);
			Calendar toCal = Calendar.getInstance();
			toCal.setTime(toDate);
			
			toCal.add(Calendar.DAY_OF_MONTH, 1); // aggiungiamo un giorno perche altrimenti non lo prende
			
			if (fromCal.compareTo(toCal) > 0)
				throw new UserErrorException("Try to invert from and to");

			JSONArray tot = new JSONArray();

			for (HashSet<HourCities> hs : this.dataset.getDataset()) {
				for (HourCities hourc : hs) {
					
					if (fromCal.compareTo(hourc.getCalendar()) <= 0 && 
						toCal.compareTo(hourc.getCalendar()) >= 0) { // Se la data in esame e' compresa nell'interv. viene
																	 // aggiunta al JSONArray
						JSONArray cittaOrarie = new JSONArray();
						for (CityData cd : hourc.getHourCities()) {
							cittaOrarie.add(cd.getAllHashMap());
						}
						tot.add(cittaOrarie);
					}
				}
			}
			return tot;
		} catch (ParseException e) {
			throw new UserErrorException("Formato della data non corretto");
		}
	}
	
	/**
	 * Effettua le statistiche specificate su un periodo di tempo
	 * @param stat Contiene parametro "days" che contiene su quanti giorni effettuare le statistiche e le statische da calcolare
	 * @return Statistiche sui metadati
	 * @throws InternalServerException
	 */
	public JSONArray postStats(JSONObject stat) throws UserErrorException,InternalServerException {
		Vector<String> param = new Vector<String>();
		boolean flag = true;
		
		for(int i = 1; flag; i++) {
			String s = (String)stat.get("stat" + i);
			param.add(i-1, s);
			if(param.get(i-1) == null) {
				param.remove(i-1);
				flag = false;
			}
		}
		
		Integer numDays;
		try {
			numDays = (Integer)stat.get("days");
			if(numDays > this.dataset.getDataset().size() || numDays <= 0)
				throw new UserErrorException("Puoi inserire un numero di giorni compreso tra " + 1 + " e " + this.dataset.getDataset().size());
		} catch(ClassCastException e) {
			throw new UserErrorException("Puoi inserire solo un valore di tipo intero su days");
		}
		
		WeatherStats sc = new WeatherStats();
		Stat s;
		
		LinkedList<LinkedList<Double>> statistics = new LinkedList<LinkedList<Double>>();
		LinkedList<String> nomi = new LinkedList<String>();
		
		JSONArray ja = new JSONArray();
		
			for(int j = 0; j < param.size(); j++) {
				s = sc.getStat(param.get(j), numDays);
				statistics.add(s.getStats(dataset.getDataset()));
				nomi = s.getNames(dataset.getDataset());
				
				if(param.size() == 1)
					s.sortStats(nomi, statistics.get(j), false);
			}
		
		int i = 0;
		for(String name : nomi) {
			LinkedHashMap<String, Object> jo = new LinkedHashMap<String, Object>();
			jo.put("name", name);
			LinkedHashMap<String, Double> statistic = new LinkedHashMap<String, Double>();
			
			for(int j = 0; j < param.size(); j++)
				statistic.put(param.get(j), statistics.get(j).get(i));
				
			jo.put("result", statistic);
			ja.add(jo);
			i++;
		}
		return ja;
	}
	
	/**
	 * Calcola le statistiche applicando i filtri specificati
	 * @param jo JSONObject che contiene i filtri sulle statistiche 
	 * @return Statistiche filtrate
	 * @throws UserErrorException
	 * @throws InternalServerException
	 */
	public JSONArray getFilteredStats(JSONObject jo) throws UserErrorException, InternalServerException, FilterErrorException {
		FilterService fs = new FilterService(jo);
		JSONArray response = new JSONArray();
		
		response = fs.getResponse(dataset.getDataset());
		
		return response;
	}
	
	/**
	 * Salva il dataset su un file
	 * @throws InternalServerException
	 */
	@SuppressWarnings("unchecked")
	public void salvaDT() throws InternalServerException {
		JSONArray tot = new JSONArray();

		for (int i = this.dataset.getDataset().size() - 1; i >= 0; i--) {
			JSONArray giornata = new JSONArray();

			for (HourCities hc : this.dataset.getDataset().get(i)) {

				JSONArray cittaOrarie = new JSONArray();
				for (CityData cd : hc.getHourCities()) {
					try {
						cittaOrarie.add(cd.getAllHashMap());
					} catch (ClassCastException ex) {
						throw new InternalServerException("Errore nella conversione del dataset in JSON");
					}
				}
				giornata.add(cittaOrarie);
			}
			tot.add(giornata);
		}

		fileJSON.scriviFile(nomeFile, tot);
	}
	
	/**
	 * Legge il dataset da file, se esiste
	 * @throws InternalServerException
	 */
	public void leggiDT() throws InternalServerException {
		JSONArray ja = new JSONArray();

		JSONWeatherParser jwp = new JSONWeatherParser();
		try {
			ja = fileJSON.caricaFileArr(nomeFile);

			for (int i = 0; i < ja.size(); i++) {
				for (int j = 0; j < ((JSONArray) ja.get(i)).size(); j++) {
						HourCities cities = new HourCities();
						JSONArray city = (JSONArray) ((JSONArray) ((JSONArray) ja.get(i))).get(j);
						jwp.parseBoxFile(city, cities);
						this.dataset.aggiornaDataset(cities);
				}
			}
		} catch (FileNotFoundException ex) {
			//Se il file non e' stato trovato, verra creato in seguito
		}
	}
}
