package com.sharedcloud.pc.model;

/**
 * UsersMagLog entity. @author MyEclipse Persistence Tools
 */

public class UsersMagLog implements java.io.Serializable {

	// Fields

	private String id;
	private Users users;
	private Adm adm;
	private String operation;
	private Boolean status;

	// Constructors

	/** default constructor */
	public UsersMagLog() {
	}

	/** full constructor */
	public UsersMagLog(String id, Users users, Adm adm, String operation,
			Boolean status) {
		this.id = id;
		this.users = users;
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

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
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

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}