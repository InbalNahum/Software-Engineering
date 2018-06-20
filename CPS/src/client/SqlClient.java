// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import actors.CasualCustomer;
import actors.MonthlySubscription;
import common.CpsGlobals;
import common.CpsGlobals.ServerOperation;
import common.CpsServerCommunicator;
import entity.Branch;
import entity.BranchStateRequest;
import entity.PreOrderCustomer;
import ocsf.client.AbstractClient;
import server.ServerResponse;

public class SqlClient extends AbstractClient implements CpsServerCommunicator
{
	private static SqlClient instance = null;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Map<Integer,ServerResponse> responseMap = new HashMap();

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

	public void handleMessageFromServer(Object msg) 
	{
		ServerResponse serverResponse = (ServerResponse) msg;
		responseMap.put(serverResponse.getCommunicateToken(), serverResponse);
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

	public void quit()
	{
		try
		{
			closeConnection();
		}
		catch(IOException e) {}
		System.exit(0);
	}
	
	public Optional<ServerResponse> getResponseByToken(int token) {
		Optional<ServerResponse> toRet;
		synchronized (this) {
			if(responseMap.containsKey(token)) {
				toRet = Optional.ofNullable(responseMap.get(token));
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
	public void employeeAuthentication(String id, String password,int token) throws InterruptedException {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.employeeAuthentication);
		clientRequest.addTolist(Integer.parseInt(id));
		clientRequest.addTolist(password);
		clientRequest.setCommunicateToken(token);
		handleMessageFromGuiClient(clientRequest);
	}
	
	@Override
	public void getBranchState(BranchStateRequest request, int token) throws InterruptedException {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.getBranchState);
		clientRequest.addTolist(request);
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
}
