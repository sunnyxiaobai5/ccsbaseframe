<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../js/libs/easyui/easyui.css"/>
<link rel="stylesheet" type="text/css" href="../js/libs/easyui/icon.css"/>
<link rel="stylesheet" href="../css/reeasyui.css" type="text/css" media="screen" />

<script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="../js/func/drogselector.js"></script>
<script type="text/javascript" src="../js/func/jquery-ui.js"></script>
<script type="text/javascript" src="../js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="../js/libs/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/libs/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/func/fun.js"></script>
<script type="text/javascript" src="../js/common.js"></script>
</head>
<body style="padding-top: 0; margin: 0; overflow: hidden;">
<form action="<%=basePath %>FuncController/addDisFuncOps" method="post" id="popForm">
<div class="popFormBox">
	<input type="hidden" name="id" value="${func.id }" />
	<span class="nameBas"><b>功能名称：</b><input class="easyui-validatebox" value="${func.funcName }" name="funcName" readonly="readonly" /></span>
	<span class="nameBas"><b>功能类型：</b><input class="easyui-validatebox" value="${func.funcTypeStr }" name="funcType" readonly="readonly" /></span>
	<span class="nameBas"><b>功能地址：</b><input class="easyui-validatebox" value="${func.funcUrl }" name="funcUrl" readonly="readonly" /></span>
	<span class="nameBas"><b>功能描述：</b><input class="easyui-validatebox" value="${func.description }" name="description" readonly="readonly" /></span>
</div>
<div class="lr-box">
   	<div class="lr-box-title">操作列表</div>
   	<div class="lr-search">
       <span><b>操作名称：</b>
			<input class="easyui-validatebox" name="fun" id="searchltext" />
		</span>
		<input type="button" class="easyui-linkbutton" id="searchl" value="搜索" />
	</div>
  	<div class="lr-tree sortItemContainer"><ul id="sort1" style="height:100%" class="easyui-tree" data-options="url:'<%=basePath %>OpsController/findOpsTreeList',method:'get',animate:true,checkbox:false"></ul></div>
</div>
<div class="lr-box">
   	<div class="lr-box-title">已分配列表</div>
   	<div class="lr-search">
       	<span><b>操作名称：</b>
			<input class="easyui-validatebox" name="fun" id="searchrtext" />
		</span>
        <input type="button" class="easyui-linkbutton" id="searchr" value="搜索" />
    </div>
   	<div class="lr-tree sortItemContainer"><ul id="sort2" style="height:100%" class="easyui-tree" data-options="url:'<%=basePath %>OpsController/findOpsTreeList?funcId=${ func.id}',method:'get',animate:true,checkbox:false"></ul></div>
	<input type="hidden" id="opsIds" name="opsIds"/>
</div>
<div class="iframeBut">
	<a href="javascript:void(0)" class="l-btn" onclick="doSubmit()"><span class="l-btn-left"><span class="l-btn-text">提交</span></span></a>
	<a href="javascript:void(0)" class="l-btn closeIframe" onclick="doClose()"><span class="l-btn-left"><span class="l-btn-text">关闭</span></span></a>
</div>
</form>
<script type="text/javascript">
/*搜索start*/
$(function(){
	/*单击按钮触发查询*/
	$("#searchl").click(function(){
		$("#searchltext").search("#sort2");
	});
	$("#searchr").click(function(){
		$("#searchrtext").search("#sort2");
	});
	/*回车触发查询*/
	$("#searchltext").keydown(function(){
		if(event.keyCode==13){
			$("#searchltext").search("#sort1");
		}
	});
	$("#searchrtext").keydown(function(){
		if(event.keyCode==13){
			$("#searchrtext").search("#sort2");
		}
	});
});

(function($){
	$.fn.extend({
		search:function(selector){
			var searchVal = $.trim($(this).val());
			if(searchVal==null || searchVal==""){		/*如果输入参数为空，则显示全部*/
				$(selector).children("li").show();
				console.log(searchVal+"--");
				return;
			}
			console.log(searchVal+"##");
			var items = $(selector).children("li");	
			for(var i=0;i<items.length;i++){
				
				var curVal = $(items[i]).find(".tree-title").text();
				if(curVal.indexOf(searchVal)>-1){	//执行模糊查询，显示命中项，反之隐藏
					$(items[i]).show();
				}else{
					$(items[i]).hide();
				}
		}
	}
	});
})(jQuery);
/*搜索end*/
function doClose(){
	top.$('#funcDialog').dialog('close');
}
function doSubmit() {
	$('#popForm').form('submit',{
		dataType : 'json',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(result){
			if(typeof result == 'string') {
				//if(result.indexOf('{') !== 0) {
				//	Cmessager.alert(result);
				//	return false;
				//}
				result = eval('('+result+')');
			}

			if(result.callback) {
				eval(result.callback+'()');
			}
			if (result.status == 'success'){
				top.$.messager.show({
					title:GV.MESSAGER_TITLE,
					msg:result.message,
					timeout:1500
				});
				top.$('#funcDialog').dialog('close');
				//if(_winId!=undefined && _winId) top.$('#'+_winId).dialog('close'); //关闭弹出层
				if(result.closeTab == true) {
					var tab = top.$('#tt').tabs('getSelected');  // 获取选择的面板
					top.$('#tt').tabs('close', top.$('#tt').tabs('getTabIndex', tab));
				} else if(result.reloadTab == true) { //刷新tab
					var tab = top.$('#tt').tabs('getSelected');  // 获取选择的面板
					tab.panel('refresh');
				}
			} else {
				top.$.messager.alert(GV.MESSAGER_TITLE, result.message, 'error');
			}
		}
	});
}
</script>
</body>
</html>