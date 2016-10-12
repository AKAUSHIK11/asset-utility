package assets.upload.process;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import assets.upload.beans.Asset;
import assets.upload.helpers.ReportGenerator;
import assets.upload.services.DataBaseService;

public class AssetsDBProcess {
	
	public AssetsDBProcess(Properties resourceInfo) throws Exception{					
		this.dataBaseService = new DataBaseService(resourceInfo);
		this.reportLocation = resourceInfo.getProperty("report.location");
		this.reportGenerator = new ReportGenerator();
	}
	
	private DataBaseService dataBaseService; 
	private String reportLocation;
	private ReportGenerator reportGenerator;
	private static  Logger LOGGER = Logger.getLogger(AssetsDBProcess.class);	

	/**
	 * Starts the DB Upload utility
	 */	
	public void startUpdate(List<Asset> assets){
		
		if(assets.size() == 0){
			LOGGER.error("Cann't proceed further as no asset information found...");
			return;
		}
		updateAssets(assets);		
		generateReport(assets, "db_update");
	}

	/**
	 * Inserts the asset files into Database
	 */
	protected void updateAssets(List<Asset> assets) {
		LOGGER.info("Updating assets in DB started..");
		dataBaseService.updateAssets(assets);
		LOGGER.info("Updating assets in DB completed..");
	}

	/**
	 * Generates output report with upload status
	 */
	protected void generateReport(List<Asset> assets, String reportName) {
		
		LOGGER.info("Generating reports for BLOB");
		reportGenerator.generateReport(assets, reportName, reportLocation);
		LOGGER.info("Reports has been generated for BLOB");
		
	}

	public void startDownload(List<Asset> assets){
		
		if(assets.size() == 0){
			LOGGER.error("Cann't proceed further as no asset information found...");
			return;
		}
		downloadAssets(assets);		
		generateReport(assets, "db_download");
	}
	protected void downloadAssets(List<Asset> assets) {
		LOGGER.info("Downloading assets in DB started..");
		dataBaseService.downloadAssets(assets);
		LOGGER.info("Downloading assets in DB completed..");
	}

	public void startUpload(List<Asset> assets) {
		
		if(assets.size() == 0){
			LOGGER.error("Cann't proceed further as no asset information found...");
			return;
		}
		insertAssets(assets);		
		generateReport(assets, "db_upload");
	}

	public void insertAssets(List<Asset> assets) {
		
		LOGGER.info("Inserting assets in DB started..");
		dataBaseService.insertAssets(assets);
		LOGGER.info("Inserting assets in DB completed..");
		
	}


}
