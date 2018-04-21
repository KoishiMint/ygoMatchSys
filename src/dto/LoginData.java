package dto;

public class LoginData {
	private String userName;
	private String userID;
	private String IP;
	private int wrongTime = 0;
	private String userType;
	private String lastIP;
	private String lastloginDate;
	private int firstLogin;
	
	public int getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(int firstLogin) {
		this.firstLogin = firstLogin;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getLastIP() {
		return lastIP;
	}
	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}
	public String getLastloginDate() {
		return lastloginDate;
	}
	public void setLastloginDate(String lastloginDate) {
		this.lastloginDate = lastloginDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public int getWrongTime() {
		return wrongTime;
	}
	public void setWrongTime(int wrongTime) {
		this.wrongTime = wrongTime;
	}
}
