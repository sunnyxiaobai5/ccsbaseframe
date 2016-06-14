package cn.ccsgroup.ccsframework.tree.service;

import java.util.List;
import java.util.Map;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.entity.Org;


public interface TreeService {

	/**
	 * @Title: findOrgAndRegionTree
	 * @Description: TODO(获取组织机构树)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findOrgAndRegionTree(Integer userId, Integer childRows);
	
	
	/**
	 * 
	 * @Title: findCurrentOrgTree
	 * @Description: TODO(获取标准组织机构树)
	 * @param @param userId
	 * @param @param childRows
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findCurrentOrgTree(Integer userId, Integer childRows);

	/**
	 * 
	 * @Title: findRegionTree
	 * @Description: TODO(获取区域树，根据传入的区域ID取 id下级的区域)
	 * @param @param code 
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findRegionTree(Integer code);

	public List<TreeNode> sysTree(SessionBean sessionBean);

	public List<TreeNode> sysTree(Integer roleid, SessionBean sessionBean);

	public List<TreeNode> findRegionTreeByRoleId(Integer roldId, Integer userId);

	public List<TreeNode> findRegionTreeByUserId(Integer userId);

	public List<TreeNode> findSysTreeByOrgId(String[] ordIds);

	public List<TreeNode> findOrgTreeByUserId(Integer userId);

	public List<TreeNode> findCurrentOrgTree(Integer userId, Integer roleId,int i);
	
	public List<TreeNode> findCurrentRegionByUser(Integer userId,List<String> vList , Integer index);


	public List<TreeNode> findSysTreeByRoleOrg(String[] orgIds, Integer roleId);


	public List<TreeNode> findOrgTreeByUserIdOwner(Integer userId, int i);


	List<TreeNode> regManTreeByRegionCodeAndChildRows(String regionCode,Integer childRows);
	
	public List<TreeNode> findAllRegionCodeCombo();
	
	public List<Ztree> findAllRegions();
	
	public List<TreeNode> findAllOrgExistsOwnerCombo(Map<String, Object> map);
	
	public List<TreeNode> findAllOrgAndPost(Map<String, Object> map , Integer rows);
	
	public List<TreeNode> findChoosedRolePermissions(List<Integer> roleIds);
	
	//获取组织机构树--zTree  lp3
	public List<Org> getOrgzTree(int orgid);

    public List<Org> getSearchableOrgzTree(int orgid);
	
	//获取组织机构和职位树(变更组织机构用)--zTree  lp3
	public List<Ztree> getOrgAndPosition(int orgid);
	
	//获取所有权限树--zTree  lp3
	public List<Ztree> getSysTree(SessionBean sessionBean);
	
	//根据系统id获取权限树--zTree  lp3
	public List<Ztree>getSysTreeById(int sysid);
	
	//获取角色权限树(编辑角色用)--zTree  lp3
	public List<Ztree> getRoleSysTree(int roleid,SessionBean sessionBean);
	
	//获取当前登录用户的权限树
	public List<Ztree> getUserSysTree(SessionBean sessionBean);
	
	public List<Ztree> getUserSysTreeUtil(List<Integer> roleids);
	
	//根据区域查询所有的街道
	public List<Ztree> findAllStreetByRegionCode(String regionCode);
	
	//根据街道查询所有的社区
	public List<Ztree> findAllCommunityByStreetCode(String streetCode);

    public Integer getSysTopOrgId(Integer id,Integer topOrgId);

    public Integer getTopOrgId();

}
