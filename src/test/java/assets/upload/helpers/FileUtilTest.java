package assets.upload.helpers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



public class FileUtilTest {
	
	@Mock
	FileInputStream mockFileInputStream;
	@Mock
	Properties mockProperties;
	@Mock
	ClassLoader mockClassLoader;
	@Mock
	File mockFile;
	
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void loadProperties()throws Exception{
		String propertyFileName = "abc.xyz";
		final String fileAbsoultePath = "e://abc.xyz";
		FileUtil fileUtil = new FileUtil(){
			@Override
			public String getFileLocationFromClassPath(String propertyFileName)
					throws Exception {
				
				return fileAbsoultePath;
			}
			@Override
			public Properties getProperties() {
				return mockProperties;
			}
			@Override
			public FileInputStream getFileInputStream(String fileAbsoultePath)
					throws Exception {
				return mockFileInputStream;
			}
		};
		
		fileUtil.loadResources(propertyFileName);
		verify(mockProperties).load(mockFileInputStream);
		 
	}
	@Test
	public void getFileExtension_for_xlsx(){
		String fileLocation = "e://abc.xlsx";
		FileUtil fileUtil = new FileUtil();
		fileUtil.getFileExtension(fileLocation);
		assertEquals("xlsx", fileUtil.getFileExtension(fileLocation));
	}
}
