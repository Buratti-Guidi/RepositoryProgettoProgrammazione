package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.exception.InternalServerException;
import bg.Weather.model.HourCities;

/**
 * Interfaccia di una statistica generica
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public interface Stat {

	/**
	 * Ottieni una LinkedList contenente i valori della statistica di ogni città
	 * @param dataset l'intero dataset
	 * @return valori della statistica di ogni città
	 */
	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset);
	
	/**
	 * Ottieni una LinkedList contenente il nome di ogni città
	 * @param dataset l'intero dataset
	 * @return nome di ogni città
	 */
	public LinkedList<String> getNames(LinkedList<HashSet<HourCities>> dataset); //restituise una LinkedList contenente i nomi di ogni città
	
	/**
	 * Ordina i valori delle statistiche in ordine crescente o decrescente e ordina di conseguenza l'ordine dei nomi delle città
	 * per lasciare associati nome e statistica corrispondente
	 * @param cityNames nome di ogni città ordinata nell'ordine della statistica assegnata al nome
	 * @param values statistica di ogni città ordinata nell'ordine del nome corrispondente
	 * @param ascending "true" per ordinamento crescente, "false" per quello decrescente
	 * @throws InternalServerException
	 */
	public void sortStats(LinkedList<String> cityNames, LinkedList<Double> values, boolean ascending) throws InternalServerException;
}
