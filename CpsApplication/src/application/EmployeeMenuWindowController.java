/**
 * Sample Skeleton for 'EmployeeMenuWindow.fxml' Controller Class
 */

package application;

import java.io.IOException;

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
		moveToWindow(event, CpsGlobals.showBranchStateWindow,
				CpsGlobals.showBranchStateWindowTitle);
	}

	@FXML
	void updatePriceClicked(ActionEvent event) {
		moveToWindow(event, CpsGlobals.updatePriceListWindow,
				CpsGlobals.updatePriceListWindowTitle);
	}

	@FXML
	void outOfOrderClicked(ActionEvent event) {
		moveToWindow(event, CpsGlobals.outOfOrderManagementWindow,
				CpsGlobals.outOfOrderManagementWindowTitle);
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
