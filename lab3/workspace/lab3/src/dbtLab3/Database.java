package dbtLab3;

import java.sql.*;

/**
 * Database is a class that specifies the interface to the movie database. Uses
 * JDBC and the MySQL Connector/J driver.
 */
public class Database {
	/**
	 * The database connection.
	 */
	private Connection conn;

	/**
	 * Create the database interface object. Connection to the database is
	 * performed later.
	 */
	public Database() {
		conn = null;

	}

	/**
	 * Open a connection to the database, using the specified user name and
	 * password.
	 * 
	 * @param userName
	 *            The user name.
	 * @param password
	 *            The user's password.
	 * @return true if the connection succeeded, false if the supplied user name
	 *         and password were not recognized. Returns false also if the JDBC
	 *         driver isn't found.
	 */
	public boolean openConnection(String userName, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://puccini.cs.lth.se/" + userName, userName,
					password);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Close the connection to the database.
	 */
	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
		}
		conn = null;
	}

	/**
	 * Check if the connection to the database has been established
	 * 
	 * @return true if the connection has been established
	 */
	public boolean isConnected() {
		return conn != null;
	}

	public boolean logIn(String username) {

		try {
			String sql = "select * from users where username = ?";
			PreparedStatement login = conn.prepareStatement(sql);
			login.setString(1, username);
			ResultSet users = login.executeQuery();
			return users.next();
		} catch (SQLException e) {
			System.out.println("No connection to database");
			e.printStackTrace();
		}
		return false;

	}

	public String[] fetchMovies() {
		Statement fetchMovies;
		try {
			fetchMovies = conn.createStatement();
			ResultSet movies = fetchMovies.executeQuery("select * from movies");
			StringBuilder sb = new StringBuilder();
			while (movies.next()) {
				sb.append(movies.getString(1) + ";");
			}
			return sb.toString().split(";");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String[] fetchDates(String movieName) {

		try {
			Statement fetchDates = conn.createStatement();
			ResultSet dates = fetchDates
					.executeQuery("select performanceDate from movieperformance where moviename = '"
							+ movieName + "'");
			StringBuilder sb = new StringBuilder();
			while (dates.next()) {
				sb.append(dates.getString(1) + ";");
			}
			return sb.toString().split(";");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String[] getTheatreInfo(String movieName, String date) {
		String[] info = new String[2];
		try {
			Statement fetchTheatreInfo = conn.createStatement();
			ResultSet tInfo = fetchTheatreInfo
					.executeQuery("select theatreName,seats from moviePerformance where performanceDate = '"
							+ date
							+ "'"
							+ " and movieName = '"
							+ movieName
							+ "'");
			tInfo.next();
			info[0] = tInfo.getString(1);
			info[1] = Integer.toString(tInfo.getInt(2));
			return info;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int bookSeat(String movieName, String date, String user) {
		int reservId;
		try {
			conn.setAutoCommit(false);
			Statement transaction = conn.createStatement();
			ResultSet ticket = transaction
					.executeQuery("select theatreName, seats from moviePerformance where performanceDate = '"
							+ date
							+ "'"
							+ " and movieName = '"
							+ movieName
							+ "'");
			ticket.next();
			if (ticket.getInt(2) == 0) {
				conn.rollback();
				reservId = -1;
			} else {
				transaction
						.executeUpdate("update movieperformance set seats = "
								+ (ticket.getInt(2) - 1)
								+ " where performanceDate = '" + date + "'"
								+ " and movieName = '" + movieName + "'");
				transaction
						.executeUpdate("insert into reservation values(null, '"
								+ user + "', '" + movieName + "', '" + date
								+ "')");
				ticket = transaction.executeQuery("select last_insert_id()");
				ticket.next();
				reservId = ticket.getInt(1);
				conn.commit();
			}
			conn.setAutoCommit(true);
			return reservId;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

}