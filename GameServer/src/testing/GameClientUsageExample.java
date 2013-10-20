package testing;
import java.io.IOException;

import net.GameClient;
import net.GameServer;
import util.messages.*;


public class GameClientUsageExample {
	boolean authenticated;
	
	/**
	 * Usage example
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("///Testing Game Client///");
		String host = "localhost";
		//host = "141.219.153.161";
		
		GameClientUsageExample logic = new GameClientUsageExample();
		GameClient gameBot = new GameClient(host,GameServer.TEST_PORT);
		
		//gameBot.setUserInfo(123, "Mike");
		
		String username = "";
		String password = "";
		
		/*
		 * Add User 
		 */
		//-------------------
		
		
		//------------------
		
		
		gameBot.authenticateUser(username, password);
		
		Message rec;
		while(!logic.authenticated){
			while((rec = gameBot.receiveMessage()) == null) ;
			logic.handleMessage(rec);
		}
		
		//long time = System.currentTimeMillis();
		//while((System.currentTimeMillis() - time ) < 3000);
		
		
		System.out.println("[CHAT_CLIENT]Successfully conneceted");
		System.out.println("[CHAT_CLIENT]First Message sent");
		Message inMsg;
		while(true){
			if((inMsg = gameBot.receiveMessage()) != null){
				System.out.println("[ChatClient]Received");
				System.out.println(inMsg);
				//if(gameBot.streamEmpty())
					//gameBot.sendMessage(
							//new Message(new Long(1),gameBot.user + gameBot.randomID,
								//	System.currentTimeMillis(),"spam spam spam"));
			}
		}
		//System.out.println("Fail");
	}
	
	public GameClientUsageExample(){
		
	}
	public void handleMessage(Message m){

		System.out.println(m);
	}
}
