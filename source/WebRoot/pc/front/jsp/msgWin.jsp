<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
	保存request信息
--%>
<label id="request_msg_lal" style="display:none;">${requestScope.msg}</label>
<div id="msgWin">
	<div id="msgWin_close">X</div>
	<div id="msgShower">
		<div id="msgShower_title">提示</div>
		<div id="msgShower_content">消息未初始化!</div>
	</div>

</div>

