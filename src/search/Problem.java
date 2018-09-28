package search;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Problem {
	HashSet<String> operators;
	Object initialState;
	ArrayList<Object> stateSpace;
	abstract boolean goalTest(Object state);
	abstract int pathCost(Object node);
}
