package application;

import java.util.Optional;

import actors.User;
import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import server.ServerResponse;

public class RemoveCarFromParkingWindowController {

    @FXML // fx:id="btn_Cancel"
    private Button btn_Cancel; // Value injected by FXMLLoader

    @FXML // fx:id="btn_RemoveCar"
    private Button btn_RemoveCar; // Value injected by FXMLLoader

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
