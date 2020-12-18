/**
 * 
 */
package bg.Weather.service;

import org.json.simple.JSONObject;

import bg.Weather.model.UserBox;

/**
 * @author Luca
 *
 */
public interface WeatherService {

	public void initialize(String cap,UserBox box);
	
	public void getCities();
	
	public JSONObject getData();
}
