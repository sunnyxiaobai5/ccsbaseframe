package cn.ccsgroup.ccsframework.userManager.controller;

import cn.ccsgroup.ccsframework.annotation.PrivilegeAnnotation;
import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.*;
import cn.ccsgroup.ccsframework.role.service.RoleManagerService;
import cn.ccsgroup.ccsframework.tree.service.TreeService;
import cn.ccsgroup.ccsframework.userManager.service.OrgService;
import cn.ccsgroup.ccsframework.userManager.service.UserManagerService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.usermanager.controller.UserManagerController.java]  
 * @ClassName:    [UserManagerController]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [zengheng]   
 * @CreateDate:   [2013-12-10 下午02:54:50]   
 * @UpdateUser:   [zengheng(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-10 下午02:54:50，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@Controller
@RequestMapping("/UserManagerController")
@Transactional
public class UserManagerController extends BaseController {

	@Resource(name="UserManagerService")
	private UserManagerService userManagerService;

	@Resource(name="OrgService")
	private OrgService orgService;
	
	@Resource(name="treeService")
	private TreeService treeService;

    @Resource(name = "roleManagerService")
    private RoleManagerService roleManagerService;
	
	@RequestMapping(value="/viewUserManager")
	public String viewPostion(){
		return "userManager/userManager";
	}
	
	@PrivilegeAnnotation()//进行拦截
	@RequestMapping(value="/create")
	public ModelAndView getAddUserManager(@RequestParam(value="orgid")Integer orgid){
		ModelMap map = new ModelMap();
		map.put("orgid", orgid);
		return new ModelAndView("userManager/addUserManager",map);
	}
	
	@RequestMapping(value="/change")
	public ModelAndView getChangeUserOrg(@RequestParam(value="userid")Integer userid){
		ModelMap map = new ModelMap();
		map.put("userid", userid);
		String orgName = userManagerService.getOrgNameByUserid(userid);
		map.put("orgName", orgName);
		
		return new ModelAndView("userManager/changeUserOrg",map);
	}
	
	@RequestMapping(value="/update")
	public ModelAndView getUpdateUserManager(@RequestParam(value="id")Integer id,@RequestParam(value="orgid",required=false)Integer orgid){
		ModelMap map = new ModelMap();
		if(orgid == null){
			orgid = userManagerService.getUserOrgid(id);
		}
		Integer postionid = userManagerService.getUserPostionByUId(id);
		UserManager userManager = userManagerService.initUserManager(id);
		
		map.put("userManager", userManager);
		map.put("orgid", orgid);
		map.put("postionid", postionid);
		return new ModelAndView("userManager/userManagerDetail",map);
	}
	
	@RequestMapping(value="/show")
	public ModelAndView getShowUserManager(@RequestParam(value="id")Integer id){
		ModelMap map = new ModelMap();
		UserManager userManager = userManagerService.initUserManager(id);
		Integer orgid = userManagerService.getUserOrgid(id);
		Org org = orgService.selectOrgById(orgid);
		map.put("orgid", orgid);
		map.put("Org", org);
		map.put("userManager", userManager);
		return new ModelAndView("userManager/showUserManager",map);
	}
	
	
	
	@RequestMapping(value="/getUserManagerList",method=RequestMethod.POST)
	public @ResponseBody EasyUIPage getUserManagerList(@RequestParam(value="id",required=false)Integer id,
            @RequestParam(value = "userName",required = false)String userName,
            @RequestParam(value = "loginid",required = false)String loginid,
            @RequestParam(value = "ownerid",required = false)String ownerid,
			@RequestParam(value="rows")Integer rows,
			EasyUIPage page
			){
		page.setPagePara(rows);
		if(id != null){
			page = userManagerService.getUserManagerList(id,userName,loginid,ownerid,page);
		}else{
			return null;
		}
		return page;
	}
	
	/**l
	 * @Title: getUserList
	 * @Description: TODO(搜索)
	 * @return EasyUIPage
	 * @throws
	 */
	@RequestMapping(value="/getUserList",method = RequestMethod.GET)
	public @ResponseBody EasyUIPage getUserList(
			@RequestParam(value="id",required=false)Integer id,
			@RequestParam(value="rows")Integer rows,
			@RequestParam(value="userName",required=false)String userName,
			@RequestParam(value="usertype",required=false)String usertype,
			@RequestParam(value="loginid",required=false)String loginid,
			@RequestParam(value="ownerid",required=false)String ownerid,
			EasyUIPage page
			){
		page.setPagePara(rows);
		if(id != null){
			page = userManagerService.getUserManagerListByLikeAndOrgid(page,id,userName,usertype,loginid,ownerid);//有orgid			
		}else{
			page = userManagerService.getUserManagerListByLike(page,userName,usertype,loginid,ownerid);//没有orgid
		}
		return page;
	}

	/**
	 * @Title: getUserOfRoleList
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param userid
	 * @param @param rows
	 * @param @param page
	 * @param @return    设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getUserOfRoleList/{id}",method = RequestMethod.GET)
	public @ResponseBody EasyUIPage getUserOfRoleList(@PathVariable(value="id")Integer userid,
			@RequestParam(value="rows")Integer rows,
			EasyUIPage page
			){
		page.setPagePara(rows);
		if(userid != null){
			page = userManagerService.getUserOfRoleList(userid,page,getSessionBean());
		}else{
			return null;
		}
		return page;
	}
	
	/**
	 * @Title: getAllRoleList
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param rows
	 * @param @param page
	 * @param @return    设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getAllRoleList",method=RequestMethod.GET)
	public @ResponseBody EasyUIPage getAllRoleList(
			@RequestParam(value="rows")Integer rows,
			Integer orgid,
			EasyUIPage page
			){
        Integer realOrgId = this.roleManagerService.queryRealOrgId(orgid);
		page.setPagePara(rows);
		page = userManagerService.getAllRoleList(page,realOrgId,this.getSessionBean());
		
		return page;
	}
	
	/**
	 * @Title: getUserManager
	 * @Description: TODO(分页查询已有人员信息)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value = "/userManager")  
	public ModelAndView getUserManager(){
		return new ModelAndView("userManager/userManager");
	}
	
	/** 	lp3 新增人员信息
	 * @Title: addUserManagerInfo
	 * @Description: TODO(新增人员---仅新增人员基本信息)
	 * @param userManager
	 * @param orgid
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/addUserManagerInfo",method= RequestMethod.POST)
	public ModelAndView addUserManager(
			UserManager userManager,
			String orgid,
			String positionid){
		ModelAndView mode = new ModelAndView(AppConst.FORWORD);
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			//先判断当前录入的登录名是否存在
			int count = userManagerService.loginidIsExsit(userManager.getLoginid());
			if(count > 0 ){
				map.put(AppConst.STATUS, AppConst.FAIL);
				map.put(AppConst.MESSAGES,super.getMessage("user.alreadyExists"));
			}else{
				userManager.setStatus(AppConst.STATUS_ENABLE);
				userManager.setIsactive(AppConst.STATUS_ENABLE);
				
				userManagerService.saveUserManagerInfo(userManager,orgid,positionid);
				
				map.put(AppConst.STATUS, AppConst.SUCCESS);
				map.put(AppConst.MESSAGES,super.getAddSuccess());
			}
		}catch (Exception e) {
			e.printStackTrace();
			map.put(AppConst.STATUS, AppConst.FAIL);
			map.put(AppConst.MESSAGES,getAddFail());
		}
		mode.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mode;
	}
	
	
	/**  lp3   弃用  addUserManagerInfo替代
	 * @Title: addUserManager
	 * @Description: TODO(新增用户信息)
	 * @param @param userManager
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@Deprecated
	@PrivilegeAnnotation()//进行拦截
	@RequestMapping(method= RequestMethod.POST, value="/addUserManager")
	public ModelAndView addUserManager(UserManager userManager,Role role,@RequestParam(value="treeArrPer")String[] arr,@RequestParam(value="dgArr")String str,String orgid,@RequestParam(value="pname")String positionid){
		ModelAndView mode = new ModelAndView(AppConst.FORWORD);
		Map<String, String> map = new HashMap<String, String>();
		try{
			if(userManager != null){
				//保存前先判断当前录入的登录名是否存在
				int count = userManagerService.loginidIsExsit(userManager.getLoginid());
				if(count > 0 ){
					map.put(AppConst.STATUS, AppConst.FAIL);
					map.put(AppConst.MESSAGES,super.getMessage("user.alreadyExists"));
				}else{
					//保存前获取用户以及角色序列值，处理数据
					Integer userid = userManagerService.getUseridBySeq();
					map.put(AppConst.STATUS, AppConst.SUCCESS);
					map.put(AppConst.MESSAGES,super.getAddSuccess());
					userManager.setStatus(AppConst.STATUS_ENABLE);
					userManager.setIsactive(AppConst.STATUS_ENABLE);
					userManager.setId(userid);
					//先保存人员信息，页签一的保存方法
					//userManagerService.saveUserManager(userManager);
					
					// 统一保存  update by dl
					userManagerService.saveUser(userManager, role, arr, str, orgid, positionid);
					
					//页签3的处理方法
//					if(orgid.equals("")||orgid.equals(null)){
//					}else{
//						 OrgUsers ou = new OrgUsers();
//						 ou.setUserid(userid);
//						 ou.setOrgid(Integer.valueOf(orgid));
//						 ou.setStatus(AppConst.STATUS_ENABLE);
//						 userManagerService.saveUserOfOrg(ou);
//					}
////					if(arrOrg.equals("")||arrOrg.equals(null)){
////						//在进入新增页面前选择了组织机构节点
////						if(orgid.equals("")||orgid.equals(null)){
////							
////							 OrgUsers ou = new OrgUsers();
////							 ou.setUserid(userid);
////							 ou.setOrgid(Integer.valueOf(orgid));
////							 ou.setStatus(AppConst.STATUS_ENABLE);
////							 userManagerService.addUserOfOrg(ou);
////						}
////					}else{
////						addUserOfOrg(userid,arrOrg);
////					}
//					//页签4的处理方法,处理页签二 人员角色信息,处理之前先判断页签4是否有数据变化，如果有则直接处理页签4，没有则处理页签2
//					if(role.getrName().equals(null) || role.getrName().equals("")){
//						if(str.equals("") || str.equals(null)){
//						}else{
//							//进入页签的处理方法
//							handlePageTwo(userManager.getId(),str);
//						}
//					}else{
//						//处理人员和新增角色关系以及角色及权限的关系
//						addRoleOfUser(userid,role,arr);
//					}
//					//处理职位数据
//					saveUserPostion(userid,positionid);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			map.put(AppConst.STATUS, AppConst.FAIL);
			map.put(AppConst.MESSAGES,getAddFail());
		}
		mode.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mode;
	}
	
	public void saveUserPostion(Integer userid,String positionid){
		//先清除原有的人员职位关系
		userManagerService.deleteUsersPositions(userid);
		UsersPositions up = new UsersPositions();
		up.setUserid(userid);
		up.setPositionid(Integer.valueOf(positionid));
		up.setStatus(AppConst.STATUS_ENABLE);
		userManagerService.saveUserPostion(up);
	}
	
	//页签3的处理
	public void addUserOfOrg(Integer userid,String arrOrg){
		//首先清除当前人员原有的组织机构关系
		 userManagerService.delUserOfOrg(userid);
		 String[] strArr=arrOrg.split(","); 
		 List<Org> list = new ArrayList<Org>();
		 for(int i = 0; i < strArr.length; i++){
			 String [] child = strArr[i].split(":");
			 Org org = new Org();
			 org.setId(Integer.valueOf(child[0]));
			 org.setParentId(Integer.valueOf(child[1]));
			 org.setOrgName(child[2]);
			 list.add(org);
		 }
		
		 //list集合为所有选中的数据，处理方法为判断当前节点的id是否有等于其他节点的parentid，如果id不是作为其他节点的parentid则为要保存的数据
		 for(int j = 0; j < list.size(); j++){
			 Boolean b = true;
			 for(int k = 0; k <list.size() ; k++){
				 if(list.get(j).getParentId() == list.get(k).getId() || list.get(j).getParentId().equals(list.get(k).getId())){
					b = false;
				 }
			 }
			 if(b){
				 OrgUsers ou = new OrgUsers();
				 ou.setUserid(userid);
				 ou.setOrgid(list.get(j).getParentId());
				 ou.setStatus(AppConst.STATUS_ENABLE);
				 userManagerService.saveUserOfOrg(ou);
			 }
		 }
	}
	
	//页签2的处理
	/**
	 * @Title: addUserInRole
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param userid
	 * @param @param str    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void addUserInRole(Integer userid,String str){
	    String[] strArr=str.split(","); 
		for(int i = 0; i < strArr.length; i++){
			UserRoles ur = new UserRoles();
			ur.setUserid(userid);
			ur.setRoleid(Integer.valueOf(strArr[i]));
			ur.setStatus(AppConst.STATUS_ENABLE);
			userManagerService.saveUserRolesOfUser(ur);
		}
	}
	
	/**
	 * @Title: addRoleOfUser
	 * @Description: TODO(新增人员权限页签)
	 * @param @param userid
	 * @param @param role
	 * @param @param arr    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void addRoleOfUser(Integer userid,Role role,String[] arr){
		//为新增的角色获取一个序列值
		Integer roleid = userManagerService.getRoleidBySeq();
		//保存角色信息
		role.setStatus(AppConst.STATUS_ENABLE);
		role.setId(roleid);
		userManagerService.saveRoleOfUser(role);
		//保存角色权限信息
		String[] arr2;
		RolesPermissions rp = new RolesPermissions();
		for(int i = 0; i < arr.length; i++){
			//处理树节点
			arr2 = arr[i].split(":");
			rp.setRoleid(roleid);
			rp.setPermissionid(Integer.valueOf(arr2[1]));
			rp.setStatus(AppConst.STATUS_ENABLE);
			userManagerService.saveRolePermissions(rp);
		}
		//保存人员角色关系信息
		UserRoles userRoles = new UserRoles();
		userRoles.setUserid(userid);
		userRoles.setRoleid(roleid);
		userRoles.setStatus(AppConst.STATUS_ENABLE);
		userManagerService.saveUserRolesOfUser(userRoles);
	}
	
	/**
	 * 
	 * @Title: del
	 * @Description: TODO(根据用户id删除用户信息)
	 * @param @param param
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation()//进行拦截
	@RequestMapping(value="/del",method = RequestMethod.POST)
	public @ResponseBody Map<String,String> deleteUserManager(@RequestParam(value="id")Integer id)
	{
		Map<String, String> map = new HashMap<String, String>();
		boolean b = userManagerService.deleteUserManager(id);
		if(b){
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES,super.getDeleteSuccess());
			//处理关系表数据
			//删除人员机构的关系
			userManagerService.delUserOfOrg(id);
			userManagerService.delUserOfRole(id);
		}else{
			map.put(AppConst.STATUS, AppConst.ERRORS);
			map.put(AppConst.MESSAGES,super.getDeleteFail());
		}
		return map;
	}
	
	/**	lp3  弃用 		 updateUserInfo替换
	 * @Title: updateUserManager
	 * @Description: TODO(修改用户信息)
	 * @param @param userManager
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation()//进行拦截
	@RequestMapping(value="/updateUserManager",method=RequestMethod.POST)
	public ModelAndView updateUserManager(UserManager userManager,
											Role role,
											@RequestParam(value="treeArrPer")String[] arr,
											@RequestParam(value="dgArr")String str,
											@RequestParam(value="pname")String positionid)
	{
		ModelAndView mode = new ModelAndView(AppConst.FORWORD);
		Map<String, String> map = new HashMap<String, String>();
//		//处理页签一 人员信息
//		boolean b = userManagerService.updateUserManager(userManager);
//		//处理页签二 人员角色信息,处理之前先判断页签4是否有数据变化，如果有则直接处理页签4，没有则处理页签2
//		if(role.getrName().equals(null) || role.getrName().equals("")){
//			//判断人员所属的角色列表是否有数据
//			if(str.equals("") || str.equals(null)){
//			}else{
//				handlePageTwo(userManager.getId(),str);
//			}
//		}else{
//			handlePageFour(userManager.getId(),role,arr);
//		}
//		//处理页签三 人员组织机构信息
////		if(arrOrg.equals(null) || arrOrg.equals("")){
////		}else{
////			addUserOfOrg(userManager.getId(),arrOrg);
////		}
//		//处理人员职位关系
//		saveUserPostion(userManager.getId(),positionid);
		boolean b = userManagerService.updateUser(userManager, role, arr, str, positionid);
		if(b){
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES,super.getUpdateSuccess());
		}else{
			map.put(AppConst.STATUS, AppConst.ERRORS);
			map.put(AppConst.MESSAGES,super.getUpdateFail());
		}
		mode.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mode;
	}
	
	//lp--3 仅修改用户信息
	@RequestMapping(value="/updateUserInfo",method=RequestMethod.POST)
	public ModelAndView updateUserInfo(UserManager userManager,String positionid){
		ModelAndView mode = new ModelAndView(AppConst.FORWORD);
		Map<String, String> map = new HashMap<String, String>();

		boolean b = userManagerService.updateUser(userManager,positionid);
		if(b){
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES,super.getUpdateSuccess());
		}else{
			map.put(AppConst.STATUS, AppConst.FAIL);
			map.put(AppConst.MESSAGES,super.getUpdateFail());
		}
		mode.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mode;
	} 
	
	//lp3 分配角色
	@RequestMapping(value="/updateUserRole",method=RequestMethod.POST)
	public ModelAndView updateUserRole(String uid,String roleids){
		ModelAndView mode = new ModelAndView(AppConst.FORWORD);
		Map<String, String> map = new HashMap<String, String>();
		boolean flag = userManagerService.updateUserRole(uid,roleids);
		if(flag){
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES,super.getUpdateSuccess());
		}else{
			map.put(AppConst.STATUS, AppConst.FAIL);
			map.put(AppConst.MESSAGES,super.getUpdateFail());
		}
		mode.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mode;
	}
	
	/**弃用
	 * @Title: handlePageTwo
	 * @Description: TODO(处理人员角色页签)
	 * @param @param userid
	 * @param @param str    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	@Deprecated
	public void handlePageTwo(Integer userid,String str){
		//先删除当前人员已经拥有的角色
		boolean b = userManagerService.delUserOfRole(userid);
		//删除成功后,新增加人员角色关系
		String[] strArr=str.split(","); 
		for(int i = 0; i < strArr.length; i++){
			UserRoles ur = new UserRoles();
			ur.setUserid(userid);
			ur.setRoleid(Integer.valueOf(strArr[i]));
			ur.setStatus(AppConst.STATUS_ENABLE);
			userManagerService.saveUserRolesOfUser(ur);
		}
	}
	
	/**
	 * @Title: handlePageFour
	 * @Description: TODO(处理人员新增权限页签)
	 * @param @param userid
	 * @param @param role
	 * @param @param arr    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void handlePageFour(Integer userid,Role role,String[] arr){
		//处理数据前先删除所有已经拥有的角色
		boolean b = userManagerService.delUserOfRole(userid);
			//为新增的角色获取一个序列值
		Integer roleid = userManagerService.getRoleidBySeq();
		//保存角色信息
		role.setStatus(AppConst.STATUS_ENABLE);
		role.setId(roleid);
		userManagerService.saveRoleOfUser(role);
		//保存角色权限信息
		String[] arr2 ;
		RolesPermissions rp = new RolesPermissions();
		for(int i = 0; i < arr.length; i++){
			//处理树节点
			arr2 = arr[i].split(":");
			rp.setRoleid(roleid);
			rp.setPermissionid(Integer.valueOf(arr2[1]));
			rp.setStatus(AppConst.STATUS_ENABLE);
			userManagerService.saveRolePermissions(rp);
		}
		//保存人员角色关系信息
		UserRoles userRoles = new UserRoles();
		userRoles.setUserid(userid);
		userRoles.setRoleid(roleid);
		userRoles.setStatus(AppConst.STATUS_ENABLE);
		userManagerService.saveUserRolesOfUser(userRoles);
	}
	
	/**
	 * @Title: getOrgDetail
	 * @Description: TODO(获取组织详细信息)
	 * @param @param id
	 * @param @return    设定文件
	 * @return JSONObject    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getOrgDetail",method=RequestMethod.GET)
	public @ResponseBody JSONObject getOrgDetail(@RequestParam(value="id",required=false)Integer id){
		Org org = new Org();
		org = userManagerService.getOrgDetail(id);
		JSONObject jsObj = JSONObject.fromObject(org);  
		System.out.print(jsObj);
		return jsObj;
	}
	
	/**       弃用弃用弃用弃用弃用    
	 * @Title: sysTreeRole
	 * @Description: TODO(获取人员权限树)
	 * @param @param userid
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@Deprecated
	@RequestMapping(value="/sysTree/{id}",method = RequestMethod.GET)
	public @ResponseBody List<TreeNode> sysTreeRole(@PathVariable(value="id")Integer userid){
		//根据userid 获取当前人拥有的角色
		List<Role> list = userManagerService.getRoleByUserid(userid);
		List<TreeNode> list1 = new ArrayList<TreeNode>();
		String roleid = "";
		if(list.size() != 0){
			for(int i = 0;i < list.size(); i++){
				if( i < (list.size() - 1)){
					roleid += list.get(i).getId()+",";
				}else{
					roleid += list.get(i).getId();
				}
			}
		}else{
			roleid = "-1";
		}
		list1 =  userManagerService.getUserTree(roleid,getSessionBean());
		return list1;
	}
	
	//lp3  获取该人员的权限树
	@RequestMapping(value="/sysTreeByUser/{id}",method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<Ztree> sysTreeByUser(@PathVariable(value="id")Integer userid){
		List<Ztree> ztrees = new ArrayList<Ztree>();	//返回的人员权限树
		List<Ztree> allZtrees = new ArrayList<Ztree>();//所有的权限树以及人员被选中的权限
		
		List<Role> roles = userManagerService.getRoleByUserid(userid);
		
		for(int i = 0; i < roles.size(); i++){
			allZtrees = treeService.getRoleSysTree(roles.get(i).getId(),this.getSessionBean());
			for(int j = 0; j < allZtrees.size(); j++){
				if(allZtrees.get(j).getChecked() != null && allZtrees.get(j).getChecked().equals("true")){
					ztrees.add(allZtrees.get(j));
				}
			}
		}
		return ztrees;
	}
	
	//lp3  改变人员的权限树
	@RequestMapping(value="/changeSysTreeByRole",method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<Ztree> changeSysTreeByRole(@RequestParam(value="ids",required=false)String roleids){
		List<Ztree> ztrees = new ArrayList<Ztree>();	//返回的人员权限树
		List<Ztree> allZtrees = new ArrayList<Ztree>();//所有的权限树以及人员被选中的权限
		
		if(roleids.length() > 0){
			//拆分传入的角色ids字符串
			String[] roleidsStr = roleids.split(",");
			List<Integer> ids = new ArrayList<Integer>();
			
			for(int i = 0; i < roleidsStr.length; i++){
				if(!roleidsStr[i].equals("")){
					ids.add(Integer.parseInt(roleidsStr[i]));
				}
			}
			
//			for(int i = 0; i < ids.size(); i++){
//				allZtrees = treeService.getRoleSysTree(Integer.parseInt(ids.get(i)),this.getSessionBean());
//				for(int j = 0; j < allZtrees.size(); j++){
//					if(allZtrees.get(j).getChecked() != null && allZtrees.get(j).getChecked().equals("true")){
//						ztrees.add(allZtrees.get(j));
//					}
//				}
//			}
			ztrees = treeService.getUserSysTreeUtil(ids);
		}
		return ztrees;
	}
	
	/**
	 * @Title: sysTreeOrg
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param userid
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/sysTreeOrg/{id}",method = RequestMethod.GET)
	public @ResponseBody List<TreeNode> sysTreeOrg(@PathVariable(value="id")Integer userid){
		List<TreeNode> list = userManagerService.sysTreeOrg(userid);
		return list;
	}
	
	/**
	 * @Title: sysReTreeRole
	 * @Description: TODO(点击添加或者删除角色后刷新页签4的权限树)
	 * @param @param userid
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/sysReTreeRole/{id}",method = RequestMethod.GET)
	public @ResponseBody List<TreeNode> sysReTreeRole(@PathVariable(value="id")String roleid){
		List<TreeNode> list = userManagerService.getUserTree(roleid,getSessionBean());
		return list;
	}
	
	/**
	 * @Title: changeDateToYMD
	 * @Description: TODO(调整时间格式)
	 * @param @param date
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String changeDateToYMD(String date) {
		if (date == null) {
			return null;
		}
		if (date.length() > 15) {
			try {
				String[] tt = date.split("\\.");
				return tt[0];
			} catch (Exception e) {

				return "0000-00-00";
			}
		} else if (date.length() <= 10) {

			return date;
		}
		return "0000-00-00";
	}
	
	//变更组织机构
	@RequestMapping(method= RequestMethod.POST, value="/saveUserOrg")
	public ModelAndView saveUserOrg(Integer userid,Integer orgid,Integer postid){
		ModelAndView mode = new ModelAndView(AppConst.FORWORD);
		Map<String, String> map = new HashMap<String, String>();
		try{
			userManagerService.saveUserOrgPost(userid,orgid,postid);
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES,super.getAddSuccess());
		}catch (Exception e) {
			e.printStackTrace();
			map.put(AppConst.STATUS, AppConst.FAIL);
			map.put(AppConst.MESSAGES,getAddFail());
		}
		mode.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mode;
	}
	
	//lp3   重置密码   可弃用
	@Deprecated
	@RequestMapping(value="/reSetUserPassword/{id}",method = RequestMethod.GET)
	public @ResponseBody Map<String,String> reSetUserPassword(@PathVariable(value="id")Integer userid){
		Map<String, String> map = new HashMap<String, String>();
		boolean b = userManagerService.saveSetUserPassword(userid);
		if(b){
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES,super.getDeleteSuccess());
		}else{
			map.put(AppConst.STATUS, AppConst.ERRORS);
			map.put(AppConst.MESSAGES,super.getDeleteFail());
		}
		return map;
	}
	
	//lp3  打开分配角色页面
	@RequestMapping(value = "/goDisRole")  
	public ModelAndView goDisRole(@RequestParam(value="id")Integer id){
		UserManager userManager = userManagerService.initUserManager(id);
		Integer orgid = userManagerService.getUserOrgid(id);
		
		ModelMap map = new ModelMap();
		map.put("orgid", orgid);
		map.put("userManager", userManager);
		return new ModelAndView("userManager/disRole",map);
	}
	
	//lp3 分配角色
	@RequestMapping(value = "/goOrg")  
	public ModelAndView goOrg(@RequestParam(value="id")Integer id){
		ModelMap map = new ModelMap();
		Integer orgid = userManagerService.getUserOrgid(id);
		Org org = orgService.selectOrgById(orgid);
		
		map.put("Org", org);
		return new ModelAndView("userManager/seeOrgByUser",map);
	}
	
	@RequestMapping(value = "/queryOrgAndPosition",method={RequestMethod.POST,RequestMethod.GET})  
	public @ResponseBody List<Ztree> queryOrgAndPosition(){
		List<Ztree> ztrees = treeService.getOrgAndPosition(getSessionBean().getOrganization().getId());
		
		return ztrees;
	}
}
