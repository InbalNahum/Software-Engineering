package parkingLogic;

import java.io.Serializable;

import common.CpsGlobals.parkingState;

/**
 * Car Park Details
 * @author OmerG
 *
 */
public class CarPark implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private parkingState state;
	private Car car = null;

	/**
	 * 
	 * @param state
	 */
	public CarPark(parkingState state) {
		this.state = state;
	}

	/**
	 * 
	 * @return state
	 */
	public parkingState getState() {
		return state;
	}

	/**
	 * 
	 * @param state
	 */
	public void setState(parkingState state) {
		this.state = state;
	}

	/**
	 * 
	 * @param car
	 */
	public void addCar(Car car) {
		this.car = car;
		setState(parkingState.unAvailable);
	}
	
	/**
	 * 
	 */
	public void removeCar() {
		this.car = null; 
		setState(parkingState.available);
	}

}
