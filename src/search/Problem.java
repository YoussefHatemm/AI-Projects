package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.function.BiFunction;

import utils.Pair;

public abstract class Problem {
	ArrayList<String> operators;
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
			Node curNode = queue.remove(0);
			curState = curNode.state;
			System.out.println(((WesterosState) curState).walkersAlive);
			System.out.println(((WesterosState)curNode.state).wGrid);
			System.out.println("depth: "+curNode.depth);
			if (curNode == root) {
				System.out.println(curNode);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
