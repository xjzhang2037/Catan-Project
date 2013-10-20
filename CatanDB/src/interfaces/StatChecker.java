package interfaces;

import util.dataWrappers.UserStats;

/**
 * Aggregates statistics for users to be displayed on the 
 * user info screen in the lobby
 * 
 * @author Michael Tuer
 *
 */
public interface StatChecker extends Connector{

	/**
	 * Get a UserStats object wrapping all the statistics
	 * 
	 * @param userid
	 * 
	 * @return UserStats
	 */
	public UserStats getStats(int userid);
}
