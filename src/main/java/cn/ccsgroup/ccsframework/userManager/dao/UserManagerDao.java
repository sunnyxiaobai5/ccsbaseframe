/**
 * 
 */
package cn.ccsgroup.ccsframework.userManager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.components.AppConst;
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
 * @Package:      [cn.ccsgroup.ccsframework.usermanager.dao.UserManagerDao.java]  
 * @ClassName:    [UserManagerDao]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [zengheng]   
 * @CreateDate:   [2013-12-10 下午02:53:54]   
 * @UpdateUser:   [zengheng(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-10 下午02:53:54，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@Repository("userManagerDao")
public class UserManagerDao extends BaseDaoImpl {
	
	//lp3 仅添加用户信息
	public int addUserManagerInfo(UserManager userManager){
		int uid = (Integer)this.getSqlMapClientTemplate().insert("UserManager.addUserManagerInfo", userManager);
		return uid;
	}
	
	//lp3 添加用户组织机构信息
	public int addUserOfOrgInfo(OrgUsers orgUsers){
		int orguserid = (Integer)this.getSqlMapClientTemplate().insert("UserManager.addUserOfOrgInfo", orgUsers);
		return orguserid;
	}
	
	/**
	 * ??????
	 * @Title: addUserManager
	 * @Description: TODO(新增用户信息)
	 * @param @param userManager
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean addUserManager(UserManager userManager)
	{
		UserManager vCheck = (UserManager)this.getSqlMapClientTemplate().insert("UserManager.insertUserManager", userManager);
		return vCheck != null ? true : false;
	}
	
	public boolean addUserRolesOfUser(UserRoles userRoles)
	{
		UserRoles vCheck = (UserRoles)this.getSqlMapClientTemplate().insert("UserManager.addUserRolesOfUser", userRoles);
		return vCheck != null ? true : false;
	}
	
	public boolean addUserOfOrg(OrgUsers orgUsers)
	{
		UserRoles vCheck = (UserRoles)this.getSqlMapClientTemplate().insert("UserManager.addUserOfOrg", orgUsers);
		return vCheck != null ? true : false;
	}
	
	public boolean addRoleOfUser(Role role)
	{
		Role vCheck = (Role)this.getSqlMapClientTemplate().insert("UserManager.addRoleOfUser", role);
		return vCheck != null ? true : false;
	}
	
	public boolean addRolePermissions(RolesPermissions rp)
	{
		RolesPermissions vCheck = (RolesPermissions)this.getSqlMapClientTemplate().insert("UserManager.addRolePermissions", rp);
		return vCheck != null ? true : false;
	}
	
	/**
	 * 
	 * @Title: initUserManager
	 * @Description: TODO(根据用户id查询该用户信息)
	 * @param @param userid
	 * @param @return    设定文件
	 * @return UserManager    返回类型
	 * @throws
	 */
	public UserManager initUserManager(Integer id)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		UserManager userManager = (UserManager)this.getSqlMapClientTemplate().queryForObject("UserManager.initUserManager", map);
		return userManager;
	}
	
//	public List<UserManager> getUserManagerList()
//	{
//		List<UserManager> list = null;
//		list = (List<UserManager>)this.getSqlMapClientTemplate().queryForList("UserManager.selectUserManagerByPage");
//		return list;
//	}
	
	public List<UserManager> getUserManagerList(HashMap<String, Object> map){

		return this.getSqlMapClientTemplate().queryForList("UserManager.getUserManagerList",map);
	}
	
	public List<Role> getUserOfRoleList(Integer userid,EasyUIPage page,SessionBean sessionBean){
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("userid", userid);
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		map.put("sessionBean", sessionBean);
		return this.getSqlMapClientTemplate().queryForList("UserManager.getUserOfRoleList",map);
	}
	
	public List<Role> getAllRoleList(Map map){
		return this.getSqlMapClientTemplate().queryForList("UserManager.getAllRoleList",map);
	}
	
	public Integer getAllRoleListCount(Map map){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("UserManager.getAllRoleListCount",map);
	}
	
	public Integer getUserOfRoleListCount(Integer userid,SessionBean sessionBean){
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("userid", userid);
		map.put("sessionBean", sessionBean);
		return (Integer)this.getSqlMapClientTemplate().queryForObject("UserManager.getUserOfRoleListCount",map);
	}
	
	public Integer getUserManagerCount(HashMap<String, Object> map){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("UserManager.getUserManagerCount",map);
	}
	
	
	
	/**
	 * 
	 * @Title: deleteUserManager
	 * @Description: TODO(根据用户id删除该用户信息)
	 * @param @param userid
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean deleteUserManager(Integer id)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		//map.put("status", AppConst.STATUS_DISABLE);
		//int i = this.getSqlMapClientTemplate().update("UserManager.deleteUserManager", map);
		int i = this.getSqlMapClientTemplate().delete("UserManager.deleteUserManager",map);
		return i > 0 ? true : false; 
	}
	
	public boolean delUserOfRole(Integer userid)
	{
		int i = this.getSqlMapClientTemplate().update("UserManager.delUserOfRole", userid);
		return i > 0 ? true : false; 
	}
	
	public boolean delUserOfOrg(Integer userid)
	{
		int i = this.getSqlMapClientTemplate().update("UserManager.delUserOfOrg", userid);
		return i > 0 ? true : false; 
	}
	
	/**
	 * 
	 * @Title: selectUserManagerByPage
	 * @Description: TODO(分页查询已有用户信息)
	 * @param @return    设定文件
	 * @return List<UserManager>    返回类型
	 * @throws
	 */
	public List<UserManager> selectUserManagerByPage()
	{
		List<UserManager> list = null;
		list = (List<UserManager>)this.getSqlMapClientTemplate().queryForList("UserManager.selectUserManagerByPage");
		return list;
	}
	
	/**
	 * @Title: updateUserManager
	 * @Description: TODO(仅修改用户信息)
	 * @param @param userManager
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean updateUserManager(UserManager userManager){
		int k =(int)this.getSqlMapClientTemplate().update("UserManager.updateUserManager",userManager);
		return k > 0 ? true : false;
	}
	
	public Org getOrgDetail(Integer orgid)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", orgid);
		Org org = (Org)this.getSqlMapClientTemplate().queryForObject("UserManager.getOrgDetail", map);
		return org;
	}
	
	public Integer getUseridBySeq(){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("UserManager.getUseridBySeq");
	}
	
	public Integer getRoleidBySeq(){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("UserManager.getRoleidBySeq");
	}
	
	public List<TreeNode> getUserTree(Integer roleid) {
		return this.getSqlMapClientTemplate().queryForList("Role.getUserTree", roleid);
	}
	
	public List<Role> getRoleByUserid(Integer userid) {
		return this.getSqlMapClientTemplate().queryForList("UserManager.getRoleByUserid", userid);
	}
	
	public List<TreeNode> sysReTreeRole(String roleid){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("roleid", roleid);
		List<TreeNode> list = this.getSqlMapClientTemplate().queryForList("UserManager.sysReTreeRole1", map);
		return list;
	}
	
	//=============
	public List<LogiSystem> queryLogisystem(String roleid){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("roleid", roleid);
		return this.getSqlMapClientTemplate().queryForList("UserManager.queryLogisystem", map);
	}
	public List<Model> queryMods(String roleid,Integer sysid){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("roleid", roleid);
		map.put("sysid", sysid);
		return this.getSqlMapClientTemplate().queryForList("UserManager.queryMods", map);
	}
	public List<Func> queryFunc(String roleid,Integer modid){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("roleid", roleid);
		map.put("modid", modid);
		return this.getSqlMapClientTemplate().queryForList("UserManager.queryFunc", map);
	}
	public List<Ops> queryOps(String roleid,Integer funcid){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("roleid", roleid);
		map.put("funcid", funcid);
		return this.getSqlMapClientTemplate().queryForList("UserManager.queryOps", map);
	}
	//===========--
	public List<Position> getUserPostion(Integer orgid){
		return this.getSqlMapClientTemplate().queryForList("UserManager.getUserPostion",orgid);
	}
	
	public void saveUserPostion(UsersPositions up)
	{
		this.getSqlMapClientTemplate().insert("UserManager.saveUserPostion", up);
	}
	
	public boolean deleteUsersPositions(Integer userid)
	{
		int i = this.getSqlMapClientTemplate().update("UserManager.deleteUsersPositions", userid);
		return i > 0 ? true : false; 
	}
	
	public Integer loginidIsExsit(String loginid){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("UserManager.loginidIsExsit",loginid);
	}
	
	public Integer getUserOrgid(Integer userid){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("UserManager.getUserOrgid",userid);
	}
	
	public boolean reSetUserPassword(UserManager um)
	{
		int k =(int)this.getSqlMapClientTemplate().update("UserManager.updateUserPassword",um);
		return k > 0 ? true : false;
	}
	
	public List<UserManager>  getUserManagerListByLike(EasyUIPage page,String userName,String usertype,String loginid,String ownerid){
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("userName", userName);
		map.put("usertype", usertype);
		map.put("loginid", loginid);
		map.put("ownerid", ownerid);
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		return this.getSqlMapClientTemplate().queryForList("UserManager.getUserManagerListByLike",map);
	}
	
	public Integer getUserManagerCountByLike(String userName,String usertype,String loginid,String ownerid){
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("userName", userName);
		map.put("usertype", usertype);
		map.put("loginid", loginid);
		map.put("ownerid", ownerid);
		return (Integer)this.getSqlMapClientTemplate().queryForObject("UserManager.getUserManagerCountByLike",map);
	}
	
	public List<UserManager>  getUserManagerListByLikeAndOrgiD(EasyUIPage page,int orgid,String userName,String usertype,String loginid,String ownerid){
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("userName", userName);
		map.put("usertype", usertype);
		map.put("loginid", loginid);
		map.put("ownerid", ownerid);
		map.put("orgid", orgid+"");
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		return this.getSqlMapClientTemplate().queryForList("UserManager.getUserManagerListByLikeAndOrgiD",map);
	}
	
	public Integer getUserManagerCountByLikeAndOrgiD(int orgid,String userName,String usertype,String loginid,String ownerid){
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("userName", userName);
		map.put("usertype", usertype);
		map.put("loginid", loginid);
		map.put("ownerid", ownerid);
		map.put("orgid", orgid+"");
		return (Integer)this.getSqlMapClientTemplate().queryForObject("UserManager.getUserManagerCountByLikeAndOrgiD",map);
	}

	/**
	 * 通过组织机构获取所有用户(包括子机构的)
	 * @param orgId
	 * @return
	 */
	public List<Map<String, String>> getUsersByOrgId(int orgId) {
		return this.queryForList("UserManager.getUsersByOrgId",orgId);
	}
}
