package assets.upload.helpers;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import assets.upload.beans.Asset;


public class AssetsInfoTest {
	
	@Mock
	Properties mockProperties;
	@Mock
	XSSFWorkbook mockXSSFWorkbook;
	@Mock
	XSSFSheet mockXSSFSheet;
	@Mock
	FileUtil mockFileUtil;
	@Mock
	Iterator<Row> mockRowIterator;
	@Mock
	Iterator<Cell> mockCellIterator;
	@Mock
	Row mockRow;
	@Mock
	Cell mockCell;
	
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void loadAssetsInfo()throws Exception{
		String assetInfoFile = "abc.xyz";
		String assetLocation = "e://abc/";
		String assetInfoFileAbsolutePath = "e://abc/abc.xlsx";
		final List<Asset> assets = new ArrayList<Asset>();
		assets.add(new Asset("101","101.xls",new File("101.xls")));
		assets.add(new Asset("102","102.xlsx",new File("101.xlsx")));
		assets.add(new Asset("103","103.doc",new File("101.doc")));
		AssetsInfo assetsInfo = new AssetsInfo(){
			@Override
			protected FileUtil getFileUtil() {
				
				return mockFileUtil;
			}
			@Override
			protected List<Asset> readDataFromExcel(String assetNamePath, String sourceDir) throws Exception {
				return assets;
			}
		};
		when(mockFileUtil.getFileLocationFromClassPath(assetInfoFile)).thenReturn(assetInfoFileAbsolutePath);
		when(mockFileUtil.getFileExtension(assetInfoFileAbsolutePath)).thenReturn("xlsx");
		assertEquals(assetsInfo.loadAssetsInfo(assetInfoFile, assetLocation).size(), 3);
	}
	
	@Test(expected = Exception.class)
	public void loadAssetsInfoFromNonExcel()throws Exception{
		String assetInfoFile = "abc.docx"; 
		String assetLocation = "e://abc/";
		String assetInfoFileAbsolutePath = "e://abc/abc.docx";
		AssetsInfo assetsInfo = new AssetsInfo(){
			@Override
			protected FileUtil getFileUtil() {
				
				return mockFileUtil;
			}
			
		};
		when(mockFileUtil.getFileLocationFromClassPath(assetInfoFile)).thenReturn(assetInfoFileAbsolutePath);
		when(mockFileUtil.getFileExtension(assetInfoFileAbsolutePath)).thenReturn("docx");
		assetsInfo.loadAssetsInfo(assetInfoFile, assetLocation);
	}

	@Test
	public void readDataFromExcel()throws Exception{
		String assetNamePath = "e://abc/abc.xlsx";
		AssetsInfo assetsInfo = new AssetsInfo(){
			@Override
			protected XSSFWorkbook getExcelWorkbook(String assetNamePath)
					throws Exception {
				return mockXSSFWorkbook;
			}
			
		};
		when(mockXSSFWorkbook.getSheetAt(0)).thenReturn(mockXSSFSheet);
		when(mockXSSFSheet.iterator()).thenReturn(mockRowIterator);
		when(mockRowIterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockRowIterator.next()).thenReturn(mockRow);
		when(mockRow.cellIterator()).thenReturn(mockCellIterator);
		when(mockCellIterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockCellIterator.next()).thenReturn(mockCell);
		when(mockCell.getStringCellValue()).thenReturn("Id").thenReturn("File Name");
		when(mockRow.getCell(0)).thenReturn(mockCell);
		when(mockCell.getNumericCellValue()).thenReturn(1.0);
		when(mockRow.getCell(1)).thenReturn(mockCell);
		assertEquals(assetsInfo.readDataFromExcel(assetNamePath, "e://files/").size(),1);
		verify(mockXSSFWorkbook).close();
	}
}

