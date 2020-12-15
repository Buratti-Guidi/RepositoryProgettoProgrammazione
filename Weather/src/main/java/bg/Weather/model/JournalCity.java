package bg.Weather.model;

import java.util.Date;
import java.util.Vector;

public class JournalCity {//ROMA

	private String name;
	private String id;
	
	private Date time;//ultima ora
	private Vector<Double> temp;//24
	
	private static double ultimaOraInserita;
	
	public void addTemp(double t) {
		this.temp.add(t);
	}
	
	public Vector<Double> getTemp(){
		return this.temp;
	}
}
