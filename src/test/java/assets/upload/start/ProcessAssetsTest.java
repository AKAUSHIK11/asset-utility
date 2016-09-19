package assets.upload.start;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import assets.upload.beans.Asset;
import assets.upload.destinations.DBDestination;
import assets.upload.destinations.FTPDestination;
import assets.upload.helpers.AssetsInfo;
import assets.upload.helpers.FileUtil;

public class ProcessAssetsTest {
	
	@Mock
	Properties mockProperties;
	@Mock
	AssetsInfo mockAssetsInfo;
	@Mock
	DBDestination mockDBDestination;
	@Mock
	FTPDestination mockFTPDestination;
	@Mock
	List<Asset> mockAssets;
	@Mock
	FileUtil mockFileUtil;
	
	
	@Before
    public void setUp()throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void loadAssetsInfo()throws Exception{
		
		String assetInfoFile = "abc.xlsx";
		String assetLocation = "e://abc/";
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("101", "101.tld", new File("101.tld"),"test_table","test_column"));
		ProcessAssets processAssets = new ProcessAssets(){
			
			@Override
			protected Properties loadConfiguration(String propertyFile) {
				return mockProperties;
			}
			@Override
			protected AssetsInfo getAssetsInfo() {
				return mockAssetsInfo;
			}
			@Override
			protected void updateFileSizeCompatibility(List<Asset> assets) {
				
			}
		};
		
		Field privateField = ProcessAssets.class.getDeclaredField("resourceInfo");
		privateField.setAccessible(true);
		privateField.set(processAssets, mockProperties);
		when(mockProperties.getProperty("asset.updating.info.file")).thenReturn(assetInfoFile);
		when(mockProperties.getProperty("asset.location")).thenReturn(assetLocation);
		when(mockAssetsInfo.loadAssetsInfo(assetInfoFile, assetLocation)).thenReturn(assets);
		List<Asset> returnAssets = processAssets.loadUpdatingAssetsInfo();
		verify(mockAssetsInfo).loadAssetsInfo(assetInfoFile, assetLocation);
		assertEquals(returnAssets.size(), assets.size());
	}
	
	@Test(expected = Exception.class) 
	public void checkConstructorForException()throws Exception{
		
		new ProcessAssets(){
			@Override
			protected Properties loadConfiguration(String propertyFile) {
				return null;
			}
			
		};
	}
	@Test
	public void process()throws Exception{
		
		final List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("101", "101.docx", new File("101.docx"),"test_table","test_column"));
		String[] destinations = new String[]{"DB-UpdateAssets", "FTP", "ABC"};
		when(mockProperties.getProperty("asset.size")).thenReturn("12345");
		ProcessAssets processAssets = new ProcessAssets(){
			
			@Override
			protected DBDestination getDBUpdateDestination() {
				return mockDBDestination;
			}
			@Override
			protected FTPDestination getFTPDestination() {
				return mockFTPDestination;
			}
			@Override
			protected Properties loadConfiguration(String propertyFile) {
				return mockProperties;
			}
			@Override
			protected List<Asset> loadUpdatingAssetsInfo() {
				return assets;
			}
		};
		Field resourceInfoPrivateField = ProcessAssets.class.getDeclaredField("resourceInfo");
		resourceInfoPrivateField.setAccessible(true);
		resourceInfoPrivateField.set(processAssets, mockProperties);
		
		processAssets.process(destinations);
		verify(mockDBDestination).update();
		verify(mockFTPDestination).upload();
	}
	
	@Test
	public void processWithNoDestinations()throws Exception{
		
		final List<Asset> assets = new ArrayList<Asset>();
		
		String[] destinations = new String[]{};
		ProcessAssets processAssets = new ProcessAssets(){
			
			
			@Override
			protected Properties loadConfiguration(String propertyFile) {
				return mockProperties;
			}
			@Override
			protected List<Asset> loadUpdatingAssetsInfo() {
				return assets;
			}
			@Override
			protected void updateFileSizeCompatibility(List<Asset> assets) {
				
			}
		};
		processAssets.process(destinations);
	}
}
