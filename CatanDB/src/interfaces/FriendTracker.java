package interfaces;

import java.util.List;
import java.util.Map;

import util.dataWrappers.DbMessage;

/**
 * Methods used to keep track of users' friends (add, remove, list,
 * request, delete Message)
 * 
 * @author Michael Tuer
 *
 */
public interface FriendTracker extends Connector {

	/**
	 * Get a list of the names of friends associated with
	 * the passed userid
	 * 
	 * @param userid
	 * 
	 * @return List<String> friend names
	 */
	public List<String> getFriends(int userid);
	
	/**
	 * Get a list of the userids of friends associated with
	 * the passed userid
	 * 
	 * @param userid
	 * 
	 * @return List<Integer> friend ids
	 */
	public List<Integer> getFriendIds(int userid);
	
	/**
	 * Get a map of the ids to names of friends associated with
	 * the passed userid
	 * 
	 * @param userid
	 * 
	 * @return Map<Integer, String> id to friend name
	 */
	public Map<Integer, String> getFriendMap(int userid);
	
	/**
	 * Adds a friend association between the two userids
	 * *NOTE* Automatically deletes the friend request
	 * 
	 * @param userid
	 * @param friendid
	 * 
	 * @return true if the users weren't already friends
	 */
	public boolean addFriend(int userid, int friendid);
	
	/**
	 * Sends a message to the friend id that requests friendship
	 * 
	 * @param userid user sending the message
	 * @param friendid person receiving friend request
	 * @param msg Message from the user
	 * 
	 * @return false if there is some error
	 */
	public boolean requestFriend(int userid, int friendid, String msg);
	
	/**
	 * Delete the friend association between the two users
	 * 
	 * @param userid
	 * @param friendid
	 * 
	 * @return false if there is some error
	 */
	public boolean removeFriend(int userid, int friendid);
	
	/**
	 * Get a map of requester id to friend requests
	 * 
	 * @param userid owner of friend requests
	 * 
	 * @return Map<Integer, DbMessage> senderid to request
	 */
	public Map<Integer, DbMessage> getFriendRequests(int userid);
	
	/**
	 * Delete a friend request
	 * 
	 * @param userid
	 * @param senderid
	 * 
	 * @return false if no request exists
	 */
	public boolean deleteRequest(int userid, int senderid);
}
