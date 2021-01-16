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
 * Interfaccia di un weather service
 * @author Luca Guidi
 * @author Christopher Buratti
 */
public interface WeatherService {

	/**
	 * Inizializza il dataset  
	 * @param cap nome della capitale
	 * @param ub contiene le coordinate in km del box
	 */
	public void initialize(String cap,JSONObject ub);
	
	/**
	 * Effettua la chiamata all' API
	 * @return informazioni meteorologiche delle città nell' ora attuale
	 */
	public HashSet<CityData> getCities();
	
	/**
	 * Restituisce il contenuto del dataset
	 * @return tutto il dataset
	 */
	public JSONArray getData();
}
