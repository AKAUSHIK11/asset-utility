package assets.upload.process;

import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import assets.upload.beans.Asset;
import assets.upload.helpers.ReportGenerator;
import assets.upload.services.FTPService;

public class AssetsFTPProcess {
	
	public AssetsFTPProcess(Properties resourceInfo) throws Exception{					

		this.ftpService = new FTPService(resourceInfo);
		this.reportLocation = resourceInfo.getProperty("report.location");
		this.reportGenerator = new ReportGenerator();
	}
	
	private FTPService ftpService; 
	private String reportLocation;
	private ReportGenerator reportGenerator;
	private static Logger LOGGER = Logger.getLogger(AssetsFTPProcess.class);	

	/**
	 * Starts the FTP Upload utility
	 */	
	public void start(List<Asset> assets){
		
		if(assets.size() == 0){
			LOGGER.error("Cann't proceed further as no asset information found...");
			return;
		}
		uploadAssets(assets);		
		generateReport(assets, "ftp_upload");
	}

	/**
	 * Uploads the asset files on FTP
	 */
	private void uploadAssets(List<Asset> assets) {
		LOGGER.info("Uploading assets on FTP started..");
		ftpService.uploadAssets(assets);
		LOGGER.info("Uploading assets on FTP completed..");
	}

	/**
	 * Generates output report with status
	 */
	private void generateReport(List<Asset> assets, String reportName) {
		
		LOGGER.info("Generating reports for FTP upload");
		reportGenerator.generateReport(assets, reportName, reportLocation);
		LOGGER.info("Reports has been generated for FTP upload");
		
	}

}
