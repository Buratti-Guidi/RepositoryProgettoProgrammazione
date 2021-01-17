package bg.Weather.application_Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bg.Weather.exception.UserErrorException;
import bg.Weather.util.stats.AvgStats;
import bg.Weather.util.stats.Stat;
import bg.Weather.util.stats.WeatherStats;

/**
 * Classe che testa alcuni metodi di WeatherStats
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class WeatherStatsTest {

	private WeatherStats statS;
	
	/**
	 * Viene eseguito prima dell’esecuzione di ogni test, inizializza gli oggetti necessari per l'esecuzione dei test
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		statS = new WeatherStats();
	}

	/**
	 * Viene eseguito dopo ogni caso di test, resetta le postcondizioni
	 * @throws Exception
	 */
	@AfterEach
	public void tearDown() throws Exception {
	}

	/**
	 * Test per verificare il corretto funzionamento del metodo "getStat"
	 * controlla che venga correttamente restituito un oggetto della classe richiesta tramite il proprio nome
	 */
	@Test
	public void test1() {
		Stat s = statS.getStat("avg", 3);
		assertEquals(new AvgStats(3).getClass(), s.getClass());
	}
	
	/**
	 * Test per verificare la corretta gestione delle eccezioni del metodo "getStat"
	 * controlla la gestione dell'eccezione derivata da una richiesta errata, ovvero la richiesta di una classe non esistente
	 */
	@Test
	public void test2() {
		UserErrorException e = assertThrows(UserErrorException.class, () -> {
			statS.getStat("xxx", 4);
		});
		assertEquals("This stat doesn't exist", e.getMessage());
	}
}
