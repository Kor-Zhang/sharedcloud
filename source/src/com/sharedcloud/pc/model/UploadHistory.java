package com.sharedcloud.pc.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.sharedcloud.pc.utils.GConfig;


/**
 * UploadHistory entity. @author MyEclipse Persistence Tools
 */

public class UploadHistory implements java.io.Serializable {
	

	public static String MUSICEXTS = null;
	public static String OTHEREXTS = null;
	public static String PICEXTS = null;
	public static String TOREXTS = null;
	public static String TXTEXTS = null;
	public static String ZIPEXTS = null;
	public static String VIDEOEXTS = null;
	public static String DIRECTORYExts = null;
	public static String ALLExts = null;
	
	
	static{
		MUSICEXTS = GConfig.getProps().get("musicExts").toString().toLowerCase().replaceAll("[ ]*", "");
		OTHEREXTS = GConfig.getProps().get("otherExts").toString().toLowerCase().replaceAll("[ ]*", "");
		PICEXTS = GConfig.getProps().get("picExts").toString().toLowerCase().replaceAll("[ ]*", "");
		TOREXTS = GConfig.getProps().get("torExts").toString().toLowerCase().replaceAll("[ ]*", "");
		TXTEXTS = GConfig.getProps().get("txtExts").toString().toLowerCase().replaceAll("[ ]*", "");
		ZIPEXTS = GConfig.getProps().get("zipExts").toString().toLowerCase().replaceAll("[ ]*", "");
		VIDEOEXTS = GConfig.getProps().get("videoExts").toString().toLowerCase().replaceAll("[ ]*", "");
		DIRECTORYExts = GConfig.getProps().get("directoryExts").toString().toLowerCase().replaceAll("[ ]*", "");
		ALLExts = MUSICEXTS+","+OTHEREXTS+","+OTHEREXTS+","+PICEXTS+","+TOREXTS+","+TXTEXTS+","+ZIPEXTS+","+VIDEOEXTS+","+DIRECTORYExts;
	}
	
	/**
	 * 定义文件删除状态
	 */
	public static final Byte STATUS_DELETE = -1;
	/**
	 * 定义文件激活状态
	 */
	public static final Byte STATUS_ACTIVITY = 1;
	/**
	 * 分页大小
	 */
	public static final Integer ROWS_DEFAULT = 15;
	// Fields

	private String id;
	private Files files;
	private Users users;
	private Timestamp uploadtime;
	private Byte status;
	private String filename;
	private UploadHistory uploadhistory;
	private Set childs = new HashSet(0);
	
	
	
	public Set getChilds() {
		return childs;
	}
	public void setChilds(Set childs) {
		this.childs = childs;
	}
	public UploadHistory() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Files getFiles() {
		return files;
	}
	public void setFiles(Files files) {
		this.files = files;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public Timestamp getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(Timestamp uploadtime) {
		this.uploadtime = uploadtime;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public UploadHistory getUploadhistory() {
		return uploadhistory;
	}
	public void setUploadhistory(UploadHistory uploadhistory) {
		this.uploadhistory = uploadhistory;
	}
	
	
	
	public UploadHistory(String id, Files files, Users users,
			Timestamp uploadtime, Byte status, String filename,
			UploadHistory uploadhistory, Set childs) {
		super();
		this.id = id;
		this.files = files;
		this.users = users;
		this.uploadtime = uploadtime;
		this.status = status;
		this.filename = filename;
		this.uploadhistory = uploadhistory;
		this.childs = childs;
	}
	/**
	 * hql的new构造法；
	 * @param id
	 * @param files
	 * @param users
	 * @param uploadtime
	 * @param status
	 * @param filename
	 */
	public UploadHistory(String id, Files files, Users users,
			Date uploadtime, Byte status, String filename) {
		super();
		this.id = id;
		this.files = files;
		this.users = users;
		this.uploadtime = new Timestamp(uploadtime.getTime());
		this.status = status;
		this.filename = filename;
	}
	@Override
	public String toString() {
		return "UploadHistory [id=" + id + ", files=" + files + ", users="
				+ users + ", uploadtime=" + uploadtime + ", status=" + status
				+ ", filename=" + filename + ", uploadhistory=" + uploadhistory
				+ "]";
	}
	

}