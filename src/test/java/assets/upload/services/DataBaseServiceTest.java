package assets.upload.services;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
	
	@Before
    public void setUp()throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void inserAssets()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"));
		List<Asset> assets = new ArrayList<Asset>();
		assets.add(asset);
		when(dbHelper.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement("update dbo.file1 set fileData=? where id=?")).thenReturn(mockPreparedStatement);
		final FileInputStream inputStream = mock(FileInputStream.class);
		when(mockPreparedStatement.executeUpdate()).thenReturn(1);
		when(inputStream.available()).thenReturn(1024);
		DataBaseService dataBaseService = new DataBaseService(mockProperties){
			@Override
			protected void inserAsset(Asset asset, PreparedStatement preparedStatement) {
				
			}
		};
		Field privateField = DataBaseService.class.getDeclaredField("dbHelper");
		privateField.setAccessible(true);
		privateField.set(dataBaseService, dbHelper);
		
		dataBaseService.insertAssets(assets);
		verify(dbHelper).closeConnection(mockConnection);
	}
	
	@Test
	public void inserAsset()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"));
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
		dataBaseService.inserAsset(asset, mockPreparedStatement);
		assertEquals(asset.isUploaded(), true);
		verify(dbHelper).closeStatement(mockPreparedStatement);
	}
	
	@Test
	public void inserAssetNoRecordUpdated()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"));
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
		dataBaseService.inserAsset(asset, mockPreparedStatement);
		assertEquals(asset.isUploaded(), false);
		verify(dbHelper).closeStatement(mockPreparedStatement);
	}
	
	@Test
	public void inserAssetFileSizeNotCompatible()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"));
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
		dataBaseService.inserAsset(asset, mockPreparedStatement);
		assertEquals(asset.isUploaded(), false);
		verify(dbHelper).closeStatement(mockPreparedStatement);
	}
	@Test
	public void inserAssetFileNotFoundException()throws Exception {
		Asset asset = new Asset("102", "102.tld", new File("e://abc.xyz"));
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
		dataBaseService.inserAsset(asset, mockPreparedStatement);
		assertEquals(asset.isUploaded(), false);
		verify(dbHelper).closeStatement(mockPreparedStatement);
	}
}
