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
import bg.Weather.exception.InternalServerException;


public class DownloadJSON {

	//Legge da chiamata API un oggetto
	public JSONObject chiamataAPIObj(String url) throws InternalServerException{
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
			
		} catch (IOException  e) {
			throw new InternalServerException("Errore lettura chiamata API di un oggetto");
		} catch (ParseException ex) {
			throw new InternalServerException("Errore parsing JSONObject");
		} 
	}
	
	
	
	//Serve per APIKey
	//Legge da file.json un oggetto
	public JSONObject caricaFileObj(String nome_file) {
		
		JSONObject jo = new JSONObject();
		JSONParser parser = new JSONParser();
		try {	
			Object json_file = parser.parse(new FileReader(nome_file));
			jo = (JSONObject) json_file;
			return jo;
			
		} catch (FileNotFoundException e) {
			throw new InternalServerException("File per lettura di un oggetto non trovato");
		} catch (IOException  e) {
			throw new InternalServerException("Errore lettura file di un oggetto");
		} catch (ParseException ex) {
			throw new InternalServerException("Errore parsing JSONObject");
		} 
	}
	
	//Legge da file.json un array
	public JSONArray caricaFileArr(String nome_file) throws FileNotFoundException, InternalServerException {

		JSONArray ja = new JSONArray();
		JSONParser parser = new JSONParser();

		try {
			Object json_file = parser.parse(new FileReader(nome_file));
			ja = (JSONArray) json_file;
			return ja;

		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			throw new InternalServerException("Errore su caricaFileArr");
		} catch (ParseException e) {
			throw new InternalServerException("Errore su parse caricaFileArr");
		}
	}
	
	public void scriviFile(String nome_file, JSONArray ja) throws InternalServerException {
		String txt = ja.toString();
		try {

			File file = new File(nome_file);
			if (!file.exists())
				file.createNewFile();

			BufferedWriter buf = new BufferedWriter(new FileWriter(nome_file));
			buf.write(txt);
			buf.close();
		} catch (IOException e) {
			throw new InternalServerException("Errore scrittura sul file");
		}
	}
}
