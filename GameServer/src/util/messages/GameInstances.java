package util.messages;

import java.util.List;

import util.GameInstancesWrapper;

/**
 * Message for list of current games
 * 
 * @author Dan Larson
 * 
 */
public class GameInstances extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<GameInstancesWrapper> games;

	public GameInstances(List<GameInstancesWrapper> games) {
		this.setGames(games);
	}

	/**
	 * Get list of game info
	 * 
	 * @return
	 */
	public List<GameInstancesWrapper> getGames() {
		return games;
	}

	/**
	 * Set list of game info
	 * 
	 * @param games
	 */
	public void setGames(List<GameInstancesWrapper> games) {
		this.games = games;
	}

}
