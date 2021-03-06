/**
 * Sample Skeleton for 'OneTimePreOrderWindow.fxml' Controller Class
 */

package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import actors.User;
import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import entity.PreOrderCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxtras.scene.control.CalendarTimeTextField;
import server.ServerResponse;
/**
 * Window controller for one time preOrder 
 * @author inahum
 *
 */
public class OneTimePreOrderWindowController implements Initializable{

	@FXML // fx:id="tf_Email"
	private TextField tf_Email; // Value injected by FXMLLoader

	@FXML // fx:id="btn_Cancel"
	private Button btn_Cancel; // Value injected by FXMLLoader

	@FXML // fx:id="tf_LeavingDate"
	private DatePicker tf_LeavingDate; // Value injected by FXMLLoader

	@FXML // fx:id="tf_ArrivingDate"
	private DatePicker tf_ArrivingDate; // Value injected by FXMLLoader

	@FXML // fx:id="tf_LeavingTime"
	private CalendarTimeTextField tf_LeavingTime; // Value injected by FXMLLoader

	@FXML // fx:id="Btn_MakeOrder"
	private Button Btn_MakeOrder; // Value injected by FXMLLoader

	@FXML // fx:id="tf_ArrivingTime"
	private CalendarTimeTextField tf_ArrivingTime; // Value injected by FXMLLoader

	@FXML // fx:id="cb_Branch"
	private ComboBox<String> cb_Branch; // Value injected by FXMLLoader
/**
 * @param Location url and resource bundle
 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> comboBoxList = FXCollections.observableArrayList();
		try {
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.sendBranchListRequest(requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			for(Object obj : serverResponse.get().getObjects()) {
				String branchName = (String) obj;
				comboBoxList.add(branchName);
			}
		} catch (IOException | InterruptedException e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.serverIssue);
		}
		cb_Branch.setItems(comboBoxList);
	}
/**
 * Subscription clicked on make order
 * @param event
 */
	@FXML
	void makeOrderClick(ActionEvent event) {
		try {
			isValidInput();
			String id = User.getCurrentUser().getUserName();
			String carNumber = User.getCurrentUser().getPassword();
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
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.addPreOrderCustomer(preOrderCustomer,requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			ServiceMethods.alertFeedback(serverResponse,event);
            cancelClick(event);
		}catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
			return;
		}
	}
/**
 * Cancel clicked
 * @param event
 */
	@FXML  
	void cancelClick(ActionEvent event) {
        moveToWindow(event, CpsGlobals.casualCustomerMenuWindow,
       		   CpsGlobals.casualCustomerMenuWindowTitle);
	}

	private void isValidInput() throws Exception {
		FieldValidation.emailValidation(tf_Email.getText());
		FieldValidation.branchNameValidation(cb_Branch.getValue());
		FieldValidation.calendarValidation(tf_ArrivingDate.getValue(),tf_ArrivingTime.getCalendar());
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
