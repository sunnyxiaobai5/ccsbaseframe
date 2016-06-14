/**
 * 
 */
package cn.ccsgroup.ccsframework.userManager.service;

import java.util.List;
import java.util.Map;

import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.entity.Org;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.utils.PageHelper;

/**
 * @Package:      [cn.ccsgroup.ccsframework.userManager.service]  
 * @ClassName:    [OrgService]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-12-25]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-25，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [版本号]
 */
@TypeAnnotation("组织机构管理")
public interface OrgService {
	
	/**
	 * 分页显示所有机构
	 * @return
	 */
	public List<Org> findAllOrgByPage(PageHelper pager);
	
	/**
	 * 新增组织机构
	 * @param org
	 * @return
	 */
	@MethodAnnotation("新增组织机构")
	public boolean saveOrg(Org org);
	
	/**
	 * 删除组织机构
	 * @param orgId
	 * @return
	 */
	@MethodAnnotation("删除组织机构")
	public boolean deleteOrg(Integer orgId);
	
	/**
	 * 修改组织机构
	 * @param org
	 * @return
	 */
	@MethodAnnotation("修改组织机构")
	public boolean updateOrg(Org org,Integer oldSort) throws Exception;
	
	/**
	 * 父类Id查询下级机构
	 * @param fid
	 * @return
	 */
	public List<Org> selectOrgByFid(Integer fid);
	
	/**
	 * 超级管理获取顶级树
	 * @return
	 */
	public List<TreeNode> selectOrgTreeByPid(Integer parentId);
	
	/**
	 * 获取详细组织机构信息
	 * @param orgId
	 * @return
	 */
	public Org selectOrgById(Integer orgId);
	
	
	public List<UserManager> selectUserByOrgId(Map<String, Object> map);
	
	public String getRegionNameBycode(String code);
	public String getStreetNameBycode(String code);
	public String getCommunityNameBycode(String code);
}
