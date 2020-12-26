package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;
import bg.Weather.model.Stats;
import bg.Weather.service.StatsCalculating;

public class AllStats extends StatsCalculating{

	private LinkedList<Stats> stats = new LinkedList<Stats>();
	
	public AllStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	public LinkedList<Stats> getStats(LinkedList<HashSet<HourCities>> dataset) {
		LinkedList<Double> medie = new LinkedList<Double>();
		medie = this.getAverages(dataset);
		LinkedList<Double> varianze = new LinkedList<Double>();
		varianze = this.getVariances(dataset);
		LinkedList<Double> temperatureMax = new LinkedList<Double>();
		temperatureMax = this.getTempMax(dataset);
		LinkedList<Double> temperatureMin = new LinkedList<Double>();
		temperatureMin = this.getTempMin(dataset);
		LinkedList<String> nomi = new LinkedList<String>();
		nomi = this.getNames(dataset);
		
		for(int i=0; i<nomi.size(); i++) {
			Stats s = new Stats();
			s.setName(nomi.get(i));
			s.setAvgTemp(medie.get(i));
			s.setTempMax(temperatureMax.get(i));
			s.setTempMin(temperatureMin.get(i));
			s.setVar(varianze.get(i));
			this.stats.add(s);
		}
		return stats;
	}
}
