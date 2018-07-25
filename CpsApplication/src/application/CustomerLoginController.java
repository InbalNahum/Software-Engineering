/**
 * Sample Skeleton for 'CustomerLogin.fxml' Controller Class
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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.ServerResponse;
/**
 * Window controller for customer login
 * @author inahum
 *
 */
public class CustomerLoginController implements Initializable {

	@FXML // fx:id="anchor_pane"
	private AnchorPane anchor_pane; // Value injected by FXMLLoader

	@FXML // fx:id="id_tf"
	private TextField id_tf; // Value injected by FXMLLoader

	@FXML // fx:id="btn_cancel"
	private Button btn_cancel; // Value injected by FXMLLoader

	@FXML // fx:id="carNumber_tf"
	private PasswordField carNumber_tf; // Value injected by FXMLLoader

	@FXML // fx:id="signIn_btn"
	private Button signIn_btn; // Value injected by FXMLLoader

	private String userId;
	private String userCarNum;

	/**
	 * @param Location url and resource bundle
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources){

	}
/**
 * Return user id
 * @return
 */
	public String getUserId() {
		return userId;
	}
/**
 * Set user id
 * @param userId
 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
/**
 * Get user car number
 * @return
 */
	public String getUserCarNum() {
		return userCarNum;
	}
/**
 * Set user car number
 * @param userCarNum
 */
	public void setUserCarNum(String userCarNum) {
		this.userCarNum = userCarNum;
	}
/**
 * Customer clicked on login 
 * @param event
 */
	@FXML
	void sigIn_click(ActionEvent event) {
		String customerId = id_tf.getText();
		String carNum = carNumber_tf.getText();

		try {
			FieldValidation.idValidation(customerId);
			FieldValidation.carNumberValidation(carNum);
			userId = customerId;
			userCarNum = carNum;
			ServerResponse serverResponse = customerAuthentication(customerId,carNum);
			boolean isSubscriber = (boolean) serverResponse.getObjectAtIndex(0);
			if(isSubscriber) {
				User.initalizeUser(userId, userCarNum, UserType.subscriber);
				if(!isValidUser(User.getCurrentUser())) {
					ServiceMethods.alertDialog(AlertType.ERROR,
							CpsGlobals.userAlreadyConnected);
					return;
				}
				String date = (String) serverResponse.getObjectAtIndex(1);
				if(date.equals(CpsGlobals.inTokef)) {
					moveToWindow(event, CpsGlobals.subscriberMenuWindow,
							CpsGlobals.subscriberMenuWindowTitle);
				}
				else {
					moveToWindow(event,CpsGlobals.subscriptionExpiredWindow,
							CpsGlobals.subscriptionExpiredTitle);
				}
			}
			else {
				User.initalizeUser(userId, userCarNum, UserType.casualCustomer);
				if(!isValidUser(User.getCurrentUser())) {
					ServiceMethods.alertDialog(AlertType.ERROR,
							CpsGlobals.userAlreadyConnected);
					return;
				}
				moveToWindow(event, CpsGlobals.casualCustomerMenuWindow,
						CpsGlobals.casualCustomerMenuWindowTitle);
			}
		}
		catch(Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
		}


	}

	private ServerResponse customerAuthentication(String customerId, String carNum) throws InterruptedException, IOException {
		SqlClient sqlClient = SqlClient.getInstance();
		sqlClient.sendTokenRequest();
		int requestToken = WaitToServer.waitForServerToken(sqlClient);
		sqlClient.customerAuthentication(customerId,carNum,requestToken);
		Optional<ServerResponse> serverResponse = ServiceMethods.waitToServerResponse(sqlClient,requestToken);
		return serverResponse.get();
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
	
	private boolean isValidUser(User user) throws IOException, InterruptedException {
		SqlClient sqlClient = SqlClient.getInstance();
		sqlClient.sendTokenRequest();
		int token = WaitToServer.waitForServerToken(sqlClient);
		sqlClient.isValidUser(user,token);
		Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, token);
	    String result = (String) serverResponse.get().getObjectAtIndex(0);
	    if(result.equals(CpsGlobals.operationSuccess)) {
	    	return true;
	    }
	    else {
	    	return false;
	    }
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
}
