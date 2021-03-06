<<<<<<< CatanBoard_vServer.java
package catanNodes;
import java.util.ArrayList;
import java.util.LinkedList;
import player.player;
import devCards.CardArray;

/** The class that will construct the Catan map
 *  Will contain some rules functions
 *  @author Xin Zhang */

public class CatanBoard_vServer{
	
	private LinkedList<AttributeNode> aNodeList; // List for all tiles
	private CardArray deck;
	private player[] players;
	private int[] award_LongestRoad = { 0 , 4 }; // {Player ID that holds the award , size of longest road }
	private int[] award_LargestArmy = { 0 , 2 }; // {Player ID that holds the award , size of largest army }
	private AttributeNode thiefLocation;	
	int turnNum = 0;
	
	/** Constructor for standard catanBoard will set up the game 
	 * @param Number of players
	 * @param This will be the pre-game Lobby's record of the players.**/
	public CatanBoard_vServer( int numPlayers , ArrayList<player> playerFromLobby){
		aNodeList = new LinkedList<AttributeNode>();
		deck = new CardArray();		
		players = (player[]) playerFromLobby.toArray(); // The array of players should already contain ID and string name
		
		ResourceDistro tileStack = new ResourceDistro();
		tileStack.shuffleStack(35); // Resource Stack setter , number can be a variable later
		// No actual use except for debug ( name Alignment array )
		int index = 0; // counter for nAlign and trigNum
		int tindex = 0;
		// Hard code trigger number order
		char nAlignArr[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S'}; 
		int trigNum[]    = { 5 , 4 , 2 , 6 , 8 , 10, 9 , 12, 11, 4 , 8 , 10, 9 , 4 , 5 , 6 , 3 , 11}; 
		
		/** Hardcode for tile 'placement' and setup **/
		while( !tileStack.isEmpty() ){
			int rVal = tileStack.popStack(); 	// Popped value for resource value
			if( rVal == 0 ){ // Desert case
				AttributeNode toAdd = new AttributeNode(nAlignArr[index] , rVal , 7 );	// Creates the attribute node
				toAdd.setThief( true );				// Special settings
				thiefLocation = toAdd;				// Updates the current location of the thief
				aNodeList.addLast(toAdd);			// Adds to list				
				index ++;							// Increments				
			} else {
				AttributeNode toAdd = new AttributeNode(nAlignArr[index] , rVal , trigNum[tindex] );	// Creates the attribute node
				aNodeList.addLast(toAdd);			// Adds to list
				index ++;							// Increments
				tindex++;
			} 			
		}
		/**-----Now to add jNodes to the attribute nodes-----**/
		JunctionNode Y1X4 = new JunctionNode(1,4), Y1X6 = new JunctionNode(1,6),Y1X8 = new JunctionNode(1,8);
		//----
		JunctionNode Y2X3 = new JunctionNode(2,3), Y2X5 = new JunctionNode(2,5);
		JunctionNode Y2X7 = new JunctionNode(2,7), Y2X9 = new JunctionNode(2,9);
		//----
		JunctionNode Y3X3 = new JunctionNode(3,3), Y3X5 = new JunctionNode(3,5);
		JunctionNode Y3X7 = new JunctionNode(3,7), Y3X9 = new JunctionNode(3,9);	
		//----
		JunctionNode Y4X2 = new JunctionNode(4,2),Y4X4 = new JunctionNode(4,4);
		JunctionNode Y4X6 = new JunctionNode(4,6), Y4X8 = new JunctionNode(4,8);
		JunctionNode Y4X10 = new JunctionNode(4,10);	
		//----
		JunctionNode Y5X2 = new JunctionNode(5,2), Y5X4 = new JunctionNode(5,4);	
		JunctionNode Y5X6 = new JunctionNode(5,6), Y5X8 = new JunctionNode(5,8);
		JunctionNode Y5X10 = new JunctionNode(5,10);	
		//----
		JunctionNode Y6X1 = new JunctionNode(6,1), Y6X3 = new JunctionNode(6,3);
		JunctionNode Y6X5 = new JunctionNode(6,5), Y6X7 = new JunctionNode(6,7);
		JunctionNode Y6X9 = new JunctionNode(6,9), Y6X11 = new JunctionNode(6,11);
		//----
		JunctionNode Y7X1 = new JunctionNode(7,1), Y7X3 = new JunctionNode(7,3);	
		JunctionNode Y7X5 = new JunctionNode(7,5), Y7X7 = new JunctionNode(7,7);
		JunctionNode Y7X9 = new JunctionNode(7,9), Y7X11 = new JunctionNode(7,11);
		//----
		JunctionNode Y8X2 = new JunctionNode(8,2), Y8X4 = new JunctionNode(8,4);
		JunctionNode Y8X6 = new JunctionNode(8,6), Y8X8 = new JunctionNode(8,8);
		JunctionNode Y8X10 = new JunctionNode(8,10);	
		//----
		JunctionNode Y9X2 = new JunctionNode(9,2), Y9X4 = new JunctionNode(9,4);	
		JunctionNode Y9X6 = new JunctionNode(9,6), Y9X8 = new JunctionNode(9,8);	
		JunctionNode Y9X10 = new JunctionNode(9,10);	
		//----
		JunctionNode Y10X3 = new JunctionNode(10,3), Y10X5 = new JunctionNode(10,5);
		JunctionNode Y10X7 = new JunctionNode(10,7), Y10X9 = new JunctionNode(10,9);
		//----
		JunctionNode Y11X3 = new JunctionNode(11,3), Y11X5 = new JunctionNode(11,5);
		JunctionNode Y11X7 = new JunctionNode(11,7), Y11X9 = new JunctionNode(11,9);
		//----
		JunctionNode Y12X4 = new JunctionNode(12,4), Y12X6 = new JunctionNode(12,6);
		JunctionNode Y12X8 = new JunctionNode(12,8);	
		/** Now create appropriate edgePath to add to the jNode **/
		PathEdge Y2X3cY1X4 = new PathEdge(Y2X3 , Y1X4), Y1X4cY2X5 = new PathEdge(Y1X4 , Y2X5);
		PathEdge Y2X5cY1X6 = new PathEdge(Y2X5 , Y1X6), Y1X6cY2X7 = new PathEdge(Y1X6 , Y2X7);
		PathEdge Y2X7cY1X8 = new PathEdge(Y2X7 , Y1X8), Y1X8cY2X9 = new PathEdge(Y1X8 , Y2X9); 
		//----
		PathEdge Y2X3cY3X3 = new PathEdge(Y2X3 , Y3X3), Y2X5cY3X5 = new PathEdge(Y2X5 , Y3X5);
		PathEdge Y2X7cY3X7 = new PathEdge(Y2X7 , Y3X7), Y2X9cY3X9 = new PathEdge(Y2X9 , Y3X9); 
		//----
		PathEdge Y4X2cY3X3 = new PathEdge(Y4X2 , Y3X3), Y3X3cY4X4 = new PathEdge(Y3X3 , Y4X4);
		PathEdge Y4X4cY3X5 = new PathEdge(Y4X4 , Y3X5), Y3X5cY4X6 = new PathEdge(Y3X5 , Y4X6);
		PathEdge Y4X6cY3X7 = new PathEdge(Y4X6 , Y3X7), Y3X7cY4X8 = new PathEdge(Y3X7 , Y4X8); 
		PathEdge Y4X8cY3X9 = new PathEdge(Y4X8 , Y3X9), Y3X9cY4X10 = new PathEdge(Y3X9 , Y4X10); 
		//----
		PathEdge Y4X2cY5X2 = new PathEdge(Y4X2 , Y5X2), Y4X4cY5X4 = new PathEdge(Y4X4 , Y5X4);
		PathEdge Y4X6cY5X6 = new PathEdge(Y4X6 , Y5X6), Y4X8cY5X8 = new PathEdge(Y4X8 , Y5X8); 
		PathEdge Y4X10cY5X10 = new PathEdge(Y4X10 , Y5X10); 	
		//----
		PathEdge Y6X1cY5X2 = new PathEdge(Y6X1 , Y5X2), Y5X2cY6X3 = new PathEdge(Y5X2 , Y6X3);
		PathEdge Y6X3cY5X4 = new PathEdge(Y6X3 , Y5X4), Y5X4cY6X5 = new PathEdge(Y5X4 , Y6X5); 
		PathEdge Y6X5cY5X6 = new PathEdge(Y6X5 , Y5X6), Y5X6cY6X7 = new PathEdge(Y5X6 , Y6X7); 
		PathEdge Y6X7cY5X8 = new PathEdge(Y6X7 , Y5X8), Y5X8cY6X9 = new PathEdge(Y5X8 , Y6X9);
		PathEdge Y6X9cY5X10 = new PathEdge(Y6X9 , Y5X10), Y5X10cY6X11 = new PathEdge(Y5X10 , Y6X11);
		//----
		PathEdge Y6X1cY7X1 = new PathEdge(Y6X1 , Y7X1), Y6X3cY7X3 = new PathEdge(Y6X3 , Y7X3);
		PathEdge Y6X5cY7X5 = new PathEdge(Y6X5 , Y7X5), Y6X7cY7X7 = new PathEdge(Y6X7 , Y7X7);
		PathEdge Y6X9cY7X9 = new PathEdge(Y6X9 , Y7X9), Y6X11cY7X11 = new PathEdge(Y6X11 , Y7X11);
		//----
		PathEdge Y7X1cY8X2 = new PathEdge(Y7X1 , Y8X2), Y8X2cY7X3 = new PathEdge(Y8X2 , Y7X3);
		PathEdge Y7X3cY8X4 = new PathEdge(Y7X3 , Y8X4), Y8X4cY7X5 = new PathEdge(Y8X4 , Y7X5);
		PathEdge Y7X5cY8X6 = new PathEdge(Y7X5 , Y8X6), Y8X6cY7X7 = new PathEdge(Y8X6 , Y7X7);
		PathEdge Y7X7cY8X8 = new PathEdge(Y7X7 , Y8X8), Y8X8cY7X9 = new PathEdge(Y8X8 , Y7X9);
		PathEdge Y7X9cY8X10 = new PathEdge(Y7X9 , Y8X10), Y8X10cY7X11 = new PathEdge(Y8X10 , Y7X11);
		//----
		PathEdge Y8X2cY9X2 = new PathEdge(Y8X2 , Y9X2); 		PathEdge Y8X4cY9X4 = new PathEdge(Y8X4 , Y9X4);
		PathEdge Y8X6cY9X6 = new PathEdge(Y8X6 , Y9X6); 		PathEdge Y8X8cY9X8 = new PathEdge(Y8X8 , Y9X8);
		PathEdge Y8X10cY9X10 = new PathEdge(Y8X10 , Y9X10);
		//----
		PathEdge Y9X2cY10X3 = new PathEdge(Y9X2 , Y10X3), Y10X3cY9X4 = new PathEdge(Y10X3 , Y9X4);
		PathEdge Y9X4cY10X5 = new PathEdge(Y9X4 , Y10X5), Y10X5cY9X6 = new PathEdge(Y10X5 , Y9X6);
		PathEdge Y9X6cY10X7 = new PathEdge(Y9X6 , Y10X7), Y10X7cY9X8 = new PathEdge(Y10X7 , Y9X8);
		PathEdge Y9X8cY10X9 = new PathEdge(Y9X8 , Y10X9), Y10X9cY9X10 = new PathEdge(Y10X9 , Y9X10);
		//----
		PathEdge Y10X3cY11X3 = new PathEdge(Y10X3 , Y11X3), Y10X5cY11X5 = new PathEdge(Y10X5 , Y11X5);
		PathEdge Y10X7cY11X7 = new PathEdge(Y10X7 , Y11X7), Y10X9cY11X9 = new PathEdge(Y10X9 , Y11X9);
		//----
		PathEdge Y11X3cY12X4 = new PathEdge(Y11X3 , Y12X4), Y12X4cY11X5 = new PathEdge(Y12X4 , Y11X5);
		PathEdge Y11X5cY12X6 = new PathEdge(Y11X5 , Y12X6), Y12X6cY11X7 = new PathEdge(Y12X6 , Y11X7);
		PathEdge Y11X7cY12X8 = new PathEdge(Y11X7 , Y12X8), Y12X8cY11X9 = new PathEdge(Y12X8 , Y11X9);
		/** Now to add edges to their respective lists in jNodes**/
		Y1X4.addPath(Y2X3cY1X4);	Y1X4.addPath(Y1X4cY2X5);	
		Y1X6.addPath(Y2X5cY1X6);	Y1X6.addPath(Y1X6cY2X7);	
		Y1X8.addPath(Y2X7cY1X8);	Y1X8.addPath(Y1X8cY2X9);	

		Y2X3.addPath(Y2X3cY1X4);	Y2X3.addPath(Y2X3cY3X3);	
		Y2X5.addPath(Y1X4cY2X5);	Y2X5.addPath(Y2X5cY1X6);	Y2X5.addPath(Y2X5cY3X5);	
		Y2X7.addPath(Y1X6cY2X7);	Y2X7.addPath(Y2X7cY1X8);	Y2X7.addPath(Y2X7cY3X7);	
		Y2X9.addPath(Y1X8cY2X9);	Y2X9.addPath(Y2X9cY3X9);	

		Y3X3.addPath(Y2X3cY3X3);	Y3X3.addPath(Y4X2cY3X3);	Y3X3.addPath(Y3X3cY4X4);	
		Y3X5.addPath(Y2X5cY3X5);	Y3X5.addPath(Y4X4cY3X5);	Y3X5.addPath(Y3X5cY4X6);	
		Y3X7.addPath(Y2X7cY3X7);	Y3X7.addPath(Y4X6cY3X7);	Y3X7.addPath(Y3X7cY4X8);	
		Y3X9.addPath(Y2X9cY3X9);	Y3X9.addPath(Y4X8cY3X9);	Y3X9.addPath(Y3X9cY4X10);	

		Y4X2.addPath(Y4X2cY3X3);	Y4X2.addPath(Y4X2cY5X2);	
		Y4X4.addPath(Y3X3cY4X4);	Y4X4.addPath(Y4X4cY3X5);	Y4X4.addPath(Y4X4cY5X4);	
		Y4X6.addPath(Y3X5cY4X6);	Y4X6.addPath(Y4X6cY3X7);	Y4X6.addPath(Y4X6cY5X6);	
		Y4X8.addPath(Y3X7cY4X8);	Y4X8.addPath(Y4X8cY3X9);	Y4X8.addPath(Y4X8cY5X8);	
		Y4X10.addPath(Y3X9cY4X10);	Y4X10.addPath(Y4X10cY5X10);	

		Y5X2.addPath(Y4X2cY5X2);	Y5X2.addPath(Y6X1cY5X2);	Y5X2.addPath(Y5X2cY6X3);	
		Y5X4.addPath(Y4X4cY5X4);	Y5X4.addPath(Y6X3cY5X4);	Y5X4.addPath(Y5X4cY6X5);	
		Y5X6.addPath(Y4X6cY5X6);	Y5X6.addPath(Y6X5cY5X6);	Y5X6.addPath(Y5X6cY6X7);	
		Y5X8.addPath(Y4X8cY5X8);	Y5X8.addPath(Y6X7cY5X8);	Y5X8.addPath(Y5X8cY6X9);	
		Y5X10.addPath(Y4X10cY5X10);	Y5X10.addPath(Y6X9cY5X10);	Y5X10.addPath(Y5X10cY6X11);	

		Y6X1.addPath(Y6X1cY5X2);	Y6X1.addPath(Y6X1cY7X1);	
		Y6X3.addPath(Y5X2cY6X3);	Y6X3.addPath(Y6X3cY5X4);	Y6X3.addPath(Y6X3cY7X3);	
		Y6X5.addPath(Y5X4cY6X5);	Y6X5.addPath(Y6X5cY5X6);	Y6X5.addPath(Y6X5cY7X5);	
		Y6X7.addPath(Y5X6cY6X7);	Y6X7.addPath(Y6X7cY5X8);	Y6X7.addPath(Y6X7cY7X7);	
		Y6X9.addPath(Y5X8cY6X9);	Y6X9.addPath(Y6X9cY5X10);	Y6X9.addPath(Y6X9cY7X9);	
		Y6X11.addPath(Y5X10cY6X11);	Y6X11.addPath(Y6X11cY7X11);	

		Y7X1.addPath(Y6X1cY7X1);	Y7X1.addPath(Y7X1cY8X2);	
		Y7X3.addPath(Y6X3cY7X3);	Y7X3.addPath(Y8X2cY7X3);	Y7X3.addPath(Y7X3cY8X4);	
		Y7X5.addPath(Y6X5cY7X5);	Y7X5.addPath(Y8X4cY7X5);	Y7X5.addPath(Y7X5cY8X6);	
		Y7X7.addPath(Y6X7cY7X7);	Y7X7.addPath(Y8X6cY7X7);	Y7X7.addPath(Y7X7cY8X8);	
		Y7X9.addPath(Y6X9cY7X9);	Y7X9.addPath(Y8X8cY7X9);	Y7X9.addPath(Y7X9cY8X10);	
		Y7X11.addPath(Y6X11cY7X11);	Y7X11.addPath(Y8X10cY7X11);	

		Y8X2.addPath(Y7X1cY8X2);	Y8X2.addPath(Y8X2cY7X3);	Y8X2.addPath(Y8X2cY9X2);	
		Y8X4.addPath(Y7X3cY8X4);	Y8X4.addPath(Y8X4cY7X5);	Y8X4.addPath(Y8X4cY9X4);	
		Y8X6.addPath(Y7X5cY8X6);	Y8X6.addPath(Y8X6cY7X7);	Y8X6.addPath(Y8X6cY9X6);	
		Y8X8.addPath(Y7X7cY8X8);	Y8X8.addPath(Y8X8cY7X9);	Y8X8.addPath(Y8X8cY9X8);	
		Y8X10.addPath(Y7X9cY8X10);	Y8X10.addPath(Y8X10cY7X11);	Y8X10.addPath(Y8X10cY9X10);	

		Y9X2.addPath(Y8X2cY9X2);	Y9X2.addPath(Y9X2cY10X3);	
		Y9X4.addPath(Y8X4cY9X4);	Y9X4.addPath(Y10X3cY9X4);	Y9X4.addPath(Y9X4cY10X5);	
		Y9X6.addPath(Y8X6cY9X6);	Y9X6.addPath(Y10X5cY9X6);	Y9X6.addPath(Y9X6cY10X7);	
		Y9X8.addPath(Y8X8cY9X8);	Y9X8.addPath(Y10X7cY9X8);	Y9X8.addPath(Y9X8cY10X9);	
		Y9X10.addPath(Y8X10cY9X10);	Y9X10.addPath(Y10X9cY9X10);	

		Y10X3.addPath(Y9X2cY10X3);	Y10X3.addPath(Y10X3cY9X4);	Y10X3.addPath(Y10X3cY11X3);	
		Y10X5.addPath(Y9X4cY10X5);	Y10X5.addPath(Y10X5cY9X6);	Y10X5.addPath(Y10X5cY11X5);	
		Y10X7.addPath(Y9X6cY10X7);	Y10X7.addPath(Y10X7cY9X8);	Y10X7.addPath(Y10X7cY11X7);	
		Y10X9.addPath(Y9X8cY10X9);	Y10X9.addPath(Y10X9cY9X10);	Y10X9.addPath(Y10X9cY11X9);	

		Y11X3.addPath(Y10X3cY11X3);	Y11X3.addPath(Y11X3cY12X4);	
		Y11X5.addPath(Y10X5cY11X5);	Y11X5.addPath(Y12X4cY11X5);	Y11X5.addPath(Y11X5cY12X6);	
		Y11X7.addPath(Y10X7cY11X7);	Y11X7.addPath(Y12X6cY11X7);	Y11X7.addPath(Y11X7cY12X8);	
		Y11X9.addPath(Y10X9cY11X9);	Y11X9.addPath(Y12X8cY11X9);	

		Y12X4.addPath(Y11X3cY12X4);	Y12X4.addPath(Y12X4cY11X5);	
		Y12X6.addPath(Y11X5cY12X6);	Y12X6.addPath(Y12X6cY11X7);	
		Y12X8.addPath(Y11X7cY12X8);	Y12X8.addPath(Y12X8cY11X9);	
		/** Now to add the respective jNodes to the aNodes**/
		for( int idx = 0; idx < aNodeList.size(); idx++ ){
			char var = nAlignArr[idx];
			AttributeNode temp = getANode( var ); // Get's relative node
			switch ( var ){
			case( 'A' ): 	temp.add2List( Y1X4 );
			temp.add2List( Y2X3 );
			temp.add2List( Y2X5 );
			temp.add2List( Y3X3 );
			temp.add2List( Y3X5 );
			temp.add2List( Y4X4 );
			break;
			case( 'B' ): 	temp.add2List( Y1X6 );
			temp.add2List( Y2X5 );
			temp.add2List( Y2X7 );
			temp.add2List( Y3X5 );
			temp.add2List( Y3X7 );
			temp.add2List( Y4X6 );
			break;
			case( 'C' ): 	temp.add2List( Y1X8 );
			temp.add2List( Y2X7 );
			temp.add2List( Y2X9 );
			temp.add2List( Y3X7 );
			temp.add2List( Y3X9 );
			temp.add2List( Y4X8 );
			break;
			case( 'L' ): 	temp.add2List( Y3X3 );
			temp.add2List( Y4X2 );
			temp.add2List( Y4X4 );
			temp.add2List( Y5X2 );
			temp.add2List( Y5X4 );
			temp.add2List( Y6X3 );
			break;
			case( 'M' ): 	temp.add2List( Y3X5 );
			temp.add2List( Y4X4 );
			temp.add2List( Y4X6 );
			temp.add2List( Y5X4 );
			temp.add2List( Y5X6 );
			temp.add2List( Y6X5 );
			break;
			case( 'N' ): 	temp.add2List( Y3X7 );
			temp.add2List( Y4X6 );
			temp.add2List( Y4X8 );
			temp.add2List( Y5X6 );
			temp.add2List( Y5X8 );
			temp.add2List( Y6X7 );
			break;
			case( 'D' ): 	temp.add2List( Y3X9 );
			temp.add2List( Y4X8 );
			temp.add2List( Y4X10 );
			temp.add2List( Y5X8 );
			temp.add2List( Y5X10 );
			temp.add2List( Y6X9 );
			break;
			case( 'K' ): 	temp.add2List( Y5X2 );
			temp.add2List( Y6X1 );
			temp.add2List( Y6X3 );
			temp.add2List( Y7X1 );
			temp.add2List( Y7X3 );
			temp.add2List( Y8X2 );
			break;
			case( 'R' ): 	temp.add2List( Y5X4 );
			temp.add2List( Y6X3 );
			temp.add2List( Y6X5 );
			temp.add2List( Y7X3 );
			temp.add2List( Y7X5 );
			temp.add2List( Y8X4 );
			break;
			case( 'S' ): 	temp.add2List( Y5X6 );
			temp.add2List( Y6X5 );
			temp.add2List( Y6X7 );
			temp.add2List( Y7X5 );
			temp.add2List( Y7X7 );
			temp.add2List( Y8X6 );
			break;
			case( 'O' ): 	temp.add2List( Y5X8 );
			temp.add2List( Y6X7 );
			temp.add2List( Y6X9 );
			temp.add2List( Y7X7 );
			temp.add2List( Y7X9 );
			temp.add2List( Y8X8 );
			break;
			case( 'E' ): 	temp.add2List( Y5X10 );
			temp.add2List( Y6X9 );
			temp.add2List( Y6X11 );
			temp.add2List( Y7X9 );
			temp.add2List( Y7X11 );
			temp.add2List( Y8X10 );
			break;
			case( 'I' ): 	temp.add2List( Y9X4 );
			temp.add2List( Y10X3 );
			temp.add2List( Y10X5 );
			temp.add2List( Y11X3 );
			temp.add2List( Y11X5 );
			temp.add2List( Y12X4 );
			break;
			case( 'H' ): 	temp.add2List( Y9X6 );
			temp.add2List( Y10X5 );
			temp.add2List( Y10X7 );
			temp.add2List( Y11X5 );
			temp.add2List( Y11X7 );
			temp.add2List( Y12X6 );
			break;
			case( 'G' ): 	temp.add2List( Y9X8 );
			temp.add2List( Y10X7 );
			temp.add2List( Y10X9 );
			temp.add2List( Y11X7 );
			temp.add2List( Y11X9 );
			temp.add2List( Y12X8 );
			break;
			case( 'J' ): 	temp.add2List( Y7X3 );
			temp.add2List( Y8X2 );
			temp.add2List( Y8X4 );
			temp.add2List( Y9X2 );
			temp.add2List( Y9X4 );
			temp.add2List( Y10X3 );
			break;
			case( 'Q' ): 	temp.add2List( Y7X5 );
			temp.add2List( Y8X4 );
			temp.add2List( Y8X6 );
			temp.add2List( Y9X4 );
			temp.add2List( Y9X6 );
			temp.add2List( Y10X5 );
			break;
			case( 'P' ): 	temp.add2List( Y7X7 );
			temp.add2List( Y8X6 );
			temp.add2List( Y8X8 );
			temp.add2List( Y9X6 );
			temp.add2List( Y9X8 );
			temp.add2List( Y10X7 );
			break;
			case( 'F' ): 	temp.add2List( Y7X9 );
			temp.add2List( Y8X8 );
			temp.add2List( Y8X10 );
			temp.add2List( Y9X8 );
			temp.add2List( Y9X10 );
			temp.add2List( Y10X9 );
			break;
			default: break;			}
			/**Board IS NOW INITIALIZED. HOORAAY!**/
		}
	}
	/** -------- General Access Functions -------- **/
	
	/** Finds attribute with associated name
	 * @return the attributeNode with the name, null if not found **/
	public AttributeNode getANode( char toFind ){
		for(AttributeNode ret : aNodeList ) // Iterator through the list, only search once since each name is unique
			if( ret.getName() == toFind) 
				return ret;		
		return null;
	}
	
	/** Finds the aNodes with the similar trigger number
	 * @return iterable list of aNodes **/
	public Iterable<AttributeNode> getANodewTrig( int trig ){
		LinkedList<AttributeNode> ret = new LinkedList<AttributeNode>();
		
		for (AttributeNode x : aNodeList )
			if( trig == x.getTrigger() )
				ret.addLast( x );		
		return ret;
	}
	
	/** Will distribute resources to associated players by given roll number **/
	public void distribute_RescByTrigNum( int roll ){
		for (AttributeNode attr : getANodewTrig( roll ) )
			for( JunctionNode jNode : attr.jNodes() )
				if( jNode.hasCity() )
					getPlayer( jNode.getOwnerID() ).addRec( attr.getResourceNumber() , jNode.getCityType() );
	}
	
	/** Returns a the list of attributeNodes **/
	public Iterable<AttributeNode> tileList(){
		return aNodeList;
	}
	
	/** Gets the player with the matching ID **/
	public player getPlayer( int ID ){
		for( int idx = 0; idx < players.length; idx++ )
			if( players[idx].getID() == ID )
				return players[idx];
		return null;
	}

	/** -------- Construction Logic -------- **/
	
	/**@param Edge to build the road on
	 * @param player Number that wants to build the Road
	 * @return  1 if that player builds a road
	 * 			0 if not enough resources
	 * 		   -1 if there is already a road there **/
	public int buildRoad( PathEdge path , int playerNum ){
		// Cost : 1 wood 1 Brick
		if( path.hasRoad() )									// If the path already has a road there
			return -1;
		else if( players[playerNum].checkResource( 4 , 1 )
			  && players[playerNum].checkResource( 5 , 1 ) ){	// If the player has enough resources
			path.setOwner( players[playerNum].getID() );		// Sets the player ID as the owner of the path.
			
			players[playerNum].removeRec( 4 , 1 ); // Resource Cost
			players[playerNum].removeRec( 5 , 1 );
			/**TODO: Call longest Road check */
			return 1;
		}
		return 0;												// Else player lacks resources
	}
	
	/**@param playerNum to buy the devCard
	 * @return  1 if that player buys the card successfully
	 * 			0 player lacks resources
	 * 		   -1 Deck Empty	 */
	public int buyDevCard( int playerNum ){
		// Cost : 1 wheat 1 Ore 1 Wool		
		if( deck.isEmpty() )		// Empty Deck Case
			return -1;		
		if( players[playerNum].checkResource( 1 , 1 )
		 && players[playerNum].checkResource( 2 , 1 )
		 && players[playerNum].checkResource( 3 , 1 )){	// If the player has enough resources			
			players[playerNum].addDev( deck.draw() );	// Add devCard to player stack
			
			players[playerNum].removeRec( 1 , 1 );		// Cost
			players[playerNum].removeRec( 2 , 1 );
			players[playerNum].removeRec( 3 , 1 );	
			return 1;
		}
		return 0;					// Not enough Resources		
	}
	
	/**@param playerNum to build the settlement
	 * @return  1 if that player buys the settlement
	 * 			0 player lacks resources
	 * 		   -1 Already a settlement there	 */
	public int buildSettlement( JunctionNode place , int playerNum){
		// Cost : 1 wheat 1 wool 1 brick 1 wood
		if( place.hasCity() )									// If the node already has a settlement / city
			return -1;
		else if( players[playerNum].checkResource( 3 , 1 )
			  && players[playerNum].checkResource( 2 , 1 ) 
			  && players[playerNum].checkResource( 5 , 1 )
			  && players[playerNum].checkResource( 4 , 1 ) ){	// If the player has enough resources
			place.setOwner( players[playerNum].getID() );		// Sets the player ID as the owner of the Node
			players[playerNum].addSettlement(1);				// Adds a settlement to the player
			
			players[playerNum].removeRec( 3 , 1 );				// Cost
			players[playerNum].removeRec( 2 , 1 );
			players[playerNum].removeRec( 5 , 1 );
			players[playerNum].removeRec( 4 , 1 );
			return 1;
		}
		return 0;												// Else player lacks resources
	}
	
	/**@param playerNum to build the city
	 * @return  1 if that player buys the card successfully
	 * 			0 player lacks resources
	 * 		   -1 No settlement in place / you do not own the settlement */
	public int buildCity(JunctionNode place , int playerNum ){
		// Cost : 2 wheats 3 ores
		if( place.getCityType() != 1  ||
			players[playerNum].getID() != place.getOwnerID())
			return -1;
		
		if(players[playerNum].checkResource( 3 , 2 )
		&& players[playerNum].checkResource( 1 , 3 )){
			place.upgrade();
			players[playerNum].addCities(1); // add points
			
			players[playerNum].removeRec( 3 , 2 ); // Cost
			players[playerNum].removeRec( 1 , 3 );
			return 1;
		}
		return 0;
	}
	/** -------- Action Logic -------- **/
	
	/** Turn Declaration Roll : Determines turn order **/
	public void turnOrder(){
		
		
		
	}
	
	
	/**Action for dice roll of 7 - Thief Action
	 *  players with more than 7 cards remove half
	 *  rounds down
	 *  @return returns an array matching the size of players.
	 *  		The element will match the number of cards that player
	 *  		has to remove **/	
	public int[] robberAction(){
		int[] info = new int[players.length];
		
		for( int idx = 0; idx < players.length; idx ++)
			info[idx] = (players[idx].getTotalResources() > 7) ? players[idx].getTotalResources() / 2 : 0;		
		return info;		
	}

	/** Action for moving the thief
	 * @param Place to move the thief to
	 * @return  1 if move was successful
	 * 		   -1 if newPlace is invalid **/
	public int moveThief( AttributeNode newPlace ){
		if( thiefLocation.equals( newPlace ) )
			return -1;
		thiefLocation.setThief( false );
		newPlace.setThief( true );	
		return 1;		
	}
	
	
	
	
}
=======
package catanNodes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import player.player;
import devCards.CardArray;

/** The class that will construct the Catan map
 *  Will contain some rules functions
 *  @author Wes
 *  @author Xin Zhang */

public class CatanBoard_vServer{
	
	private LinkedList<AttributeNode> aNodeList; // List for all tiles
	private CardArray deck;
	private player[] players;
	private Random rand = new Random();
	
	private int[] award_LongestRoad = { 0 , 4 }; // {Player ID that holds the award , size of longest road }
	private int[] award_LargestArmy = { 0 , 2 }; // {Player ID that holds the award , size of largest army }
	private AttributeNode thiefTile;			 // Current location of the thief
	private int turnNum = 0;
	
	/** Constructor for standard catanBoard will set up the game 
	 * @param Number of players
	 * @param This will be the pre-game Lobby's record of the players.**/
	public CatanBoard_vServer(ArrayList<player> playersFromLobby){
		aNodeList = new LinkedList<AttributeNode>();
		deck = new CardArray();	
		
		/** Reset data and initialize turn order **/
		for(player person : playersFromLobby ){
			person.newGame();
			person.pInfo().setPlayerNum( diceRoll() );
		}
		
		/** Sort by roll number, repeats = reroll **/
		// TODO: Sort
		players = new player[playersFromLobby.size()];
		playersFromLobby.toArray( players ); // Store the array by size of players.		
		ResourceDistro tileStack = new ResourceDistro();		
		tileStack.shuffleStack(35); // Resource Stack setter , number can be a variable later
		// No actual use except for debug ( name Alignment array )
		int index = 0; // counter for nAlign and trigNum
		int tindex = 0;
		// Hard code trigger number order
		char nAlignArr[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S'}; 
		int trigNum[]    = { 5 , 4 , 2 , 6 , 8 , 10, 9 , 12, 11, 4 , 8 , 10, 9 , 4 , 5 , 6 , 3 , 11}; 
		
		/** Hardcode for tile 'placement' and setup **/
		while( !tileStack.isEmpty() ){
			int rVal = tileStack.popStack(); 	// Popped value for resource value
			if( rVal == 0 ){ // Desert case
				AttributeNode toAdd = new AttributeNode(nAlignArr[index] , rVal , 7 );	// Creates the attribute node
				toAdd.setTheif( true );				// Special settings
				thiefTile = toAdd;					// Update current location of thief
				aNodeList.addLast(toAdd);			// Adds to list
				index ++;							// Increments				
			} else {
				AttributeNode toAdd = new AttributeNode(nAlignArr[index] , rVal , trigNum[tindex] );	// Creates the attribute node
				aNodeList.addLast(toAdd);			// Adds to list
				index ++;							// Increments
				tindex++;
			} 			
		}
		/**-----Now to add jNodes to the attribute nodes-----**/
		JunctionNode Y1X4 = new JunctionNode(1,4), Y1X6 = new JunctionNode(1,6),Y1X8 = new JunctionNode(1,8);
		//----
		JunctionNode Y2X3 = new JunctionNode(2,3), Y2X5 = new JunctionNode(2,5);
		JunctionNode Y2X7 = new JunctionNode(2,7), Y2X9 = new JunctionNode(2,9);
		//----
		JunctionNode Y3X3 = new JunctionNode(3,3), Y3X5 = new JunctionNode(3,5);
		JunctionNode Y3X7 = new JunctionNode(3,7), Y3X9 = new JunctionNode(3,9);	
		//----
		JunctionNode Y4X2 = new JunctionNode(4,2),Y4X4 = new JunctionNode(4,4);
		JunctionNode Y4X6 = new JunctionNode(4,6), Y4X8 = new JunctionNode(4,8);
		JunctionNode Y4X10 = new JunctionNode(4,10);	
		//----
		JunctionNode Y5X2 = new JunctionNode(5,2), Y5X4 = new JunctionNode(5,4);	
		JunctionNode Y5X6 = new JunctionNode(5,6), Y5X8 = new JunctionNode(5,8);
		JunctionNode Y5X10 = new JunctionNode(5,10);	
		//----
		JunctionNode Y6X1 = new JunctionNode(6,1), Y6X3 = new JunctionNode(6,3);
		JunctionNode Y6X5 = new JunctionNode(6,5), Y6X7 = new JunctionNode(6,7);
		JunctionNode Y6X9 = new JunctionNode(6,9), Y6X11 = new JunctionNode(6,11);
		//----
		JunctionNode Y7X1 = new JunctionNode(7,1), Y7X3 = new JunctionNode(7,3);	
		JunctionNode Y7X5 = new JunctionNode(7,5), Y7X7 = new JunctionNode(7,7);
		JunctionNode Y7X9 = new JunctionNode(7,9), Y7X11 = new JunctionNode(7,11);
		//----
		JunctionNode Y8X2 = new JunctionNode(8,2), Y8X4 = new JunctionNode(8,4);
		JunctionNode Y8X6 = new JunctionNode(8,6), Y8X8 = new JunctionNode(8,8);
		JunctionNode Y8X10 = new JunctionNode(8,10);	
		//----
		JunctionNode Y9X2 = new JunctionNode(9,2), Y9X4 = new JunctionNode(9,4);	
		JunctionNode Y9X6 = new JunctionNode(9,6), Y9X8 = new JunctionNode(9,8);	
		JunctionNode Y9X10 = new JunctionNode(9,10);	
		//----
		JunctionNode Y10X3 = new JunctionNode(10,3), Y10X5 = new JunctionNode(10,5);
		JunctionNode Y10X7 = new JunctionNode(10,7), Y10X9 = new JunctionNode(10,9);
		//----
		JunctionNode Y11X3 = new JunctionNode(11,3), Y11X5 = new JunctionNode(11,5);
		JunctionNode Y11X7 = new JunctionNode(11,7), Y11X9 = new JunctionNode(11,9);
		//----
		JunctionNode Y12X4 = new JunctionNode(12,4), Y12X6 = new JunctionNode(12,6);
		JunctionNode Y12X8 = new JunctionNode(12,8);	
		/** Now create appropriate edgePath to add to the jNode **/
		PathEdge Y2X3cY1X4 = new PathEdge(Y2X3 , Y1X4), Y1X4cY2X5 = new PathEdge(Y1X4 , Y2X5);
		PathEdge Y2X5cY1X6 = new PathEdge(Y2X5 , Y1X6), Y1X6cY2X7 = new PathEdge(Y1X6 , Y2X7);
		PathEdge Y2X7cY1X8 = new PathEdge(Y2X7 , Y1X8), Y1X8cY2X9 = new PathEdge(Y1X8 , Y2X9); 
		//----
		PathEdge Y2X3cY3X3 = new PathEdge(Y2X3 , Y3X3), Y2X5cY3X5 = new PathEdge(Y2X5 , Y3X5);
		PathEdge Y2X7cY3X7 = new PathEdge(Y2X7 , Y3X7), Y2X9cY3X9 = new PathEdge(Y2X9 , Y3X9); 
		//----
		PathEdge Y4X2cY3X3 = new PathEdge(Y4X2 , Y3X3), Y3X3cY4X4 = new PathEdge(Y3X3 , Y4X4);
		PathEdge Y4X4cY3X5 = new PathEdge(Y4X4 , Y3X5), Y3X5cY4X6 = new PathEdge(Y3X5 , Y4X6);
		PathEdge Y4X6cY3X7 = new PathEdge(Y4X6 , Y3X7), Y3X7cY4X8 = new PathEdge(Y3X7 , Y4X8); 
		PathEdge Y4X8cY3X9 = new PathEdge(Y4X8 , Y3X9), Y3X9cY4X10 = new PathEdge(Y3X9 , Y4X10); 
		//----
		PathEdge Y4X2cY5X2 = new PathEdge(Y4X2 , Y5X2), Y4X4cY5X4 = new PathEdge(Y4X4 , Y5X4);
		PathEdge Y4X6cY5X6 = new PathEdge(Y4X6 , Y5X6), Y4X8cY5X8 = new PathEdge(Y4X8 , Y5X8); 
		PathEdge Y4X10cY5X10 = new PathEdge(Y4X10 , Y5X10); 	
		//----
		PathEdge Y6X1cY5X2 = new PathEdge(Y6X1 , Y5X2), Y5X2cY6X3 = new PathEdge(Y5X2 , Y6X3);
		PathEdge Y6X3cY5X4 = new PathEdge(Y6X3 , Y5X4), Y5X4cY6X5 = new PathEdge(Y5X4 , Y6X5); 
		PathEdge Y6X5cY5X6 = new PathEdge(Y6X5 , Y5X6), Y5X6cY6X7 = new PathEdge(Y5X6 , Y6X7); 
		PathEdge Y6X7cY5X8 = new PathEdge(Y6X7 , Y5X8), Y5X8cY6X9 = new PathEdge(Y5X8 , Y6X9);
		PathEdge Y6X9cY5X10 = new PathEdge(Y6X9 , Y5X10), Y5X10cY6X11 = new PathEdge(Y5X10 , Y6X11);
		//----
		PathEdge Y6X1cY7X1 = new PathEdge(Y6X1 , Y7X1), Y6X3cY7X3 = new PathEdge(Y6X3 , Y7X3);
		PathEdge Y6X5cY7X5 = new PathEdge(Y6X5 , Y7X5), Y6X7cY7X7 = new PathEdge(Y6X7 , Y7X7);
		PathEdge Y6X9cY7X9 = new PathEdge(Y6X9 , Y7X9), Y6X11cY7X11 = new PathEdge(Y6X11 , Y7X11);
		//----
		PathEdge Y7X1cY8X2 = new PathEdge(Y7X1 , Y8X2), Y8X2cY7X3 = new PathEdge(Y8X2 , Y7X3);
		PathEdge Y7X3cY8X4 = new PathEdge(Y7X3 , Y8X4), Y8X4cY7X5 = new PathEdge(Y8X4 , Y7X5);
		PathEdge Y7X5cY8X6 = new PathEdge(Y7X5 , Y8X6), Y8X6cY7X7 = new PathEdge(Y8X6 , Y7X7);
		PathEdge Y7X7cY8X8 = new PathEdge(Y7X7 , Y8X8), Y8X8cY7X9 = new PathEdge(Y8X8 , Y7X9);
		PathEdge Y7X9cY8X10 = new PathEdge(Y7X9 , Y8X10), Y8X10cY7X11 = new PathEdge(Y8X10 , Y7X11);
		//----
		PathEdge Y8X2cY9X2 = new PathEdge(Y8X2 , Y9X2); 		PathEdge Y8X4cY9X4 = new PathEdge(Y8X4 , Y9X4);
		PathEdge Y8X6cY9X6 = new PathEdge(Y8X6 , Y9X6); 		PathEdge Y8X8cY9X8 = new PathEdge(Y8X8 , Y9X8);
		PathEdge Y8X10cY9X10 = new PathEdge(Y8X10 , Y9X10);
		//----
		PathEdge Y9X2cY10X3 = new PathEdge(Y9X2 , Y10X3), Y10X3cY9X4 = new PathEdge(Y10X3 , Y9X4);
		PathEdge Y9X4cY10X5 = new PathEdge(Y9X4 , Y10X5), Y10X5cY9X6 = new PathEdge(Y10X5 , Y9X6);
		PathEdge Y9X6cY10X7 = new PathEdge(Y9X6 , Y10X7), Y10X7cY9X8 = new PathEdge(Y10X7 , Y9X8);
		PathEdge Y9X8cY10X9 = new PathEdge(Y9X8 , Y10X9), Y10X9cY9X10 = new PathEdge(Y10X9 , Y9X10);
		//----
		PathEdge Y10X3cY11X3 = new PathEdge(Y10X3 , Y11X3), Y10X5cY11X5 = new PathEdge(Y10X5 , Y11X5);
		PathEdge Y10X7cY11X7 = new PathEdge(Y10X7 , Y11X7), Y10X9cY11X9 = new PathEdge(Y10X9 , Y11X9);
		//----
		PathEdge Y11X3cY12X4 = new PathEdge(Y11X3 , Y12X4), Y12X4cY11X5 = new PathEdge(Y12X4 , Y11X5);
		PathEdge Y11X5cY12X6 = new PathEdge(Y11X5 , Y12X6), Y12X6cY11X7 = new PathEdge(Y12X6 , Y11X7);
		PathEdge Y11X7cY12X8 = new PathEdge(Y11X7 , Y12X8), Y12X8cY11X9 = new PathEdge(Y12X8 , Y11X9);
		/** Now to add edges to their respective lists in jNodes**/
		Y1X4.addPath(Y2X3cY1X4);	Y1X4.addPath(Y1X4cY2X5);	
		Y1X6.addPath(Y2X5cY1X6);	Y1X6.addPath(Y1X6cY2X7);	
		Y1X8.addPath(Y2X7cY1X8);	Y1X8.addPath(Y1X8cY2X9);	

		Y2X3.addPath(Y2X3cY1X4);	Y2X3.addPath(Y2X3cY3X3);	
		Y2X5.addPath(Y1X4cY2X5);	Y2X5.addPath(Y2X5cY1X6);	Y2X5.addPath(Y2X5cY3X5);	
		Y2X7.addPath(Y1X6cY2X7);	Y2X7.addPath(Y2X7cY1X8);	Y2X7.addPath(Y2X7cY3X7);	
		Y2X9.addPath(Y1X8cY2X9);	Y2X9.addPath(Y2X9cY3X9);	

		Y3X3.addPath(Y2X3cY3X3);	Y3X3.addPath(Y4X2cY3X3);	Y3X3.addPath(Y3X3cY4X4);	
		Y3X5.addPath(Y2X5cY3X5);	Y3X5.addPath(Y4X4cY3X5);	Y3X5.addPath(Y3X5cY4X6);	
		Y3X7.addPath(Y2X7cY3X7);	Y3X7.addPath(Y4X6cY3X7);	Y3X7.addPath(Y3X7cY4X8);	
		Y3X9.addPath(Y2X9cY3X9);	Y3X9.addPath(Y4X8cY3X9);	Y3X9.addPath(Y3X9cY4X10);	

		Y4X2.addPath(Y4X2cY3X3);	Y4X2.addPath(Y4X2cY5X2);	
		Y4X4.addPath(Y3X3cY4X4);	Y4X4.addPath(Y4X4cY3X5);	Y4X4.addPath(Y4X4cY5X4);	
		Y4X6.addPath(Y3X5cY4X6);	Y4X6.addPath(Y4X6cY3X7);	Y4X6.addPath(Y4X6cY5X6);	
		Y4X8.addPath(Y3X7cY4X8);	Y4X8.addPath(Y4X8cY3X9);	Y4X8.addPath(Y4X8cY5X8);	
		Y4X10.addPath(Y3X9cY4X10);	Y4X10.addPath(Y4X10cY5X10);	

		Y5X2.addPath(Y4X2cY5X2);	Y5X2.addPath(Y6X1cY5X2);	Y5X2.addPath(Y5X2cY6X3);	
		Y5X4.addPath(Y4X4cY5X4);	Y5X4.addPath(Y6X3cY5X4);	Y5X4.addPath(Y5X4cY6X5);	
		Y5X6.addPath(Y4X6cY5X6);	Y5X6.addPath(Y6X5cY5X6);	Y5X6.addPath(Y5X6cY6X7);	
		Y5X8.addPath(Y4X8cY5X8);	Y5X8.addPath(Y6X7cY5X8);	Y5X8.addPath(Y5X8cY6X9);	
		Y5X10.addPath(Y4X10cY5X10);	Y5X10.addPath(Y6X9cY5X10);	Y5X10.addPath(Y5X10cY6X11);	

		Y6X1.addPath(Y6X1cY5X2);	Y6X1.addPath(Y6X1cY7X1);	
		Y6X3.addPath(Y5X2cY6X3);	Y6X3.addPath(Y6X3cY5X4);	Y6X3.addPath(Y6X3cY7X3);	
		Y6X5.addPath(Y5X4cY6X5);	Y6X5.addPath(Y6X5cY5X6);	Y6X5.addPath(Y6X5cY7X5);	
		Y6X7.addPath(Y5X6cY6X7);	Y6X7.addPath(Y6X7cY5X8);	Y6X7.addPath(Y6X7cY7X7);	
		Y6X9.addPath(Y5X8cY6X9);	Y6X9.addPath(Y6X9cY5X10);	Y6X9.addPath(Y6X9cY7X9);	
		Y6X11.addPath(Y5X10cY6X11);	Y6X11.addPath(Y6X11cY7X11);	

		Y7X1.addPath(Y6X1cY7X1);	Y7X1.addPath(Y7X1cY8X2);	
		Y7X3.addPath(Y6X3cY7X3);	Y7X3.addPath(Y8X2cY7X3);	Y7X3.addPath(Y7X3cY8X4);	
		Y7X5.addPath(Y6X5cY7X5);	Y7X5.addPath(Y8X4cY7X5);	Y7X5.addPath(Y7X5cY8X6);	
		Y7X7.addPath(Y6X7cY7X7);	Y7X7.addPath(Y8X6cY7X7);	Y7X7.addPath(Y7X7cY8X8);	
		Y7X9.addPath(Y6X9cY7X9);	Y7X9.addPath(Y8X8cY7X9);	Y7X9.addPath(Y7X9cY8X10);	
		Y7X11.addPath(Y6X11cY7X11);	Y7X11.addPath(Y8X10cY7X11);	

		Y8X2.addPath(Y7X1cY8X2);	Y8X2.addPath(Y8X2cY7X3);	Y8X2.addPath(Y8X2cY9X2);	
		Y8X4.addPath(Y7X3cY8X4);	Y8X4.addPath(Y8X4cY7X5);	Y8X4.addPath(Y8X4cY9X4);	
		Y8X6.addPath(Y7X5cY8X6);	Y8X6.addPath(Y8X6cY7X7);	Y8X6.addPath(Y8X6cY9X6);	
		Y8X8.addPath(Y7X7cY8X8);	Y8X8.addPath(Y8X8cY7X9);	Y8X8.addPath(Y8X8cY9X8);	
		Y8X10.addPath(Y7X9cY8X10);	Y8X10.addPath(Y8X10cY7X11);	Y8X10.addPath(Y8X10cY9X10);	

		Y9X2.addPath(Y8X2cY9X2);	Y9X2.addPath(Y9X2cY10X3);	
		Y9X4.addPath(Y8X4cY9X4);	Y9X4.addPath(Y10X3cY9X4);	Y9X4.addPath(Y9X4cY10X5);	
		Y9X6.addPath(Y8X6cY9X6);	Y9X6.addPath(Y10X5cY9X6);	Y9X6.addPath(Y9X6cY10X7);	
		Y9X8.addPath(Y8X8cY9X8);	Y9X8.addPath(Y10X7cY9X8);	Y9X8.addPath(Y9X8cY10X9);	
		Y9X10.addPath(Y8X10cY9X10);	Y9X10.addPath(Y10X9cY9X10);	

		Y10X3.addPath(Y9X2cY10X3);	Y10X3.addPath(Y10X3cY9X4);	Y10X3.addPath(Y10X3cY11X3);	
		Y10X5.addPath(Y9X4cY10X5);	Y10X5.addPath(Y10X5cY9X6);	Y10X5.addPath(Y10X5cY11X5);	
		Y10X7.addPath(Y9X6cY10X7);	Y10X7.addPath(Y10X7cY9X8);	Y10X7.addPath(Y10X7cY11X7);	
		Y10X9.addPath(Y9X8cY10X9);	Y10X9.addPath(Y10X9cY9X10);	Y10X9.addPath(Y10X9cY11X9);	

		Y11X3.addPath(Y10X3cY11X3);	Y11X3.addPath(Y11X3cY12X4);	
		Y11X5.addPath(Y10X5cY11X5);	Y11X5.addPath(Y12X4cY11X5);	Y11X5.addPath(Y11X5cY12X6);	
		Y11X7.addPath(Y10X7cY11X7);	Y11X7.addPath(Y12X6cY11X7);	Y11X7.addPath(Y11X7cY12X8);	
		Y11X9.addPath(Y10X9cY11X9);	Y11X9.addPath(Y12X8cY11X9);	

		Y12X4.addPath(Y11X3cY12X4);	Y12X4.addPath(Y12X4cY11X5);	
		Y12X6.addPath(Y11X5cY12X6);	Y12X6.addPath(Y12X6cY11X7);	
		Y12X8.addPath(Y11X7cY12X8);	Y12X8.addPath(Y12X8cY11X9);	
		/** Now to add the respective jNodes to the aNodes**/
		for( int idx = 0; idx < aNodeList.size(); idx++ ){
			char var = nAlignArr[idx];
			AttributeNode temp = getANode( var ); // Get's relative node
			switch ( var ){
			case( 'A' ): 	temp.add2List( Y1X4 );
			temp.add2List( Y2X3 );
			temp.add2List( Y2X5 );
			temp.add2List( Y3X3 );
			temp.add2List( Y3X5 );
			temp.add2List( Y4X4 );
			break;
			case( 'B' ): 	temp.add2List( Y1X6 );
			temp.add2List( Y2X5 );
			temp.add2List( Y2X7 );
			temp.add2List( Y3X5 );
			temp.add2List( Y3X7 );
			temp.add2List( Y4X6 );
			break;
			case( 'C' ): 	temp.add2List( Y1X8 );
			temp.add2List( Y2X7 );
			temp.add2List( Y2X9 );
			temp.add2List( Y3X7 );
			temp.add2List( Y3X9 );
			temp.add2List( Y4X8 );
			break;
			case( 'L' ): 	temp.add2List( Y3X3 );
			temp.add2List( Y4X2 );
			temp.add2List( Y4X4 );
			temp.add2List( Y5X2 );
			temp.add2List( Y5X4 );
			temp.add2List( Y6X3 );
			break;
			case( 'M' ): 	temp.add2List( Y3X5 );
			temp.add2List( Y4X4 );
			temp.add2List( Y4X6 );
			temp.add2List( Y5X4 );
			temp.add2List( Y5X6 );
			temp.add2List( Y6X5 );
			break;
			case( 'N' ): 	temp.add2List( Y3X7 );
			temp.add2List( Y4X6 );
			temp.add2List( Y4X8 );
			temp.add2List( Y5X6 );
			temp.add2List( Y5X8 );
			temp.add2List( Y6X7 );
			break;
			case( 'D' ): 	temp.add2List( Y3X9 );
			temp.add2List( Y4X8 );
			temp.add2List( Y4X10 );
			temp.add2List( Y5X8 );
			temp.add2List( Y5X10 );
			temp.add2List( Y6X9 );
			break;
			case( 'K' ): 	temp.add2List( Y5X2 );
			temp.add2List( Y6X1 );
			temp.add2List( Y6X3 );
			temp.add2List( Y7X1 );
			temp.add2List( Y7X3 );
			temp.add2List( Y8X2 );
			break;
			case( 'R' ): 	temp.add2List( Y5X4 );
			temp.add2List( Y6X3 );
			temp.add2List( Y6X5 );
			temp.add2List( Y7X3 );
			temp.add2List( Y7X5 );
			temp.add2List( Y8X4 );
			break;
			case( 'S' ): 	temp.add2List( Y5X6 );
			temp.add2List( Y6X5 );
			temp.add2List( Y6X7 );
			temp.add2List( Y7X5 );
			temp.add2List( Y7X7 );
			temp.add2List( Y8X6 );
			break;
			case( 'O' ): 	temp.add2List( Y5X8 );
			temp.add2List( Y6X7 );
			temp.add2List( Y6X9 );
			temp.add2List( Y7X7 );
			temp.add2List( Y7X9 );
			temp.add2List( Y8X8 );
			break;
			case( 'E' ): 	temp.add2List( Y5X10 );
			temp.add2List( Y6X9 );
			temp.add2List( Y6X11 );
			temp.add2List( Y7X9 );
			temp.add2List( Y7X11 );
			temp.add2List( Y8X10 );
			break;
			case( 'I' ): 	temp.add2List( Y9X4 );
			temp.add2List( Y10X3 );
			temp.add2List( Y10X5 );
			temp.add2List( Y11X3 );
			temp.add2List( Y11X5 );
			temp.add2List( Y12X4 );
			break;
			case( 'H' ): 	temp.add2List( Y9X6 );
			temp.add2List( Y10X5 );
			temp.add2List( Y10X7 );
			temp.add2List( Y11X5 );
			temp.add2List( Y11X7 );
			temp.add2List( Y12X6 );
			break;
			case( 'G' ): 	temp.add2List( Y9X8 );
			temp.add2List( Y10X7 );
			temp.add2List( Y10X9 );
			temp.add2List( Y11X7 );
			temp.add2List( Y11X9 );
			temp.add2List( Y12X8 );
			break;
			case( 'J' ): 	temp.add2List( Y7X3 );
			temp.add2List( Y8X2 );
			temp.add2List( Y8X4 );
			temp.add2List( Y9X2 );
			temp.add2List( Y9X4 );
			temp.add2List( Y10X3 );
			break;
			case( 'Q' ): 	temp.add2List( Y7X5 );
			temp.add2List( Y8X4 );
			temp.add2List( Y8X6 );
			temp.add2List( Y9X4 );
			temp.add2List( Y9X6 );
			temp.add2List( Y10X5 );
			break;
			case( 'P' ): 	temp.add2List( Y7X7 );
			temp.add2List( Y8X6 );
			temp.add2List( Y8X8 );
			temp.add2List( Y9X6 );
			temp.add2List( Y9X8 );
			temp.add2List( Y10X7 );
			break;
			case( 'F' ): 	temp.add2List( Y7X9 );
			temp.add2List( Y8X8 );
			temp.add2List( Y8X10 );
			temp.add2List( Y9X8 );
			temp.add2List( Y9X10 );
			temp.add2List( Y10X9 );
			break;
			default: break;			}
			/**Board IS NOW INITIALIZED. HOORAAY!**/
		}
	}
	/** -------- General Functions -------- **/
	
	/**Random Dice Roll System
	 * @returns sum of two dice**/
	public int diceRoll(){		
		rand.setSeed(System.nanoTime()); //seed the time with the clock
		int number = rand.nextInt(11)+2;
		return number;
	}
	
	/** Finds attribute with associated name
	 * @return the attributeNode with the name, null if not found **/
	public AttributeNode getANode( char toFind ){
		for(AttributeNode ret : aNodeList ) // Iterator through the list, only search once since each name is unique
			if( ret.getName() == toFind) 
				return ret;		
		return null;
	}
	
	/** Finds the aNodes with the similar trigger number
	 * @return iterable list of aNodes **/
	public Iterable<AttributeNode> getANodewTrig( int trig ){
		LinkedList<AttributeNode> ret = new LinkedList<AttributeNode>();
		
		for (AttributeNode x : aNodeList )
			if( trig == x.getTrigger() )
				ret.addLast( x );		
		return ret;
	}
	
	/** Will distribute resources to associated players by given roll number **/
	public void distribute_RescByTrigNum( int roll ){
		if( roll == 7 )
			event_Thief();
		
		for (AttributeNode tile : getANodewTrig( roll ) )
			for( JunctionNode jNode : tile.jNodes() )
				if( jNode.hasCity() )
					players[jNode.getOwnerID()].pInfo().addRec( tile.getResourceNumber() , jNode.getCityType() );
	}
	
	/** Returns a the list of attributeNodes **/
	public Iterable<AttributeNode> tileList(){
		return aNodeList;
	}

	/** -------- Construction Logic -------- **/
	
	/**@param Edge to build the road on
	 * @param player Number that wants to build the Road
	 * @return  1 if that player builds a road
	 * 			0 if not enough resources
	 * 		   -1 if there is already a road there **/
	public int buildRoad( PathEdge path , int playerNum ){
		// Cost : 1 wood 1 Brick
		if( path.hasRoad() )									// If the path already has a road there
			return -1;
		else if( players[playerNum].pInfo().checkResource( 4 , 1 )
			  && players[playerNum].pInfo().checkResource( 5 , 1 ) ){	// If the player has enough resources
			path.setOwner( playerNum );		// Sets the player ID as the owner of the path.
			
			players[playerNum].pInfo().removeRec( 4 , 1 ); // Resource Cost
			players[playerNum].pInfo().removeRec( 5 , 1 );
			/**TODO: Call longest Road check */
			return 1;
		}
		return 0;												// Else player lacks resources
	}
	
	/**@param playerNum to buy the devCard
	 * @return  1 if that player buys the card successfully
	 * 			0 player lacks resources
	 * 		   -1 Deck Empty	 */
	public int buyDevCard( int playerNum ){
		// Cost : 1 wheat 1 Ore 1 Wool		
		if( deck.isEmpty() )		// Empty Deck Case
			return -1;		
		if( players[playerNum].pInfo().checkResource( 1 , 1 )
		 && players[playerNum].pInfo().checkResource( 2 , 1 )
		 && players[playerNum].pInfo().checkResource( 3 , 1 )){	// If the player has enough resources			
			players[playerNum].pInfo().addDev( deck.draw() );	// Add devCard to player stack
			
			players[playerNum].pInfo().removeRec( 1 , 1 );		// Cost
			players[playerNum].pInfo().removeRec( 2 , 1 );
			players[playerNum].pInfo().removeRec( 3 , 1 );	
			return 1;
		}
		return 0;					// Not enough Resources		
	}
	
	/**@param playerNum to build the settlement
	 * @return  1 if that player buys the settlement
	 * 			0 player lacks resources
	 * 		   -1 Already a settlement there	 */
	public int buildSettlement( JunctionNode place , int playerNum){
		// Cost : 1 wheat 1 wool 1 brick 1 wood
		if( place.hasCity() )									// If the node already has a settlement / city
			return -1;
		else if( players[playerNum].pInfo().checkResource( 3 , 1 )
			  && players[playerNum].pInfo().checkResource( 2 , 1 ) 
			  && players[playerNum].pInfo().checkResource( 5 , 1 )
			  && players[playerNum].pInfo().checkResource( 4 , 1 ) ){	// If the player has enough resources
			place.setOwner( playerNum );		// Sets the player Num as the owner of the Node
			players[playerNum].pInfo().addSettlement(1);				// Adds a settlement to the player
			
			players[playerNum].pInfo().removeRec( 3 , 1 );				// Cost
			players[playerNum].pInfo().removeRec( 2 , 1 );
			players[playerNum].pInfo().removeRec( 5 , 1 );
			players[playerNum].pInfo().removeRec( 4 , 1 );
			return 1;
		}
		return 0;												// Else player lacks resources
	}
	
	/**@param playerNum to build the city
	 * @return  1 if that player buys the card successfully
	 * 			0 player lacks resources
	 * 		   -1 No settlement in place / you do not own the settlement */
	public int buildCity(JunctionNode place , int playerNum ){
		// Cost : 2 wheats 3 ores
		if( place.getCityType() != 1  ||
			playerNum != place.getOwnerID())
			return -1;
		
		if(players[playerNum].pInfo().checkResource( 3 , 2 )
		&& players[playerNum].pInfo().checkResource( 1 , 3 )){
			place.upgrade();
			players[playerNum].pInfo().addCities(1); // add points
			
			players[playerNum].pInfo().removeRec( 3 , 2 ); // Cost
			players[playerNum].pInfo().removeRec( 1 , 3 );
			return 1;
		}
		return 0;
	}
	
	/** -------- Action Logic -------- **/
	
	/** Standard Turn command **/
	public void playerTurn( int playerNum ){
		
		distribute_RescByTrigNum( diceRoll() ); // Roll Dice
		
		/** Trade and Build turn **/
	}
	
	/** Is called if dice roll is 7 **/
	public void event_Thief(){
		
		
	}
	
	/** Moves thief
	 * @param tile to Move thief to
	 * @return 0 if invalid move. 1 if valid **/
	public int moveThief( AttributeNode tile ){
		if( thiefTile.equals( tile ) )  // If the player tries to "not move the thief"
			return 0;
		
		thiefTile.setTheif( false );	// Updates values.
		tile.setTheif( true );
		thiefTile = tile;
		return 1;
	}
	
}
>>>>>>> 1.4
