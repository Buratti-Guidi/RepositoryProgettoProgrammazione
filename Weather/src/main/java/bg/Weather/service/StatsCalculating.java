package bg.Weather.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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
	
	public void sortStats(LinkedList<String> cityNames, LinkedList<Double> values, boolean ascending) {
		LinkedList<String> n_final = new LinkedList<String>();
		LinkedList<Double> v_final = new LinkedList<Double>();
		
		int cont = 0;
		do {
			int indice = 0;
			Double min = Double.MIN_VALUE;
			for(int i = 0; i < values.size(); i++) {
				if(values.get(i) > min) {
					indice = i;
					min = values.get(i);
				}
			}
			v_final.push(values.get(indice));
			n_final.push(cityNames.get(indice));
			values.set(indice, Double.MIN_VALUE);
			cont++;
		} while(cont < values.size());
		if(ascending) {
			for(int i = 0; i < values.size(); i++) {
				values.set(i, v_final.get(i));
				cityNames.set(i, n_final.get(i));
			}
		}
		else {
			for(int i = values.size() - 1; i >= 0; i--) {
				values.set(values.size() - i - 1, v_final.get(i));
				cityNames.set(values.size() - i - 1, n_final.get(i));
			}
		}
	}
}
	
	
	/*
	public void sortStats(LinkedList<String> cityNames, LinkedList<Double> values, boolean ascending) {
		HashMap<String, Double> mp = new HashMap<String, Double>();
		
		for(int x = 0; x < cityNames.size();x++) {
			mp.put(cityNames.get(x), values.get(x));
		}
		Object[] sort = values.toArray();
		java.util.Arrays.sort(sort);
		
		Set<String> n = mp.keySet();
		Object[] namesArray = n.toArray();
		
		
			for(int x = 0; x < sort.length;x++) {
				for(int p = 0; p < namesArray.length ; p++) {
					if(mp.get((String)namesArray[p]) == sort[x]) {
						cityNames.set(x,(String)namesArray[p]); 
						break;
					}	
				}
				values.set(x,(Double)sort[x]);
			}
			
			if(!ascending) {
				int j = 0;
				for(int x = cityNames.size() - 1; x >= 0 ; x--,j++) {
					String nomeApp = cityNames.get(j);
					Double valueApp = values.get(j);
					
					cityNames.set(j, cityNames.get(x));
					values.set(j, values.get(x));
					
					cityNames.set(x, nomeApp);
					values.set(x, valueApp);
				}
				
			}
		/*
		if(ascending) {
			for(int x = 0; x < sort.length;x++) {
				for(int p = 0; p < namesArray.length ; p++) {
					if(mp.get((String)namesArray[p]) == sort[x]) {
						cityNames.set(x,(String)namesArray[p]); 
						break;
					}	
				}
				values.set(x,(Double)sort[x]);
			}
		}
		else {
			for(int x = sort.length - 1; x >= 0; x--) {
				for(int p = 0; p < namesArray.length ; p++) {
					if(mp.get((String)namesArray[p]) == sort[x]) {
						cityNames.set(x,(String)namesArray[p]); 
						break;
					}	
				}
				values.set(x,(Double)sort[x]);
			}
		}
		*/