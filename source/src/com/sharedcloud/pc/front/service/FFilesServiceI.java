package com.sharedcloud.pc.front.service;

import java.util.List;

import com.sharedcloud.pc.front.pageModel.FPageFiles;
import com.sharedcloud.pc.front.pageModel.FPageUsers;

public interface FFilesServiceI {
	/**
	 * （在线用户）通过（文件父节点的id）获取文件列表
	 * @param userid
	 * @param pId
	 * @return
	 * @throws Exception
	 */
	List<FPageFiles> getFilesByPId(String userid, String pId) throws Exception;
	/**
	 * （在线用户）通过（文件的id）修改文件的名称
	 * @param userid
	 * @param pageModel
	 * @throws Exception
	 */
	void updateFileNameById(String userid, FPageFiles pageModel) throws Exception;
	/**
	 * （在线用户）通过（文件的id）删除文件的名称
	 * @param userid
	 * @param pageModel
	 * @throws Exception
	 */
	void deleteFileById(String userid, FPageFiles pageModel) throws Exception;
	/**
	 * （在线用户）通过（文件的id）新建文件的名称
	 * @param userid
	 * @param pageModel
	 * @throws Exception
	 */
	void newFileById(String userid, FPageFiles pageModel)throws Exception;
	
}
