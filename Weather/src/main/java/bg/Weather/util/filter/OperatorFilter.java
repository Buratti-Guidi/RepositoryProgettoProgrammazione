package bg.Weather.util.filter;

import java.util.Vector;

public class OperatorFilter {

	protected static Vector<Vector<Boolean>> conditions = new Vector<Vector<Boolean>>();
	
	public OperatorFilter() {}
	
	public void addCondition(Boolean condition, int index) {
		if(conditions.size() == index)
			conditions.add(index, new Vector<Boolean>());
		conditions.get(index).add(condition);
	}
	
	public void clearVector() {
		conditions.clear();
	}
}
