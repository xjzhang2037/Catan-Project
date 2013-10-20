package util.dataWrappers;

import java.io.Serializable;

/**
 * Wrapper for information stored in the TB_FRIEND_REQUESTS table
 * 
 * @author Michael Tuer
 *
 */
public class DbMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int userid;
	public int senderid;
	public String senderName;
	public String message;
	
	/**
	 * Constructor 
	 * 
	 * @param userid
	 * @param senderid
	 * @param message
	 */
	public DbMessage(int userid, int senderid, String senderName, String message){
		this.userid = userid;
		this.senderid = senderid;
		this.message = message;
		this.senderName = senderName;
	}
	
	/**
	 * Display information stored in this object
	 */
	public String toString(){
		return "[DBMESSAGE]UID:" + userid + ";SID:" + senderid + ";MSG: " + message;
	}
}
