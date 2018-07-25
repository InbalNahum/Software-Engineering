/**
 * Sample Skeleton for 'UpdatePriceListWindow.fxml' Controller Class
 */

package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import entity.PriceList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import server.ServerResponse;
/**
 * Window controller for update price list
 * Access only management 
 * @author inahum
 *
 */
public class UpdatePriceListWindowController implements Initializable{

	@FXML // fx:id="tf_Subscription"
	private TextField tf_Subscription; // Value injected by FXMLLoader

	@FXML // fx:id="btn_Cancel"
	private Button btn_Cancel; // Value injected by FXMLLoader

	@FXML // fx:id="tf_preOrder"
	private TextField tf_preOrder; // Value injected by FXMLLoader

	@FXML // fx:id="tf_casual"
	private TextField tf_casual; // Value injected by FXMLLoader
/**
 * Cancel clicked
 * @param event
 */
	@FXML
	void Cancel_Clicked(ActionEvent event) {
		moveToWindow(event,CpsGlobals.employeeMenuWindow,
				CpsGlobals.employeeMenuWindowTitle);		
	}
/**
 * Management update the price list
 * @param event
 */
	@FXML
	void Updet_Clicked(ActionEvent event) {

		String casualParking = tf_casual.getText();
		String preOrderParking = tf_preOrder.getText();
		String monthlySubscription = tf_Subscription.getText();

		try {

			FieldValidation.isNumber(casualParking);
			FieldValidation.isNumber(preOrderParking);
			FieldValidation.isNumber(monthlySubscription);
			PriceList priceList = new PriceList(casualParking, preOrderParking, monthlySubscription);
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.updatePriceListTable(priceList, requestToken);

		}catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.numberFormatException);
		}

		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
		ServiceMethods.alertDialog(AlertType.INFORMATION, CpsGlobals.permisionFromManager);
		moveToWindow(event,CpsGlobals.employeeMenuWindow,
				CpsGlobals.employeeMenuWindowTitle);	

	}
/**
 * @param Location url and resource bundle
 * Shows current prices to the manager
 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<String> priceList = new ArrayList<String>();
		int i = 0;
		try {
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.sendPriceListRequest(requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			for(Object obj : serverResponse.get().getObjects()) {
				String price = (String) obj;
				priceList.add(i, price);
				i++;
			}
			tf_casual.setText(priceList.get(0));
			tf_preOrder.setText(priceList.get(1));
			tf_Subscription.setText(priceList.get(2));

		} catch (IOException | InterruptedException e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.serverIssue);		
		}
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
