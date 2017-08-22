package com.sharedcloud.pc.front.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.sharedcloud.pc.front.dao.FUsersDaoI;
import com.sharedcloud.pc.front.pageModel.FPageUsers;
import com.sharedcloud.pc.front.service.FUsersServiceI;
import com.sharedcloud.pc.front.stati.FStatic;
import com.sharedcloud.pc.front.utils.FConfig;
import com.sharedcloud.pc.front.utils.FMail;
import com.sharedcloud.pc.front.utils.FProjectUtils;
import com.sharedcloud.pc.front.utils.FUtils;
import com.sharedcloud.pc.model.Users;

public class FUsersService implements FUsersServiceI {
	private FUsersDaoI fUsersDao;

	public void setfUsersDao(FUsersDaoI fUsersDao) {
		this.fUsersDao = fUsersDao;
	}
	/**
	 * 用户登录
	 */
	public FPageUsers userLogin(FPageUsers pageModel) throws Exception {
		//密码加密
		pageModel.setPassword(FUtils.e(pageModel.getPassword()));
		//查询数据
		String hql = "from Users t where t.email=:email and t.password=:password and t.status=:activityStatus or status=:frozenStatus";
		Map<String,Object> parameters = FUtils.getMap(new String[]{"email","password","activityStatus","frozenStatus"}, new Object[]{pageModel.getEmail(),pageModel.getPassword(),Users.STATUS_ACTIVITY,Users.STATUS_FROZEN});
		Users dbUser = fUsersDao.getByHql(hql, parameters);
		if(null == dbUser){
			throw new Exception("账户或者密码错误！");
		}
		if(dbUser.getStatus()==Users.STATUS_FROZEN){

			throw new Exception("账户被冻结！");
		}
		//登陆成功
		BeanUtils.copyProperties(dbUser, pageModel);
		return pageModel;
	}

	public Boolean userLogout(FPageUsers pageModel) throws Exception {
		return null;
	}

	@Override
	public Boolean userRegist(FPageUsers pageModel) throws Exception {
		/*
		 * 前台参数验证
		 */
		// 检测邮箱完整性
		if (null == pageModel.getEmail() || pageModel.getEmail().equals("")) {
			throw new Exception("邮箱不能为空！");
		}
		/*设置邮箱字母小写*/
		pageModel.setEmail(pageModel.getEmail().toLowerCase());
		//检测邮箱格式
		checkEmailFormat(pageModel);
		// 验证密码完整性
		if (null == pageModel.getPassword() || pageModel.getPassword().equals("")) {
			throw new Exception("密码不能为空！");
		}
		// 验证密码格式
		checkPwdFormat(pageModel);
		// 检测昵称完整性
		if (null == pageModel.getUsername() || pageModel.getUsername().equals("")) {
			throw new Exception("昵称不能为空！");
		}
		// 验证passowrd
		if (null == pageModel.getPassword() || pageModel.getPassword().equals("")) {
			throw new Exception("密码不能为空！");
		}
		/*SQL> SELECT TO_DATE(TO_CHAR(systimestamp,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH
				24:MI:SS')-TO_DATE(TO_CHAR(activityCodeDisableTime_01,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:
				MI:SS') FROM users;
*/		
		
		/*Double activityCodeDisableTime = Users.ACTIVITY_CODE_DISABLE_TIME;
		激活码过期且状态为激活，冻结（-2，-1），或则激活码未过期，且邮箱与当前用户相同的用户
		List<Users> dbUsers = fUsersDao.queryByHql(
				"FROM Users T WHERE T.email =:email_01 AND (TO_DATE (TO_CHAR (SYSTIMESTAMP,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS') - TO_DATE (TO_CHAR (T.registtime,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS'))*24*60*60 <= :activityCodeDisableTime_01 OR T.email =:email_02 AND (TO_DATE (TO_CHAR (SYSTIMESTAMP,'YYYY-MM-DD HH24:MI:SS'	),'YYYY-MM-DD HH24:MI:SS') - TO_DATE (TO_CHAR (T.registtime,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS'))*24*60*60 >:activityCodeDisableTime_02 AND (T.status = :status_activity OR T.status =:status_frozen)", FUtils.getMap(new String[]{
						"email_01","activityCodeDisableTime_01","email_02","activityCodeDisableTime_02","status_activity","status_frozen"
				}, new Object[]{pageModel.getEmail(),activityCodeDisableTime,pageModel.getEmail(),activityCodeDisableTime,Users.STATUS_ACTIVITY,Users.STATUS_FROZEN}),1,1);
		
		if(dbUsers!=null&&dbUsers.size()>=1){
			throw new Exception("该邮箱已存在！");
		}*/
		checkEmailStatus(pageModel);
		checkUserNameStatus(pageModel);
		/*激活码过期且状态为激活，冻结（-2，-1），或则激活码未过期，且用户名与当前用户相同的用户
		dbUsers = fUsersDao.queryByHql(
				"FROM Users T WHERE T.username =:username_01 AND (TO_DATE (TO_CHAR (SYSTIMESTAMP,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS') - TO_DATE (TO_CHAR (T.registtime,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS'))*24*60*60 <= :activityCodeDisableTime_01 OR T.username =:username_02 AND (TO_DATE (TO_CHAR (SYSTIMESTAMP,'YYYY-MM-DD HH24:MI:SS'	),'YYYY-MM-DD HH24:MI:SS') - TO_DATE (TO_CHAR (T.registtime,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS'))*24*60*60 >:activityCodeDisableTime_02 AND (T.status = :status_activity OR T.status =:status_frozen)", FUtils.getMap(new String[]{
				"username_01","activityCodeDisableTime_01","username_02","activityCodeDisableTime_02","status_activity","status_frozen"
		}, new Object[]{pageModel.getUsername(),activityCodeDisableTime,pageModel.getUsername(),activityCodeDisableTime,Users.STATUS_ACTIVITY,Users.STATUS_FROZEN}),1,1);
		
		if(dbUsers!=null&&dbUsers.size()>=1){
			throw new Exception("该昵称已存在！");
		}*/
		
		/*
		 * 设置参数，执行写入
		 */

		String activityCode = UUID.randomUUID().toString();
		pageModel.setActivitycode(activityCode);
		pageModel.setPassword(FUtils.e(pageModel.getPassword()));
		pageModel.setAge(Users.AGE_DEFALUT);
		pageModel.setBirthday(Users.BIRTHDAY_DEFALUT);
		pageModel.setSavedfilesize(Users.SAVEDFILESIZE_DEFALUT);
		pageModel.setSex(Users.SEX_DEFAULT);
		pageModel.setStatus(Users.STATUS_UNACTIVITY);
		pageModel.setTotalfilesize(Users.TOTALFILESIZE_DEFALUT);
		pageModel.setActivitytime(new Timestamp(new Date().getTime()));
		pageModel.setRegisttime(new Timestamp(new Date().getTime()));
		pageModel.setUserid(UUID.randomUUID().toString());
		/*copy属性*/
		Users savedUser = new Users();
		BeanUtils.copyProperties(pageModel,savedUser);
		//删除与当前注册用户昵称，邮箱冲突的垃圾账户
		Double activityCodeDisableTime = Users.ACTIVITY_CODE_DISABLE_TIME;
		fUsersDao.executeHql("DELETE FROM Users T WHERE T.email =:email OR T.username =:username AND T.status =:unActivityStatus AND (TO_DATE (TO_CHAR (SYSTIMESTAMP,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS') - TO_DATE (TO_CHAR (T.registtime,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS'))*24*60*60>:activityCodeDisableTime", FUtils.getMap(new String[]{
				"email","username","unActivityStatus","activityCodeDisableTime"
		}, new Object[]{pageModel.getEmail(),pageModel.getUsername(),Users.STATUS_UNACTIVITY,activityCodeDisableTime}));
		//写入当前用户
		fUsersDao.save(savedUser);
		try {
			/*
			 * 发送激活邮件
			 */
			FMail pm = new FMail();
			pm.setPropsPath(FConfig.getPropsPath());
			String content = FConfig.getProps().get("content").toString();
			String subject = FConfig.getProps().get("subject").toString();
			content = content.replaceAll("replaceUserName",
					pageModel.getUsername());
			content = content
					.replaceAll("replaceUserId", pageModel.getUserid());
			content = content.replaceAll("replaceActivityCode", activityCode);
			//换取项目域名
			content = FProjectUtils.getWebProjectPath(content);
			pm.sendMessage(pageModel.getEmail(), subject, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * 检测密码格式
	 * @param pageModel
	 * @throws Exception
	 */
	private void checkPwdFormat(FPageUsers pageModel) throws Exception {
		String reg = Users.USER_PWD_REG;
		if (!FUtils.check(pageModel.getPassword(), reg)) {
			throw new Exception("密码格式错误！");
		}
		
	}
	/**
	 * 检测邮箱格式
	 * @param pageModel
	 * @throws Exception
	 */
	private void checkEmailFormat(FPageUsers pageModel) throws Exception {
		// 检测邮箱格式
		String reg = Users.USER_EMAIL_REG;
		if (!FUtils.check(pageModel.getEmail(), reg)) {
			throw new Exception("邮箱格式不正确。");
		}
		
	}
	/**
	 * 查询所有用户
	 */
	public List<FPageUsers> queryUsers() throws Exception {
		String queryHql = "from Users t";
		List<Users> users = fUsersDao.queryByHql(queryHql, null, 1, 10);
		List<FPageUsers> pagepageModels = FUtils.copyProList(FPageUsers.class,
				users);
		return pagepageModels;
	}

	/**
	 * 账户激活验证
	 */
	public FPageUsers checkActivityCode(FPageUsers pageModel) throws Exception {
		Double activityCodeDisableTime = Users.ACTIVITY_CODE_DISABLE_TIME;
		//查找验证码有效，且未验证的指定id和激活码的账户
		String hql = "FROM Users T WHERE ( TO_DATE ( TO_CHAR ( SYSTIMESTAMP, 'YYYY-MM-DD HH24:MI:SS' ), 'YYYY-MM-DD HH24:MI:SS' ) - TO_DATE ( TO_CHAR ( T.registtime, 'YYYY-MM-DD HH24:MI:SS' ), 'YYYY-MM-DD HH24:MI:SS' ) ) * 24 * 60 * 60 <= :activityCodeDisableTime AND T.userid = :userid AND T.status = :unActivityStatus and T.activitycode=:activitycode";
		Map<String,Object> parameters = FUtils.getMap(new String[]{"activityCodeDisableTime","userid","unActivityStatus","activitycode"},new Object[]{activityCodeDisableTime,pageModel.getUserid(),Users.STATUS_UNACTIVITY,pageModel.getActivitycode()});
		Users dbUser = fUsersDao.getByHql(hql, parameters);
		//不存在这样的用户
		if(dbUser == null){
			throw new Exception("激活失败！");
		}
		//存在这样的用户，激活其状态
		dbUser.setStatus(Users.STATUS_ACTIVITY);
		fUsersDao.save(dbUser);
		
		//复制属性到pageModel，用于session储存
		BeanUtils.copyProperties(dbUser, pageModel);
		return pageModel;
	}
	/**
	 * 通过cookie中的密码，userid信息登录
	 */
	public FPageUsers loginByCookie(FPageUsers pageModel) throws Exception {
		//不需要对密码加密，应为cookie的密码已经为md5加密
		//根据密码，userid查询激活状态的user的信息
		String hql = "from Users t where t.userid=:userid and t.status=:activityStatus and t.password = :password";
		Map<String,Object> parameters = FUtils.getMap(new String[]{"userid","activityStatus","password"}, new Object[]{pageModel.getUserid(),Users.STATUS_ACTIVITY,pageModel.getPassword()});
		Users dbUser = fUsersDao.getByHql(hql, parameters);
		if(null == dbUser){
			throw new Exception("自动登录失败！");
		}
		FPageUsers fPageUser = new FPageUsers();
		BeanUtils.copyProperties(dbUser, fPageUser);
		return fPageUser;
	}
	/**
	 * 更新用户的基本信息
	 */
	public FPageUsers saveUserBasicInfo(FPageUsers pageModel) throws Exception {
		
		if(pageModel.getIntro().getBytes("UTF-8").length>255){
			throw new Exception("签名过长，最大255字节。");
		}
		Users newUser = fUsersDao.get(Users.class, pageModel.getUserid());
		//更新指定列
		newUser.setBirthday(pageModel.getBirthday());
		newUser.setAge(pageModel.getAge());
		newUser.setSex(pageModel.getSex());
		newUser.setIntro(pageModel.getIntro());
		//执行更新
		fUsersDao.saveOrUpdate(newUser);
		//设置返回的pagemodel
		BeanUtils.copyProperties(newUser, pageModel);
		return pageModel;
	}
	/**
	 * 验证指定userid（携带填写的emial参数）的用户的emial
	 */
	public FPageUsers verifyEmail(FPageUsers pageModel) throws Exception {
		if(pageModel.getEmail()==null){
			throw new Exception("输入的邮箱不能为空。");
		}else{
			pageModel.setEmail(pageModel.getEmail().toLowerCase());
		}
		Users dbUser = fUsersDao.get(Users.class, pageModel.getUserid());
		if(null == dbUser||!dbUser.getEmail().equals(pageModel.getEmail())){
			throw new Exception("输入的邮箱错误。");
		}
		/*
		 * 设置参数，执行写入数据库操作
		 */
		String updatePwdVerifyCode = UUID.randomUUID().toString();//申明验证码

		
		dbUser.setUpdatepwdverifycode(updatePwdVerifyCode);//保存验证码
		dbUser.setUpdatepwdreqsubmittime(new Timestamp(new Date().getTime()));//设置修改请求提交时间
		fUsersDao.saveOrUpdate(dbUser);
		/*
		 * 发送修改密码连接邮件
		 */
		
		BeanUtils.copyProperties(dbUser, pageModel);//复制属性
		//发送修改密码的邮件
		try {
			FMail pm = new FMail();
			pm.setPropsPath(FConfig.getPropsPath());
			String content = FConfig.getProps().get("updateUserPwdContent").toString();
			String subject = FConfig.getProps().get("updateUserPwdSubject").toString();
			content = content.replaceAll("replaceUserName",pageModel.getUsername());
			content = content.replaceAll("replaceUserId", pageModel.getUserid());
			content = content.replaceAll("replaceUpdatePwdVerifyCode",updatePwdVerifyCode);
			//换取项目域名
			content = FProjectUtils.getWebProjectPath(content);
			pm.sendMessage(pageModel.getEmail(), subject, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pageModel;
	}
	/**
	 * 修改密码
	 */
	public FPageUsers updateUserPwd(FPageUsers pageModel) throws Exception {
		// 验证密码完整性
		if (null == pageModel.getPassword() || pageModel.getPassword().equals("")) {
			throw new Exception("密码不能为空！");
		}
		// 验证密码格式
		String reg = "^[0-9a-zA-Z_.]{8,15}$";
		if (!FUtils.check(pageModel.getPassword(), reg)) {
			throw new Exception("密码格式错误！");
		}
		Users dbUser = fUsersDao.get(Users.class, pageModel.getUserid());
		dbUser.setPassword(FUtils.e(pageModel.getPassword()));
		fUsersDao.saveOrUpdate(dbUser);
		BeanUtils.copyProperties(dbUser, pageModel);
		return pageModel;
	}
	/**
	 * 验证修改密码的url连接的有效性
	 * 即验证参数中的userid和verifycode
	 * @return
	 */
	public Boolean verifyUpdateUserPwdUrl(FPageUsers pageModel) throws Exception {
		Users dbUser = fUsersDao.get(Users.class, pageModel.getUserid());
		//用户为空
		if(null == dbUser){
			throw new Exception("验证错误。");
		}
		//验证码不同
		if(!dbUser.getUpdatepwdverifycode().equals(pageModel.getUpdatepwdverifycode())){
			throw new Exception("验证码错误。");
		}
		//检测是否验证过期
		Long updatePwdDisableTime = Long.parseLong(Users.UPDATE_PWD_DISABLE_TIME.toString());
		if((new Timestamp(new Date().getTime()).getTime()-dbUser.getUpdatepwdreqsubmittime().getTime())/1000>updatePwdDisableTime){
			throw new Exception("验证过期。");
		}
		return true;
	}
	@Override
	public FPageUsers getUserByUserid(String userid) throws Exception {
		Users dbUser = fUsersDao.get(Users.class, userid);
		FPageUsers fps = null;
		if(dbUser!=null){
			fps = new FPageUsers();
			BeanUtils.copyProperties(dbUser,fps);
		}
		
		return fps;
	}
	@Override
	public void checkLoginEmail(FPageUsers pageModel) throws Exception {
		
		/*
		 * 检测邮箱格式
		 */
		String reg = Users.USER_EMAIL_REG.toString();
		if (!FUtils.check(pageModel.getEmail(), reg)) {
			throw new Exception("邮箱格式不正确。");
		}
		/*
		 * 是否存在该邮箱
		 */
		String hql = "from Users t where t.status=:activityStatus and t.email=:email";
		Map<String,Object> parameters = FUtils.getMap(new String[]{"activityStatus","email"}, new Object[]{Users.STATUS_ACTIVITY,pageModel.getEmail().toLowerCase()});
		Users dbUser = fUsersDao.getByHql(hql, parameters);
		if (null == dbUser) {
			throw new Exception("邮箱不存在。");
		}
	}
	@Override
	public void checkRegistEmail(FPageUsers pageModel) throws Exception {
		checkEmailFormat(pageModel);
		checkEmailStatus(pageModel);
		
	}
	/**
	 * 检测邮箱是否存在
	 * @param pageModel
	 * @throws Exception 
	 */
	private void checkEmailStatus(FPageUsers pageModel) throws Exception{
		Double activityCodeDisableTime = Users.ACTIVITY_CODE_DISABLE_TIME;
		/*激活码过期且状态为激活，冻结（-2，-1），或则激活码未过期，且邮箱与当前用户相同的用户*/
		List<Users> dbUsers = fUsersDao.queryByHql(
				"FROM Users T WHERE T.email =:email_01 AND (TO_DATE (TO_CHAR (SYSTIMESTAMP,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS') - TO_DATE (TO_CHAR (T.registtime,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS'))*24*60*60 <= :activityCodeDisableTime_01 OR T.email =:email_02 AND (TO_DATE (TO_CHAR (SYSTIMESTAMP,'YYYY-MM-DD HH24:MI:SS'	),'YYYY-MM-DD HH24:MI:SS') - TO_DATE (TO_CHAR (T.registtime,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS'))*24*60*60 >:activityCodeDisableTime_02 AND (T.status = :status_activity OR T.status =:status_frozen)", FUtils.getMap(new String[]{
						"email_01","activityCodeDisableTime_01","email_02","activityCodeDisableTime_02","status_activity","status_frozen"
				}, new Object[]{pageModel.getEmail(),activityCodeDisableTime,pageModel.getEmail(),activityCodeDisableTime,Users.STATUS_ACTIVITY,Users.STATUS_FROZEN}),1,1);
		
		if(dbUsers!=null&&dbUsers.size()>=1){
			throw new Exception("该邮箱已存在。");
		}
	}
	/**
	 * 检测昵称是否存在
	 * @param pageModel
	 * @throws Exception 
	 */
	private void checkUserNameStatus(FPageUsers pageModel) throws Exception{
		Double activityCodeDisableTime = Users.ACTIVITY_CODE_DISABLE_TIME;
		/*激活码过期且状态为激活，冻结（-2，-1），或则激活码未过期，且用户名与当前用户相同的用户*/
		List<Users> dbUsers = fUsersDao.queryByHql(
				"FROM Users T WHERE T.username =:username_01 AND (TO_DATE (TO_CHAR (SYSTIMESTAMP,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS') - TO_DATE (TO_CHAR (T.registtime,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS'))*24*60*60 <= :activityCodeDisableTime_01 OR T.username =:username_02 AND (TO_DATE (TO_CHAR (SYSTIMESTAMP,'YYYY-MM-DD HH24:MI:SS'	),'YYYY-MM-DD HH24:MI:SS') - TO_DATE (TO_CHAR (T.registtime,'YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS'))*24*60*60 >:activityCodeDisableTime_02 AND (T.status = :status_activity OR T.status =:status_frozen)", FUtils.getMap(new String[]{
				"username_01","activityCodeDisableTime_01","username_02","activityCodeDisableTime_02","status_activity","status_frozen"
		}, new Object[]{pageModel.getUsername(),activityCodeDisableTime,pageModel.getUsername(),activityCodeDisableTime,Users.STATUS_ACTIVITY,Users.STATUS_FROZEN}),1,1);
		
		if(dbUsers!=null&&dbUsers.size()>=1){
			throw new Exception("该昵称已存在。");
		}
	}
	
	@Override
	public void checkRegistUserName(FPageUsers pageModel) throws Exception {
		checkUserNameStatus(pageModel);
	}
	@Override
	public void checkRegistPwd(FPageUsers pageModel) throws Exception {
		checkPwdFormat(pageModel);
	}
	@Override
	public void checkLoginPwd(FPageUsers pageModel) throws Exception {
		checkPwdFormat(pageModel);
	}
	@Override
	public void checkResetPwd(FPageUsers pageModel) throws Exception {
		checkPwdFormat(pageModel);
		
	}
	
	
}
