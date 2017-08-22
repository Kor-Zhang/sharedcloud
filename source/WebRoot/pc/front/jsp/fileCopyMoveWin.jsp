<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
	保存request信息
--%>
<label id="request_fileCopyMove_lal" style="display:none;">${requestScope.fileCopyMove}</label>
<div id="fileCopyMoveWin">
	<div id="fileCopyMoveShower">
		<div id="fileCopyMoveWinTitle">
			复制
		</div>
		<div id="directoryListDiv">
			<ul id="directoryTree">
				<li id="parentTreeNode" treeNodeId="undefined" level="1" class="treeNodes selectedTreeNode">
					<span class="childIcons closedTreeNode"></span>
					<img class="treeNodeDirectoryIcon" src='<c:url value='/pc/front/img/icon/directoryIcon.jpg'></c:url>' />
					<span class="treeNodeText">全部文件夹</span>
				</li>
			</ul>
		</div>
		<div id="operBtnsDiv">
			<input id="newDirectoryBtn" type="button" value="新建文件夹"/>
			<input id="confirmCopyMoveBtn" type="button" value="确认"/>
			<input id="cancelCopyMoveBtn" type="button" value="取消"/>
		</div>
	</div>

</div>

