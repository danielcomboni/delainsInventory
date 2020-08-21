package com.delains.model.imageicon;

import java.io.File;
import java.math.BigDecimal;

import javafx.scene.image.Image;

public class ImageIcon {

	private BigDecimal id;
	private File file;
	private String fileRetrievingName;
	private String businessTitle;
	private Image image;

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public File getFile() {
		return file;
	}

	public void setFile( File file ) {
		this.file = file;
	}

	public String getFileRetrievingName() {
		return fileRetrievingName;
	}

	public void setFileRetrievingName( String fileRetrievingName ) {
		this.fileRetrievingName = fileRetrievingName;
	}

	public String getBusinessTitle() {
		return businessTitle;
	}

	public void setBusinessTitle( String businessTitle ) {
		this.businessTitle = businessTitle;
	}

	public Image getImage() {
		return image;
	}

	public void setImage( Image image ) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "ImageIcon [id=" + id + ", file=" + file + ", fileRetrievingName=" + fileRetrievingName
				+ ", businessTitle=" + businessTitle + ", image=" + image + "]";
	}
}
