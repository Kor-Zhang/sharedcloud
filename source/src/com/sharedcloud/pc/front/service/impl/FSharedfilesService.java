package com.sharedcloud.pc.front.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.sharedcloud.pc.front.dao.FSharedfilesDaoI;
import com.sharedcloud.pc.front.dao.FUploadHistoryDaoI;
import com.sharedcloud.pc.front.dao.FUsersDaoI;
import com.sharedcloud.pc.front.pageModel.FPageSharedfiles;
import com.sharedcloud.pc.front.service.FSharedfilesServiceI;
import com.sharedcloud.pc.front.utils.FLogger;
import com.sharedcloud.pc.front.utils.FProjectUtils;
import com.sharedcloud.pc.front.utils.FUtils;
import com.sharedcloud.pc.model.Files;
import com.sharedcloud.pc.model.Sharedfiles;
import com.sharedcloud.pc.model.UploadHistory;
import com.sharedcloud.pc.model.Users;

public class FSharedfilesService implements FSharedfilesServiceI{
	
	//申明fSharedfilesDao
	private FSharedfilesDaoI fSharedfilesDao;

	public FSharedfilesDaoI getfSharedfilesDao() {
		return fSharedfilesDao;
	}

	public void setfSharedfilesDao(FSharedfilesDaoI fSharedfilesDao) {
		this.fSharedfilesDao = fSharedfilesDao;
	}
	//声明fUploadHistoryDao
	private FUploadHistoryDaoI fUploadHistoryDao;
	

	
	public FUploadHistoryDaoI getfUploadHistoryDao() {
		return fUploadHistoryDao;
	}

	public void setfUploadHistoryDao(FUploadHistoryDaoI fUploadHistoryDao) {
		this.fUploadHistoryDao = fUploadHistoryDao;
	}
	//声明fUsersDao
	private FUsersDaoI fUsersDao;

	public void setfUsersDao(FUsersDaoI fUsersDao) {
		this.fUsersDao = fUsersDao;
	}
	@Override
	public FPageSharedfiles createShareingLink(String userid, FPageSharedfiles pageModel)
			throws Exception {
		/*
		 * 初始化参数
		 */
		String sharedfilesid = UUID.randomUUID().toString();
		//获取项目名称
		String projectPath = FProjectUtils.getWebProjectPath(null);;
		String hql;
		Map<String, Object> parameters;
		String pwdHql;
		//如果创建私密分享
		if(null != pageModel.getIspublic()&&pageModel.getIspublic()==false){
			String pwd = UUID.randomUUID().toString().substring(0,4);
			pageModel.setPassword(pwd);
			//设置pwdHql
			pwdHql = " and sf.password is not null ";
		}else{
			//设置pwdHql
			pwdHql = " and sf.password is null ";
		}
		/*
		 * 查询数据库是否存在此分享
		 */
		hql = "from Sharedfiles sf where sf.status=:sf_status_activity and sf.users.id=:userid and sf.uploadHistory.id=:uploadHistoryId "+pwdHql;
		parameters = FUtils.getMap(new String[]{"sf_status_activity","userid","uploadHistoryId"}, new Object[]{Sharedfiles.STATUS_ACTIVITY,userid,pageModel.getUploadHistoryId()});
		Sharedfiles sf = fSharedfilesDao.getByHql(hql, parameters);
		//如果存在该分享，返回信息
		if(sf != null){
			BeanUtils.copyProperties(sf, pageModel);
			//设置url
			pageModel.setShreadUrl(FProjectUtils.getSharedUrl(projectPath, pageModel.getSharedfileid()));
			return pageModel;
		}
		//数据库不存在分享
		/*
		 * 写入数据库
		 */
		//设置属性
		sf = new Sharedfiles();
		//copy属性
		BeanUtils.copyProperties(pageModel, sf);
		sf.setSharedfileid(sharedfilesid);
		sf.setBrowsenumber(Sharedfiles.BROWSENUMBER_DEFAULT);
		sf.setDownloadnumber(Sharedfiles.DOWNLOADNUMBER_DEFAULT);
		sf.setSavenumber(Sharedfiles.SAVENUMBER_DEFAULT);
		sf.setSharedtime(new Timestamp(new Date().getTime()));
		sf.setStatus(Sharedfiles.STATUS_ACTIVITY);
		//设置user
		Users user = fUsersDao.get(Users.class, userid);
		sf.setUsers(user);
		//设置uploadhistory
		UploadHistory uh = fUploadHistoryDao.get(UploadHistory.class, pageModel.getUploadHistoryId());;
		sf.setUploadHistory(uh);
		//保存sf
		fSharedfilesDao.save(sf);
		/*
		 * 设置pagemodel
		 */
		//设置sharedfilesid
		pageModel.setSharedfileid(sharedfilesid);
		//设置url
		pageModel.setShreadUrl(FProjectUtils.getSharedUrl(projectPath, pageModel.getSharedfileid()));
		
		return pageModel;
	}
	
	@Override
	public FPageSharedfiles getSharedfilesById(FPageSharedfiles pageModel) throws Exception {
		if(null == pageModel.getSharedfileid()||pageModel.getSharedfileid().equals("")){
			throw new Exception("获取分享信息失败。");
		}
		//根据分享记录id获取分享记录
		Sharedfiles sf = fSharedfilesDao.get(Sharedfiles.class, pageModel.getSharedfileid());
		
		if(null == sf){
			throw new Exception("获取分享信息失败。");
		}
		if(Sharedfiles.STATUS_DELETE == sf.getStatus()){
			throw new Exception("分享已取消。");
		}
		//复制属性
		BeanUtils.copyProperties(sf, pageModel);
		//设置userid
		pageModel.setUserid(sf.getUsers().getUserid());
		
		//设置文件信息
		//目录的ext为directory，其他的文件的ext为文件后缀
		UploadHistory uh = sf.getUploadHistory();
		//设置文件后缀
		if(Files.ISDIR_YES == uh.getFiles().getIsdir()){
			pageModel.setExt("directory");
		}else{
			pageModel.setExt(FUtils.getFileNameExt(uh.getFilename()));
		}
		pageModel.setFilename(uh.getFilename());//设置文件名
		pageModel.setFilesize(uh.getFiles().getFilesize());//设置文件大小
		pageModel.setUploadHistoryId(uh.getId());//设置uploadhistoryid
		
		
		//增加浏览量
		FLogger.info(sf.getBrowsenumber());
		sf.setBrowsenumber(sf.getBrowsenumber()+1d);
		FLogger.info(sf.getBrowsenumber());
		fSharedfilesDao.save(sf);
		return pageModel;
	}

	@Override
	public void verifySharedfilesPwd(FPageSharedfiles pageModel)
			throws Exception {
		Sharedfiles sf = fSharedfilesDao.get(Sharedfiles.class, pageModel.getSharedfileid());
		if(null == sf){
			throw new Exception("分享文件不存在。");
		}
		if(!sf.getPassword().equals(pageModel.getPassword())){
			throw new Exception("密码错误。");
		}
	}

	@Override
	public List<FPageSharedfiles> getSharedfiles(String userid,
			FPageSharedfiles pageModel) throws Exception {
		/*
		 *判断排序表 
		 */
		String sortTableName = "sf";
		//判断排序表；如果对file表排序-f；如果对uploadhistory排序-uh
		if(pageModel.getOrder().equals("filesize")){
			sortTableName = "f";
		}else if(pageModel.getOrder().equals("filename")){
			//如果对UploadHistory表的字段排序，如filename等
			sortTableName = "uh";
		}
		/*
		 *查询数据 
		 */
		/*sharedfileid, uploadHistory,users,ispublic,password,sharedtime,downloadnumber,browsenumber,savenumber,status*/
		String hql = "select new Sharedfiles(sf.sharedfileid,sf.uploadHistory,sf.users,sf.ispublic,sf.password,sf.sharedtime,sf.downloadnumber,sf.browsenumber,sf.savenumber,sf.status) from Sharedfiles sf,Users u,UploadHistory uh,Files f where sf.uploadHistory.id=uh.id and sf.users.userid=u.userid and f.fileid=uh.files.id and sf.status=:sf_status_activity and uh.status=:uh_status_activity and sf.users.id=:userid order by "+sortTableName+"."+pageModel.getOrder()+" "+pageModel.getSort();;
		Map<String,Object> parameters = FUtils.getMap(new String[]{"sf_status_activity","uh_status_activity","userid"}, new Object[]{Sharedfiles.STATUS_ACTIVITY,Sharedfiles.STATUS_ACTIVITY,userid});
		List<Sharedfiles> sfs = fSharedfilesDao.queryByHql(hql, parameters,-1,-1);
		/*
		 * 设置返回集合
		 */
		List<FPageSharedfiles> fpsfs = FUtils.copyProList(FPageSharedfiles.class, sfs);
		//设置filename，filesize，ext，sharedurl属性
		String projectPath = FProjectUtils.getWebProjectPath(null);
		for (int i = 0; i < sfs.size(); i++) {
			Sharedfiles sf = sfs.get(i);
			FPageSharedfiles fpsf = fpsfs.get(i);
			fpsf.setFilename(sf.getUploadHistory().getFilename());//设置filename
			fpsf.setFilesize(sf.getUploadHistory().getFiles().getFilesize());//设置filesize
			//设置ext
			String ext = FUtils.getFileNameExt(sf.getUploadHistory().getFilename());
			if(Files.ISDIR_YES == sf.getUploadHistory().getFiles().getIsdir()){//如果为文件夹
				ext = "directory";
			}
			fpsf.setExt(ext);
			
			fpsf.setShreadUrl(FProjectUtils.getSharedUrl(projectPath, sf.getSharedfileid()));//设置url
		}
		return fpsfs;
	}

	@Override
	public void cancelSharedfiles(FPageSharedfiles pageModel)
			throws Exception {
		//查询数据
		Sharedfiles sf = fSharedfilesDao.get(Sharedfiles.class,pageModel.getSharedfileid());
		//删除数据
		if(null != sf){
			sf.setStatus(Sharedfiles.STATUS_DELETE);
			fSharedfilesDao.saveOrUpdate(sf);
		}
	}

}
