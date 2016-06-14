<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="cn.ccsgroup.ccsframework.entity.Model"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<script type="text/javascript" src="../js/jquery-1.7.1.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var isActive=<%=((Model)(request.getAttribute("_Moduel"))).getIsActive()%>;
		$("#facebox #sel").find("option").each(function(value){
			if($(this).attr("value")==isActive)
			$(this).attr("selected",true);
		})
	})
	
</script>
<div id="messages">
    <h4>模块管理</h4>
    <form action="updateModel" method="post">
      <p>
	      <span><b>模块名称：</b><input class="text-input small-input" type="text" id="modName" name="modName"  value="${_Moduel.modName}"/></span>
	      <span><b>模块描述：</b><input class="text-input small-input" type="text" id="description" name="description"  value="${_Moduel.description}" /></span>
      </p>
      <p>
      	  <span><b>是否有效：</b><select id="sel" name="isActive" class="small-input" >
                <option value="1">是</option>
                <option value="-1">否</option>
              </select>
           </span>
            <span>
            	<b>模块标识：</b><input class="text-input small-input" type="text" id="alias" name="alias" value="${_Moduel.alias}" />
            </span>
           <input  style="display: none" id="id" name="id" value="${_Moduel.id}"/> 
      </p>
      <fieldset>
      
      <div class="default-tab" id="main-content">
        </div>
      
      
      </fieldset>
      <div class="formBut">
      	<input class="button butCenter" type="submit" value="保 存" />
      	<input class="button butCenter butGray" type="button" value="取 消"  />
      </div>
    </form>
   
  </div>