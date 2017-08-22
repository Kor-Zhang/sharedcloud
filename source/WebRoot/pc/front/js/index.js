/*文件显示列表的div*/
/*显示列表的div*/
var index_content_right_filelist_table_div;
/*显示列表的导航栏*/
var index_content_right_nav;

var index_content_right_filelist;
var index_content_right_nav_left;
/**
 * 选择文件 的参数
 */
var file_list_tr;
var file_list_tr_click_bg = "rgb(241,242,243)";
var file_list_tr_unclick_bg;
var file_list_selectd_file  = undefined;

//文件的table
var fileTable;
/*操作按钮*/
var uploadBtn;
var newFileBtn;
var sharedBtn;
var downloadBtn;
var deleteBtn;
var renameBtn;
var moveBtn;
var cancelBtn;
//已用文件进度条
var usedProgress;

var usedSize;
var totalSize;

var fileName;
//当前正在编辑的行的id（*******锁定操作作用，此值不为空时，一般情况下无法进行下一步操作******）
var currtEditTrId;
//当前的操作renameFileName，newFile，无deleteFile
var currtOper;
//当前被点击了的trId
var clickedTrId;
//当前被双击了的trId
var dblClickedTrId;
//tr被选择时的背景
var tr_selected_bg = "rgb(239,244,248)";
/*//tr为被选择时的背景
var tr_unSelected_bg = "white";*/
//排序标题
var fillist_title_filename;
var fillist_title_filesize;
var fillist_title_uploadtime;
//是否为默认排序（即默认排序为uploadtime排序），1为是默认排序，-1不是默认排序
//加载列表的两个方法loadFileList和loadExtFileList将调用该参数
var isDefaultSort = -1;
var currt_select_title;
//排序的sort参数
var sort = "desc";
var order = "uploadtime";
//升序降序图标
/*▴▾*/
//记录当前目录的uploadhistory的id
var currtUploadHistoryDirPId = undefined;
var descImg = "▾";
var ascImg = "▴";
//返回上一级按钮
var backBtn;
var backBtnDiv;
var file_positionDiv;
//储存上级的uploadhistory的id
var file_parent_id_arr = new Array();
//储存上级的uploadhistory的filername
var file_parent_filename_arr = new Array();

var upFileItems;
//操作等待提示
var operFileLoading;
//操作等待默认提示文字
var operFileLoadingMsg;
//执行hide操作后，操作提示消失事件
var operFileLoadingWiatTime = 1800;
var operFileLoadingTimer;

//上传组件
var uploadCom;
var uploader;
var uploadComForm;
//上传组件的关闭按钮
var uploadCom_closeBtn;

//记录所有操作按钮的状态：1代表激活，-1代表冻结
var allBtnStatus = 1;

//分类查找文件;除totalExtBtn采用loadFileList查询，其他的全部采用loadExtFileList查询
var totalExtBtn;
var picExtBtn;
var txtExtBtn;
var videoExtBtn;
var torExtBtn;
var musicExtBtn;
var zipExtBtn;
var otherExtBtn;
//记录下查询的分类参数
var currtExts = "";
//当前被选择的分类按钮
var currtSelectExtBtn;
var extBtnDefaultBg;
var extBtnDefaultColor;
var extBtnSelectBg = "rgb(227,232,236)";
var extBtnSelectColor= "rgb(59,140,255)";
//全部的分类按钮
var allExtBtn;

//记录修改名称前的name
var currtEditTrOldFileName;
//文件搜索框
var searchYourFileInput;
//暂存搜索关键字
var searchKeyWord;
//两大视图
var mySharedFilesListView;
var filesListView;
var currtIndexView;
var views;
//我的分享按钮
var mySharedBtn;
//所有标签按钮
var allLableBtn;
//当前页
var currtPage = 1;
//下拉加载计时器
var scrollLoadDataTimer;
var scrollLoadDataTablePreBottom;
$(function($){
	
	initIndexDoms();
	setIndexUI();
	setIndexLis();
	initIndexData();
	
	
	
});
/**
 * 加载上传组建
 */
function loadUploadCom(parameters){
	uploader = uploader.pluploadQueue({
		// General settings
		runtimes : 'gears,flash,silverlight,browserplus,html5,html4',
		url : getWebProjectName()+'/pc/front/fUploadHistoryAction!uploadFiles.action',
		multipart_params:parameters,//携带参数
		max_file_size : '1024mb',//最大支持1g上传
		unique_names : true,
		multiple_queues : true,
		chunk_size: '0',//不使用分片上传
		// Specify what files to browse for
		filters : [
			{title : "*, *.*", extensions : "*,*"}
			/*{title : "xls, xlsx文档", extensions : "xls,xlsx"}*/
		],

		// Flash settings
		flash_swf_url : getWebProjectName()+'/pc/plug-in/plupload/js/plupload.flash.swf',
		// Silverlight settings
		silverlight_xap_url : getWebProjectName()+'/pc/plug-in/plupload/js/plupload.silverlight.xap'
	});
	/*uploadComForm.submit(function(e) {
        var uploader = $('#uploader').pluploadQueue();
        if (uploader.files.length > 0) {
            // When all files are uploaded submit form
            uploader.bind('StateChanged', function() {
                if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
                    $('form')[0].submit();
                }
            });
            uploader.start();
        } else {
			alert('请先上传数据文件.');
		}
        return false;
	});*/
}
/**
 * 初始化数据
 */
function initIndexData(){
	
	//设置默认显示视图
	toIndexView(filesListView,function(){
		//加载文件列表，排序为按照上传日期
		loadFilesList(undefined);
		//加载上传组建
		loadUploadCom();
		//获取session中user的信息；在回调函数中初始化储存进度条
		loadSavedSizeProgress();
	});
	
}

/**
 * 通过现在目录的id（uploadhistory#id）加载文件列表，携带sort和order参数；<br/>
 * 自动冻结操作按钮机制：即在调用者没有冻结按钮而进行了获取列表（loadFilesList）操作，那么loadFilesList将自动冻结操作按钮<br/>
 * @param sortDom：选择排序的dom；如果为undefined，那么默认为uploadtime排序
 * @param callFun：列表加载完成后的回调函数
 * @param append：采用追加数据的形式加载数据
 * @param scrollTop：添加数据后，返回顶部
 */
function loadFilesList(sortDom,callFun,append,scrollTop){
	//显示等待提示
	showOperFileLoading("正在获取列表");
	//记录是否能在加载列表完成后接触按钮冻结
	var canEnableAllBtn = -1;
	//外部未对按钮冻结
	if(allBtnStatus==1){
		disableAllBtn();
		canEnableAllBtn = 1;
	}
	//如果sortDom==keep，保持原来排序不变
	if(sortDom!="keep"){
		//默认排序
		if(sortDom==undefined){
			setSelectedTitle(fillist_title_filename);
			setSelectedTitle(fillist_title_uploadtime);
		}else{
			setSelectedTitle(sortDom);
		}
	}
	//需要传递的参数
	var parameters = new Object();
	
	//获取参数：exts；如果当前不是选择全部文件的按钮，说明应该是更具类型加载文件列表
	if(isExtListStatus()){
		//文件类型参数：
		parameters["exts"] = currtExts;
		//重置参数：当前的目录id
		currtUploadHistoryDirPId = undefined;
	}else{
		//文件类型参数：
		parameters["exts"] = "null";
	}
	//获取参数：当前需要查询的目录的父uploadhistory的id
	if(currtUploadHistoryDirPId!=undefined&&currtUploadHistoryDirPId!=""){
		parameters["pId"] = currtUploadHistoryDirPId;
		
		//显示返回上一级等按钮
		showBackNav();
	}else{
		//清空显示的位置信息
		var ins = file_positionDiv.find(".upFileItems");
		for(var i =1;i<ins.length;i++){
			ins[i].remove();
		}
		
		//不显示返回上一级等按钮
		hideBackNav();
	}
	//参数：searchKeyWord
	parameters["searchKeyWord"] = searchKeyWord;
	
	
	
	
	parameters["order"] = order;
	parameters["sort"] = sort;
	
	//分页参数
	parameters["page"] = currtPage;
	/*a(currtPage);*/
	//加载文件一级列表
	zkAjaxData("/pc/front/fUploadHistoryAction!getFiles.action",parameters, function(data){

		//显示等待提示
		showOperFileLoading(data.msg);
		//加载成功
		if(data.success){
			if(append==false||append==undefined){
				//清除table（除标题外）
				for(var i = 0;i<file_list_tr.length;i++){
					$(file_list_tr[i]).remove();
				}
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

			c(files.length);
			c(currtPage);
			//增加currtPage
			currtPage++;
		}else{
			/*//显示错误详细
			showMsgWin(undefined,data.msg, 1);*/
		}
		//判断是否能够激活按钮
		if(1 == canEnableAllBtn){
			enableAllBtn();
		}
		//隐藏提示
		hideOperFileLoading(100);
		if(scrollTop!=undefined&&scrollTop==true){
			//产生动画效果；滑动滚动条到最上部；
			scrollTo(index_content_right_filelist_table_div,$("html"),800);
		}
		//回调函数
		if(callFun){
			callFun();
		}
	}, true);
}
/**
 * 获取格式化的文件大小<br/>
 * 比如：1024B==>1KB；
 * @param fileSize：单位为B
 *//*
function getFormatFileSize(fileSize){
	//设置文件显示大小
	if(fileSize==0||fileSize=="0"){
		return "0B";
	}
	//目录
	if(fileSize==-1||fileSize=="-1"){
		return "-";
	}
	var fileSizeSize = ["B","KB","MB","G","T"];
	var fileSizeNumber = [1,1024,1024*1024,1024*1024*1024,1024*1024*1024*1024];
	for ( var i =1;i<fileSizeNumber.length;i++) {
		var dealN =  parseFloat(fileSize/fileSizeNumber[i]).toFixed(0);;
		if(dealN<=1){
			dealN =  parseFloat(fileSize/fileSizeNumber[i-1]).toFixed(0);;
			fileSize = dealN+""+fileSizeSize[i-1];
			return fileSize;
		}
	}
}*/
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
function addFileNode(fileId,fileName,fileSize,uploadTime,ext,enableEditFileName,first){
	//生成节点，记录该行后缀类型（fileExtClazz，如txt）
	var tr = $("<tr fileextclazz='"+ext+"' id='"+fileId+"' class='file_list_tr'></tr>");
	
	var td1 = $("<td class='directory_Name_Icon_td'>"); 
	var td1_div1 = $("<div class='directoryIcon_div'></div>");
	var td1_div1_img = $("<img class='directoryIcon' src='"+getFileImgSrc(ext)+"'/>");
	td1_div1.append(td1_div1_img);
	td1.append(td1_div1);
	
	var td1_div2 = $("<div class='directoryName_div'></div>");
	var td1_div2_input = $("<input class='fileName' type='text' value='"+fileName+"' placeholder='请输入文件名'/>");
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
		fileTable.find("tr").eq(0).after(tr);
	}else{
		//添加一行到fileTable的的最后一行
		fileTable.find("tr:last").after(tr);
	}
	
	
	//设置是否开启编辑
	if(enableEditFileName==false){
		disEditFileName(td1_div2_input);
	}else{
		editFileName(td1_div2_input);
	}
	//重新加载dom
	file_list_tr = $(".file_list_tr");
	directory_Name_Icon_td = $(".directory_Name_Icon_td");
	directoryIcon_div = $(".directoryIcon_div");
	directoryIcon = $(".directoryIcon");
	directoryIcon_div = $(".directoryIcon_div");
	directoryName_div = $(".directoryName_div");
	fileName = $(".fileName");
	//重新加载监听器
	setIndexLis();
	//设置对filenam的监听,主要是对焦点脱离事件的监控（文件名为空不能脱离焦点）
	setFileNameDomEndEditLis(td1_div2_input);
	return tr;
}
/**
 * 初始化Indexd的dom
 */
function initIndexDoms(){
	searchYourFileInput  = $("#searchYourFileInput").html("");
	
	totalExtBtn = $("#totalExtBtn");
	picExtBtn = $("#picExtBtn");
	txtExtBtn = $("#txtExtBtn");
	videoExtBtn = $("#videoExtBtn");
	torExtBtn = $("#torExtBtn");
	musicExtBtn = $("#musicExtBtn");
	zipExtBtn = $("#zipExtBtn");
	otherExtBtn = $("#otherExtBtn");
	extBtnDefaultBg = totalExtBtn.css("background");
	extBtnDefaultColor = totalExtBtn.css("color");;
	
	
	uploadCom = $("#uploadCom");
	uploader = $("#uploader");
	uploadComForm = $('#uploadComForm');
	uploadCom_closeBtn = $("#uploadCom_closeBtn");
	
	index_content_right_filelist_table_div = $('#index_content_right_filelist_table_div');
	fileTable = $("#index_content_right_filelist_table");
	index_content_right_filelist = $('#index_content_right_filelist');
	index_content_right_nav = $('#index_content_right_nav');
	file_list_tr = $('.file_list_tr');
	file_list_tr_unclick_bg = file_list_tr.css('background');
	index_content_right_nav_left = $('#index_content_right_nav_left');
	
	uploadBtn = $('#uploadBtn');
	newFileBtn = $('#newFileBtn');
	enabledBtn(newFileBtn);
	sharedBtn = $('#sharedBtn');;
	downloadBtn = $('#downloadBtn');;
	deleteBtn = $('#deleteBtn');;
	renameBtn = $('#renameBtn');;
	enabledBtn(renameBtn);
	moveBtn = $('#moveBtn');;
	cancelBtn = $('#cancelBtn');;
	
	
	operFileLoading = $("#operFileLoading").hide();
	operFileLoadingMsg = operFileLoading.find("#operFileLoadingMsg").html();
	
	usedSize = $("#usedSize");
	totalSize = $("#totalSize");
	usedProgress = $("#usedProgress");
	fileName = $(".fileName");
	
	backBtnDiv = $("#backBtnDiv");
	file_positionDiv = $("#file_positionDiv");
	
	backBtn = $("#backBtn");
	upFileItems = $(".upFileItems");
	fillist_title_filename = $("#fillist_title_filename");
	fillist_title_filesize = $("#fillist_title_filesize");
	fillist_title_uploadtime = $("#fillist_title_uploadtime");
	

	mySharedFilesListView = $("#mySharedFilesListView");
	filesListView = $("#filesListView");
	
	views = $("#index_content_right>div");
	
	mySharedBtn = $("#mySharedBtn");
	
	allLableBtn = $(".labs>a");
	
	allExtBtn = $(".extBtns");
	
	
}
/**
 * 清除搜索的关键字<br/>
 * 即：清空输入框；<br/>
 * 清空变量searchKeyWord；<br/>
 */
function clearSearchKeyWord(){
	searchYourFileInput.val("");
	searchKeyWord = undefined;
}
/**
 * 显示视图；<br/>
 * 如果当前的view已经显示，那么进行操作；
 */
function toIndexView(view,cF){
	//显示新视图所需时间
	var t = 500;
	//是否延时执行回调函数
	var delay = false;
	/*
	 * 如果当前的view已经显示，那么不进行操作
	 */
	if(currtIndexView){
		if(currtIndexView.attr("id")!=view.attr("id")){
			delay = true;
			showView(views,view,t);
		}
	}else{
		delay = true;
		showView(views,view,t);
	}
	//执行回调
	if(cF&&true==delay){
		try{
			setTimeout(function(){
				cF();
			}, t);
		}catch(e){
			c(e);
		}
	}else if(cF&&false==delay){
		cF();
	}
}
/**
 * 显示视图
 * @param views：需要隐藏的视图
 * @param view：需要显示的视图
 * @returns {Number}：返回显示新视图所需时间
 */
function showView(views,view,t){
	//显示
	views.fadeOut(t);
	view.fadeIn(t);
	currtIndexView = view;
}
/**
 * 判断现在是否在 后缀分类 查询查询状态
 * @returns：true正在分类状态，false不在分类状态
 */
function isExtListStatus(){
	//比较当前正激活的分类按钮，是否为“全部文件”
	return currtSelectExtBtn!=undefined&&currtSelectExtBtn.attr("id")!=totalExtBtn.attr("id");
}

/**
 * 设置文件分类对象为选择状态（修改按钮背景和文字样式）；<br/>
 * 设置currtSelectExtBtn的值；<br/>
 * 设置currtExts；<br/>
 * @param btn
 */
function selectExtBtn(btn){
	
	chgBtnBgAndColor(allLableBtn,btn);
	//设置当前选择的按钮
	currtSelectExtBtn = btn;
	//设置参数
	currtExts = currtSelectExtBtn.attr("exts");
	/*a(currtExts);*/
}
/**
 * 只是单单纯纯的修改按钮背景和文字样式
 */
function chgBtnBgAndColor(toOldStyleBtn,newStyleBtn){
	toOldStyleBtn.css("background",extBtnDefaultBg);
	toOldStyleBtn.css("color",extBtnDefaultColor);
	newStyleBtn.css("background",extBtnSelectBg);
	newStyleBtn.css("color",extBtnSelectColor);
}
/**
 * 设置index的ui
 */
function setIndexUI(){
	/*设置显示文件列表的div的高度*/
	var navHeight = nav.height();
	var fileTableNavHeight = index_content_right_nav.height();
	index_content_right_filelist_table_div.height(html.height()-navHeight-fileTableNavHeight-100);
	
	//默认选择“全部文件”按钮，必选在allLableBtn后
	selectExtBtn(totalExtBtn);
	
	
}
/**
 * 设置储存空间显示进度条
 * @returns
 */
function setSavedSizeProgress(usedSizeNum,totalSizeNum){
	/*//设置文件容量进度条
	var usedSizeNum = parseFloat(usedSize.html()).toFixed(0);;
	var totalSizeNum = parseFloat(totalSize.html()).toFixed(0);;;*/
	//重设精度
	/*usedSize.html(parseFloat(usedSizeNum/1024/1024/1024).toFixed(0)); 
	totalSize.html(parseFloat(totalSizeNum/1024/1024/1024).toFixed(0)); */
	/*c(usedSizeNum);
	c(totalSizeNum);*/
	usedSize.html(getFormatFileSize(usedSizeNum)); 
	totalSize.html(getFormatFileSize(totalSizeNum)); 
	/*c(usedSizeNum);
	c(totalSizeNum);
	c(usedSizeNum/totalSizeNum*100);*/
	var scale = usedSizeNum/totalSizeNum*100;
	if(scale>100){
		scale = 100;
	}
	usedProgress.width(scale+"%");
}
/**
 * 连接数据库加载储存进度条
 */
function loadSavedSizeProgress(){
	//获取session中user的信息；在回调函数中初始化储存进度条
	getSessionUser(function(data){
		if(data.online){
			//初始化储存进度条
			var usedSizeNum = parseFloat(data.obj.savedfilesize).toFixed(0);;
			var totalSizeNum = parseFloat(data.obj.totalfilesize).toFixed(0);
			setSavedSizeProgress(usedSizeNum,totalSizeNum);
		}
	});
}

/**
 * 操作file tr后需要重新加载的方法
 *//*
function reloadAfterOperFile(){
	initIndexDoms();
	setIndexLis();
}*/
/**
 * 监听
 */
function setIndexLis(){
	//设置行点击事件
	setTrClickLis();
	setNewFileBtnClickLis();
	setDownloadBtnClickLis();
	setRenameBtnClickLis();
	setDeleteBtnClickLis();
	setSortLis();
	setTrDblClickLis();
	setBackBtnClickLis();
	//设置点击上传按钮的监听事件
	setUploadBtnClickLis();
	//设置点击关闭上传组件的监听事件
	setUploadComCloseBtnClickLis();
	//设置点击分类查询的触发事件
	setFileExtBtnClickLis();
	//点击取消按钮的触发事件；
	setCancelOperationBtnClickLis();
	//点击文件搜索按钮触发事件；
	setSearchInputKeyupLis();
	//设置移动按钮的点击触发事件
	setMoveBtnClickLis();
	//设置分享按钮的点击触发事件
	setSharedBtnClickLis();
	//设置我的分享按钮的点击触发事件
	setMySharedBtnClickLis();
	//设置文件列表滚动监听事件
	setFilesListScrollList();
}
/**
 * 设置文件列表滚动监听事件
 */
function setFilesListScrollList(){
	index_content_right_filelist_table_div.unbind("scroll");
	index_content_right_filelist_table_div.scroll(function(){
		
		
    	var divTop = $(this).offset().top;
        var divBottom = $(this).height()+divTop; 
        var tableTop= fileTable.offset().top;
        var tableBottom= fileTable.height()+tableTop;
        /*c(tableTop);
        c(tableBottom);*/
        //加载新数据
        if(tableBottom<=divBottom+3){
        	//延时加载
    		if(scrollLoadDataTimer){
    			clearTimeout(scrollLoadDataTimer);
    			
    		}
    		//往下拉猜加载页面
    		if(scrollLoadDataTablePreBottom>tableBottom){
    			//防止频繁操作
        		scrollLoadDataTimer = setTimeout(function(){
        			/*currtPage++;*/
                	loadFilesList("keep", function(){
                		
                	},true,false);
        	    }, 300);
    		}
    		
        }
        scrollLoadDataTablePreBottom = tableBottom;
    });
}
/**
 * 设置我的分享按钮的点击触发事件；<br/>
 * 注意：此处调用了mySharedFileList.js方法
 */
function setMySharedBtnClickLis(){
	mySharedBtn.unbind("click");
	mySharedBtn.click(function(){
		//如果存在正在编辑的行，那么拒绝操作
		if(currtEditTrId){
			return;
		}
		//单纯的设置背景
		chgBtnBgAndColor(allLableBtn,$(this));
		//切换视图
		toIndexView(mySharedFilesListView,function(){
			//加载视图
			mySharedFilesList.loadSharedFilesListView();
		});
	});
}
/**
 * 设置分享按钮的点击触发事件
 */
function setSharedBtnClickLis(){
	sharedBtn.unbind("click");
	sharedBtn.click(function(){
		
		
		//设置参数
		/*showShareingWin(sharedFileOrDirectoryId,sharedFileOrDirectoryName,finishShareFileCallFun){*/
		var sharedFileOrDirectoryName = getFileNameByTrId(clickedTrId);
		var sharedFileOrDirectoryId = clickedTrId;
		//显示分享框
		showShareingWin(sharedFileOrDirectoryId,sharedFileOrDirectoryName,function(isPublicLink,selectedDirectoryId,afterLinkCreateHandler){
			/*c(isPublicLink);
			c(selectedDirectoryId);*/
			//显示操作等待信息
			/*showOperFileLoading(undefined);*/
			var parameters = new Object();
			parameters["uploadHistoryId"] = selectedDirectoryId;
			parameters["ispublic"] = isPublicLink;
			//加载文件一级列表
			zkAjaxData("/pc/front/fSharedfilesAction!createShareingLink.action",parameters, function(data){
				
				/*调用createShareingwin的回调方法*/
				//添加参数
				data['ispublic'] = isPublicLink;
				//供分享界面回调
				afterLinkCreateHandler(data);
				//此处非异步
			}, false);
		});
	});
}
/**
 * 设置移动按钮的点击触发事件
 */
function setMoveBtnClickLis(){
	moveBtn.unbind("click");
	moveBtn.click(function(){
		//显示移动框
		showCopyMoveWin("移动",clickedTrId,function(movedFileOrDirectoryId,selectedDirectoryId){
			/*c(movedFileOrDirectoryId);c(selectedDirectoryId);*/
			//显示操作等待信息
			showOperFileLoading("正在移动");
			var parameters = new Object();
			parameters["pId"] = selectedDirectoryId;
			parameters["id"] = movedFileOrDirectoryId;
			//加载文件一级列表
			zkAjaxData("/pc/front/fUploadHistoryAction!moveDirectoryOrFile.action",parameters, function(data){
				/*//显示错误详细
				showMsgWin(undefined,data.msg, 1);*/

				//显示操作等待信息
				showOperFileLoading(data.msg);
				//显示n秒后隐藏
				hideOperFileLoading(operFileLoadingWiatTime);
				//移动成功
				if(data.success){
					/*loadFilesList(undefined, function(){
						
					});*/
					//移动完成后，去除该列
					if(!isExtListStatus()&&movedFileOrDirectoryId!=selectedDirectoryId){
						getTrDomByTrId(movedFileOrDirectoryId).remove();
					}
				}else{
					
				}
				/*//结束限制
				currtEditTrId = undefined;*/
			}, true);
		});
	});
}
/**
 * 点击文件搜索按钮触发事件；
 */
function setSearchInputKeyupLis(){
	searchYourFileInput.unbind("keyup");
	searchYourFileInput.keyup(function(e){
		if(e.keyCode==13){
			searchKeyWord = searchYourFileInput.val();
			loadFilesList(undefined, function(){
				
			});
		}
	});
}
/**
 * 点击取消按钮的触发事件；<br/>
 * 取消重名<br/>
 * 取消新建<br/>
 */
function setCancelOperationBtnClickLis(){
	cancelBtn.unbind("click");
	cancelBtn.click(function(){
		
		//判断当前操作
		if(currtOper=="newFile"){
			var tr = getTrDomByTrId(currtEditTrId);
			tr.remove();
		}
		if(currtOper=="renameFileName"){
			var filenamedom = getFileNameDomByTrId(currtEditTrId);
			//恢复旧值
			setFileNameByFileNameDom(filenamedom, currtEditTrOldFileName);
			//禁止编辑
			disEditFileName(filenamedom);
		}
		//结束占用；//将不会执行焦点脱离的触发事件
		endOper();

		//隐藏取消按钮
		cancelBtn.hide();
		
		//激活按钮
		enableAllBtn();
	});
}

/**
 * 设置点击分类查询的触发事件
 */
function setFileExtBtnClickLis(){
	//这里了设置“全部文件”的监听事件，没有最后一个"分享"按钮的触发事件
	for(var i = 1;i<allExtBtn.length;i++){
		var btn = $(allExtBtn[i]);
		btn.unbind("click");
		btn.click(function(){
			//如果有在编辑的行，那么拒绝
			if(currtEditTrId){
				return;
			}
			var btn = $(this);
			// 设置当前点击的按钮样式，设置ext参数
			selectExtBtn(btn);
			//隐藏操作按钮
			hideOperationBtn();
			//隐藏新建按钮
			newFileBtn.hide();
			//结束操作
			endOper();
			//清空关键字搜索
			clearSearchKeyWord();
			//显示视图
			toIndexView(filesListView,function(){
				currtPage = 1;
				//设置排序；加载列表
				loadFilesList(undefined);
			});
			
			
			
		});
	}
	//重置设置“全部文件”的监听事件
	totalExtBtn.unbind("click");
	totalExtBtn.click(function(){
		//如果有在编辑的行，那么拒绝
		if(currtEditTrId){
			return;
		}
		

		//储存上级的uploadhistory的id；重置数组；
		file_parent_id_arr = new Array();
		//储存上级的uploadhistory的filername；重置数组；
		file_parent_filename_arr = new Array();
		
		var btn = $(this);
		//隐藏操作按钮
		hideOperationBtn();

		//显示新建按钮
		newFileBtn.show();
		//结束操作
		endOper();
		// 设置当前点击的按钮样式，设置ext参数
		selectExtBtn(btn);
		//不采用通过ext查询的方式，使用loadFileList的方式，查询目录结构和文件
		//初始化参数
		//初始化目录为根目录
		currtUploadHistoryDirPId = undefined;
		//清空关键字搜索
		clearSearchKeyWord();
		//显示视图
		toIndexView(filesListView,function(){
			currtPage = 1;
			//加载列表
			loadFilesList(undefined);
			//获取session中user的信息；在回调函数中初始化储存进度条
			loadSavedSizeProgress();
		});
		
	});
	
	/*allExtBtn.each(function(){
		$(this).click(function(){
			var btn = $(this);
			c(btn);
			// 设置当前点击的按钮样式，设置ext参数
			selectExtBtn(btn);
		});
	});*/
}
/**
 * 设置点击上传按钮的监听事件
 */
function setUploadBtnClickLis(){
	uploadBtn.unbind("click");
	uploadBtn.click(function(){
		if(currtEditTrId){
			return ;
		}
		//加载上传组建，携带参数：当前目录uploadhistory的id
		var parameters = new Object();
		parameters["pId"] = currtUploadHistoryDirPId;
		c(sessionUserid);
		//sessionUserid引用自index.jsp页面的js
		
		parameters["userid"] = sessionUserid;
		//加载
		loadUploadCom(parameters);
		//显示
		showUploadCom();
	});
}
/**
 * 设置点击关闭上传组件的监听事件
 */
function setUploadComCloseBtnClickLis(){

	uploadCom_closeBtn.unbind("click");
	uploadCom_closeBtn.click(function(){
		hideUploadCom();
	});
}
/**
 * 隐藏上传组件
 * 并且转到显示文件夹和文件的列表
 */
function hideUploadCom(){
	//刷新列表，采取默认排序
	setTimeout(function(){

		currtPage =1;
		loadFilesList(undefined);
	}, 500);
	//获取session中user的信息；在回调函数中初始化储存进度条
	loadSavedSizeProgress();
	
	
	//设置为显示目录和文件
	selectExtBtn(totalExtBtn);
	
	
	uploadCom.hide();
	uploadCom.css("z-index","-999");
	
	
}
/**
 * 显示上传组件
 */
function showUploadCom(){
	uploadCom.show();
	uploadCom.css("z-index","1");
}
/**
 * 点击返回上一层
 */
function setBackBtnClickLis(){
	backBtn.unbind("click");
	backBtn.click(function(){
		//如果有正在编辑的，那么拒绝操作
		if(currtEditTrId){
			return;
		}

		//冻结操作按钮
		disableAllBtn();
		//隐藏操作按钮
		hideOperationBtn();
		//获取上一级目录id
		var uploadHistoryId = file_parent_id_arr.pop(currtUploadHistoryDirPId);
		currtUploadHistoryDirPId = uploadHistoryId;
		/*if(file_parent_id_arr.length<=0){
			hideBackNav();
		}*/
		
		file_parent_filename_arr.pop();
		//设置导航信息
		setBackNavItems();
		//设置sort和order，恢复默认排序
		currtPage =1;
		loadFilesList(undefined,function(){
			//ajax执行完成后，激活按钮
			enableAllBtn();
		});
	});
}
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
/**
 * 双击行
 */
function setTrDblClickLis(){
	file_list_tr.unbind("dblclick");
	file_list_tr.dblclick(function(){
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
		/*c(dblClickedTrId);*/
	
		//添加跳转前目录的uploadhistory的id到数组
		file_parent_id_arr.push(currtUploadHistoryDirPId);
		c(file_parent_id_arr);
		c(file_parent_id_arr.length);
		/*//显示返回导航
		if(file_parent_id_arr.length>=1){
			showBackNav();
		}*/
		

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
		currtPage =1;
		loadFilesList(undefined);
	});
}
/**
 * 通过事先对file_parent_filename_arr的push和pop操作，对导航文字进行刷新
 */
function setBackNavItems(){
	//清空显示的位置信息
	var ins = file_positionDiv.find(".upFileItems");
	for(var i =1;i<ins.length;i++){
		$(ins[i]).remove();
	}
	for(var i = 0;i<file_parent_filename_arr.length;i++){
		var filename = file_parent_filename_arr[i];
		//添加导航文字显示
		var str = ">"+filename;
		var input = "<input type='button' class='upFileItems' parentId='"+dblClickedTrId+"' value='"+str+"'/>";
		file_positionDiv.append(input);
		//重新加载信息
		//reloadAfterOperFile();
		upFileItems = $(".upFileItems");
	}
	
}
/**
 * 排序触发事件
 * loadListFun：指定排序出发后执行的获取列表方法
 */
function setSortLis(loadListFun){
	/*▴▾*/
	fillist_title_filename.unbind("click");
	fillist_title_filename.click(function(){
		//如果当前有在编辑的行
		if(currtEditTrId){
			return;
		}
		//不是默认排序
		isDefaultSort = -1;
		currtPage=1;
		loadFilesList(fillist_title_filename);
	});
	fillist_title_filesize.unbind("click");
	fillist_title_filesize.click(function(){
		//如果当前有在编辑的行
		if(currtEditTrId){
			return;
		}
		//不是默认排序
		isDefaultSort = -1;
		currtPage=1;
		loadFilesList(fillist_title_filesize);
	});
	fillist_title_uploadtime.unbind("click");
	fillist_title_uploadtime.click(function(){
		//如果当前有在编辑的行
		if(currtEditTrId){
			return;
		}
		//不是默认排序
		isDefaultSort = 1;
		currtPage=1;
		loadFilesList(fillist_title_uploadtime);
	});
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
/**
 * 设置行点击事件
 */
function setTrClickLis(){
	file_list_tr.unbind("click");
	/*点击一个文件，改变其背景，显示，隐藏按钮*/
	file_list_tr.click(function(){
		//记录下当前click了的tr的id
		clickedTrId = $(this).attr("id");
		
		//如果有列处于编辑filename状态，拒绝其他操作
		if(currtEditTrId){
			return;
		}
		//选择tr，改变其背景
		var tr = getTrDomByTrId(clickedTrId);
		/*c(tr);*/
		select_tr(tr);
		//有选择文件
		if(file_list_selectd_file){
			
			file_list_selectd_file.css("background",file_list_tr_unclick_bg);
			hideOperationBtn();
			//如果没有重复选择
			if(file_list_selectd_file!=$(this)){
				file_list_selectd_file = $(this);
				file_list_selectd_file.css("background",file_list_tr_click_bg);
				showOperationBtn();
			}
		}else{
			//没有选择
			file_list_selectd_file = $(this);
			file_list_selectd_file.css("background",file_list_tr_click_bg);
			showOperationBtn();
		}
		
		
	});
}
/**
 * 设置新建文件夹按钮的点击事件
 */
function setNewFileBtnClickLis(){
	newFileBtn.unbind("click");
	//新建文件夹
	newFileBtn.click(function(){
		//如果当前存在正在编辑的文件名，那么拒绝执行
		if(currtEditTrId){
			/*var fileN = getFileNameByTrId(currtEditTrId);
			disEditFileName(fileN);*/
			return;
		}
		//处于分类查询，拒绝新建操作
		if(isExtListStatus()){
			return;
		}

		//显示取消按钮
		cancelBtn.show();
		
		/*//冻结新建按钮
		disabledBtn(newFileBtn);*/
		//冻结所有按钮
		disableAllBtn();
		//隐藏操作按钮
		hideOperationBtn();
		
		currtOper = "newFile";
		/*<tr class="file_list_tr">
			<td class="directory_Name_Icon_td">
				<div class="directoryIcon_div">
					<img class="directoryIcon" src='<c:url value='/pc/front/img/icon/directoryIcon.jpg'></c:url>'/>
				</div>
				<div class="directoryName_div">
					<input readonly="true" type="text" value="fallout5"/>
				</div>
				
			</td>
			<td>-</td>
			<td>1995-12-17 12:12:31</td>
		</tr>*/
		
		currtEditTrId = uuid();
		var tr;
		//获取服务时间
		//显示操作等待信息
		showOperFileLoading("等待获取服务器信息");
		zkAjaxData("/pc/front/fUploadHistoryAction!getServerNowTime.action",undefined, function(data){
			
			/*//显示错误详细
			showMsgWin(undefined,data.msg, 1);*/

			//显示操作等待信息
			showOperFileLoading(data.msg);
			//显示n秒后隐藏
			hideOperFileLoading(operFileLoadingWiatTime);
			var now = new Date(); 
			//加载成功
			if(data.success){
				now = data.obj;
			}else{
				
			}
			//添加节点（添加到第一行）
			tr = addFileNode(currtEditTrId,"",-1,now,"directory",true,true);
		}, false);
		
		
		//选择tr
		select_tr(tr);
		
	});
}


/**
 * 下载文件名的点击事件
 */
function setDownloadBtnClickLis(){
		//取消以前的事件
	downloadBtn.unbind("click");
	downloadBtn.click(function(){
		
		//如果有列处于编辑filename状态，拒绝其他操作
		if(currtEditTrId){
			return;
		}
		;
		//如果为目录，拒绝下载
		if(getTrDomByTrId(clickedTrId).attr("fileextclazz")=="directory"){

			showMsgWin(undefined,"不能下载文件夹",0);
			return;
		}
		//设置下载路径
		self.location= getWebProjectName()+"/pc/front/fUploadHistoryAction!downloadFile.action?id="+clickedTrId;
		/*//冻结所有按钮
		disableAllBtn();
		//隐藏操作按钮
		hideOperationBtn();*/
		//获取当前被点击了的tr为正在编辑的tr
		/*currtEditTrId = clickedTrId;*/
		//声明操作
		/*currtOper = "renameFileName";*/
		/*//获取filenameDom
		var filenameDom = getFileNameDomByTrId(currtEditTrId);
		//开启编辑
		editFileName(filenameDom);*/
		/*reloadAfterOperFile();*/
	});
}
/**
 * 修改文件名的点击事件
 */
function setRenameBtnClickLis(){
	//取消以前的事件
	renameBtn.unbind("click");
	renameBtn.click(function(){
		
		//如果有列处于编辑filename状态，拒绝其他操作
		if(currtEditTrId){
			return;
		}
		//获取当前被点击了的tr为正在编辑的tr
		currtEditTrId = clickedTrId;
		//记录旧的filnamename值
		currtEditTrOldFileName = getFileNameByTrId(currtEditTrId);
		//显示取消按钮
		cancelBtn.show();
		//冻结所有按钮
		disableAllBtn();
		//隐藏操作按钮
		hideOperationBtn();
		//声明操作
		currtOper = "renameFileName";
		//获取filenameDom
		var filenameDom = getFileNameDomByTrId(currtEditTrId);
		//开启编辑
		editFileName(filenameDom);
		/*reloadAfterOperFile();*/
	});
}
/**
 * 删除文件的点击事件
 */
function setDeleteBtnClickLis(){

	deleteBtn.unbind("click");
	deleteBtn.click(function(){
		//如果有列处于编辑filename状态，拒绝其他操作
		if(currtEditTrId){
			return;
		}
		//询问是否删除
		showConfirmWin("确认删除？",function(r){
			//如果删除，那么执行下面操作
			if(r){

				//隐藏操作按钮
				hideOperationBtn();
				//获取当前被点击了的tr为正在编辑的tr
				currtEditTrId = clickedTrId;
				//不需要声明操作，因为不需要经过焦点脱离事件的处理
				/*//声明操作
				currtOper = "deleteFile";*/
				//获取tr
				var tr = getTrDomByTrId(currtEditTrId);
				/*c(getFileNameDomByTrId(currtEditTrId));*/
				
				//连接服务器删除
				//显示操作等待信息
				showOperFileLoading();
				//获取参数
				var parameters = new Object();
				parameters["id"] = currtEditTrId;
				//删除
				zkAjaxData("/pc/front/fUploadHistoryAction!deleteFileById.action",parameters, function(data){
					
					/*//显示错误详细
					showMsgWin(undefined,data.msg, 1);*/

					//显示操作等待信息
					showOperFileLoading(data.msg);
					//显示n秒后隐藏
					hideOperFileLoading(operFileLoadingWiatTime);
					//加载成功
					if(data.success){
						/*tr.remove();*/
						currtPage = 1;
						loadFilesList("keep", function(){
							
						});
						//加载用户空间进度条
						loadSavedSizeProgress();
					}else{
					}
					//结束限制
					currtEditTrId = undefined;
				}, true);
			}
		});
		

	});
}

/**
 * 检测用户对filenam的编辑合法性
 */
function operationInterceptor(dom){
	//检测其文件名是否为空
	if(dom.val().trim()==""){
		dom.attr("placeholder","文件名不能为空");
		dom.css("border","1px solid red");
		//光标回到文本框
		dom.val("");
		dom.focus();
		throw e;
	}
}
/**
 * 检测filename的dom并且调用连接数据库方法
 */
function verifyFileNameAndExecuteLink(dom){
	try{
		//将不合法的操作（如名称为空）拦截，保持焦点不脱离文本框
		operationInterceptor(dom);
	}catch(e){
		return;
	}
	//-------以下为合法的本地操作-----
	
	//禁止编辑，可在连接服务器获取操作结果后，决定是否再次开启编辑
	disEditFileName(dom);
	//连接远程服务器
	
	//修改文件名
	if(currtOper=="renameFileName"){
		linkServerToRenameFileName();
	}
	/*//删除
	if(currtOper=="deleteFile"){
		linkServerToDeleteFile();
	}*/
	//新建
	if(currtOper=="newFile"){
		linkServerToNewFile();
	}
//	c(currtEditTrId);
//	c(currtOper);
}
/**
 * 设置input(文件名)监听器，处理input的脱离焦点事件（处理operation完成后的任务，例如远程连接）
 */
function setFileNameDomEndEditLis(dom){
	//姓名输入框失去焦点，完成编辑
	dom.unbind("blur");
	dom.blur(function(){
		/*verifyFileNameAndExecuteLink();*/
		
	});
	//姓名输入框键盘点击事件（实现回车完成编辑）
	dom.unbind("click");
	dom.keyup(function(e){
		if(e.keyCode==13){
			verifyFileNameAndExecuteLink(dom);
		}
		
	});
}
/**
 * 结束操作<br/>
 * 设置：<br/>
 * currtOper = undefined;<br/>
 * currtEditTrId = undefined;
 */
function endOper(){
	//清空标志
	//清空当前的操作限制
	currtOper = undefined;
	//清空当前的编辑行限制
	currtEditTrId = undefined;
}
/**
 * 连接服务器进行名称修改
 */
function linkServerToRenameFileName(){
	
	//冻结按钮
	disabledBtn(renameBtn);
	//显示操作等待信息
	showOperFileLoading();
	//获取参数
	var parameters = new Object();
	parameters["id"] = currtEditTrId;
	parameters["filename"] = getFileNameByTrId(currtEditTrId);
	//加载文件一级列表
	zkAjaxData("/pc/front/fUploadHistoryAction!updateFileNameById.action",parameters, function(data){
		
		/*//显示错误详细
		showMsgWin(undefined,data.msg, 1);*/

		//显示操作等待信息
		showOperFileLoading(data.msg);
		//显示n秒后隐藏
		hideOperFileLoading(operFileLoadingWiatTime);
		//加载成功
		if(data.success){
			//重新加载文件的图标和重名文件的fileextclazz
			var filename = getFileNameByTrId(currtEditTrId);
			var splits = filename.split(/[.]{1}/);
			/*c(splits);*/
			var ext = "";
			if(splits.length>=2){
				ext = splits[splits.length-1];
			}
			var tr = getTrDomByTrId(currtEditTrId);
			
			//如果是根据后缀查找的列表中进行修改后缀，那么修改过后的后缀名与之前不同则移除该行
			if(isExtListStatus()){
				/*a(ext);
				a(tr.attr("fileextclazz"));*/
				
				if(currtExts.toLowerCase().indexOf(ext.toLowerCase())==-1){
					tr.remove();
				}
			}
			
			
			//如果为目录，无论后缀为什么都不可改变其图标
			if(tr.attr("fileextclazz")!="directory"){
				tr.attr("fileextclazz",ext);
				//设置图片
				tr.find("img").attr("src",getFileImgSrc(ext));
			}
			
			
			
			/*//解冻按钮
			enabledBtn(renameBtn);*/

			//解冻所有按钮
			enableAllBtn();

			//隐藏取消按钮
			cancelBtn.hide();
			//清空标志
			endOper();
		}else{
			//修改名称出现问题，那么重启修改；可能该处的验证前台脱离焦点时已经做了
			editFileName(getFileNameDomByTrId(currtEditTrId));
		}
		
	}, true);
}

/**
 * 连接服务器进行新建文件
 */
function linkServerToNewFile(){
	//冻结新建按钮
	disabledBtn(newFileBtn);
	
	//显示操作等待信息
	showOperFileLoading();
	//获取参数
	var parameters = new Object();
	parameters["id"] = currtEditTrId;
	parameters["filename"] = getFileNameByTrId(currtEditTrId);
	parameters["pId"] = currtUploadHistoryDirPId;
	//加载文件一级列表
	zkAjaxData("/pc/front/fUploadHistoryAction!newFileById.action",parameters, function(data){
		
		/*//显示错误详细
		showMsgWin(undefined,data.msg, 1);*/
		/*重新加载文件列表*/
		/*setSelectedTitle(fillist_title_filename);
		setSelectedTitle(fillist_title_uploadtime);
		loadFilesList();*/
		
		//显示操作等待信息（会与上面重新加载产生冲突）
		showOperFileLoading(data.msg);
		//显示n秒后隐藏
		hideOperFileLoading(operFileLoadingWiatTime);
		
		//加载成功
		if(data.success){
			//设置排序为按上传时间降序，将影响sort和order参数
			currtPage =1;
			loadFilesList(undefined,function(){
				//列表加载完成后，激活按钮
				enableAllBtn();
				//隐藏取消按钮
				cancelBtn.hide();
				//解除限制
				endOper();
			});
			
		}else{
			editFileName(getFileNameDomByTrId(currtEditTrId));
		}
		
	}, true);
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
	return fileTable.find("#"+trId);
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
 * 根据filename的dom节点设置filename
 * @param dom
 */
function setFileNameByFileNameDom(dom,val){
	dom.html(val);
	dom.val(val);
	return dom;
}
/**
 * 可编辑filename
 * @param dom
 */
function editFileName(dom){

	/*//替换节点类型为input
	dom = dom.replaceWith();
	dom.attr("readonly","false");
	//刷新dom
	reloadAfterOperFile();

	c(dom);
	dom.removeAttr("readonly");
	dom.css("border","1px solid rgb(40,140,255)");
	//获取焦点，移动光标
	var t = getFileNameByFileNameDom(dom); 
	a(getFileNameByFileNameDom(dom)); 
	setFileNameByFileNameDom(dom,t);
	dom.focus().val(t);*/
	var newDom = $("<input class="+dom.attr('class')+" value='"+getFileNameByFileNameDom(dom)+"'/>");
	dom = dom.after(newDom);
	dom.remove();

//	//刷新dom
//	initIndexDoms();
//	setIndexUI();
//	setIndexLis();
	//增加焦点脱离的监听时间
	setFileNameDomEndEditLis(newDom);
	//设置样式
	newDom.css("border","1px solid rgb(40,140,255)");
	//获取焦点，移动光标
	var t = getFileNameByFileNameDom(newDom); 
	newDom.val("");
	newDom.focus().val(t);
}
/**
 * 不可编辑filename
 * @param dom
 */
function disEditFileName(dom){
	//替换节点类型为label
	var newDom = $('<label class='+dom.attr('class')+'>'+getFileNameByFileNameDom(dom)+'</label>');
	dom = dom.after(newDom);
	dom.remove();

	newDom.css("border","0px solid rgb(40,140,255)");
}
/**
 * 设置位置导航的名称
 *//*
function setBackNavPositionName(){
	
}
*/

/**
 * 隐藏返回导航
 */
function hideBackNav(){
	backBtnDiv.hide();
	file_positionDiv.hide();
}
/**
 * 显示返回导航
 */
function showBackNav(){

	backBtnDiv.show();
	file_positionDiv.show();
}

/**
 * 选择某一行，改变其背景
 * @param tr
 */
function select_tr(tr){
	/*if(currtEditTrId){
		return;
	}*/
	//改变其背景
	fileTable.find("tr").css("background-color", ""); 
	tr.css("background-color",tr_selected_bg);
}
/**
 * 显示操作等待提示
 * @param c
 */
function showOperFileLoading(c){
	if(c==undefined){
		c=operFileLoadingMsg;
	}
	if(operFileLoadingTimer){
		clearTimeout(operFileLoadingTimer);
	}
	
	operFileLoading.find("#operFileLoadingMsg").html(c);
	/*//隐藏导航
	hideBackNav();*/
	operFileLoading.show();
}
/**
 * outWaitTime秒后隐藏
 * operFileLoadingMsg来自jsp
 */
function hideOperFileLoading(outWaitTime){
	if(outWaitTime==undefined){
		outWaitTime = 0;
	}
	operFileLoadingTimer = setTimeout(function(){
		operFileLoading.find("#operFileLoadingMsg").html(operFileLoadingMsg);
		operFileLoading.hide();
		//如果当前位于最顶层目录，那么一起取消返回导航
		if(currtUploadHistoryDirPId!=undefined){
			//显示导航
			showBackNav();
		}
		
	}, outWaitTime);
}
/**
 * 隐藏操作按钮
 */
function hideOperationBtn(){
	/*uploadBtn.hide();
	newFileBtn.hide();*/
	sharedBtn.hide();
	downloadBtn.hide();
	deleteBtn.hide();
	renameBtn.hide();
	moveBtn.hide();
}
/**
 * 显示操作按钮
 */
function showOperationBtn(){
	/*uploadBtn.show();
	newFileBtn.show();*/
	sharedBtn.show();
	downloadBtn.show();
	deleteBtn.show();
	renameBtn.show();
	moveBtn.show();
}

/**
 * 冻结所有按钮；与（enableAllBtn）配合使用，在一个操作进行时，禁止用户进行其他操作（***）
 */
function disableAllBtn(){
	allBtnStatus = -1;
	//操作按钮
	disabledBtn(uploadBtn);
	disabledBtn(newFileBtn);
	disabledBtn(sharedBtn);
	disabledBtn(downloadBtn);
	disabledBtn(deleteBtn);
	disabledBtn(renameBtn);
	disabledBtn(moveBtn);
	//排序按钮
	disabledBtn(fillist_title_filename);
	disabledBtn(fillist_title_filesize);
	disabledBtn(fillist_title_uploadtime);
	//label按钮
	disabledBtn(allLableBtn);
	
}
/**
 * 激活所有按钮；与（disableAllBtn）配合使用，在一个操作进行时，禁止用户进行其他操作（***）
 */
function enableAllBtn(){
	allBtnStatus = 1;
	//操作按钮
	enabledBtn(uploadBtn);
	enabledBtn(newFileBtn);
	enabledBtn(sharedBtn);
	enabledBtn(downloadBtn);
	enabledBtn(deleteBtn);
	enabledBtn(renameBtn);
	enabledBtn(moveBtn);
	//排序按钮
	enabledBtn(fillist_title_filename);
	enabledBtn(fillist_title_filesize);
	enabledBtn(fillist_title_uploadtime);
	//label按钮
	enabledBtn(allLableBtn);
	
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
 * 获取session的user
 * @param callfun：用户在线时回调的函数
 */
function getSessionUser(callFun){
	zkAjaxData("/pc/front/fUsersAction!getSessionUser.action",undefined, function(data){
		var msg = data.msg;
		//在线
		if(data.online){
			//执行回调函数
			callFun(data);
		}else{
			
		}
		
	}, true);
}

