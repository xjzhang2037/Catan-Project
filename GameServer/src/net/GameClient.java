package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import util.messages.AcceptFriend;
import util.messages.AddUser;
import util.messages.ChangePassword;
import util.messages.CloseConnection;
import util.messages.CreateGame;
import util.messages.DeclineFriendRequest;
import util.messages.GameFunction;
import util.messages.GameInstances;
import util.messages.HeartBeat;
import util.messages.JoinGame;
import util.messages.JoinServer;
import util.messages.LeaveGame;
import util.messages.Message;
import util.messages.MessageTypes;
import util.messages.RemoveFriend;
import util.messages.RequestAuthentication;
import util.messages.RequestFriend;
import util.messages.RequestFriendRequests;
import util.messages.RequestFriends;
import util.messages.RequestGameInstances;
import util.messages.RequestUserInfo;
import util.messages.ResultOfRequest;
import util.messages.UserInfo;

/**
 * Flow for game client: Instantiate GameClient [Connects to Server socket]
 * invoke authenticateUser(), wait to receive message regarding results invoke
 * joinServer(), wait to receive Message containing User info and current games
 * invoke joinGame(), wait for a Message containing initial game state invoke
 * requestUserInfo() or requestCurrentGames() at any time
 * 
 * invoke closeConnection() to kill the connection
 * 
 * @author Michael Tuer, Dan Larson
 * 
 */
public class GameClient {
	// members to maintain the connection
	public Socket s;
	ObjectInputStream in;
	ObjectOutputStream out;
	InputStream checkReady;

	// user and game information
	// private String username;
	// private String password;
	// TODO
	private boolean userAuthenticated = true;// testing change back

	private boolean gameInSession = false;
	private int userID = -1;
	private int gameID = -1;
	private String password;
	public boolean serverAvailable = true;

	public static void main(String[] args) {
		GameClient gc = new GameClient("localhost", GameServer.TEST_PORT);
		String u = "csdoig";
		String p = "testPassword2";
		boolean a = true;
		while (a) {
			gc.receiveMessage();
			// if (System.currentTimeMillis() > heartBeatTimer) {
			// gc.sendMessage(new HeartBeat(System.currentTimeMillis()));
			// System.out.println("Sent HeartBeat");
			// heartBeatTimer += 1000;
			// }

		}
		// TEST AUTHENTICATION
		gc.authenticateUser(u, p);
		while (true) {
			Message m = gc.receiveMessage();
			if (m != null) {
				System.out.println("[GameClient]Received message:");
				if (m instanceof RequestAuthentication) {
					System.out.println("Return userid = "
							+ ((RequestAuthentication) m).getUserID());
					break;
				}
			}
		}

		// TEST JOIN SERVER
		gc.joinServer();
		boolean one = false;
		while (true) {
			Message m = gc.receiveMessage();
			if (m != null) {
				System.out.println("[GameClient]Received message:");
				if (m instanceof GameInstances) {
					System.out.println("Return GameInstances: "
							+ ((GameInstances) m).getGames());
					if (one)
						break;
					one = true;
				} else if (m instanceof UserInfo) {
					UserInfo msg = (UserInfo) m;
					if (msg.getUserStats() == null) {
						System.out.println("No user stats available.");
					} else {

						System.out.println("Return UserStats: totalwins "
								+ msg.getUserStats().totalWins);
					}
					if (one)
						break;
					one = true;
				} else if (m instanceof ResultOfRequest) {
					ResultOfRequest msg = (ResultOfRequest) m;
					System.out.println("Return result "
							+ msg.isRequestAccepted() + " for "
							+ msg.mType.toString());
				}
			}
		}

		//
	}

	/**
	 * Constructor that automatically sets up the connection Does not
	 * authenticate however, call joinServer() after setting up the username and
	 * id
	 * 
	 * @param hostname
	 * @param port
	 */
	public GameClient(String hostname, int port) {
		try {
			System.out.println("Game Client begun");
			s = new Socket(hostname, port);
			in = new ObjectInputStream(checkReady = s.getInputStream());
			out = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets information needed to authenticate with the server and sends a
	 * request. Wait to receive the message back.
	 * 
	 * @param id
	 *            int userID
	 * @param name
	 *            String username
	 * 
	 * @return true if the message was sent
	 */
	public boolean authenticateUser(String username, String password) {
		userAuthenticated = false;
		gameInSession = false;
		gameID = -1;
		userID = -1;

		RequestAuthentication msg = new RequestAuthentication(username,
				password);
		return this.sendMessage(msg);
	}

	/**
	 * Joins the server and returns user stats
	 * 
	 * @return
	 */
	public boolean joinServer() {
		if (!userAuthenticated)
			return false;
		JoinServer msg = new JoinServer(userID);

		return this.sendMessage(msg);
	}

	/**
	 * Sends a join game request to the server, sends a server message back if
	 * successful
	 * 
	 * @param gid
	 */
	public boolean joinGame(int gameid) {
		if (!userAuthenticated)
			return false;
		JoinGame jg = new JoinGame(gameid);
		return this.sendMessage(jg);
	}

	/**
	 * Sends request to server
	 * 
	 * @return true if the message was sent
	 */
	public boolean requestUserInfo() {
		if (!userAuthenticated)
			return false;
		RequestUserInfo rui = new RequestUserInfo();
		return this.sendMessage(rui);
	}

	/**
	 * Sends request to server
	 * 
	 * @return true if the message was sent
	 */
	public boolean requestGameInstances() {
		if (!userAuthenticated)
			return false;
		return this.sendMessage(new RequestGameInstances());
	}

	/**
	 * Sends request to server
	 * 
	 * @return true if the message was sent
	 */
	public boolean requestFriends() {
		if (!userAuthenticated)
			return false;
		return this.sendMessage(new RequestFriends());
	}

	/**
	 * Decline a friend request
	 * 
	 * @param deleteID
	 * @return
	 */
	public boolean declineFriendRequest(int deleteID) {
		if (!userAuthenticated)
			return false;

		return this.sendMessage(new DeclineFriendRequest(deleteID));
	}

	/**
	 * Request friends request
	 * 
	 * @return
	 */
	public boolean requestFriendRequests() {
		if (!userAuthenticated)
			return false;

		return this.sendMessage(new RequestFriendRequests());
	}

	/**
	 * Accept friend request
	 * 
	 * @param newFriendID
	 * @return
	 */
	public boolean acceptFriendRequest(int newFriendID) {
		if (!userAuthenticated) {
			return false;
		}

		return this.sendMessage(new AcceptFriend(newFriendID));
	}

	/**
	 * Request a friend
	 * 
	 * @param friend
	 * @param message
	 * @return
	 */
	public boolean requestFriend(String friend, String message) {
		if (!userAuthenticated) {
			return false;
		}
		return this.sendMessage(new RequestFriend(friend, message));
	}

	/**
	 * Remove a friend
	 * 
	 * @param friend
	 * @return
	 */
	public boolean removeFriend(String friend) {
		if (!userAuthenticated) {
			return false;
		}
		return this.sendMessage(new RemoveFriend(friend));
	}

	/**
	 * Add a user to the database
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean addUser(String username, String password) {

		return this.sendMessage(new AddUser(username, password));
	}

	/**
	 * Create Game
	 * 
	 * @param gamename
	 * @return
	 */
	public boolean createGame(String gamename) {
		if (!userAuthenticated) {
			return false;
		}
		return this.sendMessage(new CreateGame(gamename));
	}

	/**
	 * Send a game function to the server
	 * 
	 * @param gf
	 * @return
	 */
	public boolean sendGameFunction(GameFunction gf) {
		if (!gameInSession || !userAuthenticated)
			return false;

		return this.sendMessage(gf);
	}

	/**
	 * Leave game
	 * 
	 * @return
	 */
	public boolean leaveGame() {
		if (!gameInSession) {
			return false;
		}
		gameInSession = false;
		return this.sendMessage(new LeaveGame());
	}

	/**
	 * Informs the server that the connection is being closed Closes the socket
	 * and all streams and sets all variables to null Do not call any more
	 * methods after this is called
	 * 
	 * @return
	 */
	public boolean closeConnection() {
		CloseConnection cc = new CloseConnection();
		this.sendMessage(cc);

		try {
			out.close();
			in.close();
			checkReady.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		s = null;
		in = null;
		out = null;
		checkReady = null;

		return true;
	}

	/**
	 * Write a message to the ServerConnection
	 * 
	 * @param m
	 *            the Message to be written
	 * 
	 * @return true if no exceptions were thrown
	 */
	private boolean sendMessage(Message msg) {
		try {
			out.reset();
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			sendMessage(msg);
			return false;
		}
		return true;
	}

	/**
	 * Read a message from the server connection
	 * 
	 * @return null if their is no message to receive
	 */
	public Message receiveMessage() {
		if (!streamReady()) {
			return null;
		}

		try {
			Message m = (Message) this.readObject();

			// Figure out the userid
			if (!this.userAuthenticated && !(m instanceof ResultOfRequest)) {
				if (m instanceof HeartBeat) {// userid
					return null;
				}
				RequestAuthentication msg = (RequestAuthentication) m;
				password = msg.getPassword();
				userAuthenticated = (userID = msg.getUserID()) != -1;
				return msg;
			}
			// Figure out if the game is in session
			else if (m instanceof ResultOfRequest) {
				ResultOfRequest msg = (ResultOfRequest) m;
				if (msg.mType == MessageTypes.JoinGame) {
					gameInSession = true;
				} else if (msg.mType == MessageTypes.LeaveGame) {
					gameInSession = false;
				}
			} else if (m instanceof CreateGame) {
				CreateGame cg = (CreateGame) m;
				this.gameID = cg.gameID;
				gameInSession = true;
			}

			return m;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Check to see if there is an incoming message on the stream
	 * 
	 * @return true if there is a message
	 */
	private boolean streamReady() {
		try {
			return checkReady.available() > 0;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Read a message from the Server connection
	 * 
	 * @return
	 * @throws IOException
	 */
	private Object readObject() throws IOException {
		try {
			Object ret = in.readObject();
			if (ret == null)
				System.out.println("[GameClient]: Message was null.");

			return ret;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the groupID
	 */
	public int getGameID() {
		return gameID;
	}

	/**
	 * @param groupID
	 *            the groupID to set
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Change a users password
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @return false if old passwords do not match, otherwise wait for
	 *         resultOfRequest message
	 */
	public boolean changePassword(String oldPassword, String newPassword) {
		if (!password.equals(oldPassword)) {
			return false;
		}
		return this.sendMessage(new ChangePassword(newPassword));
	}

}
