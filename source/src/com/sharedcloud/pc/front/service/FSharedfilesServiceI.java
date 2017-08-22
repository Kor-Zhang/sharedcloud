package com.sharedcloud.pc.front.service;

import java.util.List;

import com.sharedcloud.pc.front.pageModel.FPageSharedfiles;


public interface FSharedfilesServiceI {
	/**
	 * 创建分享连接
	 * @param userid
	 * @param pageModel
	 * @throws Exception
	 */
	FPageSharedfiles createShareingLink(String userid,FPageSharedfiles pageModel)throws Exception;
	/**
	 * 通过分享记录的id获取记录
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	FPageSharedfiles getSharedfilesById(FPageSharedfiles pageModel)throws Exception;
	/**
	 * 验证分享文件密码
	 * @param pageModel
	 * @throws Exception
	 */
	void verifySharedfilesPwd(FPageSharedfiles pageModel)throws Exception;
	/**
	 * 获取指定用户的所有记录
	 * @param userid
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	List<FPageSharedfiles> getSharedfiles(String userid,
			FPageSharedfiles pageModel)throws Exception;
	/**
	 * 通过指定的id取消分享的sharedfiles
	 * @param pageModel
	 * @throws Exception
	 */
	void cancelSharedfiles(FPageSharedfiles pageModel) throws Exception;
		
}
