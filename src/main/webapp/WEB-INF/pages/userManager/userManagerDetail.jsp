<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

		<form action="<%=basePath %>UserManagerController/updateUserInfo" method="post" id="searchForm">
		    <div class="emTabsCon" title="人员信息">   
		        <div class="searchFormBox">
		        	<span class="nameBas"><b>登陆账号：</b><input class="easyui-validatebox" name="loginid" value="${userManager.loginid}" data-options="required:true"/></span>
		            <span class="nameBas"><b>人员姓名： </b><input class="easyui-validatebox" name="userName" value="${userManager.userName}" data-options="required:true"/></span>
		            <span class="nameBas"><b>性别： </b><input class="easyui-combobox" name="gender" value="${userManager.gender}" data-options="width:150,editable:false,url:'<%=basePath%>service/getTypeList?kind=BR',textField:'type',valueField:'type',panelHeight:200" /></span>
		            <span class="nameBas"><b>是否启用密钥： </b><select class="easyui-combobox" name="certifykind" panelHeight="auto">
		                <option value="是">是</option>
		              		<option value="否">否</option>
		            </select></span>
		            <span class="nameBas"><b>密码：</b><input class="easyui-validatebox" name="password" type="password" value="${userManager.password}" data-options="required:true"/></span>
		        	<span class="nameBas"><b>人员类型：</b><input class="easyui-combobox" name="usertype" value="${userManager.usertype}" data-options="width:150,editable:false,url:'<%=basePath%>service/getTypeList?kind=BQ',textField:'type',valueField:'type',panelHeight:200" /></span>
		            <span class="nameBas"><b>证件类型：</b><input class="easyui-combobox" name="idtype" id="idtype" value="${userManager.idtype}" data-options="width:150,editable:false,url:'<%=basePath%>service/getTypeList?kind=BS',textField:'type',valueField:'type',panelHeight:200"/></span>
		        	<span class="nameBas"><b>证件号码：</b><input class="easyui-validatebox" name="ownerid" value="${userManager.ownerid}" data-options="required:true,validType:'idcard[\'#idtype\']'"/></span>
		        	<span class="nameBas"><b>证件地址：</b><input class="easyui-validatebox" name="certaddr" value="${userManager.certaddr}"/></span>
		        	<span class="nameBas"><b>国籍：</b><input class="easyui-combobox" name="nationality" data-options="width:150,editable:false,url:'<%=basePath%>service/getTypeList?kind=AR',textField:'type',valueField:'type',panelHeight:200"  value="${userManager.nationality}" /></span>
		        	<span class="nameBas"><b>出生年月：</b><input class="easyui-datebox" name="birthdate" value='<fmt:formatDate value="${userManager.birthdate}" pattern="yyyy/MM/dd"/>'/></span>
		        	<span class="nameBas"><b>联系电话：</b><input class="easyui-validatebox" name="phone" value="${userManager.phone}"/></span>
		        	<span class="nameBas"><b>学历：</b><input class="easyui-validatebox" name="knowledge" value="${userManager.knowledge}"/></span>
		        	<span class="nameBas"><b>电子邮件：</b><input class="easyui-validatebox" name="email" value="${userManager.email}" data-options="validType:'email'"/></span>
		        	<span class="nameBas"><b>通讯地址：</b><input class="easyui-validatebox" name="address" value="${userManager.address}"/></span>
		        	<span class="nameBas"><b>传真：</b><input class="easyui-validatebox" name="fax" value="${userManager.fax}"/></span>
		        	<span class="nameBas"><b>备注：</b><input class="easyui-validatebox" name="note" value="${userManager.note}"/></span>
		        	<span class="nameBas"><b>是否有效： </b><select class="easyui-combobox" name="isactive" panelHeight="auto" value="${userManager.isactive}">
		            	<option value="1">是</option>
		              		<option value="0">否</option>
		            </select></span>
		           <span class="nameBas"><b>职位：</b><input class="easyui-combobox" id="pname" name="pname" value="${postionid}" data-options="width:150,editable:false,url:'<%=basePath%>service/getUserPostion?orgid=${orgid}',textField:'pname',valueField:'id',panelHeight:200"/></span>
			    </div>    
		    </div>
			<input type="hidden" id="id" name="id" value="${userManager.id}"/>
			<input type="hidden" id="positionid" name="positionid" value=""/>
		</form>
		
	<script type="text/javascript">		
			function MybeforeSubmit(){
				$("#positionid").val($("#pname").combobox('getValue'));
			}
	</script>		