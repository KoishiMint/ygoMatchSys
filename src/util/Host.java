package util;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import c3p0.DataObject;
import dto.MatchData;

public class Host {
	static Logger log4j = Logger.getLogger(Host.class.getName());
	
	public MatchData createMac(String macName,String macTime,String macInfo,int macType,int regNum,int macBan){
/*
 * @return
 *   @ 0 : success
 *   @ 1 : failure
 */
		System.out.println("create match");
		WebContext wc = WebContextFactory.get();
		HttpSession hs = wc.getSession();
		MatchData md = new MatchData();
		System.out.println("getAttribute(\"userType\"):" + hs.getAttribute("userType"));
		if(!"1".equals(hs.getAttribute("userType"))){
			String sql = "insert into match(macName,macTime,macLogger,macStat,macInfo,macType,regTime,regNum,banlist) values ('" + macName
					+ "','" + macTime.replaceAll("[ÄêÔÂ]", "-").replace("ÈÕ", "") + ":00" 
					+ "'," + hs.getAttribute("userID")
					+ "," + 1
					+ ",'" + macInfo
					+ "'," + macType
					+ ",now()," + regNum
					+ "," + macBan
					+ ")";
			DataObject dao = c3p0.ExcuteSQL.excuteUpdate(sql);
			if(dao.mark == true){
				if(dao.i == 1){
					return null;
				} else {
					return null;
				}
			} else {
				log4j.error("system error");
				return null;
			}
		} else {
			return null;
		}
	}
}
