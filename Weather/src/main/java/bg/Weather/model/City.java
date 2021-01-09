package bg.Weather.model;

import java.util.Date;

/**
 * Classe astratta contenente i valori "fissi" di ogni città
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public abstract class City {

	protected String name; //nome della città
	protected long id; //id della città
	
	protected double lat; //latitudine
	protected double lon; //longitudine
	
	/**
	 * Ottieni il nome della città
	 * @return nome città
	 */
	public String getNome() {
		return name;
	}
	
	/**
	 * Imposta il nome della città
	 * @param name nome città
	 */
	public void setNome(String name) {
		this.name = name;
	}
	
	/**
	 * Ottieni l'id della città
	 * @return id città
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Imposta l'id della città
	 * @param id id città
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Ottieni la latitudine della città
	 * @return latitudine città
	 */
	public double getLatitudine() {
		return lat;
	}
	
	/**
	 * Imposta la latitudine della città
	 * @param lat latitudine città
	 */
	public void setLatitudine(double lat) {
		this.lat = lat;
	}
	
	/**
	 * Ottieni la longitudine della città
	 * @return longitudine città
	 */
	public double getLongitudine() {
		return lon;
	}
	
	/**
	 * Imposta la longitudine della città
	 * @param lon longitudine città
	 */
	public void setLongitudine(double lon) {
		this.lon = lon;
	}
	
	public abstract Date getTime();
	public abstract void setTime(Date time);
	
	public abstract double getTemperatura();
	public abstract void setTemperatura(double temp);
	
	public abstract double getTemp_feels_like();
	public abstract void setTemp_feels_like(double temp_feels_like);
	
	public abstract double getWindSpeed();
	public abstract void setWindSpeed(double windSpeed);
	
	public abstract String toString();
}
