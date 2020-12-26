package bg.Weather.util.stats;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.exception.InternalServerException;
import bg.Weather.model.HourCities;

public interface Stat {

	public LinkedList<Double> getStats(LinkedList<HashSet<HourCities>> dataset); //restituise una LinkedList contenente i valori della statistica di ogni città
	
	public LinkedList<String> getNames(LinkedList<HashSet<HourCities>> dataset); //restituise una LinkedList contenente i nomi di ogni città
	
	public void sortStats(LinkedList<String> cityNames, LinkedList<Double> values, boolean ascending) throws InternalServerException;
}
