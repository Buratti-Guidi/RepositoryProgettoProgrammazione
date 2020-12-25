package bg.Weather.model;

/**
 * Classe che contiene i valori delle coordinate in cui il box (scelto dall'utente) è situato nel pianeta, così da essere in grado di effettuare le
 * chiamate API tramite openweathermap
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class Box {

	private double latUp; //Latitudine superiore
	private double latDown; //Latitudine inferiore
	private double lonSx; //longitudine sinistra			I valori sono espressi in gradi decimali
	private double lonDx; //longitudine destra
	
	public double getLatUp() {
		return latUp;
	}
	
	public void setLatUp(double latUp) {
		this.latUp = latUp;
	}
	
	
	public double getLatDown() {
		return latDown;
	}
	
	public void setLatDown(double latDown) {
		this.latDown = latDown;
	}
	
	
	public double getLonSx() {
		return lonSx;
	}
	
	public void setLonSx(double lonSx) {
		this.lonSx = lonSx;
	}
	
	
	public double getLonDx() {
		return lonDx;
	}
	
	public void setLonDx(double lonDx) {
		this.lonDx = lonDx;
	}
	
}
