package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;
import bg.Weather.service.StatsService;

/**
 * Ottiene e restituisce la deviazione standard delle temperature delle città
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class StdStats extends StatsService implements Stat{

	private LinkedList<Double> devStd = new LinkedList<Double>();
	
	/**
	 * Assegna il numero di giorni preso in input all'attributo della classe madre
	 * @param numeroGiorni numero dei giorni passati in cui si vuole ottenere la statistica
	 */
	public StdStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	/**
	 * Ottieni la deviazione standard di ogni città
	 * @param dataset l'intero dataset in cui prendere i dati e calcolarne la deviazione standard
	 * @return deviazione standard di ogni città
	 */
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		this.devStd = super.calc.ottieniDevStd(dataset);
		return devStd;
	}
}
