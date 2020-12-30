package bg.Weather.util.filter;

import java.util.Vector;

public class OperatorFilter {

	protected Vector<Vector<Boolean>> conditions = new Vector<Vector<Boolean>>();
	
	public OperatorFilter() {}
	
	/*
	public OperatorFilter(Vector<Boolean> cond) {
		conditions.clear();
		conditions = cond;
	}
	
	public void setConditions(Vector<Boolean> cond) {
		conditions.clear();
		conditions = cond;
	}
	*/
	
	public void addCondition(Boolean condition, int index) {
		this.conditions.get(index).add(condition);
	}
	
	public void clearVector() {
		this.conditions.clear();
	}
}
