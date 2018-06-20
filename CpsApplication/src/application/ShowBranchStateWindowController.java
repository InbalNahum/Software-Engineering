package application;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import parkingLogic.BranchParkState;
import server.ServerResponse;

public class ShowBranchStateWindowController implements Initializable {
	
    @FXML // fx:id="btn_GetBranchState"
    private Button btn_GetBranchState; // Value injected by FXMLLoader

    @FXML // fx:id="btn_Cancel"
    private Button btn_Cancel; // Value injected by FXMLLoader

    @FXML
    void Cancel_Click(ActionEvent event) {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    
    @FXML // fx:id="canvasOne"
    private Canvas canvasOne; // Value injected by FXMLLoader

    @FXML // fx:id="canvasTwo"
    private Canvas canvasTwo; // Value injected by FXMLLoader

    @FXML // fx:id="canvasThree"
    private Canvas canvasThree; // Value injected by FXMLLoader

    @FXML // fx:id="cb_Branch"
    private ComboBox<String> cb_Branch; // Value injected by FXMLLoader
    
	ObservableList<String> comboBoxList = FXCollections.observableArrayList(CpsGlobals.telHaiBranch,
			CpsGlobals.telAvivBranch, CpsGlobals.tiberiasBranch, CpsGlobals.qiryatShemonaBranch);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cb_Branch.setItems(comboBoxList);
	}

    @FXML
    void GetBranchState_Click(ActionEvent event) {
    	try {
    		isValidInput();
        	String name = cb_Branch.getValue();
        	BranchStateRequest request = new BranchStateRequest(name);
        	SqlClient sqlClient = SqlClient.getInstance();
			int requestToken = CpsGlobals.getNextToken();
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
}
