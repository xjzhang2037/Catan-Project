package util.messages;

import util.dataWrappers.UserStats;

/**
 * Message for user stats
 * 
 * @author Dan Larson
 * 
 */
public class UserInfo extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserStats userinfo;

	public UserInfo(UserStats userinfo) {
		this.userinfo = userinfo;

	}

	/**
	 * Get user stats
	 * 
	 * @return
	 */
	public UserStats getUserStats() {
		return userinfo;
	}

}