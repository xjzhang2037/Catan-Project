package util;

import java.util.PriorityQueue;

/**
 * Generates game ids
 * 
 * @author Dan
 * 
 */
public class Tracker {
	private PriorityQueue<Integer> tracker;
	private int size = 10;

	/**
	 * Generates 10 game ids in queue to start
	 */
	public Tracker() {
		tracker = new PriorityQueue<Integer>();
		for (int i = 1; i < size; i++)
			tracker.add(i);
	}

	/**
	 * Adds to the tracker
	 * 
	 * @param i
	 */
	private void growTracker(int i) {
		tracker.add(i);
	}

	/**
	 * Get a ID
	 * 
	 * @return
	 */
	public int getNewID() {
		int id = tracker.poll();
		if (tracker.isEmpty()) {
			this.growTracker(++size);
		}
		return id;
	}
}
