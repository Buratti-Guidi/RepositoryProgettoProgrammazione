package bg.Weather.service;

import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe che contiene i metodi per "parsare" i file JSON in risposta alle chiamate API
 * 
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class JSONWeatherParser{
	
	//Richiede come parametro un file json contenente le informazioni riguardanti una lista di città,
	//lo "parsa" e assegna i valori di ogni singola città ad un oggetto City
	public void parseBox(JSONObject jo, HourCities cities) {
		JSONArray citiesList = new JSONArray();
		citiesList = (JSONArray) jo.get("list");
		
		for(Object o : citiesList) {
			
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
	
	public void parseCity(JSONObject jo,CityData citta) {
		
		JSONObject coord = new JSONObject();
		coord = (JSONObject) jo.get("coord");
		
		long id = ((Number) jo.get("id")).longValue();

		citta.setLongitudine(((Number) coord.get("lon")).doubleValue());
		citta.setLatitudine(((Number) coord.get("lat")).doubleValue());
		citta.setId(id);
	}
	
	//parsa il jsonArray delle hourcities
	public void parseBoxFile(JSONArray ja, HourCities ct) {
		
		for(Object o : ja) {
			
			JSONObject city = (JSONObject) o;
			CityData citta = new CityData();
			
			citta.setNome((String) city.get("name"));
			citta.setId(((Number) city.get("id")).longValue());
		
			String dt = (String)city.get("time");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ROOT);
			Instant instant = ZonedDateTime.parse(dt, dtf).toInstant();
			Date data = Date.from(instant);
			
			citta.setTime(data);
			
			citta.setLongitudine(((Number) city.get("lon")).doubleValue());
			citta.setLatitudine(((Number) city.get("lat")).doubleValue());
			citta.setWindSpeed(((Number) city.get("windSpeed")).doubleValue());
			citta.setTemperatura(((Number) city.get("temp")).doubleValue());
			citta.setTemp_feels_like(((Number) city.get("temp_feels_like")).doubleValue());
			
			ct.addCity(citta);
		}
	}
	
}
