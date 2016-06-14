<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>

    <!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
<div class="easyui-layout" data-options="fit:true">    
    <form action="<%=basePath %>RoleController/setUserPerm/${entity.id }"  name="updateForm" method="post">
	    <div data-options="region:'west',split:true" style="width:370px;">
	      <div class="popFormBox"> 
		      <span class="nameBas"><b>用户名称：</b><input class="easyui-validatebox" readonly="readonly" type="text"  value="${entity.userName }"/></span><br/><br/>
		      <span class="nameBas"><b>用户类型：</b><input class="easyui-validatebox" readonly="readonly" type="text"  value="${entity.usertype }"/></span><br/><br/>
	      	  <input type="hidden"  name="id" value="${roleId }" />
		      <span class="nameBas"><b>角色名称：</b><input class="easyui-validatebox" data-options="required:true;validType:'maxLength[8]'" type="text"  name="rName" /></span><br/><br/>
		      <span class="nameBas"><b>角色描述：</b><input class="easyui-validatebox" data-options="required:true;validType:'maxLength[64]'" type="text"  name="remark" /></span><br/><br/>
	      </div>
	    </div>
    	<div data-options="region:'center'" style="padding:5px;background:#eee;width:220px;">
	    	<div class="easyui-layout" data-options="fit:true">
				<%-- <div data-options="region:'west',title:'组织机构',split:true" style="width:220px;">
					<ul id="orgTree" class="easyui-tree" data-options="url:'<%=basePath %>org/user/${loginUserId }/${roleId }',method:'get',animate:true,checkbox:true,lines:true,onCheck:orgTreeOnCheck"></ul>
				</div>
				<div data-options="region:'center',split:true">
					<div class="easyui-layout" data-options="fit:true"> --%>
						<div data-options="region:'center',title:'权限列表',split:true" style="width:220px;">
							<ul id="sysTree" class="easyui-tree" data-options="url:'<%=basePath %>sysTree/${roleId }',method:'get',animate:true,checkbox:true"></ul>
						</div>
				<!-- 	 </div>
				</div>   -->
			</div>
	    </div>
    	<%-- <div data-options="region:'center',title:'权限列表'" style="padding:5px;background:#eee;width:220px;">
	    	<ul id="sysTree_update" class="easyui-tree" data-options="url:'<%=basePath %>RoleController/sysTree/${entity.id }',method:'get',animate:true,checkbox:true"></ul>
	    </div> --%>
		<input type="hidden" id="orgTreeArr" name="orgTreeArr" />
		<input type="hidden" id="sysTreeArr" name="sysTreeArr" />
    </form>
</div>
<script type="text/javascript" >
function orgTreeOnCheck(node,checked){
	var temp = TreeGetChecked2("orgTree");
	if(temp.indexOf("_")>0)temp = temp.substring(0, temp.length-1);
	var url = "<%=basePath %>sysTree/${roleId }/"+(temp!=""?temp:"000000");
	$("#sysTree").tree({url:url});
}
function MybeforeSubmit(){
	var data = $("#sysTree").gctrt();
	$("#sysTreeArr").val(JSON.stringify(data));
}
</script>