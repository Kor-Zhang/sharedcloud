package com.sharedcloud.pc.front.utils;

import com.sharedcloud.pc.front.pageModel.FPageUsers;
import com.sharedcloud.pc.utils.GProjectUtils;

/**
 * 前台关于本项目的工具包
 * @author XiaoYu
 *
 */
public class FProjectUtils extends GProjectUtils{
	/**
	 * 生成共享的url
	 * @param projectPath
	 * @param sharedfilesid
	 * @return
	 */
	public static String getSharedUrl(String projectPath,String sharedfilesid){
		//生成url
		String url = projectPath+"/pc/front/jsp/sharedFiles.jsp?sharedfileid="+sharedfilesid;
		return url;
	}
	/**
	 * 隐藏用户的部分信息
	 * @param user
	 * @return
	 */
	public static FPageUsers hideUserInfo(FPageUsers user){
		
		/*
		 * 隐藏邮箱
		 */
		String emial = user.getEmail();
		Integer emailLength = emial.length();
		Integer pwdHideStartPos = emailLength/3;
		Integer pwdHideEndPos = emailLength/3*2;
		emial = emial.substring(0,pwdHideStartPos)+"*****"+emial.substring(pwdHideEndPos,emailLength);
		user.setEmail(emial);
		return user;
	}
}
