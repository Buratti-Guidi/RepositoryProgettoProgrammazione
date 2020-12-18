/**
 * 
 */
package bg.Weather.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bg.Weather.model.UserBox;

/**
 * @author Luca
 *
 */
public interface WeatherService {

	public void initialize(String cap,JSONObject ub);
	
	public void getCities();
	
	public JSONArray getData();
}
