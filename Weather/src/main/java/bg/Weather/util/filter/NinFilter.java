package bg.Weather.util.filter;

import bg.Weather.exception.FilterErrorException;

/**
 * Filtro che controlla i nomi delle città presenti nel dataset con il nome preso in input
 * @author Christopher Buratti
 * @author Luca Guidi
 */

public class NinFilter extends WeatherFilter implements Filter{

	private String vrfValue;
	
	/**
	 * Assegna il valore da verificare preso in input all'attributo della classe
	 * @param vrfValue valore da verificare preso in input
	 */
	public NinFilter(String vrfValue) {
		this.vrfValue = vrfValue.toUpperCase();
	}
	
	/**
	 * Se il nome è presente nella lista dei nomi da controllare ritorna "false", "true" altrimenti
	 */
	public boolean response() throws FilterErrorException {
		try {
			for(Object o : super.getValue()) {
				if(this.vrfValue.equals(((String)o).toUpperCase()))
					return false;
			}
			return true;
		} catch(ClassCastException e) {
			throw new FilterErrorException("The filter 'nin' accepts only strings");
		}
	}
}
