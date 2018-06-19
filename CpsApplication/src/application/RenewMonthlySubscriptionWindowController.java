package application;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import entity.MonthlySubscription;
import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.scene.control.CalendarTimeTextField;
import token.TokenProvider;

public class RenewMonthlySubscriptionWindowController {

    @FXML // fx:id="window"
    private AnchorPane window; // Value injected by FXMLLoader

    @FXML // fx:id="tf_Id"
    private TextField tf_Id; // Value injected by FXMLLoader

    @FXML // fx:id="tf_CarNumber"
    private TextField tf_CarNumber; // Value injected by FXMLLoader

    @FXML // fx:id="tf_StartingDate"
    private DatePicker tf_StartingDate; // Value injected by FXMLLoader

    @FXML // fx:id="tf_StartingTime"
    private CalendarTimeTextField tf_StartingTime; // Value injected by FXMLLoader

    @FXML // fx:id="btn_MakeOrder"
    private Button btn_MakeOrder; // Value injected by FXMLLoader

    @FXML // fx:id="btn_Cancel"
    private Button btn_Cancel; // Value injected by FXMLLoader

    @FXML
    void cancel_click(MouseEvent event) {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void makeOrder_click(ActionEvent event) {
    	try {
			isValidInput();
			String id = tf_Id.getText();
			String carNumber = tf_CarNumber.getText();
			LocalDate startingDate = tf_StartingDate.getValue();
			Calendar startingCalendar = tf_StartingTime.getCalendar();
			Date startingDateTime = ServiceMethods.convertToDateObject(startingDate, startingCalendar);	
			FieldValidation.dateValidation(startingDateTime);
			MonthlySubscription monthlySubscription = new MonthlySubscription(Integer.parseInt(id), Integer.parseInt(carNumber), startingDateTime);
			SqlClient sqlClient = SqlClient.getInstance();
			TokenProvider tokenProvider = new TokenProvider();
			int requestToken = tokenProvider.getCommunicateToken();
			sqlClient.renewMonthlySubscription(monthlySubscription,requestToken);
		}catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
			return;
		}
    	
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();	
		ServiceMethods.alertDialog(AlertType.INFORMATION, CpsGlobals.successMessage);
    }
    
	private void isValidInput() throws Exception {
		FieldValidation.idValidation(tf_Id.getText());
		FieldValidation.carNumberValidation(tf_CarNumber.getText());
		FieldValidation.calendarValidation(tf_StartingDate.getValue(), CpsGlobals.emptyCalander);
	}
}
