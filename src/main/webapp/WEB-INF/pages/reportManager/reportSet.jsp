<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div class="popFormBox nameBasBig">
    <form action="<c:if test="${opt eq 'add'}">${pageContext.request.contextPath}/report/reportSet/add</c:if>
    <c:if test="${opt eq 'update'}">${pageContext.request.contextPath}/report/reportSet/update</c:if>" method="post" id="popForm">
        <input type="hidden" id="id" name="id" value="${reportSet.id}"/>
        <input type="hidden" id="status" name="status" value="${reportSet.status}"/>
		<span class="nameBas"> <b>报表类型：</b>
			<input type="text" name="rtype" class="easyui-validatebox" value="${reportSet.rtype}"
                   data-options="required:true,trim:true"/>
		</span>
		<span class="msgBox"><b class="msgBoxB">*</b>
		</span>

		<span class="nameBas"> <b>报表名称：</b>
            <input type="text" name="rname" class="easyui-validatebox" value="${reportSet.rname}"
                   data-options="required:true,trim:true"/>
		</span>
		<span class="msgBox"><b class="msgBoxB">*</b>
		</span>

		<span class="nameBas"> <b>报表文件：</b>
            <input type="text" name="rfile" class="easyui-validatebox" value="${reportSet.rfile}"
                   data-options="required:true,trim:true"/>
		</span>
		<span class="msgBox"><b class="msgBoxB">*</b>
		</span>

		<span class="nameBas"> <b>报表URL：</b>
            <input type="text" name="rfileurl" class="easyui-validatebox" value="${reportSet.rfileurl}"
                   data-options="required:true,trim:true"/>
		</span>
		<span class="msgBox"><b class="msgBoxB">*</b>
		</span>
		<span class="nameBas"> <b>所属系统：</b>
            <select id="rabb" class="easyui-combobox" name="rabb" style="width:260px;" data-options="required:true,editable:false">
                <c:forEach var="sys" items="${systems}">
                    <option value="${sys.id}" <c:if test="${sys.id eq reportSet.rabb}">selected="selected"</c:if>>${sys.sysName}</option>
                </c:forEach>
            </select>
		</span>
		<span class="msgBox"><b class="msgBoxB">*</b>
		</span>
        <span class="nameBas"> <b>描述：</b>
            <input type="text" name="description" class="easyui-validatebox" value="${reportSet.description}"
                   data-options="trim:true"/>
		</span>

    </form>
</div>
