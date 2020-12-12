/**
 * 
 */
package bg.Weather.service;

import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author Luca
 *
 */
public class JSONCapitalParser {

	private HashSet<String> capitals = new HashSet<String>();//vettore di capitali
	
	
	public void parseJSON(JSONArray ja) {
		
		try {	
		
			for(Object o : ja) {
				
				JSONObject citta = (JSONObject) o;
				String app = (String)citta.get("capital");
				
				capitals.add(app);
				
			}
		}catch(Exception e) {//non so che tipo di eccezione puo generare
			System.out.println("Errore sul parsing");
		}

	}

}
