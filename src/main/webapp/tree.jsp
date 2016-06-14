<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>index</title>
<!--                       CSS                       -->
<link rel="stylesheet" type="text/css" href="js/libs/easyui/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/libs/easyui/icon.css" />
<link rel="stylesheet" href="css/reeasyui.css" type="text/css" media="screen" />
<!--                       Javascripts                       -->
<script type="text/javascript" src="js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="js/libs/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/libs/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/common.js"></script>
</head>
<body class="easyui-layout" data-options="fit:true"> 
<script type="text/javascript">
function setValue(self,val){
} 
</script>

<div data-options="region:'west',title:'系统用户树',split:true" style="width:220px;">
	<input type="text" /> <button onclick=""></button>
	<ul id="setUserTree" class="easyui-tree" data-options="url:'<%=basePath %>RoleController/orgUser/94',method:'get',animate:true,checkbox:true,lines:true"></ul>
</div>  

<div data-options="region:'center',split:true">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',title:'用户区域树',split:true" style="width:220px;">
			<input type="text" /> <button onclick=""></button>
			<ul id="setUserTree2" class="easyui-tree" data-options="url:'<%=basePath %>region/user/28',method:'get',animate:true,checkbox:true,lines:true"></ul>
		</div>
	 </div>
</div>  
</body>
</html>
