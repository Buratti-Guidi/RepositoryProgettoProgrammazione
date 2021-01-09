package bg.Weather.model;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Classe contenente i dati "variabili" di ogni città
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class CityData extends City{

	private double temp; //temperatura
	private double temp_feels_like; //temperatura percepita
	
	private double windSpeed; //velocità del vento (in m/s)
	
	private Date time; //data in cui sono stati ottenuti i dati
	
	/**
	 * Ottieni la data dell'ottenimento delle informazioni
	 * @return data dell'ottenimento delle informazioni
	 */
	public Date getTime() {
		return this.time;
	}
	
	/**
	 * Imposta la data dell'ottenimento delle informazioni
	 * @param time data dell'ottenimento delle informazioni
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	
	/**
	 * Ottieni la temperatura
	 * @return temperatura
	 */
	public double getTemperatura() {
		return temp;
	}
	
	/**
	 * Imposta la temperatura
	 * @param temp temperatura
	 */
	public void setTemperatura(double temp) {
		this.temp = temp;
	}
	
	/**
	 * Ottieni la temperatura percepita
	 * @return temperatura percepita
	 */
	public double getTemp_feels_like() {
		return temp_feels_like;
	}
	
	/**
	 * Imposta la temperatura percepita
	 * @param temp_feels_like temperatura percepita
	 */
	public void setTemp_feels_like(double temp_feels_like) {
		this.temp_feels_like = temp_feels_like;
	}
	
	/**
	 * Ottieni la velocità del vento
	 * @return velocità del vento
	 */
	public double getWindSpeed() {
		return windSpeed;
	}
	
	/**
	 * Imposta la velocità del vento
	 * @param windSpeed velocità del vento
	 */
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	
	/**
	 * Generazione stringa per rappresentare l'oggetto CityData
	 * @return stringa per rappresentare l'oggetto CityData
	 */
	public String toString() {
		return ("nome: " + super.getNome() + ", id: " + super.getId() + ", latitudine: " + super.getLatitudine() + ", longitudine: " + super.getLongitudine() 
		+ ", temperatura: " + this.getTemperatura() + ", percepita: " + this.getTemp_feels_like() + ", velocità vento(m/s): " +this.getWindSpeed()
		+ ", dt: " + this.getTime());
	}
	
	/**
	 * Associo a ogni chiave il valore corrispondente
	 * @return chiavi con valori associati
	 */
	public LinkedHashMap<String, Object> getAllHashMap() {
		LinkedHashMap<String, Object> all = new LinkedHashMap<String, Object>();
		all.put("name", super.getNome());
		all.put("id", super.getId());
		all.put("lat", super.getLatitudine());
		all.put("lon", super.getLongitudine());
		all.put("temp", this.getTemperatura());
		all.put("temp_feels_like", this.getTemp_feels_like());
		all.put("windSpeed", this.getWindSpeed());
		all.put("time", this.getTime().toString());
		return all;
	}
}
