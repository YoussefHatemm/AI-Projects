package search;

import search.WesterosState.Occupant;

/**
 * WesterosGrid
 */
public class WesterosGrid {

    Occupant[][] grid;
    int walkersAmount;
    int capacity;

    public WesterosGrid(Occupant[][] grid, int walkersAmount) {
        this.grid = grid;
        this.walkersAmount = walkersAmount;
        capacity = (int)(Math.random()*100) + 1; 		
    }

    public static WesterosGrid GenGrid() {
        int m = (int) (Math.random() * 100) + 4;
		int n = (int) (Math.random() * 100) + 4;
        
        int walkersAmount = 0;

        Occupant[][] grid = new Occupant[m][n];
		grid[0][0] = Occupant.JON;
		
		Occupant[] cellOptions = Occupant.values();
		
		for (int i = 0; i < grid.length; i++)
            for (int j = (i == 0) ? 1 : 0; j < grid[i].length; j++) {
                grid[i][j] = cellOptions[(int) (Math.random() * 3)];
                if (grid[i][j] == Occupant.WALKER)
                    walkersAmount++;
            }
		
		int dragonX = (int) (Math.random() * (m - 1)) + 1;
		int dragonY = (int) (Math.random() * (n - 1)) + 1;
		grid[dragonX][dragonY] = cellOptions[3];
		
		return new WesterosGrid(grid, walkersAmount);
    }

    public int getWalkersAmount() {
        return walkersAmount;
    }

    public int getCapacity() {
        return capacity;
    }
}