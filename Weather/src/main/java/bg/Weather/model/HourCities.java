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

	private HashSet<CityData> hourCities = new HashSet<CityData>(); //HashSet delle città ottenute durante una chiamata API

	/**
	 * Ottieni i dati delle città della chiamata API oraria
	 * @return dati delle città della chiamata API oraria
	 */
	public HashSet<CityData> getHourCities() {
		return hourCities;
	}

	/**
	 * Imposta i dati delle città della chiamata API oraria
	 * @param hourCities dati delle città della chiamata API oraria
	 */
	public void setHourCities(HashSet<CityData> hourCities) {
		this.hourCities = hourCities;
	}
	
	/**
	 * Aggiungi una città all'HashSet contenente le città della chiamata API oraria
	 * @param c città della chiamata API oraria
	 */
	public void addCity(CityData c) {
		hourCities.add(c);
	}
	
	/**
	 * Ottieni la data della chiamata API oraria
	 * @return data della chiamata API oraria
	 * @throws NullPointerException
	 */
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
	
	/**
	 * Ottieni il numero delle città di una chiamata API oraria
	 * @return numero delle città di una chiamata API oraria
	 */
	public int getSize() {
		return this.hourCities.size();
	}
}
