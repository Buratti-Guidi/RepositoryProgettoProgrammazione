package bg.Weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bg.Weather.model.Box;
import bg.Weather.model.UserBox;
import bg.Weather.service.BoxCalculating;
import bg.Weather.service.VerifyCap;
import bg.Weather.service.WeatherServiceImpl;

@RestController
public class WeatherController {

	@Autowired
	WeatherServiceImpl weatherService;
	
	@PostMapping(value = "/capital/{name}")
	public ResponseEntity<Object> inizialization(@PathVariable("name") String nameCap, @RequestBody UserBox ub) {
		
		weatherService.initialize(nameCap, ub);
		
		return new ResponseEntity<>("The city is a capital", HttpStatus.OK);//E' UNA PROVA!!!
		
	}
}
