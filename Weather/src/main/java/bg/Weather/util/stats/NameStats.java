package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.HourCities;

/**
 * Classe apposita per riconoscere i filtri applicati ai nomi
 * @author Christopher Buratti
 * @author Luca Guidi
 */


public class NameStats extends WeatherStats implements Stat{

	/**
	* Assegna il numero di giorni preso in input all'attributo della classe madre
	* @param numeroGiorni numero dei giorni passati in cui si vuole ottenere la statistica
	*/
	
	public NameStats(int numeroGiorni) {
		super(numeroGiorni);
	}
	
	/**
	* Metodo obbligato dall'implementazione dell'interfaccia Stat
	* @param dataset l'intero dataset
	* @return null
	*/
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset) {
		return null;
	}
}
