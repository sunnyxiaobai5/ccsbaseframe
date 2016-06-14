/**
 * 
 */
package cn.ccsgroup.ccsframework.sysManager.service;

import java.util.List;
import java.util.Map;

import cn.ccsgroup.ccsframework.base.entity.Dict;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.entity.Role;
import cn.ccsgroup.ccsframework.entity.SMS;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/**
 * @Package:      [cn.ccsgroup.ccsframework.sysManager.service]  
 * @ClassName:    [UserPowerService]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-12-18]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-18，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [版本号]
 */
public interface UserPowerService {
	
	
	public List<LogiSystem> getUserSysPower();
	
	public List<Map<String, Object>> getUserMods(String username); 
	
	/**
	 * 
	 * @Title: getUser
	 * @Description: TODO(登录验证用户)
	 * @param @param user
	 * @param @return    设定文件
	 * @return UserManager    返回类型
	 * @throws
	 */
	public UserManager getUser(UserManager user);

	//获取用户所有角色
	public List<Role> getUserRole(Integer id);
	//获取用户所有区域代码
	public List<String> countUserOrgRegionCode(Integer id);


	public List<Model> getRoleMods(SessionBean sessionBean, Integer integer);

	public List<LogiSystem> getRolSys(SessionBean sessionBean);
	//获取用户所有组织机构代码
	public Dict getUserOrganization(Integer id);
	//获取用户所有系统代码，根据组织机构代码
	public List<Dict> getUserSystem(List<Integer> roleIds);

	public List<Dict> getUserPosition(Integer id);
	//根据区域编码取角色
	public List<Role> getUserRole(Integer id, Integer regionCode);

	public Dict getSystem(Integer briefName);
	
	public List<Role> findRolesListByLogin(String loginId);
	
	public EasyUIPage findAllRoles(EasyUIPage page);

	//l 检查用户--修改密码用
	public UserManager checkUser(String loginId,String Pwd);
	
	//改密码
	public Boolean updateUserPwd(String newpassword,int userId);
	
	public String getRegionNameByCode(String code);
	
}
