package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;
import bg.Weather.service.StatsService;

public class StdStats extends StatsService implements Stat{

	private LinkedList<Double> devStd = new LinkedList<Double>();
	
	public StdStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		this.devStd = super.calc.ottieniDevStd(dataset);
		return devStd;
	}
}
