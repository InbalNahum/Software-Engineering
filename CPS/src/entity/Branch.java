package entity;

import java.io.Serializable;
import parkingLogic.BranchPark;

public class Branch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private int id;
	private String branchName;
	private BranchPark carPark;

	public Branch(int id, String branchName, BranchPark carPark) {
		super();
		this.id = id;
		this.branchName = branchName;
		this.carPark = carPark;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public BranchPark getCarPark() {
		return carPark;
	}
	public void setCarPark(BranchPark carPark) {
		this.carPark = carPark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
