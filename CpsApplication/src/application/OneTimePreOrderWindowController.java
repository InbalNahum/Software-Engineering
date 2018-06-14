package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jfxtras.scene.control.CalendarTimeTextField;

public class OneTimePreOrderWindowController {

    @FXML // fx:id="tf_CarNumber"
    private TextField tf_CarNumber; // Value injected by FXMLLoader

    @FXML // fx:id="tf_CustomerId"
    private TextField tf_CustomerId; // Value injected by FXMLLoader

    @FXML // fx:id="tf_Email"
    private TextField tf_Email; // Value injected by FXMLLoader

    @FXML // fx:id="tf_ArriveDate"
    private DatePicker tf_ArriveDate; // Value injected by FXMLLoader

    @FXML // fx:id="tf_ArriveTime"
    private CalendarTimeTextField tf_ArriveTime; // Value injected by FXMLLoader

    @FXML // fx:id="tf_LeavingDate"
    private DatePicker tf_LeavingDate; // Value injected by FXMLLoader

    @FXML // fx:id="tf_LeavingTime"
    private CalendarTimeTextField tf_LeavingTime; // Value injected by FXMLLoader

    @FXML // fx:id="cb_Branch"
    private ComboBox<?> cb_Branch; // Value injected by FXMLLoader

    @FXML // fx:id="btn_Cancel"
    private Button btn_Cancel; // Value injected by FXMLLoader

    @FXML // fx:id="btn_MakeOrder"
    private Button btn_MakeOrder; // Value injected by FXMLLoader

    @FXML
    void cancel(ActionEvent event) {

    }

}
