package search;

public class State {
	int xCoord;
	int yCoord;
	int ammo;
	int wKilled;
	Occupant northOccupant;
	Occupant westOccupant;
	Occupant eastOccupant;
	Occupant southOccupant;
	
	 
	public enum Occupant {
		WALKER, DRAGOSTONE, OBSTACLE, FREE 
	}
}
