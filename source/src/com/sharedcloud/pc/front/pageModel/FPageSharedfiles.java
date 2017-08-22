package com.sharedcloud.pc.front.pageModel;

import java.sql.Timestamp;
/**
 * 前台类，接收页面参数，Sharedfiles类的页面pagemodel
 * @author Kor_Zhang
 *
 */
public class FPageSharedfiles  extends FPageBase{
	private String sharedfileid;
	private String uploadHistoryId ;
	private String userid;
	private Boolean ispublic;
	private String password;
	private Timestamp sharedtime;
	private Double downloadnumber;
	private Double browsenumber;
	private Double savenumber;
	private Byte status;
	
	//其他属性
	//分享的url
	private String shreadUrl;
	
	/**
	 * 额外属性
	 */
	
	//文件的后缀
	private String ext;
	
	private String filename;
	private Double filesize;
	
	
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Double getFilesize() {
		return filesize;
	}
	public void setFilesize(Double filesize) {
		this.filesize = filesize;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getShreadUrl() {
		return shreadUrl;
	}
	public void setShreadUrl(String shreadUrl) {
		this.shreadUrl = shreadUrl;
	}
	public String getSharedfileid() {
		return sharedfileid;
	}
	public void setSharedfileid(String sharedfileid) {
		this.sharedfileid = sharedfileid;
	}
	public String getUploadHistoryId() {
		return uploadHistoryId;
	}
	public void setUploadHistoryId(String uploadHistoryId) {
		this.uploadHistoryId = uploadHistoryId;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Boolean getIspublic() {
		return ispublic;
	}
	public void setIspublic(Boolean ispublic) {
		this.ispublic = ispublic;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Timestamp getSharedtime() {
		return sharedtime;
	}
	public void setSharedtime(Timestamp sharedtime) {
		this.sharedtime = sharedtime;
	}
	public Double getDownloadnumber() {
		return downloadnumber;
	}
	public void setDownloadnumber(Double downloadnumber) {
		this.downloadnumber = downloadnumber;
	}
	public Double getBrowsenumber() {
		return browsenumber;
	}
	public void setBrowsenumber(Double browsenumber) {
		this.browsenumber = browsenumber;
	}
	public Double getSavenumber() {
		return savenumber;
	}
	public void setSavenumber(Double savenumber) {
		this.savenumber = savenumber;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "FPageSharedfiles [sharedfileid=" + sharedfileid
				+ ", uploadHistoryId=" + uploadHistoryId + ", userid=" + userid
				+ ", ispublic=" + ispublic + ", password=" + password
				+ ", sharedtime=" + sharedtime + ", downloadnumber="
				+ downloadnumber + ", browsenumber=" + browsenumber
				+ ", savenumber=" + savenumber + ", status=" + status + "]";
	}
	public FPageSharedfiles() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FPageSharedfiles(String sharedfileid, String uploadHistoryId,
			String userid, Boolean ispublic, String password,
			Timestamp sharedtime, Double downloadnumber, Double browsenumber,
			Double savenumber, Byte status, String shreadUrl, String ext,
			String filename, Double filesize) {
		super();
		this.sharedfileid = sharedfileid;
		this.uploadHistoryId = uploadHistoryId;
		this.userid = userid;
		this.ispublic = ispublic;
		this.password = password;
		this.sharedtime = sharedtime;
		this.downloadnumber = downloadnumber;
		this.browsenumber = browsenumber;
		this.savenumber = savenumber;
		this.status = status;
		this.shreadUrl = shreadUrl;
		this.ext = ext;
		this.filename = filename;
		this.filesize = filesize;
	}
	public FPageSharedfiles(String sharedfileid, String uploadHistoryId,
			String userid, Boolean ispublic, String password,
			Timestamp sharedtime, Double downloadnumber,
			Double browsenumber, Double savenumber, Byte status) {
		super();
		this.sharedfileid = sharedfileid;
		this.uploadHistoryId = uploadHistoryId;
		this.userid = userid;
		this.ispublic = ispublic;
		this.password = password;
		this.sharedtime = sharedtime;
		this.downloadnumber = downloadnumber;
		this.browsenumber = browsenumber;
		this.savenumber = savenumber;
		this.status = status;
	}

}
