package chatutil;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import chatutil.messages.Message;


/**
 * Class that stores a map of Queues that store messages. Used by
 * ServerConnections to send messages to other ServerConnections
 * 
 * @author Michael Tuer
 *
 */
public class OutputBundle {
	private int groupID;
	private Map<Integer,Queue<Message>> msgQs;
	private GroupTracker parent;
		
	/**
	 * Constructor for an OutputBundle
	 * @param id groupID
	 * @param watcher GroupTracker - keeps track of group numbers
	 */
	public OutputBundle(int id, GroupTracker watcher){
		this.groupID = id;
		msgQs = new HashMap<Integer,Queue<Message>>();
		parent = watcher;
	}
	
	/**
	 * Get the groupID of this bundle
	 * @return int groupID
	 */
	public int getID(){
		return groupID;
	}
	
	/**
	 * Add a ServerConnection to the map so it can receive
	 * messages from the group
	 * 
	 * @param userID of the client
	 * 
	 * @return a queue that will be filled with group messages
	 */
	public Queue<Message> addClient(int userID){
		System.out.println("[OutputBundle]Bundle:" + groupID + "; Added userID: " + userID);
		if(msgQs.get(userID) != null){
			System.out.println("[OutputBundle] stream already exists in this group.");
			return null;
		}
		Queue<Message> msgQ = new LinkedList<Message>();
		msgQs.put(userID, msgQ);
		
		return msgQ;
	}
	
	/**
	 * Remove the associated queue from the map
	 * 
	 * @param userID of the client
	 * @return true if the client was removed
	 */
	public boolean removeClient(int userID){
		boolean result = msgQs.remove(userID) != null;
		if(msgQs.size() == 0){
			parent.destroyGroup(groupID);
		}
			
		return result;
	}
	
	/** @return amount of players in group */
	public int getSize(){
		return msgQs.size();
	}
	
	/**
	 * Add the msg to each ServerConnections queues
	 * 
	 * @param msg
	 * @throws IOException
	 */
	public void writeToClients(Message msg){
		System.out.println("[OutputBundle]Writing to clients.");
		for(Entry<Integer, Queue<Message>> msgQ: msgQs.entrySet()){
			//if(write.getKey() != msg.getUserID())
				msgQ.getValue().offer(msg);
		}
	}
	
	/**
	 * When the ServerConnection reads null, 
	 * it will know the group is terminated
	 */
	public void closeGroup(){
		for(Entry<Integer, Queue<Message>> msgQ: msgQs.entrySet()){
				msgQ.getValue().offer(null);
		}
	}

}
