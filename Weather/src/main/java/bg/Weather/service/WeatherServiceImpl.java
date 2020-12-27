/**
 * 
 */
package bg.Weather.service;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import bg.Weather.database.Database;
import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;
import bg.Weather.model.Box;
import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;
import bg.Weather.model.Stats;
import bg.Weather.util.APIKey;
import bg.Weather.util.filter.WeatherFilter;
import bg.Weather.util.stats.Stat;

import bg.Weather.util.stats.*; //da lasciare per il cast a una classe generica di questo package

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
	public void initialize(String cap, JSONObject ub)throws UserErrorException,InternalServerException{
		dataset = new Database();
		CityInfo verifica = new CityInfo();
		CityData capital = new CityData();
		String nomeCap;
		
		if(!verifica.verifyCap(cap))
			throw new UserErrorException( "The city is not a capital");
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
			throw new UserErrorException("The body format is incorrect");	
		}
	}
	
	public void getCities() throws InternalServerException {
		
		String url;
		APIKey ak = new APIKey();
		HourCities cities = new HourCities();
		JSONWeatherParser jwp = new JSONWeatherParser();
		
		url = "http://api.openweathermap.org/data/2.5/box/city?bbox=" + b.getLonSx() + "," + b.getLatDown() + "," + b.getLonDx() + 
				"," + b.getLatUp() + ",10&appid=" + ak.getAPIKey();
		
			
			JSONObject jo = new JSONObject();
			jo = this.fileJSON.chiamataAPIObj(url);//viene chiamata l'api e viene salvato il json object 
			
			jwp.parseBox(jo, cities);//viene parsato il JSONObject e inseriti i dati su cities
			
			dataset.aggiornaDatabase(cities);//viene aggiornato il database
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	public JSONArray getData()  {
		JSONArray tot = new JSONArray();
		
		for(HashSet<HourCities> hs : this.dataset.getDataset()) {
			for(HourCities hourc : hs) {
				JSONArray cittaOrarie = new JSONArray();
				for(CityData cd : hourc.getHourCities()) {
						cittaOrarie.add(cd.getAllHashMap());
				}
				tot.add(cittaOrarie);
			}
		}
		return tot;
	}
	
	public JSONArray getStats(JSONObject stat) throws InternalServerException {
		String param = (String)stat.get("param");
		Integer numDays = (Integer)stat.get("days");
		
		Stat s;
		
		LinkedList<Double> statistics;
		LinkedList<String> nomi;
		
		JSONArray ja = new JSONArray();
		
		String className = "bg.Weather.util.stats." + param.substring(0,1).toUpperCase()+ param.substring(1, param.length()).toLowerCase() + "Stats";
		try {
			Class<?> cls = Class.forName(className);
			Constructor<?> ct = cls.getDeclaredConstructor(int.class);
			s = (Stat)ct.newInstance(numDays);
			
			statistics = s.getStats(dataset.getDataset());
			nomi = s.getNames(dataset.getDataset());
			s.sortStats(nomi, statistics, false);
			
			int i = 0;
			for(String name : nomi) {
				LinkedHashMap<String, Object> joavg = new LinkedHashMap<String, Object>();
				joavg.put("name", name);
				HashMap<String, Double> statistic = new HashMap<String, Double>();
				statistic.put(param, statistics.get(i));
				joavg.put("result", statistic);
				
				ja.add(joavg);
				i++;
			}
			
		} catch (ClassNotFoundException e) {
			throw new UserErrorException("This stat doesn't exist");
		}
		catch(InvocationTargetException e) {
			throw new InternalServerException("Error in getStats");
		}
		catch(LinkageError | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
			throw new InternalServerException("General error, try later...");
		}
		
		return ja;
	}
	
	/*
	@SuppressWarnings("unchecked")
	public JSONArray getStatsWFilters(String param, JSONObject stat) throws InternalServerException {
		Object filter = stat.get("filter");
		String key;
		double value;
		WeatherFilter wf = new WeatherFilter();
		
		boolean flag = false;
		
		if(filter != null) {
			HashMap<String, Double> fltr = new HashMap<String, Double>();
			fltr = (JSONObject) filter;
			String[] split = new String[2];
			split = fltr.toString().split(":");
			
			key = split[0].substring(1);
			split[1] = split[1].substring(0, split[1].length() - 1);
			value = Double.parseDouble(split[1]);
			
			flag = true;
			
			wf = new WeatherFilter(key, value);
			try {
				wf.verifyFilter();
			} catch(Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This filter doesn't exist");
			}
		}
		
		Integer numDays = (Integer)stat.get("days");
		StatsCalculating sc = new StatsCalculating(numDays);
		JSONArray ja = new JSONArray();
		int i;
		
		LinkedList<String> nomi;
		
		switch(param) {
			
			case "avg":
				nomi = new LinkedList<String>();
				nomi = sc.getNames(dataset.getDataset());
				LinkedList<Double> averages = new LinkedList<Double>();
				averages = sc.getAverages(dataset.getDataset());
				
				sc.sortStats(nomi, averages, false);
				
				i = 0;
				for(String name : nomi) {
					if(flag){
						if(wf.getResponse(averages.get(i))) {
							LinkedHashMap<String, Object> joavg = new LinkedHashMap<String, Object>();
							joavg.put("name", name);
							HashMap<String, Double> avg = new HashMap<String, Double>();
							avg.put("avg", averages.get(i));
							joavg.put("result", avg);
							
							ja.add(joavg);
							i++;
						}
					}
					else {
						LinkedHashMap<String, Object> joavg = new LinkedHashMap<String, Object>();
						joavg.put("name", name);
						HashMap<String, Double> avg = new HashMap<String, Double>();
						avg.put("avg", averages.get(i));
						joavg.put("result", avg);
						
						ja.add(joavg);
						i++;
					}
				}
				break;
				
			case "var":
				nomi = new LinkedList<String>();
				nomi = sc.getNames(dataset.getDataset());
				LinkedList<Double> variances = new LinkedList<Double>();
				variances = sc.getVariances(dataset.getDataset());
				
				sc.sortStats(nomi, variances, false);
				
				i = 0;
				for(String name : nomi) {
					LinkedHashMap<String, Object> jovar = new LinkedHashMap<String, Object>();
					jovar.put("name", name);
					HashMap<String, Double> var = new HashMap<String, Double>();
					var.put("var", variances.get(i));
					jovar.put("result", var);
					
					ja.add(jovar);
					i++;
				}
				break;
				
			case "temp_min":
				nomi = new LinkedList<String>();
				nomi = sc.getNames(dataset.getDataset());
				LinkedList<Double> temp_min = new LinkedList<Double>();
				temp_min = sc.getTempMin(dataset.getDataset());
				
				sc.sortStats(nomi, temp_min, true);
				
				i = 0;
				for(String name : nomi) {
					LinkedHashMap<String, Object> jotmin = new LinkedHashMap<String, Object>();
					jotmin.put("name", name);
					HashMap<String, Double> tempMin = new HashMap<String, Double>();
					tempMin.put("temp_min", temp_min.get(i));
					jotmin.put("result", tempMin);
					
					ja.add(jotmin);
					i++;
				}
				break;
				
			case "temp_max":
				nomi = new LinkedList<String>();
				nomi = sc.getNames(dataset.getDataset());
				LinkedList<Double> temp_max = new LinkedList<Double>();
				temp_max = sc.getTempMin(dataset.getDataset());
				
				sc.sortStats(nomi, temp_max, false);
				
				i = 0;
				for(String name : nomi) {
					LinkedHashMap<String, Object> jotmax = new LinkedHashMap<String, Object>();
					jotmax.put("name", name);
					HashMap<String, Double> tempMax = new HashMap<String, Double>();
					tempMax.put("temp_max", temp_max.get(i));
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
	*/
	
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
						this.dataset.aggiornaDatabase(cities);
				}
			}
		} catch (FileNotFoundException ex) {
			//Se il file non e' stato trovato, verra creato in seguito
		}
	}
}
