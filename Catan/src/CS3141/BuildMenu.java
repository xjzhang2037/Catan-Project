package CS3141;

import java.awt.*;
import java.awt.event.*;

import catanNodes.*;

/**
 * 
 * @author csdoig
 *
 */
public class BuildMenu {
	private static final double COS_PI_6 = Math.cos(Math.PI / 6);
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Color c;
	
	private boolean show = false;
	
	public final static int BUILDING_NONE = 0;
	public final static int BUILDING_ROAD = 1;
	public final static int BUILDING_SETTLEMENT = 2;
	public final static int BUILDING_CITY = 3;
	private int buildingChoice = BUILDING_NONE;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public BuildMenu(int x, int y, int width, int height, Color c){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.c = c;
	}
	
	/**
	 * 
	 */
	public int buildingChoice(){
		return buildingChoice;
	}
	
	/**
	 * 
	 * @param choice
	 */
	public void setBuildingChoice(int choice){
		buildingChoice = choice;
	}
	
	/**
	 * 
	 * @param state
	 */
	public void setVisible(boolean state){
		show = state;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isVisible(){
		return show;
	}
	
	/**
	 * 
	 * @param c
	 */
	public void setColor(Color c){
		this.c = c;
	}
	
	/**
	 * 
	 * @param g
	 * @param client
	 */
	public void draw(Graphics g, CatanBoard_vClient client){
		if(show){
			Font f2 = new Font(Font.SERIF, Font.PLAIN, 18);
			
			Color background = new Color(255, 255, 200);
			Color highlighted = new Color(200, 200, 150);
			
			g.setColor(background);
			g.fillRect(x, y, width, height);
			
			
			
			// road
			if(buildingChoice == BUILDING_ROAD){
				g.setColor(highlighted);
				g.fillRect(x + 10, y + 10, 80, 60);
			}
			
			g.setColor(Color.BLACK);
			g.drawRect(x + 10, y + 10, 80, 60);
			
			drawRoadAbsolute(g, c, 10, x + 50, y + 40);
			CatanGraphics.drawCenteredText(g, Color.BLACK, f2, "road", x + 10, y + 70, 80, 40);

			
			// settlement
			if(buildingChoice == BUILDING_SETTLEMENT){
				g.setColor(highlighted);
				g.fillRect(x + 110, y + 10, 80, 60);
			}
			
			g.setColor(Color.BLACK);
			g.drawRect(x + 110, y + 10, 80, 60);
			drawSettlementAbsolute(g, c, 16, x + 150, y + 40);
			CatanGraphics.drawCenteredText(g, Color.BLACK, f2, "settlement", x + 110, y + 70, 80, 40);

			
			if(buildingChoice == BUILDING_CITY){
				g.setColor(highlighted);
				g.fillRect(x + 210, y + 10, 80, 60);
			}
			g.setColor(Color.BLACK);
			g.drawRect(x + 210, y + 10, 80, 60);
			drawCityAbsolute(g, c, 16, x + 250, y + 40);
			CatanGraphics.drawCenteredText(g, Color.BLACK, f2, "city", x + 210, y + 70, 80, 40);
		}
	}
	
	/**
	 * 
	 * @param me
	 * @param deselect
	 * @param client
	 */
	public void isClicked(MouseEvent me, boolean deselect, CatanBoard_vClient client){
		if(isVisible()){
			if(me.getX() > x + 10 && me.getX() < x + 10 + 80 && me.getY() > y + 10 && me.getY() < y + 10 + 60){
				if(true){ // if choice is available
					buildingChoice = BUILDING_ROAD;
					System.out.println("ROAD");
				}
			} else if(me.getX() > x + 110 && me.getX() < x + 110 + 80 && me.getY() > y + 10 && me.getY() < y + 10 + 60){
				if(true){ // if choice is available
					buildingChoice = BUILDING_SETTLEMENT;
					System.out.println("SETTLEMENT");
				}
			} else if(me.getX() > x + 210 && me.getX() < x + 210 + 80 && me.getY() > y + 10 && me.getY() < y + 10 + 60){
				if(true){ // if choice is available
					buildingChoice = BUILDING_CITY;
					System.out.println("CITY");
				}
			} else if(deselect){
				buildingChoice = BUILDING_NONE;
			}
		}
	}
	
	/**
	 * 
	 * @param g
	 * @param c
	 * @param size
	 * @param x
	 * @param y
	 */
	private static void drawCityAbsolute(Graphics g, Color c, int size, int x, int y){
		int[] cityX = new int[7];
		cityX[0] = 0;
		cityX[1] = size;
		cityX[2] = size;
		cityX[3] = -size;
		cityX[3] = -size;
		cityX[4] = -size;
		cityX[5] = (int)(-size * .5);

		int[] cityY = new int[7];
		cityY[0] = 0;
		cityY[1] = 0;
		cityY[2] = size;
		cityY[3] = size;
		cityY[4] = -size;
		cityY[5] = (int)(-size * 1.5);
		cityY[6] = -size;

		Polygon city = new Polygon(cityX, cityY, 7);
		
		CatanGraphics.drawPolygon(g, city, c, true, x, y);
	}
	
	/**
	 * 
	 * @param g
	 * @param c
	 * @param size
	 * @param x
	 * @param y
	 */
	static void drawRoadAbsolute(Graphics g, Color c, int size, int x, int y){
		int[] roadX = new int[4];
		roadX[0] = (int)(-3 * size * COS_PI_6 - size * .5);
		roadX[1] = (int)(-3 * size * COS_PI_6 + size * .5);
		roadX[2] = (int)(3 * size * COS_PI_6 + size * .5);
		roadX[3] = (int)(3 * size * COS_PI_6 - size * .5);

		int[] roadY = new int[4];
		roadY[0] = (int)(-3 * size * .5 + size * COS_PI_6);
		roadY[1] = (int)(-3 * size * .5 - size * COS_PI_6);
		roadY[2] = (int)(3 * size * .5 - size * COS_PI_6);
		roadY[3] = (int)(3 * size * .5 + size * COS_PI_6);

		Polygon road120 = new Polygon(roadX, roadY, 4);

		CatanGraphics.drawPolygon(g, road120, c, true, x, y);
	}
	
	/**
	 * 
	 * @param g
	 * @param c
	 * @param size
	 * @param x
	 * @param y
	 */
	private static void drawSettlementAbsolute(Graphics g, Color c, int size, int x, int y){
		int[] settlementX = new int[5];
		settlementX[0] = 0;
		settlementX[1] = size;
		settlementX[2] = size;
		settlementX[3] = -size;
		settlementX[4] = -size;

		int[] settlementY = new int[5];
		settlementY[0] = -size;
		settlementY[1] = 0;
		settlementY[2] = size;
		settlementY[3] = size;
		settlementY[4] = 0;
		Polygon settlement = new Polygon(settlementX, settlementY, 5);
		
		CatanGraphics.drawPolygon(g, settlement, c, true, x, y);
	}
}
