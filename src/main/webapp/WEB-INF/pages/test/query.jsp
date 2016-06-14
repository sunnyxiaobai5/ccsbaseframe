<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>该页面不存在</title>
</head>
<body>
<form style="display:none" id="queryFrom" name="queryFrom" target="queryList" method="post" action="<%=basePath %>test/query">
	用户名：<input id="userName" name="userName" /><br/>
	密码：<input id="loginName" name="loginName" /><br/>
	每页<input type="text" name="rowPage" value="3"/>记录
	<input type="hidden" name="page"/>
	<input type="submit" value="提交" />
</form>
<form id="queryFrom2" name="queryFrom2" target="queryList" method="post" action="<%=basePath %>service/queryTest">
	用户名：<input id="userName" name="userName" /><br/>
	密码：<input id="loginName" name="loginName" /><br/>
	每页<input type="text" name="rowPage" value="3"/>记录
	<input type="hidden" name="page"/>
	<input type="hidden" name="view" value="test/queryList" />  
	<input type="submit" value="提交" />
</form>
</body>
</html>