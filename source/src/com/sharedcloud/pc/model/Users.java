package com.sharedcloud.pc.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.sharedcloud.pc.utils.GConfig;
import com.sharedcloud.pc.utils.GUtils;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */

public class Users implements java.io.Serializable {
	/*状态*/
	/**
	 * 未激活；已注册；
	 */
	public static final Byte STATUS_UNACTIVITY = -2;
	/**
	 * 冻结；注册验证后；
	 */
	public static final Byte STATUS_FROZEN = -1;
	/**
	 * 激活；已注册验证；
	 */
	public static final Byte STATUS_ACTIVITY = 1;
	
	/**
	 * 修改密码请求提交后的有效验证时间
	 */
	public static String UPDATE_PWD_DISABLE_TIME;
	/**
	 * 用户email的正则
	 */
	public static String USER_EMAIL_REG;
	
	/**
	 * 用户pwd的正则
	 */
	public static String USER_PWD_REG;
	/**
	 * 文件空间默认大小(1T)
	 */
	public static final Double TOTALFILESIZE_DEFALUT = 1024*1024*1024*1024D;
	
	/**
	 * 默认生日
	 */
	public static Timestamp BIRTHDAY_DEFALUT;
	/**
	 * 默认年龄
	 */
	public static Short AGE_DEFALUT;
	/**
	 * 默认用户已用文件空间
	 */
	public final static Double SAVEDFILESIZE_DEFALUT = 0D;
	/**
	 * 默认性别
	 */
	public final static String SEX_DEFAULT = "未知";
	
	/**
	 * 定义用户头像的默认图片
	 */
	public final static String HEADIMGNAME_DEFAULT = "default.png";
	/**
	 * 定义用户验证码名称
	 */
	public final static String VERIFY_CODE_NAME = "verifyCode";
	
	/**
	 * 定义session中user在线的name
	 */
	public final static String SESSION_USER_NAME = "user";
	/**
	 * 定义request中user在线的name
	 */
	public final static String REQUEST_USER_NAME = "reqUser";
	/**
	 * 定义cookie中userid(即Users的主键)的name
	 */
	public final static String COOKIE_USERID_NAME = "com.sharedcloud.cookie.userid";
	/**
	 * 定义cookie中password(即Users的password)的name
	 */
	public final static String COOKIE_PASSWORD_NAME = "com.sharedcloud.cookie.password";
	/**
	 * 定义默认的intro
	 */
	public final static String INTRO_DEFAULT = "";
		
	/**
	 * 配置文件中“激活码失效时间”的参数name
	 */
	public final static String PROPS_ACTIVITYCODEDISABLETIME_NAME = "activityCodeDisableTime";
	/**
	 * 用户激活码的有效激活时间（秒级）
	 */
	public static Double ACTIVITY_CODE_DISABLE_TIME;
	/**
	 * 用户头像储存路径
	 */
	public static String HEADIMGPATH;
	/*静态加载*/
	static{
		try {
			BIRTHDAY_DEFALUT = new Timestamp(new SimpleDateFormat("yyyy-mm-dd").parse("1995-12-17").getTime());
			AGE_DEFALUT = (short) (GUtils.getAgeByYears(BIRTHDAY_DEFALUT.getTime()));
			ACTIVITY_CODE_DISABLE_TIME = Double.parseDouble(GConfig.getValue(Users.PROPS_ACTIVITYCODEDISABLETIME_NAME).toString());
			HEADIMGPATH = GConfig.getValue("headImgPath").toString();
			UPDATE_PWD_DISABLE_TIME = GConfig.getValue("updatePwdVerifyCodeDisableTime").toString();
			USER_EMAIL_REG = GConfig.getValue("userEmailReg").toString();
			USER_PWD_REG = GConfig.getValue("userPwdReg").toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	// Fields

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
	private String activitycode;
	private Timestamp registtime;
	private Timestamp activitytime;
	private String updatepwdverifycode;
	private Timestamp updatepwdreqsubmittime;
	private String headimgpath;
	private Set uploadHistories = new HashSet(0);
	private Set usersMagLogs = new HashSet(0);
	private Set sharedfileses = new HashSet(0);
	private Set downloadHistories = new HashSet(0);
	
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
	public Set getUploadHistories() {
		return uploadHistories;
	}
	public void setUploadHistories(Set uploadHistories) {
		this.uploadHistories = uploadHistories;
	}
	public Set getUsersMagLogs() {
		return usersMagLogs;
	}
	public void setUsersMagLogs(Set usersMagLogs) {
		this.usersMagLogs = usersMagLogs;
	}
	public Set getSharedfileses() {
		return sharedfileses;
	}
	public void setSharedfileses(Set sharedfileses) {
		this.sharedfileses = sharedfileses;
	}
	public Set getDownloadHistories() {
		return downloadHistories;
	}
	public void setDownloadHistories(Set downloadHistories) {
		this.downloadHistories = downloadHistories;
	}
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Users [userid=" + userid + ", email=" + email + ", password="
				+ password + ", username=" + username + ", birthday="
				+ birthday + ", age=" + age + ", sex=" + sex + ", intro="
				+ intro + ", savedfilesize=" + savedfilesize
				+ ", totalfilesize=" + totalfilesize + ", status=" + status
				+ ", activitycode=" + activitycode + ", registtime="
				+ registtime + ", activitytime=" + activitytime
				+ ", updatepwdverifycode=" + updatepwdverifycode
				+ ", updatepwdreqsubmittime=" + updatepwdreqsubmittime
				+ ", headimgpath=" + headimgpath + ", uploadHistories="
				+ uploadHistories + ", usersMagLogs=" + usersMagLogs
				+ ", sharedfileses=" + sharedfileses + ", downloadHistories="
				+ downloadHistories + "]";
	}
	public Users(String userid, String email, String password, String username,
			Timestamp birthday, Short age, String sex, String intro,
			Double savedfilesize, Double totalfilesize, Byte status,
			String activitycode, Timestamp registtime, Timestamp activitytime,
			String updatepwdverifycode, Timestamp updatepwdreqsubmittime,
			String headimgpath, Set uploadHistories, Set usersMagLogs,
			Set sharedfileses, Set downloadHistories) {
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
		this.activitycode = activitycode;
		this.registtime = registtime;
		this.activitytime = activitytime;
		this.updatepwdverifycode = updatepwdverifycode;
		this.updatepwdreqsubmittime = updatepwdreqsubmittime;
		this.headimgpath = headimgpath;
		this.uploadHistories = uploadHistories;
		this.usersMagLogs = usersMagLogs;
		this.sharedfileses = sharedfileses;
		this.downloadHistories = downloadHistories;
	}
	
}