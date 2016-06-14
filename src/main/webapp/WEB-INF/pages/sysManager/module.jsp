<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模块管理</title>
<!--                       CSS                       -->
<link rel="stylesheet" href="../css/invalid.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../css/common.css" type="text/css" media="screen" />
<!--                       Javascripts                       -->
<script type="text/javascript" src="../js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="../js/simpla.jquery.configuration.js"></script>
<script type="text/javascript" src="../js/facebox.js"></script>
<script type="text/javascript" src="../js/jquery.wysiwyg.js"></script>

<script type="text/javascript">
	// 删除功能
	function btn_dele(modId)
	{
		//alert("-----------"+modId)
		// ajax提交数据删除
		if(confirm("你确认要删除该模块吗?")){
			$.ajax({
				type: "POST",
				url: "delModel",
				data: {"modelId":modId},
				success: function(data)
				{
					var _List = data._List;
					var _result = "";
					for(var i = 0;i < _List.length; i++)
					{
						var model =_List[i];
						_result += "<tr><td><input type='checkbox' /></td>"; // 复选框
						_result += "<td>"+model.id+"</td>";// 序号
						_result += "<td>"+model.modName+"</td>";// 名称
						_result += "<td>"+model.description+"</td>";// 描述
						_result += "<td>";
						_result += " <a href='getModelbyid?modelId="+model.id+"}' rel='modal' title='Edit'><img src='../images/icons/pencil.png' alt='修改' /></a> ";
						_result += " <a href='#' title='删除' onclick='btn_dele("+model.id+")'><img src='../images/icons/cross.png' alt='删除' /></a> ";
						_result += "</td></tr>";
					}
					$('#tb').html(_result);
					alert(data._result);
				}
			});
		}
	}
	

//	function btn_update(id,modename,description,isActive){
//		//alert(id+"===="+isActive);
//		$("#id").val(id);
//		$("#modName").val(modename);
//		$("#description").val(description);
//		//alert($("#sel").find("option[value='"+isActive+"']").attr("selected"));
//		$("#sel").find("option").each(function(value){
//			alert($(this).attr("value"));
//			if($(this).attr("value")==-1)
//			$(this).attr("selected",true);
//		})
//				
//	}
	

</script>
</head>
<body>
<div id="messages" style="display: none">
    <!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
    <h4>模块管理</h4>
    <form action="addModel" method="post">
      <p>
	      <span><b>模块名称：</b><input class="text-input small-input" type="text" id="modName" name="modName" /></span>
	      <span><b>模块描述：</b><input class="text-input small-input" type="text" id="description" name="description"  /></span>
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
           <input  style="display: none" id="id"/> 
      </p>
      <fieldset>
      
      <div class="default-tab" id="main-content">
        </div>
      
      
      </fieldset>
      <div class="formBut">
      	<input class="button butCenter" type="submit" value="保 存" />
      	<input class="button butCenter butGray" type="button" value="取 消" />
      </div>
    </form>
  </div>
  <!-- End #messages -->
<div id="main-content">
<div class="content-box">
      <!-- Start Content Box -->
      <div class="content-box-header">
      	<div class="content-button">
      		<a class="button" href="#messages" rel="modal">新 增</a>
      		<a class="button" href="#"  onclick="hr()">审 核</a>
      		<a class="button refresh" href="#">刷 新</a>
      	</div>
      </div>
      <!-- End .content-box-header -->
      <div class="content-box-content">
      	
		<form action="getModleByCondition" method="post" >
			<div class="seachBox" id="main-content">
	          <select name="condtionname" class="seach-input">
                <option value="modName">模块名称</option>
                <option value="alias">模块标识</option>
              </select>
              <input class="text-input seach-input" type="text" id="small-input" name="condition" />
              <input class="button butCenter" type="submit" value="搜 索" />
             </div>
		</form>
        <div class="default-tab">
          <table>
            <thead>
              <tr>
                <th>
                  <input class="check-all" type="checkbox" />
                </th>
                <th>序列号</th>
                <th>模块名称</th>
                <th>模块描述</th>
                <th>操作</th>
              </tr>
            </thead>
            <tfoot >
              <tr>
                <td colspan="6">
                  <div class="bulk-actions align-left">
<!--                    <select name="dropdown">-->
<!--                      <option value="option1">Choose an action...</option>-->
<!--                      <option value="option2">Edit</option>-->
<!--                      <option value="option3">Delete</option>-->
<!--                    </select>-->
<!--                    <a class="button" href="#">Apply to selected</a> -->
					</div>
                  <div class="pagination"> <a href="#" title="首页">&laquo; 首页</a><a href="#" title="上一页">&laquo; 上一页</a> <a href="#" class="number" title="1">1</a> <a href="#" class="number" title="2">2</a> <a href="#" class="number current" title="3">3</a> <a href="#" class="number" title="4">4</a> <a href="#" title="下一页">下一页 &raquo;</a><a href="#" title="末页">末页 &raquo;</a> </div>
                  <!-- End .pagination -->
                  <div class="clear"></div>
                </td>
              </tr>
            </tfoot>
            <tbody id="tb">
            	<c:forEach items="${modelList}" var="model" varStatus="st">
            		<tr>
            			<td>
            				 <input type="checkbox" />
            			</td>
            			<td>${model.id}</td>
            			<td><a href="#" title="title">${model.modName}</a></td>
            			<td>${model.description}</td>
            			<td><!-- Icons -->
                  			<a href="getModelbyid?modelId=${model.id}"  rel="modal" title="Edit" ><img src="../images/icons/pencil.png" alt="Edit" /></a> 
                  			<a href="#" title="Delete" onclick="btn_dele(${model.id})"><img src="../images/icons/cross.png" alt="Delete" /></a> 
                  		</td>
            		</tr>
            	</c:forEach>
            </tbody>
          </table>
        </div>
        <!-- End #tab1 -->
      </div>
      <!-- End .content-box-content -->
    </div>
    </div>
 
</body>
<!-- Download From www.exet.tk-->
</html>
