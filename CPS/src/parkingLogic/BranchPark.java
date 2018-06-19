package parkingLogic;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import common.CpsGlobals.parkingState;

public class BranchPark implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ParkingFloor[] park;
	private int numOfFloors = 3;
	private Map<Integer, Location> carPlacement;
	Location globalOptimalLocation;
	boolean isFull;
	int columns[];
	
	public BranchPark(int columns[]) {
		park = new ParkingFloor[numOfFloors];
		this.columns = new int[3];
		for(int i=0; i<numOfFloors; i++) {
			park[i] = new ParkingFloor(columns[i]);
			this.columns[i] = columns[i];
		}
		carPlacement = new HashMap<Integer, Location>();
		globalOptimalLocation = new Location(0, 0, 0);
		isFull = false;
	}
	
	public void enterCarToPark(Car car) {
		park[globalOptimalLocation.getX()].enterCarToPark(car);
		carPlacement.put(car.getCarNumber(), globalOptimalLocation);
		calculateGlobalOptimalPlace();
	}
	
	public void removeCarFromPark(int carNumber) {
		Location carLocation = carPlacement.get(carNumber);
		park[carLocation.getX()].removeCarFromPark(new Point(carLocation.getY(),carLocation.getZ()));
		calculateGlobalOptimalPlace();
		carPlacement.remove(carNumber);
	}
	
	public boolean isFull() {
		return isFull;
	}

	private void calculateGlobalOptimalPlace() {
		for(int i=0; i<numOfFloors; i++) {
			if(!park[i].getIsFull()) {
				Point localOptimalLocation = park[i].getOptimalPlace();
				globalOptimalLocation = new Location(i, localOptimalLocation.x, localOptimalLocation.y);
				return;
			}
		}
		isFull = true;
	}
	
	public BranchParkState getBranchState() {
		return new BranchParkState(park, columns);
	}
	
	public void setParkingState(Location carLocation, parkingState state) {
		park[carLocation.getX()].setParkingState(new Point(carLocation.getY(), carLocation.getZ()), state);
	}
	

}
