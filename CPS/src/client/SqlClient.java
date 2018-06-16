// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import actors.CasualCustomer;
import common.CpsGlobals;
import common.CpsGlobals.ServerOperation;
import common.CpsServerCommunicator;
import entity.PreOrderCustomer;
import ocsf.client.AbstractClient;
import server.ServerResponse;

public class SqlClient extends AbstractClient implements CpsServerCommunicator
{
	private static SqlClient instance = null;
	List<ServerResponse> responseQueue = null;

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
		if(responseQueue == null) {
			responseQueue = new ArrayList<ServerResponse>();
		}
		ServerResponse serverResponse = (ServerResponse) msg;
		
		responseQueue.add(serverResponse);
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
	
	public ServerResponse getLastResponse() {
		synchronized (this) {
			return (responseQueue == null) ? null : responseQueue.get(responseQueue.size()-1);
		}
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

	@Override
	public void employeeAuthentication(String id, String password) throws InterruptedException {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setServerOperation(ServerOperation.employeeAuthentication);
		clientRequest.addTolist(Integer.parseInt(id));
		clientRequest.addTolist(password);
		handleMessageFromGuiClient(clientRequest);
	}

}
