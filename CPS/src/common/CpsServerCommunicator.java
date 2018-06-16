package common;

import actors.CasualCustomer;
import entity.PreOrderCustomer;

public interface CpsServerCommunicator {
	
	public void addCasualCustomer(CasualCustomer customer);
	public void addPreOrderCustomer(PreOrderCustomer preOrderCustomer);
    public void employeeAuthentication(String userName, String password) throws InterruptedException;
}
