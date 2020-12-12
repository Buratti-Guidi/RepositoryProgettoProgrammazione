package bg.Weather.service;

import bg.Weather.model.Box;

public class BoxCalculating {

	private double lat_centro;
	private double long_centro;
	
	private double lunghezzaBox;
	private double larghezzaBox;
	
	private double lungh_in_gradi;
	private double largh_in_gradi;

	final double km_to_deg = 0.009; //Costante per convertire una grandezza da km in gradi
	
	//Il costruttore prende in ingresso i valori della larghezza e della lunghezza (in km) del box rettangolare
	public BoxCalculating(double lunghezzaBox, double larghezzaBox, double lat_centro, double long_centro) {
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
		
		public Box generaBox() {
			
			Box box = new Box();
			
			this.lungh_in_gradi = this.lunghezzaBox * 0.5 * km_to_deg; //Converto la semi-lunghezza del box in gradi decimali
			this.largh_in_gradi = this.larghezzaBox * 0.5 * km_to_deg; //Converto la semi-larghezza del box in gradi decimali
			
			box.setLatUp(lat_centro + lungh_in_gradi);
			box.setLatDown(lat_centro - lungh_in_gradi);
			box.setLonDx(long_centro + largh_in_gradi);
			box.setLonSx(long_centro - largh_in_gradi);
			
			return box;
		}
}
