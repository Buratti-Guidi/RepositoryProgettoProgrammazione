package bg.Weather.service;

import bg.Weather.model.City;

import java.time.Instant;
import java.util.Date;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONWeatherParser implements JSONParser{
	
	private Vector<City> cities = new Vector<City>();
	
	//Richiede come parametro un file json contenente le informazioni riguardanti una lista di città,
	//lo "parsa" e assegna i valori di ogni singola città ad un oggetto City
	public void parseJSON(JSONObject jo) {
		JSONArray citiesList = new JSONArray();
		citiesList = (JSONArray) jo.get("list");
		
		for(Object o : citiesList) {
			
			JSONObject city = (JSONObject) o;
			City citta = new City();
			
			citta.setId(((Number) city.get("id")).longValue());
			citta.setNome((String) city.get("name"));
			
			Instant time = Instant.ofEpochSecond((Long) city.get("dt"));
			citta.setTime(Date.from(time));
			
			JSONObject coord = new JSONObject();
			
			coord = (JSONObject) city.get("coord");
			citta.setLongitudine(((Number) coord.get("Lon")).doubleValue());
			citta.setLatitudine(((Number) coord.get("Lat")).doubleValue());
			
			JSONObject temperatura = new JSONObject();
			
			temperatura = (JSONObject) city.get("main");
			citta.setTemperatura(((Number) temperatura.get("temp")).doubleValue());
			citta.setTemp_feels_like(((Number) temperatura.get("feels_like")).doubleValue());
			
			this.cities.add(citta);
		}
	}
	
	public void stampaTutto() {
		System.out.println(cities);
		
		for(City c : cities) {
			System.out.println(c);
		}
	}
}
