package catanNodes;
import java.util.Random;

/** Distribution system for resource tiles
 * @author Xin Zhang */
public class ResourceDistro {
	/** 19 total resource tiles in standard edition
	 * 4 wheat	 * 4 woods
	 * 4 wool	 * 3 brick
	 * 3 ore	 * 1 desert*/
	
	/** Sets tile with resource type
	 *  0 = desert	 		*  1 = ore
	 *  2 = Sheep / wool	*  3 = wheat / grain
	 *  4 = wood	    	*  5 = brick / clay  */
	private int popCount = 19;
	private int rStack[] = {0,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4,5,5,5};
	
	/** Shuffles the stack x times **/
	public void shuffleStack( int xtime){
		Random rand = new Random();
		rand.setSeed( System.nanoTime() );
		for( int x = 0; x < xtime; x++ ){
			arrSwap(Math.abs( rand.nextInt()%19 ) , Math.abs (rand.nextInt()%19) );
		}
	}
	
	private void arrSwap( int first , int second ){
		int temp = rStack[first];
		rStack[first] = rStack[second];
		rStack[second] = temp;
	}
	
	/** Pops a number off the resource stack
	 * returns -1 if there is it empty **/
	public int popStack(){
		if( popCount <= 0)
			return -1;

		return rStack[19 - popCount--];
	}
	
	public boolean isEmpty(){
		return ( popCount == 0 );
	}
}
