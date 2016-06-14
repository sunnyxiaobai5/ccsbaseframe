<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
	<div id="deploy">
    <!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
    <h4>操作分配</h4>
    <form action="#" method="post">
      <div class="deploy-box">
	      <h5>操作列表</h5>
	      <fieldset>
	      
	      <div class="default-tab" id="main-content">
	      	<div class="seachBox">
	          <select name="dropdown" class="seach-input">
                <option value="option1">Option 1</option>
                <option value="option2">Option 2</option>
                <option value="option3">Option 3</option>
                <option value="option4">Option 4</option>
              </select>
              <input class="text-input seach-input" type="text" id="small-input" name="small-input" />
              <input class="button butCenter" type="submit" value="搜 索" />
             </div>
             <div class="treeBox">
             	<ul class="sortable1 connectedSortable" oldData="" newAddData="">
             	</ul>
             </div>
	      </div>
	      </fieldset>
      </div>
      <div class="deploy-box">
	      <h5>已分配</h5>
	      <fieldset>
	      
	      <div class="default-tab" id="main-content">
	      	<div class="seachBox">
	          <select name="dropdown" class="seach-input">
                <option value="option1">Option 1</option>
                <option value="option2">Option 2</option>
                <option value="option3">Option 3</option>
                <option value="option4">Option 4</option>
              </select>
              <input class="text-input seach-input" type="text" id="small-input" name="small-input" />
              <input class="button butCenter" type="submit" value="搜 索" />
             </div>
             <div class="treeBox">
             	<ul class="sortable2 connectedSortable" oldData="" newAddData="">
				</ul>
	          </div>
	        </div>
	      </fieldset>
      </div>
      <div class="formBut">
      	<input class="button butCenter" type="submit" value="保 存" />
      	<input class="button butCenter butGray" type="submit" value="取 消" />
      </div>
    </form>
  </div>
  <script type="text/javascript" src="../js/fun.js"></script>