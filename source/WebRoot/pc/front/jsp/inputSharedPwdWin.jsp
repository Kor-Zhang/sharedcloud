<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
	保存request信息
--%>
<label id="request_inputSharedPwd_lal" style="display:none;">${requestScope.inputSharedPwd}</label>
<div id="inputSharedPwdWin">
	<div id="inputSharedPwdShower">
		<div id="inputSharedPwdWinTitle">
			输入分享密码
		</div>
		<div id="directoryListDiv">
			<div id="inputTipDiv">请输入分享密码：</div>
			<div id="inputPwdDiv"><input id="passwordInput" name="password" type="text" onFocus="this.type='password'" value="" placeholder="请输入分享密码"/></div>
		</div>
		<div id="operBtnsDiv">
			<input id="confirmInputPwdBtn" type="button" value="确认"/>
			<%--<input id="cancelInputPwdBtn" type="button" value="取消"/>
			--%>
		</div>
	</div>

</div>

