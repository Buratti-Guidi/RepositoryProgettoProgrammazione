package bg.Weather.service;

import bg.Weather.exception.UserErrorException;
import bg.Weather.model.Box;

/**
 * Calcola il box di coordinate a partire dalle lunghezze in chilometri e dalle coordinate della capitale
 * @author Christopher Buratti
 * @author Luca Guidi
 */
public class BoxCalculator {

	private double lat_centro; 
	private double long_centro; 
	
	private double lungh_in_gradi; 
	private double largh_in_gradi; 

	private final double km_to_deg = 0.009; //costante per convertire una grandezza da km in gradi decimali
	
	/**
	 * Assegna le coordinate della capitale
	 * @param lat_centro 
	 * @param long_centro
	 */
	public BoxCalculator(double lat_centro, double long_centro) {
		this.lat_centro = lat_centro;
		this.long_centro = long_centro;
	}
	
	/**
	 * Calcola il box di coordinate 
	 * @param length lunghezza in km
	 * @param width larghezza in km
	 * @return Box di coordinate
	 * @throws UserErrorException
	 */
	public Box generaBox(double length, double width) throws UserErrorException{
			
		Box box = new Box();
			
		this.lungh_in_gradi = length * 0.5 * km_to_deg; //Converto la semi-lunghezza del box in gradi decimali
		this.largh_in_gradi = width * 0.5 * km_to_deg; //Converto la semi-larghezza del box in gradi decimali
			
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

	public double getLong_centro() {
		return long_centro;
	}

	public double getLungh_in_gradi() {
		return lungh_in_gradi;
	}

	public double getLargh_in_gradi() {
		return largh_in_gradi;
	}
}
