package assets.upload.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBHelper {

	private String driver = null;;
	private String url = null;
	private String user = null;
	private String password = null;
	private String assetDownloadLocation=null;
	private static Logger LOGGER = Logger.getLogger(DBHelper.class);

	public DBHelper(Properties resourceInfo) {
		driver = resourceInfo.getProperty("driver.class");
		url = resourceInfo.getProperty("database.url");
		user = resourceInfo.getProperty("database.user");
		password = resourceInfo.getProperty("database.password");
		assetDownloadLocation=resourceInfo.getProperty("asset.download.location");
	}

	/**
	 * Creates database connection
	 */
	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception ex) {
			LOGGER.error("Probelm while creating connection object..", ex);
		}
		return connection;
	}

	/**
	 * Closes database connection
	 */
	public void closeConnection(Connection connection) {

		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException ex) {
			LOGGER.error("Can not close the DB connection.", ex);
		}
	}

	/**
	 * Closes database statement
	 */
	public void closeStatement(Statement statement) {

		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException ex) {
			LOGGER.error("Can not close statement object.", ex);
		}
	}

	public String getAssetDownloadLocation() {
		
		return assetDownloadLocation;
	}

}
