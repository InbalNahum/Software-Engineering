package client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import common.CpsGlobals.ServerOperation;

/**
 * Request service from server to client
 * @author OmerG
 *
 */
public class ClientRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3072026581614231372L;
    
	private ServerOperation serverOperation;
    List<Object> objects;
    private int communicateToken;

    /**
     * C'tor
     * @param serverOperation
     * @param objects
     * @param communicateToken
     */
	public ClientRequest(ServerOperation serverOperation,
			List<Object> objects,int communicateToken) {
		super();
		this.serverOperation = serverOperation;
		this.objects = objects;
		this.communicateToken = communicateToken;
	}
	
	/**
	 * empty C'tor
	 */
	public ClientRequest() {}
	
	/**
	 * 
	 * @return serverOperation
	 */
	public ServerOperation getServerOperation() {
		return serverOperation;
	}
	
	/**
	 * 
	 * @param serverOperation
	 */
	public void setServerOperation(ServerOperation serverOperation) {
		this.serverOperation = serverOperation;
	}
	
	/**
	 * 
	 * @return objects
	 */
	public List<Object> getObjects() {
		return objects;
	}
	
	/**
	 * 
	 * @param index
	 * @return Object
	 * @throws IndexOutOfBoundsException
	 */
	public Object getObjectAtIndex(int index) throws IndexOutOfBoundsException {
		return objects.get(index);
	}
	
	/**
	 * 
	 * @param objects
	 */
	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
	
	/**
	 * 
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
	/**
	 * 
	 * @param object
	 */
	public void addTolist(Object object) {
		if(objects == null) {
			objects = new ArrayList<Object>();
		}
		objects.add(object);
	}
	
	/**
	 * 
	 * @return communicateToken
	 */
	public int getCommunicateToken() {
		return communicateToken;
	}

	/**
	 * 
	 * @param communicateToken
	 */
	public void setCommunicateToken(int communicateToken) {
		this.communicateToken = communicateToken;
	}
}
