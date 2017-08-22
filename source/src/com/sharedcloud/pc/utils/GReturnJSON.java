package com.sharedcloud.pc.utils;

import java.io.Serializable;

public class GReturnJSON implements Serializable{
	private String msg = "消息为初始化！";
	private Boolean success = false;
	private Object obj = new Object();
	//如果用户不在线，CheckSessionUserStatusInterceptor将设置该值为false，默认为true
	private Boolean online = true;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Boolean getOnline() {
		return online;
	}
	public void setOnline(Boolean online) {
		this.online = online;
	}
	@Override
	public String toString() {
		return "PubReturnJSON [msg=" + msg + ", success=" + success + ", obj="
				+ obj + ", online=" + online + "]";
	}
	public GReturnJSON() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GReturnJSON(String msg, Boolean success, Object obj, Boolean online) {
		super();
		this.msg = msg;
		this.success = success;
		this.obj = obj;
		this.online = online;
	}
	
	
}
