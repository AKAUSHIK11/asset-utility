package assets.upload.helpers;

import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

public class FTPHelper {

	private static  Logger LOGGER = Logger.getLogger(FTPHelper.class);
	private String host;
	private String port;
	private String user;
	private String password;
	
	public FTPHelper(Properties resourceInfo){
		
		this.host = resourceInfo.getProperty("ftp.host");
		this.port = resourceInfo.getProperty("ftp.port");
		this.user = resourceInfo.getProperty("ftp.username");
		this.password = resourceInfo.getProperty("ftp.password");
		
	}
	
	/**
	 * Creates FTP connection
	 */
	public FTPClient getConnection() {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(this.host, Integer.parseInt(this.port));		
			ftpClient.login(this.user, this.password);			
		}
		
		catch(Exception ex) {
			LOGGER.error("Problem while creating FTP connection.. " + ex);
		}
		return ftpClient;		
	}

	/**
	 * Closes FTP connection
	 */
	public void closeConnection(FTPClient ftpConnection) {

		if (ftpConnection.isConnected()) {
			try {
				ftpConnection.logout();
				ftpConnection.disconnect();
			} 
			catch (Exception ex) {
				LOGGER.error("Error while closing the FTP connection.. ");
			}

		}

	}

}
