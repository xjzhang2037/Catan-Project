package util.messages;

import java.util.Map;

import util.dataWrappers.DbMessage;

/**
 * Message for returning Friend Requests
 * 
 * @author Dan Larson
 * 
 */
public class FriendRequests extends Message {

	private static final long serialVersionUID = 1L;

	private Map<Integer, DbMessage> messages;

	public FriendRequests(Map<Integer, DbMessage> friendRequests) {
		this.setMessages(friendRequests);
	}

	/**
	 * Return request messages
	 * 
	 * @return request
	 */
	public Map<Integer, DbMessage> getMessages() {
		return messages;
	}

	/**
	 * Set request messages
	 * 
	 * @param messages
	 */
	public void setMessages(Map<Integer, DbMessage> messages) {
		this.messages = messages;
	}
}
