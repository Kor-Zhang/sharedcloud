var createShareingWin;
var fileShareingShower;
var createShareingWinTitle;
//对话框的显示和隐藏时间
var shareingWinInOutTime = 400;
//记录被分享的文件或者文件夹的id（即uploadhistory的id）
var gSharedFileOrDirectoryId;
//选择分享连接后的回调方法
var gChooseLinkTypeCallFun;
var cancelShareingBtn;
var displayDiv;
//两个可切换视图
var chooseSharedLinkTypeDiv;
var privateLinkDiv;
var publicLinkDiv;

//分享连接按钮
var createPublicShareingBtn;
var createPrivateShareingBtn;
//创建的连接类型：public：true；private：false


var privateLinkUrl;
var privateLinkUrlCopyBtn;
var privateLinkUrlPwd;
var isPublicLink;


var publicLinkUrl;
var publicLinkUrlCopyBtn;
$(function($){
	
	initFileShareingWinDoms();
	setFileShareingWinUI();
	setFileShareingWinLis();
	
	
	
	
});
/**
 * 初始化dom节点
 */
function initFileShareingWinDoms(){
	createShareingWin = $("#createShareingWin");
	shareingShower = $("#shareingShower");
	createShareingWinTitle = $("#createShareingWinTitle");
	cancelShareingBtn = $("#cancelShareingBtn");
	displayDiv = $(".displayDiv");

	chooseSharedLinkTypeDiv = $("#chooseSharedLinkTypeDiv");;
	privateLinkDiv = $("#privateLinkDiv");;
	publicLinkDiv = $("#publicLinkDiv");;
	//默认显示
	changeDisplayDiv(chooseSharedLinkTypeDiv);
	
	createPublicShareingBtn = $("#createPublicShareingBtn");;
	createPrivateShareingBtn = $("#createPrivateShareingBtn");;
	

	privateLinkUrl = $("#privateLinkUrl");;
	privateLinkUrlCopyBtn = $("#privateLinkUrlCopyBtn");;
	privateLinkUrlPwd = $("#privateLinkUrlPwd");
	
	

	publicLinkUrl = $("#publicLinkUrl");
	publicLinkUrlCopyBtn = $("#publicLinkUrlCopyBtn");
}
/**
 * 初始化dom节点
 */
function setFileShareingWinUI(){
	createShareingWin.height(html.height());
	createShareingWin.width(html.width());
	//设置显示信息居中
	setAbsolution(shareingShower);
	shareingShower.css("top",html.height()/2-shareingShower.height()/2);
	shareingShower.css("left",html.width()/2-shareingShower.width()/2);
	
}
/**
 * 设置监听事件
 */
function setFileShareingWinLis(){
	//设置点击关闭按钮的监听事件
	setCancelShareingBtnClickLis();
	//设置点击创建公共连接按钮的监听事件
	setCreatePublicShareingBtnClickLis();
	//设置点击创建私密连接按钮的监听事件
	setCreatePrivateShareingBtnClickLis();
	//设置点击复制私密连接和密码按钮的监听事件
	setPrivateLinkUrlCopyBtnClickLis();
	//设置点击复制公共连接按钮的监听事件
	setPublicLinkUrlCopyBtnClickLis();
}
/**
 * 设置点击复制公共连接按钮的监听事件
 */
function setPublicLinkUrlCopyBtnClickLis(){
	publicLinkUrlCopyBtn.unbind("click");
	publicLinkUrlCopyBtn.click(function(){


		$(this).zclip({
		    path: getWebProjectName()+'/pc/plug-in/jquery.zclip.1.1.1/ZeroClipboard.swf',
		    copy: function(){
		    	var content ="公共连接："+publicLinkUrl.val();
		    return content;
		    },
		    beforeCopy:function(){/* 按住鼠标时的操作 */
		       /* $(this).css("color","orange");*/
		    },
		    afterCopy:function(){/* 复制成功后的操作 */
		    	showMsgWin(undefined,"复制成功",0);
		    }
		});

		
	});
}
/**
 * 设置点击复制私密连接和密码按钮的监听事件
 */
function setPrivateLinkUrlCopyBtnClickLis(){
	
	
	privateLinkUrlCopyBtn.unbind("click");
	
	privateLinkUrlCopyBtn.click(function(){
		$(this).zclip({
	        path: getWebProjectName()+'/pc/plug-in/jquery.zclip.1.1.1/ZeroClipboard.swf',
	        copy: function(){
	        	var content ="私密连接："+privateLinkUrl.val()+",密码："+privateLinkUrlPwd.val();
	        return content;
	        },
	        beforeCopy:function(){/* 按住鼠标时的操作 */
		       /* $(this).css("color","orange");*/
	        },
	        afterCopy:function(){/* 复制成功后的操作 */
	        	showMsgWin(undefined,"复制成功",0);
	        }
	    });
		

		
		
		
	});
}
/**
 * 设置点击创建公共连接按钮的监听事件
 */
function setCreatePublicShareingBtnClickLis(){
	createPublicShareingBtn.unbind("click");
	createPublicShareingBtn.click(function(){
		//显示等待
		showLoadingModelWithContent("正在获取分享连接", undefined, function(){
			//设置创建的连接类型
			isPublicLink = true;
			//回调函数，用于执行远程连接；远程操作完成后，显示结果等操作；
			gChooseLinkTypeCallFun(isPublicLink,gSharedFileOrDirectoryId,afterLinkCreateHandler);

			//隐藏等待
			hideLoadingModel(undefined);
		});
		
	});
}
/**
 * 设置点击创建私密连接按钮的监听事件
 */
function setCreatePrivateShareingBtnClickLis(){
	createPrivateShareingBtn.unbind("click");
	createPrivateShareingBtn.click(function(){
		
		//显示等待
		showLoadingModelWithContent("正在获取分享连接", undefined, function(){
			//设置创建的连接类型
			isPublicLink = false;
			//回调函数，用于执行远程连接；远程操作完成后，显示结果等操作；
			gChooseLinkTypeCallFun(isPublicLink,gSharedFileOrDirectoryId,afterLinkCreateHandler);
			//隐藏等待
			hideLoadingModel(undefined);
		});
		
	});
}
/**
 * ajax请求生成分享url后额回调方法；用于显示服务器生成的分享的url
 */
function afterLinkCreateHandler(data){
	/*c(data.ispublic);*/
	if(!data.ispublic){//如果为私密连接
		privateLinkUrl.val(data.obj.shreadUrl);//设置显示的连接
		privateLinkUrlPwd.val(data.obj.password);//设置显示的密码
		changeDisplayDiv(privateLinkDiv);
	}else{//如果为公共连接
		publicLinkUrl.val(data.obj.shreadUrl);//设置显示的连接
		changeDisplayDiv(publicLinkDiv);
	}
}



/**
 * 设置点击关闭按钮的监听事件
 */
function setCancelShareingBtnClickLis(){
	cancelShareingBtn.unbind("click");
	cancelShareingBtn.click(function(){
		hideShareingWin(undefined);
		//恢复默认显示
		changeDisplayDiv(chooseSharedLinkTypeDiv);
	});
}
/**
 * 隐藏分析框
 * @param hideTime
 */
function hideShareingWin(hideTime){
	if(hideTime==undefined){
		hideTime = shareingWinInOutTime;
	}
	createShareingWin.fadeOut(shareingWinInOutTime);
}
/**
 * 显示分享框；<br/>
 * sharedFileOrDirectoryId：被移动的uploadhistory的id；<br/>
 * sharedFileOrDirectoryName：分享的文件夹的名称；<br/>
 * chooseLinkTypeCallFun：选择创建的分享连接类型后的回调方法；<br/>
 */
function showShareingWin(sharedFileOrDirectoryId,sharedFileOrDirectoryName,chooseLinkTypeCallFun){
	createShareingWinTitle.html("分享文件（夹）："+sharedFileOrDirectoryName);
	createShareingWin.fadeIn(shareingWinInOutTime);
	gSharedFileOrDirectoryId = sharedFileOrDirectoryId;
	//设置回调函数
	/*c(chooseLinkTypeCallFun);*/
	gChooseLinkTypeCallFun = chooseLinkTypeCallFun;
};
/**
 * 切换显示的div
 */
function changeDisplayDiv(div){
	displayDiv.hide();
	$(div).show();
}

