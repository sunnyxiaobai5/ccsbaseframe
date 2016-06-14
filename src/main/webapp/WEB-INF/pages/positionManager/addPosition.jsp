<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="popFormBox">
      <form action="<%=basePath %>PositionController/addPosition" method="post" id="popForm">
         <span class="nameBas">
          <b>所属组织： </b><input class="easyui-validatebox" value="${orgname}" readonly="readonly" data-options="required:true" /></span>
          <span class="nameBas">
          <b>职位名称：</b><input class="easyui-validatebox" name="pname" data-options="required:true" /></span>
          <span class="nameBas">
          <b>职位编号： </b><input class="easyui-validatebox" name="pno"/></span>
          <span class="nameBas">
          <b>职位描述： </b><input class="easyui-validatebox" name="description"/></span>
          <input type="hidden" name="orgid" value="${orgid}" />
       </form>
</div>