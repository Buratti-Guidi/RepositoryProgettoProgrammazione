/**
 * 
 */
package bg.Weather.service;

import org.json.simple.JSONObject;

import bg.Weather.model.CityData;

/**
 * @author Luca
 *
 */
public class VerifyCap {

	private DownloadJSON fileJSON;
	
	//metodo che dato il nome di una citta verifica che sia quello di una capitale
	public boolean verify(String cap) {
		
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
		
		fileJSON = new DownloadJSON();
		//fileJSON.chamataAPIBisognaVedereilJSON(api.openweathermap.org/data/2.5/weather?q="+ citta.getNome +"&appid=8116d9a3e69f6c85080b2ee006d460c8);
		JSONObject jo = fileJSON.getObject();
		JSONWeatherParser pars = new JSONWeatherParser();
		pars.parseCity(jo, citta);
	}

}
