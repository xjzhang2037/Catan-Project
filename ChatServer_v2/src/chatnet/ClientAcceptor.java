package chatnet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import chatnet.ServerConnection;

/**
 * Thread to listen for join requests from ChatClients
 * 
 * @author Michael Tuer
 *
 */
public class ClientAcceptor extends Thread{
	ServerSocket listener;
	ChatServer chatServ;
	
	/**
	 * Constructor for a ClientAcceptor
	 * 
	 * @param cs ChatServer reference to pass to the ServerConnections
	 * @param ss ServerSocket socket to listen on
	 */
	public ClientAcceptor(ChatServer cs, ServerSocket ss){
		listener = ss;
		this.chatServ = cs;
	}
	
	/**
	 * Called when this thread is run.
	 * Continuously accept new clients
	 */
	public void run(){
		
		while(true){
			try {
				Socket s = listener.accept();
				new ServerConnection(chatServ, s).start();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				return; // stop listening
			}
		}
	}
}
