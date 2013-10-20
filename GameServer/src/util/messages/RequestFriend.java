package util.messages;

/**
 * Message for requesting a friend
 * 
 * @author Dan Larson
 * 
 */
public class RequestFriend extends Message {

	private static final long serialVersionUID = 1L;
	int friendid;
	public String friendName;
	String message = null;

	public RequestFriend(String friendname, String msg) {
		friendName = friendname;
		this.setMessage(msg);

	}

	/**
	 * get friend id
	 * 
	 * @return
	 */
	public int getFriend() {
		return friendid;
	}

	/**
	 * set friend id
	 * 
	 * @param friendid
	 */
	public void setFriend(int friendid) {
		this.friendid = friendid;
	}

	/**
	 * get message
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * set message
	 * 
	 * @param msg
	 */
	public void setMessage(String msg) {
		this.message = msg;
	}

}
