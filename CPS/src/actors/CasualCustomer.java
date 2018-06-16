package actors;

import java.io.Serializable;
import java.util.Date;

public class CasualCustomer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date arriveTime;
	private Date leaveTime;
	private String branchName;
	private int carNumber;
	private String email;
	private int id;

	
	public CasualCustomer() {
		
	}
	
	public CasualCustomer(Date arriveTime, String branchName, int carNumber, String email, int id, Date leaveTime) {
		this.arriveTime = arriveTime;
		this.branchName = branchName;
		this.carNumber = carNumber;
		this.email = email;
		this.id = id;
		this.leaveTime = leaveTime;
	}
	public Date getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public int getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(int carNumber) {
		this.carNumber = carNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

}
