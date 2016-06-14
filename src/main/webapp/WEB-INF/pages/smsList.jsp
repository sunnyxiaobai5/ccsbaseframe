<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<script type="text/javascript">
var smsId;
function open(val,row,index) {
	return '<a href="javascript:void(0);" onclick="send('+ index +')">受理</a>';
	//return '<button onclick="send('+ index +')">授理</button>';
}

function send(index){
	var rows = $("#dg").datagrid("getRows");
	var datas = rows[index];
	
	var url = datas.url;	//url地址
	smsId = datas.id;	//短信id
	var bid = datas.bid;	//业务主键
	var cateGory = datas.cateGory;//业务类型
	var tableId = datas.tableId;//房屋唯一号
	
	$.ajax({
		url:"<%=basePath %>/sms/checkSmsStatus",
		type:"post",
		data:{"smsId":smsId},
		dataType:"json",
		success:function(info){
			if(info ==  1){
				//openDialog("title:'短信',url:'"+ url +"?bid="+ bid +"&cateGory="+ cateGory +"&tableId="+ tableId +"',width:960,height:420,buttons:false,isIframe:true");
				openDialog("title:'短信',winId:'winId',url:'"+ url +"?bid="+ bid +"&cateGory="+ cateGory +"&tableId="+ tableId +"',width:960,height:620,buttons:[{text:'处理',handler:function(){update()}}],isIframe:true");//加处理按钮
			}else{
				$.messager.show({
					title:'信息提示',
					msg:'该短信已经处理过了！',
					timeout:0,
					showType:'slide'
				});
			}
		}	
	});
}

/*改短信status*/
function update(){
	GV.top$("#winId").dialog('close');
	$.ajax({
		url:"<%=basePath %>/sms/updateSMSStatus",
		type:"post",
		data:{"smsId":smsId},
		dataType:"json",
		success:function(info) {
			if(info == 0){
				$.messager.show({
					title:'信息提示',
					msg:'处理完成！',
					timeout:1000,
					showType:'slide'
				});
				setTimeout('flush()',1400);//延迟刷新
			}else{
				$.messager.show({
					title:'信息提示',
					msg:'处理失败！',
					timeout:3000,
					showType:'slide'
				});
			}
		}
	});
}
/*刷新*/
function flush(){
	window.location.reload();
	parent.location.reload();
}

</script>

</head>
<body>
	<table class="easyui-datagrid" id="dg" data-options="url:'<%=basePath %>sms/getSMSList/-1',border:false,method:'get',pagination:true,layout:['first','links','last']">  
    	<thead>   
	        <tr> 
	            <th data-options="field:'loginId',width:20">用户ID</th>
	            <th data-options="field:'bid',width:20">业务主键ID</th>
	            <th data-options="field:'cateGory',width:20">业务类型</th>
	            <th data-options="field:'phone',width:20">短信发送目标电话</th>
	            <th data-options="field:'issend',width:20">是否发送</th>
	            <th data-options="field:'sendTime',width:20">发送时间</th>
	            <th data-options="field:'systemtype',width:20">系统名称</th>
	            <th data-options="field:'smsContent',width:20">短信内容</th>
				<th data-options="field:'url',align:'center',formatter:open">操作</th>   
	        </tr>   
	    </thead> 
	</table>
</body>
</html>