package com.sharedcloud.pc.model;

import java.sql.Timestamp;

/**
 * DownloadHistory entity. @author MyEclipse Persistence Tools
 */

public class DownloadHistory implements java.io.Serializable {

	// Fields

	private String id;
	private Files files;
	private Users users;
	private Timestamp downloadtime;
	private Byte status;

	// Constructors

	/** default constructor */
	public DownloadHistory() {
	}

	/** full constructor */
	public DownloadHistory(String id, Files files, Users users,
			Timestamp downloadtime, Byte status) {
		this.id = id;
		this.files = files;
		this.users = users;
		this.downloadtime = downloadtime;
		this.status = status;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Files getFiles() {
		return this.files;
	}

	public void setFiles(Files files) {
		this.files = files;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Timestamp getDownloadtime() {
		return this.downloadtime;
	}

	public void setDownloadtime(Timestamp downloadtime) {
		this.downloadtime = downloadtime;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

}