package bg.Weather.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

public class CityData extends City{

	private double temp;
	private double temp_feels_like;
	
	private double windSpeed;
	
	private Date time;
	
	public Date getTime() {
		return this.time;
	}
	
	public void setTime(Date time) {
		this.time = time;
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
		return ("nome: " + super.getNome() + ", id: " + super.getId() + ", latitudine: " + super.getLatitudine() + ", longitudine: " + super.getLongitudine() 
		+ ", temperatura: " + this.getTemperatura() + ", percepita: " + this.getTemp_feels_like() + ", velocità vento(m/s): " +this.getWindSpeed()
		+ ", dt: " + this.getTime());
	}
	
	public HashMap<String, Object> getAllHashMap() {
		HashMap<String, Object> all = new HashMap<String, Object>();
		all.put("name", super.getNome());
		all.put("id", super.getId());
		all.put("lat", super.getLatitudine());
		all.put("lon", super.getLongitudine());
		all.put("temp", this.getTemperatura());
		all.put("temp_feels_like", this.getTemp_feels_like());
		all.put("windSpeed", this.getWindSpeed());
		all.put("time", this.getTime());
		return all;
	}
	
}
