<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
	保存request信息
--%>
<label id="request_fileCopyMove_lal" style="display:none;">${requestScope.fileCopyMove}</label>
<div id="createShareingWin">
	<div id="shareingShower">
		<div id="createShareingWinTitle">
			分享
		</div>
		<div id="infoDisplayDiv">
			<div class="displayDiv" id="chooseSharedLinkTypeDiv">
				<div id="shareingIntroDiv">
					生成下载链接，然后复制链接发送到QQ等好友
				</div>
				<div id="shareingBtnsDiv">
					<div id="createPublicShareingBtnDiv">
						<input id="createPublicShareingBtn" type="button" value="创建公开连接"/>：文件会出现在你的分享主页，其他人都能查看下载
					</div>
					<div id="createPrivateShareingBtnDiv">
						<input id="createPrivateShareingBtn" type="button" value="创建私密连接"/>：只有分享的好友能看到，其他人都看不到
					</div>
					
				</div>
			</div>
			
			
			
			<div  class="displayDiv" id="privateLinkDiv">
				<div id="createPrivateLinkResult">创建私密连接成功</div>
				<div id="privateLinkDisplayDiv">
					<input id="privateLinkUrl" type="text" readonly="readonly"/>
					<input id="privateLinkUrlCopyBtn" type="button" value="复制连接和密码"/>
					
				</div>
				<div id="privateLinkPwdDisplayDiv">
					<label>提取密码
						<input id="privateLinkUrlPwd" type="text" readonly="readonly"/>
					</label>
				</div>
				<div id="privateLinkTipDiv">
					可以将链接发送给你的QQ等好友
				</div>
			</div>
			<div  class="displayDiv" id="publicLinkDiv">
				<div id="createPublicLinkResult">创建公共连接成功</div>
				<div id="publicLinkDisplayDiv">
					<input id="publicLinkUrl" type="text" readonly="readonly"/>
					<input id="publicLinkUrlCopyBtn" type="button" value="复制连接"/>
					
				</div>
				<div id="publicLinkTipDiv">
					可以将链接发送给你的QQ等好友
				</div>
			</div>
		</div>
		<div id="operBtnsDiv">
			<%--<input id="newDirectoryBtn" type="button" value="新建文件夹"/>
			--%>
			<%--<input id="confirmCopyMoveBtn" type="button" value="确认"/>
			--%><input id="cancelShareingBtn" type="button" value="关闭"/>
		</div>
	</div>

</div>

