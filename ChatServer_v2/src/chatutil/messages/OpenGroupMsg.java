package chatutil.messages;

import chatutil.messages.Message;

/**
 * Group Message includes the a list of open groups.  
 * 
 * @author djlarson
 *
 */
public class OpenGroupMsg extends Message{

	private static final long serialVersionUID = 1L;
	public int userID;
	public int groupID;
	
	/**
	 * Creates a new group on the server for users to join
	 */
	public OpenGroupMsg(){
		super();
	}
	
}
