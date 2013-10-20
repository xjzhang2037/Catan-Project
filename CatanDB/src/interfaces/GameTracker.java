package interfaces;

import java.util.List;

import util.dataWrappers.EndOfGameStats_Game;
import util.dataWrappers.EndOfGameStats_User;

/**
 * Add statistics about a completed game to the database that can
 * be used to display user statistics in the StatChecker interface
 * 
 * @author Michael Tuer
 *
 */
public interface GameTracker extends Connector {
	
	/**
	 * Add statistics about the game to the database
	 * 
	 * @param game EndOfGameStats_Game for the game
	 * @param users List of EndOfGameStats_User for each user in the game
	 * 
	 * return true if there were no errors
	 */
	public boolean addGame(EndOfGameStats_Game game, List<EndOfGameStats_User> users);
	
}
