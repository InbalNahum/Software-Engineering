package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import actors.User;
import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import entity.BranchParkParameters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import server.ServerResponse;

public class SaveParkingWindowController implements Initializable{

	private BranchParkParameters parameters = null;
	
    @FXML // fx:id="cb_Branch"
    private ComboBox<String> cb_Branch; // Value injected by FXMLLoader

    @FXML // fx:id="cb_floor"
    private ComboBox<Integer> cb_floor; // Value injected by FXMLLoader

    @FXML // fx:id="spinner_Row"
    private Spinner<Integer> spinner_Row; // Value injected by FXMLLoader

    @FXML // fx:id="spinner_Column"
    private Spinner<Integer> spinner_Column; // Value injected by FXMLLoader

    @FXML // fx:id="btn_UpdateParking"
    private Button btn_UpdateParking; // Value injected by FXMLLoader

    @FXML // fx:id="btn_Cancel"
    private Button btn_Cancel; // Value injected by FXMLLoader

    @FXML
    void Cancel_Click(ActionEvent event) {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void UpdateParking_Click(ActionEvent event) {
    	try {
    		isValidInput();
    		String name = cb_Branch.getValue();
    		int floor = cb_floor.getValue()-1;
    		int row = spinner_Row.getValue()-1;
    		int column[] = new int[1];
    		column[0] = spinner_Column.getValue()-1;  			
    		SqlClient sqlClient = SqlClient.getInstance();
        	sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.setSavedParking(name, new BranchParkParameters(floor, row, column), requestToken);
			Optional<ServerResponse> serverResponse = ServiceMethods.waitToServerResponse(sqlClient,requestToken);
			ServiceMethods.alertFeedback(serverResponse,event);
		} catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
		}   	
    }
    

    @FXML
    void BranchName_Selected(ActionEvent event) {
    	try {
    		String name = cb_Branch.getValue();
        	SqlClient sqlClient = SqlClient.getInstance();
        	sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.getBranchParkParameters(name,requestToken);
			Optional<ServerResponse> serverResponse = ServiceMethods.waitToServerResponse(sqlClient,requestToken);
			parameters = (BranchParkParameters) serverResponse.get().getObjectAtIndex(0);
			ObservableList<Integer> comboBoxList = FXCollections.observableArrayList();
			for(int i=0; i<parameters.getFloor(); i++) {
				comboBoxList.add(i+1);
			}
			cb_floor.setItems(comboBoxList);		
		} catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
		}
    }
	
    @FXML
    void Floor_Selected(ActionEvent event) {
    	try {
    		setBranchParkRaw(spinner_Row);
    		if(cb_floor.getValue() == null)
    			setBranchParkColumn(spinner_Column,1); 	
    		else 
    			setBranchParkColumn(spinner_Column,cb_floor.getValue());
		} catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
		}
    }
	
	private void setBranchParkRaw(Spinner<Integer> spinner) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory
                (1, parameters.getRaw(), parameters.getRaw()/2);
        spinner.setValueFactory(valueFactory);
	}
	
	private void setBranchParkColumn(Spinner<Integer> spinner, int floor) {	
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory
                (1, parameters.getColumn()[floor], parameters.getColumn()[floor]/2);
        spinner.setValueFactory(valueFactory);
	}
	
	private void isValidInput() throws Exception {
		FieldValidation.branchNameValidation(cb_Branch.getValue());
		FieldValidation.branchFloorValidation(cb_floor.getValue());
		FieldValidation.rawSpinnerValidation(spinner_Row.getValue());
		FieldValidation.columnSpinnerValidation(spinner_Column.getValue());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> comboBoxList = FXCollections.observableArrayList();
		try {
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.sendBranchByIdRequest(User.getCurrentUser().getUserName(), requestToken);
			Optional<ServerResponse> serverResponse = WaitToServer.waitToServerResponse(sqlClient, requestToken);
			for(Object obj : serverResponse.get().getObjects()) {
				String branchName = (String) obj;
				comboBoxList.add(branchName);
			}
		} catch (IOException | InterruptedException e) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.serverIssue);
		}
		cb_Branch.setItems(comboBoxList);		
	}
}
