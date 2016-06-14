<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div class="easyui-layout" data-options="fit:true">
    <form action="<%=basePath%>RoleController/save" id="addRoleForm" name="addForm" method="post">
	    <div data-options="region:'west',split:true,title:'角色信息'" style="width:275px;">
	    	<div class="popFormBox">
			      <span class="nameBas" style="width:240px;"><b>角色名称：</b><input style="width:110px;" class="easyui-validatebox" data-options="required:true,validType:'maxLength[8]'" type="text"  name="rName" /></span><br/><br/>
			      <span class="nameBas" style="width:240px;"><b>角色描述：</b><input style="width:110px;" class="easyui-validatebox" data-options="required:true,validType:'maxLength[64]'" type="text"  name="remark" /></span><br/><br/>
		    </div>
	    </div>
	    <div data-options="region:'center'" style="background:#eee;">
	    	<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'west',title:'组织机构',split:true" style="width:275px;" id="treeBoxMan1">
					<ul id="orgZtree" class="ztree"></ul>
					<div class="treeLoadingBox1"></div>
				</div>
				<div data-options="region:'center',title:'权限列表'" id="treeBoxMan2">
					<ul id="sysZtree" class="ztree"></ul>
					<div class="treeLoadingBox2"></div>
				</div>
			</div>
	    </div>
	    <input type="hidden" id="orgId" name="orgId" value=""/>
    	<input type="hidden" id="sys" name="sys" value=""/>
    </form>
    
</div>
<link rel="stylesheet" href="../css/demo.css" type="text/css">
<link rel="stylesheet" href="../css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="../js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="../js/jquery.ztree.excheck-3.5.js"></script>

<script type="text/javascript" >
		//提交
		function MybeforeSubmit(){
			var orgTree = $.fn.zTree.getZTreeObj("orgZtree");
			var orgNodes = orgTree.getCheckedNodes(true);
			//判断组织机构
			if(orgNodes.length > 0){
				$("#orgId").val(orgNodes[0].id);
				var sysTree = $.fn.zTree.getZTreeObj("sysZtree");
				var sysNodes = sysTree.getCheckedNodes(true);
				var list = new Array;
				if(sysNodes.length > 0) {
					for(var i in sysNodes) {
						var obj = new Object;
						obj.id = sysNodes[i].id;
						obj.tid = sysNodes[i].tid;
						obj.text = sysNodes[i].text;
						obj.parentId = sysNodes[i].parentId;
						obj.nodepid = sysNodes[i].nodepid;
						obj.nodelevel = sysNodes[i].nodelevel;
						
						list.push(obj);
					}
				}
				var sys = JSON.stringify(list);
				$("#sys").val(sys);
				
			}else{
				Cmessager.show({
					title:'提示',
					msg:'请分配组织机构',
					timeout:3000,
					showType:'slide'
				});
			}
		}
		
		//组织机构树配置
		/** jquery-ztree封装树**/
		$.fn.extend({
			initZtree1:function(conf){
				var ztreeObj = false ;
				var setting = {
					async:{
						enable:true,
						url: conf.url,
						type:'post',
						dataType:'json'
					},
					view:{  
						showIcon:true,
						showLine:true,
						expandSpeed:'normal',
						txtSelectedEnable:true
					},
					data:{
						simpleData:{
							enable:true,
							idKey: conf.id || 'id',
							pIdKey: conf.parentId || 'pid',
							rootPId: null,
						},
						key:{
						   url:'xurl', //屏蔽URL
						   name: conf.name || 'text'
						}
					},
					check:{
						enable: conf.check || true,
						chkStyle:"radio",
						autoCheckTrigger: true,
						radioType : "all"
					},
					callback:{
						onClick:conf.onClick,
						beforeAsync:zTreeBeforeAsync1,
						onAsyncSuccess:zTreeOnAsyncSuccess1
					}
				};
				ztreeObj = $.fn.zTree.init($(this), setting);
				return ztreeObj ;
			}
		});
		
		function zTreeOnAsyncSuccess1(treeId, treeNode){
			$(".treeLoadingBox1").remove();
		}

		function zTreeBeforeAsync1(treeId, treeNode) {
			setTimeout('treeBoxSize1()', 0);
		}
			
		function treeBoxSize1() {
			var treeBoxH = $("#treeBoxMan1").height();
			var treeBoxW = $("#treeBoxMan1").width();
			$(".treeLoadingBox1").css({"height":treeBoxH,"width":treeBoxW,"position":"absolute","z-index":"999","background":"url('${pageContext.request.contextPath}/images/loadingBg.png')","top":"29px"});
			$(".treeLoadingBox1").html("<img src='${pageContext.request.contextPath}/images/ajax-loader.gif' />");
			$(".treeLoadingBox1 img").css({"position":"absolute","top":"50%","left":"50%","margin-top":"-16px","margin-left":"-16px"});
		}
		
		//权限树配置
		/** jquery-ztree封装树**/
		$.fn.extend({
			initZtree2:function(conf){
				var ztreeObj = false ;
				var setting = {
					async:{
						enable:true,
						url: conf.url,
						type:'post',
						dataType:'json'
					},
					view:{  
						showIcon:true,
						showLine:true,
						expandSpeed:'normal',
						txtSelectedEnable:true
					},
					data:{
						simpleData:{
							enable:true,
							idKey: conf.id || 'tid',
							pIdKey: conf.parentId || 'pid',
							rootPId: null,
						},
						key:{
						   url:'xurl', //屏蔽URL
						   name: conf.name || 'text'
						}
					},
					check:{
						enable: conf.check || true,
						chkStyle:"checkbox",
						autoCheckTrigger: true,
						chkboxType: conf.chkboxType || { "Y": "ps", "N": "ps" }
					},
					callback:{
						onClick:conf.onClick,
						beforeAsync:zTreeBeforeAsync2,
						onAsyncSuccess:zTreeOnAsyncSuccess2
					}
				};
				ztreeObj = $.fn.zTree.init($(this), setting);
				return ztreeObj ;
			}
		});
		
		function zTreeOnAsyncSuccess2(treeId, treeNode){
			$(".treeLoadingBox2").remove();
		}

		function zTreeBeforeAsync2(treeId, treeNode) {
			setTimeout('treeBoxSize2()', 0);
		}
			
		function treeBoxSize2() {
			var treeBoxH = $("#treeBoxMan2").height();
			var treeBoxW = $("#treeBoxMan2").width();
			$(".treeLoadingBox2").css({"height":treeBoxH,"width":treeBoxW,"position":"absolute","z-index":"999","background":"url('${pageContext.request.contextPath}/images/loadingBg.png')","top":"29px"});
			$(".treeLoadingBox2").html("<img src='${pageContext.request.contextPath}/images/ajax-loader.gif' />");
			$(".treeLoadingBox2 img").css({"position":"absolute","top":"50%","left":"50%","margin-top":"-16px","margin-left":"-16px"});
		}
		
		$(function(){
			//组织机构树
			var conf1 = {
				url:'<%=basePath%>OrgController/getOrgzTree' ,
				name:'orgName',
				parentId:'parentId',
				onClick:function(event, treeId, treeNode){
					
				}
			} ;
			$("#orgZtree").initZtree1(conf1) ;
			
			//系统权限树 
			var conf2 = {
					url:'<%=basePath%>SysController/getSysTree' ,
					name:'text',
					parentId:'parentId',
					onClick:function(event, treeId, treeNode){
						
					}
				} ;
				$("#sysZtree").initZtree2(conf2) ;
		});
		
</script>











