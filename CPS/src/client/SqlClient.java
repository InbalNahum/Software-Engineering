// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import common.CpsGlobals.ServerOperation;
import entity.PreOrderCustomer;
import java.io.*;
import actors.CasualCustomer;
import actors.MonthlySubscription;

public class SqlClient extends AbstractClient implements CpsServerCommunicator
{
	private static SqlClient instance = null;


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

	}


	public void handleMessageFromGuiClient(Object message)
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

	@Override
	public void addCasualCustomer(CasualCustomer casualCustomer) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.writeCasualCustomer);
		clientRequest.addTolist(casualCustomer);
		handleMessageFromGuiClient(clientRequest);
	}

	@Override
	public void addPreOrderCustomer(PreOrderCustomer preOrderCustomer) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.writeOneTimePreOrder);
		clientRequest.addTolist(preOrderCustomer);
		handleMessageFromGuiClient(clientRequest);
	}
	
	public void addMonthlySubscription(MonthlySubscription monthlySubscription) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.monthlySubscription);
		clientRequest.addTolist(monthlySubscription);
		handleMessageFromGuiClient(clientRequest);
	}
	
	public void renewMonthlySubscription(MonthlySubscription monthlySubscription) {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.renewMonthlySubscription);
		clientRequest.addTolist(monthlySubscription);
		handleMessageFromGuiClient(clientRequest);
	}
}
