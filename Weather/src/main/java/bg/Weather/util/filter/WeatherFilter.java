package bg.Weather.util.filter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;

/**
 * Classe madre di un filtro generico
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class WeatherFilter {

	protected String filter; //Nome del filtro
	protected static Vector<Object> value = new Vector<Object>(); //Lista dei parametri del filtro da applicare
	
	/**
	 * Costruttore vuoto
	 */
	public WeatherFilter() {
	}

	/**
	 * Assegna agli attributi della classe i valori ricevuti in input
	 * @param filter Nome del filtro
	 * @param values Lista dei parametri del filtro da applicare
	 */
	public WeatherFilter(String filter, Vector<Object> values) {
		this.filter = filter;
		value.clear();
		for(int i = 0; i < values.size(); i++)
			value.add(values.get(i));
	}

	/**
	 * Controlla se il valore di tipo "double" preso in input rispetta i parametri del filtro richiesto
	 * @param vfrValue valore da controllare preso in input
	 * @return "true" se il valore rispetta i parametri del filtro richiesto, "false" altrimenti
	 */
	public boolean getResponse(double vfrValue) throws UserErrorException, InternalServerException{
		Filter f;
		
		try {
			String className = "bg.Weather.util.filter." + this.getFilter().substring(0,1).toUpperCase() + this.getFilter().substring(1, this.getFilter().length()).toLowerCase() + "Filter";
			Class<?> cls = Class.forName(className);
			Constructor<?> ct = cls.getDeclaredConstructor(double.class);
			f = (Filter)ct.newInstance(vfrValue);
			
			return f.response();
		} catch(ClassNotFoundException e) {
			throw new UserErrorException("This filter doesn't exist"); 
		}
		catch(InvocationTargetException e) {
			throw new InternalServerException("Error in getStats");
		}
		catch(LinkageError | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
			throw new InternalServerException("General error, try later...");
		}
	}
	
	/**
	 * Controlla se il valore di tipo "String" preso in input rispetta i parametri del filtro richiesto
	 * @param vfrValue valore da controllare preso in input
	 * @return "true" se il valore rispetta i parametri del filtro richiesto, "false" altrimenti
	 */
	public boolean getResponse(String vfrValue){
		Filter f;
		
		try {
			String className = "bg.Weather.util.filter." + this.getFilter().substring(0,1).toUpperCase() + this.getFilter().substring(1, this.getFilter().length()).toLowerCase() + "Filter";
			Class<?> cls = Class.forName(className);
			Constructor<?> ct = cls.getDeclaredConstructor(String.class);
			f = (Filter)ct.newInstance(vfrValue);
			
			return f.response();
		} catch(ClassNotFoundException e) {
			throw new UserErrorException("This filter doesn't exist");
		}
		catch(InvocationTargetException e) {
			throw new InternalServerException("Error in getStats");
		}
		catch(LinkageError | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
			throw new InternalServerException("General error, try later...");
		}
	}

	/**
	 * Ottieni il nome del filtro
	 * @return nome del filtro
	 */
	public String getFilter() {
		return this.filter;
	}
	
	/**
	 * Ottieni la lista dei parametri del filtro da applicare
	 * @return lista dei parametri del filtro da applicare
	 */
	public Vector<Object> getValue() {
		return value;
	}
}
