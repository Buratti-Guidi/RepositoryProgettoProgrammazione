/**
 * 
 */
package bg.Weather.service;

import java.io.FileNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import bg.Weather.exception.InternalServerException;
import bg.Weather.model.CityData;
import bg.Weather.util.APIKey;

/**
 * @author Luca Guidi
 * @author Christopher Buratti
 */
public class CityInfo {

	private DownloadJSON fileJSON;
	
	//metodo che dato il nome di una citta verifica che sia quello di una capitale
	public boolean verifyCap(String cap) throws InternalServerException {

		fileJSON = new DownloadJSON();
		try {
			JSONArray ja = fileJSON.caricaFileArr("capitali.json");

			for (Object o : ja) {

				JSONObject citta = (JSONObject) o;
				String app = (String) citta.get("capital");
				app = app.toUpperCase();
				if (app.equals(cap.toUpperCase())) {
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			throw new InternalServerException("Errore sulla lettura file delle capitali");
		}
		return false;
	}
	
	//metodo che inserisce le coordinate dentro la variabile citta passata
	public void getCoord(CityData citta) throws InternalServerException {
		
		APIKey apikey = new APIKey();
		
		JSONObject jo = fileJSON.chiamataAPIObj("http://api.openweathermap.org/data/2.5/weather?q="+ citta.getNome() +"&appid=" + apikey.getAPIKey());
		JSONWeatherParser pars = new JSONWeatherParser();
		pars.parseCity(jo, citta);						//problemi sul NULL POINT EXCEPTION
	}

}
