<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sharedcloud.pc.model.Users" %>
<%@ page autoFlush="true" buffer="1094kb"%>
<div id="nav_nav">
	<div id="nav_logo">
		<div>
			<img src="<c:url value='/pc/front/img/icon/logo.jpg'></c:url>">
		</div>
		
		<div>
			云共享
		</div>
	</div>
	<div id="nav_labels">
		<%--<div class="nav_label">
			<a href="#">网盘</a>
		</div>
		<div class="nav_label">
			<a href="#">分享</a>
		</div>
		<div class="nav_label">
			<a href="#">更多</a>
		</div>
	--%></div>
	<div id="nav_user">
		<c:if test="${not empty sessionScope.user}">
			<div id="user_head_div">
				<img id="user_head_img" src="<c:url value='/pc/front/fUsersAction!getUserHeadImg.action?userid=${sessionScope.user.userid}&headImgSize=48'></c:url>"/>
			</div>
			<div id="user_name_div">
				<a id="user_name_a" href='<c:url value='/pc/front/jsp/userSelfInfo.jsp'></c:url>'>${sessionScope.user.username}</a>
			</div>
			<div id="user_logout_div">
				<a id="user_logout_btn" onClick="clickLogoutBtn();" href="javascript:void(0);">注销</a>
			</div>
		</c:if>
		
	</div>
	<div id="nav_other">
		
		<a href="#">
			<div id="sub_adv_div">
			
			</div>
		</a>
	</div>
</div>


