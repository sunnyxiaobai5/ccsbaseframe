<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<link rel="stylesheet" href="../css/demo.css" type="text/css">
<link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="../js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="../js/jquery.ztree.exhide-3.5.min.js"></script>

<script type="text/javascript">
    var Clist = {
        id : 'dg',
        idField : 'id',
        listUrl : '<%=basePath %>UserManagerController/getUserList', //列表url
        viewUrl : '<%=basePath%>UserManagerController/show',
        addUrl : '<%=basePath%>UserManagerController/create?orgid=', //新增表单提交地址
        changeUrl : '<%=basePath%>UserManagerController/change?userid=', //新增表单提交地址
        editUrl : '<%=basePath%>UserManagerController/update', //编辑表单提交地址
        deleteUrl : '<%=basePath%>UserManagerController/del', //删除提交的url
        dialogWidth : 950, //弹出层宽度
        dialogHeight : 570 //弹出层高度
    };

    var orgName= "";

    function rowFormater2(val,row,index) {
        return '&nbsp;<b:button opsUrl="/UserManagerController/updateUserManager"><a class="listALink" href="javascript:void(0)" onclick="doEdit(this,'+index+')">编辑</a></b:button>'
                +'<b:button opsUrl="/UserManagerController/del">| <a href="javascript:void(0)" onclick="doDelete(this,'+index+')">删除</a></b:button>'
                +'<b:button opsUrl="/UserManagerController/del">| <a href="javascript:void(0)" onclick="disRole('+index+')">分配角色</a></b:button>'
                +'<b:button opsUrl="/UserManagerController/del">| <a href="javascript:void(0)" onclick="seeOrg('+index+')">查看组织机构信息</a></b:button>'
                +'<b:button opsUrl="/UserManagerController/del">| <a href="javascript:void(0)" onclick="changeOrg('+index+')">变更所在组织机构</a></b:button>&nbsp;';
    }

    //分配角色
    function disRole(index){
        var rows = $("#dg").datagrid("getRows");
        var datas = rows[index];
        console.log(datas);
        openDialog('title:\'分配角色\',url:"<%=basePath %>UserManagerController/goDisRole?id='+datas.id+'",width:950,height:530');
    }

    //查看组织机构信息
    function seeOrg(index){
        var rows = $("#dg").datagrid("getRows");
        var datas = rows[index];
        openDialog('title:\'查看组织机构信息\',url:"<%=basePath %>UserManagerController/goOrg?id='+datas.id+'",width:950,height:530,buttons:false');
    }

    //变更所在组织机构
    function changeOrg(index){
        var rows = $("#dg").datagrid("getRows");
        var datas = rows[index];
        openDialog('title:\'变更所在组织机构\',url:"<%=basePath%>UserManagerController/change?userid='+datas.id+'",width:950,height:530');

    }

    /** jquery-ztree封装树**/
    $.fn.extend({
        initZtree:function(conf){
            var ztreeObj = false ;
            var setting = {
                async:{
                    enable:true,
                    url: conf.url,
                    type:'post',
                    dataType:'json'
                },
                view:{
                    showIcon:true,
                    showLine:true,
                    expandSpeed:'normal',
                    txtSelectedEnable:true
                },
                data:{
                    simpleData:{
                        enable:true,
                        idKey: conf.id || 'id',
                        pIdKey: conf.parentId || 'pid',
                        rootPId: null
                    },
                    key:{
                        url:'xurl', //屏蔽URL
                        name: conf.name || 'text'
                    }
                },
                check:{
                    enable: true,
                    chkStyle:"checkbox",
                    chkboxType: { "Y": "p", "N": "ps" }
                },
                callback:{
                    onClick:conf.onClick,
                    beforeAsync:zTreeBeforeAsync,
                    onAsyncSuccess:zTreeOnAsyncSuccess
                }
            };
            ztreeObj = $.fn.zTree.init($(this), setting);
            return ztreeObj ;
        }
    });

    function zTreeOnAsyncSuccess(treeId, treeNode){
        $(".treeLoadingBox").remove();
    }

    function zTreeBeforeAsync(treeId, treeNode) {
        setTimeout('treeBoxSize()', 0);
    }

    function treeBoxSize() {
        var treeBoxH = $("#treeBoxMan").height();
        var treeBoxW = $("#treeBoxMan").width();
        $(".treeLoadingBox").css({"height":treeBoxH,"width":treeBoxW,"position":"absolute","z-index":"999","background":"url('${pageContext.request.contextPath}/images/loadingBg.png')","top":"29px"});
        $(".treeLoadingBox").html("<img src='${pageContext.request.contextPath}/images/ajax-loader.gif' />");
        $(".treeLoadingBox img").css({"position":"absolute","top":"50%","left":"50%","margin-top":"-16px","margin-left":"-16px"});
    }

    var key;
    $(function(){
        var conf = {
            url:'<%=basePath%>OrgController/getSearchableOrgzTree',
            name:'orgName',
            parentId:'parentId',
            onClick:function(event, treeId, treeNode){
                orgName = treeNode.orgName;
                var checkOrgid = treeNode.id;
                $('#orgIdVal').val(checkOrgid);
                var url = "<%=basePath %>UserManagerController/getUserManagerList";
                $("#dg").datagrid({url:url, method:'post', queryParams:{id:checkOrgid}});
                $('.addbtn').linkbutton('enable');
                Clist.addUrl = "<%=basePath%>UserManagerController/create?orgid="+checkOrgid;
                Clist.editUrl = "<%=basePath%>UserManagerController/update?orgid="+checkOrgid;
            }
        };
        $("#orgTree").initZtree(conf);

    });

    var lastValue = "", nodeList = [];
    function searchNode(value) {
        var orgTree = $.fn.zTree.getZTreeObj("orgTree");

        //var value = $.trim(key.get(0).value);
        var keyType = "";
        keyType = "orgName";


        if (lastValue === value) return;
        lastValue = value;

        //获取所有节点
        var nodes = orgTree.transformToArray(orgTree.getNodes());
        //将所有节点重新显示
        orgTree.showNodes(nodes) ;
        //去掉所有勾选的节点
        orgTree.checkAllNodes(false);
        //如果参数为空
        if(value == null || value==""){
            orgTree.reAsyncChildNodes(null, "refresh");

        }
        //获取搜索的节点
        var _nodes = orgTree.getNodesByParamFuzzy(keyType, value);
        //将搜索到的节点和父节点勾选上
        $.each(_nodes , function(i , node){
            orgTree.checkNode(node , true , true) ;
        });
        //获取所有勾选的节点,包含父节点
        _nodes = orgTree.getCheckedNodes();
        //如果没搜到结果，显示全部节点
        if(_nodes ==null || _nodes.length ==0 ) {
            orgTree.showNodes(nodes);
        }else{
            //隐藏所有节点
            orgTree.hideNodes(nodes);
            //显示被勾选的节点
            orgTree.showNodes(_nodes);
            orgTree.expandAll(true);
        }
        $('#orgIdVal').val("");
    }


    function searchUsers(){
        var orgIdValue = $('#orgIdVal').val();
        if(orgIdValue==""){
            $.messager.alert('提示信息','请选中相应的组织机构!','info');
            return;
        }
        var userName = $('#userName').val();
        var loginid = $('#loginid').val();
        var ownerid = $('#ownerid').val();
        var url = "<%=basePath %>UserManagerController/getUserManagerList";
        $("#dg").datagrid({url:url, method:'post', queryParams:{id:orgIdValue,userName:userName,loginid:loginid,ownerid:ownerid}});
    }
</script>
<style type="text/css">
.searchbox {
	background-color: #FFFFFF;
	border-color: #cccccc;
	margin-top: 10px;
	margin-right: 0;
	}
</style>

</head>
<body class="easyui-layout" data-options="fit:true">
<input type="hidden" id="orgIdVal" name="orgIdVal" value="" />
<div data-options="region:'west',title:'组织机构',split:true" style="width:220px;text-align: center;" id="treeBoxMan">
    <!--树菜单搜索功能-->
    <input id="key" class="easyui-searchbox" data-options="prompt:'请输入查询条件',searcher:searchNode" style="width:185px;height: 25px;"/>
    <ul id="orgTree" class="ztree"></ul>
    <div class="treeLoadingBox"></div>
</div>

<div data-options="region:'center',title:'人员列表'">
    <div id="toolbar" style="padding:5px;height:auto">
        <!-- 搜索 -->
        <div class="searchFormBox">
            <span class="nameBas"><b>人员姓名：</b><input class="easyui-validatebox" value="" name="userName" id="userName"></span>
            <span class="nameBas"><b>登录账号：</b><input class="easyui-validatebox" value="" name="loginid" id="loginid"></span>
            <span class="nameBas"><b>证件号码：</b><input class="easyui-validatebox" value="" name="ownerid" id="ownerid"></span>
            <a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="searchUsers()">搜索</a>
        </div>
        <div class="listButBox">
            <b:button opsUrl="/UserManagerController/create"><a href="javascript:void(0)" class="easyui-linkbutton addbtn" iconcls="icon-add" plain="true" onclick="doAdd(this)" disabled="true">添加</a></b:button>
        </div>
    </div>
    <table id="dg" class="easyui-datagrid" data-options="toolbar:'#toolbar',method:'get',border:false,onDblClickRow:doDatagridOnDblClickRow">
        <thead>
        <tr>
            <th data-options="field:'id',checkbox:true,width:20"></th>
            <th data-options="field:'userName',sortable:true,width:50">人员姓名</th>
            <th data-options="field:'loginid',width:50">登陆账号</th>
            <th data-options="field:'usertype',width:50">人员类型</th>
            <th data-options="field:'idtype',width:50">证件类型</th>
            <th data-options="field:'ownerid',width:50">证件号码</th>
            <th data-options="field:'isactive',width:50">是否有效</th>
            <th data-options="field:'true',align:'center',width:190,formatter:rowFormater2">操作</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>