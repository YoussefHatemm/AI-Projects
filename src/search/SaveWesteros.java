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
		this.operators.add("Up");
		this.operators.add("Down");
		this.operators.add("Right");
		this.operators.add("Left");
		this.operators.add("Stab1");
		this.operators.add("Stab2");
		this.operators.add("Stab3");
		this.operators.add("Stab4");
		this.operators.add("Collect");

		this.initialState = initialState;
		this.capacity = (int) (Math.random() * 100) + 1;
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
		case (""):
			break; // opCost = btngan
		}
		node.setPathCost(opCost + node.parent.pathCost);
	}

	@Override
	State stateSpace(String operator, State state) {
		WesterosState wState = (WesterosState) state;
		int newY = wState.yCoord;
		int newX = wState.xCoord;
		int newAmmo = wState.ammo;
		int newWalkers = wState.walkersAlive;
		Occupant[][] grid = wState.wGrid.grid.clone();
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
			walkersLocations.add(new Pair(newY, newX - 1));

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
				newX--;
			else
				return null;
			break;
		case ("Stab1"):
			if (newAmmo > 0 && walkersLocations.size() == 1) {
				newAmmo--;
				Pair location =  walkersLocations.get(0);
				grid[(int)location.getFirst()][(int)location.getSecond()] = Occupant.FREE;
			} else 
				return null;	
			break;
		case ("Stab2"):
			if (newAmmo > 0 && walkersLocations.size() == 2) {
				newAmmo--;
				for (Pair location: walkersLocations)
					grid[(int)location.getFirst()][(int)location.getSecond()] = Occupant.FREE;
			} else 
				return null;
			break;

		case ("Stab3"):
			if (newAmmo > 0 && walkersLocations.size() == 3) {
				newAmmo--;
				for (Pair location: walkersLocations)
					grid[(int)location.getFirst()][(int)location.getSecond()] = Occupant.FREE;
			} else 
				return null;	
			break;
		case ("Stab4"):
				if (newAmmo > 0 && walkersLocations.size() == 4) {
				newAmmo--;
				for (Pair location: walkersLocations)
					grid[(int)location.getFirst()][(int)location.getSecond()] = Occupant.FREE;
			} else 
				return null;
			break;
		case ("Collect"):
			if ((northOccupant == Occupant.DRAGONSTONE || eastOccupant == Occupant.DRAGONSTONE
					|| southOccupant == Occupant.DRAGONSTONE || westOccupant == Occupant.DRAGONSTONE)
					&& wState.ammo < this.capacity)
				newAmmo = this.capacity;
			else
				return null;

		}
		grid[newY][newX] = Occupant.JON;
		grid[ wState.yCoord][wState.xCoord] = Occupant.FREE;
		WesterosGrid newGrid = new WesterosGrid(grid, wState.wGrid.walkersAmount);
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
				movesSequence.add(current.operatorApplied); // sequence is in reverse (starts from end)
			}
		}
			
		int pathCost = (solutionNode != null)? solutionNode.pathCost : 0;

		return new SolutionTrio(movesSequence, pathCost, numberOfNodesExpanded);
	}
	public static void main(String []args) {
		WesterosGrid westerosGrid = WesterosGrid.GenGrid();
		SolutionTrio solution = Search(westerosGrid, Strategies.bfs, false);
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
			return "THE SOLLUTION:\n Sequence: " + movesSequence.toString() + "\n TotalCost: " + totalCost + "\n Number of Nodes expanded " + numberOfNodesExpanded;
		}
	}
}
