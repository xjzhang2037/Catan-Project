package CS3141;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import CS3141.Button.ButtonImage;
import chatutil.messages.*;
import util.Friend;
import util.dataWrappers.DbMessage;
import util.dataWrappers.UserStats;
import util.messages.FriendRequests;
import util.messages.Friends;
import util.messages.Message;
import util.messages.UserInfo;

/**
 * 
 * @author csdoig
 *
 */
public class UserScreen {
	/**
	 * 
	 * @author csdoig
	 *
	 */
	public class FriendRequestLine {
		private int _width;
		private int _height;
		
		Button accept;
		Button reject;
		
		Font font = new Font(Font.SERIF, Font.PLAIN, 12);
		
		String username = "";
		int id = 0;
		
		/**
		 * 
		 * @param width
		 * @param height
		 * @param msg
		 */
		public FriendRequestLine(int width, int height, DbMessage msg){
			this._width = width;
			this._height = height;
			
			id = msg.senderid;
			username = msg.senderName;
			
			
			accept = new Button("accept", font, new Color(200, 200, 200), 0, 0, 100, 25 - 1, false);
			//accept.setImage(ButtonImage.JOIN);
			reject = new Button("reject", font, new Color(200, 200, 200), 0, 0, 100, 25 - 1, false);
			//reject.setImage(ButtonImage.OBSERVE);
		}
		
		/**
		 * 
		 * @param g
		 * @param x
		 * @param y
		 */
		public void draw(Graphics g, int x, int y){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, _width, _height);

			CatanGraphics.drawCenteredText(g, Color.BLACK, font, username, x + 10, y, _width - 20, 25);
			
			accept.updatePosition(x, y + 25);
			accept.draw(g);
			
			reject.updatePosition(x + 100, y + 25);
			reject.draw(g);
		}
	}
	
	/**
	 * 
	 * @author csdoig
	 *
	 */
	public class FriendListLine {
		private int _width;
		private int _height;
		
		Button chat;
		Button remove;
		
		boolean chatInProgress = false;
		
		Font font = new Font(Font.SERIF, Font.PLAIN, 12);
		
		String username;
		boolean online;
		int id;
		
		/**
		 * 
		 * @param width
		 * @param height
		 * @param friend
		 */
		public FriendListLine(int width, int height, Friend friend){
			this._width = width;
			this._height = height;
			
			username = friend.getFriendName();
			online = friend.checkConnection();
			id = friend.getFriendID();
			
			chat = new Button("chat", font, new Color(200, 200, 200), 0, 0, 100, 25 - 1, false);
			//chat.setImage(ButtonImage.JOIN);
			remove = new Button("un-friend", font, new Color(200, 200, 200), 0, 0, 100, 25 - 1, false);
			//remove.setImage(ButtonImage.OBSERVE);
		}
		
		/**
		 * 
		 * @param g
		 * @param x
		 * @param y
		 */
		public void draw(Graphics g, int x, int y){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, _width, _height);

			if(online){
				CatanGraphics.drawCenteredText(g, Color.BLACK, font, username + " (online)", x + 10, y, _width - 20, 25);
			} else {
				CatanGraphics.drawCenteredText(g, Color.BLACK, font, username + " (offline)", x + 10, y, _width - 20, 25);
			}
			
			//if(chatGroupID > 0 && chatWithID == id && chatStatus){
			//	chat.setText("stop chat");
			//} else {
			//	chat.setText("chat");
			//}
			
			chat.updatePosition(x, y + 25);
			chat.draw(g);
			
			remove.updatePosition(x + 100, y + 25);
			remove.draw(g);
		}
	}

	private int width;
	private int height;
	
	private Color background = new Color(100, 100, 100);
	
	int titleBarHeight = Catan.getFrames()[0].getInsets().top;
	int left = Catan.getFrames()[0].getInsets().left;
	int right = Catan.getFrames()[0].getInsets().right;
	
	Button lobby;
	Button user;
	Button exit;
	
	private TextBox currentPassword;
	private TextBox newPassword1;
	private TextBox newPassword2;
	private Button changePassword;
	
	Message gameMsg = null;
	
	private int updateCount = 1000;
	
	UserStats stats = null;
	
	
	private TextBox friendChatHistory;
	private TextBox friendChatNewMessage;
	private Button friendChatSubmit;
	
	private TextBox friendRequest;
	private Button friendRequestSubmit;
	
	int[] tri1X = {0, 8, 16};
	int[] tri1Y = {16, 0, 16};
	Polygon tri1 = new Polygon(tri1X, tri1Y, 3);
	int[] tri2X = {0, 8, 16};
	int[] tri2Y = {0, 16, 0};
	Polygon tri2 = new Polygon(tri2X, tri2Y, 3);
	public boolean canScrollUp = false;
	public boolean canScrollDown = false;
	public boolean scrollUp = false;
	public boolean scrollDown = false;
	public boolean scrollUpOnce = false;
	public boolean scrollDownOnce = false;
	private int loops;
	private final int loopsToDelay = 1;
	int yOffset = 0;
	
	Polygon requests_tri1 = new Polygon(tri1X, tri1Y, 3);
	Polygon requests_tri2 = new Polygon(tri2X, tri2Y, 3);
	public boolean requests_canScrollUp = false;
	public boolean requests_canScrollDown = false;
	public boolean requests_scrollUp = false;
	public boolean requests_scrollDown = false;
	public boolean requests_scrollUpOnce = false;
	public boolean requests_scrollDownOnce = false;
	private int requests_loops;
	private final int requests_loopsToDelay = 1;
	int requests_yOffset = 0;
	
	
	List<Friend> friends;
	List<FriendListLine> friendsLineList = new LinkedList<FriendListLine>();
	List<FriendRequestLine> friendRequestsList = new LinkedList<FriendRequestLine>();
	
	
	public int chatGroupID = -1;
	public int chatWithID = 0;
	public boolean chatStatus = false;
	
	public int chatOtherID = 0;
	public String chatOtherUsername = "";
	
	
	Map<Integer, Integer> chatIDs; // userid, chat groupid
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param g
	 */
	public UserScreen(int width, int height, Graphics g){
		this.width = width;
		this.height = height;
		
		tri1.translate(1000 + 10 - 25, 450 + 30);
		tri2.translate(1000 + 10 - 25, 700);
		
		requests_tri1.translate(770 + 10 - 25, 450 + 30);
		requests_tri2.translate(770 + 10 - 25, 630);
		
		lobby = new Button("Lobby", Color.LIGHT_GRAY, left, titleBarHeight, 100, 50, false);
		user = new Button("User", Color.LIGHT_GRAY, left + 100, titleBarHeight, 100, 50, false);
		exit = new Button("Exit", Color.LIGHT_GRAY, width - right - 100, titleBarHeight, 100, 50, false);
		exit.setImage(ButtonImage.EXIT);
		
		//username = new TextBox("", 50, 200, 250, 30, true, false, g, false);
		currentPassword = new TextBox("", 25, 250, 250, 30, true, false, g, true);
		newPassword1 = new TextBox("", 25, 350, 250, 30, true, false, g, true);
		newPassword2 = new TextBox("", 25, 450, 250, 30, true, false, g, true);
		changePassword = new Button("Change Password", new Color(200, 200, 200), 25, 500, 250, 50, false);
		changePassword.setImage(ButtonImage.PASSWORD);
		
		
		friendChatHistory = new TextBox("", 320, 470, 200,
				height - 470 - 80, false, true, g, false);
		friendChatNewMessage = new TextBox("", 320, height - 60,
				110, 30, true, false, g, false);
		friendChatSubmit = new Button("Submit", new Font(Font.SERIF, Font.PLAIN, 18),
				new Color(200, 200, 200), 440, height - 60, 80, 30,
				false);
		friendChatSubmit.setImage(ButtonImage.SEND);
		
		
		friendRequest = new TextBox("", 550, height - 60,
				110, 30, true, false, g, false);
		friendRequestSubmit = new Button("Submit", new Font(Font.SERIF, Font.PLAIN, 18),
				new Color(200, 200, 200), 670, height - 60, 80, 30,
				false);
		friendRequestSubmit.setImage(ButtonImage.SEND);
	}
	
	/**
	 * 
	 * @param me
	 * @return
	 */
	public boolean isPressed(MouseEvent me){

		if(tri1.contains(me.getX(), me.getY())){
			scrollUp = false;
			scrollDown = true;
		}
		if(tri2.contains(me.getX(), me.getY())){
			scrollUp = true;
			scrollDown = false;
		}
		if(requests_tri1.contains(me.getX(), me.getY())){
			requests_scrollUp = false;
			requests_scrollDown = true;
		}
		if(requests_tri2.contains(me.getX(), me.getY())){
			requests_scrollUp = true;
			requests_scrollDown = false;
		}
		
		if(lobby.isDepressed(me)){
			
		}
		if(user.isDepressed(me)){
					
		}
		if(exit.isDepressed(me)){
			
		}
		
		if(currentPassword.isDepressed(me)){
			
		}
		if(newPassword1.isDepressed(me)){
			
		}
		if(newPassword2.isDepressed(me)){
			
		}
		if(changePassword.isDepressed(me)){
			
		}
		
		if(friendChatSubmit.isDepressed(me)){
			
		}
		if(friendChatHistory.isDepressed(me)){
			
		}
		if(friendChatNewMessage.isDepressed(me)){
			
		}
		
		if(friendRequest.isDepressed(me)){
			
		}
		if(friendRequestSubmit.isDepressed(me)){
			
		}
		
		for(FriendListLine line: friendsLineList){
			if(line.chat.isDepressed(me)){
				
			}
			if(line.remove.isDepressed(me)){
				
			}
		}
		
		for(FriendRequestLine line: friendRequestsList){
			if(line.accept.isDepressed(me)){
				
			}
			if(line.reject.isDepressed(me)){
				
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param me
	 * @param g
	 * @return
	 */
	public boolean isClicked(MouseEvent me, Graphics g){
		if(lobby.isClicked(me)){
			Catan.screen = Catan.LOBBY_SCREEN;
			Catan.newToScreen = true;			
		}
		if(user.isClicked(me)){

		}
		if(exit.isClicked(me)){
			if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(Catan.getFrames()[0], "Are you sure you want to quit?", "Quitting", JOptionPane.YES_NO_OPTION)){
				System.exit(0);
			}
		}
		
		if(currentPassword.isClicked(me, g)){
			
		}
		if(newPassword1.isClicked(me, g)){
			
		}
		if(newPassword2.isClicked(me, g)){
			
		}
		if(changePassword.isClicked(me)){
			if(!newPassword1.getText().equals(newPassword2.getText())){
				JOptionPane.showMessageDialog(Catan.getFrames()[0],
						"New passwords do not match", "Error",
						JOptionPane.WARNING_MESSAGE);
			} else {
			
				if(currentPassword.getText().equals(Catan.pass)){
					Catan.client.changePassword(currentPassword.getText(), newPassword1.getText());	
					Catan.pass = newPassword1.getText();
				} else {
					JOptionPane.showMessageDialog(Catan.getFrames()[0],
							"Incorrect current password", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
				
							
				currentPassword.setText("");
				newPassword1.setText("");
				newPassword2.setText("");
			}
		}
		
		if(friendChatSubmit.isClicked(me)){
			sendMessage();
		}
		if(friendChatHistory.isClicked(me, g)){
					
		}
		if(friendChatNewMessage.isClicked(me, g)){
			
		}
		
		if(friendRequest.isClicked(me, g)){
			
		}
		if(friendRequestSubmit.isClicked(me)){
			requestFriend();
		}
		
		for(FriendListLine line: friendsLineList){
			if(line.chat.isClicked(me)){
				Catan.chatBot.createNewGroup();
				chatWithID = line.id;
				
				
				//if(chatGroupID <= 0 && chatOtherID <= 0){
				//	Catan.chatBot.createNewGroup();
				//	chatWithID = line.id;
				//} else {
				//	Catan.chatBot.leaveGroup(chatGroupID);
				//	chatWithID = -1;
				//}
				
				//if(chatIDs.get(Integer.valueOf(line.id)) == null){
				//	Catan.chatBot.createNewGroup();
				//	chatIDs.put(key, value)
				//}
				
			}
			if(line.remove.isClicked(me)){
				if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(Catan.getFrames()[0],
						"Are you sure you want to un-friend " + line.username + "?", "Un-friending",
						JOptionPane.YES_NO_OPTION)){
					
					Catan.client.removeFriend(line.username);
					
				}
			}
		}
		
		for(FriendRequestLine line: friendRequestsList){
			if(line.accept.isClicked(me)){
				Catan.client.acceptFriendRequest(line.id);
			}
			if(line.reject.isClicked(me)){
				Catan.client.declineFriendRequest(line.id);
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param me
	 * @return
	 */
	public boolean isReleased(MouseEvent me){
		scrollUp = false;
		scrollDown = false;
		
		requests_scrollUp = false;
		requests_scrollDown = false;
		
		lobby.released();
		user.released();
		exit.released();
		
		currentPassword.released();
		newPassword1.released();
		newPassword2.released();
		
		changePassword.released();

		friendChatSubmit.released();
		friendChatHistory.released();
		friendChatNewMessage.released();
		
		friendRequest.released();
		friendRequestSubmit.released();
		
		
		for(FriendListLine line: friendsLineList){
			line.chat.released();
			line.remove.released();
		}
		
		for(FriendRequestLine line: friendRequestsList){
			line.accept.released();
			line.reject.released();
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param e
	 */
	public void isKeyPressed(KeyEvent e){
		currentPassword.isKeyPressed(e, newPassword2, newPassword1);
		newPassword1.isKeyPressed(e, currentPassword, newPassword2);
		newPassword2.isKeyPressed(e, newPassword1, currentPassword);
		
		friendChatHistory.isKeyPressed(e);
		if(friendChatNewMessage.isKeyPressed(e)){
			sendMessage();
		}
		
		if(friendRequest.isKeyPressed(e)){
			requestFriend();
		}
	}
	
	/**
	 * 
	 * @param e
	 */
	public void isKeyTyped(KeyEvent e){
		currentPassword.isKeyTyped(e);
		newPassword1.isKeyTyped(e);
		newPassword2.isKeyTyped(e);
		
		friendChatHistory.isKeyTyped(e);
		friendChatNewMessage.isKeyTyped(e);
		
		friendRequest.isKeyTyped(e);
	}
	
	/**
	 * 
	 * @param e
	 */
	public void isKeyReleased(KeyEvent e){
		currentPassword.isKeyReleased(e);
		newPassword1.isKeyReleased(e);
		newPassword2.isKeyReleased(e);
		
		friendChatHistory.isKeyReleased(e);
		friendChatNewMessage.isKeyReleased(e);
		
		friendRequest.isKeyReleased(e);
	}
	
	/**
	 * 
	 * @param g
	 * @param msgs
	 */
	public void draw(Graphics g, ArrayList<Message> msgs){
		if(updateCount > 200){
			//Catan.client.requestUserInfo();
			Catan.client.requestFriends();
			Catan.client.requestFriendRequests();
			updateCount = 0;
		}
		updateCount++;

		chatutil.messages.Message inMsg = Catan.chatBot.receiveMessage();
		ChatMsg chatMsg = null;
		
		while (inMsg != null) {
			
			if(inMsg instanceof ChatMsg){
				
				chatMsg = (ChatMsg)inMsg;
				
				if(chatMsg.message != null){
					String name = chatMsg.username;
					String message = chatMsg.message;
					
					Date date = new Date(chatMsg.timeStamp);
					Calendar cal = GregorianCalendar.getInstance();
					cal.setTime(date);
					
					int hours = cal.get(GregorianCalendar.HOUR);
					int minutes = cal.get(GregorianCalendar.MINUTE);
					
					String mZero = "";
					if (minutes < 10) {
						mZero = "0";
					}
					String hZero = "";
					if (hours == 0) {
						hZero = "0";
					}

					friendChatHistory.append(hZero + hours + ":" + mZero
							+ minutes + " (" + name + ")" + "   "
							+ message + "\n");
					friendChatHistory.moveToBottom();

					//System.out.println("CHAT MESSAGE 2: " + inMsg);
				}

			} else if(inMsg instanceof OpenGroupMsg){
				chatGroupID = ((OpenGroupMsg)inMsg).groupID;
				Catan.chatBot.inviteFriend(chatWithID, chatGroupID);
				//System.out.println("[GUI] invite for chat");
			} else if(inMsg instanceof ServerMsg){
				chatStatus = ((ServerMsg)inMsg).status;
				//System.out.println("[GUI] get status: " + chatStatus);
			} else if(inMsg instanceof RequestJoinGroupMsg){
				
				if(((RequestJoinGroupMsg) inMsg).responding){
					boolean declined = ((RequestJoinGroupMsg) inMsg).declined;
					

					if(declined){
						JOptionPane.showMessageDialog(Catan.getFrames()[0],
								((RequestJoinGroupMsg)inMsg).acceptorUsername +
								" denied your chat request.", "Friend Chat",
								JOptionPane.WARNING_MESSAGE);						
					} else {
						JOptionPane.showMessageDialog(Catan.getFrames()[0],
								((RequestJoinGroupMsg)inMsg).acceptorUsername +
								" accepted your chat request.", "Friend Chat",
								JOptionPane.PLAIN_MESSAGE);
					}
					
				} else {
					
					boolean affirmChatting = JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(Catan.getFrames()[0],
							((RequestJoinGroupMsg)inMsg).username +
							" wants to chat with you. Do you want to?", "Friend Chat", JOptionPane.YES_NO_OPTION);

					Catan.chatBot.respondToRequest(affirmChatting, (RequestJoinGroupMsg)inMsg);
					
					if(affirmChatting){
						friendChatNewMessage.requestFocus();
						
						chatGroupID = ((RequestJoinGroupMsg)inMsg).groupID;
						chatOtherID = ((RequestJoinGroupMsg)inMsg).userID;
						chatOtherUsername = ((RequestJoinGroupMsg)inMsg).username;
					}
				}
			}	
			inMsg = Catan.chatBot.receiveMessage();
		}

		for(Message m: msgs){
			if(m instanceof UserInfo){
				stats = ((UserInfo)m).userinfo;
			} else if(m instanceof Friends){
				friends = ((Friends) m).getAllFriends();
				//onlineFriends = ((Friends) m).getOnlineFriends();
				

				friendsLineList.clear();
				for(Friend f: friends){
					friendsLineList.add(new FriendListLine(201, 50, f));
				}
			} else if(m instanceof FriendRequests){

				friendRequestsList.clear();
				for(DbMessage msg: ((FriendRequests)m).getMessages().values()){
					friendRequestsList.add(new FriendRequestLine(201, 50, msg));
				}
			}
		}

		Font font = new Font(Font.SERIF, Font.PLAIN, 18);
		Font font2 = new Font(Font.SERIF, Font.PLAIN, 24);
		user.setDepress(true);
		lobby.setDepress(false);

		g.setColor(new Color(230, 230, 230));
		g.fillRect(0, 0, width, height);
		
		// ------------ List of friends ------------ //
		int x = 800 - 20;
		int y = 450 + 30 + yOffset;
		
		for(FriendListLine line: friendsLineList){
			line.draw(g, x, y);
			y += 60;
			
			canScrollUp = (y > height - 20);
			canScrollDown = (yOffset < 0);
		}
		if(loops > loopsToDelay){
			loops = 0;
			if(canScrollDown && scrollDown){
				yOffset += 5;
			} else if(canScrollUp && scrollUp){
				yOffset -= 5;
			} else if(canScrollDown && scrollDownOnce){
				yOffset += 20;
				scrollDownOnce = false;
			} else if(canScrollUp && scrollUpOnce){
				yOffset -= 20;
				scrollUpOnce = false;
			}
		}
		loops++;
		
		if(canScrollDown){
			g.setColor(Color.RED);
		} else {
			g.setColor(new Color(100, 0, 0));
		}
		g.fillPolygon(tri1);
		
		if(canScrollUp){
			g.setColor(Color.RED);
		} else {
			g.setColor(new Color(100, 0, 0));
		}
		g.fillPolygon(tri2);

		
		// ------------ List of friend requests ------------ //
		
		x = 550;
		y = 450 + 30 + requests_yOffset;
		
		
		for(FriendRequestLine line: friendRequestsList){
			line.draw(g, x, y);
			y += 60;
			
			requests_canScrollUp = (y > height - 60 - 20);
			requests_canScrollDown = (requests_yOffset < 0);
		}
		
		if(requests_loops > requests_loopsToDelay){
			requests_loops = 0;
			if(requests_canScrollDown && requests_scrollDown){
				requests_yOffset += 5;
			} else if(requests_canScrollUp && requests_scrollUp){
				requests_yOffset -= 5;
			} else if(requests_canScrollDown && requests_scrollDownOnce){
				requests_yOffset += 20;
				requests_scrollDownOnce = false;
			} else if(requests_canScrollUp && requests_scrollUpOnce){
				requests_yOffset -= 20;
				requests_scrollUpOnce = false;
			}
		}
		requests_loops++;
		
		
		if(requests_canScrollDown){
			g.setColor(Color.RED);
		} else {
			g.setColor(new Color(100, 0, 0));
		}
		g.fillPolygon(requests_tri1);
		
		if(requests_canScrollUp){
			g.setColor(Color.RED);
		} else {
			g.setColor(new Color(100, 0, 0));
		}
		g.fillPolygon(requests_tri2);

		
		
		g.setColor(background);
		g.fillRect(0, 0, width, 450);
		g.fillRect(0, 450, 300, height - 450);
		g.fillRect(300, height - 20, width - 300, 20);
		g.fillRect(width - 20, 450, 20, height - 450);
		
		g.setColor(Color.BLACK);
		g.fillRect(800 - 20, 450, 201, 20);
		
		CatanGraphics.drawCenteredText(g, Color.WHITE, new Font(Font.SERIF, Font.PLAIN, 12),
				"Friends", 800 - 20, 450, 200, 20);
		
		g.setColor(Color.BLACK);
		g.fillRect(550, 450, 201, 20);
		CatanGraphics.drawCenteredText(g, Color.WHITE, new Font(Font.SERIF, Font.PLAIN, 12),
				"Friend requests", 550, 450, 200, 20);
		
		
		g.setColor(Color.BLACK);
		g.fillRect(320, 450, 201, 20);
		CatanGraphics.drawCenteredText(g, Color.WHITE, new Font(Font.SERIF, Font.PLAIN, 12),
				"Friend chat", 320, 450, 200, 20);
		
		
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, titleBarHeight, width, 51);
		lobby.draw(g);
		user.draw(g);
		exit.draw(g);
		
		
		CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font, "Username: " + Catan.myName, 25, 100, 50);
		//CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font, "Join Date: " + "January 10, 2013", 50, 150, 50);
		
		CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font2, "Change Password", 25, 150, 50);
		
		CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font, "Current Password", 25, 200, 50);
		CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font, "New Password", 25, 300, 50);
		CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font, "Repeat New Password", 25, 400, 50);
		currentPassword.paint(g);
		newPassword1.paint(g);
		newPassword2.paint(g);
		changePassword.draw(g);
		
		friendChatSubmit.draw(g);
		friendChatHistory.paint(g);
		friendChatNewMessage.paint(g);
		
		g.setColor(new Color(230, 230, 230));
		//g.setColor(Color.GREEN);
		g.fillRect(550, 650, 201, 70);
		CatanGraphics.drawCenteredText(g, Color.BLACK, new Font(Font.SERIF, Font.PLAIN, 12),
				"Request a friend", 550, 650, 200, 20);
		friendRequest.paint(g);
		friendRequestSubmit.draw(g);
		
		// display stats
		if(stats != null){
			int offset = 0;
			int statsY = 100;
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Most cards traded: " + stats.mostCardsTraded,
					325, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Most cities built: " + stats.mostCitiesBuilt,
					325, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Most development cards played: " + stats.mostDevCardsPlayed,
					325, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Most roads built: " + stats.mostRoadsBuilt,
					325, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Most settlements built: " + stats.mostSettlementsBuilt,
					325, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Most victory points: " + stats.mostVictoryPoints,
					325, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Beggar total: " + stats.totalBeggar,
					325, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total cards traded: " + stats.totalCardsTraded,
					325, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total cities built: " + stats.totalCitiesBuilt,
					325, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total settlements built: " + stats.totalSettlementsBuilt,
					325, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total roads built: " + stats.totalRoadsBuilt,
					325, statsY + offset, 30);
			offset += 30;
			
			offset = 0;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total development cards played: " + stats.totalDevCardsPlayed,
					675, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total games played: " + stats.totalGamesPlayed,
					675, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total game turns: " + stats.totalGameTurns,
					675, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total largest army holder: " + stats.totalLargestArmyHolder,
					675, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total longest road holder: " + stats.totalLongestRoadHolder,
					675, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total most aggressive trader: " + stats.totalMostAggresiveTrader,
					675, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total most robbed: " + stats.totalMostRobbed,
					675, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total sevens rolled: " + stats.totalSevensRolled,
					675, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total time played: " + (int)(stats.totalTimePlayed / 60000) + " minutes",
					675, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total utter loser: " + stats.totalUtterLoser,
					675, statsY + offset, 30);
			offset += 30;
			
			CatanGraphics.drawCenteredHeightText(g, Color.WHITE, font,
					"Total wins: " + stats.totalWins,
					675, statsY + offset, 30);
			offset += 30;
		}
	}
	
	/**
	 * 
	 */
	private void sendMessage(){
		String str = friendChatNewMessage.getText();
		if(!"".equals(str)){
			friendChatNewMessage.setText("");
			
			Catan.chatBot.sendMessage(chatGroupID, str);
			//System.out.println("chatGroupID: " + chatGroupID);
		}
	}
	
	/**
	 * 
	 */
	private void requestFriend(){
		String str = "";

		str = friendRequest.getText();

		if (!str.equals("")) {
			friendRequest.setText("");
			
			Catan.client.requestFriend(str, "NEW REQUEST");
			//System.out.println("FRIEND REQUESTED");
		}	
	}
}
