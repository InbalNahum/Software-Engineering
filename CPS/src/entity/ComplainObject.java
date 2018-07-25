package entity;

import java.io.Serializable;

/**
 * Complain Object Details
 * @author OmerG
 *
 */
public class ComplainObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String firstName;
	private  String lastName;
	private  String customerId;
	private  String carNumber;
	private  String sendTime;
	private  String description;
	private  String status;
	private String refund;

	/**
	 * C'tor
	 * @param firstName
	 * @param lastName
	 * @param customerId
	 * @param carNumber
	 * @param sendTime
	 * @param description
	 * @param status
	 * @param refund
	 */
	public ComplainObject(String firstName, String lastName, String customerId
			, String carNumber, String sendTime, String description, String status, String refund) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.customerId = customerId;
		this.carNumber = carNumber;
		this.sendTime = sendTime;
		this.description = description;
		this.status = status;
		this.refund = refund;
	}
	
	/**
	 * C'tor
	 * @param carNumber
	 * @param refund
	 */
	public ComplainObject(String carNumber, String refund) {
		super();
		this.carNumber = carNumber;
		this.refund = refund;
		this.firstName = "";
		this.lastName = "";
		this.customerId = "";
		this.sendTime = "";
		this.description = "";
		this.status = "";
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
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
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
	public void getLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * 
	 * @return customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * 
	 * @param customerId
	 */
	public void getCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * 
	 * @return carNumber
	 */
	public String getCarNumber() {
		return carNumber;
	}

	/**
	 * 
	 * @param carNumber
	 */
	public void getCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	/**
	 * 
	 * @return sendTime
	 */
	public String getSendTime() {
		return sendTime;
	}

	/**
	 * 
	 * @param sendTime
	 */
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 */
	public void getDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * 
	 * @param refund
	 */
	public void setRefund(String refund) {
		this.refund = refund;
	}
	
	/**
	 * 
	 * @return refund
	 */
	public String getRefund() {
		return this.refund;
	}
}
