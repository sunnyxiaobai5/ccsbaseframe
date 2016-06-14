<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
	$(function(){
		var currItem = {	//当前树选项
				node:null
		};
		//绑定事件，选择树时执行
		$("#tt1").tree({onSelect:function(node){
			currItem.node = node;		//选择后赋值
			$("#orgid").val(node.attributes.ID);
			$("#orgname").val(node.attributes.ORGNAME);
		}});
	});
</script>
<div class="popFormBox">
     <form action="<%=basePath%>PositionController/copyPosition" method="post" id="popForm">
     	<input type="hidden" name="token" value="${token }" />
         <span class="nameBas">
         <b>当前组织： </b><input class="easyui-validatebox" value="${position.orgname}" readonly="readonly" data-options="required:true" /></span>
         <span class="nameBas">
         <b>职位名称：</b><input class="easyui-validatebox" name="pname" value="${position.pname}" data-options="required:true" /></span>
         <span class="nameBas">
         <b>职位编号： </b><input class="easyui-validatebox" name="pno" value="${position.pno}"/></span>
         <span class="nameBas">
         <b>职位描述： </b><input class="easyui-validatebox" name="description" value="${position.description}"/></span>
         <span class="nameBas">
         <b>目标组织： </b><input class="easyui-validatebox" name="description" id="orgname"/></span>
         <input type="hidden" id="orgid" name="orgid"/>
      </form>
</div>
<div class="popTreeBox">
	<ul id="tt1" class="easyui-tree" data-options="url:'<%=basePath %>findCurrentOrgTree/-10',method:'get',animate:true,lines:true"></ul>
</div>