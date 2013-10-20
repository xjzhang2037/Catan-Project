package chatnet;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import chatutil.OutputBundle;
import chatutil.messages.*;

import chatutil.messages.Message;

/**
 * Thread hosted by the Server. Handles a single connection with a client
 * 
 * @author Michael Tuer
 *
 */
public class ServerConnection extends Thread{
	private boolean threadLife = true;
	
	// Members to maintain the connection
	private Socket connection;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private InputStream checkReady;
	private ChatServer cs;
	
	//output
	private Map<Integer,OutputBundle> bundles = new HashMap<Integer, OutputBundle>();
	//intput
	private Map<Integer, Queue<Message>> msgQs = new HashMap<Integer, Queue<Message>>();
	
	// Members to store client information
	private String username;
	private int userid;

	/**
	 * Constructor for the ServerConnection
	 * 
	 * @param cs referrence to the ChatServer
	 * @param socket connection with the ChatClient
	 */
	public ServerConnection(ChatServer cs, Socket socket){
		super("ServerConnectionThread");
		connection = socket;
		this.cs = cs;
	}
	
	/**
	 * Called when the thread is started. Checks for messages,
	 * handles them, and writes messages to the client from other
	 * clients.
	 */
	public void run(){
		try{
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(checkReady = connection.getInputStream());
			
			while(threadLife){
				Message m = null;
				
				// check for messages from other connections in the group
				for(Entry<Integer,Queue<Message>> ent: msgQs.entrySet()){
					Queue<Message> msgQ = ent.getValue();
					if(msgQ == null){ // should never happen
						msgQs.remove(ent.getKey());
						
					}else if(msgQ.size() > 0){
						m = msgQ.poll();
						if(m == null){
							msgQs.remove(ent.getKey());
						}
						
						this.writeMsg(m);
						
					} 
				}
				
				// check for messages from the client
				if(checkReady.available() > 0){ 
					m = this.readMsg();
					this.handleMessage(m);
				}
				
			} // end while loop, thread will now exit
			
		}catch(IOException e){
			e.printStackTrace();
			this.handleDisconnect();
		}
		
		System.out.println("[ServerConnection] Connection is dead; thread will die now...");
	}
	
	/**
	 * Handle a disconnect
	 */
	private void handleDisconnect(){
		cs.removeClient(userid);
		threadLife = false;
		msgQs.clear();
		
		// remove self from all OutputBundles
		for(OutputBundle bundle: bundles.values()){
			bundle.removeClient(userid);
		}
	}
	
	/**
	 * Handle an incoming message
	 * 
	 * @param m Message to be dealt with
	 */
	private void handleMessage(Message m) {

		// Send message to server, send back response
		if(m instanceof GroupJoinMsg){ 
			GroupJoinMsg gjm = (GroupJoinMsg)m;
			int gid = gjm.groupID;
			gjm.userID = userid;
			
			ServerMsg response = cs.putGroupJoinMessage(gjm);
			bundles.put(gid, response.getOutputBundle());
			msgQs.put(gid, response.getQueue());
			
			System.out.println("Write message success: " + this.writeMsg(response));
		
		// Send message to server, send result of authentication	
		}else if(m instanceof JoinMsg){
			JoinMsg jm = (JoinMsg)m;
		
			userid = jm.userID;
			username = jm.username;
			boolean outcome = cs.putJoinMessage(jm.userID, this);
			
			ServerMsg response = new ServerMsg(outcome);
			this.writeMsg(response);
			
		// Send message to GroupHandler
		}else if(m instanceof ChatMsg){
			ChatMsg cm = (ChatMsg)m;
			cm.userID = userid;
			cm.username = username;
			
			OutputBundle bundle = bundles.get(cm.groupID);
			if(bundle == null){
				System.out.println("ERROR: Client is not part of this group");
			}else{
				bundle.writeToClients(cm);
			}
			
		}else if(m instanceof OpenGroupMsg){
			// create the group
			int group = cs.createGroup();
			OpenGroupMsg ogm = (OpenGroupMsg)m;
			ogm.groupID = group;
			
			// join the group
			GroupJoinMsg gjm = new GroupJoinMsg(group);
			gjm.userID = userid;
			
			ServerMsg response = cs.putGroupJoinMessage(gjm);
			bundles.put(group, response.getOutputBundle());
			msgQs.put(group, response.getQueue());
			
			this.writeMsg(ogm);
			
		}else if(m instanceof CloseConnectionMsg){
		
			handleDisconnect();
			
		}else if(m instanceof LeaveGroupMsg){
			leaveGroup(((LeaveGroupMsg) m).groupID, false);
			
		}else if(m instanceof RequestJoinGroupMsg){
			RequestJoinGroupMsg rjgm = (RequestJoinGroupMsg)m;
			if(((RequestJoinGroupMsg) m).responding){
				((RequestJoinGroupMsg) m).acceptorUsername = this.username;
				cs.respondToRequest(rjgm);
				
				if(!rjgm.declined){
					int group = rjgm.groupID;
					GroupJoinMsg gjm = new GroupJoinMsg(group);
					gjm.userID = userid;
					
					ServerMsg response = cs.putGroupJoinMessage(gjm);
					bundles.put(group, response.getOutputBundle());
					msgQs.put(group, response.getQueue());
				}
			}else{
				((RequestJoinGroupMsg) m).senderID = this.userid;
				((RequestJoinGroupMsg) m).username = username;
				this.writeMsg(new ServerMsg(cs.askUserToJoin((RequestJoinGroupMsg)m)));
			}
		}else{
			System.out.println("error");
		}

	}
	
	public void leaveGroup(int gid, boolean removeIfOne){
		OutputBundle ob = bundles.get(gid);
		if(!removeIfOne || (removeIfOne && ob.getSize() == 1)){
			bundles.get(gid).removeClient(userid);
			bundles.remove(gid);
			msgQs.remove(gid);
		}
	}
	
	/**
	 * Write a message to the client connection
	 * 
	 * @param m the Message to be written
	 * 
	 * @return true if no exceptions were thrown
	 */
	public boolean writeMsg(Message m){
		
		try {
			out.reset();
			out.writeObject(m);
			out.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			this.handleDisconnect();
			return false;
		}
	}
	
	/**
	 * Read a message from the client connection
	 * 
	 * @return Message from the client
	 * @throws IOException
	 */
	private Message readMsg() throws IOException{
		
		try {
			return (Message)in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
