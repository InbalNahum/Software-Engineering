package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import actors.CasualCustomer;
import common.CpsGlobals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ParkingOrderOccesionalController {

	@FXML
	private TextField emailTf;

	@FXML
	private Button makeOrderBtn;

	@FXML
	private Button cancelBtn;

	@FXML
	private TextField leavingTimeTf;

	@FXML
	private TextField carNumberTf;

	@FXML
	private AnchorPane window;

	@FXML
	private TextField customerIdTf;

	@FXML
	void makeOrder(ActionEvent event) {
		if(isValidOrder()) {
			CasualCustomer toAdd = new CasualCustomer();
			toAdd.setArriveTime(new Date());
			toAdd.setBranchName(CpsGlobals.telHaiBranch);
			toAdd.setCarNumber(Integer.parseInt(carNumberTf.getText()));
			toAdd.setEmail(emailTf.getText());
			toAdd.setId(Integer.parseInt(customerIdTf.getText()));
			toAdd.setLeaveTime(new Date());
			
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
			try {
				Date leavingTime = formatter.parse(leavingTimeTf.getText());
				toAdd.setLeaveTime(leavingTime);
			} catch (ParseException e) {
				//TODO ydanan - change time picker component
	           System.out.println("cant parse leaving time");
	           return;
			}
		}
		else {
			//TODO ydanan - error message shows up
			System.out.println("invalid order");
		}

	}

	@FXML
	void cancel(ActionEvent event) {

	}

	private boolean isValidOrder() {
		if(leavingTimeTf.getText().equals("") ||
				carNumberTf.getText().equals("") ||
				emailTf.getText().equals("") ||
				customerIdTf.getText().equals("")) {
			return false;
		}
		return true;
	}

}
