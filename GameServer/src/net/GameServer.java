package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import util.Friend;
import util.GameInstancesWrapper;
import util.GameWrapper;
import util.Tracker;
import util.UserDatabase;
import util.dataWrappers.DbMessage;
import util.dataWrappers.EndOfGameStats_Game;
import util.dataWrappers.EndOfGameStats_User;
import util.dataWrappers.UserStats;
import util.messages.CreateGame;
import util.player.Player;
import catanNodes.CatanBoard_vServer;
import chatnet.ChatServer;

/**
 * Parent Class for the Game Server.
 * 
 * @author Dan Larson
 * 
 */
public class GameServer {
	public static final int TEST_PORT = 50000;

	private ServerSocket ss;
	private Tracker gameIDs = new Tracker();
	private Map<Integer, GameWrapper> games;
	private ChatServer chatServ;
	private Map<Integer, ServerConnection> connectedUsers;

	private boolean debug = false;

	// Setup the datbase connections
	private String hostname = "orion.csl.mtu.edu";
	private String databaseName = "CatanDB";
	private String username = "TSP_team6";
	private String password = "Can we create Catan 2 complete this course?";
	private UserDatabase udb = new UserDatabase(hostname, databaseName,
			this.username, this.password);;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		System.out.println("[SERVER]Testing Game Server");
		GameServer testServ = new GameServer(GameServer.TEST_PORT);
		System.out.println("[SERVER]Port opened");
		// starts a new thread to accept clients
		// that will communicate with this server through the piped streams

		testServ.acceptClients();
		// testServ.createTestGames();
		boolean loop = true;
		while (loop)
			;
		testServ.ss.close();
		System.out.println("[SERVER]Server done");
	}

	/**
	 * Initializes lists and port
	 * 
	 * @param port
	 * @throws IOException
	 */
	public GameServer(int port) throws IOException {
		ss = new ServerSocket(port);
		games = new HashMap<Integer, GameWrapper>();
		connectedUsers = new HashMap<Integer, ServerConnection>();
	}

	/**
	 * Constructor for use of ChatServer_v2
	 * 
	 * @param port
	 *            int port number
	 * @param chat
	 *            ChatServer
	 * 
	 * @throws IOException
	 */
	public GameServer(int port, ChatServer chat) throws IOException {
		this(port);
		chatServ = chat;
	}

	/**
	 * Start a new thread to accept clients creates an instance of
	 * ClientAcceptor
	 */
	public void acceptClients() {
		new ClientAcceptor(this, ss).start();
	}

	/**
	 * Creates test games
	 */
	public void createTestGames() {

		GameWrapper temp = new GameWrapper(this);
		GameWrapper temp2 = new GameWrapper(this);
		GameWrapper temp3 = new GameWrapper(this);

		int chatGroupID = chatServ.createGroup();
		temp.setChatNum(chatGroupID);
		chatGroupID = chatServ.createGroup();
		temp2.setChatNum(chatGroupID);
		chatGroupID = chatServ.createGroup();
		temp3.setChatNum(chatGroupID);

		temp.setGameName("Elmo's World");
		temp2.setGameName("Game Of Thrones");
		temp3.setGameName("Bifrost");

		Player Elmo = new Player("Elmo", 50);
		Elmo.setToDummy();
		Player CookieMonster = new Player("CookieMonster", 51);
		CookieMonster.setToDummy();
		Player Thor = new Player("Thor", 54);
		Thor.setToDummy();
		Player Loki = new Player("Loki", 55);
		Loki.setToDummy();
		Player Odin = new Player("Odin", 56);
		Odin.setToDummy();
		Player NedStark = new Player("NedStark", 57);
		NedStark.setToDummy();
		Player LittleFinger = new Player("LittleFinger", 58);
		LittleFinger.setToDummy();

		temp.addUser(Elmo);
		temp.addUser(CookieMonster);
		temp2.addUser(NedStark);
		temp2.addUser(LittleFinger);
		temp3.addUser(Thor);
		// temp3.addUser(Loki);
		// temp3.addUser(Odin);
		int id1 = gameIDs.getNewID();
		temp.setGameID(id1);
		int id2 = gameIDs.getNewID();
		temp2.setGameID(id2);
		int id3 = gameIDs.getNewID();
		temp3.setGameID(id3);
		games.put(id1, temp);
		games.put(id2, temp2);
		games.put(id3, temp3);
	}

	/**
	 * @param user
	 *            - the user requesting to join game
	 * @param gameID
	 *            - the game ID of the game the user wants to join
	 * @return - true if join was successful, otherwise false
	 */
	public boolean joinGame(ServerConnection sc, int gameID) {

		GameWrapper game = games.get(gameID);

		System.out.println("GAMESERVER -- " + sc.getUsername() + " "
				+ sc.getUserID());
		Player user = new Player(sc.getUsername(), sc.getUserID());

		if (!game.getInProgress()) {
			if (game.addUser(user)) {
				game.setOutGoingStates(sc.getUserID(),
						sc.getQToPushMessagesTo());
				sc.setGameLogicQ(game.getQueue());
				if (game.getNumberOfPlayers() == 4) {
					if (debug) {
						System.out.println("[GameServer]Started Game");
					}
					game.setGameInstance(new CatanBoard_vServer(
							(ArrayList<Player>) game.getPlayers(), game
									.getQueue(), game));
					game.setInProgress(true);
					game.start();
				}
				return true;
			}
			if (debug) {
				System.out.println("[GameServer]Joined Game: " + gameID
						+ " User " + user.getName());
			}

		} else if (game.getInProgress()) {
			if (game.addUser(user)) {
				game.setOutGoingStates(sc.getUserID(),
						sc.getQToPushMessagesTo());
				sc.setGameLogicQ(game.getQueue());
				System.out
						.println("[GameServer.joinGame]Added User to an in progress game");
				return true;
			}

		}
		return false;
	}

	/**
	 * 
	 * @param user
	 *            - user requesting to create game
	 * @return - the game ID of the newly created game
	 */
	public int createGame(ServerConnection sc, String gameName, CreateGame cg) {
		int gameid = gameIDs.getNewID();
		GameWrapper newgame = new GameWrapper(this);
		newgame.setGameName(gameName);
		newgame.setGameID(gameid);
		int chatGroupID = chatServ.createGroup();
		cg.chatGroupID = chatGroupID;
		cg.gameID = gameid;
		newgame.setChatNum(chatGroupID);
		System.out.println("chatGroupID " + chatGroupID);

		games.put(gameid, newgame);
		this.joinGame(sc, gameid);
		if (debug) {
			System.out.println("[GameServer.createGame] gameID: " + gameid);
		}
		return gameid;
	}

	/**
	 * Returns a set of Game ids
	 * 
	 * @return
	 */
	public Set<Integer> getGamesIDs() {
		if (debug) {
			System.out.println("[GameServer.getGamesIDs]");
		}
		return games.keySet();
	}

	/**
	 * Returns a list of game instances
	 * 
	 * @return
	 */
	public Map<Integer, GameWrapper> getInstances() {
		if (debug) {
			System.out.println("[GameServer.getInstances]");
		}
		return games;
	}

	/**
	 * Retrieves user stats
	 * 
	 * @param userid
	 * @return stats object
	 */
	public UserStats getUserStats(int userid) {
		udb.connect();
		UserStats ustats = udb.getStats(userid);
		udb.close();
		if (debug) {
			System.out.println("[GameServer.getUserStats]");
		}

		return ustats;
	}

	/**
	 * Return a list of game info
	 * 
	 * @return
	 */
	public List<GameInstancesWrapper> getGameList() {
		List<GameInstancesWrapper> gameList = new LinkedList<GameInstancesWrapper>();

		for (Entry<Integer, GameWrapper> ent : games.entrySet()) {
			int gameid = ent.getKey();
			GameWrapper curGame = ent.getValue();
			GameInstancesWrapper gameInfo = new GameInstancesWrapper();
			gameInfo.setGameid(gameid);
			gameInfo.setUsers(curGame.getPlayersNames());
			gameInfo.setName(curGame.getGameName());
			gameInfo.setInProgress(curGame.getInProgress());
			gameInfo.setChatRoomNumber(curGame.getChatRoomNum());
			gameList.add(gameInfo);

		}
		return gameList;
	}

	/**
	 * Add user to the database
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean addUser(String username, String password) {
		udb.connect();
		if (udb.addUser(username, password) == -1) {
			udb.close();
			if (debug) {
				System.out.println("[GameServer.addUser] " + false);
			}
			return false;
		} else {
			udb.close();
			if (debug) {
				System.out.println("[GameServer.addUser] " + true);
			}
			return true;
		}
	}

	/**
	 * Alerts the game logic that a player has left the game
	 * 
	 * @param gameid
	 * @param userid
	 */
	public void leaveGame(int gameid, int userid) {

		GameWrapper game = games.get(gameid);
		if (debug) {
			System.out.println("[GameServer]Leaving Game " + game.getGameName()
					+ " Player: " + userid);
		}
		game.leaveGame(userid);

		if (!game.checkActivePlayers()) {
			this.removeActiveGame(gameid);
		}
	}

	/**
	 * Remove player from a game's list of players
	 * 
	 * @param gameid
	 * @param userid
	 */
	public void removePlayer(int gameid, int userid) {

		GameWrapper game = games.get(gameid);
		if (debug) {
			System.out.println("[GameServer]Removing Player from "
					+ game.getGameName() + " Player id: " + userid);
		}
		List<Player> players = game.getPlayers();
		for (Player user : players) {
			if (user.getID() == userid) {
				game.removeUser(user);
				break;
			}
		}
		if (!game.checkActivePlayers()) {
			this.removeActiveGame(gameid);
		}
	}

	/**
	 * Takes in username and password
	 * 
	 * @param username
	 * @param password
	 * @return true if valid login
	 */
	public int getAuthentication(ServerConnection sc, String username,
			String password) {
		udb.connect();
		int userid = udb.authenticateUser(username, password);
		if (!connectedUsers.containsKey(userid) && userid != -1) {
			connectedUsers.put(userid, sc);
		} else {
			System.out.println("[GameServer.getAuthentication] userID: "
					+ userid + ". Already connected");
			userid = -2;
		}

		udb.close();

		if (debug) {
			System.out.println("[GameServer.getAuthentication] userID: "
					+ userid);
		}

		for (int id : connectedUsers.keySet()) {
			System.out.println("\t|" + id + "|");
		}

		return userid;
	}

	/**
	 * Creates a user in the database
	 * 
	 * @param username
	 * @param password
	 * @return userid
	 */
	public int createUser(String username, String password) {
		udb.connect();
		udb.addUser(username, password);
		int userid = udb.getUserID(username);
		udb.close();
		if (debug) {
			System.out.println("[GameServer.createUser] userID: " + userid);
		}
		return userid;
	}

	/**
	 * Returns the userid
	 * 
	 * @param username
	 * @return userid
	 */
	public int getUserID(String username) {
		udb.connect();
		int userid = udb.getUserID(username);
		udb.close();
		if (debug) {
			System.out.println("[GameServer.getUserID] userID: " + userid);
		}
		return userid;
	}

	/**
	 * Remove a user from the connected users list
	 * 
	 * @param userid
	 */
	public void removeConnectedUser(int userid) {
		connectedUsers.remove(userid);
		for (int id : connectedUsers.keySet()) {
			System.out.println("\t|" + id + "|");
		}
	}

	/**
	 * Change a users password
	 * 
	 * @param userid
	 * @param username
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public boolean changePassword(int userid, String newPassword) {
		boolean success = false;
		udb.connect();
		success = udb.changePassword(userid, newPassword);
		udb.close();
		return success;
	}

	/**
	 * Submits endgame stats to the database
	 * 
	 * @param game
	 *            Game stats
	 * @param users
	 *            User stats
	 * @param gameid
	 */
	public void submitEndGameStats(EndOfGameStats_Game game,
			List<EndOfGameStats_User> users, int gameid) {

		udb.connect();
		udb.addGame(game, users);
		udb.close();

		for (EndOfGameStats_User u : users) {
			UserStats us = this.getUserStats(u.userid);
			ServerConnection sc = connectedUsers.get(u.userid);
			if (sc != null) {
				sc.sendStatsToUser(us);
			}
		}
	}

	/**
	 * Remove game from game list
	 * 
	 * @param gameid
	 */
	public void removeActiveGame(int gameid) {
		System.out.println("[GameServer]Removing gameid from list: " + gameid);
		games.remove(gameid);
	}

	/**
	 * Adds friend request
	 * 
	 * @param userid
	 * @param newFriendID
	 * @return
	 */
	public boolean addFriend(int userid, String newFriendName, String msg) {
		// get friend id
		udb.connect();
		int friendid = udb.getUserID(newFriendName);
		udb.close();

		// request friendship
		udb.connect();
		boolean result = udb.requestFriend(userid, friendid, msg);
		udb.close();

		if (debug) {
			System.out.println("[GameServer.addFriend]");
		}
		return result;
	}

	/**
	 * Delete Friend
	 * 
	 * @param userid
	 * @param FriendID
	 * @return
	 */
	public boolean removeFriend(int userid, int FriendID) {
		udb.connect();
		boolean result = udb.removeFriend(userid, FriendID);
		udb.close();
		if (debug) {
			System.out.println("[GameServer.removeFriend]");
		}
		return result;
	}

	/**
	 * Returns a list of friends online
	 * 
	 * @param userid
	 * @return
	 */
	public List<Friend> getOnlineFriends(int userid) {
		udb.connect();
		List<Friend> list = new LinkedList<Friend>();
		Map<Integer, String> people = udb.getFriendMap(userid);
		for (Entry<Integer, String> ent : people.entrySet()) {
			int id = ent.getKey();
			String name = ent.getValue();
			boolean connection = connectedUsers.containsKey(id);
			list.add(new Friend(id, name, connection));
		}
		udb.close();
		if (debug) {
			System.out.println("[GameServer.getOnlineFriends]");
		}
		return list;
	}

	/**
	 * Decline Friend Request
	 * 
	 * @param userID
	 * @param friendID
	 */
	public void declineFriend(int userID, int friendID) {

		udb.connect();
		udb.deleteRequest(userID, friendID);
		udb.close();

	}

	/**
	 * Accept Friend Request
	 * 
	 * @param userID
	 * @param friendID
	 */
	public void acceptFriend(int userID, int friendID) {

		udb.connect();
		udb.addFriend(userID, friendID);
		udb.close();
	}

	/**
	 * Returns all friend requests for a user
	 * 
	 * @param userID
	 * @return
	 */
	public Map<Integer, DbMessage> getFriendRequests(int userID) {

		udb.connect();
		Map<Integer, DbMessage> requests = udb.getFriendRequests(userID);
		udb.close();
		return requests;
	}

}
