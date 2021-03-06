package application;

import java.io.IOException;
import java.util.Optional;

import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import entity.Branch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import parkingLogic.BranchPark;
import server.ServerResponse;
/**
 * Window controller for create a new branch 
 * @author inahum
 *
 */
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

	/**
	 * Cancel clicked
	 * @param event
	 */
	@FXML
	void Cancel_Click(ActionEvent event) {
		moveToWindow(event,CpsGlobals.employeeMenuWindow,
				CpsGlobals.employeeMenuWindowTitle);	    
	}
/**
 * Employee create a new branch 
 * @param event
 */
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
		moveToWindow(event,CpsGlobals.employeeMenuWindow,
				CpsGlobals.employeeMenuWindowTitle);	
	}

	private void isValidInput() throws Exception {
		FieldValidation.branchIdValidation(tf_Id.getText());
		FieldValidation.nameValidation(tf_Name.getText());
		FieldValidation.widthValidation(tf_FirstWidth.getText());
		FieldValidation.widthValidation(tf_SecondWidth.getText());
		FieldValidation.widthValidation(tf_ThirdWidth.getText());
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
