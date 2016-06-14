<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<div class="easyui-layout" style="height:430px">
	<div data-options="region:'center'" style="border: none">
		<div class="easyui-layout" data-options="fit:true">
			<form action="<%=basePath %>UserManagerController/updateUserRole"
				method="post" id="searchForm">
				<div
					data-options="region:'north',height:230,title:'人员所属角色列表',split:true">
					<table id="dg1" class="easyui-datagrid" data-options="url:'<%=basePath %>UserManagerController/getUserOfRoleList/${userManager.id}',method:'get',border:false">
						<thead>
							<tr>
								<th data-options="field:'rName',width:50">角色名称</th>
								<th data-options="field:'remark',width:50">角色描述</th>
								<th data-options="field:'issysdef',width:50" field="issysdef" width="50">是否系统自动生成</th>
								<th data-options="field:'status',width:50">状态</th>
								<th data-options="field:'createrUser',width:60">创建人</th>
								<th data-options="field:'true',align:'center',formatter:rowFormater1">操作</th>
							</tr>
						</thead>
					</table>
				</div>

				<div data-options="region:'center',heght:100,title:'角色列表'">
					<table id="dg2" class="easyui-datagrid" data-options="url:'<%=basePath %>UserManagerController/getAllRoleList?orgid=${orgid}',method:'get',border:false">
						<thead>
							<tr>
								<th data-options="field:'rName',sortable:true,width:50">角色名称</th>
								<th data-options="field:'remark',width:50">角色描述</th>
								<th data-options="field:'issysdef',width:50" field="issysdef" width="50">是否系统自动生成</th>
								<th data-options="field:'status',width:50">状态</th>
								<th data-options="field:'createrUser',width:60">创建人</th>
								<th data-options="field:'true',align:'center',formatter:rowFormater2">操作</th>
							</tr>
						</thead>
					</table>
				</div>
				<input type="hidden" id="roleids" name="roleids" value=""/>
			    <input type="hidden" id="orgid" name="orgid" value="${orgid}"/>			
			    <input type="hidden" id="uid" name="uid" value="${userManager.id}"/>			
			    <input type="hidden" id="userManager" name="userManager" value="${userManager}"/>	
			</form>
		</div>
	</div>  

	<!--右侧	   权限列表-->
	<div data-options="region:'east',title:'权限列表',split:true" style="width:270px;">
		<ul id="sysTree" class="ztree"></ul>
	 </div>
</div>

<link rel="stylesheet" href="../css/demo.css" type="text/css">
<link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript">
	function rowFormater1(val,row,index) {
		return '&nbsp;<a href="javascript:javascript:void(0)" onclick="disRoleDelete('+index+')">删除</a>&nbsp;';
	}
	
	function disRoleDelete(rowIndex){
		$("#dg1").datagrid("deleteRow",rowIndex);
		
		var roleids = "";
		var rows = $("#dg1").datagrid("getRows");
		for(var i=0;i<rows.length;i++){
			roleids += "," + rows[i].id;
		}
		changeTree(roleids);//该表权限树
	}
	
	function rowFormater2(val,row,index) {
		return '&nbsp;<a href="javascript:javascript:void(0)" onclick="disRoleAdd('+index+')">添加</a>&nbsp;';
	}
	
	function disRoleAdd(rowIndex){
		var rows1 = $("#dg1").datagrid("getRows");
		var data = $("#dg2").datagrid("getRows")[rowIndex];
		var roleids = "";
		for(var i=0;i<rows1.length;i++){
			if(rows1[i].id == data.id){
				return;
			}
		}
		$("#dg1").datagrid("insertRow",{row:data});
		
		var rows = $("#dg1").datagrid("getRows");
		for(var i=0;i<rows.length;i++){
			roleids += "," + rows[i].id;
		}
		changeTree(roleids);//改变权限树
	}
	
	//保存
	function MybeforeSubmit(){
		var roleids = "";
		
		var rows = $("#dg1").datagrid("getRows");
		for(var i=0;i<rows.length;i++){
			roleids += "," + rows[i].id;
		}
		$("#roleids").val(roleids);
	}
	
	/** jquery-ztree封装树**/
	$.fn.extend({
		initZtree1:function(conf){
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
						idKey: conf.id || 'tid',
						pIdKey: conf.parentId || 'pid',
						rootPId: null
					},
					key:{
					   url:'xurl', 
					   name: conf.name || 'text'
					}
				},
				check:{
					enable: conf.check || true,
					chkStyle:"radio",
					autoCheckTrigger: true,
					radioType : "all"
				},
				callback:{
					onClick:conf.onClick,
					onAsyncSuccess:conf.onAsyncSuccess
				}
			};
			ztreeObj = $.fn.zTree.init($(this), setting);
			return ztreeObj ;
		}
	});
	
	//改变树
	function changeTree(roleids){
		var conf1 = {
			url:'<%=basePath%>UserManagerController/changeSysTreeByRole?ids=' + roleids,
			name:'text',
			parentId:'parentId',
			onClick:function(event, treeId, treeNode){
				
			}
		};
		$("#sysTree").initZtree1(conf1) ;
	}
	
	$(function(){
		var uid = $("#uid").val();
		
		var conf1 = {
			url:'<%=basePath%>UserManagerController/sysTreeByUser/'+ uid ,
			name:'text',
			parentId:'parentId',
			onClick:function(event, treeId, treeNode){
				
			}
		} ;
		$("#sysTree").initZtree1(conf1) ;
	});
</script>
				