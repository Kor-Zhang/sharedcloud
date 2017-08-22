var inputSharedPwdWin;
var inputSharedPwdShower;
var confirmInputPwdBtn;
var cancelInputPwdBtn;
//对话框的显示和隐藏时间
var copyMoveInOutTime = 100;
var passwordInput;
//保存回调方法
var inputSharedPwdWinCloseCallFun;
$(function($){
	initFileInputPwdWinDoms();
	setFileInputPwdWinUI();
	setFileInputPwdWinLis();
});


/**
 * 初始化dom节点
 */
function initFileInputPwdWinDoms(){
	inputSharedPwdWin = $("#inputSharedPwdWin");
	confirmInputPwdBtn = $("#confirmInputPwdBtn");
	cancelInputPwdBtn = $("#cancelInputPwdBtn");
	inputSharedPwdShower = $("#inputSharedPwdShower");
	passwordInput = $("#passwordInput");
}
/**
 * 初始化dom节点
 */
function setFileInputPwdWinUI(){
	inputSharedPwdWin.height(html.height());
	inputSharedPwdWin.width(html.width());
	//设置显示信息居中
	setAbsolution(inputSharedPwdShower);
	inputSharedPwdShower.css("top",html.height()/2-inputSharedPwdShower.height()/2);
	inputSharedPwdShower.css("left",html.width()/2-inputSharedPwdShower.width()/2);
	
}
/**
 * 设置监听事件
 */
function setFileInputPwdWinLis(){
	/*//设置取消按钮点击事件
	setCancelInputPwdBtnClickLis();*/
	//设置确定按钮点击事件
	setConfirmInputPwdBtnClickLis();
	
}


/**
 * 设置取消按钮点击事件
 *//*
function setCancelInputPwdBtnClickLis(){
	cancelInputPwdBtn.unbind("click");
	cancelInputPwdBtn.click(function(){
		hideInputPwdWin();
		
		
	});
}*/
/**
 * 设置确定按钮点击事件
 */
function setConfirmInputPwdBtnClickLis(){
	confirmInputPwdBtn.unbind("click");
	confirmInputPwdBtn.click(function(){
		/*hideInputPwdWin();*/
		//回调方法
		inputSharedPwdWinCloseCallFun(passwordInput.val());
		
	});
}

/**
 * 显示对话框；<br/>
 * @param confirmCallFun
 */
function showInputPwdWin(confirmCallFun){
	inputSharedPwdWin.fadeIn(copyMoveInOutTime);
	if(confirmCallFun){
		//设置回调方法
		inputSharedPwdWinCloseCallFun= confirmCallFun;
	}
	

}
/**
 * 隐藏对话框
 */
function hideInputPwdWin(){
	inputSharedPwdWin.fadeOut(copyMoveInOutTime);
}
