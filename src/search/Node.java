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
}
