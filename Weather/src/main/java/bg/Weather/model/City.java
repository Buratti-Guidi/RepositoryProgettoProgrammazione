package bg.Weather.model;

/**
 * Classe contenente i valori "fissi" di ogni città
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class City {

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
}
