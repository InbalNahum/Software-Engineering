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

    @FXML
    void parkCar_click(ActionEvent event) {
     moveToWindow(event, CpsGlobals.enterCarToParkingWindow,
    		 CpsGlobals.EnterCarToParkingWithCheckWindowTitle);
    }

    @FXML
    void mailBox_click(ActionEvent event) {
      moveToWindow(event, CpsGlobals.clientMailBoxWindow, CpsGlobals.clientMailBoxTitle);
    }

    @FXML
    void sendComplaint_click(ActionEvent event) {
       moveToWindow(event, CpsGlobals.complaintFormWindow,
    		   CpsGlobals.ComplaintFormWindowTitle);
    }

    @FXML
    void renewSub_click(ActionEvent event) {
      moveToWindow(event, CpsGlobals.renewSubscriptionWindow,
    		  CpsGlobals.RenewMonthlySubscriptionWindowTitle);
    }
    

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
