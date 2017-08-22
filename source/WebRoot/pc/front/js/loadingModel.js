
//加载页面
var loadingModel = undefined;
//等待文字dom
var loadFont = undefined;
//默认等待文字dom
var loadFontDefault = "";
//是否打开了加载页面的标志
var loadingModelStatus = 0;
var loadingFadeOutAndInTime = 400;
$(function(){
	initLoadingModelDom();
	setLoadingModelListener();
	setLoadingModelUI();
	
	//监听浏览器尺寸变化，解决浏览器尺寸变化时，页面出现变形
	$(window).resize(function(){
		//调用fun，调整界面
		setLoadingModelUI();
	});
});
/**
 * 初始化dom
 */
function initLoadingModelDom(){
	/****初始化dom****/
	
	loadingModel = $('#loadingModel').hide();
	loadFont = $("#loadFont");
	loadFontDefault = loadFont.html();
}
/**
 * 设置界面
 */
function setLoadingModelUI(){
	
	
	loadingModel.css('padding-top',html.height()/2);
	
}


/**
 * 设置监听器
 */
function setLoadingModelListener(){
	
}


/************************加载界面的显示，隐藏方法*********************/
/**
 * 是否打开了加载页面
 * @returns {Number}
 */
function isPop(){
	return loadingModelStatus==1?true:false;
}
/**
 * 修改加载界面的状态
 */
function setShowStatus(){
	loadingModelStatus = 1;
}
function setHideStatus(){
	loadingModelStatus = 0;
}
/**
 *  显示加载界面
 * @param outTime：显示过程的耗时
 * @param callFun：回调函数
 * @param callFunWaitTime：回调函数等待时间
 */
function showLoadingModel(inTime,callFun){
	//如果加载界面已经打开，直接执行回调函数
	if(isPop()){
		//执行加载界面显示后的回调函数
		c(1);
		callFun();/*
		executeCallFun(callFun,0);*/
		return;
	}
	//执行界面显示
	var realInTime = loadingFadeOutAndInTime;
	if(inTime!=undefined){
		realInTime = inTime;
	}
	loadingModel.fadeIn(realInTime);
	//修改加载界面存在状态
	setShowStatus();
	//执行加载界面显示后的回调函数
	callFun();
}
/**
 * 显示加载界面（可修改显示内容）
 * @param title
 * @param inTime
 * @param callFun
 */
function showLoadingModelWithContent(title,inTime,callFun){
	
	if(title){
		loadFont.html(title);
	}
	showLoadingModel(inTime,callFun);
}

/**
 * 隐藏加载界面
 * @param outTime
 */
function hideLoadingModel(outTime){
	
	//执行界面隐藏
	var realOutTime = 500;
	if(outTime!=undefined){
		realOutTime = outTime;
	}
	loadingModel.fadeOut(realOutTime);
	loadFont.html(loadFontDefault);
	//修改加载界面存在状态
	setHideStatus();
}

