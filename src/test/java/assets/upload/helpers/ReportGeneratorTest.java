package assets.upload.helpers;

import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import assets.upload.beans.Asset;

public class ReportGeneratorTest {
	
	@Mock
	PrintWriter mockPrintWriter;
	
	
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void generateReportForSuccess(){
		
		List<Asset> assets = new ArrayList<Asset>();
		Asset asset = new Asset("102", "101.tld", new File("101.tld"),"test_table","test_column");
		asset.setUploaded(true);
		assets.add(asset);
		String reportName = "abcReport.csv";
		String reportLocation = "report";
		ReportGenerator reportGenerator = new ReportGenerator(){
			
			
			@Override
			protected PrintWriter getPrintWriter(String reportPath)throws Exception {
				return mockPrintWriter;
			}
		};
		reportGenerator.generateReport(assets, reportName, reportLocation);
		verify(mockPrintWriter).close();
	}
	@Test
	public void generateReportForFailure(){
		
		List<Asset> assets = new ArrayList<Asset>();
		Asset asset = new Asset("102", "101.tld", new File("101.tld"),"test_table","test_column");
		asset.setUploaded(false);
		assets.add(asset);
		String reportName = "abcReport.csv";
		String reportLocation = "report";
		ReportGenerator reportGenerator = new ReportGenerator(){
			
			
			@Override
			protected PrintWriter getPrintWriter(String reportPath)throws Exception {
				return mockPrintWriter;
			}
		};
		reportGenerator.generateReport(assets, reportName, reportLocation);
		verify(mockPrintWriter).close();
	}
	
	@Test
	public void generateReportFileNotFound(){
		
		List<Asset> assets = new ArrayList<Asset>();
		Asset asset = new Asset("102", "101.tld", new File("101.tld"),"test_table","test_column");
		asset.setUploaded(false);
		assets.add(asset);
		String reportName = "abcReport.csv";
		String reportLocation = "report";
		ReportGenerator reportGenerator = new ReportGenerator(){
			
			
			@Override
			protected PrintWriter getPrintWriter(String reportPath)throws Exception {
				return null;
			}
		};
		reportGenerator.generateReport(assets, reportName, reportLocation);
	}

}
