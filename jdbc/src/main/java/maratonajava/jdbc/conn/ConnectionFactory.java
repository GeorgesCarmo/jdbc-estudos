package maratonajava.jdbc.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

/**
 * java.sql = Connection, Statement, ResultSet, DiverManager
 *
 */
public class ConnectionFactory {
	
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3307/anime_store";
		String userName = "root";
		String password = "root";
		return DriverManager.getConnection(url,userName,password);
	}
	
	public static JdbcRowSet getJdbcRowSet() throws SQLException {
		String url = "jdbc:mysql://localhost:3307/anime_store";
		String userName = "root";
		String password = "root";
		JdbcRowSet jdbcRowSet = RowSetProvider.newFactory().createJdbcRowSet();
		jdbcRowSet.setUrl(url);
		jdbcRowSet.setUsername(userName);
		jdbcRowSet.setPassword(password);
		return jdbcRowSet;
	}
	
	public static CachedRowSet getCachedRowSet() throws SQLException {
		return RowSetProvider.newFactory().createCachedRowSet();
	}
	
	}
