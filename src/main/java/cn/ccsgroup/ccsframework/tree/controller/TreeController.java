package cn.ccsgroup.ccsframework.tree.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.tree.service.TreeService;
import cn.ccsgroup.ccsframework.utils.StringUtil;
/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.tree.controller.TreeController.java]  
 * @ClassName:    [TreeController]   
 * @Description:  [树的统一控制器]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2014-1-15 下午3:40:16]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-15 下午3:40:16，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@Scope("prototype")
public class TreeController extends BaseController {
	
	@Resource(name="treeService")
	private TreeService treeService;
	
	/**
	 * 
	 * @Title: findRegionTree
	 * @Description: TODO(取传入ID本级及所有下级组织机构树)
	 * @param @param code (组织)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/region/{regionCode}",method={RequestMethod.GET})
	public @ResponseBody List<TreeNode> findRegionTree(@PathVariable(value="regionCode")Integer code){
		return treeService.findRegionTree(code);
	}
	/**
	 * 待完成
	 * @Title: findRegionTreeLevel
	 * @Description: TODO(取传入ID本级下 level 级组织机构树)
	 * @param @param code
	 * @param @param level
	 * @param @return    设定文件  
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/region/{regionCode}/{childRows}",method={RequestMethod.GET})
	public @ResponseBody List<TreeNode> findRegionTreeLevel(@PathVariable(value="regionCode")Integer code,
			@PathVariable(value="childRows")Integer childRows){
		return treeService.findRegionTree(code);
	}
	/**
	 * 
	 * @Title: findRegionTreeByUserId
	 * @Description: TODO(根据用户取所属区域树)
	 * @param @param roldId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/region/user/{userId}",method={RequestMethod.GET})
	public @ResponseBody List<TreeNode> findRegionTreeByUserId(@PathVariable(value="userId")Integer userId){
		return treeService.findRegionTreeByUserId(userId);
	}
	
	/**
	 * 
	 * @Title: findRegionTreeByRoleId
	 * @Description: TODO(角色所分配的区域为选中)
	 * @param @param roldId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/region/role/{roleId}",method={RequestMethod.GET})
	public @ResponseBody List<TreeNode> findRegionTreeByRoleId(@PathVariable(value="roleId")Integer roleId){
		return treeService.findRegionTreeByRoleId(roleId,getSessionBean().getId());
	}
	
	/**
	 * 
	 * @Title: findOrgTreeByUserId
	 * @Description: TODO(根据用户ID取其 本级及下级组织机构树)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/org/user/{userId}",method={RequestMethod.GET})
	public @ResponseBody List<TreeNode> findOrgTreeByUserId(@PathVariable(value="userId")Integer userId){
		return treeService.findCurrentOrgTree(userId, -10);
	}
	/**
	 * 
	 * @Title: findOrgTreeByUserIds
	 * @Description: TODO(取用户已分配的组织机构树)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/org/userOwner/{userId}",method={RequestMethod.GET})
	public @ResponseBody List<TreeNode> findOrgTreeByUserIdOwner(@PathVariable(value="userId")Integer userId){
		return treeService.findOrgTreeByUserIdOwner(userId, -10);
	}
	
	/**
	 * 
	 * @Title: findOrgTreeByUserAndRole
	 * @Description: TODO(取当前登录者的组织机构树，对应角色ID 已分配的组织为选中)
	 * @param @param userId
	 * @param @param roleId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/org/user/{userId}/{roleId}",method={RequestMethod.GET})
	public @ResponseBody List<TreeNode> findOrgTreeByUserAndRole(@PathVariable(value="userId")Integer userId,
			@PathVariable(value="roleId")Integer roleId){
		return treeService.findCurrentOrgTree(userId, roleId, -10);
	}
	
	/**
	 * 
	 * @Title: findSysTreeByOrg
	 * @Description: TODO(根据组织机构ID取 所属 系统权限树)
	 * @param @param orgId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/sysTree/org/{orgId}",method={RequestMethod.GET})
	public @ResponseBody List<TreeNode> findSysTreeByOrg(@PathVariable(value="orgId")String orgId){
		if(orgId.equals("000000")) return new ArrayList<TreeNode>();
		return treeService.findSysTreeByOrgId(orgId.split("_"));
	}
	
	/**
	 * 
	 * @Title: findSysTreeByRoleOrg
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param orgId
	 * @param @param roleId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/sysTree/{roleId}/{orgId}",method={RequestMethod.GET})
	public @ResponseBody List<TreeNode> findSysTreeByRoleOrg(@PathVariable(value="orgId")String orgId,
			@PathVariable(value="roleId")Integer roleId){
		if(orgId.equals("000000")) return new ArrayList<TreeNode>();
		return treeService.findSysTreeByRoleOrg(orgId.split("_"),roleId);
	}
	
	/**
	 * lp3  获取权限树  弃用
	 * @Title: sysTree
	 * @Description: TODO(取当前登录用户角色的所有系统权限树)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/sysTree",method = RequestMethod.GET)
	public @ResponseBody List<TreeNode> sysTree(){
		return treeService.sysTree(getSessionBean());
	}
	
	/**
	 * lp3   弃用
	 * @Title: sysTreeRole
	 * @Description: TODO(取当前登录用户角色所有系统权限树 （已拥有的是选中状态） )
	 * @param @param roleid
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/sysTree/{id}",method = RequestMethod.GET)
	public @ResponseBody List<TreeNode> sysTreeRole(@PathVariable(value="id")Integer roleid){
		List<TreeNode> list  = treeService.sysTree(roleid,getSessionBean());
		return list;
	}
	
	/**
	 * @Title: findChooseRolePermissions
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/findChoosedRolePermissions/{id}",method = RequestMethod.GET)
	public @ResponseBody List<TreeNode> findChoosedRolePermissions(@PathVariable(value="id")String roleid)
	{	
		List<TreeNode> list = null;
		if(StringUtil.isNotBlank(roleid))
		{
			List<Integer> roleIds = new ArrayList<Integer>();
			String [] tmp = roleid.split(",");
			for(int index = 0;index < tmp.length ; index++)
			{
				if(StringUtil.isNotBlank(tmp[index])) roleIds.add(Integer.valueOf(tmp[index]));
			}
			list = treeService.findChoosedRolePermissions(roleIds);
		}
		return list;
	}
	
	@RequestMapping(value="/findOrgAndReionTree/{userId}/{childRows}", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<TreeNode> findOrgAndRegionTree(@PathVariable(value="userId")Integer userId,@PathVariable(value="childRows")Integer childRows)
	{
		return treeService.findOrgAndRegionTree(userId, childRows);
	}
	
	@RequestMapping(value="/findCurrentOrgTree/{childRows}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<TreeNode> findCurrentOrgTree(@PathVariable(value="childRows")Integer childRows)
	{
		Integer userId = getSessionBean().getId();
		return treeService.findCurrentOrgTree(userId, childRows);
	}
	
	/**
	 * 
	 * @Title: findRegionTreeByUserId
	 * @Description: TODO(根据用户和显示的级数获取所属区域树)
	 * @param @param roldId
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/findRegionManageTree/{childRows}",method={RequestMethod.GET})
	public @ResponseBody List<TreeNode> findRegionManageTree(@PathVariable(value="childRows")Integer childRows){
		return treeService.regManTreeByRegionCodeAndChildRows(getSessionBean().getRegionCode(),childRows);
	}
	@RequestMapping(value="/findCurrentRegionByUser/{regionCodes}/{rows}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<TreeNode> findCurrentRegionByUser(@PathVariable(value="regionCodes")String regionCodes,@PathVariable(value="rows")Integer rows)
	{
		Integer userId = getSessionBean().getId();
		List<String> vList = new ArrayList<String>();
		if(regionCodes!=null&&!"".equals(regionCodes)&&!"nocache".equals(regionCodes)){
			for(String code :regionCodes.split(",") )
			{
				vList.add(code);
			}
		}
		return treeService.findCurrentRegionByUser(userId, vList, rows);
	}
	
	@RequestMapping(value="/findAllRegion/{regionCodes}")
	public @ResponseBody List<TreeNode> findAllRegion(@PathVariable(value="regionCodes")String regionCodes)
	{
		List<String> vList = new ArrayList<String>();
		if(regionCodes!=null&&!"".equals(regionCodes)&&!"nocache".equals(regionCodes)){
			for(String code :regionCodes.split(",") )
			{
				vList.add(code);
			}
		}
		return treeService.findCurrentRegionByUser(null, vList, -10);
	}
	
	/**
	 * @Title: findAllRegionCodeCombo
	 * @Description: TODO(获取所有区域下拉树)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/findAllRegionCodeCombo", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<Ztree> findAllRegionCodeCombo()
	{
		List<Ztree> ztrees = treeService.findAllRegions();
		return ztrees;//treeService.findAllRegionCodeCombo();
	}
	
	//根据区域查询所有的街道
	@RequestMapping(value="/findAllStreetByRegionCode", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<Ztree> findAllStreetByRegionCode(String regionCode){
		List<Ztree> streets = treeService.findAllStreetByRegionCode(regionCode);
		
		return streets;
	}
	
	//根据街道查询所有的社区
	@RequestMapping(value="/findAllCommunityByRegionCode", method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<Ztree> findAllCommunityByRegionCode(String streetCode){
		List<Ztree> communitys = treeService.findAllCommunityByStreetCode(streetCode);
		
		return communitys;
	}
	
	@RequestMapping(value="/findAllOrgCombo/{orgId}" , method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<TreeNode> findAllOrgExistsOwnerCombo(@PathVariable(value="orgId")Integer orgId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", getSessionBean().getId());
		map.put("orgId", orgId);
		return treeService.findAllOrgExistsOwnerCombo(map);
	}
	
	/**
	 * @Title: findAllOrgAndPost
	 * @Description: TODO(获取当前组织机构跟职位关系)
	 * @param @param rows
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/findAllOrgAndPost/{childRows}" , method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<TreeNode> findAllOrgAndPost(@PathVariable(value="childRows")Integer rows)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", getSessionBean().getId());
		return treeService.findAllOrgAndPost(map , rows);
	}
}
