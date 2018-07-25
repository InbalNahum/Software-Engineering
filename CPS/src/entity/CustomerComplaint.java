package entity;

import java.io.Serializable;
import java.util.Date;

/**
 *  Customer Complaint Details
 * @author OmerG
 *
 */
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
	
	/**
	 * C'tor
	 * @param firstName
	 * @param lastName
	 * @param userId
	 * @param carNumber
	 * @param Description
	 * @param sendTime
	 * @param email
	 */
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
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * 
	 * @return userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * 
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
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
	 * @return Description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		Description = description;
	}

	/**
	 * 
	 * @return sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * 
	 * @param sendTime
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * 
	 * @return status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
