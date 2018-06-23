package common;

public class CpsGlobals {

	public static final String tokenName = "token";
	public static final int SubscriptionDays = 14;
	public static final int daysInWeek = 7;
	
	//Icons Paths
	public static final String cpsIconPath = "cps-icon.png";

	//Server Connection Details
	public static  int port = 5555;
	public static  String host = "localhost";

	//MySql Connection Details
	public static final String url = "jdbc:mysql://cs.telhai.ac.il/studentDB_cs203495098";
	public static final String user = "cs203495098";
	public static final String password = "ya0522491015";
	public static final String driver = "com.mysql.jdbc.Driver";

	//Objects possible types
	public static enum ServerOperation{writeCasualCustomer, writeOneTimePreOrder, monthlySubscription, 
		renewMonthlySubscription, employeeAuthentication, createNewBranch, getBranchState, branchListRequest, 
		tokenRequest, feedback,createNewComplain, getBranchParkParameters, setOutOfOrderParking,
		setSavedParking, getBranchById, customerComplaintRequest, priceListRequest,
		updatePriceListTable, getUserMessages, updateComplaintTable, EnterCarToParkingWithCheck,
		setBranchToFullState, setBranchSetup, EnterCarToParking, customerAuthentication
		,cancelPreOrder, removeCarFromParking, addNewUser, removeUser};
		
	//Sql commands
	public static final String readObjectSql = "SELECT object_value FROM java_objects WHERE id = ?";
	
	public static final String writeCasualCustomer = "INSERT INTO CasualCustomer("
			+ "	CasualCustomer_ID, CasualCustomer_CarNumber, "
			+ "CasualCustomer_Email, CasualCustomer_LeaveTime, "
			+ "CasualCustomer_ArriveTime) VALUES (?, ?, ?, ?, ?)";
	
	public static final String readBranchFromPreOrder = "Select OneTimePreOrder_BranchName, "
			+ "OneTimePreOrder_ArriveTime "
			+ "FROM OneTimePreOrder WHERE OneTimePreOrder_ID = ?";
	
	public static final String readIdFromCasualCustomer = "Select CasualCustomer_ID"
			+" FROM CasualCustomer WHERE CasualCustomer_ID = ?";
	
	public static final String deletePreOrder = "DELETE FROM OneTimePreOrder"
			+ " WHERE OneTimePreOrder_CarNumber = ?";
	
	public static final String deleteCasualOrder = "DELETE FROM CasualCustomer"
			+ " WHERE CasualCustomer_CarNumber = ?";
	
	public static final String writeOneTimePreOrder = "INSERT INTO OneTimePreOrder("
			+ "	OneTimePreOrder_ID, OneTimePreOrder_CarNumber, "
			+ "OneTimePreOrder_BranchName, OneTimePreOrder_ArriveTime, "
			+ "OneTimePreOrder_LeaveTime, OneTimePreOrder_Email) VALUES (?, ?, ?, ?, ?, ?)";
	
	public static final String employeeAuthentication = "select * from Employee where Employee_ID=?";
    public static final String isSubscriber = "SELECT * FROM MonthlySubscription WHERE MonthlySubscription_ID = ? "
    		+ "AND MonthlySubscription_CarNumber = ?";
	public static final String writeMonthlySubscription = "INSERT INTO MonthlySubscription("
			+ "MonthlySubscription_ID, MonthlySubscription_CarNumber, "
			+ "MonthlySubscription_StartTime, CustomerAccount) VALUES (?, ?, ?, ?)";

	public static final String writeBranchUpdate = "REPLACE INTO Branch("
			+ "Branch_ID, Branch_Name, Branch_CarPark) VALUES (?, ?, ?)";
	
	public static final String writeNewBranch = "INSERT INTO Branch("
			+ "Branch_ID, Branch_Name, Branch_CarPark) VALUES (?, ?, ?)";
	
	public static final String updateMonthlySubscriptionTable = "UPDATE MonthlySubscription SET "
			+ "CustomerAccount = ? WHERE MonthlySubscription_CarNumber = ?";
	
	public static final String getUserAccount = "SELECT * FROM MonthlySubscription WHERE MonthlySubscription_CarNumber = ?";
	
	public static final String updatePriceListTable = "UPDATE PriceList SET "
			+ "PriceList_Price = ? WHERE PriceList_ID = ?";
	
	public static final String updateComplainTable = "UPDATE CustomerComplaint SET "
			+ "CustomerComplaint_Status = ?, Promotional = ? WHERE ComplainCarNumber = ?";
	
	public static final String writeNewComplain = "INSERT INTO CustomerComplaint("
			+ "ComplainCarNumber, ComplainUserId, ComplainFirstName,"
			+ "ComplainLastName, ComplainDescription, "
			+ "CustomerComplaint_CreateTime, CustomerComplaint_Status, 	Promotional) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String writeRenewMonthlySubscription = "REPLACE INTO MonthlySubscription("
			+ "MonthlySubscription_ID, MonthlySubscription_CarNumber, "
			+ "MonthlySubscription_StartTime, CustomerAccount) VALUES (?, ?, ?, ?)";


	public static final String readRenewMonthlySubscription = "SELECT * FROM MonthlySubscription"
			+ " WHERE MonthlySubscription_ID = ?";
		
	public static final String readBranch = "SELECT * FROM Branch"
			+ " WHERE Branch_Name = ?";
	
	public static final String subscriptionRenewalReminder = "SELECT MonthlySubscription_StartTime FROM MonthlySubscription "
			+ "WHERE MonthlySubscription_ID = ? AND MonthlySubscription_CarNumber = ?";
	public static final String getClientComplaint = "SELECT * FROM CustomerComplaint WHERE ComplainUserId"
			+ " = ? AND ComplainCarNumber = ?";
	public static final String getUserPreorders = "SELECT * FROM OneTimePreOrder WHERE OneTimePreOrder_ID = ?";
	public static final String fetchToken = "SELECT * FROM TokenHolder";
	public static final String increaseToken = "UPDATE TokenHolder SET token=token+1";
	public static final String getBranchList = "SELECT * FROM Branch";
	public static final String getBranchListById = "SELECT Employee_Branch FROM Employee WHERE Employee_ID = ?";
	public static final String getPriceList = "SELECT PriceList_Price FROM PriceList";
	public static final String getCustomerComplaint = "SELECT * FROM CustomerComplaint";
  


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
	public static final String SaveParkingWindowTitle = "Save Parking";
	public static final String OutOfOrderManagementWindowTitle = "Out of order management";
	public static final String MonthlySubscriptionOrderWindowTitle = "Monthly subscription order";
	public static final String RenewMonthlySubscriptionWindowTitle = "Renew monthly subscription";
	public static final String ComplaintFormWindowTitle = "Complaint form";
	public static final String employeeLoginTitle = "Employee Login";
	public static final String customerLoginTitle = "Customer Login";
	public static final String WelcomeWindowTitle = "Welcome";
	public static final String EnterCarToParkingWithCheckWindowTitle = "Enter Car To Parking";
	public static final String subscriberMenuWindowTitle = "Subscriber Menu";
	public static final String casualCustomerMenuWindowTitle = "Casual Customer Menu";
	public static final String clientMailBoxTitle = "Mail Box";
	public static final String subscriptionExpiredTitle = "Subscription Expired";
	public static final String OneTimePreOrderWindowTitle = "Pre parking order";
	public static final String branchSetupWindowTitle = "branch Setup";
	public static final String handleComplaintWindowTitle = "Complaint Management";
	public static final String setBranchToFullStaeWindowTitle = "Set Branch To Full Stae";
	public static final String updatePriceListWindowTitle = "Update Price List";
	public static final String outOfOrderManagementWindowTitle = "Out Of Order Management";
	public static final String employeeMenuWindowTitle = "Employee Menu";
	public static final String removeCarTitle = "Get Car Back";
	public static final String cancelPreOrderTitle = "Cancel Pre-Order";
	public static final String ServerConfigWindowTitle = "Server Configurations";


	//Window names
	public static final String casualCustomerMenuWindow = "CasualCustomerMenu.fxml";
	public static final String customerLogin = "CustomerLogin.fxml";
	public static final String subscriberMenuWindow = "SubScriberMenu.fxml";
	public static final String clientMailBoxWindow = "ClientMailBox.fxml";
	public static final String welcomeWindow = "WelcomeWindow.fxml";
	public static final String handleComplaintWindow = "HandleComplaint.fxml";
	public static final String renewSubscriptionWindow = "RenewMonthlySubscriptionWindow2.fxml";
	public static final String subscriptionExpiredWindow = "SubscriptionExpiredWindow.fxml";
	public static final String enterCarToParkingWindow= "EnterCarToParkingWindow.fxml";
	public static final String cancelPreOrderWindow= "CancelPreOrderWindow.fxml";
	public static final String removeCarFromParkingWindow = "RemoveCarFromParkingWindow.fxml";	
	public static final String complaintFormWindow = "ComplaintFormWindow.fxml";
	public static final String oneTimePreOrderWindow = "OneTimePreOrderWindow.fxml";
	public static final String casualOrderWindow = "CasualOrderWindow.fxml";
	public static final String enterCarToParkingWithCheckWindow = "EnterCarToParkingWithCheckWindow.fxml";
	public static final String employeeLoginWindow = "LoginWindow.fxml";
	public static final String branchSetupWindow = "BranchSetupWindow.fxml";
	public static final String createNewBranchWindow = "CreateNewBranchWindow.fxml";
	public static final String saveParkingWindow = "SaveParkingWindow.fxml";
	public static final String setBranchToFullStaeWindow = "SetBranchToFullStaeWindow.fxml";
	public static final String showBranchStateWindow = "ShowBranchStateWindow.fxml";
	public static final String updatePriceListWindow = "UpdatePriceListWindow.fxml";
	public static final String outOfOrderManagementWindow = "OutOfOrderManagementWindow.fxml";
	public static final String employeeMenuWindow = "EmployeeMenuWindow.fxml";
	public static final String removeCar = "RemoveCarFromParkingWindow.fxml";
	public static final String cancelPreOrder = "CancelPreOrderWindow.fxml";
	public static final String MonthlySubscriptionOrderWindow = "MonthlySubscriptionOrderWindow.fxml";
	public static final String ServerConfigWindow = "ServerConfig.fxml";


	//exception messages 
	public static final String emptyString = "";
	public static final String emptyId = "Enter your id";
	public static final String emptyBranchId = "Enter Branch id";
	public static final String emptyRefund = "Enter refund number";
	public static final String notValidId = "Id is not valid";
	public static final String notValidBranchId = "Id is not valid";
	public static final String notValidWidth = "Width is not valid";
	public static final String emptyEmail = "Enter your email";
	public static final String notValidEmail = "Email is not valid";
	public static final String emptyBranchName = "Select branch name";
	public static final String emptyBranchFloor = "Select floor value";
	public static final String emptyRawSpinner = "Select Raw value";
	public static final String emptyColumnSpinner = "Select Column name";
	public static final String emptyWidth = "Select width";
	public static final String leavingBeforeArrivig = "Leaving time before arriving time";
	public static final String invalidDate = "Time is not valid";
	public static final String notValidCarNumber = "Car number is not valid";
	public static final String emptyCarNumber = "Enter your car number";
	public static final String emptyCalander = "Select date and time";
	public static final String serverIssue = "Server issues";
	public static final String numberFormatException = "Click only numbers";
	public static final String orderDoesntExist = "Your Order doesn't exist";
	public static final String carDoesntExist = "Your Car doesn't exist";
	public static final String userAlreadyConnected = "The user is already connected";
	public static final String invalidIp = "You entered invalid ip address";
	public static final String invalidPort = "You entered invalid port number";

	//dialog messages
	public static final String errorDialogTitle = "Error Dialog";
	public static final String informationDialogTitle = "Information Dialog";
	public static final String successMessage = "Order done successfully!";
	public static final String somethingGoWrone = "Something go wrong";
	public static final String wrongUserOrPassword = "User name or password are incorrect";
	public static final String permisionFromManager = "Manager permit the price update";
	public static final String permisionDenied = "Permision denied";

	//column names
	public static final String employeePassword = "Employee_Password";
	public static final String complaintStatus = "CustomerComplaint_Status";
	public static final String complaintPromotional = "Promotional";
	public static final String complainDescription = "ComplainDescription";
    
	//Client message formats
    public static final String subscriptionRenewalMessageFormat = "Your subscription ends in %s days, it's time to renew :)";
    public static final String complaintFormat = "%s\nStatus: %s\nPromotional: %s";
    public static final String preOrderMessageFormat = "Branch name:%s\nArrive time:%s\nLeave time:%s";
    
	//parkingStatus
	public static enum parkingState{available, unAvailable, outOfOrder,anoutOfOrder};	

	//Complaint status
	public enum complaintStatus{opened,closed};
	
	//operation indicators
	public static final String operationSuccess = "Operation done successfully";
	public static final String operationFailure = "Operation failure";
	public static final String failToLoadWindow = "Fail to load window";
	public static final String expiredOrNotStart = "expiredOrNotStart";
	public static final String inTokef = "inTokef";
	
	//Positions names
	public static final String BranchWorker = "Branch Worker";
	public static final String CEO = "CEO";
	public static final String BranchManager = "Branch Manager";
	public static final String CustomerServiceWorker = "Customer Service Worker";
	

}
