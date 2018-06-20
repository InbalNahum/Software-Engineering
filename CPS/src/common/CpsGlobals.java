package common;

import java.util.concurrent.atomic.AtomicInteger;

public class CpsGlobals {

	//Communicate Token
	public static AtomicInteger communicateToken = new AtomicInteger();
	public static int getNextToken() {
		return communicateToken.incrementAndGet();
	}

	//Icons Paths
	public static final String cpsIconPath = "cps-icon.png";

	//Server Connection Details
	public static final int port = 5555;
	public static final String host = "localhost";

	//MySql Connection Details
	public static final String url = "jdbc:mysql://cs.telhai.ac.il/studentDB_cs203495098";
	public static final String user = "cs203495098";
	public static final String password = "ya0522491015";
	public static final String driver = "com.mysql.jdbc.Driver";

	//Objects possible types
	public static enum ServerOperation{writeCasualCustomer, writeOneTimePreOrder, monthlySubscription, 
		renewMonthlySubscription, employeeAuthentication, createNewBranch, getBranchState};

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

		public static final String writeMonthlySubscription = "INSERT INTO MonthlySubscription("
				+ "MonthlySubscription_ID, MonthlySubscription_CarNumber, "
				+ "MonthlySubscription_StartTime) VALUES (?, ?, ?)";
		
		public static final String writeNewBranch = "INSERT INTO Branch("
				+ "Branch_ID, Branch_Name, Branch_CarPark) VALUES (?, ?, ?)";

		public static final String writeRenewMonthlySubscription = "REPLACE INTO MonthlySubscription("
				+ "MonthlySubscription_ID, MonthlySubscription_CarNumber, "
				+ "MonthlySubscription_StartTime) VALUES (?, ?, ?)";

		public static final String readRenewMonthlySubscription = "SELECT * FROM MonthlySubscription"
				+ " WHERE MonthlySubscription_ID = ?";
		
		public static final String readBranch = "SELECT * FROM Branch"
				+ " WHERE Branch_Name = ?";

		//Branches
		public static final String telHaiBranch = "Tel-Hai";
		public static final String telAvivBranch = "Tel-Aviv";
		public static final String tiberiasBranch = "Tiberias";
		public static final String qiryatShemonaBranch = "Qiryat Shemona";

		//Windows Titles
		public static final String oneTimePreOrderTitle = "One time pre order parking";
		public static final String casualOrderWindowTitle = "Casual customer parking order";
		public static final String loginWindowTitle = "CPS Login";
		public static final String createNewBranchWindowTitle = "Create new branch";
		public static final String showBranchStateWindowTitle = "Show branch state";


		//exception messages 
		public static final String emptyString = "";
		public static final String emptyId = "Error: Enter your id";
		public static final String emptyBranchId = "Error: Enter Branch id";
		public static final String notValidId = "Error: Id is not valid";
		public static final String notValidBranchId = "Error: Id is not valid";
		public static final String notValidWidth = "Error: Width is not valid";
		public static final String emptyEmail = "Error: Enter your email";
		public static final String notValidEmail = "Error: Email is not valid";
		public static final String emptyBranchName = "Error: Select branch name";
		public static final String emptyWidth = "Error: Select width";
		public static final String leavingBeforeArrivig = "Error: Leaving time before arriving time";
		public static final String invalidDate = "Error: Time is not valid";
		public static final String notValidCarNumber = "Error: Car number is not valid";
		public static final String emptyCarNumber = "Error: Enter your car number";
		public static final String emptyCalander = "Error: Select date and time";

		//dialog messages
		public static final String errorDialogTitle = "Error Dialog";
		public static final String informationDialogTitle = "Information Dialog";
		public static final String successMessage = "Order done successfully!";
		public static final String somethingGoWrone = "Something go wrong";
		public static final String wrongUserOrPassword = "User name or password are incorrect";

		//column names
		public static final String employeePassword = "Employee_Password";



		//parkingStatus
		public static enum parkingState{available, unAvailable, outOfOrder};	

}
