package bg.Weather.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONPrinter <T>{
	
	public JSONObject printObj(T obj) throws ClassCastException{
		try {
			JSONObject jo = (JSONObject) obj;
			return jo;
		}catch(ClassCastException e) {
			throw new ClassCastException();
		}
		
	}
	
	public JSONArray printArr(T obj) throws ClassCastException{
		try {
		JSONArray ja = (JSONArray) obj;
		return ja;
		}catch(ClassCastException e) {
			throw new ClassCastException();
		}
	}
}
