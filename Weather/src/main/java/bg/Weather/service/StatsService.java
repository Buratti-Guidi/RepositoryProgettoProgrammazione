package bg.Weather.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;
import bg.Weather.model.HourCities;
import bg.Weather.util.stats.Stat;

/**
 * Classe che si occupa di restituire i dati riguardanti le statistiche richieste dall'utente
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class StatsService {
	
	private LinkedList<String> names = new LinkedList<String>();
	
	protected Calculate calc; //Calcolatore utilizzato per eseguire i calcoli per le varie statistiche
	
	public StatsService() {}
	
	public StatsService(int numeroGiorni) {
		calc = new Calculate(numeroGiorni);
	}
	
	public Stat getStat(String statType, int numDays) throws UserErrorException, InternalServerException{
		try {
			String className = "bg.Weather.util.stats." + statType.substring(0,1).toUpperCase() + statType.substring(1, statType.length()).toLowerCase() + "Stats";
			Stat s;
			Class<?> cls = Class.forName(className);
			Constructor<?> ct = cls.getDeclaredConstructor(int.class);
			s = (Stat)ct.newInstance(numDays);
			return s;
			
		} catch (ClassNotFoundException e) {
			throw new UserErrorException("This stat doesn't exist");
		}
		catch(InvocationTargetException e) {
			throw new InternalServerException("Error in getStats");
		}
		catch(LinkageError | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
			throw new InternalServerException("GetStat in StatService error, try later...");
		}
	}

	public LinkedList<String> getNames(LinkedList<HashSet<HourCities>> dataset) {
		this.names = this.calc.ottieniNomi(dataset);
		return names;
	}
	
	public void sortStats(LinkedList<String> cityNames, LinkedList<Double> values, boolean ascending) throws InternalServerException{
		LinkedList<String> n_final = new LinkedList<String>();
		LinkedList<Double> v_final = new LinkedList<Double>();
		
		try {
			int cont = 0;
			do {
				int indice = 0;
				Double min = Double.MIN_VALUE;
				for(int i = 0; i < values.size(); i++) {
					if(values.get(i) > min) {
						indice = i;
						min = values.get(i);
					}
				}
				v_final.push(values.get(indice));
				n_final.push(cityNames.get(indice));
				values.set(indice, Double.MIN_VALUE);
				cont++;
			} while(cont < values.size());
			if(ascending) {
				for(int i = 0; i < values.size(); i++) {
					values.set(i, v_final.get(i));
					cityNames.set(i, n_final.get(i));
				}
			}
			else {
				for(int i = values.size() - 1; i >= 0; i--) {
					values.set(values.size() - i - 1, v_final.get(i));
					cityNames.set(values.size() - i - 1, n_final.get(i));
				}
			}
		}catch(Exception e){
			throw new InternalServerException("Errore su SortStats");
		}
	}
}
