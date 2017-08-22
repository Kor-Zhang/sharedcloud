<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>

<title>SharedCloud ResetPassword</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/global.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/loadingModel.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/resetPassword.css'></c:url>">
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
	
	修改密码内容
	
	--%>
	<div id="resetPassword_content">
		<div id="resetPassword_nav">
			<div id="resetPassword_nav_logo">
				<div>
					<img src="<c:url value='/pc/front/img/icon/logo.jpg'></c:url>">
				</div>

				<div>云共享</div>
			</div>
			<div id="fg">|</div>
			<div id="title">修改密码</div>
			<%--<div id="toLogin">
				我有账户，<a href='<c:url value='/pc/front/jsp/login.jsp'></c:url>'>登录</a>
			</div>
		--%></div>
		<%--
		
		详细的修改密码信息
	--%>
		<div id="resetPassword_div">
			<form id="resetPassword_form" action=''><%--
				保存userid
				--%><input name="userid" style="display: none;" value="${requestScope.reqUser.userid}"/>
				<table id="resetPassword_table">
					<tr>
						<td align="right"><label>&nbsp;&nbsp;&nbsp;新密码：<input
								name="password" id="passwordInput" class="resetPasswordInput"
								type="text"  onfocus="this.type='password'" placeholder="请输入新密码" /> </label></td>
						<td align="left"><label id="passwordlLal">请输入8-15位的密码，可包含a-z，A-Z_.</label>
						</td>

					</tr>
					<tr>
						<td colspan="2"><input id="resetPasswordBtn" type="button"
							value="修改密码" /></td>


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
	src='<c:url value='/pc/front/js/resetPassword.js'></c:url>'></script>

<script type="text/javascript"
	src='<c:url value='/pc/front/js/msgWin.js'></c:url>'></script>
</html>
