package actors;

import java.io.Serializable;

/**
 * User Details
 * @author OmerG
 *
 */
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum UserType{manager, customerService, branchEmployee, employee ,casualCustomer,subscriber};
	private String userName;
	private String password;
	private UserType userType;
	private static User currentUser = null;
	
	/**
	 * 
	 * @return currentUser
	 */
	public static User getCurrentUser() {
        return currentUser;
	}
	
	/**
	 * Initialize user details
	 * @param userName
	 * @param password
	 * @param userType
	 */
	public static void initalizeUser(String userName, String password, UserType userType) {
		currentUser = new User(userName,password,userType);
	}

	private User(String userName, String password, UserType userType) {
		super();
		this.userName = userName;
		this.password = password;
		this.userType = userType;
	}

	/**
	 * 
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userType != other.userType)
			return false;
		return true;
	}

	/**
	 * 
	 * @return userType
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * 
	 * @param userType
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
