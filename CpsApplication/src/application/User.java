package application;

public class User {

	public enum UserType{employee,customer};
	private String userName;
	private String password;
	private UserType userType;
	private static User currentUser = null;
	
	public static User getCurrentUser() {
        return currentUser;
	}
	
	public static void initalizeUser(String userName, String password, UserType userType) {
		currentUser = new User(userName,password,userType);
	}

	private User(String userName, String password, UserType userType) {
		super();
		this.userName = userName;
		this.password = password;
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}


	public String getPassword() {
		return password;
	}


	public UserType getUserType() {
		return userType;
	}

}
