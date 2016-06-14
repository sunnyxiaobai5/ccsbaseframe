<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>区域管理</title>
<!--                       CSS                       -->
<link rel="stylesheet" type="text/css"
	href="../js/libs/easyui/easyui.css">
<link rel="stylesheet" type="text/css" href="../js/libs/easyui/icon.css">
<link rel="stylesheet" href="../css/reeasyui.css" type="text/css"
	media="screen" />
<!--                       Javascripts                       -->
<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="../js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript"
	src="../js/libs/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../js/libs/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript"><!--

$(function(){
	var currItem = {	//当前树选项
			node:null
	};
		//绑定事件，选择树时执行
		$("#regionCodeTree").tree({onSelect:function(node){
		currItem.node = node;		//选择后赋值
		$("#btnSubmit").attr("style","display:none");
		$.ajax({
				type: "get",
				url: "<%=basePath%>submitToken/new",
				contentType: "text/plain; charset=utf-8",
				success: function(val)
				{
					var parentNode = $('#regionCodeTree').tree('getParent', node.target);
					if(parentNode==null){
						$("#parentRegionName").val("");
					}else{
						$("#parentRegionName").val(node.attributes.PARENTREGIONNAME);
					}
					$("#token").val(val.token);
					$("#regionCodeStr").val(node.attributes.REGIONCODE);
					$("#id").val(node.attributes.ID);
					$("#regionName").val(node.attributes.REGIONNAME);
					$("#regionAbb").val(node.attributes.REGIONABB);
					$("#regionPId").val(node.attributes.REGIONPID);
					$("#retrievalA").val(node.attributes.RETRIEVALA);
					$("#retrievalB").val(node.attributes.RETRIEVALB);
					setDisabled(true);
				},
				error: function(eo)
				{
					alert("ajax调用出错！");
				}
			});
	}});
});

function setDisabled(status) {
	$("#regionCodeStr").prop("disabled", status);
	$("#regionAbb").prop("disabled", status);
	$("#regionName").prop("disabled", status);
	$("#parentRegionName").prop("disabled", status);
	$("#retrievalA").prop("disabled", status);
	$("#retrievalB").prop("disabled", status);
}

function reset(){
	$("#regionCodeStr").val("");
	$("#id").val("");
	$("#regionName").val("");
	$("#regionAbb").val("");
	$("#regionPId").val("");
	$("#retrievalA").val("");
	$("#retrievalB").val("");
	$("#parentRegionName").val("");
	$("#btnSubmit").attr("style", "display:none");
}

function doDeleteNode() {
	var node = $('#regionCodeTree').tree('getSelected');
	if(node!=null){
		if(confirm("确认要删除该区域吗?")){
			$.post('<%=basePath %> RegionManageController/deleteRegionCode', {id : node.attributes.ID}, function(data) {
					if (data.status == 'success') {
					//	Cmessager.alert("删除成功", data.message);
						Cmessager.show({
										title : '信息提示',
										msg : data.message,
										timeout : 1500
									});
						$('#regionCodeTree').tree('reload');
						reset();
					} else {
						Cmessager.alert("删除失败", data.message);
					}
				});
			}
	}else{
		Cmessager.alert("提示", "请选择要删除区域！");
	}
}
	function doUpdateNode() {
		var node = $('#regionCodeTree').tree('getSelected');
		if (node != null) {
			var parentNode = $('#regionCodeTree').tree('getParent', node.target);
			$('#btnSubmit').attr("style", "display:inline-block");
			if (parentNode == null) {
				$("#parentRegionName").val("");
			} else {
				$("#parentRegionName").val(node.attributes.PARENTREGIONNAME);
			}
			$("#regionCodeStr").prop("disabled", false);
			$("#regionName").prop("disabled", false);
			$("#regionAbb").prop("disabled", false);
			$("#retrievalA").prop("disabled", false);
			$("#retrievalB").prop("disabled", false);
			$("#regionCodeStr").val(node.attributes.REGIONCODE);
			$("#id").val(node.attributes.ID);
			$("#regionName").val(node.attributes.REGIONNAME);
			$("#regionAbb").val(node.attributes.REGIONABB);
			$("#regionPId").val(node.attributes.REGIONPID);
			$("#retrievalA").val(node.attributes.RETRIEVALA);
			$("#retrievalB").val(node.attributes.RETRIEVALB);
		}else{
			Cmessager.alert("提示", "请选择修改区域！");
		}
	}
	/* 质朴长存法  by lifesinger */
	function pad(num, n) {
		var len = num.toString().length;
		while (len < n) {
			num = "0" + num;
			len++;
		}
		return num;
	}
	function submitClick() {
		var regionCode = $("#regionCodeStr").val();
		$("#regionCode").val(regionCode);
		
		var _url = "/CCSBaseFrame/RegionManageController/saveRegionCode";
		if($('#id').val() != '') {
			_url = "/CCSBaseFrame/RegionManageController/updRegionCode";
		}
		$('#regionForm').form('submit', {
			url : _url,
			onSubmit : function(param) {
				var status = $('#regionForm').form('validate');
				if (status) {
					$('#btnSubmit').css('display', 'none');
				}
				return status;
			},
			success : function(result) {
				result = parseReturn(result);
				if (result.status == 'success') {
					Cmessager.show({
						title : '信息提示',
						msg : result.message,
						timeout : 1500
					});
					$('#regionCodeTree').tree('reload');
					reset();
				} else {
					Cmessager.alert('', result.message);
				}
			}
		});
	};

	function doAddNode() {
		var node = $('#regionCodeTree').tree('getSelected');
		//var parentNode = $('#regionCodeTree').tree('getParent', node.target);
		if (node!=null) {
			$("#btnSubmit").attr("style", "display:inline-block");
			//if(!parentNode){
			//	$('#regionCodeStr').prop("disabled", false);
			//	$("#regionCodeStr").val("");	
			//}else{
			//	$('#regionCodeStr').prop("disabled", true);
			/*var  clen = node.children.length;
			if (clen) {
				$("#regionCodeStr").val(node.attributes.REGIONCODE+pad(clen+1,2));
			}else{
				$("#regionCodeStr").val(node.attributes.REGIONCODE+pad(1,2));
			}*/
			//}
			$("#regionCodeStr").val("");
			$("#regionCodeStr").prop("disabled", false);
			$("#id").val("");
			$("#regionName").prop("disabled", false);
			$("#regionAbb").prop("disabled", false);
			$("#retrievalA").prop("disabled", false);
			$("#retrievalB").prop("disabled", false);
			$("#parentRegionName").val(node.attributes.REGIONNAME);
			$("#regionPId").val(node.attributes.REGIONCODE);
			$("#regionName").val("");
			$("#regionAbb").val("");
			$("#retrievalA").val("");
			$("#retrievalB").val("");
		}else{
			Cmessager.alert("提示", "请选择要添加的区域！");
		}
	}


    //自定义validatebox的验证方法
       $.extend($.fn.validatebox.defaults.rules, {  
        remote: {  
            validator: function(value){  
                	var flag=true;
					$.ajax({
						url:'<%=basePath %> RegionManageController/findRegionCode',
						timeout:5000,
						type:"POST",
						async:false,
						data:{id:$('#id').val() ,regionCode : $('#regionCodeStr').val()},
						success:function(data){
							if(data.status=="error"){
								flag = false;
								$.fn.validatebox.defaults.rules.remote.message = data.message;
							}else{
								 flag = true;
							}
						},
						error:function(data){
							flag = false;
						}
					});
                  return flag;
            }  
        }
    });
</script>
</head>

<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',title:'区域',split:true" style="width:220px;">
<!--  		<ul id="regionCodeTree" class="easyui-tree" data-options="url:'<%=basePath%>RegionManageController/findAllRegionCode',method:'get',animate:true,lines:true"></ul> -->
 		<ul id="regionCodeTree" class="easyui-tree" data-options="url:'<%=basePath%>findRegionManageTree/1',method:'get',animate:true,lines:true"></ul>
	</div>
	<div data-options="region:'center',title:'区域详细'"
		style="background:#eee; margin-top: -1px;">
<!-- 		<div id="toolbar" style="padding:5px;height:auto"> -->
			<!-- 搜索 -->
			<!-- <div class="searchFormBox">
		        <form action="" method="" id="searchForm">
		        	<span class="nameBas"><b>功能名称：</b><input class="easyui-validatebox" name="fun" 

/></span>
		            <span class="nameBas"><b>功能类型： </b><select class="easyui-combobox" name="language" 

panelHeight="auto" />
		                <option value="java">Java</option>
		                <option value="c">C</option>
		                <option value="basic">Basic</option>
		                <option value="perl">Perl</option>
		                <option value="python">Python</option>
		            </select></span>
		            <span class="nameBas"><b>标示：</b><input class="easyui-validatebox" name="to" /></span>
		            <span class="nameBas"><b>区域：</b><input class="easyui-validatebox" name="to" /></span>
		        	<span class="nameBas"><b>开始时间：</b><input class="easyui-datebox" name="from" /></span>
		            <span class="nameBas"><b>结束时间：</b><input class="easyui-datebox" name="to" /></span>
		            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch

('searchForm')">搜索</a>
		        </form>
		    </div>-->
			<!-- 其他操作按钮 -->
			<div class="listButBox" style="padding-top: 0px;">
				<b:button opsUrl="/RegionManageController/saveRegionCode">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="doAddNode()">添加</a> 
				</b:button>	
				<b:button opsUrl="/RegionManageController/updRegionCode">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="doUpdateNode()">修改</a>
				</b:button>
				<b:button opsUrl="/RegionManageController/deleteRegionCode">	
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="doDeleteNode()">删除</a>
				</b:button>
			</div>

				<form class="popFormBox" id="regionForm" method="post">
					<!--		    	action="/CCSBaseFrame/RegionManageController/saveRegionCode" -->
					<input type="hidden" id="token" name="token" value="${token }" />	
					<input id="id" name="id" type="hidden" />
					<!--	type="hidden" -->
					<input id="regionPId" name="regionPId" type="hidden" /> 
					<input name="regionCode" id="regionCode" type="hidden" />
					<span class="nameBas"><b>上级区域：</b><input id="parentRegionName" name="parentRegionName" type="text" disabled="disabled" class="easyui-validatebox" /></span> 
					<span class="nameBas"><b>区域名称：</b><input id="regionName" name="regionName" type="text" disabled="disabled" class="easyui-validatebox" data-options="required:true,missingMessage:'区域名称不能为空！'" ></span> <!-- validType:'remote[\'<%=basePath %>RegionManageController/findRegionCode\',\'regionCode\']'" -->
					<span class="nameBas"><b>区域编码：</b><input id="regionCodeStr" name="regionCodeStr" type="text" class="easyui-validatebox" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" disabled="disabled" class="easyui-validatebox" data-options="required:true,missingMessage:'区域编码不能为空！',validType:'remote'"></span> 
					<span class="nameBas"><b>区域简称：</b><input id="regionAbb" name="regionAbb" type="text" disabled="disabled" class="easyui-validatebox"></span>
					<span class="nameBas"><b>拼音全拼：</b><input id="retrievalA" name="retrievalA" type="text" disabled="disabled" class="easyui-validatebox"></span> 
					<span class="nameBas"><b>五笔全拼：</b><input id="retrievalB" name="retrievalB" type="text" disabled="disabled" class="easyui-validatebox"></span>
					<div class="formBut">
						<input id="btnSubmit" class="button" type="button" value="保  存" style="display:none;" onclick="submitClick()">
					</div>
				</form>
		</div>
</body>
</html>
