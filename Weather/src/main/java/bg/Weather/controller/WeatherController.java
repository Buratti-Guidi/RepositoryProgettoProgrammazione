package bg.Weather.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;
import bg.Weather.model.CityData;
import bg.Weather.service.WeatherServiceImpl;

@RestController
public class WeatherController {

	@Autowired
	WeatherServiceImpl weatherService;
	private boolean inizialized = false;

	@PostMapping(value = "/capital/{name}")
	public ResponseEntity<Object> initialization(@PathVariable("name") String nameCap, @RequestBody JSONObject ub)
			throws InternalServerException, UserErrorException {

		this.inizialized = true;
		weatherService.initialize(nameCap, ub);
		
		HashSet<CityData> hs = weatherService.getCities();
		HashSet<HashMap<String, Object>> towns = new HashSet<HashMap<String, Object>>();
		for (CityData ct : hs) {
			towns.add(ct.getAllHashMap());
		}
		// METODO CHE AVVIA IL TIMER DI UN'ORA
		Timer timer = new Timer();
		timer.schedule(new TimerTask() { // Classe ANONIMA PER LA TASK ORARIA

			@Override
			public void run() {
				weatherService.getCities();
			}
		}, TimeUnit.HOURS.toMillis(1), TimeUnit.HOURS.toMillis(1));

		return new ResponseEntity<>(towns, new HttpHeaders(), HttpStatus.OK);

	}

	@GetMapping(value = "/data")
	public JSONArray getData() throws InternalServerException, UserErrorException{
		if (this.inizialized == false) throw new UserErrorException("Capital initialization is needed");

		return weatherService.getData();
	}

	@PostMapping(value = "/data")
	public JSONArray postData(@RequestBody JSONObject from_to) throws UserErrorException, InternalServerException {
		if (this.inizialized == false) throw new UserErrorException("Capital initialization is needed");

		return weatherService.postData(from_to);
	}

	@PostMapping(value = "/stats")
	public JSONArray postStats(@RequestBody JSONObject stat) throws UserErrorException, InternalServerException {
		if (this.inizialized == false) throw new UserErrorException("Capital initialization is needed");

		return weatherService.getStats(stat);
	}
	
	@PostMapping(value = "/filters")
	public JSONArray postFilters(@RequestBody JSONObject filters) throws UserErrorException, InternalServerException {
		if (this.inizialized == false) throw new UserErrorException("Capital initialization is needed");
		
		return weatherService.getFilteredStats(filters);
	}

	@GetMapping(value = "/save")
	public ResponseEntity<Object> saveDB() throws InternalServerException,UserErrorException {
		if (this.inizialized == false) throw new UserErrorException("Capital initialization is needed");

		weatherService.salvaDB();
		return new ResponseEntity<>("File saved correctly", HttpStatus.OK);
	}

}
