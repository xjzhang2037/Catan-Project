package util.messages;

/**
 * Message for removing a friend
 * 
 * @author Dan Larson
 * 
 */
public class RemoveFriend extends Message {

	private static final long serialVersionUID = 1L;
	String friend;

	public RemoveFriend(String name) {
		this.setFriend(name);
	}

	/**
	 * Get friend name
	 * 
	 * @return
	 */
	public String getFriend() {
		return friend;
	}

	/**
	 * Set friend name
	 * 
	 * @param friend
	 */
	public void setFriend(String friend) {
		this.friend = friend;
	}

}
