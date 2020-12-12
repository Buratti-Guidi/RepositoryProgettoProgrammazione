package bg.Weather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bg.Weather.model.Box;
import bg.Weather.service.VerifyCap;

@RestController
public class WeatherController {

	@PostMapping(value = "/capital/{name}")
	public ResponseEntity<Object> inizialization(@PathVariable("name") String nameCap, @RequestBody Box box) {
		
		VerifyCap verifica = new VerifyCap();
		if(!verifica.verify(nameCap))
			return new ResponseEntity<>("The city is not a capital", HttpStatus.BAD_REQUEST);
		
		
	}
}
