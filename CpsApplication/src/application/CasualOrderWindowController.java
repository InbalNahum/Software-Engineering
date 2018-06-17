/**
 * Sample Skeleton for 'CasualOrderWindow.fxml' Controller Class
 */

package application;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import actors.CasualCustomer;
import client.ClientRequest;
import client.SqlClient;
import common.CpsGlobals;
import common.CpsGlobals.ServerOperation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jfxtras.scene.control.CalendarTimeTextField;

public class CasualOrderWindowController {

	@FXML // fx:id="tf_Email"
	private TextField tf_Email; // Value injected by FXMLLoader

	@FXML // fx:id="btn_Cancel"
	private Button btn_Cancel; // Value injected by FXMLLoader

	@FXML // fx:id="tf_LeavingDate"
	private DatePicker tf_LeavingDate; // Value injected by FXMLLoader

	@FXML // fx:id="tf_Id"
	private TextField tf_Id; // Value injected by FXMLLoader

	@FXML // fx:id="tf_LeavingTime"
	private CalendarTimeTextField tf_LeavingTime; // Value injected by FXMLLoader

	@FXML // fx:id="btn_MakeOrder"
	private Button btn_MakeOrder; // Value injected by FXMLLoader

	@FXML // fx:id="tf_CarNumber"
	private TextField tf_CarNumber; // Value injected by FXMLLoader

	@FXML
	void makeOrder_click(ActionEvent event) {
		if(!isValidOrder()) {
			//TODO report to user 
		}
		String id = tf_Id.getText();
		String carNumber = tf_CarNumber.getText();
		String email = tf_Email.getText();
		LocalDate date = tf_LeavingDate.getValue();
		Calendar calendar = tf_LeavingTime.getCalendar();
		Date dateTime = calendar.getTime();
		LocalTime time = LocalDateTime.ofInstant(dateTime.toInstant(), ZoneId.systemDefault()).toLocalTime();
		LocalDateTime dt = LocalDateTime.of(date, time);
		Date leavingDateTime = Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());	
		try {
			CasualCustomer casualCustomer = new CasualCustomer(new Date(),
					Integer.parseInt(carNumber),email, Integer.parseInt(id),
					leavingDateTime);
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.addCasualCustomer(casualCustomer);
		}catch(NumberFormatException e) {

		} catch (IOException e) {

		}
	}

	@FXML
	void cancel_click(ActionEvent event) {

	}




	private boolean isValidOrder() {
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
