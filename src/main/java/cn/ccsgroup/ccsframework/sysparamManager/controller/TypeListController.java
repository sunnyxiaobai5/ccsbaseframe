package cn.ccsgroup.ccsframework.sysparamManager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import cn.ccsgroup.ccsframework.annotation.PrivilegeAnnotation;
import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.TypeList;
import cn.ccsgroup.ccsframework.sysparamManager.service.TypeListService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;


/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysparamManager.controller.TypeListController.java]  
 * @ClassName:    [TypeListController]   
 * @Description:  [通用标准参数管理Controller]   
 * @Author:       [lenovo]   
 * @CreateDate:   [2014-1-6 上午11:50:04]   
 * @UpdateUser:   [lenovo(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-6 上午11:50:04，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@Controller
@RequestMapping("/TypeListController")
public class TypeListController extends BaseController{

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name="TypeListService")
	private TypeListService typeListService;
	
	public void setTypeListService(TypeListService typeListService) {
		this.typeListService = typeListService;
	}

	
	/**
	 * @Title: viewTypeList
	 * @Description: 打开typelist
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	@RequestMapping(value="/viewTypeList")
	public String viewTypeList(){
		return "typeListManager/typeList";
	}
	
	
	/**
	 * @Title: getTypeList
	 * @Description: 获取主页面列表
	 * @param @param rows
	 * @param @param page
	 * @param @return    设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getTypeList")
	public @ResponseBody EasyUIPage getTypeList(@RequestParam(value="rows")Integer rows,
			EasyUIPage page){
		page.setPagePara(rows);
		page = typeListService.getTypeList(page);
		return page;
	}
	
	/**
	 * @Title: getAddTypeListView
	 * @Description: 新增通用标准
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/addTypeList")
	public String getAddTypeListView(){
		return "typeListManager/addTypeList";
	}
	
	/**
	 * @Title: saveTypeList
	 * @Description: 新增保存
	 * @param @param typeList
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public ModelAndView saveTypeList(TypeList typeList){
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			typeListService.saveTypeList(typeList);
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES, getAddSuccess());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage(), e);
			map.put(AppConst.STATUS, AppConst.FAIL);
			map.put(AppConst.MESSAGES,getAddFail());
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mv;
	}
	
	/**
	 * @Title: updateTypeList
	 * @Description: 修改获取数据，根据id查询
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/update",method = RequestMethod.GET)
	public ModelAndView updateTypeList(@RequestParam(value="id")Integer id){
		ModelAndView mv = new ModelAndView("typeListManager/updateTypeList");
		TypeList typeList = typeListService.queryTypeListById(id);
		mv.addObject("typeList", typeList);
		return mv;
	}
	
	/**
	 * @Title: updateTypeList
	 * @Description: 修改保存方法
	 * @param @param typeList
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public ModelAndView updateTypeList(TypeList typeList){
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			int i = typeListService.updateTypeList(typeList);
			if(i > 0){
				map.put(AppConst.STATUS, AppConst.SUCCESS);
				map.put(AppConst.MESSAGES, getUpdateSuccess());
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getLocalizedMessage(), e);
			map.put(AppConst.MESSAGES,getUpdateFail());
			map.put(AppConst.STATUS, AppConst.FAIL);
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mv;
	}
	
	/**
	 * @Title: delRole
	 * @Description: 批量删除方法，前台传回id[]，后台解析充逗号分割的字符串，再用in封装条件并查询
	 * @param @param id
	 * @param @return    设定文件
	 * @return Map<String,Object>    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/del",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> delRole(@RequestParam(value="id[]")Integer[] id){
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			String ids = "";
			for (int j = 0; j<id.length; j++){

				int i = id[j];
				
				ids +=","+i;

				}
			int i = typeListService.deleteTypeList(ids.substring(1));
			if(i > 0){
				map.put(AppConst.STATUS, AppConst.SUCCESS);
				map.put(AppConst.MESSAGES, getDeleteSuccess());
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage(), e);
			map.put(AppConst.MESSAGES,getDeleteFail());
			map.put(AppConst.STATUS, AppConst.FAIL);
		}
		return map;
	}
	
	/**
	 * @Title: viewTypeList
	 * @Description: 查看方法
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/view",method = RequestMethod.GET)
	public ModelAndView viewTypeList(@RequestParam(value="id")Integer id){
		ModelAndView mv = new ModelAndView("typeListManager/viewTypeList");
		TypeList typeList = typeListService.queryTypeListById(id);
		mv.addObject("typeList", typeList);
		return mv;
	}
}
