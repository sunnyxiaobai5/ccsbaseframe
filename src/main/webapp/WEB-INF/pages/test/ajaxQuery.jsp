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
<script type="text/javascript" src="<%= basePath%>js/jquery-1.7.1.min.js" ></script>
<script type="text/javascript" src="<%= basePath%>js/jquery.form.min.js" ></script>
<!--                       CSS                       -->
<link rel="stylesheet" href="../css/easyui.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="../css/jquery-ui.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../css/invalid.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../css/common.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../css/fun.css" type="text/css" media="screen" />
<!--                       Javascripts                       -->
<script type="text/javascript" src="../js/drogselector.js"></script>
<script type="text/javascript" src="../js/simpla.jquery.configuration.js"></script>

<script type="text/javascript" src="../js/pagination.js"></script>
<title>该页面不存在</title>
<script type="text/javascript">
function aa(){
 	var req = $("#queryFrom").formSerialize();
	$.ajax({
	   	type:"POST",
		url:"<%= basePath%>service/queryTest",
		data:req,
		dataType:"json",
		success:function(data){
			$("#content").empty();
			$.each(data.data,function(i ,value){
				$("#content").append(value.userName);
			});
			$("#content").append("<br/>");
			$("#content").append(data.output);
			$("#content").append(data.pager);
		}
	});
}
</script>
</head>
<body style="width:100%;">
<div >
<form id="queryFrom" name="queryFrom"  method="post" action="" onsubmit="return false;">
	用户名：<input id="userName" name="userName" /><br/>
	密码：<input id="loginName" name="loginName" /><br/>
	每页<input type="text" name="rowPage" value="3"/>记录
	<input type="hidden" id="currentPage" name="currentPage" value="1"/>
	<input type="hidden" name="page"/>
	<input type="hidden" name="pagingJsName" value="aa"/>
</form>
	<button onclick="aa();">提交</button>
	</div>
<div id="content" class="pageBox">

</div>
</body>
</html>