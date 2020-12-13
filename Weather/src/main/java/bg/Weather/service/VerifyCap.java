/**
 * 
 */
package bg.Weather.service;

import org.json.simple.JSONObject;

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

}
