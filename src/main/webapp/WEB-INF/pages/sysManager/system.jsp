<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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
	<script type="text/javascript" src="../js/libs/easyui/datagrid-detailview.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/ccsSearch.js"></script>
	<script type="text/javascript" src="../js/json2.js"></script>
</head>
<body>
<div id="toolbar" style="padding:5px; height:auto;">
    <!-- 搜索 -->
				    <div class="searchFormBox">
				     <b:button opsUrl="/service/getSystem">
				        <form action="" method="post" id="system" class="searchForm">
				        
				        </form>
				     </b:button>
				    </div>
	<!-- 其他操作按钮 -->
	<div class="listButBox">
    	<b:button opsUrl="/SysController/getLogiSystemDetail">
    		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="doAdd(this)">添加</a>
    	</b:button>	
    </div>
</div>
<table id="dg" class="easyui-datagrid" data-options="url:'<%=basePath %>SysController/findLogiSystemByPage',toolbar:'#toolbar',fitColumns:true,view:detailview,detailFormatter:doDgDetailFormatter,onExpandRow:doDgOnExpandRow">
    <thead>
        <tr>
            <th data-options="field:'id',sortable:true">序列号</th>
            <th data-options="field:'sysName',width:80">系统名称</th>
            <th data-options="field:'description',width:120">系统描述</th>
            <th data-options="field:'briefName',width:100">系统简称</th>
            <th data-options="field:'regionCode',width:50">系统区域</th>
            <th data-options="field:'true',align:'center',formatter:systemRowFormater">操作</th>
        </tr>
    </thead>
</table>
<script type="text/javascript">
	var Clist = {
		id : 'dg',
		idField : 'id',
		listUrl : '<%=basePath%>service/getSystem', //列表url
		addUrl : '<%=basePath %>SysController/getLogiSystemDetail',
		editUrl : '<%=basePath %>SysController/getLogiSystemUpdate',
		deleteUrl : '<%=basePath %>SysController/delSystem',
		dialogWidth : 880,
		dialogHeight : 530 //自适应
	};

	function doDgDetailFormatter(index,row) {
		return '<div style="padding:2px"><table class="ddv" id="ddvgrid'+ index +'"></table></div>';
	}
	
	function doDgOnExpandRow(index,row) {
		var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
        var _data = [];
        $.ajax({
         	url : '<%=basePath %>SysController/getModelsBySysId?id='+row.id,
         	dataType : 'json',
         	async : false,
         	success : function(data) {
         		_data = data;
         		//ddv.data('isload', true);
         	}
        });
        ddv.datagrid({
            //url:'<%=basePath %>SysController/getModelsBySysId?id='+row.id,
            //data:[row],
            data : _data,
            fitColumns:false,
            singleSelect:true,
            rownumbers:true,
            loadMsg:'',
            pagination:false,
            height:'auto',
            columns:[[
                {field:'tid',title:'序列号',width:60},
                {field:'text',title:'模块名', width:450},
                {field:'true',title:'操作',width:120,align:'center',formatter:modelFormater}
            ]],
            onResize:function(){
                $('#dg').datagrid('fixDetailRowHeight',index);
            },
            onLoadSuccess:function(){
                setTimeout(function(){
                    $('#dg').datagrid('fixDetailRowHeight',index);
                },50);
            }
        });
        $('#dg').datagrid('fixDetailRowHeight',index);
	}
	
	$(function(){
		$(".searchForm").parsexh({
			xmlURL:"<%=basePath%>plugin/CCSSearch.xml",
			param:{"id":"system"},
			success:function(){
				$(".searchForm").append('<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch(\'system\',this)">搜索</a>');
				$(".searchForm").append('<a href="javascript:void(0)" class="easyui-linkbutton" onclick="doReset()">重置</a>');
				$.parser.parse(".searchForm");
			}
		});
	});
	
	function systemRowFormater(val,row,index) {
		return '&nbsp;<b:button opsUrl="/SysController/getLogiSystemUpdate"><a class="listALink" href="javascript:javascript:void(0)" onclick="doEdit(this, '+index+')">编辑</a> | </b:button>'+
		'&nbsp;<a class="listALink" href="javascript:void(0)" data-options="title:\'分配模块\',url:\'${pageContext.request.contextPath}/SysController/getModelsForUpdate\',width:800,height:500,buttons:[{text:\'保存\',handler:function(){assignModels('+ index +')}}]" onclick="sysId='+row.id+',doEdit(this, '+index+')">分配模块</a> | '+
		'&nbsp;<a class="listALink" href="javascript:void(0)" data-options="title:\'预览\',width:800,height:500" onclick="overview('+index+')">预览</a> | '+
		'&nbsp;<b:button opsUrl="/SysController/delSystem"><a href="javascript:javascript:void(0)" onclick="doDelete(this, '+index+')">删除</a></b:button>&nbsp;';
	}
	
	//预览
	function overview(index){
		var rows = $("#dg").datagrid("getRows");
		var datas = rows[index];
		openDialog('title:\'系统预览\',url:"<%=basePath %>SysController/seeSys?id='+datas.id+'",width:440,height:530,buttons:false');
	}
	
	function modelFormater(val,row,index){
		return '&nbsp;<a class="listALink" href="javascript:void(0)" onclick="doFunc(this, '+ row.tid +', '+index+')">分配功能</a> | '+
		'&nbsp;<a href="javascript:javascript:void(0)" onclick="delMod(this, '+ row.tid +', '+index+')">删除</a>&nbsp;';
	}
	
	//删除模块
	function delMod(elem, tid, index){
		var _pid = $(elem).parents().eq(13).prev('tr').find('td[field="id"]').text();
		if(!_pid) return false;

		$.getJSON('${pageContext.request.contextPath}/SysController/delSysAndMod', {modId:tid,sysId:_pid}, function(result) {
			doFormSubmit(result);
			if(result.status == 'success') {
				var ddv = $(elem).parents().eq(6).siblings('table.ddv');
				var data = ddv.datagrid('getData');
				var new_data = [];
				for(var i in data.rows) {
					if(data.rows[i].tid != tid) {
						new_data.push(data.rows[i]);
					}
				}
				ddv.datagrid('loadData', new_data);
			}
		})
	}
	
	function doFunc(elem, tid, index){
		var _pid = $(elem).parents().eq(13).prev('tr').find('td[field="id"]').text();
		if(!_pid) return false;
		openDialog("title:\'分配功能\',url:\'${pageContext.request.contextPath}/SysController/getFuncsForUpdate?modId="+ tid +"&sysId="+_pid+"\',width:800,height:500,buttons:[{text:\'保存\',handler:function(){assignFuncs()}}]");
	}
	
	function assignModels(index){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getNodes();
		var list = new Array;
		if(nodes.length>0){
			for(var i in nodes){
				var obj = new Object;
				obj.tid = nodes[i].tid;
				
				list.push(obj);
			}
		}
		var mods = JSON.stringify(list);
		$.post("<%=basePath %>SysController/saveAssignModels", { 'mods':mods,'id':$('#id').val() }, function(result) {
			result = parseReturn(result);
			if (result.status == 'success'){
				Cmessager.show({
					title:GV.MESSAGER_TITLE,
					msg:result.message,
					timeout:1500
				});
				
				var _data = [];
		        $.ajax({
		         	url : '<%=basePath %>SysController/getModelsBySysId?id='+sysId,
		         	dataType : 'json',
		         	async : false,
		         	success : function(data) {
		         		_data = data;
		         		//ddv.data('isload', true);
		         	}
		        });
		        $("#ddvgrid"+index).datagrid('loadData', _data);
				
			} else {
				Cmessager.alert(GV.MESSAGER_TITLE, result.message, 'error');
			}
		});
	}
	
	function assignFuncs(){
		var treeObj = $.fn.zTree.getZTreeObj("funcsTree");
		var nodes = treeObj.getNodes();
		var list = new Array;
		if(nodes.length>0){
			for(var i in nodes){
				var obj = new Object;
				obj.tid = nodes[i].tid;
				
				list.push(obj);
			}
		}
		var funcs = JSON.stringify(list);
		$.post("<%=basePath %>SysController/saveAssignFuncs", { 'funcs':funcs,'modId':$('#modId').val(),'sysId':$('#sysId').val() }, function(result) {
			result = parseReturn(result);
			if (result.status == 'success'){
				Cmessager.show({
					title:GV.MESSAGER_TITLE,
					msg:result.message,
					timeout:1500
				});
			} else {
				Cmessager.alert(GV.MESSAGER_TITLE, result.message, 'error');
			}
		});
	}
	
</script>
</body>
</html>
