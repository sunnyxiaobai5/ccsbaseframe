<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!--                       Ztree                       -->
	<link rel="stylesheet" href="../css/demo.css" type="text/css">
	<link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="../js/jquery.ztree.exedit-3.5.js"></script>
	
	<SCRIPT type="text/javascript">
	
		$.fn.extend({
			initZtree:function(conf){
				var ztreeObj = false ;
				var setting = {
					async:{
						enable:true,
						url: conf.url,
						type:'post',
						dataType:'json'
					},
					view:{  
						showIcon:true,
						showLine:true,
						expandSpeed:'normal',
						txtSelectedEnable:true
					},
					data:{
						simpleData:{
							enable:true,
							idKey: conf.id || 'id',
							pIdKey: conf.parentId || 'pid',
							rootPId: null
						},
						key:{
						   url:'xurl', //屏蔽URL
						   name: conf.name || 'text'
						}
					},
					edit: {
						drag: {
							autoExpandTrigger: true,
							prev: dropPrev,
							inner: dropInner,
							next: dropNext
						},
						enable: true,
						showRenameBtn: false
					},
					callback:{
						beforeDrag: beforeDrag,
						beforeDrop: beforeDrop,
						beforeDragOpen: beforeDragOpen,
						onDrag: onDrag,
						onDrop: onDrop,
						onExpand: onExpand,
						onAsyncSuccess:conf.onAsyncSuccess
					}
				};
				ztreeObj = $.fn.zTree.init($(this), setting);
				return ztreeObj ;
			}
		});

		function dropPrev(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function dropInner(treeId, nodes, targetNode) {
			if (targetNode && targetNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					if (!targetNode && curDragNodes[i].dropRoot === false) {
						return false;
					} else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function dropNext(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}

		var log, className = "dark", curDragNodes, autoExpandNode;
		function beforeDrag(treeId, treeNodes) {
			className = (className === "dark" ? "":"dark");
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					curDragNodes = null;
					return false;
				} else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
					curDragNodes = null;
					return false;
				}
			}
			curDragNodes = treeNodes;
			return true;
		}
		function beforeDragOpen(treeId, treeNode) {
			autoExpandNode = treeNode;
			return true;
		}
		function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
			className = (className === "dark" ? "":"dark");
			return true;
		}
		function onDrag(event, treeId, treeNodes) {
			className = (className === "dark" ? "":"dark");
		}
		function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
			className = (className === "dark" ? "":"dark");
		}
		function onExpand(event, treeId, treeNode) {
			if (treeNode === autoExpandNode) {
				className = (className === "dark" ? "":"dark");
			}
		}
		
		function setTrigger() {
			var zTree = $.fn.zTree.getZTreeObj("funcsTree");
			zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
		}
		
		$(function(){
			var modId = $('#modId').val();
			var sysId = $('#sysId').val();
			//组织机构树
			var conf = {
				url:'<%=basePath%>SysController/getAssignFucs?modId='+modId+'&sysId='+sysId ,
				name:'text',
				parentId:'parentId'
			} ;
			$("#funcsTree").initZtree(conf) ;
			//$("#callbackTrigger").bind("change", {}, setTrigger);
		});
		/**
		function modelOnLoadSuccess(data) {
			var rowData = data.rows;
			$.each(rowData, function (idx, val) {
				if (val.isChecked == "1") {
					$('#modelsWithChecked').datagrid('checkRow' , idx);
				}      
			});        
		}**/
		function funcRowFormater(val,row,index){
			var rows = $("#funcs").datagrid("getRows");
			var datas = rows[index];
			return '&nbsp;<a href="#" onclick="assignModel('+ datas.id +',\''+ datas.funcName +'\')">添加</a>&nbsp;';
		}
		
		function assignModel(id,modName){
			var treeObj = $.fn.zTree.getZTreeObj("funcsTree");
			console.log(treeObj.getNodes());
			var node = treeObj.getNodeByParam("tid", id, null);
			if(node==null){
				var newNode = {tid:id,text:modName,dropInner: false};
				newNode = treeObj.addNodes(null, newNode);
			}
			console.log(node);
		}
		
		//搜索
		function searchModels(){
			$("#funcs").datagrid({
				queryParams:{
					funcName : $('input[name="funcName"]').val()
				},
				method : 'post',
				url:$("#funcsForm").attr('action')
			});
		}
	</SCRIPT>

  
  	<input type="hidden" id="modId" name="modId" value="${modId}"/>
  	<input type="hidden" id="sysId" name="sysId" value="${sysId}"/>
  	<div id="toolbar1" style="padding:5px;height:auto;display:none;">
	    <!-- 搜索 -->
	    <div class="searchFormBox">
	        <form action="<%=basePath %>SysController/getFuncs" method="post" id="funcsForm" class="searchForm">
	        	<span class="nameBas"><b>功能名称：</b><input class="easyui-validatebox" value="" name="funcName"></span>
	        	<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="searchModels()">搜索</a>
	        </form>
	    </div>
	</div>
	
	
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'north',split:false" style="height:36px;">
  			<div class="modName">功能名称：${modName}</div>
  		</div>
  		<div data-options="region:'west',split:true" style="width:200px;">
  			<ul id="funcsTree" class="ztree"></ul>
  		</div>
  		<div data-options="region:'center'">
  			<table id="funcs" class="easyui-datagrid" data-options="url:'<%=basePath %>SysController/getFuncs',toolbar:'#toolbar1'">
			    <thead>
			        <tr>
			            <th data-options="field:'funcName',sortable:true,width:50">功能名称</th>
			            <th data-options="field:'true',align:'center',formatter:funcRowFormater">操作</th>
			        </tr>
			    </thead>
			</table>
  		</div>
  	</div>