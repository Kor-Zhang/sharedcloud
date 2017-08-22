var sharedFilesBody;

//文件的table
var sharedFilesTable;
//table的行
var trs;
//当前单击的行
var clickedTrId;
var click_tr_bg = "rgb(241,242,243)";
var unclick_tr_bg = "white";
//记录操作按钮的当前状态
var allBtnStatus;
//排序标题
var fillist_title_filename;
var fillist_title_filesize;
var fillist_title_uploadtime;
//下载按钮
var sharedFilesDonloadBtn;
//保存按钮
var sharedFilesSaveBtn;
//记录所有操作按钮的状态：1代表激活，-1代表冻结
var allBtnStatus = 1;
//返回上一级
var sharedFilesPositionDiv;
var sharedFilesBackUp;

//显示提示信息
var sharedFilesTiper;
var sharedFilesTiperMsg;
//默认提示信息
var defaultTiperMsg;
var tiperCloseTimer; 
//当前文件的父id
var currtUploadHistoryDirPId;

//储存上级的uploadhistory的id
var file_parent_id_arr = new Array();
//储存上级的uploadhistory的filername
var file_parent_filename_arr = new Array();
//当前排序标题
var currt_select_title;
//排序的sort参数
var sort = "desc";
var order = "uploadtime";
var descImg = "▾";
var ascImg = "▴";
//是否为默认排序（即默认排序为uploadtime排序），1为是默认排序，-1不是默认排序
//加载列表的两个方法loadFileList和loadExtFileList将调用该参数

var dblClickedTrId;
var isDefaultSort = -1;

//保存分享记录Id
var sharedfileid;

//返回按钮
var backBtn;
var dblClickedTrId;
var sharedFilesPositionDiv;
var upFileItems;
//分享的文件信息
var sharedFilesTime;
var sharedFilesBrowseNumber;
var sharedFilesDownloadNumber;
var sharedFilesFileName;
var sharedFilesSaveNumber;

//分享文件图标
var sharedFilesIcon;

//储存需要查询的记录的用户的id
var userid = undefined;
var currtSharedFilesPage = 1;
$(function($){
	//获取地址栏分享记录的id参数
	sharedfileid = getParam("sharedfileid");
	
	
	initsharedFilesDoms();
	setsharedFilesUI();
	setsharedFilesLis();
	
	//加载分享的文件记录
	loadSharedfiles(sharedfileid,function(data){
		showLoadingModelWithContent("正在加载分享文件", 0, function(){//打开等待界面
			//判断是否需要输入密码
			var ispublic = data.obj.ispublic;
			if(false==ispublic){//如果为私密连接，那么弹出密码输入框，等待用户输入
				/*
				 *打开密码输入框，等待用户输入信息 
				 */
				showInputPwdWin(function(pwd){
					
					showLoadingModelWithContent("正在验证分享文件密码", 0, function(){//打开等待界面
						var parameters = new Object();
						parameters["password"] = pwd;
						var currtSharedfilesid = data.obj.sharedfileid;
						parameters["sharedfileid"] = sharedfileid;
						//验证密码
						zkAjaxData("/pc/front/fSharedfilesAction!verifySharedfilesPwd.action",parameters, function(data){

							if(data.success){//验证成功
								hideInputPwdWin();//隐藏密码输入框
							}else{
								showMsgWin(undefined, data.msg, 0);//显示信息
								showInputPwdWin();//显示输入框
							}
							hideLoadingModel(0);//隐藏等待界面
						}, true);
					});
				});
				
				
			}else{//如果为公共连接，关闭密码输入框
				hideInputPwdWin();
			}
			hideLoadingModel(0);//隐藏等待界面
		});
		
	});
	
	
});
/**
 * 加载分享的文件记录
 */
function loadSharedfiles(sharedfileid,successCallFun){
	
	var parameters = new Object();
	parameters["sharedfileid"] = sharedfileid;
	//加载文件一级列表
	zkAjaxData("/pc/front/fSharedfilesAction!getSharedfilesById.action",parameters, function(data){

		//显示等待提示
		showTiper(data.msg);
		//加载成功
		if(data.success){
			//清除table（除标题外）
			clearTrs();
			//加载数据
			var file = data.obj;
			var fileId = file.uploadHistoryId;
			var fileName = file.filename;
			var fileSize = file.filesize;
			var sharedtime = file.sharedtime;
			var ext = file.ext;
			var downloadnumber = file.downloadnumber;
			var savenumber = file.savenumber;
			var browsenumber = file.browsenumber;
			/*"browsenumber":0,"downloadnumber":0,"ext":"directory","filename":"我的项目","filesize"
			:-1,"ispublic":true,"savenumber":0,"sharedfileid":"afdb127c-42d1-4adf-9d07-91c3a728f352","sharedtime"
			:"2017-01-02 19:25:22","status":1,"uploadHistoryId":"81a12bf0-0908-4d1a-84e6-ae736a1a2b33"},"online"
			:true,"success":true}*/
			
			addFileNode(fileId,fileName,fileSize,sharedtime,ext,false);
			
			//加载其他属性
			sharedFilesTime.html(sharedtime);
			sharedFilesBrowseNumber.html(browsenumber);
			sharedFilesDownloadNumber.html(downloadnumber);
			sharedFilesFileName.html(fileName);
			sharedFilesSaveNumber.html(savenumber);
			//设置图标
			sharedFilesIcon.attr("src",getFileImgSrc(ext));
			/*for ( var f in files) {
//				c(files[f]);
				var fileId = files[f].id;
				var fileName = files[f].filename;
				var fileSize = files[f].filesize==undefined?"":files[f].filesize;
				var uploadTime = files[f].uploadtime;
				var ext = files[f].ext;
				addFileNode(fileId,fileName,fileSize,uploadTime,ext,false);
			}*/
			
			//设置当前分享记录的userid记录
			userid = file.userid;
			//回调方法
			if(successCallFun){
				successCallFun(data);
			}
		}else{
			//显示错误详细
			showLoadingModelWithContent(data.msg,1,function(){
				setTimeout(function(){
					self.location= getWebProjectName()+'/pc/front/jsp/info.jsp?msg='+data.msg;
				}, 1000);
			});
		}
		//判断是否能够激活按钮
		/*if(1 == canEnableAllBtn){
			enableAllBtn();
		}*/
		//隐藏提示
		hideTiper(100);
		//产生动画效果；滑动滚动条到最上部；
		/*scrollTo(index_content_right_filelist_table_div,$("html"),800);*/
		//回调函数
		/*if(callFun){
			callFun();
		}*/

	}, true);
}
/**
 * 清除列表记录（除标题外）
 */
function clearTrs(){
	//清除table（除标题外）
	for(var i = 0;i<trs.length;i++){
		$(trs[i]).remove();
	}
}
/**
 * dom初始化
 */
function initsharedFilesDoms(){
	sharedFilesBody = $("#sharedFilesBody");
	sharedFilesTable = $("#sharedFilesListTable");
	trs = $(".trs");
	//排序按钮
	fillist_title_filename = $("#fillist_title_filename");
	fillist_title_filesize = $("#fillist_title_filesize");
	fillist_title_uploadtime = $("#fillist_title_uploadtime");
	
	//默认排序：时间
	setSelectedTitle(fillist_title_uploadtime);
	//下载按钮
	sharedFilesDonloadBtn = $("#sharedFilesDonloadBtn");
	//保存按钮
	sharedFilesSaveBtn = $("#sharedFilesSaveBtn");;
	
	//返回上一级
	sharedFilesPositionDiv = $("#sharedFilesPositionDiv ");
	sharedFilesBackUp = $("#sharedFilesBackUp");
	//显示提示信息
	sharedFilesTiper = $("#sharedFilesTiper");
	sharedFilesTiperMsg = $("#sharedFilesTiperMsg");
	defaultTiperMsg = sharedFilesTiperMsg.html();
	
	backBtn = $("#sharedFilesBackUp");
	sharedFilesPositionDiv = $("#sharedFilesPositionDiv");
	upFileItems = $(".upFileItems");
	
	
	sharedFilesTime = $("#sharedFilesTime");;
	sharedFilesBrowseNumber = $("#sharedFilesBrowseNumber");;
	sharedFilesDownloadNumber = $("#sharedFilesDownloadNumber");;
	sharedFilesFileName = $("#sharedFilesFileName");;
	sharedFilesSaveNumber = $("#sharedFilesSaveNumber");;
	
	sharedFilesIcon = $("#sharedFilesIcon");
}


/**
 *  生成文件节点
 * @param editFileName:设置是否开启编辑
 * @param fileId
 * @param fileName
 * @param fileSize
 * @param uploadTime
 * @param first：节点的添加未知：第一行或则最后一行，默认为first=false即最后一行
 * @returns
 */
function addFileNode(fileId,fileName,fileSize,uploadTime,ext,first){
	//生成节点，记录该行后缀类型（fileExtClazz，如txt）
	var tr = $("<tr fileextclazz='"+ext+"' id='"+fileId+"' class='trs'></tr>");
	
	var td1 = $("<td class='directory_Name_Icon_td'>"); 
	var td1_div1 = $("<div class='directoryIcon_div'></div>");
	var td1_div1_img = $("<img class='directoryIcon' src='"+getFileImgSrc(ext)+"'/>");
	td1_div1.append(td1_div1_img);
	td1.append(td1_div1);
	
	var td1_div2 = $("<div class='directoryName_div'></div>");
	var td1_div2_input = $("<label class='fileName'>"+fileName+"</label>");
	td1_div2.append(td1_div2_input);
	td1.append(td1_div2);
	//设置文件显示大小
	
	var td2 = $("<td>"+getFormatFileSize(fileSize)+"</td>"); 
	
	
	var td3 = $("<td>"+uploadTime+"</td>"); 
	
	tr.append(td1);
	tr.append(td2);
	tr.append(td3);
	if(first!=undefined&&first==true){
		//添加第一行（标题后面）
		sharedFilesTable.find("tr").eq(0).after(tr);
	}else{
		//添加一行到fileTable的的最后一行
		sharedFilesTable.find("tr:last").after(tr);
	}
	
	
	/*//设置是否开启编辑
	if(enableEditFileName==false){
		disEditFileName(td1_div2_input);
	}else{
		editFileName(td1_div2_input);
	}*/
	//重新加载dom
	trs = $(".trs");
	
	//重新加载监听器
	setsharedFilesLis();
	//设置对filenam的监听,主要是对焦点脱离事件的监控（文件名为空不能脱离焦点）
	/*setFileNameDomEndEditLis(td1_div2_input);*/
	return tr;
}

/**
 * 初始化dom样式
 */
function setsharedFilesUI(){
	sharedFilesBody.height(html.height());
	sharedFilesBody.width(html.width());
	//默认隐藏位置导航栏
	hideBackNav();
	//默认隐藏操作按钮
	hideOperationBtn();
	//隐藏信息显示
	hideTiper(0);
	
}
/**
 * 设置监听事件
 */
function setsharedFilesLis(){
	//table的行点击事件
	setSharedTableTrClickLis();
	//排序监听
	setSortLis();
	//行双击事件
	setTrDblClickLis();
	//返回按钮触发事件
	setBackBtnClickLis();
	//点击保存按钮的监听事件
	setSaveBtnClickBtnL();
	//设置下载按钮点击事件
	setDownloadBtnClickBtnLis();
}
/**
 * 设置下载按钮点击事件
 */
function setDownloadBtnClickBtnLis(){
	sharedFilesDonloadBtn.unbind("click");
	sharedFilesDonloadBtn.click(function(){
		//如果为目录，拒绝下载
		if(getTrDomByTrId(clickedTrId).attr("fileextclazz")=="directory"){
			showMsgWin(undefined,"不能下载文件夹",0);
			return;
		}
		//设置下载路径
		self.location= getWebProjectName()+"/pc/front/fUploadHistoryAction!downloadFile.action?id="+clickedTrId;
	});
}
/**
 * 点击保存按钮的监听事件
 */
function setSaveBtnClickBtnL(){
	sharedFilesSaveBtn.unbind("click");
	sharedFilesSaveBtn.click(function(){
		//显示等待提示
//		showTiper("正在保存");
		//弹出保存框
		showCopyMoveWin("保存",clickedTrId,function(savedDirId,selectdDirId){
			//显示等待
			showLoadingModelWithContent("正在保存。",undefined,function(){
				
			});
			//保存到当前用户的目录中
			var parameters = new Object();
			parameters["savedUploadHistoryId"] = savedDirId;
			parameters["selectdUploadHistoryId"] = selectdDirId;
			//加载文件一级列表
			zkAjaxData("/pc/front/fUploadHistoryAction!saveOtherUserFileToSelf.action",parameters, function(data){

				//显示提示
//				showTiper(data.msg);
				//隐藏等待
				hideLoadingModel(undefined);
				showMsgWin(undefined,data.msg,undefined);
				//加载成功
				if(data.success){
					
				}else{
					
				}
				//隐藏提示
//				hideTiper(100);

			}, true);
		});
		
	});
}
/**
 * 返回按钮触发事件
 */
function setBackBtnClickLis(){
	backBtn.unbind("click");
	backBtn.click(function(){
		/*//如果有正在编辑的，那么拒绝操作
		if(currtEditTrId){
			return;
		}*/
		//冻结操作按钮
		disableAllBtn();
		//隐藏操作按钮
		hideOperationBtn();
		//获取上一级目录id
		var uploadHistoryId = file_parent_id_arr.pop();
		currtUploadHistoryDirPId = uploadHistoryId;
		file_parent_filename_arr.pop();
		/*if(file_parent_id_arr.length<=0){
			hideBackNav();
		}*/
		
	/*	c(file_parent_id_arr);
		c(file_parent_filename_arr);*/
		
		//设置导航信息
		setBackNavItems();
		//判断加载方式
		if(currtUploadHistoryDirPId){
			//设置sort和order，恢复默认排序
			
			loadFilesList(undefined,function(){
				//ajax执行完成后，激活按钮
				enableAllBtn();
			});
		}else{
			loadSharedfiles(sharedfileid);
		}
		
		
		
		
	});
}

/**
 * 通过事先对file_parent_filename_arr的push和pop操作，对导航文字进行刷新
 */
function setBackNavItems(){
	//清空显示的位置信息
	var ins = sharedFilesPositionDiv.find(".upFileItems");
	for(var i =1;i<ins.length;i++){
		$(ins[i]).remove();
	}
	for(var i = 0;i<file_parent_filename_arr.length;i++){
		var filename = file_parent_filename_arr[i];
		//添加导航文字显示
		var str = ">"+filename;
		var span = "<spa class='upFileItems' parentId='"+dblClickedTrId+"'>"+str+"</span>";
		sharedFilesPositionDiv.append(span);
		
		
	}
	//重新加载信息
	//reloadAfterOperFile();
	upFileItems = $(".upFileItems");
	//如果导航栏为“全部文件”，那么隐藏
	if(upFileItems.length<=1){
		hideBackNav();
	}
}
/**
 * 行双击事件
 */
function setTrDblClickLis(){
	trs.unbind("dblclick");
	trs.dblclick(function(){

		dblClickedTrId = getTrIdByTrDom($(this));
		//判断文件类型
		if(getTrDomByTrId(dblClickedTrId).attr("fileextclazz")!="directory"){
			return;
		}
		//隐藏操作按钮
		hideOperationBtn();
		
		//设置数组
		file_parent_id_arr.push(currtUploadHistoryDirPId);
		c(file_parent_id_arr);
		c(file_parent_filename_arr);
		
		//设置uploadhistory父id
		currtUploadHistoryDirPId = getTrIdByTrDom($(this));
		

		file_parent_filename_arr.push(getFileNameByTrId(dblClickedTrId));
		
		//设置导航信息
		setBackNavItems();
		//加载数据
		loadFilesList(undefined, function(data){
			c(data);
		});
		
	});
}
/**
 * 通过现在目录的id（uploadhistory#id）加载文件列表，携带sort和order参数；<br/>
 * 自动冻结操作按钮机制：即在调用者没有冻结按钮而进行了获取列表（loadFilesList）操作，那么loadFilesList将自动冻结操作按钮<br/>
 * @param sortDom：选择排序的dom；如果为undefined，那么默认为uploadtime排序
 * @param callFun：列表加载完成后的回调函数
 */
function loadFilesList(sortDom,callFun){
	//如果是第一级被分享的文件，那么拒绝排序，获取列表
	if(!currtUploadHistoryDirPId){
		return ;
	}
	
	
	//显示等待提示
	showTiper("正在获取列表");
	//记录是否能在加载列表完成后接触按钮冻结
	var canEnableAllBtn = -1;
	//外部未对按钮冻结
	if(allBtnStatus==1){
		disableAllBtn();
		canEnableAllBtn = 1;
	}
	
	//默认排序
	if(sortDom==undefined){
		setSelectedTitle(fillist_title_filename);
		setSelectedTitle(fillist_title_uploadtime);
	}else{
		setSelectedTitle(sortDom);
	}
	//需要传递的参数
	var parameters = new Object();
	
	/*//获取参数：exts；如果当前不是选择全部文件的按钮，说明应该是更具类型加载文件列表
	if(isExtListStatus()){
		//文件类型参数：
		parameters["exts"] = currtExts;
		//重置参数：当前的目录id
		currtUploadHistoryDirPId = undefined;
	}else{
		//文件类型参数：
		parameters["exts"] = "null";
	}*/
	
	
	//获取参数：当前需要查询的目录的父uploadhistory的id
	if(currtUploadHistoryDirPId!=undefined&&currtUploadHistoryDirPId!=""){
		parameters["pId"] = currtUploadHistoryDirPId;
		
		//显示返回上一级等按钮
		showBackNav();
	}else{
		/*//清空显示的位置信息
		var ins = file_positionDiv.find(".upFileItems");
		for(var i =1;i<ins.length;i++){
			ins[i].remove();
		}
		*/
		//不显示返回上一级等按钮
		hideBackNav();
	}
	
	
	
	
	parameters["order"] = order;
	parameters["sort"] = sort;
	//设置userid
	parameters["userid"] = userid;
	//设置page
	parameters["page"] = currtSharedFilesPage;
	
	//加载文件一级列表
	zkAjaxData("/pc/front/fUploadHistoryAction!getFiles.action",parameters, function(data){

		//显示等待提示
		showTiper(data.msg);
		//加载成功
		if(data.success){
			//清除table（除标题外）
			for(var i = 0;i<trs.length;i++){
				$(trs[i]).remove();
			}
			//加载数据
			var files = data.obj;
			for ( var f in files) {
//				c(files[f]);
				var fileId = files[f].id;
				var fileName = files[f].filename;
				var fileSize = files[f].filesize==undefined?"":files[f].filesize;
				var uploadTime = files[f].uploadtime;
				var ext = files[f].ext;
				addFileNode(fileId,fileName,fileSize,uploadTime,ext,false);
			}
		}else{
			/*//显示错误详细
			showMsgWin(undefined,data.msg, 1);*/
		}
		//判断是否能够激活按钮
		if(1 == canEnableAllBtn){
			enableAllBtn();
		}
		//隐藏提示
		hideTiper(100);
		//产生动画效果；滑动滚动条到最上部；
		/*scrollTo(index_content_right_filelist_table_div,$("html"),800);*/
		//回调函数
		if(callFun){
			callFun(data);
		}
	}, true);
}
/**
 * 排序监听
 */
function setSortLis(){
	/*▴▾*/
	fillist_title_filename.unbind("click");
	fillist_title_filename.click(function(){
		/*//如果当前有在编辑的行
		if(currtEditTrId){
			return;
		}*/
		//不是默认排序
		isDefaultSort = -1;
		loadFilesList(fillist_title_filename);
	});
	fillist_title_filesize.unbind("click");
	fillist_title_filesize.click(function(){
		/*//如果当前有在编辑的行
		if(currtEditTrId){
			return;
		}*/
		//不是默认排序
		isDefaultSort = -1;
		loadFilesList(fillist_title_filesize);
	});
	fillist_title_uploadtime.unbind("click");
	fillist_title_uploadtime.click(function(){
		/*//如果当前有在编辑的行
		if(currtEditTrId){
			return;
		}*/
		//不是默认排序
		isDefaultSort = 1;
		loadFilesList(fillist_title_uploadtime);
	});
}
/**
 * table的行点击事件
 */
function setSharedTableTrClickLis(){
	trs.unbind("click");
	/*点击一个文件，改变其背景，显示，隐藏按钮*/
	trs.click(function(){
		//选择行：改变currtclicktr和被选择的行的背景
		select_tr($(this));
		
	});
}
/**
 * 选择行：改变currtclicktr；设置被选择的行的背景；
 */
function select_tr(trDom){
	//获取当前trDom的id
	clickedTrId = getTrIdByTrDom(trDom);
	trDom = $(trDom);
	trs.css("background",unclick_tr_bg);
	trDom.css("background",click_tr_bg);
	//显示操作按钮
	showOperationBtn();
	//可操作按钮
	enableAllBtn();
}
/**
 * 通过tr的id属性获取.filename节点
 * @param trId
 * @returns
 */
function getFileNameDomByTrId(trId){
	return getTrDomByTrId(trId).find(".fileName");;
}
/**
 * 通过tr的id属性获取trdom节点
 * @param id
 * @returns
 */
function getTrDomByTrId(trId){
	return sharedFilesTable.find("#"+trId);
}
/**
 * 通过trdom节点获取tr的id属性
 * @param id
 * @returns
 */
function getTrIdByTrDom(trDom){
	return trDom.attr("id");
}
/**
 * 通过tr的id属性获取trdom节点的文件名
 * @param trId
 * @returns
 */
function getFileNameByTrId(trId){
	return getFileNameByFileNameDom(getTrDomByTrId(trId).find(".fileName"));
}
/**
 * 根据filename的dom节点获取filename
 * @param dom
 */
function getFileNameByFileNameDom(dom){
	var filename = dom.val();
	if(filename==undefined||filename==""){
		filename = dom.html();
	}
	return filename;
}
/**
 * 冻结所有按钮；与（enableAllBtn）配合使用，在一个操作进行时，禁止用户进行其他操作（***）
 */
function disableAllBtn(){
	allBtnStatus = -1;
	//操作按钮
	disabledBtn(sharedFilesDonloadBtn);
	disabledBtn(sharedFilesSaveBtn);
	//排序按钮
	disabledBtn(fillist_title_filename);
	disabledBtn(fillist_title_filesize);
	disabledBtn(fillist_title_uploadtime);
	
}
/**
 * 激活所有按钮；与（disableAllBtn）配合使用，在一个操作进行时，禁止用户进行其他操作（***）
 */
function enableAllBtn(){
	allBtnStatus = 1;
	//操作按钮
	enabledBtn(sharedFilesDonloadBtn);
	enabledBtn(sharedFilesSaveBtn);
	//排序按钮
	enabledBtn(fillist_title_filename);
	enabledBtn(fillist_title_filesize);
	enabledBtn(fillist_title_uploadtime);
	
}

/**
 * 冻结某个按钮
 * @param btn
 */
function disabledBtn(btn){
	$(btn).attr("disabled",true);
}
/**
 * 激活某个按钮
 * @param btn
 */
function enabledBtn(btn){
	$(btn).attr("disabled",false);
	$(btn).remove("disabled");
}

/**
 * 隐藏操作按钮
 */
function hideOperationBtn(){
	sharedFilesDonloadBtn.hide();
	sharedFilesSaveBtn.hide();
}
/**
 * 显示操作按钮
 */
function showOperationBtn(){
	sharedFilesDonloadBtn.show();
	sharedFilesSaveBtn.show();
}

/**
 * 隐藏返回导航
 */
function hideBackNav(){
	sharedFilesPositionDiv.hide();
}
/**
 * 显示返回导航
 */
function showBackNav(){

	sharedFilesPositionDiv.show();
}


/**
 * 显示操作等待提示
 * @param c
 */
function showTiper(c){
	if(c==undefined){
		c=defaultTiperMsg;
	}
	if(defaultTiperMsg){
		clearTimeout(tiperCloseTimer);
	}
	
	sharedFilesTiper.html(c);
	/*//隐藏导航
	hideBackNav();
	operFileLoading.show();*/
}
/**
 * outWaitTime秒后隐藏
 * operFileLoadingMsg来自jsp
 */
function hideTiper(outWaitTime){
	if(outWaitTime==undefined){
		outWaitTime = 0;
	}
	tiperCloseTimer = setTimeout(function(){
		sharedFilesTiperMsg.html(defaultTiperMsg);
		sharedFilesTiper.hide();
		//如果当前位于最顶层目录，那么一起取消返回导航
		if(currtUploadHistoryDirPId!=undefined){
			//显示导航
			showBackNav();
		}
		
	}, outWaitTime);
}

/**
 * 返回显示的文件内容
 */
function getFileImgSrc(ext){
/*	
	var musicExts ="<%=UploadHistory.MUSICEXTS%>";
	var otherExts ="<%=UploadHistory.OTHEREXTS%>";
	var picExts ="<%=UploadHistory.PICEXTS%>";
	var torExts ="<%=UploadHistory.TOREXTS%>";
	var txtExts ="<%=UploadHistory.TXTEXTS%>";
	var zipExts ="<%=UploadHistory.ZIPEXTS%>";
	var videoExts ="<%=UploadHistory.VIDEOEXTS%>";*/
	
	/*c(txtExts);
	c(ext);
	c(txtExts.indexOf(ext)>=0);*/
	//如果后缀为空，那么属于”其他“；与最后一种情况相似
	ext = ext.toLowerCase();
	if(""==ext){
		return getWebProjectName()+"/pc/front/img/icon/otherIcon.jpg?"+new Date().getTime();
	}else if(musicExts.indexOf(ext)>=0){
		return getWebProjectName()+"/pc/front/img/icon/musicIcon.jpg?"+new Date().getTime();
	}else if(picExts.indexOf(ext)>=0){
		return getWebProjectName()+"/pc/front/img/icon/picIcon.jpg?"+new Date().getTime();
	}else if(torExts.indexOf(ext)>=0){
		return getWebProjectName()+"/pc/front/img/icon/torIcon.jpg?"+new Date().getTime();
	}else if(txtExts.indexOf(ext)>=0){
		return getWebProjectName()+"/pc/front/img/icon/txtIcon.jpg?"+new Date().getTime();
	}else if(zipExts.indexOf(ext)>=0){
		return getWebProjectName()+"/pc/front/img/icon/zipIcon.jpg?"+new Date().getTime();
	}else if(videoExts.indexOf(ext)>=0){
		return getWebProjectName()+"/pc/front/img/icon/videoIcon.jpg?"+new Date().getTime();
	}else if(directoryExts.indexOf(ext)>=0){
		return getWebProjectName()+"/pc/front/img/icon/directoryIcon.jpg?"+new Date().getTime();
	}else {
		return getWebProjectName()+"/pc/front/img/icon/otherIcon.jpg?"+new Date().getTime();
	}
}


/**
 * 设置选择的排序，并且设置url参数order和sort
 */
function setSelectedTitle(dom){
	/*c(dom);*/
	//当前存在被选择的排序标签
	if(currt_select_title!=undefined){
		//点击相同的标签
		if(dom.attr("id")==currt_select_title.attr("id")){
			if(sort=="desc"){
				dom.html(dom.html().replace(descImg,"")+ascImg);
				sort="asc";
				order = currt_select_title.attr("order");
			}else{
				dom.html(dom.html().replace(ascImg,"")+descImg);
				sort="desc";
				order = currt_select_title.attr("order");
			}
		}else{
			//点击不相同的标签
			currt_select_title.html(currt_select_title.html().substring(0,currt_select_title.html().length-1));
			dom.html(dom.html().replace(ascImg,"")+descImg);
			currt_select_title = dom;
			
			order = currt_select_title.attr("order");
			sort="desc";
			
		}
		
	}else{
		//当前不存在被选择的排序标签
		dom.html(dom.html().replace(ascImg,"")+descImg);
		currt_select_title = dom;
		sort="desc";
		order = currt_select_title.attr("order");
	}
	
	
	
}

/*
*//**
 * 双击行
 *//*
function setTrDblClickLis(){
	trs.unbind("dblclick");
	trs.dblclick(function(){
		//设置双击行的id
		dblClickedTrId = $(this).attr("id");
		//如果有正在编辑的，那么拒绝操作
		if(currtEditTrId){
			return;
		}
		//判断是否为文件夹
		if(!isDir(dblClickedTrId)){
			return;
		}
		
		
		//双击进入下一目录时，隐藏操作按钮
		hideOperationBtn();
		//设置参数
		//设置id
		dblClickedTrId = $(this).attr("id");
		c(dblClickedTrId);
	
		//添加跳转前目录的uploadhistory的id到数组
		file_parent_id_arr.push(currtUploadHistoryDirPId);
		c(file_parent_id_arr);
		c(file_parent_id_arr.length);
		//显示返回导航
		if(file_parent_id_arr.length>=1){
			showBackNav();
		}
		

		//添加信息到数组
		var filename = getFileNameByTrId(dblClickedTrId);
		file_parent_filename_arr.push(filename);
		//设置导航信息
		setBackNavItems();
		
		//设置父uploadhistory的id，以查询下一目录
		currtUploadHistoryDirPId = dblClickedTrId;
		
		//清除搜索关键字
		clearSearchKeyWord();
		//不使用关键字搜索列表；因为前面使用了clearSearchKeyWord()
		loadFilesList(undefined);
	});
}*/

/**
 * 判断某一行是否为目录
 * @param tr
 * @returns
 */
function isDir(tTrId){
	if(tTrId==undefined){
		return false;
	}
	var ext = $(getTrDomByTrId(tTrId)).attr("fileextclazz");
	if(ext=="directory"){
		return true;
	}
	return false;
}