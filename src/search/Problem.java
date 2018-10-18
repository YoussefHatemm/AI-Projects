package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.function.BiFunction;

import utils.Pair;

public abstract class Problem {
	ArrayList<String> operators;
	State initialState;
	abstract State stateSpace(String operator, State state);
	abstract boolean goalTest(State state);
	abstract void pathCost(Node node);
	
	public static Pair generalSearch(Problem problem, BiFunction< ArrayList<Node>, ArrayList<Node>, ArrayList<Node> > strategyQnFn, boolean visualize) {
		int aux = -1; // auxilarry variable to be used by any strategy such as ID
		ArrayList<Node> queue = new ArrayList<Node>();
		State curState = problem.initialState;
		Node root = new Node(curState, null, null,0);
		queue.add(root);
		int nodesExpanded = 0;
		if (strategyQnFn == Strategies.ID)
			aux = 0;

		Hashtable<String,State> repeatedStates = new Hashtable<>();
		while(!queue.isEmpty()) {
			Node curNode = queue.remove(0);

			if (strategyQnFn == Strategies.ID) {
				while (curNode.depth > aux && !queue.isEmpty())
					curNode = queue.remove(0);

				if (queue.isEmpty()) {
					aux++;
					repeatedStates.clear();
					queue.add(root);
				}
			}

			curState = curNode.state;
			if (repeatedStates.contains(curState))
				continue;

			repeatedStates.put(((WesterosState)curState).simpleString(), curState);

			if (visualize) {
				System.out.println("Walkers Alive: " + ((WesterosState)curState).walkersAlive);
				System.out.println("Ammo: " + ((WesterosState)curState).ammo);
				System.out.println("Depth: "+curNode.depth);
				System.out.println(((WesterosState)curNode.state).wGrid);
			}

//			System.out.println("The currNode is: \n" + curNode.toString());
			if (problem.goalTest(curState))
				return new Pair(curNode, nodesExpanded);

			queue = strategyQnFn.apply(queue, problem.expand(curNode));
			nodesExpanded++;
		}
	
		return new Pair(null, nodesExpanded); // no solution
	}
	
	public ArrayList<Node> expand(Node node) {
		ArrayList<Node> output = new ArrayList<>();
		for (String operator: this.operators) {
//			System.out.println("The curr operator: " + operator);
			State newState = this.stateSpace(operator, node.state);
			if (newState == null)
				continue;
//			System.out.println("operator valid");
			Node newNode = new Node(newState, node, operator, node.depth + 1);
			this.pathCost(newNode);
			output.add(newNode);
		}
		return output;
	}
	
}
