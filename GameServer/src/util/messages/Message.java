package util.messages;

import java.io.Serializable;

/**
 * Basic outline for any messages
 * 
 * @author Dan Larson
 * 
 */
public abstract class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	public Message() {

	}

	/**
	 * Set time
	 * 
	 * @return
	 */
	public long setTime() {
		return System.currentTimeMillis();
	}
}
