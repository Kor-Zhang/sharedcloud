package com.sharedcloud.pc.front.action;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.sharedcloud.pc.front.pageModel.FPageUploadHistory;
import com.sharedcloud.pc.front.pageModel.FPageUsers;
import com.sharedcloud.pc.front.service.FUploadHistoryServiceI;
import com.sharedcloud.pc.front.utils.FReturnJSON;
import com.sharedcloud.pc.front.utils.FUtils;
import com.sharedcloud.pc.model.Files;
import com.sharedcloud.pc.model.UploadHistory;
import com.sharedcloud.pc.model.Users;
import com.sharedcloud.pc.utils.GReturnJSON;

public class FUploadHistoryAction extends FBaseAction implements ModelDriven<FPageUploadHistory>{

	public FPageUploadHistory pageModel = new FPageUploadHistory();
	public FPageUploadHistory getModel() {
		return pageModel;
	}
	
	private FUploadHistoryServiceI fUploadHistoryService;
	
	public void setfUploadHistoryService(
			FUploadHistoryServiceI fUploadHistoryService) {
		this.fUploadHistoryService = fUploadHistoryService;
	}
	
	
	/**
	 * 获取服务器现在的时间
	 */
	public void getServerNowTime(){
		GReturnJSON retJSON = new GReturnJSON();
		try {
			Timestamp now = new Timestamp(new Date().getTime());
			retJSON.setObj(now);
			//设置返回信息
			retJSON.setMsg("获取服务器时间成功。");
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
	 * （在线用户）获取文件列表；或者进行分类查询；或则关键字查询<br/>
	 * @param pId：当前文件夹；查询结果为当前文件夹的子文件和文件夹；<br/>
	 * @param sort:排序方式：desc或则asc
	 * @param order：排序的字段
	 * @param exts：如果为"null"，那么不进行分类查询；
	 * @param searchKeyWord：关键字查询，如果包含该字段，那么将不会使用pId或exts字段；
	 * @param page：分页
	 */
	public void getFiles(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			//获取查询记录的所属用户
			String userid = pageModel.getUserid();
			if(null == userid || userid.equals("")){//如果未指定userid，默认为是当前session中的userid
				userid = ((FPageUsers)getSessionAttr(Users.SESSION_USER_NAME)).getUserid();
			}
			System.out.println(pageModel.getPage());
			pageModel.setPage(pageModel.getPage()==null?1:pageModel.getPage());//设置page
			pageModel.setRows(UploadHistory.ROWS_DEFAULT);//设置分页大小
			List<FPageUploadHistory> files = fUploadHistoryService.getFiles(userid,pageModel);
			
			//设置返回信息
			//刷新session中的user，因为事务中更新了user的信息
			retJSON.setMsg("获取文件列表成功");
			retJSON.setSuccess(true);
			retJSON.setObj(files);
		} catch (Exception e) {
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
		}finally{
			writeJSON(retJSON);
		}
	}
	/**
	 * 通过文件夹的id（uploadhistory的id）获取其子文件夹；<br/>
	 * 将不会包含本uploadhistory#id==pagemodel#id的文件夹
	 */
	public void getDirectorysByPId(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			String userid = ((FPageUsers)getSessionAttr(Users.SESSION_USER_NAME)).getUserid();
			List<FPageUploadHistory> directorys = fUploadHistoryService.getDirectorysByPId(userid,pageModel);
			
			//设置返回信息
			//刷新session中的user，因为事务中更新了user的信息
			retJSON.setMsg("获取文件夹列表成功");
			retJSON.setSuccess(true);
			retJSON.setObj(directorys);
		} catch (Exception e) {
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
		}finally{
			writeJSON(retJSON);
		}
	}
	
	
	/**
	 * （在线用户）通过（文件上传记录的id）修改文件的名称
	 */
	public void updateFileNameById(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			String userid = ((FPageUsers)getSessionAttr(Users.SESSION_USER_NAME)).getUserid();
			fUploadHistoryService.updateFileNameById(userid,pageModel);
			//设置返回信息
			retJSON.setMsg("修改文件成功");
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
	 * （在线用户）通过（文件上传记录的id）删除文件的名称
	 */
	public void deleteFileById(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			String userid = ((FPageUsers)getSessionAttr(Users.SESSION_USER_NAME)).getUserid();
			fUploadHistoryService.deleteFileById(userid,pageModel);
			//设置返回信息
			retJSON.setMsg("删除文件成功");
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
	 * （在线用户）通过（文件上传记录的id）新建文件的名称
	 */
	public void newFileById(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			String userid = ((FPageUsers)getSessionAttr(Users.SESSION_USER_NAME)).getUserid();
			fUploadHistoryService.newFileById(userid,pageModel);
			//设置返回信息
			retJSON.setMsg("新建文件成功");
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
	 * 上传文件
	 */
	public void uploadFiles(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			//获取用户，该代码无法获取session参数
			/*String userid = ((FPageUsers)getSessionAttr(Users.SESSION_USER_NAME)).getUserid();*/
			String userid = getRequestAttr("userid");
			/*System.out.println("userid"+userid);*/
			fUploadHistoryService.uploadFiles(userid,pageModel);
			
			//设置返回信息
			retJSON.setMsg("上传文件成功");
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
	 * 下载文件（只限于下载单个文件）
	 */
	public void downloadFile(){
		FReturnJSON retJSON = new FReturnJSON();
		//获取用户
		String userid = ((FPageUsers)getSessionAttr(Users.SESSION_USER_NAME)).getUserid();
		FileInputStream fis = null;
		ServletOutputStream os = null;
		try {

			//获取资源储存路径
			String srcPath = Files.FILEPATH;
			//执行事务，返回下载的文件的“物理”路径名称
			pageModel = fUploadHistoryService.downloadFile(userid,pageModel);
			//获取文件夹路径
			String filePath = srcPath+"/"+pageModel.getFilepath();
			
			File dwonloadFile = new File(filePath);

			fis = new FileInputStream(dwonloadFile);
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment;filename="
			+new String(pageModel.getFilename().getBytes("utf-8"),"ISO-8859-1"));
            //获取响应报文输出流对象  
            os = response.getOutputStream();  
            //输出  
            os.write(IOUtils.toByteArray(fis));  
			
			/*//设置返回信息
			retJSON.setMsg("下载文件成功");
			retJSON.setSuccess(true);*/
		} catch (Exception e) {
			//此处可以抛出异常：Connection reset by peer: socket write error；但是并不影响使用；
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
			//只在出错情况写入json
			writeJSON(retJSON);
		}finally{
			
			FUtils.closeStream(fis, os);
		}
	}
	
	/**
	 * 文件或则文件夹的移动
	 */
	public void moveDirectoryOrFile(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			
			//获取用户
			String userid = ((FPageUsers)getSessionAttr(Users.SESSION_USER_NAME)).getUserid();
			fUploadHistoryService.moveDirectoryOrFile(userid,pageModel);
			
			//设置返回信息
			retJSON.setMsg("移动成功");
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
	 * 保存其他用户的文件到自身目录
	 */
	public void saveOtherUserFileToSelf(){
		FReturnJSON retJSON = new FReturnJSON();
		try {
			//获取用户
			String userid = ((FPageUsers)getSessionAttr(Users.SESSION_USER_NAME)).getUserid();
			fUploadHistoryService.saveOtherUserFileToSelf(userid,pageModel);
			
			//设置返回信息
			retJSON.setMsg("保存成功");
			retJSON.setSuccess(true);
		} catch (Exception e) {
			
			e.printStackTrace();
			retJSON.setMsg(e.getMessage());
			retJSON.setSuccess(false);
		}finally{
			writeJSON(retJSON);
		}
	}
}
;