<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower"%>
<%request.setCharacterEncoding("utf-8");%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--                       CSS                       -->
<link rel="stylesheet" type="text/css" href="../js/libs/easyui/easyui.css"/>
<link rel="stylesheet" type="text/css" href="../js/libs/easyui/icon.css"/>
<link rel="stylesheet" href="../css/reeasyui.css" type="text/css" media="screen" />
<!--                       Javascripts                       -->
<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="../js/libs/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/libs/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/ccsSearch.js"></script>
</head>
<body class="easyui-layout" data-options="fit:true">
    <div data-options="region:'east',title:'操作列表',split:true" style="width:320px; border: none;margin-top: -1px">
    	<table id="opsdg" class="easyui-datagrid" data-options="toolbar:'#toolbar',pagination:true,fitColumns:true,striped:true,toolbar:'#toolbar2',singleSelect:true,selectOnCheck:false,checkOnSelect:false,fit:true,pageSize:10,layout:['first','prev','next','last','manual']">   
		    <thead>   
		        <tr>
		            <th data-options="field:'opsName',width:50">操作名称</th>  
		            <th data-options="field:'opsUrl',width:50">操作URL</th>
		            <th data-options="field:'isStartStr'">是否验证USBKey</th>  
		            <th data-options="field:'true',align:'center',formatter:rowFormater1">操作</th>
		        </tr>   
		    </thead>   
		</table>
    </div>   
    <div data-options="region:'center',title:' '" style="border:none; background:#eee; margin-top: -1px;">
		<table id="dg" class="easyui-datagrid" data-options="url:'<%=basePath %>FuncController/findFuncByPage',toolbar:'#toolbar',pagination:true,fitColumns:true,striped:true,singleSelect:true,selectOnCheck:false,onClickRow:doOnClickRow,checkOnSelect:false,fit:true,pageSize:10,method:'post'">
		    <thead>
		        <tr>
		            <th data-options="field:'funcTypeStr'">功能类型</th>
		            <th data-options="field:'funcName'">功能名称</th>
		            <th data-options="field:'funcUrl',width:50">功能地址</th>
		            <th data-options="field:'description',width:50">功能描述</th>
		            <th data-options="field:'true',align:'center',formatter:rowFormater2">操作</th>
		        </tr>
		    </thead>
		</table>
		<div id="toolbar2" style="padding:5px;height:auto">
			<!-- 操作功能按钮 -->
			<div class="opsButBox">
				<b:button opsUrl="/OpsController/addOps">
		    		<a href="javascript:void(0)" class="easyui-linkbutton" id="addOps" iconCls="icon-add" plain="true" onclick="doAdd2(this)" data-options="disabled:true">添加</a>
		    	</b:button>
		    </div>
		</div>
		<div id="toolbar" style="padding:5px;height:auto">
		   <!-- 搜索 -->
				    <div class="searchFormBox">
				        <form action="" method="post" id="func" class="searchForm">
				        
				        </form>
				    </div>
			<!-- 其他操作按钮 -->
			<div class="listButBox">
				<b:button opsUrl="/FuncController/addFunc">
		    		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="doAdd(this)">添加</a>
		    	</b:button>
		    </div>
		</div>
    </div>
    <script type="text/javascript">
    	//格式化操作列
		function rowFormater2(val,row,index) {
    		//<b:button opsUrl="/FuncController/addDisFuncOps"><a class="listALink" href="javascript:javascript:void(0)" onclick="doFenpei(this, '+index+')" data-options="width:880,height:500,isIframe:true,winId:\'funcDialog\',buttons:\'none\'">分配</a></b:button> 
			return '&nbsp;<b:button opsUrl="/FuncController/updateFunc"> <a class="listALink" href="javascript:javascript:void(0)" onclick="doEdit(this, '+index+')">编辑</a></b:button> <b:button opsUrl="/FuncController/deleteFunc"> <a href="javascript:javascript:void(0)" onclick="doDelete(this, '+index+')">删除</a></b:button>&nbsp;';
		}
		
		function rowFormater1(val,row,index) {
			return '&nbsp;<b:button opsUrl="/OpsController/updateOps"><a class="listALink" href="javascript:javascript:void(0)" onclick="doEdit2(this, '+index+')">编辑</a></b:button>  <b:button opsUrl="/OpsController/deleteOps"><a href="javascript:javascript:void(0)" onclick="doDelete2(this, '+index+')">删除</a></b:button>&nbsp;';
		}
		
		var Clist = {
			dg : {
				idField : 'id',
				listUrl : '<%=basePath%>service/getFunc', //列表url
				addUrl : '<%=basePath %>FuncController/getFuncDetail',
				editUrl : '<%=basePath %>FuncController/getFuncDetailUpdate',
				deleteUrl : '<%=basePath %>FuncController/deleteFunc',
				dialogWidth : 450,
				dialogHeight : 270 //自适应
			},
			opsdg : {
				idField : 'id',
				listUrl : '<%=basePath%>service/getFunc', //列表url
				addUrl : '<%=basePath %>OpsController/findOpsById',
				editUrl : '<%=basePath %>OpsController/findOpsByIdUpdate',
				deleteUrl : '<%=basePath %>OpsController/deleteOps',
				dialogWidth : 450,
				dialogHeight : 270 //自适应
			}
		};
		function doFenpei(elem, index) {
			var _Clist = doBeforeDatagrid(0, elem);
			var rows = $('#'+_Clist.id).datagrid('getData');
			var row = rows.rows[index];
			
			//弹出层编辑的url
			var _url = '<%=basePath %>FuncController/disFuncOpsByFid?id='+row.id;
			
		
			var _options = {};
			_options = parseOptions(elem);
			
			if(_options.title == undefined || !_options.title) {
				_options.title = '分配';
			}
			
			_options.url = _url;
			if(!_options.width) {
				_options.width = (_Clist.dialogWidth?_Clist.dialogWidth:'auto');
			}
			if(!_options.height) {
				_options.height = (_Clist.dialogHeight?_Clist.dialogHeight:'auto');
			}
			_options._Clist = _Clist;
			_createWin(_options);
		}
		
		function doOnClickRow(rowIndex, rowData)
		{
			var dgUrl = "<%=basePath %>FuncController/findOpsByPage?funcId=" + rowData.id;
			$("#opsdg").datagrid({url:dgUrl,method:"get"});
			$('#addOps').linkbutton('enable');
		}
		function doAdd2(elem) {
			var row = $('#dg').datagrid('getSelected');
			Clist.opsdg.addUrl = '<%=basePath %>OpsController/findOpsById?funcId='+row.id;
			doAdd(elem);
		}
		function doDelete2(elem, index)
		{
			var row = $('#dg').datagrid('getSelected');
			Clist.opsdg.deleteUrl = '<%=basePath %>OpsController/deleteOps?funcId='+row.id;
			doDelete(elem, index, 'GET');
		}
		
		function doEdit2(elem, index)
		{
			var row = $('#dg').datagrid('getSelected');
			Clist.opsdg.editUrl = '<%=basePath %>OpsController/findOpsByIdUpdate?funcId='+row.id;
			doEdit(elem, index, 'GET');
		}
		
		$(function(){
		$(".searchForm").parsexh({
			xmlURL:"<%=basePath%>plugin/CCSSearch.xml",
			param:{"id":"func"},
			success:function(){
				$(".searchForm").append('<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch(\'func\',this)">搜索</a>');
				$(".searchForm").append('<a href="javascript:void(0)" class="easyui-linkbutton" onclick="doReset()">重置</a>');
				$.parser.parse(".searchForm");
			}
		});
	});
	</script>   
</body>
</html>
