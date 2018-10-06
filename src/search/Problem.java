package search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Function;

import utils.Pair;

public abstract class Problem {
	HashSet<String> operators;
	State initialState;
	ArrayList<State> stateSpace;
	abstract boolean goalTest(State state);
	abstract int pathCost(Node node);
	
	public static Pair generalSearch(Problem problem, BiFunction< Deque<Node>, Node, Deque<Node> > strategyQnFn) {
		//ArrayDeque to be used as a queue
		Deque<Node> nodes = new ArrayDeque<Node>();
		State curState = problem.initialState;
		Node root = new Node(curState, null, null,0,0);
		nodes.addFirst(root);
		int nodesExpanded = 0;
		while(nodes.size() != 0) {
			Node curNode = nodes.removeFirst();
			curState = curNode.state;
			if (problem.goalTest(curState))
				return new Pair(curNode, nodesExpanded);

			nodes = strategyQnFn.apply(nodes, curNode);	
		}
		return new Pair(null, nodesExpanded); // no solution
	}	
	
}
