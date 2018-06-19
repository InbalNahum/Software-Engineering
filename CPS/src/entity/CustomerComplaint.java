package entity;

import java.io.Serializable;
import java.util.Date;

public class CustomerComplaint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum Status{opened,closed};
	
	private String firstName;
	private String lastName;
	private int userId;
	private int carNumber;
	private String Description;
	private Date sendTime;
	private Status status;
	private String email;
	
	public CustomerComplaint(String firstName, String lastName, int userId, int carNumber,
							String Description, Date sendTime, String email) {
		super();
		this.firstName =  firstName;
		this.lastName = lastName;
		this.userId = userId;
		this.carNumber = carNumber;
		this.Description = Description;
		this.sendTime = sendTime;
		this.status = Status.opened;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(int carNumber) {
		this.carNumber = carNumber;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
