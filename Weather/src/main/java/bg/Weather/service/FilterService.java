package bg.Weather.service;

import java.util.Set;

import org.json.simple.JSONObject;

public class FilterService {

	
	public void filterParser(JSONObject jof) {
		
		Set<String> keys = jof.keySet();
		int days;
		
		for(String key : keys) {
			Object value = jof.get(key);
			
			if(value instanceof Integer)
				days = (Integer) value;
			
			JSONObject joValue = (JSONObject) value;
			if(joValue.size() == 1) {
				//E' una stat
			}
			else {
				//E' un and o un or
			}			
		}
	}
	
	public void statParser(JSONObject joStat) {
		
		
	}
}
