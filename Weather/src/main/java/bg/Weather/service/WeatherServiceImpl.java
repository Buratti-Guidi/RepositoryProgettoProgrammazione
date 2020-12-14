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
	
	@Override
	public void initialize(String cap,UserBox box) {
		
		CityInfo verifica = new CityInfo();
		
		if(!verifica.verifyCap(cap))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The city is not a capital");
		
		capital.setNome(cap);
		
		verifica.getCoord(capital);//Metodo di VerifyCap che aggiunge le coordinate all attributo capital
		
		Box b = new Box();
		BoxCalculating bc = new BoxCalculating();
		
		//coordinate della capitale capital.getLatitudine() e capital.getLongitudine()
		
		//bc.setLenght(ub.getLenght()); 
		//bc.setWidth(ub.getWidth());
		if(!bc.verifyBox())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The box is too big");
		b = bc.generaBox();
	}
}
