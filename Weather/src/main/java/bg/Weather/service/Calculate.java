package bg.Weather.service;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;

/**
 * Calcola i nomi delle città e le statistiche sulle temperature (media, varianza, temperatura massima/minima) in un "numeroGiorni" di giorni,
 * in modo tale che le liste di nomi e statistiche siano sempre appaiate
 * @author Christopher Buratti
 * @author Luca Guidi
 */
public class Calculate {
	
	private int numeroGiorni;
	
	/**
	 * Costruttore che inizializza il numero di giorni
	 * @param numeroGiorni
	 */
	public Calculate(int numeroGiorni) {
		this.numeroGiorni = numeroGiorni;
	}

	/**
	 * Ritorna una lista con i nomi delle città
	 * @param dataset L'intero dataset
	 * @return LinkedList con i nomi delle città
	 */
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
	
	/**
	 * Calcola la media delle temperature a partire da tutti i valori del dataset
	 * @param dataset L'intero dataset
	 * @return LinkedList con i valori delle medie ordinate secondo "ottieniNomi"
	 */
	public LinkedList<Double> ottieniMedie(LinkedList<HashSet<HourCities>> dataset) {
		
		LinkedList<String> names = this.ottieniNomi(dataset);
		LinkedList<Double> sums = new LinkedList<Double>();
		LinkedList<Double> averages = new LinkedList<Double>();
		
		boolean flag = false; //Se è false, il ciclo for each è al primo giro (alla prima ora)
		int tot_hours = 0; //Numero totale delle ore "lette" in un numero di "numeroGiorni" giorni
	
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
		
		this.truncateTo(averages, 3);
		
		return averages;
	}
	
	/**
	 * Calcola i massimi delle temperature a partire da tutti i valori del dataset
	 * @param dataset L'intero dataset
	 * @return LinkedList con i valori dei massimi ordinati secondo "ottieniNomi"
	 */
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
	
	/**
	 * Calcola i minimi delle temperature a partire da tutti i valori del dataset
	 * @param dataset L'intero dataset
	 * @return LinkedList con i valori dei minimi ordinati secondo "ottieniNomi"
	 */
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
	
	/**
	 * Calcola la varianza delle temperature a partire da tutti i valori del dataset
	 * @param dataset L'intero dataset
	 * @return LinkedList con i valori delle varianze ordinate secondo "ottieniNomi"
	 */
	public LinkedList<Double> ottieniVarianze(LinkedList<HashSet<HourCities>> dataset) {
		
		LinkedList<Double> averages = this.ottieniMedie(dataset);
		LinkedList<String> names = this.ottieniNomi(dataset);
		LinkedList<Double> variances = new LinkedList<Double>();
		
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
		
		this.truncateTo(variances, 3);
		
		return variances;
	}
	
	/**
	 * Calcola la deviazione standard delle temperature a partire da tutti i valori del dataset
	 * @param dataset L'intero dataset
	 * @return LinkedList con i valori delle deviazioni standard ordinate secondo "ottieniNomi"
	 */
	public LinkedList<Double> ottieniDevStd(LinkedList<HashSet<HourCities>> dataset) {
		LinkedList<Double> std = this.ottieniVarianze(dataset);
		for(int i = 0; i < std.size(); i++) 
			std.set(i, Math.sqrt(std.get(i)));
		
		this.truncateTo(std, 3);
		return std;
	}
	
	/**
	 * Tronca i valori della LinkedList
	 * @param unroundedNumber Lista di double da troncare
	 * @param decimalPlaces Numeri
	 */
	public void truncateTo(LinkedList<Double> unroundedNumber, int decimalPlaces){
		LinkedList<Integer> truncatedNumberInt = new LinkedList<Integer>();
		LinkedList<Double> truncatedNumber = new LinkedList<Double>();
		for(int i = unroundedNumber.size() - 1; i >= 0; i--) {
			truncatedNumberInt.add((int)(unroundedNumber.get(i) * Math.pow(10, decimalPlaces)));
			truncatedNumber.push((double)(truncatedNumberInt.get(unroundedNumber.size() - i - 1) / Math.pow(10, decimalPlaces)));
		}
		unroundedNumber.clear();
		unroundedNumber.addAll(truncatedNumber);
	}
}
