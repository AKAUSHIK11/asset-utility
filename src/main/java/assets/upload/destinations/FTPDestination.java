package assets.upload.destinations;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import assets.upload.beans.Asset;
import assets.upload.process.AssetsFTPProcess;



public class FTPDestination implements IAssetsDestination{
	
	public FTPDestination(Properties resourceInfo, List<Asset> assets){
		this.resourceInfo = resourceInfo;
		this.assets = assets;
	}
	
	private static Logger LOGGER = Logger.getLogger(FTPDestination.class);
	private Properties resourceInfo;
	List<Asset> assets;

	/*
	 * 	Starts Upload process for FTP
	 */
	@Override
	public void upload() {
		
		AssetsFTPProcess assetsFTPProcess;	
		try {
			assetsFTPProcess = new AssetsFTPProcess(resourceInfo);
			assetsFTPProcess.start(assets);
		} catch (Exception ex) {
			LOGGER.error("Problem occured while uploading assests to FTP", ex);			
		}
		
	}

}
