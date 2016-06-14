<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    <!--                       CSS                       -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/libs/easyui/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/libs/easyui/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reeasyui.css"/>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css" type="text/css">
</head>
<body>

<div id="toolbar" style="padding:5px;height:auto">

    <div class="listButBox">
            <a href="javascript:void(0)" class="easyui-linkbutton addbtn" iconcls="icon-add" plain="true"
               onclick="doAdd(this)">发送</a>
    </div>
</div>
<table class="easyui-datagrid" id="dg" data-options="
				url:'${pageContext.request.contextPath}/mail/list',
			    singleSelect:true,
			    toolbar: '#toolbar',
			    border:false">
    <thead>
    <tr>
        <th data-options="field:'title',width:20">标题</th>
        <th data-options="field:'userName',width:20">发送人</th>
        <th data-options="field:'sender',width:20">发送人账号</th>
        <th data-options="field:'isRead',width:20,formatter:mailStatusFormatter">状态</th>
        <th data-options="field:'sendTime',width:20">时间</th>
        <th data-options="field:'url',align:'center',formatter:optFormatter">操作</th>
    </tr>
    </thead>
</table>

<!--                       Javascripts                       -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/libs/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.exhide-3.5.min.js"></script>
<script type="text/javascript">
    var Clist = {
        dg: {
            idField: 'id',
            listUrl: '${pageContext.request.contextPath}/mail/list', //列表url
            addUrl: '${pageContext.request.contextPath}/mail/add',
            deleteUrl: '${pageContext.request.contextPath}/mail/delete',
            dialogWidth: 650,
            dialogHeight: 500 //自适应
        }
    };
    function mailStatusFormatter(val, row, index) {
        return val == "0" ? "已读" : "未读";
    }

    //格式化操作列
    function optFormatter(val, row, index) {

        return '&nbsp;<a class="listALink" href="#" data-options="title:\'查看消息\',url:\'${pageContext.request.contextPath}/mail/view\',width:650,height:500,buttons:\'none\',onClose:closeViewMail" onclick="doEdit(this, ' + index + ')">查看</a> | ' +
                '<a class="listALink" href="#"  data-options="title:\'回复消息\',url:\'${pageContext.request.contextPath}/mail/reply\',width:650,height:500,onClose:closeViewMail" onclick="doEdit(this, ' + index + ')">回复</a> | ' +
                '<a href="#" onclick="doDelete(this, ' + index + ')">删除</a>';
    }


    /**
     * 关闭查看窗口
     * @param grid
     */
    function closeViewMail() {
        $('#dg').datagrid('reload');
    }
</script>
</body>
</html>