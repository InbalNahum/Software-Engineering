package entity;

import java.io.Serializable;

/**
 * Branch State Request Details
 * @author OmerG
 *
 */
public class BranchStateRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	
	/**
	 * 
	 * @param name
	 */
	public BranchStateRequest(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
