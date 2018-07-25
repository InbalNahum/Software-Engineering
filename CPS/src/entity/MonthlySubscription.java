package entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Monthly Subscription Details
 * @author OmerG
 *
 */
public class MonthlySubscription implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int carNumber;
	private Date StartingTime;
	private float custonerAccount; 
	
	/**
	 * C'tor
	 * @param id
	 * @param carNumber
	 * @param StartingTime
	 */
	public MonthlySubscription(int id, int carNumber, Date StartingTime) {
		this.id = id;
		this.carNumber = carNumber;
		this.StartingTime = StartingTime;
	}

	/**
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
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

	/**
	 * 
	 * @return StartingTime
	 */
	public Date getStartingTime() {
		return StartingTime;
	}

	/**
	 * 
	 * @param statingTime
	 */
	public void setStartingTime(Date statingTime) {
		StartingTime = statingTime;
	}

	/**
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	public float getCustomerAccout() {
		return this.custonerAccount;
	}
	
	public void setCustomerAccout(float customerAccout) {
		this.custonerAccount = customerAccout; 
	}

}