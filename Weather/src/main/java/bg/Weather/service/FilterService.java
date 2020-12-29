package bg.Weather.service;

import java.util.HashSet;
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
	
	public void filterParser(LinkedList<HashSet<HourCities>> dataset) {
		
		StatsService statService = new StatsService();
		Set<String> keys = this.jof.keySet();
		
		days = (Integer)this.jof.get("days");
		keys.remove("days");
		
		for(String key : keys) {
			Object value = this.jof.get(key);
			JSONObject joValue = (JSONObject) value;
			
			if(joValue.size() == 1) {
				
				Vector<Double> filterValue = new Vector<Double>();
				String filterName = null;
				
				Stat s = statParser(joValue,key,filterValue,filterName);
				LinkedList<String> names = s.getNames(dataset);
				LinkedList<Double> stats = s.getStats(dataset);
				
				wf= new WeatherFilter(filterName,filterValue);
				int i = 0;
				for(String name : names) {
					
					if(wf.getResponse(stats.get(i))) {
						//allora viene aggiunta la citta al jsonArray
					}
				
				
				i++;
				}
			}
			else {
				//E' un and o un or
			}			
		}
		
	}
	

	public Stat statParser(JSONObject joValue,String stat,Vector<Double> valore,String filterName) {
		
		StatsService statService = new StatsService();
		
		Stat s = statService.getStat(stat,this.days);	
		
		Set<String> filterKeys = joValue.keySet();
		
		for(String filterN : filterKeys) {			//finto for che scorre solo per il nome del filtro
			filterName = filterN;
			
			Object filterValue = joValue.get(filterN);
			
			if(filterValue instanceof Number) {
				valore.add((Double)filterValue);
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
