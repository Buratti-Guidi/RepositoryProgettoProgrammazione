/**
 * 
 */
package bg.Weather.service;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import bg.Weather.dataset.Dataset;
import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;
import bg.Weather.model.Box;
import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;
import bg.Weather.util.APIKey;
import bg.Weather.util.filter.WeatherFilter;

import bg.Weather.util.stats.*; //da lasciare per il cast a una classe generica di questo package

/**
 * @author Luca Guidi
 * @author Christopher Buratti
 */
@Service
public class WeatherServiceImpl implements WeatherService {

	private DownloadJSON fileJSON = new DownloadJSON();
	private Dataset dataset;
	private String nomeFile;
	private Box b = new Box();
	
	@Override
	public void initialize(String cap, JSONObject ub)throws UserErrorException,InternalServerException{
		dataset = new Dataset();
		CityInfo verifica = new CityInfo();
		CityData capital = new CityData();
		String nomeCap;
		
		if(!verifica.verifyCap(cap))
			throw new UserErrorException( "The city is not a capital");
		nomeCap = cap.toUpperCase();
		
		capital.setNome(cap);
		verifica.getCoord(capital);//Metodo di VerifyCap che aggiunge le coordinate all' attributo capital
		
		BoxCalculator bc = new BoxCalculator(capital.getLatitudine(), capital.getLongitudine());
		
		try {
			Number l = (Number)ub.get("length");
			Number w = (Number)ub.get("width");
			b = bc.generaBox(l.doubleValue(),w.doubleValue());
			
			Integer len = (Integer)l;
			Integer wid = (Integer)w;
			
			nomeFile = nomeCap.replace(" ","_") + len.toString() + "x" + wid.toString() + ".json";
			this.leggiDB();
		}catch(ClassCastException ex) {
			throw new UserErrorException("The body format is incorrect");	
		}
	}
	
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
	
	
	
	@SuppressWarnings("unchecked")
	public JSONArray getData() {
		JSONArray tot = new JSONArray();
		
		//if(this.dataset.getDataset() == null)throw new UserErrorException("Capital initialization is needed");
			
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
	
	public JSONArray postStats(JSONObject stat) throws InternalServerException {
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
		Integer numDays = (Integer)stat.get("days");
		StatsService sc = new StatsService();
		Stat s;
		
		LinkedList<LinkedList<Double>> statistics = new LinkedList<LinkedList<Double>>();
		LinkedList<String> nomi = new LinkedList<String>();
		
		JSONArray ja = new JSONArray();
		try {
			for(int j = 0; j < param.size(); j++) {
				s = sc.getStat(param.get(j), numDays);
				statistics.add(s.getStats(dataset.getDataset()));
				nomi = s.getNames(dataset.getDataset());
				
				if(param.size() == 1)
					s.sortStats(nomi, statistics.get(j), false);
			}	
		}catch(Exception e) {
			throw new InternalServerException("errore");
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
	
	public JSONArray getFilteredStats(JSONObject jo)throws UserErrorException,InternalServerException {
		FilterService fs = new FilterService(jo);
		JSONArray response = new JSONArray();
		
		response = fs.getResponse(dataset.getDataset());
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public void salvaDB() throws InternalServerException {
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
	
	public void leggiDB() throws InternalServerException {
		JSONArray ja = new JSONArray();

		JSONWeatherParser jwp = new JSONWeatherParser();
		try {
			ja = fileJSON.caricaFileArr(nomeFile);

			for (int i = 0; i < ja.size(); i++) {
				// for(JSONArray jsonArr : (JSONArray)(ja.get(i))) {
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
