package bg.Weather.util;

import org.json.simple.JSONObject;

import bg.Weather.service.DownloadJSON;

public class APIKey {

	private String apiKey;
	
	public APIKey() {
		
		DownloadJSON jsonAPI = new DownloadJSON();
		
		jsonAPI.caricaFileObj("APIKey.json");
		JSONObject api = jsonAPI.getObject();
			
		this.apiKey = (String) api.get("APIKey");
	}
	
	public String getAPIKey() {
		return this.apiKey;
	}
}
