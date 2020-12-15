package bg.Weather.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
	
	//Legge da chiamata API un oggetto
	public void chiamataAPIObj(String url) {
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
			
			this.jo = (JSONObject) JSONValue.parseWithException(data);	 //parse JSON Object
				
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Legge da una chiamata API un array
	public void chiamataAPIArr(String url) {
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
			
			this.ja = (JSONArray) JSONValue.parseWithException(data);	//parse JSON Array
				
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Legge da file.json un oggetto
	public void caricaFileObj(String nome_file) {
		try {
			ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nome_file)));	
			
			this.jo = (JSONObject) file_input.readObject();
			
			file_input.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Legge da file.json un array
	public void caricaFileArr(String nome_file) {
		try {
			ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nome_file)));
			
			this.ja = (JSONArray) file_input.readObject();
				
			file_input.close();
				
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
