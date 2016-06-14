<%@ page language="java" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="b" uri="http://www.asia-home.com.cn/buttonPower" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
<div class="popFormBox org-top">
    <form action="${pageContext.request.contextPath}/OrgController/addOrg" method="post" id="addform">
        <input type="hidden" name="token" value="${token }"/>
              <span class="nameBas">
                  <b>上级机构：</b>
                  <label>${parent.orgName}</label>
              </span><br/>
          	  <span class="nameBas">
                  <b>机构名称：</b>
                  <input class="easyui-validatebox" type="text" data-options="required:true" name="orgName"/>
              </span>
		      <span class="nameBas">
                  <b>组织机构代码：</b>
                  <input class="easyui-validatebox" type="text" name="orgCode"/>
              </span>
		      <span class="nameBas">
                  <b>机构简称：</b>
                  <input class="easyui-validatebox" type="text" name="shortName" data-options="required:true"/>
              </span>
	      	  <span class="nameBas">
                  <b>机构类别：</b>
                  <input class="easyui-combobox" style="width:170px;" name="orgType"
                         data-options="width:142,editable:false,url:'${pageContext.request.contextPath}/service/getTypeList?kind=BP',textField:'type',valueField:'type',panelHeight:200,required:true"/>
	          </span>
              <span class="nameBas">
                  <b>组织机构类型：</b>
                  <input class="easyui-combobox" style="width:142px;" name="isVirtual" data-options="valueField:'value',textField:'label',data:[{label:'实体组织',value:'0'},{label:'虚拟组织',value:'1'}]">
                  </input>
	          </span>
              <br/>
		      <span class="nameBas">
                  <b>所属区域：</b>
<%--
                  <input class="easyui-combotree" name="regionCode" 
						data-options="width:142,editable:false,url:'${pageContext.request.contextPath}/findAllRegionCodeCombo',
						method:'GET',lines:true,panelHeight:200,required:true"/>
--%>
           	  <input id="citySel" style="width:142px" type="text" readonly value="" style="width:120px;" onclick="showMenu()" />
              </span>
              <span class="nameBas">
                  <b>所属街道</b>
                  <select id="streesTree" class="easyui-combobox" data-options="editable:false,width:142,panelHeight:'auto',onSelect:loadcommunityTree">
                  </select>
              </span>
              <span class="nameBas">
                  <b>所属社区</b>
                  <select id="communityTree" class="easyui-combobox" data-options="editable:false,width:142,panelHeight:'auto',onSelect:getcommuityid"></select>
              </span>
		      <span class="nameBas">
                  <b>办公地址：</b>
                  <input class="easyui-validatebox" type="text" name="address"/>
              </span>
	          <span class="nameBas">
                  <b>联系人：</b>
                  <input class="easyui-validatebox" type="text" name="contact"/>
              </span>
	          <span class="nameBas">
                  <b>联系电话：</b>
                  <input class="easyui-validatebox" type="text" name="phone"/>
              </span>
	          <span class="nameBas">
                  <b>法人手机：</b>
                  <input class="easyui-validatebox" type="text" name="legalMobile"/>
              </span>
	          <span class="nameBas">
                  <b>法人电话：</b>
                  <input class="easyui-validatebox" type="text" name="legalPhone"/>
              </span>
	      	  <span class="nameBas"><b>是否有效：</b>
		      	  	<select name="status" class="easyui-combobox" data-options="width:142,panelHeight:'auto'">
                        <option value="1">是</option>
                        <option value="-1">否</option>
                    </select>
	          </span>
	    	<span class="nameBas" style="width: 400px; padding-left: 20px;">
                <input type="radio" checked="checked" name="orderCheck"/>同级部门最后位置
                <input type="radio" name="orderCheck" value="2"/>位于
                <select name="orderSelect" class="easyui-combobox" data-options="editable:false,width:142,panelHeight:'auto',onChange:orderSelectChange">
                    <c:forEach items="${childList}" var="o">
                        <option value="${o.id}">${o.orgName}</option>
                    </c:forEach>
                </select>
                前面
	    	</span>
        <input type="hidden" name="sort" value="${sort }"/>
        <input type="hidden" name="oldSort" value="${sort }"/>
        <input type="hidden" name="parentId" value="${parentId }" id="parentId"/>
        <input type="hidden" id="regionCode" name="regionCode" value="" />

        <div class="formBut">
            <b:button opsUrl="/OrgController/addOrg">
                <input class="button" type="button" onclick="orgSubmit('addform')" value="保 存"/>
            </b:button>
        </div>
    </form>
    
    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top:0; width:180px; height: 300px;background: #f0f6e4;overflow-y:scroll;overflow-x:auto;border: 1px solid #617775;"></ul>
	</div>
</div>

<script>
	var ztreeObj = false ;
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
		$("#menuContent").css({left:cityOffset.left - 206 + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	
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
				var regioncode = treeNode.tid;
				
				loadStreetTree(regioncode);
			}
		} ;
		ztreeObj = $("#treeDemo").initZtree(conf) ;
		 // 排序
        clickSortRadio();
	});
</script>