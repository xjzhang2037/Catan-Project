package chatutil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Class used to create groups and keep track of free group numbers
 * 
 * @author Michael Tuer
 *
 */
public class GroupTracker{
	private PriorityQueue<Integer> tracker;
	private Map<Integer, OutputBundle> groups;
	private int growRate = 25;
	private final int  minSize = 5;
	
	/**
	 * Constructor
	 */
	public GroupTracker(){
		tracker = new PriorityQueue<Integer>();
		this.growTracker(1);
		groups = new HashMap<Integer,OutputBundle>();
	}
	
	/**
	 * Constructor specifying how fast the class allocates groups
	 * 
	 * @param growRate set higher if a high amount of users are
	 * 			expected
	 */
	public GroupTracker(int growRate){
		super();
		this.growRate = growRate;
	}
	
	/**
	 * Makes the priority q
	 * @param start
	 */
	private void growTracker(int start){
		for(int idx = start; idx < start + growRate; idx++){
			tracker.offer(idx);
		}
	}
	
	/**
	 * Create a new chat group
	 * 
	 * @return id of the group to join
	 */
	public int createGroup(){
		int id = tracker.poll();
		if(tracker.isEmpty()){
			this.growTracker(++id);
		}
		System.out.println("Creating Bundle: " + id);
		groups.put(id, new OutputBundle(id, this));
		return id;
	}
	
	/**
	 * Responsibility of the user to close the streams
	 * 
	 * @param id Long groupID
	 * 
	 * @return OutputBundle to close
	 */
	public OutputBundle destroyGroup(int id){
		tracker.offer(id);
		if(groups.size() - 1 < this.minSize){
			this.createGroup();
		}
		groups.get(id).closeGroup();
		return groups.remove(id);
	}
	
	/**
	 * List all open groups
	 * 
	 * @return List of groupids
	 */
	public List<Integer> listGroups(){
		List<Integer> groupNumbers = new LinkedList<Integer> ();
		
		for(Integer group: groups.keySet()){
			groupNumbers.add(group);
		}
		
		return groupNumbers;
	}
	
	/**
	 * Return the group
	 * 
	 * @param id groupid
	 * @return OutputBundle
	 */
	public OutputBundle getGroup(int id){
		return groups.get(id);
	}

}
