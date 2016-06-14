package cn.ccsgroup.ccsframework.sysparamManager.controller;

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

import com.google.gson.Gson;

import cn.ccsgroup.ccsframework.annotation.PrivilegeAnnotation;
import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.entity.Ops;
import cn.ccsgroup.ccsframework.entity.SysParam;
import cn.ccsgroup.ccsframework.sysparamManager.service.SysParamService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysparamManager.controller.SyslogsController.java]  
 * @ClassName:    [SyslogsController]   
 * @Description:  [系统参数控制类]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2014-1-23 下午05:29:46]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2014-1-23 下午05:29:46]   
 * @UpdateRemark: [新建]  
 * @Version:      [v1.0] 
 */
@Controller
@RequestMapping("/SysParamController")
public class SysParamController extends BaseController{

	@Resource(name="SysParamService")
	private SysParamService sysparamservice;
	public void setSysparamservice(SysParamService sysparamservice) {
		this.sysparamservice = sysparamservice;
	}

	@RequestMapping(value="/getSysParamList")
	public @ResponseBody EasyUIPage getSysParamList(@RequestParam(value="rows")Integer rows,
			EasyUIPage page){
		page.setPagePara(rows);
		page = sysparamservice.querySysParamList(page);
		return page;
	}

	@RequestMapping(value="/getLogiSystem")
	public @ResponseBody List<LogiSystem> getLoginSystemList(){
		List<LogiSystem> list=sysparamservice.queryLoginSystemList();
		return list;
	}

	@RequestMapping(value="/viewSysParam")
	public String viewSysParam(){
		return "sysparamManager/sysparam";
	}

	@RequestMapping(value="/create")
	public ModelAndView createSysParam(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("sysparamManager/addParam");
		return mv;
	}

	@RequestMapping(value="/view",method = RequestMethod.POST)
	public ModelAndView viewSysParam(@RequestParam(value="id")Integer id){
		ModelAndView mv = new ModelAndView("sysparamManager/viewParam");
		SysParam sysParam = sysparamservice.querySysParamById(id);
		mv.addObject("sysParam", sysParam);
		return mv;
	}

	@RequestMapping(value="/findSysParamById",method=RequestMethod.GET)
	public ModelAndView findSysParamById(Integer id)
	{
		ModelAndView mv = new ModelAndView();
		try {
			if(id != null){ // 修改
				SysParam sysParam = sysparamservice.querySysParamById(id);
				mv.addObject("sysParam", sysParam);
				mv.setViewName("sysparamManager/addParam");
			}else{
				mv.setViewName("sysparamManager/addParam");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value= "/addSysParam",method=RequestMethod.POST)
	public ModelAndView addSysParam(SysParam param)
	{
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
		boolean flag=false;
		String value=param.getParamvalue();
		if(param.getParamtype().equals("功能控制")){
			if(value.endsWith("1")){
				param.setParamvalue("是");
			}else if(value.endsWith("2")){
				param.setParamvalue("否");
			}
		}
		if(param.getId()!=null){//编辑
			flag = sysparamservice.updateSysParam(param);
			// 判断是否成功
			if(flag){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getUpdateSuccess());
			}else
			{
				model.put(AppConst.STATUS, AppConst.FAIL);
				model.put(AppConst.MESSAGES, getUpdateFail());
			}
		}else{//新增
			/**设置系统固定参数   */
			param.setStatus(AppConst.STATUS_ENABLE);
			flag = sysparamservice.insertSysParam(param);
			// 判断是否成功
			if(flag){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getAddSuccess());
			}else
			{
				model.put(AppConst.STATUS, AppConst.FAIL);
				model.put(AppConst.MESSAGES, getAddFail());
			}
		}
		mv.addObject(AppConst.MESSAGES,new Gson().toJson(model));
		return mv;
	}

	@RequestMapping(value="/deleteSysParam",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteSysParam(@RequestParam(value="id")Integer id){
		Map<String, Object> model = new HashMap<String, Object>();
		boolean flag=sysparamservice.deleteSysParam(id);
		if(flag){
			model.put(AppConst.STATUS, AppConst.SUCCESS);
			model.put(AppConst.MESSAGES, getDeleteSuccess());
		}else{
			model.put(AppConst.STATUS, AppConst.FAIL);
			model.put(AppConst.MESSAGES, getDeleteFail());
		}
		return model;
	}

}
