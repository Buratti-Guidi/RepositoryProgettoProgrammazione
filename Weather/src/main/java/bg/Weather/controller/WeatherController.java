package bg.Weather.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;
import bg.Weather.model.CityData;
import bg.Weather.service.DownloadJSON;
import bg.Weather.service.WeatherServiceImpl;

/**
 * Classe Controller che gestisce le richieste effettuate dall'utente tramite la porta "localhost:8080/"
 * @author Christopher Buratti
 * @author Luca Guidi
 */

@RestController
public class WeatherController {

	@Autowired
	WeatherServiceImpl weatherService;
	private boolean initialized = false;
	
	private Timer timer;
	
	/**
	 * Rotta per l'inizializzazione della capitale e del box
	 * @param nameCap nome della capitale
	 * @param userBox contiene larghezza e lunghezza del box in km
	 * @return JSONArray contenente le informazioni metereologiche attuali delle città interne al box
	 * @throws InternalServerException
	 * @throws UserErrorException
	 */
	@PostMapping(value = "/capital/{name}")
	public ResponseEntity<Object> initialization(@PathVariable("name") String nameCap, @RequestBody JSONObject userBox)
			throws InternalServerException, UserErrorException {

		weatherService.initialize(nameCap, userBox);
		
		HashSet<CityData> hs = weatherService.getCities();
		HashSet<HashMap<String, Object>> towns = new HashSet<HashMap<String, Object>>();
		for (CityData ct : hs) {
			towns.add(ct.getAllHashMap());
		}

		TimerTask task = new TimerTask() { // Classe ANONIMA PER LA TASK ORARIA
			
			@Override
			public void run() {
				weatherService.getCities();
			}
		};
		
		try {
			timer.cancel();
			timer.purge();
		}catch(NullPointerException e) {}
		
		timer = new Timer();
		timer.schedule(task, TimeUnit.HOURS.toMillis(1), TimeUnit.HOURS.toMillis(1));

		this.initialized = true;
		
		return new ResponseEntity<>(towns, new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * Rotta per ottenere l'intero dataset
	 * @return dataset contenente le informazioni metereologiche delle città interne al box per ogni chiamata oraria effettuata
	 * @throws InternalServerException
	 * @throws UserErrorException
	 */
	@GetMapping(value = "/data")
	public JSONArray getData() throws InternalServerException, UserErrorException{
		if (this.initialized == false) throw new UserErrorException("Capital initialization is needed");

		return weatherService.getData();
	}

	/**
	 * Rotta per ottenere le informazioni salvate nel dataset nell'intervallo di tempo scelto
	 * @param from_to JSONObject contenente la data di "inizio" e quella di "fine" per rappresentare l'intervallo temporale in cui si vuole ottenere il dataset
	 * @return dataset contenente le informazioni metereologiche delle città interne al box per ogni chiamata oraria effettuata nell'intervallo temporale scelto
	 * @throws UserErrorException
	 * @throws InternalServerException
	 */
	@PostMapping(value = "/data")
	public JSONArray postData(@RequestBody JSONObject from_to) throws UserErrorException, InternalServerException {
		if (this.initialized == false) throw new UserErrorException("Capital initialization is needed");

		return weatherService.postData(from_to);
	}

	/**
	 * Rotta per ottenere le statistiche
	 * @param stat JSONObject contenente il numero di giorni a partire da oggi in cui si vogliono ottenere le statistiche e i nomi delle statistiche richieste
	 * @return elenco città e relative statistiche
	 * @throws UserErrorException
	 * @throws InternalServerException
	 */
	@PostMapping(value = "/stats")
	public JSONArray postStats(@RequestBody JSONObject stat) throws UserErrorException, InternalServerException {
		if (this.initialized == false) throw new UserErrorException("Capital initialization is needed");

		return weatherService.postStats(stat);
	}
	
	/**
	 * Rotta per ottenere le statistiche filtrate secondo qualche criterio
	 * @param filters JSONObject contenente il numero di giorni a partire da oggi in cui si vogliono ottenere le statistiche, i nomi delle statistiche richieste e
	 * i filtri da rispettare
	 * @return elenco città che rispettano i filtri richiesti, e relative statistiche
	 * @throws UserErrorException
	 * @throws InternalServerException
	 */
	@PostMapping(value = "/filters")
	public JSONArray postFilters(@RequestBody JSONObject filters) throws UserErrorException, InternalServerException {
		if (this.initialized == false) throw new UserErrorException("Capital initialization is needed");
		
		return weatherService.getFilteredStats(filters);
	}
	
	/**
	 * Rotta per visualizzare i metadati con relativo tipo e spiegazione sintetica
	 * @return  elenco metadati
	 */
	@GetMapping(value = "/metadata")
	public JSONArray getMetadata() {
		DownloadJSON d = new DownloadJSON();
		try {
			JSONArray ja = d.caricaFileArr("metadata.json");
			return ja;
		} catch (FileNotFoundException | InternalServerException e) {
			
		}
		return null;
	}

	/**
	 * Rotta per salvare il dataset su file in formato JSON
	 * @return esito della richiesta
	 * @throws InternalServerException
	 * @throws UserErrorException
	 */
	@GetMapping(value = "/save")
	public ResponseEntity<Object> saveDB() throws InternalServerException,UserErrorException {
		if (this.initialized == false) throw new UserErrorException("Capital initialization is needed");

		weatherService.salvaDT();
		return new ResponseEntity<>("File saved correctly", HttpStatus.OK);
	}
}
