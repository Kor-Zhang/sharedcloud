var registBtn;
var registForm;
var verifyImg;
//邮箱
var emailInput;
var emailLal;
var emailLalDefault;
//昵称
var nameInput;
var nameLal;
var nameLalDefault;
//密码
var passwordInput;
var passwordLal;
var passwordInputDefault;

//验证码
var verifyCodeInput;
var verifyCodeInputLal;
var verifyCodeInputLalDefault;
//等待界面的加载和消失时间
var loadingModelInAndOutTime = 500;
//连接服务器验证的等待时间
var checkerWaitTime = 800;
var checkerTimer;
$(function($){
	initRegistDoms();
	setRegistUI();
	setRegistLis();
});

function initRegistDoms(){
	registBtn = $('#registBtn');
	registForm = $("#regist_form");
	verifyImg = $("#verifyImg");
	//邮箱
	emailInput = $("#emialInput");
	emailLal = $("#emailLal");
	emailLalDefault = emailLal.html();
	//昵称
	nameInput = $("#nameInput");
	nameLal = $("#nameLal");
	nameLalDefault = nameLal.html();
	//密码
	passwordInput = $("#passwordInput");
	passwordLal = $("#passwordlLal");
	passwordLalDefault = passwordLal.html();
	//验证码
	verifyCodeInput = $("#veritfyCodeInput");
	verifyCodeLal = $("#veritfyCodelLal");
	verifyCodeLalDefault = verifyCodeLal.html();
}

function setRegistUI(){
	

}

/**
 * 设置监听器
 */
function setRegistLis(){
	//点击注册
	registBtn.click(function (){
		//打开加载页面
		showLoadingModel(0,function(){
			var parameters = new Object();
			//邮箱
			parameters["email"] = emailInput.val();
			//昵称
			parameters["username"] = nameInput.val();
			//密码
			parameters["password"] = passwordInput.val();
			//验证码
			parameters["verifyCode"] = verifyCodeInput.val();
			
			zkAjaxData("/pc/front/fUsersAction!userRegist.action",parameters, function(data){
				showMsgWin(undefined,data.msg);
				/*刷行验证码*/
				verifyImg.attr("src",verifyImg.attr("src")+"?"+new Date().getTime());
				
				//隐藏加载界面
				hideLoadingModel(0);
			}, true);
		});
		
	});
	
	
	//邮箱输入框的键盘点击事件
	emailInput.unbind("keyup");
	emailInput.keyup(function(e) {
		//防tab键
		if(e.key==9){
			return ;
		}
		checkEmail(false);
	});
	//邮箱输入框的焦点脱离事件
	emailInput.unbind("blur");
	emailInput.blur(function(e) {
		checkEmail(true);
	});
	/**
	 * 小写字母；远程验证；
	 */
	function checkEmail(immdie){
		/*
		 * 小写输入的字母
		 */
		var emailStr = emailInput.val();
		emailInput.val(emailStr.toLowerCase());
		/*
		 * 验证信息
		 */
		if(checkerTimer){
			clearTimeout(checkerTimer);
		}
		//是否立即执行
		var waitTime = checkerWaitTime;
		if(immdie){
			waitTime = 0;
		}
		checkerTimer = setTimeout(function(){
			
			var parameters = new Object();
			parameters["email"] = emailStr;
			zkAjaxData("/pc/front/fUsersAction!checkRegistEmail.action",parameters, function(data){
				c(data);
				//成功
				if(data.success){
					
				}else{//错误
					
				}
				emailLal.html(data.msg);
				if (emailInput.val().length == 0) {
					emailLal.html(emailLalDefault);
				}
			}, true);
		}, waitTime);
		
	}
	//验证码框的键盘点击事件
	verifyCodeInput.unbind("keyup");
	verifyCodeInput.keyup(function(e) {
		//防tab键
		if(e.key==9){
			return ;
		}
		checkVerifyCode(false);
	});
	//验证码框的焦点脱离事件
	verifyCodeInput.unbind("blur");
	verifyCodeInput.blur(function(e) {
		checkVerifyCode(true);
	});
	/**
	 * 小写验证码；远程验证验证码；
	 */
	function checkVerifyCode(immdie){

		var verifyCodeStr = verifyCodeInput.val();
		verifyCodeInput.val(verifyCodeStr.toLowerCase());
		
		/*
		 * 验证信息
		 */
		if(checkerTimer){
			clearTimeout(checkerTimer);
		}
		//是否立即执行
		var waitTime = checkerWaitTime;
		if(immdie){
			waitTime = 0;
		}
		checkerTimer = setTimeout(function(){
			
			var parameters = new Object();
			parameters["verifyCode"] = verifyCodeStr;
			zkAjaxData("/pc/front/fUsersAction!checkRegistVerifyCode.action",parameters, function(data){
				c(data);
				//成功
				if(data.success){
					
				}else{//错误
					
				}
				verifyCodeLal.html(data.msg);
				if (verifyCodeInput.val().length == 0) {
					verifyCodeLal.html(verifyCodeInputLalDefault);
				}
			}, true);
		}, waitTime);
	}
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
			zkAjaxData("/pc/front/fUsersAction!checkRegistPwd.action",parameters, function(data){
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
	
	//密码框键盘点击事件
	nameInput.unbind("keyup");
	nameInput.keyup(function(e) {
		//防tab键
		if(e.key==9){
			return ;
		}
		checkUserName(false);
	});
	//密码框焦点脱离事件
	nameInput.unbind("blur");
	nameInput.blur(function(e) {
		checkUserName(true);
	});
	/**
	 * 检测username
	 */
	function checkUserName(immdie){

		var nameStr = nameInput.val();
		
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
			parameters["username"] = nameStr;
			zkAjaxData("/pc/front/fUsersAction!checkRegistUserName.action",parameters, function(data){
				c(data);
				//成功
				if(data.success){
					
				}else{//错误
					
				}
				
				nameLal.html(data.msg);
				if (nameInput.val().length == 0) {
					nameLal.html(nameLalDefault);
				}
			}, true);
		}, waitTime);
	}
	
	
	
	//点击验证码刷行图片
	verifyImg.unbind("click");
	verifyImg.click(function(){
		/*刷行验证码*/
		verifyImg.attr("src",verifyImg.attr("src")+"?"+new Date().getTime());
	});
}