package assets.upload.start;

import java.util.Arrays;

import org.apache.log4j.Logger;

public class Main {
	
	private static Logger LOGGER = Logger.getLogger(Main.class);
	
	/**
     * Starts the application
     */
    public static void main( String[] destinations ) throws Exception {
    	
    	LOGGER.info("**********************************************"); 
    	LOGGER.info("Assets-uploader-utility has been started...");    	
    	LOGGER.info("Following destinations have been supplied for assets to be upload: " + Arrays.toString(destinations));
    	
        ProcessAssets processAssets = new ProcessAssets();
        processAssets.process(destinations);
        
        LOGGER.info("Assets-uploader-utility has been completed.");
        LOGGER.info("**********************************************");
    }
}
