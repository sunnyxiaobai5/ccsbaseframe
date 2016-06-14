<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <!--                       CSS                       -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/libs/easyui/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/libs/easyui/icon.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reeasyui.css" type="text/css" media="screen"/>
    <!--                       Javascripts                       -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/libs/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/libs/easyui/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <!--                       zTree                             -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.excheck-3.5.js"></script><!--  需要复选框的js  -->--%>
</head>
<body class="easyui-layout" data-options="fit:true">
<div data-options="region:'west',title:'组织机构列表',split:true" style="width:200px;">
    <%--    	<ul id="orgTree" class="easyui-tree" data-options="url:'${pageContext.request.contextPath}/findCurrentOrgTree/-10',method:'get',animate:true,lines:true"></ul>--%>
    <ul id="orgTree" class="ztree"></ul>
</div>
<div data-options="region:'center',split:true" style="background: #F0F0F0">
    <div id="tt" class="easyui-tabs" data-options="fit:true">

    </div>
</div>

<div id="rMenu" class="easyui-menu">
    <div id="m_add" data-options="iconCls:'icon-add'" onclick="addOrgTab();">增加</div>
    <div id="m_update" data-options="iconCls:'icon-edit'" onclick="updateOrgTab()">修改</div>
    <div class="menu-sep"></div>
    <div id="m_refresh" data-options="iconCls:'icon-reload'" onclick="refreshOrgTree()">刷新</div>
</div>
</body>
<script>

    var orgTree, rMenu;
    var currItem = {	//当前树选项
        node: null
    };


    $(function () {
        //禁用所有input
        $(".formBut input").addClass("orgForm");


        var conf = {
            url: '${pageContext.request.contextPath}/OrgController/getOrgzTree',
            name: 'orgName',
            parentId: 'parentId',
            onClick: function (event, treeId, treeNode) {
                currItem.node = treeNode;

                //可编辑
                $(".popFormBox input").attr("disabled", "");

            },
            onRightClick: function (event, treeId, treeNode) {
                currItem.node = treeNode;
                orgTree.selectNode(treeNode);
                if (treeNode && !treeNode.parentId) {
                    showRMenu("root", event.clientX, event.clientY);
                } else if (treeNode) {
                    showRMenu("node", event.clientX, event.clientY);
                }
            }
        };

        $("#orgTree").initZtree(conf);

        orgTree = $.fn.zTree.getZTreeObj("orgTree");
        rMenu = $("#rMenu");
    });


    function orderSelectChange(newValue, oldValue) {
        var _form = $(this).parents('form');
        _form.find('input[name="sort"]').val(newValue);
    }

    function clickSortRadio() {
        // 排序
        $("input[name='orderCheck']").on('click',function (event) {
            var _form = $(this).parents('form');
            if ($(this).val() == 2) {
                _form.find('input[name="sort"]').val(1);
                _form.find('input[name="orderSelect"]').attr('disabled', "disabled");
            } else {
                _form.find('input[name="orderSelect"]').attr('disabled', "");
                _form.find('input[name="sort"]').val(_form.find('input[name="oldSort"]').val()); // 还原
            }
        });
    }

    function showRMenu(type,x, y) {
        if(type == 'root'){
            $('#m_update').hide();
        }else{
            $('#m_update').show();
        }

        $("#rMenu").menu('show', {
            left: x,
            top: y
        });



        $("body").bind("mousedown", onBodyMouseDown);
    }
    function hideRMenu() {
        $("#rMenu").menu('hide');
        $("body").unbind("mousedown", onBodyMouseDown);
    }
    function onBodyMouseDown(event) {
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
            hideRMenu();
        }
    }

    function addOrSelectTab(name, url, id) {
        //选择并刷新
        if ($('#tt').tabs('exists', name)) {
            $('#tt').tabs('select', name);
            var selectedTab = $('#tt').tabs('getSelected');
            // call 'refresh' method for tab panel to update its content
            selectedTab.panel('refresh', url);
        } else {//新增一个tab页面
            $('#tt').tabs('add', {
                title: name,
                id: id,
                href: url,
                fit: true,
                closable:true
            });
            $('#tt').tabs('getTab', name).panel('body').css({'overflow': 'hidden'});
        }
    }
    //右键添加
    function addOrgTab() {
        var name = "添加";
        var url = "${pageContext.request.contextPath}/OrgController/getOrgDetail?parentId=" + currItem.node.id;
        addOrSelectTab(name, url, 0);
        hideRMenu();
    }
    //右键修改
    function updateOrgTab() {
        var name = "修改";
        var url = "${pageContext.request.contextPath}/OrgController/getOrgDetail?parentId=" + currItem.node.parentId + "&orgId=" + currItem.node.id;
        addOrSelectTab(name, url, 1);
        hideRMenu();
    }
    //邮件刷新
    function refreshOrgTree() {
        orgTree.reAsyncChildNodes(null, "refresh");
        hideRMenu();
    }

    //添加表单或编辑表单提交
    function orgSubmit(formid) {
        $('#' + formid).form('submit', {
            dataType: 'json',
            onSubmit: function () {
                var orgPid = $(".org-top #parentId").val();
                return orgPid && $(this).form('validate');
            },
            success: function (result) {
                result = parseReturn(result);
                if (result.status == 'success') {
                    Cmessager.show({
                        title: GV.MESSAGER_TITLE,
                        msg: result.message,
                        timeout: 1500
                    });
                    if (formid == 'addform') {
                        $('#addform').form('clear');
                    }

                    refreshOrgTree();//刷新
                } else {
                    Cmessager.alert(GV.MESSAGER_TITLE, result.message, 'error');
                }
            }
        });
    }


</script>
</html>

