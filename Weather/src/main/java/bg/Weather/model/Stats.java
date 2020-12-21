package bg.Weather.model;

import java.util.LinkedHashMap;

public class Stats {

	private String name; //nome della città
	
	private double avgTemp; //media delle temperature della città
	private double tempMax; //temperatura massima della città
	private double tempMin; //temperatura minima della città
	private double var; //varianza delle temperature della città
	
	public double getAvgTemp() {
		return avgTemp;
	}
	public void setAvgTemp(double avgTemp) {
		this.avgTemp = avgTemp;
	}
	
	public double getTempMax() {
		return tempMax;
	}
	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}
	
	public double getTempMin() {
		return tempMin;
	}
	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}
	
	public double getVar() {
		return var;
	}
	public void setVar(double var) {
		this.var = var;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public LinkedHashMap<String, Object> getAllHashMap() {
		LinkedHashMap<String, Object> all = new LinkedHashMap<String, Object>();
		all.put("name", this.getName());
		all.put("avg", this.getAvgTemp());
		all.put("var", this.getVar());
		all.put("temp_max", this.getTempMax());
		all.put("temp_min", this.getTempMin());
		return all;
	}
}
