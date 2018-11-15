package search;

import search.WesterosState.Occupant;

/**
 * WesterosGrid
 */
public class WesterosGrid {

    Occupant[][] grid;
    int walkersAmount;

    public WesterosGrid(Occupant[][] grid, int walkersAmount) {
        this.grid = grid;
        this.walkersAmount = walkersAmount; 		
    }

    public static WesterosGrid GenGrid() {
        int m = (int) (Math.random() * 4) + 4;
		int n = (int) (Math.random() * 4) +  4;
        
        int walkersAmount = 0;

        Occupant[][] grid = new Occupant[m][n];
		grid[0][0] = Occupant.JON;
		
		Occupant[] cellOptions = Occupant.values();

        int dragonX = (int) (Math.random() * (m - 1)) + 1;
        int dragonY = (int) (Math.random() * (n - 1)) + 1;
        grid[dragonX][dragonY] = cellOptions[3];

        for (int i = 0; i < grid.length; i++)
            for (int j = (i == 0) ? 1 : 0; j < grid[i].length; j++) {
                if (i == dragonX && j == dragonY)
                    continue;

                grid[i][j] = cellOptions[(int) (Math.random() * 3)];
                if (grid[i][j] == Occupant.WALKER)
                    walkersAmount++;
            }

		return new WesterosGrid(grid, walkersAmount);
    }

    public static Occupant[][] deepCloneGrid(WesterosGrid wGrid) {
        Occupant [][] oldGrid = wGrid.grid;
        Occupant [][] newGrid = new Occupant[oldGrid.length][oldGrid[0].length];
        for (int i = 0; i < oldGrid.length; i++)
            for(int j= 0; j < oldGrid[0].length; j++)
                newGrid[i][j] = oldGrid[i][j];

        return newGrid;
    }

    public int getWalkersAmount() {
        return walkersAmount;
    }

    @Override
    public String toString() {
        String output = "|";

        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = grid[0].length - 1; j >= 0; j--)
                output += occupantString(grid[i][j]) + "|";

            if (i > 0)
                output += "\n|";
        }
        return output;
    }

    public String simpleString() {
        String hash = "";
        Occupant [][] grid = this.grid;
        for (int i = 0; i < grid.length; i++)
            for(int j= 0; j < grid[0].length; j++)
                hash += occupantString(grid[i][j]);

        return hash;
    }

    public static String occupantString(Occupant occup) {
        switch (occup) {
            case FREE:
                return "F";
            case OBSTACLE:
                return "O";
            case DRAGONSTONE:
                return "D";
            case WALKER:
                return "W";
            case JON:
                return "J";

            case JONDRAGONSTONE:
                return "J";


        }
        return null;
    }
}