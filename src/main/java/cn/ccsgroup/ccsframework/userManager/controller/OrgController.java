/**
 * 
 */
package cn.ccsgroup.ccsframework.userManager.controller;

import cn.ccsgroup.ccsframework.annotation.PrivilegeAnnotation;
import cn.ccsgroup.ccsframework.annotation.TokenAnnotation;
import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Org;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.exception.BussinessException;
import cn.ccsgroup.ccsframework.tree.service.TreeService;
import cn.ccsgroup.ccsframework.userManager.service.OrgService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package:      [cn.ccsgroup.ccsframework.userManager.controller]  
 * @ClassName:    [OrgController]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-12-25]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-25，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [版本号]
 */
@Controller
@RequestMapping("/OrgController")
public class OrgController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrgController.class);
	
	@Resource(name="treeService")
	private TreeService treeService;

	private OrgService orgService;
	
	@Resource(name="OrgService")
	public void setOrgService(OrgService orgService)
	{
		this.orgService = orgService;
	}
	
	/**
	 * 初始化组织机构
	 * @return
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/initOrgList")
	public ModelAndView initOrgList()
	{
		ModelAndView mv = new ModelAndView("orgManager/orgManager");
		return mv;
	}
	
	/**
	 * 新增组织机构
	 * @param org 组织机构对象
	 * @return
	 */
	@PrivilegeAnnotation
	@TokenAnnotation(remove=true)
	@RequestMapping(value="/addOrg",method=RequestMethod.POST)
	public ModelAndView addOrg(Org org)
	{
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
		SessionBean bean = getSessionBean();
		org.setCreaterUser(bean.getUserName());
		try {
			boolean fb = orgService.saveOrg(org);
			if(fb){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getAddSuccess());
			}else{
				model.put(AppConst.STATUS, AppConst.FAIL);
				model.put(AppConst.MESSAGES, getAddFail());
			}
		} catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(),e);
			model.put(AppConst.STATUS, AppConst.FAIL);
			model.put(AppConst.MESSAGES, getAddFail());
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(model));
		return mv;
	}
	
	
	/**
	 * 获取相信的组织机构信息
	 * @param orgId
	 * @return
	 */
	@TokenAnnotation(save=true)
	@RequestMapping(value="/getOrgDetail",method=RequestMethod.GET)
	public ModelAndView getOrgDetail(Integer orgId,Integer parentId)
	{
		// 默认跳转修改界面
		ModelAndView mv = new ModelAndView("orgManager/orgEditDetail");
		try {
            if (orgId != null) {
            	Org org = orgService.selectOrgById(orgId);
            	
				mv.addObject("Org", org);
				
				String regionCodeTemp = "";
				String streetTemp = "";
				String communityTemp= "";
				
				//判断并获取regioncode的值
				String orgRegionCode = org.getRegionCode();

				//如果code是9位--区域  街道
				if(orgRegionCode.length() == 9){
					streetTemp = orgService.getStreetNameBycode(orgRegionCode);//街道
					String regioncode = orgRegionCode.substring(0,6);
					regionCodeTemp = orgService.getRegionNameBycode(regioncode);//区域
				}
                //如果code是12位--区域  街道  社区
                else if(orgRegionCode.length() == 12){
					communityTemp = orgService.getCommunityNameBycode(orgRegionCode);//社区
					String streetcode = orgRegionCode.substring(0,9);
					streetTemp = orgService.getStreetNameBycode(streetcode);//街道
					String regioncode = orgRegionCode.substring(0,6);
					regionCodeTemp = orgService.getRegionNameBycode(regioncode);//区域
				}
                //如果code是6位--区域
                else{
					regionCodeTemp = orgService.getRegionNameBycode(orgRegionCode);//区域
				}
				
				mv.addObject("regionCodeTemp", regionCodeTemp); 
				mv.addObject("streetTemp", streetTemp); 
				mv.addObject("communityTemp", communityTemp); 
				
				// 如果是修改的话则查询同级组织机构
				mv.addObject("childList", orgService.selectOrgByFid(org.getParentId()));
			}else
			{
				mv.setViewName("orgManager/orgAddDetail");
                if (parentId != null) {
                    mv.addObject("parentId", parentId);
                    Org parent = orgService.selectOrgById(parentId);
                    mv.addObject("parent", parent);
                    // 新增的时候获取子节点个数 保证两种排序方式
                    List<Org> sortList = orgService.selectOrgByFid(parentId); // 通过父类Id查询
                    mv.addObject("sort", sortList.size() + 1); // 默认加载到列表最后
                    mv.addObject("childList", sortList);
                }
			}
		} catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(),e);
		}
		return mv;
	}
	/**
	 * @Title: updateOrg
	 * @Description: TODO(修改组织机构)
	 * @param @param org
	 * @param @param oldSort
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/updateOrg",method=RequestMethod.POST)
	public ModelAndView updateOrg(Org org,Integer oldSort)
	{
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			org.setSysEditTime(new Date());
			boolean ub = orgService.updateOrg(org,oldSort);
			if(ub){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getUpdateSuccess());
			}else{
				model.put(AppConst.STATUS, AppConst.FAIL);
				model.put(AppConst.MESSAGES, getUpdateFail());
			}
		}catch (BussinessException be){
            LOGGER.error(be.getLocalizedMessage(),be);
			model.put(AppConst.STATUS, AppConst.FAIL);
			model.put(AppConst.MESSAGES, be.getMessage());
		}catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(),e);
			model.put(AppConst.STATUS, AppConst.FAIL);
			model.put(AppConst.MESSAGES, getUpdateFail());
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(model));
		return mv;
	}
	
	/**
	 * @Title: deleteOrg
	 * @Description: TODO(删除组织机构)
	 * @param @param orgId
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/deleteOrg",method=RequestMethod.GET)
	public ModelAndView deleteOrg(@RequestParam("orgId")Integer orgId)
	{
		ModelMap map = new ModelMap();
		try {
			boolean db = orgService.deleteOrg(orgId);
			if(db) // 删除成功执行新查询
			{
				map.put( AppConst.MESSAGES, getDeleteSuccess());
			}else
			{
				map.put( AppConst.ERRORS, getDeleteFail());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/OrgController/initOrgList", map);
	}
	
	/**
	 * @Title: getOrgDetailByFatherId
	 * @Description: TODO(根据选择节点 联动带出同级机构)
	 * @param @param fid
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getOrgChildList",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> getOrgDetailByFatherId(@RequestParam("fid")Integer fid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		orgService.selectOrgByFid(fid);
		
		return map;
	}
	
	/**
	 * 
	 * @Title: findUsersByOrgId
	 * @Description: TODO(获取该组织机构下的用户)
	 * @param @return    设定文件
	 * @return List<UserManager>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/findUsersByOrgId",method=RequestMethod.GET)
	public @ResponseBody List<UserManager> findUsersByOrgId(@RequestParam("orgId")Integer orgId,
										@RequestParam("page")Integer page,@RequestParam("rows")Integer rows)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("begin", (page - 1) * rows); // 起始数据
		map.put("end",  page * rows); // 结束
		List<UserManager> uList = orgService.selectUserByOrgId(map);
		return uList;
	}
	
	/**
	 * 获取组织机构树型菜单
	 * @return
	 */
	@RequestMapping(value="/getOrgTreeList",method=RequestMethod.GET)
	public @ResponseBody List<TreeNode> getOrgTreeList()
	{
		// 根据当前登录帐号获取本级id
		List<TreeNode> vList = orgService.selectOrgTreeByPid(1);
		return vList;
	}
	
	/**
	 * @Title: findChildOrgByPid
	 * @Description: TODO(获取子组织)
	 * @param @param pid
	 * @param @return    设定文件
	 * @return List<Org>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/findChildOrgByPid" , method=RequestMethod.GET)
	public @ResponseBody List<Org> findChildOrgByPid(Integer pid)
	{
		return orgService.selectOrgByFid(pid); // 通过父类Id查询
	}
	
	
	/**lp3
	 * @Title: getTree
	 * @Description: TODO(获取组织机构树   zTree)
	 * @return List
	 * @throws
	 */
	@RequestMapping(value="/getOrgzTree")
	public @ResponseBody List<Org> getOrgzTree(){
		List<Org> list = treeService.getOrgzTree(this.getSessionBean().getOrganization().getId());
		
		return list;
	}

    @RequestMapping(value="/getSearchableOrgzTree")
    public @ResponseBody List<Org> getSearchableOrgzTree(){
        List<Org> list = treeService.getSearchableOrgzTree(this.getSessionBean().getOrganization().getId());

        return list;
    }

    @RequestMapping(value = "/getAcceptableOrgzTree")
    public
    @ResponseBody
    List<Org> getAcceptableOrgzTree() {
        Integer topOrgId = this.treeService.getTopOrgId();
        Integer sysTopOrgId = this.treeService.getSysTopOrgId(this.getSessionBean().getOrganization().getId(),topOrgId);
        List<Org> list = treeService.getSearchableOrgzTree(sysTopOrgId);

        return list;
    }


	
}
