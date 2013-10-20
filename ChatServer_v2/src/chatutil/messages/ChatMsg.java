package chatutil.messages;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import chatutil.messages.Message;

/**
 * Stores a chat message from a user
 * 
 * @author Michael Tuer
 *
 */
public class ChatMsg extends Message {
		
	private static final long serialVersionUID = 1L;
	
	public int groupID;
	public String message;
	public int userID;
	public String username;
	
	/**
	 * Constructor
	 * @param msg Message to send to the group
	 * @param gid id of the group
	 */
	public ChatMsg(String msg, int gid) {
		super();
		groupID = gid;
		message = msg;
	}
	
	/**
	 * Display the contents of this message.	
	 */
	public String toString(){
		Date timestamp = new Date(this.timeStamp);
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(timestamp);
		String ts = cal.get(GregorianCalendar.HOUR_OF_DAY) + ":" + cal.get(GregorianCalendar.MINUTE)  + ":" + cal.get(GregorianCalendar.SECOND) ;
		return "@" + ts + "[G:"+ groupID + "; U:" + username + "] " + message;  
	}

}
