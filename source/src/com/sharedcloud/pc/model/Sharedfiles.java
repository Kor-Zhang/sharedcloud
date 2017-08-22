package com.sharedcloud.pc.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.sharedcloud.pc.utils.GUtils;

/**
 * Sharedfiles entity. @author MyEclipse Persistence Tools
 */

public class Sharedfiles implements java.io.Serializable {

	/**
	 * request的sharedfiles对象参数名称
	 */
	public static final String REQ_ATTR_SHAREDFILE_NAME = "req_sharedfiles";
	
	/**
	 * 默认下载次数
	 */
	public static final Double DOWNLOADNUMBER_DEFAULT = new Double(0);
	/**
	 * 默认浏览次数
	 */
	public static final Double BROWSENUMBER_DEFAULT = new Double(0);
	/**
	 * 默认保存次数
	 */
	public static final Double SAVENUMBER_DEFAULT = new Double(0);
	/**
	 * 激活状态
	 */
	public static final Byte STATUS_ACTIVITY = 1;
	/**
	 * 删除状态
	 */
	public static final Byte STATUS_DELETE = -1;
	/**
	 * rows默认
	 */
	public static final Integer ROWS_DEFAULT = 15;
	// Fields

	private String sharedfileid;
	private UploadHistory uploadHistory;
	private Users users;
	private Boolean ispublic;
	private String password;
	private Timestamp sharedtime;
	private Double downloadnumber;
	private Double browsenumber;
	private Double savenumber;
	private Byte status;
	private Set sharedfilesMagLogs = new HashSet(0);
	public String getSharedfileid() {
		return sharedfileid;
	}
	public void setSharedfileid(String sharedfileid) {
		this.sharedfileid = sharedfileid;
	}
	public UploadHistory getUploadHistory() {
		return uploadHistory;
	}
	public void setUploadHistory(UploadHistory uploadHistory) {
		this.uploadHistory = uploadHistory;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
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
	public Set getSharedfilesMagLogs() {
		return sharedfilesMagLogs;
	}
	public void setSharedfilesMagLogs(Set sharedfilesMagLogs) {
		this.sharedfilesMagLogs = sharedfilesMagLogs;
	}
	public Sharedfiles() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Sharedfiles [sharedfileid=" + sharedfileid + ", uploadHistory="
				+ uploadHistory + ", users=" + users + ", ispublic=" + ispublic
				+ ", password=" + password + ", sharedtime=" + sharedtime
				+ ", downloadnumber=" + downloadnumber + ", browsenumber="
				+ browsenumber + ", savenumber=" + savenumber + ", status="
				+ status + ", sharedfilesMagLogs=" + sharedfilesMagLogs + "]";
	}
	public Sharedfiles(String sharedfileid, UploadHistory uploadHistory,
			Users users, Boolean ispublic, String password,
			Timestamp sharedtime, Double downloadnumber,
			Double browsenumber, Double savenumber, Byte status,
			Set sharedfilesMagLogs) {
		super();
		this.sharedfileid = sharedfileid;
		this.uploadHistory = uploadHistory;
		this.users = users;
		this.ispublic = ispublic;
		this.password = password;
		this.sharedtime = sharedtime;
		this.downloadnumber = downloadnumber;
		this.browsenumber = browsenumber;
		this.savenumber = savenumber;
		this.status = status;
		this.sharedfilesMagLogs = sharedfilesMagLogs;
	}
	/**
	 * hql使用的构造法
	 * @param sharedfileid
	 * @param uploadHistory
	 * @param users
	 * @param ispublic
	 * @param password
	 * @param sharedtime
	 * @param downloadnumber
	 * @param browsenumber
	 * @param savenumber
	 * @param status
	 */
	public Sharedfiles(String sharedfileid, UploadHistory uploadHistory,
			Users users, Boolean ispublic, String password,
			Object sharedtime, Double downloadnumber,
			Double browsenumber, Double savenumber, Byte status) {
		super();
		this.sharedfileid = sharedfileid;
		this.uploadHistory = uploadHistory;
		this.users = users;
		this.ispublic = ispublic;
		this.password = password;
		this.sharedtime = GUtils.stringToTimestamp(sharedtime.toString());;
		this.downloadnumber = downloadnumber;
		this.browsenumber = browsenumber;
		this.savenumber = savenumber;
		this.status = status;
	}

}