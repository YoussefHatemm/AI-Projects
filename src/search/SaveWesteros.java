package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.BiFunction;

import search.State.Occupant;

public class SaveWesteros extends Problem {
	static int walkersAmount = 0;

	public SaveWesteros(int walkersAlive, Occupant northOccupant, Occupant westOccupant, Occupant eastOccupant,
			Occupant southOccupant) { // initial Occupants
		this.operators = new HashSet<String>();
		this.operators.add("Up");
		this.operators.add("Down");
		this.operators.add("Right");
		this.operators.add("Left");
		this.operators.add("Stab");
		this.operators.add("Collect");

		this.initialState = new State(0, 0, walkersAlive, northOccupant, westOccupant, eastOccupant, southOccupant);
	}

	@Override
	boolean goalTest(Object state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	int pathCost(Object node) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static Occupant[][] GenGrid() {
		int m = (int) (Math.random() * 100) + 4;
		int n = (int) (Math.random() * 100) + 4;
		// int capacity = (int)(Math.random()*100); to be added somewhere else

		Occupant[][] grid = new Occupant[m][n];
		grid[0][0] = Occupant.JON;

		Occupant[] cellOptions = Occupant.values();

		int walkersAmount = 0;

		for (int i = 0; i < grid.length; i++)
			for (int j = (i == 0) ? 1 : 0; j < grid[i].length; j++) {
				grid[i][j] = cellOptions[(int) (Math.random() * 3)];
				if (grid[i][j] == Occupant.WALKER)
					walkersAmount++;
			}

		int dragonX = (int) (Math.random() * (m - 1)) + 1;
		int dragonY = (int) (Math.random() * (n - 1)) + 1;
		grid[dragonX][dragonY] = cellOptions[3];

		return grid;
	}

	// public static BiFunction <ArrayList<Node> , ArrayList<Node>, > add = (a,
	// b) -> {
	// return a + b;
	// }

	public static Triple Search(Occupant[][] grid, BiFunction strategy, boolean visualize) {
		SaveWesteros saveWesteros = new SaveWesteros(walkersAmount, grid[0][1], grid[1][0], null, null);

		Pair solution = generalSearch(saveWesteros, strategy);
		ArrayList<String> sequence = new ArrayList<String>();
		
		for(Node current = solution; current != null; current = current.parent)
			sequence.add(current.operatorApplied); //sequence is in reverse (starts from end)
		
		int pathCost = solution.pathCost;
		

	}

	public class Triple {
		ArrayList<String> sequence;
		int totalCost;
		int nodesExpanded;

		public Triple(ArrayList<String> sequence, int totalCost, int nodesExpanded) {
			this.sequence = sequence;
			this.totalCost = totalCost;
			this.nodesExpanded = nodesExpanded;
		}
	}
}
