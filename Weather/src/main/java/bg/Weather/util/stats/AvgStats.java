package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;
import bg.Weather.service.StatsService;

/**
 * Ottiene e restituisce la media delle temperature delle città
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class AvgStats extends StatsService implements Stat{

	private LinkedList<Double> averages = new LinkedList<Double>();
	
	/**
	 * Assegna il numero di giorni preso in input all'attributo della classe madre
	 * @param numeroGiorni numero dei giorni passati in cui si vuole ottenere la statistica
	 */
	public AvgStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	/**
	 * Ottieni la media di ogni città
	 * @param dataset l'intero dataset in cui prendere i dati e calcolarne la media
	 * @return media di ogni città
	 */
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		this.averages = super.calc.ottieniMedie(dataset);
		return averages;
	}
}
