package bg.Weather.application_Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bg.Weather.exception.UserErrorException;
import bg.Weather.model.CityData;
import bg.Weather.service.JSONWeatherParser;
import bg.Weather.service.StatsService;
import bg.Weather.service.WeatherServiceImpl;
import bg.Weather.util.stats.AvgStats;
import bg.Weather.util.stats.Stat;

class JTest {

	private StatsService statS;
	private WeatherServiceImpl ws;
	private JSONWeatherParser jwp;
	
	@BeforeEach
	void setUp() throws Exception {
		statS = new StatsService();
		ws = new WeatherServiceImpl();
		jwp = new JSONWeatherParser();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test1() {
		Stat s = statS.getStat("avg", 3);
		assertEquals(new AvgStats(3).getClass(), s.getClass());
		
		UserErrorException e = assertThrows(UserErrorException.class, () -> {
			statS.getStat("xxx", 4);
		});
		assertEquals("This stat doesn't exist", e.getMessage());
	}
	
	@Test
	void test2() {
		JSONObject jo = new JSONObject();
		jo.put("length", 80);
		jo.put("width", 1000000);
		
		UserErrorException e = assertThrows(UserErrorException.class, () -> {
			ws.initialize("Lisbon", jo);
		});
		assertEquals("The box isn't acceptable", e.getMessage());
	}
	
	@Test
	void test3() {
		JSONObject jo = new JSONObject();
		jo.put("length", 80);
		jo.put("width", 220);
		
		UserErrorException e = assertThrows(UserErrorException.class, () -> {
			ws.initialize("Ancona", jo);
		});
		assertEquals("The city is not a capital", e.getMessage());
	}
	
	@Test
	void test4() {
		CityData emptyCity = new CityData();
		CityData fullCity = new CityData();
		JSONObject tot = new JSONObject();
		JSONObject coord = new JSONObject();
		
		fullCity.setLatitudine(27.89);
		fullCity.setLongitudine(35.48);
		fullCity.setId(8834942);
		
		coord.put("lon", 35.48);
		coord.put("lat", 27.89);
		
		tot.put("coord", coord);
		tot.put("id", 8834942);
		
		jwp.parseCity(tot, emptyCity);
		
		assertEquals(fullCity.getId(), emptyCity.getId());
		assertEquals(fullCity.getLatitudine(), emptyCity.getLatitudine());
		assertEquals(fullCity.getLongitudine(), emptyCity.getLongitudine());
	}

}
