package util.messages;

/**
 * Message sent form the client to indicate the user is leaving the game
 * 
 * @author jwwebber
 */
public class LeaveGame extends Message {

	private static final long serialVersionUID = 1L;
	public boolean didInFactLeaveGame;

	public LeaveGame() {
		didInFactLeaveGame = true;
	}
}
