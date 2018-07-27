package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import common.CpsGlobals.ServerOperation;

public class ServerResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    
	private List<Object> objects = new ArrayList<Object>();;
	private ServerOperation serverOperation;
	private int communicateToken;

	/**
	 * c'tor
	 * @param serverOperation
	 * @param objects
	 * @param communicateToken
	 */
	public ServerResponse(ServerOperation serverOperation,
			List<Object> objects, int communicateToken) {
		super();
		this.serverOperation = serverOperation;
		this.objects = objects;
		this.communicateToken = communicateToken;
	}
	
	/**
	 * empty c'tor
	 */
	public ServerResponse() {}
	
	/**
	 * getter for the server operation
	 * @return
	 */
	public ServerOperation getServerOperation() {
		return serverOperation;
	}
	
	/**
	 * setter for the server operation
	 * @param serverOperation
	 */
	public void setServerOperation(ServerOperation serverOperation) {
		this.serverOperation = serverOperation;
	}
	
	/**
	 * getter for the objects that the server returns
	 * @return
	 */
	public List<Object> getObjects() {
		return objects;
	}
	
	/**
	 * get object from object array by index
	 * @param index
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public Object getObjectAtIndex(int index) throws IndexOutOfBoundsException {
		return objects.get(index);
	}
	
	/**
	 * setter for the objects array
	 * @param objects
	 */
	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
	
	/**
	 * 
	 * @return
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
	/**
	 * add an object to the object list
	 * @param object
	 */
	public void addTolist(Object object) {
		objects.add(object);
	}
	
	/**
	 * get the communicate token
	 * @return
	 */
	public int getCommunicateToken() {
		return communicateToken;
	}

	/**
	 * set communicate token
	 * @param communicateToken
	 */
	public void setCommunicateToken(int communicateToken) {
		this.communicateToken = communicateToken;
	}

}
