<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<script type="text/javascript" src="../js/jquery-1.7.1.js"></script>
<div id="operating">
    <h4>操作管理</h4>
    <form action="/CCSBaseFrame/OpsController/updateOps" method="post">
      <input type="hidden" name="id" value="${vOps.id}"/>
      <p>
	      <span><b>操作名称：</b><input class="text-input small-input" type="text" id="small-input" name="opsName" value="${vOps.opsName }"/></span>
	      <span><b>是否启用：</b>
	      	  <select name="isActive" class="small-input">
                <option value="1" <c:if test="${vOps.isActive ==1}"> selected="selected"</c:if>>是</option>
                <option value="-1" <c:if test="${vOps.isActive ==-1}"> selected="selected"</c:if>>否</option>
              </select>
           </span>
      </p>
      <p>
          <span><b>是否验证：</b>
	      	  <select name="isStart" class="small-input">
                <option value="1" <c:if test="${vOps.isStart == 1 }"> selected="selected"</c:if> >是</option>
                <option value="-1" <c:if test="${vOps.isStart == -1 }"> selected="selected"</c:if>>否</option>
              </select>
           </span>
      </p>
      <fieldset>
      <div class="default-tab" id="main-content">
      </div>
      </fieldset>
      <div class="formBut">
      	<input class="button butCenter" type="submit" value="保 存" />
      	<input class="button butCenter butGray" type="reset" value="取 消" />
      </div>
    </form>
  </div>
