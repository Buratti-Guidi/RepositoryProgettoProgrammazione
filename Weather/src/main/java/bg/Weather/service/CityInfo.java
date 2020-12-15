/**
 * 
 */
package bg.Weather.service;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import bg.Weather.model.CityData;
import bg.Weather.util.APIKey;

/**
 * @author Luca Guidi
 * @author Christopher Buratti
 */
public class CityInfo {

	private DownloadJSON fileJSON;
	
	//metodo che dato il nome di una citta verifica che sia quello di una capitale
	public boolean verifyCap(String cap) {
		
		fileJSON = new DownloadJSON();
		
		
		try {	
			fileJSON.caricaFileArr("capitali.json");
		
			for(Object o : fileJSON.getArray()) {
				
				JSONObject citta = (JSONObject) o;
				String app = (String)citta.get("capital");
				app = app.toUpperCase();				//Mettendo le stringhe tutte in maiuscolo, risolviamo i problemi per cui una capitale non veniva riconosciuta
				if(app.equals(cap.toUpperCase())) {		//a causa di una lettera maiuscola/minuscola errata
					return true;
				}
				
			}
		}catch(NullPointerException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Il file non e' stato letto correttamente");//PROVA
		}
		catch(Exception e) {//non so che tipo di eccezione puo generare
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Altra eccezione");//PROVA
		}
		return false;
	}
	
	//metodo che inserisce le coordinate dentro la variabile citta passata
	public void getCoord(CityData citta) {
		
		APIKey apikey = new APIKey();
		
		fileJSON = new DownloadJSON();
		fileJSON.chiamataAPIObj("http://api.openweathermap.org/data/2.5/weather?q="+ citta.getNome() +"&appid=" + apikey.getAPIKey());
		JSONObject jo = fileJSON.getObject();
		JSONWeatherParser pars = new JSONWeatherParser();
		pars.parseCity(jo, citta);
	}

}
