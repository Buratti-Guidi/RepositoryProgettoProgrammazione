package bg.Weather.service;

import java.util.Collection;
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
		this.jof = jof;
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

	public Stat statParser(LinkedHashMap<String, Object> joValue, String stat, Vector<Double> valore, StringBuffer filterName) {
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
		
		Vector<String> final_names = this.filterParser(dataset);
		
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
