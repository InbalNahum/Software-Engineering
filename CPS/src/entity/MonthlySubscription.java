package entity;

import java.io.Serializable;
import java.util.Date;

public class MonthlySubscription implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int carNumber;
	private Date StartingTime;
	private float custonerAccount; 
	
	public MonthlySubscription(int id, int carNumber, Date StartingTime) {
		this.id = id;
		this.carNumber = carNumber;
		this.StartingTime = StartingTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(int carNumber) {
		this.carNumber = carNumber;
	}

	public Date getStartingTime() {
		return StartingTime;
	}

	public void setStartingTime(Date statingTime) {
		StartingTime = statingTime;
	}

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