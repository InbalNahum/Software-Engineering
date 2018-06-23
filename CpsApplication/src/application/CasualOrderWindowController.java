/**
 * Sample Skeleton for 'CasualOrderWindow.fxml' Controller Class
 */

package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import actors.CasualCustomer;
import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.scene.control.CalendarTimeTextField;
import server.ServerResponse;

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

		try {
			isValidInput();
			String id = tf_Id.getText();
			String carNumber = tf_CarNumber.getText();
			String email = tf_Email.getText();


			LocalDate leavingDate = tf_LeavingDate.getValue();
			Calendar leavingCalendar = tf_LeavingTime.getCalendar();

			Date arrivingDateTime = new Date();
			Date leavingDateTime = ServiceMethods.convertToDateObject(leavingDate, leavingCalendar);

			CasualCustomer casualCustomer = new CasualCustomer(arrivingDateTime,
					Integer.parseInt(carNumber),email, Integer.parseInt(id),leavingDateTime);
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.addCasualCustomer(casualCustomer,requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			String message = (String) serverResponse.get().getObjectAtIndex(0);
			ServiceMethods.alertDialog(AlertType.INFORMATION, message);
		}catch(Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
			return;
		}
	}

    @FXML
    void cancel_click(ActionEvent event) {
        moveToWindow(event, CpsGlobals.casualCustomerMenuWindow,
     		   CpsGlobals.casualCustomerMenuWindowTitle);
    }

	private void isValidInput() throws Exception {
		FieldValidation.idValidation(tf_Id.getText());
		FieldValidation.carNumberValidation(tf_CarNumber.getText());
		FieldValidation.emailValidation(tf_Email.getText());
		FieldValidation.calendarValidation(tf_LeavingDate.getValue(), tf_LeavingTime.getCalendar());
	}
	
	private void moveToWindow(ActionEvent event,String windowName,String windowTitle) {
    	try {		
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(windowName));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Stage stage = new Stage();
    		stage.setTitle(windowTitle);
    		stage.setScene(new Scene(root1));
    		stage.getIcons().add(new Image(getClass().getResourceAsStream(CpsGlobals.cpsIconPath)));
    		stage.show();
    		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    	}
    	catch(IOException e) {
    		ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.failToLoadWindow);
    	}
	}
}
