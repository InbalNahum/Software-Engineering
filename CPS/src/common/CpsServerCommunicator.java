package common;

import java.util.Date;

import actors.CasualCustomer;
import entity.PreOrderCustomer;

public interface CpsServerCommunicator {
	
	public void addCasualCustomer(CasualCustomer customer);
	public void addPreOrderCustomer(PreOrderCustomer preOrderCustomer);
	public void addCasualParkingOrder(Date arriveTime, String branchName,
			int carNumber,String email, int id, Date leaveTime);

}
