package com.sharedcloud.pc.front.service;

import java.util.List;

import com.sharedcloud.pc.front.pageModel.FPageUploadHistory;

public interface FUploadHistoryServiceI {
	/**
	 *（（在线用户）获取文件列表；或者进行分类查询；或则关键字查询<br/>
	 * @param pId：当前文件夹；查询结果为当前文件夹的子文件和文件夹；<br/>
	 * @param sort:排序方式：desc或则asc
	 * @param order：排序的字段
	 * @param exts：如果为"null"，那么不进行分类查询；
	 * @param searchKeyWord：关键字查询，如果包含该字段，那么将不会使用pId或exts字段；
	 * @return
	 * @throws Exception
	 */
	List<FPageUploadHistory> getFiles(String userid, FPageUploadHistory pageModel) throws Exception;
	/**
	 * （在线用户）通过（文件上传记录的id）修改文件的名称
	 * @param userid
	 * @param pageModel
	 * @throws Exception
	 */
	void updateFileNameById(String userid, FPageUploadHistory pageModel) throws Exception;
	/**
	 * （在线用户）通过（文件上传记录的id）删除文件的名称
	 * @param userid
	 * @param pageModel
	 * @throws Exception
	 */
	void deleteFileById(String userid, FPageUploadHistory pageModel) throws Exception;
	/**
	 * （在线用户）通过（文件上传记录的id）新建文件的名称
	 * @param userid
	 * @param pageModel
	 * @throws Exception
	 */
	void newFileById(String userid, FPageUploadHistory pageModel)throws Exception;
	/**
	 * 上传文件
	 * @param userid
	 * @param pageModel
	 * @throws Exception
	 */
	void uploadFiles(String userid, FPageUploadHistory pageModel)throws Exception;
	/**
	 * 下载文件（只限于下载单个文件）
	 * @param userid
	 * @param pageModel
	 * @return 
	 * @throws Exception
	 */
	FPageUploadHistory downloadFile(String userid, FPageUploadHistory pageModel)throws Exception;
	/**
	 * 通过文件夹的id（uploadhistory的id）获取其子文件夹；<br/>
	 * 将不会包含本uploadhistory#id==pagemodel#id的文件夹；
	 * @param userid
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	List<FPageUploadHistory> getDirectorysByPId(String userid,
			FPageUploadHistory pageModel)throws Exception;
	/**
	 * 文件或则文件夹的移动
	 * @param userid
	 * @param pageModel
	 * @throws Exception
	 */
	void moveDirectoryOrFile(String userid, FPageUploadHistory pageModel)throws Exception;
	/**
	 * 保存其他用户的文件到自身目录
	 * @param userid
	 * @param pageModel
	 * @throws Exception
	 */
	void saveOtherUserFileToSelf(String userid, FPageUploadHistory pageModel)throws Exception;
	
}
