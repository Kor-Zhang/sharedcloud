var confirmWin;
var confirmShower;
var confirmShower_content;
var confirmWinBtn;
var cancelWinBtn;
//用户选择值后的回调方法
var gCallFun;
//界面显示和隐藏时间
var inOutTime = 400;
$(function($){
	initConfirmWinDoms();
	setConfirmWinUI();
	setConfirmWinLis();
	
});
/**
 * 初始化dom节点
 */
function initConfirmWinDoms(){
	confirmWin = $("#confirmWin");
	confirmShower = $("#confirmShower");
	confirmWinBtn = $("#confirmWinBtn");
	cancelWinBtn = $("#cancelWinBtn");
	confirmShower_content = $("#confirmShower_content");
}
/**
 * 初始化dom节点
 */
function setConfirmWinUI(){
	confirmWin.height(html.height());
	confirmWin.width(html.width());
	//设置显示信息居中
	setAbsolution(confirmShower);
	confirmShower.css("top",html.height()/2-confirmShower.height()/2);
	confirmShower.css("left",html.width()/2-confirmShower.width()/2);
	
}
/**
 * 设置监听事件
 */
function setConfirmWinLis(){
	setCancelWinBtnClickLis();
	setConfirmWinBtnClickLis();
}

/**
 * 点击取消触发事件
 * @param outTime
 */
function setCancelWinBtnClickLis(){
	cancelWinBtn.unbind("click");
	cancelWinBtn.click(function(){
		gCallFun(false);
		confirmWin.fadeOut(inOutTime);
	});
}
/**
 * 点击确定触发事件
 * @param outTime
 */
function setConfirmWinBtnClickLis(){
	confirmWinBtn.unbind("click");
	confirmWinBtn.click(function(){
		gCallFun(true);
		confirmWin.fadeOut(inOutTime);
	});
}


/**
 * 显示选择框
 */
function showConfirmWin(title,callFun){
	confirmShower_content.html(title);
	//显示
	confirmWin.fadeIn(inOutTime);
	//设置回调方法
	gCallFun = callFun;
}