package bg.Weather.application_Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bg.Weather.exception.UserErrorException;
import bg.Weather.service.WeatherServiceImpl;

public class WeatherServiceImplTest {

	private WeatherServiceImpl ws;
	
	@BeforeEach
	void setUp() throws Exception {
		ws = new WeatherServiceImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void test1() {
		JSONObject jo = new JSONObject();
		jo.put("length", 80);
		jo.put("width", 1000000);
		
		UserErrorException e = assertThrows(UserErrorException.class, () -> {
			ws.initialize("Lisbon", jo);
		});
		assertEquals("The box isn't acceptable", e.getMessage());
	}
	
	@Test
	void test2() {
		JSONObject jo = new JSONObject();
		jo.put("length", 80);
		jo.put("width", 220);
		
		UserErrorException e = assertThrows(UserErrorException.class, () -> {
			ws.initialize("Ancona", jo);
		});
		assertEquals("The city is not a capital", e.getMessage());
	}
}
