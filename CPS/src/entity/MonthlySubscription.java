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
	private Date startTime;
	
	public MonthlySubscription(int id, int carNumber, Date startTime) {
		super();
		this.id = id;
		this.carNumber = carNumber;
		this.startTime = startTime;
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
