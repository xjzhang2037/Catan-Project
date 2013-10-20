package util.messages;

import java.util.List;

import util.Friend;

/**
 * Message for list of friends
 * 
 * @author Dan Larson
 * 
 */
public class Friends extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Friend> allFriends;

	public Friends(List<Friend> all) {
		this.setAllFriends(all);
	}

	/**
	 * Get list of friends
	 * 
	 * @return
	 */
	public List<Friend> getAllFriends() {
		return allFriends;
	}

	/**
	 * Set all friends
	 * 
	 * @param friends
	 */
	public void setAllFriends(List<Friend> friends) {
		this.allFriends = friends;
	}

}
