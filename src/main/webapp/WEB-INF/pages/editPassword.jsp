<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<div class="searchFormBox" style="margin: 30px 0 0 20px;">
    <form action="" method="" id="searchForm">
    	<span class="nameBas"><b>原密码：</b><input class="easyui-validatebox" type="password" name="oldpassword"/></span>
        <span class="nameBas"><b>新密码：</b><input class="easyui-validatebox" type="password" name="newpassword" /></span>
        <span class="nameBas"><b>重复新密码：</b><input class="easyui-validatebox" type="password" name="repeatpassword" /></span>
    </form>
</div>
