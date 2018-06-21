package server;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import actors.CasualCustomer;
import entity.MonthlySubscription;
import client.ClientRequest;
import common.CpsGlobals;
import common.CpsGlobals.ServerOperation;
import entity.Branch;
import entity.BranchStateRequest;
import entity.ComplainObject;
import entity.Complaint;
import entity.CustomerComplaint;
import entity.PreOrderCustomer;
import entity.PriceList;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import parkingLogic.BranchPark;
import parkingLogic.BranchParkState;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class SQLServer extends AbstractServer 
{

	final public static int DEFAULT_PORT = 5555;


	public SQLServer(int port) 
	{
		super(port);
	}

	public void handleMessageFromClient(Object object, ConnectionToClient client)
	{
		Connection serverConnection = getSqlServerConnection();
		ClientRequest clientRequest = (ClientRequest) object;
		ServerResponse serverResponse = new ServerResponse();
		try {
			switch(clientRequest.getServerOperation()) {
			case branchListRequest:
				serverResponse = getBranchList(clientRequest,serverConnection);
				client.sendToClient(serverResponse);
				break;

			case priceListRequest:
				serverResponse = getPriceList(clientRequest,serverConnection);
				client.sendToClient(serverResponse);
				break;

			case customerComplaintRequest:
				serverResponse = getCustomerComplaintRequest(clientRequest,serverConnection);
				client.sendToClient(serverResponse);
				break;
			case tokenRequest:
				serverResponse = getNextToken(serverConnection);
				client.sendToClient(serverResponse);
				break;

			case writeCasualCustomer:
				writeCasualCustomer(clientRequest,serverConnection);
				sendOperationSuccess(clientRequest.getCommunicateToken(),
						client);
				break;

			case writeOneTimePreOrder:
				writeOneTimePreOrder(clientRequest,serverConnection);
				sendOperationSuccess(clientRequest.getCommunicateToken(),client);
				break;

			case employeeAuthentication:
				try {		     
					boolean result = employeeAuthentication(clientRequest,serverConnection);
					serverResponse.setServerOperation(ServerOperation.employeeAuthentication);
					serverResponse.addTolist(result);
					serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
					client.sendToClient(serverResponse);
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;

			case monthlySubscription:
				writeMonthlySubscription(clientRequest,serverConnection);
				sendOperationSuccess(clientRequest.getCommunicateToken(),client);
				break;

			case renewMonthlySubscription:
				writeRenewMonthlySubscription(clientRequest,serverConnection);
				sendOperationSuccess(clientRequest.getCommunicateToken(),client);
				break;
			case createNewBranch:

				writeNewBranch(clientRequest, serverConnection);
				sendOperationSuccess(clientRequest.getCommunicateToken(),client);
				break;

			case updateComplaintTable:

				updateComplaintTable(clientRequest, serverConnection);
				sendOperationSuccess(clientRequest.getCommunicateToken(),client);
				break;

			case updatePriceListTable:

				updatePriceListTable(clientRequest, serverConnection);
				sendOperationSuccess(clientRequest.getCommunicateToken(),client);
				break;
				
			case createNewComplain:
				writeNewComplain(clientRequest,serverConnection);
				sendOperationSuccess(clientRequest.getCommunicateToken(),client);
				break;

			case getBranchState:
				try {
					BranchParkState State = readBranchState(clientRequest, serverConnection);
					serverResponse = new ServerResponse();
					serverResponse.setServerOperation(ServerOperation.getBranchState);
					serverResponse.addTolist(State);
					serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
					client.sendToClient(serverResponse);
				} catch (SQLException | IOException | ClassNotFoundException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;

			default:
				break;
			}		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeNewComplain(ClientRequest clientRequest, Connection serverConnection) throws SQLException{
		CustomerComplaint customerComplaint = (CustomerComplaint) clientRequest.getObjects().get(0);
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.writeNewComplain);
		statement.setInt(1,customerComplaint.getCarNumber());
		statement.setInt(2,customerComplaint.getUserId());
		statement.setString(3,customerComplaint.getFirstName());
		statement.setString(4,customerComplaint.getLastName());
		statement.setString(5,customerComplaint.getDescription());
		Timestamp arrivingDate = new Timestamp(customerComplaint.getSendTime().getTime());
		statement.setTimestamp(6,arrivingDate);
		statement.setInt(7, customerComplaint.getStatus().ordinal());
		statement.executeUpdate();	
	}

	private ServerResponse getBranchList(ClientRequest clientRequest,Connection serverConnection) throws SQLException {
		PreparedStatement queryStatement = serverConnection.prepareStatement(CpsGlobals.getBranchList);
		ResultSet result = queryStatement.executeQuery();
		ServerResponse serverResponse = new ServerResponse();
		while(result.next()) {
			String toAdd = result.getString(2);
			serverResponse.addTolist(toAdd);
			serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
		}
		serverResponse.setServerOperation(ServerOperation.branchListRequest);
		return serverResponse;
	}

	private ServerResponse getPriceList(ClientRequest clientRequest,Connection serverConnection) throws SQLException {
		PreparedStatement queryStatement = serverConnection.prepareStatement(CpsGlobals.getPriceList);
		ResultSet result = queryStatement.executeQuery();
		ServerResponse serverResponse = new ServerResponse();
		while(result.next()) {
			String toAdd = result.getString(1);
			serverResponse.addTolist(toAdd);
			serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
		}
		serverResponse.setServerOperation(ServerOperation.priceListRequest);
		return serverResponse;
	}

	private ServerResponse getCustomerComplaintRequest(ClientRequest clientRequest,Connection serverConnection) throws SQLException {
		PreparedStatement queryStatement = serverConnection.prepareStatement(CpsGlobals.getCustomerComplaint);
		ResultSet result = queryStatement.executeQuery();
		ServerResponse serverResponse = new ServerResponse();
		while(result.next()) {
			String carNumber = result.getString(1);
			String id = result.getString(2);
			String firstName = result.getString(3);
			String lastName = result.getString(4);
			String description = result.getString(5);
			String 	createTime = result.getString(6);
			String status = result.getString(7);
			ComplainObject complainObject = new ComplainObject(firstName, lastName, id, 
					carNumber, createTime, description, status);
			serverResponse.addTolist(complainObject);
			serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
		}
		serverResponse.setServerOperation(ServerOperation.customerComplaintRequest);
		return serverResponse;
	}

	private ServerResponse getNextToken(Connection serverConnection) throws SQLException {
		PreparedStatement queryStatement = serverConnection.prepareStatement(CpsGlobals.fetchToken);
		ResultSet result = queryStatement.executeQuery();
		int tokenToRet = -1;
		if(result.next()) {
			tokenToRet = result.getInt(CpsGlobals.tokenName);
		}
		PreparedStatement updateStatement = serverConnection.prepareStatement(CpsGlobals.increaseToken);
		updateStatement.executeUpdate();
		ServerResponse serverResponse = new ServerResponse();
		serverResponse.setServerOperation(ServerOperation.tokenRequest);
		serverResponse.addTolist(tokenToRet);
		return serverResponse;
	}

	private boolean employeeAuthentication(ClientRequest clientRequest, Connection serverConnection) throws SQLException {
		boolean answer = false;
		int id = (int) clientRequest.getObjectAtIndex(0);
		String password = (String) clientRequest.getObjectAtIndex(1);
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.employeeAuthentication);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		if(result.next()) {
			String databasePass = result.getString(CpsGlobals.employeePassword);
			if(databasePass.equals(password)) {
				answer = true;
			}
		}
		return answer;
	}


	private void writeOneTimePreOrder(ClientRequest clientRequest, Connection serverConnection) throws SQLException {
		PreOrderCustomer preOrderCustomer = (PreOrderCustomer) clientRequest.getObjectAtIndex(0);
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.writeOneTimePreOrder);
		statement.setInt(1,preOrderCustomer.getId());
		statement.setInt(2, preOrderCustomer.getCarNumber());
		statement.setString(3, preOrderCustomer.getBranchName());
		Timestamp arrivingDate = new Timestamp(preOrderCustomer.getArriveTime().getTime());
		statement.setTimestamp(4, arrivingDate);
		Timestamp leavingDate = new Timestamp(preOrderCustomer.getLeaveTime().getTime());
		statement.setTimestamp(5, leavingDate);
		statement.setString(6, preOrderCustomer.getEmail());
		statement.executeUpdate();
	}

	private void writeCasualCustomer(ClientRequest clientRequest,Connection serverConnection) throws SQLException {
		CasualCustomer customer = (CasualCustomer) clientRequest.getObjectAtIndex(0);
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.writeCasualCustomer);
		statement.setInt(1,customer.getId());
		statement.setInt(2, customer.getCarNumber());
		statement.setString(3, customer.getEmail());
		Timestamp leavingDate = new Timestamp(customer.getLeaveTime().getTime());
		statement.setTimestamp(4, leavingDate);
		Timestamp arrivingDate = new Timestamp(customer.getArriveTime().getTime());
		statement.setTimestamp(5, arrivingDate);
		statement.executeUpdate();
	}

	private void writeMonthlySubscription(ClientRequest clientRequest, Connection serverConnection) throws SQLException {
		MonthlySubscription monthlySubscription = (MonthlySubscription) clientRequest.getObjects().get(0);
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.writeMonthlySubscription);
		statement.setInt(1,monthlySubscription.getId());
		statement.setInt(2, monthlySubscription.getCarNumber());
		Timestamp startingDate = new Timestamp(monthlySubscription.getStartingTime().getTime());
		statement.setTimestamp(3, startingDate);
		statement.setInt(4, 0);
		statement.executeUpdate();
	}

	private void writeRenewMonthlySubscription(ClientRequest clientRequest, Connection serverConnection) throws SQLException {
		MonthlySubscription monthlySubscription = (MonthlySubscription) clientRequest.getObjects().get(0);	
		PreparedStatement readStatement = serverConnection.prepareStatement(CpsGlobals.readRenewMonthlySubscription);
		readStatement.setInt(1, monthlySubscription.getId());
		ResultSet res = readStatement.executeQuery();
		if(res.next()) {
			PreparedStatement writeStatement = serverConnection.prepareStatement(CpsGlobals.writeRenewMonthlySubscription);
			writeStatement.setInt(1,monthlySubscription.getId());
			writeStatement.setInt(2, monthlySubscription.getCarNumber());
			Timestamp startingDate = new Timestamp(monthlySubscription.getStartingTime().getTime());
			writeStatement.setTimestamp(3, startingDate);
			writeStatement.setInt(4,0);
			writeStatement.executeUpdate();						
		} else {
			throw new SQLException("Error: row was not found");
		}
	}

	private void writeNewBranch(ClientRequest clientRequest, Connection serverConnection) throws SQLException, IOException {
		Branch branch = (Branch) clientRequest.getObjects().get(0);
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.writeNewBranch);
		statement.setInt(1,branch.getId());
		statement.setString(2,branch.getBranchName());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(branch.getCarPark());
		byte[] carParkAsBytes = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(carParkAsBytes);
		statement.setBinaryStream(3, bais, carParkAsBytes.length);
		statement.executeUpdate();
	}

	private void updateComplaintTable(ClientRequest clientRequest, Connection serverConnection) throws SQLException, IOException {
		ComplainObject complainObject = (ComplainObject) clientRequest.getObjects().get(0);
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.updateMonthlySubscriptionTable);
		statement.setInt(1,Integer.parseInt(complainObject.getRefund()));
		statement.setInt(2, Integer.parseInt(complainObject.getCarNumber()));
		statement.executeUpdate();

		statement = serverConnection.prepareStatement(CpsGlobals.updateComplainTable);
		statement.setInt(1,1);
		statement.setInt(2, Integer.parseInt(complainObject.getCarNumber()));
		statement.executeUpdate();
	}

	private void updatePriceListTable(ClientRequest clientRequest, Connection serverConnection) throws SQLException, IOException {
		PriceList priceList = (PriceList) clientRequest.getObjects().get(0);
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.updatePriceListTable);
		statement.setInt(1,Integer.parseInt(priceList.getCasualParking()));
		statement.setInt(2, 1);
		statement.executeUpdate();
		
		statement.setInt(1,Integer.parseInt(priceList.getPreOrderParking()));
		statement.setInt(2, 2);
		statement.executeUpdate();
		
		statement.setInt(1,Integer.parseInt(priceList.getMonthlySubscription()));
		statement.setInt(2, 3);
		statement.executeUpdate();
	}

	private BranchParkState readBranchState(ClientRequest clientRequest, Connection serverConnection) throws SQLException, IOException, ClassNotFoundException {
		BranchStateRequest request = (BranchStateRequest) clientRequest.getObjects().get(0);
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.readBranch);
		statement.setString(1,request.getName());
		ResultSet result = statement.executeQuery();
		BranchPark park = null;
		while (result.next()) {
			byte[] branchAsBytes = (byte[]) result.getObject(3);
			ByteArrayInputStream baip = new ByteArrayInputStream(branchAsBytes);
			ObjectInputStream ois = new ObjectInputStream(baip);
			park = (BranchPark) ois.readObject();
			return park.getBranchState();
		}
		return null;
	}


	private String buildSQLErrorMessage(SQLException e){
		String toRet = "SQLException: " + e.getMessage() + "\n";
		toRet += "SQLState: " + e.getSQLState() + "\n";
		toRet += "VendorError: " + e.getErrorCode() + "\n";
		return toRet;
	}

	private Connection getSqlServerConnection() {
		try 
		{
			Class.forName(CpsGlobals.driver).newInstance();
			String Url = CpsGlobals.url;
			String user = CpsGlobals.user;
			String password = CpsGlobals.password;
			Connection connection = DriverManager.getConnection(Url,user,password);
			return connection;
		}
		catch(Exception ex){
			System.out.println("Connection Failed: " + ex.getMessage());
			return null;
		}
	}

	private void sendOperationSuccess(int requestToken,
			ConnectionToClient client) throws IOException {
		ServerResponse serverResponse = new ServerResponse();
		serverResponse.setServerOperation(ServerOperation.feedback);
		serverResponse.addTolist(CpsGlobals.operationSuccess);
		serverResponse.setCommunicateToken(requestToken);
		client.sendToClient(serverResponse);
	}

	private void sendOperationFailure(int requestToken,ConnectionToClient client) throws IOException {
		ServerResponse serverResponse = new ServerResponse();
		serverResponse.setServerOperation(ServerOperation.feedback);
		serverResponse.addTolist(CpsGlobals.operationFailure);
		serverResponse.setCommunicateToken(requestToken);
		client.sendToClient(serverResponse);
	}

	protected void serverStarted()
	{
		System.out.println
		("Server listening for connections on port " + getPort());
	}


	protected void serverStopped()
	{
		System.out.println
		("Server has stopped listening for connections.");
	}


	public static void main(String[] args) 
	{
		int port = 0; //Port to listen on

		try
		{
			port = Integer.parseInt(args[0]); //Get port from command line
		}
		catch(Throwable t)
		{
			port = DEFAULT_PORT; //Set port to 5555
		}

		SQLServer sv = new SQLServer(port);

		try 
		{
			sv.listen(); //Start listening for connections
		} 
		catch (Exception ex) 
		{
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
//End of EchoServer class
