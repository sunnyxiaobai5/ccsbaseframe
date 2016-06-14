<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="popFormBox nameBasBig">
	<form 
	<c:if test="${empty reportSetPara.id}">action="${pageContext.request.contextPath}/report/reportSet/para/add"</c:if>
	<c:if test="${not empty reportSetPara.id}">action="${pageContext.request.contextPath}/report/reportSet/para/update"</c:if>
	 method="post" id="popForm">
	 	<c:if test="${not empty reportSetPara.id}">
			<input type="hidden" name="id" value="${reportSetPara.id}"   />
		</c:if>
        <input type="hidden" name="reportId" value="${reportId}"   />
		
		<span class="nameBas"> <b>名称：</b>
			<input type="text" name="name" class="easyui-validatebox" value="${reportSetPara.name}"
				data-options="required:true"/>
		</span>
		<span class="msgBox"><b class="msgBoxB">*</b></span>
		<span class="nameBas"> <b>参数名称：</b>
			<input type="text" name="paraName" class="easyui-validatebox" value="${reportSetPara.paraName}"
				data-options="required:true"/>
		</span>
		<span class="msgBox"><b class="msgBoxB">*</b></span>
	</form>
</div>