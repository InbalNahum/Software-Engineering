package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import server.ServerResponse;

public class BranchSetupWindowController implements Initializable {

    @FXML // fx:id="btn_Cancel"
    private Button btn_Cancel; // Value injected by FXMLLoader

    @FXML // fx:id="cb_BranchName"
    private ComboBox<String> cb_BranchName; // Value injected by FXMLLoader

    @FXML // fx:id="btn_SetSetup"
    private Button btn_SetSetup; // Value injected by FXMLLoader

    @FXML
    void Cancel_Click(ActionEvent event) {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void SetSetup_Click(ActionEvent event) {
    	try {
    		isValidInput();
			String name = cb_BranchName.getValue();			
    		SqlClient sqlClient = SqlClient.getInstance();
        	sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.setBranchSetup(name, requestToken);
			Optional<ServerResponse> serverResponse = 
					ServiceMethods.waitToServerResponse(sqlClient,requestToken);
			ServiceMethods.alertFeedback(serverResponse,event);
		} catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
		}
    }
    
	private void isValidInput() throws Exception {
		FieldValidation.branchNameValidation(cb_BranchName.getValue());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> comboBoxList = FXCollections.observableArrayList();
		try {
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.sendBranchByIdRequest(User.getCurrentUser().getUserName(), requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			for(Object obj : serverResponse.get().getObjects()) {
				String branchName = (String) obj;
				comboBoxList.add(branchName);
			}
		} catch (IOException | InterruptedException e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.serverIssue);
		}
		cb_BranchName.setItems(comboBoxList);			
	}

}
