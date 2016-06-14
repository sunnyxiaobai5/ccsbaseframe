<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="easyui-layout" data-options="fit:true">
<form id="myform" action="<%=basePath %>InformationController/addInformation" method="POST" region="center">
    <div data-options="region:'north',title:'',split:false" style="height:30px;padding-top: 2px;border:none">
    
    	<span class="nameBas"><b>&nbsp;&nbsp;&nbsp;&nbsp;公告类型： </b>
    			<select class="easyui-combobox" id="infotype" name="infotype" panelHeight="auto">
	                <option value="公告" selected="selected">公告</option>
				    <option value="提示">提示</option>
				    <option value="工具">工具</option>
	            </select>
         </span>
         &nbsp;&nbsp;
         <span class="nameBas"><b id="showtitle">公告标题：</b><input class="easyui-validatebox" id="infotitle" data-options="required:true,validType:'maxLength[32]'" name="infotitle" style="width:250px"/></span>
         &nbsp;&nbsp;
         <span id="filespan"  class="nameBas"><b>添加附件：</b><a href="javascript:void(0)" class="easyui-linkbutton" id="pickfiles">上传附件</a> 文件大小请控制在50M之类</span>
         &nbsp;&nbsp;
         <span id="toolspan" class="nameBas" style="display:none"><b>工具URL：</b><input class="easyui-validatebox" id="toolurl" name="toolurl" style="width:200px" value="http://"/></span>
   		 <input type="hidden" name="appids" id="appids"/>
    </div>  
    <div data-options="region:'center',split:false,border:false">
       	<div class="easyui-layout" data-options="fit:true,border:false">
       		<div data-options="region:'east',title:'系统发送',split:true" style="width:260px">
	       		<table id="appdg"  class="easyui-datagrid" data-options="url:'<%=basePath%>InformationController/getAppList',pagination:false,border:false,checkOnSelect:true,onCheck:dgOnCheck,onUncheck:dgOnUncheck,onCheckAll:dgOnCheckAll,onUncheckAll:dgOnUncheckAll,selectOnCheck:true,singleSelect:false" style="padding-top:0">   
				    <thead>   
				        <tr>
				        	<th data-options="checkbox:true"></th>
				            <th data-options="field:'sysname'">系统名称</th>   
				            <th data-options="field:'alias'">系统标识</th>   
				            <th data-options="field:'description'">系统描述</th>  
				        </tr>   
				    </thead>   
				</table>  
       		</div>   
    		<div id="contenttitle" data-options="region:'center',title:'公告内容',border:false">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center',border:false">
						<textarea  name="infocontent">
						</textarea>
						<script type="text/javascript" src="../js/libs/ckeditor/ckeditor.js"></script>
						<script type="text/javascript">
							CKEDITOR.replace( 'infocontent', {toolbar:'basic'});
						</script>
					</div>
					<div data-options="region:'south',title:'附件',split:true" style="height:150px;">
						<table id="filelists" class="easyui-datagrid" data-options="pagination:false,border:false">   
							<thead>
								<tr>   
									<th data-options="field:'filename',width:20">文件名</th>   
									<th data-options="field:'fileformat',width:20">文件类型</th>   
									<th data-options="field:'filesize',width:20">文件大小</th> 
									<th data-options="field:'true',align:'center',formatter:rowFormaterFile">操作</th>
								</tr>
							</thead>  
						</table>
					</div>
				</div>
    		</div>
			<input type="hidden" name="formid" id="formid" value="" />
			<input type="hidden" name="fileids" id="fileids" value="" />
       	</div>
    </div>
    </form>
</div>
<script type="text/javascript" src="../js/libs/plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="../js/pages/informationManager/page.js"></script>