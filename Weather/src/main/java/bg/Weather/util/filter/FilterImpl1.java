package bg.Weather.util.filter;

import java.util.LinkedList;

public class FilterImpl1 implements Filter {
	
	private LinkedList<String> params;
	
	public FilterImpl1() {
		params = new LinkedList<String>();
		params.push("avg"); //posto 3
		params.push("var"); //posto 2
		params.push("temp_max"); //posto 1
		params.push("temp_min"); //posto 0
	}
	
	public void verifyParam(String param, int days) {
		if(days>0 && days<=30) {
			int i;
			for(i=0; i<4; i++) {
				String temp = params.get(i);
				if(temp.equals(param)) {
					if(i == 0) {
						
					}
					if(i == 1) {
						
					}
					if(i == 2) {
						
					}
					if(i == 3) {
						AvgFilter avg = new AvgFilter();
					}
				}
			}
			//se i == 4
			//throw new ParamNonEsistenteException
		}
		//throw new TroppiGiorniException();
	}
	
}
