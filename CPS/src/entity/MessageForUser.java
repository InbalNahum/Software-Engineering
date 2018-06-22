package entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextArea;

public class MessageForUser {
	
	private SimpleStringProperty messageNumber;
	private SimpleStringProperty messageDetails;
	
	public MessageForUser() {}

	public MessageForUser(int messageNumber, String messageDetails) {
		super();
		this.messageNumber = new SimpleStringProperty(String.valueOf(messageNumber));
		this.messageDetails = new SimpleStringProperty(messageDetails);
	}

	public String getMessageNumber() {
		return messageNumber.getValue();
	}

	public void setMessageNumber(SimpleStringProperty messageNumber) {
		this.messageNumber = messageNumber;
	}

	public TextArea getMessageDetails() {
		String messageDetails = this.messageDetails.getValue();
		TextArea detailsHolder = new TextArea();
		detailsHolder.setPrefHeight(40);
		detailsHolder.setText(messageDetails);
		detailsHolder.setEditable(false);
		return detailsHolder;
	}

	public void setMessageDetails(SimpleStringProperty messageDetails) {
		this.messageDetails = messageDetails;
	}

}
