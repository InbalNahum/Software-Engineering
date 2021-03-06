package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import client.SqlClient;
import common.CpsGlobals;
import common.FieldValidation;
import common.ServiceMethods;
import common.CpsGlobals.parkingState;
import entity.BranchStateRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import parkingLogic.BranchParkState;
import server.ServerResponse;
/**
 * Window controller for show branch state
 * @author inahum
 *
 */
public class ShowBranchStateWindowController implements Initializable {

	@FXML // fx:id="btn_GetBranchState"
	private Button btn_GetBranchState; // Value injected by FXMLLoader

	@FXML // fx:id="btn_Cancel"
	private Button btn_Cancel; // Value injected by FXMLLoader

	@FXML // fx:id="canvasOne"
	private Canvas canvasOne; // Value injected by FXMLLoader

	@FXML // fx:id="canvasTwo"
	private Canvas canvasTwo; // Value injected by FXMLLoader

	@FXML // fx:id="canvasThree"
	private Canvas canvasThree; // Value injected by FXMLLoader

	@FXML // fx:id="cb_Branch"
	private ComboBox<String> cb_Branch; // Value injected by FXMLLoader

	/**
	 * @param Location url and resource bundle
	 * Update branch list from the server 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> comboBoxList = FXCollections.observableArrayList();
		try {
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.sendBranchListRequest(requestToken);
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
/**
 * The user want to see the branch state 
 * @param event
 */
	@FXML
	void GetBranchState_Click(ActionEvent event) {
		try {
			isValidInput();
			String name = cb_Branch.getValue();
			BranchStateRequest request = new BranchStateRequest(name);
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int requestToken = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.getBranchState(request,requestToken);
			Optional<ServerResponse> serverResponse = ServiceMethods.waitToServerResponse(sqlClient,requestToken);
			BranchParkState state = (BranchParkState) serverResponse.get().getObjectAtIndex(0);
			GraphicsContext gc = canvasOne.getGraphicsContext2D();
			drawParkingState(gc, state.getFloorOne(), state.getRows(), state.getColumns()[0]);	
			gc = canvasTwo.getGraphicsContext2D();
			drawParkingState(gc, state.getFloorTwo(), state.getRows(), state.getColumns()[1]);		
			gc = canvasThree.getGraphicsContext2D();
			drawParkingState(gc, state.getFloorThree(), state.getRows(), state.getColumns()[2]);

		} catch (Exception e) {
			ServiceMethods.alertDialog(AlertType.ERROR, e.getMessage());
		}	

	}
/**
 * Cancel clicked
 * @param event
 */
	@FXML
	void Cancel_Click(ActionEvent event) {
		moveToWindow(event,CpsGlobals.employeeMenuWindow,
				CpsGlobals.employeeMenuWindowTitle);	    
	}
/**
 * Draw the parking state 
 * @param gc
 * @param floor
 * @param rows
 * @param columns
 */
	public void drawParkingState(GraphicsContext gc, parkingState[][] floor, int rows, int columns) {
		int xAxis = 30, yAxis = 25;
		gc.clearRect(0, 0, 276, 121);
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				if(floor[i][j] == parkingState.available)
					gc.setFill(Color.GREENYELLOW);
				if(floor[i][j] == parkingState.unAvailable)
					gc.setFill(Color.RED);
				if(floor[i][j] == parkingState.outOfOrder)
					gc.setFill(Color.BLACK);
				gc.fillRect(xAxis +(j*30), yAxis , 20, 10); 
			}
			yAxis += 30;
		}    
	}

	private void isValidInput() throws Exception {
		FieldValidation.branchNameValidation(cb_Branch.getValue());
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
