package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;

/**
 * Ottiene e restituisce la temperatura massima di ogni città
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class TempmaxStats extends WeatherStats implements Stat{

	private LinkedList<Double> tempMax = new LinkedList<Double>();
	
	/**
	 * Assegna il numero di giorni preso in input all'attributo della classe madre
	 * @param numeroGiorni numero dei giorni passati in cui si vuole ottenere la statistica
	 */
	public TempmaxStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	/**
	 * Ottieni la temperatura massima di ogni città
	 * @param dataset l'intero dataset in cui prendere i dati e ricavarne la temperatura massima
	 * @return temperatura massima di ogni città
	 */
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		this.tempMax = super.calc.ottieniMassimi(dataset);
		return tempMax;
	}
}
