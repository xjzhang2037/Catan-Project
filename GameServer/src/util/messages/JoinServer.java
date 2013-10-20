package util.messages;

/**
 * Message for joining the server
 * 
 * @author Dan Larson
 * 
 */
public class JoinServer extends Message {

	private static final long serialVersionUID = 1L;
	private int userid;

	/**
	 * 
	 * @param id
	 */
	public JoinServer(int id) {
		userid = id;
	}

	/**
	 * Get user id
	 * 
	 * @return userid
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * setUserid
	 * 
	 * @param userid
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

}
