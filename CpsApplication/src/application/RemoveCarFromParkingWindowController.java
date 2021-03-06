package application;

import java.io.IOException;
import java.util.Optional;

import actors.User;
import actors.User.UserType;
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
/**
 * Window controller for remove car from the parking
 * @author inahum
 *
 */
public class RemoveCarFromParkingWindowController {

    @FXML // fx:id="btn_Cancel"
    private Button btn_Cancel; // Value injected by FXMLLoader

    @FXML // fx:id="btn_RemoveCar"
    private Button btn_RemoveCar; // Value injected by FXMLLoader
/**
 * Remove car from the parking clicked
 * @param event
 */
    @FXML
    void RemoveCar_Click(ActionEvent event) {
    	try {	
			String id = User.getCurrentUser().getUserName();
			String carNumber = User.getCurrentUser().getPassword();	
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.removeCarFromParking(id, carNumber, requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			boolean status = (boolean) serverResponse.get().getObjectAtIndex(0);
			if (!status)
				throw new Exception(CpsGlobals.carDoesntExist);
			ServiceMethods.alertDialog(AlertType.INFORMATION,CpsGlobals.operationSuccess);	
		    Cancel_Click(event);
    	} catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
		}
    }
/**
 * Cancel clicked
 * @param event
 */
    @FXML
    void Cancel_Click(ActionEvent event) {
    	User currentUser = User.getCurrentUser();
    	if(currentUser.getUserType().equals(UserType.subscriber)) {
        	moveToWindow(event,CpsGlobals.subscriberMenuWindow,
        			CpsGlobals.subscriberMenuWindowTitle);
    	}
    	else {
        	moveToWindow(event,CpsGlobals.casualCustomerMenuWindow,
        			CpsGlobals.casualCustomerMenuWindowTitle);
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
}
