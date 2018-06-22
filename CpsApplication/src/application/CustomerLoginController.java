/**
 * Sample Skeleton for 'CustomerLogin.fxml' Controller Class
 */

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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.ServerResponse;

public class CustomerLoginController {

	@FXML // fx:id="anchor_pane"
	private AnchorPane anchor_pane; // Value injected by FXMLLoader

	@FXML // fx:id="id_tf"
	private TextField id_tf; // Value injected by FXMLLoader

	@FXML // fx:id="btn_cancel"
	private Button btn_cancel; // Value injected by FXMLLoader

	@FXML // fx:id="carNumber_tf"
	private PasswordField carNumber_tf; // Value injected by FXMLLoader

	@FXML // fx:id="signIn_btn"
	private Button signIn_btn; // Value injected by FXMLLoader

	@FXML
	void sigIn_click(ActionEvent event) {
		String customerId = id_tf.getText();
		String carNum = carNumber_tf.getText();

		try {
			FieldValidation.idValidation(customerId);
			FieldValidation.carNumberValidation(carNum);
			if(customerAuthentication(customerId,carNum)) {
				moveToWindow(event, CpsGlobals.subscriberMenuWindow,
						CpsGlobals.subscriberMenuWindowTitle);
 			}
			else {
				moveToWindow(event, CpsGlobals.casualCustomerMenuWindow,
						CpsGlobals.casualCustomerMenuWindowTitle);
			}
		}
		catch(Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
		}


	}

	private boolean customerAuthentication(String customerId, String carNum) throws InterruptedException, IOException {
		boolean isSubscriber = false;
			SqlClient sqlClient = SqlClient.getInstance();
            sqlClient.sendTokenRequest();
            int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.customerAuthentication(customerId,carNum,requestToken);
			Optional<ServerResponse> serverResponse = ServiceMethods.waitToServerResponse(sqlClient,requestToken);
			isSubscriber = (boolean) serverResponse.get().getObjectAtIndex(0);
		return isSubscriber;
	}
	
	private void moveToWindow(ActionEvent event,String windowName,String windowTitle) {
    	try {		
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(windowName));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Stage stage = new Stage();
    		stage.setTitle(windowTitle);
    		stage.setScene(new Scene(root1));  
    		stage.show();
    		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    	}
    	catch(IOException e) {
    		ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.failToLoadWindow);
    	}
	}

	@FXML
	void cancel_click(ActionEvent event) {
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}

}
