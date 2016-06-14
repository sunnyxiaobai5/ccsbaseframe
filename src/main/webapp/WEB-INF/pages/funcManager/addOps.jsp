<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
  	<div class="popFormBox">
	    <form action="<%=basePath %>OpsController/addOps" method="post"  id="popForm">
	    	<input type="hidden" value="${funcId}" name="funcId"/>
	    	<span class="nameBas"><b>操作名称：</b><input class="easyui-validatebox" name="opsName" data-options="required:true" /></span>
	    	<span class="nameBas"><b>是否启用： </b>
		    	<select class="easyui-combobox" name="isActive" panelHeight="auto" data-options="required:true">
			        <option value="1">是</option>
			        <option value="-1">否</option>
		    	</select>
	    	</span>
	    	<span class="nameBas"><b>是否验证： </b>
		    	<select class="easyui-combobox" name="isStart" panelHeight="auto" data-options="required:true">
			        <option value="1">是</option>
			        <option value="-">否</option>
		    	</select>
	    	</span>
	  		<span class="nameBas"><b>操作URL：</b><input class="easyui-validatebox" name="opsUrl" data-options="required:true" /></span>
	    </form>
    </div>
  </body>
</html>
