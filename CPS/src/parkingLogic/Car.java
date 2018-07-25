package parkingLogic;

import java.io.Serializable;

/**
 * Car Details
 * @author OmerG
 *
 */
public class Car implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ownerId;
	private int carNumber; 
	
	/**
	 * C'tor
	 * @param ownerId
	 * @param carNumber
	 */
	public Car(int ownerId, int carNumber) {	
		this.ownerId = ownerId;
		this.carNumber = carNumber;
	}

	/**
	 * 
	 * @return ownerId
	 */
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * 
	 * @param ownerId
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * 
	 * @return carNumber
	 */
	public int getCarNumber() {
		return carNumber;
	}

	/**
	 * 
	 * @param carNumber
	 */
	public void setCarNumber(int carNumber) {
		this.carNumber = carNumber;
	}

}
