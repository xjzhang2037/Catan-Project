package CS3141;

import java.awt.*;

/**
 * 
 * @author csdoig
 *
 */
public class DiceRollAnimation {
	
	/**
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param num
	 */
	public static void draw(Graphics g, int x, int y, int num){
		g.setColor(Color.WHITE);
		g.fillRoundRect(x - 50, y - 50, 100, 100, 30, 30);
		
		g.setColor(Color.BLACK);
		switch(num){
		case 1:
			g.fillOval(x - 10, y - 10, 20, 20);
			break;
		case 2:
			g.fillOval(x - 40, y - 40, 20, 20);
			g.fillOval(x + 20, y + 20, 20, 20);
			break;
		case 3:
			g.fillOval(x - 10, y - 10, 20, 20);
			g.fillOval(x - 40, y - 40, 20, 20);
			g.fillOval(x + 20, y + 20, 20, 20);
			break;
		case 4:
			g.fillOval(x - 40, y - 40, 20, 20);
			g.fillOval(x + 20, y + 20, 20, 20);
			
			g.fillOval(x - 40, y + 20, 20, 20);
			g.fillOval(x + 20, y - 40, 20, 20);
			break;
		case 5:
			g.fillOval(x - 40, y - 40, 20, 20);
			g.fillOval(x + 20, y + 20, 20, 20);
			
			g.fillOval(x - 40, y + 20, 20, 20);
			g.fillOval(x + 20, y - 40, 20, 20);
			
			g.fillOval(x - 10, y - 10, 20, 20);
			break;
		case 6:
			g.fillOval(x - 40, y - 40, 20, 20);
			g.fillOval(x - 40, y - 10, 20, 20);
			g.fillOval(x - 40, y + 20, 20, 20);
						
			g.fillOval(x + 20, y + 20, 20, 20);
			g.fillOval(x + 20, y - 10, 20, 20);
			g.fillOval(x + 20, y - 40, 20, 20);
			break;
		default:
			break;
		}
	}
}