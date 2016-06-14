<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',split:true,border:false" id="orgs" style="background:#F4F4F4;">
			<ul id="sysTree" class="ztree"></ul>
		</div>
		<input type="hidden" id="sysid" name="sysid" value="${sysid}" />
	</div>

	<link rel="stylesheet" href="../css/demo.css" type="text/css">
	<link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>

	<script type="text/javascript">
		var sysid = $("#sysid").val();
		/** jquery-ztree封装树**/
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
							idKey: conf.id || 'tid',
							pIdKey: conf.parentId || 'pid',
							rootPId: null
						},
						key:{
						   url:'xurl', //屏蔽URL
						   name: conf.name || 'text'
						}
					},
					check:{
						enable: conf.check || true,
						chkStyle:"checkbox",
						chkboxType: conf.chkboxType || { "Y": "s", "N": "p" }
					},
					callback:{
						onClick:conf.onClick,
						onAsyncSuccess:conf.onAsyncSuccess
					}
				};
				ztreeObj = $.fn.zTree.init($(this), setting);
				return ztreeObj;
			}
		});
		
		var conf = {
			url:'<%=basePath %>SysController/getSysTreeByid?sysid='+ sysid,
			name:'text',
			parentId:'parentId',
			onClick:function(event, treeId, treeNode){
				
			}
		} ;
		
		$("#sysTree").initZtree(conf) ;
	</script>
				