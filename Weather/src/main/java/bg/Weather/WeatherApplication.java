package bg.Weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe che da avvio a tutto il programma
 * @author Christopher Buratti
 * @author Luca Guidi
 */

@SpringBootApplication
public class WeatherApplication {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

}
