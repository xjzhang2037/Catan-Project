package CS3141;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import CS3141.Button.ButtonImage;
import util.Phases;
import chatnet.ChatClient;
import net.GameClient;
import util.player.Player;
import util.GameInstancesWrapper;
import util.GameToken;
import util.TokenCode;
import util.dataWrappers.EndOfGameStats_Game;
import util.messages.CreateGame;
import util.messages.GameInstances;
import util.messages.GameState;
import util.messages.HeartBeat;
import util.messages.MessageTypes;
import util.messages.RequestAuthentication;
import util.messages.ResultOfRequest;
import util.messages.UserInfo;
import catanNodes.AttributeNode;
import catanNodes.CatanBoard_vClient;
import catanNodes.CatanBoard_vServer;
import catanNodes.JunctionNode;
import catanNodes.PathEdge;
import chatutil.messages.ChatMsg;

/**
 * @author Chris Doig Adapted from GUI used for Othello
 */
public class Catan extends JFrame implements MouseListener, MouseWheelListener,
		MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 3565410620119384337L;
	
	// double buffering
	private Image _offScreen;
	private Graphics _buffer;

	// window size
	static int _width = 1024; // 1024
	static int _height = 740; // 740

	// begin screen objects
	private Button login;
	private Button register;
	//private Button guest;

	// login screen objects
	private TextBox username;
	private TextBox password;

	private Button loginCancel;
	private Button loginSubmit;

	// guest screen objects
	private Button guestCancel;
	private Button guestSubmit;

	// register screen objects
	private TextBox registerUsername;
	private TextBox registerPassword;
	private TextBox registerPasswordConfirm;

//	private Button checkUsername;
	private Button registerUser;
	private Button registerCancel;

	// lobby screen objects
	LobbyScreen lobbyScreen;

	UserScreen userScreen;

	// in-game objects
	private Button trade;
	private Button build;
	private Button card;
	private Button roll;
	private Button leave;
	private Button endPhase;
	private Button chat;

	static BuildMenu buildMenu;
	static TradeMenu tradeMenu;
	static TradeOffer tradeOffer;


	TextBox chatInput;
	TextBox chatHistory;

	static boolean robberSelected = false;

	// GUI and mouse translations
	float zoom = 9;
	int xTranslate = 0;
	int yTranslate = 0;
	static int oldx = 0;
	static int oldy = 0;

	static int beginDragX = 0;
	static int beginDragY = 0;

	static final double COS_PI_6 = Math.cos(Math.PI / 6);

	Font chatFont = new Font(Font.SERIF, Font.PLAIN, 12);
	Font chatFont2 = new Font(Font.SERIF, Font.PLAIN, 18);
	Font fancy = new Font(Font.SERIF, Font.ITALIC, 80);

	// screens
	static boolean newToScreen = true;
	static int SPLASH_SCREEN = 0;
	static int BEGIN_SCREEN = 1;
	static int LOGIN_SCREEN = 2;
	static int GUEST_SCREEN = 3;
	static int REGISTER_SCREEN = 4;
	static int LOBBY_SCREEN = 5;
	static int GAME_SCREEN = 6;
	static int USER_SCREEN = 7;
	static int NEW_GAME_SCREEN = 8;
	static int IP_SCREEN = 9;
	static int screen = SPLASH_SCREEN;

	TextBox IPAddress;
	Button IPSubmit;
	
	TextBox gameName;
	Button gameNameSubmit;
	Button gameNameCancel;
	
	long currentTime = 0;

	static CatanBoard_vServer server;
	static CatanBoard_vClient vClient;
	static ChatClient chatBot;
	static GameClient client;
	
	private int DEVNONE = 0;
	private int DEVBUILDROAD = 1;
	private int DEVKNIGHT = 2;
	private int DEVMONOPOLY = 3;
	private int DEVVICTORY = 4;
	private int DEVPLENTY = 5;
	private int devPhase = DEVNONE;
	
	private boolean endOfGame = false;
	private boolean endOfGameShowStats = false;
	private String winner = null;
	EndOfGameStats_Game stats = null;
	
	static String pass = "";

	ArrayList<util.messages.Message> gameMsgs = null;

	private final boolean disableChat = false;

	int titleBarHeight;
	int left;
	int right;
	int bottom;


	private static int totalLoops = 0;
	private static int[] currentDiceForOthers = {-1, -1};
	private static int diceDisplay1 = 1;
	private static int diceDisplay2 = 1;
	private static boolean beginDiceRoll = false;
	private static boolean continueDisplay = false;
	private static boolean permitRollDisplayInit = false;
	private long diceTime = 0;

	private static int userid = 0;
	static String myName = "";
	static int gameid = 0;

	static String host; // "141.219.153.160";

	int updateCount = 1000;
	int updateCountLoops = 0;

	
	static int chatRoomNumber = 0;
	
	//private boolean haxOnlyOnce = true;
	
	
	private static int toBuild = 0;
	private static JunctionNode node1;
	private static PathEdge edge1;
	
	private static PathEdge edge2;
	
	private static AttributeNode aNode1;
	private static AttributeNode aNode2;
	
	private int[] lastVertexCoords = { 0, 0 };
	private int[] lastRoadCoords = { 0, 0 };

	public static String clientSystemMessage = "";

	public static Color myOwnColor = Color.WHITE;
	public static int myOwnPlayerID = 0;
	
	private boolean hitFourPlayers = false;

	int[][] vertexState = { { 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0 } };

	int[][] vertexColor = { { 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0 } };

	boolean[][] roadState = {
			{ false, false, false, false, false, false },
			{ false, false, false, false },
			{ false, false, false, false, false, false, false, false },
			{ false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false,
					false },
			{ false, false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false,
					false }, { false, false, false, false, false },
			{ false, false, false, false, false, false, false, false },
			{ false, false, false, false },
			{ false, false, false, false, false, false } };

	int[][] roadColor = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 } };

	/**
	 * Execute everything
	 * @param args
	 */
	public static void main(String args[]) {
		if (args.length != 0) {
			host = args[0];
		} else {
			host = "localhost";
		}
		//host = "141.219.153.160";
		
		CatanGraphics.init();
		new Catan();
	}

	/**
	 * instantiate the jframe and perform a bunch of setup
	 */
	public Catan() {
		super("Settlers of Catan"); // Window title
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				if (!disableChat && chatBot != null){
					chatBot.closeConnection();
				}
				System.exit(0);
			}
		});

		// setup the window
		setSize(_width, _height);
		setVisible(true);
		setResizable(false);
		addMouseListener(this);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);

		setFocusTraversalKeysEnabled(false); // to detect tabs

		// double buffering
		_offScreen = createImage(_width, _height);
		_buffer = _offScreen.getGraphics();

		// custom window icon
		ImageIcon img;
		try{
			img = new javax.swing.ImageIcon(getClass().
					getResource("/Images/alpha_small.png"));
		} catch(NullPointerException npe){
			img = new ImageIcon("Images/alpha_small.png");
		}
		
		
		this.setIconImage(img.getImage());

		// in-game
		chatInput = new TextBox("", _width - 375, _height - 200, 250, 30,
				true, false, _buffer, false);
		chatHistory = new TextBox("", _width - 375, 330, 350,
				_height - 330 - 220, false, true, _buffer, false);

		// login
		username = new TextBox("", (int) (_width / 2) - 125, _height - 300,
				250, 30, true, false, _buffer, false);
		password = new TextBox("", (int) (_width / 2) - 125, _height - 200,
				250, 30, true, false, _buffer, true);

		// register
		registerUsername = new TextBox("", (int) (_width / 2) - 125,
				_height - 400, 250, 30, true, false, _buffer, false);
		registerPassword = new TextBox("", (int) (_width / 2) - 125,
				_height - 300, 250, 30, true, false, _buffer, true);
		registerPasswordConfirm = new TextBox("", (int) (_width / 2) - 125,
				_height - 200, 250, 30, true, false, _buffer, true);


		this.requestFocusInWindow();

		titleBarHeight = getInsets().top;
		left = getInsets().left;
		right = getInsets().right;
		bottom = getInsets().bottom;
		
		Button.init();

		// game screen
		trade = new Button("Trade", new Color(200, 200, 200), left, _height
				- 150 - bottom, 150 - 1, 50, false);
		trade.setImage(ButtonImage.TRADE);
		
		build = new Button("Build", new Color(200, 200, 200), left + 150,
				_height - 150 - bottom, 150 - 1, 50, false);
		build.setImage(ButtonImage.BUILD);
		
		card = new Button("Buy Card", new Color(200, 200, 200), left, _height
				- 100 - bottom, 150 - 1, 50, false);
		card.setImage(ButtonImage.USE_CARD);
		
		roll = new Button("Roll", new Color(200, 200, 200), left + 150, _height
				- 100 - bottom, 150 - 1, 50, false);
		roll.setImage(ButtonImage.ROLL);
		
		leave = new Button("Leave Game", new Color(200, 200, 200), left,
				_height - 50 - bottom, 150 - 1, 50, false);
		leave.setImage(ButtonImage.END_GAME);
		
		endPhase = new Button("End Phase", new Color(200, 200, 200), left + 150,
				_height - 50 - bottom, 150 - 1, 50, false);
		//endPhase.setImage(ButtonImage.);

		chat = new Button("Submit", new Font(Font.SERIF, Font.PLAIN, 18),
				new Color(200, 200, 200), _width - 100, _height - 200, 80, 30,
				false);
		chat.setImage(ButtonImage.SEND);

		
		// IP Address screen
		IPAddress = new TextBox("", (int) (_width / 2) - 100,
				_height - 300, 200, 30, true, false, _buffer, false);
	
		IPSubmit = new Button("Proceed", new Color(200, 200, 200),
				(int) (_width / 2) - 100, _height - 250, 200, 50, false);
		IPSubmit.setImage(ButtonImage.CONFIRM_ADDRESS);
		
		
		// game creation screen
		gameName = new TextBox("", (int) (_width / 2) - 100,
				_height - 300, 200, 30, true, false, _buffer, false);
		gameNameSubmit = new Button("Create", new Color(200, 200, 200),
				(int) (_width / 2) - 100, _height - 250, 200, 50, false);
		gameNameSubmit.setImage(ButtonImage.CREATE);
		
		gameNameCancel = new Button("Create", new Color(200, 200, 200),
				(int) (_width / 2) - 50, _height - 190, 200, 50, false);
		gameNameCancel.setImage(ButtonImage.BACK);		
		
		// begin screen
		login = new Button("Login", new Color(200, 200, 200),
				(int) (_width / 2) - 100, 300, 200, 50, false);
		login.setImage(ButtonImage.LOGIN2);
		
		register = new Button("Register", new Color(200, 200, 200),
				(int) (_width / 2) - 100, 400, 200, 50, false);
		register.setImage(ButtonImage.REGISTER);
		//guest = new Button("Play as Guest", new Color(200, 200, 200),
		//		(int) (_width / 2) - 100, 500, 200, 50, false);

		// login screen
		loginSubmit = new Button("Login", new Color(200, 200, 200),
				(int) (_width / 2) - 125, _height - 150, 100, 50, false);
		loginSubmit.setImage(ButtonImage.LOGIN);
		loginCancel = new Button("Back", new Color(200, 200, 200),
				(int) (_width / 2) + 25, _height - 150, 100, 50, false);
		loginCancel.setImage(ButtonImage.BACK);

		// guest screen
		guestSubmit = new Button("Proceed", new Color(200, 200, 200),
				(int) (_width / 2) - 125, _height - 250, 100, 50, false);
		guestCancel = new Button("Back", new Color(200, 200, 200),
				(int) (_width / 2) + 25, _height - 250, 100, 50, false);

		// register screen
		registerUser = new Button("Proceed", new Color(200, 200, 200),
				(int) (_width / 2) - 125, _height - 150, 100, 50, false);
		registerUser.setImage(ButtonImage.CONTINUE);
		registerCancel = new Button("Back", new Color(200, 200, 200),
				(int) (_width / 2) + 25, _height - 150, 100, 50, false);
		registerCancel.setImage(ButtonImage.BACK);

		buildMenu = new BuildMenu(300 + left, _height - 300 - bottom + 30,
				_width - 410 - 300 - left + 1, 150 - 30, Color.WHITE);
		buildMenu.setVisible(false);

		tradeMenu = new TradeMenu(300 + left, _height - 430 - bottom,
				_width - 410 - 300 - left + 1, 280);
		tradeMenu.setVisible(false);

		Integer[] offer = { 1, 0, 0, 0, 0 };
		Integer[] receive = { 0, 0, 1, 0, 0 };
		tradeOffer = new TradeOffer(null, 0,
				titleBarHeight + 000, _width - 410, 120, offer, receive);
		//tradeOffer.setVisible(true);

		lobbyScreen = new LobbyScreen(_width, _height);

		userScreen = new UserScreen(_width, _height, _buffer);
	}

	/**
	 * 
	 */
	public void mouseEntered(MouseEvent me) {
	}

	/**
	 * 
	 */
	public void mouseExited(MouseEvent me) {
	}

	/**
	 * 
	 */
	public void mousePressed(MouseEvent me) {
		this.requestFocusInWindow();

		if (screen == IP_SCREEN) {
			if(IPAddress.isDepressed(me)){
				
			}
			if(IPSubmit.isDepressed(me)){
				
			}
		} else if (screen == BEGIN_SCREEN) {
			if (login.isDepressed(me)) {
				System.out.println("login - press");
			}
			if (register.isDepressed(me)) {
				System.out.println("register - press");
			}
		} else if (screen == LOGIN_SCREEN) {
			if (username.isDepressed(me)) {
				System.out.println("username - press");
			}
			if (password.isDepressed(me)) {
				System.out.println("password - press");
			}
			if (loginCancel.isDepressed(me)) {
				System.out.println("loginCancel - press");
			}
			if (loginSubmit.isDepressed(me)) {
				System.out.println("loginSubmit - press");
			}
		} else if (screen == GUEST_SCREEN) {
			if (username.isDepressed(me)) {
				System.out.println("username - press");
			}
			if (guestCancel.isDepressed(me)) {
				System.out.println("guestCancel - press");
			}
			if (guestSubmit.isDepressed(me)) {
				System.out.println("guestSubmit - press");
			}
		} else if (screen == REGISTER_SCREEN) {
			if (registerUsername.isDepressed(me)) {
				System.out.println("registerUsername - press");
			}
			if (registerPassword.isDepressed(me)) {
				System.out.println("registerPassword - press");
			}
			if (registerPasswordConfirm.isDepressed(me)) {
				System.out.println("registerPasswordConfirm - press");
			}
			if (registerUser.isDepressed(me)) {
				System.out.println("registerUser - press");
			}
			if (registerCancel.isDepressed(me)) {
				System.out.println("registerCancel - press");
			}
		} else if (screen == LOBBY_SCREEN) {

			lobbyScreen.isPressed(me);
		} else if (screen == USER_SCREEN) {

			userScreen.isPressed(me);
		} else if (screen == NEW_GAME_SCREEN) {
			if(gameName.isDepressed(me)){
				
			}
			if(gameNameSubmit.isDepressed(me)){
				
			}
			if(gameNameCancel.isDepressed(me)){
				
			}
		} else if (screen == GAME_SCREEN) {
			oldx = me.getX();
			oldy = me.getY();

			beginDragX = me.getX();
			beginDragY = me.getY();

			if (me.getButton() == MouseEvent.BUTTON1) { // left-click
				if (trade.isDepressed(me)) {
					System.out.println("trade - press");
				}
				if (build.isDepressed(me)) {
					System.out.println("build - press");
				}
				if (card.isDepressed(me)) {
					System.out.println("card - press");
				}
				if (roll.isDepressed(me)) {
					System.out.println("roll - press");
				}
				if (leave.isDepressed(me)) {
					System.out.println("leave - press");
				}
				if (endPhase.isDepressed(me)) {
					System.out.println("endTurn - press");
				}
				if (chat.isDepressed(me)) {
					System.out.println("chat - press");
				}
				if (chatHistory.isDepressed(me)) {
					System.out.println("history - press");
				}
				if (chatInput.isDepressed(me)) {
					System.out.println("input - press");
				}
				if (tradeMenu.isDepressed(me)) {
					System.out.println("tradeMenu - press");
				}
				if (tradeOffer.isDepressed(me)) {
					System.out.println("tradeOffer - press");
				}
			}
		}
	}

	/**
	 * 
	 */
	public void mouseReleased(MouseEvent me) {
		if(screen == IP_SCREEN) {
			IPAddress.released();
			IPSubmit.released();
		} else if (screen == BEGIN_SCREEN) {
			login.released();
			register.released();
			//guest.released();
		} else if (screen == LOGIN_SCREEN) {
			username.released();
			password.released();
			loginCancel.released();
			loginSubmit.released();
		} else if (screen == GUEST_SCREEN) {
			username.released();
			guestCancel.released();
			guestSubmit.released();
		} else if (screen == REGISTER_SCREEN) {
			//checkUsername.released();
			registerUser.released();
			registerCancel.released();
		} else if (screen == LOBBY_SCREEN) {
			lobbyScreen.isReleased(me);
		} else if (screen == USER_SCREEN) {
			userScreen.isReleased(me);
		} else if (screen == NEW_GAME_SCREEN) {
			gameName.released();
			gameNameSubmit.released();
			gameNameCancel.released();
		} else if (screen == GAME_SCREEN) {
			trade.released();
			build.released();
			card.released();
			roll.released();
			leave.released();
			endPhase.released();
			chat.released();

			chatHistory.released();
			chatInput.released();

			tradeMenu.released();
			tradeOffer.released();
		}
	}

	/**
	 * 
	 */
	public void mouseClicked(MouseEvent me) {
		if (screen == SPLASH_SCREEN) {
			if (me.getButton() == MouseEvent.BUTTON1) {
				screen = IP_SCREEN; // move to next screen
				if(IPAddress != null)
					IPAddress.requestFocus();
			}
		} else if (screen == IP_SCREEN) {
			if(IPAddress != null && IPAddress.isClicked(me, _buffer)){
				
			}
			if(IPSubmit.isClicked(me)){
				setIP();
			}
		} else if (screen == BEGIN_SCREEN) {
			if (login.isClicked(me)) {
				System.out.println("login - click");
				screen = LOGIN_SCREEN;
				newToScreen = true;
				username.requestFocus();
			}
			if (register.isClicked(me)) {
				System.out.println("register - click");
				screen = REGISTER_SCREEN;
				newToScreen = true;
				registerUsername.requestFocus();
			}

		} else if (screen == LOGIN_SCREEN) {
			if (username.isClicked(me, _buffer)) {
				System.out.println("username - click");
			}
			if (password.isClicked(me, _buffer)) {
				System.out.println("password - click");
			}
			if (loginCancel.isClicked(me)) {
				System.out.println("loginCancel - click");
				screen = BEGIN_SCREEN;
				newToScreen = true;

				username.setText("");
				password.setText("");
			}
			if (loginSubmit.isClicked(me)) {
				System.out.println("loginSubmit - click");

				authenticate(username.getText(), password.getText());

			}
		} else if (screen == GUEST_SCREEN) {
			if (username.isClicked(me, _buffer)) {
				System.out.println("username - click");
			}
			if (guestCancel.isClicked(me)) {
				System.out.println("guestCancel - click");
				screen = BEGIN_SCREEN;
				newToScreen = true;

				username.setText("");
			}
			if (guestSubmit.isClicked(me)) {
				System.out.println("guestSubmit - click");

				if (!"".equals(username.getText())) {
					screen = LOBBY_SCREEN;
					newToScreen = true;
					username.setText("");
				} else {
					JOptionPane.showMessageDialog(this,
							"Your nickname cannot be blank", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		} else if (screen == REGISTER_SCREEN) {
			if (registerUsername.isClicked(me, _buffer)) {
				System.out.println("registerUsername - click");
			}
			if (registerPassword.isClicked(me, _buffer)) {
				System.out.println("registerPassword - click");
			}
			if (registerPasswordConfirm.isClicked(me, _buffer)) {
				System.out.println("registerPasswordConfirm - click");
			}

			if (registerUser.isClicked(me)) {
				System.out.println("registerUser - click");

				System.out.println(client);

				if (!registerPassword.getText().equals(
						registerPasswordConfirm.getText())) {
					JOptionPane.showMessageDialog(this,
							"Passwords do not match");
				}

				client.addUser(registerUsername.getText(),
						registerPassword.getText());

				screen = LOGIN_SCREEN;
				newToScreen = true;
				username.requestFocus();
			}
			if (registerCancel.isClicked(me)) {
				System.out.println("registerCancel - click");

				registerUsername.setText("");
				registerPassword.setText("");
				registerPasswordConfirm.setText("");

				screen = BEGIN_SCREEN;
				newToScreen = true;
			}
		} else if (screen == LOBBY_SCREEN) {

			lobbyScreen.isClicked(me);

			// screen = GAME_SCREEN; // just cut to game for now
		} else if (screen == USER_SCREEN) {

			userScreen.isClicked(me, _buffer);
		} else if (screen == NEW_GAME_SCREEN) {
			if(gameName.isClicked(me, _buffer)){
				
			}
			if(gameNameSubmit.isClicked(me)){
				if(!"".equals(gameName.getText())){
					createNewGame();
				}
			}
			if(gameNameCancel.isClicked(me)){
				screen = LOBBY_SCREEN;
				gameName.setText("");
				newToScreen = true;
			}
		} else if (screen == GAME_SCREEN) {

			// move robber or monopoly
			int robberIndex = 0;
			if (me.getButton() == MouseEvent.BUTTON1) { // left-click
				
				if(vClient.phase == Phases.THIEF_PLACEMENT ||
						devPhase == DEVKNIGHT || devPhase == DEVMONOPOLY ||
						devPhase == DEVPLENTY){
					
					for (AttributeNode node : vClient.tileList()) {
		
						int robberX = 0;
						int robberY = 0;
		
						switch (robberIndex) {
						case 0:
							robberX = 0;
							robberY = 0;
							break;
						case 1:
							robberX = 1;
							robberY = 0;
							break;
						case 2:
							robberX = 2;
							robberY = 0;
							break;
						case 3:
							robberX = 3;
							robberY = 1;
							break;
						case 4:
							robberX = 4;
							robberY = 2;
							break;
						case 5:
							robberX = 3;
							robberY = 3;
							break;
						case 6:
							robberX = 2;
							robberY = 4;
							break;
						case 7:
							robberX = 1;
							robberY = 4;
							break;
						case 8:
							robberX = 0;
							robberY = 4;
							break;
						case 9:
							robberX = 0;
							robberY = 3;
							break;
						case 10:
							robberX = 0;
							robberY = 2;
							break;
						case 11:
							robberX = 0;
							robberY = 1;
							break;
						case 12:
							robberX = 1;
							robberY = 1;
							break;
						case 13:
							robberX = 2;
							robberY = 1;
							break;
						case 14:
							robberX = 3;
							robberY = 2;
							break;
						case 15:
							robberX = 2;
							robberY = 3;
							break;
						case 16:
							robberX = 1;
							robberY = 3;
							break;
						case 17:
							robberX = 1;
							robberY = 2;
							break;
						case 18:
							robberX = 2;
							robberY = 2;
							break;
						default:
							break;
						}
						robberIndex++;
		
						if(devPhase != DEVMONOPOLY && devPhase != DEVPLENTY){
							if (robberSelected) {
								if (CatanGraphics.robberClicked(me, robberX, robberY)) {
									if(vClient.phase == Phases.THIEF_PLACEMENT){
										vClient.send_thiefPlacement(node);
									} else if(devPhase == DEVKNIGHT) {
										vClient.send_dev_UseKnight(TokenCode.DEV_KNIGHT, node);
										devPhase = DEVNONE;
									}
									robberSelected = false;
								}
							} else {
								if (node.hasTheif()) {
									if (CatanGraphics.robberClicked(me, robberX, robberY)) {
										robberSelected = true;
									}
								}
							}
						} else if(devPhase == DEVMONOPOLY) {
							if (CatanGraphics.robberClicked(me, robberX, robberY)) {
								vClient.send_dev_UseMonopoly(TokenCode.DEV_MONOPOLY, node.getResourceNumber());
								devPhase = DEVNONE;
							}
						} else if(devPhase == DEVPLENTY) {
							if (CatanGraphics.robberClicked(me, robberX, robberY)) {
								if(aNode1 == null){
									aNode1 = node;
								} else {
									aNode2 = node;
									
									vClient.send_dev_UsePlenty(TokenCode.DEV_PLENTY, aNode1.getResourceNumber(), aNode2.getResourceNumber());
									devPhase = DEVNONE;
									
									aNode1 = null;
									aNode2 = null;
									System.out.println("PLENTY CARD DONE");
								}
							}
						}
					}
				}
				if(vClient.phase == Phases.THIEF_STEAL){
					JunctionNode node = mouseOverVertex(me);
					vClient.send_thiefSteal(node);
				}
				if(vClient.phase == Phases.DEV_STEAL){
					JunctionNode node = mouseOverVertex(me);
					vClient.send_thiefSteal(node);
				}
			}

			tradeMenu.isClicked(me);

			if (me.getButton() == MouseEvent.BUTTON1) { // left-click
				for(int devIndex = 0; devIndex < 5; devIndex++){
					if(me.getX() > devIndex * 50 + 580 + 20 && me.getX() < devIndex * 50 + 580 + 60 &&
							me.getY() > _height - 100 && me.getY() < _height - 100 + 60){
									
						switch(devIndex){
						case 0:
							devPhase = DEVKNIGHT;
							break;
						case 1:
							devPhase = DEVBUILDROAD;
							buildMenu.setVisible(true);
							buildMenu.setBuildingChoice(BuildMenu.BUILDING_ROAD);
							break;
						case 2:
							devPhase = DEVMONOPOLY;
							break;
						case 3:
							devPhase = DEVPLENTY;
							break;
						case 4:
							devPhase = DEVVICTORY;
							vClient.send_dev_UseVictory(TokenCode.DEV_VICTORY);
							devPhase = DEVNONE;
							break;
						}
					}
				}
				
				System.out.println(devPhase);
				
				tradeOffer.isClicked(me);

				boolean buildMade = false;

				// settlements/citys
				if (buildMenu.buildingChoice() == BuildMenu.BUILDING_SETTLEMENT) {
					JunctionNode node = mouseOverVertex(me);
					if (node != null) {
						buildMade = true;

						if (vClient.phase == Phases.SETUP && vClient.isMyTurn()) {
							toBuild = 2;
							buildMenu.setBuildingChoice(BuildMenu.BUILDING_ROAD);
							node1 = node;							
						} else if(vClient.phase == Phases.BUILD) {
							// node.setOwner(myOwnPlayerID);
							System.out.println("I AM BUILDING A SETTLEMENT");
							vClient.send_buildSettlement(node);
							//vClient.playSound(SoundClip.SETTLEMENT);
						}
					}
				} else if (buildMenu.buildingChoice() == BuildMenu.BUILDING_CITY) {
					JunctionNode node = mouseOverVertex(me);
					if (node != null && vClient.phase == Phases.BUILD) {
						buildMade = true;
						// node.setOwner(myOwnPlayerID);
						System.out.println("I AM BUILDING A CITY");
						vClient.send_buildCity(node);
						//vClient.playSound(SoundClip.SETTLEMENT);
					}
				} else if (buildMenu.buildingChoice() == BuildMenu.BUILDING_ROAD) {

					CatanGraphics.clickRoad(me);
					PathEdge edge = mouseOverRoad(me);
					if (edge != null) {
						buildMade = true;

						if (vClient.phase == Phases.SETUP) {
							toBuild = 0;
							buildMenu.setBuildingChoice(BuildMenu.BUILDING_NONE);
							buildMenu.setVisible(false);
							//setupPhase = false;
							edge1 = edge;
							System.out.println("I AM DOING INITIAL SETUP");
							
							edge1.setOwner(vClient.getPlayerNum(vClient.userID));
							node1.setOwner(vClient.getPlayerNum(vClient.userID));
							vClient.send_InitialPlacement(node1, edge1);
							node1.setOwner(-10);
							edge1.setOwner(-10);
							node1 = null;
							edge1 = null;
							resetVertexHovers();
							resetEdgeHovers();
							//vClient.playSound(SoundClip.ROADBUILDING);
						} else if(devPhase == DEVBUILDROAD && vClient.isMyTurn()){
							if(edge1 == null){
								edge1 = edge;
							} else {
								edge2 = edge;
								
								vClient.send_dev_UseBuildRoads(TokenCode.DEV_BUILDROAD, edge1, edge2);
								resetEdgeHovers();
								
								edge1 = null;
								edge2 = null;
								
								devPhase = DEVNONE;
							}
						} else if(vClient.phase == Phases.BUILD) {
							// edge.setOwner(myOwnPlayerID);
							System.out.println("I AM BUILDING A ROAD");
							vClient.send_buildRoad(edge);
							resetVertexHovers();
							resetEdgeHovers();
							//vClient.playSound(SoundClip.ROADBUILDING);
						}
					}
				}
				buildMenu.isClicked(me, !buildMade, vClient);

				if (trade.isClicked(me)) {
					System.out.println("trade - click");
					tradeMenu.setVisible(!tradeMenu.isVisible());
					buildMenu.setVisible(false);
				}
				if (build.isClicked(me)) {
					System.out.println("build - click");

					if (vClient.phase == Phases.BUILD) {
						buildMenu.setVisible(!buildMenu.isVisible());
					}
					tradeMenu.setVisible(false);
				}
				if (card.isClicked(me)) {
					System.out.println("card - click");
					tradeMenu.setVisible(false);
					buildMenu.setVisible(false);
					
					if(vClient.phase == Phases.BUILD){
						vClient.send_BuyDevCard();
					}
				}
				if (roll.isClicked(me)) {
					System.out.println("roll - click");
					tradeMenu.setVisible(false);
					buildMenu.setVisible(false);

					if (!beginDiceRoll && !continueDisplay) {
						beginDiceRolling();
					}

				}
				if (leave.isClicked(me)) {
					System.out.println("leave - click");
					if(!endOfGame){						
						tradeMenu.setVisible(false);
						buildMenu.setVisible(false);
						screen = LOBBY_SCREEN;
						newToScreen = true;
					
						System.out.println("LEAVING: " + client.leaveGame());
						lobbyScreen.gameid = 0;
						
						resetForNextGame();
					}

				}
				if(endOfGame){
					endOfGame = false;
					endOfGameShowStats = true;
				}
				
				if (endPhase.isClicked(me)) {
					System.out.println("endTurn - click");
					
					
					if(vClient.phase == Phases.ROLL){
						beginDiceRolling();
					} else if(vClient.phase == Phases.TRADE){
						vClient.curContract = null;
						vClient.send_endPhase();
					} else if(vClient.phase != Phases.THIEF_PLACEMENT &&
							vClient.phase != Phases.THIEF_STEAL &&
							vClient.phase != Phases.SETUP) {
						
						vClient.send_endPhase();
					}
					
				}
				if (chat.isClicked(me)) {
					sendMessage();
				}

				if (chatHistory.isClicked(me, _buffer)) {
					System.out.println("history - click");
				}
				if (chatInput.isClicked(me, _buffer)) {
					System.out.println("input - click");
				}
			}
		}
	}

	/**
	 * 
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (screen == GAME_SCREEN) {
			zoom = zoom - e.getWheelRotation();

			if (zoom < 5)
				zoom = 5;
			if (zoom > 20)
				zoom = 20;
		} else if(screen == USER_SCREEN){
			if(e.getX() > 780 && e.getX() < 980 && e.getY() > 470 && e.getY() < 720){
				if(userScreen.canScrollUp && e.getWheelRotation() > 0){
				//if(e.getWheelRotation() > 0){
					userScreen.scrollUpOnce = true;
					userScreen.scrollDownOnce = false;
				} else if(userScreen.canScrollDown && e.getWheelRotation() < 0){
				//} else if(e.getWheelRotation() < 0){
					userScreen.scrollUpOnce = false;
					userScreen.scrollDownOnce = true;
				} else {
					userScreen.scrollUpOnce = false;
					userScreen.scrollDownOnce = false;
				}
			} else if(e.getX() > 550 && e.getX() < 750 && e.getY() > 470 && e.getY() < 650){
				if(userScreen.requests_canScrollUp && e.getWheelRotation() > 0){
				//if(e.getWheelRotation() > 0){
					userScreen.requests_scrollUpOnce = true;
					userScreen.requests_scrollDownOnce = false;
				} else if(userScreen.requests_canScrollDown && e.getWheelRotation() < 0){
				//} else if(e.getWheelRotation() < 0){
					userScreen.requests_scrollUpOnce = false;
					userScreen.requests_scrollDownOnce = true;
				} else {
					userScreen.requests_scrollUpOnce = false;
					userScreen.requests_scrollDownOnce = false;
				}
			}
		} else if(screen == LOBBY_SCREEN){
			if(e.getX() > left + lobbyScreen.scrollx1 &&
					e.getX() < _width - right - lobbyScreen.scrollx2 &&
					e.getY() > titleBarHeight + lobbyScreen.scrolly1 &&
					e.getY() < _height - bottom - lobbyScreen.scrolly2){
				
				
				if(lobbyScreen.openOrInProgress){
					if(e.getWheelRotation() > 0){
						lobbyScreen.scrollUpOnce = true;
						lobbyScreen.openScrollDownOnce = false;
					} else if(lobbyScreen.canScrollDown && e.getWheelRotation() < 0){
						lobbyScreen.scrollUpOnce = false;
						lobbyScreen.openScrollDownOnce = true;
					} else {
						lobbyScreen.scrollUpOnce = false;
						lobbyScreen.openScrollDownOnce = false;
					}
				} else {
					if(lobbyScreen.inProgressCanScrollUp && e.getWheelRotation() > 0){
						lobbyScreen.inProgressScrollUpOnce = true;
						lobbyScreen.inProgressScrollUpOnce = false;
					} else if(lobbyScreen.inProgressCanScrollDown && e.getWheelRotation() < 0){
						lobbyScreen.inProgressScrollUpOnce = false;
						lobbyScreen.inProgressScrollUpOnce = true;
					} else {
						lobbyScreen.inProgressScrollUpOnce = false;
						lobbyScreen.inProgressScrollUpOnce = false;
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	public void mouseDragged(MouseEvent e) {
		if (screen == GAME_SCREEN) {
			if (beginDragX < _width - 410 && beginDragY < _height - 150) {
				xTranslate = xTranslate + e.getX() - oldx;
				yTranslate = yTranslate + e.getY() - oldy;
				oldx = e.getX();
				oldy = e.getY();
			}

			if (xTranslate < -200 * zoom / 5)
				xTranslate = (int) (-200 * zoom / 5);
			if (xTranslate > 500 * zoom / 5)
				xTranslate = (int) (500 * zoom / 5);

			if (yTranslate < -200 * zoom / 5)
				yTranslate = (int) (-200 * zoom / 5);
			if (yTranslate > 300 * zoom / 5)
				yTranslate = (int) (300 * zoom / 5);
		}
	}

	/**
	 * 
	 */
	public void mouseMoved(MouseEvent me) {
		if(node1 == null){
			resetVertexHovers();
		}
		if(edge1 == null){
			resetEdgeHovers();
		}
		if (screen == GAME_SCREEN && vClient.isMyTurn() &&
				(vClient.phase == Phases.BUILD || vClient.phase == Phases.SETUP)) {
			if (buildMenu.buildingChoice() == BuildMenu.BUILDING_SETTLEMENT) {
				if (mouseOverVertex(me) != null) {
					int[] coords = CatanGraphics.clickVertex(me);
					vertexState[coords[0]][coords[1]] = 1;
					vertexColor[coords[0]][coords[1]] = myOwnPlayerID;

					lastVertexCoords = coords;
				} else {
					vertexState[lastVertexCoords[0]][lastVertexCoords[1]] = 0;
					vertexColor[lastVertexCoords[0]][lastVertexCoords[1]] = 0;
				}
			} else if (buildMenu.buildingChoice() == BuildMenu.BUILDING_CITY) {
				if (mouseOverVertex(me) != null) {
					int[] coords = CatanGraphics.clickVertex(me);
					vertexState[coords[0]][coords[1]] = 2;
					vertexColor[coords[0]][coords[1]] = myOwnPlayerID;

					lastVertexCoords = coords;
				} else {
					vertexState[lastVertexCoords[0]][lastVertexCoords[1]] = 0;
					vertexColor[lastVertexCoords[0]][lastVertexCoords[1]] = 0;
				}
			} else if (buildMenu.buildingChoice() == BuildMenu.BUILDING_ROAD) {
				if (mouseOverRoad(me) != null) {
					int[] coords = CatanGraphics.clickRoad(me);

					roadState[coords[0]][coords[1]] = true;
					roadColor[coords[0]][coords[1]] = myOwnPlayerID;

					lastRoadCoords = coords;
				} else if(devPhase != DEVBUILDROAD) {
					roadState[lastRoadCoords[0]][lastRoadCoords[1]] = false;
					roadColor[lastRoadCoords[0]][lastRoadCoords[1]] = 0;
				}
			}
		}
	}

	/**
	 * 
	 */
	public void keyTyped(KeyEvent e) {
		if (screen == IP_SCREEN) {
			IPAddress.isKeyTyped(e);
		} else if (screen == LOGIN_SCREEN) {
			username.isKeyTyped(e);
			password.isKeyTyped(e);
		} else if (screen == GUEST_SCREEN) {
			username.isKeyTyped(e);
		} else if (screen == REGISTER_SCREEN) {
			registerUsername.isKeyTyped(e);
			registerPassword.isKeyTyped(e);
			registerPasswordConfirm.isKeyTyped(e);
		} else if (screen == USER_SCREEN) {
			userScreen.isKeyTyped(e);
		} else if (screen == NEW_GAME_SCREEN) {	
			gameName.isKeyTyped(e);
		} else if (screen == GAME_SCREEN) {
			chatInput.isKeyTyped(e);
		}
	}

	/**
	 * 
	 */
	public void keyPressed(KeyEvent e) {
		if (screen == IP_SCREEN) {
			if(IPAddress.isKeyPressed(e)){
				setIP();
			}
		} else if (screen == LOGIN_SCREEN) {
			if (username.isKeyPressed(e, password, password)) {
				System.out.println("username - key press");
				authenticate(username.getText(), password.getText());
			}
			if (password.isKeyPressed(e, username, username)) {
				System.out.println("password - key press");
				authenticate(username.getText(), password.getText());
			}
		} else if (screen == GUEST_SCREEN) {
			if (username.isKeyPressed(e)) {
				System.out.println("username - key press");
			}
		} else if (screen == REGISTER_SCREEN) {
			if (registerUsername.isKeyPressed(e, registerPasswordConfirm,
					registerPassword)) {
				System.out.println("registerUsername - key press");
			}
			if (registerPassword.isKeyPressed(e, registerUsername,
					registerPasswordConfirm)) {
				System.out.println("registerPassword - key press");
			}
			if (registerPasswordConfirm.isKeyPressed(e, registerPassword,
					registerUsername)) {
				System.out.println("registerPasswordConfirm - key press");

				if (!registerPassword.getText().equals(
						registerPasswordConfirm.getText())) {
					JOptionPane.showMessageDialog(this,
							"Passwords do not match");
				}

				client.addUser(registerUsername.getText(),
						registerPassword.getText());
			}
		} else if (screen == USER_SCREEN) {
			userScreen.isKeyPressed(e);
		} else if (screen == NEW_GAME_SCREEN) {	
			if(gameName.isKeyPressed(e)){
				createNewGame();
			}
		} else if (screen == GAME_SCREEN) {
			if (e.getKeyCode() == KeyEvent.VK_HOME) {
				zoom = 9;
				xTranslate = 0;
				yTranslate = 0;
				oldx = 0;
				oldy = 0;
			}

			if (chatInput.isKeyPressed(e)) {
				sendMessage();
			}
		}
	}

	/**
	 * 
	 */
	public void keyReleased(KeyEvent e) {
		IPAddress.isKeyReleased(e);
		
		gameName.isKeyReleased(e);
		
		username.isKeyReleased(e);
		password.isKeyReleased(e);

		registerUsername.isKeyReleased(e);
		registerPassword.isKeyReleased(e);
		registerPasswordConfirm.isKeyReleased(e);

		userScreen.isKeyReleased(e);
	}

	/**
	 * 
	 */
	public void paint(Graphics g) {
		
		_width = getWidth();
		_height = getHeight();
		_offScreen = createImage(_width, _height);
		_buffer = _offScreen.getGraphics();

		// clear the offscreen buffer
		_buffer.clearRect(0, 0, _width, _height);

		gameMsgs = new ArrayList<util.messages.Message>();

		// System.out.println(updateCountDisplay++);

		try {
			util.messages.Message temp;
			do {
				temp = client.receiveMessage();
				if (temp != null)
					gameMsgs.add(temp);

				if (temp != null && !(temp instanceof HeartBeat)) {
					System.out.println("Message: " + temp);
				}

				if(temp instanceof UserInfo){
					userScreen.stats = ((UserInfo)temp).userinfo;
				}
				
				if (temp instanceof ResultOfRequest) {
					System.out.println("------------");
					System.out.println(((ResultOfRequest) temp).mType);

					if (((ResultOfRequest) temp).mType
							.equals(MessageTypes.JoinGame)) {
						if (((ResultOfRequest) temp).isRequestAccepted()) {
							gameid = lobbyScreen.gameid;
							System.out.println(gameid);

							//client.requestUserInfo();
							/*
							 * System.out.println(vClient); screen =
							 * GAME_SCREEN; newToScreen = true;
							 */
						}
					}
				}
				
				if(temp instanceof CreateGame){
					gameid = ((CreateGame)temp).gameID;
					//chatRoomNumber = ((CreateGame)temp).chatGroupID;
					//chatBot.joinGroup(chatRoomNumber);
					//System.out.println("CREATE CHAT # " + chatRoomNumber);
				}

				if (temp instanceof GameInstances) {

					for (GameInstancesWrapper giw :
						((GameInstances) temp).getGames()) {
						
						if (giw.gameid == gameid && gameid != 0) {
							lobbyScreen.players = giw.getUsers();
							
							if(chatRoomNumber == 0){
								chatRoomNumber = giw.chatRoomNumber;
								chatBot.joinGroup(chatRoomNumber);
								System.out.println("CHAT ROOM NUMBER ON SET: " + chatRoomNumber);
							}
						}
					}
				}

				if (temp instanceof GameState) {
					vClient.recvMsgfromServer((GameState) temp);

					if(((GameToken)( (GameState) temp).getObj()).getKey() == TokenCode.SERVER_ENDGAME){
						endOfGame = true;
						//client.requestUserInfo();
						stats = (EndOfGameStats_Game)((GameToken)( (GameState) temp).getObj()).getValue();
						
						if(stats != null){
							winner = vClient.getUserName(stats.winner);
						}
					}
					screen = GAME_SCREEN;
					newToScreen = true;
				}

				if (temp instanceof RequestAuthentication) {
					userid = ((RequestAuthentication) temp).getUserID();
					System.out.println(userid);
					if (userid > 0) {

						screen = LOBBY_SCREEN;
						newToScreen = true;

						myName = username.getText();

						username.setText("");
						password.setText("");

						System.out.println("[GUI] Joining Server");
						client.joinServer();

						System.out.println("[GUI] Constructing ChatClient");
						chatBot = new ChatClient(host, starter.ServerStarter.CHAT_PORT);

						chatBot.setUserInfo(client.getUserID(), myName);
						chatBot.joinServer();
						System.out.println(chatBot.receiveMessage());

						System.out.println("[GUI] Constructing vClient");
						vClient = new CatanBoard_vClient(client.getUserID(),
								client);
					} else {
						JOptionPane.showMessageDialog(this,
								"Username / password combination is invalid");
					}
				}

			} while (temp != null);
		} catch (NullPointerException e) {
		}

		if (screen == SPLASH_SCREEN) { // splash screen
			if (newToScreen) {
				currentTime = System.currentTimeMillis();
				newToScreen = false;
			}

			_buffer.setColor(new Color(100, 100, 100));
			_buffer.fillRect(0, 0, _width, _height);
			CatanGraphics.drawGradient(_buffer, 0, 0);

			CatanGraphics.drawCenteredText(_buffer, new Color(255, 216, 0),
					fancy, "Settlers of Catan", 0, 0, _width, 300);

			if (System.currentTimeMillis() - currentTime > 5000) {
				screen = IP_SCREEN;
				newToScreen = true;
				if(IPAddress != null)
					IPAddress.requestFocus();
			}
		} else if (screen == IP_SCREEN) {
			if (newToScreen) {
				newToScreen = false;
			}
			
			_buffer.setColor(new Color(100, 100, 100));
			_buffer.fillRect(0, 0, _width, _height);
			CatanGraphics.drawGradient(_buffer, 0, 0);

			CatanGraphics.drawLogo(_buffer, 50, _height - 350);
			
			CatanGraphics.drawCenteredText(_buffer, new Color(255, 216, 0),
					fancy, "Settlers of Catan", 0, 0, _width, 300);
			
			CatanGraphics.drawCenteredText(_buffer, new Color(255, 216, 0),
					chatFont2, "Enter IP Address of Server", 0, _height - 350, _width, 50);
			
			if(IPAddress != null)
				IPAddress.paint(_buffer);
			if(IPSubmit != null)
				IPSubmit.draw(_buffer);
			
		} else if (screen == BEGIN_SCREEN) { // login/register
			if (newToScreen) {
				newToScreen = false;
			}

			_buffer.setColor(new Color(100, 100, 100));
			_buffer.fillRect(0, 0, _width, _height);
			CatanGraphics.drawGradient(_buffer, 0, 0);
			
			CatanGraphics.drawLogo(_buffer, 50, _height - 350);

			CatanGraphics.drawCenteredText(_buffer, new Color(255, 216, 0),
					fancy, "Settlers of Catan", 0, 0, _width, 300);

			login.draw(_buffer);
			register.draw(_buffer);

			if(client.s != null){

			} else {
				CatanGraphics.drawCenteredText(_buffer, new Color(255, 30, 0),
						chatFont2, "Server is Unavailable", 0, _height - 200, _width, 100);

				client = new GameClient(host, starter.ServerStarter.GAME_PORT);
			}
		} else if (screen == LOGIN_SCREEN) { // login
			if (newToScreen) {
				newToScreen = false;
			}

			_buffer.setColor(new Color(100, 100, 100));
			_buffer.fillRect(0, 0, _width, _height);
			CatanGraphics.drawGradient(_buffer, 0, 0);
			
			CatanGraphics.drawLogo(_buffer, 50, _height - 350);

			CatanGraphics.drawCenteredText(_buffer, new Color(255, 216, 0),
					fancy, "Login", 0, 0, _width, 300);

			CatanGraphics.drawCenteredText(_buffer, new Color(255, 255, 255),
					chatFont, "Username", _width / 2 - 100, _height - 375, 200,
					100);

			CatanGraphics.drawCenteredText(_buffer, new Color(255, 255, 255),
					chatFont, "Password", _width / 2 - 100, _height - 275, 200,
					100);

			username.paint(_buffer);
			password.paint(_buffer);
			loginCancel.draw(_buffer);
			loginSubmit.draw(_buffer);
		} else if (screen == GUEST_SCREEN) {
			if (newToScreen) {
				newToScreen = false;
			}

			_buffer.setColor(new Color(100, 100, 100));
			_buffer.fillRect(0, 0, _width, _height);
			CatanGraphics.drawGradient(_buffer, 0, 0);

			CatanGraphics.drawCenteredText(_buffer, new Color(255, 216, 0),
					fancy, "Play as Guest", 0, 0, _width, 300);

			CatanGraphics.drawCenteredText(_buffer, new Color(255, 255, 255),
					chatFont, "Nickname", _width / 2 - 100, _height - 375, 200,
					100);
			username.paint(_buffer);

			guestCancel.draw(_buffer);
			guestSubmit.draw(_buffer);
		} else if (screen == REGISTER_SCREEN) {
			if (newToScreen) {
				newToScreen = false;
			}

			_buffer.setColor(new Color(100, 100, 100));
			_buffer.fillRect(0, 0, _width, _height);
			CatanGraphics.drawGradient(_buffer, 0, 0);
			
			CatanGraphics.drawLogo(_buffer, 50, _height - 350);

			CatanGraphics.drawCenteredText(_buffer, new Color(255, 216, 0),
					fancy, "Register", 0, 0, _width, 300);

			CatanGraphics.drawCenteredText(_buffer, new Color(255, 255, 255),
					chatFont, "Username", _width / 2 - 100, _height - 475, 200,
					100);

			CatanGraphics.drawCenteredText(_buffer, new Color(255, 255, 255),
					chatFont, "Password", _width / 2 - 100, _height - 375, 200,
					100);

			CatanGraphics.drawCenteredText(_buffer, new Color(255, 255, 255),
					chatFont, "Confirm Password", _width / 2 - 100,
					_height - 275, 200, 100);

			registerUsername.paint(_buffer);
			registerPassword.paint(_buffer);
			registerPasswordConfirm.paint(_buffer);

			//checkUsername.draw(_buffer);
			registerUser.draw(_buffer);
			registerCancel.draw(_buffer);

		} else if (screen == LOBBY_SCREEN) {
			if (newToScreen) {
				newToScreen = false;
			}

			lobbyScreen.draw(_buffer, gameMsgs);

		} else if (screen == USER_SCREEN) {
			if (newToScreen) {
				newToScreen = false;
			}

			userScreen.draw(_buffer, gameMsgs);
		} else if (screen == NEW_GAME_SCREEN) {
			if (newToScreen) {
				newToScreen = false;
				gameName.requestFocus();
			}
			
			_buffer.setColor(new Color(100, 100, 100));
			_buffer.fillRect(0, 0, _width, _height);
			
			CatanGraphics.drawCenteredText(_buffer, new Color(255, 216, 0),
					fancy, "Create New Game", 0, 0, _width, 300);
			
			CatanGraphics.drawCenteredText(_buffer, new Color(255, 216, 0),
					chatFont2, "Name of New Game", 0, _height - 350, _width, 50);
			
			gameName.paint(_buffer);
			gameNameSubmit.draw(_buffer);
			gameNameCancel.draw(_buffer);
			
			
			//GameWrapper temp = new GameWrapper();
			//temp.setGameName("Elmo's World");
		} else if (screen == GAME_SCREEN) {
			if (newToScreen) {
				newToScreen = false;
			}
			lobbyScreen.gameid = client.getGameID();
			
			
			//if(haxOnlyOnce){
			//	System.out.println("Chat Room #: " + chatRoomNumber);
			//	
			//	haxOnlyOnce = false;
			//	vClient.send_ineedhaxors();
			//	System.out.println("[GUI] Sent hax request");
			//}
			
			buildMenu.setColor(myOwnColor);
			
			
			
			if(vClient.phase == Phases.ROLL){
				buildMenu.setBuildingChoice(BuildMenu.BUILDING_NONE);
				buildMenu.setVisible(false);
			}
			
			if(vClient.phase == Phases.BUILD){
				//endPhase.updateLabel("End Turn");
				endPhase.setImage(ButtonImage.END_TURN);
			} else {
				//endPhase.updateLabel("End Phase");
				endPhase.setImage(ButtonImage.END_PHASE);
			}
			
			
			if(vClient.curContract != null && !vClient.curContract.getOfferer().equals(vClient.thisClient)){
				tradeOffer.setVisible(true);
				tradeOffer.offer = vClient.curContract.getOffer();
				tradeOffer.receive = vClient.curContract.getAccept();
				tradeOffer.offerer = vClient.curContract.getOfferer();
			}
			
			if(vClient.phase != Phases.BUILD && vClient.phase != Phases.SETUP && vClient.phase != Phases.BUILDROAD){
				buildMenu.setVisible(false);
			}
			if(vClient.phase != Phases.TRADE){
				tradeMenu.setVisible(false);
			}
			

			try {
				if (updateCount > 200 && updateCountLoops < 5) {
					client.requestGameInstances();
					updateCount = 0;
					updateCountLoops++;
				}
				updateCount++;
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			
			//for(Player p: vClient.getPlayers()){
			//	lobbyScreen.players.add(p.getName());
			//}

			//myOwnPlayerID = lobbyScreen.players.indexOf(myName);
			myOwnPlayerID = vClient.thisClient.pInfo().get_pNum();
			
			
			//System.out.println("myID " + myOwnPlayerID);

			int[] otherIndices = new int[3];
			if (myOwnPlayerID == 0) {
				myOwnColor = Color.WHITE;

				otherIndices[0] = 1;
				otherIndices[1] = 2;
				otherIndices[2] = 3;
			}
			if (myOwnPlayerID == 1) {
				myOwnColor = Color.RED;

				otherIndices[0] = 0;
				otherIndices[1] = 2;
				otherIndices[2] = 3;
			}
			if (myOwnPlayerID == 2) {
				myOwnColor = Color.ORANGE;

				otherIndices[0] = 0;
				otherIndices[1] = 1;
				otherIndices[2] = 3;
			}
			if (myOwnPlayerID == 3) {
				myOwnColor = Color.BLUE;

				otherIndices[0] = 0;
				otherIndices[1] = 1;
				otherIndices[2] = 2;
			}


			int hexSize = (int) (120 * zoom / 10); // 120
			int settleSize = (int) (8 * zoom / 10); // 8
			int citySize = (int) (10 * zoom / 10); // 10
			int roadSize = (int) (5 * zoom / 10); // 5

			int originX = (int) (50 + hexSize * COS_PI_6) + xTranslate; // 150
			int originY = 70 + yTranslate; // 50

			CatanGraphics.set(hexSize, settleSize, citySize, roadSize, originX,
					originY, myOwnPlayerID);

			// water
			_buffer.setColor(new Color(0, 127, 255));
			_buffer.fillRect(0, 0, _width, _height);
			CatanGraphics.drawWater(_buffer, 0, -300);

			// display map
			CatanGraphics.drawMap(_buffer, vClient);

			// Color teamColor = Color.WHITE;

			if (vClient.phase == Phases.SETUP) {
				buildMenu.setVisible(true);
				if (toBuild == 0) {
					toBuild = 1;
					buildMenu.setBuildingChoice(BuildMenu.BUILDING_SETTLEMENT);
				}
			}
			
			// display settlements and cities hovers
			for (int y = 0; y < 12; y++) {
				for (int x = 0; x < CatanGraphics.vertexOffsetsX[y].length; x++) {

					if (myOwnPlayerID == 0) {
						myOwnColor = Color.WHITE;
					} else if (myOwnPlayerID == 1) {
						myOwnColor = Color.RED;
					} else if (myOwnPlayerID == 2) {
						myOwnColor = Color.ORANGE;
					} else if (myOwnPlayerID == 3) {
						myOwnColor = Color.BLUE;
					}

					if (vertexState[y][x] == 1) {
						CatanGraphics.drawSettlement(_buffer, myOwnColor, x, y);
					} else if (vertexState[y][x] == 2) {
						CatanGraphics.drawCity(_buffer, myOwnColor, x, y);
					}
				}
			}

			// display settlements and cities
			for (AttributeNode node : vClient.tileList()) {
				for (JunctionNode jNode : node.jNodes()) {
					if(jNode.hasCity() && jNode.getOwnerID() != -10){
						Color c = Color.WHITE;
						switch (jNode.getOwnerID()) {
						case 0:
							c = Color.WHITE;
							break;
						case 1:
							c = Color.RED;
							break;
						case 2:
							c = Color.ORANGE;
							break;
						case 3:
							c = Color.BLUE;
							break;
						default:
							c = Color.WHITE;
							break;
						}
						
						//System.out.println("[GUI] jNode owner: " + jNode.getOwnerID());
						
						
						int[] coords = vertexConverttoLocal(jNode.getjNumber());
	
						int y = coords[0];
						int x = coords[1];
	
						if (vertexState[y][x] == 0) { // don't overlap current with
													  // highlighted possibility
							if (jNode.getCityType() == 1) {
								CatanGraphics.drawSettlement(_buffer, c, x, y);
							} else if (jNode.getCityType() == 2) {
								CatanGraphics.drawCity(_buffer, c, x, y);
							}
							
						}
					}
				}
			}

			// display road hovers
			for (int y = 0; y < 11; y++) {
				for (int x = 0; x < CatanGraphics.roadOffsetsX[y].length; x++) {
					if (myOwnPlayerID == 0) {
						myOwnColor = Color.WHITE;
					} else if (myOwnPlayerID == 1) {
						myOwnColor = Color.RED;
					} else if (myOwnPlayerID == 2) {
						myOwnColor = Color.ORANGE;
					} else if (myOwnPlayerID == 3) {
						myOwnColor = Color.BLUE;
					}

					if (roadState[y][x]) {
						CatanGraphics.drawRoad(_buffer, myOwnColor, x, y);
					}
				}
			}

			// display roads
			for (AttributeNode node : vClient.tileList()) {
				for (JunctionNode jNode : node.jNodes()) {
					for (PathEdge edge : jNode.PathEdges()) {
						if (edge.hasRoad() && edge.getOwner() != -10) {
							Color c = Color.WHITE;
							
							
							switch (edge.getOwner()) {
							case 0:
								c = Color.WHITE;
								break;
							case 1:
								c = Color.RED;
								break;
							case 2:
								c = Color.ORANGE;
								break;
							case 3:
								c = Color.BLUE;
								break;
							default:
								c = Color.WHITE;
								break;
							}
							
							
							int y1 = vertexConverttoLocal(edge.getjNodeA()
									.getjNumber())[0];
							int x1 = vertexConverttoLocal(edge.getjNodeA()
									.getjNumber())[1];

							int y2 = vertexConverttoLocal(edge.getjNodeB()
									.getjNumber())[0];
							int x2 = vertexConverttoLocal(edge.getjNodeB()
									.getjNumber())[1];

							CatanGraphics.drawRoad(_buffer, c, x1, y1, x2, y2);
						}
					}
				}
			}

			_buffer.setColor(new Color(200, 200, 200));
			_buffer.fillRoundRect(10, 10 + titleBarHeight, 200, 40, 20, 20);
			_buffer.setColor(Color.WHITE);
			if(vClient.isMyTurn()){
				if(vClient.phase == Phases.BUILD){
					
					CatanGraphics.drawCenteredText(_buffer, Color.BLACK, CatanGraphics.buttonFont,
							"BUILD", 10, 10 + titleBarHeight, 200, 40);
				} else if(vClient.phase == Phases.ROLL){
					CatanGraphics.drawCenteredText(_buffer, Color.BLACK, CatanGraphics.buttonFont,
							"ROLL", 10, 10 + titleBarHeight, 200, 40);
				} else if(vClient.phase == Phases.SETUP){
					CatanGraphics.drawCenteredText(_buffer, Color.BLACK, CatanGraphics.buttonFont,
							"SETUP", 10, 10 + titleBarHeight, 200, 40);
				} else if(vClient.phase == Phases.THIEF_PLACEMENT){
					CatanGraphics.drawCenteredText(_buffer, Color.BLACK, CatanGraphics.buttonFont,
							"MOVE THIEF", 10, 10 + titleBarHeight, 200, 40);
				} else if(vClient.phase == Phases.THIEF_STEAL){
					CatanGraphics.drawCenteredText(_buffer, Color.BLACK, CatanGraphics.buttonFont,
							"STEAL", 10, 10 + titleBarHeight, 200, 40);
				} else if(vClient.phase == Phases.TRADE){
					CatanGraphics.drawCenteredText(_buffer, Color.BLACK, CatanGraphics.buttonFont,
							"TRADE", 10, 10 + titleBarHeight, 200, 40);
				} else if(vClient.phase == Phases.DEV_STEAL){
					CatanGraphics.drawCenteredText(_buffer, Color.BLACK, CatanGraphics.buttonFont,
							"KNIGHT", 10, 10 + titleBarHeight, 200, 40);
				} else if(vClient != null && vClient.phase != null){
					CatanGraphics.drawCenteredText(_buffer, Color.BLACK, CatanGraphics.buttonFont,
							vClient.phase.toString(), 10, 10 + titleBarHeight, 200, 40);
				}				
			} else {
				if(vClient.phase == Phases.THIEF_STEAL){// || vClient.phase == Phases.TRADE){
					

					if(vClient.phase == Phases.THIEF_STEAL){
						CatanGraphics.drawCenteredText(_buffer, Color.BLACK, CatanGraphics.buttonFont,
								"THIEF STOLE", 10, 10 + titleBarHeight, 200, 40);
					}
					//if(vClient.phase == Phases.TRADE){
					//	CatanGraphics.drawCenteredText(_buffer, Color.WHITE, CatanGraphics.buttonFont,
					//			"TRADE", 10, 10 + titleBarHeight, 200, 40);
					//}
				} else {
					CatanGraphics.drawCenteredText(_buffer, Color.BLACK, CatanGraphics.buttonFont,
							vClient.currentTurn().getName() + "'s turn", 10, 10 + titleBarHeight, 200, 40);
				}
			}
			
			CatanGraphics.drawPanel(_buffer, _width - 410, 0);
			

			_buffer.setColor(new Color(200, 200, 200));
			_buffer.fillRect(left, _height - 150 - bottom, _width - left, 150);
			_buffer.draw3DRect(300 + left, _height - 150 - bottom, _width - 300
					- left, 150, true);
			
			
			// buttons
			trade.draw(_buffer);
			build.draw(_buffer);
			card.draw(_buffer);
			roll.draw(_buffer);
			leave.draw(_buffer);
			endPhase.draw(_buffer);

			// resource cards
			_buffer.setColor(Color.BLACK);
			_buffer.drawString("Resources:", 300 + 20, _height - 150 + 30);

			CatanGraphics.drawCard(_buffer, 1, 300 + 20, _height - 100);
			CatanGraphics.drawCard(_buffer, 2, 300 + 20 + 50, _height - 100);
			CatanGraphics.drawCard(_buffer, 3, 300 + 20 + 100, _height - 100);
			CatanGraphics.drawCard(_buffer, 4, 300 + 20 + 150, _height - 100);
			CatanGraphics.drawCard(_buffer, 5, 300 + 20 + 200, _height - 100);

			_buffer.setColor(Color.BLACK);
			int[] resources = vClient.getPlayerResc();

			if (resources == null) {
				resources = new int[5];
				for (int i = 0; i < 5; i++) {
					resources[i] = 0;
				}
			}

			CatanGraphics.drawCenteredText(_buffer, Color.BLACK,
					CatanGraphics.buttonFont, ""
							+ (resources[0] - tradeMenu.give[0]), 300 + 20,
							_height - 60, 40, 60);

			CatanGraphics.drawCenteredText(_buffer, Color.BLACK,
					CatanGraphics.buttonFont, ""
							+ (resources[1] - tradeMenu.give[1]),
							300 + 20 + 50, _height - 60, 40, 60);

			CatanGraphics.drawCenteredText(_buffer, Color.BLACK,
					CatanGraphics.buttonFont, ""
							+ (resources[2] - tradeMenu.give[2]),
							300 + 20 + 100, _height - 60, 40, 60);

			CatanGraphics.drawCenteredText(_buffer, Color.BLACK,
					CatanGraphics.buttonFont, ""
							+ (resources[3] - tradeMenu.give[3]),
							300 + 20 + 150, _height - 60, 40, 60);

			CatanGraphics.drawCenteredText(_buffer, Color.BLACK,
					CatanGraphics.buttonFont, ""
							+ (resources[4] - tradeMenu.give[4]),
							300 + 20 + 200, _height - 60, 40, 60);

			// development cards
			_buffer.setColor(Color.BLACK);
			_buffer.drawString("Development:", 580 + 20,
					_height - 150 + 30);

			
			CatanGraphics.drawCard(_buffer, 7, 580 + 20, _height - 100);
			CatanGraphics.drawCard(_buffer, 6, 580 + 20 + 50, _height - 100);
			CatanGraphics.drawCard(_buffer, 10, 580 + 20 + 100, _height - 100);
			CatanGraphics.drawCard(_buffer, 12, 580 + 20 + 150, _height - 100);
			CatanGraphics.drawCard(_buffer, 11, 580 + 20 + 200, _height - 100);
			CatanGraphics.drawCenteredText(_buffer, Color.BLACK,
					CatanGraphics.buttonFont, ""
							+ vClient.thisClient.pInfo().get_DevCardArray()[0], 580 + 20,
							_height - 60, 40, 60);

			CatanGraphics.drawCenteredText(_buffer, Color.BLACK,
					CatanGraphics.buttonFont, ""
							+ vClient.thisClient.pInfo().get_DevCardArray()[1],
							580 + 20 + 50, _height - 60, 40, 60);

			CatanGraphics.drawCenteredText(_buffer, Color.BLACK,
					CatanGraphics.buttonFont, ""
							+ vClient.thisClient.pInfo().get_DevCardArray()[2],
							580 + 20 + 100, _height - 60, 40, 60);

			CatanGraphics.drawCenteredText(_buffer, Color.BLACK,
					CatanGraphics.buttonFont, ""
							+ vClient.thisClient.pInfo().get_DevCardArray()[3],
							580 + 20 + 150, _height - 60, 40, 60);

			CatanGraphics.drawCenteredText(_buffer, Color.BLACK,
					CatanGraphics.buttonFont, ""
							+ vClient.thisClient.pInfo().get_DevCardArray()[4],
							580 + 20 + 200, _height - 60, 40, 60);
			
			_buffer.setColor(Color.BLACK);
			_buffer.drawString("Special:", 300 + 20 + 550, _height - 150 + 30);
			
			if(vClient.longestRoad()){
				CatanGraphics.drawCard(_buffer, 9, 300 + 20 + 550, _height - 110);
			}
			if(vClient.largestArmy()){
				CatanGraphics.drawCard(_buffer, 8, 300 + 20 + 620, _height - 110);
			}
			
			// victory points

			_buffer.setColor(new Color(100, 100, 100));
			_buffer.fillRoundRect(10, _height - 200, 200, 40, 20, 20);
			_buffer.setColor(myOwnColor);
			
			_buffer.drawString(myName + ": " + vClient.thisClient.pInfo().getPoints()
					+ " VP", 20, _height - 200 + 30);
			

			_buffer.setColor(new Color(100, 100, 100));

			if(lobbyScreen.players.size() == 4){
				hitFourPlayers = true;
			}
				
			if(hitFourPlayers){	
				for (int playerIndex = 0; playerIndex < 3; playerIndex++) {
					Player p = vClient.getPlayers()[otherIndices[playerIndex]];
					
					Color c = Color.WHITE;
					if (otherIndices[playerIndex] == 0) {
						c = Color.WHITE;
					} else if (otherIndices[playerIndex] == 1) {
						c = Color.RED;
					} else if (otherIndices[playerIndex] == 2) {
						c = Color.ORANGE;
					} else if (otherIndices[playerIndex] == 3) {
						c = Color.BLUE;
					}
					
					int numCards = 0;
					for(int index = 0; index < 5; index++){
						numCards += p.pInfo().get_DevCardArray()[index];
					}
					CatanGraphics.drawUserBlock(_buffer, _width - 405,
							titleBarHeight + 90 * playerIndex, p.getName(),
							p.pInfo().getPoints(), numCards,
							p.pInfo().get_ArmySize(), c);
				}
			}

			// chat
			_buffer.setColor(Color.WHITE);
			_buffer.setFont(new Font(Font.SERIF, Font.PLAIN, 24));
			_buffer.drawString("Chat", _width - 375, 320);

			chat.draw(_buffer);


			if(vClient.phase == Phases.ROLL){
				continueDisplay = false;
			}
			
			if(!vClient.isMyTurn()){
				if(vClient.phase == Phases.ROLL){
					permitRollDisplayInit = true;
				} else if(vClient.phase == Phases.TRADE || vClient.phase == Phases.THIEF_PLACEMENT){
					if(permitRollDisplayInit && vClient.roll[0] != 0 && vClient.roll[1] != 0){
	
						System.out.println("BB");
						continueDisplay = true;
						permitRollDisplayInit = false;
						diceTime = System.currentTimeMillis();
					}
				}
			}
			
			Integer[] ints = { 0, 0 };
			if (beginDiceRoll) {
				if(System.currentTimeMillis() - diceTime > 250){
					diceDisplay1 = (int) (Math.random() * 6) + 1;
					diceDisplay2 = (int) (Math.random() * 6) + 1;
					diceTime = System.currentTimeMillis();
					totalLoops++;
				}

				if (totalLoops > 5) {
					beginDiceRoll = false;
					continueDisplay = true;

					ints = vClient.roll;
					System.out.println("[GUI] DICE ROLL" + ints[0] + "," + ints[1]);
					diceTime = System.currentTimeMillis();
				}

				DiceRollAnimation.draw(_buffer, 200, 300, diceDisplay1);
				DiceRollAnimation.draw(_buffer, 350, 300, diceDisplay2);
			}
			if (continueDisplay) {
				ints = vClient.roll;
				if(System.currentTimeMillis() - diceTime < 2000){
					DiceRollAnimation.draw(_buffer, 200, 300, ints[0]);
					DiceRollAnimation.draw(_buffer, 350, 300, ints[1]);
				} else {
					continueDisplay = false;
					currentDiceForOthers[0] = vClient.roll[0];
					currentDiceForOthers[1] = vClient.roll[1];
				}
			}

			// update chat messages
			if (!disableChat) {
				chatutil.messages.Message inMsg = chatBot.receiveMessage();
				ChatMsg chatMsg = null;
				
				while (inMsg != null) {
					
					if(inMsg instanceof ChatMsg){
						
						chatMsg = (ChatMsg)inMsg;
						
						if(chatMsg.message != null){
	
							int index = chatMsg.message.indexOf("~~$$~~", 1);
							String name = chatMsg.message.substring(6, index);
							String message = chatMsg.message.substring(index + 6);
	
							Date date = new Date(chatMsg.timeStamp);
							Calendar cal = GregorianCalendar.getInstance();
							cal.setTime(date);
							
							int hours = cal.get(GregorianCalendar.HOUR);
							int minutes = cal.get(GregorianCalendar.MINUTE);
							
							String mZero = "";
							if(minutes < 10){
								mZero = "0";
							}
							String hZero = "";
							if(hours == 0){
								hZero = "0";
							}
	
							chatHistory.append(hZero + hours + ":" + mZero
									+ minutes + " (" + name + ")" + "   "
									+ message + "\n");
							chatHistory.moveToBottom();
						}

					}
						
					inMsg = chatBot.receiveMessage();
				}
				
				if (!"".equals(clientSystemMessage)) {
					chatHistory.append(clientSystemMessage + "\n");
					clientSystemMessage = "";
					chatHistory.moveToBottom();
				}
			}

			chatInput.paint(_buffer);
			chatHistory.paint(_buffer);

			buildMenu.draw(_buffer, vClient);
			tradeMenu.draw(_buffer);
			tradeOffer.draw(_buffer);
			
			if(endOfGame){
				_buffer.setColor(Color.BLACK);
				CatanGraphics.drawFilter(_buffer);
				
				CatanGraphics.drawCenteredText(_buffer, new Color(255, 216, 0), fancy, winner,
						left + 100, titleBarHeight, _width - right - left - 200, _height - bottom - titleBarHeight - 200);
				CatanGraphics.drawCenteredText(_buffer, new Color(255, 216, 0), fancy, "has won the game",
						left + 100, titleBarHeight + 100, _width - right - left - 200, _height - bottom - titleBarHeight - 200);
				
			} else if(endOfGameShowStats){
				_buffer.setColor(Color.BLACK);
				
				CatanGraphics.drawFilter(_buffer);
				
				if(stats != null){
					Date d = (new Date(stats.gameDate));
					
					Calendar cal = GregorianCalendar.getInstance();
					cal.setTime(d);
					
					String month = cal.getDisplayName(GregorianCalendar.MONTH,
							Calendar.LONG, Locale.US);
					int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
					int year = cal.get(GregorianCalendar.YEAR);
					
					CatanGraphics.drawCenteredHeightText(_buffer, Color.WHITE, chatFont2,
							(month + 1) + "/" + day + "/" + year,
							left + 150, titleBarHeight + 150, 30);
					
					long minutes = (stats.gameLength) / (60 * 1000);
					long seconds = stats.gameLength % 60;
					int offset = 30;
					
					CatanGraphics.drawCenteredHeightText(_buffer, Color.WHITE, chatFont2,
							"Game Length: " + minutes + " minutes, " + seconds + " seconds",
							left + 150, titleBarHeight + 150 + offset, 30);
					offset += 30;
					
					if(stats.winner >= 0){
						CatanGraphics.drawCenteredHeightText(_buffer, Color.WHITE, chatFont2,
								vClient.getUserName(stats.winner) + " won the game",
								left + 150, titleBarHeight + 150 + offset, 30);
						offset += 45;
					}
					
					if(stats.longestRoadHolder >= 0){
						CatanGraphics.drawCenteredHeightText(_buffer, Color.WHITE, chatFont2,
								"Longest Road: " + stats.longestRoadLength + " by " + vClient.getUserName(stats.longestRoadHolder),
								left + 150, titleBarHeight + 150 + offset, 30);
						offset += 30;
					}
					
					if(stats.largestArmyHolder >= 0){
						CatanGraphics.drawCenteredHeightText(_buffer, Color.WHITE, chatFont2,
								"Largest Army: " + stats.largestArmySize + " by " + vClient.getUserName(stats.largestArmyHolder),
								left + 150, titleBarHeight + 150 + offset, 30);
						offset += 30;
					}
					
					if(stats.mostAggressiveTrader >= 0){
						CatanGraphics.drawCenteredHeightText(_buffer, Color.WHITE, chatFont2,
								"Most Aggressive Trader: " + vClient.getUserName(stats.mostAggressiveTrader),
								left + 150, titleBarHeight + 150 + offset, 30);
						offset += 30;
					}
					if(stats.beggar >= 0){
						CatanGraphics.drawCenteredHeightText(_buffer, Color.WHITE, chatFont2,
								"Beggar: " + vClient.getUserName(stats.beggar),
								left + 150, titleBarHeight + 150 + offset, 30);
						offset += 30;
					}
					
					if(stats.mostRobbed >= 0){
						CatanGraphics.drawCenteredHeightText(_buffer, Color.WHITE, chatFont2,
								"Most Robbed: " + vClient.getUserName(stats.mostRobbed),
								left + 150, titleBarHeight + 150 + offset, 30);
						offset += 30;
					}
					
					if(stats.sevensRolled >= 0){
						CatanGraphics.drawCenteredHeightText(_buffer, Color.WHITE, chatFont2,
								"Sevens Rolled: " + stats.sevensRolled,
								left + 150, titleBarHeight + 150 + offset, 30);
						offset += 30;
					}
					
					if(stats.totalGameTurns >= 0){
						CatanGraphics.drawCenteredHeightText(_buffer, Color.WHITE, chatFont2,
								"Total Turns: " + stats.totalGameTurns,
								left + 150, titleBarHeight + 150 + offset, 30);
						offset += 30;
					}
					
					if(stats.utterLoser >= 0){
						CatanGraphics.drawCenteredHeightText(_buffer, Color.WHITE, chatFont2,
								"Utter Loser: " + vClient.getUserName(stats.utterLoser),
								left + 150, titleBarHeight + 150 + offset, 30);
						offset += 30;
					}
				}
				
				leave.draw(_buffer);
			}
		}
		
		// draw to screen
		g.drawImage(_offScreen, 0, 0, this);
		repaint();
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static int[] vertexConverttoServer(int[] input) {
		int[] ret = { 0, 0 };

		ret[0] = input[0] + 1;

		if (input[0] == 0) {
			ret[1] = 4 + 2 * input[1];
		} else if (input[0] == 1) {
			ret[1] = 3 + 2 * input[1];
		} else if (input[0] == 2) {
			ret[1] = 3 + 2 * input[1];
		} else if (input[0] == 3) {
			ret[1] = 2 + 2 * input[1];
		} else if (input[0] == 4) {
			ret[1] = 2 + 2 * input[1];
		} else if (input[0] == 5) {
			ret[1] = 1 + 2 * input[1];
		} else if (input[0] == 6) {
			ret[1] = 1 + 2 * input[1];
		} else if (input[0] == 7) {
			ret[1] = 2 + 2 * input[1];
		} else if (input[0] == 8) {
			ret[1] = 2 + 2 * input[1];
		} else if (input[0] == 9) {
			ret[1] = 3 + 2 * input[1];
		} else if (input[0] == 10) {
			ret[1] = 3 + 2 * input[1];
		} else if (input[0] == 11) {
			ret[1] = 4 + 2 * input[1];
		}
		return ret;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static int[] vertexConverttoLocal(int[] input) {
		int[] ret = { 0, 0 };

		ret[0] = input[0] - 1;

		if (input[0] == 1) {
			ret[1] = (input[1] - 4) / 2;
		} else if (input[0] == 2) {
			ret[1] = (input[1] - 3) / 2;
		} else if (input[0] == 3) {
			ret[1] = (input[1] - 3) / 2;
		} else if (input[0] == 4) {
			ret[1] = (input[1] - 2) / 2;
		} else if (input[0] == 5) {
			ret[1] = (input[1] - 2) / 2;
		} else if (input[0] == 6) {
			ret[1] = (input[1] - 1) / 2;
		} else if (input[0] == 7) {
			ret[1] = (input[1] - 1) / 2;
		} else if (input[0] == 8) {
			ret[1] = (input[1] - 2) / 2;
		} else if (input[0] == 9) {
			ret[1] = (input[1] - 2) / 2;
		} else if (input[0] == 10) {
			ret[1] = (input[1] - 3) / 2;
		} else if (input[0] == 11) {
			ret[1] = (input[1] - 3) / 2;
		} else if (input[0] == 12) {
			ret[1] = (input[1] - 4) / 2;
		}
		return ret;
	}

	/**
	 * 
	 * @param edge
	 * @return
	 */
	public static int[][] edgeToVertices(int[] edge) {
		int[][] ret = { { 0, 0 }, { 0, 0 } }; // {{y1, x1}, {y2, x2}}

		if (edge[0] % 2 == 1) { // odd indexed rows
			ret[0][0] = edge[0];
			ret[0][1] = edge[1];

			ret[1][0] = edge[0] + 1;
			ret[1][1] = edge[1];
		} else if (edge[0] % 2 == 0 && edge[0] <= 5) { // upper-half even
														// indexed rows
			ret[0][0] = edge[0];
			ret[0][1] = (int) (edge[1] / 2);

			ret[1][0] = edge[0] + 1;
			ret[1][1] = (int) ((edge[1] + 1) / 2);
		} else if (edge[0] % 2 == 0 && edge[0] > 5) { // lower-half even indexed
														// rows
			ret[0][0] = edge[0];
			ret[0][1] = (int) ((edge[1] + 1) / 2);

			ret[1][0] = edge[0] + 1;
			ret[1][1] = (int) (edge[1] / 2);
		}
		return ret;
	}

	/**
	 * 
	 */
	private void sendMessage() {
		if (!disableChat) {
			String str = "";

			str = chatInput.getText();

			if (!str.equals("")) {
				chatInput.setText("");
				str = "~~$$~~" + myName + "~~$$~~" + str;

				System.out.println("CHAT ROOM NUMER ON SEND " + chatRoomNumber);
				chatBot.sendMessage(chatRoomNumber, str);
			}
		}
	}

	/**
	 * 
	 * @param me
	 * @return
	 */
	private JunctionNode mouseOverVertex(MouseEvent me) {
		int[] coords = CatanGraphics.clickVertex(me);

		if (coords[0] != -1) {
			int[] newCoords = vertexConverttoServer(coords);

			for (AttributeNode node : vClient.tileList()) {
				for (JunctionNode jNode : node.jNodes()) {
					if (jNode.getjNumber()[0] == newCoords[0]
							&& jNode.getjNumber()[1] == newCoords[1]) {

						return jNode;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param me
	 * @return
	 */
	private PathEdge mouseOverRoad(MouseEvent me) {
		int[] coords = CatanGraphics.clickRoad(me);
		if (coords[0] != -1) {

			int[][] edgeCoords = edgeToVertices(coords);
			edgeCoords[0] = vertexConverttoServer(edgeCoords[0]);
			edgeCoords[1] = vertexConverttoServer(edgeCoords[1]);


			for (AttributeNode node : vClient.tileList()) {
				for (JunctionNode jNode : node.jNodes()) {
					for (PathEdge edge : jNode.PathEdges()) {
						int[] a = edge.getjNodeA().getjNumber();
						int[] b = edge.getjNodeB().getjNumber();

						if ((a[0] == edgeCoords[0][0]
								&& a[1] == edgeCoords[0][1]
								&& b[0] == edgeCoords[1][0] && b[1] == edgeCoords[1][1])
								|| (b[0] == edgeCoords[0][0]
										&& b[1] == edgeCoords[0][1]
										&& a[0] == edgeCoords[1][0] && a[1] == edgeCoords[1][1])) {

							return edge;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param u
	 *            username
	 * @param p
	 *            password
	 */
	private void authenticate(String u, String p) {
		client.authenticateUser(u, p);
		pass = p;
	}

	/**
	 * 
	 */
	private void resetVertexHovers() {
		for(int[] i: vertexState){
			Arrays.fill(i, 0);
		}
		
		for(int[] i: vertexColor){
			Arrays.fill(i, 0);
		}
	}
	
	/**
	 * 
	 */
	private void resetEdgeHovers() {
		for(boolean[] i: roadState){
			Arrays.fill(i, false);
		}
		
		for(int[] i: roadColor){
			Arrays.fill(i, 0);
		}
	}	
	
	/**
	 * 
	 */
	private void beginDiceRolling(){
		if(vClient.phase == Phases.ROLL && vClient.isMyTurn()){
			beginDiceRoll = true;
			totalLoops = 0;
			//loops = 0;
			vClient.send_diceroll();
			continueDisplay = false;
			//vClient.playSound(SoundClip.DICEROLL);
		}
	}
	
	/**
	 * 
	 */
	private void setIP(){
		host = IPAddress.getText();
		if("".equals(IPAddress.getText())){
			host = "localhost";
		}
		
		client = new GameClient(host, starter.ServerStarter.GAME_PORT);
		if(client.s == null){
			screen = IP_SCREEN;
			JOptionPane.showMessageDialog(this,
					"Invalid IP or Server is Unavailable", "Error",
					JOptionPane.WARNING_MESSAGE);
		} else {
			screen = BEGIN_SCREEN;
			newToScreen = true;
		}
	}
	
	/**
	 * 
	 */
	private void createNewGame(){
		System.out.println(gameName.getText());
		client.createGame(gameName.getText());
		screen = LOBBY_SCREEN;
		newToScreen = true;
		gameName.setText("");
	}
	
	/**
	 * 
	 */
	private void resetForNextGame(){
		endOfGameShowStats = false;
		endOfGame = false;
		
		gameid = 0;
		chatHistory.setText("");
		zoom = 9;
		xTranslate = 0;
		yTranslate = 0;
		oldx = 0;
		oldy = 0;
		beginDragX = 0;
		beginDragY = 0;
		
		myOwnColor = Color.WHITE;
		myOwnPlayerID = 0;
		toBuild = 0;
		
		updateCount = 1000;
		updateCountLoops = 0;
		
		//haxOnlyOnce = true;
		currentTime = 0;
		
		lobbyScreen.players = (List<String>)(new ArrayList<String>());
		
		vClient = new CatanBoard_vClient(client.getUserID(),
				client);
		
		winner = "";
		stats = null;
		
		chatBot.leaveGroup(chatRoomNumber);
		chatRoomNumber = 0;
		
		lobbyScreen.inProgressGameList.clear();
		lobbyScreen.openGameList.clear();
		client.requestGameInstances();
		
		hitFourPlayers = false;
	}
}