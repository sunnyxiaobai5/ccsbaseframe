
package cn.ccsgroup.ccsframework.userManager.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.entity.Func;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.entity.Ops;
import cn.ccsgroup.ccsframework.entity.Org;
import cn.ccsgroup.ccsframework.entity.OrgUsers;
import cn.ccsgroup.ccsframework.entity.Position;
import cn.ccsgroup.ccsframework.entity.Role;
import cn.ccsgroup.ccsframework.entity.RolesPermissions;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.entity.UserRoles;
import cn.ccsgroup.ccsframework.entity.UsersPositions;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.usermanager.service.UserManagerService.java]  
 * @ClassName:    [UserManagerService]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [zengheng]   
 * @CreateDate:   [2013-12-10 下午02:54:01]   
 * @UpdateUser:   [zengheng(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-10 下午02:54:01，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.userManager.service.UserManagerService.java]  
 * @ClassName:    [UserManagerService]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [zengheng]   
 * @CreateDate:   [2014-1-7 下午03:14:47]   
 * @UpdateUser:   [zengheng(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-7 下午03:14:47，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@TypeAnnotation("用户管理")
public interface UserManagerService {
	
	
	/** lp3 
	 * @Title: addUserManager
	 * @Description: TODO(仅新增用户和用户组织机构关系)
	 * @param @param userManager
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("新增人员")
	public void saveUserManagerInfo(UserManager userManager,String orgid,String positionid);
	
	
	/**
	 * 
	 * @Title: addUserManager
	 * @Description: TODO(新增用户)
	 * @param @param userManager
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("新增人员")
	public boolean saveUserManager(UserManager userManager);
	
	/**
	 * update by denglong
	 * @Title: saveUser
	 * @Description: TODO(新增用户)
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("新增人员")
	public boolean saveUser(UserManager userManager,Role role,String [] arr,
					String str,String orgid,String positionid);
	
	/**String userName,String usertype,String loginid,String ownerid
	 * @Title: updateUser
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("修改人员")
	public boolean updateUser(UserManager userManager,Role role,String [] arr,
			String str,String positionid);
	
	//分配角色
	@MethodAnnotation("人员分配角色")
	public boolean updateUserRole(String uid,String roleids);
	
	/**
	 * @Title: addUserRolesOfUser
	 * @Description: TODO(新增人员角色关系表数据)
	 * @param @param userRoles
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean saveUserRolesOfUser(UserRoles userRoles);
	
	/**
	 * @Title: addUserOfOrg
	 * @Description: TODO(新增人员组织机构关系)
	 * @param @param orgUses
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean saveUserOfOrg(OrgUsers orgUses);
	
	/**
	 * @Title: addRoleOfUser
	 * @Description: TODO(新增人员所属角色)
	 * @param @param role
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("新增人员所属角色")
	public boolean saveRoleOfUser(Role role);
	
	/**
	 * @Title: saveRolePermissions
	 * @Description: TODO(新增人员所属角色的角色权限关系)
	 * @param @param rp
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean saveRolePermissions(RolesPermissions rp);
	
	/**
	 * 
	 * @Title: selectUserManagerByPage
	 * @Description: TODO(分页查询)
	 * @param @return    设定文件
	 * @return List<UserManager>    返回类型
	 * @throws
	 */
	public List<UserManager> selectUserManagerByPage();

	/**
	 * 
	 * @Title: deleteUserManager
	 * @Description: TODO(根据用户id删除用户)
	 * @param @param userid
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("删除人员")
	public boolean deleteUserManager(Integer id);
	
	/**
	 * @Title: initUserManager
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param id
	 * @param @return    设定文件
	 * @return UserManager    返回类型
	 * @throws
	 */
	public UserManager initUserManager(Integer id);
	
	/**
	 * @Title: getUserManagerList
	 * @Description: TODO(点击组织机构树获取该组织机构下拥有的人员)
	 * @param @param orgid
	 * @param @param page
	 * @param @return    设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	public EasyUIPage getUserManagerList(Integer orgid,String userName,String loginid,String ownerid,EasyUIPage page);
	
	/**
	 * @Title: getUserOfRoleList
	 * @Description: TODO(获取该人员拥有的角色)
	 * @param @param userid
	 * @param @param page
	 * @param @return    设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	public EasyUIPage getUserOfRoleList(Integer userid,EasyUIPage page,SessionBean sessionBean);
	
	/**
	 * @Title: getAllRoleList
	 * @Description: TODO(获取所有角色信息)
	 * @param @param page
	 * @param @return    设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	public EasyUIPage getAllRoleList(EasyUIPage page,Integer orgid,SessionBean sessionBean);
	
	/**
	 * 
	 * @Title: updateUserManager
	 * @Description: TODO(修改用户信息)
	 * @param @param userManager
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("修改人员")
	public boolean updateUserManager(UserManager userManager);
	
	/**
	 * @Title: delUserOfRole
	 * @Description: TODO(根据userid删除人员角色关系)
	 * @param @param userid
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean delUserOfRole(Integer userid);
	
	
	/**
	 * @Title: delUserOfOrg
	 * @Description: TODO(根据userid删除人员组织机构关系)
	 * @param @param userid
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean delUserOfOrg(Integer userid);
	
	/**
	 * @Title: getOrgDetail
	 * @Description: TODO(获取组织机构详细信息)
	 * @param @param orgid
	 * @param @return    设定文件
	 * @return Org    返回类型
	 * @throws
	 */
	public Org getOrgDetail(Integer orgid);
	
	/**
	 * @Title: getUseridBySeq
	 * @Description: TODO(获取人员序列)
	 * @param @return    设定文件
	 * @return Integer    返回类型
	 * @throws
	 */
	public Integer getUseridBySeq();
	
	/**
	 * @Title: getRoleidBySeq
	 * @Description: TODO(获取角色序列)
	 * @param @return    设定文件
	 * @return Integer    返回类型
	 * @throws
	 */
	public Integer getRoleidBySeq();
	
	/**
	 * @Title: getUserTree
	 * @Description: TODO(获取人员的权限树)
	 * @param @param id
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> getUserTree(String id,SessionBean sessionBean);
	
	/**
	 * @Title: sysTreeOrg
	 * @Description: TODO(获取人员所属组织机构的树)
	 * @param @param id
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> sysTreeOrg(Integer id);
	
	/**
	 * @Title: getRoleByUserid
	 * @Description: TODO(获取人员拥有的角色信息)
	 * @param @param userid
	 * @param @return    设定文件
	 * @return List<Role>    返回类型
	 * @throws
	 */
	public List<Role> getRoleByUserid(Integer userid);
	
	public List<TreeNode> sysReTreeRole(String roleid);
	//=============
	public List<LogiSystem> queryLogisystem(String roleid);
	public List<Model> queryMods(String roleid,Integer sysid);
	public List<Func> queryFunc(String roleid,Integer modid);
	public List<Ops> queryOps(String roleid,Integer funcid);
	//==============
	public void saveUserPostion(UsersPositions up);
	public boolean deleteUsersPositions(Integer userid);
	
	/**
	 * @Title: loginidIsExsit
	 * @Description: TODO(验证当前要保存的人员登录名是否已经存在)
	 * @param @param loginid
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public Integer loginidIsExsit(String loginid);
	
	/**
	 * @Title: getUserOrgid
	 * @Description: TODO(根据用户id查询当前用户所在的组织机构)
	 * @param @param userid
	 * @param @return    设定文件
	 * @return Integer    返回类型
	 * @throws
	 */
	public Integer getUserOrgid(Integer userid);
	
	@MethodAnnotation("变更人员所在组织")
	public void saveUserOrg(String userid,Integer orgid);
	
	/**
	 * @Title: saveSetUserPassword
	 * @Description: TODO(重置人员密码)
	 * @param @param userid
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("重置人员密码")
	public boolean saveSetUserPassword(Integer userid);
	
	@MethodAnnotation("变更人员所在组织职位")
	public void saveUserOrgPost(Integer userid,Integer orgid,Integer positionid);
	
	//l
	public EasyUIPage getUserManagerListByLike(EasyUIPage page,String userName,String usertype,String loginid,String ownerid);
	
	//l
	public EasyUIPage getUserManagerListByLikeAndOrgid(EasyUIPage page,int orgid,String userName,String usertype,String loginid,String ownerid);
	
	//lp--3
	@MethodAnnotation("修改人员")
	public boolean updateUser(UserManager userManager,String positionid);
	
	//根据userid获取user的职位
	public Integer getUserPostionByUId(int userid);
	
	public String getOrgNameByUserid(int userid);

	/**
	 * 根据机构id获取该机构下的所有用户，包括子级机构的用户
	 * @param orgId 机构id
	 * @return
	 */
	List<Map<String,String>> getUsersByOrgId(int orgId);
}
