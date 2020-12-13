/**
 * 
 */
package bg.Weather.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
			return new ResponseEntity<>("The city is not a capital", HttpStatus.BAD_REQUEST);
		
		Box b = new Box();
		BoxCalculating bc = new BoxCalculating();
		bc.setLenght(ub.getLenght());
		bc.setWidth(ub.getWidth());
	}
}
