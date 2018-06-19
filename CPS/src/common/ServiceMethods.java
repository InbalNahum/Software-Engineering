package common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import server.ServerResponse;

public class ServiceMethods {
	
	public static void alertDialog(AlertType alertType, String message) {
		Alert alert = new Alert(alertType);

		if(alertType.equals(AlertType.INFORMATION)) {
			alert.setTitle(CpsGlobals.informationDialogTitle);
		}else {
			alert.setTitle(CpsGlobals.errorDialogTitle);
		}
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	
	public static Date convertToDateObject(LocalDate date, Calendar calander) {

		Date dateTime = calander.getTime();
		LocalTime time = LocalDateTime.ofInstant(dateTime.toInstant(), ZoneId.systemDefault()).toLocalTime();
		LocalDateTime dt = LocalDateTime.of(date, time);
		Date leavingDateTime = Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
		return leavingDateTime;
	}


	public static void alertFeedback(Optional<ServerResponse> serverResponse,
			ActionEvent event) {
		String result = (String)serverResponse.get().getObjectAtIndex(0);
		if(result.equals(CpsGlobals.operationSuccess)) {
			((Stage)(((Button)event.getSource()).getScene().getWindow())).close();	
			ServiceMethods.alertDialog(AlertType.INFORMATION, CpsGlobals.successMessage);
		}
		else {
			ServiceMethods.alertDialog(AlertType.ERROR, CpsGlobals.operationFailure);
		}
		
	}
	
	
}
