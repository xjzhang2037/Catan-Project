package util;

import java.io.Serializable;
import java.util.List;

/**
 * Wrapper for game info
 * 
 * @author Dan Larson
 * 
 */
public class GameInstancesWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public int gameid;
	public boolean inProgress;
	public int chatRoomNumber;
	public List<String> users;

	/**
	 * ToString method
	 */
	public String toString() {
		String ret = "Game Instance: " + name + "(" + gameid + ")\n"
				+ "In Progress? " + inProgress + "\nUsers:";
		for (String u : users) {
			ret += "\n\t" + u;
		}
		return ret;
	}

	/**
	 * Get Game name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set game name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get game id
	 * 
	 * @return
	 */
	public int getGameid() {
		return gameid;
	}

	/**
	 * Set Game id
	 * 
	 * @param gameid
	 */
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}

	/**
	 * Return game status
	 * 
	 * @return
	 */
	public boolean isInProgress() {
		return inProgress;
	}

	/**
	 * Set game status
	 * 
	 * @param inProgress
	 */
	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}

	/**
	 * Return users in game
	 * 
	 * @return
	 */
	public List<String> getUsers() {
		return users;
	}

	/**
	 * Set users in game
	 * 
	 * @param users
	 */
	public void setUsers(List<String> users) {
		this.users = users;
	}

	/**
	 * Set chatroom number
	 * 
	 * @param chatRoom
	 */
	public void setChatRoomNumber(int chatRoom) {
		this.chatRoomNumber = chatRoom;
	}

	/**
	 * Get chat room number
	 * 
	 * @return
	 */
	public int getChatRoomNumber() {
		return this.chatRoomNumber;
	}
}
