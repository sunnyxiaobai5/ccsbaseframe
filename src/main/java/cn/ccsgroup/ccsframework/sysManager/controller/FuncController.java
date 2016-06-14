/**
 * 
 */
package cn.ccsgroup.ccsframework.sysManager.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import cn.ccsgroup.ccsframework.entity.Func;
import cn.ccsgroup.ccsframework.sysManager.service.FuncService;
import cn.ccsgroup.ccsframework.sysManager.service.OpsService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

import com.google.gson.Gson;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.biz]  
 * @ClassName:    [FuncAction]   
 * @Description:  [功能交互类主要做数据存储修改用]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-11-15]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@RequestMapping("/FuncController")
public class FuncController extends BaseController{

	@Resource(name="FuncService")
	private FuncService funcService;

	
	@Resource(name="OpsService")
	private OpsService opsService;
	
	/**
	 * 
	 * @Title: getFunc
	 * @Description: TODO(分页展示已有功能)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value = "/fun")
	public ModelAndView getFunc()
	{
		return new ModelAndView("funcManager/funcManager");
	}
	
	/**
	 * 
	 * @Title: getFuncDetail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param funcId
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getFuncDetail",method=RequestMethod.GET)
	public ModelAndView getFuncDetail(Integer id)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("funcManager/addFunc");
		return mv;
	}
	
	/**
	 * @Title: getFuncDetailUpdate
	 * @Description: TODO(进入修改功能界面)
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getFuncDetailUpdate",method=RequestMethod.GET)
	public ModelAndView getFuncDetailUpdate(Integer id)
	{
		ModelAndView mv = new ModelAndView("funcManager/editFunc");
		Func func = funcService.findFuncById(id);
		mv.addObject("func", func);
		return mv;
	}
	
	
	/**
	 * @Title: findAllOpsByPage
	 * @Description: TODO(获取操作列表)
	 * @param @param page
	 * @param @param rows
	 * @param @return    设定文件
	 * @return List<Ops>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/findOpsByPage",method=RequestMethod.GET)
	public @ResponseBody EasyUIPage findAllOpsByPage(EasyUIPage page,@RequestParam("rows")Integer rows,Integer funcId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("funcId", funcId);
		page.setPagePara(rows);
		return opsService.findAllOpsByPage(map, page, getSessionBean());
	}
	
	/**
	 * 
	 * @Title: findFuncByPage
	 * @Description: TODO(分页查询功能列表)
	 * @param @param page
	 * @param @param rows
	 * @param @return    设定文件
	 * @return List<Func>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/findFuncByPage",method=RequestMethod.POST)
	public @ResponseBody EasyUIPage findFuncByPage(EasyUIPage page,@RequestParam("rows")Integer rows)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		page.setPagePara(rows);
		return funcService.selectFuncByPage(page, map);
	}
	
	/**
	 * 信息功能模块
	 * @param arg0 需要保存的map对象。功能属性的集合
	 * @return 新增功能是否成功，如果成功则返回当前对象
	 */
	@PrivilegeAnnotation
	@RequestMapping(value= "/addFunc",method=RequestMethod.POST)
	public ModelAndView addFunc(Func arg0)
	{
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
		/**设置系统固定参数   */
		arg0.setStatus(AppConst.STATUS_ENABLE);
		arg0.setIsActive(AppConst.STATUS_ENABLE); // 设置为启用
		arg0.setIsInside(AppConst.CANMODIFY); // 设置为可修改
		try {
			boolean fb = funcService.saveFunc(arg0);
			// 判断是否成功
			if(fb){
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
	 * @Title: disFuncOpsByFid
	 * @Description: TODO(开始分配操作)
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/disFuncOpsByFid",method=RequestMethod.GET)
	public ModelAndView disFuncOpsByFid(@RequestParam("id")Integer id)
	{
		ModelAndView mv = new ModelAndView("funcManager/disFuncOps");
		mv.addObject("func", funcService.findFuncById(id));
		return mv;
	}
	
	
	/**
	 * @Title: updateFunc
	 * @Description: TODO(修改功能操作)
	 * @param @param arg0
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/updateFunc",method=RequestMethod.POST)
	public ModelAndView updateFunc(Func func)
	{
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			func.setSysEditTime(new Date());
			boolean ub = funcService.updateFunc(func);
			if(ub){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getUpdateSuccess());
			}else{
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
	 * @Title: deleteFunc
	 * @Description: TODO(删除功能)
	 * @param @param id
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/deleteFunc",method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteFunc(@RequestParam("id")Integer id)
	{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			boolean db = funcService.deleteFunc(id);
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
	 * 
	 * @Title: addDisFuncOps
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/addDisFuncOps",method=RequestMethod.POST)
	public ModelAndView addDisFuncOps(@RequestParam("id")Integer id,@RequestParam("opsIds")String opsIds)
	{
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// 封装数据
			Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
			// 创建已分配数列
			List<Integer> list = new ArrayList<Integer>();
			if(opsIds != null && !"".equals(opsIds))
			{
				String [] opsArray = opsIds.split(",");
				for(int i = 0;i < opsArray.length; i ++)
				{
					list.add(Integer.valueOf(opsArray[i]));
				}
			}
			map.put(id, list);
			boolean adb = funcService.updateDisFuncOps(map);
			if(adb)
			{
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, super.getMessage("funcDisSuccess"));
			}else
			{
				model.put(AppConst.STATUS, AppConst.FAIL);
				model.put(AppConst.MESSAGES, super.getMessage("funcDisFail"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.put(AppConst.STATUS, AppConst.FAIL);
			model.put(AppConst.MESSAGES, super.getMessage("funcDisFail"));
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(model));
		
		return mv;
	}
	
	/**
	 * 
	 * @Title: getFuncOpsTree
	 * @Description: TODO(获取功能操作树)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getFuncOpsTree",method=RequestMethod.GET)
	public @ResponseBody List<TreeNode> getFuncOpsTree(String checkedBean)
	{
		List<Integer> list = null;
		if(checkedBean != null && !"".equals(checkedBean))
		{
			list = new ArrayList<Integer>();
			String [] array = checkedBean.split(",");
			for(String key : array)
			{
				list.add(Integer.valueOf(key));
			}
		}
		return funcService.getFuncOpsTree(list);
	}
	
}
