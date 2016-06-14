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
<form action="<%=basePath %>TypeListController/update" id="updateTypeListForm" name="updateForm" method="post">
	<div class="popFormBox">
		<input type="hidden"  name="id" value="${typeList.id }"/>
		<span class="nameBas"><b>标准类型编码：</b><input class="easyui-validatebox" data-options="required:true,validType:'maxLength[5]'" type="text"  name="kind" value="${typeList.kind}"/></span>
		<span class="nameBas"><b>标准类型：</b><input class="easyui-validatebox" data-options="required:true,validType:'maxLength[40]'" type="text"  name="typename" value="${typeList.typename}"/></span>
		<span class="nameBas"><b>标准类型值：</b><input class="easyui-validatebox" data-options="required:true,validType:'maxLength[80]'" type="text"  name="type" value="${typeList.type}"/></span>
		<span class="nameBas"><b>标准类型值全称：</b><input class="easyui-validatebox" data-options="validType:'maxLength[120]'" type="text"  name="fullname" value="${typeList.fullname}"/></span>
		<span class="nameBas"><b>排序编号：</b><input class="easyui-numberbox" precision="0" max="99999" type="text"  name="porder" value="${typeList.porder}"/></span>
		<span class="nameBas"><b>系统标志：</b><input class="easyui-validatebox" data-options="validType:'maxLength[20]'" type="text"  name="ident" value="${typeList.ident}"/></span>
	</div>
</form> 