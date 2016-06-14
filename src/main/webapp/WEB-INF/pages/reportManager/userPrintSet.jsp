<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div class="popFormBox nameBasBig">
    <form action="<c:if test="${opt eq 'add'}">${pageContext.request.contextPath}/report/userPrintSet/add</c:if>
    <c:if test="${opt eq 'update'}">${pageContext.request.contextPath}/report/userPrintSet/update</c:if>" method="post" id="popForm">
        <input type="hidden" id="id" name="id" value="${userPrintSet.ID}" />
        <input type="hidden" id="printerHidden" value="${userPrintSet.PRINTER}" />
		<span class="nameBas"> <b>报表类型：</b>
			<input type="text" name="rtype" class="easyui-validatebox" value="${userPrintSet.RTYPE}" disabled="disabled"
                   data-options="required:true,disabled:true"/>
		</span>

		<span class="nameBas"> <b>报表名称：</b>
            <input type="text" name="rname" class="easyui-validatebox" value="${userPrintSet.RNAME}" disabled="disabled"
                   data-options="required:true,disabled:true"/>
		</span>

        <span class="nameBas"> <b>是否预览：</b>
            <select name="isPreview" class="easyui-combobox" style="width: 260px;" data-options="editable:false">
                <option value="1" <c:if test="${userPrintSet.ISPREVIEW eq 1}">selected="selected"</c:if>>是</option>
                <option value="0" <c:if test="${userPrintSet.ISPREVIEW eq 0}">selected="selected" </c:if>>否</option>
            </select>
		</span>
		<span class="msgBox"><b class="msgBoxB">*</b>
		</span>

        <span class="nameBas"> <b>打印机：</b>
            <select id="printer" name="printer" class="easyui-combobox" style="width: 260px;" data-options="editable:false">

            </select>
		</span>
		<span class="msgBox"><b class="msgBoxB">*</b>
		</span>
    </form>
</div>

<script language="javascript" type="text/javascript">


    $(function(){
        $('#printer').append('<option value="-1">默认：'+getPrinterName(-1)+'</option>');
        var number = getPrinterCount();
        for (var i=0;i<number;i++)
        {
            var option = '<option value="'+i+'"';
            if($('#printerHidden').val() == i){
                option += 'selected="selected"'
            }
            option+='>'+getPrinterName(i)+'</option>';
            console.log(option);

            $('#printer').append(option);
        }
    });

</script>
