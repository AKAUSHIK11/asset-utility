package assets.upload.services;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import assets.upload.beans.Asset;
import assets.upload.helpers.DBHelper;
public class DataBaseServiceTest {

	@Mock
	DBHelper dbHelper;
	@Mock
	PreparedStatement mockPreparedStatement;
	@Mock
	Connection mockConnection;
	@Mock
	Properties mockProperties;
	@Mock
	FileInputStream mockFileInputStream;
	@Mock
	InputStream mockInputStream;
	@Mock
	ResultSet mockResultSet;
	
	@Mock
	Blob mockBlob;
	
	@Before
    public void setUp()throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void updateAssets()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"),"test_table","test_column","","","","","",0,"",0,0,null);
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(asset);
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("update dbo.file1 set fileData=? where id=?")).thenReturn(mockPreparedStatement);
		final FileInputStream inputStream = mock(FileInputStream.class);
		when(mockPreparedStatement.executeUpdate()).thenReturn(1);
		when(inputStream.available()).thenReturn(1024);
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			@Override
			protected void updateAsset(Asset asset, PreparedStatement preparedStatement) {
				
			}
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		
		dataBaseService.updateAssets(assets);
		verify(dbHelper).closeConnection(mockConnection);
	}
	
	@Test
	public void updateAsset()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"),"test_table","test_column","","","","","",0,"",0,0,null);
		asset.setFileSizeCompatible(true);
		
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("update dbo.file1 set fileData=? where id=?")).thenReturn(mockPreparedStatement);
		final FileInputStream inputStream = mock(FileInputStream.class);
		when(mockPreparedStatement.executeUpdate()).thenReturn(1);
		when(inputStream.available()).thenReturn(1024);
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			
			@Override
			protected FileInputStream getInputStream(File file)
					throws Exception {
				
				return mockFileInputStream;
			}
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		dataBaseService.updateAsset(asset, mockPreparedStatement);
		assertEquals(asset.isUpdated(), true);
		verify(dbHelper).closeStatement(mockPreparedStatement);
	}
	
	@Test
	public void updateAssetNoRecordUpdated()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"),"test_table","test_column","","","","","",0,"",0,0,null);
		asset.setFileSizeCompatible(true);
		
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("update dbo.file1 set fileData=? where id=?")).thenReturn(mockPreparedStatement);
		final FileInputStream inputStream = mock(FileInputStream.class);
		when(mockPreparedStatement.executeUpdate()).thenReturn(0);
		when(inputStream.available()).thenReturn(1024);
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			
			@Override
			protected FileInputStream getInputStream(File file)
					throws Exception {
				
				return mockFileInputStream;
			}
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		dataBaseService.updateAsset(asset, mockPreparedStatement);
		assertEquals(asset.isUpdated(), false);
		verify(dbHelper).closeStatement(mockPreparedStatement);
	}
	
	@Test
	public void updateAssetFileSizeNotCompatible()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"),"test_table","test_column","","","","","",0,"",0,0,null);
		asset.setFileSizeCompatible(false);
		
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("update dbo.file1 set fileData=? where id=?")).thenReturn(mockPreparedStatement);
		final FileInputStream inputStream = mock(FileInputStream.class);
		when(mockPreparedStatement.executeUpdate()).thenReturn(0);
		when(inputStream.available()).thenReturn(1024);
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			
			@Override
			protected FileInputStream getInputStream(File file)
					throws Exception {
				
				return mockFileInputStream;
			}
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		dataBaseService.updateAsset(asset, mockPreparedStatement);
		assertEquals(asset.isUpdated(), false);
		verify(dbHelper).closeStatement(mockPreparedStatement);
	}
	@Test
	public void updateAssetFileNotFoundException()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"),"test_table","test_column","","","","","",0,"",0,0,null);
		asset.setFileSizeCompatible(true);
		
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("update dbo.file1 set fileData=? where id=?")).thenReturn(mockPreparedStatement);
		final FileInputStream inputStream = mock(FileInputStream.class);
		when(mockPreparedStatement.executeUpdate()).thenReturn(0);
		when(inputStream.available()).thenReturn(1024);
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			
			
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		dataBaseService.updateAsset(asset, mockPreparedStatement);
		assertEquals(asset.isUpdated(), false);
		verify(dbHelper).closeStatement(mockPreparedStatement);
	}
	
	@Test
	public void downloadAssets()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"),"test_table","test_column","","","","","",0,"",0,0,null);
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(asset);
		String tableName=asset.getTableName();
		String columnName=asset.getColumnName();
		Blob blob = null;
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("SELECT HTML_ASSET_BYTES FROM " + tableName + " WHERE " + columnName + " =?")).thenReturn(mockPreparedStatement);
		final FileInputStream inputStream = mock(FileInputStream.class);
		when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.getBlob("HTML_ASSET_BYTES")).thenReturn(blob);
		when(inputStream.available()).thenReturn(1024);
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			@Override
			protected void downloadAsset(Asset asset, PreparedStatement preparedStatement) {
				
			}
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		
		dataBaseService.downloadAssets(assets);
		verify(dbHelper).closeConnection(mockConnection);
	}
	
	@Test
	public void downloadAsset()throws Exception {
		
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"),"test_table","test_column","","","","","",0,"",0,0,null);
		asset.setFileSizeCompatible(true);
		String tableName=asset.getTableName();
		String columnName=asset.getColumnName();
		byte[] buffer = new byte[4096];
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("SELECT HTML_ASSET_BYTES FROM " + tableName + " WHERE " + columnName + " =?")).thenReturn(mockPreparedStatement);
		when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getBlob("HTML_ASSET_BYTES")).thenReturn(mockBlob);
		when(mockBlob.getBinaryStream()).thenReturn(mockInputStream);
		when(mockInputStream.read(buffer)).thenReturn(0).thenReturn(-1);
		
		
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			
			@Override
			protected FileInputStream getInputStream(File file)
					throws Exception {
				
				return mockFileInputStream;
			}
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		dataBaseService.downloadAsset(asset, mockPreparedStatement);
		assertEquals(asset.isDownloaded(), true);
		verify(mockInputStream).close();
	}
	@Test
	public void insertAssets()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"),"test_table","test_column","","","","","",0,"",0,0,null);
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(asset);
		String tableName=asset.getTableName();
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("INSERT INTO " +tableName+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")).thenReturn(mockPreparedStatement);
		final FileInputStream inputStream = mock(FileInputStream.class);
		when(mockPreparedStatement.executeUpdate()).thenReturn(1);
		when(inputStream.available()).thenReturn(1024);
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			@Override
			protected void insertAsset(Asset asset, PreparedStatement preparedStatement) {
				
			}
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		
		dataBaseService.insertAssets(assets);
		verify(dbHelper).closeConnection(mockConnection);
	}
	@Test
	public void insertAsset()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"),"test_table","test_column","","","","","",0,"",0,0,null);
		asset.setFileSizeCompatible(true);
		asset.setUploaded(true);
		String tableName=asset.getTableName();
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("INSERT INTO " +tableName+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")).thenReturn(mockPreparedStatement);
		final FileInputStream inputStream = mock(FileInputStream.class);
		when(mockPreparedStatement.executeUpdate()).thenReturn(1);
		when(inputStream.available()).thenReturn(1024);
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			
			@Override
			protected FileInputStream getInputStream(File file)
					throws Exception {
				
				return mockFileInputStream;
			}
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		dataBaseService.insertAsset(asset, mockPreparedStatement);
		assertEquals(asset.isUploaded(), true);
		verify(dbHelper).closeStatement(mockPreparedStatement);
	}
	
	@Test
	public void insertAssetNoRecordUpdated()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"),"test_table","test_column","","","","","",0,"",0,0,null);
		asset.setFileSizeCompatible(true);
		asset.setUploaded(false);
		String tableName=asset.getTableName();
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("INSERT INTO " +tableName+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")).thenReturn(mockPreparedStatement);
		final FileInputStream inputStream = mock(FileInputStream.class);
		when(mockPreparedStatement.executeUpdate()).thenReturn(0);
		when(inputStream.available()).thenReturn(1024);
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			
			@Override
			protected FileInputStream getInputStream(File file)
					throws Exception {
				
				return mockFileInputStream;
			}
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		dataBaseService.updateAsset(asset, mockPreparedStatement);
		assertEquals(asset.isUploaded(), false);
		verify(dbHelper).closeStatement(mockPreparedStatement);
	}
	
	@Test
	public void insertAssetFileSizeNotCompatible()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"),"test_table","test_column","","","","","",0,"",0,0,null);
		asset.setFileSizeCompatible(false);
		String tableName=asset.getTableName();
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("INSERT INTO " +tableName+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")).thenReturn(mockPreparedStatement);
		final FileInputStream inputStream = mock(FileInputStream.class);
		when(mockPreparedStatement.executeUpdate()).thenReturn(0);
		when(inputStream.available()).thenReturn(1024);
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			
			@Override
			protected FileInputStream getInputStream(File file)
					throws Exception {
				
				return mockFileInputStream;
			}
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		dataBaseService.updateAsset(asset, mockPreparedStatement);
		assertEquals(asset.isUploaded(), false);
		verify(dbHelper).closeStatement(mockPreparedStatement);
	}
	@Test
	public void insertAssetFileNotFoundException()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"),"test_table","test_column","","","","","",0,"",0,0,null);
		asset.setFileSizeCompatible(true);
		String tableName=asset.getTableName();
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("INSERT INTO " +tableName+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")).thenReturn(mockPreparedStatement);
		final FileInputStream inputStream = mock(FileInputStream.class);
		when(mockPreparedStatement.executeUpdate()).thenReturn(0);
		when(inputStream.available()).thenReturn(1024);
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			
			
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		dataBaseService.updateAsset(asset, mockPreparedStatement);
		assertEquals(asset.isUploaded(), false);
		verify(dbHelper).closeStatement(mockPreparedStatement);
	}
	
}
