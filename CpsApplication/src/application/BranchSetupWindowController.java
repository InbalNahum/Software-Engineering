package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import actors.User;
import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
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
		moveToWindow(event,CpsGlobals.employeeMenuWindow,
				CpsGlobals.employeeMenuWindowTitle);	    }

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
		moveToWindow(event,CpsGlobals.employeeMenuWindow,
				CpsGlobals.employeeMenuWindowTitle);	
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
