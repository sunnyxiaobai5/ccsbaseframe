/**
 * 
 */
package cn.ccsgroup.ccsframework.userManager.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.entity.Org;
import cn.ccsgroup.ccsframework.entity.UserManager;

/**
 * @Package:      [cn.ccsgroup.ccsframework.userManager.dao]  
 * @ClassName:    [OrgDao]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-12-25]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-25，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [版本号]
 */
@Repository("OrgDao")
public class OrgDao extends BaseDaoImpl{

	/**
	 * 分页查询组织机构
	 * @return
	 */
	public List<Org> findAllOrgByPage(Map<String, Object> map)
	{
		return null;
	}
	
	public Integer findAllOrgByCount(Map<String, Object> map)
	{
//		return (Integer)this.getSqlMapClientTemplate().queryForObject("Org.findAllOrgByCount", map);
		return 0;
	}
	
	
	/**
	 * 修改组织机构
	 * @param org
	 * @return
	 */
	public boolean updateOrg(Org org)
	{
		int k = (Integer)this.getSqlMapClientTemplate().update("Org.updateOrg", org);
		return  k > 0 ? true : false;
	}
	
	/**
	 * 查询所有排序的list
	 * @param fid
	 * @return
	 */
	public List<Org> findAllChildByFid(Integer fid)
	{
		return this.getSqlMapClientTemplate().queryForList("Org.findAllChildByFid", fid);
	}
	
	/**
	 * 根据定位sort修改排序列
	 * @return
	 */
	public boolean updateEnlargeSort(Map<String, Object> map)
	{
		int k = (Integer)this.getSqlMapClientTemplate().update("Org.updateSortByKey", map);
		return k > 0 ? true : false;
	}
	
	/**
	 * 将对应序列调小
	 * @return
	 */
	public boolean updateReduceSort(Map<String, Object> map)
	{
		int k = (Integer)this.getSqlMapClientTemplate().update("Org.updateReduceSortByKey" , map);
		return k > 0 ? true : false;
	}
	
	
	/**
	 * 新增组织机构
	 * @param org
	 * @return
	 */
	public boolean addOrg(Org org)
	{
		Org vo = (Org)this.getSqlMapClientTemplate().insert("Org.addOrg", org);
		return vo == null ? true : false;
	}
	
	
	/**
	 * 获取组织机构树
	 * @return
	 */
	public List<TreeNode> selectOrgTreeByPid(Map<String, Object> map)
	{
		return (List<TreeNode>)this.getSqlMapClientTemplate().queryForList("Org.selectOrgTreeByPid", map);
	}
	
	/**
	 * 获取组织机构
	 * @param orgId
	 * @return
	 */
	public Org selectOrgById(Integer orgId)
	{
		return (Org)this.getSqlMapClientTemplate().queryForObject("Org.selectOrgById", orgId);
	}
	
	/**
	 * 
	 * @Title: deleteOrg
	 * @Description: TODO(删除组织机构)
	 * @param @param orgId
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean deleteOrg(Map<String, Object> map)
	{
		int k = (Integer)this.getSqlMapClientTemplate().update("Org.deleteOrgById", map);
		return k > 0 ? true : false;
	}
	
	/**
	 * @Title: selectUserByOrgId
	 * @Description: TODO(获取组织机构下用户)
	 * @param @param map
	 * @param @return    设定文件
	 * @return List<UserManager>    返回类型
	 * @throws
	 */
	public List<UserManager> selectUserByOrgId(Map<String, Object> map)
	{
		return (List<UserManager>) this.getSqlMapClientTemplate().queryForList("Org.selectUserByOrgId", map);
	}
}
