package chatutil.messages;

import chatutil.messages.Message;

/**
 * Used to disconnect from the server safely
 * 
 * @author Michael Tuer
 *
 */
public class CloseConnectionMsg extends Message {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs the CloseConnectionMsg
	 * Used to inform the server of a disconnect
	 */
	public CloseConnectionMsg(){
		super();
	}



}
