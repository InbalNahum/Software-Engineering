/**
 * Sample Skeleton for 'EmployeeMenuWindow.fxml' Controller Class
 */

package application;

import java.io.IOException;

import actors.User;
import actors.User.UserType;
import client.SqlClient;
import common.CpsGlobals;
import common.ServiceMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * Window controller for employee menu
 * @author inahum
 *
 */
public class EmployeeMenuWindowController {
/**
 * Employee clicked on branch setup 
 * @param event
 */
	@FXML
	void branchSetupClicked(ActionEvent event) {
		moveToWindow(event, CpsGlobals.branchSetupWindow,
				CpsGlobals.branchSetupWindowTitle);
	}
/**
 * Employee want to creat new branch
 * @param event
 */
	@FXML
	void createBranchClicked(ActionEvent event) {
		moveToWindow(event, CpsGlobals.createNewBranchWindow,
				CpsGlobals.createNewBranchWindowTitle);
	}
/**
 * Employee save new parking 
 * @param event
 */
	@FXML
	void saveParkingClicked(ActionEvent event) {
		moveToWindow(event, CpsGlobals.saveParkingWindow,
				CpsGlobals.SaveParkingWindowTitle);
	}
/**
 * Employee want to see the complaints
 * @param event
 */
	@FXML
	void complaintClicked(ActionEvent event) {
		if(User.getCurrentUser().getUserType() == UserType.branchEmployee) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.permisionDenied);
			return;
		}
		moveToWindow(event, CpsGlobals.handleComplaintWindow,
				CpsGlobals.handleComplaintWindowTitle);
	}
/**
 * Employee update that the parking is full 
 * @param event
 */
	@FXML
	void setFullStateClicked(ActionEvent event) {
		moveToWindow(event, CpsGlobals.setBranchToFullStaeWindow,
				CpsGlobals.setBranchToFullStaeWindowTitle);
	}
/**
 * Show branch state clicked
 * @param event
 */
	@FXML
	void showBranchStateClicked(ActionEvent event) {
		if(User.getCurrentUser().getUserType() != UserType.manager) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.permisionDenied);
			return;
		}
		moveToWindow(event, CpsGlobals.showBranchStateWindow,
				CpsGlobals.showBranchStateWindowTitle);
	}
/**
 * The manager want to update the price of the parking
 * @param event
 */
	@FXML
	void updatePriceClicked(ActionEvent event) {
		if(User.getCurrentUser().getUserType() != UserType.manager) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.permisionDenied);
			return;
		}
		moveToWindow(event, CpsGlobals.updatePriceListWindow,
				CpsGlobals.updatePriceListWindowTitle);
	}
/**
 * Report on parking is wrong
 * @param event
 */
	@FXML
	void outOfOrderClicked(ActionEvent event) {
		moveToWindow(event, CpsGlobals.outOfOrderManagementWindow,
				CpsGlobals.outOfOrderManagementWindowTitle);
	}
	
/**
 * Cancel cliked
 * @param event
 */
    @FXML
    void cancel_click(ActionEvent event) {
    	try {
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.sendTokenRequest();
			int token = WaitToServer.waitForServerToken(sqlClient);
			sqlClient.removeUser(User.getCurrentUser(),token);
    	} catch (IOException | InterruptedException e) {
          e.printStackTrace();
		}
        moveToWindow(event, CpsGlobals.employeeLoginWindow,
       		   CpsGlobals.employeeLoginTitle);
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
