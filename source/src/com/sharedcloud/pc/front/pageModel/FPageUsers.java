package com.sharedcloud.pc.front.pageModel;

import java.sql.Timestamp;
/**
 * 前台类，接收参数，Users的pagemodel
 * @author Kor_Zhang
 *
 */
public class FPageUsers extends FPageBase {

	private String userid;
	private String email;
	private String password;
	private String username;
	private Timestamp birthday;
	private Short age;
	private String sex;
	private String intro;
	private Double savedfilesize;
	private Double totalfilesize;
	private Byte status;
	private Timestamp updatepwdreqsubmittime;
	private String activitycode;
	private Timestamp registtime;
	private Timestamp activitytime;
	private String updatepwdverifycode;
	private String headimgpath;

	/* 附加属性 */
	/**
	 * 验证码
	 */
	private String verifyCode;
	/**
	 * "记住我"
	 */
	private String remeberMe;
	
	/**
	 * 请求的头像的尺寸：162x162;48x48；等
	 */
	private String headImgSize;
	
	
	
	
	public Timestamp getUpdatepwdreqsubmittime() {
		return updatepwdreqsubmittime;
	}
	public void setUpdatepwdreqsubmittime(Timestamp updatepwdreqsubmittime) {
		this.updatepwdreqsubmittime = updatepwdreqsubmittime;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Timestamp getBirthday() {
		return birthday;
	}
	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}
	public Short getAge() {
		return age;
	}
	public void setAge(Short age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public Double getSavedfilesize() {
		return savedfilesize;
	}
	public void setSavedfilesize(Double savedfilesize) {
		this.savedfilesize = savedfilesize;
	}
	public Double getTotalfilesize() {
		return totalfilesize;
	}
	public void setTotalfilesize(Double totalfilesize) {
		this.totalfilesize = totalfilesize;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getActivitycode() {
		return activitycode;
	}
	public void setActivitycode(String activitycode) {
		this.activitycode = activitycode;
	}
	public Timestamp getRegisttime() {
		return registtime;
	}
	public void setRegisttime(Timestamp registtime) {
		this.registtime = registtime;
	}
	public Timestamp getActivitytime() {
		return activitytime;
	}
	public void setActivitytime(Timestamp activitytime) {
		this.activitytime = activitytime;
	}
	public String getUpdatepwdverifycode() {
		return updatepwdverifycode;
	}
	public void setUpdatepwdverifycode(String updatepwdverifycode) {
		this.updatepwdverifycode = updatepwdverifycode;
	}
	public String getHeadimgpath() {
		return headimgpath;
	}
	public void setHeadimgpath(String headimgpath) {
		this.headimgpath = headimgpath;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getRemeberMe() {
		return remeberMe;
	}
	public void setRemeberMe(String remeberMe) {
		this.remeberMe = remeberMe;
	}
	public String getHeadImgSize() {
		return headImgSize;
	}
	public void setHeadImgSize(String headImgSize) {
		this.headImgSize = headImgSize;
	}
	public FPageUsers() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FPageUsers [userid=" + userid + ", email=" + email
				+ ", password=" + password + ", username=" + username
				+ ", birthday=" + birthday + ", age=" + age + ", sex=" + sex
				+ ", intro=" + intro + ", savedfilesize=" + savedfilesize
				+ ", totalfilesize=" + totalfilesize + ", status=" + status
				+ ", updatepwdreqsubmittime=" + updatepwdreqsubmittime
				+ ", activitycode=" + activitycode + ", registtime="
				+ registtime + ", activitytime=" + activitytime
				+ ", updatepwdverifycode=" + updatepwdverifycode
				+ ", headimgpath=" + headimgpath + ", verifyCode=" + verifyCode
				+ ", remeberMe=" + remeberMe + ", headImgSize=" + headImgSize
				+ "]";
	}
	public FPageUsers(String userid, String email, String password,
			String username, Timestamp birthday, Short age, String sex,
			String intro, Double savedfilesize, Double totalfilesize,
			Byte status, Timestamp updatepwdreqsubmittime, String activitycode,
			Timestamp registtime, Timestamp activitytime,
			String updatepwdverifycode, String headimgpath, String verifyCode,
			String remeberMe, String headImgSize) {
		super();
		this.userid = userid;
		this.email = email;
		this.password = password;
		this.username = username;
		this.birthday = birthday;
		this.age = age;
		this.sex = sex;
		this.intro = intro;
		this.savedfilesize = savedfilesize;
		this.totalfilesize = totalfilesize;
		this.status = status;
		this.updatepwdreqsubmittime = updatepwdreqsubmittime;
		this.activitycode = activitycode;
		this.registtime = registtime;
		this.activitytime = activitytime;
		this.updatepwdverifycode = updatepwdverifycode;
		this.headimgpath = headimgpath;
		this.verifyCode = verifyCode;
		this.remeberMe = remeberMe;
		this.headImgSize = headImgSize;
	}
	
	
}