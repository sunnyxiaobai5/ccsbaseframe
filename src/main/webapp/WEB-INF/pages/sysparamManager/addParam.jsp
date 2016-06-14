<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<div class="popFormBox">
	<form action="<%=basePath %>SysParamController/addSysParam" method="post" id="popForm">
		<span class="nameBas"> <b>参数名称： </b>
			<input maxlength="64" class="easyui-validatebox" name="paramname" value="${sysParam.paramname}" 
				data-options="required:true" />
		</span>
		<span class="nameBas"> <b>所属系统：</b>
			<input type="text" class="easyui-combobox" name="sysid" panelHeight="auto" 
				data-options="editable:false,required:true,url:'<%=basePath %>SysParamController/getLogiSystem',
					valueField:'id',textField:'sysName',width:140,value:'${sysParam.sysid}'" />
		</span>
		
		<!--#############modify###############-->
		<span class="nameBas"> <b>参数类型： </b>
			<select id="cbbType" name="paramtype" class="easyui-combobox" panelHeight="auto" contenteditable="false"
				data-options="required:true">
				<option value="基本">基本</option>
				<option value="功能控制">功能控制</option>
			</select>
		</span>
		<span class="nameBas"> <b>参 数 值：</b>
			<input id="param1"  maxlength="512" class="easyui-validatebox" data-options="required:true"/>
			<select id="param2" name="paramvalue"  class="easyui-combobox" panelHeight="auto" data-options="editable:false" >
				<option value="1">是</option>
				<option value="2">否</option>
			</select>
		</span>
		<input type="hidden" name="id" value="${sysParam.id}"/>
		<input type="hidden" name="status" value="${sysParam.status}"/>
		<input type="hidden" name="sysinsttime" value="${sysParam.sysinsttime}"/>
		<input type="hidden" name="sysedittime" value="${sysParam.sysedittime}"/>
		<input type="hidden" name="sysdeltime" value="${sysParam.sysdeltime}"/>
	</form>
</div >
<script>
$("#cbbType").combobox({
	onSelect:function(record){
		hideSelect(record.value);
	},
	onLoadSuccess:function(){
		var txt = "${sysParam.paramtype }";
		if(txt==null || txt==""){
			txt = "基本";	//默认为"基本"
		}else{
			var value = "${sysParam.paramvalue }";
			if(txt !="功能控制"){
				$("#param1").val(value);
			}else{
				$("#param2").combobox();
				$("#param2").combobox("select",value);
				if(value=="否"){
					$("#param2").combobox("options").value = "2";
				}else if(value=="是"){
					$("#param2").combobox("options").value = "1";
				}
				
			}
		}
		$("#cbbType").combobox('setValue',txt);
		$("#cbbType").combobox('options').value = txt;
		hideSelect(txt);
	},onChange:function(newValue,oldValue){
		newValue = $.trim(newValue);
		if(newValue!="" || newValue !=null){
			hideSelect(newValue);
		}
	}
});
function hideSelect(type){
		type = $.trim(type);
		var $param1 = $("#param1");
		var $param2 = $("#param2");
		$("[name='paramvalue']").removeAttr("name");
		if(type=="功能控制"){		//只有等于功能控制时才显示"是/否"下拉框
			$param1.validatebox().validatebox("disableValidation");
			$param1.hide();
			showcom($param2);
		}else{
			$param1.attr("name","paramvalue");
			$param1.validatebox().validatebox("enableValidation");
			$param1.show();
			hidecom($param2);
		}
		
}
$("#param2").combobox({
	onSelect:function(){
		$(this).next(".combo").find(".combo-value").attr("name","paramvalue");
	}
});
function showcom(combox){
	var box = $(combox).next(".combo");
	if(box[0]){
		box.show();
		box.find(".combo-value").attr("name","paramvalue");
	}else{
		$(combox).combobox({onLoadSuccess:function(){
			$(this).next(".combo").find(".combo-value").attr("name","paramvalue");
		}});
	}
}
function hidecom(combox){
	var box = $(combox).next(".combo");
	if(box[0]){
		$(box).hide();
	}else{
		$(combox).combobox({onLoadSuccess:function(){
			$(this).next().hide();
		}});
	}
}
</script>