package bg.Weather.util;

import org.json.simple.JSONObject;

import bg.Weather.service.DownloadJSON;

/**
 * Classe che legge ed ottiene da file JSON l'API Key necessaria per eseguire correttamente le chiamate API a OpenWeather
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class APIKey {

	private String apiKey;
	
	/**
	 * Legge il file JSON "APIKey.json" e ne estrae l'API Key
	 */
	public APIKey() {
		
		DownloadJSON jsonAPI = new DownloadJSON();
		
		JSONObject api = jsonAPI.caricaFileObj("APIKey.json");
			
		this.apiKey = (String) api.get("APIKey");
	}
	
	/**
	 * Ottieni l'API Key
	 * @return API Key
	 */
	public String getAPIKey() {
		return this.apiKey;
	}
}
