package assets.upload.beans;

import java.io.File;

public class Asset implements Cloneable {
	
	public Asset(String id, String fileName, File file) {
		super();
		this.id = id;
		this.name = fileName;
		this.file = file;
	}
	
	String id;
	String name;
	File file;
	boolean isUploaded;
	boolean isFileSizeCompatible;
	
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
	@Override
	public Object clone() {
		
		Asset cloneObject = new Asset(this.getId(), this.getName(), this.getFile());
		cloneObject.setUploaded(this.isUploaded());	
		cloneObject.setFileSizeCompatible(this.isFileSizeCompatible());
		return cloneObject;
	}
	
	
	
}
