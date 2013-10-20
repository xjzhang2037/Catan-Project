package chatutil.messages;

import java.util.Queue;

import chatutil.messages.Message;
import chatutil.OutputBundle;


/**
 * Server Message includes the fields time and status.  
 * 
 * 
 * @author djlarson
 *
 */
public class ServerMsg extends Message{

	private static final long serialVersionUID = 1L;
	
	private transient OutputBundle gHandle;
	private transient Queue<Message> theQueue;
	
	public boolean status;
	
	/**
	 * Constructs a server message for sending
	 * 
	 * @param b boolean was the action successful?
	 */
	public ServerMsg(boolean b){
		status = b;
	}
	
	/**
	 * Displays the message to the client
	 */
	public String toString(){
		String retStr = "ServerMessage: received at " + this.timeStamp + ", Status: ";
		if(status){
			return retStr + "true";
		}
		else{
			return retStr + "false";
		}
		
	}	

	public Queue<Message> getQueue(){
		return theQueue;
	}
	public void setQueue(Queue<Message> q){
		theQueue = q;
	}
	public void setOutputBundle(OutputBundle gh){
		gHandle = gh;
	}
	
	public OutputBundle getOutputBundle() {
		return	gHandle;
	}

}
