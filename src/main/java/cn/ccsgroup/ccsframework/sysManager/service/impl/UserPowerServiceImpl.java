package cn.ccsgroup.ccsframework.sysManager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsgroup.ccsframework.base.entity.Dict;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.entity.Role;
import cn.ccsgroup.ccsframework.entity.SMS;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.sysManager.dao.UserPowerDao;
import cn.ccsgroup.ccsframework.sysManager.service.UserPowerService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

@Service("userPowerService")
@Transactional(readOnly=true)
public class UserPowerServiceImpl implements UserPowerService {

	@Resource(name="userPowerDao")
	private UserPowerDao userPowerDao;
	
	@Override
	public List<Map<String, Object>> getUserMods(String briefName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("briefName", briefName);

		return userPowerDao.getUserMods(map);
	}

	public List<LogiSystem> getUserSysPower() {
		return userPowerDao.getUserSysPower();
	}

	@Override
	public UserManager getUser(UserManager user) {
		// TODO Auto-generated method stub
		return userPowerDao.getUser(user);
	}

	@Override
	public List<Role> getUserRole(Integer id) {
		// TODO Auto-generated method stub
		return  userPowerDao.getUserRole(id);
	}

	@Override
	public List<String> countUserOrgRegionCode(Integer id) {
		// TODO Auto-generated method stub
		return userPowerDao.getUserRegionCode(id);
	}

	@Override
	public List<Model> getRoleMods(SessionBean sessionBean ,Integer id) {
		// TODO Auto-generated method stub
		return userPowerDao.getRoleMods(sessionBean ,id);
	}

	@Override
	public List<LogiSystem> getRolSys(SessionBean sessionBean) {
		// TODO Auto-generated method stub
		return userPowerDao.getRoleSys(sessionBean);
	}

	@Override
	public Dict getUserOrganization(Integer id) {
		// TODO Auto-generated method stub
		return userPowerDao.queryUserOrganization(id);
	}

	@Override
	public List<Dict> getUserSystem(List<Integer> roles) {
		// TODO Auto-generated method stub
		return userPowerDao.queryUserSystem(roles);
	}

	@Override
	public List<Dict> getUserPosition(Integer id) {
		// TODO Auto-generated method stub
		return userPowerDao.queryUserPosition( id) ;
	}

	@Override
	public List<Role> getUserRole(Integer id, Integer regionCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dict getSystem(Integer briefName) {
		// TODO Auto-generated method stub
		return userPowerDao.querySystem( briefName) ;
	}

	@Override
	public List<Role> findRolesListByLogin(String loginId) {
		// TODO Auto-generated method stub
		return userPowerDao.findRolesListByLogin(loginId);
	}
	
	/**ll
	 * 查询所有的有效角色
	 */
	public EasyUIPage findAllRoles(EasyUIPage page){
		page.setRows(userPowerDao.findAllRoles(page));
		page.setTotal(userPowerDao.findAllRolesCount());
		return page;
	}
	
	//l 检查用户--修改密码用
	public UserManager checkUser(String loginId,String Pwd){
		return userPowerDao.checkUser(loginId,Pwd);
	}
	
	public Boolean updateUserPwd(String newpassword,int userId){
		return userPowerDao.updateUserPwd(newpassword,userId);
	}
	
	@Override
	public String getRegionNameByCode(String code) {
		// TODO Auto-generated method stub
		return (String) userPowerDao.queryForObject("UserPower.queryRegionNameByCode", code);
	}
}
