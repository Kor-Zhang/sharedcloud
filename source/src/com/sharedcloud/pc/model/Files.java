package com.sharedcloud.pc.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import com.sharedcloud.pc.utils.GConfig;
import com.sharedcloud.pc.utils.GUtils;

/**
 * Files entity. @author MyEclipse Persistence Tools
 */

public class Files implements java.io.Serializable {

	
	/***常量**/
	/**
	 * 定义文件删除状态
	 */
	public static final Byte STATUS_DELETE = -1;
	/**
	 * 定义文件激活状态
	 */
	public static final Byte STATUS_ACTIVITY = 1;
	/**
	 * 定义文件不为目录
	 */
	public static final Byte ISDIR_NO = -1;
	/**
	 * 定义文件为目录
	 */
	public static final Byte ISDIR_YES = 1;
	/**
	 * 定义目录的大小
	 */
	public static final Double FILESIZE_DIRECTORY = -1d;
	
	/**
	 * 文件头像储存路径
	 */
	public static String FILEPATH;
	/*静态加载*/
	static{
		FILEPATH = GConfig.getProps().get("filePath").toString();
		
	}
	// Fields

	private String fileid;
	private Double filesize;
	private String filepath;
	private Byte status;
	private Byte isdir;
	private Set filesMagLogs = new HashSet(0);
	/*private Set fileses = new HashSet(0);*/
	private Set sharedfileses = new HashSet(0);
	private Set downloadHistories = new HashSet(0);
	private Set uploadHistories = new HashSet(0);
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
	public Set getFilesMagLogs() {
		return filesMagLogs;
	}
	public void setFilesMagLogs(Set filesMagLogs) {
		this.filesMagLogs = filesMagLogs;
	}
	public Set getSharedfileses() {
		return sharedfileses;
	}
	public void setSharedfileses(Set sharedfileses) {
		this.sharedfileses = sharedfileses;
	}
	public Set getDownloadHistories() {
		return downloadHistories;
	}
	public void setDownloadHistories(Set downloadHistories) {
		this.downloadHistories = downloadHistories;
	}
	public Set getUploadHistories() {
		return uploadHistories;
	}
	public void setUploadHistories(Set uploadHistories) {
		this.uploadHistories = uploadHistories;
	}
	public Files() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Files(String fileid, Double filesize, String filepath, Byte status,
			Byte isdir, Set filesMagLogs, Set sharedfileses,
			Set downloadHistories, Set uploadHistories) {
		super();
		this.fileid = fileid;
		this.filesize = filesize;
		this.filepath = filepath;
		this.status = status;
		this.isdir = isdir;
		this.filesMagLogs = filesMagLogs;
		this.sharedfileses = sharedfileses;
		this.downloadHistories = downloadHistories;
		this.uploadHistories = uploadHistories;
	}
	@Override
	public String toString() {
		return "Files [fileid=" + fileid + ", filesize=" + filesize
				+ ", filepath=" + filepath + ", status=" + status + ", isdir="
				+ isdir + ", filesMagLogs=" + filesMagLogs + ", sharedfileses="
				+ sharedfileses + ", downloadHistories=" + downloadHistories
				+ ", uploadHistories=" + uploadHistories + "]";
	}

}