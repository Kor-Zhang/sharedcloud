package com.sharedcloud.pc.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Adm entity. @author MyEclipse Persistence Tools
 */

public class Adm implements java.io.Serializable {

	// Fields

	private String admid;
	private String email;
	private String password;
	private Byte status;
	private Set usersMagLogs = new HashSet(0);
	private Set sharedfilesMagLogs = new HashSet(0);
	private Set filesMagLogs = new HashSet(0);

	// Constructors

	/** default constructor */
	public Adm() {
	}

	/** minimal constructor */
	public Adm(String admid, String email, String password, Byte status) {
		this.admid = admid;
		this.email = email;
		this.password = password;
		this.status = status;
	}

	/** full constructor */
	public Adm(String admid, String email, String password, Byte status,
			Set usersMagLogs, Set sharedfilesMagLogs, Set filesMagLogs) {
		this.admid = admid;
		this.email = email;
		this.password = password;
		this.status = status;
		this.usersMagLogs = usersMagLogs;
		this.sharedfilesMagLogs = sharedfilesMagLogs;
		this.filesMagLogs = filesMagLogs;
	}

	// Property accessors

	public String getAdmid() {
		return this.admid;
	}

	public void setAdmid(String admid) {
		this.admid = admid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Set getUsersMagLogs() {
		return this.usersMagLogs;
	}

	public void setUsersMagLogs(Set usersMagLogs) {
		this.usersMagLogs = usersMagLogs;
	}

	public Set getSharedfilesMagLogs() {
		return this.sharedfilesMagLogs;
	}

	public void setSharedfilesMagLogs(Set sharedfilesMagLogs) {
		this.sharedfilesMagLogs = sharedfilesMagLogs;
	}

	public Set getFilesMagLogs() {
		return this.filesMagLogs;
	}

	public void setFilesMagLogs(Set filesMagLogs) {
		this.filesMagLogs = filesMagLogs;
	}

}