/**
 * Sample Skeleton for 'WelcomeWindow.fxml' Controller Class
 */

package application;

import java.io.IOException;
import java.net.URL;

import common.CpsGlobals;
import common.ServiceMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WelcomeWindowController {

	@FXML // fx:id="customer_btn"
	private Button customer_btn; // Value injected by FXMLLoader

	@FXML // fx:id="employee_btn"
	private Button employee_btn; // Value injected by FXMLLoader

	@FXML
	void customer_click(ActionEvent event) {
		try {		

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerLogin.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle(CpsGlobals.customerLoginTitle);
			stage.setScene(new Scene(root1));  
			stage.show();
			((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
		}
		catch(IOException e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.failToLoadWindow);
		}
	}

	@FXML
	void employee_click(ActionEvent event) {
		try {		
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle(CpsGlobals.employeeLoginTitle);
			stage.setScene(new Scene(root1));  
			stage.show();
			((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
		}
		catch(IOException e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.failToLoadWindow);
		}
	}

}
