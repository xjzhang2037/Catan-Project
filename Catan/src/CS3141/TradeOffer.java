package CS3141;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import CS3141.Button.ButtonImage;

import util.player.*;

/**
 * 
 * @author csdoig
 *
 */
public class TradeOffer {

	Player offerer;
	int x;
	int y;
	int width;
	int height;
	Integer[] offer;
	Integer[] receive;
	
	Button accept;
	Button reject;
	
	Boolean acceptTrade = null; // null - nothing, true - accept, false - reject
	
	private boolean show = false;
	
	util.TradeContract contract = null;
	
	/**
	 * 
	 * @param offerer
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param offer
	 * @param receive
	 */
	public TradeOffer(Player offerer, int x, int y, int width, int height, Integer[] offer, Integer[] receive){
		this.offerer = offerer;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.offer = offer;
		this.receive = receive;
		
		accept = new Button("✓", new Font(Font.SERIF, Font.PLAIN, 20), new Color(200, 200, 200), new Color(100, 220, 100), width - x - 25 - 10, y + 30, 25, 25, false);
		accept.setImage(ButtonImage.CHECK);
		reject = new Button("X", new Font(Font.SERIF, Font.PLAIN, 20), new Color(200, 200, 200), Color.RED, width - x - 25 - 10, y + 30 + 30, 25, 25, false);
		reject.setImage(ButtonImage.REJECT);
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
	 * @param g
	 */
	public void draw(Graphics g){
		if(contract != null && !contract.equals(Catan.vClient.curContract)){
			acceptTrade = null;
			contract = Catan.vClient.curContract;
		}
	
		if(isVisible()){
			Font f2 = new Font(Font.SERIF, Font.PLAIN, 18);
			
			g.setColor(new Color(255, 255, 200));
			g.fillRect(x, y, width, height);
			
			if(offerer == null){
				CatanGraphics.drawCenteredHeightText(g, Color.BLACK, f2, "Offer from NULL", x + 20, y, 20);
			} else {
				CatanGraphics.drawCenteredHeightText(g, Color.BLACK, f2, "Offer from " + offerer.getName(), x + 20, y, 20);
			}
			
			for(int index = 0; index < 5; index++){
				CatanGraphics.drawCard(g, index + 1, x + 50 * index + 20, y + 30);
				CatanGraphics.drawCenteredText(g, Color.BLACK, f2, "" + offer[index], x + 50 * index + 20, y + 90, 40, 20);
				
				CatanGraphics.drawCard(g, index + 1, x + 50 * index + 320, y + 30);
				CatanGraphics.drawCenteredText(g, Color.BLACK, f2, "" + receive[index], x + 50 * index + 320, y + 90, 40, 20);
			}
			
			CatanGraphics.drawCenteredText(g, Color.BLACK, f2, "for", 260, y, 60, height - 10);
			
			accept.draw(g);
			reject.draw(g);
		}
	}

	/**
	 * 
	 * @param me
	 * @return
	 */
	public boolean isDepressed(MouseEvent me) {
		if(isVisible()){
			if(accept.isDepressed(me)){
				
			}
			if(reject.isDepressed(me)){
				
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param me
	 * @return
	 */
	public boolean isClicked(MouseEvent me) {
		if(isVisible()){
			if(accept.isClicked(me)){
				setVisible(false);
				Catan.vClient.send_acceptTrade();
				acceptTrade = Boolean.TRUE;
				
				contract = null;
				Catan.vClient.curContract = null;
				//for(int i = 0; i < 5; i++){
				//	Catan.tradeMenu.give[i] = 0;
				//}
			}
			if(reject.isClicked(me)){
				setVisible(false);
				acceptTrade = Boolean.FALSE;
				
				contract = null;
				Catan.vClient.curContract = null;
			}
		}
		return false;
	}
	
	/**
	 * 
	 */
	public void released() {
		accept.released();
		reject.released();
	}
	
	/**
	 * 
	 * @param p
	 */
	public void setOfferer(Player p){
		offerer = p;
	}
	
	/**
	 * 
	 */
	public void reset(){
		acceptTrade = null;
		contract = null;
	}
}
