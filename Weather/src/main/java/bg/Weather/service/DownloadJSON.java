package bg.Weather.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class DownloadJSON {

	private JSONArray ja = null;
	private JSONObject jo = null;
	
	public DownloadJSON() {
		this.jo = new JSONObject();
		this.ja = new JSONArray();
	}

	public JSONArray getArray() {
		return ja;
	}

	public void setArray(JSONArray ja) {
		this.ja = ja;
	}

	public JSONObject getObject() {
		return jo;
	}

	public void setObject(JSONObject jo) {
		this.jo = jo;
	}
	
	public void chiamataAPI(String url, boolean isObject) {
		try {
			URLConnection openConnection = new URL(url).openConnection();
			InputStream in = openConnection.getInputStream();
			
			String data = "";
			String line = "";
			
			try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {
				   data+= line;
			   }
			} finally {
			   in.close();
			}
			//System.out.println("Dati scaricati: "+data);
			if(isObject) {
				this.jo = (JSONObject) JSONValue.parseWithException(data);	 //parse JSON Object
				System.out.println("JSONObject scaricato: "+ this.jo);
			} else {
				this.ja = (JSONArray) JSONValue.parseWithException(data);	//parse JSON Array
				System.out.println("JSONArray scaricato: "+ this.ja);
				System.out.println("IL JSONArray scaricato ha "+ this.ja.size()+" elementi:");
			}
				
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
