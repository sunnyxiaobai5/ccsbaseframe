package cn.ccsgroup.ccsframework.role.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.entity.Role;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
import cn.ccsgroup.ccsframework.utils.PageHelper;
@TypeAnnotation("角色管理")
public interface RoleManagerService {

	public List<Role> roleList(PageHelper pg, SessionBean sessionBean);

	//弃用 @MethodAnnotation("新增角色")
	//public int saveRole(Role role, List<TreeNodeBean> list, List<TreeNodeBean> list2, SessionBean sessionBean);

	public List<TreeNode> sysTree(SessionBean sessionBean);

	public List<TreeNode> queryUsersByRoleId(Integer id);

	@MethodAnnotation("注销角色")
	public int deleteRole(Integer id);
	
	public Role queryRoleById(Integer id);

	public EasyUIPage roleList(EasyUIPage page, SessionBean sessionBean);

	public List<UserManager> queryUsersListByRoleId(Integer id);

	public List<TreeNode> sysTree(Integer id, SessionBean sessionBean);

	public List<TreeNode> queryOrgUserTree(Integer roleid,
			SessionBean sessionBean);

	public EasyUIPage queryOrgUserList(EasyUIPage page,Integer orgId,Integer roleId);
	
	//带条件
	public EasyUIPage queryOrgUserList(EasyUIPage page,Integer orgId,Integer roleid,String userName,String loginid);	

	@MethodAnnotation("删除角色用户")
	public int deleteRoleUser(Integer roleId, Integer userId);

	public UserManager queryUserById(Integer userId);

	@MethodAnnotation("分配角色用户")
	public void saveRoleUser(Integer roleId, String[] ids,String[] currentPageIds);

	public Map<String,String> queryRoleUrl(SessionBean sessionBean);

	//public Map<String, String> queryModOps(Integer[] modIds);

	public int queryPriOps(String opsName, SessionBean sessionBean);
	
	@MethodAnnotation("修改用户角色")
	public int updateUserPerm(Role role, List<TreeNode> list, Integer userId,
			SessionBean sessionBean);

	public boolean queryRoleName(String getrName);
	
	//lp3 新增角色
	@MethodAnnotation("新增角色")
	public int saveRole(String orgid,List<Ztree> ztreeList,SessionBean sessionBean,Role role);
	
	//lp3新增的功能(根据组织机构查询角色)
	public EasyUIPage roleListByOrgid(EasyUIPage page, SessionBean sessionBean,int orgid,String roleName);
	
	//lp3 修改角色
	@MethodAnnotation("修改角色")
	public int updateTheRole(Role role, SessionBean sessionBean,List<Ztree> list);

    public Integer queryRealOrgId(Integer orgId);
}
