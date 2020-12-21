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

import bg.Weather.service.WeatherServiceImpl;

@RestController
public class WeatherController {

	@Autowired
	WeatherServiceImpl weatherService;
	
	@PostMapping(value = "/capital/{name}")
	public ResponseEntity<Object> initialization(@PathVariable("name") String nameCap, @RequestBody JSONObject ub) {
		weatherService.initialize(nameCap, ub);
		//METODO CHE AVVIA IL TIMER DI UN'ORA
		
		Timer timer = new Timer();
        timer.schedule(new TimerTask() {

							            @Override
							            public void run() {
							            	weatherService.getCities();
							            }
        }, 0, TimeUnit.HOURS.toMillis(1));
    
        
		return new ResponseEntity<>("Everything is ok", HttpStatus.OK);
	}
	
	@GetMapping(value = "/getData")
	public JSONArray getData(){
		weatherService.salvaDB();
		return weatherService.getData();
	}
	
	@PostMapping(value = "/getStats")
	public JSONArray postStats(@RequestBody JSONObject stat) {
		return weatherService.getStats(stat);
	}
	
	@GetMapping(value = "/save")
	public ResponseEntity<Object> saveDB() {
		return new ResponseEntity<>("File saved correctly", HttpStatus.OK);
	}
	/*
	 * @GetMApping(value = "/boh/{acaso}")
	 * public ResponseEntity<Object> funzioneAcaso(@PathVariable("acaso") String nameCap) {
	 * 
	 * try
	 * {
			weatherService.funzioneacaso();
			
		}catch(NotInitializedException e)
		{
			return new ResponseEntity<>("Non sono ancora stati inseriti i valori", HttpStatus.BAD_REQUEST);
		}
		...
		...
		return new ResponseEntity<>("E' andato tutto bene", HttpStatus.OK);
		
	 */
	
}
