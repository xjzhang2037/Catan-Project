package util.dataWrappers;

import java.io.Serializable;

/**
 * Data wrapper for the statistics aggregated in the StatChecker interface
 * 
 * @author Michael Tuer
 *
 */
public class UserStats implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int totalGamesPlayed;
	public int totalDevCardsPlayed;
	public int totalRoadsBuilt;
	public int totalSettlementsBuilt;
	public int totalCitiesBuilt;
	public int totalCardsTraded;
	public int totalVictoryPoints;

	public int mostDevCardsPlayed;
	public int mostRoadsBuilt;
	public int mostSettlementsBuilt;
	public int mostCitiesBuilt;
	public int mostCardsTraded;
	public int mostVictoryPoints;
	
	public int totalTimePlayed;
	public int totalWins;
	public int totalMostAggresiveTrader;
	public int totalUtterLoser;
	public int totalBeggar;
	public int totalLongestRoadHolder;
	public int totalLargestArmyHolder;
	public int totalSevensRolled;
	public int totalMostRobbed;
	public int totalGameTurns;
	
	/**
	 * Constructor
	 * 
	 * @param totalGamesPlayed
	 * @param totalDevCardsPlayed
	 * @param totalRoadsBuilt
	 * @param totalSettlementsBuilt
	 * @param totalCitiesBuilt
	 * @param totalCardsTraded
	 * @param totalVictoryPoints
	 * @param mostDevCardsPlayed
	 * @param mostRoadsBuilt
	 * @param mostSettlementsBuilt
	 * @param mostCitiesBuilt
	 * @param mostCardsTraded
	 * @param mostVictoryPoints
	 * @param totalTimePlayed
	 * @param totalWins
	 * @param totalMostAggressiveTrader
	 * @param totalUtterLoser
	 * @param totalBeggar
	 * @param totalLongestRoadHolder
	 * @param totalLargestArmyHolder
	 * @param totalSevensRolled
	 * @param totalMostRobbed
	 * @param totalGameTurns
	 */
	public UserStats(int totalGamesPlayed, int totalDevCardsPlayed, int totalRoadsBuilt, int totalSettlementsBuilt, int totalCitiesBuilt, int totalCardsTraded, int totalVictoryPoints, 
			int mostDevCardsPlayed, int mostRoadsBuilt, int mostSettlementsBuilt, int mostCitiesBuilt, int mostCardsTraded, int mostVictoryPoints, int totalTimePlayed, int totalWins, int totalMostAggressiveTrader, int totalUtterLoser, int totalBeggar, int totalLongestRoadHolder, int totalLargestArmyHolder, int totalSevensRolled, int totalMostRobbed, int totalGameTurns){
		this.totalGamesPlayed = totalGamesPlayed;
		this.totalDevCardsPlayed = totalDevCardsPlayed;
		this.totalRoadsBuilt = totalRoadsBuilt;
		this.totalSettlementsBuilt = totalSettlementsBuilt;
		this.totalCitiesBuilt = totalCitiesBuilt;
		this.totalCardsTraded = totalCardsTraded;
		this.totalVictoryPoints = totalVictoryPoints;
		
		this.mostDevCardsPlayed = mostDevCardsPlayed;
		this.mostRoadsBuilt = mostRoadsBuilt;
		this.mostSettlementsBuilt = mostSettlementsBuilt;
		this.mostCitiesBuilt = mostCitiesBuilt;
		this.mostCardsTraded = mostCardsTraded;
		this.mostVictoryPoints = mostVictoryPoints;
		
		this.totalTimePlayed = totalTimePlayed;
		this.totalWins = totalWins;
		this.totalMostAggresiveTrader = totalMostAggressiveTrader;
		this.totalUtterLoser = totalUtterLoser;
		this.totalBeggar = totalBeggar;
		this.totalLongestRoadHolder = totalLongestRoadHolder;
		this.totalLargestArmyHolder = totalLargestArmyHolder;
		this.totalSevensRolled = totalSevensRolled;
		this.totalMostRobbed = totalMostRobbed;
		this.totalGameTurns = totalGameTurns;
	}
}
