package util;

import interfaces.Authenticator;
import interfaces.Connector;
import interfaces.FriendTracker;
import interfaces.GameTracker;
import interfaces.StatChecker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import util.dataWrappers.DbMessage;
import util.dataWrappers.EndOfGameStats_Game;
import util.dataWrappers.EndOfGameStats_User;
import util.dataWrappers.UserStats;

/**
 * Class that implements all the methods to manage the database for the game
 * Settlers of Catan
 * 
 * @author Michael Tuer
 * 
 */
public class UserDatabase implements Connector, Authenticator, FriendTracker,
		GameTracker, StatChecker {
	private String hostname;
	private String dbName;
	private Properties props;
	private String username;
	private String password;
	private Connection conn;
	private Statement stmt;

	private boolean beingAccessed = false;

	/**
	 * Constructor for UserDatabase, containing all database functions
	 * 
	 * @param hostname
	 * @param databaseName
	 * @param username
	 * @param password
	 */
	public UserDatabase(String hostname, String databaseName, String username,
			String password) {
		this.hostname = hostname;
		this.dbName = databaseName;
		this.username = username;
		this.password = password;
		props = new Properties();
		props.setProperty("user", this.username);
		props.setProperty("password", this.password);
	}

	/**
	 * Connect to the database with the entered credentials
	 * 
	 * @return true if successful
	 */
	public boolean connect() {
		while (beingAccessed)
			;
		beingAccessed = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + this.hostname
					+ "/" + this.dbName, props);

			// stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		if (conn == null) {
			System.out.println("ERROR IN DATBASE: connection was null");
		}
		return conn != null;
	}

	/**
	 * Close the connection with the database
	 * 
	 * @return false if the client wasn't connected
	 */
	@Override
	public boolean close() {
		try {
			conn.close();
			beingAccessed = false;
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Get the username being used
	 * 
	 * @return String username
	 */
	@Override
	public String getUsername() {
		return this.username;
	}

	/**
	 * Get the password being used
	 * 
	 * @return String the password
	 */
	@Override
	public String getPassword() {
		return this.password;
	}

	/**
	 * Get the hostname of the database
	 * 
	 * @return String hostname
	 */
	@Override
	public String getHostname() {
		return this.hostname;
	}

	/**
	 * Check if the entered credentials are correct
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return true if info is correct
	 */
	@Override
	public int authenticateUser(String username, String password) {
		String sql = "SELECT userid, password FROM TB_USERS where username = '"
				+ username + "'";

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				String pswd = rs.getString(2);
				if (password.equals(pswd))
					return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	/**
	 * Add a user to the database
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return false if user already exists
	 */
	public int addUser(String username, String password) {
		// check for username availability
		String sql = "SELECT userid FROM TB_USERS WHERE username = '"
				+ username + "'";

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) { // there should be no results
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

		// add user to the database
		sql = "INSERT INTO TB_USERS (username, password, accountCreationDate) VALUES ('"
				+ username + "','" + password + "', NOW())";

		if (execute(sql)) {
			return -1;
		}

		// pass back the userid generated
		sql = "SELECT userid FROM TB_USERS WHERE username = '" + username + "'";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) { // there should be no results
				return rs.getInt(1);
			} else {
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Removes all references to the user in the database tables: users messages
	 * friends user stats
	 * 
	 * @param userid
	 *            user being deleted
	 * 
	 * @return false if any of the delete statements failed
	 */
	public boolean removeUser(int userid) {
		String sql = "DELETE FROM TB_USERS WHERE userid = " + userid;

		if (execute(sql)) {
			return false;
		}

		// delete messages
		sql = "DELETE FROM TB_FRIEND_REQUESTS WHERE userid = " + userid
				+ " OR senderid = " + userid;

		if (execute(sql)) {
			return false;
		}

		// delete from friends table
		sql = "DELETE FROM TB_FRIENDS WHERE userid = " + userid
				+ " OR friendid = " + userid;

		if (execute(sql)) {
			return false;
		}

		// delete from user stats
		sql = "DELETE FROM TB_USER_GAME WHERE userid = " + userid;

		if (execute(sql)) {
			return false;
		}

		return true;
	}

	/**
	 * Change the password for the user
	 * 
	 * @param userid
	 * @param newPassword
	 * 
	 * @return false if there is some error
	 */
	public boolean changePassword(int userid, String newPassword) {
		String sql = "UPDATE TB_USERS SET password = '" + newPassword
				+ "' WHERE userid = " + userid;

		return execute(sql);
	}

	/**
	 * Change the username for the user
	 * 
	 * @param userid
	 * @param newUsername
	 * 
	 * @return false if the username is already taken
	 */
	public boolean changeUsername(int userid, String newUsername) {
		// check for username availability
		String sql = "SELECT userid FROM TB_USERS WHERE username = '"
				+ username + "'";

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) { // there should be no results
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		sql = "UPDATE TB_USERS SET username = '" + newUsername
				+ "' WHERE userid = " + userid;

		return execute(sql);
	}

	/**
	 * Get a list of the names of friends associated with the passed userid
	 * 
	 * @param userid
	 * 
	 * @return List<String> friend names
	 */
	@Override
	public List<String> getFriends(int userid) {
		String sql = "SELECT TB_USERS.username FROM TB_USERS LEFT JOIN TB_FRIENDS "
				+ "ON TB_FRIENDS.friendid = TB_USERS.userid WHERE TB_FRIENDS.userid = "
				+ userid;

		try {
			List<String> friends = new LinkedList<String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				friends.add(rs.getString(1));
			}
			return friends;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get a list of the userids of friends associated with the passed userid
	 * 
	 * @param userid
	 * 
	 * @return List<Integer> friend ids
	 */
	@Override
	public List<Integer> getFriendIds(int userid) {
		String sql = "SELECT TB_USERS.userid FROM TB_USERS LEFT JOIN TB_FRIENDS "
				+ "ON TB_FRIENDS.friendid = TB_USERS.userid WHERE TB_FRIENDS.userid = "
				+ userid;

		try {
			List<Integer> friends = new LinkedList<Integer>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				friends.add(rs.getInt(1));
			}
			return friends;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get a map of the ids to names of friends associated with the passed
	 * userid
	 * 
	 * @param userid
	 * 
	 * @return Map<Integer, String> id to friend name
	 */
	@Override
	public Map<Integer, String> getFriendMap(int userid) {
		String sql = "SELECT TB_USERS.userid, TB_USERS.username FROM TB_USERS LEFT JOIN TB_FRIENDS "
				+ "ON TB_FRIENDS.friendid = TB_USERS.userid WHERE TB_FRIENDS.userid = "
				+ userid;

		try {
			Map<Integer, String> friends = new HashMap<Integer, String>();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				friends.put(rs.getInt(1), rs.getString(2));
			}

			return friends;
		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * Adds a friend association between the two userids *NOTE* Automatically
	 * deletes the friend request
	 * 
	 * @param userid
	 * @param friendid
	 * 
	 * @return true if the users weren't already friends
	 */
	@Override
	public boolean addFriend(int userid, int friendid) {
		String sql = "SELECT COUNT(userid) FROM TB_FRIENDS WHERE userid = "
				+ userid + " AND " + "friendid = " + friendid;
		int count = -1;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
				count = rs.getInt(1);
			if (count == 0) {
				sql = "INSERT INTO TB_FRIENDS VALUES (" + userid + ", "
						+ friendid + "),(" + friendid + ", " + userid + ")";
				this.deleteRequest(userid, friendid);
				return execute(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * Sends a message to the friend id that requests friendship
	 * 
	 * @param userid
	 *            user sending the message
	 * @param friendid
	 *            person receiving friend request
	 * @param msg
	 *            Message from the user
	 * 
	 * @return false if there is some error
	 */
	@Override
	public boolean requestFriend(int userid, int friendid, String msg) {
		boolean retFalse = false;
		String sql = "SELECT userid, senderid FROM TB_FRIEND_REQUESTS "
				+ "WHERE userid = " + friendid + " AND senderid = " + userid;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				retFalse = true;
			}

			if (retFalse)
				return false;

			sql = "SELECT * FROM TB_FRIENDS " + "WHERE userid = " + friendid
					+ " AND friendid = " + userid;

			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				retFalse = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			retFalse = true;
		}

		if (retFalse)
			return false;

		sql = "INSERT INTO TB_FRIEND_REQUESTS VALUES (" + friendid + ", "
				+ userid + ", '" + msg + "')";
		return this.execute(sql);
	}

	/**
	 * Delete a friend request
	 * 
	 * @param userid
	 * @param senderid
	 * 
	 * @return false if no request exists
	 */
	@Override
	public boolean deleteRequest(int userid, int senderid) {
		String sql = "DELETE FROM TB_FRIEND_REQUESTS WHERE userid = " + userid
				+ " AND senderid = " + senderid;
		return this.execute(sql);

	}

	/**
	 * Delete the friend association between the two users
	 * 
	 * @param userid
	 * @param friendid
	 * 
	 * @return false if there is some error
	 */
	@Override
	public boolean removeFriend(int userid, int friendid) {
		String sql = "DELETE FROM TB_FRIENDS WHERE (userid = " + userid
				+ " AND friendid = " + friendid + ") OR (userid = " + friendid
				+ " AND friendid = " + userid + ")";

		return execute(sql);
	}

	/**
	 * Get a map of requester id to friend requests
	 * 
	 * @param userid
	 *            owner of friend requests
	 * 
	 * @return Map<Integer, DbMessage> senderid to request
	 */
	@Override
	public Map<Integer, DbMessage> getFriendRequests(int userid) {
		String sql = "SELECT TB_FRIEND_REQUESTS.senderid, TB_FRIEND_REQUESTS.message, TB_USERS.username "
				+ "FROM TB_FRIEND_REQUESTS LEFT JOIN TB_USERS "
				+ "ON TB_FRIEND_REQUESTS.senderid = TB_USERS.userid "
				+ "WHERE TB_FRIEND_REQUESTS.userid = " + userid;

		Map<Integer, DbMessage> messages = new HashMap<Integer, DbMessage>();

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				DbMessage message = new DbMessage(userid, rs.getInt(1),
						rs.getString(3), rs.getString(2));
				messages.put(rs.getInt(1), message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return messages;
	}

	/**
	 * Returns the id for the specific username
	 * 
	 * @param username
	 *            String
	 * 
	 * @return int userid; 0 if username not found
	 */
	@Override
	public int getUserID(String username) {
		String sql = "SELECT userid FROM TB_USERS WHERE username = '"
				+ username + "'";

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Add end of game statistics to the database
	 * 
	 * @param EndOfGameStats_Game
	 *            stats specific to the game
	 * @param List
	 *            <EndOfGameStats_User> stats specific to the user for each user
	 * 
	 * @return successful? if false, the sql statement will be printed to stderr
	 *         and stdout will be notified of where the sql failed in this
	 *         method. Game stats added first, then user stats
	 */
	@Override
	public boolean addGame(EndOfGameStats_Game game,
			List<EndOfGameStats_User> users) {
		java.sql.Date d1 = new java.sql.Date(game.gameDate);
		System.out.println("[addGame] " + d1);
		String sql = "INSERT INTO TB_GAMES (gameLength, gameDate, winner, mostAggressiveTrader,"
				+ " utterLoser, beggar, longestRoadHolder, longestRoadLength, largestArmyHolder, "
				+ "largestArmySize, sevensRolled, mostRobbed, totalGameTurns)"
				+ "VALUES ("
				+ (int) game.gameLength
				+ ",'"
				+ d1
				+ "',"
				+ game.winner
				+ ","
				+ game.mostAggressiveTrader
				+ ","
				+ game.utterLoser
				+ ","
				+ game.beggar
				+ ","
				+ game.longestRoadHolder
				+ ","
				+ game.longestRoadLength
				+ ","
				+ game.largestArmyHolder
				+ ","
				+ game.largestArmySize
				+ ","
				+ game.sevensRolled
				+ ","
				+ game.mostRobbed + "," + game.totalGameTurns + ")";

		if (!this.execute(sql)) {
			System.out
					.println("[DB] Failed to insert game info at game level.");
			return false;
		}

		sql = "SELECT MAX(gameid) FROM TB_GAMES";

		int gameid;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				gameid = rs.getInt(1);
			} else {
				System.out.println("[DB] addGame utterly failed...");
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		sql = "INSERT INTO TB_USER_GAME VALUES ";
		for (EndOfGameStats_User user : users) {
			sql += "(" + user.userid + "," + gameid + "," + user.devCardsPlayed
					+ "," + user.roadsBuilt + "," + user.settlementsBuilt + ","
					+ user.citiesBuilt + "," + user.cardsTraded + ","
					+ user.victoryPoints + "),";

		}

		sql = sql.substring(0, sql.length() - 1); // remove the last comma
		System.out.println(sql);
		if (!this.execute(sql)) {
			System.out
					.println("[DB] Failed to insert game info at users level.");
			return false;
		}

		return true;
	}

	/**
	 * Get a UserStats object wrapping all the statistics
	 * 
	 * @param userid
	 * 
	 * @return UserStats
	 */
	@Override
	public UserStats getStats(int userid) {
		int totalGamesPlayed = 0;
		int totalDevCardsPlayed = 0;
		int totalRoadsBuilt = 0;
		int totalSettlementsBuilt = 0;
		int totalCitiesBuilt = 0;
		int totalCardsTraded = 0;
		int totalVictoryPoints = 0;

		int mostDevCardsPlayed = 0;
		int mostRoadsBuilt = 0;
		int mostSettlementsBuilt = 0;
		int mostCitiesBuilt = 0;
		int mostCardsTraded = 0;
		int mostVictoryPoints = 0;

		int totalTimePlayed = 0;
		int totalGameTurns = 0;
		int totalSevensRolled = 0;

		MutableInteger totalWins = new MutableInteger(0);
		MutableInteger totalMostAggressiveTrader = new MutableInteger(0);
		MutableInteger totalUtterLoser = new MutableInteger(0);
		MutableInteger totalBeggar = new MutableInteger(0);
		MutableInteger totalLongestRoadHolder = new MutableInteger(0);
		MutableInteger totalLargestArmyHolder = new MutableInteger(0);
		MutableInteger totalMostRobbed = new MutableInteger(0);

		List<MutableInteger> fieldValues = new ArrayList<MutableInteger>();
		fieldValues.add(totalWins);
		fieldValues.add(totalMostAggressiveTrader);
		fieldValues.add(totalUtterLoser);
		fieldValues.add(totalBeggar);
		fieldValues.add(totalLongestRoadHolder);
		fieldValues.add(totalLargestArmyHolder);
		fieldValues.add(totalMostRobbed);

		List<String> fields = new ArrayList<String>();
		fields.add("winner");
		fields.add("mostAggressiveTrader");
		fields.add("utterLoser");
		fields.add("beggar");
		fields.add("longestRoadHolder");
		fields.add("largestArmyHolder");
		fields.add("mostRobbed");

		String from = "TB_USER_GAME LEFT JOIN TB_GAMES ON TB_USER_GAME.gameid = TB_GAMES.gameid";
		String where = " = " + userid;
		String function = "COUNT";
		String sql = "SELECT COUNT(TB_USER_GAME.userid), SUM(TB_USER_GAME.devCardsPlayed), SUM(TB_USER_GAME.roadsBuilt), SUM(TB_USER_GAME.settlementsBuilt), SUM(TB_USER_GAME.citiesBuilt), SUM(TB_USER_GAME.cardsTraded), SUM(TB_USER_GAME.victoryPoints), "
				+ "MAX(TB_USER_GAME.devCardsPlayed), MAX(TB_USER_GAME.roadsBuilt), MAX(TB_USER_GAME.settlementsBuilt), MAX(TB_USER_GAME.citiesBuilt), MAX(TB_USER_GAME.cardsTraded), MAX(TB_USER_GAME.victoryPoints), "
				+ "SUM(TB_GAMES.gameLength), SUM(TB_GAMES.sevensRolled), SUM(TB_GAMES.totalGameTurns) "
				+ "FROM " + from + " WHERE TB_USER_GAME.userid = " + userid;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				totalGamesPlayed = rs.getInt(1);
				totalDevCardsPlayed = rs.getInt(2);
				totalRoadsBuilt = rs.getInt(3);
				totalSettlementsBuilt = rs.getInt(4);
				totalCitiesBuilt = rs.getInt(5);
				totalCardsTraded = rs.getInt(6);
				totalVictoryPoints = rs.getInt(7);

				mostDevCardsPlayed = rs.getInt(8);
				mostRoadsBuilt = rs.getInt(9);
				mostSettlementsBuilt = rs.getInt(10);
				mostCitiesBuilt = rs.getInt(11);
				mostCardsTraded = rs.getInt(12);
				mostVictoryPoints = rs.getInt(13);

				totalTimePlayed = rs.getInt(14);
				totalSevensRolled = rs.getInt(15);
				totalGameTurns = rs.getInt(16);
			}

			for (int i = 0; i < fields.size(); i++) {
				rs = this.aggregateSingleField(function, fields.get(i), from,
						fields.get(i) + where + " AND TB_USER_GAME.userid = "
								+ userid);
				if (rs.next()) {
					fieldValues.get(i).setInt(rs.getInt(1));
				}
			}

			UserStats stats = new UserStats(totalGamesPlayed,
					totalDevCardsPlayed, totalRoadsBuilt,
					totalSettlementsBuilt, totalCitiesBuilt, totalCardsTraded,
					totalVictoryPoints, mostDevCardsPlayed, mostRoadsBuilt,
					mostSettlementsBuilt, mostCitiesBuilt, mostCardsTraded,
					mostVictoryPoints, totalTimePlayed, totalWins.getInt(),
					totalMostAggressiveTrader.getInt(),
					totalUtterLoser.getInt(), totalBeggar.getInt(),
					totalLongestRoadHolder.getInt(),
					totalLargestArmyHolder.getInt(), totalSevensRolled,
					totalMostRobbed.getInt(), totalGameTurns);
			return stats;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Runs a database function on a specific column
	 * 
	 * @param function
	 * @param field
	 * @param from
	 * @param where
	 * 
	 * @return the result of the aggregate function
	 */
	private ResultSet aggregateSingleField(String function, String field,
			String from, String where) {
		String sql = "SELECT " + function + "(" + field + ") FROM " + from
				+ " WHERE " + where;
		try {
			stmt = conn.createStatement();
			return stmt.executeQuery(sql);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Executes an insert, delete, or update SQL statement
	 * 
	 * @param sql
	 *            String containing the SQL
	 * 
	 * @return false if there is an exception
	 */
	private boolean execute(String sql) {
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * FOR TESTING PURPOSES ONLY!!!!
	 * 
	 * @param sql
	 * @return
	 */
	public ResultSet testQuery(String sql) {
		try {
			stmt = conn.createStatement();
			return stmt.executeQuery(sql);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
