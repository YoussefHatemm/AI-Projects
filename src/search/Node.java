package search;

public class Node {
	State state;
	Node parent;
	String operatorApplied;
	int depth;
	int pathCost;
	
	public Node(State state, Node parent, String operatorApplied, int depth) {
		this.state = state;
		this.parent = parent;
		this.operatorApplied = operatorApplied;
		this.depth = depth;
	}

	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}

	@Override
	public String toString() {
		String parentString = (parent != null)? ( (parent.operatorApplied != null)? parent.operatorApplied : "Initial Node") : "";
		String operator = (operatorApplied != null)? operatorApplied: "";
		return 
			"State: \n" + state + 
				"\n Parent: " + parentString + 
				", OperatorApplied: " + operator + "\n depth: " +
				 depth + ", pathCost: " + pathCost;
	}
}
