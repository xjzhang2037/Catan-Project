package CS3141;

import java.awt.*;
import java.awt.event.MouseEvent;
import CS3141.Button.ButtonImage;
import catanNodes.*;

/**
 * 
 * @author csdoig
 *
 */
public class TradeMenu {
	private int x;
	private int y;
	private int width;
	private int height;
	
	private boolean show = false;
	
	Button accept;
	Button reject;
	Button bank;
	
	int[] give = {0, 0, 0, 0, 0};
	int[] have = {0, 0, 0, 0, 0};
	int[] receive = {0, 0, 0, 0, 0};
	
	CatanBoard_vClient client;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public TradeMenu(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		accept = new Button("✓", new Font(Font.SERIF, Font.PLAIN, 20), new Color(200, 200, 200), new Color(100, 220, 100), x + 50, y + 195 - 70, 25, 25, false);
		accept.setImage(ButtonImage.CHECK);
		reject = new Button("X", new Font(Font.SERIF, Font.PLAIN, 20), new Color(200, 200, 200), Color.RED, x + 120, y + 195 - 70, 25, 25, false);
		reject.setImage(ButtonImage.REJECT);
		bank = new Button("BANK", new Font(Font.SERIF, Font.PLAIN, 20), new Color(200, 200, 200), Color.BLACK, x + 190, y + 195 - 70, 75, 25, false);
		bank.setImage(ButtonImage.BANK);
		
		this.client = Catan.vClient;
	}
	
	/**
	 * 
	 * @param state
	 */
	public void setVisible(boolean state){
		show = state;
		if(!show){
			for(int i = 0; i < 5; i++){
				give[i] = 0;
				receive[i] = 0;
			}
		}
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
	 * @param me
	 * @return
	 */
	public boolean isDepressed(MouseEvent me){
		if(isVisible()){
			if(accept.isDepressed(me)){
				
			}
			if(reject.isDepressed(me)){
				
			}
			if(bank.isDepressed(me)){
				
			}
		}
		return false;
	}
	
	/**
	 * 
	 */
	public void released(){
		accept.released();
		reject.released();
		bank.released();
	}
	
	/**
	 * 
	 * @param me
	 * @return
	 */
	public boolean isClicked(MouseEvent me){
		int[] resources = Catan.vClient.getPlayerResc();
		
		if(isVisible()){
			for(int index = 0; index < 5; index++){
				if(me.getX() > x + 50 * index + 20 && me.getX() < x + 50 * index + 20 + 40 &&
						me.getY() > y + 35 && me.getY() < y + 35 + 60){
					
					if(me.getButton() == MouseEvent.BUTTON1){
						receive[index]++;
					} else if(me.getButton() == MouseEvent.BUTTON3){
						receive[index]--;
						if(receive[index] < 0){
							receive[index] = 0;
						}
					}
				}

				if(me.getX() > 300 + 50 * index + 20 && me.getX() < 300 + 50 * index + 20 + 40 &&
						me.getY() > Catan._height - 100 && me.getY() < Catan._height - 100 + 60){
					
					if(me.getButton() == MouseEvent.BUTTON1){
						if(resources[index] - give[index] > 0){
							give[index]++;
						}
					} else if(me.getButton() == MouseEvent.BUTTON3){
						give[index]--;
						if(give[index] < 0){
							give[index] = 0;
						}
					}
				}
			}
			
			if(accept.isClicked(me)){
				Integer[] toGive = new Integer[5];
				Integer[] toReceive = new Integer[5];
				for(int i = 0; i < 5; i++){
					toGive[i] = Integer.valueOf(give[i]);
					toReceive[i] = Integer.valueOf(receive[i]);
				}
				Catan.vClient.send_proposedContract(toGive, toReceive);
			}
			if(reject.isClicked(me)){
				for(int i = 0; i < 5; i++){
					give[i] = 0;
					receive[i] = 0;
				}
				setVisible(false);
			}
			if(bank.isClicked(me)){
				Integer[] toGive = new Integer[5];
				Integer[] toReceive = new Integer[5];
				for(int i = 0; i < 5; i++){
					toGive[i] = Integer.valueOf(give[i]);
					toReceive[i] = Integer.valueOf(receive[i]);
				}
				Catan.vClient.send_proposedServerContract(toGive, toReceive);
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param g
	 */
	public void draw(Graphics g){
		if(show){
			
			int[] resources = Catan.vClient.getPlayerResc();
			for(int index = 0; index < 5; index++){
				if(resources[index] - give[index] < 0){
					for(int i = 0; i < 5; i++){
						give[i] = 0;
						receive[i] = 0;
					}
					break;
				}
			}
			
			Font f2 = new Font(Font.SERIF, Font.PLAIN, 18);

			g.setColor(new Color(255, 255, 200));
			g.fillRect(x, y, width, height);

			g.setColor(Color.BLACK);
			g.drawRect(x + 5, y + 230 - 70, width - 10, 115);
			g.drawRect(x + 5, y + 225 - 150 - 70, width - 10, 115);
			
			
			g.drawString("Receive", x + 10, y + 90 - 70);
			g.drawString("Give", x + 10, y + 245 - 70);

			accept.draw(g);
			reject.draw(g);
			bank.draw(g);
			
			
			for(int index = 0; index < 5; index++){
				CatanGraphics.drawCard(g, index + 1, x + 50 * index + 20, y + 105 - 70);
				CatanGraphics.drawCenteredText(g, Color.BLACK, f2, "" + receive[index], x + 50 * index + 20, y + 105 + 60 - 70, 40, 20);
				CatanGraphics.drawCard(g, index + 1, x + 50 * index + 20, y + 260 - 70);
				CatanGraphics.drawCenteredText(g, Color.BLACK, f2, "" + give[index], x + 50 * index + 20, y + 260 + 60 - 70, 40, 20);
			}
		}
	}
}