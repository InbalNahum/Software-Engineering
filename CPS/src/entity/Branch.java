package entity;

import java.io.Serializable;
import parkingLogic.BranchPark;

/**
 * Branch Details
 * @author OmerG
 *
 */
public class Branch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private int id;
	private String branchName;
	private BranchPark carPark;

	/**
	 * C'tor
	 * @param id
	 * @param branchName
	 * @param carPark
	 */
	public Branch(int id, String branchName, BranchPark carPark) {
		super();
		this.id = id;
		this.branchName = branchName;
		this.carPark = carPark;
	}
	
	/**
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	
	/**
	 * 
	 * @param branchName
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	/**
	 * 
	 * @return carPark
	 */
	public BranchPark getCarPark() {
		return carPark;
	}
	
	/**
	 * 
	 * @param carPark
	 */
	public void setCarPark(BranchPark carPark) {
		this.carPark = carPark;
	}
	
	/**
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
