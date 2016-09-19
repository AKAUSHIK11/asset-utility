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
		assets.add(new Asset("102", "102.tld", new File(""),"test_table","test_column"));
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
	public void insertAssets()throws Exception{
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("102", "102.tld", new File("102.tld"),"test_table","test_column"));
		AssetsDBProcess assetsDBProcess = new AssetsDBProcess(mockProperties);
		Field privateField = AssetsDBProcess.class.getDeclaredField("dataBaseService");
		privateField.setAccessible(true);
		privateField.set(assetsDBProcess, mockDataBaseService);
		
		assetsDBProcess.updateAssets(assets);
		
		verify(mockDataBaseService).updateAssets(assets);
	}
	
	@Test
	public void generateReport()throws Exception{
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("102", "102.tld", new File(""),"test_table","test_column"));
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
