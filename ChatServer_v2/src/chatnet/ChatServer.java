package chatnet;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import chatnet.ClientAcceptor;
import chatnet.ServerConnection;

import chatutil.GroupTracker;
import chatutil.OutputBundle;
import chatutil.messages.GroupJoinMsg;
import chatutil.messages.Message;
import chatutil.messages.RequestJoinGroupMsg;
import chatutil.messages.ServerMsg;

/**
 * Class to create ServerConnections with ChatClients and maintain them
 * 
 * @author Michael Tuer
 *
 */
public class ChatServer {
	public static final int TEST_PORT = 4445;

	private ServerSocket ss;
	private GroupTracker groups = new GroupTracker();
	private Map<Integer, ServerConnection> clients = new HashMap<Integer,ServerConnection>();
	
	/**
	 * Starts the ChatServer
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Chat Server Started...");
		ChatServer testServ = new ChatServer(ChatServer.TEST_PORT);
		System.out.println("Port opened...");
		//starts a new thread to accept clients
		// that will communicate with this server through the piped streams
		testServ.acceptClients(); 
		boolean loop = true;
		while(loop);
	}
	
	/**
	 * Constructor for the ChatServer
	 * @param port
	 * @throws IOException
	 */
	public ChatServer(int port) throws IOException{
		ss = new ServerSocket(port);
	}
	
	/**
	 * Start a new thread to accept clients
	 * creates an instance of ClientAcceptor
	 */
	public void acceptClients(){
		new ClientAcceptor(this, ss).start();
	}
	
	
	/**
	 * Attempts to add the client to the chat group
	 * 
	 * @param groupID long id of the group
	 * @param os OutputStream that the client is listening to
	 * 
	 * @return Queue<Message> Where group messages will be sent
	 */
	public Queue<Message> joinGroup(int groupID, int uid){
		OutputBundle ob = groups.getGroup(groupID);
		if(ob == null){
			System.out.println("Null still for " + groupID);
			return null;
		}
		System.out.println("{joingroup} " + ob.getID() + " >< " + groupID);
		
		return ob.addClient(uid);
	}
	
	/**
	 * Returns an OutputBundle for the specified ChatGroup
	 * 
	 * @param groupID
	 * @return OutputBundle
	 */
	public OutputBundle getGroup(int groupID){
		return groups.getGroup(groupID);
	}
	
	/**
	 * Remove a client from the server
	 * 
	 * @param userID 
	 */
	public void removeClient(int userID){
		clients.remove(userID);
	}
	
	/**
	 * Add a client to the server
	 * Maps userid to the ServerConnection
	 * 
	 * @param m Message wrapper storing userid
	 * @param sc 
	 * @return
	 */
	public boolean putJoinMessage(int uid, ServerConnection sc){	
		clients.put(uid, sc);
		return true;
	}
	
	/**
	 * Join a user to a ChatGroup
	 * 
	 * @param m Wrapper storing group and user ids
	 * @return
	 */
	public ServerMsg putGroupJoinMessage(GroupJoinMsg m){
		int groupID = m.groupID;
		int uid = m.userID;
		
		Queue<Message> retQ = this.joinGroup(groupID, uid);
		boolean outcome = retQ != null;
		
		// Send a response back the ServerConnection that can be passed
		// to the ChatClient
		ServerMsg response = new ServerMsg(outcome);
		response.setQueue(retQ);
		//pass the OutputBundle to the server connection
		response.setOutputBundle(this.getGroup(groupID)); 
		
		return response;
	}
	
	/**
	 * Request that a friend joins a group
	 * 
	 * @param m RequestJoinGroupMsg sent to friend ChatClient
	 * 
	 * @return true if the friend was logged in
	 */
	public boolean askUserToJoin(RequestJoinGroupMsg m){
		ServerConnection sc = clients.get(m.userID);
		if(sc == null)return false;
		sc.writeMsg(m);
		return true;
	}
	
	/**
	 * Request that a friend joins a group
	 * 
	 * @param m RequestJoinGroupMsg sent to friend ChatClient
	 * 
	 * @return true if the friend was logged in
	 */
	public boolean respondToRequest(RequestJoinGroupMsg m){
		ServerConnection sc = clients.get(m.senderID);
		if(sc == null)return false;
		sc.writeMsg(m);
		if(m.declined){
			sc.leaveGroup(m.groupID, true);
		}
		
		return true;
	}
	
	/**
	 * List groups that are open and ready to join
	 * 
	 * @return List of group ids
	 */
	public List<Integer> listOpenGroups(){
		return groups.listGroups();
	}

	/**
	 * Create a ChatGroup
	 * @return id of the created group
	 */
	public int createGroup(){
		return groups.createGroup();
	}
}
