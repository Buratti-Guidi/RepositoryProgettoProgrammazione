package bg.Weather.controller;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import bg.Weather.exception.InternalServerException;
import bg.Weather.service.WeatherServiceImpl;

@RestController
public class WeatherController {

	@Autowired
	WeatherServiceImpl weatherService;
	
	@PostMapping(value = "/capital/{name}")
	public ResponseEntity<Object> initialization(@PathVariable("name") String nameCap, @RequestBody JSONObject ub) {
		
		//GUARDARE LA GESTIONE DELLE ECCEZIONI
		try {
			weatherService.initialize(nameCap, ub);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Errore initialize");
		}
			//METODO CHE AVVIA IL TIMER DI UN'ORA
			Timer timer = new Timer();
	        timer.schedule(new TimerTask() {	//Classe ANONIMA PER LA TASK ORARIA
	
								            @Override
								            public void run() {
								            	weatherService.getCities();
								            }
	        }, 0, TimeUnit.HOURS.toMillis(1));
		
		return new ResponseEntity<>("Everything is ok", HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/getData")
	public JSONArray getData(){
		return weatherService.getData();
	}
	
	@PostMapping(value = "/getStats")
	public JSONArray postStats(@RequestBody JSONObject stat) {
		try {
			
		
		return weatherService.getStats(stat);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Errore GetStats");
		}
	}
	
	@GetMapping(value = "/save")
	public ResponseEntity<Object> saveDB() {
		weatherService.salvaDB();
		return new ResponseEntity<>("File saved correctly", HttpStatus.OK);
	}
	
}
