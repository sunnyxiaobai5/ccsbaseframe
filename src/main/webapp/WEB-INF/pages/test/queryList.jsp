<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--                       CSS                       -->
<link rel="stylesheet" href="../css/easyui.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="../css/jquery-ui.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../css/invalid.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../css/common.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../css/fun.css" type="text/css" media="screen" />
<!--                       Javascripts                       -->
<script type="text/javascript" src="../js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="../js/drogselector.js"></script>
<script type="text/javascript" src="../js/simpla.jquery.configuration.js"></script>
<script type="text/javascript" src="../js/facebox.js"></script>
<script type="text/javascript" src="../js/jquery.wysiwyg.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>
<script type="text/javascript" src="../js/jquery.tree.js"></script>
<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/pagination.js"></script>
<title>该页面不存在</title>
</head>
<body>
<div style="margin:50px auto;width:600px;height:auto;">
	<ul style="font-size:12px;color:#666;line-height:23px;">
		<c:forEach items="${data }" var="list">
			<li>${list.userName }:${list.userId }  岁</li>
		</c:forEach>
	</ul>
	${output }
	<div class="pageBox">${pager }</div>
</div>
</body>
</html>