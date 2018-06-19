package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ComplaintFormController {

    @FXML
    private AnchorPane anchor_pane;

    @FXML
    private TextField tf_firstName;

    @FXML
    private TextField tf_userId;

    @FXML
    private Button btn_cancel;

    @FXML
    private TextField tf_lastName;

    @FXML
    private TextField tf_carNumber;

    @FXML
    private TextArea ta_description;

    @FXML
    private TextField tf_email;

    @FXML
    void send_click(ActionEvent event) {

    }

    @FXML
    void cancel_click(ActionEvent event) {

    }

}
