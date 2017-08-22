package com.sharedcloud.pc.model;

/**
 * FilesMagLog entity. @author MyEclipse Persistence Tools
 */

public class FilesMagLog implements java.io.Serializable {

	// Fields

	private String id;
	private Files files;
	private Adm adm;
	private String operation;
	private Byte status;

	// Constructors

	/** default constructor */
	public FilesMagLog() {
	}

	/** full constructor */
	public FilesMagLog(String id, Files files, Adm adm, String operation,
			Byte status) {
		this.id = id;
		this.files = files;
		this.adm = adm;
		this.operation = operation;
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

	public Adm getAdm() {
		return this.adm;
	}

	public void setAdm(Adm adm) {
		this.adm = adm;
	}

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

}