<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<script type="text/javascript" src="../js/jquery-1.7.1.js"></script>
<div id="messages">
    <!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
    <h4>功能管理</h4>
    <form action="updateFunc" id="subFunc" method="post">
      <input type="hidden" id="id" name="id" value="${vFunc.id}" /> 	
      <input type="hidden" id="isActive" name="isActive" value="${vFunc.isActive}" /> 	
      <p>
	      <span><b>功能名称：</b><input class="text-input small-input" type="text" id="funcName" value="${vFunc.funcName }" name="funcName" /></span>
	      <span><b>功能类型：</b>
	      	  <select name=funcType class="small-input">
                <option value="1" <c:if test="${vFunc.funcType == 1}">selected="selected"</c:if> >参数</option>
                <option value="2" <c:if test="${vFunc.funcType == 2}">selected="selected"</c:if> >系统</option>
                <option value="3" <c:if test="${vFunc.funcType == 3}">selected="selected"</c:if> >框架</option>
              </select>
           </span>
      </p>
      <p>
          <b>功能地址：</b><input class="text-input medium-input" type="text" id="funcUrl" value="${vFunc.funcUrl }" name="funcUrl" />
      </p>
      <p>
          <b>功能描述：</b><input class="text-input medium-input" type="text" id="description" value="${vFunc.description }" name="description" />
      </p>
      <fieldset>
      <div class="default-tab" id="main-content">
      </div>
      </fieldset>
      <div class="formBut">
      	<input class="button butCenter" type="submit"  value="保 存" />
      	<input class="button butCenter butGray" type="reset" value="取 消" />
      </div>
    </form>
  </div>