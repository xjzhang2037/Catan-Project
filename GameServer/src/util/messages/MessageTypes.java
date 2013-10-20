package util.messages;

import java.io.Serializable;

/**
 * Message types of result of request message
 * 
 * @author Dan Larson
 * 
 */
public enum MessageTypes implements Serializable {
	JoinGame, AddUser, LeaveGame, SendMessage, ChangePassword, ChangeUsername, AcceptFriend, AddFriend, RemoveFriend;

}
