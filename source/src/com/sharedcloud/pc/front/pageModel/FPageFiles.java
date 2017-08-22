package com.sharedcloud.pc.front.pageModel;

/**
 * 前台类，接收页面参数，Files类的页面pagemodel
 * @author Kor_Zhang
 *
 */
public class FPageFiles extends FPageBase {
	private String fileid;
	private Double filesize;
	private String filepath;
	private Byte status;
	private Byte isdir;
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public Double getFilesize() {
		return filesize;
	}
	public void setFilesize(Double filesize) {
		this.filesize = filesize;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Byte getIsdir() {
		return isdir;
	}
	public void setIsdir(Byte isdir) {
		this.isdir = isdir;
	}
	@Override
	public String toString() {
		return "FPageFiles [fileid=" + fileid + ", filesize=" + filesize
				+ ", filepath=" + filepath + ", status=" + status + ", isdir="
				+ isdir + "]";
	}
	public FPageFiles() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FPageFiles(String fileid, Double filesize, String filepath,
			Byte status, Byte isdir) {
		super();
		this.fileid = fileid;
		this.filesize = filesize;
		this.filepath = filepath;
		this.status = status;
		this.isdir = isdir;
	}
	

	
	
}