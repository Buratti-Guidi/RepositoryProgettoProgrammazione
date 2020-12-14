package bg.Weather.model;

public class City {

	private String name;
	private long id;
	
	private double lat;
	private double lon;
	
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
