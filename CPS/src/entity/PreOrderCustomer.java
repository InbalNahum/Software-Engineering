package entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Pre-Order Customer Details
 * @author OmerG
 *
 */
public class PreOrderCustomer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date arriveTime;
	private Date leaveTime;
	private int carNumber;
	private String email;
	private int id;
	private String branchName;

	/**
	 * C'tor
	 * @param arriveTime
	 * @param carNumber
	 * @param email
	 * @param id
	 * @param leaveTime
	 * @param branchName
	 */
	public PreOrderCustomer(Date arriveTime, int carNumber, String email,
			int id, Date leaveTime, String branchName) {
		this.arriveTime = arriveTime;
		this.carNumber = carNumber;
		this.email = email;
		this.id = id;
		this.leaveTime = leaveTime;
		this.branchName = branchName;
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
	
	/**
	 * 
	 * @return branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	
	/**
	 * 
	 * @param branchName
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}
