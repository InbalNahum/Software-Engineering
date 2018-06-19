package common;

import java.util.Optional;

import actors.CasualCustomer;
import entity.MonthlySubscription;
import entity.Branch;
import entity.PreOrderCustomer;

public interface CpsServerCommunicator {
	
	public void sendTokenRequest();
	public Optional<Integer> fetchToken();
	public void addCasualCustomer(CasualCustomer customer, int token);
	public void addPreOrderCustomer(PreOrderCustomer preOrderCustomer, int token);
    public void employeeAuthentication(String userName, String password, int token) throws InterruptedException;
	public void addMonthlySubscription(MonthlySubscription monthlySubscription, int token);
	public void renewMonthlySubscription(MonthlySubscription monthlySubscription, int token);
	public void addBranch(Branch branch,int token);
}
