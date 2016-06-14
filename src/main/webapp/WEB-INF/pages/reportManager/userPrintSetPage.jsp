<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户报表设置</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/libs/easyui/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/libs/easyui/icon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reeasyui.css" type="text/css" media="screen"/>
</head>
<body>
<div id="toolbar" style="padding:5px;height:auto">
    <div class="searchFormBox">
        <form action="" method="post" id="reportForm" class="searchForm">
            <span class='nameBas'>
                <b>报表类型：</b>
                <input name="rtype" class="easyui-validatebox" type="text"/>
            </span>
            <span class='nameBas'>
                <b>报表名称：</b>
                <input name="rname" class="easyui-validatebox" type="text"/>
            </span>

        </form>
        <a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="searchReport()">搜索</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="doReset()">重置</a>
    </div>
    <div class="listButBox">
        <b:button opsUrl="/report/add">
            <a href="javascript:void(0)" class="easyui-linkbutton addbtn" iconcls="icon-reload" plain="true" onclick="synReport()">同步</a>
        </b:button>
    </div>
</div>
<table id="reportgrid" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/report/userPrintSet/list',
			    singleSelect:true,
			    toolbar: '#toolbar',
			    border:false">
    <thead>
    <tr>
        <th data-options="field:'RTYPE',width:82">报表类型</th>
        <th data-options="field:'RNAME',width:72">报表名称</th>
        <th data-options="field:'ISPREVIEW',width:122,formatter:
                                function(val,row,index){
                                    if(val=='1'){return '是';}
                                    if(val=='0'){return '否';}
                                }">是否预览</th>
        <th data-options="field:'PRINTER',width:122,formatter:
                                function(val,row,index){
                                    return getPrinterName(val);
                                }">打印机</th>
        <th data-options="field:'SYSNAME',width:62">所属系统</th>
        <th data-options="field:'_operate',width:150,formatter:optFormater">操作</th>
    </tr>
    </thead>
</table>
<!--                       Javascripts                       -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/print/LodopFuncs.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
    <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
<script type="text/javascript">
    var Clist = {
        id: 'reportgrid',
        idField: 'ID',
        listUrl: '${pageContext.request.contextPath}/report/userPrintSet/list', //列表url
        editUrl: '${pageContext.request.contextPath}/report/userPrintSet/update',
        addUrl: '${pageContext.request.contextPath}/report/userPrintSet/add',
        dialogWidth: 550,
        dialogHeight: 300 //自适应

    };

    function searchReport(){
        var form = $('.searchForm');
        var data = $.serializeObject(form);
        $('#reportgrid').datagrid('load',data);
    }



    function synReport(){
        $.post('${pageContext.request.contextPath}/report/userPrintSet/synReport',function(){
            $('#reportgrid').datagrid('reload');
        });
    }

    //格式化操作列
    function optFormater(val,row,index) {
        return '&nbsp;<a class="listALink" href="javascript:javascript:void(0)" onclick="doEdit(this, '+index+')">编辑</a>&nbsp;';
    }
</script>
</body>
</html>