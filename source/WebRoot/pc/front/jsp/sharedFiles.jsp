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
	href="<c:url value='/pc/front/css/inputSharedPwdWin.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/confirmWin.css'></c:url>">
<%--<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/createShareingWin.css'></c:url>">
--%><link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/msgWin.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/nav.css'></c:url>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/pc/front/css/sharedFiles.css'></c:url>">
	
</head>
<body id="sharedFilesBody">
	
	<%--
	导航栏
	--%>
	<div id="sharedFiles_nav">
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
	分享密码输入框
	--%>
	<jsp:include page="./inputSharedPwdWin.jsp"></jsp:include>
	<div id="sharedFiles_content">
		<%--
		左部
		--%>
		<div id="sharedFiles_content_left">
			<%--
			导航
			--%>
			<div id="sharedFiles_content_left_nav">
				<div id="sharedFiles_content_left_nav_left">
					<div id="sharedFilesImgAndFileNameDiv">
						<div class="sharedFilesDirectoryIcon">
							<img id="sharedFilesIcon" alt="" src=''/>
						</div>
						<div id="sharedFilesFileName">
							辐射4
						</div>
					</div>
					
					<div  id="sharedFilesOtherInfo">
						<label>下载：</label><span id="sharedFilesDownloadNumber">4654</span>
						<label>浏览：</label><span id="sharedFilesBrowseNumber">4654</span>
						<label>保存：</label><span id="sharedFilesSaveNumber">4654</span>
						<label>分享日期：</label><span id="sharedFilesTime">2014-04-13</span>
					</div>
				</div>
				<%--
				中导航：设置加载显示
				--%>
				<div id="sharedFiles_content_left_nav_center">
					<div id="sharedFilesTiper">
						<div id="sharedFilesTiperMsg">操作执行中</div>
						<img src="<c:url value='/pc/front/img/icon/loading.gif'/>" />
					</div>
				</div>
				<%--
				右部导航：设置操作按钮
				--%>
				<div id="sharedFiles_content_left_nav_right">
					<button id="sharedFilesDonloadBtn" class="btn btn-primary sharedFilesOperBtns" type="button">
						<i class="fa fa-download"></i>
						&nbsp;下载
					</button>
					<button id="sharedFilesSaveBtn" class="btn btn-primary sharedFilesOperBtns" type="button">
						<i class="fa fa-save"></i>
						&nbsp;保存
					</button>
					
					<%--
					
					<input class="sharedFilesOperBtns" id="sharedFilesDonloadBtn" value="下载" type="button" /> 
					<input class="sharedFilesOperBtns" id="sharedFilesSaveBtn" value="保存" type="button" />
					
					--%>
				</div>
				
			</div>
			<%--
			文件列表
			--%>
			<div id="sharedFilesLit">
				<%--
				文件位置导航
				--%>
				<div id="sharedFilesPositionDiv">
					<span id="sharedFilesBackUp">返回上一级</span>
					<span id="allFilesPosition" class="upFileItems" parentId="">全部文件</span>
				</div>
				<div id="sharedFilesListTableDiv">
					<table id="sharedFilesListTable">
						<tr>
							<td id="fillist_title_filename" order="filename">文件名</td>
							<td id="fillist_title_filesize" order="filesize">大小</td>
							<td id="fillist_title_uploadtime" order="uploadtime">日期</td>
						</tr>
						<%--
						<tr id="aaaa" class="file_list_tr">
							<td class="directory_Name_Icon_td">
								<div class="directoryIcon_div">
									<img class="directoryIcon" src='<c:url value='/pc/front/img/icon/directoryIcon.jpg'></c:url>'/>
								</div>
								<div class="directoryName_div">
									<label>fallout5</label>
								</div>
								
							</td>
							<td>-</td>
							<td>1995-12-17 12:12:31</td>
						</tr>
						<tr id="bbbb" class="file_list_tr">
							<td class="directory_Name_Icon_td">
								<div class="directoryIcon_div">
									<img class="directoryIcon" src='<c:url value='/pc/front/img/icon/directoryIcon.jpg'></c:url>'/>
								</div>
								<div class="directoryName_div">
									<label>fallout11</label>
								</div>
								
							</td>
							<td>-</td>
							<td>1995-12-17 12:12:31</td>
						</tr>
					--%></table>
				</div>
			</div>
			
		</div>
		<%--
		右部
		--%>
		<div id="sharedFiles_content_right">
			这里应该写点什么
		</div>
	</div>
</body>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/plug-in/jquery-3.1.1/jquery.js'></c:url>'></script>
<%--<script type="text/javascript" charset="utf-8" src="<c:url value='/pc/plug-in/jquery.zclip.1.1.1/jquery.zclip.min.js'></c:url>"></script>
--%>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/global.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/loadingModel.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/fileCopyMoveWin.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/inputSharedPwdWin.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/confirmWin.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/msgWin.js'></c:url>'></script>
<%--<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/createShareingWin.js'></c:url>'></script>
--%>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/nav.js'></c:url>'></script>
<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/sharedFiles.js'></c:url>'></script>
<%--<script type="text/javascript" charset="utf-8" src='<c:url value='/pc/front/js/index.js'></c:url>'></script>
--%><%--<script type="text/javascript" charset="utf-8" src="<c:url value='/pc/plug-in/plupload/js/jquery.plupload.queue/jquery.plupload.queue.js'></c:url>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value='/pc/plug-in/plupload/js/plupload.full.js'></c:url>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value='/pc/plug-in/plupload/js/i18n/cn.js'></c:url>"></script>
--%>
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
		
	
	
	var sharedFile = <%=request.getAttribute("")%>
	
	
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
				request.getRequestDispatcher("/pc/front/fUsersAction!loginByCookie.action?forwardJsp=sharedFiles&userid="+userid+"&password="+password).forward(request, response);
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
