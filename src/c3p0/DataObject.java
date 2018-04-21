package c3p0;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataObject {
	public Connection conn = null;
	public Statement stmt = null;
	public ResultSet rs = null;
	public int i;
	public boolean b;
	public boolean mark;
}
