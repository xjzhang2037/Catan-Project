package Timers;
import catanNodes.CatanBoard_vServer;

/** Timer class that is threadable
 * @author Xin Zhang **/

public class timer_ThiefEvent_Resource implements Runnable {

	long stop_Time;
	CatanBoard_vServer ref;
	
	/** Init method **/	
	@Override
	public void run() {
		
	}

	/** Set timeout delay and starts timer
	 * @param reference to the Board
	 * @param int (seconds) **/
	public void setTimer( CatanBoard_vServer in , int sec ){
		ref = in;									        // Set up the reference
		stop_Time = System.nanoTime() + (sec * 1000000000); // Convert to nanoseconds and add current time
		timer();
	}
	
	/** Timer will call **/
	public void timer(){
		long cur_Time = System.nanoTime();
		
		while(cur_Time != stop_Time)
			cur_Time = System.nanoTime();

		/** Call function to evoke when is timer is up **/
	}
	
	/** Returns the number of seconds left on the timer **/
	public int getRemainingTime(){
		return (int) ((stop_Time - System.nanoTime()) / 1000000000);
	}
}
