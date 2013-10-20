package devCards;
/** Standard card class 
 * @author Wes
 * @author Xin Zhang */
public class Card {

	int data; //Contains the card number the card is
	/* 0 - Knight 1 - Progress 2 - Victory Point */
	public Card(int d){
		data = d;
	}
	
	/** Set's the card type	
	 * @return Number set in */
	public int setType( int x ){
		data = x;
		return data;
	}
	
	/** @return Returns card type number **/
	public int getData(){
		return data;
	}
}