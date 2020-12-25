package bg.Weather.service;

import bg.Weather.exception.UserErrorException;
import bg.Weather.model.Box;

/**
 * Classe che elabora i dati inseriti dall'utente e vi ricava i rispettivi dati da utilizzare durante le chiamate API a openweathermap
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class BoxCalculating {

	private double lat_centro; //latitudine del centro del box (ovvero della capitale)
	private double long_centro; //longitudine " " " "
	
	private double lungh_in_gradi; //lunghezza del box in gradi decimali
	private double largh_in_gradi; //larghezza del box in gradi decimali

	final double km_to_deg = 0.009; //costante per convertire una grandezza da km in gradi decimali
	
	public BoxCalculating(double lat_centro, double long_centro) {
		this.lat_centro = lat_centro;
		this.long_centro = long_centro;
	}
	
	//metodo per ottenere le coodinate del box conoscendo la lunghezza e la larghezza in km (e le coordinate del centro da costruttore)
	public Box generaBox(double length, double width){
			
		Box box = new Box();
			
		this.setLungh_in_gradi(length * 0.5 * km_to_deg); //Converto la semi-lunghezza del box in gradi decimali
		this.setLargh_in_gradi(width * 0.5 * km_to_deg); //Converto la semi-larghezza del box in gradi decimali
			
		if((this.lungh_in_gradi * this.largh_in_gradi) > 25.00 || (this.lungh_in_gradi * this.largh_in_gradi) <= 0)
			throw new UserErrorException("The box isn't acceptable");
		
		box.setLatUp(this.getLat_centro() + this.getLungh_in_gradi());
		box.setLatDown(this.getLat_centro() - this.getLungh_in_gradi());
		box.setLonDx(this.getLong_centro() + this.getLargh_in_gradi());
		box.setLonSx(this.getLong_centro() - this.getLargh_in_gradi());
			
		return box;
	}
		
	public double getLat_centro() {
		return lat_centro;
	}

	public void setLat_centro(double lat_centro) {
		this.lat_centro = lat_centro;
	}

	public double getLong_centro() {
		return long_centro;
	}

	public void setLong_centro(double long_centro) {
		this.long_centro = long_centro;
	}

	public double getLungh_in_gradi() {
		return lungh_in_gradi;
	}

	public void setLungh_in_gradi(double lungh_in_gradi) {
		this.lungh_in_gradi = lungh_in_gradi;
	}

	public double getLargh_in_gradi() {
		return largh_in_gradi;
	}

	public void setLargh_in_gradi(double largh_in_gradi) {
		this.largh_in_gradi = largh_in_gradi;
	}
}
