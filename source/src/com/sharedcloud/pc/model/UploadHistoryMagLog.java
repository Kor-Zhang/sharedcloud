package com.sharedcloud.pc.model;

/**
 * 管理员管理uploadhistory表记录
 * @author Kor_Zhang
 *
 */

public class UploadHistoryMagLog implements java.io.Serializable {


	private String id;
	private UploadHistory uploadHistory;
	private Adm adm;
	private String operation;
	private Boolean status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UploadHistory getUploadHistory() {
		return uploadHistory;
	}
	public void setUploadHistory(UploadHistory uploadHistory) {
		this.uploadHistory = uploadHistory;
	}
	public Adm getAdm() {
		return adm;
	}
	public void setAdm(Adm adm) {
		this.adm = adm;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public UploadHistoryMagLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UploadHistoryMagLog(String id, UploadHistory uploadHistory, Adm adm,
			String operation, Boolean status) {
		super();
		this.id = id;
		this.uploadHistory = uploadHistory;
		this.adm = adm;
		this.operation = operation;
		this.status = status;
	}
	@Override
	public String toString() {
		return "UploadHistoryMagLog [id=" + id + ", uploadHistory="
				+ uploadHistory + ", adm=" + adm + ", operation=" + operation
				+ ", status=" + status + "]";
	}


}