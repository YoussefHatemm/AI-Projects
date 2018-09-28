package search;

import java.util.ArrayList;
import java.util.HashSet;

public class SaveWesteros extends Problem {

	
	public SaveWesteros() {
		this.operators = new HashSet<String>();
		this.operators.add("Up");
		this.operators.add("Down");
		this.operators.add("Right");
		this.operators.add("Left");
		this.operators.add("Stab");
		this.operators.add("Collect");
	}
	
	@Override
	boolean goalTest(Object state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	int pathCost(Object node) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
