package util.messages;
/**
 * Message class for informing the client that the request (add friend, leave game, send message
 * accept friend) was either accepted or rejected
 * 
 * @author jwwebber
 */
public class ResultOfRequest extends Message{

	/**
	 * Initial creation: Serial UID 1L
	 */
	private static final long serialVersionUID = 1L;
	boolean requestAccepted;
	public MessageTypes mType;
	
	public ResultOfRequest(boolean requestAcceptedStatus, MessageTypes mType) {
		requestAccepted = requestAcceptedStatus;
		this.mType = mType;
	}
	
	/**
	 * @return the requestAccepted
	 */
	public boolean isRequestAccepted() {
		return requestAccepted;
	}

	/**
	 * @param requestAccepted the requestAccepted to set
	 */
	public void setRequestAccepted(boolean requestAccepted) {
		this.requestAccepted = requestAccepted;
	}

	/**
	 * @return the mType
	 */
	public MessageTypes getmType() {
		return mType;
	}

	/**
	 * @param mType the mType to set
	 */
	public void setmType(MessageTypes mType) {
		this.mType = mType;
	}

}
