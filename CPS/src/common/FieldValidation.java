package common;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidation {

	public static void idValidation(String id) throws Exception {

		if(id.equals("")) {
			throw new Exception("Error: Enter your id"); 
		}

		String regex = "^([0-9]){9}?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(id);

		if(!matcher.matches())
			throw new Exception("Error: Id is not valid"); 
	}

	public static void emailValidation(String email) throws Exception {

		if(email.equals("")) 
			throw new Exception("Error: Enter your email");  

		String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		if(!matcher.matches())
			throw new Exception("Error: Email is not valid"); 

	}

	public static void carNumberValidation(String carNumber) throws Exception {

		if(carNumber.equals("")) 
			throw new Exception("Error: Enter your car number"); 

		String regex = "^([0-9]){7}?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(carNumber);

		if(!matcher.matches())
			throw new Exception("Error: Car number is not valid"); 
	}

	public static void dateValidation(Date arrivingDate, Date leavingDate) throws Exception {

		if(leavingDate.before(arrivingDate))
			throw new Exception("Error: Leaving time before arriving time"); 

	}

	public static void branchNameValidation(String branchName) throws Exception {

		if(branchName.equals(""))
			throw new Exception("Error: Select branch name"); 
	}

}
