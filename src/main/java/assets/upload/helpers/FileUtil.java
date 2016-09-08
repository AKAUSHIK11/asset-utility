package assets.upload.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;


public class FileUtil {
	
	/**
	 * Loads the configuration properties file
	 */
	public Properties loadResources(String propertyFile) throws Exception {
		
		String fileAbsoultePath = getFileLocationFromClassPath(propertyFile);
		FileInputStream input=getFileInputStream(fileAbsoultePath);
		Properties properties = getProperties();
		properties.load(input);
		return properties;
	}
	
	/**
	 * Provides the absolute path of configuration file from classpath
	 */
	public String getFileLocationFromClassPath(String propertyFileName) throws Exception
	{
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(propertyFileName).getFile());
		return file.getCanonicalPath();
	}

	/**
	 * Provides File extension
	 */
	public String getFileExtension(String fileLocation)
	{
		String fileExtension = null;
		fileExtension = fileLocation.substring(fileLocation.lastIndexOf('.')+1);
		return fileExtension;
	}
	public FileInputStream getFileInputStream(String fileAbsoultePath)throws Exception{
		return new FileInputStream(new File(fileAbsoultePath));
	}
	public Properties getProperties(){
		return new Properties();
	}
}
