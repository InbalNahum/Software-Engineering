package server;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.mysql.jdbc.Util;

import actors.CasualCustomer;
import actors.MonthlySubscription;
import client.ClientRequest;
import common.CpsGlobals;
import entity.PreOrderCustomer;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;


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
	//Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	//Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public SQLServer(int port) 
	{
		super(port);
	}


	//Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param object The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient
	(Object object, ConnectionToClient client)
	{
		Connection sqlServer = getSqlServerConnection();
		ClientRequest clientRequest = (ClientRequest) object;

		switch(clientRequest.getServerOperation()) {
		case writeCasualCustomer:
			try {
				writeCasualCustomer(clientRequest,sqlServer);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case writeOneTimePreOrder:
			try {
				writeOneTimePreOrder(clientRequest,sqlServer);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case monthlySubscription:
			try {
				writeMonthlySubscription(clientRequest,sqlServer);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		
		case renewMonthlySubscription:
			try {
				writeRenewMonthlySubscription(clientRequest,sqlServer);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	private void writeOneTimePreOrder(ClientRequest clientRequest, Connection serverConnection) throws SQLException {
		PreOrderCustomer preOrderCustomer = (PreOrderCustomer) clientRequest.getObjects().get(0);
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
		CasualCustomer customer = (CasualCustomer) clientRequest.getObjects().get(0);
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
			writeStatement.executeUpdate();						
		} else {
			throw new SQLException("Error: row was not found");
		}
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


	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server starts listening for connections.
	 */
	protected void serverStarted()
	{
		System.out.println
		("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server stops listening for connections.
	 */
	protected void serverStopped()
	{
		System.out.println
		("Server has stopped listening for connections.");
	}

	//Class methods ***************************************************

	/**
	 * This method is responsible for the creation of 
	 * the server instance (there is no UI in this phase).
	 *
	 * @param args[0] The port number to listen on.  Defaults to 5555 
	 *          if no argument is entered.
	 */
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
