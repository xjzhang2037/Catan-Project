package util.messages;

/**
 * Message to create a game
 * 
 * @author Dan Larson
 * 
 */
public class CreateGame extends Message {
	private static final long serialVersionUID = 1L;

	public String gameName;
	public int gameID;
	public int chatGroupID = -1;

	public CreateGame(String name) {
		this.setGamename(name);
	}

	/**
	 * Get Game name
	 * 
	 * @return game name
	 */
	public String getGamename() {
		return gameName;
	}

	/**
	 * Set Game name
	 * 
	 * @param name
	 */
	public void setGamename(String name) {
		this.gameName = name;
	}

	/**
	 * Get game id
	 * 
	 * @return id
	 */
	public int getGameID() {
		return gameID;
	}

	/**
	 * Set game id
	 * 
	 * @param gameID
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

}
