package assets.upload.destinations;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import assets.upload.beans.Asset;
import assets.upload.process.AssetsDBProcess;

public class DBDestinationTest {
	
	@Mock
	Properties mockProperties;
	@Mock
	AssetsDBProcess mockAssetsDBProcess;
	
	@Before
    public void setUp()throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void upload(){
		List<Asset> assets = new ArrayList<Asset>();
		DBDestination dbDestination = new DBDestination(mockProperties, assets){
			@Override
			protected AssetsDBProcess getAssetsDBProcess() throws Exception {
				return mockAssetsDBProcess;
			}
		};
		dbDestination.upload();
		verify(mockAssetsDBProcess).start(assets);
	}
	
	@Test
	public void uploadCatchException(){
		List<Asset> assets = new ArrayList<Asset>();
		DBDestination dbDestination = new DBDestination(mockProperties, assets){
			@Override
			protected AssetsDBProcess getAssetsDBProcess() throws Exception {
				return null;
			}
		};
		dbDestination.upload();
	}
}
