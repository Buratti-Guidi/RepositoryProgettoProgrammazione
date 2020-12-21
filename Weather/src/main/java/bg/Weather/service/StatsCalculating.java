package bg.Weather.service;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;
import bg.Weather.model.Stats;

public class StatsCalculating {
	
	private LinkedList<String> names = new LinkedList<String>();
	private LinkedList<Double> averages = new LinkedList<Double>();
	private LinkedList<Double> tempMax = new LinkedList<Double>();
	private LinkedList<Double> tempMin = new LinkedList<Double>();
	private LinkedList<Double> variances = new LinkedList<Double>();
	
	private LinkedList<Stats> stats = new LinkedList<Stats>();
	
	private Calculate calc; //Calcolatore utilizzato per eseguire i calcoli per le varie statistiche
	
	public StatsCalculating(int numeroGiorni) {
		calc = new Calculate(numeroGiorni);
	}

	public LinkedList<String> getNames(LinkedList<HashSet<HourCities>> dataset) {
		this.names = this.calc.ottieniNomi(dataset);
		return names;
	}

	public LinkedList<Double> getAverages(LinkedList<HashSet<HourCities>> dataset) {
		this.averages = this.calc.ottieniMedie(dataset);
		return averages;
	}

	public LinkedList<Double> getTempMax(LinkedList<HashSet<HourCities>> dataset) {
		this.tempMax = calc.ottieniMassimi(dataset);
		return tempMax;
	}

	public LinkedList<Double> getTempMin(LinkedList<HashSet<HourCities>> dataset) {
		this.tempMin = this.calc.ottieniMinimi(dataset);
		return tempMin;
	}
	
	public LinkedList<Double> getVariances(LinkedList<HashSet<HourCities>> dataset) {
		this.variances = this.calc.ottieniVarianze(dataset);
		return variances;
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