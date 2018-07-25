package entity;

import javafx.scene.control.TextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

/**
 * Complaint Details
 * @author OmerG
 *
 */
public class Complaint{

	private  SimpleStringProperty firstName;
	private  SimpleStringProperty lastName;
	private  SimpleStringProperty customerId;
	private  SimpleStringProperty carNumber;
	private  SimpleStringProperty sendTime;
	private  SimpleStringProperty description;
	private  SimpleStringProperty status;
	private CheckBox checkbox; 
	private TextField textField;
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
	public Complaint(String firstName, String lastName, String customerId
			, String carNumber, String sendTime, String description, String status, String refund) {
		super();
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.customerId = new SimpleStringProperty(customerId);
		this.carNumber = new SimpleStringProperty(carNumber);
		this.sendTime = new SimpleStringProperty(sendTime);
		this.description = new SimpleStringProperty(description);
		this.checkbox = new CheckBox();
		this.textField = new TextField();
		if(status.equals("1")) {
			this.status = new SimpleStringProperty("Close");
			this.checkbox.setSelected(true);
			this.checkbox.setDisable(true);
			this.textField.setText(refund);
			this.textField.setEditable(false);
			this.refund = refund;
		}else
			this.status = new SimpleStringProperty("Open");
	}

	/**\
	 * 
	 * @return textField
	 */
	public TextField getTextField() {
		return this.textField;
	}

	/**
	 * 
	 * @param textField
	 */
	public void setTextField(TextField textField) {
		this.textField = textField;
	}

	/**
	 * 
	 * @return checkbox
	 */
	public CheckBox getCheckbox() {
		return checkbox;
	}

	/**
	 * 
	 * @param checkbox
	 */
	public void setCheckbox(CheckBox checkbox) {
		this.checkbox = checkbox;
	}

	/**
	 * 
	 * @return firstName.get()
	 */
	public String getFirstName() {
		return firstName.get();
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status.set(status);
	}

	/**
	 * 
	 * @return status.get()
	 */
	public String getStatus() {
		return status.get();
	}

	/**
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	/**
	 * 
	 * @return lastName.get()
	 */
	public String getLastName() {
		return lastName.get();
	}

	/**
	 * 
	 * @param lastName
	 */
	public void getLastName(String lastName) {
		this.lastName.set(lastName);
	}

	/**
	 * 
	 * @return customerId.get()
	 */
	public String getCustomerId() {
		return customerId.get();
	}

	/**
	 * 
	 * @param customerId
	 */
	public void getCustomerId(String customerId) {
		this.customerId.set(customerId);
	}

	/**
	 * 
	 * @return carNumber.get()
	 */
	public String getCarNumber() {
		return carNumber.get();
	}

	/**
	 * 
	 * @param carNumber
	 */
	public void getCarNumber(String carNumber) {
		this.carNumber.set(carNumber);
	}

	/**
	 * 
	 * @return sendTime.get()
	 */
	public String getSendTime() {
		return sendTime.get();
	}

	/**
	 * 
	 * @param sendTime
	 */
	public void setSendTime(String sendTime) {
		this.sendTime.set(sendTime);
	}

	/**
	 * 
	 * @return description.get()
	 */
	public String getDescription() {
		return description.get();
	}

	/**
	 * 
	 * @param description
	 */
	public void getDescription(String description) {
		this.description.set(description);
	}

	/**
	 * 
	 * @return refund
	 */
	public String getRefund() {
		return refund;
	}

	/**
	 * 
	 * @param refund
	 */
	public void setRefund(String refund) {
		this.refund = refund;
	}

}
