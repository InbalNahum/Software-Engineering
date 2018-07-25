package common;

import java.util.Optional;

import actors.CasualCustomer;
import actors.User;
import entity.Branch;
import entity.BranchParkParameters;
import entity.BranchStateRequest;
import entity.ComplainObject;
import entity.CustomerComplaint;
import entity.MonthlySubscription;
import entity.PreOrderCustomer;
import entity.PriceList;

/**
 * Server Communicator Interface
 * @author OmerG
 *
 */
public interface CpsServerCommunicator {
	
	/**
	 * send Token Request
	 */
	public void sendTokenRequest();
	
	/**
	 * fetch Token
	 * @return
	 */
	public Optional<Integer> fetchToken();
	
	/**
	 * add Casual Customer
	 * @param customer
	 * @param token
	 */
	public void addCasualCustomer(CasualCustomer customer, int token);
	
	/**
	 * add Pre-Order Customer
	 * @param preOrderCustomer
	 * @param token
	 */
	public void addPreOrderCustomer(PreOrderCustomer preOrderCustomer, int token);
	
	/**
	 * employee Authentication
	 * @param userName
	 * @param password
	 * @param token
	 * @throws InterruptedException
	 */
    public void employeeAuthentication(String userName, String password, int token) throws InterruptedException;
	
    /**
     * add Monthly Subscription
     * @param monthlySubscription
     * @param token
     */
    public void addMonthlySubscription(MonthlySubscription monthlySubscription, int token);
    
    /**
     * renew Monthly Subscription
     * @param monthlySubscription
     * @param token
     */
	public void renewMonthlySubscription(MonthlySubscription monthlySubscription, int token);
	
	/**
	 * add Branch
	 * @param branch
	 * @param token
	 */
	public void addBranch(Branch branch,int token);
	
	/**
	 * get Branch State
	 * @param request
	 * @param token
	 * @throws InterruptedException
	 */
	public void getBranchState(BranchStateRequest request, int token) throws InterruptedException;
	
	/**
	 * add Complain
	 * @param branch
	 * @param token
	 */
	public void addComplain(CustomerComplaint branch,int token);
	
	/**
	 * send Branch List Request
	 * @param requestToken
	 */
	public void sendBranchListRequest(int requestToken);
	
	/**
	 * get BranchPark Parameters
	 * @param name
	 * @param token
	 */
	public void getBranchParkParameters(String name, int token);
	
	/**
	 * set Out Of Order Parking
	 * @param name
	 * @param parameters
	 * @param token
	 */
	public void setOutOfOrderParking(String name, BranchParkParameters parameters, int token);
	
	/**
	 * set Saved Parking
	 * @param name
	 * @param parameters
	 * @param token
	 */
	public void setSavedParking(String name, BranchParkParameters parameters, int token);
	
	/**
	 * send Branch By Id Request
	 * @param id
	 * @param requestToken
	 */
	public void sendBranchByIdRequest(String id,int requestToken);
	
	/**
	 * send Customer Complaint Request
	 * @param requestToken
	 */
	public void sendCustomerComplaintRequest(int requestToken);
	
	/**
	 * update Complaint Table
	 * @param complainObject
	 * @param token
	 */
	public void updateComplaintTable(ComplainObject complainObject, int token);
	
	/**
	 * send Price List Request
	 * @param requestToken
	 */
	public void sendPriceListRequest(int requestToken);
	
	/**
	 * update Price List Table
	 * @param ppriceList
	 * @param token
	 */
	public void updatePriceListTable(PriceList ppriceList, int token);
	
	/**
	 * end User Messages Request
	 * @param userId
	 * @param userCarNum
	 * @param requestToken
	 */
	public void sendUserMessagesRequest(String userId, String userCarNum, int requestToken);
	
	/**
	 * Enter Car To Parking With Check 
	 * @param id
	 * @param carNumber
	 * @param token
	 */
	public void EnterCarToParkingWithCheck(String id,String carNumber,int token);
	
	/**
	 * set Branch To Full State
	 * @param name
	 * @param token
	 */
	public void setBranchToFullState(String name, int token);
	
	/**
	 * set Branch Setup
	 * @param name
	 * @param token
	 */
	public void setBranchSetup(String name, int token);
	
	/**
	 * Enter Car To Parking
	 * @param id
	 * @param carNumber
	 * @param token
	 */
	public void EnterCarToParking(String id,String carNumber,int token);
	
	/**
	 * Cancel Pre Order
	 * @param id
	 * @param carNumber
	 * @param token
	 */
	public void CancelPreOrder(String id,String carNumber,int token);
	
	/**
	 * remove Car From Parking
	 * @param id
	 * @param carNumber
	 * @param token
	 */
	public void removeCarFromParking(String id,String carNumber,int token);
	
	/**
	 * customer Authentication
	 * @param customerId
	 * @param carNum
	 * @param requestToken
	 */
	public void customerAuthentication(String customerId, String carNum, int requestToken);
	
	/**
	 * is Valid User
	 * @param user
	 * @param token
	 */
	public void isValidUser(User user,int token);
	
	/**
	 * remove User
	 * @param currentUser
	 * @param token
	 */
	public void removeUser(User currentUser, int token);
}
