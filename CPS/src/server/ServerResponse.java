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
	
    
	private List<Object> objects;
	private ServerOperation serverOperation;
	private int communicateToken;

	public ServerResponse(ServerOperation serverOperation,
			List<Object> objects, int communicateToken) {
		super();
		this.serverOperation = serverOperation;
		this.objects = objects;
		this.communicateToken = communicateToken;
	}
	
	public ServerResponse() {}
	
	public ServerOperation getServerOperation() {
		return serverOperation;
	}
	
	public void setServerOperation(ServerOperation serverOperation) {
		this.serverOperation = serverOperation;
	}
	
	public List<Object> getObjects() {
		return objects;
	}
	
	public Object getObjectAtIndex(int index) throws IndexOutOfBoundsException {
		return objects.get(index);
	}
	
	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
	public void addTolist(Object object) {
		if(objects == null) {
			objects = new ArrayList<Object>();
		}
		objects.add(object);
	}
	
	public int getCommunicateToken() {
		return communicateToken;
	}

	public void setCommunicateToken(int communicateToken) {
		this.communicateToken = communicateToken;
	}

}
