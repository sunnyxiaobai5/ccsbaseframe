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
		listUrl : '<%=basePath%>service/getPosition', //列表url
		viewUrl : '<%=basePath%>PositionController/view', //双击查看地址
		addUrl : '<%=basePath%>PositionController/create', //新增表单提交地址
		editUrl : '<%=basePath%>PositionController/update', //编辑表单提交地址
		deleteUrl : '<%=basePath%>PositionController/del', //删除提交的url
		dialogWidth : 350, //弹出层宽度
		dialogHeight : 300, //弹出层高度
	};
	function rowFormater2(val,row,index) {
		return '&nbsp;<b:button opsUrl="/PositionController/copy"><a href="javascript:void(0)"  onclick="doCopy('+index+')">克隆</a></b:button><b:button opsUrl="/PositionController/update"> | <a class="listALink" href="javascript:void(0)" onclick="doEdit(this,'+index+')">编辑</a></b:button><b:button opsUrl="/PositionController/del"> | <a href="javascript:void(0)" onclick="doDelete(this,'+index+')">删除</a></b:button> &nbsp;';
	}
	$(function(){
		var currItem = {	//当前树选项
				node:null
		};
		//绑定事件，选择树时执行
		$("#tt").tree({onSelect:function(node){
			currItem.node = node;		//选择后赋值
			var a = "<%=basePath %>PositionController/getPositionList?id="+node.attributes.ID;
			$('.addbtn').linkbutton('enable')
			$("#dg").datagrid({url:a});
			var orgname = encodeURI(node.attributes.ORGNAME);
			Clist.addUrl = "<%=basePath%>PositionController/create?orgid="+node.attributes.ID+"&orgname="+orgname+"";
		}});
	});
 	function doCopy(index){
 		var rows = $('#'+Clist.id).datagrid('getData');
		var row = rows.rows[index];
			top.$.createWin({
			title:"克隆职位",
			url:'<%=basePath%>PositionController/copy?id='+row.id,
			height:550,
			width:650,
			maximizable:true, //是否最大化图标
			mask:true, //遮罩层
			resizable:true,
			isIframe:false,
			//isMax:true, //最大化
			buttons:[
			{
				text:'保存',
				handler:function(){
					var _winId = top.$(this).parent().parent().attr('id');
					var _form = top.$(this).parent().siblings('.panel').find('form');
					var orgid = top.$(this).parent().siblings('.panel').find('#orgid').val();
					if(orgid == ""){
						EmessagerAlert('提示','请选择组织机构！');
					}else{
						_form.form('submit',{
								onSubmit: function(){
									return top.$(this).form('validate');
								},
								success: function(result){
									$('#dg').datagrid('reload');
									doFormSubmit(result, _winId);
								}
							});
					}
				}
			}]
		});
 	}
 	
 	$(function(){
		$(".searchForm").parsexh({
			xmlURL:"<%=basePath%>plugin/CCSSearch.xml",
			param:{"id":"position"},
			success:function(){
				$(".searchForm").append('<a href="javascript:void(0)" id="searchBut" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch(\'position\',this)">搜索</a>');
				$(".searchForm").append('<a href="javascript:void(0)" class="easyui-linkbutton" onclick="doReset()">重置</a>');
				$.parser.parse(".searchForm");
			}
		});
	});
	
</script> 
</head>
<body class="easyui-layout" data-options="fit:true">
            <div data-options="region:'west',title:'组织机构',split:true" style="width:200px">
           		<ul id="tt" class="easyui-tree" data-options="url:'<%=basePath %>findCurrentOrgTree/-10',method:'get',animate:true,lines:true"></ul>
            </div>
            
            <div data-options="region:'center',title:'职位列表'">
	                <div id="toolbar" style="padding:5px;height:auto">
	                	<div class="searchFormBox">
				    <b:button opsUrl="/service/getPosition">
				        <form action="" method="post" id="position" class="searchForm">
				        </form>
				    </b:button>
				    </div>
		                <div class="listButBox">
		                <b:button opsUrl="/PositionController/create">
			                <a href="javascript:void(0)" class="easyui-linkbutton addbtn" iconcls="icon-add" plain="true" onclick="doAdd(this)" disabled="true">新增职位</a>
		            	</b:button>
		            	</div>
	            	</div>
					 <table id="dg" class="easyui-datagrid" data-options="url:'<%=basePath %>PositionController/getPositionList?id=-1',toolbar:'#toolbar',method:'get',border:false,onDblClickRow:doDatagridOnDblClickRow">
					  <thead>
		                <tr>
		                    <th data-options="checkbox:true"></th>
		                    <th data-options="field:'pname',sortable:true,width:15">职位名称</th>
		                    <th data-options="field:'pno',width:15">职位编号</th>
		                    <th data-options="field:'orgname',width:15">所属组织</th>
		                    <th data-options="field:'description',width:55">职位描述</th>
		                    <th data-options="field:'true',align:'center',formatter:rowFormater2">操作</th>
		                </tr>
	          		  </thead>
	          		  </table>
            </div>
</body>
</html> 
