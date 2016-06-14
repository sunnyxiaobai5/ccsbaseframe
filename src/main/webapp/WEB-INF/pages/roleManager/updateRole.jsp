<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>

    <!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
<div class="easyui-layout" data-options="fit:true">
    <form action="<%=basePath %>RoleController/update" id="updateRoleForm" name="updateForm" method="post">
	     <div id="cc" data-options="region:'west',split:true" style="width:370px;">
		    <div class="popFormBox">
		      	<span class="nameBas"><b>角色名称：</b><input class="easyui-validatebox" data-options="validType:'maxLength[8]'" type="text"  name="rName" value="${role.rName}"/></span>
		      	<span class="nameBas"><b>角色描述：</b><input class="easyui-validatebox" data-options="validType:'maxLength[64]'" type="text"  name="remark" value="${role.remark}"/></span>
		      	<span class="nameBas"><b>当前组织机构：</b>${orgName}</span>
	    	</div>
	    </div>
		<div data-options="region:'center',title:'权限列表'" id="treeBox">
			<ul id="sysZtree" class="ztree"></ul>
			<div class="treeLoadingBox"></div>
		</div>
	    <input type="hidden" id="id" name="id" value="${role.id}"/>
	    <input type="hidden" id="roleid" name="userid" value="${userid}"/>
	    <input type="hidden" id="sys"  name="sys" value=""/>
    </form>
</div>

<link rel="stylesheet" href="../css/demo.css" type="text/css">
<link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="../js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" >
		//保存
		function MybeforeSubmit(){
			var sysTree = $.fn.zTree.getZTreeObj("sysZtree");
			var sysNodes = sysTree.getCheckedNodes(true);

			var list = new Array;
			if(sysNodes.length > 0) {
				for(var i in sysNodes) {
					var obj = new Object;
					obj.id = sysNodes[i].id;
					obj.tid = sysNodes[i].tid;
					obj.text = sysNodes[i].text;
					obj.parentId = sysNodes[i].parentId;
					obj.nodepid = sysNodes[i].nodepid;
					obj.nodelevel = sysNodes[i].nodelevel;
					
					list.push(obj);
				}
			}
			
			var sys = JSON.stringify(list);
			$("#sys").val(sys);
		}
		
		//权限树配置
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
						autoCheckTrigger: true,
						chkboxType: conf.chkboxType || { "Y": "ps", "N": "ps" }
					},
					callback:{
						onClick:conf.onClick,
						beforeAsync:zTreeBeforeAsync,
						onAsyncSuccess:zTreeOnAsyncSuccess
					}
				};
				ztreeObj = $.fn.zTree.init($(this), setting);
				return ztreeObj ;
			}
		});
		
		function zTreeOnAsyncSuccess(treeId, treeNode){
			$(".treeLoadingBox").remove();
		}
		
		function zTreeBeforeAsync(treeId, treeNode) {
			setTimeout('treeBoxSize()', 0);
		}
		
		$(function(){
			var roleid = $("#id").val();
			//系统权限树 
			var conf = {
					url:'<%=basePath%>RoleController/getRoleSysTree?roleid='+roleid ,
					name:'text',
					parentId:'parentId',
					onClick:function(event, treeId, treeNode){
						
					}
				} ;
			$("#sysZtree").initZtree(conf);
			
		});
		
		function treeBoxSize() {
			var treeBoxH = $("#treeBox").height();
			var treeBoxW = $("#treeBox").width();
			$(".treeLoadingBox").css({"height":treeBoxH,"width":treeBoxW,"position":"absolute","z-index":"999","background":"url('${pageContext.request.contextPath}/images/loadingBg.png')","top":"29px"});
			$(".treeLoadingBox").html("<img src='${pageContext.request.contextPath}/images/ajax-loader.gif' />");
			$(".treeLoadingBox img").css({"position":"absolute","top":"50%","left":"50%","margin-top":"-16px","margin-left":"-16px"});
		}

</script>















