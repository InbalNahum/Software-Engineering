package entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;


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

	public Complaint(String firstName, String lastName, String customerId
			, String carNumber, String sendTime, String description, String status) {
		super();
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.customerId = new SimpleStringProperty(customerId);
		this.carNumber = new SimpleStringProperty(carNumber);
		this.sendTime = new SimpleStringProperty(sendTime);
		this.description = new SimpleStringProperty(description);
		this.checkbox = new CheckBox();

		if(status.equals("1")) {
			this.status = new SimpleStringProperty("Close");
			this.checkbox.setSelected(true);
			this.checkbox.setDisable(true);
		}else
			this.status = new SimpleStringProperty("Open");

		this.textField = new TextField();
	}

	public TextField getTextField() {
		return this.textField;
	}

	public void setTextField(TextField textField) {
		this.textField = textField;
	}

	public CheckBox getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(CheckBox checkbox) {
		this.checkbox = checkbox;
	}


	public String getFirstName() {
		return firstName.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	public String getStatus() {
		return status.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public String getLastName() {
		return lastName.get();
	}

	public void getLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public String getCustomerId() {
		return customerId.get();
	}

	public void getCustomerId(String customerId) {
		this.customerId.set(customerId);
	}

	public String getCarNumber() {
		return carNumber.get();
	}

	public void getCarNumber(String carNumber) {
		this.carNumber.set(carNumber);
	}

	public String getSendTime() {
		return sendTime.get();
	}

	public void setSendTime(String sendTime) {
		this.sendTime.set(sendTime);
	}

	public String getDescription() {
		return description.get();
	}

	public void getDescription(String description) {
		this.description.set(description);
	}

}
