package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.BiFunction;

import search.WesterosState.Occupant;
import utils.Pair;

/*
		M * N GRID
		M is the number of rows (y-axis)
		N is the number of columns(x-axis)
	_____________________________
	|XXXXXXXXXXXXXXXXXXXXXXXXXXX|
	|XXXXXXXXXXXXXXXXXXXXXXXXXXX|	
	|XXXXXXXXXXXXXXXXXXXXXXXXXXX|	
	|XXXXXXXXXXXXXXXXXXXXXXXXXXX|
	|XXXXXXXXXXXXXXXXXXXXXXXXXXX|
	|XXXXXXXXXXXXXXXXXXXXXXXXXXX|
	|XXXXXXXXXXXXXXXXXXXXXXXXXXX|
	|___________________________|
*/

public class SaveWesteros extends Problem {
	int capacity;

	public SaveWesteros(WesterosState initialState) {
		this.operators = new ArrayList<String>();
		this.operators.add("Collect");
		this.operators.add("Stab1");
		this.operators.add("Stab2");
		this.operators.add("Stab3");
		this.operators.add("Up");
		this.operators.add("Left");
		this.operators.add("Down");
		this.operators.add("Right");

		this.initialState = initialState;
		this.capacity = (int) (Math.random() * 20) + 1;
		((WesterosState) this.initialState).ammo = this.capacity;

	}

	@Override
	boolean goalTest(State state) {
		return ((WesterosState) state).walkersAlive == 0;
	}

	@Override
	void pathCost(Node node) {
		int opCost = 0;
		switch (node.operatorApplied) {
		// TODO: implement yastaa
		case ("Stab3"):
			opCost = 1;
			break;
		case ("Collect"):
			opCost = 2;
			break;
		case ("Up"):
		case ("Down"):
		case ("Left"):
		case ("Right"):
			opCost = 3;
			break;
		case ("Stab2"):
			opCost = 4;
			break;
		case ("Stab1"):
			opCost = 16;
			break;

		}
		node.setPathCost(opCost + node.parent.pathCost);
	}

	@Override
	State stateSpace(String operator, State state) {
//		System.out.println("Trying operator...");
		WesterosState wState = (WesterosState) state;
		int newY = wState.yCoord;
		int newX = wState.xCoord;
		int newAmmo = wState.ammo;
		int newWalkers = wState.walkersAlive;
		Occupant[][] grid = WesterosGrid.deepCloneGrid(wState.wGrid);
		Occupant northOccupant = (newY < grid.length - 1) ? grid[newY + 1][newX] : null;
		Occupant southOccupant = (newY > 0) ? grid[newY - 1][newX] : null;
		Occupant eastOccupant = (newX > 0) ? grid[newY][newX - 1] : null;
		Occupant westOccupant = (newX < grid[0].length - 1) ? grid[newY][newX + 1] : null;

		ArrayList<Pair> walkersLocations = new ArrayList<Pair>();
		if (northOccupant != null && northOccupant == Occupant.WALKER)
			walkersLocations.add(new Pair(newY + 1, newX));

		if (southOccupant != null && southOccupant == Occupant.WALKER)
			walkersLocations.add(new Pair(newY - 1, newX));

		if (eastOccupant != null && eastOccupant == Occupant.WALKER)
			walkersLocations.add(new Pair(newY, newX - 1));

		if (westOccupant != null && westOccupant == Occupant.WALKER)
			walkersLocations.add(new Pair(newY, newX + 1));

		switch (operator) {
		case ("Up"):
			if (northOccupant != null && northOccupant == Occupant.FREE) {
				newY++;
			} else
				return null;
			break;
		case ("Down"):
			if (southOccupant != null && southOccupant == Occupant.FREE)
				newY--;
			else
				return null;
			break;
		case ("Right"):
			if (eastOccupant != null && eastOccupant == Occupant.FREE)
				newX--;
			else
				return null;
			break;
		case ("Left"):
			if (westOccupant != null && westOccupant == Occupant.FREE)
				newX++;
			else
				return null;
			break;
		case ("Stab1"):
			if (newAmmo > 0 && walkersLocations.size() == 1) {
				newAmmo--;
				Pair location =  walkersLocations.get(0);
				grid[(int)location.getFirst()][(int)location.getSecond()] = Occupant.FREE;
				newWalkers--;
			} else 
				return null;	
			break;
		case ("Stab2"):
			if (newAmmo > 0 && walkersLocations.size() == 2) {
				newAmmo--;
				for (Pair location: walkersLocations)
					grid[(int)location.getFirst()][(int)location.getSecond()] = Occupant.FREE;
				newWalkers-=2;
			} else 
				return null;
			break;

		case ("Stab3"):
			if (newAmmo > 0 && walkersLocations.size() == 3) {
				newAmmo--;
				for (Pair location: walkersLocations)
					grid[(int)location.getFirst()][(int)location.getSecond()] = Occupant.FREE;
				newWalkers-=3;
			} else 
				return null;	
			break;
		case ("Collect"):
			if ((northOccupant == Occupant.DRAGONSTONE || eastOccupant == Occupant.DRAGONSTONE
					|| southOccupant == Occupant.DRAGONSTONE || westOccupant == Occupant.DRAGONSTONE)
					&& wState.ammo < this.capacity) {
				newAmmo = this.capacity;
				if (northOccupant == Occupant.DRAGONSTONE)
					newY++;

				if (eastOccupant == Occupant.DRAGONSTONE)
					newX--;

				if (westOccupant == Occupant.DRAGONSTONE)
					newX++;

				if (southOccupant == Occupant.DRAGONSTONE)
					newY--;
			}

			else
				return null;

		}
//		System.out.println("da new Y and X: " + newY + " " + newX);

		if (operator == "Collect") {
			grid[newY][newX] = Occupant.JONDRAGONSTONE; // the order of this statement and the one above it is essential!!
			grid[ wState.yCoord][wState.xCoord] = Occupant.FREE;
		}
		else if (!operator.contains("Stab")){
			if (grid[wState.yCoord][wState.xCoord] == Occupant.JONDRAGONSTONE)
				grid[wState.yCoord][wState.xCoord] = Occupant.DRAGONSTONE;
			else
				grid[wState.yCoord][wState.xCoord] = Occupant.FREE;

			grid[newY][newX] = Occupant.JON;
		}

		WesterosGrid newGrid = new WesterosGrid(grid, newWalkers);
		return new WesterosState(newX, newY, newAmmo, newWalkers, newGrid);
	}

	public static SolutionTrio Search(WesterosGrid westerosGrid,
			BiFunction<ArrayList<Node>, ArrayList<Node>, ArrayList<Node>> strategy, boolean visualize) {

		WesterosState initalState = new WesterosState(0, 0, westerosGrid.walkersAmount, westerosGrid);

		SaveWesteros saveWesteros = new SaveWesteros(initalState);

		Pair solution = generalSearch(saveWesteros, strategy);

		ArrayList<String> movesSequence = null;

		Node solutionNode = null;
		int numberOfNodesExpanded = (Integer) solution.getSecond();

		if (solution.getFirst() != null) {
			movesSequence = new ArrayList<String>();
			solutionNode = (Node) solution.getFirst();
	
			for (Node current = solutionNode; current != null; current = current.parent) {
				if (current.operatorApplied != null)
					movesSequence.add(current.operatorApplied); // sequence is in reverse (starts from end)
			}
		}
			
		int pathCost = (solutionNode != null)? solutionNode.pathCost : 0;

		return new SolutionTrio(movesSequence, pathCost, numberOfNodesExpanded);
	}
	public static void main(String []args) {
		WesterosGrid westerosGrid = WesterosGrid.GenGrid();
		SolutionTrio solution = Search(westerosGrid, Strategies.ucs, false);
		System.out.println(solution.toString());
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

		@Override
		public String toString() {
			if (movesSequence == null)
				return "NO SOLUTION!";

			return "THE SOLLUTION:\n Sequence: " + movesSequence.toString() + "\n TotalCost: " + totalCost + "\n Number of Nodes expanded " + numberOfNodesExpanded;
		}
	}
}
