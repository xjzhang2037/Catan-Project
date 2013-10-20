package chatutil.messages;

/**
 * Message used to request that a friend joins a chat group
 * 
 * @author Michael Tuer
 *
 */
public class RequestJoinGroupMsg extends Message {
	private static final long serialVersionUID = 1L;
	public int senderID;
	public int userID;
	public int groupID;
	public String username;
	public String acceptorUsername;
	public boolean declined = false;
	public boolean responding = false;
	/**
	 * Constructor
	 * 
	 * @param uid userid of the friend
	 * @param gid id of the group to join
	 */
	public RequestJoinGroupMsg(int uid, int gid){
		userID = uid; //friend
		groupID = gid;
	}
	
	/**
	 * Displays the request to the user
	 */
	public String toString(){
		return username + " has invited you to group " + groupID;
	}
}
