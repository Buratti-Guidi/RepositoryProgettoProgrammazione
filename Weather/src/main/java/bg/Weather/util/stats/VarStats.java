package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;

/**
 * Ottiene e restituisce la varianza delle temperature delle città
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class VarStats extends WeatherStats implements Stat{

	private LinkedList<Double> variances = new LinkedList<Double>();
	
	/**
	 * Assegna il numero di giorni preso in input all'attributo della classe madre
	 * @param numeroGiorni numero dei giorni passati in cui si vuole ottenere la statistica
	 */
	public VarStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	/**
	 * Ottieni la varianza di ogni città
	 * @param dataset l'intero dataset in cui prendere i dati e calcolarne la varianza
	 * @return varianza di ogni città
	 */
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		this.variances = super.calc.ottieniVarianze(dataset);
		return variances;
	}
}
