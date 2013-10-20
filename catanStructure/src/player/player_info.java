package player;
import java.util.ArrayList;
/** Player Info Class -- resets every game
 * @author Wes
 * @author Xin Zhang */

public class player_info {

	private int[] resource;  				//The resource hand
	private ArrayList<Integer> development; //The development hand
	private int longestRoad; 				//Size of the longest road
	private int army_size;					//Size of army
	private int points; 					//Amount of points
	private int numCities; 					//Number of cities
	private int numVillages;				//Number of village
	private int playerNum = -1;				//Number relating to turn / player Num
	
	/** Trade Variables 
	 * 3:1 ? x4	 * 2:1 wool
	 * 2:1 brick * 2:1 wood
	 * 2:1 wheat * 2:1 ore	 */
	private boolean trade_3to1 = false;
	private boolean trade_2Wool = false;
	private boolean trade_2Brick = false;
	private boolean trade_2Wheat = false;
	private boolean trade_2Wood = false;
	private boolean trade_2Ore = false;
	
	/** Default Player **/
	public player_info(){
		resource = new int[5]; // holds the count for each type of resource.
							   // Location of each resource is designated by type #
		development = new ArrayList<Integer>();
		longestRoad = 0;
		army_size = 0; 
		numCities = 0;
		numVillages = 0;
		points = 0;
	}
	
	/**Sets player number **/
	public void setPlayerNum( int x ){
		playerNum = x;				}
	
	/** Gets player number **/
	public int get_pNum(){
		return playerNum;
	}
	
	
	/** Adds a Resource Card to your hand
	 * @param resourceCard - num defines type	 */
	public void addRec(int resourceCard , int quant){
		resource[resourceCard-1] += quant;
	}
	
	/**Returns an array of your Resource Cards int[5]
	 * 	Resource# -1 == Location in array that corresponds
	 *  to the quantity of that resource
	 *  1 = ore
	 *  2 = Sheep / wool	*  3 = wheat / grain
	 *  4 = wood	    	*  5 = brick / clay
	 * @return resource array	 */
	public int[] getRec(){
		return resource;	}
	
	/**@return total number of all resources **/
	public int getTotalRes(){
		return resource[0] + resource[1] + resource[2] + resource[3] + resource[4]; 
	}
	
	/**Finds the matching resource card and removes it from the array
	 * @param resourceCard
	 * @param size quantity to remove	 */
	public void removeRec(int resourceCard, int size){
		resource[resourceCard-1] -= size;		
	}
	
	/** Checks if they have enough of one resource
	 * 	1 = ore
	 *  2 = Sheep / wool	*  3 = wheat / grain
	 *  4 = wood	    	*  5 = brick / clay
	 * @param resourceCard
	 * @param size quantity to remove
	 * @return true if it has enough **/
	public boolean checkResource( int resourceType , int size){
		return resource[resourceType-1] >= size;
	}	
	
	/** Gives you a Development Card
	 * @param developmentCard */
	public void addDev(int developmentCard){
		development.add(developmentCard);
	}
	
	/** @return Returns an arrayList of your Development Cards */
	public ArrayList<Integer> get_DevCardList(){
		return development;
	}
	
	/** Given input, it will use that card.
	 * -- Will Link to GUI --
	 * @param developmentCard	 */
	public void useDevCard(int index){
		if( 0 <= index && index < development.size() ){
			development.remove( index );
		}
	}
	
	/** Adds points to total score, add neg points to subtract
	 * @param p	 */
	public void addPoints(int p){
		points+=p;
	}
	
	/** Returns total points
	 * @return points	 */
	public int getPoints(){
		return points;
	}
	
	/** Adds the amount of cities
	 * @param c	 */
	public void addCities(int c){
		numCities+=c;
		addPoints(2);
	}
	
	/**	 * Returns the number of cities
	 * @return	 */
	public int get_numCities(){
		return numCities;
	}
	
	/**	 * Adds the amount of cities
	 * @param c	 */
	public void addSettlement(int v){
		numVillages+=v;
		addPoints(1);
	}
	
	/**	 * Returns the amount of villages
	 * @return	 */
	public int get_numSettlement(){
		return numVillages;
	}
	
	/** Get's the length of the longest road **/
	public int get_lengthOfLongestRoad(){
		return longestRoad;
	}
	
	/** Adds to the size of the army
	 * @param size number to add	 */
	public void add2Army( int size ){
		army_size += size;
	}
	
	/**@return current size of army **/
	public int get_ArmySize(){
		return army_size;
	}
	
	/**--------Trade Enable / Getter--------**/
	
	/** Enable 3 to 1 type trade **/
	public void enableThreeToOne(){
		trade_3to1 = true;    	}
	
	/**@return true if this type of trade is possible */
	public boolean getThreeToOne(){
		return trade_3to1;
	}
	
	/** Enables trade combo 2:1 Wool */
	public void enableTwoWool(){
		trade_2Wool = true;    }
	
	/**@return true if this type of trade is possible */
	public boolean getTwoWool(){
		return trade_2Wool;
	}
	
	/** Enables trade combo 2:1 Brick	 */
	public void enableTwoBrick(){
		trade_2Brick = true;	}
	
	/**@return true if this type of trade is possible */
	public boolean getTwoBrick(){
		return trade_2Brick;
	}
	
	/**	 Enables trade combo 2:1 Wheat	 */
	public void enableTwoWheat(){
		trade_2Wheat = true;	}
	
	/**@return true if this type of trade is possible */
	public boolean getTwoWheat(){
		return trade_2Wheat;
	}
	
	/** Enables trade combo 2:1 Wood	 */
	public void enableTwoWood(){
		trade_2Wood = true;		}
	
	/**@return true if this type of trade is possible */
	public boolean getTwoWood(){
		return trade_2Wood;
	}
	
	/**	Enables trade combo 2:1 Ore	 */
	public void enableTwoOre(){
		trade_2Ore = true;	}
	
	/**@return true if this type of trade is possible */
	public boolean getTwoOre(){
		return trade_2Ore;
	}
}
