<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <div class="easyui-layout" data-options="fit:true">
	<textarea class="easyui-layout" data-options="region:'center',fit:true" readonly="readonly" style="overflow: scroll">
		${logs.opeparam }
	</textarea>
 </div>

