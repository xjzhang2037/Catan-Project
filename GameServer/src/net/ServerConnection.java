package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import util.dataWrappers.UserStats;
import util.messages.AcceptFriend;
import util.messages.AddUser;
import util.messages.ChangePassword;
import util.messages.CloseConnection;
import util.messages.CreateGame;
import util.messages.DeclineFriendRequest;
import util.messages.FriendRequests;
import util.messages.Friends;
import util.messages.GameFunction;
import util.messages.GameInstances;
import util.messages.GameState;
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
 * Thread hosted by the Server. Handles a single connection with a client
 * 
 * @author Dan Larson
 * 
 */
public class ServerConnection extends Thread {
	int counter = 0;

	// Members to maintain the connection
	private Socket connection;
	private GameServer gs;

	private ObjectOutputStream out;
	private ObjectInputStream in;
	private InputStream readyCheck;

	private Queue<GameFunction> toGameLogicQ; // incoming
	private Queue<GameState> toClientQ = new ConcurrentLinkedQueue<GameState>();// outgoing
	boolean threadLife = true;

	// Members to store client information
	private String username;
	private int userid = 0;
	private int gameid = -1;
	public boolean gameInProgress;
	private long heartBeatTimer = System.currentTimeMillis();

	/**
	 * Constructor for a ServerConnection
	 * 
	 * @param gs
	 * @param socket
	 */
	public ServerConnection(GameServer gs, Socket socket) {
		super("ServerConnectionThread");
		System.out.println("Server connection being established");
		connection = socket;
		this.gs = gs;
		// masterOut.print("Hello Server");
	}

	/**
	 * Continuously attempts to first, write all messages from the Server/Game
	 * to the client second, read all messages from the client
	 */
	public void run() {
		try {
			System.out.println("[ServerConnection] Started");
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(
					(readyCheck = connection.getInputStream()));

			while (threadLife) {
				Message m = null;
				if (toClientQ != null && toClientQ.size() > 0) {
					System.out
							.println("[ServerConnection] GameState found to be sent off");
					m = toClientQ.poll();
					if (m != null) {
						System.out
								.println("[ServerConnection] Sending GameState");
						gameInProgress = true;
						this.writeMsg(m);
					}
				}
				if ((m = this.readMsg()) != null) {
					this.handleMessage(m);
				}
				if (heartBeatTimer < System.currentTimeMillis()) {
					this.writeMsg(new HeartBeat());
					heartBeatTimer += 5000;
				}

			} // end while loop
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to handle the logic for each message type
	 * 
	 * @param m
	 */
	private void handleMessage(Message m) {
		try {
			if (m instanceof GameFunction) {
				// Receive a message for the game logic
				// Pass to queue that game logic is aware of
				((GameFunction) m).userid = userid;
				toGameLogicQ.add((GameFunction) m);
				return;

			} else if (m instanceof RequestAuthentication) {
				// Receive a user trying to authenticate
				// Send back the message with userid set
				System.out
						.println("[ServerConnection]Requesting Authentication");
				RequestAuthentication msg = (RequestAuthentication) m;
				username = msg.getUser();
				this.setName("ServerConnectionThread: " + username);
				userid = gs
						.getAuthentication(this, username, msg.getPassword());
				msg.setUserID(userid);
				this.writeMsg(msg);
				return;

			} else if (m instanceof JoinGame) {
				// Receive a user trying to connect to a game
				int tempID = ((JoinGame) m).gameid;
				if (gameid == -1) {
					boolean results = gs.joinGame(this, tempID);
					this.writeMsg(new ResultOfRequest(results,
							MessageTypes.JoinGame));
					if (results) {
						gameid = tempID;
					}
				} else {
					this.writeMsg(new ResultOfRequest(false,
							MessageTypes.JoinGame));
				}

				return;
			} else if (m instanceof JoinServer) {
				// Receive from a user that is already authenticated
				// send back UserInfo and GameInstances messages
				userid = ((JoinServer) m).getUserid();
				UserInfo stats = new UserInfo(gs.getUserStats(userid));
				this.writeMsg(stats);
				GameInstances games = new GameInstances(gs.getGameList());
				this.writeMsg(games);

				return;

			} else if (m instanceof AddUser) {
				// Handle adding user to the database
				// Send back a ResultOfRequest message
				userid = gs.createUser(((AddUser) m).getUsername(),
						((AddUser) m).getPassword());
				ResultOfRequest msg;
				if (userid > 0) {
					msg = new ResultOfRequest(true, MessageTypes.AddUser);
				} else {
					msg = new ResultOfRequest(false, MessageTypes.AddUser);
				}
				this.writeMsg(msg);
				return;

			} else if (m instanceof LeaveGame) {
				// User wishes to leave the game
				// Inform the game logic
				System.out.println("[SC] Leaving Game");
				gs.leaveGame(gameid, userid);
				gameInProgress = false;
				gameid = -1;
				toClientQ = new ConcurrentLinkedQueue<GameState>();
				this.writeMsg(new ResultOfRequest(true, MessageTypes.LeaveGame));
				return;

			} else if (m instanceof RequestUserInfo) {
				// Client wishes for update user info
				// Send back UserInfo message
				UserInfo stats = null;
				try {
					stats = new UserInfo(gs.getUserStats(userid));
				} catch (Exception ex) {
					System.out.println("[SC] UserStats error: " + username);
					ex.printStackTrace();
					this.handleMessage(m);
				}
				this.writeMsg(stats);
				return;
			} else if (m instanceof RequestGameInstances) {
				// Client wishes for an updated list of game instances
				// send back GameInstances message
				GameInstances games = new GameInstances(gs.getGameList());
				this.writeMsg(games);

				return;
			} else if (m instanceof CloseConnection) {
				// if game in process, follow leave gamelogic
				// close this socket and kill the thread
				if (gameInProgress) {
					gs.leaveGame(gameid, userid);
				} else if (gameid != -1) {
					gs.removePlayer(gameid, userid);
				}
				try {
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				gs.removeConnectedUser(userid);
				threadLife = false;

				return;
			} else if (m instanceof CreateGame) {
				String gameName = ((CreateGame) m).getGamename();
				if (gameid == -1)
					gameid = gs.createGame(this, gameName, (CreateGame) m);
				this.writeMsg(m);

				return;
			} else if (m instanceof ChangePassword) {
				// changes password, sends back result of request
				boolean result = gs.changePassword(userid,
						((ChangePassword) m).getNewPassword());
				this.writeMsg(new ResultOfRequest(result,
						MessageTypes.ChangePassword));

				return;
			} else if (m instanceof DeclineFriendRequest) {
				gs.declineFriend(userid, ((DeclineFriendRequest) m).getFriend());

				return;
			} else if (m instanceof AcceptFriend) {

				gs.acceptFriend(userid, ((AcceptFriend) m).getFriendID());

				return;
			} else if (m instanceof RequestFriendRequests) {
				this.writeMsg(new FriendRequests(gs.getFriendRequests(userid)));

				return;
			} else if (m instanceof RequestFriends) {
				// Client wants a list of online friends
				// Send back FriendsAndMessages message

				Friends msg = new Friends(gs.getOnlineFriends(userid));
				this.writeMsg(msg);

				return;
			} else if (m instanceof RequestFriend) {
				boolean results = gs.addFriend(userid,
						((RequestFriend) m).friendName,
						((RequestFriend) m).getMessage());
				this.writeMsg(new ResultOfRequest(results,
						MessageTypes.AddFriend));

				return;
			} else if (m instanceof RemoveFriend) {
				String name = ((RemoveFriend) m).getFriend();
				boolean results = gs.removeFriend(userid, gs.getUserID(name));
				this.writeMsg(new ResultOfRequest(results,
						MessageTypes.RemoveFriend));

				return;
			}
		} catch (Exception e) {
			System.out.println("HandleMessage Exception " + username);
			e.printStackTrace();
			// this.playerDisconnect();
		}

	}

	/**
	 * Write a message to the client connection
	 * 
	 * @param m
	 *            the Message to be written
	 * 
	 * @return true if no exceptions were thrown
	 */
	private boolean writeMsg(Object m) {
		// System.out.println("ServerConnection writing message." + m.toString()
		// );

		try {
			out.reset();
			out.writeObject(m);
			out.flush();
			return true;

		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("[SC]Client Disconnected: " + username + " "
					+ userid);
			this.playerDisconnect();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SC] " + username + " Other Exception");
			this.playerDisconnect();
			return false;
		}
	}

	/**
	 * Read a message from the client connection
	 * 
	 * @param obj
	 * @param m
	 * 
	 * @return Null if failed
	 */
	public Message readMsg() {
		try {
			if (readyCheck.available() <= 0) {
				return null;
			} else {
				return (Message) in.readObject();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Pass a reference to the ServerConnection used to pass messages from the
	 * client to the game logic
	 */
	public void setGameLogicQ(Queue<GameFunction> gameLogicCheck) {
		System.out
				.println("[ServerConnection] Received Queue, can pass GameFunctions to the Game Logic");
		toGameLogicQ = gameLogicCheck;
	}

	/**
	 * A safer way to send stats back to the user
	 * 
	 * @param us
	 */
	public void sendStatsToUser(UserStats us) {
		this.writeMsg(new UserInfo(us));
	}

	/**
	 * Pass a reference to the queue which the server connection reads from to
	 * send messages to the client.
	 */
	public Queue<GameState> getQToPushMessagesTo() {
		System.out
				.println("[ServerConnection] Gave out Queue, can now receive messages from the Game Logic");
		return toClientQ;
	}

	/**
	 * Return the userid
	 * 
	 * @return userid
	 */
	public Integer getUserID() {
		return userid;
	}

	/**
	 * Return username
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Method for when a player disconnects
	 */
	public void playerDisconnect() {
		gs.removeConnectedUser(userid);
		if (gameInProgress == true) {
			gs.leaveGame(gameid, userid);
		} else if (gameid != -1) {
			gs.removePlayer(gameid, userid);
		}
		try {
			connection.close();
		} catch (IOException ee) {
			ee.printStackTrace();
		}

		threadLife = false;
	}
}