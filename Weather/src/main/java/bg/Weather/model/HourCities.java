package bg.Weather.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Classe contenente i dati di tutte le città interne al box in ogni chiamata oraria dell'API
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class HourCities {

	private HashSet<CityData> hourCities = new HashSet<CityData>(); //HashSet delle città ottenute durante una chiamata

	public HashSet<CityData> getHourCities() {
		return hourCities;
	}

	public void setHourCities(HashSet<CityData> hourCities) {
		this.hourCities = hourCities;
	}
	
	public void addCity(CityData c) {
		hourCities.add(c);
	}
	
	public Calendar getCalendar() throws NullPointerException{
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
		try {
			for(CityData c : hourCities) {
				cal.setTime(c.getTime());
				return cal;
			}
		}catch(NullPointerException e) {
			throw e;
		}
		
		return null;
	}
	
	public int getSize() {
		return this.hourCities.size();
	}
}
