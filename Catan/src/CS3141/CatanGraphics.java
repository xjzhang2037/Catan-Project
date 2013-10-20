package CS3141;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import javax.swing.ImageIcon;
import catanNodes.*;

/**
 * @author Chris Doig
 */
public class CatanGraphics {

	static final double COS_PI_6 = Math.cos(Math.PI / 6);

	private static int team; // 0 = white, 1 = red, 2 = orange, 3 = blue
	static Color teamColor;


	static int _hexSize;
	static int _settleSize;
	static int _citySize;
	static int _roadSize;

	static int _originX;
	static int _originY;

	// define hexagon shape
	static int[] hexagonX;
	static int[] hexagonY;
	static Polygon hexagon;

	// define settlement shape
	static int[] settlementX;
	static int[] settlementY;
	static Polygon settlement;

	// define city shape
	static int[] cityX;
	static int[] cityY;
	static Polygon city;

	// define road shapes

	// 120 degree angle (\)
	static int[] road120X;
	static int[] road120Y;
	static Polygon road120;

	// 240 degree angle (/)
	static Polygon road240;

	// 360 degree angle (|) (vertical)
	static int[] road360X;
	static int[] road360Y;
	static Polygon road360;

	// vertex hexes
	static int[] hex1X;
	static int[] hex1Y;
	static Polygon hex1;
	static Polygon hex2;

	static boolean updateSize = false;
	
	static double[] beachX = {-0.05, 0.5, 1, 1.5, 2, 2.5, 3.05,
			3.05, 3.55, 3.55, 4.05, 4.05, 3.55, 3.55, 3.05,
			3.05, 2.5, 2, 1.5, 1, 0.5, -0.05,
			-.05, -.55, -.55, -1.05, -1.05, -.55, -.55, -0.05};
	
	static double[] beachY = {.20, -0.075, .175, -.075, .175, -.075, .20,
			.7, .95, 1.45, 1.7, 2.3, 2.55, 3.05, 3.3,
			3.8, 4.075, 3.825, 4.075, 3.825, 4.075, 3.8,
			3.3, 3.05, 2.55, 2.3, 1.7, 1.45, .95, .7};

	static int[] beachXInt = new int[beachX.length];
	static int[] beachYInt = new int[beachX.length];
	
	static Polygon beach;
	
	static Color beachColor = new Color(248, 222, 126);

	static BasicStroke dashed;
	static BasicStroke plain;


	static final double[][] vertexOffsetsX = {{0.5, 1.5, 2.5},
						{0, 1, 2, 3},
						{0, 1, 2, 3},
						{-0.5, 0.5, 1.5, 2.5, 3.5},
						{-0.5, 0.5, 1.5, 2.5, 3.5},
						{-1, 0, 1, 2, 3, 4},
						{-1, 0, 1, 2, 3, 4},
						{-0.5, 0.5, 1.5, 2.5, 3.5},
						{-0.5, 0.5, 1.5, 2.5, 3.5},
						{0, 1, 2, 3},
						{0, 1, 2, 3},
						{0.5, 1.5, 2.5}};

	static final double[][] vertexOffsetsY = {{0, 0, 0},
						{0.25, 0.25, 0.25, 0.25},
						{0.75, 0.75, 0.75, 0.75},
						{1, 1, 1, 1, 1},
						{1.5, 1.5, 1.5, 1.5, 1.5},
						{1.75, 1.75, 1.75, 1.75, 1.75, 1.75},
						{2.25, 2.25, 2.25, 2.25, 2.25, 2.25},
						{2.5, 2.5, 2.5, 2.5, 2.5},
						{3, 3, 3, 3, 3},
						{3.25, 3.25, 3.25, 3.25},
						{3.75, 3.75, 3.75, 3.75},
						{4, 4, 4, 4}};



	static final double[][] roadOffsetsX = {{0.25, 0.75, 1.25, 1.75, 2.25, 2.75},
						{0, 1, 2, 3},
						{-0.25, 0.25, 0.75, 1.25, 1.75, 2.25, 2.75, 3.25},
						{-0.5, 0.5, 1.5, 2.5, 3.5},
						{-0.75, -0.25, 0.25, 0.75, 1.25, 1.75, 2.25, 2.75, 3.25, 3.75},
						{-1, 0, 1, 2, 3, 4},
						{-0.75, -0.25, 0.25, 0.75, 1.25, 1.75, 2.25, 2.75, 3.25, 3.75},
						{-0.5, 0.5, 1.5, 2.5, 3.5},
						{-0.25, 0.25, 0.75, 1.25, 1.75, 2.25, 2.75, 3.25},
						{0, 1, 2, 3},
						{0.25, 0.75, 1.25, 1.75, 2.25, 2.75}};

	static final double[][] roadOffsetsY = {{0.125, 0.125, 0.125, 0.125, 0.125, 0.125,},
						{0.5, 0.5, 0.5, 0.5},
						{0.875, 0.875, 0.875, 0.875, 0.875, 0.875, 0.875, 0.875},
						{1.25, 1.25, 1.25, 1.25, 1.25},
						{1.625, 1.625, 1.625, 1.625, 1.625, 1.625, 1.625, 1.625, 1.625, 1.625},
						{2, 2, 2, 2, 2, 2},
						{2.375, 2.375, 2.375, 2.375, 2.375, 2.375, 2.375, 2.375, 2.375, 2.375},
						{2.75, 2.75, 2.75, 2.75, 2.75},
						{3.125, 3.125, 3.125, 3.125, 3.125, 3.125, 3.125, 3.125},
						{3.5, 3.5, 3.5, 3.5},
						{3.875, 3.875, 3.875, 3.875, 3.875, 3.875}};


	static final double[][] hexOffsetsX = {{0.5, 1.5, 2.5},
						{0, 1, 2, 3},
						{-0.5, 0.5, 1.5, 2.5, 3.5},
						{0, 1, 2, 3},
						{0.5, 1.5, 2.5}};

	static final double[][] hexOffsetsY = {{0.5, 0.5, 0.5},
						{1.25, 1.25, 1.25, 1.25},
						{2, 2, 2, 2, 2},
						{2.75, 2.75, 2.75, 2.75},
						{3.5, 3.5, 3.5}};


	// fonts
	static Font buttonFont;


	static Image robberImg2;
	static Image brickImg2;
	static Image grainImg2;
	static Image lumberImg2;
	static Image oreImg2;
	static Image woolImg2;
	static Image desertImg2;
	static Image shipImg2;
	
	static Image robberSmall;
	static Image robberBig;
	
	static BufferedImage robberImg;
	static BufferedImage brickImg;
	static BufferedImage grainImg;
	static BufferedImage lumberImg;
	static BufferedImage oreImg;
	static BufferedImage woolImg;
	static BufferedImage desertImg;
	static BufferedImage shipImg;
	
	static Image scaledBrick;
	static Image scaledGrain;
	static Image scaledLumber;
	static Image scaledOre;
	static Image scaledWool;
	static Image scaledDesert;
	static Image scaledShip;
	
	
	static Image brickCard2;
	static Image grainCard2;
	static Image lumberCard2;
	static Image oreCard2;
	static Image woolCard2;
	static Image freeRoadsCard2;
	static Image knightCard2;
	static Image largestArmyCard2;
	static Image longestRoadCard2;
	static Image monopolyCard2;
	static Image oneVPCard2;
	static Image plentyCard2;
	
	static BufferedImage brickCard;
	static BufferedImage grainCard;
	static BufferedImage lumberCard;
	static BufferedImage oreCard;
	static BufferedImage woolCard;
	static BufferedImage freeRoadsCard;
	static BufferedImage knightCard;
	static BufferedImage largestArmyCard;
	static BufferedImage longestRoadCard;
	static BufferedImage monopolyCard;
	static BufferedImage oneVPCard;
	static BufferedImage plentyCard;
	
	static Image scaledBrickCard;
	static Image scaledGrainCard;
	static Image scaledLumberCard;
	static Image scaledOreCard;
	static Image scaledWoolCard;
	static Image scaledFreeRoadsCard;
	static Image scaledKnightCard;
	static Image scaledLargestArmyCard;
	static Image scaledLongestRoadCard;
	static Image scaledMonopolyCard;
	static Image scaledOneVPCard;
	static Image scaledPlentyCard;
	
	static BufferedImage filter;
	static Image filter2;
	
	static BufferedImage userDisplayBlock;
	static Image userDisplayBlock2;
	static Image scaledUserDisplayBlock;
	
	static BufferedImage panel;
	static BufferedImage gradient;
	static BufferedImage logo;
	
	static Image panel2;
	static Image gradient2;
	static Image logo2;
	
	static Image scaledLogo;
	
	static BufferedImage water;
	static Image water2;
	

	static void init(){
		robberImg2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/Robber.png"))).getImage();
		double hexSize = 5;
		robberSmall = robberImg2.getScaledInstance((int)(hexSize / 3.5), (int)(hexSize / 1.75), Image.SCALE_SMOOTH);
		robberBig = robberImg2.getScaledInstance((int)(hexSize / 3), (int)(hexSize / 1.5), Image.SCALE_SMOOTH);
		
		brickImg2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/brick_small.png"))).getImage();
		grainImg2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/grain_small.png"))).getImage();
		lumberImg2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/lumber_small.png"))).getImage();
		oreImg2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/ore_small.png"))).getImage();
		woolImg2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/wool_small.png"))).getImage();
		desertImg2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/desert_small.png"))).getImage();
		shipImg2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/ship_small.png"))).getImage();
		
		brickCard2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/brickcard.png"))).getImage();
		grainCard2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/graincard.png"))).getImage();
		lumberCard2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/lumbercard.png"))).getImage();
		oreCard2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/ore_card.png"))).getImage();
		woolCard2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/woolcard.png"))).getImage();

		freeRoadsCard2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/2free_roads.png"))).getImage();
		knightCard2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/knight.png"))).getImage();
		largestArmyCard2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/largest_army.png"))).getImage();
		longestRoadCard2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/longest_road.png"))).getImage();
		monopolyCard2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/monopoly.png"))).getImage();
		oneVPCard2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/victory_point.png"))).getImage();
		plentyCard2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/year_of_plenty.png"))).getImage();
					

		filter2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/filter.png"))).getImage();
		
		userDisplayBlock2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/userblockdisplay.png"))).getImage();
		scaledUserDisplayBlock = userDisplayBlock2.getScaledInstance(400, 85, Image.SCALE_SMOOTH);
		
		panel2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/panel.png"))).getImage();
		gradient2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/gradient.png"))).getImage();
		logo2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/settlers icon big no blue.png"))).getImage();
		scaledLogo = logo2.getScaledInstance(300, 300, Image.SCALE_SMOOTH);

		
		water2 = (new ImageIcon(CatanGraphics.class.getResource("/Images/diving_ocean-792387.jpg"))).getImage();
		
		scaledBrickCard = brickCard2.getScaledInstance(40, 60, Image.SCALE_SMOOTH);
		scaledGrainCard = grainCard2.getScaledInstance(40, 60, Image.SCALE_SMOOTH);
		scaledLumberCard = lumberCard2.getScaledInstance(40, 60, Image.SCALE_SMOOTH);
		scaledOreCard = oreCard2.getScaledInstance(40, 60, Image.SCALE_SMOOTH);
		scaledWoolCard = woolCard2.getScaledInstance(40, 60, Image.SCALE_SMOOTH);
		
		scaledFreeRoadsCard = freeRoadsCard2.getScaledInstance(40, 60, Image.SCALE_SMOOTH);
		scaledKnightCard = knightCard2.getScaledInstance(40, 60, Image.SCALE_SMOOTH);
		scaledLargestArmyCard = largestArmyCard2.getScaledInstance(60, 90, Image.SCALE_SMOOTH);
		scaledLongestRoadCard = longestRoadCard2.getScaledInstance(60, 90, Image.SCALE_SMOOTH);
		scaledMonopolyCard = monopolyCard2.getScaledInstance(40, 60, Image.SCALE_SMOOTH);
		scaledOneVPCard = oneVPCard2.getScaledInstance(40, 60, Image.SCALE_SMOOTH);
		scaledPlentyCard = plentyCard2.getScaledInstance(40, 60, Image.SCALE_SMOOTH);
	}

	/**
	 * updates sizes when the map is zoomed
	 * @param hexSize
	 * @param settleSize
	 * @param citySize
	 * @param roadSize
	 * @param originX
	 * @param originY
	 * @param color
	 */
	static void set(int hexSize,
						int settleSize,
						int citySize,
						int roadSize,
						int originX,
						int originY,
						int color){

		updateSize = (_hexSize != hexSize);

		_hexSize = hexSize;
		_settleSize = settleSize;
		_citySize = citySize;
		_roadSize = roadSize;
		_originX = originX;
		_originY = originY;


		team = color;
		if(team == 0){
			teamColor = Color.WHITE;
		} else if(team == 1){
			teamColor = Color.RED;
		} else if(team == 2){
			teamColor = Color.ORANGE;
		} else if(team == 3){
			teamColor = Color.BLUE;
		}


		buttonFont = new Font(Font.SERIF, Font.PLAIN, 24);


		// ----------- roads ----------- //
		if(updateSize){
			// 120 degree angle (\)
			road120X = new int[4];
			road120X[0] = (int)(-3 * _roadSize * COS_PI_6 - _roadSize * .5);
			road120X[1] = (int)(-3 * _roadSize * COS_PI_6 + _roadSize * .5);
			road120X[2] = (int)(3 * _roadSize * COS_PI_6 + _roadSize * .5);
			road120X[3] = (int)(3 * _roadSize * COS_PI_6 - _roadSize * .5);

			road120Y = new int[4];
			road120Y[0] = (int)(-3 * _roadSize * .5 + _roadSize * COS_PI_6);
			road120Y[1] = (int)(-3 * _roadSize * .5 - _roadSize * COS_PI_6);
			road120Y[2] = (int)(3 * _roadSize * .5 - _roadSize * COS_PI_6);
			road120Y[3] = (int)(3 * _roadSize * .5 + _roadSize * COS_PI_6);

			road120 = new Polygon(road120X, road120Y, 4);

			// 240 degree angle (/)
			for(int index = 0; index < 4; index++){
				road120X[index] = -road120X[index];
			}
			road240 = new Polygon(road120X, road120Y, 4);

			// 360 degree angle (|) (vertical)
			road360X = new int[4];
			road360X[0] = -_roadSize;
			road360X[1] = _roadSize;
			road360X[2] = _roadSize;
			road360X[3] = -_roadSize;

			road360Y = new int[4];
			road360Y[0] = -3 * _roadSize;
			road360Y[1] = -3 * _roadSize;
			road360Y[2] = 3 * _roadSize;
			road360Y[3] = 3 * _roadSize;

			road360 = new Polygon(road360X, road360Y, 4);


			// ----------- vertices ----------- //
			hex1X = new int[6];
			hex1X[0] = (int)(-3 * _roadSize * COS_PI_6 + _roadSize * .5);
			hex1X[1] = (int)(-3 * _roadSize * COS_PI_6 - _roadSize * .5);
			hex1X[2] = -_roadSize;
			hex1X[3] = _roadSize;
			hex1X[4] = (int)(3 * _roadSize * COS_PI_6 + _roadSize * .5);
			hex1X[5] = (int)(3 * _roadSize * COS_PI_6 - _roadSize * .5);

			hex1Y = new int[6];
			hex1Y[0] = (int)(3 * _roadSize * .5 + _roadSize * COS_PI_6);
			hex1Y[1] =(int)(3 * _roadSize * .5 - _roadSize * COS_PI_6);
			hex1Y[2] = -3 * _roadSize;
			hex1Y[3] = -3 * _roadSize;
			hex1Y[4] = (int)(3 * _roadSize * .5 - _roadSize * COS_PI_6);
			hex1Y[5] =(int)(3 * _roadSize * .5 + _roadSize * COS_PI_6);

			hex1 = new Polygon(hex1X, hex1Y, 6);

			for(int index = 0; index < 6; index++){
				hex1Y[index] = -hex1Y[index];
			}
			hex2 = new Polygon(hex1X, hex1Y, 6);

			// ----------- cities ----------- //
			cityX = new int[7];
			cityX[0] = 0;
			cityX[1] = _citySize;
			cityX[2] = _citySize;
			cityX[3] = -_citySize;
			cityX[3] = -_citySize;
			cityX[4] = -_citySize;
			cityX[5] = (int)(-_citySize * .5);

			cityY = new int[7];
			cityY[0] = 0;
			cityY[1] = 0;
			cityY[2] = _citySize;
			cityY[3] = _citySize;
			cityY[4] = -_citySize;
			cityY[5] = (int)(-_citySize * 1.5);
			cityY[6] = -_citySize;

			city = new Polygon(cityX, cityY, 7);

			// ----------- settlements ----------- //
			settlementX = new int[5];
			settlementX[0] = 0;
			settlementX[1] = _settleSize;
			settlementX[2] = _settleSize;
			settlementX[3] = -_settleSize;
			settlementX[4] = -_settleSize;

			settlementY = new int[5];
			settlementY[0] = -_settleSize;
			settlementY[1] = 0;
			settlementY[2] = _settleSize;
			settlementY[3] = _settleSize;
			settlementY[4] = 0;
			settlement = new Polygon(settlementX, settlementY, 5);
		}
	}

	/**
	 * 
	 * @param g
	 * @param client
	 */
	static void drawMap(Graphics g, CatanBoard_vClient client){

		if(brickImg2 != null){
			if(updateSize){
				scaledBrick = brickImg2.getScaledInstance((int)(COS_PI_6 * _hexSize * 1.02), (int)(_hexSize), Image.SCALE_SMOOTH);
				scaledGrain = grainImg2.getScaledInstance((int)(COS_PI_6 * _hexSize * 1.02), (int)(_hexSize), Image.SCALE_SMOOTH);
				scaledLumber = lumberImg2.getScaledInstance((int)(COS_PI_6 * _hexSize * 1.02), (int)(_hexSize), Image.SCALE_SMOOTH);
				scaledOre = oreImg2.getScaledInstance((int)(COS_PI_6 * _hexSize * 1.02), (int)(_hexSize), Image.SCALE_SMOOTH);
				scaledWool = woolImg2.getScaledInstance((int)(COS_PI_6 * _hexSize * 1.02), (int)(_hexSize), Image.SCALE_SMOOTH);
				scaledDesert = desertImg2.getScaledInstance((int)(COS_PI_6 * _hexSize * 1.02), (int)(_hexSize), Image.SCALE_SMOOTH);
				scaledShip = shipImg2.getScaledInstance((int)(_hexSize * .5), (int)(_hexSize * .5), Image.SCALE_SMOOTH);

			}
			// beach
			for(int index = 0; index < beachX.length; index++){
				beachXInt[index] = (int)(beachX[index] * COS_PI_6 * _hexSize);
				beachYInt[index] = (int)(beachY[index] * _hexSize);
			}
			beach = new Polygon(beachXInt, beachYInt, 30);


			drawPolygon(g, beach, beachColor, true, _originX, _originY);


			Font shipFont = new Font(Font.SERIF,Font.PLAIN, (int)(_hexSize / 5));

			// ships
			g.setColor(Color.BLACK);
			g.setFont(shipFont);

			g.drawImage(scaledShip, (int)(_originX - COS_PI_6 * _hexSize * .25), (int)(_originY - _hexSize * .5), null);
			g.drawString("? 3:1", (int)(_originX - COS_PI_6 * _hexSize * .25), (int)(_originY - _hexSize * .55));


			g.drawImage(scaledShip, (int)(_originX + COS_PI_6 * _hexSize * 1.75), (int)(_originY - _hexSize * .5), null);
			g.drawString("W 2:1", (int)(_originX + COS_PI_6 * _hexSize * 1.75), (int)(_originY - _hexSize * .55));


			g.drawImage(scaledShip, (int)(_originX + COS_PI_6 * _hexSize * 3.25), (int)(_originY + _hexSize * 0.25), null);
			g.drawString("? 3:1", (int)(_originX + COS_PI_6 * _hexSize * 3.25), (int)(_originY + _hexSize * 0.20));


			g.drawImage(scaledShip, (int)(_originX + COS_PI_6 * _hexSize * 4.25), (int)(_originY + _hexSize * 1.75), null);
			g.drawString("? 3:1", (int)(_originX + COS_PI_6 * _hexSize * 4.25), (int)(_originY + _hexSize * 1.70));


			g.drawImage(scaledShip, (int)(_originX + COS_PI_6 * _hexSize * 3.25), (int)(_originY + _hexSize * 3.25), null);
			g.drawString("B 2:1", (int)(_originX + COS_PI_6 * _hexSize * 3.25), (int)(_originY + _hexSize * 3.95));


			g.drawImage(scaledShip, (int)(_originX + COS_PI_6 * _hexSize * 1.75), (int)(_originY + _hexSize * 4), null);
			g.drawString("L 2:1", (int)(_originX + COS_PI_6 * _hexSize * 1.75), (int)(_originY + _hexSize * 4.70));


			g.drawImage(scaledShip, (int)(_originX - COS_PI_6 * _hexSize * .25), (int)(_originY + _hexSize * 4), null);
			g.drawString("? 3:1", (int)(_originX - COS_PI_6 * _hexSize * .25), (int)(_originY + _hexSize * 4.70));


			g.drawImage(scaledShip, (int)(_originX - COS_PI_6 * _hexSize * 1.35), (int)(_originY + _hexSize * 2.5), null);
			g.drawString("G 2:1", (int)(_originX - COS_PI_6 * _hexSize * 1.35), (int)(_originY + _hexSize * 3.2));


			g.drawImage(scaledShip, (int)(_originX - COS_PI_6 * _hexSize * 1.35), (int)(_originY + _hexSize * 1), null);
			g.drawString("O 2:1", (int)(_originX - COS_PI_6 * _hexSize * 1.35), (int)(_originY + _hexSize * .95));


			// lines to ships
			Graphics2D g2 = (Graphics2D)g;

			float dash1[] = {2.0f};
			plain = new BasicStroke();
			dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

			g2.setStroke(dashed);
			g.drawLine((int)(_originX), (int)(_originY), (int)(_originX), (int)(_originY + _hexSize * .25));
			g.drawLine((int)(_originX), (int)(_originY), (int)(_originX + COS_PI_6 * _hexSize * .5), (int)(_originY));

			g.drawLine((int)(_originX + COS_PI_6 * _hexSize * 2), (int)(_originY), (int)(_originX + COS_PI_6 * _hexSize * 2), (int)(_originY + _hexSize * .25));
			g.drawLine((int)(_originX + COS_PI_6 * _hexSize * 1.5), (int)(_originY), (int)(_originX + COS_PI_6 * _hexSize * 2), (int)(_originY));

			g.drawLine((int)(_originX + COS_PI_6 * _hexSize * 3.5), (int)(_originY + _hexSize * .75), (int)(_originX + COS_PI_6 * _hexSize * 3.5), (int)(_originY + _hexSize * 1));
			g.drawLine((int)(_originX + COS_PI_6 * _hexSize * 3), (int)(_originY + _hexSize * .75), (int)(_originX + COS_PI_6 * _hexSize * 3.5), (int)(_originY + _hexSize * .75));



			g.drawLine((int)(_originX + COS_PI_6 * _hexSize * 4), (int)(_originY + _hexSize * 1.75), (int)(_originX + COS_PI_6 * _hexSize * 4.25), (int)(_originY + _hexSize * 2));
			g.drawLine((int)(_originX + COS_PI_6 * _hexSize * 4), (int)(_originY + _hexSize * 2.25), (int)(_originX + COS_PI_6 * _hexSize * 4.25), (int)(_originY + _hexSize * 2));



			g.drawLine((int)(_originX + COS_PI_6 * _hexSize * 3.5), (int)(_originY + _hexSize * 3), (int)(_originX + COS_PI_6 * _hexSize * 3.5), (int)(_originY + _hexSize * 3.25));
			g.drawLine((int)(_originX + COS_PI_6 * _hexSize * 3), (int)(_originY + _hexSize * 3.25), (int)(_originX + COS_PI_6 * _hexSize * 3.5), (int)(_originY + _hexSize * 3.25));

			g.drawLine((int)(_originX + COS_PI_6 * _hexSize * 2), (int)(_originY + _hexSize * 3.75), (int)(_originX + COS_PI_6 * _hexSize * 2), (int)(_originY + _hexSize * 4));
			g.drawLine((int)(_originX + COS_PI_6 * _hexSize * 1.5), (int)(_originY + _hexSize * 4), (int)(_originX + COS_PI_6 * _hexSize * 2), (int)(_originY + _hexSize * 4));

			g.drawLine((int)(_originX), (int)(_originY + _hexSize * 3.75), (int)(_originX), (int)(_originY + _hexSize * 4));
			g.drawLine((int)(_originX), (int)(_originY + _hexSize * 4), (int)(_originX + COS_PI_6 * _hexSize * .5), (int)(_originY + _hexSize * 4));


			g.drawLine((int)(_originX - COS_PI_6 * _hexSize * .5), (int)(_originY + _hexSize * 1), (int)(_originX - COS_PI_6 * _hexSize * .75), (int)(_originY + _hexSize * 1.25));
			g.drawLine((int)(_originX - COS_PI_6 * _hexSize * .5), (int)(_originY + _hexSize * 1.5), (int)(_originX - COS_PI_6 * _hexSize * .75), (int)(_originY + _hexSize * 1.25));

			g.drawLine((int)(_originX - COS_PI_6 * _hexSize * .5), (int)(_originY + _hexSize * 2.5), (int)(_originX - COS_PI_6 * _hexSize * .75), (int)(_originY + _hexSize * 2.75));
			g.drawLine((int)(_originX - COS_PI_6 * _hexSize * .5), (int)(_originY + _hexSize * 3), (int)(_originX - COS_PI_6 * _hexSize * .75), (int)(_originY + _hexSize * 2.75));

			g2.setStroke(plain);


			int index = 0;
			
			for(AttributeNode node: client.tileList()){
				Image i = null;
				
				switch(node.getResourceNumber()){
					case 1:
						i = scaledOre;
						break;
					case 2:
						i = scaledWool;
						break;
					case 3:
						i = scaledGrain;
						break;
					case 4:
						i = scaledLumber;
						break;
					case 5:
						i = scaledBrick;
						break;
					default:
						i = scaledDesert;
						break;
				}
								
				// tiles and tokens
				switch(index){
					case 0:
						g.drawImage(i, _originX, _originY, null);
						if(node.hasTheif())
							drawRobber(g, 0, 0, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 0, 0);
						break;
					case 1:
						g.drawImage(i, (int)(_originX + COS_PI_6 * _hexSize), _originY, null);
						if(node.hasTheif())
							drawRobber(g, 1, 0, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 1, 0);
						break;
					case 2:
						g.drawImage(i, (int)(_originX + 2 * COS_PI_6 * _hexSize), _originY, null);
						if(node.hasTheif())
							drawRobber(g, 2, 0, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 2, 0);
						break;
					case 3:
						g.drawImage(i, (int)(_originX + COS_PI_6 * _hexSize * 2.5), _originY + (int)(_hexSize * .75), null);
						if(node.hasTheif())
							drawRobber(g, 3, 1, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 3, 1);
						break;
					case 4:
						g.drawImage(i, (int)(_originX + 3 * COS_PI_6 * _hexSize), _originY + (int)(_hexSize * 1.5), null);
						if(node.hasTheif())
							drawRobber(g, 4, 2, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 4, 2);
						break;
					case 5:
						g.drawImage(i, (int)(_originX + COS_PI_6 * _hexSize * 2.5), _originY + (int)(_hexSize * 2.25), null);
						if(node.hasTheif())
							drawRobber(g, 3, 3, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 3, 3);
						break;
					case 6:
						g.drawImage(i, (int)(_originX + 2 * COS_PI_6 * _hexSize), _originY + (int)(_hexSize * 3), null);
						if(node.hasTheif())
							drawRobber(g, 2, 4, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 2, 4);
						break;
					case 7:
						g.drawImage(i, (int)(_originX + COS_PI_6 * _hexSize), _originY + (int)(_hexSize * 3), null);
						if(node.hasTheif())
							drawRobber(g, 1, 4, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 1, 4);
						break;
					case 8:
						g.drawImage(i, (int)(_originX), _originY + (int)(_hexSize * 3), null);
						if(node.hasTheif())
							drawRobber(g, 0, 4, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 0, 4);
						break;
					case 9:
						g.drawImage(i, (int)(_originX - COS_PI_6 * _hexSize * .5), _originY + (int)(_hexSize * 2.25), null);
						if(node.hasTheif())
							drawRobber(g, 0, 3, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 0, 3);
						break;
					case 10:
						g.drawImage(i, (int)(_originX - COS_PI_6 * _hexSize), _originY + (int)(_hexSize * 1.5), null);
						if(node.hasTheif())
							drawRobber(g, 0, 2, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 0, 2);
						break;
					case 11:
						g.drawImage(i, (int)(_originX - COS_PI_6 * _hexSize * .5), _originY + (int)(_hexSize * .75), null);
						if(node.hasTheif())
							drawRobber(g, 0, 1, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 0, 1);
						break;
					case 12:
						g.drawImage(i, (int)(_originX + COS_PI_6 * _hexSize * .5), _originY + (int)(_hexSize * .75), null);
						if(node.hasTheif())
							drawRobber(g, 1, 1, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 1, 1);
						break;
					case 13:
						g.drawImage(i, (int)(_originX + COS_PI_6 * _hexSize * 1.5), _originY + (int)(_hexSize * .75), null);
						if(node.hasTheif())
							drawRobber(g, 2, 1, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 2, 1);
						break;
					case 14:
						g.drawImage(i, (int)(_originX + 2 * COS_PI_6 * _hexSize), _originY + (int)(_hexSize * 1.5), null);
						if(node.hasTheif())
							drawRobber(g, 3, 2, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 3, 2);
						break;
					case 15:
						g.drawImage(i, (int)(_originX + COS_PI_6 * _hexSize * 1.5), _originY + (int)(_hexSize * 2.25), null);
						if(node.hasTheif())
							drawRobber(g, 2, 3, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 2, 3);
						break;
					case 16:
						g.drawImage(i, (int)(_originX + COS_PI_6 * _hexSize * .5), _originY + (int)(_hexSize * 2.25), null);
						if(node.hasTheif())
							drawRobber(g, 1, 3, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 1, 3);
						break;
					case 17:
						g.drawImage(i, (int)(_originX), _originY + (int)(_hexSize * 1.5), null);
						if(node.hasTheif())
							drawRobber(g, 1, 2, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 1, 2);
						break;
					case 18:
						g.drawImage(i, (int)(_originX + COS_PI_6 * _hexSize), _originY + (int)(_hexSize * 1.5), null);
						if(node.hasTheif())
							drawRobber(g, 2, 2, Catan.robberSelected);
						else
							drawToken(g, node.getTrigger(), 2, 2);
						break;
					default:
						
						break;
				}
				
				index++;
				
			}
		} else {
			// hexagon shape
			int[] hexagonX = {0, 0, (int)(COS_PI_6 * _hexSize * .5), (int)(COS_PI_6 * _hexSize),
				(int)(COS_PI_6 * _hexSize), (int)(COS_PI_6 * _hexSize * .5)};
			int[] hexagonY = {(int)(_hexSize * .25), (int)(_hexSize * .75), _hexSize,
				(int)(_hexSize * .75), (int)(_hexSize * .25), 0};
			Polygon hexagon = new Polygon(hexagonX, hexagonY, 6);


			// row 1
			drawPolygon(g, hexagon, Color.RED, true, _originX, _originY);

			drawPolygon(g, hexagon, Color.GREEN, true, (int)(_originX + COS_PI_6 * _hexSize), _originY);

			drawPolygon(g, hexagon, Color.GRAY, true, (int)(_originX + 2 * COS_PI_6 * _hexSize), _originY);


			// row 2
			drawPolygon(g, hexagon, Color.GREEN, true, (int)(_originX - COS_PI_6 * _hexSize * .5), _originY + (int)(_hexSize * .75));

			drawPolygon(g, hexagon, Color.RED, true, (int)(_originX + COS_PI_6 * _hexSize * .5), _originY + (int)(_hexSize * .75));

			drawPolygon(g, hexagon, Color.ORANGE, true, (int)(_originX + COS_PI_6 * _hexSize * 1.5), _originY + (int)(_hexSize * .75));

			drawPolygon(g, hexagon, new Color(34, 139, 34), true, (int)(_originX + COS_PI_6 * _hexSize * 2.5), _originY + (int)(_hexSize * .75));


			// row 3
			drawPolygon(g, hexagon, Color.GRAY, true, (int)(_originX - COS_PI_6 * _hexSize), _originY + (int)(_hexSize * 1.5));

			drawPolygon(g, hexagon, new Color(34, 139, 34), true, _originX, _originY + (int)(_hexSize * 1.5));

			drawPolygon(g, hexagon, Color.WHITE, true, (int)(_originX + COS_PI_6 * _hexSize), _originY + (int)(_hexSize * 1.5));

			drawPolygon(g, hexagon, Color.RED, true, (int)(_originX + COS_PI_6 * _hexSize * 2), _originY + (int)(_hexSize * 1.5));

			drawPolygon(g, hexagon, Color.GREEN, true, (int)(_originX + COS_PI_6 * _hexSize * 3), _originY + (int)(_hexSize * 1.5));


			// row 4
			drawPolygon(g, hexagon, Color.RED, true, (int)(_originX - COS_PI_6 * _hexSize * .5), _originY + (int)(_hexSize * 2.25));

			drawPolygon(g, hexagon, Color.ORANGE, true, (int)(_originX + COS_PI_6 * _hexSize * .5), _originY + (int)(_hexSize * 2.25));

			drawPolygon(g, hexagon, new Color(34, 139, 34), true, (int)(_originX + COS_PI_6 * _hexSize * 1.5), _originY + (int)(_hexSize * 2.25));

			drawPolygon(g, hexagon, Color.GRAY, true, (int)(_originX + COS_PI_6 * _hexSize * 2.5), _originY + (int)(_hexSize * 2.25));


			// row 5
			drawPolygon(g, hexagon, Color.GREEN, true, (int)(_originX), _originY + (int)(_hexSize * 3));

			drawPolygon(g, hexagon, Color.GRAY, true, (int)(_originX + COS_PI_6 * _hexSize), _originY + (int)(_hexSize * 3));

			drawPolygon(g, hexagon, new Color(34, 139, 34), true, (int)(_originX + COS_PI_6 * _hexSize * 2), _originY + (int)(_hexSize * 3));
		}
	}

	/**
	 * 
	 * @param g
	 * @param p
	 * @param c
	 * @param border
	 */
	static void drawPolygon(Graphics g, Polygon p, Color c, boolean border){
		drawPolygon(g, p, c, border, 0, 0);
	}

	/**
	 * 
	 * @param g
	 * @param p
	 * @param c
	 * @param border
	 * @param translateX
	 * @param translateY
	 */
	static void drawPolygon(Graphics g, Polygon p, Color c, boolean border, int translateX, int translateY){
		p.translate(translateX, translateY);

		g.setColor(c);
		g.fillPolygon(p);

		if(border){
			g.setColor(Color.BLACK);
			g.drawPolygon(p);
		}

		p.translate(-translateX, -translateY);
	}

	/**
	 * 
	 * @param me
	 * @param x
	 * @param y
	 * @return
	 */
	static boolean robberClicked(MouseEvent me, int x, int y){
		int[] PolyX = {-(int)(_hexSize / 6),
				(int)(_hexSize / 6),
				(int)(_hexSize / 6),
				-(int)(_hexSize / 6)};
		
		int[] PolyY = {(int)(_hexSize / 3),
				(int)(_hexSize / 3),
				-(int)(_hexSize / 3),
				-(int)(_hexSize / 3)};
		
		
		Polygon p = new Polygon(PolyX, PolyY, 4);
		p.translate((int)(_originX + hexOffsetsX[y][x] * COS_PI_6 * _hexSize), (int)(_originY + hexOffsetsY[y][x] * _hexSize));
				
		return p.contains(me.getX(), me.getY());
	}
	
	/**
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	static void drawRobber(Graphics g, int x, int y){
		drawRobber(g, x, y, false);
	}
	
	/**
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param selected
	 */
	static void drawRobber(Graphics g, int x, int y, boolean selected){
		if(updateSize){
			robberSmall = robberImg2.getScaledInstance((int)(_hexSize / 3.5), (int)(_hexSize / 1.75), Image.SCALE_SMOOTH);
			robberBig = robberImg2.getScaledInstance((int)(_hexSize / 3), (int)(_hexSize / 1.5), Image.SCALE_SMOOTH);
		}
		if(selected){
			g.drawImage(robberSmall,
					(int)(_originX + hexOffsetsX[y][x] * COS_PI_6 * _hexSize - _hexSize / 7),
					(int)(_originY + hexOffsetsY[y][x] * _hexSize - _hexSize / 3.5), null);
		} else {
			g.drawImage(robberBig,
					(int)(_originX + hexOffsetsX[y][x] * COS_PI_6 * _hexSize - _hexSize / 6),
					(int)(_originY + hexOffsetsY[y][x] * _hexSize - _hexSize / 3), null);
		}
	}
	
	/**
	 * 
	 * @param g
	 * @param num
	 * @param x
	 * @param y
	 */
	static void drawToken(Graphics g, int num, int x, int y){
		Font tokenFont = new Font(Font.SERIF,Font.PLAIN, (int)(_hexSize / 5));

		int xorigin = (int)(_originX + hexOffsetsX[y][x] * COS_PI_6 * _hexSize);
		int yorigin = (int)(_originY + hexOffsetsY[y][x] * _hexSize);

		g.setColor(new Color(200, 200, 150));
		g.fillOval(xorigin - (int)(_hexSize / 3 / 2), yorigin - (int)(_hexSize / 3 / 2),
				(int)(_hexSize / 3), (int)(_hexSize / 3));

		Color c;
		if(num == 6 || num == 8){
			c = Color.RED;
		} else {
			c = Color.BLACK;
		}
		drawCenteredText(g, c, tokenFont, String.valueOf(num),
			xorigin - (int)(_hexSize / 4 / 2), yorigin - (int)(_hexSize / 7),
			(int)(_hexSize / 4), (int)(_hexSize / 4));
	}

	/**
	 * 
	 * @param g
	 * @param c
	 * @param x
	 * @param y
	 */
	static void drawCity(Graphics g, Color c, int x, int y){
		drawPolygon(g, city, c, true,
			(int)(_originX + vertexOffsetsX[y][x] * COS_PI_6 * _hexSize),
			(int)(_originY + vertexOffsetsY[y][x] * _hexSize));
	}
	
	/**
	 * 
	 * @param g
	 * @param c
	 * @param x
	 * @param y
	 */
	static void drawSettlement(Graphics g, Color c, int x, int y){
		drawPolygon(g, settlement, c, true,
			(int)(_originX + vertexOffsetsX[y][x] * COS_PI_6 * _hexSize),
			(int)(_originY + vertexOffsetsY[y][x] * _hexSize));
	}

	/**
	 * 
	 * @param g
	 * @param c
	 * @param x
	 * @param y
	 */
	static void drawRoad(Graphics g, Color c, int x, int y){
		Polygon p;
		if(y % 2 == 1){
			p = road360;
		} else if(x % 2 == 0){
			if(y > 5){
				p = road120;
			} else {
				p = road240;
			}
		} else {
			if(y > 5){
				p = road240;
			} else {
				p = road120;
			}
		}

		drawPolygon(g, p, c, true,
			(int)(_originX + roadOffsetsX[y][x] * COS_PI_6 * _hexSize),
			(int)(_originY + roadOffsetsY[y][x] * _hexSize));
	}
	
	/**
	 * 
	 * @param g
	 * @param c
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	static void drawRoad(Graphics g, Color c, int x1, int y1, int x2, int y2){
		Polygon p = road360;
				
		double slope = (vertexOffsetsY[y1][x1] - vertexOffsetsY[y2][x2]) /
				(double)(vertexOffsetsX[y1][x1] - vertexOffsetsX[y2][x2]);
		
		if(Double.isInfinite(slope)){
			p = road360;			
		} else if(slope < 0){
			p = road240;
		} else if(slope > 0){
			p = road120;
		}
		
		drawPolygon(g, p, c, true,
			(int)(_originX + (vertexOffsetsX[y1][x1] + vertexOffsetsX[y2][x2]) * .5 * COS_PI_6 * _hexSize),
			(int)(_originY + (vertexOffsetsY[y1][x1] + vertexOffsetsY[y2][x2]) * .5 * _hexSize));
	}

	/**
	 * 
	 * @param g
	 * @param c
	 * @param f
	 * @param text
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	static void drawCenteredText(Graphics g, Color c, Font f, String text, int x, int y, int width, int height){
		FontMetrics fontMetrics = g.getFontMetrics(f);
		if(fontMetrics != null && f != null && g != null && text != null){
			Rectangle2D bounds = fontMetrics.getStringBounds(text, g);
			g.setColor(c);
			g.setFont(f);
			g.drawString(text, (int)(x + (width - bounds.getWidth()) / 2), (int)(y + height - (height - bounds.getHeight()) / 2));
		}
	}
	/**
	 * 
	 * @param g
	 * @param c
	 * @param f
	 * @param text
	 * @param x
	 * @param y
	 * @param height
	 */
	static void drawCenteredHeightText(Graphics g, Color c, Font f, String text, int x, int y, int height){
		FontMetrics fontMetrics = g.getFontMetrics(f);
		Rectangle2D bounds = fontMetrics.getStringBounds(text, g);
		g.setColor(c);
		g.setFont(f);
		g.drawString(text, x, (int)(y + height - (height - bounds.getHeight()) / 2));
	}

	/**
	 * 
	 * @param me
	 * @return
	 */
	static int[] clickRoad(MouseEvent me){
		int[] ret = {-1, 1};

		for(int y = 0; y < 11; y++){
			for(int x = 0; x < roadOffsetsX[y].length; x++){
				Polygon p;
				if(y % 2 == 1){
					p = road360;
				} else if(x % 2 == 0){
					if(y > 5){
						p = road120;
					} else {
						p = road240;
					}
				} else {
					if(y > 5){
						p = road240;
					} else {
						p = road120;
					}
				}

				if(p.contains(me.getX() - _originX - roadOffsetsX[y][x] * COS_PI_6 * _hexSize,
					me.getY() - _originY - roadOffsetsY[y][x] * _hexSize)){

					ret[0] = y;
					ret[1] = x;
					return ret;
					//roadState[y][x] = !roadState[y][x];
				}
			}
		}
		return ret;
	}

	/**
	 * 
	 * @param me
	 * @return
	 */
	static int[] clickVertex(MouseEvent me){
		int[] ret = {-1, 1};

		for(int y = 0; y < 12; y++){
			Polygon p;
			if(y % 2 == 0){
				p = hex1;
			} else {
				p = hex2;
			}

			for(int x = 0; x < vertexOffsetsX[y].length; x++){
				if(p.contains(me.getX() - _originX - vertexOffsetsX[y][x] * COS_PI_6 * _hexSize,
					me.getY() - _originY - vertexOffsetsY[y][x] * _hexSize)){

					ret[0] = y;
					ret[1] = x;
					return ret;
				}
			}
		}
		return ret;
	}
 
	static void drawCard(Graphics g, int card, int x, int y){
		// card, 0 is nothing, 1 is brick, 2 grain, 3 lumber, 4 ore, 5 wool
		// 6 is 2free roads, 7 is knight, 8 is largest army, 9 is longest road
		// 10 is monopoly, 11 is +1VP, 12 is Year of Plenty
		switch(card){
			case 1:
				g.drawImage(scaledOreCard, x, y, null);
				break;
			case 2:
				g.drawImage(scaledWoolCard, x, y, null);
				break;
			case 3:
				g.drawImage(scaledGrainCard, x, y, null);
				break;
			case 4:
				g.drawImage(scaledLumberCard, x, y, null);
				break;
			case 5:
				g.drawImage(scaledBrickCard, x, y, null);
				break;
			case 6:
				g.drawImage(scaledFreeRoadsCard, x, y, null);
				break;
			case 7:
				g.drawImage(scaledKnightCard, x, y, null);
				break;
			case 8:
				g.drawImage(scaledLargestArmyCard, x, y, null);
				break;
			case 9:
				g.drawImage(scaledLongestRoadCard, x, y, null);
				break;
			case 10:
				g.drawImage(scaledMonopolyCard, x, y, null);
				break;
			case 11:
				g.drawImage(scaledOneVPCard, x, y, null);
				break;
			case 12:
				g.drawImage(scaledPlentyCard, x, y, null);
				break;
			default:
				break;
		}		
		
	}
	
	/**
	 * 
	 * @param g
	 */
	static void drawFilter(Graphics g){
		g.drawImage(filter2, 0, 0, null);
	}
	
	/**
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param name
	 * @param vp
	 * @param dev
	 * @param army
	 * @param c
	 */
	static void drawUserBlock(Graphics g, int x, int y, String name,
			int vp, int dev, int army, Color c){
		
		Font f = new Font(Font.SERIF, Font.PLAIN, 24);
		Font f2 = new Font(Font.SERIF, Font.PLAIN, 16);
		
		g.drawImage(scaledUserDisplayBlock, x, y, null);
		
		drawCenteredText(g, c, f, name, x + 5, y, 230, 90);

		// stats
		drawCenteredHeightText(g, c, f2, "VP:", x + 270, y + 10, 20);
		drawCenteredHeightText(g, c, f2, "DEV:", x + 270, y + 30, 20);
		drawCenteredHeightText(g, c, f2, "ARMY:", x + 270, y + 50, 20);
		
		drawCenteredHeightText(g, c, f2, "" + vp, x + 270 + 70, y + 10, 20);
		drawCenteredHeightText(g, c, f2, "" + dev, x + 270 + 70, y + 30, 20);
		drawCenteredHeightText(g, c, f2, "" + army, x + 270 + 70, y + 50, 20);
		
	}
	
	/**
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	static void drawPanel(Graphics g, int x, int y){
		g.drawImage(panel2, x, y, null);
	}
	
	/**
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	static void drawGradient(Graphics g, int x, int y){
		g.drawImage(gradient2, x, y, null);
	}
	
	/**
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	static void drawLogo(Graphics g, int x, int y){
		g.drawImage(scaledLogo, x, y, null);
	}
	
	/**
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	static void drawWater(Graphics g, int x, int y){
		g.drawImage(water2, x, y, null);
	}
}
