/**
 * Sample Skeleton for 'SubScriberMenu.fxml' Controller Class
 */

package application;

import java.io.IOException;

import actors.User;
import client.SqlClient;
import common.CpsGlobals;
import common.ServiceMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
/**
 * Window controller for subscription
 * @author inahum
 *
 */
public class SubscriberMenuController {

    @FXML // fx:id="mailBox_btn"
    private Button mailBox_btn; // Value injected by FXMLLoader

    @FXML // fx:id="parkCar_btn"
    private Button parkCar_btn; // Value injected by FXMLLoader

    @FXML // fx:id="sendComplaint_btn"
    private Button sendComplaint_btn; // Value injected by FXMLLoader

    @FXML // fx:id="orderSub_btn"
    private Button orderSub_btn; // Value injected by FXMLLoader

    @FXML // fx:id="renewSub_btn"
    private Button renewSub_btn; // Value injected by FXMLLoader
/**
 * ParkCar clicked
 * The subscription want to park car
 * @param event
 */
    @FXML
    void parkCar_click(ActionEvent event) {
     moveToWindow(event, CpsGlobals.enterCarToParkingWindow,
    		 CpsGlobals.EnterCarToParkingWithCheckWindowTitle);
    }
/**
 * MailBox clicked
 * The subscription wants to see his mail box
 * @param event
 */
    @FXML
    void mailBox_click(ActionEvent event) {
      moveToWindow(event, CpsGlobals.clientMailBoxWindow, CpsGlobals.clientMailBoxTitle);
    }
    
/**
 * Send complaint clicked
 * The subscription want to send a new complaint
 * @param event
 */
    @FXML
    void sendComplaint_click(ActionEvent event) {
       moveToWindow(event, CpsGlobals.complaintFormWindow,
    		   CpsGlobals.ComplaintFormWindowTitle);
    }
/**
 * Renew subscription clicked
 * The subscription want to renew the subscription
 * @param event
 */
    @FXML
    void renewSub_click(ActionEvent event) {
      moveToWindow(event, CpsGlobals.renewSubscriptionWindow,
    		  CpsGlobals.RenewMonthlySubscriptionWindowTitle);
    }
    /**
     * Get car clicked
     * The subscription want to get his car from the parking
     * @param event
     */
    @FXML
    void getCar_click(ActionEvent event) {
    	moveToWindow(event,CpsGlobals.removeCar,
    			CpsGlobals.removeCarTitle);
    }
    
/**
 * Cancel clicked 
 * @param event
 */
    @FXML
    void cancel_click(ActionEvent event) {
    	try {
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int token = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.removeUser(User.getCurrentUser(),token);
    	} catch (IOException | InterruptedException e) {
          e.printStackTrace();
		}
        moveToWindow(event, CpsGlobals.customerLogin,
       		   CpsGlobals.customerLoginTitle);
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
