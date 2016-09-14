package assets.upload.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import assets.upload.beans.Asset;

public class AssetsInfo {

	private static Logger LOGGER = Logger.getLogger(AssetsInfo.class);

	/**
	 * 
	 * Loads assets information from input excel file
	 */
	public List<Asset> loadAssetsInfo(String assetInfoFile, String assetLocation)
			throws Exception {

		String assetInfoFileAbsolutePath = getFileUtil().getFileLocationFromClassPath(assetInfoFile);
		String fileExtension = getFileUtil().getFileExtension(assetInfoFileAbsolutePath);
		List<Asset> assets = new ArrayList<Asset>();
		if (fileExtension.equals("xlsx")) {
			assets = readDataFromExcel(assetInfoFileAbsolutePath, assetLocation);
		} else {
			LOGGER.error("Invalid file extension: " + fileExtension);
			LOGGER.info("We only support .xlsx extension");
			throw new Exception("Invalid file extension: " + fileExtension);
		}
		return assets;
	}

	/**
	 * Reads data from input excel file
	 */
	protected List<Asset> readDataFromExcel(String assetNamePath, String sourceDir) throws Exception {

		XSSFWorkbook xlsxWorkbook = null;
		XSSFSheet xlsxSheet = null;
		Iterator<Row> rowIterator = null;

		xlsxWorkbook = getExcelWorkbook(assetNamePath);
		xlsxSheet = xlsxWorkbook.getSheetAt(0);
		rowIterator = xlsxSheet.iterator();
		Row row;
		Cell cell;
		List<Asset> assets = new ArrayList<Asset>();
		Asset asset = null;
		try {
			List<Integer> columns = new ArrayList<Integer>();
			if (rowIterator.hasNext()) {
				row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				int columnPosition = 0;
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();
					if (cell.getStringCellValue().equalsIgnoreCase("Id")) {
						columns.add(columnPosition);
					}
					else if (cell.getStringCellValue().equalsIgnoreCase("File Name")) {
						columns.add(columnPosition);
					}
					else if (cell.getStringCellValue().equalsIgnoreCase("Table Name")) {
						columns.add(columnPosition);
					}
					else if (cell.getStringCellValue().equalsIgnoreCase("Column Name")) {
						columns.add(columnPosition);
					}
					columnPosition++;
				}
			}
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				String id = Long.toString(Math.round(row.getCell(columns.get(0)).getNumericCellValue()));
				String name = row.getCell(columns.get(1)).getStringCellValue();
				String tableName=row.getCell(columns.get(2)).getStringCellValue();
				String columnName=row.getCell(columns.get(3)).getStringCellValue();
				asset = new Asset(id, name, new File(sourceDir + name),tableName,columnName);
				assets.add(asset);
			}
		} catch (Exception ex) {
			LOGGER.error("Error while reading excel containing Assets info "+ ex);
		}
		xlsxWorkbook.close();
		return assets;
	}
	
	protected FileUtil getFileUtil(){
		return new FileUtil();
	}
	protected XSSFWorkbook getExcelWorkbook(String assetNamePath)throws Exception{
		
		return new XSSFWorkbook(new FileInputStream(new File(assetNamePath)));
	}

}
