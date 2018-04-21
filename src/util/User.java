package util;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import c3p0.DataObject;
import dto.LoginData;
import dto.RegisterData;

/*
 * @HttpSession
 *  @key userName
 *  @key userID
 *  @key IP
 * 
 * @table user
 *  @row userName  varchar 					pk
 *  @row password  varchar
 *  @row userID    int	 auto increased 	pk
 *  @row userType  varchar
 *    0 : superadmininster
 *    1 : register user
 *    2 : admin
 *  @row isEnabled int 
 *    0 : enabled
 *    1 : disabled
 *    
 *  @table login
 *   @row loginDate date  		pk
 *   @row userName  varchar		pk
 *   @row userID	int			pk
 *   @row IP    	varchar
 *   @row success	int
 */
public class User {
	static Logger log4j = Logger.getLogger(User.class.getName());
	
	public LoginData checkLogin(){
		WebContext wc = WebContextFactory.get();
		HttpSession hs = wc.getSession();
		LoginData lcd = new LoginData();
		if(hs.getAttribute("userName") != null && hs.getAttribute("userID") != null && hs.getAttribute("userName") != "" && hs.getAttribute("userID") != ""){
			lcd.setUserID((String) hs.getAttribute("userID"));
			lcd.setUserName((String) hs.getAttribute("userName"));
			lcd.setIP((String) hs.getAttribute("IP"));
			lcd.setLastIP((String) hs.getAttribute("lastIP"));
			lcd.setWrongTime(0);
			lcd.setUserType((String) hs.getAttribute("userType"));
			lcd.setLastloginDate((String) hs.getAttribute("lastloginDate"));
			lcd.setFirstLogin(0);
			return lcd;
		} else {
			lcd.setUserID("");
			lcd.setUserName("");
			lcd.setIP("");
			lcd.setLastIP("");
			lcd.setUserType("");
			lcd.setWrongTime(-1);
			lcd.setLastloginDate(null);
			lcd.setFirstLogin(1);
			return lcd;
		}
	}
	
	public LoginData login(String userName,String password){
/*
 * @return
 *  @key IP
 *  @key userName
 *  @key wrongTime
 */
		LoginData lcd = checkLogin();
		if(!lcd.getUserID().equals("")){
			LoginData lda = new LoginData();
			lda.setIP(getIP());
			lda.setUserName(lcd.getUserName());
			lda.setUserType(lcd.getUserType());
			lda.setWrongTime(0);
			lda.setLastIP(lcd.getLastIP());
			lda.setLastloginDate(lcd.getLastloginDate());
			lda.setFirstLogin(0);
			return lda;			
		}
		password = c3p0.ExcuteSQL.checkSQLInjection(password);
		userName = c3p0.ExcuteSQL.checkSQLInjection(userName);
		String sql = "select count(*) from user where userName = '" + userName 
				+ "' and password = md5('" + password + "')"
				+ " and isEnabled = 0;";
		DataObject dao = c3p0.ExcuteSQL.excuteSelect(sql);
		LoginData ld = new LoginData();
		try{
			if(dao.mark == true){
				if(dao.rs.next()){
					if(dao.rs.getInt(1) == 1){
						ld.setUserName(userName);
						HttpSession hs = WebContextFactory.get().getSession();
						hs.setAttribute("IP",getIP());
						ld.setIP(getIP());
						hs.setAttribute("userName", userName);
						String [] idAndType = getUserIDandType(userName, password);
						if(idAndType[0].equals("")){
							ld.setWrongTime(-1);
							return ld;
						}
						hs.setAttribute("userID", idAndType[0]);
						ld.setUserID(idAndType[0]);
						hs.setAttribute("userType",idAndType[1]);
						ld.setUserType(idAndType[1]);
						sql = "select loginDate,IP from login where userName = '" + userName
								+ "' and userID = '" + idAndType[0]
								+ "' order by loginDate desc "
								+ "limit 1";
						DataObject dao1 = c3p0.ExcuteSQL.excuteSelect(sql);
						if(dao1.mark == true){
							if(dao1.rs.next()){
								ld.setLastIP(dao1.rs.getString("IP"));
								hs.setAttribute("lastIP", dao1.rs.getString("IP"));
								ld.setLastloginDate(dao1.rs.getDate("loginDate").toString());
								hs.setAttribute("lastloginDate", dao1.rs.getDate("loginDate").toString());
							} else {
								ld.setLastIP("初次登陆");
								ld.setLastloginDate("初次登陆");
							}
							sql = "insert into login values(now(),'" + userName + "',"
									+ hs.getAttribute("userID") + ",'" + getIP() + "',1)";
							DataObject dao2 = c3p0.ExcuteSQL.excuteUpdate(sql);
							if(dao2.mark == true){
								ld.setWrongTime(0);
								ld.setFirstLogin(1);
								System.out.println("getUserName=" + ld.getUserName());
								System.out.println("getUserID=" + ld.getUserID());
								System.out.println("getIP=" + ld.getIP());
								System.out.println("getWrongTime=" + ld.getWrongTime());
								System.out.println("getUserType=" + ld.getUserType());
								System.out.println("getLastIP=" + ld.getLastIP());
								System.out.println("getLastloginDate=" + ld.getLastloginDate());
								System.out.println("getFirstLogin=" + ld.getFirstLogin());
								return ld;
							} else {
								ld.setWrongTime(-1);
								return ld;
							}
						} else {
							ld.setWrongTime(-1);
							return ld;
						}
					} else {
						if(dao.rs.getInt(1) == 0){
							ld.setWrongTime(ld.getWrongTime() + 1);
							return ld;
						} else {
							ld.setWrongTime(-1);
							return ld;
						}
					}
				} else {
					ld.setWrongTime(-1);
					return ld;
				}
			}
			ld.setWrongTime(-1);
			return ld;
		} catch (SQLException e){
			log4j.error(e);
			ld.setWrongTime(-1);
			return ld;
		}
	}
	
	public String[] getUserIDandType(String userName,String password){
/*
 * @return 
 *  @[0] userID
 *  @[1] userType
 */
		String sql = "select userID,userType from user where  userName = '" + userName 
				+ "' and password = md5('" + password + "')"
				+ " and isEnabled = 0;";
		DataObject dao = c3p0.ExcuteSQL.excuteSelect(sql);
		String [] IdandType = new String [2];
		if(dao.mark == true){
			try {
				if(dao.rs.next()){
					IdandType[0] =  dao.rs.getString("userID");
					IdandType[1] =  dao.rs.getString("userType");
					return IdandType;
				} else {
					IdandType[0] =  "";
					IdandType[1] =  "";
					return IdandType;
				}
			} catch (SQLException e) {
				log4j.error(e);
				IdandType[0] =  "";
				IdandType[1] =  "";
				return IdandType;
			}
		}
		IdandType[0] =  "";
		IdandType[1] =  "";
		return IdandType;
	}
	
	public String[] getUserIDandType(String userName){
		String sql = "select userID,userType from user where  userName = '" + userName 
				+ "' and isEnabled = 0;";
		DataObject dao = c3p0.ExcuteSQL.excuteSelect(sql);
		String [] IdandType = new String [2];
		if(dao.mark == true){
			try {
				if(dao.rs.next()){
					IdandType[0] =  dao.rs.getString("userID");
					IdandType[1] =  dao.rs.getString("userType");
					return IdandType;
				} else {
					IdandType[0] =  "";
					IdandType[1] =  "";
					return IdandType;
				}
			} catch (SQLException e) {
				log4j.error(e);
				IdandType[0] =  "";
				IdandType[1] =  "";
				return IdandType;
			}
		}
		IdandType[0] =  "";
		IdandType[1] =  "";
		return IdandType;
	}
	
	public String getIP(){
		HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
			ip = request.getHeader("Proxy-Client-IP");
		}    
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
			ip = request.getHeader("WL-Proxy-Client-IP");    
		}    
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.indexOf(",") != -1) { 
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim(); 
		}
		return ip;
	}
	
	public RegisterData register(String userName,String password){
		/*
		 * @wrongReason
		 *  0 : success
		 *  1 : already has this user
		 *  2 : userName or userPassword not allowed or unknown reason
		 *  
		 */
		userName = c3p0.ExcuteSQL.checkSQLInjection(userName);
		password = c3p0.ExcuteSQL.checkSQLInjection(password);
		String sql = "select count(*) from user where userName = '" + userName
				+ "' and isEnabled = 0;";
		DataObject dao = c3p0.ExcuteSQL.excuteSelect(sql);
		RegisterData rd = new RegisterData();
		if(dao.mark == true){
			try {
				dao.rs.next();
				if(dao.rs.getInt(1) == 0){
					sql = "insert into user (userName,password,userType,isEnabled) values ('" + userName
							+ "',md5('" + password + "')"
							+ ",'1',0)";
					DataObject dao1 = c3p0.ExcuteSQL.excuteUpdate(sql);
					if(dao1.mark == true ){
						if(dao1.i == 1){
							rd.setUserName(userName);
							rd.setPassword(password);
							rd.setWrongReason(0);
							login(userName, password);
							return rd;
						} else {
							rd.setWrongReason(2);
							return rd;
						} 
					} else {
						rd.setWrongReason(2);
						return rd;
					}
//考虑，使用dwr调用前端的js再反调用java？会浪费性能吗？
				} else {
					if(dao.rs.getInt(1) == 1){
						rd.setWrongReason(1);
						return rd;
					} else {
						rd.setWrongReason(2);
						return rd;
					}
				}
			} catch (Exception e){
				log4j.error(e);
				rd.setWrongReason(2);
				return rd;
			}
		} else {
			rd.setWrongReason(2);
			return rd;
		}
	}
	
	public boolean createAdmin(String userName){
		userName = c3p0.ExcuteSQL.checkSQLInjection(userName);
		String [] IDandType = getUserIDandType(userName);
		if(IDandType[1].equals("")){
			String sql = "insert into user (userNmae,password,userType,isEnabled) values ('" + userName
					+ "','4baba135439fad596c82feeea39aad3b','2',0)";
			DataObject dao = c3p0.ExcuteSQL.excuteUpdate(sql);
			if(dao.mark == true){
				if(dao.i == 1){
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean updatePassword(String password){
		password = c3p0.ExcuteSQL.checkSQLInjection(password);
		HttpSession hs = WebContextFactory.get().getSession();
		String sql = "select count(*) from user where userName = '" + hs.getAttribute("userName")
				+ "'";
		DataObject dao = c3p0.ExcuteSQL.excuteSelect(sql);
		if(dao.mark == true){
			try{
				if(dao.rs.next()){
					if(dao.rs.getInt(1) == 1){
						sql = "update user set password = md5('" + password + "')"
								+ "' where userName = '" + hs.getAttribute("userName")
								+ "';";
						DataObject dao1 = c3p0.ExcuteSQL.excuteUpdate(sql);
						if(dao1.mark == true){
							if(dao1.i == 1){
								return true;
							}
						}
						return false;
					} else {
						return false;
					}
				} else {
					return false;
				}				
			} catch(Exception e){
				log4j.error(e);
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean disUser(String userName){
		userName = c3p0.ExcuteSQL.checkSQLInjection(userName);
		HttpSession hs = WebContextFactory.get().getSession();
		String sql = "select userType from user where userName = '" + hs.getAttribute("userName")
				+ "'";
		DataObject dao = c3p0.ExcuteSQL.excuteSelect(sql);
		if(dao.mark == true){
			try{
				if(dao.rs.next()){
					if(dao.rs.getString("userType") == "0"){
						sql = "update user set isDisabled = 1 where userName = '" + userName
								+ "'";
						DataObject dao1 = c3p0.ExcuteSQL.excuteUpdate(sql);
						if(dao1.mark == true ){
							if(dao1.i == 1){
								return true;
							}
						}
					}
				}
				return false;
			} catch(Exception e){
				log4j.error(e);
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean resUser(String userName){
		userName = c3p0.ExcuteSQL.checkSQLInjection(userName);
		HttpSession hs = WebContextFactory.get().getSession();
		String sql = "select userType from user where userName = '" + hs.getAttribute("userName")
				+ "'";
		DataObject dao = c3p0.ExcuteSQL.excuteSelect(sql);
		if(dao.mark == true){
			try{
				if(dao.rs.next()){
					if(dao.rs.getString("userType") == "0"){
						sql = "update user set isDisabled = 0 where userName = '" + userName
								+ "'";
						DataObject dao1 = c3p0.ExcuteSQL.excuteUpdate(sql);
						if(dao1.mark == true){
							if(dao1.i == 1){
								return true;
							}
						}
					}
				}
				return false;
			} catch(Exception e){
				log4j.error(e);
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void logout(){
		HttpSession hs = WebContextFactory.get().getSession();
		hs.invalidate();
	}
	
//	public boolean freezeIP(){
		
//	}
	
//	public boolean checkIPError(String userName){
//		userName = c3p0.ExcuteSQL.checkSQLInjection(userName);
//		String sql = "select ";
//	}
}
