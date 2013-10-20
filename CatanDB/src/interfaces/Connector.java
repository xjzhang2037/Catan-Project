package interfaces;

/**
 * Methods used to connect to the database
 * 
 * @author Michael Tuer
 *
 */
public interface Connector {
	
	/**
	 * Connect to the database with the entered credentials
	 * 
	 * @return true if successful
	 */
	public boolean connect();
	
	/**
	 * Close the connection with the database
	 * 
	 * @return false if the client wasn't connected
	 */
	public boolean close();
	
	/**
	 * Get the username being used
	 * 
	 * @return String username
	 */
	public String getUsername();
	
	/**
	 * Get the password being used
	 * 
	 * @return String the password
	 */
	public String getPassword();
	
	/**
	 * Get the hostname of the database
	 * 
	 * @return String hostname
	 */
	public String getHostname();
	
}
