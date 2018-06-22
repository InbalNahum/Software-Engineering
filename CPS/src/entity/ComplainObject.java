package entity;

import java.io.Serializable;

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

	public ComplainObject(String firstName, String lastName, String customerId
			, String carNumber, String sendTime, String description, String status) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.customerId = customerId;
		this.carNumber = carNumber;
		this.sendTime = sendTime;
		this.description = description;
		this.status = status;
		this.refund = "";
	}
	
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

	public String getFirstName() {
		return firstName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void getLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void getCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void getCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getDescription() {
		return description;
	}

	public void getDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void setRefund(String refund) {
		this.refund = refund;
	}
	
	public String getRefund() {
		return this.refund;
	}
}
