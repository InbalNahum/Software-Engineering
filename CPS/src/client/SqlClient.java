// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;
import java.util.Date;

import actors.CasualCustomer;

public class SqlClient extends AbstractClient implements CpsServerCommunicator
{

  
  public SqlClient(String host, int port) 
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
public void addCasualCustomer(CasualCustomer customer) {
	
	
}

@Override
public void addCasualParkingOrder(Date arriveTime, String branchName,
		int carNumber,String email, int id, Date leaveTime) {
   	
}

}
