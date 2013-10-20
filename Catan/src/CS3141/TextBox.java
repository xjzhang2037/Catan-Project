package CS3141;

import java.awt.Font;
import java.awt.Polygon;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;

/**
 * @author Chris Doig
 */
public class TextBox {

	private Font chatFont = new Font(Font.SERIF,Font.PLAIN, 12);

	private String str;
	private int _x;
	private int _y;
	private int _width;
	private int _height;
	private boolean _editable;
	private boolean _multiline;
	private FontMetrics fontMetrics;


	// for single line input
	private boolean _hasFocus = false;
	private int cursorPos = 0;
	private int firstPosDisplayed = 0;
	private int displayableWidth;


	// for multiline, non editable
	private int firstLineDisplayed = 0;
	private int lastLineDisplayed = 0;
	private int totalLines = 0;

	private final int displayableLines;

	long currentTime = 0;
	
	private boolean passwordBox = false;
	
	private boolean listenToTabs = true;
	
	
	private boolean scrollUp = false;
	private boolean scrollDown = false;
	private final int loopsToDelay = 5;
	private int loops = 0;

	/**
	 * 
	 * @param s string
	 * @param x x position of upper-left corner
	 * @param y y position of upper-left corner
	 * @param width width of box
	 * @param height height of box
	 * @param editable true if content can be typed in
	 * @param multiline true if the box displays more than one line of text
	 * @param g Graphics
	 * @param password true if input is anonymized with '*'
	 */
	public TextBox(String s,
				   int x,
				   int y,
				   int width,
				   int height,
				   boolean editable,
				   boolean multiline,
				   Graphics g,
				   boolean password){
		str = s;
		_x = x;
		_y = y;
		_width = width;
		_height = height;
		_editable = editable;
		_multiline = multiline;

		displayableWidth = _width - 20;

		fontMetrics = g.getFontMetrics(chatFont);
		displayableLines = _height / (int)(fontMetrics.getStringBounds(" ", g).getHeight() + 3);
		
		passwordBox = password;
	}

	/**
	 * 
	 * @return
	 */
	public String getText(){
		return str;
	}

	/**
	 * 
	 * @param s
	 */
	public void setText(String s){
		str = s;
		cursorPos = s.length();
	}

	/**
	 * 
	 * @param s
	 */
	public void append(String s){
		str = str + s;
	}

	/**
	 * 
	 * @param g
	 */
	public void paint(Graphics g){
				
		g.setColor(Color.WHITE);
		g.fillRect(_x, _y, _width, _height);

		g.setColor(Color.BLACK);
		g.drawRect(_x, _y, _width, _height);


		int[] tri1X = {0, 5, 10};
		int[] tri1Y = {10, 0, 10};
		Polygon tri1 = new Polygon(tri1X, tri1Y, 3);


		int[] tri2X = {0, 5, 10};
		int[] tri2Y = {0, 10, 0};
		Polygon tri2 = new Polygon(tri2X, tri2Y, 3);


		int height = (int)fontMetrics.getStringBounds(" ", g).getHeight();
		g.setFont(chatFont);
		
		
		if(_multiline){
			//System.out.println(displayableLines + "," + height + "," + _height);
			
			if(loops > loopsToDelay){
				loops = 0;
				if(scrollUp){
					firstLineDisplayed--;
					if(firstLineDisplayed < 0){
						firstLineDisplayed = 0;
					}
				} else if(scrollDown){
					if(lastLineDisplayed != 0){
						firstLineDisplayed++;
	
						if(firstLineDisplayed > totalLines - displayableLines){
							firstLineDisplayed = totalLines - displayableLines;
						}
					}
				}
			}
			loops++;
			
			//System.out.println(scrollUp + "," + scrollDown);

			g.drawRect(_x + _width - 10 - 5, _y + 5, 10, 10);
			g.drawRect(_x + _width - 10 - 5, _y + _height - 10 - 5, 10, 10);

			tri1.translate(_x + _width - 10 - 5, _y + 5);
			g.drawPolygon(tri1);

			tri2.translate(_x + _width - 10 - 5, _y + _height - 10 - 5);
			g.drawPolygon(tri2);



			String[] s = wordWrap(str, g);

			for(int index = firstLineDisplayed; index < s.length; index++){
				int yOffset = (height + 3) * (index - firstLineDisplayed + 1);


				if(s[index] == null){
					break;
				} else if(yOffset < _height){
					g.drawString(s[index], _x + 10, _y + yOffset);
				} else {
					lastLineDisplayed = index;
					break;
				}

				//System.out.println(s[index]);
			}
		} else {
			String stringtoDraw = str;
			
			if(passwordBox){
				String newString = "";
				for(int index = 0; index < str.length(); index++){
					newString = newString + "*";
				}
				
				stringtoDraw = newString;
			}
			
			
			int i = stringtoDraw.length();
			//System.out.println((int)fontMetrics.getStringBounds(str.substring(firstPosDisplayed, i), g).getWidth() + "," + displayableWidth);


			if(cursorPos < firstPosDisplayed){
				firstPosDisplayed = cursorPos;
			}

			//while(firstPosDisplayed > 0 && (int)fontMetrics.getStringBounds(str.substring(firstPosDisplayed, cursorPos), g).getWidth() < displayableWidth){
			//	firstPosDisplayed--;
			//}


			while((int)fontMetrics.getStringBounds(stringtoDraw.substring(firstPosDisplayed, cursorPos), g).getWidth() > displayableWidth){
				firstPosDisplayed++;
			}



			while(i != 0 && (int)fontMetrics.getStringBounds(stringtoDraw.substring(firstPosDisplayed, i), g).getWidth() > displayableWidth){
				//System.out.println(firstPosDisplayed + "," + i);
				i--;
			}
			//System.out.println(i);
			//System.out.println(str.substring(firstPosDisplayed, i));


			g.drawString(stringtoDraw.substring(firstPosDisplayed, i), _x + 10, _y + height + 5);

			if(_hasFocus && ((int)((System.currentTimeMillis() - currentTime) / 700) % 2) == 0){
				int cursorDrawPos = (int)fontMetrics.getStringBounds(stringtoDraw.substring(firstPosDisplayed, cursorPos), g).getWidth();
				g.drawLine(_x + 10 + cursorDrawPos - 1,  _y + 7, _x + 10 + cursorDrawPos - 1,  _y + 22);
			}

		}
	}

	/**
	 *
	 * @param str
	 * @param g
	 * @return
	 */
	private String[] wordWrap(String inputString, Graphics g){

		String[] str = inputString.split("\n");


		int lineWidth = _width - 30;
		int spaceWidth = (int)fontMetrics.getStringBounds(" ", g).getWidth();

		String[] output = new String[2];
		int line = 0;

		for(int block = 0; block < str.length; block++){
			String[] arr = str[block].split(" ");

			String textLine = "";

			int spaceLeft = lineWidth;
			for(int index = 0; index < arr.length; index++){
				int width = (int)fontMetrics.getStringBounds(arr[index], g).getWidth();

				if(width + spaceWidth > spaceLeft){

					int i = arr[index].length();
					if(width > lineWidth){ // in case the word is wider than the box
						while(i != 0 && (int)fontMetrics.getStringBounds(arr[index].substring(0, i) + "-", g).getWidth() > lineWidth){
							i--;
						}
						textLine = textLine + "\n" + arr[index].substring(0, i) + "-";
						spaceLeft = lineWidth - (int)fontMetrics.getStringBounds(arr[index].substring(0, i), g).getWidth();

						arr[index] = arr[index].substring(i, arr[index].length());
						index--;

					} else {
						textLine = textLine + "\n" + arr[index];
						spaceLeft = lineWidth - width;
					}
				} else {
					if(textLine != ""){
						textLine = textLine + " " + arr[index];
					} else {
						textLine = textLine + arr[index];
					}
					spaceLeft = spaceLeft - (width + spaceWidth);
				}
			}

			String[] o = textLine.split("\n");
			while(line + o.length > output.length){ // expand array
				String[] expanded = new String[output.length * 2];
				for(int index = 0; index < output.length; index++){
					expanded[index] = output[index];
				}
				output = expanded;
			}


			for(int index = 0; index < o.length; index++){
				output[line] = o[index];
				line++;
			}
		}
		totalLines = line;

		return output;
	}

	/**
	 * 
	 * @param me
	 * @param g
	 * @return
	 */
	boolean isClicked(MouseEvent me, Graphics g){
		_hasFocus = false;

		if(me.getX() > _x && me.getX() < _x + _width &&
				me.getY() > _y && me.getY() < _y + _height){


			_hasFocus = true;
			currentTime = System.currentTimeMillis();

			if(_multiline){
				if(me.getX() > _x + _width - 10 - 5 && me.getX() < _x + _width - 5 &&
						me.getY() > _y + 5 && me.getY() < _y + 15){

					firstLineDisplayed--;
					if(firstLineDisplayed < 0){
						firstLineDisplayed = 0;
					}
				}

				if(me.getX() > _x + _width - 10 - 5 && me.getX() < _x + _width - 5 &&
						me.getY() > _y + _height - 10 - 5 && me.getY() < _y + _height - 5){


					if(lastLineDisplayed != 0){
						firstLineDisplayed++;


						if(firstLineDisplayed > totalLines - displayableLines){
							firstLineDisplayed = totalLines - displayableLines;
						}
					}
				}
			} else {
				int i = str.length();
				if(me.getX() > _x + 10 && me.getX() < _x + 10 + (int)fontMetrics.getStringBounds(str.substring(firstPosDisplayed, i), g).getWidth()){


					int width = 0;

					while(i > firstPosDisplayed){
						width = (int)fontMetrics.getStringBounds(str.substring(firstPosDisplayed, i), g).getWidth();
						if(me.getX() > _x + 10 + width){
							i++;
							break;
						}
						i--;
					}
				} else if(me.getX() > _x + 10 + (int)fontMetrics.getStringBounds(str.substring(firstPosDisplayed, i), g).getWidth()){
					i = str.length();
				} else if(me.getX() < _x + 10){
					i = 0;
				}
				cursorPos = i;
			}


			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public void moveToBottom(){
		if(_multiline){
			firstLineDisplayed = totalLines - displayableLines + 1;
			if(firstLineDisplayed < 0){
				firstLineDisplayed = 0;
			}
		}
	}

	/**
	 *
	 */
	public void released(){
		scrollUp = false;
		scrollDown = false;
	}

	/**
	 * 
	 * @param me
	 * @return
	 */
	public boolean isDepressed(MouseEvent me){
		if(me.getX() > _x && me.getX() < _x + _width &&
				me.getY() > _y && me.getY() < _y + _height){

			
			if(!scrollUp && !scrollDown){
				if(_multiline){
					if(me.getX() > _x + _width - 10 - 5 && me.getX() < _x + _width - 5 &&
							me.getY() > _y + 5 && me.getY() < _y + 15){
	
						scrollUp = true;
						scrollDown = false;
					}
	
					if(me.getX() > _x + _width - 10 - 5 && me.getX() < _x + _width - 5 &&
							me.getY() > _y + _height - 10 - 5 && me.getY() < _y + _height - 5){
	
						scrollUp = false;
						scrollDown = true;
					}
				}
			}
			_hasFocus = true;
			currentTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param e
	 */
	public void isKeyReleased(KeyEvent e){
		if(_editable){
			if(e.getKeyCode() == KeyEvent.VK_TAB){
				listenToTabs = true;
			}
		}
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	public boolean isKeyTyped(KeyEvent e){
		if(_hasFocus && _editable){
			if(e.getKeyChar() > 31 && e.getKeyChar() != 127){
				str = str.substring(0, cursorPos) + e.getKeyChar() + str.substring(cursorPos, str.length());
				cursorPos++;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	public boolean isKeyPressed(KeyEvent e){
		if(_editable){
			return isKeyPressed(e, this, this);
		}
		return false;
	}
	
	/**
	 * 
	 * @param e
	 * @param prev
	 * @param next
	 * @return
	 */
	public boolean isKeyPressed(KeyEvent e, TextBox prev, TextBox next){
		if(_hasFocus && _editable){
			currentTime = System.currentTimeMillis();

			if(e.getKeyCode() == KeyEvent.VK_LEFT && e.getModifiers() == InputEvent.CTRL_MASK){
				if(str.lastIndexOf(" ", cursorPos - 1) > 0){
					cursorPos = str.lastIndexOf(" ", cursorPos - 1);
				} else {
					cursorPos = 0;
				}

				if(cursorPos < 0){
					cursorPos = 0;
				}
			} else if(e.getKeyCode() == KeyEvent.VK_LEFT){
				cursorPos--;
				if(cursorPos < 0){
					cursorPos = 0;
				}
			}

			if(e.getKeyCode() == KeyEvent.VK_RIGHT && e.getModifiers() == InputEvent.CTRL_MASK){
				if(str.indexOf(" ", cursorPos) > 0){
					cursorPos = str.indexOf(" ", cursorPos) + 1;
				} else {
					cursorPos = str.length();
				}

				if(cursorPos > str.length()){
					cursorPos = str.length();
				}
			} else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				cursorPos++;
				if(cursorPos > str.length()){
					cursorPos = str.length();
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_HOME){
				cursorPos = 0;
			}
			if(e.getKeyCode() == KeyEvent.VK_END){
				cursorPos = str.length();
			}

			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
				if(cursorPos > 0){
					str = str.substring(0, cursorPos - 1) + str.substring(cursorPos, str.length());
					cursorPos--;
					if(cursorPos < 0){
						cursorPos = 0;
					}
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_DELETE){
				if(cursorPos < str.length() && str.length() > 0){
					str = str.substring(0, cursorPos) + str.substring(cursorPos + 1, str.length());
					if(cursorPos > str.length()){
						cursorPos = str.length();
					}
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				return true;
			}
			
			if(listenToTabs){
				if(e.getKeyCode() == KeyEvent.VK_TAB && e.getModifiers() == InputEvent.SHIFT_DOWN_MASK){
					_hasFocus = false;
					prev._hasFocus = true;
					
					listenToTabs = false;
					prev.listenToTabs = false;
				} else if(e.getKeyCode() == KeyEvent.VK_TAB){
					_hasFocus = false;
					next._hasFocus = true;
				
					listenToTabs = false;
					next.listenToTabs = false;
				}
			}
		}
		return false;
	}

	/**
	 * get typing focus on this box
	 */
	public void requestFocus(){
		_hasFocus = true;
	}
}
