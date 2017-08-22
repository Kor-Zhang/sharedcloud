<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@ page import="com.sharedcloud.pc.model.Users"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page autoFlush="true" buffer="1094kb"%>
<!DOCTYPE HTML>
<html>
<head>

<title>SharedCloud UserSelfInfo</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/global.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/loadingModel.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/userSelfInfo.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/msgWin.css'></c:url>">
</head>

<body>
	
	<c:if test="${empty sessionScope.user}">
		<%--
	如果用户不在线，那么跳转到主页
	--%>
		<jsp:forward page="./index.jsp"></jsp:forward>
	</c:if>

	<%--
	加载界面
	--%>
	<jsp:include page="./loadingModel.jsp"></jsp:include><%--
	<%--
	消息提示框
	--%>
	<jsp:include page="./msgWin.jsp"></jsp:include><%--
	
	用户信息内容
	
	--%>
	<div id="userSelfInfo_content">
		<div id="userSelfInfo_nav">
			<div id="userSelfInfo_nav_logo">
				<div>
					<img src="<c:url value='/pc/front/img/icon/logo.jpg'></c:url>">
				</div>

				<div>云共享</div>
			</div>
			<div id="fg">|</div>
			<div id="title">用户信息（${sessionScope.user.username}）</div>
			<%--
			存储userid
			--%>
			<input id="userSelfInfo_session_user_userid" style="display:none;" value="${sessionScope.user.userid}"/>
			<div id="toLogin">
				返回<a href='<c:url value='/pc/front/jsp/index.jsp'></c:url>'>主页</a>
			</div>
		</div>
		<%--
		
		详细的用户信息信息
	--%>
		<div id="userSelfInfo_div">
			<%--<form id="userSelfInfo_form">

				<table id="userSelfInfo_table">
					<tr>
						<td align="right"><label>&nbsp;&nbsp;&nbsp;邮箱：<input
								autocomplete="off" name="email" id="emialInput"
								class="userSelfInfoInput" placeholder="用户登录和找回密码" /> </label></td>
						<td align="left"><label id="emailLal">请输入正确格式的邮箱地址</label></td>

					</tr>
					<tr>
						<td align="right"><label>&nbsp;&nbsp;&nbsp;昵称：<input
								name="username" id="nameInput" class="userSelfInfoInput"
								placeholder="请设置昵称" /> </label></td>
						<td align="left"><label id="nameLal">请输入您的昵称</label></td>

					</tr>
					<tr>
						<td align="right"><label>&nbsp;&nbsp;&nbsp;密码：<input
								name="password" id="passwordInput" class="userSelfInfoInput"
								type="password" placeholder="请输入登录密码" /> </label></td>
						<td align="left"><label id="passwordlLal">请输入8-15位的密码，可包含a-z，A-Z_.</label>
						</td>

					</tr>
					<tr>
						<td align="right"><label>验证码：<input name="verifyCode"
								id="veritfyCodeInput" class="userSelfInfoInput" placeholder="请输入登验证码" />
						</label></td>
						<td align="left"><label id="veritfyCodelLal">请输入验证码</label></td>

					</tr>
					<tr>
						<td colspan="2" id="verifyImg_td"><img id="verifyImg"
							src="<c:url value='/pc/verifyAction!getVerifyCode.action'/>" />
						</td>
					</tr>
					<tr>
						<td colspan="2"><input id="userSelfInfoBtn" type="button"
							value="用户信息" /></td>


					</tr>
				</table>


				<div class="form_div">
					
				</div>
				
				<div id="veritfyImg_div" class="form_div"></div>
				
				<div class="form_div">
					<input type="radio" />
				</div>
				
				<div id="form_sub_btn" class="form_div">
					
				</div>
			
			</form>
		--%>
			<%--
		左部内容
		--%>
			<div id="userSelfInfo_div_left">
				<table id="userSelfInfo_table">
					<tr>
						<td><img id="userSelfInfo_head_img"
							src="<c:url value='/pc/front/fUsersAction!getUserHeadImg.action?userid=${sessionScope.user.userid}&headImgSize=162'></c:url>"/></td>
					</tr>
					<%--<tr>
						<td id="userSelfInfo_quike_way">快捷操作</td>
					</tr>
					--%><tr>
						<td class="userSelfInfo_oper_td"><a href="javascript:void(0);"
							id="userSelfInfo_chg_basicInfo">基本信息</a></td>
					</tr>
					<tr>
						<td class="userSelfInfo_oper_td"><a href="#"
							id="userSelfInfo_chg_pwd">修改密码</a></td>
					</tr>
					<tr>
						<td class="userSelfInfo_oper_td"><a href="#"
							id="userSelfInfo_chg_head">修改头像</a></td>
					</tr>

				</table>
			</div>
			<%--
		右部内容
		--%>
			<div id="userSelfInfo_div_right">
				<div class="chg_views" id="chg_basicInfo_view">
					<div id="basicInfo_title">基本信息</div>
					<form  id="basicInfo_form"><%--
						储存userid
						--%>
						<input name="userid" value="${sessionScope.user.userid}" style="display: none;"/>
						<table id="basicInfo_table">
							<tr>
								<td align="right">
									性别：<%--
									暂存sex，利于js操作
									--%>
									<input id="session_user_sex" style="display: none;" value="${sessionScope.user.sex}"/>
								</td>
								<td align="left">
									<label><input name="sex" value="男" type="radio"/>男</label>
									&nbsp;
									&nbsp;	
									&nbsp;
									<label><input name="sex" value="女" type="radio"/>女</label>
									&nbsp;
									&nbsp;	
									&nbsp;
									<label><input name="sex" value="未知" type="radio"/>未知</label>
								</td>
							</tr>
							<tr>
								<td align="right">
									昵称：
								</td>
								<td align="left">
									<input readOnly="true" class="readonlyBasicInfoInput" name="username" value="${sessionScope.user.username}"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									邮箱：
								</td>
								<td align="left">
									<input readOnly="true" class="readonlyBasicInfoInput" name="email" value="${sessionScope.user.email}"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									生日：<%--
									暂存birthday，利于js操作
									--%><input id="session_user_birthday" style="display: none;" value="${sessionScope.user.birthday}"/>
								</td>
								<td align="left">
									<select id="birthday_year">
										
									</select>
									<select id="birthday_month">
										
									</select>
									<select id="birthday_day">
										
									</select>
								</td>
							</tr>
							<tr>
								<td align="right">
									年龄：
								</td>
								<td align="left">
									<input readOnly="true" class="readonlyBasicInfoInput" name="age" value="${sessionScope.user.age}"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									签名：
								</td>
								<td  align="left">
									<textarea placeholder="请输入签名0/255" id="introId" name="intro">${sessionScope.user.intro}</textarea>
								</td>
								
							</tr>
							
							<tr>
								
								<td align="center" colspan="2">
									<input id="basicInfo_save_btn" value="保存" type="button"/>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="chg_views" id="chg_password_view">
					<div id="password_title">密码修改</div>
					
					<div id="password_content">
						<div>
							<label>请验证您的邮箱，然后登录邮箱修改密码：</label>
						</div>
						<div>
							<input id="password_emailInput" placeholder="请输入您的邮箱" /><input
								id="password_sendBtn" type="button" value="发送" />
						</div>
					</div>
				</div>
				<div class="chg_views" id="chg_head_view">
					<div id="head_title">头像信息</div>
					<div id="head_content">
						<jsp:include page="./uploadHeadImg.html"></jsp:include>
					</div>
				</div>
			
			</div>
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
	src='<c:url value='/pc/front/js/userSelfInfo.js'></c:url>'></script>
<script type="text/javascript"
	src='<c:url value='/pc/front/js/msgWin.js'></c:url>'></script>
</html>
