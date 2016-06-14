<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>index</title>
<!--                       CSS                       -->
<link rel="stylesheet" type="text/css" href="../js/libs/easyui/easyui.css" />
<link rel="stylesheet" type="text/css" href="../js/libs/easyui/icon.css" />
<link rel="stylesheet" href="../css/reeasyui.css" type="text/css" media="screen" />
<!--                       Javascripts                       -->
<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="../js/libs/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/libs/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript" src="../js/ccsSearch.js"></script>
<!--                       Ztree                       -->
<link rel="stylesheet" href="../css/demo.css" type="text/css">
<link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
</head>
<body class="easyui-layout" data-options="fit:true">
    <input type="hidden" id="orgIdVal" name="orgIdVal" value="" />
    <div data-options="region:'west',title:'组织机构',split:true" style="width:220px;" id="treeBoxMan">
		<ul id="orgTree" class="ztree"></ul>
		<div class="treeLoadingBox"></div>
    </div>
    <div data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,border:false">   
            <div data-options="region:'north',split:true,title:'角色列表',border:true" style="height:391px">
            	<table id="dg" class="easyui-datagrid" data-options="toolbar:'#toolbar2',pageSize:10,onClickRow:doOnClickRow,border:false">
				    <thead>
				        <tr>
				            <th data-options="field:'rName',width:50,align:'center'">角色名称</th>
				            <th data-options="field:'remark',width:50,align:'center'">角色描述</th>
				            <th data-options="field:'createrUser',width:20,align:'center'">创建人</th>
				            <th data-options="field:'sysInstTime',width:20,align:'center'">创建时间</th>
				            <th data-options="field:'true',align:'center',formatter:roleRowFormater">操作</th>
				        </tr>
				    </thead>
				</table>
				<div id="toolbar2" style="padding:5px;height:auto">
					<!-- 搜索 -->
				    <div class="searchFormBox">
                            <span class="nameBas"><b>角色名称：</b><input class="easyui-validatebox" value="" name="roleName" id="roleName"></span>
                            <a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="searchRoles()">搜索</a>
				    </div>
					<!-- 其他操作按钮 -->
					<div class="listButBox">
				    	<b:button opsUrl="/RoleController/save"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="doAdd(this)">添加</a></b:button>
				    </div>
				</div>
			</div>
			 <div data-options="region:'center',title:'用户列表'" class="emTabs">
            	<table id="user" class="easyui-datagrid" data-options="pagination:true,fit:true,pageSize:10,border:false">
				    <thead>
				        <tr>
				            <th data-options="field:'userName',align:'center'">用户姓名</th>
				            <th data-options="field:'loginid',width:20,align:'center'">登录帐号</th>
				            <th data-options="field:'usertype',width:30,align:'center'">用户类型</th>
				            <th data-options="field:'idtype',width:20,align:'center'">证件类型</th>
				            <th data-options="field:'ownerid',width:50,align:'center'">证件号码</th>
				            <th data-options="field:'isactive',align:'center'">是否有效</th>
				            <th data-options="field:'true',align:'center',formatter:rowFormaterUser">操作</th>
				        </tr>
				    </thead>
				</table>
            </div>   	
        </div>
    </div>   

<script type="text/javascript">
var Clist = {
	dg : {
		idField : 'id',
		listUrl : '<%=basePath%>service/getRole', //列表url
		addUrl : '<%=basePath %>RoleController/create', //新增表单提交地址
		editUrl : '<%=basePath %>RoleController/update', //编辑表单提交地址
		edit2Url : '<%=basePath %>RoleController/setUsers', //编辑表单提交地址
		deleteUrl : '<%=basePath %>RoleController/del', //删除提交的url
		dialogWidth : 840, //弹出层宽度
		dialogHeight : 520 //弹出层高度
	},
	user: {
		idField : 'id',
		listUrl : '../../json/get_users.json', //列表url
		deleteUrl : '<%=basePath %>RoleController/delRoleUser/', //删除提交的url
		editUrl : '<%=basePath %>RoleController/setUserPerm/', //编辑表单提交地址
		dialogWidth : 750, //弹出层宽度
		dialogHeight : 470 //弹出层高度
	}
	
};

function rowFormaterUser(val,row,index) {
	return '<b:button opsUrl="/RoleController/setUserPerm/{roleId}">&nbsp;<a class="listALink" href="javascript:javascript:void(0)" onclick="doEdit(this,'+index+')">修改权限</a></b:button><b:button opsUrl="/RoleController/delRoleUser/{roleId}"> <a href="javascript:javascript:void(0)" onclick="doDelete(this,'+index+')">删除</a>&nbsp;</b:button>';
}
function roleRowFormater(val,row,index) {
	return '<b:button opsUrl="/RoleController/saveRoleUser/{roleId}">&nbsp;<a class="listALink" href="javascript:javascript:void(0)" onclick="doEdit2(this, '+index+')" data-options="winId:\'setDialog\',buttons:\'none\'">分配</a></b:button>'+
	'<b:button opsUrl="/RoleController/update">&nbsp;<a class="listALink" href="javascript:javascript:void(0)" onclick="doEdit(this, '+index+')">编辑</a></b:button>'+
	'<b:button opsUrl="/RoleController/del">&nbsp;<a href="javascript:javascript:void(0)" onclick="doDelete(this, '+index+')">删除</a>&nbsp;</b:button>';
}

function doOnClickRow(rowIndex, rowData){
	Clist.user.deleteUrl = splitUrl(Clist.user.deleteUrl)+rowData.id;
	Clist.user.editUrl = splitUrl(Clist.user.editUrl)+rowData.id;
	var a = "<%=basePath %>RoleController/usersList/"+rowData.id;
	$("#user").datagrid({url:a});
}
function splitUrl(url){
	return url.substring(0,url.lastIndexOf("/")+1);
}

//分配
function doEdit2(elem, index, isIframe) {
    var orgIdValue = $('#orgIdVal').val();
    if(orgIdValue==""){
        $.messager.alert('提示信息','请选中相应的组织机构!','info');
        return;
    }

	var _Clist = doBeforeDatagrid(0, elem);
	 
	var rows = $('#'+_Clist.id).datagrid('getData');
	var row = rows.rows[index];
	
	var _url = _Clist.edit2Url+(_Clist.edit2Url.indexOf('?')!=-1?'&':'?');
	var _extUrl = [];
	var _args = _Clist.idField.split('&');
	for(var i in _args) {
		_extUrl.push(_args[i]+'='+row[_args[i]]);
	}
    _extUrl.push('orgid='+orgIdValue);
	_url += _extUrl.join('&');
	
	var _options = {};
	_options = parseOptions(elem);
	if(_options.buttons == undefined) {
		_options.buttons = 'formSubmit';
	}
	
	if(_options.title == undefined || !_options.title) {
		_options.title = '分配用户';
	}
	
	_options.url = _url;
	if(!_options.width) {
		_options.width = (_Clist.dialogWidth?_Clist.dialogWidth:'auto');
	}
	if(!_options.height) {
		_options.height = (_Clist.dialogHeight?_Clist.dialogHeight:'auto');
	}
	_options._Clist = _Clist;
	_createWin(_options);
}

	
	//组织机构树配置
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
					enable: conf.check || true,
					chkStyle:"checkbox",
					autoCheckTrigger: true,
					chkboxType: conf.chkboxType || { "Y": "", "N": "" }
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
	
	$(function(){
		//组织机构树
		var conf = {
			url:'<%=basePath%>OrgController/getOrgzTree' ,
			name:'orgName',
			parentId:'parentId',
			onClick:function(event, treeId, treeNode){
				var orgid = treeNode.id;
                $('#orgIdVal').val(orgid);
				var url = "<%=basePath %>RoleController/roleListByOrgid?orgid="+orgid;
				$("#dg").datagrid({url:url});
			}
		} ;
		$("#orgTree").initZtree(conf) ;

		//待定
		//var url = "<%=basePath %>RoleController/roleListByOrgid?orgid="+26;
		//$("#dg").datagrid({url:url});
	});

    function searchRoles(){
        var orgIdValue = $('#orgIdVal').val();
        if(orgIdValue==""){
            $.messager.alert('提示信息','请选中相应的组织机构!','info');
            return;
        }
        var roleName = $('#roleName').val();
        var url = "<%=basePath %>RoleController/roleListByOrgid";
        $("#dg").datagrid({url:url, method:'post', queryParams:{orgid:orgIdValue,roleName:roleName}});
    }
</script>   
</body>
</html>
