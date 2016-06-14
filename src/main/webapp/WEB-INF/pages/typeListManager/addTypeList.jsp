<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
    <!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
<form action="<%=basePath %>TypeListController/save" id="addTypeList" name="addForm" method="post">
	<div class="popFormBox">
		<span class="nameBas"><b>标准类型编码：</b><input class="easyui-validatebox" data-options="required:true,validType:'maxLength[5]'" type="text"  name="kind" /></span>
		<span class="nameBas"><b>标准类型：</b><input class="easyui-validatebox" data-options="required:true,validType:'maxLength[40]'" type="text"  name="typename" /></span>
		<span class="nameBas"><b>标准类型值：</b><input class="easyui-validatebox" data-options="required:true,validType:'maxLength[80]'" type="text"  name="type" /></span>
		<span class="nameBas"><b>标准类型值全称：</b><input class="easyui-validatebox" data-options="validType:'maxLength[120]'" type="text"  name="fullname" /></span>
		<span class="nameBas"><b>排序编号：</b><input class="easyui-numberbox" precision="0" max="99999" type="text"  name="porder" /></span>
		<span class="nameBas"><b>系统标志：</b><input class="easyui-validatebox" data-options="validType:'maxLength[20]'" type="text"  name="ident" /></span>
	</div>
</form> 
