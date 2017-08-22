package com.sharedcloud.pc.model;

/**
 * SharedfilesMagLog entity. @author MyEclipse Persistence Tools
 */

public class SharedfilesMagLog implements java.io.Serializable {

	// Fields

	private String id;
	private Sharedfiles sharedfiles;
	private Adm adm;
	private String operation;
	private Byte status;

	// Constructors

	/** default constructor */
	public SharedfilesMagLog() {
	}

	/** full constructor */
	public SharedfilesMagLog(String id, Sharedfiles sharedfiles, Adm adm,
			String operation, Byte status) {
		this.id = id;
		this.sharedfiles = sharedfiles;
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

	public Sharedfiles getSharedfiles() {
		return this.sharedfiles;
	}

	public void setSharedfiles(Sharedfiles sharedfiles) {
		this.sharedfiles = sharedfiles;
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