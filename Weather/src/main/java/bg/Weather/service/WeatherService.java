/**
 * 
 */
package bg.Weather.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bg.Weather.exception.InternalServerException;

/**
 * @author Luca
 *
 */
public interface WeatherService {

	public void initialize(String cap,JSONObject ub) throws Exception;
	
	public void getCities();
	
	public JSONArray getData();
}
