package com.sharedcloud.pc.front.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.print.attribute.standard.PageRanges;
import javax.servlet.http.Cookie;

import com.opensymphony.xwork2.ModelDriven;
import com.sharedcloud.pc.front.pageModel.FPageUsers;
import com.sharedcloud.pc.front.service.FUsersServiceI;
import com.sharedcloud.pc.front.stati.FStatic;
import com.sharedcloud.pc.front.utils.FConfig;
import com.sharedcloud.pc.front.utils.FProjectUtils;
import com.sharedcloud.pc.front.utils.FReturnJSON;
import com.sharedcloud.pc.front.utils.FUtils;
import com.sharedcloud.pc.model.Users;
import com.sharedcloud.pc.stati.GStatic;
import com.sharedcloud.pc.utils.GProjectUtils;
/**
 * 前台users的action
 * @author Kor_Zhang
 *
 */
public class FUsersAction extends FBaseAction implements ModelDriven<FPageUsers>{
	public FPageUsers pageModel = new FPageUsers();
	public FPageUsers getModel() {
		return pageModel;
	}
	//申明fUsersService
	private FUsersServiceI fUsersService;
	public void setfUsersService(FUsersServiceI fUsersService) {
		this.fUsersService = fUsersService;
	}
	
	/**
	 * 注册一个用户
	 * @return
	 */
	public void userRegist(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			/*
			 * 判断验证码
			 */
			if(null == pageModel.getVerifyCode()||!pageModel.getVerifyCode().toLowerCase().equals(((String) getSessionAttr(Users.VERIFY_CODE_NAME)).toLowerCase())){
				throw new Exception("验证码错误。");
			}
			/*
			 * 执行事务
			 */
			fUsersService.userRegist(pageModel);
			/*
			 * 设置cookie
			 */
			setLoginCookie(true);
			/*
			 * 设置返回信息
			 */
			retJSON.setMsg("注册成功，请登录邮箱验证。");
			retJSON.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
		}finally{
			writeJSON(retJSON);
		}
		
	}
	/**
	 * 用户登录
	 * @return
	 */
	public void userLogin(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			//判断验证码
			if(null == pageModel.getVerifyCode()||!pageModel.getVerifyCode().toLowerCase().equals(((String) getSessionAttr(Users.VERIFY_CODE_NAME)).toLowerCase())){
				throw new Exception("验证码错误。");
			}
			//执行事务
			pageModel = fUsersService.userLogin(pageModel);
			//设置在线用户
			setSessionAttr(Users.SESSION_USER_NAME, FProjectUtils.hideUserInfo(pageModel));
			//选择了“记住我”
			if(pageModel.getRemeberMe()!=null && pageModel.getRemeberMe().equals("on")){
				setLoginCookie(true);
			}else{
				setLoginCookie(false);
			}
			
			//设置提示信息
			retJSON.setMsg("登录成功，正在转跳。");
			retJSON.setSuccess(true);
			retJSON.setOnline(true);
		} catch (Exception e) {
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 验证邮件激活
	 * @return
	 */
	public String checkActivityCode(){
		/*http://localhost:8080/sharedcloud/pc/front/fUsersAction!checkActivityCode.action?activityCode=replaceActivityCode&userid=replaceUserId*/		
		try {
			pageModel = fUsersService.checkActivityCode(pageModel);
			setSessionAttr(Users.SESSION_USER_NAME,FProjectUtils.hideUserInfo(pageModel));
			setRequestAttr(GStatic.REQUEST_MSG_NAME, "账户激活成功。");
			//设置返回信息
			return "redirectIndex";
		}catch (Exception e) {
			setRequestAttr(GStatic.REQUEST_MSG_NAME, e.getMessage());
			return "info";
		}
	}
	
	/**
	 * 用户注销
	 */
	public void logout(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			//删除cookie
			setLoginCookie(false);
			//去除session
			removeSessionAttr(Users.SESSION_USER_NAME);
			getSession().invalidate();
			retJSON.setMsg("注销成功，正在跳转");
			retJSON.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
		}finally{
			writeJSON(retJSON);
		}
		
	}
	
	/**
	 * 通过cookie登录
	 * @return
	 */
	public String loginByCookie(){
		try {
			
			//通过userid获取user信息
			pageModel = fUsersService.loginByCookie(pageModel);
			setSessionAttr(Users.SESSION_USER_NAME, FProjectUtils.hideUserInfo(pageModel));
			String forwardJsp =getRequestParam("forwardJsp");//获取转发地址
			return forwardJsp;
		} catch (Exception e) {
			e.printStackTrace();
			setRequestAttr(GStatic.REQUEST_MSG_NAME, e.getMessage());
			
			//如果自动登录失败，那么删除cookie
			setLoginCookie(false);
			return "info";
		}
		
	}
	
	
	/**
	 * 设置客户端的登录cookie（包括账户cookie和密码cookie）
	 * @param saveCookies：true：保存cookie，false：删除客户端cookie
	 * @return
	 */
	private Boolean setLoginCookie(Boolean saveCookies){
		try {
			Cookie UserIdCookie = null;
			Cookie passwordCookie = null;
			if (saveCookies) {
				//设置账户
				UserIdCookie = new Cookie(Users.COOKIE_USERID_NAME,
						pageModel.getUserid());
				UserIdCookie.setPath("/");
				//一年
				UserIdCookie.setMaxAge(60 * 60 * 24 * 365);
				//设置密码
				passwordCookie = new Cookie(Users.COOKIE_PASSWORD_NAME,
						pageModel.getPassword());
				passwordCookie.setPath("/");
				//一年
				passwordCookie.setMaxAge(60 * 60 * 24 * 365);
			} else {
				//清除cookie
				//清除账户
				UserIdCookie = new Cookie(Users.COOKIE_USERID_NAME, null);
				UserIdCookie.setMaxAge(0);
				UserIdCookie.setPath("/");

				//清除密码
				passwordCookie = new Cookie(Users.COOKIE_PASSWORD_NAME, null);
				passwordCookie.setMaxAge(0);
				passwordCookie.setPath("/");
			}
			//response设置cookie
			getResponse().addCookie(UserIdCookie);
			getResponse().addCookie(passwordCookie);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	/**
	 * 更新用户的基本信息
	 */
	public void saveUserBasicInfo(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			//处理birthday的值获取异常（struts无法转换前台字符串为timestamp）
			pageModel.setBirthday(new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(getRequestParam("birthday").toString()).getTime()));
			//执行事务
			pageModel = fUsersService.saveUserBasicInfo(pageModel);
			//更新seesion的用户
			setSessionAttr(Users.SESSION_USER_NAME, FProjectUtils.hideUserInfo(pageModel));
			retJSON.setMsg("更新成功。");
			retJSON.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 发送修改密码的邮箱
	 */
	public void sendUpdateUserPwdEamil(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			//执行业务
			pageModel = fUsersService.verifyEmail(pageModel);
			
			//设置返回信息
			retJSON.setMsg("邮箱验证成功，请登录邮箱修改密码。");
			retJSON.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 验证修改密码的url连接的有效性（即邮箱中修改密码的url）
	 * 即验证参数中的userid和verifycode
	 * @return
	 */
	public String verifyUpdateUserPwdUrl(){
		try {
			fUsersService.verifyUpdateUserPwdUrl(pageModel);
			//设置userid的信息到密码修改界面
			setRequestAttr(Users.REQUEST_USER_NAME, pageModel);
		} catch (Exception e) {
			setRequestAttr(GStatic.REQUEST_MSG_NAME, e.getMessage());
			e.printStackTrace();
			return "info";
		}
		return "resetPassword";
	}
	
	/**
	 * 修改密码
	 * 需要参数：userid和新的password
	 * @return
	 */
	public void updateUserPwd(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			pageModel = fUsersService.updateUserPwd(pageModel);
			//去除session的user
			removeSessionAttr(Users.SESSION_USER_NAME);
			//清除cookie
			setLoginCookie(false);
			//设置返回信息
			retJSON.setMsg("修改密码成功，正在跳转。");
			retJSON.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();

			//返回错误信息
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			
		}finally{
			writeJSON(retJSON);
		}
	}
	
	/**
	 * 获取session中user的信息
	 */
	public void getSessionUser(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			FPageUsers onlineUser = getSessionAttr(Users.SESSION_USER_NAME);
			onlineUser = fUsersService.getUserByUserid(onlineUser.getUserid());
			//设置返回信息
			retJSON.setMsg("");
			retJSON.setSuccess(true);
			retJSON.setOnline(true);
			//返回在线用户
			onlineUser = FProjectUtils.hideUserInfo(onlineUser);
			retJSON.setObj(onlineUser);
		} catch (Exception e) {
			e.printStackTrace();

			//返回错误信息
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			retJSON.setOnline(false);
			
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 验证登录时用户输入的邮箱
	 */
	public void checkLoginEmail(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			fUsersService.checkLoginEmail(pageModel);
			//设置返回信息
			retJSON.setMsg("邮箱正确。");
			retJSON.setSuccess(true);
			retJSON.setOnline(true);
		} catch (Exception e) {
			e.printStackTrace();
			//返回错误信息
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			retJSON.setOnline(true);
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 验证注册时用户输入的邮箱
	 */
	public void checkRegistEmail(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			fUsersService.checkRegistEmail(pageModel);
			//设置返回信息
			retJSON.setMsg("邮箱可用。");
			retJSON.setSuccess(true);
			retJSON.setOnline(true);
		} catch (Exception e) {
			e.printStackTrace();
			//返回错误信息
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			retJSON.setOnline(true);
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 验证注册时用户输入的昵称
	 */
	public void checkRegistUserName(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			fUsersService.checkRegistUserName(pageModel);
			//设置返回信息
			retJSON.setMsg("昵称可用。");
			retJSON.setSuccess(true);
			retJSON.setOnline(true);
		} catch (Exception e) {
			e.printStackTrace();
			//返回错误信息
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			retJSON.setOnline(true);
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 验证登录时用户输入的验证码 
	 */
	public void checkLoginVerifyCode(){
		checkVerifyCode(pageModel);
	}
	/**
	 * 验证登录时用户输入的密码
	 */
	public void checkLoginPwd(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			fUsersService.checkLoginPwd(pageModel);
			//设置返回信息
			retJSON.setMsg("密码可用。");
			retJSON.setSuccess(true);
			retJSON.setOnline(true);
		} catch (Exception e) {
			e.printStackTrace();
			//返回错误信息
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			retJSON.setOnline(true);
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 验证注册时用户输入的密码
	 */
	public void checkRegistPwd(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			fUsersService.checkRegistPwd(pageModel);
			//设置返回信息
			retJSON.setMsg("密码可用。");
			retJSON.setSuccess(true);
			retJSON.setOnline(true);
		} catch (Exception e) {
			e.printStackTrace();
			//返回错误信息
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			retJSON.setOnline(true);
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 验证修改时时用户输入的密码
	 */
	public void checkResetPwd(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			fUsersService.checkResetPwd(pageModel);
			//设置返回信息
			retJSON.setMsg("密码可用。");
			retJSON.setSuccess(true);
			retJSON.setOnline(true);
		} catch (Exception e) {
			e.printStackTrace();
			//返回错误信息
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			retJSON.setOnline(true);
		}finally{
			writeJSON(retJSON);
		}
	}
	
	/**
	 * 检测验证码
	 * @param pageModel
	 */
	private void checkVerifyCode(FPageUsers pageModel){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			/*
			 *检测验证码 
			 */
			String verifyCode = getSessionAttr(Users.VERIFY_CODE_NAME).toString();
			if(!verifyCode.toLowerCase().equals(pageModel.getVerifyCode().toLowerCase())){
				throw new Exception("验证码错误，您也可以尝试刷新验证码。");
			}
			//设置返回信息
			retJSON.setMsg("验证码正确。");
			retJSON.setSuccess(true);
			retJSON.setOnline(true);
		} catch (Exception e) {
			e.printStackTrace();
			//返回错误信息
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			retJSON.setOnline(true);
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 验证注册时用户输入的验证码 
	 */
	public void checkRegistVerifyCode(){
		checkVerifyCode(pageModel);
	}
	/**
	 * 通过userid和图片尺寸获取头像
	 */
	public void getUserHeadImg(){
		writeImgByPath(Users.HEADIMGPATH, pageModel.getUserid()+"_"+pageModel.getHeadImgSize()+".png",Users.HEADIMGNAME_DEFAULT);
	}
}
;