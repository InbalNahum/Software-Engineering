/**
 * Sample Skeleton for 'CasualOrderWindow.fxml' Controller Class
 */

package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jfxtras.scene.control.CalendarTimeTextField;

public class CasualOrderWindowController {

    @FXML // fx:id="tf_CarNumber"
    private TextField tf_CarNumber; // Value injected by FXMLLoader

    @FXML // fx:id="tf_CustomerId"
    private TextField tf_CustomerId; // Value injected by FXMLLoader

    @FXML // fx:id="tf_Email"
    private TextField tf_Email; // Value injected by FXMLLoader

    @FXML // fx:id="tf_Date"
    private DatePicker tf_Date; // Value injected by FXMLLoader

    @FXML // fx:id="tf_Time"
    private CalendarTimeTextField tf_Time; // Value injected by FXMLLoader

    @FXML // fx:id="btn_Cancel"
    private Button btn_Cancel; // Value injected by FXMLLoader

    @FXML // fx:id="btn_MakeOrder"
    private Button btn_MakeOrder; // Value injected by FXMLLoader

    @FXML
    void cancel(ActionEvent event) {

    }

}
