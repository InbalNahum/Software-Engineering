package entity;

import java.io.Serializable;
import java.sql.Blob;

public class Branch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String branchName;
	private Blob carPark;
	public Branch() {}
	public Branch(int id, String branchName, Blob carPark) {
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
	public Blob getCarPark() {
		return carPark;
	}
	public void setCarPark(Blob carPark) {
		this.carPark = carPark;
	}

}
