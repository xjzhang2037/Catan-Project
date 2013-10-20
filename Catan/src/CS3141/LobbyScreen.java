package CS3141;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.JOptionPane;
import CS3141.Button.ButtonImage;
import util.messages.GameInstances;
import util.messages.Message;
import util.*;

/**
 * 
 * @author csdoig
 *
 */
public class LobbyScreen {
	/**
	 * 
	 * @author csdoig
	 *
	 */
	public class GameLine {
		private int _width;
		private int _height;
		
		boolean inProgress;
		
		Button join;
		Button observe;
		
		Font font = new Font(Font.SERIF, Font.PLAIN, 18);
		
		GameInstancesWrapper instance;
		
		/**
		 * 
		 * @param width
		 * @param height
		 * @param inProgress
		 * @param instance
		 */
		public GameLine(int width, int height, boolean inProgress, GameInstancesWrapper instance){
			this._width = width;
			this._height = height;
			
			this.inProgress = inProgress;
			this.instance = instance;
			
			join = new Button("join", new Color(200, 200, 200), 0, 0, 100, 50 - 1, false);
			join.setImage(ButtonImage.JOIN);
			observe = new Button("observe", new Color(200, 200, 200), 0, 0, 100, 50 - 1, false);
			observe.setImage(ButtonImage.OBSERVE);
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
			
			String str = "|"+instance.name+"| ";
			int numPlayers = 0;
			
			for(String s: instance.users){
				str += s + ", ";
				numPlayers++;
			}
			str = str.substring(0, str.length() - 2);
			
			//str += " (" + numPlayers+") " + instance.getChatRoomNumber() ;
			str += " (" + numPlayers+")";
			CatanGraphics.drawCenteredHeightText(g, Color.BLACK, font, str, x + 10, y, 50);
			
			//if(!inProgress){
				join.draw(g);
			//}
			//observe.draw(g);
		}
	}

	private int width;
	private int height;
	
	private int updateCount = 1000;
	
	
	
	boolean openOrInProgress = true; // true for open, false for inProgress
	
	//scrolling ability
	int scrollx1 = 100;
	int scrolly1 = 250;
	int scrollx2 = 100;
	int scrolly2 = 50;
	
	boolean canScrollUp = false;
	boolean canScrollDown = false;
	boolean inProgressCanScrollUp = false;
	boolean inProgressCanScrollDown = false;
	
	private int openYOffset = 0;
	private int inProgressYOffset = 0;
	private boolean scrollUp = false;
	private boolean openScrollDown = false;
	private boolean inProgressScrollUp = false;
	private boolean inProgressScrollDown = false;
	
	public boolean scrollUpOnce = false;
	public boolean openScrollDownOnce = false;
	public boolean inProgressScrollUpOnce = false;
	public boolean inProgressScrollDownOnce = false;
	
	private int loops;
	private final int loopsToDelay = 1;
	
	Button lobby;
	Button user;
	Button exit;
	
	Button start;
	Button open;
	Button inProgress;
	
	private Color background = new Color(100, 100, 100);
	
	ArrayList<GameLine> openGameList = new ArrayList<GameLine>();
	ArrayList<GameLine> inProgressGameList = new ArrayList<GameLine>();
	
	
	int[] tri1X = {0, 10, 20};
	int[] tri1Y = {20, 0, 20};
	Polygon tri1 = new Polygon(tri1X, tri1Y, 3);


	int[] tri2X = {0, 10, 20};
	int[] tri2Y = {0, 20, 0};
	Polygon tri2 = new Polygon(tri2X, tri2Y, 3);
	
	int titleBarHeight = Catan.getFrames()[0].getInsets().top;
	int left = Catan.getFrames()[0].getInsets().left;
	int right = Catan.getFrames()[0].getInsets().right;
	
	int gameid = 0;
	java.util.List<String> players;
	
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public LobbyScreen(int width, int height){
		this.width = width;
		this.height = height;
		
		tri1.translate(width - scrollx2 - 30, scrolly1 + 10);
		tri2.translate(width - scrollx2 - 30, height - scrolly2 - 30);
		

		
		lobby = new Button("Lobby", Color.LIGHT_GRAY, left, titleBarHeight, 100, 50, false);
		user = new Button("User", Color.LIGHT_GRAY, left + 100, titleBarHeight, 100, 50, false);
		exit = new Button("Exit", Color.LIGHT_GRAY, width - right - 100, titleBarHeight, 100, 50, false);
		exit.setImage(ButtonImage.EXIT);
		
		start = new Button("Start New Game", Color.LIGHT_GRAY, 100, 125, 200, 50, false);
		start.setImage(ButtonImage.NEW_GAME);
		
		open = new Button("Open Games", Color.LIGHT_GRAY, 100, scrolly1 - 50 - 1, 175, 50, true);
		open.setDepress(true);
		inProgress = new Button("In-Progress", Color.LIGHT_GRAY, 275, scrolly1 - 50 - 1, 175, 50, true);
	}
	
	/**
	 * 
	 * @param me
	 * @return
	 */
	public boolean isPressed(MouseEvent me){

		if(canScrollDown){
			if(tri1.contains(me.getX(), me.getY())){
				scrollUp = false;
				openScrollDown = true;
			}
		}
		if(canScrollUp){
			if(tri2.contains(me.getX(), me.getY())){
				scrollUp = true;
				openScrollDown = false;
			}
		}
		
		if(inProgressCanScrollDown){
			if(tri1.contains(me.getX(), me.getY())){
				inProgressScrollUp = false;
				inProgressScrollDown = true;
			}
		}
		if(inProgressCanScrollUp){
			if(tri2.contains(me.getX(), me.getY())){
				inProgressScrollUp = true;
				inProgressScrollDown = false;
			}
		}

		
		if(lobby.isDepressed(me)){
			
		}
		if(user.isDepressed(me)){
					
		}
		if(exit.isDepressed(me)){
			
		}
		if(start.isDepressed(me)){
			
		}
		if(open.isDepressed(me)){
			
		}
		if(inProgress.isDepressed(me)){
			
		}
		
		for(GameLine line: openGameList){
			//if(!line.inProgress && line.join.isDepressed(me)){
			if(line.join.isDepressed(me)){
				
			}
			if(line.observe.isDepressed(me)){
				
			}
		}
		for(GameLine line: inProgressGameList){
			//if(!line.inProgress && line.join.isDepressed(me)){
			if(line.join.isDepressed(me)){
				
			}
			if(line.observe.isDepressed(me)){
				
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param me
	 * @return
	 */
	public boolean isClicked(MouseEvent me){
		if(lobby.isClicked(me)){
			
		}
		if(user.isClicked(me)){
			Catan.screen = Catan.USER_SCREEN;
			Catan.newToScreen = true;
		}
		if(exit.isClicked(me)){
			if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(Catan.getFrames()[0], "Are you sure you want to quit?", "Quitting", JOptionPane.YES_NO_OPTION)){
				System.exit(0);
			}
		}
		if(start.isClicked(me)){
			
			Catan.screen = Catan.NEW_GAME_SCREEN;
			Catan.newToScreen = true;
			
			//Catan.vClient = new CatanBoard_vClient(Catan.client.getUserID(), Catan.client);
		}
		if(open.isClicked(me)){
			openOrInProgress = true;
			inProgress.setDepress(false);
		}
		if(inProgress.isClicked(me)){
			openOrInProgress = false;
			open.setDepress(false);
		}
		
		if(openOrInProgress){
			for(GameLine line: openGameList){
				//if(!line.inProgress && line.join.isClicked(me)){
				if(line.join.isClicked(me)){
					if(gameid == 0){
					
						gameid = line.instance.gameid;
						players = line.instance.getUsers();
						Catan.client.joinGame(line.instance.gameid);
						//if(chatRoomNumer != 0){
						Catan.chatBot.joinGroup(line.instance.getChatRoomNumber());
						Catan.chatRoomNumber = line.instance.getChatRoomNumber();
						//}
					}
				}
				if(line.observe.isClicked(me)){
					
				}
			}
		} else {
			for(GameLine line: inProgressGameList){
				if(line.join.isClicked(me)){
					if(gameid == 0){
					
						gameid = line.instance.gameid;
						players = line.instance.getUsers();
						Catan.client.joinGame(line.instance.gameid);
						//if(chatRoomNumer != 0){
						Catan.chatBot.joinGroup(line.instance.getChatRoomNumber());
						Catan.chatRoomNumber = line.instance.getChatRoomNumber();
						
						if(players.size() == 4){
							Catan.screen = Catan.GAME_SCREEN;
							Catan.newToScreen = true;
						}
						//}
					}
				}
				if(line.observe.isClicked(me)){
					
				}
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
		openScrollDown = false;
	
		lobby.released();
		user.released();
		exit.released();
		start.released();
		open.released();
		inProgress.released();
		//enterGame.released();
		
		for(GameLine line: openGameList){
			line.join.released();
			line.observe.released();
		}
		for(GameLine line: inProgressGameList){
			line.join.released();
			line.observe.released();
		}

		return false;
	}
	
	/**
	 * 
	 * @param g
	 * @param msgs
	 */
	public void draw(Graphics g, ArrayList<Message> msgs){
		user.setDepress(false);
		lobby.setDepress(true);
		
		try{
			if(updateCount > 200){
				Catan.client.requestGameInstances();
				updateCount = 0;
			}
			updateCount++;
		} catch(NullPointerException e){}		
		
		// populate game list
		for(Message m: msgs){
			if(m instanceof GameInstances){
				inProgressGameList.clear();
				openGameList.clear();
				java.util.List<GameInstancesWrapper> list = ((GameInstances)m).getGames();
				
				for(GameInstancesWrapper wrapper: list){
					GameLine gl = new GameLine(width - scrollx1 - scrollx2 - 50, 50, wrapper.inProgress, wrapper);
					
					//System.out.println(wrapper.inProgress);
					
					if(wrapper.inProgress){
						inProgressGameList.add(gl);
					} else {
						openGameList.add(gl);
					}
				}
				m = null;
			}
		}

		int x = 0;
		int y = 0;
		
		if(openOrInProgress){
			// draw list of open games
			x = scrollx1 + 10;
			y = scrolly1 + 10 + openYOffset;
			
			for(GameLine line: openGameList){
				//line.join.updatePosition(x + line._width - scrollx1 - 100, y);
				line.join.updatePosition(x + line._width - scrollx1, y);
				line.observe.updatePosition(x + line._width - scrollx1, y);

				line.draw(g, x, y);
				y += 60;
				
				
				canScrollUp = (y > height - 50);
				canScrollDown = (openYOffset < 0);
			}
			if(loops > loopsToDelay){
				loops = 0;
				if(canScrollDown && openScrollDown){
					openYOffset += 5;
				} else if(canScrollUp && scrollUp){
					openYOffset -= 5;
				} else if(canScrollDown && openScrollDownOnce){
					openYOffset += 20;
					openScrollDownOnce = false;
				} else if(canScrollUp && scrollUpOnce){
					openYOffset -= 20;
					scrollUpOnce = false;
				}
				
			}
			
			loops++;
		} else{
			// draw list of in-progress games
			x = scrollx1 + 10;
			y = scrolly1 + 10 + inProgressYOffset;
			for(GameLine line: inProgressGameList){
				//line.join.updatePosition(x + line._width - scrollx1 - 100, y);
				line.join.updatePosition(x + line._width - scrollx1, y);
				line.observe.updatePosition(x + line._width - scrollx1, y);

				line.draw(g, x, y);
				y += 60;
								
				inProgressCanScrollUp = (y > height - 50);
				inProgressCanScrollDown = (inProgressYOffset < 0);
			}
			if(loops > loopsToDelay){
				loops = 0;
				if(inProgressCanScrollDown && inProgressScrollDown){
					inProgressYOffset += 5;
				} else if(inProgressCanScrollUp && inProgressScrollUp){
					inProgressYOffset -= 5;
				} else if(inProgressCanScrollDown && inProgressScrollDownOnce){
					inProgressYOffset += 20;
					inProgressScrollDownOnce = false;
				} else if(inProgressCanScrollUp && inProgressScrollUpOnce){
					inProgressYOffset -= 20;
					inProgressScrollUpOnce = false;
				}
			}
			loops++;
		}

		g.setColor(background);
		//left
		g.fillRect(0, 0, scrollx1, height);
		
		//right
		g.fillRect(width - scrollx2, 0, scrollx2, height);
		
		//top
		g.fillRect(0, 0, width, scrolly1);
		
		//bottom
		g.fillRect(0, height - scrolly2, width, scrolly2);
		
		g.setColor(Color.BLACK);
		g.drawRect(scrollx1, scrolly1, width - scrollx1 - scrollx1, height - scrolly1 - scrolly2);
		
		// scroll triangles
		if(openOrInProgress){
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
		} else {
			if(inProgressCanScrollDown){
				g.setColor(Color.RED);
			} else {
				g.setColor(new Color(100, 0, 0));
			}
			g.fillPolygon(tri1);
			
			if(inProgressCanScrollUp){
				g.setColor(Color.RED);
			} else {
				g.setColor(new Color(100, 0, 0));
			}
			g.fillPolygon(tri2);
		}
				
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, titleBarHeight, width, 51);
		lobby.draw(g);
		user.draw(g);
		exit.draw(g);
		
		start.draw(g);
		open.draw(g);
		inProgress.draw(g);
	}
}
