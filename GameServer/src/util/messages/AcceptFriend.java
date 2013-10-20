package util.messages;
/**
 * Message is sent by client to indicate accepting a friend
 * 
 * @author jwwebber
 */
public class AcceptFriend extends Message{

	private static final long serialVersionUID = 1L;
	int friendID; //id of the friend you are accepting
	public AcceptFriend(int friendID) {
		super();
		this.friendID = friendID;
	}
	
	/**
	 * @return the friendID
	 */
	public int getFriendID() {
		return friendID;
	}
	/**
	 * @param friendID the friendID to set
	 */
	public void setFriendID(int friendID) {
		this.friendID = friendID;
	}

}
