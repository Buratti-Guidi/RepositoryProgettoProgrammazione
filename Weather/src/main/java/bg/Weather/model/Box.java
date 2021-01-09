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
	
	/**
	 * Ottieni latitudine superiore del box
	 * @return latitudine superiore
	 */
	public double getLatUp() {
		return latUp;
	}
	
	/**
	 * Imposta latitudine superiore del box
	 * @param latUp latitudine superiore
	 */
	public void setLatUp(double latUp) {
		this.latUp = latUp;
	}
	
	/**
	 * Ottieni latitudine inferiore del box
	 * @return latitudine inferiore
	 */
	public double getLatDown() {
		return latDown;
	}
	
	/**
	 * Imposta latitudine inferiore del box
	 * @param latDown latitudine inferiore
	 */
	public void setLatDown(double latDown) {
		this.latDown = latDown;
	}
	
	/**
	 * Ottieni longitudine sinistra del box
	 * @return longitudine sinistra
	 */
	public double getLonSx() {
		return lonSx;
	}
	
	/**
	 * Imposta longitudine sinistra del box
	 * @param lonSx longitudine sinistra
	 */
	public void setLonSx(double lonSx) {
		this.lonSx = lonSx;
	}
	
	/**
	 * Ottieni longitudine destra del box
	 * @return longitudine destra
	 */
	public double getLonDx() {
		return lonDx;
	}
	
	/**
	 * Imposta longitudine destra del box
	 * @param lonDx longitudine destra
	 */
	public void setLonDx(double lonDx) {
		this.lonDx = lonDx;
	}
}
