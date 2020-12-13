package bg.Weather.service;

import bg.Weather.model.Box;
import bg.Weather.model.UserBox;

public class BoxCalculating extends UserBox{

	private double lat_centro;
	private double long_centro;
	
	private double lungh_in_gradi;
	private double largh_in_gradi;

	final double km_to_deg = 0.009; //Costante per convertire una grandezza da km in gradi
	
	//Metodo che controlla se la grandezza del box è accettabile dall'API
		public boolean verifyBox() {
			if((this.lungh_in_gradi * this.largh_in_gradi) <= 25.00)
				return true;
			
			return false;      //DA SOSTITUIRE CON THROW EXCEPTION
		}
		
		public Box generaBox() {
			
			Box box = new Box();
			
			this.setLungh_in_gradi(super.getLenght() * 0.5 * km_to_deg); //Converto la semi-lunghezza del box in gradi decimali
			this.setLargh_in_gradi(super.getWidth() * 0.5 * km_to_deg); //Converto la semi-larghezza del box in gradi decimali
			
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
