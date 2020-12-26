package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;
import bg.Weather.service.StatsCalculating;

public class TempminStats extends StatsCalculating implements Stat{

	private LinkedList<Double> tempMin = new LinkedList<Double>();
	
	public TempminStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		this.tempMin = super.calc.ottieniMinimi(dataset);
		return tempMin;
	}
}
