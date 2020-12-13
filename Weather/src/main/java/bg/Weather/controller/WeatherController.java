package bg.Weather.controller;

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

@RestController
public class WeatherController {

	@PostMapping(value = "/capital/{name}")
	public ResponseEntity<Object> inizialization(@PathVariable("name") String nameCap, @RequestBody UserBox ub) {
		
		VerifyCap verifica = new VerifyCap();
		if(!verifica.verify(nameCap))
			return new ResponseEntity<>("The city is not a capital", HttpStatus.BAD_REQUEST);
		
		Box b = new Box();
		BoxCalculating bc = new BoxCalculating();
		bc.setLenght(ub.getLenght());
		bc.setWidth(ub.getWidth());
		//bc.setLat_centro(lat_centro);
		//bc.setLong_centro(long_centro);
		if(!bc.verifyBox())
			return new ResponseEntity<>("The box is too big", HttpStatus.BAD_REQUEST);
		
		b = bc.generaBox();
	}
}
