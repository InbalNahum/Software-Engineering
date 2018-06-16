package application;

import java.io.IOException;

import client.SqlClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.ServerResponse;

public class LoginWindowController {
	
    @FXML
    private PasswordField tf_password;

    @FXML
    private TextField tf_UserName;

    @FXML
    void sigIn_click(ActionEvent event) {
        String userName = tf_UserName.getText();
        String password = tf_password.getText();
        try {
			if(employeeAuthentication(userName,password)) {
				
			}
			else {
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private boolean employeeAuthentication(String userName, String password) throws InterruptedException {
    	boolean isAuth = false;
    	try {
			SqlClient sqlClient = SqlClient.getInstance();
			sqlClient.employeeAuthentication(userName,password);
            while(sqlClient.getLastResponse() == null) {
            	
            }
            ServerResponse serverResponse = sqlClient.getLastResponse();
            isAuth = (boolean) serverResponse.getObjectAtIndex(0);
		} catch (IOException e) {
			isAuth = false;
			e.printStackTrace();
		}
    	System.out.println(isAuth);
    	return isAuth;
    }

}
