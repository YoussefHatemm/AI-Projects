package search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Function;

import search.WesterosState.Occupant;
import utils.Pair;

public class SaveWesteros extends Problem {
	int capacity;
	public SaveWesteros(WesterosState initState, int capacity) {
		this.operators = new HashSet<String>();
		this.operators.add("Up");
		this.operators.add("Down");
		this.operators.add("Right");
		this.operators.add("Left");
		this.operators.add("Stab");
		this.operators.add("Collect");

		this.initialState = initState;

		this.capacity = capacity;
	}

	@Override
	boolean goalTest(State state) {
		return ((WesterosState)state).walkersAlive == 0;
	}

	@Override
	int pathCost(Node node) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static SolutionTrio Search(WesterosGrid westerosGrid, String strategy, boolean visualize) {
		Occupant[][]grid = westerosGrid.grid;
		WesterosState initalState = new WesterosState(
									0, 
									0,
									westerosGrid.walkersAmount,
									grid[0][1], 
									grid[1][0], 
									null, 
									null
									);

		SaveWesteros saveWesteros = new SaveWesteros(initalState, westerosGrid.capacity);

		Pair solution = generalSearch(saveWesteros, null); // till we create the actual

		ArrayList<String> movesSequence = new ArrayList<String>();
		//ArrayDeque to be used as a stack, to allow root to be placed on top
		Deque<Node> nodesExpanded = new ArrayDeque<Node>();  

		Node solutionNode = (Node) solution.getFirst();
		int numberOfNodesExpanded = (Integer) solution.getSecond();

		for(Node current = solutionNode; current != null; current = current.parent) {
			movesSequence.add(current.operatorApplied); //sequence is in reverse (starts from end)
			nodesExpanded.push(current);
		}
		
		int pathCost = solutionNode.pathCost;

		return new SolutionTrio(movesSequence, pathCost, numberOfNodesExpanded);
	}

	public static class SolutionTrio {
		ArrayList<String> movesSequence;
		int totalCost;
		int numberOfNodesExpanded;

		public SolutionTrio(ArrayList<String> movesSequence, int totalCost, int numberOfNodesExpanded) {
			this.movesSequence = movesSequence;
			this.totalCost = totalCost;
			this.numberOfNodesExpanded = numberOfNodesExpanded;
		}
	}
}
