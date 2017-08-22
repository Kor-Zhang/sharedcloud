var user_logout_btn;
$(function($){
	initNavDoms();
	setNavUI();
	setNavLis();
});

function initNavDoms(){
	user_logout_btn = $("#user_logout_btn");
}
function setNavUI(){
	
}
function setNavLis(){
	
	
	
	
}
//点击注销后
function clickLogoutBtn(){
	//打开加载页面
	showLoadingModel(0,function(){
		var parameters = new Object();
		zkAjaxData("/pc/front/fUsersAction!logout.action",parameters, function(data){
			//隐藏加载界面
			hideLoadingModel(0);
			//登录成功
			if(data.success){
				//注销成功,进行转跳
				//跳转等待提示
				showLoadingModelWithContent(data.msg,1,function(){});
				self.location= getWebProjectName()+'/pc/front/jsp/login.jsp';					
			}else{
				//显示错误详细
				showMsgWin(undefined,data.msg, 1);
			}
			
		}, true);
	});
}