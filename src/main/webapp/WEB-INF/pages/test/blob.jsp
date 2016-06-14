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
<title>BLOB</title>
</head>
<body>
<div style="margin:50px auto;width:600px;height:400px;">
	<ul style="font-size:12px;color:#666;line-height:23px;">
		<li>${tb.id }</li>
		<li>${tb.img }</li>
		<li>国际化信息</li>
		<li><sp:message code="user.alreadyExists"></sp:message></li>
	</ul>
</div>
</body>
</html>