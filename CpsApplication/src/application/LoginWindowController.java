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

	@FXML
	void sigIn_click(ActionEvent event) {
		String userName = tf_UserName.getText();
		String password = tf_password.getText();

		try {
			FieldValidation.idValidation(userName);
			User.initalizeUser(userName, password, UserType.employee);
			if(employeeAuthentication(userName,password)) {
				moveToWindow(event,CpsGlobals.employeeMenuWindow,
						CpsGlobals.employeeMenuWindowTitle);			}
			else {
				ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.wrongUserOrPassword);
			}
		} catch (InterruptedException e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.somethingGoWrone);
		} catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
		}
	}

	private boolean employeeAuthentication(String userName, String password) throws InterruptedException {
		boolean isAuth = false;
		try {
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.employeeAuthentication(userName,password,requestToken);
			Optional<ServerResponse> serverResponse = ServiceMethods.waitToServerResponse(sqlClient,requestToken);

			isAuth = (boolean) serverResponse.get().getObjectAtIndex(0);
		} catch (IOException e) {
			isAuth = false;
			e.printStackTrace();
		}
		return isAuth;
	}

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
