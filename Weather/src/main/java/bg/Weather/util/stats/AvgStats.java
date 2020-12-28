package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;
import bg.Weather.service.StatsService;

public class AvgStats extends StatsService implements Stat{

	private LinkedList<Double> averages = new LinkedList<Double>();
	
	public AvgStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		this.averages = super.calc.ottieniMedie(dataset);
		return averages;
	}
}
