package util.messages;

/**
 * Message for adding a user.
 * 
 * @author Dan Larson
 * 
 */
public class AddUser extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	public AddUser(String user, String pass) {
		username = user;
		password = pass;
	}

	/**
	 * Get Username
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set username
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get Password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set Password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
