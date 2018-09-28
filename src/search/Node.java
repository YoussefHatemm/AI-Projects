package search;

public class Node {
	State state;
	Node parent;
	String operatorApplied;
	int depth;
	int pathCost;
	
	public Node(State state, Node parent, String operatorApplied, int depth, int pathCost) {
		this.state = state;
		this.parent = parent;
		this.operatorApplied = operatorApplied;
		this.depth = depth;
		this.pathCost = pathCost;
	}
}
