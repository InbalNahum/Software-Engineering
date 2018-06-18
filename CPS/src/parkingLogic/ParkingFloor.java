package parkingLogic;

import java.awt.Point;

import common.CpsGlobals.parkingState;

public class ParkingFloor {
	private CarPark[][] parkingFloor;
	private int rows = 3; 
	private int columns;
	private Point optimalPlace;
	private boolean isFull;

	public ParkingFloor(int columns) {
		parkingFloor = new CarPark[rows][columns];
		optimalPlace = new Point(0, 0);
		isFull = false;
		this.columns = columns;
		initialParkingFloor();
	}

	private void initialParkingFloor() {
		for(int i = 0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				parkingFloor[i][j] = new CarPark(parkingState.available);
			}
		}
	}

	public Point getOptimalPlace() {
		return optimalPlace;
	}

	public boolean getIsFull() {
		return isFull;
	}

	public void removeCarFromPark(Point carLocation) {
		isFull = false;
		CarPark carPark = parkingFloor[carLocation.x][carLocation.y];
		carPark.removeCar();
		calculateOptimal();
	}
	
	public Point enterCarToPark(Car car) {
		CarPark carPark = parkingFloor[optimalPlace.x][optimalPlace.y];
		carPark.addCar(car);
		Point carLocation = new Point(optimalPlace.x,optimalPlace.y);
		calculateOptimal();
		return carLocation;
	}

	private void calculateOptimal() {
		for(int i = 0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				if(parkingFloor[i][j].getState() == parkingState.available) {
					optimalPlace = new Point(i, j);
					return;
				}
			}
		}
		isFull = true;
	}
	
	public parkingState[][] getFloorState(){
		parkingState[][] state = new parkingState[rows][columns];
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				state[i][j] = parkingFloor[i][j].getState();
			}
		}
		return state;
	}
	
	public void setParkingState(Point Location, parkingState state) {
		parkingFloor[Location.x][Location.y].setState(state); 
	}
}
