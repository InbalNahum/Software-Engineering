package common;

public class CpsGlobals {
	
	//Server Details
	public static final int port = 5555;
	public static final String host = "localhost";

	//Objects possible types
	public static enum ServerOperation{writeCasualCustomer, writeOneTimePreOrder};
	
	//Sql commands
	public static final String readObjectSql = "SELECT object_value FROM java_objects WHERE id = ?";
	public static final String writeObjectSql = "INSERT INTO java_objects(name, object_value) VALUES (?, ?)";


	//Branches
	public static final String telHaiBranch = "Tel-Hai";
	public static final String telAvivBranch = "Tel-Aviv";

}
