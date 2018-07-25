/**
 * Sample Skeleton for 'LoginWindow.fxml' Controller Class
 */

package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import actors.User;
import actors.User.UserType;
import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.ServerResponse;
/**
 * Window controller for login 
 * @author inahum
 *
 */
public class LoginWindowController implements Initializable {

	@FXML // fx:id="anchor_pane"
	private AnchorPane anchor_pane; // Value injected by FXMLLoader

	@FXML // fx:id="btn_cancel"
	private Button btn_cancel; // Value injected by FXMLLoader

	@FXML // fx:id="tf_password"
	private PasswordField tf_password; // Value injected by FXMLLoader

	@FXML // fx:id="tf_UserName"
	private TextField tf_UserName; // Value injected by FXMLLoader

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
/**
 * The user clicked on sigIn
 * @param event
 */
	@FXML
	void sigIn_click(ActionEvent event) {
		String userName = tf_UserName.getText();
		String password = tf_password.getText();

		try {
			FieldValidation.idValidation(userName);
			User.initalizeUser(userName, password, UserType.employee);
			ServerResponse serverResponse = employeeAuthentication(userName,password);
			boolean isEmployee = (boolean) serverResponse.getObjectAtIndex(0);
			if(isEmployee) {
				String position = (String) serverResponse.getObjectAtIndex(1);
				if(position.equals(CpsGlobals.CEO))
					User.getCurrentUser().setUserType(UserType.manager);
				
				if(position.equals(CpsGlobals.BranchManager))
					User.getCurrentUser().setUserType(UserType.manager);
				
				if(position.equals(CpsGlobals.CustomerServiceWorker))
					User.getCurrentUser().setUserType(UserType.customerService);
				
				if(position.equals(CpsGlobals.BranchWorker))
					User.getCurrentUser().setUserType(UserType.branchEmployee);
				
				moveToWindow(event,CpsGlobals.employeeMenuWindow,
						CpsGlobals.employeeMenuWindowTitle);	
			}
			else {
				ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.wrongUserOrPassword);
			}
		} catch (InterruptedException e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.somethingGoWrone);
		} catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
		}
	}

	private ServerResponse employeeAuthentication(String userName, String password) throws InterruptedException {
		Optional<ServerResponse> serverResponse = null;
		try {
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.employeeAuthentication(userName,password,requestToken);
		    serverResponse = ServiceMethods.waitToServerResponse(sqlClient,requestToken);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return serverResponse.get();
	}
/**
 * Cancel clicked 
 * @param event
 */
	@FXML
	void cancel_click(ActionEvent event) {
		moveToWindow(event,CpsGlobals.welcomeWindow,
				CpsGlobals.WelcomeWindowTitle);	
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
