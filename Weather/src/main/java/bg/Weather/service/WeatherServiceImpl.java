/**
 * 
 */
package bg.Weather.service;

import java.util.HashSet;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import bg.Weather.database.Database;
import bg.Weather.exception.GeneralException;
import bg.Weather.exception.NotInitializedException;
import bg.Weather.model.Box;
import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;
import bg.Weather.util.APIKey;

/**
 * @author Luca
 *
 */
@Service
public class WeatherServiceImpl implements WeatherService {

	Database dataset = new Database();
	
	Box b = new Box();
	
	@Override
	public void initialize(String cap, JSONObject ub) {
		
		CityInfo verifica = new CityInfo();
		CityData capital = new CityData();
		JSONValue jv = new JSONValue();
		
		if(!verifica.verifyCap(cap))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The city is not a capital");
		
		capital.setNome(cap);
		verifica.getCoord(capital);//Metodo di VerifyCap che aggiunge le coordinate all' attributo capital
		
		BoxCalculating bc = new BoxCalculating(capital.getLatitudine(), capital.getLongitudine());
		
		
		Number l = (Number)ub.get("length");
		Number w = (Number)ub.get("width");
		
		try {
			b = bc.generaBox(l.doubleValue(),w.doubleValue());
		}catch(GeneralException ge) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The box isn't acceptable");
		}
		
		this.getCities();
		
	}
	
	public void getCities() {
		
		String url;
		APIKey ak = new APIKey();
		DownloadJSON fileJSON = new DownloadJSON();
		HourCities cities = new HourCities();
		JSONWeatherParser jwp = new JSONWeatherParser();
		
		url = "http://api.openweathermap.org/data/2.5/box/city?bbox=" + b.getLonSx() + "," + b.getLatDown() + "," + b.getLonDx() + 
				"," + b.getLatUp() + ",10&appid=" + ak.getAPIKey();
		
		fileJSON.chiamataAPIObj(url);//viene chiamata l'api e viene salvato un json object su fileJSON
		
		jwp.parseBox(fileJSON.getObject(), cities);//viene parsato il JSONObject e inseriti i dati su cities
		
		dataset.aggiornaDatabase(cities);//viene aggiornato il database
	}
	
	public JSONArray getData() {
		
		JSONArray ja = new JSONArray();
		
		LinkedList<HashSet<HourCities>> data = new LinkedList<HashSet<HourCities>>();
		data = this.dataset.getDataset();
		
		JSONPrinter jp = new JSONPrinter();
		
		for(HashSet<HourCities> hs : data) {
			for(HourCities hourc : hs) {
				try {
					ja.add(jp.printArr(hourc.getHourCities()));
					
				}catch(ClassCastException ex) {
					throw new ResponseStatusException(HttpStatus.CONFLICT,"Errore nella conversione del dataset in JSON");
				}
				
			}
		}
		return ja;
	}
}
