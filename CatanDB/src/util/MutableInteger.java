package util;

/**
 * Used to modify integers stored in a list without adding and removing from
 * the list
 * 
 * @author Michael Tuer
 *
 */
public class MutableInteger {
	int i;
	public MutableInteger(int i){
		this.i = i;
	}
	public int getInt(){
		return i;
	}
	public void setInt(int i){
		this.i = i;
	}
}
