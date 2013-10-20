package util;
import interfaces.*;

/**
 * Gives access to specific functions implemented by the class UserDatabase
 * 
 * @author Michael Tuer
 *
 */
public class DBEntryPoint {

	/**
	 * See the Authenticator interface in interfaces package
	 * 
	 * @param hostname
	 * @param databaseName
	 * @param username
	 * @param password
	 * @return
	 */
	public static Authenticator getAuthenticator(String hostname, String databaseName, String username, String password){
		return new UserDatabase(hostname, databaseName, username, password);
	}
	
	/**
	 * See the FriendTracker interface in interfaces package
	 * 
	 * @param hostname
	 * @param databaseName
	 * @param username
	 * @param password
	 * @return
	 */
	public static FriendTracker getFriendTracker(String hostname, String databaseName,String username, String password){
		return new UserDatabase(hostname, databaseName, username, password);
	}
	
	/**
	 * See the GameTracker interface in interfaces package
	 * 
	 * @param hostname
	 * @param databaseName
	 * @param username
	 * @param password
	 * @return
	 */
	public static GameTracker getGameTracker(String hostname, String databaseName, String username, String password){
		return new UserDatabase(hostname, databaseName, username, password);
	}
	
	/**
	 * See the StatChecker interface in interfaces package
	 * 
	 * @param hostname
	 * @param databaseName
	 * @param username
	 * @param password
	 * @return
	 */
	public static StatChecker getStatChecker(String hostname, String databaseName, String username, String password){
		return new UserDatabase(hostname, databaseName, username, password);
	}
	
	/**
	 * Contains all methods of the above interfaces
	 * 
	 * @param hostname
	 * @param databaseName
	 * @param username
	 * @param password
	 * @return
	 */
	public static UserDatabase getUserDB(String hostname, String databaseName, String username, String password){
		return new UserDatabase(hostname, databaseName, username, password);
	}
}
