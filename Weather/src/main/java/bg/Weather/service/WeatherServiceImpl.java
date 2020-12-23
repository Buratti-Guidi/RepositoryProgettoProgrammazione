/**
 * 
 */
package bg.Weather.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;

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
	String nomeFile;
	Box b = new Box();
	
	@Override
	public void initialize(String cap, JSONObject ub){
		dataset = new Database();
		CityInfo verifica = new CityInfo();
		CityData capital = new CityData();
		String nomeCap;
		
		if(!verifica.verifyCap(cap))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The city is not a capital");
		nomeCap = cap.toUpperCase();
		
		capital.setNome(cap);
		verifica.getCoord(capital);//Metodo di VerifyCap che aggiunge le coordinate all' attributo capital
		
		BoxCalculating bc = new BoxCalculating(capital.getLatitudine(), capital.getLongitudine());
		
		try {
			Number l = (Number)ub.get("length");
			Number w = (Number)ub.get("width");
			b = bc.generaBox(l.doubleValue(),w.doubleValue());
			
			Integer len = (Integer)l;
			Integer wid = (Integer)w;
			
			nomeFile = nomeCap.replace(" ","_") + len.toString() + "x" + wid.toString() + ".json";
			this.leggiDB();
			
		}catch(ClassCastException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The body format is incorrect");	
		}catch(GeneralException ge) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The box isn't acceptable");
		}
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
		
		for(HashSet<HourCities> hs : this.dataset.getDataset()) {
			for(HourCities hourc : hs) {
				JSONArray cittaOrarie = new JSONArray();
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
		int i;
		
		switch(param) {
			
			case "avg":
				i = 0;
				for(String name : sc.getNames(dataset.getDataset())) {
					LinkedHashMap<String, Object> joavg = new LinkedHashMap<String, Object>();
					joavg.put("name", name);
					HashMap<String, Double> avg = new HashMap<String, Double>();
					avg.put("avg", sc.getAverages(dataset.getDataset()).get(i));
					joavg.put("result", avg);
					
					ja.add(joavg);
					i++;
				}
				break;
				
			case "provAvg":
				
				
				LinkedList<String> nomi = new LinkedList<String>();
				nomi = sc.getNames(dataset.getDataset()); 
				
				LinkedList<Double> values = new LinkedList<Double>();
				values = sc.getAverages(dataset.getDataset()); 
				
				HashMap mp = new HashMap();//mettere String Double
				
				for(int x = 0; x<nomi.size();x++) {
					mp.put(nomi.get(x), values.get(x));
				}
				
				Object[] ordine = values.toArray();
				java.util.Arrays.sort(ordine);
				//nomi.clear();
				//values.clear();
				
				for(int x = 0; x<ordine.length;x++) {
					
					nomi.set(x,(String)mp.get(ordine[x])); 
					values.set(x,(Double)ordine[x]);
				}
				
				
				i = 0;
				
				for(String name : nomi) {
					LinkedHashMap<String, Object> joavg = new LinkedHashMap<String, Object>();
					joavg.put("name", name);
					HashMap<String, Double> avg = new HashMap<String, Double>();
					avg.put("avg", values.get(i));
					joavg.put("result", avg);
					
					ja.add(joavg);
					i++;
				}
				break;
				
			case "var":
				i = 0;
				for(String name : sc.getNames(dataset.getDataset())) {
					LinkedHashMap<String, Object> jovar = new LinkedHashMap<String, Object>();
					jovar.put("name", name);
					HashMap<String, Double> var = new HashMap<String, Double>();
					var.put("var", sc.getVariances(dataset.getDataset()).get(i));
					jovar.put("result", var);
					
					ja.add(jovar);
					i++;
				}
				break;
				
			case "temp_min":
				i = 0;
				for(String name : sc.getNames(dataset.getDataset())) {
					LinkedHashMap<String, Object> jotmin = new LinkedHashMap<String, Object>();
					jotmin.put("name", name);
					HashMap<String, Double> tempMin = new HashMap<String, Double>();
					tempMin.put("temp_min", sc.getTempMin(dataset.getDataset()).get(i));
					jotmin.put("result", tempMin);
					
					ja.add(jotmin);
					i++;
				}
				break;
				
			case "temp_max":
				i = 0;
				for(String name : sc.getNames(dataset.getDataset())) {
					LinkedHashMap<String, Object> jotmax = new LinkedHashMap<String, Object>();
					jotmax.put("name", name);
					HashMap<String, Double> tempMax = new HashMap<String, Double>();
					tempMax.put("temp_max", sc.getTempMax(dataset.getDataset()).get(i));
					jotmax.put("result", tempMax);
					
					ja.add(jotmax);
					i++;
				}
				break;
				
			case "all":
				for(Stats s : sc.getStats(dataset.getDataset())) {
					ja.add(s.getAllHashMap());
				}
				break;
				
			default:
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Richiesta non corretta");
		}
		return ja;
	}
	
	public void salvaDB() {
		
		JSONArray tot = new JSONArray();
		
		for(int i = this.dataset.getDataset().size()-1; i >= 0 ; i--) {
			JSONArray giornata = new JSONArray();
					
			for(HourCities hc : this.dataset.getDataset().get(i)) {
				
				JSONArray cittaOrarie = new JSONArray();
				for(CityData cd : hc.getHourCities()) {
					try {
						cittaOrarie.add(cd.getAllHashMap());
					}catch(ClassCastException ex) {
						throw new ResponseStatusException(HttpStatus.CONFLICT,"Errore nella conversione del dataset in JSON");
					}
				}
				giornata.add(cittaOrarie);
			}
			tot.add(giornata);
		}
		
		fileJSON.scriviFile(nomeFile, tot);
	}
	
	public void leggiDB() {
		
		JSONArray ja = new JSONArray();
	
		JSONWeatherParser jwp = new JSONWeatherParser();
		try {
			ja = fileJSON.caricaFileArr(nomeFile);
			
			for(int i = 0; i < ja.size(); i++) {
			//for(JSONArray jsonArr : (JSONArray)(ja.get(i))) {
				for(int j=0; j < ((JSONArray)ja.get(i)).size(); j++) {
				try {
					HourCities cities = new HourCities();
					JSONArray city = (JSONArray)((JSONArray)((JSONArray)ja.get(i))).get(j);
					jwp.parseBoxFile(city, cities);
					this.dataset.aggiornaDatabase(cities);
				}catch(Exception e) {
					throw new ResponseStatusException(HttpStatus.CONFLICT, "ERRORE sulla lettura del file");
				}
				}
			}
		}catch(Exception ex) {
			
		}
	}
}
