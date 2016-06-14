<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--                       CSS                       -->
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
<script type="text/javascript">
	var Clist = {
		id : 'dg',
		idField : 'id', //如果多个参数可写成 id&firtname
		viewUrl : '<%=basePath%>TypeListController/view',//查看URl
		listUrl : '<%=basePath %>service/getTypeListSearch', //列表url
		addUrl : '<%=basePath%>TypeListController/addTypeList', //新增表单提交地址
		editUrl : '<%=basePath%>TypeListController/update', //编辑表单提交地址
		deleteUrl : '<%=basePath%>TypeListController/del', //删除提交的url
		dialogWidth : 600, //弹出层宽度
		dialogHeight : 250, //弹出层高度
	};
	function rowFormater2(val,row,index) {
		//return '&nbsp;<a class="listALink" href="javascript:void(0)" onclick="doEdit(this, '+index+')">编辑</a> | <a href="javascript:void(0)" onclick="doDelete(this, '+index+')">删除</a> &nbsp;';
		return '&nbsp;<b:button opsUrl="/TypeListController/update"><a class="listALink" href="javascript:void(0)" onclick="doEdit(this, '+index+')">编辑</a></b:button> &nbsp;';
	}
	$(function(){
		$(".searchForm").parsexh({
			xmlURL:"<%=basePath%>plugin/CCSSearch.xml",
			param:{"id":"typeList"},
			success:function(){
				$(".searchForm").append('<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch(\'typeList\',this)">搜索</a>');
				$(".searchForm").append('<a href="javascript:void(0)" class="easyui-linkbutton" onclick="doReset()">重置</a>');
				$.parser.parse(".searchForm");
			}
		});
	});
</script> 
</head>
<body class="easyui-layout" data-options="fit:true">
<!--    左侧-->
<!--    <div data-options="region:'north'" style="  height:50px;">-->
<!--       	查询界面-->
<!--    </div> -->

    <!--中间1层Div 自由扩充-->
    <div data-options="region :'center',border:false">
        <div class="easyui-layout" data-options="fit:true">
            <!--左侧-->
<!--            <div data-options="region:'west',title:'组织机构',split:true"" style="width:200px;">-->
<!--                <ul id="tt" class="easyui-tree" data-options="url:'<%=basePath %>OrgController/getOrgTreeList',method:'get',animate:true,checkbox:false"></ul>-->
<!--            </div>-->
            <div data-options="region:'center',title:'通用标准列表'">
                <div id="toolbar" style="padding:5px;height:auto">
                <!-- 搜索 -->
				    <div class="searchFormBox">
				    <b:button opsUrl="/service/getTypeListSearch">
				        <form action="" method="post" id="typeList" class="searchForm">
				        </form>
				        </b:button>
				    </div>
	                <div class="listButBox">
		                <b:button opsUrl="/TypeListController/addTypeList"><a href="javascript:void(0)" class="easyui-linkbutton addbtn" iconcls="icon-add" plain="true" onclick="doAdd(this)" >新增</a></b:button>
		                <b:button opsUrl="/TypeListController/del"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="doDeleteChecked(this)">删除选中</a></b:button>
	            	</div>
            	</div>
				 <table id="dg" class="easyui-datagrid" data-options="url:'<%=basePath %>TypeListController/getTypeList',toolbar:'#toolbar',method:'get',border:false,onDblClickRow:doDatagridOnDblClickRow">
				  <thead>
	                <tr>
	                    <th data-options="checkbox:true"></th>
	                    <th data-options="field:'kind',sortable:true,width:15">标准类型编码</th>
	                    <th data-options="field:'typename',width:30">标准类型</th>
	                    <th data-options="field:'type',width:30">标准类型值</th>
	                    <th data-options="field:'fullname',width:40">标准类型值全称</th>
	                    <th data-options="field:'porder',align:'center'">排序编号</th>
	                    <th data-options="field:'ident',align:'center'">系统标志</th>
	                     <th data-options="field:'true',align:'center',formatter:rowFormater2">操作</th>
	                </tr>
          		  </thead>
          		  </table>
                </div>
</body>
</html> 
