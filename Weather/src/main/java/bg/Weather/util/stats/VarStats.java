package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;
import bg.Weather.service.StatsCalculating;

public class VarStats extends StatsCalculating implements Stat{

	private LinkedList<Double> variances = new LinkedList<Double>();
	
	public VarStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		this.variances = super.calc.ottieniVarianze(dataset);
		return variances;
	}
}
