package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;
import bg.Weather.service.StatsCalculating;

public class AvgStats extends StatsCalculating implements Stat{

	private LinkedList<Double> averages = new LinkedList<Double>();
	
	public AvgStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		this.averages = super.calc.ottieniMedie(dataset);
		return averages;
	}
}
