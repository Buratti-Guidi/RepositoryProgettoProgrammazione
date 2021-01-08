package bg.Weather.model;

import java.util.Date;

/**
 * Classe contenente i valori "fissi" di ogni città
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public abstract class City {

	protected String name; //nome della città
	protected long id; //id della città
	
	protected double lat; //latitudine
	protected double lon; //longitudine
	
	public String getNome() {
		return name;
	}
	
	public void setNome(String name) {
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public double getLatitudine() {
		return lat;
	}
	
	public void setLatitudine(double lat) {
		this.lat = lat;
	}
	
	public double getLongitudine() {
		return lon;
	}
	
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
