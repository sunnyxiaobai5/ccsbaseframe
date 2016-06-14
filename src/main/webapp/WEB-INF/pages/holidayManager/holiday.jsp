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
<style>
	.cselected{		/* 选中&未保存数据单元格样式 */
		background:blue;
	}
	.cselected1{	/* 已保存至数据库的数据 */
		background:red;
	}
	.cselected2{	/* 准备删除的数据 */
		background:gray;
	}
	.calendar-header{
		height:30px;
	}
	.calendar-body thead tr{
		height:25px;
	}
</style>
<script>
var Clist = {
		//viewUrl : '<%=basePath%>',//查看URl
	};
var Cparam = {
	monthView:'<%=basePath %>HolidayController/listHolidayByMonth',		//获取月份路径
	saveUpdateURL:'<%=basePath %>HolidayController/saveUpdateHoliday',	//保存或更新路径
	interval:null,//定时器对象，用于监听Calendar月份的改变
	month:null,	//当前calendar月份--一月2014
	dates:[],	//已保存的数据
	sdate:[]	//未保存的数据
};

//给每个日期添加一个单击事件，是否选中
$(document).on("click",".calendar-day",function(){
	var flag = false;
	if($(this).hasClass("cselected")){
		if($(this).hasClass("cselected1")){
			$(this).removeClass("cselected1");
			$(this).addClass("cselected2");
		}else if($(this).hasClass("cselected2")){
			$(this).removeClass("cselected2");
		}else if(!$(this).hasClass("cselected1") && !$(this).hasClass("cselected2")){
			$(this).addClass("cselected1");
		}
	}else{
		if($(this).hasClass("cselected1")){
			$(this).removeClass("cselected1");
		}else{
			$(this).addClass("cselected1");
			flag = true;
		}	
	}
	var item = {};
	var flag = false;
	if($(this).hasClass("cselected1")){
		flag = true;
	}else if($(this).hasClass("cselected2")){
		flag = true;
		item.status = 0;
	}
	var _thisVal = $(this).attr("abbr").split(",");
	for(var i=0;i<Cparam.sdate.length;i++){
		var sd = Cparam.sdate[i].day.split("-");
		if(_thisVal[0]*1==sd[0]*1 && _thisVal[1]*1 == sd[1]*1 && _thisVal[2]*1 == sd[2]*1){
			Cparam.sdate.splice(i,1);
		}
	}
	if(flag){
		item.day = $(this).attr("abbr").replace(/\,/g,"-");
		item.cause = null;
		Cparam.sdate.push(item);
	}
	//console.log(JSON.stringify(Cparam.sdate));
});

	
//阻止日期控件单选
function dispose(date){
	$(".calendar-selected").removeClass("calendar-selected");
}
	
//根据数据将样式绑定到Calendar上
function boundCalendar(){
	$("#calendar").find(".calendar-day").each(function(){
		var abbr = $(this).attr("abbr").split(",");
		for(var i=0;i<Cparam.dates.length;i++){		//绑定添加数据来自服务器
			var day = Cparam.dates[i].day.split("-");
			if(day[0]*1==abbr[0]*1 && day[1]*1==abbr[1]*1 && day[2]*1==abbr[2]*1){
				$(this).addClass("cselected");
			}
		}
		
		for(var i=0;i<Cparam.sdate.length;i++){	//绑定样式，数据来自手选
			var day = Cparam.sdate[i].day.split("-");
			if(day[0]*1==abbr[0]*1 && day[1]*1==abbr[1]*1 && day[2]*1==abbr[2]*1){
				$(this).addClass("cselected1");
			}
		}
	});
		
		//console.log(JSON.stringify(Cparam.dates));
}
//实时监听Canlendar值的改变，刷新数据
function ListenerMonth(){
	var my = $("#calendar").find(".calendar-title>span").text();		//eg:1月 2014
	if(my==null || my=="")return;
	my = my.split(" ");
	var m = my[0].substring(0,my[0].length-1)*1;
	var y = my[1]*1;
	if(Cparam.month!=null){
		var my1 = Cparam.month.split("-");
		var y1 = my1[0]*1;
		var m1 = my1[1]*1;
		if(m==m1 && y==y1){
			return;
		}
	}
	
	Cparam.month = y+"-"+m;
	$("#viewMonth").datagrid({
		url:Cparam.monthView,
		queryParams:{date:Cparam.month+'-1'}
	});
}

//单击保存按钮时触发
function doSave(obj){
	
	var form = $(obj).parents(".form1");
	var value = form.find(".area").val();		//找到文本域对象
	if("节假日原因" == $.trim(value)){
		value = "";
	}
	var flag = false;
	
	var cday = Cparam.month.split("-");
	for(var i=0;i<Cparam.sdate.length;i++){
		var day = Cparam.sdate[i].day.split("-");
		Cparam.sdate[i].cause = value;
		if(day[0]*1==cday[0]*1 && day[1]*1==cday[1]*1 && !flag){
			flag = true;
		}
	}
	for(var i=0;i<Cparam.dates.length;i++){
		var day = Cparam.dates[i].day.split("-");
		if(day[0]*1==cday[0]*1 && day[1]*1==cday[1]*1){
			flag = true;
			break;
		}
	}
	if(!flag){		//如果该月未有选中项，且该月未保存过，作默认值处理
		Cparam.sdate.push({"day":Cparam.month+"-1","status":"2"});
	}
	var param = {"holiday":JSON.stringify(Cparam.sdate)};
	console.log(param);
	$.ajax({
		url:Cparam.saveUpdateURL,
		data:param,
		type:"POST",
		success:function(data){		//操作成功
			console.log(data);
			if(data.status == "success"){
				Cmessager.show({
					title:GV.MESSAGER_TITLE,
					msg:"操作成功！",
					timeout:1500
				});
				Cparam.sdate = [];
				$("#viewMonth").datagrid({
					url:Cparam.monthView
				});	
			}else{
				Cmessager.show({
					title:GV.MESSAGER_TITLE,
					msg:data.message,
					timeout:1500
				});
			}
		},
		error:function(data){		//操作失败
			Cmessager.show({
				title:GV.MESSAGER_TITLE,
				msg:data.message,
				timeout:1500
			});
		}
	});
	form.find(".area").val('');
}

/*表格数据加载完后执行*/
function success(data){
	//初始化数据
	Cparam.dates = [];
	$('.cselected,.cselected1,.cselected2').removeClass('cselected').removeClass('cselected1').removeClass('cselected2');
	if(Cparam.interval!=null){
		clearInterval(Cparam.interval);
	}
	if(Cparam.dates.length==0 && data.rows){
		for(var i=0;i<data.rows.length;i++){
			if(Cparam.month==null){		//获取当前月份
				var d = data.rows[i].day.split('-');
				Cparam.month = d[0]+'-'+d[1];
				$("#calendar").calendar('moveTo',new Date(d[0],d[1]-1,d[2]));	//初始化calendar
				$(".calendar-selected,.calendar-today").removeClass("calendar-selected").removeClass("calendar-today");
			}
			if(data.rows[i].isholiday == '是'){
				var item = {};
				item.day = data.rows[i].day;
				item.cause = data.rows[i].cause;
				Cparam.dates.push(item);
			}
		}
	}
	Cparam.interval = setInterval('ListenerMonth()',50);
	boundCalendar();
}
</script>
</head>
  
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',title:'节假日选择',split:true" style="width:500px;">
		<div id="calendar" class="easyui-calendar" style="width:440px;height:320px;margin:30px auto;"
			data-options="
				months:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
				onSelect:function(date){
			        dispose();		//处理选中时的事件
				}">
		</div>
		<form class="form1">
			<div>
				<table align="center">
					<tr><td style="vertical-align: top" style="text-align:right" width="80px">节假日原因：</td>
					<td>
						<textarea cols="40" class="area" rows="3" name="text" class="easyui-validatebox" data-options="required:true"></textarea>
						<input type="hidden" id="dates" name="dates">
					</td></tr>
					<tr><td colspan="2" style="text-align:center;padding-top: 20px;">
						<b:button opsUrl="/HolidayController/saveUpdateHoliday"><a href="javascript:void(0)" class="easyui-linkbutton addbtn" iconcls="icon-save" onclick="doSave(this)" >保存</a></b:button>
					</td></tr>
				</table>
			</div>
		</form>
	</div>
	<div data-options="region:'center',title:'节假日列表'">
		<table id="viewMonth" class="easyui-datagrid"
			data-options="singleSelect:true,pagination:false,collapsible:true,border:false,
				url:'<%=basePath %>HolidayController/listHolidayByMonth',
				method:'get',
				onLoadSuccess:function(data){
					success(data);
				}
				">
			<thead>
				<tr>
					<th data-options="field:'day',width:100">日期</th>
					<th data-options="field:'cause',width:100">原因</th>
					<th data-options="field:'isholiday',width:100">节假日</th>
				</tr>
			</thead>		
		</table>
	</div>
</body>
</html>
