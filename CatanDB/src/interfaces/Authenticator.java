package interfaces;

/**
 * Methods used to authenticate Users, add them to the database,
 * change their user info and remove users
 * 
 * @author Michael Tuer
 *
 */
public interface Authenticator extends Connector {
	
	/**
	 * Check if the entered credentials are correct
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return true if info is correct
	 */
	public int authenticateUser(String username, String password);
	
	/**
	 * Receive the userid associated with the passed in username
	 * 
	 * @param username
	 * 
	 * @return userid, 0 if user does not exist in database
	 */
	public int getUserID(String username);
	
	/**
	 * Add a user to the database
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return false if user already exists
	 */
	public int addUser(String username, String password);
	
	/**
	 * Remove a user from the database
	 * 
	 * @param userid
	 * 
	 * @return false if some error occurs
	 */
	public boolean removeUser(int userid);
	
	/**
	 * Change the password for the user
	 * 
	 * @param userid
	 * @param newPassword
	 * 
	 * @return false if there is some error
	 */
	public boolean changePassword(int userid, String newPassword);
	
	/**
	 * Change the username for the user
	 * 
	 * @param userid
	 * @param newUsername
	 * 
	 * @return false if the username is already taken
	 */
	public boolean changeUsername(int userid, String newUsername);
	
}
