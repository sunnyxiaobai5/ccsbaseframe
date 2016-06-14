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
<title>分页测试页面</title>
</head>
<body>
<iframe id="query" name="query" src="<%=basePath %>forward/test|query"></iframe>
<iframe id="ajaxquery" name="ajaxquery" src="<%=basePath %>forward/test|ajaxQuery" width="600px"></iframe>
<iframe id="queryList" name="queryList" width="100%" height="400px" src="<%=basePath %>forward/test|queryList"></iframe>
</body>
</html>