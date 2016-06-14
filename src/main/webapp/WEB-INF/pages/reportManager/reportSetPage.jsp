<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>报表管理</title>
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
                <b>系统标识：</b>
                <select id="rabb" class="easyui-combobox" name="rabb" data-options="required:true,editable:false">
                    <option value="">全部</option>
                    <c:forEach var="sys" items="${systems}">
                        <option value="${sys.id}">${sys.sysName}</option>
                    </c:forEach>
                </select>
            </span>
            <span class='nameBas'>
                <b>报表名称：</b>
                <input name="rname" class="easyui-validatebox" type="text"/>
            </span>

        </form>
        <a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search"
           onclick="searchReport()">搜索</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="doReset()">重置</a>
    </div>
    <div class="listButBox">
        <b:button opsUrl="/report/add">
            <a href="javascript:void(0)" class="easyui-linkbutton addbtn" iconcls="icon-add" plain="true"
               onclick="doAdd(this)">添加</a>
        </b:button>
    </div>
</div>
<table id="reportgrid" class="easyui-datagrid" data-options="
				url:'${pageContext.request.contextPath}/report/reportSet/list',
			    singleSelect:true,
			    toolbar: '#toolbar',
			    border:false">
    <thead>
    <tr>
        <th data-options="field:'rname',width:82">报表名称</th>
        <th data-options="field:'rtype',width:82">报表类型</th>
        <th data-options="field:'sysName',width:62">所属系统</th>
        <th data-options="field:'rfile',width:122">报表文件</th>
        <th data-options="field:'rfileurl',width:152">报表URL</th>
        <!--
        <th data-options="field:'rparam',width:62">打印参数</th>
        <th data-options="field:'rposition',width:62">打印方向</th>
        <th data-options="field:'isbatch',width:82">能否批量打印</th><th data-options="field:'regionCode',width:62">区域编码</th>-->

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
<script type="text/javascript">
    var Clist = {
        reportgrid: {
            idField: 'id',
            listUrl: '${pageContext.request.contextPath}/report/reportSet/list', //列表url
            editUrl: '${pageContext.request.contextPath}/report/reportSet/update',
            addUrl: '${pageContext.request.contextPath}/report/reportSet/add',
            deleteUrl: '${pageContext.request.contextPath}/report/reportSet/delete',
            dialogWidth: 550,
            dialogHeight: 400 //自适应
        },
        paraGrid: {
            idField: 'id&reportId',
            listUrl: '${pageContext.request.contextPath}/report/reportSet/para/list', //列表url
            editUrl: '${pageContext.request.contextPath}/report/reportSet/para/update',
            addUrl: '${pageContext.request.contextPath}/report/reportSet/para/add',
            deleteUrl: '${pageContext.request.contextPath}/report/reportSet/para/delete',
            dialogWidth: 550,
            dialogHeight: 250 //自适应
        }
    };

    function searchReport() {
        var form = $('.searchForm');
        var data = $.serializeObject(form);
        $('#reportgrid').datagrid('load', data);
    }

    //格式化操作列
    function optFormater(val, row, index) {

        return '&nbsp;<a class="listALink" href="javascript:void(0)" onclick="doEdit(this, ' + index + ')">编辑</a> | ' +
                '<a href="javascript:void(0)" onclick="doDelete(this, ' + index + ')">删除</a> | ' +
                '<a class="listALink" href="javascript:void(0)" data-options="title:\'参数管理\',url:\'${pageContext.request.contextPath}/report/reportSet/para\',width:800,height:500,buttons:\'none\'" onclick="doEdit(this, '+index+')">参数</a> | '+
                '<a class="listALink" href="javascript:void(0)"  onclick="previewReport('+index+')">预览</a> ';
    }

    function previewReport(index){
        var rows = $('#reportgrid').datagrid('getData');
        var row = rows.rows[index];
        window.open(row.rfileurl);

        //printByNoPreview(3,row.rfileurl+"&ID=165&printCode=BTZHGM201409150637");
    }

</script>
</body>
</html>