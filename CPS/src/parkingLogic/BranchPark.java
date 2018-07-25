package parkingLogic;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import common.CpsGlobals.parkingState;

/**
 * Branch Park Details
 * @author OmerG
 *
 */
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
	
	/**
	 * C'tor
	 * @param columns
	 */
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
	
	/**
	 * enter Car To Park
	 * @param car
	 */
	public void enterCarToPark(Car car) {
		park[globalOptimalLocation.getX()].enterCarToPark(car);
		carPlacement.put(car.getCarNumber(), globalOptimalLocation);
		calculateGlobalOptimalPlace();
	}
	
	/**
	 * remove Car From Park
	 * @param carNumber
	 * @return
	 */
	public boolean removeCarFromPark(int carNumber) {
		Location carLocation = carPlacement.get(carNumber);
		if(carLocation == null)
			return false;
			
		park[carLocation.getX()].removeCarFromPark(new Point(carLocation.getY(),carLocation.getZ()));
		calculateGlobalOptimalPlace();
		carPlacement.remove(carNumber);
		return true;
	}
	
	/**
	 * 
	 * @return isFull
	 */
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
	
	/**
	 * 
	 * @return BranchParkState
	 */
	public BranchParkState getBranchState() {
		return new BranchParkState(park, columns);
	}
	
	/**
	 * 
	 * @param carLocation
	 * @param state
	 */
	public void setParkingState(Location carLocation, parkingState state) {
		park[carLocation.getX()].setParkingState(new Point(carLocation.getY(), carLocation.getZ()), state);
	}

	/**
	 * 
	 * @return numOfFloors
	 */
	public int getNumOfFloors() {
		return numOfFloors;
	}

	/**
	 * 
	 * @param numOfFloors
	 */
	public void setNumOfFloors(int numOfFloors) {
		this.numOfFloors = numOfFloors;
	}

	/**
	 * 
	 * @return columns
	 */
	public int[] getColumns() {
		return columns;
	}

	/**
	 * 
	 * @param columns
	 */
	public void setColumns(int[] columns) {
		this.columns = columns;
	}

	/**
	 * 
	 * @param isFull
	 */
	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}
}
