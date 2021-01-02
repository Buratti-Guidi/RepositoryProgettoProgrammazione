package bg.Weather.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;
import bg.Weather.model.HourCities;
import bg.Weather.util.operator.Operator;
import bg.Weather.util.operator.WeatherOperator;
import bg.Weather.util.filter.WeatherFilter;
import bg.Weather.util.stats.Stat;

public class FilterService {

	private int days;
	private WeatherFilter wf;
	private JSONObject jof;
	private LinkedList<String> tot_names;
	private WeatherOperator of;
	
	public FilterService(JSONObject jof) {
		this.jof = jof;
		tot_names = new LinkedList<String>();
		of = new WeatherOperator();
	}
	
	public Vector<String> filterParser(LinkedList<HashSet<HourCities>> dataset) throws InternalServerException,UserErrorException{
		
		Vector<String> final_names = new Vector<String>();
		Set<String> keys = this.jof.keySet();
			
		if(!this.jof.containsKey("days"))
			throw new UserErrorException("days parameter is missing");
			
		days = (Integer)this.jof.get("days");
		keys.remove("days");
			
		for(String key : keys) { //finto for che scorre per ottenere la chiave "esterna" oltre a "days"
			
			Object value = this.jof.get(key);
			LinkedHashMap<String, Object> joValue = (LinkedHashMap<String, Object>)value;
			
			if(joValue.size() == 1) {
				Vector<Double> filterValue = new Vector<Double>();
				StringBuffer filterName = new StringBuffer();
				LinkedList<Double> stats;
				
				Stat s = this.statParser(joValue, key, filterValue, filterName);
				tot_names = s.getNames(dataset);
				stats = s.getStats(dataset);
				
				wf = new WeatherFilter(filterName.toString(), filterValue);
				int i = 0;
				for(String name : s.getNames(dataset)) {
					if(wf.getResponse(stats.get(i)))
						final_names.add(name);
				i++;
				}
				return final_names;
			}
			else {
				Operator o = of.getOperator(key);
				Set<String> ks = joValue.keySet();    //nomi delle statistiche annidate ad un operator
				
				this.opResult(o, ks, joValue, dataset);
					
				int j = 0;
				for(String name : tot_names) {
					if(o.getResponse(j))
						final_names.add(name);
					j++;
				}
					
				return final_names;
			}
		}
		throw new InternalServerException("Something has gone bad");
	}

	/**
	 *  Riempe le condizioni dell operator o
	 * @param o : Operator piu a monte
	 * @param keys : nomi parametri dentro un operator
	 * @param joValue : Tutto il contenuto (stat + filtri) 
	 * @param dataset : database
	 */
	public void opResult(Operator o, Set<String> keys, LinkedHashMap<String, Object> joValue, LinkedList<HashSet<HourCities>> dataset) {

		for (String stkey : keys) {

			Vector<Double> filterValue = new Vector<Double>();
			StringBuffer filterName = new StringBuffer();
			LinkedHashMap<String, Object> joValueOne = new LinkedHashMap<String, Object>();

			joValueOne = (LinkedHashMap<String, Object>) joValue.get(stkey);

			try { // prima controllo se la chiave e' una stat
				Stat s = this.statParser(joValueOne, stkey, filterValue, filterName);
				tot_names = s.getNames(dataset);

				LinkedList<Double> stats = s.getStats(dataset);
				wf = new WeatherFilter(filterName.toString(), filterValue);
				int i = 0;
				// tot_names = s.getNames(dataset);
				for (String name : s.getNames(dataset)) {
					if (wf.getResponse(stats.get(i))) {
						o.addCondition(true, i);
					} else {
						o.addCondition(false, i);
					}
					i++;
				}
			} catch (UserErrorException e) {// da mettere InternalServerException(?)

				try { // se non e' una stat controllo se e' un operatore
					Set<String> keysAnnidate = joValueOne.keySet();
					Operator oAnnidato;

					for (String keyAnn : keysAnnidate) { // finto for che controlla se la chiave e' un operatore

						oAnnidato = of.getOperator(stkey); // deve essere stkey

						this.opResult(oAnnidato, joValueOne.keySet(), joValueOne, dataset);// joValueAnnidate ->
																							// joValueOne
						int j = 0;
						for (String name : tot_names) {
							if (oAnnidato.getResponse(j))
								o.addCondition(true, j);
							else
								o.addCondition(false, j);
							j++;
						}

					}

				} catch (UserErrorException ex) {
					throw ex; // comando non ricounosciuto, non e' una stat ne un operatore
				}

			}

		} // fine for delle chiavi
	}
	
	
	//valore e filtername sono inizialmente vuoti, quindi da riempire
	public Stat statParser(LinkedHashMap<String, Object> joValue, String stat, Vector<Double> valore, StringBuffer filterName) 
	throws UserErrorException,InternalServerException { 
		
		StatsService statService = new StatsService();
		Stat s = statService.getStat(stat,this.days);
		Set<String> filterKeys = joValue.keySet();
		
		for(String filterN : filterKeys) {			//finto for che scorre solo per il nome del filtro
			filterName.insert(0, filterN);
			Object filterValue = joValue.get(filterN);
			if(filterValue instanceof Number) {
				if(filterValue instanceof Integer)
					valore.add(((Integer)filterValue).doubleValue());
				
				if(filterValue instanceof Double)
					valore.add((Double)filterValue);
			}
			if(filterValue instanceof Collection) {
				for(Object elem : (Collection)filterValue) {
					if(elem instanceof Integer)
						valore.add(((Integer)elem).doubleValue());
					
					if(elem instanceof Double)
						valore.add((Double)elem);
				}
			}
		break;
		}
		return s;
	}	
	
	public JSONArray response(LinkedList<HashSet<HourCities>> dataset) throws InternalServerException,UserErrorException{
		Vector<String> final_names = this.filterParser(dataset);
		JSONArray result = new JSONArray();
		Stat s;
		StatsService ss = new StatsService(this.days);
		
		s = ss.getStat("avg", this.days);
		LinkedList<Double> avg = new LinkedList<Double>();
		avg = s.getStats(dataset);
		
		s = ss.getStat("var", this.days);
		LinkedList<Double> var = new LinkedList<Double>();
		var = s.getStats(dataset);
		
		s = ss.getStat("std", this.days);
		LinkedList<Double> std = new LinkedList<Double>();
		std = s.getStats(dataset);
		
		s = ss.getStat("tempmax", this.days);
		LinkedList<Double> tempMax = new LinkedList<Double>();
		tempMax = s.getStats(dataset);
		
		s = ss.getStat("tempmin", this.days);
		LinkedList<Double> tempMin = new LinkedList<Double>();
		tempMin = s.getStats(dataset);
		
		LinkedList<String> names = new LinkedList<String>();
		names = s.getNames(dataset);
		
		
		for(String str : final_names) {
			for(int i = 0; i < names.size(); i++) {
				if(str.equals(names.get(i))) {
					LinkedHashMap<String, Object> jo = new LinkedHashMap<String, Object>();
					jo.put("name", str);
					jo.put("avg", avg.get(i));
					jo.put("var", var.get(i));
					jo.put("std", std.get(i));
					jo.put("tempmax", tempMax.get(i));
					jo.put("tempmin", tempMin.get(i));
					
					result.add(jo);
				}
			}
		}
		return result;
	}
}
