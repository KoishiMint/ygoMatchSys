package dto;

public class MatchData {
	private String macName;
	private String macTime;
	private String macLogger;
	private int macID;
	private String macStat;
	private String macInfo;
	private int macType;
	private String regTime;
	private int regNum;
	private int banlist;
	
	public int getBanlist() {
		return banlist;
	}
	public void setBanlist(int banlist) {
		this.banlist = banlist;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public int getRegNum() {
		return regNum;
	}
	public void setRegNum(int regNum) {
		this.regNum = regNum;
	}
	public String getMacName() {
		return macName;
	}
	public void setMacName(String macName) {
		this.macName = macName;
	}
	public String getMacTime() {
		return macTime;
	}
	public void setMacTime(String macTime) {
		this.macTime = macTime;
	}
	public String getMacLogger() {
		return macLogger;
	}
	public void setMacLogger(String macLogger) {
		this.macLogger = macLogger;
	}
	public int getMacID() {
		return macID;
	}
	public void setMacID(int macID) {
		this.macID = macID;
	}
	public String getMacStat() {
		return macStat;
	}
	public void setMacStat(String macStat) {
		this.macStat = macStat;
	}
	public String getMacInfo() {
		return macInfo;
	}
	public void setMacInfo(String macInfo) {
		this.macInfo = macInfo;
	}
	public int getMacType() {
		return macType;
	}
	public void setMacType(int macType) {
		this.macType = macType;
	}
}
