package assets.upload.beans;

import java.io.File;

public class Asset implements Cloneable {
	
	public Asset(String id, String fileName, File file,String tableName,String columnName) {
		super();
		this.id = id;
		this.name = fileName;
		this.file = file;
		this.tableName=tableName;
		this.columnName=columnName;
	}

	String id;
	String name;
	File file;
	boolean isUploaded;
	boolean isFileSizeCompatible;
	String tableName;
	String columnName;
	boolean isDownloaded;
	
	
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

	@Override
	public Object clone() {
		
		Asset cloneObject = new Asset(this.getId(), this.getName(), this.getFile(),this.getTableName(),this.getColumnName());
		cloneObject.setUploaded(this.isUploaded());	
		cloneObject.setFileSizeCompatible(this.isFileSizeCompatible());
		return cloneObject;
	}
	
	
	
}
