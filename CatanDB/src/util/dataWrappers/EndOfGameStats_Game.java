package util.dataWrappers;

import java.io.Serializable;

/**
 * Wrapper for information about a played game
 * Passed into GameTracker interface methods
 * 
 * @author Michael Tuer
 *
 */
public class EndOfGameStats_Game implements Serializable{
	private static final long serialVersionUID = 1L;
	public long gameLength;
	public long gameDate;
	public int winner;
	public int mostAggressiveTrader;
	public int utterLoser;
	public int beggar;
	public int longestRoadHolder;
	public int longestRoadLength;
	public int largestArmyHolder;
	public int largestArmySize;
	public int sevensRolled;
	public int mostRobbed;
	public int totalGameTurns;
	
	/**
	 * Constructor 
	 * 
	 * @param gameLength
	 * @param gameDate
	 * @param winner
	 * @param mostAggressiveTrader
	 * @param utterLoser
	 * @param beggar
	 * @param longestRoadHolder
	 * @param longestRoadLength
	 * @param largestArmyHolder
	 * @param largestArmySize
	 * @param sevensRolled
	 * @param mostRobbed
	 * @param totalGameTurns
	 */
	public EndOfGameStats_Game(long gameLength, long gameDate, int winner, int mostAggressiveTrader, int utterLoser, int beggar, int longestRoadHolder, int longestRoadLength, int largestArmyHolder, int largestArmySize, int sevensRolled, int mostRobbed, int totalGameTurns){
		this.gameLength = gameLength;
		this.gameDate = gameDate;
		this.winner = winner;
		this.mostAggressiveTrader = mostAggressiveTrader;
		this.utterLoser = utterLoser;
		this.beggar = beggar;
		this.longestRoadHolder = longestRoadHolder;
		this.longestRoadLength = longestRoadLength;
		this.largestArmyHolder = largestArmyHolder;
		this.largestArmySize = largestArmySize;
		this.sevensRolled = sevensRolled;
		this.mostRobbed = mostRobbed;
		this.totalGameTurns = totalGameTurns;
	}
}
