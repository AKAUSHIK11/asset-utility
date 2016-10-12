package assets.upload.process;

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
import assets.upload.helpers.ReportGenerator;
import assets.upload.services.DataBaseService;

public class AssetsDBProcessTest {
	
	AssetsDBProcess mockAssetsDBProcess;
	@Mock
	Properties mockProperties;
	@Mock
	DataBaseService mockDataBaseService;
	@Mock
	ReportGenerator mockReportGenerator;
	
	@Before
    public void setUp()throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void start()throws Exception{
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("102", "102.tld", new File(""),"test_table","test_column","","","","","",0,"",0,0,null));
		AssetsDBProcess assetsDBProcess = new AssetsDBProcess(mockProperties){
			@Override
			protected void updateAssets(List<Asset> assets) {
			}
			@Override
			protected void generateReport(List<Asset> assets, String reportName) {
			}
		};
		assetsDBProcess.startUpdate(assets);
	}
	
	@Test
	public void startWithNoAsset()throws Exception{
		List<Asset> assets = new ArrayList<Asset>();
		AssetsDBProcess assetsDBProcess = new AssetsDBProcess(mockProperties);
		assetsDBProcess.startUpdate(assets);
	}
	@Test
	public void updateAssets()throws Exception{
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("102", "102.tld", new File("102.tld"),"test_table","test_column","","","","","",0,"",0,0,null));
		AssetsDBProcess assetsDBProcess = new AssetsDBProcess(mockProperties);
		Field privateField = AssetsDBProcess.class.getDeclaredField("dataBaseService");
		privateField.setAccessible(true);
		privateField.set(assetsDBProcess, mockDataBaseService);
		
		assetsDBProcess.updateAssets(assets);
		
		verify(mockDataBaseService).updateAssets(assets);
	}
	
	@Test
	public void generateUpdateReport()throws Exception{
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("102", "102.tld", new File(""),"test_table","test_column","","","","","",0,"",0,0,null));
		String reportLocation="e://abc/";
		when(mockProperties.getProperty("report.location")).thenReturn(reportLocation);
		AssetsDBProcess assetsDBProcess = new AssetsDBProcess(mockProperties);
		Field privateField = AssetsDBProcess.class.getDeclaredField("reportGenerator");
		privateField.setAccessible(true);
		privateField.set(assetsDBProcess, mockReportGenerator);
		
		assetsDBProcess.generateReport(assets,"db_update.csv");
		
		verify(mockReportGenerator).generateReport(assets,"db_update.csv",reportLocation );
	}
	
	@Test
	public void downloadAssets()throws Exception{
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("102", "102.tld", new File("102.tld"),"test_table","test_column","","","","","",0,"",0,0,null));
		AssetsDBProcess assetsDBProcess = new AssetsDBProcess(mockProperties);
		Field privateField = AssetsDBProcess.class.getDeclaredField("dataBaseService");
		privateField.setAccessible(true);
		privateField.set(assetsDBProcess, mockDataBaseService);
		
		assetsDBProcess.downloadAssets(assets);
		
		verify(mockDataBaseService).downloadAssets(assets);
	}
	
	@Test
	public void startDownload()throws Exception{
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("102", "102.tld", new File(""),"test_table","test_column","","","","","",0,"",0,0,null));
		AssetsDBProcess assetsDBProcess = new AssetsDBProcess(mockProperties){
			@Override
			public void downloadAssets(List<Asset> assets) {
			}
			@Override
			protected void generateReport(List<Asset> assets, String reportName) {
			}
		};
		assetsDBProcess.startDownload(assets);
	}
	
	@Test
	public void generateDownloadReport()throws Exception{
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("102", "102.tld", new File(""),"test_table","test_column","","","","","",0,"",0,0,null));
		String reportLocation="e://abc/";
		when(mockProperties.getProperty("report.location")).thenReturn(reportLocation);
		AssetsDBProcess assetsDBProcess = new AssetsDBProcess(mockProperties);
		Field privateField = AssetsDBProcess.class.getDeclaredField("reportGenerator");
		privateField.setAccessible(true);
		privateField.set(assetsDBProcess, mockReportGenerator);
		
		assetsDBProcess.generateReport(assets,"db_download.csv");
		
		verify(mockReportGenerator).generateReport(assets,"db_download.csv",reportLocation );
	}
	
	@Test
	public void insertAssets()throws Exception{
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("102", "102.tld", new File("102.tld"),"test_table","test_column","","","","","",0,"",0,0,null));
		AssetsDBProcess assetsDBProcess = new AssetsDBProcess(mockProperties);
		Field privateField = AssetsDBProcess.class.getDeclaredField("dataBaseService");
		privateField.setAccessible(true);
		privateField.set(assetsDBProcess, mockDataBaseService);
		
		assetsDBProcess.insertAssets(assets);
		
		verify(mockDataBaseService).insertAssets(assets);
	}
	@Test
	public void startUpload()throws Exception{
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("102", "102.tld", new File(""),"test_table","test_column","","","","","",0,"",0,0,null));
		AssetsDBProcess assetsDBProcess = new AssetsDBProcess(mockProperties){
			@Override
			public void insertAssets(List<Asset> assets) {
			}
			@Override
			protected void generateReport(List<Asset> assets, String reportName) {
			}
		};
		assetsDBProcess.startUpload(assets);
	}
	@Test
	public void generateUploadReport()throws Exception{
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("102", "102.tld", new File(""),"test_table","test_column","","","","","",0,"",0,0,null));
		String reportLocation="e://abc/";
		when(mockProperties.getProperty("report.location")).thenReturn(reportLocation);
		AssetsDBProcess assetsDBProcess = new AssetsDBProcess(mockProperties);
		Field privateField = AssetsDBProcess.class.getDeclaredField("reportGenerator");
		privateField.setAccessible(true);
		privateField.set(assetsDBProcess, mockReportGenerator);
		
		assetsDBProcess.generateReport(assets,"db_upload.csv");
		
		verify(mockReportGenerator).generateReport(assets,"db_upload.csv",reportLocation );
	}
}
