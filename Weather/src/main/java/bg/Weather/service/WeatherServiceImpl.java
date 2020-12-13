/**
 * 
 */
package bg.Weather.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import bg.Weather.model.Box;
import bg.Weather.model.CityData;
import bg.Weather.model.UserBox;

/**
 * @author Luca
 *
 */
@Service
public class WeatherServiceImpl implements WeatherService {

	//Database dataset = new...
	CityData capital = new CityData();
	
	public void initialize(String cap,UserBox box) {
		
		VerifyCap verifica = new VerifyCap();
		
		if(!verifica.verify(cap))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The city is not a capital");
		
		Box b = new Box();
		BoxCalculating bc = new BoxCalculating();
		//bc.setLenght(ub.getLenght());  IN ATTESA DELLE COORDINATE DELLA CAPITALE(CENTRO DEL BOX)
		//bc.setWidth(ub.getWidth());
		if(!bc.verifyBox())
			return new ResponseEntity<>("The box is too big", HttpStatus.BAD_REQUEST);
		b = bc.generaBox();
	}
}
