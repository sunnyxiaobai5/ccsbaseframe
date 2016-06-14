<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .nameBasBig span.nameBas {
        width: 580px;
        margin-top: 10px;
    }

    .nameBasBig span.nameBas .easyui-validatebox, .nameBasBig span.nameBas .validatebox-text {
        width: 450px;
        height: 20px;
    }

    .nameBasBig span.nameBas b {
        width: 80px;
    }

    .wu-example {
        position: relative;
    }

    .wu-example:after {
        position: absolute;
    }

    .uploadBtn {
        height: 36px;
        width: 78px;
        background: #00b7ee;
        border: 1px solid #cfcfcf;
        color: #ffffff;
        display: inline-block;
        border-radius: 3px;
        margin-left: 10px;
        cursor: pointer;
        font-size: 12px;
        float: left;
        font-family: "微软雅黑", Arial, Helvetica, sans-serif;

    }
    
	.searchbox {
		background-color: #FFFFFF;
		border-color: #cccccc;
		margin-top: 10px;
		margin-right: 0;
	}
	.tooltip{
		z-index:9001;
	}
</style>
	<link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="../js/jquery.ztree.exedit-3.5.js"></script>
<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/webuploader/webuploader.css">
<!--引入JS-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/webuploader/webuploader.min.js"></script>
<script type="text/javascript">
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


$.fn.extend({
    initZtreeUser:function(conf){
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
            edit: {
                drag: {
                    autoExpandTrigger: true,
                    prev: dropPrev,
                    inner: dropInner,
                    next: dropNext
                },
                enable: true,
                showRenameBtn: false
            },
            callback:{
                beforeDrag: beforeDrag,
                beforeDrop: beforeDrop,
                beforeDragOpen: beforeDragOpen,
                onDrag: onDrag,
                onDrop: onDrop,
                onExpand: onExpand,
                onAsyncSuccess:conf.onAsyncSuccess
            }
        };
        ztreeObj = $.fn.zTree.init($(this), setting);
        return ztreeObj ;
    }
});

function dropPrev(treeId, nodes, targetNode) {
    var pNode = targetNode.getParentNode();
    if (pNode && pNode.dropInner === false) {
        return false;
    } else {
        for (var i=0,l=curDragNodes.length; i<l; i++) {
            var curPNode = curDragNodes[i].getParentNode();
            if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                return false;
            }
        }
    }
    return true;
}
function dropInner(treeId, nodes, targetNode) {
    if (targetNode && targetNode.dropInner === false) {
        return false;
    } else {
        for (var i=0,l=curDragNodes.length; i<l; i++) {
            if (!targetNode && curDragNodes[i].dropRoot === false) {
                return false;
            } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
                return false;
            }
        }
    }
    return true;
}
function dropNext(treeId, nodes, targetNode) {
    var pNode = targetNode.getParentNode();
    if (pNode && pNode.dropInner === false) {
        return false;
    } else {
        for (var i=0,l=curDragNodes.length; i<l; i++) {
            var curPNode = curDragNodes[i].getParentNode();
            if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                return false;
            }
        }
    }
    return true;
}

var log, className = "dark", curDragNodes, autoExpandNode;
function beforeDrag(treeId, treeNodes) {
    className = (className === "dark" ? "":"dark");
    for (var i=0,l=treeNodes.length; i<l; i++) {
        if (treeNodes[i].drag === false) {
            curDragNodes = null;
            return false;
        } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
            curDragNodes = null;
            return false;
        }
    }
    curDragNodes = treeNodes;
    return true;
}
function beforeDragOpen(treeId, treeNode) {
    autoExpandNode = treeNode;
    return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
    className = (className === "dark" ? "":"dark");
    return true;
}
function onDrag(event, treeId, treeNodes) {
    className = (className === "dark" ? "":"dark");
}
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
    className = (className === "dark" ? "":"dark");
}
function onExpand(event, treeId, treeNode) {
    if (treeNode === autoExpandNode) {
        className = (className === "dark" ? "":"dark");
    }
}

function setTrigger() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
}
$(function () {
    var state = "";
    var uploader = WebUploader.create({

        // swf文件路径
        swf: '${pageContext.request.contextPath}/js/webuploader/Uploader.swf',

        // 文件接收服务端。
        server: '${pageContext.request.contextPath}/mail/uploadFile',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',

        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false,
        fileSingleSizeLimit: 10*1024*1024
    });

    // 当有文件被添加进队列的时候
    uploader.on('fileQueued', function (file) {

        $('#thelist').append('<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + file.name + '</h4>' +
                '<p class="state">等待上传...</p>' +
                '</div>');


        var $fileNum = $('#fileNum');
        $fileNum.val(parseInt($fileNum.val())+1);
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function (file, percentage) {
        var $li = $('#' + file.id),
                $percent = $li.find('.process');

        // 避免重复创建
        if (!$percent.length) {
            $percent = $('<div class="process" style="width:300px;height:20px;margin-bottom: 10px;">' +
                    '</div>').appendTo($li).find('.process');

        }

        $percent.progressbar({
            value: parseInt(percentage * 100)
        });

        $li.find('p.state').text('上传中');

    });

    uploader.on('uploadSuccess', function (file, response) {
        $('#' + file.id).find('p.state').text('已上传');
        $('<input type="hidden" name="fileName" value="'+response._raw+':'+file.name+'">').appendTo($('#popForm'));
    });

    uploader.on('uploadError', function (file) {
        $('#' + file.id).find('p.state').text('上传出错');
    });

    uploader.on('uploadComplete', function (file) {
        $('#' + file.id).find('.process').fadeOut();
    });

    uploader.on('all', function (type) {
        if (type === 'startUpload') {
            state = 'uploading';
        } else if (type === 'stopUpload') {
            state = 'paused';
        } else if (type === 'uploadFinished') {
            state = 'done';
        }

        if (state === 'uploading') {
            $('#ctlBtn').text('暂停上传');
        } else {
            $('#ctlBtn').text('开始上传');
        }
    });

    $('#ctlBtn').on('click', function () {
        if (state === 'uploading') {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });

    var conf = {
        url:'' ,
        name:'text',
        parentId:'parentId'
    } ;
    $("#treeDemo").initZtreeUser(conf) ;

    var conf = {
        url:'${pageContext.request.contextPath}/OrgController/getAcceptableOrgzTree',
        name:'orgName',
        parentId:'parentId',
        beforeAsync:zTreeBeforeAsync,
        onAsyncSuccess:zTreeOnAsyncSuccess,

        onClick:function(event, treeId, treeNode){
            orgName = treeNode.orgName;
            var checkOrgid = treeNode.id;
            $('#orgIdVal').val(checkOrgid);
            $("#userdg").datagrid({url:"${pageContext.request.contextPath}/UserManagerController/getUserManagerList?id=" + checkOrgid});
            $('.addbtn').linkbutton('enable');
        }
    };


    $("#orgTree").initZtree(conf);

});

function rowFormater(val,row,index) {
    var rows = $("#userdg").datagrid("getRows");
    var datas = rows[index];
    console.log(datas.userName);
    return '&nbsp;<a href="#" onclick="assignUsers('+ datas.id +',\''+ datas.loginid +'\',\''+ datas.userName +'\')">添加</a>&nbsp;';
}

function assignUsers(id,loginid,userName){
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    console.log(userName);
    var node = treeObj.getNodeByParam("tid", id, null);
    if(node==null){
        var newNode = {tid:id,text:userName+'('+loginid+')',dropInner: false};
        newNode = treeObj.addNodes(null, newNode);
    }
    console.log(node);
}

function selUser(){
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes = treeObj.getNodes();
    console.log(nodes);
    var receiversStr = "";
    var receivers = "";
    if(nodes.length>0){
        for(var i in nodes){
            receiversStr += nodes[i].text+',';
            receivers += dealStr(nodes[i].text)+',';
        }
    }
    $("input[name='receiversStr']").val(receiversStr);
    $("input[name='receivers']").val(receivers);
	$("#win").dialog('close');
}

function dealStr(text){
    var startIndex = text.indexOf('(');
    var endIndex = text.indexOf(')');
    var user = text.substring(startIndex+1,endIndex);
    return user;
}

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

function MybeforeSubmit(){

    var num = $('#fileNum').val();
    var finishNum = $('input[name="fileName"]').length;
    if(parseInt(num) != finishNum){
        EmessagerAlert("警告","请等待文件上传完成后再发送","warning")
        return false;
    }
}

function openUserDialog(){
    $('#win').dialog('open');

}

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
    var url = "${pageContext.request.contextPath}/UserManagerController/getUserManagerList?id="+orgIdValue+"&userName="+userName+"&loginid="+loginid;
    $("#userdg").datagrid({url:url});
}


</script>

<input type="hidden" id="orgIdVal" name="orgIdVal" value="" />
<div class="popFormBox nameBasBig">
    <form action="${pageContext.request.contextPath}/mail/sendMail"
            method="post" id="popForm">
        <input type="hidden" id="fileNum" name="fileNum" value="0"/>

		<span class="nameBas"> <b>标题：</b>
			<input type="text" name="title" class="easyui-validatebox"
                   data-options="required:true"/>
		</span>
        <span class="msgBox"><b class="msgBoxB">*</b></span>
        <span class="nameBas"> <b>接受者：</b>
            <c:choose>
                <c:when test="${not empty mail}">
                    <input type="text" name="receiversStr" class="easyui-validatebox" value="${mail.userName}(${mail.sender})" readonly="true"
                           data-options="required:true"/>
                    <input type="hidden" name="receivers" value="${mail.sender}"/>
                </c:when>
                <c:otherwise>
                    <input type="text" name="receiversStr" class="easyui-validatebox"  contenteditable="false" onfocus="openUserDialog()"
                           data-options="required:true"/>
                    <input type="hidden" name="receivers" />
                </c:otherwise>
            </c:choose>

		</span>

        <span class="msgBox"><b class="msgBoxB">*</b></span>
		<span class="nameBas"> <b>内容：</b>
			<textarea type="text" name="content" class="easyui-validatebox" rows="20"
                      style="height: 200px; vertical-align: top"
                      data-options="required:true"></textarea>
		</span>
        <span class="msgBox"><b class="msgBoxB">*</b></span>
		<span class="nameBas "><b style="float: left;">附件：</b>
			<div class="wu-example" style="float: left;">
                <!--用来存放文件信息-->
                <div id="thelist" class="uploader-list"></div>
                <div style="width: 450px;">
                    <div id="picker" style="float: left;">选择文件</div>
                    <button id="ctlBtn" class="uploadBtn" type="button">开始上传</button>
                </div>
            </div>
		</span>
    </form>
</div>
<div style="display: none;">
<div id="win" class="easyui-dialog" title="人员选择" style="width:900px;height:500px;"
     data-options="modal:true,collapsible:false,minimizable:false,closed:true">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'west',title:'组织机构',split:true" style="width:220px;text-align: center;" id="treeBoxMan">
            <!--树菜单搜索功能-->
    		<input id="key" class="easyui-searchbox" data-options="prompt:'请输入查询条件',searcher:searchNode" style="width:185px;height: 25px;"/>
            <ul id="orgTree" class="ztree"></ul>
            <div class="treeLoadingBox"></div>
        </div>
        <div data-options="region:'center',title:'人员列表'">
        	<div class="easyui-layout" data-options="fit:true,border:false">
            <div class="searchFormBox" data-options="region:'north',border:false" style="height: 75px;padding-top: 5px;background: #f4f4f4">
	            <span class="nameBas"><b>人员姓名：</b><input class="easyui-validatebox" value="" name="userName" id="userName"></span>
	            <span class="nameBas"><b>登录账号：</b><input class="easyui-validatebox" value="" name="loginid" id="loginid"></span>
	            <a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="searchUsers()">搜索</a>
	        </div>
	        <div data-options="region:'center',border:false">
	        <table id="userdg" class="easyui-datagrid" data-options="method:'get',border:false">
		        <thead>
		        <tr>
		            <th data-options="field:'id',checkbox:true,width:20"></th>
		            <th data-options="field:'userName',sortable:true,width:50">人员姓名</th>
		            <th data-options="field:'loginid',width:50">登陆账号</th>
		            <th data-options="field:'usertype',width:50">人员类型</th>
            		<th data-options="field:'true',align:'center',width:20,formatter:rowFormater">操作</th>
		        </tr>
		        </thead>
		    </table>
		    </div>
		    </div>
        </div>
        <div data-options="region:'east',title:'已选人员',split:true" style="width:220px;">
            <ul id="treeDemo" class="ztree"></ul>
        </div>
		<div data-options="region:'south'" style="height: 40px;background: #F4F4F4;text-align: right; padding: 5px 10px 0 0;overflow: hidden;">
		    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="selUser()">确定</a>
		</div>
    </div>
</div>
</div>
