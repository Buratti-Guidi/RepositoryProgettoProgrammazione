package bg.Weather.util;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;

public class Stats {

	//private double average; VA MESSO A MATRICE
	//private double variance;
	
	private LinkedList<String> names = new LinkedList<String>();
	private int count = 0; //Numero città del box
	private boolean flag = false; //Se è false, il ciclo for each è al primo giro (alla prima ora) e mi salva i nomi delle città in names e il numero di città (count)
	
	public HourCities calculateAverage(LinkedList<HashSet<HourCities>> dataset, int days) {
		/*
		if(days > 30)
			throw new troppiDaysException();
		*/
		
		LinkedList<Double> averages = new LinkedList<Double>();
		LinkedList<Double> sums = new LinkedList<Double>();
		int tot_hours = 0; //Numero totale delle ore "lette" in un numero di "days" giorni
		
		for(int i = 0; i < days; i++) {
			HashSet<HourCities> temp = new HashSet<HourCities>();
			temp = dataset.get(i);
			for(HourCities hc : temp) {
				if(!flag) {
					for(CityData cd : hc.getHourCities()) {
						names.push(cd.getNome());
						sums.push(cd.getTemperatura());
						count++;
					}
					tot_hours++;
					flag = true;
				}
				else {
					int j = 0;
					for(CityData cd : hc.getHourCities()) {
						sums.set(count - j - 1, sums.get(count - j - 1) + cd.getTemperatura());
						j++;
					}
					tot_hours++;
				}
			}
		}

		for(int x = 0; x < count; x++)
			averages.push(sums.get(count - x - 1) / tot_hours);
		
		HourCities result = new HourCities();
		
		for(int y = 0; y < count; y++) {
			CityData c = new CityData();
			c.setNome(names.get(count - y - 1));
			c.setTemperatura(averages.get(count - y - 1));
			result.addCity(c);
		}
		
		return result;
	}
}
