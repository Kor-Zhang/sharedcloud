<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>

<title>SharedCloud Info</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/global.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/loadingModel.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/info.css'></c:url>">
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
	
	失败内容
	
	--%>
	<div id="info_content">
		<div id="info_nav">
			<div id="info_nav_logo">
				<div>
					<img src="<c:url value='/pc/front/img/icon/logo.jpg'></c:url>">
				</div>

				<div>云共享</div>
			</div>
			<div id="fg">|</div>
			<div id="title">失败</div>
			<div id="toLogin">
				返回，<a href='<c:url value='/pc/front/jsp/login.jsp'></c:url>'>登录</a>
			</div>
		</div>
		<%--
		
		详细的失败信息
	--%>
		<div id="info_div">
			
			${msg}
		
		</div>
	</div>

</body>
<script type="text/javascript" charset="utf-8"
	src='<c:url value='/pc/plug-in/jquery-3.1.1/jquery.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8"
	src='<c:url value='/pc/front/js/global.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8"
	src='<c:url value='/pc/front/js/loadingModel.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8"
	src='<c:url value='/pc/front/js/info.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8"
	src='<c:url value='/pc/front/js/msgWin.js'></c:url>'></script>
</html>
