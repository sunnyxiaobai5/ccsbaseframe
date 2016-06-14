<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="cn.ccsgroup.ccsframework.base.entity.TreeNode,java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="../js/json2.js"></script>
<script type="text/javascript">
function MybeforeSubmit() {

};
</script>
<div class="easyui-layout" data-options="fit:true">
<form action="<%=basePath %>SysController/addsys" method="post" id="popForm" enctype= "multipart/form-data" region="center">
<div data-options="region:'center',border:false">
	<div class="popFormBox">
		<span class="nameBas"><b>系统名称：</b><input class="easyui-validatebox" name="sysName" data-options="required:true" /></span>
	    <span class="nameBas"><b>系统描述：</b><input class="easyui-validatebox" name="description"/></span>
	    <span class="nameBas"><b>系统地址：</b><input class="easyui-validatebox" name="sysUrl" data-options="required:true" /></span>
		<span class="nameBas"><b>系统简称：</b><input class="easyui-validatebox" name="briefName" /></span>
		<span class="nameBas"><b>系统区域： </b><input style="width:143px;" class="easyui-combotree" name="regionCode" panelHeight="200" panelWidth="143" data-options="url:'<%=basePath %>findAllRegion/nocache',required:true,lines:true,multiple:true,cascadeCheck:false" /></span>
	    <span class="nameBas"><b>是否有效： </b><select style="width:143px;" class="easyui-combobox" name="isActive" panelHeight="auto" data-options="required:true">
	        <option value="1">是</option>
	        <option value="-1">否</option>
	    </select></span>
	    <span class="nameBas"><b>能否修改： </b><select style="width:143px;" class="easyui-combobox" name="isInside" panelHeight="auto" data-options="required:true">
	        <option value="1">是</option>
	        <option value="-1">否</option>
	    </select></span>
	    <span class="nameBas"><b>系统标识：</b><input class="easyui-validatebox" name="alias" /></span>
	    <span class="nameBas"><b>系统Logo： </b><input class="inputFile" type="file" name="imgs" /></span>
	</div>
</div>
</form>
</div>