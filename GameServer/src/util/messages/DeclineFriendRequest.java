package util.messages;

/**
 * Message to decline a friend request
 * 
 * @author Dan Larson
 * 
 */
public class DeclineFriendRequest extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int friend = 0;

	public DeclineFriendRequest(int declineFriend) {
		setFriend(declineFriend);
	}

	/**
	 * Get friend id
	 * 
	 * @return id
	 */
	public int getFriend() {
		return friend;
	}

	/**
	 * Set friend id
	 * 
	 * @param friend
	 */
	public void setFriend(int friend) {
		this.friend = friend;
	}
}
