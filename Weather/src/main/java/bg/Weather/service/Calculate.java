package bg.Weather.service;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;

/**
 * Classe utilizzata per lo svolgimento dei calcoli aritmetici per il calcolo delle statistiche sulle temperature (media, varianza, temperatura massima/minima)
 * in un "numeroGiorni" di giorni e per l'ottenimento dei nomi delle città ordinato in modo tale da riuscire ad accedere per ogni città alle proprie statistiche
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class Calculate {
	
	private int numeroGiorni; //numero di giorni a partire da oggi in cui ottenere le statistiche
	
	public Calculate(int numeroGiorni) {
		this.numeroGiorni = numeroGiorni;
	}

	public LinkedList<String> ottieniNomi(LinkedList<HashSet<HourCities>> dataset) {
		LinkedList<String> nomi = new LinkedList<String>();
		for(HourCities hc: dataset.getFirst()) {
			for(CityData c : hc.getHourCities()) {
				nomi.push(c.getNome());
			}
			break;
		}
		return nomi;
	}
	
	public LinkedList<Double> ottieniMedie(LinkedList<HashSet<HourCities>> dataset) {
		boolean flag = false; //Se è false, il ciclo for each è al primo giro (alla prima ora)
		
		int tot_hours = 0; //Numero totale delle ore "lette" in un numero di "numeroGiorni" giorni
		
		LinkedList<String> names = this.ottieniNomi(dataset);
		
		LinkedList<Double> sums = new LinkedList<Double>();
		LinkedList<Double> averages = new LinkedList<Double>();
		
		for(int i = 0; i < this.numeroGiorni; i++) {
			HashSet<HourCities> temp = new HashSet<HourCities>();
			temp = dataset.get(i);
			for(HourCities hc : temp) {
				if(!flag) {
						for(int k = 0; k < names.size(); k++) {
							for(CityData cd : hc.getHourCities()) {
								if(names.get(k).equals(cd.getNome())) {
									sums.add(k,cd.getTemperatura());		
									break;
								}
							}	
						}
					tot_hours++;
					flag = true;
				}
				else {
					for(int k = 0; k < names.size(); k++) {
						for(CityData cd : hc.getHourCities()) {
							if(names.get(k).equals(cd.getNome())) {
								sums.set(k,sums.get(k) + cd.getTemperatura());		
								break;
					
								}
							}
						}
					tot_hours++;
					}	
			}
		}

		for(int x = 0; x < names.size(); x++)
			averages.add(x,sums.get(x) / tot_hours);
		
		this.truncateTo(averages, 4);
		
		return averages;
	}
	
	public LinkedList<Double> ottieniMassimi(LinkedList<HashSet<HourCities>> dataset) {
		LinkedList<Double> tempMax = new LinkedList<Double>();
		LinkedList<String> names = this.ottieniNomi(dataset);
		
		boolean flag = false;
		
		for(int i = 0; i < this.numeroGiorni; i++) {
			HashSet<HourCities> temp = new HashSet<HourCities>();
			temp = dataset.get(i);
			for(HourCities hc : temp) {
				if(!flag) {
					
					for(int k = 0; k < names.size(); k++) {
						for(CityData cd : hc.getHourCities()) {
							if(names.get(k).equals(cd.getNome())) {
								tempMax.add(k,cd.getTemperatura());		
								break;
							}
						}	
					}
					flag = true;
				}
				else {
					
					for(int k = 0; k < names.size(); k++) {
						for(CityData cd : hc.getHourCities()) {
							if(names.get(k).equals(cd.getNome())) {
								if(tempMax.get(k) < cd.getTemperatura())
									tempMax.set(k, cd.getTemperatura());	
								break;
								}
							}
						}
				}
			}
		}
		return tempMax;
	}
	
	public LinkedList<Double> ottieniMinimi(LinkedList<HashSet<HourCities>> dataset) {
		LinkedList<Double> tempMin = new LinkedList<Double>();
		LinkedList<String> names = this.ottieniNomi(dataset);
		
		boolean flag = false;
		
		for(int i = 0; i < this.numeroGiorni; i++) {
			HashSet<HourCities> temp = new HashSet<HourCities>();
			temp = dataset.get(i);
			for(HourCities hc : temp) {
				if(!flag) {
					
					for(int k = 0; k < names.size(); k++) {
						for(CityData cd : hc.getHourCities()) {
							if(names.get(k).equals(cd.getNome())) {
								tempMin.add(k,cd.getTemperatura());		
								break;
							}
						}	
					}
					flag = true;
				}
				else {
					
					for(int k = 0; k < names.size(); k++) {
						for(CityData cd : hc.getHourCities()) {
							if(names.get(k).equals(cd.getNome())) {
								if(tempMin.get(k) > cd.getTemperatura())
									tempMin.set(k, cd.getTemperatura());	
								break;
								}
							}
						}
				}
			}
		}
		return tempMin;
	}
	
	public LinkedList<Double> ottieniVarianze(LinkedList<HashSet<HourCities>> dataset) {
		LinkedList<Double> averages = this.ottieniMedie(dataset);
		LinkedList<String> names = this.ottieniNomi(dataset);
		
		LinkedList<Double> variances = new LinkedList<Double>();
		
		boolean flag = false; //Se è false, il ciclo for each è al primo giro (alla prima ora) e salva il numero di città (count)
		
		int tot_hours = 0; //Numero totale delle ore "lette" in un numero di "numeroGiorni" giorni
		
		for(int i = 0; i < this.numeroGiorni; i++) {
			HashSet<HourCities> temp = new HashSet<HourCities>();
			temp = dataset.get(i);
			for(HourCities hc : temp) {
				
				for(int k = 0; k < names.size(); k++) {
					
					for(CityData cd : hc.getHourCities()) {
						if(names.get(k).equals(cd.getNome())) {
								
							double scarto;
							scarto = cd.getTemperatura() - averages.get(k);
							scarto *= scarto;
							variances.add(k,scarto);
							break;
						}
					}	
				}
				tot_hours++;
			}
		}
		
		
		for(int i = 0; i < names.size(); i++)
			variances.set(i, variances.get(i)/tot_hours);
		
		this.truncateTo(variances, 4);
		
		return variances;
	}
	
	//DA CONTROLLARE
	public void truncateTo(LinkedList<Double> unroundedNumber, int decimalPlaces){
		LinkedList<Integer> truncatedNumberInt = new LinkedList<Integer>();
		LinkedList<Double> truncatedNumber = new LinkedList<Double>();
		for(int i = unroundedNumber.size() - 1; i >= 0; i--) {
			truncatedNumberInt.add((int)(unroundedNumber.get(i) * Math.pow(10, decimalPlaces)));
			truncatedNumber.push((double)(truncatedNumberInt.get(unroundedNumber.size() - i - 1) / Math.pow(10, decimalPlaces)));//DA PROVARE CON PUSH
		}
		unroundedNumber.clear();
		unroundedNumber.addAll(truncatedNumber);
	}
}
