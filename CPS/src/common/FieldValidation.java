package common;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * all input fields validation
 * @author OmerG
 *
 */
public class FieldValidation {

	/**
	 * id Validation
	 * @param id
	 * @throws Exception
	 */
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

	/**
	 * email Validation
	 * @param email
	 * @throws Exception
	 */
	public static void emailValidation(String email) throws Exception {

		if(email.equals(CpsGlobals.emptyString)) 
			throw new Exception(CpsGlobals.emptyEmail);  

		String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);

		if(!matcher.matches())
			throw new Exception(CpsGlobals.notValidEmail); 

	}

	/**
	 * car Number Validation
	 * @param carNumber
	 * @throws Exception
	 */
	public static void carNumberValidation(String carNumber) throws Exception {

		if(carNumber.equals(CpsGlobals.emptyString)) 
			throw new Exception(CpsGlobals.emptyCarNumber); 

		String regex = "^([0-9]){7}?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(carNumber);

		if(!matcher.matches())
			throw new Exception(CpsGlobals.notValidCarNumber); 
	}

	/**
	 * date Compare Validation
	 * @param arrivingDate
	 * @param leavingDate
	 * @throws Exception
	 */
	public static void dateCompareValidation(Date arrivingDate, Date leavingDate) throws Exception {

		if(leavingDate.before(arrivingDate))
			throw new Exception(CpsGlobals.leavingBeforeArrivig); 
	}
	
	/**
	 * date Validation
	 * @param date
	 * @throws Exception
	 */
	public static void dateValidation(Date date) throws Exception {

		if(date.before(new Date()))
			throw new Exception(CpsGlobals.invalidDate); 
	}

	/**
	 * branch Name Validation
	 * @param branchName
	 * @throws Exception
	 */
	public static void branchNameValidation(Object branchName) throws Exception {
		if(branchName == null)
			throw new Exception(CpsGlobals.emptyBranchName); 
	}
	
	/**
	 * calendar Validation
	 * @param date
	 * @param time
	 * @throws Exception
	 */
	public static void calendarValidation(Object date,Object time) throws Exception{
		if(date == null || time== null) {
			throw new Exception(CpsGlobals.emptyCalander);
		}
	}
	
	/**
	 * name Validation
	 * @param name
	 * @throws Exception
	 */
	public static void nameValidation(String name) throws Exception {

		if(name.equals(CpsGlobals.emptyString)) {
			throw new Exception(CpsGlobals.emptyBranchName); 
		}
	}
	
	/**
	 * width Validation
	 * @param width
	 * @throws Exception
	 */
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
	
	/**
	 * branch Id Validation
	 * @param id
	 * @throws Exception
	 */
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
	
	/**
	 * branch Floor Validation
	 * @param floor
	 * @throws Exception
	 */
	public static void branchFloorValidation(Object floor) throws Exception {
		if(floor == null)
			throw new Exception(CpsGlobals.emptyBranchFloor); 
	}
	
	/**
	 * raw Spinner Validation
	 * @param raw
	 * @throws Exception
	 */
	public static void rawSpinnerValidation(Object raw) throws Exception {
		if(raw == null)
			throw new Exception(CpsGlobals.emptyRawSpinner); 
	}
	
	/**
	 * column Spinner Validation
	 * @param column
	 * @throws Exception
	 */
	public static void columnSpinnerValidation(Object column) throws Exception {
		if(column == null)
			throw new Exception(CpsGlobals.emptyColumnSpinner); 
	}

	/**
	 * is Number
	 * @param checkNumber
	 * @throws Exception
	 */
	public static void isNumber(String checkNumber) throws Exception {

		if(checkNumber.equals(CpsGlobals.emptyString)) {
			throw new Exception(CpsGlobals.emptyRefund); 
		}
		int number = Integer.parseInt(checkNumber);	

	}
}
