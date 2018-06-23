package application;


import java.io.IOException;
import java.util.Optional;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.ServerResponse;

public class EnterCarToParkingWithCheckWindowController {

    @FXML // fx:id="btn_Cancel"
    private Button btn_Cancel; // Value injected by FXMLLoader

    @FXML // fx:id="btn_EnterCar"
    private Button btn_EnterCar; // Value injected by FXMLLoader

    @FXML // fx:id="tf_Id"
    private TextField tf_Id; // Value injected by FXMLLoader

    @FXML // fx:id="tf_CarNumber"
    private TextField tf_CarNumber; // Value injected by FXMLLoader

    @FXML
    void EnterCar_Click(ActionEvent event) {
    	try {
    		isValidInput();		
			String id = tf_Id.getText();
			String carNumber = tf_CarNumber.getText();		
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.EnterCarToParkingWithCheck(id, carNumber, requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			boolean status = (boolean) serverResponse.get().getObjectAtIndex(0);
			if (!status)
				throw new Exception("Your Order doesn't exist");
			String message = (String) serverResponse.get().getObjectAtIndex(1);
			if(!message.equals(""))
				ServiceMethods.alertDialog(AlertType.INFORMATION,message);
			ServiceMethods.alertDialog(AlertType.INFORMATION,CpsGlobals.operationSuccess);	
		    Cancel_Click(event);
    	} catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
		}
    }

    @FXML
    void Cancel_Click(ActionEvent event) {
        moveToWindow(event, CpsGlobals.casualCustomerMenuWindow,
        		   CpsGlobals.casualCustomerMenuWindowTitle);
    }
    
	private void isValidInput() throws Exception {
		FieldValidation.idValidation(tf_Id.getText());
		FieldValidation.carNumberValidation(tf_CarNumber.getText());
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
