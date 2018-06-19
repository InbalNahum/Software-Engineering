package parkingLogic;

import java.io.Serializable;

public class Car implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ownerId;
	private int carNumber; 
	
	public Car(int ownerId, int carNumber) {	
		this.ownerId = ownerId;
		this.carNumber = carNumber;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(int carNumber) {
		this.carNumber = carNumber;
	}

}
