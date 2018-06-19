/**
 * Sample Skeleton for 'LoginWindow.fxml' Controller Class
 */

package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.ServerResponse;
import token.TokenProvider;

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
			if(employeeAuthentication(userName,password)) {
               //TODO - ydanan move to the employee operations window
			}
			else {
				ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.wrongUserOrPassword);
 			}
		} catch (InterruptedException e) {
           ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.somethingGoWrone);
		} catch (Exception e) {
	           ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.notValidId);
		}
	}

	private boolean employeeAuthentication(String userName, String password) throws InterruptedException {
		boolean isAuth = false;
		try {
			SqlClient sqlClient = SqlClient.getInstance();
            sqlClient.sendTokenRequest();
            int requestToken = waitForServerToken(sqlClient);
			sqlClient.employeeAuthentication(userName,password,requestToken);
			Optional<ServerResponse> serverResponse = waitToServerResponse(sqlClient,requestToken);
			isAuth = (boolean) serverResponse.get().getObjectAtIndex(0);
		} catch (IOException e) {
			isAuth = false;
			e.printStackTrace();
		}
		return isAuth;
	}

	private Optional<ServerResponse> waitToServerResponse(SqlClient sqlClient,
			int requestToken) {
		Optional<ServerResponse> toRet;
		
		do {
			toRet = sqlClient.getResponseByToken(requestToken);
		} while (!toRet.isPresent());
		return toRet;
	}
	
	private int waitForServerToken(SqlClient sqlClient) throws InterruptedException {
		Optional<Integer> toRet;
		
		do {
			toRet = sqlClient.fetchToken();
		} while (!toRet.isPresent());
		return toRet.get();
	}

	@FXML
	void cancel_click(ActionEvent event) {
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}

}
