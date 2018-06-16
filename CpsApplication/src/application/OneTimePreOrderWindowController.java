/**
 * Sample Skeleton for 'OneTimePreOrderWindow.fxml' Controller Class
 */

package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import client.SqlClient;
import common.CpsGlobals;
import entity.PreOrderCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jfxtras.scene.control.CalendarTimeTextField;

public class OneTimePreOrderWindowController implements Initializable{


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

	@FXML // fx:id="tf_ArrivingTime"
	private CalendarTimeTextField tf_ArrivingTime; // Value injected by FXMLLoader

	@FXML // fx:id="tf_CarNumber"
	private TextField tf_CarNumber; // Value injected by FXMLLoader

	@FXML // fx:id="cb_Branch"
	private ComboBox<String> cb_Branch; // Value injected by FXMLLoader
	
	ObservableList<String> comboBoxList = FXCollections.observableArrayList(CpsGlobals.telHaiBranch, CpsGlobals.telAvivBranch);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cb_Branch.setItems(comboBoxList);
	}

	@FXML
	void makeOrderClick(ActionEvent event) {
		
		if(!isValidation()) {
			System.err.println("Error: makeOrderClick");
			return;
		}
		
		String id = tf_Id.getText();
		String carNumber = tf_CarNumber.getText();
		String email = tf_Email.getText();		
		LocalDate leavingDate = tf_LeavingDate.getValue();
		Calendar leavingCalendar = tf_LeavingTime.getCalendar();
		Date leavingDateTime = convertToDateObject(leavingDate, leavingCalendar);	
		LocalDate arrivingDate = tf_ArrivingDate.getValue();
		Calendar arrivingCalendar = tf_ArrivingTime.getCalendar();
		Date arrivingDateTime = convertToDateObject(arrivingDate, arrivingCalendar);
		String branchName = cb_Branch.getValue();

		try {
			PreOrderCustomer preOrderCustomer = new PreOrderCustomer(arrivingDateTime,
					Integer.parseInt(carNumber), email, Integer.parseInt(id), 
					leavingDateTime, branchName);
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.addPreOrderCustomer(preOrderCustomer);
		}catch(IOException e) {}
	}

	private Date convertToDateObject(LocalDate leavingDate, Calendar leavingCalendar) {

		Date dateTime = leavingCalendar.getTime();
		LocalTime time = LocalDateTime.ofInstant(dateTime.toInstant(), ZoneId.systemDefault()).toLocalTime();
		LocalDateTime dt = LocalDateTime.of(leavingDate, time);
		Date leavingDateTime = Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
		return leavingDateTime;
	}

	@FXML  
	void cancelClick(ActionEvent event) {

	}

	private boolean isValidation() {

		if(    tf_Id.getText().equals("")
				|| tf_CarNumber.getText().equals("")
				|| tf_Email.getText().equals("")
				|| tf_LeavingDate.getValue() == null 
				|| tf_ArrivingDate.getValue() == null 
				|| tf_LeavingTime.getCalendar() == null
				|| tf_ArrivingTime.getCalendar() == null
				|| cb_Branch.getValue() == null) {
			return false;
		}

		return true;
	}

}
