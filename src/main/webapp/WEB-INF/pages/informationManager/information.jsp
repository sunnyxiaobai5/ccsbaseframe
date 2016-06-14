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
	var basePath = "<%= basePath%>";
	var Clist = {
		id : 'infodg',
		idField : 'id', //如果多个参数可写成 id&firtname
		viewUrl : '<%=basePath%>InformationController/view', //双击查看地址
		listUrl : '<%=basePath%>service/getInformation', //列表url
		addUrl : '<%=basePath%>InformationController/create', //新增表单提交地址
		editUrl : '<%=basePath%>InformationController/update', //编辑表单提交地址
		deleteUrl : '<%=basePath%>InformationController/del', //删除提交的url
		dialogWidth : 1100, //弹出层宽度
		dialogHeight : 580, //弹出层高度
	};
	
	function doOnClickRow(rowIndex, rowData){
		var a = "<%=basePath %>InformationController/getSendAppList?infoid="+rowData.id;
		$("#sendappdg").datagrid({url:a});
	}
	
	$(function(){
		$(".searchForm").parsexh({
			xmlURL:"<%=basePath%>plugin/CCSSearch.xml",
			param:{"id":"information"},
			success:function(){
				$(".searchForm").append('<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch(\'information\',this)">搜索</a>');
				$(".searchForm").append('<a href="javascript:void(0)" class="easyui-linkbutton" onclick="doReset()">重置</a>');
				$.parser.parse(".searchForm");
			}
		});
	});
	
	function rowFormater1(val,row,index) {
			return '&nbsp;<b:button opsUrl="/InformationController/update"><a class="listALink" href="javascript:javascript:void(0)" onclick="doEdit(this, '+index+')">编辑</a></b:button> <b:button opsUrl="/InformationController/del">| <a href="javascript:javascript:void(0)" onclick="doDelete(this, '+index+')">删除</a></b:button>&nbsp;';
		}
	
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
    		<div data-options="region:'center',title:'公告列表',split:true" style="border: none">
	     		<div id="toolbar" style="height:auto">
	     			<!-- 搜索 -->
				    <div class="searchFormBox">
				    <b:button opsUrl="/service/getInformation">
				        <form action="" method="post" id="information" class="searchForm">
				        </form>
				        </b:button>
				    </div>
	          		 <div class="listButBox">
	          			 <b:button opsUrl="/InformationController/create">
	             		 	<a href="javascript:void(0)" class="easyui-linkbutton addbtn" iconcls="icon-add" plain="true" onclick="doAdd(this)" >新增公告</a>
	             		 </b:button>
	           		 </div>
	    		</div>
            	<table id="infodg" class="easyui-datagrid" data-options="url:'<%=basePath %>InformationController/getInformationsList',toolbar:'#toolbar',method:'get',onClickRow:doOnClickRow,onDblClickRow:doDatagridOnDblClickRow">
	  	   		 <thead>
             		 <tr>
	                  <th data-options="field:'infotype',sortable:true">公告类型</th>
	                  <th data-options="field:'infotitle',width:30">公告标题</th>
	                  <th data-options="field:'createruser'">发布人</th>
	                  <th data-options="field:'sysinsttime'">发布时间</th>
	                  <th data-options="field:'filecounts'">附件</th>
	                  <th data-options="field:'sendstatus',align:'center'">状态</th>
	                  <th data-options="field:'true',align:'center',formatter:rowFormater1">操作</th>
             		 </tr>
          		  </thead>
      	  		 </table>
   	   		</div>
	   		<div data-options="region:'east',title:'已发送系统',split:true" style="width:280px;">
	    	<table id="sendappdg" class="easyui-datagrid" data-options="method:'get',pagination:false,border:false">
			  <thead>
                <tr>
                    <th data-options="field:'sysname',sortable:true,width:20">系统名称</th>
                    <th data-options="field:'createruser'">发送人</th>
                    <th data-options="field:'sysinsttime'">发送时间</th>
                </tr>
       		  </thead>
      		</table>
	   		</div> 
</body>
</html> 
</html> 
