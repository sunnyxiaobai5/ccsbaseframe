package cn.ccsgroup.ccsframework.userManager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Org;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.exception.BussinessException;
import cn.ccsgroup.ccsframework.userManager.dao.OrgDao;
import cn.ccsgroup.ccsframework.userManager.service.OrgService;
import cn.ccsgroup.ccsframework.utils.PageHelper;

/**
 * @Package:      [cn.ccsgroup.ccsframework.userManager.service.impl.OrgServiceImpl]  
 * @ClassName:    [OrgServiceImpl]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-12-25]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-25，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [版本号]
 */
@Transactional
@Repository("OrgService")
public class OrgServiceImpl implements OrgService {

	private OrgDao orgDao;
	
	@Resource(name="OrgDao")
	public void setOrgDao(OrgDao orgDao)
	{
		this.orgDao = orgDao;
	}

	/**
	 * 新增组织机构
	 */
	public boolean saveOrg(Org org) {
		// 新增之前首先调用排序方法给当前组织机构重新排序
		boolean ab = false;
		int currentSort = 0;
		if(org.getParentId()!=null)
		{
			List<Org> childList= orgDao.findAllChildByFid(org.getParentId());
			currentSort = childList.size();
		}
		
		if(org.getSort() > currentSort)// 当前序列大于现有数据
		{
			ab = orgDao.addOrg(org);
		}else // 排列所有
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sort", org.getSort()); // 已经选择的位置
			map.put("parentId", org.getParentId()); // 当前组织
			if(orgDao.updateEnlargeSort(map))
				ab = orgDao.addOrg(org);
		}
		return ab;
	}

	/**
	 * 删除组织机构
	 */
	public boolean deleteOrg(Integer orgId) {
		boolean db = false;
		if(orgDao.findAllChildByFid(orgId).size() > 0)
		{
			return db;
		}else
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", AppConst.STATUS_DISABLE);
			map.put("orgId", orgId);
			orgDao.deleteOrg(map);
		}
		return db;
	}

	/**
	 * 分页查询组织机构
	 */
	public List<Org> findAllOrgByPage(PageHelper pager) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pager", pager);
		pager.setTotalCount(orgDao.findAllOrgByCount(map), pager);
		return orgDao.findAllOrgByPage(map);
	}
	
	/**
	 * 修改组织机构
	 * @param org
	 * @return
	 * @throws Exception 
	 */
	public boolean updateOrg(Org org,Integer oldSort) throws Exception
	{
		boolean rb = false;
		// 如果顺序未发生变化
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sort", org.getSort());
		map.put("parentId", org.getParentId());
		//if(isCanToSmall(org)) throw new BussinessException("20140901"); // 不允许调整
		if(oldSort.equals(org.getSort()))
		{
			rb = orgDao.updateOrg(org);
		}else if(oldSort < org.getSort()) // 如果现在的顺序被调大
		{
			if(orgDao.updateReduceSort(map))
                rb = orgDao.updateOrg(org);
		}else if(oldSort > org.getSort()) // 如果现在的顺序被调小
		{
			if(orgDao.updateEnlargeSort(map))
                rb = orgDao.updateOrg(org);
		}
		return rb; 
	}
	
	
	/**
	 * 判断区域是否可以被调小
	 * @Title: isCanToSmall
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param orgId
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean isCanToSmall(Org bean)
	{
		List<Org> vList = orgDao.findAllChildByFid(bean.getId());
		
		return false;
	}

	/**
	 * 查询子类组织机构
	 */
	public List<Org> selectOrgByFid(Integer fid) {
		return orgDao.findAllChildByFid(fid);
	}

	/**
	 * 获取组织机构树
	 */
	public List<TreeNode> selectOrgTreeByPid(Integer parentId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", 26);
		map.put("status", AppConst.STATUS_ENABLE);
		return orgDao.selectOrgTreeByPid(map);
	}
	
	/**
	 * 获取详细组织机构信息
	 */
	public Org selectOrgById(Integer orgId) {
		return orgDao.selectOrgById(orgId);
	}

	/**
	 * 查询当前组织机构用户
	 */
	public List<UserManager> selectUserByOrgId(Map<String, Object> map) {
		return orgDao.selectUserByOrgId(map);
	}
	
	public String getRegionNameBycode(String code){
		String regionName = (String)orgDao.queryForObject("Org.getRegionNameBycode", code);
		return regionName;
	}
	public String getStreetNameBycode(String code){
		String streetName = (String)orgDao.queryForObject("Org.getStreetNameBycode", code);
		return streetName;
	}
	public String getCommunityNameBycode(String code){
		String communityName = (String)orgDao.queryForObject("Org.getCommunityNameBycode", code);
		return communityName;
	}
}
