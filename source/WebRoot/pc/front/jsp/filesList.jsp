<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.sharedcloud.pc.model.*"%>
<%@ page autoFlush="true" buffer="1094kb"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="filesListView">
	<%--
	上部导航栏
	--%>
	<div id="index_content_right_nav">
		<div id="index_content_right_nav_left">
			<button id="uploadBtn" class="btn btn-primary bg-blue" type="button">
				<i class="fa fa-upload"></i> &nbsp;上传
			</button>
			<button id="newFileBtn" class="btn btn-primary bg-blue" type="button">
				<i class="fa fa-check"></i> &nbsp;新建文件夹
			</button>
			<button id="cancelBtn" class="btn btn-primary bg-blue" type="button">
				<i class="fa fa-remove"></i> &nbsp;取消
			</button>
			<button id="sharedBtn" class="btn btn-primary bg-white" type="button">
				<i class="fa fa-share-alt"></i> &nbsp;分享
			</button>
			<button id="downloadBtn" class="btn btn-primary bg-white"
				type="button">
				<i class="fa fa-download"></i> &nbsp;下载
			</button>
			<button id="deleteBtn" class="btn btn-primary bg-white" type="button">
				<i class="fa fa-remove"></i> &nbsp;删除
			</button>
			<button id="renameBtn" class="btn btn-primary bg-white" type="button">
				<i class="fa fa-bars"></i> &nbsp;重命名
			</button>
			<button id="moveBtn" class="btn btn-primary bg-white" type="button">
				<i class="fa fa-recycle"></i> &nbsp;移动
			</button>
		</div>
		<div id="index_content_right_nav_right">
			<input id="searchYourFileInput" placeholder="搜索文件/文件夹（不区分大小写）"
				type="text" />
		</div>
	</div>
	<%--
	下部内容显示
	--%>
	<div id="index_content_right_filelist">
		<%--
		概要显示
		<div id="index_content_right_filelist_title">
			
		</div>
		--%>

		<%--
		table的div
		--%>
		<div id="backNavDiv">
			<div id="backBtnDiv">
				<input id="backBtn" type="button" value="返回上一级" />
			</div>
			<div id="file_positionDiv">
				<input type="button" class="upFileItems" value="全部文件" />
			</div>
			<div id="operFileLoading">
				<div id="operFileLoadingMsg">操作执行中</div>
				<img src="<c:url value='/pc/front/img/icon/loading.gif'/>" />
			</div>
		</div>
		<div id="index_content_right_filelist_table_div">
			<table id="index_content_right_filelist_table">
				<tr>
					<td id="fillist_title_filename" order="filename">文件名</td>
					<td id="fillist_title_filesize" order="filesize">大小</td>
					<td id="fillist_title_uploadtime" order="uploadtime">日期</td>
				</tr>

			</table>
		</div>
	</div>
</div>