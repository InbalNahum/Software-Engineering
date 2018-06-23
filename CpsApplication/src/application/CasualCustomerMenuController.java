/**
 * Sample Skeleton for 'CasualCustomerMenu.fxml' Controller Class
 */

package application;

import java.io.IOException;
import java.util.Optional;

import actors.User;
import client.SqlClient;
import common.CpsGlobals;
import common.ServiceMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.ServerResponse;

public class CasualCustomerMenuController {

    @FXML // fx:id="mailBox_btn"
    private Button mailBox_btn; // Value injected by FXMLLoader

    @FXML // fx:id="usePreOrder_btn"
    private Button usePreOrder_btn; // Value injected by FXMLLoader

    @FXML // fx:id="preOrder_btn"
    private Button preOrder_btn; // Value injected by FXMLLoader

    @FXML // fx:id="casualOrder_btn"
    private Button casualOrder_btn; // Value injected by FXMLLoader

    @FXML // fx:id="sendComplaint_btn"
    private Button sendComplaint_btn; // Value injected by FXMLLoader

    @FXML
    void preOrder_click(ActionEvent event) {
        moveToWindow(event, CpsGlobals.oneTimePreOrderWindow,
      		  CpsGlobals.oneTimePreOrderTitle);
    }

    @FXML
    void mailBox_click(ActionEvent event) {
        moveToWindow(event, CpsGlobals.clientMailBoxWindow,
        		CpsGlobals.clientMailBoxTitle);
    }

    @FXML
    void casualOrder_click(ActionEvent event) {
        moveToWindow(event, CpsGlobals.casualOrderWindow,
        		CpsGlobals.casualOrderWindowTitle);
    }

    @FXML
    void sendComplaint_click(ActionEvent event) {
        moveToWindow(event, CpsGlobals.complaintFormWindow,
     		   CpsGlobals.ComplaintFormWindowTitle);
    }

    @FXML
    void usePreOrder_click(ActionEvent event) {
        moveToWindow(event, CpsGlobals.enterCarToParkingWithCheckWindow,
      		   CpsGlobals.EnterCarToParkingWithCheckWindowTitle);
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
    

    @FXML
    void cancelPreOrder_click(ActionEvent event) {
        moveToWindow(event, CpsGlobals.cancelPreOrder,
        		   CpsGlobals.cancelPreOrderTitle);
    }
    

    @FXML
    void getCarBack_click(ActionEvent event) {
    	moveToWindow(event,CpsGlobals.removeCar,
    			CpsGlobals.removeCarTitle);
    }
    

    @FXML
    void purchaseSubscription_click(ActionEvent event) {
    	moveToWindow(event,CpsGlobals.MonthlySubscriptionOrderWindow,
    			CpsGlobals.MonthlySubscriptionOrderWindowTitle);
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
