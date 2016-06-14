/**
 * 
 */
package cn.ccsgroup.ccsframework.sysManager.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.base.entity.Dict;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.entity.Role;
import cn.ccsgroup.ccsframework.entity.SMS;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/**
 * @Package:      [cn.ccsgroup.ccsframework.sysManager.dao]  
 * @ClassName:    [UserPowerDao]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-12-18]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-18，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [版本号]
 */
@Repository("userPowerDao")
public class UserPowerDao extends BaseDaoImpl{

	/**
	 * 获取用户树
	 * @return
	 */
	public List<Map<String, Object>> getUserMods(Map<String, Object> map)
	{
		return this.queryForList("UserPower.getUserMods",map);
	}
	
	public List<LogiSystem> getUserSysPower()
	{
		return this.queryForList("UserPower.getSysPower");
	}

	public UserManager getUser(UserManager user) {
		// TODO Auto-generated method stub
		user.setStatus(AppConst.STATUS_ENABLE);
		return (UserManager) this.queryForObject("UserPower.queryUser", user);
	}

	public List<Role> getUserRole(Integer id) {
		// TODO Auto-generated method stub
		return this.queryForList("UserPower.queryUserRole", id);
	}
	
	/**l
	 * @Description: TODO(根据登录的id(前提登录成功)返回该用户的角色列表)
	 * @param loginName		登录名
	 * @return List<Role>
	 * @throws
	 */
	public List<Role> findRolesListByLogin(String loginId){
		List<Role> list = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginId", loginId);
		list = this.getSqlMapClientTemplate().queryForList("UserPower.findRolesByLoginId",map);
		
		return list;
	}
	
	/**
	 * ll
	 * @Title: findAllRoles
	 * @Description: TODO(查询所有的有效角色)
	 * @return List<Role>
	 * @throws
	 */
	public List<Role> findAllRoles(EasyUIPage page){
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		List<Role> list = null;
		list = this.getSqlMapClientTemplate().queryForList("UserPower.findAllRoles",map);
		return list;
	}
	
	public Integer findAllRolesCount(){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("UserPower.findAllRolesCount");
	}

	public List<Model> getRoleMods(SessionBean sessionBean, Integer id) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sessionBean", sessionBean);
		map.put("sysId", id);
		List<Model> list = this.queryForList("UserPower.queryRoleMods", map);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Model model = (Model) iterator.next();
			map.put("modId", model.getId());
			model.setfList(this.queryForList("UserPower.queryModFunc", map));
		}
		return list;
	}

	public List<String> getUserRegionCode(Integer id) {
		// TODO Auto-generated method stub
		return this.queryForList("UserPower.queryUserRegionCode", id);
	}

	public List<LogiSystem> getRoleSys(SessionBean sessionBean) {
		// TODO Auto-generated method stub
		return this.queryForList("UserPower.queryRoleSys", sessionBean);
	}

	public Dict queryUserOrganization(Integer id) {
		// TODO Auto-generated method stub
		return (Dict) this.queryForObject("UserPower.queryUserOrganization", id);
	}

	public List<Dict> queryUserSystem(List<Integer> roles){
		// TODO Auto-generated method stub
		return this.queryForList("UserPower.queryUserSystem", roles);
	}

	public List<Dict> queryUserPosition(Integer id) {
		// TODO Auto-generated method stub
		return this.queryForList("UserPower.queryUserPosition", id);
	}

	public Dict querySystem(Integer briefName) {
		// TODO Auto-generated method stub
		return (Dict) this.queryForObject("UserPower.querySystem", briefName);
	}
	
	//l 检查用户--修改密码用
	public UserManager checkUser(String loginId,String Pwd){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginId", loginId);
		map.put("Pwd", Pwd);
		return (UserManager) this.queryForObject("UserPower.checkUser", map);
	}
	
	public Boolean updateUserPwd(String newpassword,int userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newpassword", newpassword);
		map.put("userId", userId);
		
		int i = (Integer)this.getSqlMapClientTemplate().update("UserPower.updateUserPwd", map);
		
		return i>0?true:false;
	}
}
