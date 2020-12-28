package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;
import bg.Weather.service.StatsService;

public class TempmaxStats extends StatsService implements Stat{

	private LinkedList<Double> tempMax = new LinkedList<Double>();
	
	public TempmaxStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		this.tempMax = super.calc.ottieniMassimi(dataset);
		return tempMax;
	}
}
