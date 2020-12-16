package bg.Weather.service;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.CityData;
import bg.Weather.model.HourCities;

public class Calculate {
	
	private int numeroGiorni;
	private LinkedList<HashSet<HourCities>> dataset = new LinkedList<HashSet<HourCities>>();
	
	public Calculate(LinkedList<HashSet<HourCities>> dataset, int numeroGiorni) {
		this.dataset = dataset;
		this.numeroGiorni = numeroGiorni;
	}

	public LinkedList<String> ottieniNomi() {
		LinkedList<String> nomi = new LinkedList<String>();
		for(HourCities hc: this.dataset.getFirst()) {
			for(CityData c : hc.getHourCities()) {
				nomi.push(c.getNome());
			}
			break;
		}
		return nomi;
	}
	
	public LinkedList<Double> ottieniMedie() {
		int count = 0; //Numero città del box
		boolean flag = false; //Se è false, il ciclo for each è al primo giro (alla prima ora) e il numero di città (count)
		
		int tot_hours = 0; //Numero totale delle ore "lette" in un numero di "numeroGiorni" giorni
		
		LinkedList<Double> sums = new LinkedList<Double>();
		LinkedList<Double> averages = new LinkedList<Double>();
		
		for(int i = 0; i < this.numeroGiorni; i++) {
			HashSet<HourCities> temp = new HashSet<HourCities>();
			temp = this.dataset.get(i);
			for(HourCities hc : temp) {
				if(!flag) {
					for(CityData cd : hc.getHourCities()) {
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
		
		return averages;
	}
	
	public LinkedList<Double> ottieniMassimi() {
		LinkedList<Double> tempMax = new LinkedList<Double>();
		int count = 0; //Numero città del box
		boolean flag = false;
		
		for(int i = 0; i < this.numeroGiorni; i++) {
			HashSet<HourCities> temp = new HashSet<HourCities>();
			temp = dataset.get(i);
			for(HourCities hc : temp) {
				if(!flag) {
					for(CityData cd : hc.getHourCities()) {
						tempMax.push(cd.getTemperatura());
						count++;
					}
					flag = true;
				}
				else {
					int j = 0;
					for(CityData cd : hc.getHourCities()) {
						if(tempMax.get(count - j - 1) < cd.getTemperatura())
							tempMax.set(count - j - 1, cd.getTemperatura());
						
						j++;
					}
				}
			}
		}
		return tempMax;
	}
	
	public LinkedList<Double> ottieniMinimi() {
		LinkedList<Double> tempMin = new LinkedList<Double>();
		int count = 0; //Numero città del box
		boolean flag = false; //Se è false, il ciclo for each è al primo giro (alla prima ora) e mi salva i nomi delle città in names e il numero di città (count)
		
		for(int i = 0; i < this.numeroGiorni; i++) {
			HashSet<HourCities> temp = new HashSet<HourCities>();
			temp = dataset.get(i);
			for(HourCities hc : temp) {
				if(!flag) {
					for(CityData cd : hc.getHourCities()) {
						tempMin.push(cd.getTemperatura());
						count++;
					}
					flag = true;
				}
				else {
					int j = 0;
					for(CityData cd : hc.getHourCities()) {
						if(tempMin.get(count - j - 1) < cd.getTemperatura())
							tempMin.set(count - j - 1, cd.getTemperatura());
						
						j++;
					}
				}
			}
		}
		return tempMin;
	}
	
	public LinkedList<Double> ottieniVarianze() {
		LinkedList<Double> averages = new LinkedList<Double>();
		averages = this.ottieniMedie();
		
		LinkedList<Double> variances = new LinkedList<Double>();
		
		int count = 0; //Numero città del box
		boolean flag = false; //Se è false, il ciclo for each è al primo giro (alla prima ora) e il numero di città (count)
		
		int tot_hours = 0; //Numero totale delle ore "lette" in un numero di "numeroGiorni" giorni
		
		for(int i = 0; i < this.numeroGiorni; i++) {
			HashSet<HourCities> temp = new HashSet<HourCities>();
			temp = this.dataset.get(i);
			for(HourCities hc : temp) {
				if(!flag) {
					int j = 0;
					for(CityData cd : hc.getHourCities()) {
						double scarto;
						scarto = cd.getTemperatura() - averages.get(count - j - 1);
						scarto *= scarto;
						variances.push(scarto);
						count++;
					}
					tot_hours++;
					flag = true;
				}
				else {
					int j = 0;
					for(CityData cd : hc.getHourCities()) {
						double scarto;
						scarto = cd.getTemperatura() - averages.get(count - j - 1);
						scarto *= scarto;
						variances.set(count - j - 1, variances.get(count - j - 1) + scarto);
						j++;
					}
					tot_hours++;
				}
			}
		}
		
		for(Double d : variances)
			d /= tot_hours;
		
		return variances;
	}
}
