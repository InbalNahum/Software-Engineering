package entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextArea;

/**
 * Message For User Details
 * @author OmerG
 *
 */
public class MessageForUser {
	
	private SimpleStringProperty messageNumber;
	private SimpleStringProperty messageDetails;
	
	/**
	 * empty C'tor
	 */
	public MessageForUser() {}

	/**
	 * C'tor
	 * @param messageNumber
	 * @param messageDetails
	 */
	public MessageForUser(int messageNumber, String messageDetails) {
		super();
		this.messageNumber = new SimpleStringProperty(String.valueOf(messageNumber));
		this.messageDetails = new SimpleStringProperty(messageDetails);
	}

	/**
	 * 
	 * @return messageNumber.getValue()
	 */
	public String getMessageNumber() {
		return messageNumber.getValue();
	}

	/**
	 * 
	 * @param messageNumber
	 */
	public void setMessageNumber(SimpleStringProperty messageNumber) {
		this.messageNumber = messageNumber;
	}

	/**
	 * 
	 * @return detailsHolder
	 */
	public TextArea getMessageDetails() {
		String messageDetails = this.messageDetails.getValue();
		TextArea detailsHolder = new TextArea();
		detailsHolder.setPrefHeight(40);
		detailsHolder.setText(messageDetails);
		detailsHolder.setEditable(false);
		return detailsHolder;
	}

	/**
	 * 
	 * @param messageDetails
	 */
	public void setMessageDetails(SimpleStringProperty messageDetails) {
		this.messageDetails = messageDetails;
	}

}
