/**
 * 
 */
package bg.Weather.service;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author Luca
 *
 */
public class VerifyCap {

	private DownloadJSON pippo;
	
	public boolean verify(String cap) {
		
		pippo = new DownloadJSON();
		pippo.caricaFileArr("capitali.json");
		
		try {	
		
			for(Object o : pippo.getArray()) {
				
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
