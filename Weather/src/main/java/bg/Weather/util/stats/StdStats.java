package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;
import bg.Weather.service.StatsCalculating;

public class StdStats extends StatsCalculating implements Stat{

	private LinkedList<Double> devStd = new LinkedList<Double>();
	
	public StdStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		this.devStd = super.calc.ottieniVarianze(dataset);
		for(int i = 0; i < devStd.size(); i++) 
			devStd.set(i, Math.sqrt(devStd.get(i)));
		
		return this.devStd;
	}
}
