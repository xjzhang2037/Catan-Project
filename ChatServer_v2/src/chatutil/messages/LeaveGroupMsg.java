package chatutil.messages;

/**
 * Leave a group on the server so the user will stop receiving messages
 * 
 * @author Michael Tuer
 *
 */
public class LeaveGroupMsg extends Message {

	private static final long serialVersionUID = 1L;
	
	public int groupID;
	
	/**
	 * Constructor
	 * 
	 * @param gid id of the group to leave
	 */
	public LeaveGroupMsg(int gid){
		super();
		groupID = gid;
	}
}
