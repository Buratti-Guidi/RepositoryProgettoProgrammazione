package bg.Weather.service;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bg.Weather.model.HourCities;
import bg.Weather.util.filter.WeatherFilter;
import bg.Weather.util.stats.Stat;

public class FilterService {

	private int days;
	private WeatherFilter wf;
	private JSONObject jof;
	
	
	public FilterService(JSONObject jof) {
		this.jof=jof;
	}
	
	public Vector<String> filterParser(LinkedList<HashSet<HourCities>> dataset) {
		Set<String> keys = this.jof.keySet();
		
		days = (Integer)this.jof.get("days");
		keys.remove("days");
		
		for(String key : keys) {
			Object value = this.jof.get(key);
			LinkedHashMap<String, Object> joValue = (LinkedHashMap<String, Object>)value;
			
			if(joValue.size() == 1) {
				
				Vector<Double> filterValue = new Vector<Double>();
				StringBuffer filterName = new StringBuffer();
				
				Stat s = statParser(joValue,key,filterValue,filterName);
				Vector<String> final_names = new Vector<String>();
				LinkedList<Double> stats = s.getStats(dataset);
				
				wf= new WeatherFilter(filterName.toString(),filterValue);
				int i = 0;
				for(String name : s.getNames(dataset)) {
					if(wf.getResponse(stats.get(i)))
						final_names.add(name);
				
				i++;
				}
				return final_names;
			}
			else {
				//E' un and o un or
			}
		}
		return null;
		
	}
	

	public Stat statParser(LinkedHashMap<String, Object> joValue,String stat,Vector<Double> valore,StringBuffer filterName) {
		
		StatsService statService = new StatsService();
		
		Stat s = statService.getStat(stat,this.days);	
		
		Set<String> filterKeys = joValue.keySet();
		
		for(String filterN : filterKeys) {			//finto for che scorre solo per il nome del filtro
			filterName.insert(0, filterN);
			
			Object filterValue = joValue.get(filterN);
			
			if(filterValue instanceof Number) {
				valore.add((Double)filterValue);    //Se è Integer non viene castato a Double
			}
			if(filterValue instanceof JSONArray) {
				for(Object elem : (JSONArray)filterValue) {
					valore.add((Double)elem);
				}
			}
			
		break;	
		}
		return s;
		
	}
	
	public JSONArray response() {
		JSONArray results = new JSONArray();
		
		
		
		
		
		return results;
	}
	
	
	
	
	
	
}
