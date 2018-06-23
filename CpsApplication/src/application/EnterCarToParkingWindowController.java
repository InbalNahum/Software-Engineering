package application;

import java.util.Optional;

import client.SqlClient;
import common.FieldValidation;
import common.ServiceMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import server.ServerResponse;

public class EnterCarToParkingWindowController {

    @FXML // fx:id="btn_Cancel"
    private Button btn_Cancel; // Value injected by FXMLLoader

    @FXML // fx:id="btn_EnterCar"
    private Button btn_EnterCar; // Value injected by FXMLLoader

    @FXML
    void EnterCar_Click(ActionEvent event) {
    	try {
			String id = User.getCurrentUser().getUserName();
			String carNumber = User.getCurrentUser().getPassword();		
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.EnterCarToParking(id, carNumber, requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			String message = (String) serverResponse.get().getObjectAtIndex(0);
			ServiceMethods.alertDialog(AlertType.INFORMATION, message);
		} catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
		}
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void Cancel_Click(ActionEvent event) {
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}