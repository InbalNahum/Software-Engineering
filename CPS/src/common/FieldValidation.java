package common;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidation {

	public static boolean idValidation(String id) {

		if(id.equals("")) {
			return false; 
		}

		String regex = "^([0-9]){9}?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(id);

		if(!matcher.matches())
			return false;

		return true;
	}

	public static boolean emailValidation(String email) {

		if(email.equals("")) 
			return false; 

		String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		if(!matcher.matches())
			return false;

		return true;
	}
	
	public static boolean carNumberValidation(String carNumber) {

		if(carNumber.equals("")) 
			return false; 

		String regex = "^([0-9]){7}?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(carNumber);

		if(!matcher.matches())
			return false;

		return true;
	}
	
	public static boolean dateValidation(Date arrivingDate, Date leavingDate) {
		
		if(leavingDate.before(arrivingDate))
			return false; 
		
		return true;
	}
	
}
