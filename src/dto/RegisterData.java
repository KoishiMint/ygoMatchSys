package dto;

public class RegisterData {
	/*
	 * @wrongReason
	 *  0 : success
	 *  1 : already has this user
	 *  2 : userName or userPassword not allowed
	 *  3 : unknown reason
	 */
	private String userName;
	private String password;
	private int wrongReason;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getWrongReason() {
		return wrongReason;
	}
	public void setWrongReason(int wrongReason) {
		this.wrongReason = wrongReason;
	}
	
}
