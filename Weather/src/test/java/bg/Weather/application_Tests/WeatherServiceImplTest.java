package bg.Weather.application_Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bg.Weather.exception.UserErrorException;
import bg.Weather.service.WeatherServiceImpl;

/**
 * Classe che testa alcuni metodi di WeatherServiceImpl
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class WeatherServiceImplTest {

	private WeatherServiceImpl ws;
	
	/**
	 * Viene eseguito prima dell’esecuzione di ogni test, inizializza gli oggetti necessari per l'esecuzione dei test
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		ws = new WeatherServiceImpl();
	}

	/**
	 * Viene eseguito dopo ogni caso di test, resetta le postcondizioni
	 * @throws Exception
	 */
	@AfterEach
	public void tearDown() throws Exception {
	}
	
	/**
	 * Test per verificare la corretta gestione delle eccezioni del metodo "initialize"
	 * controlla la gestione dell'eccezione derivata da una richiesta errata dell'utente, ovvero la richiesta di un box troppo grande
	 */
	@Test
	public void test1() {
		JSONObject jo = new JSONObject();
		jo.put("length", 80);
		jo.put("width", 1000000);
		
		UserErrorException e = assertThrows(UserErrorException.class, () -> {
			ws.initialize("Lisbon", jo);
		});
		assertEquals("The box isn't acceptable", e.getMessage());
	}
	
	/**
	 * Test per verificare la corretta gestione delle eccezioni del metodo "initialize"
	 * controlla la gestione dell'eccezione derivata da una richiesta errata dell'utente, ovvero l'errato inserimento del nome di una capitale
	 */
	@Test
	public void test2() {
		JSONObject jo = new JSONObject();
		jo.put("length", 80);
		jo.put("width", 220);
		
		UserErrorException e = assertThrows(UserErrorException.class, () -> {
			ws.initialize("Ancona", jo);
		});
		assertEquals("The city is not a capital", e.getMessage());
	}
}
