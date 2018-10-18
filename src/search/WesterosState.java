package search;

public class WesterosState extends State {
	int xCoord;
	int yCoord;
	int ammo;
	int walkersAlive;
	WesterosGrid wGrid;
	
	public WesterosState(int xCoord, int yCoord, int walkersAlive, WesterosGrid wGrid) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.walkersAlive = walkersAlive;
		this.wGrid = wGrid;
	}

	public WesterosState(int xCoord, int yCoord, int ammo, int walkersAlive, WesterosGrid wGrid) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.ammo = ammo;
		this.walkersAlive = walkersAlive;
		this.wGrid = wGrid;
	}
	 
	public enum Occupant {
		WALKER, OBSTACLE, FREE, DRAGONSTONE, JON, JONDRAGONSTONE
	}

	public String simpleString() {
		return xCoord + yCoord + ammo + wGrid.simpleString();
	}

	@Override
	public String toString() {
		return "Xcoord: " + xCoord + "\n Ycoord: " + yCoord + "\n Ammo:" + ammo + "\n Grid:\n" + wGrid;
	}

}
