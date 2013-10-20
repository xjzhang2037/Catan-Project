package CS3141;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

/**
 * @author Chris Doig
 */
public class Button {
	public enum ButtonImage {
		BACK,
		BANK,
		BUILD,
		PASSWORD,
		CHECK,
		CONFIRM_ADDRESS,
		CONTINUE,
		CREATE,
		END_GAME,
		END_TURN,
		END_PHASE,
		EXIT,
		JOIN,
		LOGIN,
		LOGIN2,
		NEW_GAME,
		OBSERVE,
		REGISTER,
		REJECT,
		ROLL,
		SEND,
		TRADE,
		USE_CARD,
		NULL;
	}
	
	private String _text;
	private Color _c;
	private Color fontColor = Color.BLACK;
	private int _x;
	private int _y;
	private int _width;
	private int _height;

	private boolean _depressed = false;
	private boolean stayDepressed = false;
	
	private Font buttonFont;
	
	private ButtonImage image = ButtonImage.NULL;
		
	static Image back;
	static Image backDark;
	static Image bank;
	static Image bankDark;
	static Image build;
	static Image buildDark;
	static Image password;
	static Image passwordDark;
	static Image check;
	static Image checkDark;
	static Image confirmAddress;
	static Image confirmAddressDark;
	static Image continueButton;
	static Image continueDark;
	static Image create;
	static Image createDark;
	static Image endGame;
	static Image endGameDark;
	static Image endPhase;
	static Image endPhaseDark;
	static Image endTurn;
	static Image endTurnDark;
	static Image exit;
	static Image exitDark;
	static Image join;
	static Image joinDark;
	static Image login;
	static Image loginDark;
	static Image login2;
	static Image login2Dark;
	static Image newGame;
	static Image newGameDark;
	static Image observe;
	static Image observeDark;
	static Image register;
	static Image registerDark;
	static Image reject;
	static Image rejectDark;
	static Image roll;
	static Image rollDark;
	static Image send;
	static Image sendDark;
	static Image trade;
	static Image tradeDark;
	static Image useCard;
	static Image useCardDark;
	
	/**
	 * Call once to load all images
	 */
	public static void init(){
		back = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/back.png"))).getImage();
		backDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/back_dark.png"))).getImage();
		
		bank = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/bank.png"))).getImage();
		bankDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/bank_dark.png"))).getImage();
		
		build = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/build.png"))).getImage();
		buildDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/build_dark.png"))).getImage();
		
		password = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/changepassword.png"))).getImage();
		passwordDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/changepassword_dark.png"))).getImage();
		
		check = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/check.png"))).getImage();
		checkDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/check_dark.png"))).getImage();
		
		confirmAddress = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/confirm.png"))).getImage();
		confirmAddressDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/confirm_dark.png"))).getImage();
		
		continueButton = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/continue.png"))).getImage();
		continueDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/continue_dark.png"))).getImage();
		
		create = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/create.png"))).getImage();
		createDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/create_dark.png"))).getImage();
		
		endGame = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/endgame.png"))).getImage();
		endGameDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/endgame_dark.png"))).getImage();
		
		endTurn = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/endturn.png"))).getImage();
		endTurnDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/endturn_dark.png"))).getImage();
		
		endPhase = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/endphase.png"))).getImage();
		endPhaseDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/endphase_dark.png"))).getImage();
		
		exit = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/exit.png"))).getImage();
		exitDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/exit_dark.png"))).getImage();
		
		join = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/join.png"))).getImage();
		joinDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/join_dark.png"))).getImage();
		
		login = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/login.png"))).getImage();
		loginDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/login_dark.png"))).getImage();
		
		login2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/login2.png"))).getImage();
		login2Dark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/login2_dark.png"))).getImage();
		
		newGame = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/newgame.png"))).getImage();
		newGameDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/newgame_dark.png"))).getImage();
		
		observe = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/observe.png"))).getImage();
		observeDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/observe_dark.png"))).getImage();
		
		register = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/register.png"))).getImage();
		registerDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/register_dark.png"))).getImage();
		
		reject = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/reject.png"))).getImage();
		rejectDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/reject_dark.png"))).getImage();
		
		roll = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/roll.png"))).getImage();
		rollDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/roll_dark.png"))).getImage();
		
		send = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/send.png"))).getImage();
		sendDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/send_dark.png"))).getImage();
		
		trade = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/tradecard.png"))).getImage();
		tradeDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/tradecard_dark.png"))).getImage();
		
		useCard = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/buycard.png"))).getImage();
		useCardDark = (new ImageIcon(CatanGraphics.class.getResource("/Images/buttons/buycard_dark.png"))).getImage();
	}
	
	/**
	 * 
	 * @param bi
	 */
	public void setImage(ButtonImage bi){
		image = bi;
	}
	
	/**
	 * 
	 * @param text
	 * @param c
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param stayDepressed
	 */
	public Button(String text, Color c, int x, int y, int width, int height, boolean stayDepressed){
		_text = text;
		_c = c;
		_x = x;
		_y = y;
		_width = width;
		_height = height;
		
		this.stayDepressed = stayDepressed;

		buttonFont = new Font(Font.SERIF,Font.PLAIN, 24);
	}
	
	/**
	 * 
	 * @param text
	 * @param f
	 * @param c
	 * @param fontColor
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param stayDepressed
	 */
	public Button(String text, Font f, Color c, Color fontColor, int x, int y, int width, int height, boolean stayDepressed){
		_text = text;
		_c = c;
		_x = x;
		_y = y;
		_width = width;
		_height = height;
		
		this.stayDepressed = stayDepressed;
		this.fontColor = fontColor;

		buttonFont = f;
	}

	/**
	 * 
	 * @param text
	 * @param f
	 * @param c
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param stayDepressed
	 */
	public Button(String text, Font f, Color c, int x, int y, int width, int height, boolean stayDepressed){
		_text = text;
		_c = c;
		_x = x;
		_y = y;
		_width = width;
		_height = height;
		
		this.stayDepressed = stayDepressed;

		buttonFont = f;
	}

	/**
	 * 
	 * @param g
	 */
	void draw(Graphics g){
		if(image == ButtonImage.NULL){
			g.setColor(_c);
			g.fillRect(_x, _y, _width, _height);
			g.draw3DRect(_x, _y, _width, _height, !_depressed);
	
	
			FontMetrics fontMetrics = g.getFontMetrics(buttonFont);
			Rectangle2D bounds = fontMetrics.getStringBounds(_text, g);
			g.setColor(fontColor);
			g.setFont(buttonFont);
			//g.drawString(_text, (int)(_x + (_width - bounds.getWidth()) / 2),
			//	(int)(_y + _height - bounds.getHeight() / 2));
	
			g.drawString(_text,
				(int)(_x + (_width - bounds.getWidth()) / 2),
				//(int)(_y + _height - (_height - bounds.getHeight()) / 2));
				(int)(_y + _height * .5 + bounds.getHeight() / 2));
		} else {
			g.drawImage(getImage(), _x, _y, null);
		}
	}

	/**
	 * 
	 * @param me
	 * @return
	 */
	boolean isClicked(MouseEvent me){
		if(me.getX() > _x && me.getX() < _x + _width &&
			me.getY() > _y && me.getY() < _y + _height){

			if(!stayDepressed){
				_depressed = false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	void released(){
		if(!stayDepressed){
			_depressed = false;
		}
	}
	
	/**
	 * 
	 * @param state
	 */
	void setDepress(boolean state){
		_depressed = state;
	}
	
	/**
	 * 
	 * @param me
	 * @return
	 */
	boolean isDepressed(MouseEvent me){
		if(me.getX() > _x && me.getX() < _x + _width &&
			me.getY() > _y && me.getY() < _y + _height){

			_depressed = true;
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void updatePosition(int x, int y){
		_x = x;
		_y = y;
	}
	
	/**
	 * 
	 * @param s
	 */
	public void updateLabel(String s){
		_text = s;
	}
	
	/**
	 * 
	 * @return
	 */
	private Image getImage(){
		switch(image){
		case BACK:
			if(!_depressed)
				return back;
			else
				return backDark;
		case BANK:
			if(!_depressed)
				return bank;
			else
				return bankDark;
		case BUILD:
			if(!_depressed)
				return build;
			else
				return buildDark;
		case PASSWORD:
			if(!_depressed)
				return password;
			else
				return passwordDark;
		case CHECK:
			if(!_depressed)
				return check;
			else
				return checkDark;
		case CONFIRM_ADDRESS:
			if(!_depressed)
				return confirmAddress;
			else
				return confirmAddressDark;
		case CONTINUE:
			if(!_depressed)
				return continueButton;
			else
				return continueDark;
		case CREATE:
			if(!_depressed)
				return create;
			else
				return createDark;
		case END_GAME:
			if(!_depressed)
				return endGame;
			else
				return endGameDark;
		case END_PHASE:
			if(!_depressed)
				return endPhase;
			else
				return endPhaseDark;
		case END_TURN:
			if(!_depressed)
				return endTurn;
			else
				return endTurnDark;
		case EXIT:
			if(!_depressed)
				return exit;
			else
				return exitDark;
		case JOIN:
			if(!_depressed)
				return join;
			else
				return joinDark;
		case LOGIN:
			if(!_depressed)
				return login;
			else
				return loginDark;
		case LOGIN2:
			if(!_depressed)
				return login2;
			else
				return login2Dark;
		case NEW_GAME:
			if(!_depressed)
				return newGame;
			else
				return newGameDark;
		case OBSERVE:
			if(!_depressed)
				return observe;
			else
				return observeDark;
		case REGISTER:
			if(!_depressed)
				return register;
			else
				return registerDark;
		case REJECT:
			if(!_depressed)
				return reject;
			else
				return rejectDark;
		case ROLL:
			if(!_depressed)
				return roll;
			else
				return rollDark;
		case SEND:
			if(!_depressed)
				return send;
			else
				return sendDark;
		case TRADE:
			if(!_depressed)
				return trade;
			else
				return tradeDark;
		case USE_CARD:
			if(!_depressed)
				return useCard;
			else
				return useCardDark;
		case NULL:
			return null;
		default:
			return null;
		}
	}
	
	/**
	 * 
	 * @param s
	 */
	public void setText(String s){
		_text = s;
	}
}