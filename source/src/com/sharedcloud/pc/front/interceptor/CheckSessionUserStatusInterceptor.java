package com.sharedcloud.pc.front.interceptor;

import javax.servlet.http.Cookie;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.sharedcloud.pc.front.pageModel.FPageUsers;
import com.sharedcloud.pc.front.service.FUsersServiceI;
import com.sharedcloud.pc.front.utils.FActionUtils;
import com.sharedcloud.pc.front.utils.FProjectUtils;
import com.sharedcloud.pc.front.utils.FReturnJSON;
import com.sharedcloud.pc.model.Users;

/**
 * 检测用户的在线状态 
 * 离线：尝试cookie登录 
 * 在线：刷新session用户信息
 * @author Kor_Zhang
 * 
 */
public class CheckSessionUserStatusInterceptor extends MethodFilterInterceptor {

	private FUsersServiceI fUsersService;
	
	
	public FUsersServiceI getfUsersService() {
		return fUsersService;
	}
	public void setfUsersService(FUsersServiceI fUsersService) {
		this.fUsersService = fUsersService;
	}
	
	/**
	 * 检测user状态，刷新session的user信息
	 * 离线：尝试cookie登录
	 * 在线：刷新session用户信息
	 * @param ai
	 * @return
	 * @throws Exception:用户的状态不正常将抛出异常，抛出任何异常都将被视为用户离线
	 */
	public FPageUsers refreshSessionUser(ActionInvocation ai) throws Exception{
		//获取session的user
		FPageUsers fpu = (FPageUsers)(ai.getInvocationContext().getSession().get(Users.SESSION_USER_NAME));
		try {
			//如果有用户在线，那么刷新信息
			if (null != fpu) {
				fpu = fUsersService.getUserByUserid(fpu.getUserid());
				//没有该记录
				if (null == fpu) {
					throw new Exception("账户不存在。");
				}
				//判定账户状态
				if(fpu.getStatus()==Users.STATUS_FROZEN){
					throw new Exception("账户被冻结。");
				}
				if(fpu.getStatus()==Users.STATUS_UNACTIVITY){
					throw new Exception("账户不存在。");
				}
				//如果状态正常
				ai.getInvocationContext().getSession().put(Users.SESSION_USER_NAME, FProjectUtils.hideUserInfo(fpu));
			}else{
				//如果没有用户在线，尝试cookie登录
				// 检测客户端是否存在userid和password的信息
				String userid = null;
				String password = null;
				Cookie[] cookies = FActionUtils.getRequest().getCookies();
				for (Cookie c : cookies) {
					if (c.getName().equals(Users.COOKIE_USERID_NAME)) {
						userid = c.getValue();
					}
					if (c.getName().equals(Users.COOKIE_PASSWORD_NAME)) {
						password = c.getValue();
					}
				}
				//cookie没有登录信息
				if (null == userid || null == password) {
					throw new Exception("您已离线，并且自动登录失败，正在为您跳转页面。");
				}
				// 验证客户端的cookie信息
				fpu = fUsersService.getUserByUserid(userid);
				if (fpu.getPassword().equals(password)) {
					System.out.println(fpu.getEmail()+"#在拦截器（CheckSessionUserStatusInterceptor）通过cookie登陆了！");
					ai.getInvocationContext().getSession().put(Users.SESSION_USER_NAME, FProjectUtils.hideUserInfo(fpu));
				}else{
					throw new Exception("您已离线，自动登录失败，正在为您跳转页面。");
				}
			}
		} catch (Exception e) {
			//移除session的user信息，使其离线
			ai.getInvocationContext().getSession().remove(Users.SESSION_USER_NAME);
			throw e;
		}
		return fpu;
	}
	
	
	
	@Override
	protected String doIntercept(ActionInvocation ai) throws Exception {
		FReturnJSON retJSON = new FReturnJSON();
		System.out.println("CheckSessionUserStatusInterceptor");
		try {
			
			//检测用户状态
			//离线：尝试cookie登录
			//在线：刷新session用户信息
			refreshSessionUser(ai);
			
		} catch (Exception e) {
			// 返回用户不在线信息
			retJSON.setMsg(e.getMessage());
			retJSON.setOnline(false);
			retJSON.setSuccess(true);
			e.printStackTrace();
			FActionUtils.writeJSON(retJSON);
		}
		return ai.invoke();
	}

}
