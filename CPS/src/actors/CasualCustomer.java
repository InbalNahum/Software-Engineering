package actors;

import java.io.Serializable;
import java.util.Date;

/**
 * Casual Customer Details
 * @author OmerG
 *
 */
public class CasualCustomer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date arriveTime;
	private Date leaveTime;
	private int carNumber;
	private String email;
	private int id;

	/**
	 * empty C'tor
	 */
	public CasualCustomer() {
		
	}
	
	/**
	 * C'tor
	 * @param arriveTime
	 * @param carNumber
	 * @param email
	 * @param id
	 * @param leaveTime
	 */
	public CasualCustomer(Date arriveTime, int carNumber, String email, int id, Date leaveTime) {
		this.arriveTime = arriveTime;
		this.carNumber = carNumber;
		this.email = email;
		this.id = id;
		this.leaveTime = leaveTime;
	}
	
	/**
	 * 
	 * @return arriveTime
	 */
	public Date getArriveTime() {
		return arriveTime;
	}
	
	/**
	 * 
	 * @param arriveTime
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	/**
	 * 
	 * @carNumber
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
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return leaveTime
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}
	
	/**
	 * 
	 * @param leaveTime
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

}
