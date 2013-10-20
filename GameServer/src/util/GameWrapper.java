package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import net.GameServer;
import util.dataWrappers.EndOfGameStats_Game;
import util.dataWrappers.EndOfGameStats_User;
import util.messages.GameFunction;
import util.messages.GameState;
import util.player.Player;
import catanNodes.CatanBoard_vServer;

/**
 * Wrapper for game logic
 * 
 * @author Dan
 * 
 */
public class GameWrapper extends Thread {

	boolean threadLife = true;
	boolean endGame = false;
	int counter = 0;
	CatanBoard_vServer theGame;
	Queue<GameFunction> incomingFunctions = new LinkedList<GameFunction>();
	public Map<Integer, Queue<GameState>> outgoingStates = new HashMap<Integer, Queue<GameState>>();
	List<Player> users = new ArrayList<Player>();
	String gameName;
	boolean inProgress = false;
	private int chatRoomNumber;
	private GameServer server;
	private int gameID = -1;

	/**
	 * GameWrapper Constructor
	 * 
	 * @param parent
	 */
	public GameWrapper(GameServer parent) {
		super();
		server = parent;
		System.out.println("[GameWrapper] New GameWrapper created");
	}

	/**
	 * Continuously check for functions from players or timed out actions Handle
	 * the logic accordingly and update the players
	 */
	public void run() {
		while (threadLife) {
			while (!incomingFunctions.isEmpty())
				theGame.handleMessage(incomingFunctions.poll());
			theGame.handleTimer();
		}
	}

	/**
	 * Send GameState to a specific user
	 * 
	 * @param userid
	 * @param msg
	 */
	public boolean sendGameState(int userid, GameState msg) {
		System.out.println("SENDING MESSAGE " + counter++);
		// System.out.println(outgoingStates);
		Queue<GameState> q = outgoingStates.get(userid);
		if (q == null) {
			System.out
					.println("[GameWrapper.sendGameState] Attempted to send to user "
							+ userid);// +
			System.out.println(outgoingStates);
			// ", Message: "
			// +
			// msg);
			return false;
		}
		// System.out.println("FOUND QUEUE TO SEND MESSAGE TO");
		q.add(msg);
		// System.out
		// .println("[GameWrapper]FOUND QUEUE TO SEND MESSAGE TO FOR USER "
		// + userid);
		// System.out.println("[GameWrapper]Queue Size: " + q.size());
		return true;
	}

	/**
	 * Add a user to the list of Players
	 * 
	 * @param user
	 *            Player to add return boolean
	 */
	public boolean addUser(Player user) {
		if (!endGame) {
			if (!this.getInProgress()) {
				if (this.getNumberOfPlayers() < 4) {
					users.add(user);
					return true;
				}
			} else if (this.getInProgress()) {
				if (this.getNumberOfPlayers() < 4) {
					for (Player old : users) {
						System.out.println("\t\t" + old.getName());
						if (!old.getConnectionStatus()) {
							System.out.println("Replacing: " + old.getName()
									+ " with " + user.getName());
							theGame.replacePlayer(old, user);
							removeUser(old);
							users.add(user);
							return true;
						}
					}
				}
			}
		}
		System.out.println("[GameWrapper] Cannot add user");
		return false;
	}

	/**
	 * Remove a player from the list
	 * 
	 * @param user
	 */
	public void removeUser(Player user) {
		users.remove(user);
		outgoingStates.remove((Integer) user.getID());

		System.out.println("[GameWrapper.removeUser]Players left: "
				+ getNumberOfPlayers());

	}

	/**
	 * Returns the number of connected players
	 * 
	 * @return
	 */
	public int getNumberOfPlayers() {
		int connectedPlayers = 0;
		for (Player ent : users) {
			System.out.println("[GW]" + ent.getName());
			if (ent.getConnectionStatus()) {
				System.out.println("[GW]" + "\t" + ent.getName());
				connectedPlayers++;
			}
		}
		return connectedPlayers;
	}

	/**
	 * Returns the list of players
	 * 
	 * @return List<Players>
	 */
	public List<Player> getPlayers() {
		return users;
	}

	/**
	 * return list of players names
	 * 
	 * @return List<String>
	 */
	public List<String> getPlayersNames() {
		List<String> names = new ArrayList<String>();
		for (Player ent : users) {
			if (ent.getConnectionStatus())
				names.add(ent.getName());
		}
		return names;
	}

	/**
	 * Set game instance
	 * 
	 * @param game
	 */
	public void setGameInstance(CatanBoard_vServer game) {
		theGame = game;

	}

	/**
	 * Returns queue of incoming game functions
	 * 
	 * @return
	 */
	public Queue<GameFunction> getQueue() {
		return incomingFunctions;
	}

	/**
	 * Sets out going queue of game states to userid
	 * 
	 * @param userID
	 * @param qToPushMessagesTo
	 */
	public void setOutGoingStates(Integer userID,
			Queue<GameState> qToPushMessagesTo) {
		outgoingStates.put(userID, qToPushMessagesTo);
	}

	/**
	 * Returns game name
	 * 
	 * @return
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * 
	 * @param gameName
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/**
	 * returns the status of the game
	 * 
	 * @return
	 */
	public boolean getInProgress() {
		return inProgress;
	}

	/**
	 * Sets the status of the game
	 * 
	 * @param inProgress
	 */
	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}

	/**
	 * Lets the game logic know a user has left
	 * 
	 * @param userid
	 */
	public void leaveGame(int userid) {
		System.out.println("[GameWrapper]Leaving Game");
		theGame.leaveGame(userid);
		for (Player ent : users) {
			if (ent.getID() == userid) {
				System.out.println("Leaving... " + ent.getName());
				ent.changeConnectionStatus(false);
				break;
			}
		}
		outgoingStates.remove((Integer) userid);
		System.out.println("[GameWrapper]Players left: "
				+ this.getNumberOfPlayers());
	}

	/**
	 * Sets chatroom number
	 * 
	 * @param chatNum
	 */
	public void setChatNum(int chatNum) {
		chatRoomNumber = chatNum;
	}

	/**
	 * Get chatroom number
	 * 
	 * @return
	 */
	public int getChatRoomNum() {
		return chatRoomNumber;
	}

	/**
	 * Submit end game stats
	 * 
	 * @param game
	 * @param users
	 */
	public void endGameStats(EndOfGameStats_Game game,
			List<EndOfGameStats_User> users) {
		System.out.println("[GameWrapper] End Game");
		endGame = true;
		server.submitEndGameStats(game, users, getGameID());
	}

	/**
	 * Get game id
	 * 
	 * @return
	 */
	public int getGameID() {
		return gameID;
	}

	/**
	 * Set game id
	 * 
	 * @param gameID
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	/**
	 * End Thread method. Waits for 2 seconds before ending
	 */
	public void endThread() {
		System.out.println("[GameWrapper]Ending Game Thread");
		long timeout = System.currentTimeMillis() + 2000;

		while (System.currentTimeMillis() < timeout) {
			// wait 2 seconds for everything to finish
		}
		threadLife = false;
	}

	/**
	 * Returns the number of real players in game
	 * 
	 * @return
	 */
	public boolean checkActivePlayers() {
		for (String ent : this.getPlayersNames()) {
			System.out.println("[GameWrapper]" + ent);
		}
		int connectedPlayers = 0;
		for (Player ent : users) {
			if (ent.getConnectionStatus() && !ent.isDummy()) {
				connectedPlayers++;
			}
		}
		System.out.println("[GameWrapper]Real Users: " + connectedPlayers);
		if (connectedPlayers == 0) {
			System.out
					.println("[GameWrapper]No more active players...Closing game");
			this.endThread();
			return false;
		}
		System.out.println("[GameWrapper]Active Players Left");
		return true;
	}
}