/**
 * Sample Skeleton for 'ServerConfig.fxml' Controller Class
 */

package application;

import java.io.IOException;

import common.CpsGlobals;
import common.ServiceMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
/**
 * Window controller for server configuration 
 * @author inahum
 *
 */
public class ServerConfigController {

	@FXML // fx:id="port_tf"
	private TextField port_tf; // Value injected by FXMLLoader

	@FXML // fx:id="ip_tf"
	private TextField ip_tf; // Value injected by FXMLLoader
/**
 * Set clicked 
 * Update the IP address & port number for connection with the server 
 * @param event
 */
	@FXML
	void set_click(ActionEvent event) {
		String ip = ip_tf.getText();
		String port = port_tf.getText();
		IPAddressValidator ipValidator = new IPAddressValidator();
		try {
			if(ipValidator.validate(ip)) {
				int integerPort = Integer.parseInt(port);
				CpsGlobals.port = integerPort;
				CpsGlobals.host = ip;
				moveToWindow(event, CpsGlobals.welcomeWindow,
						CpsGlobals.WelcomeWindowTitle);
			}
			else {
				ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.invalidIp);
			}
		}
		catch(NumberFormatException e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.invalidPort);
		}
	}
/**
 *Default clicked
 *Default IP & port number
 * @param event
 */
	@FXML
	void default_click(ActionEvent event) {
		moveToWindow(event, CpsGlobals.welcomeWindow,
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
