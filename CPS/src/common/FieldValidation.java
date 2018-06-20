package common;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidation {

	public static void idValidation(String id) throws Exception {

		if(id.equals(CpsGlobals.emptyString)) {
			throw new Exception(CpsGlobals.emptyId); 
		}

		String regex = "^([0-9]){9}?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(id);

		if(!matcher.matches())
			throw new Exception(CpsGlobals.notValidId); 
	}

	public static void emailValidation(String email) throws Exception {

		if(email.equals(CpsGlobals.emptyString)) 
			throw new Exception(CpsGlobals.emptyEmail);  

		String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		if(!matcher.matches())
			throw new Exception(CpsGlobals.notValidEmail); 

	}

	public static void carNumberValidation(String carNumber) throws Exception {

		if(carNumber.equals(CpsGlobals.emptyString)) 
			throw new Exception(CpsGlobals.emptyCarNumber); 

		String regex = "^([0-9]){7}?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(carNumber);

		if(!matcher.matches())
			throw new Exception(CpsGlobals.notValidCarNumber); 
	}

	public static void dateCompareValidation(Date arrivingDate, Date leavingDate) throws Exception {

		if(leavingDate.before(arrivingDate))
			throw new Exception(CpsGlobals.leavingBeforeArrivig); 
	}
	
	public static void dateValidation(Date date) throws Exception {

		if(date.before(new Date()))
			throw new Exception(CpsGlobals.invalidDate); 
	}

	public static void branchNameValidation(Object branchName) throws Exception {
		if(branchName == null)
			throw new Exception(CpsGlobals.emptyBranchName); 
	}
	
	public static void calendarValidation(Object date,Object time) throws Exception{
		if(date == null || time== null) {
			throw new Exception(CpsGlobals.emptyCalander);
		}
	}
	
	public static void nameValidation(String name) throws Exception {

		if(name.equals(CpsGlobals.emptyString)) {
			throw new Exception(CpsGlobals.emptyBranchName); 
		}
	}
	
	public static void widthValidation(String width) throws Exception {

		if(width.equals(CpsGlobals.emptyString)) {
			throw new Exception(CpsGlobals.emptyWidth); 
		}
		
		String regex = "^([4-8])+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(width);

		if(!matcher.matches())
			throw new Exception(CpsGlobals.notValidWidth); 
	}
	
	public static void branchIdValidation(String id) throws Exception {
		if(id.equals(CpsGlobals.emptyString)) {
			throw new Exception(CpsGlobals.emptyBranchId); 
		}

		String regex = "^([0-9])+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(id);

		if(!matcher.matches())
			throw new Exception(CpsGlobals.notValidBranchId); 
	}
}
