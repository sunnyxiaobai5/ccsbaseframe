<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
    <div class="easyui-layout" data-options="fit:true,border:false">
		    <div data-options="region:'center',border:false">
		    	<div class="easyui-layout" data-options="fit:true">  
				    <div data-options="region:'center'">
					    <div id="toolbar">
							<!-- 搜索 -->
						    <div class="searchFormBox">
						        <form action="<%=basePath %>RoleController/querySetUser" method="post" id="userList" class="searchForm">
						        	<span class="nameBas"><b>用户姓名：</b><input class="easyui-validatebox" value="" name="userName"></span>
						        	<span class="nameBas"><b>登录帐号：</b><input class="easyui-validatebox" value="" name="loginid"></span>
						        	<input type="hidden" id="orgid" name="orgid" value="${orgid}"/>
						        	<input type="hidden" id="roleId" name="roleId" value="${roleId}"/>
						        	<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="soso()">搜索</a>
						        </form>
						    </div>
					    </div>
			           	<table id="setUserList" class="easyui-datagrid" data-options="url:'<%=basePath %>RoleController/orgUserList/${orgid}/${roleId}',toolbar:'#toolbar',pageSize:10,border:false,onLoadSuccess:cbOnLoadSuccess">
						    <thead>
						        <tr>
						       		<th data-options="checkbox:true"></th>
						            <th data-options="field:'userName',align:'center'">用户姓名</th>
						            <th data-options="field:'loginid',width:20,align:'center'">登录帐号</th>
						            <th data-options="field:'usertype',width:30,align:'center'">用户类型</th>
						            <th data-options="field:'idtype',width:50,align:'center'">证件类型</th>
						            <th data-options="field:'ownerid',width:50,align:'center'">证件号码</th>
						            <th data-options="field:'isactive',align:'center'">是否有效</th>
						            <th data-options="field:'flag',hidden:true"></th>
						        </tr>
						    </thead>
						</table>
				    </div> 
		    	</div>
		    </div>
		    <div data-options="region:'south',border:false">
				<div class="iframeBut" style="border-top:none;">
					<a href="javascript:void(0)" class="l-btn" onclick="saveRoleUser()"><span class="l-btn-left"><span class="l-btn-text">提交</span></span></a>
					<a href="javascript:void(0)" class="l-btn closeIframe" onclick="doClose()"><span class="l-btn-left"><span class="l-btn-text">关闭</span></span></a>
				</div>
		    </div>
    </div>  
    <input type="hidden" id="roleId" value="${roleId}"/>
    <input type="hidden" id="orgid" value="${orgid}"/>
    
    
<script type="text/javascript">
		//搜索
		function soso(){
			$("#setUserList").datagrid({
				queryParams:{
					userName : $('input[name="userName"]').val(),
					loginid : $('input[name="loginid"]').val(),
					orgid : $('input[name="orgid"]').val(),
					roleId : $('input[name="roleId"]').val()
				},
				method : 'post',
				url:$("#userList").attr('action')
			});
		}
		
		
		
		//关闭弹出层
		function doClose(){
			$('#setDialog').dialog('close');
		}
		
		//保存角色用户
		function saveRoleUser(){
			var rows = $("#setUserList").datagrid('getChecked');
			var req = [];

			for(var i=0; i<rows.length; i++){
		   		req.push(rows[i].id);
			}

            var currentPageRows = $("#setUserList").datagrid('getRows');
            var currentPageIds = [];
            for(var j=0;j<currentPageRows.length;j++){
                currentPageIds.push(currentPageRows[j].id);
            }

			$.ajax({
				type:"POST",
				url:"<%=basePath %>RoleController/saveRoleUser"+"/"+$("#roleId").val(),
				dataType:"json",
				data:"ids="+req.join(",")+"&currentPageIds="+currentPageIds.join(","),
				success:function(data){
					$("#setUserList").datagrid("reload");
					$("#user").datagrid("reload");
					EmessagerShow("",data.message);
					$('#setDialog').dialog('close');
				}
			});
		}
		
		function cbOnLoadSuccess(data) {
			var rowData = data.rows;
			$.each(rowData, function (idx, val) {
				if (val.flag == "true") {
					$('#setUserList').datagrid('checkRow' , idx);
				}      
			});        
		}
</script>
