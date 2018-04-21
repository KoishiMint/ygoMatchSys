package c3p0;

import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ExcuteSQL {
	static Logger log4j = Logger.getLogger(ExcuteSQL.class.getName());
	
	private ExcuteSQL( ){
	}
	
	public static String checkSQLInjection(String sql){
		return sql.replaceAll(".*([';]+|(--)+).*", " ");
	}
	
	public static DataObject excuteSelect(String sql){
		System.out.println(sql);
		
		DataObject dao = new DataObject();
		dao.conn = c3p0.C3p0_master.getConnection();
		if(dao.conn == null){
			dao.mark = false;
			return dao;
		} else {
			try {
				dao.stmt = dao.conn.createStatement();
			} catch (SQLException e) {
				log4j.error(e);
				dao.mark = false;
				return dao;
			}
			try {
				dao.rs = dao.stmt.executeQuery(sql);
			} catch (SQLException e){
				log4j.error(e);
				dao.mark = false;
				return dao;
			}
			dao.mark = true;
			return dao;
		}
	}
	
	public static DataObject excuteUpdate(String sql){
		System.out.println(sql);
		DataObject dao = new DataObject();
		dao.conn = c3p0.C3p0_master.getConnection();
		if(dao.conn == null){
			dao.mark = false;
			return dao;
		} else {
			try {
				dao.stmt = dao.conn.createStatement();
			} catch (SQLException e) {
				log4j.error(e);
				dao.mark = false;
				return dao;
			}
			try {
				dao.i = dao.stmt.executeUpdate(sql);
			} catch (SQLException e){
				log4j.error(e);
				dao.mark = false;
				return dao;
			}
			dao.mark = true;
			return dao;
		}
	}
	
	public static void printRs(DataObject dao,int rows){
		try {
			while(dao.rs.next()){
				for(int i = 1;i <= rows;i++){
					System.out.println(dao.rs.getObject(rows - 1));
				}
			}
		} catch (Exception e) {
			log4j.error(e);
		} finally {
			try {
				dao.rs.beforeFirst();
			} catch (SQLException e) {
				log4j.error(e);
			}
		}
	}
}
