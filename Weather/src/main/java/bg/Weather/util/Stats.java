package bg.Weather.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

import bg.Weather.database.Database;
import bg.Weather.model.HourCities;

public class Stats {

	//private double average; VA MESSO A MATRICE
	private double variance;
	
	public void calculateAverage(LinkedList<HashSet<HourCities>> dataset, int days) {
		/*
		if(days > 30)
			throw new Exception();
		*/
		
		for(int i = 0; i < days; i++) {
			HashSet<HourCities> temp = new HashSet<HourCities>();
			temp = dataset.get(i);
			for(HourCities hc : temp) {
				
			}
		}
	}
}
