<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower" %>
<div id="paraToolBar" style="padding:3px; height: auto; overflow:hidden;">
    <span style="float:left;">
        <b:button
                opsUrl="/api/para/add">
            <a class="easyui-linkbutton" onclick="doAdd(this)" plain="true" href="#"
               iconcls="icon-add">新增</a>
        </b:button>
    </span>
    <span style="float:right;">
    </span>
</div>
<table id="paraGrid" class="easyui-datagrid" data-options="
		 url:'${pageContext.request.contextPath}/report/reportSet/para/list',
	    singleSelect:true,
	    toolbar: '#paraToolBar',
	    striped:true,
	    fitColumns:true,
	    queryParams:{reportId:${reportId}}
	">
    <thead>
    <tr>
        <th data-options="field:'name',width:25">名称</th>
        <th data-options="field:'paraName',width:25">参数名</th>
        <th data-options="field:'true',align:'center',formatter:rowFormater">操作</th>
    </tr>
    </thead>
</table>
<script type="text/javascript">
    $(function(){
        Clist.paraGrid.addUrl='${pageContext.request.contextPath}/report/reportSet/para/add?id='+ ${reportId};
    });


</script>
