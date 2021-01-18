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

import bg.Weather.exception.InternalServerException;

/**
 * Classe che permette la lettura e la scrittura di oggetti JSON sia da file che da una chiamata API
 * @author Luca Guidi
 *@author Christopher Buratti
 */
public class DownloadJSON {

	/**
	 * Legge un JSONObject da una chiamata API (url)
	 * @param url link della chiamata API
	 * @return il JSONObject letto
	 * @throws InternalServerException
	 */
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
			throw new InternalServerException("Error while reading an Object from an API call");
		} catch (ParseException ex) {
			throw new InternalServerException("Error while parsing JSONObject");
		} 
	}
	
	/**
	 * Legge un JSONObject da un file
	 * @param nome_file nome del file da cui leggere il JSONObject
	 * @return il JSONObject letto
	 */
	public JSONObject caricaFileObj(String nome_file) {
		
		JSONObject jo = new JSONObject();
		JSONParser parser = new JSONParser();
		try {	
			Object json_file = parser.parse(new FileReader(nome_file));
			jo = (JSONObject) json_file;
			return jo;
			
		} catch (FileNotFoundException e) {
			throw new InternalServerException("File for reading a JSONObject not found");
		} catch (IOException  e) {
			throw new InternalServerException("Error while reading a JSONObject from a file");
		} catch (ParseException ex) {
			throw new InternalServerException("Error while parsing JSONObject");
		} 
	}
	
	/**
	 * Legge un JSONArray da un file
	 * @param nome_file nome del file da cui leggere il JSONArray
	 * @return il JSONArray letto
	 */
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
			throw new InternalServerException("Error while reading an JSONArray from a file");
		} catch (ParseException e) {
			throw new InternalServerException("Error while parsing a JSONArray");
		}
	}
	
	/**
	 * Scrive un JSONArray su un file
	 * @param nome_file nome del file su cui scrivere
	 * @param ja il JSONArray da scrivere
	 * @throws InternalServerException
	 */
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
			throw new InternalServerException("Error while writing in a file");
		}
	}
}
