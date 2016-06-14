/**
 * 
 */
package cn.ccsgroup.ccsframework.sysManager.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ccsgroup.ccsframework.annotation.PrivilegeAnnotation;
import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Ops;
import cn.ccsgroup.ccsframework.sysManager.service.OpsService;

import com.google.gson.Gson;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.biz]  
 * @ClassName:    [OperationAction]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-11-15]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@Scope("prototype")
@RequestMapping("/OpsController")
public class OpsController extends BaseController{

	@Resource(name="OpsService")
	private OpsService opsService;
	
	/**
	 * @Title: findOpsById
	 * @Description: TODO(根据Id查询对应操作信息)
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/findOpsById",method=RequestMethod.GET)
	public ModelAndView findOpsById(Integer id,Integer funcId)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("funcManager/addOps");
		mv.addObject("funcId", funcId);
		return mv;
	}
	
	/**
	 * @Title: findOpsByIdUpdate
	 * @Description: TODO(进入修改界面)
	 * @param @param id
	 * @param @param funcId
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/findOpsByIdUpdate",method=RequestMethod.GET)
	public ModelAndView findOpsByIdUpdate(Integer id,Integer funcId)
	{
		ModelAndView mv = new ModelAndView();
		Ops ops = opsService.selectOpsById(id);
		mv.addObject("ops", ops);
		mv.setViewName("funcManager/editOps");
		mv.addObject("funcId", funcId);
		return mv;
	}
	
	/**
	 * 新增
	 * @return
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/addOps",method=RequestMethod.POST)
	public ModelAndView saveOpt(Ops arg0,@RequestParam("funcId")Integer funcId)
	{
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			// 设置参数
			arg0.setStatus(AppConst.STATUS_ENABLE);  // 是否有效
			arg0.setIsActive(AppConst.STATUS_ENABLE); // 是否启用
			boolean ab = opsService.saveOps(arg0, funcId);
			// 判断是否成功
			if(ab){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getAddSuccess());
			}else
			{
				model.put(AppConst.STATUS, AppConst.FAIL);
				model.put(AppConst.MESSAGES, getAddFail());
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put(AppConst.STATUS, AppConst.FAIL);
			model.put(AppConst.MESSAGES, getAddFail());
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(model));
		return mv;
	}
	
	/**
	 * 
	 * @Title: deleteOps
	 * @Description: TODO(操作操作数据)
	 * @param @param id
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/deleteOps",method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> deleteOps(@RequestParam("id")Integer id, Integer funcId)
	{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("opsId", id);
			map.put("funcId", funcId);
			boolean db = opsService.deleteOps(map);
			if(db){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getDeleteSuccess());
			}else{
				model.put(AppConst.STATUS, AppConst.FAIL);
				model.put(AppConst.MESSAGES, getDeleteFail());
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put(AppConst.STATUS, AppConst.FAIL);
			model.put(AppConst.MESSAGES, getDeleteFail());
		}
		return model;
	}
	
	/**
	 * 修改操作
	 * @param arg0 传入修改对象
	 * @return map 对应的界面参数值
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/updateOps",method=RequestMethod.POST)
	public ModelAndView updateOps(Ops ops)
	{
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			// 设置参数
			ops.setSysEditTime(new Date());
			boolean flag = opsService.updateOps(ops);
			// 判断是否成功
			if(flag){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getUpdateSuccess());
			}else
			{
				model.put(AppConst.STATUS, AppConst.FAIL);
				model.put(AppConst.MESSAGES, getUpdateFail());
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put(AppConst.STATUS, AppConst.FAIL);
			model.put(AppConst.MESSAGES, getUpdateFail());
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(model));
		return mv;
	}

	/**
	 * 
	 * @Title: findOpsTreeList
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/findOpsTreeList",method=RequestMethod.GET)
	public @ResponseBody List<TreeNode> findOpsTreeList(Integer funcId)
	{
		return opsService.findOpsTreeList(funcId);
	}
}
