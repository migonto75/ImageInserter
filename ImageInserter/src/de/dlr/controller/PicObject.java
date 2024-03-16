package de.dlr.controller;

import java.io.File;
import java.sql.Blob;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import de.dlr.imageinserter.InputData;

public class PicObject {
	
	private Integer id;
	private ImageIcon img;
	private Blob imgBlob;
	private String imgName;
	private String imgType;
	private Long imgSize;
	private String imgChecksum;
	private boolean ifDouble = false;
	private int doubleIndex = -1;
	private File file;
	private String imgPath;
	
	
	
	public PicObject(File file2) {
		super();
		this.file = file2;
		imgPath = this.file.getAbsolutePath();		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ImageIcon getImg() {
		return img;
	}
	public void setImg(ImageIcon img) {
		this.img = img;
	}	
	public Blob getImgBlob() {
		return imgBlob;
	}
	public void setImgBlob(Blob imgBlob) {
		this.imgBlob = imgBlob;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getImgType() {
		return imgType;
	}
	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	public Long getImgSize() {
		return imgSize;
	}
	public void setImgSize(Long imgSize) {
		this.imgSize = imgSize;
	}
	public String getImgChecksum() {
		return imgChecksum;
	}
	public void setImgChecksum(String imgChecksum) {
		this.imgChecksum = imgChecksum;
	}
	public boolean isIfDouble() {
		return ifDouble;
	}
	public void setIfDouble(boolean ifDouble) {
		this.ifDouble = ifDouble;
	}
	public int getDoubleIndex() {
		return doubleIndex;
	}
	public void setDoubleIndex(int doubleIndex) {
		this.doubleIndex = doubleIndex;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	//-----------------------------------------------------------------------------------------------------//
	
	public void setImgInfo() {
		try {
			setImg(InputData.loadAndScaleImage(imgPath));
			setImgName(InputData.getNameFromFile(imgPath));
			setImgType(InputData.getFileExtension(imgPath));
			setImgSize(InputData.getSizeFromFile(imgPath));
			setImgChecksum(InputData.getChecksumFromFile(imgPath));			
		} catch (Exception e) {
			
		}		
	}
	
	//-----------------------------------------------------------------------------------------------------//
	
	public static PicObject getPicObject(ArrayList<PicObject> plst, int id) {
		for (int i = 0; i < plst.size(); i++) {
			if(plst.get(i) != null) {			
				if(plst.get(i).getId().intValue() == id) {
					return plst.get(i);
				}
			}
		}
		return null;
	}
}
