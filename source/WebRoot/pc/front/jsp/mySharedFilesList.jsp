<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sharedcloud.pc.model.*"%>
<%@ page autoFlush="true" buffer="1094kb"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="mySharedFilesListView">
	<%--
	上部导航操作栏
	--%>
	<div id="mySharedFilesOperNav">
		<%--
		操作按钮Div
		--%>
		<div id="operBtnNavDiv">
			<button id="copySharedFilesLinkBtn" class="btn btn-primary bg-white" type="button">
				<i class="fa fa-link"></i> &nbsp;复制连接
			</button>
			<button id="deleteSharedFilesBtn" class="btn btn-primary bg-white" type="button">
				<i class="fa fa-remove"></i> &nbsp;取消分享
			</button>
			
		</div>
		<%--
		操作提示Div
		--%>
		<div id="mySharedTiperDiv">
			<div id="mySharedTiperMsg">操作执行中</div>
			<img src="<c:url value='/pc/front/img/icon/loading.gif'/>" />
		</div>
	</div>
	<%--
	文件列表
	--%>
	<div id="mySharedFilesListTableDiv">
		<table id="mySharedFilesListTable">
			<tr id="sortTitlesTd">
				<td class="sortTitles" id="mySharedFilesSortFileName" order="filename">文件名</td>
				<td class="sortTitles" id="mySharedFilesSortFileSize" order="filesize">大小</td>
				<td class="sortTitles" id="mySharedFilesSortIspublic" order="ispublic">连接类型</td>
				<td class="sortTitles" id="mySharedFilesSortBrowseNumber" order="browsenumber">浏览次数</td>
				<td class="sortTitles" id="mySharedFilesSortSaveNumber" order="savenumber">保存次数</td>
				<td class="sortTitles" id="mySharedFilesSortDownloadNumber" order="downloadnumber">下载次数</td>
				<td class="sortTitles" id="mySharedFilesSortSharedTime" order="sharedtime">日期</td>
			</tr>
			<%--
			<tr sharedFilesId='1231' title="aaa" class="sharedFilesTrs">
				<td class="sharedFilesTds">
					<div class="sharedFilesIconDiv">
						<img class="sharedFilesIcon"
							src='<c:url value='/pc/front/img/icon/directoryIcon.jpg'></c:url>' />
					</div>
					<div class="sharedFilesNameDiv">
						<label>fallout5</label>
					</div>
				</td>
				<td>-</td>
				<td>公共连接</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>1995-12-17 12:12:31</td>
			</tr>
			--%>
		</table>
	</div>
</div>