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
<div id="toolbar">
     <!-- 搜索 -->
				    <%-- <div class="searchFormBox">
				    <b:button opsUrl="/service/getModel">
				        <form action="" method="post" id="model" class="searchForm">
				        
				        </form>
				    </b:button>
				    </div> --%>
	<!-- 其他操作按钮 -->
	<div class="listButBox">
		<b:button opsUrl="/SysParamController/create">
    		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="doAdd(this)">添加参数</a>
    	</b:button>
    </div>
</div>
<table id="dg" class="easyui-datagrid" data-options="url:'<%=basePath %>SysParamController/getSysParamList',pageSize:10,toolbar:'#toolbar',method:'post'">
    <thead>
			<tr>
				<th data-options="field:'id',sortable:true,width:5">流水号</th>
				<th data-options="field:'paramname',width:5">参数名称</th>
				<th data-options="field:'paramtype',width:5">参数类型</th>
				<th data-options="field:'paramvalue',width:30">参数值</th>
				<th data-options="field:'sysname',width:5">系统名称</th>
				<th data-options="field:'sysinsttime',width:9">添加时间</th>
				<th data-options="field:'sysedittime',width:9">修改时间</th>
				<th data-options="field:'true',align:'center',formatter:rowFormater">操作</th>
			</tr>
		</thead>
</table>
<script type="text/javascript">
	var Clist = {
		id : 'dg',
		idField : 'id',
		listUrl : '<%=basePath%>SysParamController/getSysParamList', //列表url
		addUrl : '<%=basePath %>SysParamController/create',
		editUrl : '<%=basePath %>SysParamController/findSysParamById',
		deleteUrl : '<%=basePath %>SysParamController/deleteSysParam',
		dialogWidth : 350,
		dialogHeight : 292 //自适应
	};
	
	function rowFormater(val,row,index) {
		return '&nbsp;<b:button opsUrl="/ModelController/getModelUpdateDetail"><a class="listALink" href="javascript:javascript:void(0)" onclick="doEdit(this, '+index+')">编辑</a></b:button>'+
		'&nbsp;<b:button opsUrl="/ModelController/delModel"><a href="javascript:javascript:void(0)" onclick="doDelete(this, '+index+')">删除</a></b:button>&nbsp;';
	}
</script>
</body>
</html>
