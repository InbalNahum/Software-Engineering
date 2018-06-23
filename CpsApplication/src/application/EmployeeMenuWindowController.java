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

public class EmployeeMenuWindowController {

	@FXML
	void branchSetupClicked(ActionEvent event) {
		moveToWindow(event, CpsGlobals.branchSetupWindow,
				CpsGlobals.branchSetupWindowTitle);
	}

	@FXML
	void createBranchClicked(ActionEvent event) {
		moveToWindow(event, CpsGlobals.createNewBranchWindow,
				CpsGlobals.createNewBranchWindowTitle);
	}

	@FXML
	void saveParkingClicked(ActionEvent event) {
		moveToWindow(event, CpsGlobals.saveParkingWindow,
				CpsGlobals.SaveParkingWindowTitle);
	}

	@FXML
	void complaintClicked(ActionEvent event) {
		if(User.getCurrentUser().getUserType() == UserType.branchEmployee) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.permisionDenied);
			return;
		}
		moveToWindow(event, CpsGlobals.handleComplaintWindow,
				CpsGlobals.handleComplaintWindowTitle);
	}

	@FXML
	void setFullStateClicked(ActionEvent event) {
		moveToWindow(event, CpsGlobals.setBranchToFullStaeWindow,
				CpsGlobals.setBranchToFullStaeWindowTitle);
	}

	@FXML
	void showBranchStateClicked(ActionEvent event) {
		if(User.getCurrentUser().getUserType() != UserType.manager) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.permisionDenied);
			return;
		}
		moveToWindow(event, CpsGlobals.showBranchStateWindow,
				CpsGlobals.showBranchStateWindowTitle);
	}

	@FXML
	void updatePriceClicked(ActionEvent event) {
		if(User.getCurrentUser().getUserType() != UserType.manager) {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.permisionDenied);
			return;
		}
		moveToWindow(event, CpsGlobals.updatePriceListWindow,
				CpsGlobals.updatePriceListWindowTitle);
	}

	@FXML
	void outOfOrderClicked(ActionEvent event) {
		moveToWindow(event, CpsGlobals.outOfOrderManagementWindow,
				CpsGlobals.outOfOrderManagementWindowTitle);
	}
	

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
