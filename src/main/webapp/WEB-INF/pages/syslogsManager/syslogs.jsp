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
		id : 'logsdg',
		idField : 'id', //如果多个参数可写成 id&firtname
		listUrl : '<%=basePath%>service/getsyslogs', //列表url
		viewUrl : '<%=basePath%>SysLogsController/view', //双击查看地址
		addUrl : '', //新增表单提交地址
		editUrl : '', //编辑表单提交地址
		deleteUrl : '', //删除提交的url
		dialogWidth : 500, //弹出层宽度
		dialogHeight : 450, //弹出层高度
	};
	
	$(function(){
		$(".searchForm").parsexh({
			xmlURL:"<%=basePath%>plugin/CCSSearch.xml",
			param:{"id":"syslogs"},
			success:function(){
				$(".searchForm").append('<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch(\'syslogs\',this)">搜索</a>');
				$(".searchForm").append('<a href="javascript:void(0)" class="easyui-linkbutton" onclick="doReset()">重置</a>');
				$.parser.parse(".searchForm");
			}
		});
	});
</script>
<body>
            <div id="toolbar" style="padding:5px;height:auto">
	     			<!-- 搜索 -->
				    <div class="searchFormBox">
				        <form action="" method="post" id="syslogs" class="searchForm">
				        </form>
				    </div>
			</div>
			 <table id="logsdg" class="easyui-datagrid"  data-options="url:'<%=basePath %>SysLogsController/getLogsList',method:'get',border:false,onDblClickRow:doDatagridOnDblClickRow,pageSize:20,toolbar:'#toolbar'">
			  <thead>
                <tr>
                    <th data-options="field:'id',sortable:true">日志流水号</th>
                    <th data-options="field:'sysname'">操作系统</th>
                    <th data-options="field:'opemod'">操作类</th>
                    <th data-options="field:'opefunc',width:2">操作功能</th>
<!--                    <th data-options="field:'opeparam',width:15">操作参数</th>-->
                    <th data-options="field:'flag'">操作标识</th>
                    <th data-options="field:'orgname'">操作机构</th>
                    <th data-options="field:'loginid'">操作人</th>
                    <th data-options="field:'opedate'">操作时间</th>
                    <th data-options="field:'regioncode'">操作区域</th>
                    <th data-options="field:'ip',width:2">操作IP</th>
                </tr>
         		  </thead>
	         </table>
</body>
</html> 
