<!--
	Author:Kor_Zhang 
	Date:2016/12
-->
<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sharedcloud.pc.model.*" %>
<%@ page autoFlush="true" buffer="1094kb"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>云共享</title>
<%--
导入hAdmin字体
--%>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/plug-in/hAdmin/css/hAdminFonts.css'></c:url>">	


<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/global.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/loadingModel.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/fileCopyMoveWin.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/confirmWin.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/createShareingWin.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/msgWin.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/nav.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/index.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/mySharedFilesList.css'></c:url>">

	
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/plug-in/plupload/js/jquery.plupload.queue/css/jquery.plupload.queue.css'></c:url>">
</head>
<body>
	<%--
	上传组件
	--%>
	<div id="uploadCom">
		<div id="uploadCom_closeBtn">X</div>
		<div id="uploader">
			<%--<p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>
			--%>
		</div>
		
	</div>
	<%--
	导航栏
	--%>
	<div id="index_nav">
		<jsp:include page="./nav.jsp"></jsp:include>
	</div>
	<%--
	加载界面
	--%>
	<jsp:include page="./loadingModel.jsp"></jsp:include>
	<%--
	消息提示框
	--%>
	<jsp:include page="./msgWin.jsp"></jsp:include>
	<%--
	消息确认提示框
	--%>
	<jsp:include page="./confirmWin.jsp"></jsp:include>
	<%--
	移动显示框
	--%>
	<jsp:include page="./fileCopyMoveWin.jsp"></jsp:include>
	<%--
	分享弹出框
	--%>
	<jsp:include page="./createShareingWin.jsp"></jsp:include>
	<div id="inedx_content">
		<%--
		左部
		--%>
		<div id="inedx_content_left">
			<ul id="labels_list">
				<li class="labs">
					<a  class="extBtns" id="totalExtBtn" href="#"><i class="fa fa-clipboard">
						</i>&nbsp;&nbsp;全部文件
					</a>
				</li>

				<%--细分全部文件--%>
				<li class="labs totalFileDetail">
					<a class="extBtns" id="picExtBtn" exts="<%=UploadHistory.PICEXTS%>" href="#">
						<i class="fa fa-file-image-o"></i>&nbsp;&nbsp;图片
					</a>
				</li>
				<li class="labs totalFileDetail">
					<a class="extBtns" id="txtExtBtn" exts="<%=UploadHistory.TXTEXTS%>"  href="#">
						<i class="fa fa-file-text-o"></i>&nbsp;&nbsp;文档
					</a>
				</li>
				<li class="labs totalFileDetail">
					<a class="extBtns" id="videoExtBtn" exts="<%=UploadHistory.VIDEOEXTS%>"  href="#">
						<i class="fa fa-file-video-o"></i>&nbsp;&nbsp;视频
					</a>
				</li>
				<li class="labs totalFileDetail">
					<a class="extBtns" id="torExtBtn" exts="<%=UploadHistory.TOREXTS%>"  href="#">
						<i class="fa fa-file-archive-o"></i>&nbsp;&nbsp;种子
					</a>
				</li>
				<li class="labs totalFileDetail">
					<a class="extBtns" id="musicExtBtn" exts="<%=UploadHistory.MUSICEXTS%>"  href="#">
						<i class="fa fa-file-sound-o"></i>&nbsp;&nbsp;音乐
					</a>
				</li>
				<li class="labs totalFileDetail">
					<a class="extBtns" id="zipExtBtn" exts="<%=UploadHistory.ZIPEXTS%>"  href="#">
						<i class="fa fa-file-zip-o"></i>&nbsp;&nbsp;压缩包
					</a>
				</li>
				<li class="labs totalFileDetail">
					<a class="extBtns" id="otherExtBtn" exts="<%=UploadHistory.OTHEREXTS%>"  href="#">
						<i class="fa fa-file-excel-o"></i>&nbsp;&nbsp;其他
					</a>
				</li>

				<li class="labs">
					<a id="mySharedBtn">
						<i class="fa fa-cloud"></i>&nbsp;&nbsp;我的分享
					</a>
				</li>
				<%--
				<li class="labs"><a  class="extBtns" id="totalExtBtn" href="#"><span id="total_file_ico">+</span>全部文件</a>
				</li>
				<li class="labs"><a href="#"><i id="total_file_myshare">☁</i>我的分享</a>
				</li>
				<li class="labs"><a href="#"><span id="total_file_rubish"></span>回收站&nbsp;&nbsp;&nbsp;</a>
				</li>
			--%>
			</ul>
			<div id="index_content_left_totalSize">
				<%--进度条--%>
				<div id="totalSizeProgress">
					<div id="totalProgress">
						<div id="usedProgress"></div>
					</div>
				</div>
				<div id="totalSizeNumber">

					<span>容量：</span> <span id="usedSize"><%--${sessionScope.user.savedfilesize}--%></span>
					<span>/</span> <span id="totalSize"><%--${sessionScope.user.totalfilesize}--%></span>
				</div>
			</div>
		</div>
		<%--
		右部内容
		--%>
		<div id="index_content_right">
			<%--
			导入文件列表dom
			--%>
			<jsp:include page="./filesList.jsp"></jsp:include>
			<%--
			导入我的分享文件列表dom
			--%>
			<jsp:include page="./mySharedFilesList.jsp"></jsp:include>
		</div>
	</div>
</body>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/plug-in/jquery-3.1.1/jquery.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src="<c:url value='/pc/plug-in/jquery.zclip.1.1.1/jquery.zclip.min.js'></c:url>"></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/global.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/loadingModel.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/fileCopyMoveWin.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/confirmWin.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/msgWin.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/createShareingWin.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/nav.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/index.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/mySharedFilesList.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src="<c:url value='/pc/plug-in/plupload/js/jquery.plupload.queue/jquery.plupload.queue.js'></c:url>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value='/pc/plug-in/plupload/js/plupload.full.js'></c:url>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value='/pc/plug-in/plupload/js/i18n/cn.js'></c:url>"></script>
<%--
用于嫁接jsp和js参数
--%>
<script type="text/javascript">
	//保存session中的userid信息，js将使用	
	var sessionUserid="${sessionScope.user.userid}";
	//获取服务器的文件后缀信息
	var musicExts ="<%=UploadHistory.MUSICEXTS%>";
	var otherExts ="<%=UploadHistory.OTHEREXTS%>";
	var picExts ="<%=UploadHistory.PICEXTS%>";
	var torExts ="<%=UploadHistory.TOREXTS%>";
	var txtExts ="<%=UploadHistory.TXTEXTS%>";
	var zipExts ="<%=UploadHistory.ZIPEXTS%>";
	var videoExts ="<%=UploadHistory.VIDEOEXTS%>";
	var directoryExts ="<%=UploadHistory.DIRECTORYExts%>";
		
</script>
</html>
<%--
必须放置重定（转发）向到主页面，不能放置在include的页面中
--%>
<c:if test="${empty sessionScope.user}">
	<%--
	如果用户不在线，那么查找cookie以寻求登录信息
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
			if(true == flag){
				//转发到cookie登录
				//response.sendRedirect(this.getServletContext().getContextPath()+"/pc/front/fUsersAction!loginByCookie.action?"+Users.COOKIE_USERID_NAME+"="+c.getValue());
				request.getRequestDispatcher("/pc/front/fUsersAction!loginByCookie.action?forwardJsp=index&userid="+userid+"&password="+password).forward(request, response);
			}
			//如果没有cookie存在,那么跳转登录
			if(false == flag){
				%>
				<jsp:forward page="./login.jsp"></jsp:forward>
				<%
			}
		}else{
			%>
			<jsp:forward page="./login.jsp"></jsp:forward>
			<%
		}
		
	%>
</c:if>
