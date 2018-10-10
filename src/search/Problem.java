package search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Function;

import utils.Pair;

public abstract class Problem {
	HashSet<String> operators;
	State initialState;
	abstract State stateSpace(String operator, State state);
	abstract boolean goalTest(State state);
	abstract void pathCost(Node node);
	
	public static Pair generalSearch(Problem problem, BiFunction< ArrayList<Node>, ArrayList<Node>, ArrayList<Node> > strategyQnFn) {
		ArrayList<Node> queue = new ArrayList<Node>();
		State curState = problem.initialState;
		Node root = new Node(curState, null, null,0);
		queue.add(root);
		int nodesExpanded = 0;
		while(!queue.isEmpty()) {
			Node curNode = queue.get(0);
			curState = curNode.state;
			if (problem.goalTest(curState))
				return new Pair(curNode, nodesExpanded);

			queue = strategyQnFn.apply(queue, problem.expand(curNode));	
		}
	
		return new Pair(null, nodesExpanded); // no solution
	}
	
	public ArrayList<Node> expand(Node node) {
		ArrayList<Node> output = new ArrayList<>();
		for (String operator: this.operators) {
			State newState = this.stateSpace(operator, node.state);
			Node newNode = new Node(newState, node, operator, node.depth + 1);
			this.pathCost(newNode);
			output.add(newNode);
		}
		return output;
	}
	
}
