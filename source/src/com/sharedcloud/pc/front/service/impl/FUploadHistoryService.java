package com.sharedcloud.pc.front.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.sharedcloud.pc.front.dao.FFilesDaoI;
import com.sharedcloud.pc.front.dao.FUploadHistoryDaoI;
import com.sharedcloud.pc.front.dao.FUsersDaoI;
import com.sharedcloud.pc.front.pageModel.FPageUploadHistory;
import com.sharedcloud.pc.front.service.FUploadHistoryServiceI;
import com.sharedcloud.pc.front.utils.FLogger;
import com.sharedcloud.pc.front.utils.FUtils;
import com.sharedcloud.pc.model.Files;
import com.sharedcloud.pc.model.UploadHistory;
import com.sharedcloud.pc.model.Users;

public class FUploadHistoryService implements FUploadHistoryServiceI{
	//声明fUsersDao
	private FUsersDaoI fUsersDao;

	public void setfUsersDao(FUsersDaoI fUsersDao) {
		this.fUsersDao = fUsersDao;
	}
	//声明fFilesDao
	private FFilesDaoI fFilesDao;
	public void setfFilesDao(FFilesDaoI fFilesDao) {
		this.fFilesDao = fFilesDao;
	}
	//声明fFilesService
	private FUploadHistoryDaoI fUploadHistoryDao;
	public void setfUploadHistoryDao(FUploadHistoryDaoI fUploadHistoryDao) {
		this.fUploadHistoryDao = fUploadHistoryDao;
	}


	@Override
	public List<FPageUploadHistory> getFiles(String userid, FPageUploadHistory pageModel)
			throws Exception {
		List<FPageUploadHistory> fpuhs = null;
		//searchKeyWord：关键字查询，如果包含该字段，那么将不会使用pId或exts字段；
		
		//如果exts=="null"，代表进行目录和文件查找
		if(null == pageModel.getExts()||pageModel.getExts().equals("null")){
			fpuhs = getDirectoryAndFiles(userid, pageModel);
		}else{
			fpuhs = getExtFiles(userid, pageModel);
		}
		return fpuhs;
	}
	


	/**
	 * 通过exts的值，获取该类型的文件（不包括文件夹，将在hql中限制不为文件夹）
	 * @param userid
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	private List<FPageUploadHistory> getExtFiles(String userid,
			FPageUploadHistory pageModel) throws Exception {
		List<FPageUploadHistory> fpuhs;
		List<UploadHistory> uhs;
		Map<String, Object> parameters;
		String hql;
		String sortTableName = "";	
		String keyWordHql = "";
		//判断是否含有关键之搜寻
		if(null != pageModel.getSearchKeyWord()&&!pageModel.getSearchKeyWord().equals("")){
			String keyWord = pageModel.getSearchKeyWord();
			keyWordHql = " and Lower(uh.filename) like '%"+keyWord.toLowerCase()+"%' ";
		}
		//如果对Files表的字段排序，如filesize等
		
		if(pageModel.getOrder().equals("filesize")){
			sortTableName = "f";
		}else{
			//如果对UploadHistory表的字段排序，如filename等
			sortTableName = "uh";
		}
		//查询不为文件夹的指定用户的文件
		hql = "select new UploadHistory(uh.id,uh.files,uh.users,uh.uploadtime,uh.status,uh.filename) from UploadHistory uh,Files f where f.fileid=uh.files.fileid and f.isdir=:isdir and uh.status=:uhStatus_activty and uh.users.userid=:userid and ismatchext(getFileNameExt(uh.filename),:hql_exts)=:hql_exts_status "+keyWordHql+" order by "+sortTableName+"."+pageModel.getOrder()+" "+pageModel.getSort();
		/*
		 * 判断是否为查询“其他”
		 */
		String hql_exts = pageModel.getExts();
		Integer hql_exts_status = 1;
		//如果属于“其他”后缀，或则文件没有后缀
		if(UploadHistory.ALLExts.toLowerCase().indexOf(pageModel.getExts().toLowerCase())<0||pageModel.getExts().equals("")){
			hql_exts = UploadHistory.ALLExts.toLowerCase();
			hql_exts_status = 0;
		}
		parameters = FUtils.getMap(new String[]{"isdir","uhStatus_activty","userid","hql_exts","hql_exts_status"}, new Object[]{Files.ISDIR_NO,UploadHistory.STATUS_ACTIVITY,userid,hql_exts,hql_exts_status});
		
		//获取结果
		uhs = fUploadHistoryDao.queryByHql(hql, parameters,pageModel.getPage(), pageModel.getRows());
		fpuhs = FUtils.getList();
		//设置filesize和ext；添加节点；
		for (int i=0;i<uhs.size();i++) {
			UploadHistory uh = uhs.get(i);
			String filename = uh.getFilename();
			String resExt = FUtils.getFileNameExt(filename);
			FPageUploadHistory fpuh = new FPageUploadHistory(); 
			Files file = uh.getFiles();
			//复制基础属性
			BeanUtils.copyProperties(uh, fpuh);
			fpuh.setFilesize(file.getFilesize());
			//获取
			fpuh.setExt(resExt);
			
			fpuhs.add(fpuh);
			
			
			
		}
		
		return fpuhs;
	}

	/**
	 * 通过uploadhistory的当前目录id获取目录和文件列表
	 * @param userid
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	private List<FPageUploadHistory> getDirectoryAndFiles(String userid,
			FPageUploadHistory pageModel) throws Exception {
		List<FPageUploadHistory> fpuhs;
		List<UploadHistory> uhs;
		Map<String, Object> parameters;
		String hql;
		String sortTableName = "";	
		String keyWordHql = "";
		String pIdHql = "";
		//判断是否含有关键之搜寻，有关键字搜寻，那么不限定目录
		if(null != pageModel.getSearchKeyWord()&&!pageModel.getSearchKeyWord().equals("")){
			String keyWord = pageModel.getSearchKeyWord();
			keyWordHql = " and Lower(uh.filename) like '%"+keyWord.toLowerCase()+"%' ";
		}else{//没有关键字搜寻，那么限定目录
			//判断父id语句
			if(pageModel.getpId()!=null){
				pIdHql = " and uh.uploadhistory.id='"+pageModel.getpId()+"' ";
			}else{
				pIdHql = " and uh.uploadhistory is null ";
			}
		}
		
		//判断排序表；如果对file表排序-f；如果对uploadhistory排序-uh
		if(pageModel.getOrder().equals("filesize")){
			sortTableName = "f";
		}else{
			//如果对UploadHistory表的字段排序，如filename等
			sortTableName = "uh";
		}
		//声明语句
		hql = "select new UploadHistory(uh.id,uh.files,uh.users,uh.uploadtime,uh.status,uh.filename) from UploadHistory uh,Files f where f.fileid=uh.files.fileid "+pIdHql+" and uh.status=:uhStatus_activty and uh.users.userid=:userid "+keyWordHql+" order by "+sortTableName+"."+pageModel.getOrder()+" "+pageModel.getSort();
		parameters = FUtils.getMap(new String[]{"uhStatus_activty","userid"}, new Object[]{UploadHistory.STATUS_ACTIVITY,userid});
		
		
		//获取结果
		uhs = fUploadHistoryDao.queryByHql(hql, parameters, pageModel.getPage(), pageModel.getRows());
		fpuhs = FUtils.copyProList(FPageUploadHistory.class, uhs);
		//设置filesize和ext
		for (int i=0;i<uhs.size();i++) {
			UploadHistory uh = uhs.get(i);
			FPageUploadHistory fpuh= fpuhs.get(i);
			Files file = uh.getFiles();
			fpuh.setFilesize(file.getFilesize());
			//目录的ext为directory，其他的文件的ext为文件后缀
			if(Files.ISDIR_YES == file.getIsdir()){
				fpuh.setExt("directory");
			}else{
				fpuh.setExt(FUtils.getFileNameExt(uh.getFilename()));
			}
		}
		return fpuhs;
	}


	@Override
	public void updateFileNameById(String userid,FPageUploadHistory pageModel)
			throws Exception {
		if(pageModel.getFilename().getBytes("utf-8").length>50){
			throw new Exception("文件名称过长");
		}
		String pIdHql;
		//验证本级目录是否存在同名文件
		UploadHistory currtUh = fUploadHistoryDao.get(UploadHistory.class, pageModel.getId());
		//判断父id语句
		if(currtUh.getUploadhistory()!=null){
			pIdHql = " and uh.uploadhistory.id='"+currtUh.getUploadhistory().getId()+"' ";
		}else{
			pIdHql = " and uh.uploadhistory is null ";
		}
		//查询父亲uploadhistory
		Map<String,Object> parameters = FUtils.getMap(new String[]{"status_activity","filename","uhSelfId"}, new Object[]{UploadHistory.STATUS_ACTIVITY,pageModel.getFilename(),pageModel.getId()});
		UploadHistory sameFilenameUh = fUploadHistoryDao.getByHql("from UploadHistory uh where uh.status=:status_activity and uh.filename=:filename and uh.id!=:uhSelfId"+pIdHql,parameters);
				
		//存在同名的文件
		if(null != sameFilenameUh){
			throw new Exception("文件名已存在");
		}
		currtUh.setFilename(pageModel.getFilename());
		fUploadHistoryDao.saveOrUpdate(currtUh);
		
	}

	@Override
	public void deleteFileById(String userid, FPageUploadHistory pageModel)
			throws Exception {
		//获取指定uploadid的uploadhistory记录，uploadhistory删除，file为物理记录，不删除
		UploadHistory uh = fUploadHistoryDao.get(UploadHistory.class, pageModel.getId());
		
		//删除节点（包括传过去的父节点）
		Double deleteFilesSize = deleteFilesAndCountTotalSize(uh);
		//更新用户的空间
		Users user = uh.getUsers();
		user.setSavedfilesize(user.getSavedfilesize()-deleteFilesSize);
		fUsersDao.save(user);
	}
	/**
	 * 递归删除父节点的所有子孙的文件（包括父亲），并且统计文件总大小
	 * @param parent
	 */
	private Double deleteFilesAndCountTotalSize(UploadHistory parent){
		//获取节点
		parent = fUploadHistoryDao.get(UploadHistory.class, parent.getId());
		//获取文件的大小
		Double filesize = 0d;
		//如果为激活的文件或则文件夹，那么可以删除和统计大小
		if(parent.getStatus()==UploadHistory.STATUS_ACTIVITY){

			//标志为删除
			parent.setStatus(UploadHistory.STATUS_DELETE);
			fUploadHistoryDao.saveOrUpdate(parent);
			
			if(parent.getFiles().getIsdir()==Files.ISDIR_NO){//如果为文件
				filesize = parent.getFiles().getFilesize();
				return filesize;
			}else {//如果似文件夹
				//获取子节点
				List<UploadHistory> childs = new ArrayList<UploadHistory>(parent.getChilds());
				FLogger.info("childs.size():"+childs.size());
				if(null!=childs&&childs.size()>=1){
					for(UploadHistory c:childs){
						filesize = filesize+deleteFilesAndCountTotalSize(c);
					}
					return filesize;
				}
			}
		}
		
		return 0d;
		
	}

	
	@Override
	public void newFileById(String userid, FPageUploadHistory pageModel)
			throws Exception {
		if(pageModel.getFilename().getBytes("utf-8").length>50){
			throw new Exception("文件名称过长");
		}
		String pIdHql;
		//验证本级目录是否存在同名文件
		//判断父id语句
		if(pageModel.getpId()!=null){
			pIdHql = " and uh.uploadhistory.id='"+pageModel.getpId()+"' ";
		}else{
			pIdHql = " and uh.uploadhistory is null ";
		}
		//查询父亲uploadhistory
		Map<String,Object> parameters = FUtils.getMap(new String[]{"status_activity","filename","userid"}, new Object[]{UploadHistory.STATUS_ACTIVITY,pageModel.getFilename(),userid});
		UploadHistory sameFilenameUh = fUploadHistoryDao.getByHql("from UploadHistory uh where uh.status=:status_activity and uh.filename=:filename and uh.users.userid=:userid"+pIdHql,parameters);
		//存在同名的文件
		if(null != sameFilenameUh){
			throw new Exception("文件名已存在");
		}
		
		//存储到files表
		Files newFile = new Files();
		/*newFile.setFilename(pageModel.getFilename());*/
		newFile.setFileid(UUID.randomUUID().toString());
		newFile.setStatus(Files.STATUS_ACTIVITY);
		newFile.setIsdir(Files.ISDIR_YES);
		newFile.setFilesize(Files.FILESIZE_DIRECTORY);
		fFilesDao.save(newFile);
		
		
		
		//储存到UploadHistory表
		//创建user
		Users user = new Users();
		user.setUserid(userid);
		UploadHistory uh = new UploadHistory();
		uh.setFiles(newFile);
		uh.setId(pageModel.getId());
		uh.setStatus(UploadHistory.STATUS_ACTIVITY);
		uh.setUploadtime(new Timestamp(new Date().getTime()));
		uh.setUsers(user);
		uh.setFilename(pageModel.getFilename());
		
		//设置父uploadhistory
		if(pageModel.getpId()!=null){

			UploadHistory parent = new UploadHistory();
			parent.setId(pageModel.getpId());
			uh.setUploadhistory(parent);
			
		}else{
			uh.setUploadhistory(null);
		}
		fUploadHistoryDao.save(uh);
	}


	@Override
	public void uploadFiles(String userid, FPageUploadHistory pageModel)
			throws Exception {
		File targetFile = null;
		
		try {
			
			String pIdHql;
			//验证本级目录是否存在同名文件，存在则取别名
			//判断父id语句
			if(pageModel.getpId()!=null&&!pageModel.getpId().equals("undefined")){
				pIdHql = " and uh.uploadhistory.id='"+pageModel.getpId()+"' ";
			}else{
				pIdHql = " and uh.uploadhistory is null ";
			}
			//取别名
			//保存重复的原名
			String oldFileNameWithoutExt = "";
			int i = 1;
			while(true){
				//查询父亲uploadhistory
				Map<String,Object> parameters = FUtils.getMap(new String[]{"status_activity","filename"}, new Object[]{UploadHistory.STATUS_ACTIVITY,pageModel.getUploadFileName()});
				UploadHistory sameFilenameUh = fUploadHistoryDao.getByHql("from UploadHistory uh where uh.status=:status_activity and uh.filename=:filename "+pIdHql,parameters);
				//存在同名的文件
				if(null != sameFilenameUh){
					String ext = FUtils.getFileNameExt(pageModel.getUploadFileName());
					if(i==1){
						oldFileNameWithoutExt = FUtils.getFileNameWihoutExt(pageModel.getUploadFileName());;
					}
					pageModel.setUploadFileName(oldFileNameWithoutExt+"("+i+")."+ext);
				}else{
					break;
				}
				i++;
			}
			
			
			
			//生成文件id
			String fileid = UUID.randomUUID().toString();
			//获取文件后缀
			String fileExt = FUtils.getFileNameExt(pageModel.getUploadFileName());
			//生成的filename
			String generateFileName = fileid +"."+ fileExt;
			//文件全路径，包含name
			String fileDiskPath = Files.FILEPATH + generateFileName;
			//生成文件
			targetFile = new File(fileDiskPath);
			// 文件已存在（上传了同名的文件）
			if (pageModel.getChunk() == 0 && targetFile.exists()) {
				targetFile.delete();
				targetFile = new File(fileDiskPath);
			}
			//储存文件
			FUtils.copyFiles(pageModel.getUpload(), targetFile);
			/*System.out.println("上传文件:" + pageModel.getUploadFileName()
					+ " 临时文件名：" + pageModel.getUploadContentType() + " "
					+ pageModel.getChunk() + " " + pageModel.getChunks());*/
			/*if (pageModel.getChunk() == pageModel.getChunks() - 1) {
				// 完成一整个文件;
			}*/
			
			
			
			//保存files记录
			Files file = new Files();
			file.setFileid(fileid);
			file.setFilepath(generateFileName);
			file.setFilesize((double) (FUtils.getFileSize(targetFile)));
			file.setStatus(Files.STATUS_ACTIVITY);
			file.setIsdir(Files.ISDIR_NO);
			fFilesDao.save(file);
			//保存uploadhistory记录
			UploadHistory uh = new UploadHistory();
			uh.setFilename(pageModel.getUploadFileName());
			uh.setFiles(file);
			uh.setId(UUID.randomUUID().toString());
			uh.setStatus(UploadHistory.STATUS_ACTIVITY);
			//设置父标签
			UploadHistory parent = fUploadHistoryDao.get(UploadHistory.class,
					pageModel.getpId());
			uh.setUploadhistory(parent);
			uh.setUploadtime(new Timestamp(new Date().getTime()));
			//获取和设置users的信息
			Users dbUser = fUsersDao.get(Users.class, userid);
			Double userdSize = dbUser.getSavedfilesize();
			//修改user的已储存空间大小
			dbUser.setSavedfilesize(userdSize+file.getFilesize());
			fUsersDao.save(dbUser);
			uh.setUsers(dbUser);
			fUploadHistoryDao.save(uh);
		} catch (Exception e) {
			//出现异常，删除已经上传的文件
			FUtils.deleteFile(targetFile);
			e.printStackTrace();
			throw e;
		}
	}


	@Override
	public FPageUploadHistory downloadFile(String userid, FPageUploadHistory pageModel)
			throws Exception {
		UploadHistory uh = fUploadHistoryDao.get(UploadHistory.class, pageModel.getId());
		String filepath = uh.getFiles().getFilepath();
		BeanUtils.copyProperties(uh, pageModel);
		pageModel.setFilepath(filepath);
		return pageModel;
		
	}


	@Override
	public List<FPageUploadHistory> getDirectorysByPId(String userid,
			FPageUploadHistory pageModel) throws Exception {
		
		List<FPageUploadHistory> fpuhs;
		List<UploadHistory> uhs;
		Map<String, Object> parameters;
		String hql;
		String pIdHql = "";
		//判断父id语句
		if(pageModel.getpId()!=null){
			pIdHql = " and uh.uploadhistory.id='"+pageModel.getpId()+"' ";
		}else{
			pIdHql = " and uh.uploadhistory is null ";
		}
		//声明语句，查询子文件夹
		hql = "select new UploadHistory(uh.id,uh.files,uh.users,uh.uploadtime,uh.status,uh.filename) from UploadHistory uh,Files f where f.fileid=uh.files.fileid and f.isdir=:isdir "+pIdHql+" and uh.status=:uhStatus_activty and uh.users.userid=:userid and uh.id!=:selfId order by uh.uploadtime asc";
		parameters = FUtils.getMap(new String[]{"isdir","uhStatus_activty","userid","selfId"}, new Object[]{Files.ISDIR_YES,UploadHistory.STATUS_ACTIVITY,userid,pageModel.getId()});
		
		
		//获取结果
		uhs = fUploadHistoryDao.queryByHql(hql, parameters, -1, -1);
		fpuhs = FUtils.copyProList(FPageUploadHistory.class, uhs);
		//检测当前文件夹是否还有子节点
		for (int i=0;i<uhs.size();i++) {
			UploadHistory uh = uhs.get(i);
			hql = "select new UploadHistory(uh.id,uh.files,uh.users,uh.uploadtime,uh.status,uh.filename) from UploadHistory uh,Files f where f.fileid=uh.files.fileid and f.isdir=:isdir and uh.uploadhistory.id=:pId and uh.status=:uhStatus_activty and uh.users.userid=:userid and uh.id!=:selfId";
			parameters = FUtils.getMap(new String[]{"isdir","pId","uhStatus_activty","userid","selfId"}, new Object[]{Files.ISDIR_YES,uh.getId(),UploadHistory.STATUS_ACTIVITY,userid,pageModel.getId()});
			//获取结果
			List<UploadHistory> uhs_01 = fUploadHistoryDao.queryByHql(hql, parameters, 1, 1);
			if(uhs_01!=null&&uhs_01.size()>=1){
				fpuhs.get(i).setChild(true);
			}else{
				fpuhs.get(i).setChild(false);
			}
			
		}
		return fpuhs;
	}


	@Override
	public void moveDirectoryOrFile(String userid, FPageUploadHistory pageModel)
			throws Exception {
		UploadHistory son = fUploadHistoryDao.get(UploadHistory.class, pageModel.getId());
		//判断是否为移动到最顶级目录
		if(null != pageModel.getpId()&&!pageModel.getpId().equals("undefined")&&!pageModel.getpId().equals("null")){
			//查询同目录中的相同filename的文件
			Map<String,Object> parameters = FUtils.getMap(new String[]{"pId","filename","userid"}, new Object[]{pageModel.getpId(),son.getFilename().toLowerCase(),userid});
			UploadHistory sameFileNameUh = fUploadHistoryDao.getByHql("from UploadHistory uh where uh.uploadhistory.id=:pId and uh.filename=:filename and uh.users.id=:userid", parameters);
			//判断目标目录是否存在同名文件
			if(null != sameFileNameUh){
				throw new Exception("移动失败，目标目录存在同名文件");
			}
			UploadHistory parent = fUploadHistoryDao.get(UploadHistory.class, pageModel.getpId());
			son.setUploadhistory(parent);
		}else{
			//查询顶级目录中的相同filename的文件
			Map<String,Object> parameters = FUtils.getMap(new String[]{"filename","userid"}, new Object[]{son.getFilename().toLowerCase(),userid});
			UploadHistory sameFileNameUh = fUploadHistoryDao.getByHql("from UploadHistory uh where uh.uploadhistory is null and uh.filename=:filename and uh.users.id=:userid", parameters);
			//判断目标目录是否存在同名文件
			if(null != sameFileNameUh){
				throw new Exception("移动失败，目标目录存在同名文件");
			}
			son.setUploadhistory(null);
		}
		
		fUploadHistoryDao.save(son);
	}


	@Override
	public void saveOtherUserFileToSelf(String userid,
			FPageUploadHistory pageModel) throws Exception {
		
		
		
		//被保存的文件
		UploadHistory savedUploadHistory = fUploadHistoryDao.get(UploadHistory.class, pageModel.getSavedUploadHistoryId());
	
		if(userid.equals(savedUploadHistory.getUsers().getUserid())){
			throw new Exception("不能储存自己文件。");
		}
		
		//需要保存到的目标目录
		UploadHistory selecteddUploadHistory = fUploadHistoryDao.get(UploadHistory.class, pageModel.getSelectdUploadHistoryId());
		
		/*
		 * 验证目标目录是否重名
		 */
		 
		//如果selecteddUploadHistory==null，那么代表储存在最上层目录
		String pIdHql = "";
		//判断父id语句
		if(null != selecteddUploadHistory){
			pIdHql = " and uh.uploadhistory.id='"+selecteddUploadHistory.getId()+"' ";
		}else{
			pIdHql = " and uh.uploadhistory is null ";
		}
		String hql = "from UploadHistory uh where uh.users.userid=:userid and uh.filename=:filename and uh.status=:activity_status "+pIdHql;
		Map<String,Object> parameters = FUtils.getMap(new String[]{"userid","filename","activity_status"}, new Object[]{userid,savedUploadHistory.getFilename(),UploadHistory.STATUS_ACTIVITY});
		
		UploadHistory child = fUploadHistoryDao.getByHql(hql, parameters);
		if(null !=child){
			throw new Exception("目标目录存在同名文件。");
		}
		
		
		//递归复制节点
		copyUploadHistory(userid,selecteddUploadHistory,savedUploadHistory);
		
		//增加用户已用空间
		
		Users user = fUsersDao.get(Users.class, userid);
		Double totalSize = countDirSize(savedUploadHistory);
		FLogger.info(totalSize);
		user.setSavedfilesize(user.getSavedfilesize()+totalSize);
		fUsersDao.save(user);
	}
	/**
	 * 统计某个目录的总大小（包含其中的未删除的文件）
	 * @param dir
	 * @return
	 */
	private Double countDirSize(UploadHistory dir){
		//获取节点
		dir = fUploadHistoryDao.get(UploadHistory.class, dir.getId());
		//获取文件的大小
		Double filesize = 0d;
		//如果为激活的文件或则文件夹，那么可以删除和统计大小
		if(dir.getStatus()==UploadHistory.STATUS_ACTIVITY){

			
			if(dir.getFiles().getIsdir()==Files.ISDIR_NO){//如果为文件
				filesize = dir.getFiles().getFilesize();
				return filesize;
			}else {//如果似文件夹
				//获取子节点
				List<UploadHistory> childs = new ArrayList<UploadHistory>(dir.getChilds());
				/*FLogger.info("childs.size():"+childs.size());*/
				if(null!=childs&&childs.size()>=1){
					for(UploadHistory c:childs){
						filesize = filesize+countDirSize(c);
					}
					return filesize;
				}
			}
		}
		
		return 0d;
		
	}
	/**
	 * 递归复制savedUploadHistory目录下的文件到selecteddUploadHistory，并将新的复制的节点存入数据库；<br/>
	 * 并且修改新节点用户id；<br/>
	 * 并且赋值新节点新的id；<br/>
	 * @param userid
	 * @param selecteddUploadHistory：必须处于session中的对象
	 * @param savedUploadHistory：必须处于session中的对象
	 */
	private void copyUploadHistory(String userid,UploadHistory selecteddUploadHistory,UploadHistory savedUploadHistory){
		UploadHistory newUh = new UploadHistory();
		//复制基本属性
		BeanUtils.copyProperties(savedUploadHistory, newUh);
		/*
		 * 设置userid，id，和父节点
		 */
		//设置id
		newUh.setId(UUID.randomUUID().toString());
		//设置userid
		Users user = fUsersDao.get(Users.class, userid);
		newUh.setUsers(user);
		//设置父节点
		newUh.setUploadhistory(selecteddUploadHistory);
		/*
		 *设置child为空
		 */
		newUh.setChilds(null);
		//设置时间
		newUh.setUploadtime(new Timestamp(new Date().getTime()));
		/*
		 * 保存操作
		 */
		fUploadHistoryDao.save(newUh);
		
		
		/*
		 * 遍历子节点
		 */
		List<UploadHistory> childs = new ArrayList<UploadHistory>(savedUploadHistory.getChilds());
		for(int i =0;i<childs.size();i++){
			copyUploadHistory(userid, newUh, childs.get(i));
		}
		
	}

	
	
}
