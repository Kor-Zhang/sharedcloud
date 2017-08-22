var msgWin;
var msgShower;
var title = "提示";
var content="消息未初始化！";
var msgWin_close;
var fadeOutAndInTime = 400;
//用户储存request的信息
var request_msg_lal;
$(function($){
	initMsgWinDoms();
	setMsgWinUI();
	setMsgWinLis();
	//显示request信息
	showRequestMsg();

});

function initMsgWinDoms(){
	msgWin = $('#msgWin');
	msgShower = $('#msgShower');
	content = msgShower.find("#msgShower_content");
	title = msgShower.find("#msgShower_title");
	msgWin_close = $("#msgWin_close");
	request_msg_lal = $("#request_msg_lal");
}

function setMsgWinUI(){
	hideMsgWin(1);
	
	msgWin.height(html.height());
	msgWin.width(html.width());
	setAbsolution(msgShower);
	msgShower.css("top",html.height()/2-msgShower.height()/2);
	msgShower.css("left",html.width()/2-msgShower.width()/2);
}

function setMsgWinLis(){
	msgWin_close.click(function(){
		hideMsgWin(1);
	});
}



/**
 * 关闭msgwin
 */
function hideMsgWin(time){
	content.html("消息未初始化！");
	if(time){
		msgWin.fadeOut(time);
	}else{
		msgWin.fadeOut(fadeOutAndInTime);
	}
}
/**
 * 打开msgwin
 * @param ctitle
 * @param ccontent
 */
function showMsgWin(ctitle,ccontent,time){
	if(ctitle){
		title.html(ctitle);
	}
	if(ccontent){
		content.html(ccontent);
	}
	if(time){
		
		msgWin.fadeIn(time);
	}else{

		msgWin.fadeIn(fadeOutAndInTime);
	}
}

/**
 * 显示request的msg信息：页面首次加载是自动调用
 */
function showRequestMsg(){
	
	if(request_msg_lal.html()){//尝试加载request的消息参数
		showMsgWin(undefined,request_msg_lal.html(),0);
	}else if(getParam("msg")!=""&&getParam("msg")!=undefined){//尝试加载url的的消息参数
		showMsgWin(undefined,getParam("msg"),0);
		
	}
}