<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" href="../css/demo.css" type="text/css">
<link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="../js/jquery.ztree.excheck-3.5.js"></script>
<!--
	组织机构信息中保留字段为
		机构名称、
		组织机构代码、
		机构简称、
		机构类别(原机构类型更名)、
		所属区域、
		办公地址、
		联系人、
		联系人手机、
		机构类型(机构、分机构、部门、虚拟)，
		是否有效、
		组织机构位置调整，
		是否默认职位。????
 -->
<div class="popFormBox org-top" style="position: relative;">
   	<form action="${pageContext.request.contextPath}/OrgController/updateOrg" method="post" id="editform">
   			  <input type="hidden" name="token" value="${token }" />
          	  <span class="nameBas"><b>机构名称：</b><input class="easyui-validatebox" type="text" name="orgName" value="${Org.orgName }" data-options="required:true"/></span>
		      <span class="nameBas"><b>组织机构代码：</b><input class="easyui-validatebox" type="text" name="orgCode" value="${Org.orgCode }" data-options="required:true"/></span>
		      <span class="nameBas"><b>机构简称：</b><input class="easyui-validatebox" type="text" name="shortName" value="${Org.shortName }" data-options="required:true"/></span>
	      	  <span class="nameBas"><b>机构类别：</b><input class="easyui-combobox" name="orgType" data-options="width:142,editable:false,url:'${pageContext.request.contextPath}/service/getTypeList?kind=BP',textField:'type',valueField:'type',panelHeight:200,required:true" value="${Org.orgType }"/>
	           </span>
              <span class="nameBas">
                  <b>组织机构类型：</b>
                  <input class="easyui-combobox" style="width:142px;" name="isVirtual" data-options="valueField:'value',textField:'label',data:[{label:'实体组织',value:'0'},{label:'虚拟组织',value:'1'}]" value="${Org.isVirtual }" readonly="true">
                  </input>
	          </span>
              <br />
		      <span class="nameBas"><b>所属区域：</b>
<%--
<input class="easyui-combotree" name="regionCode" data-options="width:142,editable:false,url:'${pageContext.request.contextPath}/findAllRegionCodeCombo',method:'GET',lines:true,value:'${Org.regionCode }',panelHeight:200" />
--%>
		      		<input id="citySel" type="text" readonly value="${regionCodeTemp}" style="width:120px;" onclick="showMenu();" />
		      </span>
		      <span class="nameBas">
                  <b>所属街道</b>
                  <select id="streesTree" class="easyui-combobox" data-options="editable:false,width:142,panelHeight:'auto',onSelect:loadcommunityTree">
                  		<option value="">${streetTemp}</option>
                  </select>
              </span>
              <span class="nameBas">
                  <b>所属社区</b>
                  <select id="communityTree" class="easyui-combobox" data-options="editable:false,width:142,panelHeight:'auto',onSelect:getcommuityid">
                  		<option value="">${communityTemp}</option>
                  </select>
              </span>
		      <span class="nameBas"><b>办公地址：</b><input class="easyui-validatebox" type="text" name="address" value="${Org.address }" /></span>
	          <span class="nameBas"><b>联系人：</b><input class="easyui-validatebox" type="text" name="contact" value="${Org.contact }" /></span>
	          <span class="nameBas"><b>联系电话：</b><input class="easyui-validatebox" type="text" name="phone" value="${Org.phone }" /></span>
	          <span class="nameBas"><b>法人手机：</b><input class="easyui-validatebox" type="text" name="legalMobile" value="${Org.legalMobile }"/></span>
	          <span class="nameBas"><b>法人电话：</b><input class="easyui-validatebox" type="text" name="legalPhone" value="${Org.legalPhone }"/></span>
	      	  <span class="nameBas"><b>是否有效：</b>
	      	  		<select name="status" class="easyui-combobox" data-options="width:142,panelHeight:'auto'">
	                	<option value="1">是</option>
	                	<option value="-1">否</option>
	              	</select>

            </span>
	    	<span class="nameBas" style="width: 400px; padding-left: 20px;">
                <input type="radio"  name="orderCheck"/>同级部门最后位置
                <input type="radio" name="orderCheck" checked="checked" value="2" />位于
                <input id="orderSelect" class="easyui-combobox" name="orderSelect" data-options="editable:false,valueField:'sort',panelHeight:'auto',textField:'orgName',method:'GET',
                url:'${pageContext.request.contextPath}/OrgController/findChildOrgByPid?pid=${Org.parentId}',onChange:orderSelectChange"> 前面
            </span>
	    	<input type="hidden" name="sort" value="${Org.sort}"/>
	    	<input type="hidden" name="oldSort" value="${Org.sort}"/>
	    	<input type="hidden" name="id" value="${Org.id}">
	    	<span class="nameBas" style="width: 400px;">
                <b>移动组织到：</b>
                <input style="width:143px;" class="easyui-combotree" id="parentId" name="parentId" value="${Org.parentId }" panelHeight="200"
                       data-options="parentField:'parentId',textFiled:'orgName',url:'${pageContext.request.contextPath}/OrgController/getOrgzTree',
                       method:'GET',lines:true,onChange:doCombotreeChange,panelWidth:250"/>
	    	    之下</span>
      		<div class="formBut">
              	<b:button opsUrl="/OrgController/updateOrg"><input class="button" type="button" onclick="orgSubmit('editform')" value="保 存" /></b:button>
            </div>
            <input type="hidden" id="regionCode" name="regionCode" value="" />
		  </form>
		  

	<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:170px; height: 300px;background: #f0f6e4;overflow-y:scroll;overflow-x:auto;border: 1px solid #617775;"></ul>
	</div>
</div>
<script type="text/javascript">
	$.fn.extend({
		initZtree:function(conf){
			var ztreeObj = false ;
			var setting = {
					async:{
						enable:true,
						url: conf.url,
						type:'post',
						dataType:'json'
					},
					check: {
						enable: true,
						chkStyle: "radio",
						radioType: "all"
					},
					view: {
						dblClickExpand: false
					},
					data:{
						simpleData:{
							enable:true,
							idKey: conf.id || 'tid',
							pIdKey: conf.parentId || 'pid',
							rootPId: null
						},
						key:{
						   url:'xurl', //屏蔽URL
						   name: conf.text || 'text'
						}
					},
					callback: {
						onClick: conf.onClick,
						onCheck: onCheck
					}
			};
			ztreeObj = $.fn.zTree.init($(this), setting);
			
			return ztreeObj ;
		}
	});
	
    function doCombotreeChange(newValue, oldValue) {
        var pathUrl = "${pageContext.request.contextPath}/OrgController/findChildOrgByPid?pid=" + newValue;
        $('#orderSelect').combobox('reload', pathUrl);

    }
	
    function onCheck(e, treeId, treeNode) {
    	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = zTree.getCheckedNodes(true);
		var cityObj = $("#citySel");
		cityObj.attr("value", nodes[0].text);
		
		var regioncode = treeNode.tid;
		loadStreetTree(regioncode);
	}
	
	function showMenu() {
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
		$("#menuContent").css({left:cityOffset.left - 206 + "px", top:cityOffset.top + cityObj.outerHeight() -42 + "px"}).slideDown("fast");
	
		$("body").bind("mousedown", onBodyDown);
	}
	
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	
	//生成street下拉列表
	function loadStreetTree(regioncode){
		$("#regionCode").val(regioncode);
		$("#streesTree").combobox('loadData',[]).combobox('setValue', '');
		$("#communityTree").combobox('loadData',[]).combobox('setValue', '');
	
		$("#streesTree").combobox({
			url:'<%=basePath%>findAllStreetByRegionCode?regionCode='+regioncode,
			valueField:'tid',
			valueField:'text'	
		});
	}
	
	//生成community下拉列表
	function loadcommunityTree(record){
		$("#regionCode").val(record.tid);
		$("#communityTree").combobox({
			url:'<%=basePath%>findAllCommunityByRegionCode?streetCode='+record.tid,
			valueField:'tid',
			valueField:'text'	
		});
	}
	
	function getcommuityid(record){
		$("#regionCode").val(record.tid);
	}

	$(function(){
		var conf = {
			url:'<%=basePath%>findAllRegionCodeCombo',
			name:'text',
			parentId:'parentId',
			onClick:function(event, treeId, treeNode){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				nodes = zTree.getCheckedNodes(true);
				
				var regioncode = treeNode.tid;
				loadStreetTree(regioncode);
			}
		} ;
		$("#treeDemo").initZtree(conf) ;
		 // 排序
        clickSortRadio();
	});


</script>