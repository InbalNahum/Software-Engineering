package common;

import java.util.Optional;

import actors.CasualCustomer;
import entity.Branch;
import entity.BranchStateRequest;
import entity.ComplainObject;
import entity.CustomerComplaint;
import entity.MonthlySubscription;
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
	public void getBranchState(BranchStateRequest request, int token) throws InterruptedException;
	public void addComplain(CustomerComplaint branch,int token);
	public void sendBranchListRequest(int requestToken);
	public void sendCustomerComplaintRequest(int requestToken);
	public void updateComplaintTable(ComplainObject complainObject, int token);
}
