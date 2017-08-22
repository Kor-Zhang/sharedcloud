var resetPasswordBtn;
var resetPasswordForm;

var resetPassword_form;

//密码
var passwordInput;
var passwordLal;
var passwordInputDefault;
//连接服务器验证的等待时间
var checkerWaitTime = 800;
var checkerTimer;
$(function($){
	initRegistDoms();
	setRegistUI();
	setRegistLis();
});

function initRegistDoms(){
	resetPasswordBtn = $('#resetPasswordBtn');
	resetPasswordForm = $("#resetPassword_form");
	resetPassword_form = $("#resetPassword_form");
	
	//密码
	passwordInput = $("#passwordInput").val("");
	passwordLal = $("#passwordlLal");
	passwordLalDefault = passwordLal.html();
	
}

function setRegistUI(){
	
}

/**
 * 设置监听器
 */
function setRegistLis(){
	//点击修改
	resetPasswordBtn.click(function (){
		//打开加载页面
		showLoadingModel(undefined,function(){
			var parameters = serializeObject(resetPassword_form);
			zkAjaxData("/pc/front/fUsersAction!updateUserPwd.action",parameters, function(data){
				
				//修改密码成功
				if(data.success){
					//修改密码成功,进行转跳
					//跳转等待提示
					showLoadingModelWithContent(data.msg,undefined,function(){
						setTimeout(function(){
							self.location= getWebProjectName()+'/pc/front/jsp/login.jsp';
						}, 2000);	
					});				
				}else{
					//显示错误详细
					showMsgWin(undefined,data.msg, undefined);
				}
			}, true);
		});
		
	});
	/*//提示密码大小写
	passwordInput.keyup(function(e) {
		//大写正则
		var uperReg = /^[A-Z]+$/;
		//小写正则
		var lowerReg = /^[a-z]+$/;
		if (uperReg.test(e.key)) {
			passwordLal.html("大写已锁定");
		} else if (lowerReg.test(e.key)) {
			passwordLal.html("小写已锁定");
		}
		if (passwordInput.val().length == 0) {
			passwordLal.html(passwordLalDefault);
		}
	});*/
	//密码框键盘点击事件
	passwordInput.unbind("keyup");
	passwordInput.keyup(function(e) {
		//防tab键
		if(e.key==9){
			return ;
		}
		checkPwd(false);
	});
	//密码框焦点脱离事件
	passwordInput.unbind("blur");
	passwordInput.blur(function(e) {
		checkPwd(true);
	});
	/**
	 * 提示密码大小写；远程验证；
	 */
	function checkPwd(immdie){
		var pwdStr = passwordInput.val();
		/*
		 * 显示大小写
		 */
		var isUper = isUperLetter(passwordInput);
		if (true == isUper) {
			passwordLal.html("大写已锁定");
		} else if(false == isUper){
			passwordLal.html("小写已锁定");
		}else if(undefined == isUper){
			passwordLal.html(passwordLalDefault);
		}
		/*
		 * 验证信息
		 */
		if(checkerTimer){
			clearTimeout(checkerTimer);
		}
		//是否立即执行
		var waitTime = checkerWaitTime;
		if(true == immdie){
			waitTime = 0;
		}
		checkerTimer = setTimeout(function(){
			
			var parameters = new Object();
			parameters["password"] = pwdStr;
			zkAjaxData("/pc/front/fUsersAction!checkResetPwd.action",parameters, function(data){
				c(data);
				//成功
				if(data.success){
					
				}else{//错误
					
				}
				
				passwordLal.html(data.msg);
				//文本框为空
				if (passwordInput.val().length == 0) {
					passwordLal.html(passwordLalDefault);
				}
			}, true);
		}, waitTime);
	}
	
}