package catanNodes;
import java.util.LinkedList;
/**Node for each junction in Catan
 * Will hold settlements/cities if needed.
 * @author Xin Zhang */

public class JunctionNode {
	private int jNumber[] = {0,0};    // Number to track which node. Will be referenced by GUI and map y,x
	private int devType = 0;
	private int owner = 0;			  // tracks the current ownerID of the node if any.
	private LinkedList<PathEdge> cEdgeList = new LinkedList<PathEdge>(); // List of connecting edges.
	
	/** Constructors **/
	public JunctionNode(){}
	public JunctionNode(int y, int x){
		jNumber[0] = y; jNumber[1] = x; // Sets
	}
	
	/** Function to set the junction number y,x **/
	public void setjNumber( int y , int x ) throws Exception{
		if( x != 0 || y != 0 )
			throw new Exception("junction node already set as: " + jNumber);
		jNumber[0] = y; jNumber[1] = x; // Sets
	}
	
	/** Size 2 array x,y **/
	public int[] getjNumber(){
		return jNumber;
	}
	/** Sets the owner to the input String
	 * @param toSet	 */
	public void setOwner( int toSet ){
		owner = toSet;
		upgrade();
	}
	
	/** Upgrades to settlement / city **/
	public void upgrade() {
		devType += 1;
	}
	
	/** Returns whether the junction has a settlement / city or not.
	 * 0 for nothing. 1 settlement. 2 city. **/
	public int getCityType(){
		return devType;
	}
	
	/** Returns true if there is already a city in place **/
	public boolean hasCity(){
		return devType != 0;
	}
	
	/** Returns the owner if any of the junction **/
	public int getOwnerID(){
		return owner;		
	}
	
	/** Adds a path edge to the list **/
	public void addPath( PathEdge toAdd ){
		if ( cEdgeList.contains( toAdd ) )  // If it already exists, then skip.
			return;
		cEdgeList.addLast( toAdd );				// Else Add 
	}
	
	/** Creates a PathEdge between this and the input jNode and adds to list **/
	public void addPath( JunctionNode jAdd ){
		PathEdge toAdd = new PathEdge( this , jAdd );		
		cEdgeList.addLast( toAdd );
	}
	
	/** Returns Iterable List of all paths under this jNode **/
	public Iterable<PathEdge> PathEdges(){
		return cEdgeList;
	}
}