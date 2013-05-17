package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionMgrWestLakeIsr {

	private List<Connection> connectionPool = new ArrayList<Connection>();
	
	private String jdbcdriver;
	private String jdbcurl;
	private String user;
	private String password;
	private static ConnectionMgrWestLakeIsr context;
	
	public ConnectionMgrWestLakeIsr () {
		this.jdbcdriver = new String ("com.mysql.jdbc.Driver");
		this.jdbcurl    = new String ("jdbc:mysql://westlake.isr.cs.cmu.edu/staticanalysisresults");
        this.user = "prateek";
        this.password = "wean4121";
	}
	
	//FIXME: Called in doGet of AddedCursesDAO
	public static void CreateConnectionMgr () {
		if (context == null) {
			context = new ConnectionMgrWestLakeIsr ();
		}
	}
	
	public static String getJdbcdriver () {
		if (context == null) {
			throw new AssertionError("Null Connection Manager");
		}
		return context.jdbcdriver;
	}
	
	public static String getJdbcurl () {
		if (context == null) {
			throw new AssertionError("Null Connection Manager");
		}
		return context.jdbcurl;
	}
	
	public static synchronized Connection GetConnection () {
		if (context == null) {
			throw new AssertionError("Null Connection Manager");
		}
		if (context.connectionPool.size() > 0) {
			return context.connectionPool.remove(context.connectionPool.size()-1);
		}
		
        try {
            Class.forName(context.jdbcdriver);
 
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }

        try {
            return DriverManager.getConnection(context.jdbcurl, context.user, context.password);
        } catch (SQLException e) {
            throw new AssertionError(e);
        }
	}
	
	public static synchronized void ReleaseConnection(Connection con) {
		if (context != null) {
			context.connectionPool.add(con);
		}
		else {
			throw new AssertionError("Null Connection Manager");
		}
	}
	
}
