package bg.Weather.model;

import java.util.Date;

public class City {

	private String name;
	private long id;
	
	private double lat;
	private double lon;
	
	private double temp;
	private double temp_feels_like;
	
	private double windSpeed;

	
	Date time;
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
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
	
	
	public double getTemperatura() {
		return temp;
	}
	public void setTemperatura(double temp) {
		this.temp = temp;
	}
	
	
	public double getTemp_feels_like() {
		return temp_feels_like;
	}
	public void setTemp_feels_like(double temp_feels_like) {
		this.temp_feels_like = temp_feels_like;
	}
	
	
	public double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	
	
	public String toString() {
		return ("nome: " + this.getNome() + ", id: " + this.getId() + ", latitudine: " + this.getLatitudine() + ", longitudine: " + this.getLongitudine() 
		+ ", temperatura: " + this.getTemperatura() + ", percepita: " + this.getTemp_feels_like() + ", velocità vento(m/s): " +this.getWindSpeed()
		+ ", dt: " + this.getTime());
	}
	
}
