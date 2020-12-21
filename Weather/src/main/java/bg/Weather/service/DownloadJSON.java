package bg.Weather.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import bg.Weather.database.Database;


public class DownloadJSON {

	//Legge da chiamata API un oggetto
	public JSONObject chiamataAPIObj(String url) {
		JSONObject jo = new JSONObject();
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
			
			jo = (JSONObject) JSONValue.parseWithException(data);	 //parse JSON Object
			return jo;
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Errore lettura chiamata API di un oggetto");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Errore lettura chiamata API di un oggetto");
		}
	}
	
	//Legge da una chiamata API un array
	public JSONArray chiamataAPIArr(String url) {
		try {
			JSONArray ja = new JSONArray();
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
			
			ja = (JSONArray) JSONValue.parseWithException(data);	//parse JSON Array
			return ja;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Errore lettura chiamata API di un array");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Errore lettura chiamata API di un array");
		}
	}
	
	//Legge da file.json un oggetto
	public JSONObject caricaFileObj(String nome_file) {
		
		JSONObject jo = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			//ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nome_file)));	
			Object json_file = parser.parse(new FileReader(nome_file));
			jo = (JSONObject) json_file;
			return jo;
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Errore lettura file.json di un oggetto");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Errore lettura file.json di un oggetto");
		}
	}
	
	//Legge da file.json un array
	public JSONArray caricaFileArr(String nome_file) throws Exception{
		
		JSONArray ja = new JSONArray();
		JSONParser parser = new JSONParser();
		
		try {
			Object json_file = parser.parse(new FileReader(nome_file));
			ja = (JSONArray) json_file;
			return ja;
			
		
		}catch(FileNotFoundException e) {
			throw new Exception();
		}catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Errore su caricaFileArr");
		}catch (ParseException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Errore su parse caricaFileArr");
		}
	}
	
	public void scriviFile(String nome_file,JSONArray ja) {
		
		String txt = ja.toString();
		try {
			File file = new File(nome_file);
			
			if(!file.exists())
				file.createNewFile();
			
			BufferedWriter buf = new BufferedWriter(new FileWriter(nome_file));
			buf.write(txt);
			buf.flush();
			buf.close();
		}catch(IOException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Errore scrittura sul file");
		}
	}
	
}
