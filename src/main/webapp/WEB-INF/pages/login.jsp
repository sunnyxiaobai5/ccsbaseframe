<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>住房保障</title>
		<!--                       CSS                       -->
		<link rel="stylesheet" href="css/login.css" type="text/css" />
		<!--                       Javascripts                       -->
		<script type="text/javascript" src="js/jquery-1.10.1.min.js"></script>
		<script type="text/javascript">
		$(function(){
			var errors = '${errors}';
			if(errors != ""){
				$("#msgBox").html(errors);
			}
		});
		
		function keyLogin(){
			var username = $("#loginid").val();
		    var password = $("#password").val();
		    if (event.keyCode==13){   //回车键的键值为13
			     jQuery.ajax({
				     url:"http://127.0.0.1:8090/FuniReport/ReportServer?op=fr_auth&cmd=sso",
				     dataType:"jsonp",    
				     data:{"username":username,"password":password},     
				     jsonp:"callback",   
			     	 beforeSend:ajaxLoading,   
				     timeout:5000,   
				     success:function(data) {      
				    	 $("#loginForm").submit();
		            	return true;     			            
				     },
				     error:function(){     
				    	 $("#loginForm").submit();
			            	return true;     
				    } 
				 });  
		  }
		}
		
		function ajaxLoading(){
			var loadWidth = $(document).width();
			var loadHeight = $(document).height();
			//console.log(loadWidth+','+loadHeight);
			$(".loadingBox").css({"height":loadHeight,"width":loadWidth});
			$(".loadingBox").html("<img src='${pageContext.request.contextPath}/images/loginloader.gif' />");
			$(window).resize(function(){
				var loadWidth = $(document).width();
				var loadHeight = $(document).height();
				$(".loadingBox").css({"height":loadHeight,"width":loadWidth});
			});
		}
		
		function doSubmit(){
		    var username = $("#loginid").val();
		    var password = $("#password").val();
		
		     jQuery.ajax({
			     url:"http://127.0.0.1:8090/FuniReport/ReportServer?op=fr_auth&cmd=sso",
			     dataType:"jsonp",    
			     data:{"username":username,"password":password},     
			     jsonp:"callback",
			     beforeSend:ajaxLoading,   
			     timeout:5000,   
			     success:function(data) {      
			    	 $("#loginForm").submit();
	            	return true;     			            
			     },
			     error:function(){     
			    	 $("#loginForm").submit();
		            	return true;     
			    } 
			 }); 
		}
		</script>
	</head>
	<body onkeydown="keyLogin()">
		<div class="loadingBox"></div>
		<div class="bannerBox">
			<div class="navBox">
			<form action="SSOAuth/login" method="post" id="loginForm">
				<div class="loginLogo"><img src="images/newLoginLogo.png" /></div>
				<div class="imageBox"><img src="images/baNO1.png" /></div>
				<div class="loginBox">
					<div class="inputBox ttzh"><b>用户名：</b><span><input id="loginid" type="text" class="textStyle"  name="loginid" value="" /></span></div>
					<div class="inputBox"><b>密码：</b><span><input id="password" type="password" class="textStyle" name="password" value="" /></span></div>
					<input type="hidden" name="action" value="login">
					<div id="msgBox" class="msgBox"></div>
					<div class="buttonBox"><input type="button" class="buttonStyle" value="登 录" onclick="return doSubmit()"/></div>
				</div>
			</form>
				
			</div>
		</div>
		<div class="footerBox">
		<a href="<%=basePath%>run/environment">运行环境 </a><br/>
			成都数字房产系统 (c) 2001-2013<br />最低分辨率1024*768 请使用IE7及后续版本
		</div>
	</body>
</html>