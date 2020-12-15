/**
 * 
 */
package bg.Weather.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import bg.Weather.database.Database;
import bg.Weather.model.Box;
import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;
import bg.Weather.model.UserBox;
import bg.Weather.util.APIKey;

/**
 * @author Luca
 *
 */
@Service
public class WeatherServiceImpl implements WeatherService {

	Database dataset = new Database();
	CityData capital = new CityData();
	
	@Override
	public void initialize(String cap, UserBox ub) {
		
		CityInfo verifica = new CityInfo();
		
		if(!verifica.verifyCap(cap))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The city is not a capital");
		
		capital.setNome(cap);
		verifica.getCoord(capital);//Metodo di VerifyCap che aggiunge le coordinate all' attributo capital
		
		Box b = new Box();
		BoxCalculating bc = new BoxCalculating(capital.getLatitudine(), capital.getLongitudine());
		bc.setLenght(ub.getLenght()); 
		bc.setWidth(ub.getWidth());
		
		if(!bc.verifyBox())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The box isn't acceptable");
		
		b = bc.generaBox();
		
		String url;
		APIKey ak = new APIKey();
		url = "http://api.openweathermap.org/data/2.5/box/city?bbox=" + b.getLonSx() + "," + b.getLatDown() + "," + b.getLonDx() + 
				"," + b.getLatUp() + ",10&appid=" + ak.getAPIKey();
		DownloadJSON fileJSON = new DownloadJSON();
		fileJSON.chiamataAPIObj(url);
		
		HourCities cities = new HourCities();
		JSONWeatherParser jwp = new JSONWeatherParser();
		jwp.parseBox(fileJSON.getObject(), cities);
		
		dataset.aggiornaDatabase(cities);
	}
}
