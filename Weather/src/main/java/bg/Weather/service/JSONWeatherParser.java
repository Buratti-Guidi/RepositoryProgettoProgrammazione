package bg.Weather.service;

import bg.Weather.exception.InternalServerException;
import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe che permette di effettuare il parsing di oggetti JSON
 * @author Luca Guidi
 * @author Christopher Buratti
 */

public class JSONWeatherParser{
	
	/**
	 *  Effettua il parsing del JSONObject e assegna i vaori delle città ad un oggetto HourCities
	 * @param jo contiene le informazioni meteorologiche riguardanti un "box" di città
	 * @param cities oggetto VUOTO che viene riempito con tutte le informazioni meteorologiche delle città
	 * @throws InternalServerException
	 */
	public void parseBox(JSONObject jo, HourCities cities) throws InternalServerException {
		JSONArray citiesList = new JSONArray();
		citiesList = (JSONArray) jo.get("list");

		if (citiesList == null)
			throw new InternalServerException("Errore dal box dell'API");

		for (Object o : citiesList) {

			JSONObject city = (JSONObject) o;
			CityData citta = new CityData();

			citta.setId(((Number) city.get("id")).longValue());
			citta.setNome((String) city.get("name"));

			Instant time = Instant.ofEpochSecond((Long) city.get("dt"));
			citta.setTime(Date.from(time));

			JSONObject coord = new JSONObject();

			coord = (JSONObject) city.get("coord");
			citta.setLongitudine(((Number) coord.get("Lon")).doubleValue());
			citta.setLatitudine(((Number) coord.get("Lat")).doubleValue());

			JSONObject wind = new JSONObject();

			wind = (JSONObject) city.get("wind");
			citta.setWindSpeed(((Number) wind.get("speed")).doubleValue());

			JSONObject temperatura = new JSONObject();

			temperatura = (JSONObject) city.get("main");
			citta.setTemperatura(((Number) temperatura.get("temp")).doubleValue());
			citta.setTemp_feels_like(((Number) temperatura.get("feels_like")).doubleValue());

			cities.addCity(citta);
		}
	}
	
	
	/**
	 * Effettua il parsing del JSONObject di una città
	 * @param jo contiene le informazioni meteorologiche e e coordinate di una città
	 * @param city oggetto VUOTO che viene riempito con le informazioni della città
	 * @throws InternalServerException
	 */
	public void parseCity(JSONObject jo, CityData city) throws InternalServerException {

		JSONObject coord = new JSONObject();
		coord = (JSONObject) jo.get("coord");

		if (coord == null)
			throw new InternalServerException("Error su ParseCity");

		long id = ((Number) jo.get("id")).longValue();

		city.setLongitudine(((Number) coord.get("lon")).doubleValue());
		city.setLatitudine(((Number) coord.get("lat")).doubleValue());
		city.setId(id);
	}

	/**
	 *  Effettua il parsing del JSONObject del "box" di città dal file
	 * @param ja contiene le informazioni meteorologiche riguardanti un "box" di città
	 * @param ct oggetto VUOTO che viene riempito con tutte le informazioni meteorologiche delle città
	 * @throws InternalServerException
	 */
	public void parseBoxFile(JSONArray ja, HourCities ct) throws InternalServerException{
		
		for (Object o : ja) {

			JSONObject city = (JSONObject) o;
			CityData citta = new CityData();

			citta.setNome((String) city.get("name"));
			citta.setId(((Number) city.get("id")).longValue());

			try {
				String dt = (String) city.get("time");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ROOT);
				Instant instant = ZonedDateTime.parse(dt, dtf).toInstant();
				Date data = Date.from(instant);
				citta.setTime(data);
				
			} catch (DateTimeParseException e) {
				throw new InternalServerException("Errore nel formato data letto da file");
			}

			citta.setLongitudine(((Number) city.get("lon")).doubleValue());
			citta.setLatitudine(((Number) city.get("lat")).doubleValue());
			citta.setWindSpeed(((Number) city.get("windSpeed")).doubleValue());
			citta.setTemperatura(((Number) city.get("temp")).doubleValue());
			citta.setTemp_feels_like(((Number) city.get("temp_feels_like")).doubleValue());

			ct.addCity(citta);
		}
	}
	
}
