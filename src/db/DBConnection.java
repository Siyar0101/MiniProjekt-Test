package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	private Connection connection = null;
	private static DBConnection dbConnection;

	private static final String DB_NAME = "pplace";
	private static final String SERVER_ADDRESS = "localhost";
	private static final int SERVER_PORT = 1433;
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "1234567";

	private DBConnection() throws DataAccessException {
		connect();
	}

	private void connect() throws DataAccessException {
		String connectionString = String.format(
				"jdbc:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s;encrypt=false", SERVER_ADDRESS, SERVER_PORT,
				DB_NAME, USERNAME, PASSWORD);
		try {
			connection = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			throw new DataAccessException(
					String.format("Could not connect to database %s@%s:%d user %s. Connection string was: %s", DB_NAME,
							SERVER_ADDRESS, SERVER_PORT, USERNAME,
							connectionString.substring(0, connectionString.length() - PASSWORD.length()) + "...."),
					e);

		}
	}

	public static synchronized DBConnection getInstance() throws DataAccessException {
		if (dbConnection == null) {
			dbConnection = new DBConnection();
		} else { 
	        Connection conn = dbConnection.getConnection();
	        try {
	            if (conn == null || conn.isClosed()) {
	                dbConnection.connect(); // Reopen the connection if it's closed
	            }
	        } catch (SQLException e) {
	            throw new DataAccessException("Failed to reopen the database connection.", e);
	        }
		}
		return dbConnection;
	}
	
	
	public Connection getConnection() {
		return connection;
	}

	public void disconnect() throws DataAccessException {
		try {
			if (connection != null && !connection.isClosed())
				connection.close();
		} catch (SQLException e) {
			throw new DataAccessException("Failed to close the database connection.", e);
		}
	}

	public void startTransaction() throws SQLException {
		connection.setAutoCommit(false);
	}

	public void commitTransaction() throws SQLException {
		connection.commit();
		connection.setAutoCommit(true);
	}

	public void rollbackTransaction() throws SQLException {
		connection.rollback();
		connection.setAutoCommit(true);
	}

	/**
	 * To us this function, remember to prepare statements<br>
	 * with <code>Statement.RETURN_GENERATED_KEYS</code><br>
	 * as additional argument!!!11!
	 * 
	 * @param ps
	 * @return
	 * @throws SQLException
	 */
	public int executeInsertWithIdentity(PreparedStatement ps) throws SQLException {
		int res = -1;
		try {
			res = ps.executeUpdate();
			if (res > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}

	public int executeInsertWithIdentity(String sql) throws SQLException {
		//System.out.println("DBConnection, Inserting: " + sql); //just for debugging - remove
		int res = -1;
		try (Statement s = connection.createStatement()) {
			res = s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			if (res > 0) {
				ResultSet rs = s.getGeneratedKeys();
				rs.next();
				res = rs.getInt(1);
			}
			// s.close(); -- the try block does this for us now

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}

	public int executeUpdate(String sql) throws SQLException {
		// System.out.println("DBConnection, Updating: " + sql); // just for debugging - remove
		int res = -1;
		try (Statement s = connection.createStatement()) {
			res = s.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return res;
	}


}
