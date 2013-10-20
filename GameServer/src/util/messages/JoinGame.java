package util.messages;

/**
 * Join Message includes the fields time, user name, and user id.
 * 
 * 
 * @author Dan Larson
 * 
 */
public class JoinGame extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int gameid;

	public JoinGame(int gameid) {
		setGameid(gameid);
	}

	/**
	 * Get game id
	 * 
	 * @return id
	 */
	public int getGameid() {
		return gameid;
	}

	/**
	 * Set game id
	 * 
	 * @param gameid
	 */
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}

}
