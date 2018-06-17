package parkingLogic;

import java.util.HashMap;
import java.util.Map;

import actors.Employee;

public class ParkingBranch {

	private ParkingFloor[] parking;
	private int numOfFloors = 3;
	private Map<Integer, Location> carPlacement;
	
	public ParkingBranch() {
		
		parking = new ParkingFloor[numOfFloors];
		carPlacement = new HashMap<Integer, Location>();
	}
	
	public void enterCarToPark() {
		
	}
	
	public void removeCarFromPark() {
		
	}
}
