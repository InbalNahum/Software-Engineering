package application;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jfxtras.scene.control.CalendarTimeTextField;

public class CasualOrderWindowController {

    @FXML // fx:id="tf_Email"
    private TextField tf_Email; // Value injected by FXMLLoader

    @FXML // fx:id="tf_LeavingDate"
    private DatePicker tf_LeavingDate; // Value injected by FXMLLoader

    @FXML // fx:id="tf_Id"
    private TextField tf_Id; // Value injected by FXMLLoader

    @FXML // fx:id="tf_LeavingTime"
    private CalendarTimeTextField tf_LeavingTime; // Value injected by FXMLLoader

    @FXML // fx:id="tf_CarNumber"
    private TextField tf_CarNumber; // Value injected by FXMLLoader

}
