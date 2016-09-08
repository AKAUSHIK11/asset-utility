package assets.upload.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import assets.upload.beans.Asset;
import assets.upload.helpers.FTPHelper;


public class FTPService {

	private static  Logger LOGGER = Logger.getLogger(FTPService.class);
	private String destinationLocation; 
	Properties resourceInfo;
	private FTPHelper ftpHelper;
	
	public FTPService(Properties resourceInfo) throws Exception{
		ftpHelper = new FTPHelper(resourceInfo);
		this.destinationLocation = resourceInfo.getProperty("ftp.file.store.path");
		if(this.destinationLocation == null){
			throw new Exception("Property ftp.file.store.path is missing from property file. ");
		}
		this.resourceInfo = resourceInfo;
	}
	
	/**
	 * Uploads all the asset files on FTP
	 */
	public void uploadAssets(List<Asset> assets) {

		FTPClient ftpConnection = ftpHelper.getConnection();

		try {
			setConnectionProperties(ftpConnection);
			InputStream inputStream = null;
			for (Asset asset : assets) {
				try {
					File localFile = new File(resourceInfo.getProperty("asset.location") + asset.getName());
					inputStream = new FileInputStream(localFile);
					if (ftpConnection.storeFile(asset.getName(), inputStream)) {
						asset.setUploaded(true);
					} 					
				} catch (Exception ex) {
					LOGGER.error("Problem occured while uploading file: "+ asset.getName() + " on FTP location => " + destinationLocation, ex);
				}
				if(inputStream != null){
					inputStream.close();
				}				
			}			
		} catch (Exception ex) {
			LOGGER.error("Problem occured while setting the connection properties. " + ex);
		} finally {			
			ftpHelper.closeConnection(ftpConnection);
		}						
	}
	
	/**
	 * Configures FTP connection properties
	 */
	private void setConnectionProperties(FTPClient ftpConnection) throws IOException{
		
		 ftpConnection.changeWorkingDirectory(destinationLocation);	         
         if(ftpConnection.getReplyCode() == 550) {
					LOGGER.warn("Destination directory path does not exists, creating now ****"+destinationLocation);    				
				ftpConnection.makeDirectory(destinationLocation);
				ftpConnection.changeWorkingDirectory(destinationLocation);
			}					
			ftpConnection.setFileType(FTP.BINARY_FILE_TYPE);
         ftpConnection.setFileTransferMode(FTP.BINARY_FILE_TYPE);
         ftpConnection.enterLocalPassiveMode();		
	}
}
