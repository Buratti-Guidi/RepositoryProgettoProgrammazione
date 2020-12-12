package bg.Weather.model;

public class Box {

	private double lat_up;
	private double lat_down;
	private double long_sx;
	private double long_dx;
	
	private double lungh_in_gradi;
	private double largh_in_gradi;
	
	private double lat_centro;
	private double long_centro;
	
	private double lunghezzaBox;
	private double larghezzaBox;
	
	final double km_to_deg = 0.009; //Costante per convertire una grandezza da km in gradi
	
	public double getLat_up() {
		return lat_up;
	}

	public void setLat_up(double lat_up) {
		this.lat_up = lat_up;
	}

	public double getLat_down() {
		return lat_down;
	}

	public void setLat_down(double lat_down) {
		this.lat_down = lat_down;
	}

	public double getLong_sx() {
		return long_sx;
	}

	public void setLong_sx(double long_sx) {
		this.long_sx = long_sx;
	}

	public double getLong_dx() {
		return long_dx;
	}

	public void setLong_dx(double long_dx) {
		this.long_dx = long_dx;
	}

	
	//Il costruttore prende in ingresso i valori della larghezza e della lunghezza (in km) del box rettangolare
	public Box(double lunghezzaBox, double larghezzaBox, double lat_centro, double long_centro) {
		this.lunghezzaBox = lunghezzaBox;
		this.larghezzaBox = larghezzaBox;
		this.lat_centro = lat_centro;
		this.long_centro = long_centro;
	}
	
	//Metodo che controlla se la grandezza del box è accettabile dall'API
	public boolean verifyBox() {
		if((this.lungh_in_gradi * this.largh_in_gradi) <= 25.00)
			return true;
		
		return false;      //DA SOSTITUIRE CON THROW EXCEPTION
	}
	
	public void generaBox() {
		
		this.lungh_in_gradi = this.lunghezzaBox * 0.5 * km_to_deg; //Converto la semi-lunghezza del box in gradi decimali
		this.largh_in_gradi = this.larghezzaBox * 0.5 * km_to_deg; //Converto la semi-larghezza del box in gradi decimali
		
		this.lat_up = lat_centro + lungh_in_gradi;
		this.lat_down = lat_centro - lungh_in_gradi;
		this.long_dx = long_centro + largh_in_gradi;
		this.long_sx = long_centro - largh_in_gradi;
	}
}
