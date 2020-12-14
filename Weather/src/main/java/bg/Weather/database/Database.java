package bg.Weather.database;

import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.model.CityData;

public class Database {

	final int daysMax = 30;
	
	private LinkedList<HashSet<CityData>> dataset = new LinkedList<HashSet<CityData>>();
	
	public void aggiornaDatabase(HashSet<CityData> cities) {
		
		if(dataset.size() == 0) {
			dataset.push(cities);
			return;
		}
		
		if(dataset.size() < daysMax) {
			dataset.getFirst();
		}
	}
	
}
