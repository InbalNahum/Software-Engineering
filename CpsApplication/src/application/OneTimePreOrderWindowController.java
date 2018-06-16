/**
 * Sample Skeleton for 'OneTimePreOrderWindow.fxml' Controller Class
 */

package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jfxtras.scene.control.CalendarTimeTextField;

public class OneTimePreOrderWindowController {

	@FXML // fx:id="tf_Email"
	private TextField tf_Email; // Value injected by FXMLLoader

	@FXML // fx:id="btn_Cancel"
	private Button btn_Cancel; // Value injected by FXMLLoader

	@FXML // fx:id="tf_LeavingDate"
	private DatePicker tf_LeavingDate; // Value injected by FXMLLoader

	@FXML // fx:id="tf_ArrivingDate"
	private DatePicker tf_ArrivingDate; // Value injected by FXMLLoader

	@FXML // fx:id="tf_Id"
	private TextField tf_Id; // Value injected by FXMLLoader

	@FXML // fx:id="tf_LeavingTime"
	private CalendarTimeTextField tf_LeavingTime; // Value injected by FXMLLoader

	@FXML // fx:id="Btn_MakeOrder"
	private Button Btn_MakeOrder; // Value injected by FXMLLoader

	@FXML // fx:id="cb_Branch"
	private ComboBox<?> cb_Branch; // Value injected by FXMLLoader

	@FXML // fx:id="tf_ArrivingTime"
	private CalendarTimeTextField tf_ArrivingTime; // Value injected by FXMLLoader

	@FXML // fx:id="tf_CarNumber"
	private TextField tf_CarNumber; // Value injected by FXMLLoader

	@FXML
	void makeOrderClick(ActionEvent event) {
		if(!isValidation())
			//TODO error message  
			System.err.println("Error: makeOrderClick");

	}

	@FXML  
	void cancelClick(ActionEvent event) {

	}

	private boolean isValidation() {

		if(tf_Id.getText().equals("") ||
				tf_CarNumber.getText().equals("") ||
				tf_Email.getText().equals("") ||
				tf_LeavingDate.getValue() == null ||
				tf_LeavingTime.getCalendar() == null) {
			return false;
		}

		return true;
	}


}
