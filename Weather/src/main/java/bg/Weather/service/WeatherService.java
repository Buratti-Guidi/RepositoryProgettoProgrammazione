/**
 * 
 */
package bg.Weather.service;

import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bg.Weather.exception.InternalServerException;
import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;

/**
 * @author Luca
 *
 */
public interface WeatherService {

	public void initialize(String cap,JSONObject ub) throws Exception;
	
	public HashSet<CityData> getCities();
	
	public JSONArray getData();
}
