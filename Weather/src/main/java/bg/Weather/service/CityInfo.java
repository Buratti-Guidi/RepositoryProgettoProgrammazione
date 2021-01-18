/**
 * 
 */
package bg.Weather.service;

import java.io.FileNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bg.Weather.exception.InternalServerException;
import bg.Weather.model.CityData;
import bg.Weather.util.APIKey;

/**
 * Classe che permette di avere informazioni di una città
 * @author Luca Guidi
 * @author Christopher Buratti
 */
public class CityInfo {

	private DownloadJSON fileJSON;
	
	//metodo che dato il nome di una citta verifica che sia quello di una capitale
	/**
	 * Verifica se la stringa passata è una capitale oppure no, verificando in una lista di capitali salvata in un file locale 
	 * @param cap Nome della città da verificare
	 * @return true se la citta è una capitale, false se non lo è
	 * @throws InternalServerException
	 */
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
			throw new InternalServerException("Error while reading the file of capitals");
		}
		return false;
	}
	
	/**
	 * Inserisce le coordinate della citta nell'oggetto CityData passato
	 * @param citta CityData che contiene il nome della città, di cui si vogliono sapere le coordinate
	 * @throws InternalServerException
	 */
	public void getCoord(CityData citta) throws InternalServerException {
		
		APIKey apikey = new APIKey();
		
		JSONObject jo = fileJSON.chiamataAPIObj("http://api.openweathermap.org/data/2.5/weather?q="+ citta.getNome() +"&appid=" + apikey.getAPIKey());
		JSONWeatherParser pars = new JSONWeatherParser();
		pars.parseCity(jo, citta);
	}

}
