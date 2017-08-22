var fileCopyMoveWin;
var fileCopyMoveShower;
var confirmCopyMoveBtn;
var cancelCopyMoveBtn;
//对话框的显示和隐藏时间
var copyMoveInOutTime = 400;
var fileCopyMoveWinTitle;
//目录的tree
var directoryTree;
//所有树节点
var treeNodes;
//当前单击的节点
var currtClickTreeNodeDom = undefined;
var childIcons;
//，点击关闭按钮的回调方法
var fileCopyMoveWinCloseCallFun;

var gMovedFileOrDirectoryId;
$(function($){
	initFileCopyMoveWinDoms();
	setFileCopyMoveWinUI();
	setFileCopyMoveWinLis();
});
/**
 * 通过树节点获取其数据库id
 * @param dom
 */
function getTreeNodeIdByTreeNodeDom(dom){
	return dom.attr("treeNodeId");
}
/**
 * 通过树节点的数据库id获取其dom
 * @param id
 */
function getTreeNodeDomByTreeNodeId(id){
	var treeNodeDom = directoryTree.find("li[treeNodeId='"+id+"']");
	return treeNodeDom;
}


/**
 * 根据文件夹的id加载其子节点；<br/>
 * pId：加载的是该id的子节点；<br/>
 * refresh：如果存在了列表，是否重新连接数据库获取；<br/>
 */
function loadDirectorysByPId(pId,refresh){
	//如果列表存在信息，更具参数决定是否重新连接数据库获取
	if(directoryTree.find("li[pId='"+pId+"']").length>1&&(refresh==undefined||refresh==false)){
		getChildByTreeNodeDom(getTreeNodeDomByTreeNodeId(pId)).slideDown("slow");
		return;
	}
	//重新加载信息；refresh==true
	var parameters = new Object();
	//参数：父文件夹id
	parameters["pId"] = pId;
	//参数：不要加载的文件夹id
	parameters["id"] = gMovedFileOrDirectoryId;
	zkAjaxData("/pc/front/fUploadHistoryAction!getDirectorysByPId.action",parameters, function(data){
		
		//加载成功
		if(data.success){
			//清空文件夹列表；保留第一个li节点
			clearChilds(getTreeNodeDomByTreeNodeId(pId));
			var dirs = data.obj;
			for(var i=0;i<dirs.length;i++){
				var dir = dirs[i];
				var parameters = new Object();
				//设置参数
				parameters["level"] = (Number)(getTreeNodeDomByTreeNodeId(pId).attr("level"))+1;
				parameters["child"] = dir.child;
				parameters["text"] = dir.filename;
				parameters["treeNodeId"] = dir.id;
				parameters["pId"] = pId;
				//添加节点
				addTreeNode(parameters);
			}
		}else{
			
		}
	}, true);
}
/**
 * 递归清除子节点
 * @param pId
 */
function clearChilds(pDom){
	var childs = getChildByTreeNodeDom(pDom);
	for(var i = 0;i<childs.length;i++){
		var child = $(childs[i]);
		//递归
		closeDirs(child);
		//拒绝清除“全部文件”
		if(child.attr("treeNodeId")!="undefined"){
			child.remove();
		}
		
	}
}
/**
 * 初始化dom节点
 */
function initFileCopyMoveWinDoms(){
	fileCopyMoveWin = $("#fileCopyMoveWin");
	fileCopyMoveShower = $("#fileCopyMoveShower");
	confirmCopyMoveBtn = $("#confirmCopyMoveBtn");
	cancelCopyMoveBtn = $("#cancelCopyMoveBtn");
	fileCopyMoveWinTitle = $("#fileCopyMoveWinTitle");
	directoryTree = $("#directoryTree");
	treeNodes = $(".treeNodes");
	//设置默认选择
	selectTreeNode(treeNodes.eq(0));
	childIcons = $(".childIcons");
	
	
}
/**
 * 初始化dom节点
 */
function setFileCopyMoveWinUI(){
	fileCopyMoveWin.height(html.height());
	fileCopyMoveWin.width(html.width());
	//设置显示信息居中
	setAbsolution(fileCopyMoveShower);
	fileCopyMoveShower.css("top",html.height()/2-fileCopyMoveShower.height()/2);
	fileCopyMoveShower.css("left",html.width()/2-fileCopyMoveShower.width()/2);
	
}
/**
 * 设置监听事件
 */
function setFileCopyMoveWinLis(){
	//设置取消按钮点击事件
	setCancelCopyMoveBtnClickLis();
	//设置确定按钮点击事件
	setConfirmCopyMoveBtnClickLis();
	//设置每个节点的点击事件
	setTreeNodesClickLis();
	//设置每个节点的孩子图标的点击事件
	setChildIconClickLis();
}

/**
 * 设置每个节点的点击事件（改变背景色）
 */
function setTreeNodesClickLis(){
	treeNodes.unbind("click");
	treeNodes.each(function(){
		$(this).click(function(){
			selectTreeNode($(this));
		});
	});
}
/**
 * 设置被选择的节点样式
 * 
 * @param dom
 */
function selectTreeNode(dom){
	currtClickTreeNodeDom = $(dom);
	//设置点击的节点的背景
	treeNodes.removeClass("selectedTreeNode");
	$(dom).addClass("selectedTreeNode");
}
/**
 * 设置文件夹“关闭”“打开”图标触发事件；引发加载数据；
 */
function setChildIconClickLis(){
	childIcons.unbind("click");
	childIcons.each(function(){
		$(this).click(function(){
			//设置当前点击的文件夹节点的关闭，打开图标
			var clazz = $(this).attr("class");
			var haveClosedClazz = clazz.indexOf("closedTreeNode")>=0; 
			var haveOpenedClazz = clazz.indexOf("opendTreeNode")>=0; 
			if(haveClosedClazz){
				$(this).removeClass("closedTreeNode").addClass("opendTreeNode");
				//加载数据
				var pId = getTreeNodeIdByTreeNodeDom($(this).parent());
				/*addTreeNode({"text":"123","level":"2","child":"true","treeNodeId":"123"});*/
				//“全部文件的”id为"undefined"
				loadDirectorysByPId(pId=="undefined"?undefined:pId,true);
			}else if(haveOpenedClazz){
				$(this).removeClass("opendTreeNode").addClass("closedTreeNode");
				//隐藏所有的子文件夹
				closeDirs(getChildByTreeNodeDom($(this).parent()));
			}
			
		});
	});
}
/**
 * 根据节点列表递归关闭所有的子节点；（传递的初始参数也将被关闭）<br/>
 * 并且设置其闭合图标；<br/>
 * 不包含“全部文件”；<br/>
 * @param pDom
 */
function closeDirs(pDom){
	//如果有开闭图片，设置其图片为闭合
	var clazz = pDom.find(".childIcons").attr("class");
	var haveClosedClazz = clazz.indexOf("opendTreeNode")>=0; 
	if(haveClosedClazz){
		//设置闭合图标
		pDom.find(".childIcons").removeClass("opendTreeNode").addClass("closedTreeNode");
	}
	//不为“全部文件”即可隐藏
	if(pDom.attr("treeNodeId")!="undefined"){
		//隐藏
		pDom.slideUp("slow");
	}
	//递归子标签
	var childs = getChildByTreeNodeDom(pDom);
	for(var i = 0;i<childs.length;i++){
		var child = $(childs[i]);
		//递归
		closeDirs(child);
	}
	
}
/**
 * 通过树节点获取其子节点
 * @param dom
 * @returns
 */
function getChildByTreeNodeDom(dom){
	return directoryTree.find("li[pId='"+getTreeNodeIdByTreeNodeDom(dom)+"']");
}
/**
 * 添加节点
 * @param parameters
 * text:节点名称；<br/>
 * level：节点的层级，"全部文件夹"为1；<br/>
 * child：true,false;代表是否还有子孙节点；该参数会影响打开，关闭图标；<br/>
 * treeNodeId：新添加的节点的id（数据库的id）；<br/>
 */
function addTreeNode(parameters){
	/*<li id="parentTreeNode" class="treeNodes closedTreeNode selectedTreeNode">
		<img class="treeNodeDirectoryIcon" src='<c:url value='/pc/front/img/icon/directoryIcon.jpg'></c:url>' />
		<span class="treeNodeText">全部文件夹</span>
	</li>*/
	//根据本文件的等级设置左边距
	var padding_left = (parameters.level)*13;
	//根据是否还有子孙节点设置闭合的图标
	var childIconsClass = parameters.child==true?"closedTreeNode":"";
	var li = $("<li pId='"+parameters.pId+"' treeNodeId='"+parameters.treeNodeId+"' level='"+(parameters.level)+"' style='padding-left:"+padding_left+"px' class='treeNodes'></li>");
	var childIcon = $("<span class='childIcons "+childIconsClass+"'></span>");
	var img = $("<img class='treeNodeDirectoryIcon' src='"+getWebProjectName()+"/pc/front/img/icon/directoryIcon.jpg'></img>");
	var span = $("<span class='treeNodeText'>"+parameters.text+"</span>");
	
	li.append(childIcon);
	li.append(img);
	li.append(span);
	//在父节点后追加节点
	getTreeNodeDomByTreeNodeId(parameters.pId).after(li);
	
	//重新加载节点和触发事件
	treeNodes = $(".treeNodes");
	childIcons = $(".childIcons");
	setTreeNodesClickLis();
	setChildIconClickLis();
}
/**
 * 设置取消按钮点击事件
 */
function setCancelCopyMoveBtnClickLis(){
	cancelCopyMoveBtn.unbind("click");
	cancelCopyMoveBtn.click(function(){
		hideCopyMoveWin();
		
		//关闭，清除子节点
		closeDirs(getTreeNodeDomByTreeNodeId("undefined"));
		clearChilds(getTreeNodeDomByTreeNodeId("undefined"));
	});
}
/**
 * 设置确定按钮点击事件
 */
function setConfirmCopyMoveBtnClickLis(){
	confirmCopyMoveBtn.unbind("click");
	confirmCopyMoveBtn.click(function(){
		hideCopyMoveWin();

		//关闭，清除子节点
		closeDirs(getTreeNodeDomByTreeNodeId("undefined"));
		clearChilds(getTreeNodeDomByTreeNodeId("undefined"));
		//回调方法：被移动的uploadhistory的id，当前选择的移动到的数据库id 
		fileCopyMoveWinCloseCallFun(gMovedFileOrDirectoryId,getTreeNodeIdByTreeNodeDom(currtClickTreeNodeDom));
		
	});
}

/**
 * 显示对话框；<br/>
 * movedFileOrDirectory：被移动的uploadhistory的id；<br/>
 * title：显示内容；<br/>
 * confirmCallFun：点击确认后的回调方法；<br/>
 */
function showCopyMoveWin(title,movedFileOrDirectoryId,confirmCallFun){
	fileCopyMoveWinTitle.html(title);
	fileCopyMoveWin.fadeIn(copyMoveInOutTime);
	//设置回调方法
	fileCopyMoveWinCloseCallFun= confirmCallFun;
	gMovedFileOrDirectoryId = movedFileOrDirectoryId;

	//设置默认选择
	selectTreeNode(treeNodes.eq(0));
}
/**
 * 隐藏对话框
 */
function hideCopyMoveWin(){
	fileCopyMoveWin.fadeOut(copyMoveInOutTime);
}
