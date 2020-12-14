package bg.Weather.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

public class HourCities {

	private HashSet<CityData> hourCities = new HashSet<CityData>();

	public HashSet<CityData> getHourCities() {
		return hourCities;
	}

	public void setHourCities(HashSet<CityData> hourCities) {
		this.hourCities = hourCities;
	}
	
	public void addCity(CityData c) {
		hourCities.add(c);
	}
	
	public Calendar getCalendar() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		for(CityData c : hourCities) {
			cal.setTime(c.getTime());
			return cal;
		}
		return null;
	}
}
