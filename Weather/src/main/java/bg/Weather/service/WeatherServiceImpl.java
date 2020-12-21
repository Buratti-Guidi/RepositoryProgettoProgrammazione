/**
 * 
 */
package bg.Weather.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import bg.Weather.database.Database;
import bg.Weather.exception.GeneralException;
import bg.Weather.model.Box;
import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;
import bg.Weather.model.Stats;
import bg.Weather.util.APIKey;

/**
 * @author Luca Guidi
 * @author Christopher Buratti
 */
@Service
public class WeatherServiceImpl implements WeatherService {

	DownloadJSON fileJSON = new DownloadJSON();
	Database dataset;
	String nomeCap;
	Box b = new Box();
	
	@Override
	public void initialize(String cap, JSONObject ub) {
		dataset = new Database();
		CityInfo verifica = new CityInfo();
		CityData capital = new CityData();
		
		if(!verifica.verifyCap(cap))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The city is not a capital");
		this.nomeCap = cap.toUpperCase();
		
		capital.setNome(cap);
		verifica.getCoord(capital);//Metodo di VerifyCap che aggiunge le coordinate all' attributo capital
		
		BoxCalculating bc = new BoxCalculating(capital.getLatitudine(), capital.getLongitudine());
		
		Number l = (Number)ub.get("length");
		Number w = (Number)ub.get("width");
		
		try {
			b = bc.generaBox(l.doubleValue(),w.doubleValue());
		}catch(GeneralException ge) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The box isn't acceptable");
		}
		
		this.leggiDB();
		this.getCities();
		
		
	}
	
	public void getCities() {
		
		String url;
		APIKey ak = new APIKey();
		HourCities cities = new HourCities();
		JSONWeatherParser jwp = new JSONWeatherParser();
		
		url = "http://api.openweathermap.org/data/2.5/box/city?bbox=" + b.getLonSx() + "," + b.getLatDown() + "," + b.getLonDx() + 
				"," + b.getLatUp() + ",10&appid=" + ak.getAPIKey();
		try {
			
			JSONObject jo = new JSONObject();
			jo = this.fileJSON.chiamataAPIObj(url);//viene chiamata l'api e viene salvato il json object 
			
			jwp.parseBox(jo, cities);//viene parsato il JSONObject e inseriti i dati su cities
			
			dataset.aggiornaDatabase(cities);//viene aggiornato il database
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Errore su getCities");
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public JSONArray getData() {
		JSONArray tot = new JSONArray();
		
		LinkedList<HashSet<HourCities>> data = new LinkedList<HashSet<HourCities>>();
		data = this.dataset.getDataset();
		
		for(HashSet<HourCities> hs : data) {
			for(HourCities hourc : hs) {
				JSONArray cittaOrarie = new JSONArray();
				//cittaOrarie.add(hourc.getDateHashMap()); //JSONObject con solo l'ora delle HourCities
				for(CityData cd : hourc.getHourCities()) {
					try {
						cittaOrarie.add(cd.getAllHashMap());
					}catch(ClassCastException ex) {
						throw new ResponseStatusException(HttpStatus.CONFLICT,"Errore nella conversione del dataset in JSON");
					}
				}
				tot.add(cittaOrarie);
			}
		}
		return tot;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getStats(JSONObject stat) {
		String param = (String)stat.get("param");
		Integer numDays = (Integer)stat.get("days");
		StatsCalculating sc = new StatsCalculating(numDays);
		JSONArray ja = new JSONArray();
		
		switch(param) {
			case "avg":
				int i = 0;
				HashMap<String, Double> avg = new HashMap<String, Double>();
				for(String name : sc.getNames(dataset.getDataset())) {
					avg.put(name, sc.getAverages(dataset.getDataset()).get(i));
					i++;
				}
				ja.add(avg);
				break;
				
			case "var":
				int j = 0;
				HashMap<String, Double> var = new HashMap<String, Double>();
				for(String name : sc.getNames(dataset.getDataset())) {
					var.put(name, sc.getVariances(dataset.getDataset()).get(j));
					j++;
				}
				ja.add(var);
				break;
				
			case "temp_min":
				int k = 0;
				HashMap<String, Double> tempMin = new HashMap<String, Double>();
				for(String name : sc.getNames(dataset.getDataset())) {
					tempMin.put(name, sc.getTempMin(dataset.getDataset()).get(k));
					k++;
				}
				ja.add(tempMin);
				break;
				
			case "temp_max":
				int z = 0;
				HashMap<String, Double> tempMax = new HashMap<String, Double>();
				for(String name : sc.getNames(dataset.getDataset())) {
					tempMax.put(name, sc.getTempMax(dataset.getDataset()).get(z));
					z++;
				}
				ja.add(tempMax);
				break;
				
			case "all": //DA CONTROLLARE
				for(Stats s : sc.getStats()) {
					ja.add(s.getAllHashMap());
				}
				break;
				
			default: //throw new Exception();
		}
		return ja;
	}
	
	public void salvaDB() {
		String nome_file = this.nomeCap.replace(" ","_") + ".json";
		fileJSON.scriviFile(nome_file, this.getData());
	}
	
	public void leggiDB() {
		
		JSONArray ja = new JSONArray();
		
		String nome_file = this.nomeCap.replace(" ","_") + ".json";
		JSONWeatherParser jwp = new JSONWeatherParser();
		try {
			ja = fileJSON.caricaFileArr(nome_file);
			//Scorro il JSONArray e aggiungo ogni hourcities al dataset 
			for(int i = 0; i< ja.size(); i++) {
				try {
					HourCities cities = new HourCities();
					jwp.parseBoxFile((JSONArray)ja.get(i), cities);
					this.dataset.aggiornaDatabase(cities);
				}catch(Exception e) {
					throw new ResponseStatusException(HttpStatus.CONFLICT, "ERRORE sulla lettura del file");
				}
			}
		}catch(Exception ex) {
			
		}
	}
}
