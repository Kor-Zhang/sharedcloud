<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
	保存request信息
--%>
<label id="request_confirm_lal" style="display:none;">${requestScope.confirm}</label>
<div id="confirmWin">
	<%--<div id="confirmWin_close">X</div>
	--%><div id="confirmShower">
		<div id="confirmShower_content">消息未初始化!</div>
		<div id="confirmBtns_div">
			<input class="confirmBtns" id="confirmWinBtn" type="button" value="确认"/>
			<input class="confirmBtns" id="cancelWinBtn" type="button" value="取消"/>
		</div>
	</div>

</div>

