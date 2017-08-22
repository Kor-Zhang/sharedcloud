package com.sharedcloud.pc.front.service;
import java.util.List;
import com.sharedcloud.pc.front.pageModel.FPageUsers;
import com.sharedcloud.pc.model.Users;
/**
 * 用户相关的事务
 * @author Kor_Zhang
 *
 */
public interface FUsersServiceI {
	/**
	 * 用户登录
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	public FPageUsers userLogin(FPageUsers pageModel) throws Exception;
	/**
	 * 用户注销
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	public Boolean userLogout(FPageUsers pageModel) throws Exception;
	/**
	 * 用户注册；<br/>
	 * 注册成功发送邮件验证；<br/>
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	public Boolean userRegist(FPageUsers pageModel) throws Exception;
	/**
	 * 查询用户
	 * @return
	 * @throws Exception
	 */
	public List<FPageUsers> queryUsers() throws Exception;
	/**
	 * 检测用户输入的验证码正确性
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	public FPageUsers checkActivityCode(FPageUsers pageModel) throws Exception;
	/**
	 * 用户使用cookie登录
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	public FPageUsers loginByCookie(FPageUsers pageModel) throws Exception;
	/**
	 * 保存用户更新的基本信息
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	public FPageUsers saveUserBasicInfo(FPageUsers pageModel) throws Exception;
	/**
	 * 验证修改密码前用户填写的邮箱的正确性
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	public FPageUsers verifyEmail(FPageUsers pageModel) throws Exception;
	/**
	 * 更新用户密码
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	public FPageUsers updateUserPwd(FPageUsers pageModel) throws Exception;
	/**
	 * 验证更新用户密码的url的有效性
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	public Boolean verifyUpdateUserPwdUrl(FPageUsers pageModel) throws Exception;
	/**
	 * 通过用户的id获取一个用户全部信息，不考虑其状态及其他因素
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public FPageUsers getUserByUserid(String userid) throws Exception;
	/**
	 * 验证登录时用户输入的邮箱
	 * @param pageModel
	 * @throws Exception
	 */
	public void checkLoginEmail(FPageUsers pageModel) throws Exception;

	/**
	 * 验证注册时用户输入的邮箱
	 * @param pageModel
	 * @throws Exception
	 */
	public void checkRegistEmail(FPageUsers pageModel)throws Exception;
	/**
	 * 验证注册时用户输入的昵称
	 * @param pageModel
	 * @throws Exception
	 */
	public void checkRegistUserName(FPageUsers pageModel)throws Exception;
	/**
	 * 验证注册时用户输入的密码
	 * @param pageModel
	 * @throws Exception
	 */
	public void checkRegistPwd(FPageUsers pageModel)throws Exception;
	/**
	 * 验证登录时用户输入的密码
	 * @param pageModel
	 * @throws Exception
	 */
	public void checkLoginPwd(FPageUsers pageModel)throws Exception;
	/**
	 * 验证修改时用户输入的密码
	 * @param pageModel
	 * @throws Exception
	 */
	public void checkResetPwd(FPageUsers pageModel)throws Exception;
}
