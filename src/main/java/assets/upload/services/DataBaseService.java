package assets.upload.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import assets.upload.beans.Asset;
import assets.upload.helpers.DBHelper;

public class DataBaseService {

	public DataBaseService(Properties resourceInfo) {
		this.dbHelper = new DBHelper(resourceInfo);
	}

	private static Logger LOGGER = Logger.getLogger(DataBaseService.class);
	private DBHelper dbHelper;

	/**
	 * Inserts all the asset files provided into Database
	 */
	public void updateAssets(List<Asset> assets) {

		Connection connection = dbHelper.getConnection();
		for (Asset asset : assets) {
			String tableName = asset.getTableName();
			PreparedStatement preparedStatement;
			try {
				preparedStatement = connection.prepareStatement("UPDATE " + tableName + " SET HTML_ASSET_BYTES=? WHERE NPC_ID = ?");
				updateAsset(asset, preparedStatement);
			} catch (SQLException ex) {
				LOGGER.error("Problem occured while getting prepared statement object.", ex);
			}
		}
		dbHelper.closeConnection(connection);

	}

	/**
	 * Inserts single asset file into Database
	 */
	protected void updateAsset(Asset asset, PreparedStatement preparedStatement) {

		File file = asset.getFile();
		try {
			if (asset.isFileSizeCompatible()) {
				FileInputStream inputStream = getInputStream(file);
				preparedStatement.setBinaryStream(1, inputStream, inputStream.available());
				preparedStatement.setString(2, asset.getId());
				int updatedRecord = preparedStatement.executeUpdate();
				if (updatedRecord > 0) {
					asset.setUpdated(true);
					LOGGER.info("Updating completed for asset Id : " + asset.getId() + " having Name: " + asset.getName());
				} else {
					LOGGER.info("There is no matching value in the Database for Asset " + file.getName());
				}
			} else {
				LOGGER.error("Asset " + file.getName() + " doesn't have compatible file size. ");
			}
		} catch (Exception ex) {
			LOGGER.error("Problem occured while updating BLOB for file: " + file.getName(), ex);
		} finally {
			dbHelper.closeStatement(preparedStatement);

		}
	}

	protected FileInputStream getInputStream(File file) throws Exception {

		return new FileInputStream(file);
	}

	public void downloadAssets(List<Asset> assets) {

		Connection connection = dbHelper.getConnection();
		for (Asset asset : assets) {
			String tableName = asset.getTableName();
			//String columnName = asset.getColumnName();
			PreparedStatement preparedStatement;
			try {
				preparedStatement = connection
						.prepareStatement("SELECT HTML_ASSET_BYTES FROM " + tableName + " WHERE GAME_ITEM_ID =?");
				downloadAsset(asset, preparedStatement);
			} catch (SQLException ex) {
				LOGGER.error("Problem occured while getting prepared statement object.", ex);
			}
		}
		dbHelper.closeConnection(connection);

	}

	protected void downloadAsset(Asset asset, PreparedStatement preparedStatement) {
		Connection connection = dbHelper.getConnection();
		String assetName = asset.getName();
		try {
			preparedStatement.setString(1, asset.getId());
			ResultSet result = preparedStatement.executeQuery();
			if (result.next()) {
				String filePath = dbHelper.getAssetDownloadLocation() + assetName;
				Blob blob = result.getBlob("HTML_ASSET_BYTES");
				InputStream inputStream = blob.getBinaryStream();
				OutputStream outputStream = new FileOutputStream(filePath);
				int bytesRead = -1;
				byte[] buffer = new byte[4096];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
					asset.setDownloaded(true);
				}
				inputStream.close();
				outputStream.close();
				LOGGER.info("Asset downloaded successfully: " + assetName);
			}
		} catch (Exception ex) {
			LOGGER.error("Assets: " + assetName + " cannot be downloaded because of follwing Exception : ", ex);
		}
		dbHelper.closeConnection(connection);

	}

	public void insertAssets(List<Asset> assets) {
		Connection connection = dbHelper.getConnection();
		for (Asset asset : assets) {
			String tableName=asset.getTableName();
			PreparedStatement preparedStatement;
			try {
				preparedStatement = connection.prepareStatement("INSERT INTO " +tableName+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				insertAsset(asset, preparedStatement);
			} catch (SQLException ex) {
				LOGGER.error("Problem occured while getting prepared statement object.", ex);
			}
		}
		dbHelper.closeConnection(connection);

	}

	protected void insertAsset(Asset asset, PreparedStatement preparedStatement) {

		File file = asset.getFile();
		File thumbnailFile = asset.getThumbnailFile();
		try {
			if (asset.isFileSizeCompatible()) {
				FileInputStream inputStream = getInputStream(file);
				FileInputStream inputthumbnailFileStream = getInputStream(thumbnailFile);
				FileInputStream fileInputStream = getInputStream(file);
				preparedStatement.setString(1, asset.getId());
				preparedStatement.setString(2, asset.getName());
				preparedStatement.setString(3, asset.getAssetfileName());
				preparedStatement.setString(4, asset.getAssetContentType());
				preparedStatement.setBinaryStream(5, inputStream, inputStream.available());
				preparedStatement.setString(6, asset.getThumbnailFileName());
				preparedStatement.setString(7, asset.getThumbnailContentType());
				preparedStatement.setBinaryStream(8, inputthumbnailFileStream, inputthumbnailFileStream.available());
				preparedStatement.setInt(9, asset.getOrganizationTypeValue());
				preparedStatement.setInt(10, asset.getPointValue());
				preparedStatement.setInt(11, asset.getGameItemGroupId());
				preparedStatement.setDate(12, getCurrentDate());
				preparedStatement.setDate(13, getCurrentDate());
				preparedStatement.setBinaryStream(14, fileInputStream, fileInputStream.available());

				int updatedRecord = preparedStatement.executeUpdate();
				if (updatedRecord > 0) {
					asset.setUploaded(true);
					LOGGER.info(
							"Uploading completed for asset Id : " + asset.getId() + " having Name: " + asset.getName());
				} else {
					LOGGER.info("There is no matching value in the Database for Asset " + file.getName());
				}
			} else {
				LOGGER.error("Asset " + file.getName() + " doesn't have compatible file size. ");
			}
		} catch (Exception ex) {
			LOGGER.error("Problem occured while inserting BLOB for file: " + file.getName(), ex);
		} finally {
			dbHelper.closeStatement(preparedStatement);

		}
	}

	private static java.sql.Date getCurrentDate() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}
}
