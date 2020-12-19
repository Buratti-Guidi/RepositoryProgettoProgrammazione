package bg.Weather.util;

import org.json.simple.JSONObject;

import bg.Weather.service.DownloadJSON;

public class APIKey {

	private String apiKey;
	
	public APIKey() {
		
		DownloadJSON jsonAPI = new DownloadJSON();
		
		JSONObject api = jsonAPI.caricaFileObj("APIKey.json");
			
		this.apiKey = (String) api.get("APIKey");
	}
	
	public String getAPIKey() {
		return this.apiKey;
	}
}
