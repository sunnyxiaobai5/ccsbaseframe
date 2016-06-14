<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="searchFormBox">
	<form action="<%=basePath %>ModelController/updateModel" method="post" id="searchForm">
		<input type="hidden" name="id" value="${Model.id }"/>
		<span class="nameBasInput"><b>模块名称：</b><input class="easyui-validatebox" name="modName" value="${Model.modName }" data-options="required:true" /></span>
	    <span class="nameBasInput"><b>模块描述：</b><input class="easyui-validatebox" name="description" value="${Model.description }" data-options="required:true" /></span>
	    <span class="nameBasInput"><b>是否有效： </b>
	    <select class="easyui-combobox" name="isActive" panelHeight="auto" data-options="required:true">
	        <option value="1">是</option>
	        <option value="-1">否</option>
	    </select></span>
	</form>
</div>