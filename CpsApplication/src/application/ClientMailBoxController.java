package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ClientRequest;
import client.SqlClient;
import common.CpsGlobals;
import common.ServiceMethods;
import entity.Complaint;
import entity.MessageForUser;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import server.ServerResponse;

public class ClientMailBoxController implements Initializable {

    @FXML
    private TableColumn<MessageForUser, String> message_number;

    @FXML
    private TableColumn<MessageForUser, String> message_details;

    @FXML
    private TableView<MessageForUser> table;
    
    //TODO ydanan - get the details from the last window
    String currentUserId = "999999999";
    String currentUserCarNum = "9999999";
    
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
		table.setItems(userMessages);

	}
	
	private ServerResponse getMessagesFromServer() throws IOException, InterruptedException {
		SqlClient sqlClient = SqlClient.getInstance();
		sqlClient.sendTokenRequest();
		int requestToken = WaitToServer.waitForServerToken(sqlClient);
		sqlClient.sendUserMessagesRequest(currentUserId, currentUserCarNum, requestToken);
        Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
	    return serverResponse.get();
	}

	private void inializeColumns() {
		message_number.setCellValueFactory(
				new PropertyValueFactory<MessageForUser,String>("messageNumber"));
		message_details.setCellValueFactory(
				new PropertyValueFactory<MessageForUser,String>("messageDetails"));
	}

}