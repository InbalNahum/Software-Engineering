package application;

import java.io.IOException;

import common.CpsGlobals;
import common.ServiceMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
/**
 * Window controller for a customer whose subscription has expired
 * @author inahum
 *
 */
public class SubscriptionExpiredWindowController {
/**
 * Clicked on renew
 * The customer wont to renew the subscription
 * @param event
 */
	@FXML
	void renew_click(ActionEvent event) {
		moveToWindow(event,
				CpsGlobals.renewSubscriptionWindow,
				CpsGlobals.RenewMonthlySubscriptionWindowTitle);
	}
/**
 * MybeLater clicked
 * The customer wont to renew the subscription later
 * @param event
 */
	@FXML
	void mybeLater_click(ActionEvent event) {
		moveToWindow(event,
				CpsGlobals.casualCustomerMenuWindow,
				CpsGlobals.casualCustomerMenuWindowTitle);
	}

	private void moveToWindow(ActionEvent event,String windowName,String windowTitle) {
		try {		
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(windowName));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle(windowTitle);
			stage.setScene(new Scene(root1));  
			stage.show();
			((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
		}
		catch(IOException e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.failToLoadWindow);
		}
	}

}
