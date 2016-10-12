package assets.upload.beans;

import java.io.File;

public class Asset implements Cloneable {
	
	public Asset(String id, String fileName, File file,String tableName,String columnName,String assetfileName,String assetContentType,
			String thumbnailFileName,String thumbnailContentType,String organizationType,int organizationTypeValue,String availabilityGroup,
			int gameItemGroupId,int pointValue,File thumbnailFile) {
		super();
		this.id = id;
		this.name = fileName;
		this.file = file;
		this.tableName=tableName;
		this.columnName=columnName;
		this.assetfileName=assetfileName;
		this.assetContentType=assetContentType;
		this.thumbnailFileName=thumbnailFileName;
		this.thumbnailContentType=thumbnailContentType;
		this.organizationType=organizationType;
		this.organizationTypeValue=organizationTypeValue;
		this.availabilityGroup=availabilityGroup;
		this.gameItemGroupId=gameItemGroupId;
		this.pointValue=pointValue;
		this.thumbnailFile=thumbnailFile;
		
	}

	

	String id;
	String name;
	File file;
	boolean isUploaded;
	boolean isFileSizeCompatible;
	String tableName;
	String columnName;
	boolean isDownloaded;
	String assetfileName;
	String assetContentType;
	String thumbnailFileName;
	String thumbnailContentType;
	String organizationType;
	int organizationTypeValue;
	String availabilityGroup;
	int gameItemGroupId;
	int pointValue;
	File thumbnailFile;
	boolean isUpdated;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isUploaded() {
		return isUploaded;
	}
	public void setUploaded(boolean isUploaded) {
		this.isUploaded = isUploaded;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public boolean isFileSizeCompatible() {
		return isFileSizeCompatible;
	}
	public void setFileSizeCompatible(boolean isFileSizeCompatible) {
		this.isFileSizeCompatible = isFileSizeCompatible;
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public boolean isDownloaded() {
		return isDownloaded;
	}
	public void setDownloaded(boolean isDownloaded) {
		this.isDownloaded = isDownloaded;
	}

	public String getAssetfileName() {
		return assetfileName;
	}
	public void setAssetfileName(String assetfileName) {
		this.assetfileName = assetfileName;
	}
	public String getAssetContentType() {
		return assetContentType;
	}
	public void setAssetContentType(String assetContentType) {
		this.assetContentType = assetContentType;
	}
	public String getThumbnailFileName() {
		return thumbnailFileName;
	}
	public void setThumbnailFileName(String thumbnailFileName) {
		this.thumbnailFileName = thumbnailFileName;
	}
	public String getThumbnailContentType() {
		return thumbnailContentType;
	}
	public void setThumbnailContentType(String thumbnailContentType) {
		this.thumbnailContentType = thumbnailContentType;
	}
	public String getOrganizationType() {
		return organizationType;
	}
	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}
	public int getOrganizationTypeValue() {
		return organizationTypeValue;
	}
	public void setOrganizationTypeValue(int organizationTypeValue) {
		this.organizationTypeValue = organizationTypeValue;
	}
	public String getAvailabilityGroup() {
		return availabilityGroup;
	}
	public void setAvailabilityGroup(String availabilityGroup) {
		this.availabilityGroup = availabilityGroup;
	}
	public int getGameItemGroupId() {
		return gameItemGroupId;
	}
	public void setGameItemGroupId(int gameItemGroupId) {
		this.gameItemGroupId = gameItemGroupId;
	}
	public int getPointValue() {
		return pointValue;
	}
	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}
	public File getThumbnailFile() {
		return thumbnailFile;
	}
	public void setThumbnailFile(File thumbnailFile) {
		this.thumbnailFile = thumbnailFile;
	}
	public boolean isUpdated() {
		return isUpdated;
	}
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	@Override
	public Object clone() {
		
		Asset cloneObject = new Asset(this.getId(), this.getName(), this.getFile(),this.getTableName(),this.getColumnName(),this.getAssetfileName(),this.getAssetContentType(),this.getThumbnailFileName(),this.getThumbnailContentType(),this.getOrganizationType(),this.getOrganizationTypeValue(),this.getAvailabilityGroup(),this.getGameItemGroupId(),this.getPointValue(),this.getThumbnailFile());
		cloneObject.setUploaded(this.isUploaded());	
		cloneObject.setDownloaded(this.isDownloaded());
		cloneObject.setUpdated(this.isUpdated());
		cloneObject.setFileSizeCompatible(this.isFileSizeCompatible());
		return cloneObject;
	}
	
	
	
}
