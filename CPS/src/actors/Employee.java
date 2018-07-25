package actors;

import java.io.Serializable;

/**
 * Employee Details
 * @author OmerG
 *
 */
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int password;
	private String branch;
	private String position;
	
	/**
	 * empty C'tor
	 */
	public Employee() {}
	
	/**
	 * C'tor
	 * @param id
	 * @param password
	 * @param branch
	 * @param position
	 */
	public Employee(int id, int password, String branch, String position) {
		super();
		this.id = id;
		this.password = password;
		this.branch = branch;
		this.position = position;
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
	 * @return password
	 */
	public int getPassword() {
		return password;
	}
	
	/**
	 * 
	 * @param password
	 */
	public void setPassword(int password) {
		this.password = password;
	}
	
	/**
	 * 
	 * @return branch
	 */
	public String getBranch() {
		return branch;
	}
	
	/**
	 * 
	 * @param branch
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	/**
	 * 
	 * @return position
	 */
	public String getPosition() {
		return position;
	}
	
	/**
	 * 
	 * @param position
	 */
	public void setPosition(String position) {
		this.position = position;
	}
}
