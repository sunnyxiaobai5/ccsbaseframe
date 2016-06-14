
package cn.ccsgroup.ccsframework.userManager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Func;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.entity.Ops;
import cn.ccsgroup.ccsframework.entity.Org;
import cn.ccsgroup.ccsframework.entity.OrgUsers;
import cn.ccsgroup.ccsframework.entity.Permission;
import cn.ccsgroup.ccsframework.entity.Position;
import cn.ccsgroup.ccsframework.entity.Role;
import cn.ccsgroup.ccsframework.entity.RolesPermissions;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.entity.UserRoles;
import cn.ccsgroup.ccsframework.entity.UsersPositions;
import cn.ccsgroup.ccsframework.role.dao.RoleManagerDao;
import cn.ccsgroup.ccsframework.tree.service.TreeService;
import cn.ccsgroup.ccsframework.userManager.dao.OrgDao;
import cn.ccsgroup.ccsframework.userManager.dao.UserManagerDao;
import cn.ccsgroup.ccsframework.userManager.service.UserManagerService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
import cn.ccsgroup.ccsframework.utils.StringUtil;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.usermanager.service.impl.UserManagerServiceImpl.java]  
 * @ClassName:    [UserManagerServiceImpl]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [zengheng]   
 * @CreateDate:   [2013-12-10 下午02:54:09]   
 * @UpdateUser:   [zengheng(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-10 下午02:54:09，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@Service("UserManagerService")
@Transactional
public class UserManagerServiceImpl implements UserManagerService {
	
	/**
	 * @Fields userDao : TODO(用一句话描述这个变量表示什么)
	 */
	private UserManagerDao userManagerDao;

	/**
	 * @param userManagerDao the userDao to set
	 */
	@Resource(name="userManagerDao")
	public void setUserManagerDao(UserManagerDao userManagerDao) {
		this.userManagerDao = userManagerDao;
	}
	
	@Resource(name="roleManagerDao")
	private RoleManagerDao roleManagerDao;
	
	private Map<String,Integer> roleSysNodes ;
	

	private OrgDao orgDao;
	
	@Resource(name="OrgDao")
	public void setOrgDao(OrgDao orgDao)
	{
		this.orgDao = orgDao;
	}
	
	//lp3  仅新增用户和用户组织机构关系
	public void saveUserManagerInfo(UserManager userManager,String orgid,String positionid){
		Integer userid = userManagerDao.addUserManagerInfo(userManager);
		//添加userposition
		saveUserPosition(userid, positionid);
		//添加orgusers
		OrgUsers orgUsers = new OrgUsers();
		
		orgUsers.setUserid(userid);
		orgUsers.setOrgid(Integer.valueOf(orgid));
		orgUsers.setStatus(AppConst.STATUS_ENABLE);
		
		userManagerDao.addUserOfOrg(orgUsers);
	}
	//新增用户和职位的关系
	public void saveUserPosition(Integer userid,String positionid){
		
		UsersPositions up = new UsersPositions();
		up.setUserid(userid);
		up.setPositionid(Integer.parseInt(positionid));
		up.setStatus(AppConst.STATUS_ENABLE);
		
		userManagerDao.saveUserPostion(up);
	}
	
	/**
	 * @Title: saveUserManager
	 * @Description: TODO(新增用户)
	 * @param @param userManager
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean saveUserManager(UserManager userManager) {
		return userManagerDao.addUserManager(userManager);
	}
	
	public boolean saveUserRolesOfUser(UserRoles userRoles) {
		return userManagerDao.addUserRolesOfUser(userRoles);
	}
	
	public boolean saveUserOfOrg(OrgUsers orgUses){
		return userManagerDao.addUserOfOrg(orgUses);
	}
	
	public boolean saveRoleOfUser(Role role) {
		return userManagerDao.addRoleOfUser(role);
	}
	
	public boolean saveRolePermissions(RolesPermissions rp){
		return userManagerDao.addRolePermissions(rp);
	}
	
	/**
	 * 
	 * @Title: getUserManager
	 * @Description: TODO(分页查询已有人员信息)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	public List<UserManager> selectUserManagerByPage() {
		return userManagerDao.selectUserManagerByPage();
	}

	/**
	 * 
	 * @Title: deleteUserManager
	 * @Description: TODO(根据用户id删除用户信息)
	 * @param @param param
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	public boolean deleteUserManager(Integer id) {
		return userManagerDao.deleteUserManager(id);
	}
	
	/**
	 * 
	 * @Title: initUserManager
	 * @Description: TODO(根据用户id查询用户信息)
	 * @param @param userid
	 * @param @return    设定文件
	 * @return UserManager    返回类型
	 * @throws
	 */
	public UserManager initUserManager(Integer id) {
		return userManagerDao.initUserManager(id);
	}
	
	@Override
	public EasyUIPage getUserManagerList(Integer orgid,String userName,String loginid,String ownerid,EasyUIPage page) {
        HashMap<String, Object> map = new HashMap<String, Object> ();
        map.put("orgid", orgid);
        map.put("userName", userName);
        map.put("loginid", loginid);
        map.put("ownerid", ownerid);

        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
		page.setRows(userManagerDao.getUserManagerList(map));
		page.setTotal(userManagerDao.getUserManagerCount(map));
		return page;
	}
	
	@Override
	public EasyUIPage getUserOfRoleList(Integer userid,EasyUIPage page,SessionBean sessionBean) {
		page.setRows(userManagerDao.getUserOfRoleList(userid,page,sessionBean));
		page.setTotal(userManagerDao.getUserOfRoleListCount(userid,sessionBean));
		return page;
	}
	
	@Override
	public EasyUIPage getAllRoleList(EasyUIPage page,Integer orgid,SessionBean sessionBean) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        map.put("orgid", orgid);
        map.put("roleids",sessionBean.getRoleId());
		page.setRows(userManagerDao.getAllRoleList(map));
		page.setTotal(userManagerDao.getAllRoleListCount(map));
		return page;
	}
	
	/**
	 * 
	 * @Title: updateUserManager
	 * @Description: TODO(修改用户信息)
	 * @param @param userManager
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@Deprecated
	public boolean updateUserManager(UserManager userManager) {
		return userManagerDao.updateUserManager(userManager);
	}
	
	public boolean delUserOfRole(Integer userid) {
		return userManagerDao.delUserOfRole(userid);
	}
	
	public boolean delUserOfOrg(Integer userid) {
		return userManagerDao.delUserOfOrg(userid);
	}
	
	public Org getOrgDetail(Integer orgid){
		return userManagerDao.getOrgDetail(orgid);
	}
	
	public Integer getUseridBySeq(){
		return userManagerDao.getUseridBySeq();
	}

	public Integer getRoleidBySeq(){
		return userManagerDao.getRoleidBySeq();
	}
	
	
	@Override//弃用
	public List<TreeNode> getUserTree(String roleid,SessionBean sessionBean) {
		// TODO Auto-generated method stub
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("roleId", roleid);
		map.put(AppConst.STATUS, AppConst.STATUS_ENABLE);
		List<TreeNode> list = roleManagerDao.getSqlMapClientTemplate().queryForList("Role.queryLogiSystem", sessionBean.getRegionCode());
		roleSysNodes = (Map) roleManagerDao.getSqlMapClientTemplate().queryForMap("UserManager.queryRolePermission1", map, "key","value");
		setSysCheck(list);
		return list;
	}
	
	@Override
	public List<TreeNode> sysTreeOrg(Integer userid) {
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("parentId", 26);
		map1.put("status", AppConst.STATUS_ENABLE);
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userid", userid);
		map.put(AppConst.STATUS, AppConst.STATUS_ENABLE);
		List<TreeNode> list = orgDao.getSqlMapClientTemplate().queryForList("Org.selectOrgTreeByPid", map1);
		roleSysNodes = (Map) userManagerDao.getSqlMapClientTemplate().queryForMap("UserManager.queryOrgUser", map, "key","value");
		setSysCheck(list);
		return list;
	}
	
	private void setSysCheck(List<TreeNode> list){
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TreeNode treeNode = (TreeNode) iterator.next();
			if (treeNode.getChildren() != null && treeNode.getChildren().size()>0){
				setSysCheck(treeNode.getChildren());
			}else{
				if(roleSysNodes.containsKey(treeNode.getAttributes().get("ID"))){
					treeNode.setBoo(1);
				}
			}
		}
	}
	
	public List<Role> getRoleByUserid(Integer userid){
		return userManagerDao.getRoleByUserid(userid);
	}
	
	@Override
	public List<TreeNode> sysReTreeRole(String roleid) {
		return userManagerDao.sysReTreeRole(roleid);
	}
	
	//===========
	public List<LogiSystem> queryLogisystem(String roleid){
		return userManagerDao.queryLogisystem(roleid);
	}
	public List<Model> queryMods(String roleid,Integer sysid){
		return userManagerDao.queryMods(roleid,sysid);
	}
	public List<Func> queryFunc(String roleid,Integer modid){
		return userManagerDao.queryFunc(roleid,modid);
	}
	public List<Ops> queryOps(String roleid,Integer funcid){
		return userManagerDao.queryOps(roleid,funcid);
	}
	//===============
	public void saveUserPostion(UsersPositions up){
		userManagerDao.saveUserPostion(up);
	}
	
	public boolean deleteUsersPositions(Integer userid) {
		return userManagerDao.deleteUsersPositions(userid);
	}
	
	/**
	 * @Title: loginidIsExsit
	 * @Description: TODO(验证当前要保存的人员登录名是否已经存在)
	 * @param @param loginid
	 * @param @return    设定文件
	 * @return Integer    返回类型
	 * @throws
	 */
	public Integer loginidIsExsit(String loginid){
		return userManagerDao.loginidIsExsit(loginid);
	}
	
	public Integer getUserOrgid(Integer userid){
		return userManagerDao.getUserOrgid(userid);
	}
	
	public void saveUserOrg(String userid,Integer orgid){
		//分割字符串
		String[] strArr=userid.split(","); 
		for(int i = 0; i < strArr.length; i++){
			//根据userid 清除职位关系和原组织机构关系
			userManagerDao.deleteUsersPositions(Integer.valueOf(strArr[i]));
			userManagerDao.delUserOfOrg(Integer.valueOf(strArr[i]));
			//删除成功后保存新的组织关系
			OrgUsers ou = new OrgUsers();
			ou.setUserid(Integer.valueOf(strArr[i]));
			ou.setOrgid(orgid);
			ou.setStatus(AppConst.STATUS_ENABLE);
			userManagerDao.addUserOfOrg(ou);
		}
	}
	
	public void saveUserOrgPost(Integer userid,Integer orgid,Integer positionid){
		//根据userid 清除职位关系和原组织机构关系
		userManagerDao.deleteUsersPositions(Integer.valueOf(userid));
		userManagerDao.delUserOfOrg(Integer.valueOf(userid));
		
		//删除成功后保存新的组织关系
		OrgUsers ou = new OrgUsers();
		ou.setUserid(Integer.valueOf(userid));
		ou.setOrgid(orgid);
		ou.setStatus(AppConst.STATUS_ENABLE);
		userManagerDao.addUserOfOrg(ou);
		// 保存新的职位信息
		UsersPositions up = new UsersPositions();
		up.setUserid(Integer.valueOf(userid));
		up.setPositionid(positionid);
		up.setStatus(AppConst.STATUS_ENABLE);
		userManagerDao.saveUserPostion(up);
	}
	
	//弃用
	@Deprecated
	public boolean saveSetUserPassword(Integer userid){
		//根据userid获取用户信息
		UserManager um = new UserManager();
		um = userManagerDao.initUserManager(userid);
		//修改用户密码为用户的loginid
		return userManagerDao.reSetUserPassword(um);
	}
	
	/**
	 * 新增用户
	 */
	public boolean saveUser(UserManager userManager, Role role, String[] arr,
			String str, String orgid, String positionid) {
		boolean _result = false;
		//先保存人员信息，页签一的保存方法
		_result = saveUserManager(userManager);
		Integer userid = userManager.getId();
		//页签3的处理方法
		if(StringUtil.isNotBlank(orgid)){
			 OrgUsers ou = new OrgUsers();
			 ou.setUserid(userid);
			 ou.setOrgid(Integer.valueOf(orgid));
			 ou.setStatus(AppConst.STATUS_ENABLE);
			 _result = saveUserOfOrg(ou);
		}
		//页签4的处理方法,处理页签二 人员角色信息,处理之前先判断页签4是否有数据变化，如果有则直接处理页签4，没有则处理页签2
		if(StringUtil.isNotBlank(role.getrName()))
		{
			//进入页签的处理方法
			if(StringUtil.isNotBlank(str))
			{
				//先删除当前人员已经拥有的角色
				_result = delUserOfRole(userid);
				//删除成功后,新增加人员角色关系
				String[] strArr=str.split(","); 
				for(int i = 0; i < strArr.length; i++){
					UserRoles ur = new UserRoles();
					ur.setUserid(userid);
					ur.setRoleid(Integer.valueOf(strArr[i]));
					ur.setStatus(AppConst.STATUS_ENABLE);
					_result = saveUserRolesOfUser(ur);
				}
			}else
			{
				//为新增的角色获取一个序列值
				Integer roleid = getRoleidBySeq();
				//保存角色信息
				role.setStatus(AppConst.STATUS_ENABLE);
				role.setId(roleid);
				_result = saveRoleOfUser(role);
				//保存角色权限信息
				String[] arr2;
				RolesPermissions rp = new RolesPermissions();
				for(int i = 0; i < arr.length; i++){
					//处理树节点
					arr2 = arr[i].split(":");
					rp.setRoleid(roleid);
					rp.setPermissionid(Integer.valueOf(arr2[1]));
					rp.setStatus(AppConst.STATUS_ENABLE);
					_result = saveRolePermissions(rp);
				}
				//保存人员角色关系信息
				UserRoles userRoles = new UserRoles();
				userRoles.setUserid(userid);
				userRoles.setRoleid(roleid);
				userRoles.setStatus(AppConst.STATUS_ENABLE);
				_result = saveUserRolesOfUser(userRoles);
			}
		}
		
		//先清除原有的人员职位关系
		_result = deleteUsersPositions(userid);
		UsersPositions up = new UsersPositions();
		up.setUserid(userid);
		up.setPositionid(Integer.valueOf(positionid));
		up.setStatus(AppConst.STATUS_ENABLE);
		saveUserPostion(up);
		return _result;
	}
	
	//lp--3仅修改人员信息
	public boolean updateUser(UserManager userManager,String positionid){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userManager.getId());
		map.put("positionid", positionid);
		userManagerDao.update("UserManager.updateUserPostionByUid", map);
		
		Boolean flag = userManagerDao.updateUserManager(userManager);
		return flag;
	}
	
	/**
	 * 修改人员
	 */
	public boolean updateUser(UserManager userManager, Role role, String[] arr,
			String str, String positionid) {
		boolean result = false;
		result = updateUserManager(userManager);
		Integer userid = userManager.getId();
		if(StringUtil.isNotBlank(role.getrName()))
		{
			if(StringUtil.isNotBlank(str))
			{
				//先删除当前人员已经拥有的角色
				result = delUserOfRole(userid);
				//删除成功后,新增加人员角色关系
				String[] strArr=str.split(","); 
				for(int i = 0; i < strArr.length; i++){
					UserRoles ur = new UserRoles();
					ur.setUserid(userid);
					ur.setRoleid(Integer.valueOf(strArr[i]));
					ur.setStatus(AppConst.STATUS_ENABLE);
					result = saveUserRolesOfUser(ur);
				}
			}
		}else
		{
			//处理数据前先删除所有已经拥有的角色
			result = delUserOfRole(userid);
				//为新增的角色获取一个序列值
			Integer roleid = getRoleidBySeq();
			//保存角色信息
			role.setStatus(AppConst.STATUS_ENABLE);
			role.setId(roleid);
			saveRoleOfUser(role);
			//保存角色权限信息
			String[] arr2 ;
			RolesPermissions rp = new RolesPermissions();
			for(int i = 0; i < arr.length; i++){
				//处理树节点
				arr2 = arr[i].split(":");
				rp.setRoleid(roleid);
				rp.setPermissionid(Integer.valueOf(arr2[1]));
				rp.setStatus(AppConst.STATUS_ENABLE);
				saveRolePermissions(rp);
			}
			//保存人员角色关系信息
			UserRoles userRoles = new UserRoles();
			userRoles.setUserid(userid);
			userRoles.setRoleid(roleid);
			userRoles.setStatus(AppConst.STATUS_ENABLE);
			saveUserRolesOfUser(userRoles);
		}
		//先清除原有的人员职位关系
		result = deleteUsersPositions(userid);
		UsersPositions up = new UsersPositions();
		up.setUserid(userid);
		up.setPositionid(Integer.valueOf(positionid));
		up.setStatus(AppConst.STATUS_ENABLE);
		saveUserPostion(up);
		return result;
	}
	
	//分配角色
	public boolean updateUserRole(String uid,String roleids){
		boolean flag;
		//先删除当前人员已经拥有的角色
		delUserOfRole(Integer.parseInt(uid));
		//新增人员角色关系
		String[] roleidsStr = roleids.split(",");
		for(int i = 0; i < roleidsStr.length; i++){
			if(!"".equals(roleidsStr[i])){
				UserRoles ur = new UserRoles();
				
				ur.setUserid(Integer.parseInt(uid));
				ur.setRoleid(Integer.valueOf(roleidsStr[i]));
				ur.setStatus(AppConst.STATUS_ENABLE);
				saveUserRolesOfUser(ur);	//新增	
			}
		}
		return true;
	}
	
	@Override//没有orgid
	public EasyUIPage getUserManagerListByLike(EasyUIPage page,String userName,String usertype,String loginid,String ownerid) {
			page.setRows(userManagerDao.getUserManagerListByLike(page,userName,usertype,loginid,ownerid));//模糊查询的条件
			page.setTotal(userManagerDao.getUserManagerCountByLike(userName,usertype,loginid,ownerid));
		
		return page;
	}
	
	@Override//有orgid
	public EasyUIPage getUserManagerListByLikeAndOrgid(EasyUIPage page,int orgid,String userName,String usertype,String loginid,String ownerid){
		page.setRows(userManagerDao.getUserManagerListByLikeAndOrgiD(page,orgid,userName,usertype,loginid,ownerid));//模糊查询的条件
		page.setTotal(userManagerDao.getUserManagerCountByLikeAndOrgiD(orgid,userName,usertype,loginid,ownerid));
		return page;
	}
	
	public Integer getUserPostionByUId(int userid){
		Integer postionId = (Integer) userManagerDao.queryForObject("UserManager.getUserPostionByUId", userid);
		
		return postionId;
	}
	
	public String getOrgNameByUserid(int userid){
		return (String)userManagerDao.queryForObject("UserManager.getOrgNameByUserid", userid);
	}

	@Override
	public List<Map<String, String>> getUsersByOrgId(int orgId) {
		return userManagerDao.getUsersByOrgId(orgId);
	}
}
