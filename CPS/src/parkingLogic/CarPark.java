package parkingLogic;

import java.io.Serializable;

import common.CpsGlobals.parkingState;

public class CarPark implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private parkingState state;
	private Car car = null;

	public CarPark(parkingState state) {
		this.state = state;
	}

	public parkingState getState() {
		return state;
	}

	public void setState(parkingState state) {
		this.state = state;
	}

	public void addCar(Car car) {
		this.car = car;
		setState(parkingState.unAvailable);
	}
	
	public void removeCar() {
		this.car = null; 
		setState(parkingState.available);
	}

}
