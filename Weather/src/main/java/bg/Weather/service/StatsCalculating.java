package bg.Weather.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;
import bg.Weather.model.Stats;

public class StatsCalculating {
	
	private LinkedList<String> names = new LinkedList<String>();
	private LinkedList<Double> averages = new LinkedList<Double>();
	private LinkedList<Double> tempMax = new LinkedList<Double>();
	private LinkedList<Double> tempMin = new LinkedList<Double>();
	private LinkedList<Double> variances = new LinkedList<Double>();
	
	private Calculate calc;
	
	public StatsCalculating(LinkedList<HashSet<HourCities>> dataset, int numeroGiorni) {
		calc = new Calculate(dataset, numeroGiorni);
		this.names = calc.ottieniNomi();
		this.averages = calc.ottieniMedie();
		this.tempMax = calc.ottieniMassimi();
		this.tempMin = calc.ottieniMinimi();
		this.variances = calc.ottieniVarianze();
	}

	public LinkedList<String> getNames() {
		return names;
	}

	public LinkedList<Double> getAverages() {
		return averages;
	}

	public LinkedList<Double> getTempMax() {
		return tempMax;
	}

	public LinkedList<Double> getTempMin() {
		return tempMin;
	}
	
	public LinkedList<Double> getVariances() {
		return variances;
	}

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
