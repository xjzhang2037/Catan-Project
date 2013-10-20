package util.messages;

import java.io.Serializable;

/**
 * Client -> Server
 * 
 * @author mttuer
 * 
 */
public class GameFunction extends Message {

	/**
	 * Initial creation: Serial UID 1L
	 */
	private static final long serialVersionUID = 1L;
	Object obj;
	public transient int userid;

	public GameFunction(Serializable inputObj) {
		obj = inputObj;
	}

	/**
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param obj
	 *            the obj to set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
