package devCards;
import java.util.Random;
/** Deck Stack structure for the dev cards in Catan
 * @author Wes
 * @author Xin Zhang */

public class CardArray {

	/* 25 Development Cards (14 Knight/Soldier Cards, 6 Progress Cards, 5 Victory Point Cards)
	 *  knight = 0, progress = 1, victory point = 2  */
	
	private int[] cards;
	private Random r = new Random();
	private int drawOne = 0; // Stack size
	
	/** Sets up the development card deck.
	 * The cards are shuffled with each call.	 */
	public CardArray(){
		cards = new int[25];
		
		int count = 0; 							//keeps track of what card it is in the array
		int kni = 14; 							//total amount of knights
		int pro = 6; 							//total amount of progress
		int vic = 5; 							//total amount of victory
		int rand; 								//random number
		while( count < 25 ){
			r.setSeed(System.nanoTime());
			rand = Math.abs(r.nextInt()%3);
			Card c = new Card(rand);			//creates a card
			if(kni != 0 && rand == 0 ){			//Adds a knight to the deck ( flipped direction for shorter circuit )
				cards[count] = c.setType( 0 ); count++;		// Increment index
				kni--;							// Decrement total knight count
			}else if(pro != 0 & rand == 1 ){	//Adds a progress to the deck
				cards[count] = c.setType( 1 ); count++;
				pro--;
			}else if(vic != 0 && rand == 2 ){	//Adds a victory to the deck
				cards[count] = c.setType( 2 ); count++;
				vic--;
			}drawOne = count-1; // Sets the size to 24 ( Includes 0 )
	}}
	
	/**Draws one card from the top of the deck
	 * @return then number that the card is(0-2), when it runs out it returns -9001*/
	public int draw() {
			return ( drawOne >= 0 ) ? cards[drawOne--] : -9001;
	}
	
	/** @return the name of the card	 */
	public String getName( int data ){
		String s = "";
		if(data == 0){
			s = "Knight/Soldier Card";
		}else if(data == 1){
			s = "Progress Card";
		}else if(data == 2){
			s = "Victory Point Card";
		}
		return s;
	}
	
	public boolean isEmpty(){
		return (drawOne < 0);
	}

}