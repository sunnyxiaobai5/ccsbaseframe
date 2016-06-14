package cn.ccsgroup.ccsframework.tree.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;

@Repository("treeDao")
public class TreeDao extends BaseDaoImpl {
	
	
	/**
	 * @Title: findCurrentOrg
	 * @Description: TODO(获取当前组织机构)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findCurrentOrg(Integer userId)
	{
		return this.getSqlMapClientTemplate().queryForList("Tree.findCurrentOrg", userId);
	}
	
	/**
	 * @Title: findCurrentOrg
	 * @Description: TODO(获取当前的区域编码)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findRegionTreeByUserId(Integer userId)
	{
		return this.getSqlMapClientTemplate().queryForList("Tree.findRegionTreeByUserId", userId);
	}
	
	/**
	 * @Title: findCurrentOrg
	 * @Description: TODO(区域管理   获取当前用户下的区域管理树节点)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> regManTreeByRegionCodeAndChildRows(String regionCode)
	{
		return this.getSqlMapClientTemplate().queryForList("Tree.regionManageTreeByRegionCode", regionCode);
	}
	
	public List<TreeNode> regManTreeAllChildByRegionPid(String regionPid)
	{
		return this.getSqlMapClientTemplate().queryForList("Tree.regionManageTreeByRegionPid", regionPid);
	}
	
	public List<TreeNode> regManTreeChildByRegionPid(String regionPid)
	{
		return this.getSqlMapClientTemplate().queryForList("Tree.regionManageChildren", regionPid);
	}
	/**
	 * @Title: findChildOrgByPid
	 * @Description: TODO(获取子类组织机构)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findChildOrgByPid(Integer pid)
	{
		return this.getSqlMapClientTemplate().queryForList("Tree.findChildOrgByPid", pid);
	}
	
	
	public List<TreeNode> findAllChildByPid(Integer pid)
	{
		return this.getSqlMapClientTemplate().queryForList("Tree.getChildOrg", pid);
	}
	
	/**
	 * @Title: findAllRegionByUser
	 * @Description: TODO(获取用户所有管辖区域)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findAllRegionByUser(Integer userId)
	{
		return this.getSqlMapClientTemplate().queryForList("Tree.findAllComboRegionByUser", userId);
	}
	
	/**
	 * @Title: findFirstRegionByUser
	 * @Description: TODO(获取第一级区域)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findFirstRegionByUser(Integer userId)
	{
		return this.getSqlMapClientTemplate().queryForList("Tree.findBaseComboRegionByUser", userId);
	}
	
	/**
	 * @Title: findChildrenCombo
	 * @Description: TODO(子级列表)
	 * @param @param pid
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findChildrenCombo(Integer pid)
	{
		return this.getSqlMapClientTemplate().queryForList("Tree.findChildComboRegionByPid", pid);
	}

	public List<TreeNode> findOrgTreeByUserIdOwner(Integer userId) {
		// TODO Auto-generated method stub
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("userId", userId);
		map.put("orgId", 0);
		List<TreeNode>  list  = queryForList("Tree.findOrgTreeByUserIdOwner", map);
		setChildren(userId, list, map);
		return list;
	}
	private void setChildren(Integer userId , List<TreeNode> list , Map<String,Integer> map){
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TreeNode tn = (TreeNode) iterator.next();
			map.put("orgId", Integer.valueOf(tn.getAttributes().get("ID").toString()));
			tn.setChildren(queryForList("Tree.findOrgTreeByUserIdOwner", map));
			if(tn.getChildren()!=null && !tn.getChildren().isEmpty()) setChildren(userId,tn.getChildren(),map);
		}
	}
	
	/**
	 * @Title: findAllRegionCodeCombo
	 * @Description: TODO(下来树选择)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findAllRegionCodeCombo(){
		return this.getSqlMapClientTemplate().queryForList("Tree.findAllRegionCodeCombo");
	}
	
	//
	public List<Ztree> findAllRegions(){
		return this.getSqlMapClientTemplate().queryForList("Tree.findAllRegions");
	}
	
	public List<Ztree> findStreet(String regioncode){
		return this.getSqlMapClientTemplate().queryForList("Tree.findStreet",regioncode);
	}

	public List<Ztree> findCommunity(String streetcode){
		return this.getSqlMapClientTemplate().queryForList("Tree.findCommunity",streetcode);
	}
	
	/**
	 * @Title: findAllOrgExistsOwnerCombo
	 * @Description: TODO(获取所有包含当前组织)
	 * @param @param map
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findAllOrgExistsOwnerCombo(Map<String, Object> map)
	{
		return this.getSqlMapClientTemplate().queryForList("Tree.findAllOrgExistsOwnerCombo", map);
	}
	
	public List<TreeNode> findAllOrgAndPost(Integer oid)
	{
		return this.getSqlMapClientTemplate().queryForList("Tree.findAllOrgAndPost" , oid);
	}
}
