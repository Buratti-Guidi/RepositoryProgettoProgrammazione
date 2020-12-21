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
	
	/*
	public LinkedList<Stats> getStats() {
		Stats s = new Stats();
		LinkedList<String> nomi = new LinkedList<String>();
		nomi = this.getNames(LinkedList<HashSet<HourCities>> dataset);
		for(int i = 0; i < ; i++) {
			s.setName(this.names.get(i));
			s.setAvgTemp(this.averages.get(i));
			s.setTempMax(this.tempMax.get(i));
			s.setTempMin(this.tempMin.get(i));
			s.setVar(this.variances.get(i));
			this.stats.add(s);
		}
		return stats;
	}
	*/

}	
	
	/*
	public Vector<Stats> calculateAll(LinkedList<HashSet<HourCities>> dataset, int days) {
		/*
		if(days > 30)
			throw new troppiDaysException();
		*
		
		int count = 0; //Numero città del box
		boolean flag = false; //Se è false, il ciclo for each è al primo giro (alla prima ora) e mi salva i nomi delle città in names e il numero di città (count)
		
		int tot_hours = 0; //Numero totale delle ore "lette" in un numero di "days" giorni
		
		for(int i = 0; i < days; i++) {
			HashSet<HourCities> temp = new HashSet<HourCities>();
			temp = dataset.get(i);
			for(HourCities hc : temp) {
				if(!flag) {
					for(CityData cd : hc.getHourCities()) {
						names.push(cd.getNome());
						sums.push(cd.getTemperatura());
						tempMax.push(cd.getTemperatura());
						tempMin.push(cd.getTemperatura());
						count++;
					}
					tot_hours++;
					flag = true;
				}
				else {
					int j = 0;
					for(CityData cd : hc.getHourCities()) {
						sums.set(count - j - 1, sums.get(count - j - 1) + cd.getTemperatura());
						
						if(tempMax.get(count - j - 1) < cd.getTemperatura())
							tempMax.set(count - j - 1, cd.getTemperatura());
						
						if(tempMin.get(count - j - 1) < cd.getTemperatura())
							tempMin.set(count - j - 1, cd.getTemperatura());
						
						j++;
					}
					tot_hours++;
				}
			}
		}

		for(int x = 0; x < count; x++)
			averages.push(sums.get(count - x - 1) / tot_hours);
		
		Vector<Stats> result = new Vector<Stats>();
		
		for(int y = 0; y < count; y++) {
			Stats s = new Stats();
			s.setAvgTemp(averages.get(count - y - 1));
			s.setName(names.get(count - y - 1));
			s.setTempMax(tempMax.get(count - y - 1));
			s.setTempMin(tempMin.get(count - y - 1));
			result.add(s);
		}
		
		return result;
	}
	*/
