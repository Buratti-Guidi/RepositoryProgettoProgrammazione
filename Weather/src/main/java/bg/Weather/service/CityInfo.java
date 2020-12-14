/**
 * 
 */
package bg.Weather.service;

import org.json.simple.JSONObject;

import bg.Weather.model.CityData;
import bg.Weather.util.APIKey;

/**
 * @author Luca
 *
 */
public class CityInfo {

	private DownloadJSON fileJSON;
	
	//metodo che dato il nome di una citta verifica che sia quello di una capitale
	public boolean verifyCap(String cap) {
		
		fileJSON = new DownloadJSON();
		fileJSON.caricaFileArr("capitali.json");
		
		try {	
		
			for(Object o : fileJSON.getArray()) {
				
				JSONObject citta = (JSONObject) o;
				String app = (String)citta.get("capital");
				
				if(app.equals(cap)) {
					return true;
				}
				
			}
		}catch(Exception e) {//non so che tipo di eccezione puo generare
			System.out.println("Errore sul parsing");
		}
		return false;
	}
	
	//metodo che inserisce le coordinate dentro la variabile citta passata
	public void getCoord(CityData citta) {
		
		APIKey apikey = new APIKey();
		
		fileJSON = new DownloadJSON();
		fileJSON.chiamataAPIObj("api.openweathermap.org/data/2.5/weather?q="+ citta.getNome() +"&appid=" + apikey.getAPIKey());
		JSONObject jo = fileJSON.getObject();
		JSONWeatherParser pars = new JSONWeatherParser();
		pars.parseCity(jo, citta);
	}

}