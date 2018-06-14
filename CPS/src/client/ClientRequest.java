package client;

import java.io.Serializable;
import java.util.List;

import common.CpsGlobals.ServerOperation;

public class ClientRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3072026581614231372L;
    
	private ServerOperation serverOperation;
    List<Object> objects;
    
	public ClientRequest(ServerOperation serverOperation, List<Object> objects) {
		super();
		this.serverOperation = serverOperation;
		this.objects = objects;
	}
	
	public ClientRequest() {}
	
	public ServerOperation getServerOperation() {
		return serverOperation;
	}
	
	public void setServerOperation(ServerOperation serverOperation) {
		this.serverOperation = serverOperation;
	}
	
	public List<Object> getObjects() {
		return objects;
	}
	
	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
