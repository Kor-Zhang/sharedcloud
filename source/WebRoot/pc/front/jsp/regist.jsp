<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>

<title>SharedCloud Regist</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/global.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/loadingModel.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/regist.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/msgWin.css'></c:url>">
</head>

<body>
	
	<%--
	加载界面
	--%>
	<jsp:include page="./loadingModel.jsp"></jsp:include><%--
	<%--
	消息提示框
	--%>
	<jsp:include page="./msgWin.jsp"></jsp:include><%--
	
	注册内容
	
	--%>
	<div id="regist_content">
		<div id="regist_nav">
			<div id="regist_nav_logo">
				<div>
					<img src="<c:url value='/pc/front/img/icon/logo.jpg'></c:url>">
				</div>

				<div>云共享</div>
			</div>
			<div id="fg">|</div>
			<div id="title">注册新账户</div>
			<div id="toLogin">
				我有账户，<a href='<c:url value='/pc/front/jsp/login.jsp'></c:url>'>登录</a>
			</div>
		</div>
		<%--
		
		详细的注册信息
	--%>
		<div id="regist_div">
			<form id="regist_form" autocomplete="off">
				<%--
				避免缓存的作用
				--%>
				<input type="text" name="username" style="margin-left: 9999px;"/>
				<input type="password" name="password" style="margin-left: 9999px;"/>
				<table id="regist_table">
					<tr>
						<td align="right"><label>&nbsp;&nbsp;&nbsp;邮箱：<input
								autocomplete="off" name="email" id="emialInput"
								class="registInput" placeholder="用户登录和找回密码" autocomplete="off"/> </label></td>
						<td align="left"><label id="emailLal">请输入正确格式的邮箱地址</label></td>

					</tr>
					<tr>
						<td align="right"><label>&nbsp;&nbsp;&nbsp;昵称：<input
								name="username" id="nameInput" class="registInput"
								placeholder="请设置昵称" autocomplete="off"/> </label></td>
						<td align="left"><label id="nameLal">请输入您的昵称</label></td>

					</tr>
					
					<tr>
						<td align="right"><label>&nbsp;&nbsp;&nbsp;密码：<input
								name="password" id="passwordInput" class="registInput"
								type="password" placeholder="请输入登录密码" autocomplete="off"/> </label></td>
						<td align="left"><label id="passwordlLal">请输入8-15位的密码，可包含a-z，A-Z_.</label>
						</td>

					</tr>
					<tr>
						<td align="right"><label>验证码：<input name="verifyCode"
								id="veritfyCodeInput" class="registInput" placeholder="请输入登验证码" autocomplete="off"/>
						</label></td>
						<td align="left"><label id="veritfyCodelLal">请输入验证码</label></td>

					</tr>
					<tr>
						<td colspan="2" id="verifyImg_td"><img id="verifyImg"
							src="<c:url value='/pc/verifyAction!getVerifyCode.action'/>" />
						</td>
					</tr>
					<tr>
						<td colspan="2"><input id="registBtn" type="button"
							value="注册" /></td>


					</tr>
				</table>


				<%--<div class="form_div">
					
				</div>
				--%>
				<%--<div id="veritfyImg_div" class="form_div"></div>
				--%>
				<%--<div class="form_div">
					<input type="radio" />
				</div>
				--%>
				<%--<div id="form_sub_btn" class="form_div">
					
				</div>
			--%>
			</form>
		</div>
	</div>

</body>
<script type="text/javascript"
	src='<c:url value='/pc/plug-in/jquery-3.1.1/jquery.js'></c:url>'></script>
<script type="text/javascript"
	src='<c:url value='/pc/front/js/global.js'></c:url>'></script>
<script type="text/javascript"
	src='<c:url value='/pc/front/js/loadingModel.js'></c:url>'></script>
<script type="text/javascript"
	src='<c:url value='/pc/front/js/regist.js'></c:url>'></script>

<script type="text/javascript"
	src='<c:url value='/pc/front/js/msgWin.js'></c:url>'></script>
</html>
