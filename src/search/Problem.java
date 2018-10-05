package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.BiFunction;

public abstract class Problem {
	HashSet<String> operators;
	Object initialState;
	ArrayList<Object> stateSpace;
	abstract boolean goalTest(Object state);
	abstract int pathCost(Object node);
	
	public static Pair generalSearch(Problem problem, BiFunction strategy) {
		return new Pair(0,0);
	}
	
	public static class Pair {
		Object a;
		Object b;
		
		public Pair(Object a, Object b) {
			this.a = a;
			this.b = b;
		}
	}
}
