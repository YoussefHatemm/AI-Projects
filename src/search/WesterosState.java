package search;

public class WesterosState extends State {
	int xCoord;
	int yCoord;
	int ammo;
	int walkersAlive;
	Occupant northOccupant;
	Occupant westOccupant;
	Occupant eastOccupant;
	Occupant southOccupant;
	
	public WesterosState(int xCoord, int yCoord, int walkersAlive, Occupant northOccupant, Occupant westOccupant, Occupant eastOccupant, Occupant southOccupant) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.walkersAlive = walkersAlive;
		this.northOccupant = northOccupant;
		this.westOccupant = westOccupant;
		this.eastOccupant = eastOccupant;
		this.southOccupant = southOccupant;
	}
	
	 
	public enum Occupant {
		WALKER, OBSTACLE, FREE, DRAGONSTONE, JON
	}
}
