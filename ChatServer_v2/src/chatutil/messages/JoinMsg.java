package chatutil.messages;

import chatutil.messages.Message;

/**
 * Attempt to join the Server
 * Join Message includes the fields time, user name, and user id.  
 * 
 * @author djlarson
 *
 */
public class JoinMsg extends Message {
	
	private static final long serialVersionUID = 1L;
	
	public int userID;
	public String username;
	
	/**
	 * Constructs the join message for sending
	 * 
	 * @param uid userid
	 * @param username 
	 */
	public JoinMsg(int uid, String username){
		super();
		userID = uid;
		this.username = username;
	}

	
}
