<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>

</style>
<!--引入CSS-->

<h1 style="margin-left: 10px">${mail.title}</h1>
<div style="margin-left: 10px;"><b>附件：</b>
<c:forEach items="${mail.files}" var="file">
    <a href="#" onclick="downloadFile('${file['FILEID']}','${file['FILENAME']}')">${file['FILENAME']}</a>
</c:forEach>
</div>
<hr/>
<p style="margin-left: 10px">${mail.content}</p>

<!--引入JS-->
<script type="text/javascript">

    function downloadFile(file,filename){
        window.open('${pageContext.request.contextPath}/mail/download/'+filename+'/'+file);
    }


</script>