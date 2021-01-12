package bg.Weather.application_Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bg.Weather.exception.InternalServerException;
import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;
import bg.Weather.service.JSONWeatherParser;

public class JSONWeatherParserTest {

	private JSONWeatherParser jwp;
	private JSONObject jo;
	
	@BeforeEach
	void setUp() throws Exception {
		jwp = new JSONWeatherParser();
		jo = new JSONObject();
		JSONParser jp = new JSONParser();
		try {
			jo = (JSONObject) jp.parse("{\r\n"
					+ "    \"cod\": 200,\r\n"
					+ "    \"calctime\": 0.002368854,\r\n"
					+ "    \"cnt\": 8,\r\n"
					+ "    \"list\": [\r\n"
					+ "        {\r\n"
					+ "            \"id\": 2267057,\r\n"
					+ "            \"dt\": 1610471385,\r\n"
					+ "            \"name\": \"Lisbon\",\r\n"
					+ "            \"coord\": {\r\n"
					+ "                \"Lon\": -9.1333,\r\n"
					+ "                \"Lat\": 38.7167\r\n"
					+ "            },\r\n"
					+ "            \"main\": {\r\n"
					+ "                \"temp\": 10.58,\r\n"
					+ "                \"feels_like\": 6.95,\r\n"
					+ "                \"temp_min\": 10,\r\n"
					+ "                \"temp_max\": 11.11,\r\n"
					+ "                \"pressure\": 1030,\r\n"
					+ "                \"humidity\": 43\r\n"
					+ "            },\r\n"
					+ "            \"visibility\": 10000,\r\n"
					+ "            \"wind\": {\r\n"
					+ "                \"speed\": 2.06,\r\n"
					+ "                \"deg\": 90\r\n"
					+ "            },\r\n"
					+ "            \"rain\": null,\r\n"
					+ "            \"snow\": null,\r\n"
					+ "            \"clouds\": {\r\n"
					+ "                \"today\": 0\r\n"
					+ "            },\r\n"
					+ "            \"weather\": [\r\n"
					+ "                {\r\n"
					+ "                    \"id\": 800,\r\n"
					+ "                    \"main\": \"Clear\",\r\n"
					+ "                    \"description\": \"clear sky\",\r\n"
					+ "                    \"icon\": \"01d\"\r\n"
					+ "                }\r\n"
					+ "            ]\r\n"
					+ "        },\r\n"
					+ "        {\r\n"
					+ "            \"id\": 2271071,\r\n"
					+ "            \"dt\": 1610471385,\r\n"
					+ "            \"name\": \"Barreiro\",\r\n"
					+ "            \"coord\": {\r\n"
					+ "                \"Lon\": -9.0724,\r\n"
					+ "                \"Lat\": 38.6631\r\n"
					+ "            },\r\n"
					+ "            \"main\": {\r\n"
					+ "                \"temp\": 9.86,\r\n"
					+ "                \"feels_like\": 6.06,\r\n"
					+ "                \"temp_min\": 8.89,\r\n"
					+ "                \"temp_max\": 10.56,\r\n"
					+ "                \"pressure\": 1030,\r\n"
					+ "                \"humidity\": 50\r\n"
					+ "            },\r\n"
					+ "            \"visibility\": 10000,\r\n"
					+ "            \"wind\": {\r\n"
					+ "                \"speed\": 2.57,\r\n"
					+ "                \"deg\": 10\r\n"
					+ "            },\r\n"
					+ "            \"rain\": null,\r\n"
					+ "            \"snow\": null,\r\n"
					+ "            \"clouds\": {\r\n"
					+ "                \"today\": 0\r\n"
					+ "            },\r\n"
					+ "            \"weather\": [\r\n"
					+ "                {\r\n"
					+ "                    \"id\": 800,\r\n"
					+ "                    \"main\": \"Clear\",\r\n"
					+ "                    \"description\": \"clear sky\",\r\n"
					+ "                    \"icon\": \"01d\"\r\n"
					+ "                }\r\n"
					+ "            ]\r\n"
					+ "        }\r\n"
					+ "    ]\r\n"
					+ "}");
		} catch (ParseException e) {
			
		}
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void test1() {
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
	
	@Test
	void test2() {
		CityData c1 = new CityData();
		Instant time1 = Instant.ofEpochSecond(1610471385);
		c1.setId(2267057);
		c1.setNome("Lisbon");
		c1.setLatitudine(38.7167);
		c1.setLongitudine(-9.1333);
		c1.setWindSpeed(2.06);
		c1.setTemp_feels_like(6.95);
		c1.setTemperatura(10.58);
		c1.setTime(Date.from(time1));
		
		CityData c2 = new CityData();
		Instant time2 = Instant.ofEpochSecond(1610471385);
		c2.setId(2271071);
		c2.setNome("Barreiro");
		c2.setLatitudine(38.6631);
		c2.setLongitudine(-9.0724);
		c2.setWindSpeed(2.57);
		c2.setTemp_feels_like(6.06);
		c2.setTemperatura(9.86);
		c2.setTime(Date.from(time2));
		
		HourCities testing = new HourCities();
		testing.addCity(c1);
		testing.addCity(c2);
		
		HourCities hc = new HourCities();
		jwp.parseBox(jo, hc);
		for(int i=0; i<hc.getSize(); i++) {
			if(hc.toString().equals("" + c1 + ", " + c2))
				assertEquals(hc.getHourCities().toString(), ("" + c1 + ", " + c2));
			else
				assertEquals(hc.getHourCities().toString(), ("" + c2 + ", " + c1));
		}
	}
	
	@Test
	void test3() {
		JSONObject j = new JSONObject();
		j.put("prova", "false");
		j.put("altra prova", 1000);
		
		HourCities test = new HourCities();
		
		InternalServerException e = assertThrows(InternalServerException.class, () -> {
			jwp.parseBox(j, test);
		});
		assertEquals("Errore dal box dell'API", e.getMessage());
	}
}
