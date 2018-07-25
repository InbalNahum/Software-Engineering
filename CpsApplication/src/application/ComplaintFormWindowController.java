package application;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import actors.User;
import actors.User.UserType;
import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import entity.CustomerComplaint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.ServerResponse;

/**
 * Window controller for complain form
 * @author inahum
 *
 */
public class ComplaintFormWindowController implements Initializable{

	@FXML
	private AnchorPane anchor_pane;

	@FXML
	private TextField tf_firstName;

	@FXML
	private Button btn_cancel;

	@FXML
	private TextField tf_lastName;

	@FXML
	private TextArea ta_description;

	@FXML
	private TextField tf_email;
	private String id;
	private String carNumber;
	
	/*
	 * Send new complain clicked 
	 */
	@FXML
	void send_click(ActionEvent event) {
		try {
			isValidInput();
			String firstName = tf_firstName.getText();
			String lastName = tf_lastName.getText();
			String email = tf_email.getText();
			String description = ta_description.getText();
			CustomerComplaint customerComplaint = new CustomerComplaint(firstName, lastName, 
					Integer.parseInt(id), Integer.parseInt(carNumber), description, new Date(), email);
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.addComplain(customerComplaint,requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			ServiceMethods.alertFeedback(serverResponse,event);
            cancel_click(event);
		}catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
			return;
		}
	}
/**
 * Cancel clicked
 * @param event
 */
	@FXML
	void cancel_click(ActionEvent event) {
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

	private void isValidInput() throws Exception {
		FieldValidation.nameValidation(tf_firstName.getText());
		FieldValidation.nameValidation(tf_lastName.getText());
		FieldValidation.idValidation(id);
		FieldValidation.carNumberValidation(carNumber);	
		FieldValidation.emailValidation(tf_email.getText());
		FieldValidation.nameValidation(ta_description.getText());

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
/**
 * @param Location url and resource bundle
 * update the current user & user id & car number
 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		User user = User.getCurrentUser();
		id = user.getUserName();
		carNumber = user.getPassword();
	}
}
