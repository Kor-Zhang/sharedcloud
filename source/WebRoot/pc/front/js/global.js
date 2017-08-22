var nav;
var html;
$(function($) {

	initGlobalDoms();
	setGlobalUI();

});
/**
 * 初始化参数
 */
function initGlobalDoms() {
	html = $('html');
	nav = $('#nav_nav');
}
function setGlobalUI() {

}
/**
 * 操作request
 */
jQueryRequest = {
	data : {},
	Int : function() {
		var aPairs, aTmp;
		var queryString = new String(window.location.search);
		queryString = queryString.substr(1, queryString.length);
		aPairs = queryString.split("&");
		for ( var i = 0; i < aPairs.length; i++) {
			aTmp = aPairs[i].split("=");
			this.data[aTmp[0]] = aTmp[1];
		}
	},
	Get : function(key) {
		return this.data[key];
	}
};
/**
 * 判断input的最后一个值是否为大写字母；<br/>
 * false:小写；
 * true：大写；
 * undefined：非字母
 * @param input
 */
function isUperLetter(input){
	var val = $(input).val().substr($(input).val().length-1,$(input).val().length);
	//大写正则
	var uperReg = /^[A-Z]+$/;
	var lowerReg = /^[a-z]+$/;
	if (uperReg.test(val)) {
		return true;
	}else if(lowerReg.test(val)){
		return false;
	}
	return undefined;
}
/**
 * 获取地址栏参数
 * 
 * @param name
 * @returns
 */
function getParam(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

/**
 * 获取格式化的文件大小<br/>
 * 比如：1024B==>1KB；
 * @param fileSize：单位为B
 */
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
}

/**
 * 生成uuid
 * @returns
 */
function uuid() {
	var s = [];
	var hexDigits = "0123456789abcdef";
	for ( var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}
	s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the
	// clock_seq_hi_and_reserved
	// to 01
	s[8] = s[13] = s[18] = s[23] = "-";

	var uuid = s.join("");
	return uuid;
}

/**
 * 控制台输出
 * @param info
 */
function c(info) {
	console.info(info);
}

/**
 * 弹出框输出
 * @param info
 */
function a(info) {
	alert(info);
}

/**
 * 获取某年某月最大天数
 */
function getMaxDay(year, month) {
	//获取某月最大天数
	var cdt = new Date(year, month, 0).getDate();
	return cdt;
}
/**
 * 设置外边距
 * @param dom ： 需要设置外边距的节点
 * @param margin：设置的边距
 */
function setMargin(dom, margin) {
	dom = $(dom);
	dom.css('margin-left', margin);
	dom.css('margin-top', margin);
	dom.css('margin-bottom', margin);
	dom.css('margin-right', margin);
}

/**
 * 设置外边距
 * @param dom ： 需要设置外边距的节点
 * @param marginTop：上部边距
 * @param marginBottom：下部边距
 * @param marginLeft：左部边距
 * @param marginRight：右部边距
 */
function setAllMargin(dom, marginTop, marginBottom, marginLeft, marginRight) {
	dom = $(dom);
	dom.css('margin-left', marginLeft);
	dom.css('margin-top', marginTop);
	dom.css('margin-bottom', marginBottom);
	dom.css('margin-right', marginRight);
}

/**
 * 设置内边距
 * @param dom ： 需要设置内边距的节点
 * @param margin：设置的边距
 */
function setPadding(dom, padding) {
	dom = $(dom);
	dom.css('padding-left', padding);
	dom.css('padding-top', padding);
	dom.css('padding-bottom', padding);
	dom.css('padding-right', padding);
}

/**
 * 设置内边距
 * @param dom ： 需要设置内边距的节点
 * @param paddingTop：上部边距
 * @param paddingBottom：下部边距
 * @param paddingLeft：左部边距
 * @param paddingRight：右部边距
 */
function setAllPadding(dom, paddingTop, paddingBottom, paddingLeft,
		paddingRight) {
	dom = $(dom);
	dom.css('padding-left', paddingLeft);
	dom.css('padding-top', paddingTop);
	dom.css('padding-bottom', paddingBottom);
	dom.css('padding-right', paddingRight);
}

/**
 * 清除监听事件
 * @param listener：监听事件名，例如：click
 * @param dom：清除监听事件的节点
 */
function cleanListener(listener, dom) {
	dom = $(dom);
	dom.unbind(listener);
}

/**
 * 去除str总的'px'
 * @param str：需要处理的字符串
 */
function removePx(str) {
	var newStr = str.toString().replace('px', '');
	return newStr;
}
/**
 * 滑动到顶部
 * @param targetDom ：滑动到dom
 * @param speed ：滑动速度
 * @param operDom：被滑动的dom
 */
function scrollTo(operDom, targetDom, speed) {
	if (!speed) {
		speed = 'slow';
	}
	var targetPosition = $(targetDom).offset().top;
	$(operDom).animate({
		scrollTop : targetPosition
	}, speed);
}
/**
 * 将form表单元素的值序列化成对象
 * @param form
 * @returns {___anonymous3934_3935}
 */
function serializeObject(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

/**
 * 设置position为absolution
 * @param dom
 */
function setAbsolution(dom) {
	$(dom).css("position", "absolution");
}

/**
 * 连接服务器错误后的操作
 */
function linkServletError(callfun, data) {
	//定义错误信息
	var errorMsg = struts2_info_linkServerError;
	//回调函数，传递false标志
	callfun(data, false);
	//发生连接服务器错误后的处理
	popMsg(undefined, errorMsg);
	hideLoadingModel(0);
	//连接服务器失败，抛出异常，这个异常；
	//一般来说切换视图时会捕获这个异常，并且会对其进行相应的处理
	//如果这里没有处理，那么需要程序员手动对回调函数中的success进行判断，然后处理
	throw new Error(errorMsg);
}

/**
 * 得到当前项目名称
 */
function getWebProjectName() {
	var webProjectName = undefined;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	//获取带"/"的项目名，如：/uimcardprj
	webProjectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

	return webProjectName;
}
/**
 * 快捷请求ajax，不携带参数（参数可以与url地址一同写入）
 * @param url：请求的地址
 * @param async：是否异步
 * @param callfun：回调方法
 */
/*
 function zkAjax(url, callfun,async) {
 //为url添加命名空间
 var ajaxUrl = getWebProjectName() + url;
 //是否为异步
 var ajaxAsync = false;
 if(async!=undefined){
 ajaxAsync = async;
 }
 //发送请求
 $.ajax({
 type : "POST",
 timeout : 5000, //超时时间设置，单位毫秒
 url : ajaxUrl,
 dataType : "json",
 async:ajaxAsync,
 contentType : "text/html; charset=utf-8",
 success : function(data) {
 callfun(data,true);
 },error:function(data){
 linkServletError(callfun,data);
 }
 });
 };


 *//**
 * 快捷请求ajax，携带参数
 * @param url：请求的地址
 * @param async：是否异步
 * @param jsonData：携带的json参数
 * @param callfun：回调方法
 */
/*
 function zkAjaxData(url, jsonData, callfun,async) {
 var ajaxUrl = getWebProjectName() + url;
 //是否为异步
 var ajaxAsync = true;
 if(async!=undefined){
 ajaxAsync = async;
 }
 //发送请求
 $.ajax({
 type : "POST",
 timeout : 5000, //超时时间设置，单位毫秒
 url : ajaxUrl,
 async:ajaxAsync,
 dataType : "json",
 data : jsonData,
 success : function(data) {
 callfun(data,true);
 },error:function(data){

 linkServletError(callfun,data);
 }
 });
 };*/

/**
 * 快捷请求ajax，携带参数
 * @param url：请求的地址
 * @param async：是否异步
 * @param jsonData：携带的json参数
 * @param callfun：回调方法
 */
function ajaxData(url, jsonData, callfun, async) {
	var ajaxUrl = getWebProjectName() + url;
	//是否为异步
	var ajaxAsync = true;
	if (async != undefined) {
		ajaxAsync = async;
	}
	//发送请求
	$.ajax({
		type : "POST",
		timeout : 5000, //超时时间设置，单位毫秒
		url : ajaxUrl,
		async : ajaxAsync,
		dataType : "json",
		data : jsonData,
		success : function(data) {
			//回调函数，true表示成功
			callfun(data, true);
		},
		error : function(data) {
			//回调函数，true表示失败
			callfun(data, false);
		}
	});
};

/**
 * 封装global.js的ajaxData为zkAjaxData，
 * 增加连接服务器失败提示
 * 用户不在线检测，提示
 * 等善后处理
 */

function zkAjaxData(url, jsonData, callfun, async) {

	//调用global.js的方法
	ajaxData(url, jsonData, function(data, linkStatus) {
		//如果出现连接服务器失败，显示提示信息
		if (false == linkStatus) {
			//隐藏等待界面
			hideLoadingModel(0);
			//显示提示信息
			showMsgWin(undefined, "连接服务器失败。", 0);
		} else {

			//连接服务器成功
			//如果用户不在线，提示并且跳转页面
			if (!data.online) {

				//隐藏提示信息
				hideMsgWin(0);
				//跳转等待提示
				showLoadingModelWithContent(data.msg, 0, function() {
					setTimeout(function() {
						//不在线跳转到登陆界面
						self.location = getWebProjectName()
								+ '/pc/front/jsp/login.jsp';
					}, 3000);
				});
			} else {

			}
			//反馈信息给最上乘调用者
			callfun(data, linkStatus);
		}

	}, async);
}

/**
 * 设置bg颜色
 * @param dom
 * @param color
 */
function setBg(dom, color) {
	$(dom).css("background", color);
}

/**
 * 设置字体颜色
 * @param dom
 * @param color
 */
function setColor(dom, color) {
	$(dom).css("color", color);
}