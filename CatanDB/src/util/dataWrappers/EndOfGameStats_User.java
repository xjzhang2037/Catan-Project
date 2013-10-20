package util.dataWrappers;

import java.io.Serializable;

/**
 * Wrapper for information about a played game on a per user basis
 * Passed into GameTracker interface methods
 * 
 * @author Michael Tuer
 *
 */
public class EndOfGameStats_User implements Serializable{
	private static final long serialVersionUID = 1L;
	public int userid;
	public int devCardsPlayed;
	public int roadsBuilt;
	public int settlementsBuilt;
	public int citiesBuilt;
	public int cardsTraded;
	public int victoryPoints;
	
	/**
	 * Constructor 
	 * 
	 * @param userid
	 * @param devCardsPlayed
	 * @param roadsBuilt
	 * @param settlementsBuilt
	 * @param citiesBuilt
	 * @param cardsTraded
	 * @param victoryPoints
	 */
	public EndOfGameStats_User(int userid, int devCardsPlayed, int roadsBuilt, int settlementsBuilt, int citiesBuilt, int cardsTraded, int victoryPoints){
		this.userid = userid;
		this.devCardsPlayed = devCardsPlayed;
		this.roadsBuilt = roadsBuilt;
		this.settlementsBuilt = settlementsBuilt;
		this.citiesBuilt = citiesBuilt;
		this.cardsTraded = cardsTraded;
		this.victoryPoints = victoryPoints;
	}
}
