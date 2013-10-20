package chatutil.messages;

import chatutil.messages.Message;

/**
 * GroupJoin Message includes the fields time, group id, and user id.  
 * 
 * 
 * @author djlarson
 *
 */
public class GroupJoinMsg extends Message{
	
	private static final long serialVersionUID = 1L;
	public int groupID;
	public int userID;
	
	/**
	 * Constructor
	 * 
	 * @param groupID id of the group to join
	 */
	public GroupJoinMsg(int groupID){
		super();
		this.groupID = groupID;
	}
	

}
