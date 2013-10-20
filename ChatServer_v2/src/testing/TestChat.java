package testing;

import java.io.IOException;

import chatnet.ChatClient;
import chatnet.ChatServer;
import chatutil.messages.*;

/**
 * Test functionality of the ChatClient
 * 
 * @author Michael Tuer
 *
 */
public class TestChat {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		boolean user1 = true;
		String uname = "Mike";
		int uid = 2;
		if(user1){
			uname = "Dave";
			uid = 1;
		}
		System.out.println("///Testing Chat Client///");
		String host = "localhost";
		//host = "141.219.153.161";
		ChatClient chatBot = new ChatClient(host,ChatServer.TEST_PORT);
		chatBot.setUserInfo(uid, uname);
		
		chatBot.joinServer();
		Message rec;
		
		while((rec = chatBot.receiveMessage()) == null) ;
		System.out.println(rec);
		int gid = 0;
		if(!user1){
			chatBot.createNewGroup();
			while((rec = chatBot.receiveMessage()) == null) ;
			System.out.println(rec);
			if(rec instanceof OpenGroupMsg){
				gid = ((OpenGroupMsg) rec).groupID;
				System.out.println("group created successfully");
				chatBot.inviteFriend(1, gid);
				while((rec = chatBot.receiveMessage()) == null) ;
				while(!((ServerMsg)rec).status){
					chatBot.inviteFriend(1, gid);
					while((rec = chatBot.receiveMessage()) == null) ;
				}
				System.out.println("Invite Friend successfully");
			}else{
				System.out.println("Error");
				
			}
			
		}else{
			while((rec = chatBot.receiveMessage()) == null) ;
			System.out.println(rec);
			if(rec instanceof RequestJoinGroupMsg){
				gid = ((RequestJoinGroupMsg) rec).groupID;
				
			}else{
				System.out.println("Error");
				
			}
			chatBot.joinGroup(gid);
		}
		
	
		System.out.println("[CHAT_CLIENT]Successfully conneceted");
		System.out.println("[CHAT_CLIENT]First Message sent");
		Message inMsg;
		chatBot.sendMessage(gid, "Hello everyone!");
		int msgCount = 0;
		while(true){
			if((inMsg = chatBot.receiveMessage()) != null){
				System.out.println("[ChatClient]Received");
				System.out.println(inMsg);
				//if(chatBot.streamEmpty())
					//chatBot.sendMessage(
							//new Message(new Long(1),chatBot.user + chatBot.randomID,
								//	System.currentTimeMillis(),"spam spam spam"));
			}
			delay(3000);
			chatBot.sendMessage(gid, "Message " + msgCount++);
			if(msgCount == 1 && user1){
				chatBot.leaveGroup(gid);
			}else if(msgCount == 2 && user1){
				//chatBot.joinGroup(gid);
			}
		}
		//System.out.println("Fail");
	}
	
	private static void delay(int millis){
		long time = System.currentTimeMillis();
		while((System.currentTimeMillis() - time) < millis);
	}
	

}
