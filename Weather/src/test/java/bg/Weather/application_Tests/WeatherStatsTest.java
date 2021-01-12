package bg.Weather.application_Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bg.Weather.exception.UserErrorException;
import bg.Weather.util.stats.AvgStats;
import bg.Weather.util.stats.Stat;
import bg.Weather.util.stats.WeatherStats;

class WeatherStatsTest {

	private WeatherStats statS;
	
	@BeforeEach
	void setUp() throws Exception {
		statS = new WeatherStats();
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
}
