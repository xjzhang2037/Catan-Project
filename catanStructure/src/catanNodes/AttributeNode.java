package catanNodes;
import java.util.LinkedList;

/** Attribute Node for Catan
 * Will be the "head" of a graph
 * @author Xin Zhang */

public class AttributeNode {
	private char name = ' ';
	private int resource = 0;		// Resource number to correspond with tile type.
	private int triggerNum = 0;		// Trigger number that corresponds with the dice number
	private LinkedList<JunctionNode> jNodeList = new LinkedList<JunctionNode>();
	private boolean hasRobber = false;
	
	public AttributeNode(){} // blank constructor
	/** Constructor for aNode **/
	public AttributeNode( char tile , int res , int trig ){
		name = tile;
		resource = res;
		triggerNum = trig;
	}
	
/**--------------------- Setters ---------------------**/	
	/** Sets tile with resource type
	 *  0 = desert	 		*  1 = ore
	 *  2 = Sheep / wool	*  3 = wheat / grain
	 *  4 = wood	    	*  5 = brick / clay
	 * @param toSet - int to pass in	 
	 * @throws Exception */
	public void setResource( int toSet ) throws Exception{
		if( 0 <= toSet && toSet < 6 )
			resource = toSet;
		else
			throw new Exception("Invalid Resource Number!");		
	}
	
	/** Sets the trigger number to the input number
	 * Should be a number that is a sum of two dice rolls
	 * @param toSet - Number passed in
	 * @throws Exception */
	public void setTrigger( int toSet ) {
		if( 1 < toSet && toSet < 13 )
			triggerNum = toSet;
		else
			System.err.println("NO , BAD, BAD!");
	}
	
	/** Adds a junction Node to the list 
	 * @return the size of the list after the node is added
	 * @throws Exception  if more than 6 nodes are added**/
	public int add2List( JunctionNode toAdd ){
		if( jNodeList.contains( toAdd ) ) // We do not want to add if it already contains
			return jNodeList.size();
		if ( jNodeList.size() > 6 )
			return -9001;
		
		jNodeList.addLast( toAdd );
		return jNodeList.size();
	}
	
	/** Throws exception in name is already set **/
	public void setName( char toSet ) {
		name = toSet;	}
	
	/** Sets a robber true or false on this tile 
	 * returns -9001 if there is already a robber here**/
	public int setThief( boolean toSet ){
		if( hasRobber )
			return -9001;
		
		hasRobber = toSet;
		return 0;
	}
	
	/** @return true if the two attributenodes are equal **/
	public boolean equals( AttributeNode check ){
		return check.name == name;	}
	
	/**---------------------------- Getters ------------------------------ **/
	/** @return resource number **/
	public int getResourceNumber(){
		return resource;		}
	
	/** @return trigger number **/
	public int getTrigger(){
		return triggerNum; }
		
	/** Returns the iterable of the junction nodes in the attribute node **/
	public Iterable<JunctionNode> jNodes(){
		return jNodeList;	}
	
	/** No real use except for debugging **/
	public char getName(){
		return name;	}
	
	/** Returns whether this tile has a thief or not **/
	public boolean hasThief(){
		return hasRobber;	}
	
	/** equals method **/
	public boolean equals( AttributeNode check ){
		return this.name == check.getName();
	}
	
}
