package starter;
import chatnet.ChatServer;

/**
 * A thread that can run either the Chat or Game server
 * Able to give GameServer a pointer to the ChatServer
 * 
 * @author Michael Tuer
 *
 */
public abstract class ServerThread extends Thread {
	int port;
	ChatServer cs;
	
	/**
	 * Constructor
	 * 
	 * @param port
	 */
	public ServerThread(int port){
		this.port = port;
	}
	
	/**
	 * Receive a reference to a ChatServer
	 * @return
	 */
	public ChatServer getChatServer(){
		return cs;
	}
	
	/**
	 * Set a reference to a ChatServer
	 * 
	 * @param cs
	 */
	public void setChatServer(ChatServer cs){
		this.cs = cs;
	}
}
