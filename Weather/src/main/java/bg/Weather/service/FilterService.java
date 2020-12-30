package bg.Weather.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;
import bg.Weather.model.HourCities;
import bg.Weather.util.filter.Operator;
import bg.Weather.util.filter.WeatherFilter;
import bg.Weather.util.stats.Stat;

public class FilterService {

	private int days;
	private WeatherFilter wf;
	private JSONObject jof;
	
	public FilterService(JSONObject jof) {
		this.jof = jof;
	}
	
	public Vector<String> filterParser(LinkedList<HashSet<HourCities>> dataset) {
		Vector<String> final_names = new Vector<String>();
		LinkedList<String> tot_names = new LinkedList<String>();
		
		Set<String> keys = this.jof.keySet();
		days = (Integer)this.jof.get("days");
		keys.remove("days");
		
		for(String key : keys) { //finto for che scorre per ottenere la chiave "esterna" oltre a "days"
			Object value = this.jof.get(key);
			LinkedHashMap<String, Object> joValue = (LinkedHashMap<String, Object>)value;
			
			if(joValue.size() == 1) {
				Vector<Double> filterValue = new Vector<Double>();
				StringBuffer filterName = new StringBuffer();
				Stat s = this.statParser(joValue, key, filterValue, filterName);
				LinkedList<Double> stats = s.getStats(dataset);
				wf= new WeatherFilter(filterName.toString(), filterValue);
				int i = 0;
				for(String name : s.getNames(dataset)) {
					if(wf.getResponse(stats.get(i)))
						final_names.add(name);
				i++;
				}
				return final_names;
			}
			else {
				try {
					String className = "bg.Weather.util.filter." + key.substring(0,1).toUpperCase() + key.substring(1, key.length()).toLowerCase() + "Filter";
					Operator o;
					Class<?> cls = Class.forName(className);
					Constructor<?> ct = cls.getDeclaredConstructor();
					o = (Operator)ct.newInstance();
					
					Set<String> ks = joValue.keySet();
					
					for(String stkey : ks) {
						
						Vector<Double> filterValue = new Vector<Double>();
						StringBuffer filterName = new StringBuffer();
						LinkedHashMap<String, Object> joValueOne = new LinkedHashMap<String, Object>();
						joValueOne = (LinkedHashMap<String, Object>)joValue.get(stkey);
						
						//joValueOne.put(stkey, joValue.get(stkey));
						
						Stat s = this.statParser(joValueOne, stkey, filterValue, filterName);
						LinkedList<Double> stats = s.getStats(dataset);
						wf = new WeatherFilter(filterName.toString(), filterValue);//ce una stat e non un filtro
						int i = 0;
						tot_names = s.getNames(dataset);
						for(String name : s.getNames(dataset)) {
							if(wf.getResponse(stats.get(i))) {
								o.addCondition(true, i);
							} else {
								o.addCondition(false, i);
							}
						i++;
						}
					}
					int j = 0;
					for(String name : tot_names) {
						if(o.getResponse(j))
							final_names.add(name);
						j++;
					}
					return final_names;
					
				} catch (ClassNotFoundException e) {
					throw new UserErrorException("This operator doesn't exist");
				}
				catch(InvocationTargetException e) {
					throw new InternalServerException("Error in filterParser");
				}
				catch(LinkageError | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
					throw new InternalServerException("General error, try later...");
				}
			}
		}
		throw new InternalServerException("Something has gone bad");
	}

	public Stat statParser(LinkedHashMap<String, Object> joValue, String stat, Vector<Double> valore, StringBuffer filterName) { //valore e filtername sono inizialmente vuoti, quindi da riempire
		StatsService statService = new StatsService();
		Stat s = statService.getStat(stat,this.days);	
		Set<String> filterKeys = joValue.keySet();
		
		for(String filterN : filterKeys) {			//finto for che scorre solo per il nome del filtro
			filterName.insert(0, filterN);
			Object filterValue = joValue.get(filterN);
			if(filterValue instanceof Number) {
				valore.add((Double)filterValue);    //Se è Integer non viene castato a Double
			}
			if(filterValue instanceof Collection) {
				for(Object elem : (Collection)filterValue) {
					valore.add((Double)elem);
				}
			}
		break;
		}
		return s;
	}
	
	public JSONArray response(LinkedList<HashSet<HourCities>> dataset) {
		Vector<String> final_names = this.filterParser(dataset);
		JSONArray result = new JSONArray();
		Stat s;
		StatsService ss = new StatsService(this.days);
		
		s = ss.getStat("avg", this.days);
		LinkedList<Double> avg = new LinkedList<Double>();
		avg = s.getStats(dataset);
		
		s = ss.getStat("var", this.days);
		LinkedList<Double> var = new LinkedList<Double>();
		var = s.getStats(dataset);
		
		s = ss.getStat("std", this.days);
		LinkedList<Double> std = new LinkedList<Double>();
		std = s.getStats(dataset);
		
		s = ss.getStat("tempmax", this.days);
		LinkedList<Double> tempMax = new LinkedList<Double>();
		tempMax = s.getStats(dataset);
		
		s = ss.getStat("tempmin", this.days);
		LinkedList<Double> tempMin = new LinkedList<Double>();
		tempMin = s.getStats(dataset);
		
		LinkedList<String> names = new LinkedList<String>();
		names = s.getNames(dataset);
		
		
		for(String str : final_names) {
			for(int i = 0; i < names.size(); i++) {
				if(str.equals(names.get(i))) {
					LinkedHashMap<String, Object> jo = new LinkedHashMap<String, Object>();
					jo.put("name", str);
					jo.put("avg", avg.get(i));
					jo.put("var", var.get(i));
					jo.put("std", std.get(i));
					jo.put("tempmax", tempMax.get(i));
					jo.put("tempmin", tempMin.get(i));
					
					result.add(jo);
				}
			}
		}
		return result;
	}
}
