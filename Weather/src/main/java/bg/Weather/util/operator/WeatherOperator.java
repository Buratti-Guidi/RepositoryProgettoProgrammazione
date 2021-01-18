package bg.Weather.util.operator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import bg.Weather.exception.InternalServerException;
import bg.Weather.exception.UserErrorException;

/**
 * Classe madre dell'operatore "and" e "or"
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class WeatherOperator {

	protected Vector<Vector<Boolean>> conditions; //Contiene le informazioni per ogni città dei filtri rispettati (true) e non (false)
	
	/**
	 * Inizializza l'attributo di tipo Vector
	 */
	public WeatherOperator() {
		conditions = new Vector<Vector<Boolean>>();
	}
	
	/**
	 * Aggiunge la condizione alla città index-esima
	 * @param condition "true" se la condizione rispetta il filtro, "false" altrimenti
	 * @param index indice della città all'interno del Vector conditions
	 */
	public void addCondition(Boolean condition, int index) {
		if(conditions.size() == index)
			conditions.add(index, new Vector<Boolean>());
		conditions.get(index).add(condition);
	}
	
	/**
	 * Ottieni un oggetto della classe indicata dal nome
	 * @param opName nome dell'operatore
	 * @return oggetto della classe richiesta
	 * @throws UserErrorException eccezione per gestire una richiesta errata da parte dell'utente
	 * @throws InternalServerException
	 */
	public Operator getOperator(String opName) throws UserErrorException,InternalServerException{
		String className = "bg.Weather.util.operator." + opName.substring(0,1).toUpperCase() + opName.substring(1, opName.length()).toLowerCase() + "Operator";
		Operator o;
		try {
			Class<?> cls = Class.forName(className);
			Constructor<?> ct = cls.getDeclaredConstructor();
			o = (Operator)ct.newInstance();
			
		} catch (ClassNotFoundException e) {
			throw new UserErrorException("Operator not found");
		}
		catch(InvocationTargetException ex) {
			throw new InternalServerException("Error in getOperator");
		}
		catch(LinkageError | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | UserErrorException exe) {
			throw new InternalServerException("General error, try later...");
		}
		return o;
	}
}
