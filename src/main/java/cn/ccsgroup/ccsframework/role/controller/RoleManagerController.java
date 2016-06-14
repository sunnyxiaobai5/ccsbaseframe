package cn.ccsgroup.ccsframework.role.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import cn.ccsgroup.ccsframework.annotation.PrivilegeAnnotation;
import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Org;
import cn.ccsgroup.ccsframework.entity.Role;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.role.service.RoleManagerService;
import cn.ccsgroup.ccsframework.tree.service.TreeService;
import cn.ccsgroup.ccsframework.userManager.service.OrgService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.role.RoleManagerController.java]  
 * @ClassName:    [RoleManagerController]   
 * @Description:  [系统角色管理]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-26 下午4:29:24]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-26 下午4:29:24，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@Scope("prototype")
@RequestMapping("/RoleController")
public class RoleManagerController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name="roleManagerService")
	private RoleManagerService roleManagerService;
	
	@Resource(name="OrgService")
	private OrgService orgService;
	
	@Resource(name="treeService")
	private TreeService treeService;	
	/**
	 * 
	 * @Title: roleList
	 * @Description: TODO(角色管理主页)
	 * @param @param pg
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/role",method = RequestMethod.GET)
	public String roleList() {
		return "roleManager/roleManager";
	}

	/**
	 * @Title: roleList
	 * @Description: TODO(角色翻页列表)
	 * @param @param page
	 * @param @param rows
	 * @param @return    设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	@RequestMapping(value="/roleListByOrgid",method = RequestMethod.POST)
	public @ResponseBody EasyUIPage roleListByOrgid(EasyUIPage page, 
													@RequestParam(value="rows")Integer rows,
													@RequestParam(value="orgid")Integer orgid,
                                                    @RequestParam(value="roleName",required = false)String roleName) {
        page.setPagePara(rows);
		return roleManagerService.roleListByOrgid(page,getSessionBean(),orgid,roleName);
	}
	
	/**
	 * @Title: roleList
	 * @Description: TODO(角色翻页列表)
	 * @param @param page
	 * @param @param rows
	 * @param @return    设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	@RequestMapping(value="/list",method = RequestMethod.POST)
	public @ResponseBody EasyUIPage roleList(EasyUIPage page, @RequestParam(value="rows")Integer rows) {
		page.setPagePara(rows);
		return roleManagerService.roleList(page,getSessionBean());
	}

	/**
	 * 
	 * @Title: sysTree
	 * @Description: TODO(取当前登录用户角色的所有系统权限树)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/sysTree",method = RequestMethod.GET)
	public @ResponseBody List<TreeNode> sysTree(){
		return roleManagerService.sysTree(getSessionBean());
	}
	/**
	 * 
	 * @Title: sysTreeRole
	 * @Description: TODO(取当前登录用户角色所有系统权限树 （已拥有的是选中状态） )
	 * @param @param roleid
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/sysTree/{id}",method = RequestMethod.GET)
	public @ResponseBody List<TreeNode> sysTreeRole(@PathVariable(value="id")Integer roleid){
		List<TreeNode> list  = roleManagerService.sysTree(roleid,getSessionBean());
		return list;
	}



	/**
	 * 
	 * @Title: createRole
	 * @Description: TODO(打开新增角色)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/create",method = RequestMethod.GET)
	public ModelAndView createRole(){
		ModelAndView mv = new ModelAndView("roleManager/addRole");
		mv.addObject("userId", getSessionBean().getId());
		return mv;
	}
	
	/**  lp3     	 
	 * @Title: saveRole
	 * @Description: TODO(保存新增角色)
	 * @param orgid
     * @param sys
     * @param role
	 * @return ModelAndView
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public ModelAndView saveRole(
							@RequestParam(value = "orgId")String orgid,
							@RequestParam(value = "sys")String sys,
							Role role){
		ModelAndView mv = new ModelAndView("redirect:/forward/"+AppConst.FORWORD);
		
		List<Ztree> ztreeList = JSON.parseArray(sys,Ztree.class);
		
		Map<String,Object> model = new HashMap<String,Object>();
		
		if(roleManagerService.queryRoleName(role.getrName())){
			model.put(AppConst.STATUS, AppConst.FAIL);
			model.put(AppConst.MESSAGES,getMessage("role.exists"));
		}else{			
			try{
				roleManagerService.saveRole(orgid, ztreeList, getSessionBean(), role);
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getAddSuccess());
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getLocalizedMessage(), e);
				model.put(AppConst.STATUS, AppConst.FAIL);
				model.put(AppConst.MESSAGES,getAddFail());
			}
		}
		
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(model));
		return mv;
	}
	
	
	/** 	
	 * 
	 * @Title: updateRole
	 * @Description: TODO(打开修改角色页面)
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/update",method = RequestMethod.GET)
	public ModelAndView updateRole(@RequestParam(value="id")Integer id){
		ModelAndView mv = new ModelAndView("roleManager/updateRole");
		Role role = roleManagerService.queryRoleById(id);
		Org org = orgService.selectOrgById(role.getOrgid());
		mv.addObject("role", role);
		mv.addObject("orgName", org.getOrgName());
		mv.addObject("userid", getSessionBean().getId());
		return mv;
	}
	
	/**  lp3     	 
	 * @Title: updateTheRole
	 * @Description: TODO(修改角色)
	 * @param 
	 * @return ModelAndView
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public ModelAndView updateTheRole(
							Role role,
							String sys){
		ModelAndView mv = new ModelAndView("redirect:/forward/"+AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
		
		List<Ztree> ztreeList = JSON.parseArray(sys,Ztree.class);

		try {
			int i = roleManagerService.updateTheRole(role,getSessionBean(),ztreeList);
			if(i > 0){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getUpdateSuccess());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage(), e);
			model.put(AppConst.MESSAGES,getUpdateFail());
			model.put(AppConst.STATUS, AppConst.FAIL);
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(model));
		return mv;
	}
	
	/**
	 * 
	 * @Title: delRole
	 * @Description: TODO(注销角色)
	 * @param @param id
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/del",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> delRole(@RequestParam(value="id")Integer id){
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			int i = roleManagerService.deleteRole(id);
			if(i > 0){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getDeleteSuccess());
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage(), e);
			model.put(AppConst.MESSAGES,getDeleteFail());
			model.put(AppConst.STATUS, AppConst.FAIL);
		}
		return model;
	}

	/**
	 * 
	 * @Title: queryUsers
	 * @Description: TODO(根据角色ID取所属用户树)
	 * @param @param id
	 * @param @return    设定文件
	 * @return List<UserManager>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/users/{id}",method = RequestMethod.GET)
	public @ResponseBody List<TreeNode> queryUsers(@PathVariable(value="id")Integer id ){
		return roleManagerService.queryUsersByRoleId(id);
	}
	/**
	 * 
	 * @Title: queryOrgUserTree
	 * @Description: TODO(取当前登录用户角色所有组织机构用户树 （已拥有的是选中状态）)
	 * @param @param roleid
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/orgUser/{id}",method = RequestMethod.GET)
	public @ResponseBody List<TreeNode> queryOrgUserTree(@PathVariable(value="id")Integer roleId ){
		return roleManagerService.queryOrgUserTree(roleId,super.getSessionBean());
	}
	/**
	 * 
	 * @Title: queryUsersList
	 * @Description: TODO(根据角色ID取所属用户列表)
	 * @param @param id
	 * @param @return    设定文件
	 * @return List<UserManager>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/usersList/{id}",method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<UserManager> queryUsersList(@PathVariable(value="id")Integer id ){
		return roleManagerService.queryUsersListByRoleId(id);
	}
	
	/**
	 * 
	 * @Title: setUsers
	 * @Description: TODO(分配用户页面)
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/setUsers",method = RequestMethod.GET)
	public ModelAndView setUsers(@RequestParam(value="id")Integer id,@RequestParam(value="orgid")Integer orgid){
		ModelAndView mv = new ModelAndView("roleManager/setUsers");
		mv.addObject("roleId", id);
		mv.addObject("orgid", orgid);
		return mv;
	}
	
	/** lp3 
	 * @Title: orgUserList
	 * @Description: TODO(根据组织机构分页查询用户)
	 * @param @param orgId
	 * @param @return    设定文件
	 * @return List<UserManager>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/orgUserList/{orgId}/{roleId}",method = RequestMethod.POST)
	public @ResponseBody EasyUIPage orgUserList(
												EasyUIPage page, 
												@RequestParam(value="rows")Integer rows,
												@PathVariable(value="orgId")Integer orgId,
												@PathVariable(value="roleId")Integer roleId){
		
		page.setPagePara(rows);		
		page = roleManagerService.queryOrgUserList(page,orgId,roleId);
		return page;
	}
	
	
	/**
	 * 
	 * @Title: delRoleUser
	 * @Description: TODO(删除角色用户)
	 * @param @param id
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/delRoleUser/{roleId}",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> delRoleUser(@PathVariable(value="roleId")Integer roleId,@RequestParam(value="id")Integer userId){
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			int i = roleManagerService.deleteRoleUser(roleId,userId);
			if(i > 0){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getDeleteSuccess());
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage(), e);
			model.put(AppConst.MESSAGES,getDeleteFail());
			model.put(AppConst.STATUS, AppConst.FAIL);
		}
		return model;
	}
	
	/**
	 * 
	 * @Title: setUserPerm
	 * @Description: TODO(进入用户更改权限)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/setUserPerm/{roleId}",method = RequestMethod.GET)
	public ModelAndView setUserPerm(@PathVariable(value="roleId")Integer roleId ,@RequestParam(value="id")Integer userId){
		ModelAndView mv = new ModelAndView("roleManager/setUserPerm");
		mv.addObject("roleId", roleId);
		mv.addObject("loginUserId", getSessionBean().getId());
		mv.addObject("entity", roleManagerService.queryUserById(userId));
		return mv;
		
	}
	
	/**
	 * 
	 * @Title: saveRoleUser
	 * @Description: TODO(保存角色用户)
	 * @param @param roleId
	 * @param @param ids
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/saveRoleUser/{roleId}",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> saveRoleUser(@PathVariable(value="roleId")Integer roleId ,
			@RequestParam(value="ids")String[] ids,@RequestParam(value="currentPageIds")String[] currentPageIds){
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			roleManagerService.saveRoleUser(roleId,ids,currentPageIds);
			model.put(AppConst.STATUS, AppConst.SUCCESS);
			model.put(AppConst.MESSAGES, getAddSuccess());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage(), e);
			model.put(AppConst.MESSAGES,getAddFail());
			model.put(AppConst.STATUS, AppConst.FAIL);
		}
		return model;
	}
	
	/**
	 * 
	 * @Title: setUserPerm
	 * @Description: TODO(用户更改角色保存)
	 * @param @param role
	 * @param @param arr
	 * @param @param userId
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/setUserPerm/{userId}" , method = RequestMethod.POST)
	public ModelAndView setUserPerm(Role role,String sysTreeArr,@PathVariable(value="userId")Integer userId){
		ModelAndView mv = new ModelAndView("redirect:/forward/"+AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
//		List<TreeNodeBean> list = null ,  list2 = null;
//		if(arr != null )list = setTreeList(arr);
//		if(arr != null )list2 = setTreeList(org);
		
		JSONArray jsarray = JSONArray.fromObject(sysTreeArr);
		List<TreeNode> list = (List<TreeNode>)jsarray.toCollection(jsarray, TreeNode.class);

		try{
			int i = roleManagerService.updateUserPerm(role,list,userId,getSessionBean());
			if(i > 0){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getUpdateSuccess());
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			model.put(AppConst.MESSAGES,getUpdateFail());
			model.put(AppConst.STATUS, AppConst.FAIL);
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(model));
		return mv;
	}

/*	private List<TreeNodeBean> setTreeList(String[] arr) {
		TreeNodeBean tree = null;
		String[] arr2 ;
		List<TreeNodeBean> list = new ArrayList<TreeNodeBean>();
		Map<String,Integer> map = new HashMap<String,Integer>();
		for (int i = 0; i < arr.length; i++) {
			tree = new TreeNodeBean();
			arr2 = arr[i].split(":");
			map.put(arr2[1], map.containsKey(arr2[0])?map.get(arr2[0])+1 :0);
			tree.setPid(Integer.valueOf(arr2[0]));
			tree.setId(Integer.valueOf(arr2[1]));
			tree.setText(arr2[2]);
			tree.setLevel(map.get(arr2[1]));
			list.add(tree);
		}
		return list;
	}*/
	
	/**lp3
	 * @Title: getRoleSysTree
	 * @Description: TODO(获取系统权限树(编辑角色用)  zTree)
	 * @return List
	 * @throws
	 */
	@RequestMapping(value="/getRoleSysTree",method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<Ztree> getRoleSysTree(Integer roleid){
		List<Ztree> list = treeService.getRoleSysTree(roleid,getSessionBean());
		return list;
	}
	
	/**lp3
	 * @Title: querySetUser
	 * @Description: TODO(搜索--条件查询user)
	 * @return List
	 * @throws
	 */
	@RequestMapping(value="/querySetUser",method = RequestMethod.POST)
	public @ResponseBody EasyUIPage querySetUser(EasyUIPage page,
												@RequestParam(value="rows")Integer rows,
												Integer orgid,Integer roleId,String userName,String loginid){
		page.setPagePara(rows);
		page = roleManagerService.queryOrgUserList(page,orgid,roleId,userName,loginid);
		return page;
	}
}
