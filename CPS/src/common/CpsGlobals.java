package common;

public class CpsGlobals {
	
	//Server Connection Details
	public static final int port = 5555;
	public static final String host = "localhost";
	
	//MySql Connection Details
	public static final String url = "jdbc:mysql://cs.telhai.ac.il/studentDB_cs203495098";
	public static final String user = "cs203495098";
	public static final String password = "ya0522491015";
	public static final String driver = "com.mysql.jdbc.Driver";
	
	//Objects possible types
	public static enum ServerOperation{writeCasualCustomer, writeOneTimePreOrder,
		employeeAuthentication};
	
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
    public static final String employeeAuthentication = "select Employee_Password from Employee where Employee_ID=?";
	//Branches
	public static final String telHaiBranch = "Tel-Hai";
	public static final String telAvivBranch = "Tel-Aviv";
	
	//Windows Titles
	public static final String oneTimePreOrderTitle = "One time pre order parking";
	public static final String casualOrderWindowTitle = "Casual customer parking order";
	public static final String loginWindowTitle = "CPS Login";
	

}
