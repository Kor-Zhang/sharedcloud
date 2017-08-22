package com.sharedcloud.pc.front.action;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.sharedcloud.pc.front.pageModel.FPageSharedfiles;
import com.sharedcloud.pc.front.pageModel.FPageUsers;
import com.sharedcloud.pc.front.service.FSharedfilesServiceI;
import com.sharedcloud.pc.front.utils.FReturnJSON;
import com.sharedcloud.pc.model.Sharedfiles;
import com.sharedcloud.pc.model.Users;

public class FSharedfilesAction extends FBaseAction implements ModelDriven<FPageSharedfiles>{
	private FPageSharedfiles pageModel = new FPageSharedfiles();

	@Override
	public FPageSharedfiles getModel() {
		return pageModel;
	}
	private FSharedfilesServiceI fSharedfilesService;

	public FSharedfilesServiceI getfSharedfilesService() {
		return fSharedfilesService;
	}
	public void setfSharedfilesService(FSharedfilesServiceI fSharedfilesService) {
		this.fSharedfilesService = fSharedfilesService;
	}
	/**
	 * 创建分享连接 
	 */
	public void createShareingLink(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			//获取用户
			String userid = ((FPageUsers)getSessionAttr(Users.SESSION_USER_NAME)).getUserid();
			
			pageModel = fSharedfilesService.createShareingLink(userid,pageModel);
			 
			//设置返回信息
			retJSON.setMsg("创建成功");
			retJSON.setSuccess(true);
			retJSON.setObj(pageModel);
		} catch (Exception e) {
			
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 通过分享记录的id获取记录
	 * ajax方式
	 * @return 
	 */
	public void getSharedfilesById(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			//获取项目名称
			pageModel = fSharedfilesService.getSharedfilesById(pageModel);
			retJSON.setMsg("获取成功。");
			retJSON.setSuccess(true);
			retJSON.setObj(pageModel);
			
		} catch (Exception e) {
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 验证私密文件的密码
	 * @return 
	 */
	public void verifySharedfilesPwd(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			fSharedfilesService.verifySharedfilesPwd(pageModel);
			retJSON.setMsg("验证成功。");
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
	 * 获取指定用户的所有记录
	 * ajax方式
	 * @return 
	 */
	public void getSharedfiles(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			//获取在线user
			String userid = ((FPageUsers)(getSessionAttr(Users.SESSION_USER_NAME))).getUserid();
			//设置rows
			pageModel.setRows(Sharedfiles.ROWS_DEFAULT);
			//获取指定用户的所有记录
			List<FPageSharedfiles> fpsfs = fSharedfilesService.getSharedfiles(userid,pageModel);
			//设置返回信息
			retJSON.setMsg("获取成功。");
			retJSON.setSuccess(true);
			retJSON.setObj(fpsfs);
			
		} catch (Exception e) {
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 通过指定的id取消分享的sharedfiles
	 * ajax方式
	 */
	public void cancelSharedfiles(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			//执行事务
			fSharedfilesService.cancelSharedfiles(pageModel);
			//设置返回信息
			retJSON.setMsg("取消分享成功");
			retJSON.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			
		}finally{
			writeJSON(retJSON);
		}
	}
	
};