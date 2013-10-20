package chatutil.messages;

import java.io.Serializable;

/**
 * Super class for all messages sent over the server
 * Serializable wrapper for data
 * 
 * @author Michael Tuer
 *
 */
public abstract class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	public long timeStamp;
	
	/**
	 * Constructor that keeps track of the time the 
	 * message was created
	 */
	public Message(){
		timeStamp = System.currentTimeMillis();
	}
}
