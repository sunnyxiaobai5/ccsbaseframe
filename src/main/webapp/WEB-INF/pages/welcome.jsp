<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--                       CSS                       -->
<link rel="stylesheet" type="text/css" href="../js/libs/easyui/easyui.css">
<link rel="stylesheet" type="text/css" href="../js/libs/easyui/icon.css">
<link rel="stylesheet" href="../css/reeasyui.css" type="text/css" media="screen" />
<!--                       Javascripts                       -->
<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="../js/libs/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/libs/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript">
	//格式化操作列
	function rowFormater(val,row,index) {
		return '&nbsp;<a class="welALink"  href="<%=basePath%>InformationController/downloadFile?id='+row.id+'"> <img src="../images/download.png" /></a>&nbsp;';
	}
	function tool(url,name){
		window.open(url,name);
	}
		
</script> 
</head>
<body class="easyui-layout" data-options="fit:true">

	<!--左侧-->
    <div data-options="region:'center'"  data-options="fit:true,split:true" style="border:none"> 
      <div class="easyui-layout" data-options="fit:true" >  
        <div class="welAnnBox" data-options="region:'center',title:'系统公告'" >
       		<c:choose>
				<c:when test="${finfo != null}"> 
		        	<h3>${finfo.infotitle}</h3>
		        	<h4>发布人：${finfo.createruser}  &nbsp;&nbsp;  发布日期：${finfo.sysinsttime}</h4>
		        	<div class="welText">
		        		${finfo.infocontent} 
		        	</div>
				</c:when>     
				<c:otherwise>
					<h3>暂无公告</h3>
		        	<h4>发布人：无    发布日期：无</h4>
				</c:otherwise> 
			</c:choose>
        </div>  
        <div data-options="region:'south'" style="height:150px;border:none"  >
       	 	<table id="dg" class="easyui-datagrid" data-options="method:'get',pagination:false">
			  <thead>
                <tr>
                 	<th data-options="field:'filename',width:20">附件名称</th>   
					<th data-options="field:'fileformat',width:20">附件类型</th>   
					<th data-options="field:'filesize',width:20">附件大小</th> 
                    <th data-options="field:'true',align:'center',formatter:rowFormater">下载</th>
                </tr>
       		  </thead>
      		</table>
        </div>  
      </div> 
    </div>   

    <!--右侧-->
    <div data-options="region:'east',split:true" style="width:200px;border:none" class="easyui-layout">
         <div class='welMesList' data-options="region:'north',title:'温馨提示',split:true" style="height:200px;overflow: hidden;padding:4px;">
        	<script type="text/javascript" src="../js/libs/jscroller2-1.61.js"></script>
			<div id="scroller_container" style=" width:185px;height:156px;overflow:hidden;">
				<div class="jscroller2_up jscroller2_mousemove">
					<ul>
						<c:forEach items="${Info}" var="t">
						<c:if test="${t.infotype=='提示'}">
							<li><a href="#" title="${t.infotitle}">
								${t.infocontent}
							</a></li>
						</c:if>
					</c:forEach>
					</ul>
				</div>
			</div>
       	 </div>
         <div class='welMesList' data-options="region:'center',title:'公告列表',split:true">
	         <ul>
	         	<c:forEach items="${Info}" var="o">
	         	<c:if test="${o.infotype=='公告'}">
	         		<li><a href="<%=basePath%>InformationController/notice?id=${o.id}"  title="${o.infotitle}">
	         		<c:choose>
						<c:when test="${fn:length(o.infotitle) > 10}"> 
							${fn:substring(o.infotitle,0,10)}······
						</c:when>     
						<c:otherwise> 
							${o.infotitle}
						</c:otherwise> 
					</c:choose>
	         		</a></li>
	         	</c:if>
	         	</c:forEach>
	         </ul>
         </div>   

         <div class="welToolList" data-options="region:'south',split:true" style="min-height:100px;">
         <c:forEach items="${Info}" var="g">
      	 		<c:if test="${g.infotype=='工具'}">
      	 			<span onclick="tool('${g.toolurl}','${g.infotitle}')">${g.infotitle}</span>
      	 		</c:if>
       	 </c:forEach>
         </div>
    </div> 
</body>
<script type="text/javascript">
	var s= ${finfo!=null};
	if(s){
	   var a = "<%=basePath%>InformationController/getAnnexList?id="+${finfo.id}
	   $("#dg").datagrid({url:a});
	}
</script>
</html> 

