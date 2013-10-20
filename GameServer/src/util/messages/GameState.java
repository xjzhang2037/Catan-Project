package util.messages;

import java.io.Serializable;

/**
 * Message for GameState
 * 
 * @author Dan Larson
 * 
 */
public class GameState extends Message {

	/**
	 * Initial creation: Serial UID 1L
	 */
	private static final long serialVersionUID = 1L;
	Object obj;

	public GameState(Serializable inputObj) {
		obj = inputObj;
	}

	/**
	 * Get object
	 * 
	 * @return
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * Set object
	 * 
	 * @param obj
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * toString method for GameState
	 */
	public String toString() {
		return "Message GameState:\n" + obj + "\nEnd GameState";
	}

}
