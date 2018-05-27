package client;

import java.io.Serializable;

public class ClientRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3072026581614231372L;

	public enum TYPE{Read,Write}
	private TYPE requestType;
	private String sqlCommand;
	
	public ClientRequest(TYPE requestType, String sqlCommand) {
		this.requestType = requestType;
		this.sqlCommand = sqlCommand;
	}

	public TYPE getType() {
		return requestType;
	}

	public void setRequestType(TYPE requestType) {
		this.requestType = requestType;
	}

	public String getSqlCommand() {
		return sqlCommand;
	}

	public void setSqlCommand(String sqlCommand) {
		this.sqlCommand = sqlCommand;
	}

	@Override
	public String toString() {
       String type = requestType == TYPE.Read ? "Read" : "Write";
	   return String.format("%s %s", type,sqlCommand);
	}
	
	
	
}
