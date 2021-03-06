package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import entity.MonthlySubscription;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.scene.control.CalendarTimeTextField;
import server.ServerResponse;
/**
 * Window controller for monthly subscription order
 * @author inahum
 *
 */
public class MonthlySubscriptionOrderWindowController {

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
/**
 * Cancel clicked
 * @param event
 */
    @FXML
    void cancel_click(ActionEvent event) {
    	moveToWindow(event,CpsGlobals.casualCustomerMenuWindow,
    			CpsGlobals.casualCustomerMenuWindowTitle);
    }
/**
 * sSubscription make a new order
 * @param event
 */
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
            sqlClient.sendTokenRequest();
            int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.addMonthlySubscription(monthlySubscription,requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			ServiceMethods.alertFeedback(serverResponse,event);
			cancel_click(event);
		}catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
			return;
		}
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
    
	private void isValidInput() throws Exception {
		FieldValidation.idValidation(tf_Id.getText());
		FieldValidation.carNumberValidation(tf_CarNumber.getText());
		FieldValidation.calendarValidation(tf_StartingDate.getValue(), CpsGlobals.emptyCalander);
	}
}


