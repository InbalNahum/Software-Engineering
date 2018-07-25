package entity;

import java.io.Serializable;

/**
 * Customer Message Details
 * @author OmerG
 *
 */
public class CustomerMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String message;
	
	/**
	 * empty C'tor
	 */
	public CustomerMessage() {}
	
	/**
	 * C'tor
	 * @param id
	 * @param message
	 */
	public CustomerMessage(int id, String message) {
		super();
		this.id = id;
		this.message = message;
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
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
