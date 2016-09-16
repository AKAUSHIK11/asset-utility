package assets.upload.start;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import assets.upload.beans.Asset;
import assets.upload.destinations.DBDestination;
import assets.upload.destinations.FTPDestination;
import assets.upload.destinations.IAssetsDestination;
import assets.upload.helpers.AssetsInfo;
import assets.upload.helpers.FileUtil;

public class ProcessAssets {
	
	public ProcessAssets() throws Exception{
		this.resourceInfo = loadConfiguration(PROPERTY_FILE);
		if(resourceInfo == null){
			LOGGER.error("Cann't proceed further...");
			throw new Exception();
		}
		
		this.uploadingAssets = loadAssetsInfo();
		this.downloadingAssets=loadDownloadingAssetsInfo();
		updateFileSizeCompatibility(this.uploadingAssets);
	}
	
	

	private static Logger LOGGER = Logger.getLogger(ProcessAssets.class);
	private static final String PROPERTY_FILE = "application-resources.properties";
	private Properties resourceInfo;
	private FileUtil fileUtil = new FileUtil();
	private List<Asset> uploadingAssets;
	private List<Asset> downloadingAssets;
	
	/**
	 * Loads configuration property file in classpath
	 */
	protected Properties loadConfiguration(String propertyFile) {
		
		Properties resource = null;
		try {
			LOGGER.info("loading property file: "+propertyFile);
			resource =  fileUtil.loadResources(propertyFile);
			LOGGER.info("property file: "+propertyFile + " loaded succesfully..");
		} catch (Exception e) {
			LOGGER.error("Problem occured while loading property file: "+ propertyFile, e);	
		}
		return resource;
	}
	
	/**
	 * Provides assets information from input file
	 */
	protected List<Asset> loadAssetsInfo() {
		
		List<Asset> assets = new ArrayList<Asset>();
		String assetInfoFile = this.resourceInfo.getProperty("asset.info.file");
		String assetLocation = resourceInfo.getProperty("asset.location");
		try {
			LOGGER.info("loading assets info from file: "+assetInfoFile);
			AssetsInfo assetsInfo = getAssetsInfo();
			assets = assetsInfo.loadAssetsInfo(assetInfoFile, assetLocation);
			LOGGER.info("Assets info loading process has been completed from file: "+assetInfoFile);
		} catch (Exception e) {			
			LOGGER.error("Problem occured while loading asset info from file: "+ assetInfoFile, e);	
		}
		LOGGER.info(assets.size() + " assets loaded to be process.");
		return assets;
	}
	
	protected AssetsInfo getAssetsInfo(){
		return new AssetsInfo();
	}
	
	/**
	 * Processes upload utility application for provided destination argument (DB, FTP)
	 */
	public void process(String[] destinations){
		
		IAssetsDestination assetsDestination;
		
	        if(destinations.length > 0){
	        	for (String destination : destinations) {
	        		switch (destination) {
					case "DBUpload":
						LOGGER.info("---- DBUpload module has been started.---- ");
						assetsDestination = getDBUploadDestination();
						assetsDestination.upload();
						LOGGER.info("---- DBUpload module has been completed.---- ");
						break;
					case "FTP":
						LOGGER.info("---- FTP module has been started.---- ");
						assetsDestination = getFTPDestination();
						assetsDestination.upload();
						LOGGER.info("---- FTP module has been completed.---- ");
						break;
					case "DBDownload":
						LOGGER.info("---- DBDownload module has been started.---- ");
						assetsDestination = getDBDownloadDestination();
						assetsDestination.download();
						LOGGER.info("---- DBDownload module has been completed.---- ");
						break;
					default:
						LOGGER.info("Sorry, currently this utility doesn't procees assets to destination: " +destination);
						break;
					}
				}
	        	
	        }else{
	        	LOGGER.info("No destination provided, Please provide at least one destination for assets to be upload. ");
	        }
	    }
	
	private List<Asset> cloneAssets(List<Asset> assets){
		
		List<Asset> clonedCopy = new ArrayList<Asset>();
		for (Asset asset : assets) {			
			clonedCopy.add((Asset) asset.clone());
		}
		return clonedCopy;
	}
	
	/**
	 * Checks if the asset file size is within defined limits
	 */
	protected void updateFileSizeCompatibility(List<Asset> assets){
		Long fileSize = Long.parseLong(resourceInfo.getProperty("asset.size"));
		this.uploadingAssets = new ArrayList<Asset>();
		for(Asset asset : assets){
			if(asset.getFile().length() <= fileSize){
				asset.setFileSizeCompatible(true);
			}
			this.uploadingAssets.add(asset);
		}
	}
	protected DBDestination getDBUploadDestination(){
		return new DBDestination(resourceInfo, cloneAssets(uploadingAssets));
	}
	protected FTPDestination getFTPDestination(){
		return new FTPDestination(resourceInfo, cloneAssets(uploadingAssets));
	}
	
	protected DBDestination getDBDownloadDestination(){
		return new DBDestination(resourceInfo, cloneAssets(downloadingAssets));
	}
	
	protected List<Asset> loadDownloadingAssetsInfo() {

		List<Asset> assets = new ArrayList<Asset>();
		String assetInfoFile = this.resourceInfo.getProperty("asset.info.downloading.file");
		String assetLocation = resourceInfo.getProperty("asset.location");
		try {
			LOGGER.info("loading assets info from file: " + assetInfoFile);
			AssetsInfo assetsInfo = getAssetsInfo();
			assets = assetsInfo.loadAssetsInfo(assetInfoFile, assetLocation);
			LOGGER.info("Assets info loading process has been completed from file: " + assetInfoFile);
		} catch (Exception e) {
			LOGGER.error("Problem occured while loading asset info from file: " + assetInfoFile, e);
		}
		LOGGER.info(assets.size() + " assets loaded to be process.");
		return assets;
	}
}
