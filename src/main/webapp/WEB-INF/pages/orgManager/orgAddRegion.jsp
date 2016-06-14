<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<div class="popFormBox">
   	<form action="<%=basePath %>OrgController/addOrg" method="post">
    	 <span class="nameBas"><b>区域：</b><select name="orgType" class="easyui-combobox">
              <option value="option1">Option 1</option>
              <option value="option2">Option 2</option>
              <option value="option3">Option 3</option>
              <option value="option4">Option 4</option>
            </select>
         </span>
      	 <div class="formBut">
              <input class="button" type="submit" value="保 存" />
         </div>
</form>
</div>