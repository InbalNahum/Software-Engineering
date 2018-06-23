/**
 * Sample Skeleton for 'HandleComplaint.fxml' Controller Class
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
import entity.ComplainObject;
import entity.Complaint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.ServerResponse;

public class HandleComplaintController implements Initializable{

	@FXML // fx:id="anchor_pane"
	private AnchorPane anchor_pane; // Value injected by FXMLLoader

	@FXML // fx:id="firstNameId"
	private TableColumn<Complaint, String> firstNameId; // Value injected by FXMLLoader

	@FXML // fx:id="updateid"
	private Button updateid; // Value injected by FXMLLoader

	@FXML // fx:id="descriptionId"
	private TableColumn<Complaint, String> descriptionId; // Value injected by FXMLLoader

	@FXML // fx:id="CarNumberId"
	private TableColumn<Complaint, String> CarNumberId; // Value injected by FXMLLoader

	@FXML // fx:id="actionId"
	private TableColumn<Complaint, String> actionId; // Value injected by FXMLLoader

	@FXML // fx:id="lastNameId"
	private TableColumn<Complaint, String> lastNameId; // Value injected by FXMLLoader

	@FXML // fx:id="CustomerId"
	private TableColumn<Complaint, String> CustomerId; // Value injected by FXMLLoader

	@FXML // fx:id="sendTimeId"
	private TableColumn<Complaint, String> sendTimeId; // Value injected by FXMLLoader

	@FXML // fx:id="tableViewId"
	private TableView<Complaint> tableViewId; // Value injected by FXMLLoader

	@FXML // fx:id="cancelId"
	private Button cancelId; // Value injected by FXMLLoader

	@FXML // fx:id="statusId"
	private TableColumn<Complaint, String> statusId; // Value injected by FXMLLoader

	@FXML
	private TableColumn<Complaint, String> refundId;

	ObservableList<Complaint> complaintData = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.sendCustomerComplaintRequest(requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			for(Object obj : serverResponse.get().getObjects()) {
				ComplainObject ComplainObject = (ComplainObject) obj;
				Complaint complaint = new Complaint(ComplainObject.getFirstName(), ComplainObject.getLastName()
						, ComplainObject.getCustomerId(), ComplainObject.getCarNumber(),
						ComplainObject.getSendTime(), ComplainObject.getDescription(),ComplainObject.getStatus()
						,ComplainObject.getRefund());
				complaintData.add(complaint);
			}
		} catch (IOException | InterruptedException e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.serverIssue);
		}

		dependencyComplainToTable();

		tableViewId.setItems(complaintData);
	}

	private void dependencyComplainToTable() {
		firstNameId.setCellValueFactory(
				new PropertyValueFactory<Complaint,String>("firstName"));
		lastNameId.setCellValueFactory(
				new PropertyValueFactory<Complaint,String>("lastName"));
		CustomerId.setCellValueFactory(
				new PropertyValueFactory<Complaint,String>("customerId"));
		CarNumberId.setCellValueFactory(
				new PropertyValueFactory<Complaint,String>("carNumber"));
		sendTimeId.setCellValueFactory(
				new PropertyValueFactory<Complaint,String>("sendTime"));
		descriptionId.setCellValueFactory(
				new PropertyValueFactory<Complaint,String>("description"));
		statusId.setCellValueFactory(
				new PropertyValueFactory<Complaint,String>("status"));
		actionId.setCellValueFactory(
				new PropertyValueFactory<Complaint,String>("checkbox"));
		refundId.setCellValueFactory(
				new PropertyValueFactory<Complaint,String>("textAera"));
	}

	@FXML
	void btnUpdate(ActionEvent event) {

		for(Complaint complaintRow : complaintData) {
			if(complaintRow.getCheckbox().isSelected()) {
				try {
					FieldValidation.isNumber(complaintRow.getTextField().getText());
					ComplainObject complainObject = new ComplainObject(complaintRow.getCarNumber(),
							complaintRow.getTextField().getText());
					SqlClient sqlClient = SqlClient.getInstance();
					sqlClient.sendTokenRequest();
					int requestToken = WaitToServer.waitForServerToken(sqlClient);
					sqlClient.updateComplaintTable(complainObject, requestToken);
				} catch (Exception e) {
					ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.numberFormatException);

				}
			}
		}

		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}

	@FXML
	void btnCancel(ActionEvent event) {
		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
	}



}
