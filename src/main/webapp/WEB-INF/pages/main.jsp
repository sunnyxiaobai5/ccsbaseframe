<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>index</title>
<script type="text/javascript">
	var basePath = "<%= basePath%>";
</script>
<link rel="stylesheet" href="../css/invalid.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../css/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" href="../js/libs/easyui/easyui.css"/>
<link rel="stylesheet" type="text/css" href="../js/libs/easyui/icon.css"/>
<link rel="stylesheet" href="../css/reeasyui.css" type="text/css" media="screen" />
<script type="text/javascript" src="../js/comet4j/comet4j.js"></script>
<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="../js/libs/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/libs/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/common.js"></script>
<!-- 左边菜单滚动 -->
<link rel="stylesheet" href="../js/libs/custom-scrollbar-plugin/jquery.mCustomScrollbar.css" type="text/css" media="screen" />
<script type="text/javascript" src="../js/libs/custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="../js/index.js"></script>

<script type="text/javascript">
	function btn_changeSys(briefName)
	{
		$.ajax({
			type: "get",
			dataType: "json",
			url: "<%=basePath%>ccs/changeSysList",
			contentType: "application/json; charset=utf-8",
			data:'briefName='+briefName,
			success: function(val)
			{
				closeMenuList();
				var mList = val.userTree;
				var html_ = "";
				for(var i = 0;i < mList.length;i ++)
				{	
					html_ += "<li>";
					var modx = mList[i];
					if(modx.sortNo == 1)
					{
			        	html_ += "<a href='#' class='nav-top-item current' target='"+modx.modName+"' >" + modx.modName+"</a>"
					}else
					{
			        	html_ += "<a href='#' class='nav-top-item' target='"+modx.modName+"' >" + modx.modName+"</a>"
					}
					var fList = modx.fList;
					html_ += "<ul style='display:none'>";
					for(var j = 0;j < fList.length;j ++)
					{
						var func = fList[j];
						html_ += "<li><a class='toTab' href='"+ func.funcUrl+"' id='" +func.funcName+ "' data-tabId='"+func.id+"' target=''>"+ func.funcName+"</a></li>";
					}
        			html_ += "</ul></li>";
				}
				$('#main-nav').html(html_);
				//menuLoad();
				//menuTabs();
			},
			error: function(ext)
			{
				Cmessager.alert('', "切换系统失败!");	
			}
		});
		
		function closeMenuList()
		{
				var menuListBox = $(".menuListBox");
				if (menuListBox.css("left") == "-193px") {
	                menuListBox.animate({ left: "-193px" }, 500, function () {
	                $(".arBox").removeClass("leftAr");
	                $(".arBox").addClass("rightAr");
	              })
	       	   }
	          	else {
	              	menuListBox.animate({ left: "-193px" }, 500, function () {
		                $(".arBox").removeClass("rightAr");
		                $(".arBox").addClass("leftAr");
	              })
	         	}
		}
	}
	
	function Onclick(){
		if($("#tt").tabs('exists' , '短信列表')){
			$("#tt").tabs('select' ,'短信列表');
		}else{
		 	var	tags = '<iframe scrolling="auto" frameborder="0"  src="<%=basePath %>/sms/findSms" style="width:100%;height:100%;background:#F0F0F0"></iframe>';
			$("#tt").tabs('add',{
				title:'短信列表',
				closable:true,
				content:tags,
				fit:true
			});
			$('#tt').tabs('getTab', '短信列表').panel('body').css({'overflow':'hidden'});
		}
	}
	
	$(function(){
		$("#ms").click(function(){
			Onclick();
		});
		$("#westch").click(function(){
			$('#mainLayout').layout('collapse','west');
		});
        $("#mail").click(function(){
            if($("#tt").tabs('exists' , '消息列表')){
                $("#tt").tabs('select' ,'消息列表');
            }else{
                var	tags = '<iframe scrolling="auto" frameborder="0"  src="<%=basePath %>/mail" style="width:100%;height:100%;background:#F0F0F0"></iframe>';
                $("#tt").tabs('add',{
                    title:'消息列表',
                    closable:true,
                    content:tags,
                    fit:true
                });
                $('#tt').tabs('getTab', '消息列表').panel('body').css({'overflow':'hidden'});
            }
        });


		//监听消息
/*		JS.Engine.on({
			'${userSession.loginid}' : function (msg) {//侦听一个channel
				var title = '<a href="#" onclick="$(\'#mail\').trigger(\'click\');">'+ msg.title +'</a>';
				Cmessager.show({
						title:"您有新的消息",
						msg: title,
						timeout:5000
				});
			}
		});
		JS.Engine.start('${pageContext.request.contextPath}/conn');

		$.getJSON('${pageContext.request.contextPath}/mail/mailCount',function(count){
			$('#mail').text(count);
		});*/

	});
	
	function editPassword(){
		openDialog("title:'修改密码',winId:'winId',url:'<%=basePath %>/ccs/editPassword',buttons:[{text:'保存',handler:function(){send()}}],width:450,height:260");
	}
	
	function send(){
		$.ajax({
			url:"<%=basePath %>/ccs/checkUser",
			type:"post",
			data:$("#searchForm").serialize(),
			dataType:"json",
			success:function(info){
				if(info == 1){
					GV.top$("#winId").dialog('close');
					$.messager.show({
						title:'信息提示',
						msg:'修改成功',
						timeout:1000,
						showType:'slide'
					});
				}else{
					$.messager.alert('信息提示',info,'info');
				}
			}	
		});
	}
	
	//退出
	function signout(){
		
		jQuery.ajax({   
		     url:"http://172.29.33.110:8080/FuniReport/ReportServer?op=fr_auth&cmd=ssout",//报表服务器   
		     dataType:"jsonp",//跨域采用jsonp方式   
		     jsonp:"callback",   
		     timeout:5000,//超时时间（单位：毫秒）   
		     success:function(data) {      
		     },   
		     error:function(){   
		           // 登出失败（超时或服务器其他错误）   
		     }   
		});
		
		location.href = '<%=basePath %>SSOAuth/login?action=logout';
		
		return;
	}
		
</script>
</head>
<body>
<div id="mainLayout" data-options="fit:true"> 
	<div class="areaBox" style="display: none">
		<div class="reginTitle"><b>区域列表</b><span><img src="../images/closeTab.png" /></span></div>
		<div class="reginCurrent">当前区域：<span>${regionName}</span></div>
		<div class="reginBut">
			<input value="确定" type="button" />
		</div>
	</div>
    <!-- west -->
    <div data-options="region:'west',border:false" style="width:269px;">
        <div id="sidebar">
            <div class="bigMenu">
                <div class="menuListBox">
  				<div class="closeBigBox">系统列表<img src="../images/closeBigMenu.png" /></div>
		  			<c:forEach items="${sysPower}" var="Logisystem">
		  				<div class="iconName" onclick="btn_changeSys(${Logisystem.id})"><img src="<%=basePath %>SysController/getSysLogo?id=${Logisystem.id }" width="32" /><span>${Logisystem.sysName }</span></div>
		  			</c:forEach>
		  		</div>
                <div class="arBox leftAr"></div>
            </div>
            <div class="sysBox">
                <div class="updown upBox"></div>
                <div class="updown downBox"></div>
                <ul class="sysIcon">
			  		<c:forEach items="${sysPower}" var="xLogi">
				  		<li><img src="<%=basePath %>SysController/getSysLogo?id=${xLogi.id }" width="27" onclick="btn_changeSys(${xLogi.id})" /></li>
			  		</c:forEach>
			  	</ul>
            </div>
            <div id="sidebar-wrapper">
            	<div id="westch"></div>
				<img id="logo" src="../images/mainLogo.png" alt="成都市数字房产政务服务CONE平台" />
                <div id="profile-links"><img src="../images/icons/user.png" align="bottom" /> 当前用户: <a href="#" title="当前登录用户">${userSession.userName}</a><span id="ms" class="msgNum">${smsCount}</span><%--<span id="mail" class="msgNum talk"></span>--%></div>
                <div id="setupBox"><a href="javascript:location.reload()" title="首页">首页</a> | <a href="#" onclick="editPassword()" title="修改密码">修改密码</a> | <a href="javascript:void(0);" title="退出" onclick="signout()">退出</a></div>
                <div class="curRegin">
                	<b class="operArea" title="操作区域">操作区域</b>
                	<span>${regionName}</span>
                </div>
                <div id="main-menu" style="overflow:auto;">
                    <ul id="main-nav">
			        <!-- 封装树 -->
			        <c:forEach items="${userTree}" var="modx" varStatus="index">
			        	<li> 
			        	<c:if test="${index.count == 1}">
				        	<a href="#" class="nav-top-item current" target="${modx.modName }"> ${modx.modName }</a>
			        	</c:if>
			        	<c:if test="${index.count != 1}">
			        		<a href="#" class="nav-top-item" target="${modx.modName }"> ${modx.modName }</a>
			        	</c:if>
			        		<ul>
				        		<c:forEach items="${modx.fList}" var="func">
				        			<li><a class="toTab" href="${func.funcUrl }" id="${func.funcName }" data-tabId="${func.id }" target="">${func.funcName }</a></li>
				        		</c:forEach>
			        		</ul>
			        	</li>
			        </c:forEach>
			      <li style="height: 50px;"></li>
			      </ul>
                </div>
                <!-- End #main-nav --> 
            </div>
        </div>
        <!-- End #sidebar --> 
    </div>
    <!-- center -->
    <div data-options="region:'center',border:false">
    	<div id="tt" class="easyui-tabs" fit="true" border="false" plain="true">
            <div title="首页" style="overflow:hidden;">
            	<iframe scrolling="auto" frameborder="0"  src="<%= basePath%>InformationController/notice" style="width:100%;height:100%;background:#F0F0F0"></iframe>
            </div>
        </div>
    </div>
</div>
</body>
<!-- Download From www.exet.tk-->
</html>

