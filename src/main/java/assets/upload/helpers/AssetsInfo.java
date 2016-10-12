package assets.upload.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
					if (cell.getStringCellValue().equalsIgnoreCase("GAME_ITEM_ID")) {
						columns.add(columnPosition);
					}
					else if (cell.getStringCellValue().equalsIgnoreCase("TABLE_NAME")) {
						columns.add(columnPosition);
					}
					else if (cell.getStringCellValue().equalsIgnoreCase("ASSET")) {
						columns.add(columnPosition);
					}
					columnPosition++;
				}
			}
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				String id = Long.toString(Math.round(row.getCell(columns.get(0)).getNumericCellValue()));
				String tableName = row.getCell(columns.get(1)).getStringCellValue();
				String name=row.getCell(columns.get(2)).getStringCellValue();
				//String columnName=row.getCell(columns.get(3)).getStringCellValue();
				asset = new Asset(id, name, new File(sourceDir + name),tableName,"","","","","","",0,"",0,0,null);
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
	
	public List<Asset> loadInsertingAssetsInfo(String assetInfoFile, String assetLocation, HashMap<String, String> propertyMap,String assetThumbnailLocation)
			throws Exception {

		String assetInfoFileAbsolutePath = getFileUtil().getFileLocationFromClassPath(assetInfoFile);
		String fileExtension = getFileUtil().getFileExtension(assetInfoFileAbsolutePath);
		List<Asset> assets = new ArrayList<Asset>();
		if (fileExtension.equals("xlsx")) {
			assets = readInsertingDataFromExcel(assetInfoFileAbsolutePath, assetLocation,propertyMap,assetThumbnailLocation);
		} else {
			LOGGER.error("Invalid file extension: " + fileExtension);
			LOGGER.info("We only support .xlsx extension");
			throw new Exception("Invalid file extension: " + fileExtension);
		}
		return assets;
	}

	private List<Asset> readInsertingDataFromExcel(String assetNamePath, String sourceDir,
			HashMap<String, String> propertyMap,String assetThumbnailLocation) throws Exception {

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
					if (cell.getStringCellValue().equalsIgnoreCase("GAME_ITEM_ID")) {
						columns.add(columnPosition);
					} else if (cell.getStringCellValue().equalsIgnoreCase("NAME")) {
						columns.add(columnPosition);
					} else if (cell.getStringCellValue().equalsIgnoreCase("ASSET_FILENAME")) {
						columns.add(columnPosition);
					} else if (cell.getStringCellValue().equalsIgnoreCase("ASSET_CONTENT_TYPE")) {
						columns.add(columnPosition);
					} else if (cell.getStringCellValue().equalsIgnoreCase("THUMBNAIL_FILENAME")) {
						columns.add(columnPosition);
					} else if (cell.getStringCellValue().equalsIgnoreCase("THUMBNAIL_CONTENT_TYPE")) {
						columns.add(columnPosition);
					} else if (cell.getStringCellValue().equalsIgnoreCase("TABLE_NAME")) {
						columns.add(columnPosition);
					} else if (cell.getStringCellValue().equalsIgnoreCase("ORGANIZATION_TYPE")) {
						columns.add(columnPosition);
					} else if (cell.getStringCellValue().equalsIgnoreCase("AVAILABILITY_GROUP")) {
						columns.add(columnPosition);
					} else if (cell.getStringCellValue().equalsIgnoreCase("POINT_VAUE")) {
						columns.add(columnPosition);
					}

					columnPosition++;
				}
			}
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				String id = Long.toString(Math.round(row.getCell(columns.get(0)).getNumericCellValue()));
				String name = row.getCell(columns.get(1)).getStringCellValue();
				String assetFileName = row.getCell(columns.get(2)).getStringCellValue();
				String assetContentType = row.getCell(columns.get(3)).getStringCellValue();
				String thumbnailFileName = row.getCell(columns.get(4)).getStringCellValue();
				String thumbnailContentType = row.getCell(columns.get(5)).getStringCellValue();
				String tableName = row.getCell(columns.get(6)).getStringCellValue();
				int organizationTypeValue = (int) row.getCell(columns.get(7)).getNumericCellValue();
				int gameItemGroupId = (int) row.getCell(columns.get(8)).getNumericCellValue();
				int pointValue = (int) row.getCell(columns.get(9)).getNumericCellValue();
				asset = new Asset(id, name, new File(sourceDir + assetFileName+".png"), tableName, "", assetFileName, assetContentType,
						thumbnailFileName, thumbnailContentType, "", organizationTypeValue,
						"", gameItemGroupId, pointValue,new File(assetThumbnailLocation + thumbnailFileName+".png"));
				assets.add(asset);
			}
		} catch (Exception ex) {
			LOGGER.error("Error while reading excel containing inserting Assets info " + ex);
		}
		xlsxWorkbook.close();
		return assets;
	}
}
