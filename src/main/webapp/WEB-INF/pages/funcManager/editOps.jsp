<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
  <div class="popFormBox">
    <form action="<%=basePath %>OpsController/updateOps" method="post" id="popFormBox">
      <input type="hidden" name="id" value="${ops.id}"/>
 		<span class="nameBas"><b>操作名称：</b><input class="easyui-validatebox" name="opsName" value="${ops.opsName}" data-options="required:true" /></span>
	    	<span class="nameBas"><b>是否启用： </b>
		    	<select class="easyui-combobox" name="isActive" panelHeight="auto" data-options="required:true">
			        <option value="1" <c:if test="${ops.isActive==1}">selected='selected'</c:if>>是</option>
			        <option value="-1" <c:if test="${ops.isActive==-1}">selected='selected'</c:if>>否</option>
		    	</select>
	    	</span>
	    	<span class="nameBas"><b>是否验证： </b>
		    	<select class="easyui-combobox" name="isStart" panelHeight="auto" data-options="required:true">
			        <option value="1" <c:if test="${ops.isStart==1}">selected='selected'</c:if>>是</option>
			        <option value="-1" <c:if test="${ops.isStart==-1}">selected='selected'</c:if>>否</option>
		    	</select>
	    	</span>
	  		<span class="nameBas"><b>操作URL：</b><input class="easyui-validatebox" value="${ops.opsUrl}" name="opsUrl" data-options="required:true" /></span>
    </form>
   </div> 
  </body>
</html>
