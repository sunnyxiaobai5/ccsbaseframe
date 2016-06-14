<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="searchFormBox">
	<form action="<%=basePath %>ModelController/addModel" method="post" id="searchForm">
		<span class="nameBasInput"><b>模块名称：</b><input class="easyui-validatebox" name="modName" data-options="required:true" /></span>
	    <span class="nameBasInput"><b>模块描述：</b><input class="easyui-validatebox" name="description" data-options="required:true" /></span>
	    <span class="nameBasInput"><b>是否有效： </b><select class="easyui-combobox" name="isActive" panelHeight="auto" data-options="required:true">
	        <option value="1">是</option>
	        <option value="-1">否</option>
	    </select></span>
	</form>
</div>