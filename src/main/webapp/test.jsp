<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<script type="text/javascript" src="js/jquery-1.7.1.js" charset="gbk"></script>
<script type="text/javascript">
	function a(){
	 var req = "name=动起来&userId=007";
	 $.ajax({
    	type:"POST",
		url:"test/ajax",
		data:req,
		dataType:"json",
		success:function(data){
			alert(data.tb.userName+":"+data.tb.userId);
		}
	});
	}
	function b(){
	 var req = "exc=1234";
	 $.ajax({
    	type:"POST",
		url:"test/delBlob",
		data:req,
		dataType:"json",
		success:function(data){
			alert(data.ok);
		}
	});
	}
	function c(){
	 var req = "exc=1234&page=";
	 $.ajax({
    	type:"POST",
		url:"service/getUsers",
		data:req,
		dataType:"json",
		success:function(data){
			$.each(data.data,function(i ,value){
				alert(value.name);
			});
		}
	});
	}
	
	function d(){
	 var req = "exc=1234";
	 $.ajax({
    	type:"POST",
		url:"test/testLog",
		data:req,
		dataType:"json",
		success:function(data){
			alert(data.messages);
		},
		error:function(data){
			alert(data.error);
		}
	});
	}
	function gg(){
		$("#pageName").val("dadf");
		document.getElementById("form1").submit();
	}
	function ireport(){
	 var req = "reportName=1234&userId=";
	 $.ajax({
    	type:"POST",
		url:"ReportManagerController/printOrView",
		data:req,
		dataType:"json",
		success:function(data){
			alert("ok");
		},
		error:function(data){
		}
	});
	}
</script>
	<body>
		<a href="test/1">test/1</a><br/>
		<a href="test/2/ccsBF?pageName=test/01">会话超时</a><br/>
		<a href="test/2/ccsBF?pageName=test/01">test/2/ccsBF?pageName=test/02（404）</a><br/>
		<a href="forward/test|main" target="_blank">分页测试</a><br/>
		<a href="javascript:a()">AJAX调用</a><br/>
		<a href="javascript:d()">日志</a><br/>
		<p></p>
		<form id="form1" action="test/blob" method="post" enctype= "multipart/form-data">
			<input type="file" name="imgs"/><br/>
			id<input type="text" name="id" ></input><br/>
			pageName<input type="text" id="pageName" name="pageName" ></input><br/>
			userName<input type="text" id="userName" name="userName" ></input><br/>
			<button onclick="gg();"  value="提交">提交</button>
		</form>
		<a href="javascript:b()">删除</a><br/>
		<br/>
		<div>
			<form action="service/getUsers" method="post">
				<input type="text" name="view" value="test/genneric" />
				<input type="submit" />
			</form>
		</div>
		<p></p>
		<div>
			<form action="" method="post" onsubmit="return false;">
				<button onclick="c();"value="提交">提交</button>
			</form>
		</div>
		<p></p>
		<select id="test">
			<option>1</option>
			<option value="-2">2</option>
			<option value="-1">3</option>
		</select>
		<script type="text/javascript">
			$("#test").find("option").each(function(value){
				if($(this).attr("value")==1)$(this).attr("selected",true);
			});
			$("#test").find("option[value='-2']").attr("selected",true);
		</script>
	</body>
</html>
