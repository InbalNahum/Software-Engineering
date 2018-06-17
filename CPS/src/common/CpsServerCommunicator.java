package common;

import actors.CasualCustomer;
import actors.MonthlySubscription;
import entity.PreOrderCustomer;

public interface CpsServerCommunicator {
	
	public void addCasualCustomer(CasualCustomer customer);
	public void addPreOrderCustomer(PreOrderCustomer preOrderCustomer);
    public void employeeAuthentication(String userName, String password) throws InterruptedException;
	public void addMonthlySubscription(MonthlySubscription monthlySubscription);
	public void renewMonthlySubscription(MonthlySubscription monthlySubscription);
}
