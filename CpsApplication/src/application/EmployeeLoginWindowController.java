/**
 * Sample Skeleton for 'EmployeeLoginWindow.fxml' Controller Class
 */

package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EmployeeLoginWindowController {

    @FXML // fx:id="btn_Cancel"
    private Button btn_Cancel; // Value injected by FXMLLoader
    
    @FXML // fx:id="btn_MakeOrder"
    private Button btn_MakeOrder; // Value injected by FXMLLoader

    @FXML // fx:id="tf_Id"
    private TextField tf_Id; // Value injected by FXMLLoader

    @FXML // fx:id="tf_Password"
    private TextField tf_Password; // Value injected by FXMLLoader

    @FXML
    void cancel(ActionEvent event) {

    }

}
