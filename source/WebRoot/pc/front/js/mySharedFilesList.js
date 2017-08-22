/******** index界面的嵌入页面"myShredFilesList.jsp"的js ********/
/********* 延时加载方法 *********/
$(function($){
});

/******** 申明myShredFilesList对象 *********/
var mySharedFilesList = { 
	/**** 设置属性；初始化doms；****/
	mySharedFilesListTableDiv: $("#mySharedFilesListTableDiv"), 
	mySharedFilesListTable: $("#mySharedFilesListTable"), 
	sharedFilesTrs: $(".sharedFilesTrs"), 
	sharedFilesTds: $(".sharedFilesTds"), 
	sharedFilesIconDiv: $(".sharedFilesIconDiv"), 
	sharedFilesIcon: $(".sharedFilesIcon"), 
	sharedFilesNameDiv: $(".sharedFilesNameDiv"),
	mySharedFilesOperNav:$("#mySharedFilesOperNav"),
	//操作按钮
	deleteSharedFilesBtn:$("#deleteSharedFilesBtn"),
	copySharedFilesLinkBtn:$("#copySharedFilesLinkBtn"),
	//加载消息
	mySharedTiperDiv:$("#mySharedTiperDiv"),
	mySharedTiperMsg:$("#mySharedTiperMsg"),
	/*
	 * 排序标题
	 */
	mySharedFilesSortFileName:$("#mySharedFilesSortFileName"),
	mySharedFilesSortFileSize:$("#mySharedFilesSortFileSize"),
	mySharedFilesSortSharedTime:$("#mySharedFilesSortSharedTime"),
	sortTitles:$(".sortTitles"),
	sortTitlesTd:$("#sortTitlesTd"),
	/**** 申明其他变量；****/
	/*
	 * 排序相关变量
	 */
	currtSelectSortTitle:undefined,
	ascImg:"▴",
	descImg:"▾",
	sort:"desc",
	order:"",
	defaultTiperMsg:"",
	tiperTimer:undefined,
	//记录当前单击行dom节点
	currtClickTrDom:undefined,
	//点击tr后背景切换
	trClickBg:"rgb(241,242,243)",
	//是否为第一次加载该视图
	isFirstLoadTheView:true,
	/****设置初始化方法****/
	/**
	 * 加载视图，视图切换时由index.js调用
	 */
	loadSharedFilesListView:function(){
		if(mySharedFilesList.isFirstLoadTheView){//判断是否为第一次加载该视图
			//隐藏操作按钮
			mySharedFilesList.hideOperBtns();
			//初始化dom
			mySharedFilesList.initDoms();
			//设置ui
			mySharedFilesList.setUI();
			//设置监听
			mySharedFilesList.setLis();
			
			//改变标识符
			mySharedFilesList.isFirstLoadTheView = false;
		}
		//加载数据
		mySharedFilesList.loadSharedFils(undefined, function(){
			
		});

	},
	initDoms: function initMySharedFilesListDoms(){
		//初始化默认提示者消息
		mySharedFilesList.defaultTiperMsg = mySharedFilesList.mySharedTiperMsg.html();

	},
	setLis: function setMySharedFilesListLis(){
		//设置排序图标单击事件
		mySharedFilesList.setSortTitleClickLis();
		//设置行单击事件
		mySharedFilesList.setTrsClickLis();

		//设置取消分享单击事件
		mySharedFilesList.setDeleteBtnClickLis();
		//设置复制连接单击事件
		mySharedFilesList.setCopyLinkBtnClickLis();
	},
	setUI: function setMySharedFilesListUI(){
		/*设置显示文件列表的div的高度*/
		var navHeight = nav.height();
		var mySharedFilesOperNavHeight = mySharedFilesList.mySharedFilesOperNav.height();
		mySharedFilesList.mySharedFilesListTableDiv.height(html.height()-navHeight-mySharedFilesOperNavHeight-100);
	},
	/****设置功能方法****/
	
	/**
	 * 选择排序标签
	 */
	selectedSortTitle:function(dom){
		//当前存在被选择的排序标签
		if(this.currtSelectSortTitle!=undefined){
			//点击相同的标签
			if(dom.attr("id")==this.currtSelectSortTitle.attr("id")){
				if(sort=="desc"){
					dom.html(dom.html().replace(descImg,"")+ascImg);
					sort="asc";
					order = this.currtSelectSortTitle.attr("order");
				}else{
					dom.html(dom.html().replace(ascImg,"")+descImg);
					sort="desc";
					order = this.currtSelectSortTitle.attr("order");
				}
			}else{
				//点击不相同的标签
				this.currtSelectSortTitle.html(this.currtSelectSortTitle.html().substring(0,this.currtSelectSortTitle.html().length-1));
				dom.html(dom.html().replace(ascImg,"")+descImg);
				this.currtSelectSortTitle = dom;
				
				order = this.currtSelectSortTitle.attr("order");
				sort="desc";
				
			}
			
		}else{
			//当前不存在被选择的排序标签
			dom.html(dom.html().replace(ascImg,"")+descImg);
			this.currtSelectSortTitle = dom;
			sort="desc";
			order = this.currtSelectSortTitle.attr("order");
		}
	},
	/**
	 * 加载分享文件数据
	 */
	loadSharedFils:function(sortDom,callFun){
		
		//隐藏操作按钮
		mySharedFilesList.hideOperBtns();
		//显示加载
		mySharedFilesList.showTiper("正在加载数据");
		
		if(sortDom==undefined){//默认排序
			this.selectedSortTitle(this.mySharedFilesSortFileName);
			this.selectedSortTitle(this.mySharedFilesSortSharedTime);
		}else{
			this.selectedSortTitle(sortDom);
		}
		//需要传递的参数
		var parameters = new Object();
		parameters["sort"] = sort;
		parameters["order"] = order;
		//加载文件列表
		zkAjaxData("/pc/front/fSharedfilesAction!getSharedfiles.action",parameters, function(data){

			//加载成功
			if(data.success){
				/*
				 * 添加记录到table
				 */
				//清空原始记录
				mySharedFilesList.clearTrs();
				//循环添加
				var sharedFiles = data.obj;
				for ( var i = 0; i < sharedFiles.length; i++) {
					var sharedFile = $(sharedFiles[i]);
					//获取数据
					var data = sharedFile;
					//执行添加操作
					mySharedFilesList.addTr(data);
				}
				
			}else{
				
			}
			//延时隐藏加载
			mySharedFilesList.hideTiper("加载数据成功",1000);
			//回调函数
			if(callFun){
				callFun();
			}
		}, true);
	},
	//设置排序图标单击事件
	setSortTitleClickLis:function(){
		
		mySharedFilesList.sortTitles.unbind("click");
		mySharedFilesList.sortTitles.click(function(){
			mySharedFilesList.loadSharedFils($(this), function(){
				
			});
		});
		
	},
	/**
	 * 设置复制连接单击事件
	 */
	setCopyLinkBtnClickLis:function(){
		
		mySharedFilesList.copySharedFilesLinkBtn.unbind("click");
		mySharedFilesList.copySharedFilesLinkBtn.click(function(){
			$(this).zclip({
				path: getWebProjectName()+'/pc/plug-in/jquery.zclip.1.1.1/ZeroClipboard.swf',
				copy: function(){
					var url = mySharedFilesList.getCurrtSelectdTrShreadUrl();
					var ispublic = mySharedFilesList. getCurrtSelectedTrIsPublicLink();
					var tip1 = "";
					
					var pwd = "";
					var content ="";
					if(true == ispublic||"true" == ispublic){
						tip1 = "公共连接：";
						content = tip1+url;
					}else{
						tip1 = "私密连接：";
						var tip2 = "密码：";
						var password = mySharedFilesList.getCurrtSelectedTrPassword();
						content = tip1+url+tip2+password;
					}
					return content;
				},
				beforeCopy:function(){ //按住鼠标时的操作 
					 /*$(this).css("color","orange");*/
				},
				afterCopy:function(){ //复制成功后的操作 
					showMsgWin(undefined,"复制成功",undefined);
				}
			});
		});
	},
	/**
	 * 添加一个tablete
	 */
	addTr:function(data){
		/*
		 * 获取参数
		 */
		var tableTitleTr = this.mySharedFilesListTable.find("tr:first");
		var filename = data[0]["filename"];
		var filesize = data[0]["filesize"];
		var browsenumber = data[0]["browsenumber"];
		var ispublic = data[0]["ispublic"];
		var sharedtime = data[0]["sharedtime"];
		var downloadnumber = data[0]["downloadnumber"];
		var savenumber = data[0]["savenumber"];
		var sharedFilesId = data[0]["sharedfileid"];
		var ext = data[0]["ext"];
		var sharedUrl = data[0]["shreadUrl"];//注意：此处有拼写错误
		var ispublic = data[0]["ispublic"];
		var password = data[0]["password"];
		/*
		 * 生成dom节点；并且添加节点；刷行dom节点；重设触发事件；
		 */
		//申明dom节点
		//sharedFilesTr
		
		var sharedFilesTr = $("<tr password='"+password+"' ispublic="+ispublic+" sharedUrl='"+sharedUrl+"' fileextclazz='"+ext+"' sharedFilesId='"+sharedFilesId+"' title='"+filename+"' class='sharedFilesTrs'></tr>");
		
		//sharedFilesTd
		var sharedFilesTd = $("<td class='sharedFilesTds'></td>");
		//sharedFilesIconDiv
		var sharedFilesIconDiv = $("<div class='sharedFilesIconDiv'></div>");
		var sharedFilesIcon = $("<img class='sharedFilesIcon' src='"+getFileImgSrc(ext)+"' />");
		//sharedFilesNameDiv
		var sharedFilesNameDiv = $("<div class='sharedFilesNameDiv'></div>");
		var lab = $("<label>"+filename.trim()+"</label>");
		//td2
		var td2 = $("<td>"+getFormatFileSize(filesize)+"</td>");
		//td3
		var td3 = $("<td>"+(ispublic==true?"公开连接":"私密连接")+"</td>");
		//td4
		var td4 = $("<td>"+browsenumber+"</td>");
		//td5
		var td5 = $("<td>"+savenumber+"</td>");
		//td6
		var td6 = $("<td>"+downloadnumber+"</td>");
		//td7
		var td7 = $("<td>"+sharedtime+"</td>");
		//添加dom节点；从内至外添加；
		sharedFilesIconDiv.append(sharedFilesIcon);
		
		sharedFilesNameDiv.append(lab);
		
		sharedFilesTd.append(sharedFilesIconDiv);
		sharedFilesTd.append(sharedFilesNameDiv);
		
		sharedFilesTr.append(sharedFilesTd);
		sharedFilesTr.append(td2);
		sharedFilesTr.append(td3);
		sharedFilesTr.append(td4);
		sharedFilesTr.append(td5);
		sharedFilesTr.append(td6);
		sharedFilesTr.append(td7);
		//添加新节点
		if(mySharedFilesList.sharedFilesTrs.length>=1){//在最后一个节点
			mySharedFilesList.sharedFilesTrs.eq(mySharedFilesList.sharedFilesTrs.length-1).after(sharedFilesTr);
		}else{//在标题后添加新节点
			mySharedFilesList.getTableTitleTrDom().after(sharedFilesTr);
		}
		
		//刷行dom节点
		mySharedFilesList.reloadTrs();
		//重设触发事件
		mySharedFilesList.setTrsClickLis();
		
		
		
		/*		
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
		*/
		
		
	},
	/**
	 * 获取显示列表的标题dom节点
	 */
	getTableTitleTrDom:function(){
		return mySharedFilesList.sortTitlesTd;
	},
	/**
	 * 获取当前选择行的分享url
	 */
	getCurrtSelectdTrShreadUrl:function(){
		return mySharedFilesList.currtClickTrDom.attr("sharedUrl");
	},
	/**
	 * 清空table列表
	 */
	clearTrs:function(){
		//第一个标题tr不删除；
		$(mySharedFilesList.sharedFilesTrs).remove();
		//刷新节点
		mySharedFilesList.reloadTrs();
	},
	showTiper:function(tiperMsg){
		/*
		 * 获取提示信息
		 */
		var msg = mySharedFilesList.defaultTiperMsg;
		if(tiperMsg!=undefined&&tiperMsg!=""){
			msg = tiperMsg;
		}
		//设置提示信息
		mySharedFilesList.mySharedTiperMsg.html(msg);
		//显示
		mySharedFilesList.mySharedTiperDiv.show();
	},
	hideTiper:function(tiperMsg,waitTime){
		/*
		 * 清除其他的关闭延时
		 */
		if(mySharedFilesList.tiperTimer){
			clearTimeout(mySharedFilesList.tiperTimer);
		}
		/*
		 * 设置提示信息
		 */
		var msg = mySharedFilesList.defaultTiperMsg;
		if(tiperMsg!=undefined&&tiperMsg!=""){
			msg = tiperMsg;
		}
		mySharedFilesList.mySharedTiperMsg.html(msg);//设置提示
		//设置关闭延时
		tiperTimer = setTimeout(function(){
			mySharedFilesList.mySharedTiperDiv.hide();
			mySharedFilesList.mySharedTiperMsg.html(mySharedFilesList.efaultTiperMsg);
		}, waitTime);
	},
	/**
	 * 设置行单击事件
	 */
	setTrsClickLis:function(){
		mySharedFilesList.sharedFilesTrs.unbind("click");
		mySharedFilesList.sharedFilesTrs.click(function(){
			//修改tr样式；设置mySharedFilesList.currtClickTrDom的值；
			mySharedFilesList.selectTr($(this));
			//显示操作按钮
			mySharedFilesList.showOperBtns();
			
		});
	},
	/**
	 * 修改tr样式；设置mySharedFilesList.currtClickTrDom的值；
	 */
	selectTr:function(tr){
		if(mySharedFilesList.currtClickTrDom){
			//修改旧时tr样式
			//设置背景为空，切记
			mySharedFilesList.currtClickTrDom.css("background-color","");
		}
		//修改新的tr样式
		mySharedFilesList.currtClickTrDom = tr;
		mySharedFilesList.currtClickTrDom.css("background",mySharedFilesList.trClickBg);
	},
	/**
	 * 设置取消分享单击事件
	 */
	setDeleteBtnClickLis:function(){
		mySharedFilesList.deleteSharedFilesBtn.unbind("click");
		mySharedFilesList.deleteSharedFilesBtn.click(function(){
			/*//保存（设置）当前单击行dom节点
			mySharedFilesList.currtClickTrDom = $(this); 
			//显示操作按钮
			mySharedFilesList.showOperBtns();*/
			//显示确认对话框
			showConfirmWin("确认取消分享?",function(r){
				if(r==false){//不取消分享，那么拒绝执行取消分享
					return ;
				}
				//显示提示
				mySharedFilesList.showTiper("正在取消分享");
				//需要传递的参数
				var parameters = new Object();
				parameters["sharedfileid"] = mySharedFilesList.getCurrtSelectedSharedFilesId();
				//取消分享ajax
				zkAjaxData("/pc/front/fSharedfilesAction!cancelSharedfiles.action",parameters, function(data){

					
					if(data.success){//取消分享成功
						//移除被取消的记录
						mySharedFilesList.getCurrtSelectedTrDom().remove();
						//刷新dom节点
						mySharedFilesList.reloadTrs();
						//清空当前选择行
						mySharedFilesList.clearCurrtSelectedTrDom();
						//隐藏操作按钮
						mySharedFilesList.hideOperBtns();
					}else{
						
					}

					//延时隐藏加载
					mySharedFilesList.hideTiper(data.msg,1000);
					
				}, true);
			});
		});
	},
	/**
	 * 显示操作按钮
	 */
	showOperBtns:function(){
		//显示取消分享按钮
		mySharedFilesList.deleteSharedFilesBtn.show();
		//显示复制连接按钮
		mySharedFilesList.copySharedFilesLinkBtn.show();
	},
	/**
	 * 隐藏操作按钮
	 */
	hideOperBtns:function(){
		//隐藏取消分享按钮
		mySharedFilesList.deleteSharedFilesBtn.hide();
		//隐藏复制连接按钮
		mySharedFilesList.copySharedFilesLinkBtn.hide();
	},
	/**
	 * 获取当前选择的tr的sharedfilesid
	 */
	getCurrtSelectedSharedFilesId:function(){
		return mySharedFilesList.currtClickTrDom.attr("sharedFilesId");
	},
	/**
	 * 获取当前选择的tr的连接类型：false；true；
	 */
	getCurrtSelectedTrIsPublicLink:function(){
		return mySharedFilesList.currtClickTrDom.attr("ispublic");
	},
	/**
	 * 获取当前选择的tr的dom节点
	 */
	getCurrtSelectedTrDom:function(){
		return mySharedFilesList.currtClickTrDom;
	},
	/**
	 * 获取当前选择的tr的密码
	 */
	getCurrtSelectedTrPassword:function(){
		return mySharedFilesList.currtClickTrDom.attr("password");
	},
	/**
	 * 清空当前选择的tr的dom节点
	 */
	clearCurrtSelectedTrDom:function(){
		mySharedFilesList.currtClickTrDom = undefined;
	},
	/**
	 * 重新加载tr节点
	 */
	reloadTrs:function(){
		mySharedFilesList.sharedFilesTrs = $(".sharedFilesTrs");
	}
};



