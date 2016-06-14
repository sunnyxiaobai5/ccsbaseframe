<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<form action="<%=basePath %>UserManagerController/addUserManagerInfo" method="post" id="searchForm">
    <div id="user" class="easyui-tabs">
        <div class="emTabsCon" title="人员信息">
            <div class="searchFormBox">
                <span class="nameBas"><b>登陆账号：</b><input class="easyui-validatebox" name="loginid" data-options="required:true" /></span>
                <span class="nameBas"><b>人员姓名： </b><input class="easyui-validatebox" name="userName" data-options="required:true"/></span>
				            <span class="nameBas"><b>性别： </b><select class="easyui-combobox" name="gender" data-options="width:150,editable:false,url:'<%=basePath%>service/getTypeList?kind=BR',textField:'type',valueField:'type',panelHeight:200">
                            </select></span>
				            <span class="nameBas"><b>是否启用密钥： </b><select class="easyui-combobox" name="certifykind" panelHeight="auto">
                                <option value="是">是</option>
                                <option value="否">否</option>
                            </select></span>
                <span class="nameBas"><b>密码：</b><input class="easyui-validatebox" name="password" type="password" data-options="required:true"/></span>
				        	<span class="nameBas"><b>人员类型：</b><select class="easyui-combobox" name="usertype" data-options="width:150,editable:false,url:'<%=basePath%>service/getTypeList?kind=BQ',textField:'type',valueField:'type',panelHeight:200">
                            </select></span>
				            <span class="nameBas"><b>证件类型：</b><select class="easyui-combobox" name="idtype" id="idtype" data-options="required:true,width:150,editable:false,url:'<%=basePath%>service/getTypeList?kind=BS',textField:'type',valueField:'type',panelHeight:200,onSelect:doIdtypeSelect">
                            </select></span>
                <span class="nameBas"><b>证件号码：</b><input class="easyui-validatebox" name="ownerid_" id="ownerid_" data-options="required:true,validType:'idcard[\'#idtype\']'" onchange="doOwneridChange()" /></span>
                <span class="nameBas"><b>证件地址：</b><input class="easyui-validatebox" name="certaddr" /></span>
                <span class="nameBas"><b>国籍：</b><input class="easyui-combobox" name="nationality" data-options="width:150,editable:false,url:'<%=basePath%>service/getTypeList?kind=AR',textField:'type',valueField:'type',panelHeight:200"/></span>

                <span class="nameBas"><b>出生年月：</b><input class="easyui-datebox" name="birthdate" id="birthdate" data-options="editable:false" style="width:138px;" /></span>
                <span class="nameBas"><b>联系电话：</b><input class="easyui-validatebox" name="phone" /></span>
                <span class="nameBas"><b>学历：</b><input class="easyui-validatebox" name="knowledge" /></span>
                <span class="nameBas"><b>电子邮件：</b><input class="easyui-validatebox" name="email" data-options="validType:'email'"/></span>
                <span class="nameBas"><b>通讯地址：</b><input class="easyui-validatebox" name="address" /></span>
                <span class="nameBas"><b>传真：</b><input class="easyui-validatebox" name="fax" /></span>
                <span class="nameBas"><b>备注：</b><input class="easyui-validatebox" name="note" /></span>
				        	<span class="nameBas"><b>是否有效： </b><select class="easyui-combobox" name="isactive" panelHeight="auto" value="${userManager.isavtive}">
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select></span>
                <span class="nameBas"><b>职位：</b><input class="easyui-combobox" id="pname" name="pname" data-options="required:true,width:150,editable:false,url:'<%=basePath%>service/getUserPostion?orgid=${orgid}',textField:'pname',valueField:'id',panelHeight:200"/></span>
            </div>
        </div>
        <input type="hidden" id="orgid" name="orgid" value="${orgid}"/>
        <input type="hidden" id="positionid" name="positionid" value=""/>
    </div>
</form>


<script type="text/javascript">
    function doOwneridChange() {
        var idtype = $('#idtype').combobox('getValue');
        if(idtype == '境内居民身份证' && $('#ownerid_').validatebox('isValid')) {
            var ownerid = $('#ownerid_').val();
            var birthday = ownerid.substr(6, (ownerid.length==18?8:6));
            if(birthday.length == 6) birthday = '19'+birthday;
            $('#birthdate').datebox('setValue', birthday.substr(0,4)+'/'+birthday.substr(4,2)+'/'+birthday.substr(6,2));
        }
    }
    function doIdtypeSelect(record) {
        if(record.type=='境内居民身份证') {
            $('#birthdate').datebox('disable');
        } else {
            $('#birthdate').datebox('enable');
        }
    }
    function MybeforeSubmit(){
        /*var positionid = $("#pname").combobox('getValue');
         alert(positionid);
         if(positionid == ""){
         alert(1);
         return false;
         }else{
         $("#positionid").val(positionid);
         }*/
        $("#positionid").val($("#pname").combobox('getValue'));
    }
</script>
			
				