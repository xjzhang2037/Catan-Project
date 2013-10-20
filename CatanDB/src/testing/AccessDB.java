package testing;
import util.*;

import util.dataWrappers.DbMessage;
import util.dataWrappers.EndOfGameStats_Game;
import util.dataWrappers.EndOfGameStats_User;
import util.dataWrappers.UserStats;
import interfaces.*;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class used to the display the contents of the database with a MySQL terminal
 * Also tests several functionalities of the database
 * 
 * @author Michael Tuer
 *
 */
public class AccessDB {
	UserDatabase db;
	
	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		boolean a = true;
		AccessDB adb = new AccessDB();
		adb.displayUsers();
		System.out.println("======================================================================");
		adb.displayPlayedGames();
		System.out.println("======================================================================");
		adb.displayUserGames();
		//adb.db.addFriend(5, 6);
		//adb.db.addFriend(5, 7);
		//adb.db.addFriend(5, 8);
		//adb.db.addFriend(5, 9);
		//adb.testEndGame();
		//adb.testFriends();
		//adb.displayFriendRequests();
		adb.displayFriends();
		adb.displayStats(adb.db.getUserID("asdf"));
		System.out.println("======================================================================");
		adb.displayFriendRequests();
		adb.close();
		if(a)return;
		String hostname = "orion.csl.mtu.edu";
		String dbname = "CatanDB";
		String username = "TSP_team6";
		String password = "Can we create Catan 2 complete this course?";
		//password = "Can we create Catan 2 complete this course?";
		Authenticator db = DBEntryPoint.getAuthenticator(hostname, dbname, username, password);
		System.out.println("connecting to db: success? " + db.connect());
		//System.out.println("Adding user: " + db.addUser("csdoig", "testPassword2"));
		System.out.println("Test Authentication: " + db.authenticateUser("csdoig", "testPassword2"));
	}

	public AccessDB(){

		String hostname = "orion.csl.mtu.edu";
		String dbname = "CatanDB";
		String username = "TSP_team6";
		String password = "Can we create Catan 2 complete this course?";
		db = new UserDatabase(hostname, dbname, username, password);

		db.connect();
	}
	
	public boolean testEndGame() throws IllegalArgumentException, IllegalAccessException{
		System.out.println("Testing end game...");
		int id = db.getUserID("mttuer");
		System.out.println(id);
		long gameLength = 300;
		long gameDate = System.currentTimeMillis();
		int winner = id;
		int mostAggressiveTrader = id;
		int utterLoser = id;
		int beggar = id;
		int longestRoadHolder = id;
		int longestRoadLength = 10;
		int largestArmyHolder = id;
		int largestArmySize = 5;
		int sevensRolled = 15;
		int mostRobbed = id;
		int totalGameTurns = 30;
		EndOfGameStats_Game game = new EndOfGameStats_Game(gameLength, 
				gameDate, winner, mostAggressiveTrader, utterLoser, beggar, 
				longestRoadHolder, longestRoadLength,largestArmyHolder, largestArmySize, sevensRolled, mostRobbed, totalGameTurns);
		
		int devCardsPlayed = 5;
		int roadsBuilt = 12;
		int settlementsBuilt = 4;
		int citiesBuilt = 4;
		int cardsTraded = 25;
		int victoryPoints = 10;
		
		EndOfGameStats_User user1 = new EndOfGameStats_User(id, devCardsPlayed, roadsBuilt, settlementsBuilt, citiesBuilt, cardsTraded, victoryPoints);
		List<EndOfGameStats_User> users = new LinkedList<EndOfGameStats_User>();
		users.add(user1);
		
		boolean worked = db.addGame(game, users);
		System.out.println("Added game to the database; check the user stats now");
		UserStats stats = db.getStats(id);
		System.out.println("Printing out stats:");
		for(Field f: stats.getClass().getFields()){
			System.out.println("Field:" + f.getName() + ": " + f.get(stats));
	
		}
		return worked;
	}
	public void displayStats(int userid) throws IllegalArgumentException, IllegalAccessException{
		UserStats stats = db.getStats(userid);
		System.out.println("Printing out stats:");
		for(Field f: stats.getClass().getFields()){
			System.out.println("Field:" + f.getName() + ": " + f.get(stats));
	
		}
	}
	public boolean testFriends(){
		String user1 = "mttuer";
		String friend = "Johny";
		int id1;
		int friendid;
		id1 = db.getUserID(user1);
		friendid = db.getUserID(friend);
		System.out.println("Testing request friend");
		System.out.println("Sender: " + id1);
		System.out.println("Pending Friend: " + friendid);
		String message = "Please be my friend!";
		boolean worked = db.requestFriend(id1, friendid, message);
		System.out.println("Requesting friend: " + worked);
		Map<Integer, DbMessage> requests = db.getFriendRequests(friendid);
		for(Entry<Integer, DbMessage> ent: requests.entrySet()){
			System.out.println(ent.getKey() + ": " + ent.getValue());
			db.addFriend(friendid, ent.getKey());
		}
		int friendCheck = friendid;
		System.out.println("Friends for id " + friendCheck);
		for(Integer theID: db.getFriendIds(friendCheck)){
			System.out.println(theID);
		}
		return worked;
	}
	
	public void displayPlayedGames(){
		String sql = "SELECT * FROM TB_GAMES";
		ResultSet rs = testSql(sql);

		try {
			System.out.println("id\tgameLength\tgameDate\twinner\tmostAggressiveTrader\tutterLoser\tbeggar\tlongestRoadHolder\tlongestRoadLength\tlargestArmyHolder\tLargestArmySize\tsevensRolled\tmostRobbed\ttotalGameTurns");
			while(rs.next()){
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getDate(3)+ "\t" + rs.getInt(4) + "\t" + rs.getInt(5) + "\t" + rs.getInt(6) + "\t" + rs.getInt(7) + "\t" + rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11) + "\t" + rs.getInt(12) + "\t" + rs.getInt(13) + "\t" + rs.getInt(14));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void displayUserGames(){
		String sql = "SELECT * FROM TB_USER_GAME";
		ResultSet rs = testSql(sql);

		try {
			System.out.println("id\tgameid\tdevCardsPlayed\troadsBuil\tsettlementsBuiltt\tcitiesBuilt\tcardsTraded\tvictoryPoints");
			while(rs.next()){
					System.out.println(rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3)+ "\t" + rs.getInt(4) + "\t" + rs.getInt(5)+ "\t" + rs.getInt(6)+ "\t" + rs.getInt(7)+ "\t" + rs.getInt(8));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void displayUsers(){
		String sql = "SELECT * FROM TB_USERS";
		ResultSet rs = testSql(sql);

		try {
			System.out.println("id\t\tname\t\tpassword\tcreated\t\tlastlogin");
			while(rs.next()){
					System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3)+ "\t" + rs.getDate(4) + "\t" + rs.getDate(4));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void displayFriends(){
		String sql = "SELECT * FROM TB_FRIENDS";
		ResultSet rs = testSql(sql);

		try {
			System.out.println("id\t\tfriendid");
			while(rs.next()){
					System.out.println(rs.getInt(1) + "\t" + rs.getInt(2) );	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void displayFriendRequests(){
		String sql = "SELECT * FROM TB_FRIEND_REQUESTS";
		ResultSet rs = testSql(sql);

		try {
			System.out.println("id\tsenderid\tmessage");
			while(rs.next()){
					System.out.println(rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getString(3) );	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet testSql(String sql){
		ResultSet rs = db.testQuery(sql);
		return rs;
	}
	public void close(){
		db.close();
	}
}
