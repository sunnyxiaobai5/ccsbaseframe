<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
		<form action="<%=basePath %>UserManagerController/saveUserOrg" method="post" id="searchForm">
  			<div class="orgName">当前组织机构：${orgName}</div>
       		<div data-options="region:'west',title:'组织机构',split:true" style="width:300px;">
				<ul id="orgpositionTree" class="ztree"></ul>
			</div>  
		    <input type="hidden" id="userid" name="userid" value="${userid}"/>
		    <input type="hidden" id="orgid" name="orgid" value=""/>
		    <input type="hidden" id="postid" name="postid" value=""/>
		</form>
		
<script type="text/javascript">
		//保存
		function MybeforeSubmit(){
			var postid = $("#postid").val();
			var orgid = $("#orgid").val();
			if(postid == "" && orgid == ""){
				$.messager.show({
					title:'警告',
					msg:'请选择相应的职位',
					timeout:3000,
					showType:'slide'
				});
				return false;
			}
		}

		//组织机构职位树配置
		/** jquery-ztree封装树**/
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
						   url:'xurl', 
						   name: conf.name || 'text'
						}
					},
					check:{
						enable: conf.check || true,
						chkStyle:"checkbox",
						autoCheckTrigger: true,
						chkboxType: conf.chkboxType || { "Y": "", "N": "" }
					},
					callback:{
						onClick:conf.onClick,
						onAsyncSuccess:conf.onAsyncSuccess
					}
				};
				ztreeObj = $.fn.zTree.init($(this), setting);
				return ztreeObj ;
			}
		});
		
		$(function(){
			var conf = {
				url:'<%=basePath%>UserManagerController/queryOrgAndPosition' ,
				name:'text',
				parentId:'parentId',
				onClick:function(event, treeId, treeNode){
					var id = treeNode.id;
					var pid = treeNode.parentId;
					var flag = treeNode.flag;
					var type = "";
					if(flag == "O"){
						type = "组织机构";
					}
					if(flag == "P"){
						type = "职位";
					}
					
					if(flag == "P"){
						$("#postid").val(id);
						$("#orgid").val(pid);
					}
				}
			} ;
			$("#orgpositionTree").initZtree(conf);
		});

</script>
			
				