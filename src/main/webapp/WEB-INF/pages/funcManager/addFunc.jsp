<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<div class="searchFormBox">
    <form action="<%=basePath %>FuncController/addFunc" method="post" id="searchForm">
    	<span class="nameBasInput"><b>功能名称：</b><input class="easyui-validatebox" name="funcName" data-options="required:true" /></span>
    	 <span class="nameBasInput"><b>功能类型： </b><select class="easyui-combobox" name="funcType" panelHeight="auto" data-options="required:true">
           <option value="1" >系统</option>
	       <option value="2" >框架</option>
           <option value="3" >接口</option>
	    </select></span>
    	<span class="nameBasInput"><b>功能地址：</b><input class="easyui-validatebox" name="funcUrl" data-options="required:true" /></span>
    	<span class="nameBasInput"><b>功能描述：</b><input class="easyui-validatebox" name="description" data-options="required:true" /></span>
    </form>
  </div>
