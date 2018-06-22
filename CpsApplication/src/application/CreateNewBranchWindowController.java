package application;

import java.util.Optional;

import client.SqlClient;
import common.FieldValidation;
import common.ServiceMethods;
import entity.Branch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import parkingLogic.BranchPark;
import server.ServerResponse;

public class CreateNewBranchWindowController {

    @FXML // fx:id="tf_Id"
    private TextField tf_Id; // Value injected by FXMLLoader

    @FXML // fx:id="tf_Name"
    private TextField tf_Name; // Value injected by FXMLLoader

    @FXML // fx:id="tf_FirstWidth"
    private TextField tf_FirstWidth; // Value injected by FXMLLoader

    @FXML // fx:id="tf_SecondWidth"
    private TextField tf_SecondWidth; // Value injected by FXMLLoader

    @FXML // fx:id="tf_ThirdWidth"
    private TextField tf_ThirdWidth; // Value injected by FXMLLoader

    @FXML // fx:id="btn_CreateBranch"
    private Button btn_CreateBranch; // Value injected by FXMLLoader

    @FXML // fx:id="btn_Cancel"
    private Button btn_Cancel; // Value injected by FXMLLoader

    @FXML
    void Cancel_Click(ActionEvent event) {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();    	
    }

    @FXML
    void CreateBranch_Click(ActionEvent event) {
    	try {
    		isValidInput();
    		int id = Integer.parseInt(tf_Id.getText());
    		String name = tf_Name.getText();
    		int firstWidth = Integer.parseInt(tf_FirstWidth.getText());
    		int secondWidth = Integer.parseInt(tf_SecondWidth.getText());
    		int thirdWidth = Integer.parseInt(tf_ThirdWidth.getText());
    		int columns[] = {firstWidth, secondWidth, thirdWidth};
    		Branch branch = new Branch(id,name, new BranchPark(columns));
    		SqlClient sqlClient = SqlClient.getInstance();
            sqlClient.sendTokenRequest();
            int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.addBranch(branch, requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			ServiceMethods.alertFeedback(serverResponse,event);
		} catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
			return;
		}
    }
    
	private void isValidInput() throws Exception {
		FieldValidation.branchIdValidation(tf_Id.getText());
		FieldValidation.nameValidation(tf_Name.getText());
		FieldValidation.widthValidation(tf_FirstWidth.getText());
		FieldValidation.widthValidation(tf_SecondWidth.getText());
		FieldValidation.widthValidation(tf_ThirdWidth.getText());
	}

}
