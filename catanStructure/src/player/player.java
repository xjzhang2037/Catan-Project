<<<<<<< player.java
package player;
import java.util.ArrayList;
/** Player Class
 * @author Wes
 * @author Xin Zhang */
public class player {
	private String name = "";
	private int playerID = 0;
	
	private int[] resource;  //The resource hand
	private ArrayList<Integer> development; //The development hand
	private int longestRoad; //Size of the longest road
	private int army_size; //Size of army
	private int points; //Amount of points
	private int numCities; //Number of cities
	private int numVillages; //Number of villages
	
	/** Default Player **/
	public player(){
		resource = new int[5]; // holds the count for each type of resource.
							   // Location of each resource is designated by type #
		development = new ArrayList<Integer>();
		longestRoad = 0;
		army_size = 0; 
		numCities = 0;
		numVillages = 0;
		points = 0;
	}
	
	/** Actual Player **/
	public player(String player , int ID ){
		name = player;
		playerID = ID;
		resource = new int[5]; // holds the count for each type of resource.
							   // Location of each resource is designated by type #
		development = new ArrayList<Integer>();
		longestRoad = 0;
		army_size = 0; 
		numCities = 0;
		numVillages = 0;
		points = 0;
	}
	
	public String getName(){
		return name;
	}
	public int getID(){
		return playerID;
	}
	
	/** Adds 1 Resource Card to your hand
	 * @param resourceCard - num defines type	 */
	public void addRec(int resourceCard , int quant){
		resource[resourceCard-1] += quant;
	}
	
	/** Gets the total # of resources of the player **/
	public int getTotalResources(){
		return resource[0] + resource[1] 
			 + resource[2] + resource[3]
			 + resource[4];			}
	
	/**Returns an array of your Resource Cards int[5]
	 * 	Resource# -1 == Location in array that corresponds
	 *  to the quantity of that resource
	 *  1 = ore
	 *  2 = Sheep / wool	*  3 = wheat / grain
	 *  4 = wood	    	*  5 = brick / clay
	 * @return resource array	 */
	public int[] getRec(){
		return resource;	}
	
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
}
=======
package player;
/** Player Class
 * @author Wes
 * @author Xin Zhang */
public class player {
	
	private String name = "";
	private int playerID = 0;
	private player_info info;
	
	public player( String str , int ID ){
		name = str;
		playerID = ID;
	}
	
	/**@return ID of player **/
	public int getID()	{
		return playerID;}
	
	/**@return Name of player **/
	public String getName() {
		return name;		}
	
	/** Resets playerInfo **/
	public void newGame(){
		info = new player_info();
	}
	
	/**@return instance of the player info **/
	public player_info pInfo(){
		return info;
	}
	
}
>>>>>>> 1.3
