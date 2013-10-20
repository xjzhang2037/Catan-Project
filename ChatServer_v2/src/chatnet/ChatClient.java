package chatnet;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import chatutil.messages.*;

/**
 * Class to handle a connection with the ChatServer,
 * send and receive messages from groups and friends
 * 
 * @author Michael Tuer
 *
 */
public class ChatClient {
	// members to maintain the connection
	Socket s;
	ObjectInputStream in;
	ObjectOutputStream out;
	InputStream checkReady;
	private boolean connected = false;
	
	private String hostn;
	private int portn;
	
	// user information
	private String user;
	private int userID;
	
	List<Integer> groups = new LinkedList<Integer>();
	
	/**
	 * Constructor that automatically sets up the connection
	 * Does not authenticate however, call joinServer() after
	 * setting up the username and id
	 * 
	 * @param hostname
	 * @param port
	 */
	public ChatClient(String hostname, int port){
		try{
			hostn = hostname;
			portn = port;
			
			System.out.println("Chat Client begun");
			s = new Socket(hostname, port);
			in = new ObjectInputStream(checkReady = s.getInputStream());
			out = new ObjectOutputStream(s.getOutputStream());
			connected = true;
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Reconnect after disconnecting
	 */
	public void reconnect(){
		if(!connected){
			try{
				s = new Socket(hostn, portn);
				in = new ObjectInputStream(checkReady = s.getInputStream());
				out = new ObjectOutputStream(s.getOutputStream());
				connected = true;
			}catch(IOException e){
				e.printStackTrace();
				return;
			}
			
			JoinMsg m = new JoinMsg(userID, user);
			this.writeMsg(m);
		}
	}
	
	/**
	 * Sets information needed to authenticate with the server
	 * 
	 * @param id int userID
	 * @param name String username
	 */
	public void setUserInfo(int id, String name){
		userID = id;
		user = name;
	}
	
	/**
	 * ServerMessage back if
	 * successful
	 * 
	 */
	public void joinServer(){
		JoinMsg m = new JoinMsg(userID, user);
		this.writeMsg(m);
	}
	
	
	/**
	 * Write a message to the ServerConnection
	 * 
	 * @param m the Message to be written
	 * 
	 * @return true if no exceptions were thrown
	 */
	public boolean sendMessage(int groupID, String message){
		if(!connected)
			return false;
		ChatMsg m = new ChatMsg(message, groupID);
		return writeMsg(m);
	}
	
	/**
	 * Used by the client to send off messages to the ServerConnection
	 * 
	 * @param m Message to send
	 * @return false if the client is not connected.
	 */
	private boolean writeMsg(Message m){
		try{
			out.reset();
			out.writeObject(m);
			out.flush();
		}catch(IOException e){
			e.printStackTrace();
			connected = false;
			return false;
		}
		
		return true;
	}
	
	/**
	 * Read a message from the server connection
	 * 
	 * @return null if their is no message to receive
	 */
	public Message receiveMessage(){
		if(!connected || streamEmpty()){
			return null;
		}
		
		try {
			return (Message)in.readObject();
			
		} catch (IOException e) {
			e.printStackTrace();
			connected = false;
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Join a chat group, receive this number from
	 * the GameClient when joining a game or from
	 * CreateNewGroup
	 * 
	 * @param gid
	 */
	public void joinGroup(int gid){
		this.groups.add(gid);
		GroupJoinMsg m = new GroupJoinMsg(gid);
		this.writeMsg(m);		
	}
	
	/**
	 * Wait for a OpenGroupMsg response
	 */
	public void createNewGroup(){
		this.writeMsg(new OpenGroupMsg());
	}
	
	/**
	 * Invite a friend to a group
	 * 
	 * @param friendID userid of the friend
	 * @param groupID the groupid of the group you want
	 *  		your friend to join
	 */
	public void inviteFriend(int friendID, int groupID){
		this.writeMsg(new RequestJoinGroupMsg(friendID, groupID));
	}
	
	/**
	 * Respond to a request join group message
	 * 
	 * @param response response to message
	 * @param request the received request message
	 */
	public void respondToRequest(boolean response, RequestJoinGroupMsg request){
		request.declined = !response;
		request.responding = true;
		this.writeMsg(request);
	}
	/**
	 * Check if the stream is empty
	 * @return boolean
	 */
	private boolean streamEmpty(){
		try {
			return (checkReady.available() <= 0);
			
		} catch (IOException e) {
			e.printStackTrace();
			connected = false;
			return true;
		}
	}
	

	/**
	 * Close the connection with the server. Call reconnect
	 * to join the ChatServer again
	 */
	public void closeConnection(){
		CloseConnectionMsg ccm = new CloseConnectionMsg();
		this.writeMsg(ccm);
		connected = false;
	}
	
	/**
	 * Leave a group
	 * 
	 * @param groupN groupid of the group to leave
	 */
	public void leaveGroup(int groupN){
		LeaveGroupMsg m = new LeaveGroupMsg(groupN);
		this.writeMsg(m);
	}
}
