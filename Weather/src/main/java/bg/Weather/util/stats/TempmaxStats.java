package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;
import bg.Weather.service.StatsCalculating;

public class TempmaxStats extends StatsCalculating implements Stat{

	private LinkedList<Double> tempMax = new LinkedList<Double>();
	
	public TempmaxStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		this.tempMax = super.calc.ottieniMassimi(dataset);
		return tempMax;
	}
}
