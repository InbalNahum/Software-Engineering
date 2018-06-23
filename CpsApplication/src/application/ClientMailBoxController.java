package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.User.UserType;
import client.SqlClient;
import common.CpsGlobals;
import common.ServiceMethods;
import entity.MessageForUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.ServerResponse;

public class ClientMailBoxController implements Initializable {

    @FXML
    private TableColumn<MessageForUser, String> message_number;

    @FXML
    private TableColumn<MessageForUser, String> message_details;

    @FXML
    private TableView<MessageForUser> table;
        
	ObservableList<MessageForUser> userMessages = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ServerResponse messages = null;
		try {
			 messages = getMessagesFromServer();
			 int messageIndex = 1;
			 for(Object obj : messages.getObjects()) {
				 String message = (String) obj;
				 userMessages.add(new MessageForUser(messageIndex++,message));
			 }
		} catch (IOException | InterruptedException e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.serverIssue);
		}
		inializeColumns();
		if(userMessages.size()!=0) {
			table.setItems(userMessages); 

		}
	}
	
	private ServerResponse getMessagesFromServer() throws IOException, InterruptedException {
		SqlClient sqlClient = SqlClient.getInstance();
		sqlClient.sendTokenRequest();
		int requestToken = WaitToServer.waitForServerToken(sqlClient);
		User currentUser = User.getCurrentUser();
		sqlClient.sendUserMessagesRequest(currentUser.getUserName(), currentUser.getPassword(), requestToken);
        Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
	    return serverResponse.get();
	}

	private void inializeColumns() {
		message_number.setCellValueFactory(
				new PropertyValueFactory<MessageForUser,String>("messageNumber"));
		message_details.setCellValueFactory(
				new PropertyValueFactory<MessageForUser,String>("messageDetails"));
	}
	
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
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
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