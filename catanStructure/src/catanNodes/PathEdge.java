package catanNodes;

/** Will be the vertex / path between junction Nodes
 * @author Xin Zhang */

public class PathEdge{
	private boolean hasRoad = false;
	private int owner = 0;
	private JunctionNode[] endPoints = new JunctionNode[2]; // Array that will hold the two JunctionNodes on each end.
	
	public PathEdge( JunctionNode first , JunctionNode second ){
		endPoints[0] = first;
		endPoints[1] = second;
	}
	
	/** Sets the owner to the input String
	 * @param toSet
	 * @return 0 if it works, -9001 if there is already a road there.	 */
	public void setOwner( int toSet ){
			owner = toSet;
			hasRoad = true;
	}	
	
	public boolean hasRoad(){
		return hasRoad;  	}
	
	public int getOwner(){
		return owner;   	}
	
	/** Sets one of the endPoints **/
	public void setjNodeA( JunctionNode toSet ){
		endPoints[0] = toSet;  				}
	
	/** Sets one of the endPoints **/
	public void setjNodeB( JunctionNode toSet ){
		endPoints[1] = toSet;				}
	
	/** Gets one of the endPoints **/
	public JunctionNode getjNodeA( JunctionNode toSet ){
		return endPoints[0];	}
	
	/** Gets one of the endPoints **/
	public JunctionNode getjNodeB( JunctionNode toSet ){
		return endPoints[1];	}
	
	/** Given one jNode it will return the other end
	 * @return the other jNode, null if it does not exist	 */
	public JunctionNode getOpposite( JunctionNode inNode ){
		if( inNode != endPoints[0] || inNode != endPoints[1])
			return null;
		return (inNode == endPoints[0]) ? endPoints[1] : endPoints[0];
	}
	
}
