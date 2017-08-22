<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@ page import="com.sharedcloud.pc.model.Users" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page autoFlush="true" buffer="1094kb"%>
<!DOCTYPE HTML>
<html>
<head>

<title>SharedCloud Login</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/global.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/loadingModel.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/msgWin.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/login.css'></c:url>">
	
</head>

<body>
	<%--
	如果用户在线，那么跳转到主页
	--%>
	<c:if test="${not empty sessionScope.user}">
		<jsp:forward page="./index.jsp"></jsp:forward>
	</c:if>
	
	<c:if test="${empty sessionScope.user}">
	<%--
	如果用户不在线，那么查找cookie以寻求登录信息，跳转index
	--%>
	<%
		boolean flag = false;
		Cookie[] cookies = request.getCookies();
			if(cookies!=null){
				String userid = "";
				String password = "";
				for(Cookie c:cookies){
					if(c.getName().equals(Users.COOKIE_USERID_NAME)){
						userid = c.getValue();
						flag = true;
					}
					if(c.getName().equals(Users.COOKIE_PASSWORD_NAME)){
						password = c.getValue();
					}
				}
				//如果存在cookie，那么请求通过cookie登录
				if(flag){
					//转发到cookie登录
					//response.sendRedirect(this.getServletContext().getContextPath()+"/pc/front/fUsersAction!loginByCookie.action?"+Users.COOKIE_USERID_NAME+"="+c.getValue());
					request.getRequestDispatcher("/pc/front/fUsersAction!loginByCookie.action?userid="+userid+"&passowrd="+password).forward(request, response);
				}
			}
	%>
</c:if>
	
	
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
	<div id="login_content">
		<div id="login_nav">
			<div id="login_nav_logo">
				<div>
					<img src="<c:url value='/pc/front/img/icon/logo.jpg'></c:url>">
				</div>

				<div>云共享</div>
			</div>
			<div id="fg">|</div>
			<div id="title">登录账户</div>
			<div id="toLogin">
				没有账户，<a href='<c:url value='/pc/front/jsp/regist.jsp'></c:url>'>注册</a>
			</div>
		</div>
		<%--
		
		详细的注册信息
	--%>
		<div id="login_div">
			<form id="login_form" autocomplete="off">
				<%--
				避免缓存的作用
				--%>
				<input type="text" name="username" style="margin-left: 9999px;"/>
				<input type="password" name="password" style="margin-left: 9999px;"/>
				<table id="login_table">
					<tr>
						<td align="right"><label>&nbsp;&nbsp;&nbsp;邮箱：
							<input name="email" id="emialInput" class="loginInput"
								placeholder="用户登录和找回密码" autocomplete="off"/> </label>
						</td>
						<td align="left"><label id="emailLal">请输入正确格式的邮箱地址</label>
						</td>

					</tr>
					<tr>
						<td align="right"><label>&nbsp;&nbsp;&nbsp;密码：<input
								name="password" id="passwordInput" class="loginInput"
								type="password" placeholder="请输入登录密码" autocomplete="off"/> </label>
						</td>
						<td align="left"><label id="passwordlLal">请输入8-15位的密码，可包含a-z，A-Z_.</label>
						</td>

					</tr>
					<tr>
						<td align="right"><label>验证码：<input name="verifyCode"
								id="veritfyCodeInput" class="loginInput" placeholder="请输入登验证码" autocomplete="off"/>
						</label>
						</td>
						<td align="left"><label id="veritfyCodelLal">请输入验证码</label>
						</td>

					</tr>
					<tr>
						<td colspan="2" id="verifyImg_td"><img id="verifyImg"
							src="<c:url value='/pc/verifyAction!getVerifyCode.action'/>" />
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2"><label>
						
							<input name="remeberMe" type="checkbox"
								id="remeberMeInput" class="" />&nbsp;记住我
						</label>
						</td>
						<td align="left"><label id="remeberMeLal"></label>
						</td>

					</tr>
					<tr>
						<td colspan="2"><input id="loginBtn" type="button"
							value="登录" />
						</td>


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
	src='<c:url value='/pc/front/js/msgWin.js'></c:url>'></script>
<script type="text/javascript"
	src='<c:url value='/pc/front/js/login.js'></c:url>'></script>

	
</html>


