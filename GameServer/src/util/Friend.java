package util;

import java.io.Serializable;

/**
 * Wrapper class for friend info
 * 
 * @author Dan Larson
 * 
 */
public class Friend implements Serializable {

	private static final long serialVersionUID = 1L;
	int friendID = -1;
	String friendName = null;
	boolean connected = false;

	/**
	 * Friend constructor
	 * 
	 * @param id
	 * @param name
	 * @param connection
	 *            status
	 */
	public Friend(int id, String name, boolean status) {
		this.setFriendID(id);
		this.setFriendName(name);
		this.setConnected(status);
	}

	/**
	 * Get Friend id
	 * 
	 * @return
	 */
	public int getFriendID() {
		return friendID;
	}

	/**
	 * Set friend id
	 * 
	 * @param friendID
	 */
	public void setFriendID(int friendID) {
		this.friendID = friendID;
	}

	/**
	 * Get friend name
	 * 
	 * @return
	 */
	public String getFriendName() {
		return friendName;
	}

	/**
	 * Set friend name
	 * 
	 * @param friendName
	 */
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	/**
	 * Returns connection status
	 * 
	 * @return
	 */
	public boolean checkConnection() {
		return connected;
	}

	/**
	 * Set connection status
	 * 
	 * @param connected
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

}
