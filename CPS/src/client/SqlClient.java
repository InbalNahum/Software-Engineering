// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import actors.CasualCustomer;
import actors.User;
import entity.MonthlySubscription;
import common.CpsGlobals;
import common.CpsGlobals.ServerOperation;
import common.CpsServerCommunicator;
import entity.Branch;
import entity.BranchParkParameters;
import entity.BranchStateRequest;
import entity.ComplainObject;
import entity.CustomerComplaint;
import entity.PreOrderCustomer;
import entity.PriceList;
import ocsf.client.AbstractClient;
import server.ServerResponse;

/**
 * Handle messages from client to server and vice versa 
 * @author OmerG
 *
 */
public class SqlClient extends AbstractClient implements CpsServerCommunicator
{
	private static SqlClient instance = null;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Map<Integer,ServerResponse> responseMap = new HashMap();
	List<Integer> freeTokenList = new ArrayList<Integer>();

	/**
	 * 
	 * @return SqlClient
	 * @throws IOException
	 */
	public static SqlClient getInstance() throws IOException {
		if(instance == null) {
			instance = new SqlClient(CpsGlobals.host, CpsGlobals.port);			   
		}
		return instance;
	}

	
	private SqlClient(String host, int port) 
			throws IOException 
	{
		super(host, port); 
		openConnection();
	}

	/**
	 * handle Message From Server
	 * @param msg
	 */
	public void handleMessageFromServer(Object msg) 
	{
		ServerResponse serverResponse = (ServerResponse) msg;
		if(serverResponse.getServerOperation().equals(ServerOperation.tokenRequest)) {
			int toAdd = (int) serverResponse.getObjectAtIndex(0);
			freeTokenList.add(toAdd);
		}
		else {			
			responseMap.put(serverResponse.getCommunicateToken(), serverResponse);
		}
	}


	private void handleMessageFromGuiClient(Object message)
	{
		try
		{	
			sendToServer(message);
		}
		catch(IOException e)
		{
			quit();
		}
	}

	/**
	 * close Connection
	 */
	public void quit()
	{
		try
		{
			closeConnection();
		}
		catch(IOException e) {}
		System.exit(0);
	}

	/**
	 * get Response By Token
	 * @param token
	 * @return
	 */
	public Optional<ServerResponse> getResponseByToken(int token) {
		Optional<ServerResponse> toRet;
		synchronized (this) {
			if(responseMap.containsKey(token)) {
				toRet = Optional.ofNullable(responseMap.get(token));
				responseMap.remove(token);
			}
			else {
				toRet = Optional.empty();
			}
		}
		return toRet;
	}

	@Override
	public void addCasualCustomer(CasualCustomer casualCustomer, int token) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.writeCasualCustomer);
		clientRequest.addTolist(casualCustomer);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}

	@Override
	public void addPreOrderCustomer(PreOrderCustomer preOrderCustomer,int token) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.writeOneTimePreOrder);
		clientRequest.addTolist(preOrderCustomer);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void EnterCarToParkingWithCheck(String id,String carNumber,int token) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.EnterCarToParkingWithCheck);
		clientRequest.addTolist(id);
		clientRequest.addTolist(carNumber);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void removeCarFromParking(String id,String carNumber,int token) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.removeCarFromParking);
		clientRequest.addTolist(id);
		clientRequest.addTolist(carNumber);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void CancelPreOrder(String id,String carNumber,int token) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.cancelPreOrder);
		clientRequest.addTolist(id);
		clientRequest.addTolist(carNumber);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void EnterCarToParking(String id,String carNumber,int token) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.EnterCarToParking);
		clientRequest.addTolist(id);
		clientRequest.addTolist(carNumber);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}


	@Override
	public void employeeAuthentication(String id, String password,int token)  {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.employeeAuthentication);
		clientRequest.addTolist(Integer.parseInt(id));
		clientRequest.addTolist(password);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}

	@Override
	public void getBranchState(BranchStateRequest request, int token)  {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.getBranchState);
		clientRequest.addTolist(request);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void getBranchParkParameters(String name, int token)  {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.getBranchParkParameters);
		clientRequest.addTolist(name);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void setOutOfOrderParking(String name, BranchParkParameters parameters, int token)  {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.setOutOfOrderParking);
		clientRequest.addTolist(name);
		clientRequest.addTolist(parameters);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void setBranchToFullState(String name, int token)  {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.setBranchToFullState);
		clientRequest.addTolist(name);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void setBranchSetup(String name, int token)  {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.setBranchSetup);
		clientRequest.addTolist(name);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void setSavedParking(String name, BranchParkParameters parameters, int token)  {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.setSavedParking);
		clientRequest.addTolist(name);
		clientRequest.addTolist(parameters);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}


	@Override
	public void addMonthlySubscription(MonthlySubscription monthlySubscription,int token) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.monthlySubscription);
		clientRequest.addTolist(monthlySubscription);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}

	@Override
	public void renewMonthlySubscription(MonthlySubscription monthlySubscription,int token) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.renewMonthlySubscription);
		clientRequest.addTolist(monthlySubscription);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}

	@Override
	public void addBranch(Branch branch,int token) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.createNewBranch);
		clientRequest.addTolist(branch);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}

	@Override
	public void updateComplaintTable(ComplainObject complainObject, int token)
	{
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.updateComplaintTable);
		clientRequest.addTolist(complainObject);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}

	@Override
	public void updatePriceListTable(PriceList priceList, int token)
	{
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.updatePriceListTable);
		clientRequest.addTolist(priceList);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void sendTokenRequest() {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.tokenRequest);
		handleMessageFromGuiClient(clientRequest);
	}

	@Override
	public Optional<Integer> fetchToken() {
		Optional<Integer> toRet;
		synchronized (this) {
			if(freeTokenList.isEmpty()) {
				toRet = Optional.empty();
			}
			else {
				toRet = Optional.ofNullable(freeTokenList.get(0));
				freeTokenList.remove(0);
			}
		}
		return toRet;
	}

	@Override
	public void addComplain(CustomerComplaint customerComplaint, int token) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.createNewComplain);
		clientRequest.addTolist(customerComplaint);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void sendBranchListRequest(int requestToken) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setCommunicateToken(requestToken);
		clientRequest.setServerOperation(ServerOperation.branchListRequest);
		handleMessageFromGuiClient(clientRequest);
	}

	@Override
	public void sendBranchByIdRequest(String id,int requestToken) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.addTolist(id);
		clientRequest.setCommunicateToken(requestToken);
		clientRequest.setServerOperation(ServerOperation.getBranchById);
		handleMessageFromGuiClient(clientRequest);
	}

	public void sendPriceListRequest(int requestToken) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setCommunicateToken(requestToken);
		clientRequest.setServerOperation(ServerOperation.priceListRequest);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void sendCustomerComplaintRequest(int requestToken) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setCommunicateToken(requestToken);
		clientRequest.setServerOperation(ServerOperation.customerComplaintRequest);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void sendUserMessagesRequest(String userId, String userCarNum, int requestToken) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setCommunicateToken(requestToken);
		clientRequest.addTolist(userId);
		clientRequest.addTolist(userCarNum);
		clientRequest.setServerOperation(ServerOperation.getUserMessages);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void customerAuthentication(String customerId, String carNum, int requestToken) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.customerAuthentication);
		clientRequest.addTolist(Integer.parseInt(customerId));
		clientRequest.addTolist(Integer.parseInt(carNum));
		clientRequest.setCommunicateToken(requestToken);
		handleMessageFromGuiClient(clientRequest);	
	}

	@Override
	public void isValidUser(User user,int token) {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setCommunicateToken(token);
        clientRequest.setServerOperation(ServerOperation.addNewUser);
        clientRequest.addTolist(user);
        handleMessageFromGuiClient(clientRequest);
	}

	@Override
	public void removeUser(User currentUser, int token) {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setCommunicateToken(token);
        clientRequest.setServerOperation(ServerOperation.removeUser);
        clientRequest.addTolist(currentUser);
        handleMessageFromGuiClient(clientRequest);
	}

}
