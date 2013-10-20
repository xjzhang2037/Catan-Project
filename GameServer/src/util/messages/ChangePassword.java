package util.messages;

/**
 * Message for changing a user's password
 * 
 * @author Dan Larson
 * 
 */
public class ChangePassword extends Message {

	private static final long serialVersionUID = 1L;
	private String newPassword;

	public ChangePassword(String newPassword) {
		setNewPassword(newPassword);
	}

	/**
	 * Get new password
	 * 
	 * @return new password
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Set new password
	 * 
	 * @param password
	 */
	public void setNewPassword(String password) {
		this.newPassword = password;
	}
}
