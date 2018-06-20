/**
 * Sample Skeleton for 'OneTimePreOrderWindow.fxml' Controller Class
 */

package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import entity.PreOrderCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

	ObservableList<String> comboBoxList = FXCollections.observableArrayList(CpsGlobals.telHaiBranch,
			CpsGlobals.telAvivBranch, CpsGlobals.tiberiasBranch, CpsGlobals.qiryatShemonaBranch);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cb_Branch.setItems(comboBoxList);
	}

	@FXML
	void makeOrderClick(ActionEvent event) {
		try {
			isValidInput();
			String id = tf_Id.getText();
			String carNumber = tf_CarNumber.getText();
			String email = tf_Email.getText();		
			String branchName = cb_Branch.getValue();

			LocalDate leavingDate = tf_LeavingDate.getValue();
			Calendar leavingCalendar = tf_LeavingTime.getCalendar();
			Date leavingDateTime = ServiceMethods.convertToDateObject(leavingDate, leavingCalendar);	
			LocalDate arrivingDate = tf_ArrivingDate.getValue();
			Calendar arrivingCalendar = tf_ArrivingTime.getCalendar();
			Date arrivingDateTime = ServiceMethods.convertToDateObject(arrivingDate, arrivingCalendar);
			FieldValidation.dateValidation(arrivingDateTime);
			FieldValidation.dateCompareValidation(arrivingDateTime, leavingDateTime);
			PreOrderCustomer preOrderCustomer = new PreOrderCustomer(arrivingDateTime,
					Integer.parseInt(carNumber), email, Integer.parseInt(id), 
					leavingDateTime, branchName);
			SqlClient sqlClient = SqlClient.getInstance();
			int requestToken = CpsGlobals.getNextToken();
			sqlClient.addPreOrderCustomer(preOrderCustomer,requestToken);

		}catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
			return;
		}

		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
		ServiceMethods.alertDialog(AlertType.INFORMATION, CpsGlobals.successMessage);
	}

	@FXML  
	void cancelClick(ActionEvent event) {
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}

	private void isValidInput() throws Exception {
		FieldValidation.idValidation(tf_Id.getText());
		FieldValidation.carNumberValidation(tf_CarNumber.getText());
		FieldValidation.emailValidation(tf_Email.getText());
		FieldValidation.branchNameValidation(cb_Branch.getValue());
		FieldValidation.calendarValidation(tf_ArrivingDate.getValue(),tf_ArrivingTime.getCalendar());
		FieldValidation.calendarValidation(tf_LeavingDate.getValue(), tf_LeavingTime.getCalendar());
	}
}
