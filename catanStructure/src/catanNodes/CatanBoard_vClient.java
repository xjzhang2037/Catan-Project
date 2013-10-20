package catanNodes;
import java.util.LinkedList;
import player.player;

/** Client version of Catan board
 * -- Will link to GUI --
 * @author Xin Zhang **/
/** TODO: */
public class CatanBoard_vClient {

	private int userID = 0; // ID of this player
	private LinkedList<AttributeNode> aNodeList;
	private player[] players;
	private int[] award_LongestRoad = { 0 , 4 }; // {Player ID that holds the award , size of longest road }
	private int[] award_LargestArmy = { 0 , 2 }; // {Player ID that holds the award , size of largest army }
	int turnNum = 0;
	
	/** ID of the user passed in **/
	public CatanBoard_vClient( int ID){
		userID = ID;
	}
	
	public void update( LinkedList<AttributeNode> input ){
		aNodeList = input;
	}
	
}
