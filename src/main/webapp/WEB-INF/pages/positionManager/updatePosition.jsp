<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="popFormBox">
      <form action="<%=basePath%>PositionController/updatePosition" method="post" id="popForm">
          <span class="nameBas">
          <b>所属组织： </b><input class="easyui-validatebox" value="${position.orgname}" readonly="readonly" data-options="required:true" /></span>
          <span class="nameBas">
          <b>职位名称：</b><input class="easyui-validatebox" name="pname" value="${position.pname}" data-options="required:true" /></span>
          <span class="nameBas">
          <b>职位编号： </b><input class="easyui-validatebox" name="pno" value="${position.pno}"/></span>
          <span class="nameBas">
          <b>职位描述： </b><input class="easyui-validatebox" name="description" value="${position.description}"/></span>
          <input type="hidden" name="id" value="${position.id}"></input/>
          <input type="hidden" name="orgid" value="${position.orgid}"></input/>
       </form>
</div>