package common;

public class CpsGlobals {
	
	//Server Details
	public static final int port = 5555;
	public static final String host = "localhost";
	
	//MySql Connection details
	public static final String url = "jdbc:mysql://cs.telhai.ac.il/studentDB_cs203495098";
	public static final String user = "cs203495098";
	public static final String password = "ya0522491015";
	public static final String driver = "com.mysql.jdbc.Driver";
	
	//Objects possible types
	public static enum ServerOperation{writeCasualCustomer, writeOneTimePreOrder};
	
	//Sql commands
	public static final String readObjectSql = "SELECT object_value FROM java_objects WHERE id = ?";
	public static final String writeCasualCustomer = "INSERT INTO CasualCustomer("
			+ "	CasualCustomer_ID, CasualCustomer_CarNumber, "
			+ "CasualCustomer_Email, CasualCustomer_LeaveTime, "
			+ "CasualCustomer_ArriveTime) VALUES (?, ?, ?, ?, ?)";
	
	public static final String writeOneTimePreOrder = "INSERT INTO OneTimePreOrder("
			+ "	OneTimePreOrder_ID, OneTimePreOrder_CarNumber, "
			+ "OneTimePreOrder_BranchName, OneTimePreOrder_ArriveTime, "
			+ "OneTimePreOrder_LeaveTime, OneTimePreOrder_Email) VALUES (?, ?, ?, ?, ?, ?)";

	//Branches
	public static final String telHaiBranch = "Tel-Hai";
	public static final String telAvivBranch = "Tel-Aviv";

	//exception messages 
	public static final String emptyString = "";
	public static final String emptyId = "Error: Enter your id";
	public static final String notValidId = "Error: Id is not valid";
	public static final String emptyEmail = "Error: Enter your email";
	public static final String notValidEmail = "Error: Email is not valid";
	public static final String emptyBranchName = "Error: Select branch name";
	public static final String LeavingBeforeArrivig = "Error: Leaving time before arriving time";
	public static final String notValidCarNumber = "Error: Car number is not valid";
	public static final String emptyCarNumber = "Error: Enter your car number";
	public static final String emptyCalander = "Error: Select date and time";
	
}
