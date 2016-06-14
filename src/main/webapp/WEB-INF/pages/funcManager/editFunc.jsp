<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<div class="searchFormBox">
    <form action="<%=basePath %>FuncController/updateFunc" method="post" id="searchFormBox">
    	<input type="hidden" name="id" value="${func.id }">
    	<span class="nameBasInput"><b>功能名称：</b><input class="easyui-validatebox" name="funcName" value="${func.funcName }" data-options="required:true" /></span>
    	 <span class="nameBasInput"><b>功能类型： </b><select class="easyui-combobox" name="funcType" panelHeight="auto" data-options="required:true">
	       <option value="1" <c:if test="${func.funcType==1}">selected='selected'</c:if>>系统</option>
           <option value="2" <c:if test="${func.funcType==2}">selected='selected'</c:if>>框架</option>
           <option value="3" <c:if test="${func.funcType==3}">selected='selected'</c:if>>接口</option>
	    </select></span>
    	<span class="nameBasInput" style="width:420px;"><b>功能地址：</b><input class="easyui-validatebox" name="funcUrl" value="${func.funcUrl }" data-options="required:true" style="width:280px;"/></span>
    	<span class="nameBasInput"><b>功能描述：</b><input class="easyui-validatebox" name="description" value="${func.description }" data-options="required:true" /></span>
    </form>
  </div>