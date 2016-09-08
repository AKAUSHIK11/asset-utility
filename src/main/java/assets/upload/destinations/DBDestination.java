package assets.upload.destinations;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import assets.upload.beans.Asset;
import assets.upload.process.AssetsDBProcess;


public class DBDestination implements IAssetsDestination{
	
	public DBDestination(Properties resourceInfo, List<Asset> assets){
		this.resourceInfo = resourceInfo;
		this.assets = assets;
	}
		
	private static Logger LOGGER = Logger.getLogger(DBDestination.class);
	private Properties resourceInfo;
	private List<Asset> assets;
	
	/* 
	 * 	Starts Upload process for Database
	 */
	@Override
	public void upload() {
		
		AssetsDBProcess assetsDBProcess;
		try {
			assetsDBProcess = getAssetsDBProcess();
			assetsDBProcess.start(assets);
		} catch (Exception ex) {
			LOGGER.error("Problem occured while uploading assests to DB", ex);
		}		
	}
	
	protected AssetsDBProcess getAssetsDBProcess()throws Exception{
		return new AssetsDBProcess(resourceInfo);
	}

}
