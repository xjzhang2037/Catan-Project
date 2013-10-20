 package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import interfaces.Connector;

/**
 * Class to create all tables for the database on a fresh database
 * 
 * @author Michael Tuer
 *
 */
public class SetupDB implements Connector{
	private String hostname;
	private String dbName;
	private Properties props;
	private String username;
	private String password;
	private Connection conn; 
	private Statement stmt;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String hostname = "orion.csl.mtu.edu";
		String dbName = "CatanDB";
		String username = "TSP_team6";
		String password = "Can we create Catan 2 complete this course?";
		SetupDB db = new SetupDB(hostname, dbName, username, password);
		System.out.println("Connecting to the Database: " + db.connect());
		System.out.println("Creating one table: " + db.setupFriendRequestTable());
		//System.out.println("Attempting to setup tables...\nSuccessful? " + db.setupTables());
		db.close();
	}
	
	protected SetupDB(String hostname, String databaseName, String username, String password){
		this.hostname = hostname;
		this.dbName = databaseName;
		this.username = username;
		this.password = password;
		props = new Properties();
		props.setProperty("user", this.username);
		props.setProperty("password", this.password);
	}
	
	public boolean connect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
			        "jdbc:mysql://" +
			        this.hostname + "/" + this.dbName,
			        props);

			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return conn != null;
	}

	@Override
	public boolean close() {
		try {
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getHostname() {
		return this.hostname;
	}
	
	public boolean customSQL(String sql){
		return this.execute(sql);
	}
	
	public ResultSet customSQLQuery(String sql){
		System.out.println("SQL: " + sql);
		try{
			return stmt.executeQuery(sql);
			
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean setupTables(){
		boolean userTable = setupUserTable();
		boolean friendsTable = setupFriendsTable();
		boolean gamesTable = setupGamesTable();
		boolean userGameTable = setupUserGameTable();
		boolean userMessagesTable = setupFriendRequestTable();
		return userTable &&
				friendsTable &&
				gamesTable &&
				userGameTable &&
				userMessagesTable; 
	}
	
	/**
	 * Creates the table to store instances of users
	 * TB_USERS
	 * fields:
	 * userid (KEY)
	 * username
	 * password
	 * accountCreationDate
	 * lastLoginDate
	 * 
	 * @return successful
	 */
	public boolean setupUserTable(){
		String sql = "CREATE TABLE TB_USERS (userid int AUTO_INCREMENT, username varchar(30), password varchar(50), accountCreationDate DATE," +
				"lastLoginDate DATE, PRIMARY KEY (userid))";
		
		return this.execute(sql);
	}
	
	/**
	 * Creates a table to store links to friends
	 * TB_FRIENDS
	 * fields:
	 * userid
	 * friendid
	 * 
	 * @return successful
	 */
	public boolean setupFriendsTable(){
		String sql = "CREATE TABLE TB_FRIENDS (userid int, friendid int)";
		
		return this.execute(sql);
	}
	
	/**
	 * Creates a table to store game instances
	 * TB_GAMES
	 * fields:
	 * gameid
	 * gameLength
	 * gameDate
	 * winner
	 * mostAggressiveTrader
	 * utterLoser
	 * beggar
	 * longestRoadHolder
	 * longestRoadLength
	 * largestArmyHolder
	 * LargestArmySize
	 * sevensRolled
	 * mostRobbed
	 * totalGameTurns
	 * 
	 * @return successful
	 */
	public boolean setupGamesTable(){
		String sql = "CREATE TABLE TB_GAMES (gameid int AUTO_INCREMENT, gameLength int, gameDate DATE, winner int, " +
				"mostAggressiveTrader int, utterLoser int, beggar int, longestRoadHolder int, longestRoadLength int, " +
				"largestArmyHolder int, largestArmySize int, sevensRolled int, mostRobbed int, totalGameTurns int,"	+
				"PRIMARY KEY (gameid))";
		
		return this.execute(sql);
	}
	
	/**
	 * Creates a table to store user stats per game
	 * TB_USER_GAME
	 * fields:
	 * userid
	 * gameid
	 * devCardsPlayed
	 * roadsBuilt
	 * settlementsBuilt
	 * citiesBuilt
	 * cardsTraded
	 * victoryPoints
	 * 
	 * @return successful
	 */
	public boolean setupUserGameTable(){
		String sql = "CREATE TABLE TB_USER_GAME (userid int, gameid int, devCardsPlayed int, roadsBuilt int," +
				"settlementsBuilt int, citiesBuilt int, cardsTraded int, victoryPoints int)";
		
		return this.execute(sql);
	}
	
	/**
	 * Creates a table to store messages for users
	 * TB_FRIEND_REQUESTS
	 * fields:
	 * userid
	 * senderid
	 * message
	 * 
	 * @return
	 */
	public boolean setupFriendRequestTable(){
		String sql = "CREATE TABLE TB_FRIEND_REQUESTS (userid int, senderid int, message varchar(250))";
		
		return this.execute(sql);
	}
	
	private boolean execute(String sql){
		try{
			System.out.println("SQL: " + sql);
			return !stmt.execute(sql);
			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}	
	}
	
}
