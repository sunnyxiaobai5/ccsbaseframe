<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="cn.ccsgroup.ccsframework.base.entity.TreeNode,java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="../js/json2.js"></script>
<script type="text/javascript">
$(function(){
	$("#sysImag").attr("src","<%=basePath %>SysController/getSysLogo?id=${LogiSystem.id }&random="+ Math.random());       
});
function MybeforeSubmit() {
};
</script>
<div class="easyui-layout" data-options="fit:true">
<form action="<%=basePath %>SysController/updateSys" method="post" id="popForm" enctype= "multipart/form-data" region="center">
<div data-options="region:'center',border:false" style="height:130px">
	<div class="popFormBox">
		<input type="hidden" name="id" value="${LogiSystem.id }"/>
		<span class="nameBas"><b>系统名称：</b><input class="easyui-validatebox" name="sysName" data-options="required:true" value="${LogiSystem.sysName }" /></span>
	    <span class="nameBas"><b>系统描述：</b><input class="easyui-validatebox" name="description" data-options="required:true" value="${LogiSystem.description }" /></span>
	    <span class="nameBas"><b>系统地址：</b><input class="easyui-validatebox" name="sysUrl" data-options="required:true" value="${LogiSystem.sysUrl }" /></span>
		<span class="nameBas"><b>系统简称：</b><input class="easyui-validatebox" name="briefName" data-options="required:true" value="${LogiSystem.briefName }" /></span>
		<span class="nameBas"><b>系统区域： </b><input style="width:143px;" class="easyui-combotree" name="regionCode" panelHeight="200" panelWidth="143"  data-options="url:'<%=basePath %>findAllRegion/${LogiSystem.regionCode }',required:true,lines:true,multiple:true,cascadeCheck:false"/></span>
	    <span class="nameBas"><b>是否有效： </b><select style="width:143px;" class="easyui-combobox" name="isActive" panelHeight="auto" data-options="required:true">
	        <option value="1">是</option>
	        <option value="-1">否</option>
	    </select></span>
	    <span class="nameBas"><b>能否修改： </b><select style="width:143px;" class="easyui-combobox" name="isInside" panelHeight="auto" data-options="required:true">
	        <option value="1">是</option>
	        <option value="-1">否</option>
	    </select></span>
	    <span class="nameBas"><b>系统标识：</b><input class="easyui-validatebox" name="alias" data-options="required:true" value="${LogiSystem.alias }" /></span>
	    <span class="nameBas"><b>系统Logo： </b><input class="inputFile" type="file" name="imgs" />
	    	<img id="sysImag" name="sysImag" width="30" height="30" >
	    </span>
	</div>
</div>
</form>
</div>