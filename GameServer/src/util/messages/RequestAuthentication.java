package util.messages;

/**
 * Sent from client to server with username and password set. Sent back to
 * client with userid set, if -1, authentication failed.
 * 
 * @author Michael
 * 
 */
public class RequestAuthentication extends Message {

	private static final long serialVersionUID = 1L;

	int userid;
	String username;
	String password;

	public RequestAuthentication(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Get user name
	 * 
	 * @return username
	 */
	public String getUser() {
		return username;
	}

	/**
	 * Get password
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * set user id
	 * 
	 * @param id
	 */
	public void setUserID(int id) {
		userid = id;
	}

	/**
	 * get user id
	 * 
	 * @return id
	 */
	public int getUserID() {
		return userid;
	}

}
