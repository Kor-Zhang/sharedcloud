//切换视图按钮
var userSelfInfo_chg_basicInfo;
var userSelfInfo_chg_pwd;
var userSelfInfo_chg_head;
var viewBtns;


var view_btn_select_bg = "rgb(54,104,254)";
var view_btn_unSelect_bg = "white";
var view_btn_select_color = "white";
var view_btn_unSelect_color = "rgb(54,104,254)";
//视图
var chg_basicInfo_view;
var chg_password_view;
var chg_head_view;
var chg_views;

//基本信息表单
var basicInfo_form;

//日期的select
var birthday_year;
var birthday_month;
var birthday_day;
//性别
var sex;

//生日
var age;
//保存基本信息按钮

var userSelfInfo_head_img;
var basicInfo_save_btn;

//修改密码
//邮件输入框
var password_emailInput;
//邮件验证发送按钮
var password_sendBtn;

var userid;
$(function($){
	initUserSelfInfoDoms();
	setUserSelfInfoUI();
	setUserSelfInfoLis();

	//默认加载的是基本信息界面
	loadBasicInfoView();
	
	
});

function initUserSelfInfoDoms(){
	userSelfInfo_chg_basicInfo = $("#userSelfInfo_chg_basicInfo");
	userSelfInfo_chg_pwd = $("#userSelfInfo_chg_pwd");
	userSelfInfo_chg_head = $("#userSelfInfo_chg_head");
	viewBtns = $(".userSelfInfo_oper_td").find("a");

	chg_basicInfo_view = $("#chg_basicInfo_view");
	chg_password_view = $("#chg_password_view");
	chg_head_view = $("#chg_head_view");
	
	chg_views = $(".chg_views");
	
	basicInfo_form=$("#basicInfo_form");
	
	
	birthday_year = basicInfo_form.find("#birthday_year");
	birthday_month = basicInfo_form.find("#birthday_month");
	birthday_day = basicInfo_form.find("#birthday_day");
	
	sex = basicInfo_form.find("input[name='sex']");
	age = basicInfo_form.find("input[name='age']");
	basicInfo_save_btn = $("#basicInfo_save_btn");
	
	userSelfInfo_head_img = $("#userSelfInfo_head_img");
	//邮件输入框
	password_emailInput = $("#password_emailInput").val("");
	//邮件验证发送按钮
	password_sendBtn = $("#password_sendBtn");
	userid = $("#userSelfInfo_session_user_userid").val();
}

function setUserSelfInfoUI(){
	
}

/**
 * 设置监听器
 */
function setUserSelfInfoLis(){
	//点击基本信息按钮
	userSelfInfo_chg_basicInfo.click(function(){
		//加载基本信息界面
		loadBasicInfoView();
		
	});
	//点击修改密码
	userSelfInfo_chg_pwd.click(function(){
		showViews(userSelfInfo_chg_pwd,chg_password_view);
	});
	//点击修改头像
	userSelfInfo_chg_head.click(function(){
		showViews(userSelfInfo_chg_head,chg_head_view);
	});
	
	
	
	//点击保存基本信息
	basicInfo_save_btn.click(function(){
		//打开加载页面
		showLoadingModel(0,function(){
			var parameters = serializeObject(basicInfo_form);
			//拼接生日
			var birthday = birthday_year.val()+"-"+birthday_month.val()+"-"+birthday_day.val();
			//添加生日到参数
			parameters['birthday'] = birthday; 
			c(parameters);
			//访问服务器
			zkAjaxData("/pc/front/fUsersAction!saveUserBasicInfo.action",parameters, function(data){
				
				//隐藏加载界面
				hideLoadingModel(0);
				//显示执行结果信息
				showMsgWin(undefined,data.msg, 1);
				
			}, true);
		});
	});
	
	/*//信息导航按钮悬浮事件
	viewBtns.each(function(){
		var t= $(this);
		c(t);
		var oldBg = t.css("background");
		var oldColor = t.css("color");
		c(oldBg);
		c(oldColor);
		t.hover(function(){
			setBg(t,view_btn_select_bg);
			setColor(t,view_btn_select_color);
		},function(){
			setBg(t,oldBg);
			setColor(t,oldColor);
			
		});
	});*/
	
	
	//点击密码修改的邮箱验证按钮
	password_sendBtn.click(function(){
		//打开加载页面
		showLoadingModel(0,function(){
			var parameters = new Object();

			var email = password_emailInput.val();
			//email到参数
			parameters['email'] = email;
			//userid到参数
			parameters['userid'] = userid; 
			//访问服务器
			zkAjaxData("/pc/front/fUsersAction!sendUpdateUserPwdEamil.action",parameters, function(data){
				
				//隐藏加载界面
				hideLoadingModel(0);
				//显示执行结果信息
				showMsgWin(undefined,data.msg, 1);
				
			}, true);
		});
	});
	//小写输入的邮箱
	password_emailInput.keyup(function(e) {
		
		var emailStr = password_emailInput.val();
		password_emailInput.val(emailStr.toLowerCase());
	});
}

/**
 * 加载基本信息界面
 */
function loadBasicInfoView(){
	//加载数据
	initbasicInfoData();
	
	//显示视图
	showViews(userSelfInfo_chg_basicInfo,chg_basicInfo_view);
}
/**
 * 显示某个视图，切换其按钮背景，字体颜色
 */
function showViews(viewBtn,showView){
	//设置背景
	setBg(viewBtns,view_btn_unSelect_bg);
	setBg(viewBtn,view_btn_select_bg);
	//设置字体颜色
	setColor(viewBtns,view_btn_unSelect_color);
	setColor(viewBtn,view_btn_select_color);
	//关闭所有试图
	chg_views.hide();
	//显示指定视图
	showView.show();
}
/**
 * 加载基本信息的基础数据
 */
function initbasicInfoData(){
	
	//加载生日select的option
	//加载年
	var maxYear = new Date().getFullYear();
	for(var i =1950 ;i<=maxYear;i++){
		var opt = "<option value="+i+">"+i+"</option>";
		birthday_year.append(opt);
	}
	//加载月
	for(var i =1 ;i<=12;i++){
		var opt = "<option value="+i+">"+i+"</option>";
		birthday_month.append(opt);
	}

	
	//转化字符串生日格式到date类型
	var birthdayStr = basicInfo_form.find("#session_user_birthday").val();
	birthdayStr = birthdayStr.replace(/^-$/,"/");
	var birthdayDate = new Date(birthdayStr);
	var year = birthdayDate.getFullYear();
	var month = birthdayDate.getMonth();
	month++;
	month=month==13?12:month;
	var day = birthdayDate.getDate();
	birthday_year.val(year);
	
/*	birthday_year.find("option").attr("selected",false);
	birthday_year.find("option[value='"+year+"']").attr("selected",true);*/
	birthday_month.val(month);
/*	birthday_month.find("option").attr("selected",false);
	birthday_month.find("option[value='"+month+"']").attr("selected",true);*/
	
	//加载天(需放置在年和月加载后)
	for(var i =1 ;i<=getMaxDay(year,month);i++){
		var opt = "<option value="+i+">"+i+"</option>";
		birthday_day.append(opt);
	}
	//加载天
	birthday_day.val(day);
	
	/*设置监听：月份或则年份变化后，使得天数变化*/
	birthday_year.change(function(){
		reloadDay();
		//通过当前选择的生日的“年”设置年龄
		age.val(new Date().getFullYear() - birthday_year.val());
	});
	birthday_month.change(function(){
		reloadDay();
	});
	
	
	//加载性别
	var sexStr = $("#session_user_sex").val();
	if(sexStr=="男"){
		
		checkSexRadio(sex.eq(0));
	}else if(sexStr=="女"){

		checkSexRadio(sex.eq(1));
	}else if(sexStr=="未知"){

		checkSexRadio(sex.eq(2));
	}
	 
}
/**
 * 选择性别radio
 */
function checkSexRadio(checkSexRadio){
	sex.removeAttr("checked");
	checkSexRadio.attr("checked","checked");
}
/**
 * 重新加载天
 */
function reloadDay(){
	//清空日期
	birthday_day.html("");
	var year = birthday_year.val();
	var month = birthday_month.val();
	//加载天(需放置在年和月加载后)
	for(var i =1 ;i<=getMaxDay(year,month);i++){
		var opt = "<option value="+i+">"+i+"</option>";
		birthday_day.append(opt);
	}
}