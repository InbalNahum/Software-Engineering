package server;

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
import java.util.Date;
import java.util.concurrent.TimeUnit;

import actors.CasualCustomer;
import client.ClientRequest;
import common.CpsGlobals;
import common.ServiceMethods;
import common.CpsGlobals.ServerOperation;
import common.CpsGlobals.parkingState;
import entity.Branch;
import entity.BranchParkParameters;
import entity.BranchStateRequest;
import entity.ComplainObject;
import entity.CustomerComplaint;
import entity.MonthlySubscription;
import entity.PreOrderCustomer;
import entity.PriceList;
import javafx.scene.control.Alert.AlertType;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import parkingLogic.BranchPark;
import parkingLogic.BranchParkState;
import parkingLogic.Car;
import parkingLogic.Location;
import parkingLogic.ParkingFloor;



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
		int requestToken = clientRequest.getCommunicateToken();
		ServerResponse serverResponse = new ServerResponse();
		try {
			switch(clientRequest.getServerOperation()) {
			case customerAuthentication:
				serverResponse = customerAuthentication(clientRequest,serverConnection);
				client.sendToClient(serverResponse);
				break;
			case getUserMessages:
				serverResponse = getUserMessages(clientRequest,serverConnection);
				client.sendToClient(serverResponse);
				break;
				
			case branchListRequest:
				serverResponse = getBranchList(clientRequest,serverConnection);
				client.sendToClient(serverResponse);
				break;

				
			case getBranchById:
				serverResponse = getBranchListById(clientRequest, serverConnection);
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
				String message = writeCasualCustomer(clientRequest,serverConnection);
				serverResponse.setServerOperation(ServerOperation.writeCasualCustomer);
				serverResponse.addTolist(message);
				serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
				client.sendToClient(serverResponse);
				break;

			case writeOneTimePreOrder:
				writeOneTimePreOrder(clientRequest,serverConnection);
				sendOperationSuccess(clientRequest.getCommunicateToken(),client);
				break;

			case employeeAuthentication:	     

				boolean isEmployee = employeeAuthentication(clientRequest,serverConnection);
				serverResponse.setServerOperation(ServerOperation.employeeAuthentication);
				serverResponse.addTolist(isEmployee);
				serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
				client.sendToClient(serverResponse);
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

			
		case getBranchParkParameters:
				BranchParkParameters parameters = readBranchParkParameters(clientRequest, serverConnection);
				serverResponse = new ServerResponse();
				serverResponse.setServerOperation(ServerOperation.getBranchParkParameters);
				serverResponse.addTolist(parameters);
				serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
				client.sendToClient(serverResponse);
			break;
			
		case setOutOfOrderParking:
				writeOutOfOrderParking(clientRequest, serverConnection);
				sendOperationSuccess(clientRequest.getCommunicateToken(),client);

			break;	
			
		case setSavedParking:
				writeSavedParking(clientRequest, serverConnection);
				sendOperationSuccess(clientRequest.getCommunicateToken(),client);
			break;	
				
		case getBranchState:
				BranchParkState State = readBranchState(clientRequest, serverConnection);
				serverResponse = new ServerResponse();
				serverResponse.setServerOperation(ServerOperation.getBranchState);
				serverResponse.addTolist(State);
				serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
				client.sendToClient(serverResponse);
			break;
			
		case EnterCarToParkingWithCheck:
			Object[] answer = EnterCarToParkingWithCheck(clientRequest, serverConnection);
			serverResponse = new ServerResponse();
			serverResponse.setServerOperation(ServerOperation.EnterCarToParkingWithCheck);
			serverResponse.addTolist(answer[0]);
			serverResponse.addTolist(answer[1]);
			serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
			client.sendToClient(serverResponse);
		break;

		case setBranchToFullState:
			setBranchToFullState(clientRequest, serverConnection);
			sendOperationSuccess(clientRequest.getCommunicateToken(),client);
		break;	
		
		case setBranchSetup:
			setBranchSetup(clientRequest, serverConnection);
			sendOperationSuccess(clientRequest.getCommunicateToken(),client);
		break;	
		
		case EnterCarToParking:
			String message2 = EnterCarToParking(clientRequest, serverConnection);
			serverResponse.setServerOperation(ServerOperation.writeCasualCustomer);
			serverResponse.addTolist(message2);
			serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
			client.sendToClient(serverResponse);
		break;	

			default:
				break;
			}		
		}catch (Exception e) {
			try {
				sendOperationFailure(requestToken,client);
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private ServerResponse customerAuthentication(ClientRequest clientRequest, Connection serverConnection) throws SQLException {
		ServerResponse serverResponse = new ServerResponse();
		serverResponse .setServerOperation(ServerOperation.customerAuthentication);
		serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
		boolean isSubscriber = false;
		int userId = (int) clientRequest.getObjectAtIndex(0);
        int userCarNum = (int) clientRequest.getObjectAtIndex(1);
		PreparedStatement subscriberStatement = serverConnection.prepareStatement(CpsGlobals.isSubscriber);
		subscriberStatement.setInt(1, userId);
		subscriberStatement.setInt(2, userCarNum);
		ResultSet subscriberResult = subscriberStatement.executeQuery();
        if(subscriberResult.next()) {
        	isSubscriber = true;
        	serverResponse.addTolist(isSubscriber);
        	Timestamp creationTime = subscriberResult.getTimestamp(3);
        	Timestamp currentTime = new Timestamp(new Date().getTime());
            if(creationTime.after(currentTime)) {
            	serverResponse.addTolist(CpsGlobals.expiredOrNotStart);
            }
            else {
            	long daysDiff = TimeUnit.DAYS.convert(currentTime.getTime()-creationTime.getTime()
            			, TimeUnit.MILLISECONDS);
            	if(daysDiff >= CpsGlobals.SubscriptionDays) {
                	serverResponse.addTolist(CpsGlobals.expiredOrNotStart);
            	}
            	else {
                	serverResponse.addTolist(CpsGlobals.inTokef);
            	}
            }

        }
        else {
        	serverResponse.addTolist(isSubscriber);
        }
        return serverResponse;
	}

	private ServerResponse getUserMessages(ClientRequest clientRequest, Connection serverConnection) throws SQLException {
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
        serverResponse.setServerOperation(ServerOperation.getUserMessages);
		String userId = (String) clientRequest.getObjectAtIndex(0);
        String userCarNum = (String) clientRequest.getObjectAtIndex(1);
		PreparedStatement renewStatement = serverConnection.prepareStatement(CpsGlobals.subscriptionRenewalReminder);
		renewStatement.setString(1, userId);
		renewStatement.setString(2, userCarNum);
		ResultSet renewResult = renewStatement.executeQuery();
        if(renewResult.next()) {
        	//case user is subscriber
        	Timestamp createTime = renewResult.getTimestamp(1);
        	Date today = new Date();
        	Timestamp currentTime = new Timestamp(today.getTime());
        	long diff = currentTime.getTime() - createTime.getTime();
        	long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        	long daysLeft = CpsGlobals.SubscriptionDays - daysDiff;
        	if(daysLeft < CpsGlobals.daysInWeek) {
        		String renewReminder = String.format(CpsGlobals.subscriptionRenewalMessageFormat,
        				daysLeft);
        		serverResponse.addTolist(renewReminder);
        	}
        }
		PreparedStatement complaintStatement = serverConnection.prepareStatement(CpsGlobals.getClientComplaint);
		complaintStatement.setString(1, userId);
		complaintStatement.setString(2, userCarNum);
		ResultSet complaintResult = complaintStatement.executeQuery();
		if(complaintResult.next()) {
			int status = complaintResult.getInt(CpsGlobals.complaintStatus);
			int promotional = complaintResult.getInt(CpsGlobals.complaintPromotional);
			String statusMessage = (status == 0) ? "Wait for manager treatment" : "Closed";
			String promotionalMessage = (promotional == 0) ? "Your account has not been credited" : "Your account has been credited with "+ promotional;
		    String complaintMessage = String.format(CpsGlobals.complaintFormat,
		    		complaintResult.getString(CpsGlobals.complainDescription),
		    		statusMessage, promotionalMessage);
		    serverResponse.addTolist(complaintMessage);
		} 
        return serverResponse;
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
	
	private ServerResponse getBranchListById(ClientRequest clientRequest,Connection serverConnection) throws SQLException {
		String id = (String)clientRequest.getObjects().get(0);
		PreparedStatement queryStatement = serverConnection.prepareStatement(CpsGlobals.getBranchListById);
		queryStatement.setString(1, id);
		ResultSet result = queryStatement.executeQuery();
		ServerResponse serverResponse = new ServerResponse();
		while(result.next()) {
		String toAdd = result.getString(1);
		if(toAdd.equals("None"))
			return getBranchList(clientRequest, serverConnection);
		serverResponse.addTolist(toAdd);
		serverResponse.setCommunicateToken(clientRequest.getCommunicateToken());
		}
		serverResponse.setServerOperation(ServerOperation.getBranchById);
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
			String refaunt = result.getString(8);
			ComplainObject complainObject = new ComplainObject(firstName, lastName, id, 
					carNumber, createTime, description, status, refaunt);
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

	private String writeCasualCustomer(ClientRequest clientRequest,Connection serverConnection) throws SQLException, ClassNotFoundException, IOException {
		if(checkIfBranchIsFull("Tel-Aviv", serverConnection))
			return "Sorry, the car parking is full - please try in other branch";
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
		writeRealTimeParking("Tel-Aviv",new Car(customer.getId(),customer.getCarNumber()),serverConnection);
		return CpsGlobals.operationSuccess;
	}
	
	private void writeRealTimeParking(String name, Car car, Connection serverConnection) throws SQLException, IOException, ClassNotFoundException {
		Branch branch = readBranch(serverConnection, name);
		branch.getCarPark().enterCarToPark(car);
		writeBranchUpdate(serverConnection, branch.getId(), name, branch.getCarPark());
	}
	
	private boolean checkIfBranchIsFull(String name, Connection serverConnection) throws SQLException, IOException, ClassNotFoundException {
		Branch branch = readBranch(serverConnection, name);
		return branch.getCarPark().isFull();	
	}
	
	private Object[] EnterCarToParkingWithCheck(ClientRequest clientRequest, Connection serverConnection) throws SQLException, NumberFormatException, ClassNotFoundException, IOException {
		String id = (String) clientRequest.getObjects().get(0);
		String carNumber = (String) clientRequest.getObjects().get(1);
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.readBranchFromPreOrder);
		statement.setString(1, id);
		ResultSet result = statement.executeQuery();
		Object[] toRet = new Object[2];
		String Message = "";
		toRet[0] = Boolean.FALSE;
		toRet[1] = Message;
		if (result.next()) {
			writeRealTimeParking(result.getString(1), new Car(Integer.parseInt(id),
					Integer.parseInt(carNumber)), serverConnection);
			Timestamp arriveFromOrder = result.getTimestamp(2);
			Date today = new Date();
        	Timestamp RealArrive = new Timestamp(today.getTime());
        	long diff = RealArrive.getTime() - arriveFromOrder.getTime();
        	long hoursDiff = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
        	if(hoursDiff > 0) 
        		Message = "We Charged your Account for 20% extra money because you are late";
    		toRet[0] = Boolean.TRUE;
    		toRet[1] = Message;		
			return toRet;
		}
		return toRet;
	}
	
	private String EnterCarToParking(ClientRequest clientRequest, Connection serverConnection) throws SQLException, NumberFormatException, ClassNotFoundException, IOException {
		String id = (String) clientRequest.getObjects().get(0);
		String carNumber = (String) clientRequest.getObjects().get(1);
		if(checkIfBranchIsFull("Tel-Aviv", serverConnection))
			return "Sorry, the car parking is full - please try in other branch";
		writeRealTimeParking("Tel-Aviv", new Car(Integer.parseInt(id),
				Integer.parseInt(carNumber)), serverConnection);		
		return CpsGlobals.operationSuccess;
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
		PreparedStatement statement1 = serverConnection.prepareStatement(CpsGlobals.getUserAccount);
		String carNum = complainObject.getCarNumber();
		statement1.setString(1, carNum);
		ResultSet accountResult = statement1.executeQuery();
		int accountValue = 0;
		if(accountResult.next()) {
			accountValue = accountResult.getInt(4);
		}
		PreparedStatement statement2 = serverConnection.prepareStatement(CpsGlobals.updateMonthlySubscriptionTable);
		statement2.setInt(1,Integer.parseInt(complainObject.getRefund())+accountValue);
		statement2.setInt(2, Integer.parseInt(complainObject.getCarNumber()));
		statement2.executeUpdate();

		statement2 = serverConnection.prepareStatement(CpsGlobals.updateComplainTable);
		statement2.setInt(1,1);
		statement2.setInt(2,Integer.parseInt(complainObject.getRefund()));
		statement2.setInt(3, Integer.parseInt(complainObject.getCarNumber()));
		statement2.executeUpdate();
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
	
	private BranchParkParameters readBranchParkParameters(ClientRequest clientRequest, Connection serverConnection) throws SQLException, IOException, ClassNotFoundException {
		String name = (String) clientRequest.getObjects().get(0);
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.readBranch);
		statement.setString(1,name);
		ResultSet result = statement.executeQuery();
		BranchPark park = null;
		while (result.next()) {
		      byte[] branchAsBytes = (byte[]) result.getObject(3);
		      ByteArrayInputStream baip = new ByteArrayInputStream(branchAsBytes);
		      ObjectInputStream ois = new ObjectInputStream(baip);
		      park = (BranchPark) ois.readObject();
		    }
		return new BranchParkParameters(park.getNumOfFloors(),3 ,park.getColumns());
	}
	
	private void setBranchToFullState(ClientRequest clientRequest, Connection serverConnection) throws SQLException, IOException, ClassNotFoundException {
		String name = (String) clientRequest.getObjects().get(0);
		Branch branch = readBranch(serverConnection, name);
		branch.getCarPark().setFull(Boolean.TRUE);
		writeBranchUpdate(serverConnection, branch.getId(), name, branch.getCarPark());
	}
	
	private void setBranchSetup(ClientRequest clientRequest, Connection serverConnection) throws SQLException, IOException, ClassNotFoundException {
		String name = (String) clientRequest.getObjects().get(0);
		Branch branch = readBranch(serverConnection, name);
		writeBranchUpdate(serverConnection, branch.getId(), name,
				new BranchPark(branch.getCarPark().getColumns()));
	}
	
	private void writeOutOfOrderParking(ClientRequest clientRequest, Connection serverConnection) throws SQLException, IOException, ClassNotFoundException {
		String name = (String) clientRequest.getObjects().get(0);
		BranchParkParameters parameters = (BranchParkParameters) clientRequest.getObjects().get(1);
		Branch branch = readBranch(serverConnection, name);
		branch.getCarPark().setParkingState(new Location(parameters.getFloor(), parameters.getRaw(),
				parameters.getColumn()[0]),parkingState.outOfOrder);
		writeBranchUpdate(serverConnection, branch.getId(), name, branch.getCarPark());
	}
	
	private void writeSavedParking(ClientRequest clientRequest, Connection serverConnection) throws SQLException, IOException, ClassNotFoundException {
		String name = (String) clientRequest.getObjects().get(0);
		BranchParkParameters parameters = (BranchParkParameters) clientRequest.getObjects().get(1);
		Branch branch = readBranch(serverConnection, name);
		branch.getCarPark().setParkingState(new Location(parameters.getFloor(), parameters.getRaw(),
				parameters.getColumn()[0]),parkingState.unAvailable);
		writeBranchUpdate(serverConnection, branch.getId(), name, branch.getCarPark());
	}
	
	private Branch readBranch(Connection serverConnection, String name) throws SQLException, ClassNotFoundException, IOException {
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.readBranch);
		statement.setString(1,name);
		ResultSet result = statement.executeQuery();
		BranchPark park = null;
		int id = 0;
		while (result.next()) {
			  id = result.getInt(1);
		      byte[] branchAsBytes = (byte[]) result.getObject(3);
		      ByteArrayInputStream baip = new ByteArrayInputStream(branchAsBytes);
		      ObjectInputStream ois = new ObjectInputStream(baip);
		      park = (BranchPark) ois.readObject();
		    }
		return new Branch(id, name, park);
	}
	
	private void writeBranchUpdate(Connection serverConnection, int id, String name, BranchPark park) throws SQLException, IOException {
		PreparedStatement statement = serverConnection.prepareStatement(CpsGlobals.writeBranchUpdate);
		statement.setInt(1, id);
		statement.setString(2, name);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(baos);
	    oos.writeObject(park);
	    byte[] carParkAsBytes = baos.toByteArray();
	    ByteArrayInputStream bais = new ByteArrayInputStream(carParkAsBytes);
		statement.setBinaryStream(3, bais, carParkAsBytes.length);	
		statement.executeUpdate();
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

	private void sendOperationSuccess(int requestToken,ConnectionToClient client) throws IOException {
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
