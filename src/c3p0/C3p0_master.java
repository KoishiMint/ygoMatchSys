package c3p0;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0_master {
	
	private C3p0_master(){
		
	}
	
	private static ComboPooledDataSource datasource;
	static Logger log4j = Logger.getLogger(C3p0_master.class.getName());

	static {
		datasource = new ComboPooledDataSource();
		Properties properties = new Properties();
		InputStream is = C3p0_master.class.getClassLoader()
				.getResourceAsStream("master.properties");

		try {
			properties.load(is);
		} catch (IOException e) {
			log4j.error(e);
		}

		datasource.setUser(properties.getProperty("user"));
		datasource.setPassword(properties.getProperty("password"));
		datasource.setJdbcUrl(properties.getProperty("jdbcUrl"));
		try {
			datasource.setDriverClass(properties.getProperty("driverClass"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

		datasource.setInitialPoolSize(Integer.parseInt(properties
				.getProperty("initialPoolSize")));
		datasource.setMinPoolSize(Integer.parseInt(properties
				.getProperty("minPoolSize")));
		datasource.setMaxPoolSize(Integer.parseInt(properties
				.getProperty("maxPoolSize")));
		datasource.setMaxIdleTime(Integer.parseInt(properties
				.getProperty("maxIdleTime")));
		datasource.setAcquireIncrement(Integer.parseInt(properties
				.getProperty("acquireIncrement")));
		datasource.setIdleConnectionTestPeriod(Integer.parseInt(properties
				.getProperty("idleConnectionTestPeriod")));
	}
	
	public static Connection getConnection() {
		if (testMaxConnection()) {
			try {
				return datasource.getConnection();
			} catch (SQLException e) {
				log4j.error(e);
			}
			return null;
		} else {
			return null;
		}
	}
	
	private static boolean testMaxConnection() {
		int i;
		int n;
		try {
			i = datasource.getMaxPoolSize();
			n = datasource.getNumConnections();
			if (n == -1) {
				return false;
			}
		} catch (Exception e) {
			log4j.error(e);
			return false;
		}
		if (i == n) {
			try {
				datasource.setMaxPoolSize(i + datasource.getAcquireIncrement());
			} catch (Exception e) {
				log4j.error(e);
				return false;
			}
			return true;
		} else {
			return true;
		}
	}
}
