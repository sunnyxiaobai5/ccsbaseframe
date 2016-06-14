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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import cn.ccsgroup.ccsframework.annotation.PrivilegeAnnotation;
import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.sysManager.service.ModelService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.biz]  
 * @ClassName:    [ModelAction]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [hr]   
 * @CreateDate:   [2013-11-15]   
 * @UpdateUser:   [hr，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */


@Controller
@RequestMapping("/ModelController")
public class ModelController extends BaseController{

	@Resource(name="modelService")
	private ModelService modelService;
	
	/**
	 * 分页展示已有模块
	 * @return
	 */
	@PrivilegeAnnotation
	@RequestMapping(value = "/model")  
	public ModelAndView getModel()
	{
		return new ModelAndView("modelManager/modelManager");
	}
	
	
	/**
	 * 分页展示模糊查询模块
	 * @return
	 */
	@RequestMapping(value = "/getModleByCondition",method=RequestMethod.POST) 
	public @ResponseBody EasyUIPage getModleByCondition(EasyUIPage page,@RequestParam(value="rows")Integer rows ,
													    String condtionname,String condition)
	{
		page.setPagePara(rows);
		HashMap<String,Object> _map= new HashMap<String, Object>();
		_map.put("status",AppConst.STATUS_DISABLE);
		_map.put(condtionname,condition);
		return modelService.getModleByCondition(page , _map);
	}
	
	/**
	 * 
	 * @Title: getModelDetail
	 * @Description: TODO(查询模块下详细信息)
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/getModelDetail",method=RequestMethod.GET)
	public ModelAndView getModelDetail(String id)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("modelManager/addModel");
		return mv;
	}
	
	/**
	 * @Title: getModelUpdateDetail
	 * @Description: TODO(获取修改)
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/getModelUpdateDetail",method=RequestMethod.GET)
	public ModelAndView getModelUpdateDetail(@RequestParam("id")String id)
	{
		ModelAndView mv = new ModelAndView();
		Model model = modelService.getModel(id);
		mv.addObject("Model", model);
		mv.setViewName("modelManager/updateModel");
		return mv;
	}
	
	
	/**
	 * 
	 * @Title: addModel
	 * @Description: TODO(新增模块)
	 * @param @param mod
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value= "/addModel",method=RequestMethod.POST)
	public ModelAndView addModel(Model mod)
	{
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
		/**设置系统固定参数   */
		mod.setStatus(AppConst.STATUS_ENABLE);
		mod.setIsInside(AppConst.CANMODIFY); // 设置为可修改
		try {
			boolean fb = modelService.saveModel(mod);
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
			model.put(AppConst.STATUS, AppConst.FAIL);
			model.put(AppConst.MESSAGES, getAddFail());
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(model));
		return mv;
	}
	
	/**
	 * 
	 * @Title: delModel
	 * @Description: TODO(删除模块)
	 * @param @param id
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value= "/delModel",method = RequestMethod.POST)
	public  @ResponseBody Map<String, Object> delModel(@RequestParam("id")String id)
	{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			boolean db = modelService.deleteModel(id);
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
	 * @Title: updateModel
	 * @Description: TODO(修改模块)
	 * @param @param model
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value= "/updateModel",method = RequestMethod.POST)
	public ModelAndView updateModel(Model mod)
	{
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			mod.setSysEditTime(new Date());
			boolean ub = modelService.updateModel(mod);
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
	 * @Title: showModelFuncTree
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param showData
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/showModelFuncTree", method=RequestMethod.GET)
	public @ResponseBody List<TreeNode> showModelFuncTree(String showData)
	{
		if(showData != null && !"".equals(showData))
			return modelService.selectShowTree(packageDataMap(showData));
		else
			return null;
	}
	
	/**
	 * 获取系统已经选择的模块
	 * @Title: getSysUpdateModel
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param sysId
	 * @param @return    设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getSysUpdateModel", method = RequestMethod.GET)
	public @ResponseBody EasyUIPage getSysUpdateModel(EasyUIPage page ,@RequestParam(value="rows")Integer rows, Integer sysId)
	{
		page.setPagePara(rows);
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("sysId", sysId);
		map.put("status",AppConst.STATUS_DISABLE);
		
		return modelService.getSysUpdateModel(page , map);
	}
	
	
	/**
	 * 针对于临时的选择数据进行模拟
	 * @Title: packageDataMap
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param data
	 * @param @return    设定文件
	 * @return Map<String,ArrayList<String>>    返回类型
	 * @throws
	 */
	private Map<Integer,ArrayList<Integer>> packageDataMap(String data)
	{
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		// 首先取出主节点
		String [] val = data.split("@");
		for(int i = 0; i < val.length; i ++)
		{
			ArrayList<Integer> fList = new ArrayList<Integer>();
			String [] line = val[i].split(":"); // 一行数据
			Integer key = Integer.valueOf(line[0]); // 模块Id
			if(line.length >= 2 && line[1] != null && !"".equals(line[1]))
			{
				String [] value = line[1].split(","); // 功能的数组
				for(int k = 0; k < value.length ; k ++)
				{
					fList.add(Integer.valueOf(value[k])); // 功能Id
				}
			}
			map.put(key, fList);
		}
		return map;
	}
}
