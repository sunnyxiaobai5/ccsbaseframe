<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<title>index</title>
	<link rel="stylesheet" type="text/css" href="../js/libs/easyui/easyui.css">
	<link rel="stylesheet" type="text/css" href="../js/libs/easyui/icon.css">
	<link rel="stylesheet" href="../css/reeasyui.css" type="text/css" media="screen" />
	<!--                       Javascripts                       -->
	<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="../js/libs/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/libs/easyui/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/ccsSearch.js"></script>
  </head>
 <body>
<div id="toolbar" style="padding:5px;height:auto">
     <!-- 搜索 -->
				    <div class="searchFormBox">
				    <b:button opsUrl="/service/getModel">
				        <form action="" method="post" id="model" class="searchForm">
				        
				        </form>
				    </b:button>
				    </div>
	<!-- 其他操作按钮 -->
	<div class="listButBox">
		<b:button opsUrl="/ModelController/getModelDetail">
    		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="doAdd(this)">添加</a>
    	</b:button>
    </div>
</div>
<table id="dg" class="easyui-datagrid" data-options="url:'<%=basePath %>ModelController/getModleByCondition',pageSize:10,toolbar:'#toolbar',method:'post'">
    <thead>
        <tr>
            <th data-options="field:'id',sortable:true">序列号</th>
            <th data-options="field:'modName'">模块名称</th>
            <th data-options="field:'description',width:120">模块描述</th>
            <th data-options="field:'true',align:'center',formatter:modelRowFormater">操作</th>
        </tr>
    </thead>
</table>
<script type="text/javascript">
	var Clist = {
		id : 'dg',
		idField : 'id',
		listUrl : '<%=basePath%>service/getModel', //列表url
		addUrl : '<%=basePath %>ModelController/getModelDetail',
		editUrl : '<%=basePath %>ModelController/getModelUpdateDetail',
		deleteUrl : '<%=basePath %>ModelController/delModel',
		dialogWidth : 350,
		dialogHeight : 270 //自适应
	};
	
	$(function(){
		$(".searchForm").parsexh({
			xmlURL:"<%=basePath%>plugin/CCSSearch.xml",
			param:{"id":"model"},
			success:function(){
				$(".searchForm").append('<input type="text" style="display: none;"/>');
				$(".searchForm").append('<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch(\'model\',this)">搜索</a>');
				$(".searchForm").append('<a href="javascript:void(0)" class="easyui-linkbutton" onclick="doReset()">重置</a>');
				$.parser.parse(".searchForm");
			}
		});
	});
	
	function modelRowFormater(val,row,index) {
		return '&nbsp;<b:button opsUrl="/ModelController/getModelUpdateDetail"><a class="listALink" href="javascript:javascript:void(0)" onclick="doEdit(this, '+index+')">编辑</a></b:button>'+
		'&nbsp;<b:button opsUrl="/ModelController/delModel"><a href="javascript:javascript:void(0)" onclick="doDelete(this, '+index+')">删除</a></b:button>&nbsp;';
	}
</script>
</body>
</html>
