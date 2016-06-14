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
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
		}
		
		$(function(){
			var id = $('#id').val();
			//组织机构树
			var conf = {
				url:'<%=basePath%>SysController/getAssignModels?id='+id ,
				name:'text',
				parentId:'parentId'
			} ;
			$("#treeDemo").initZtree(conf) ;
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
		function modelRowFormater(val,row,index){
			var rows = $("#modelsWithChecked").datagrid("getRows");
			var datas = rows[index];
			return '&nbsp;<a href="#" onclick="assignModel('+ datas.id +',\''+ datas.modName +'\')">添加</a>&nbsp;';
		}
		
		function assignModel(id,modName){
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
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
			$("#modelsWithChecked").datagrid({
				queryParams:{
					modName : $('input[name="modName"]').val()
				},
				method : 'post',
				url:$("#modelsForm").attr('action')
			});
		}
	</SCRIPT>

  
  	<input type="hidden" id="id" name="id" value="${id}"/>
  	<div id="toolbar1" style="padding:5px;height:auto;display:none;">
	    <!-- 搜索 -->
	    <div class="searchFormBox">
	        <form action="<%=basePath %>SysController/getModelsWithChecked" method="post" id="modelsForm" class="searchForm">
	        	<span class="nameBas"><b>模块名称：</b><input class="easyui-validatebox" value="" name="modName"></span>
	        	<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="searchModels()">搜索</a>
	        </form>
	    </div>
	</div>
	
	
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'north',split:false" style="height:36px;">
  			<div class="sysName">系统名称：${sysName}</div>
  		</div>
  		<div data-options="region:'west',split:true" style="width:200px;">
  			<ul id="treeDemo" class="ztree"></ul>
  		</div>
  		<div data-options="region:'center'">
  			<table id="modelsWithChecked" class="easyui-datagrid" data-options="url:'<%=basePath %>SysController/getModelsWithChecked',toolbar:'#toolbar1'">
			    <thead>
			        <tr>
			            <th data-options="field:'modName',sortable:true,width:50">模块名称</th>
			            <th data-options="field:'isChecked',hidden:true"></th>
			            <th data-options="field:'true',align:'center',formatter:modelRowFormater">操作</th>
			        </tr>
			    </thead>
			</table>
  		</div>
  	</div>