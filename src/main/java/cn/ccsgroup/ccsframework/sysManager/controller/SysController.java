/**
 * 
 */
package cn.ccsgroup.ccsframework.sysManager.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.ccsgroup.ccsframework.annotation.PrivilegeAnnotation;
import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.sysManager.service.SysService;
import cn.ccsgroup.ccsframework.tree.service.TreeService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.biz]  
 * @ClassName:    [SysAction]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [hr]   
 * @CreateDate:   [2013-11-19]   
 * @UpdateUser:   [hr，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-19，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@RequestMapping("/SysController")
public class SysController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="sysService")
	private SysService sysService;
	
	@Resource(name="treeService")
	private TreeService treeService;
	/**
	 * @Title: getSystem
	 * @Description: TODO(跳转系统界面)
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value = "/sys")  
	public ModelAndView getSystem()
	{
		return new ModelAndView("sysManager/system");
	}
	
	/**
	 * 
	 * @Title: addSys
	 * @Description: TODO(新增系统)
	 * @param @param logisystem
	 * @param @param file
	 * @param @param modfunc
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/addsys",method=RequestMethod.POST)
	public ModelAndView addSys(LogiSystem logisystem,@RequestParam("imgs") CommonsMultipartFile file){
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
	 	try {
	 		if(!file.isEmpty())
	 			logisystem.setSysIcon(cutImg(48,48 ,file.getInputStream()));
			logisystem.setStatus(AppConst.STATUS_ENABLE);
			
			int fb = sysService.saveSys(logisystem);
			if(fb>0){
				model.put(AppConst.STATUS, AppConst.SUCCESS);
				model.put(AppConst.MESSAGES, getAddSuccess());
			}else
			{
				model.put(AppConst.STATUS, AppConst.FAIL);
				model.put(AppConst.MESSAGES, getAddFail());
			}
		} catch (IOException e) {
			e.printStackTrace();
			model.put(AppConst.STATUS, AppConst.FAIL);
			model.put(AppConst.MESSAGES, getAddFail());
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(model));
		return mv;
	}
	
	/**
	 * @Title: cutImg
	 * @Description: TODO(转换图片)
	 * @param @param width
	 * @param @param height
	 * @param @param ins
	 * @param @return    设定文件
	 * @return byte[]    返回类型
	 * @throws
	 */
	public byte [] cutImg(int width, int height ,InputStream ins)
	{
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			Image img = ImageIO.read(ins);
			BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			buffImg.getGraphics().drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0 , 0 , null);
			ImageIO.write(buffImg, "JPEG", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
	
	/**
	 * 删除系统
	 * @return
	 */
	@PrivilegeAnnotation
	@RequestMapping(value= "/delSystem",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> delSystem(@RequestParam("id")Integer id){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sysService.deleteSys(id);
			
			model.put(AppConst.STATUS, AppConst.SUCCESS);
			model.put(AppConst.MESSAGES, getDeleteSuccess());
			
		} catch (Exception e) {
			e.printStackTrace();
			model.put(AppConst.STATUS, AppConst.FAIL);
			model.put(AppConst.MESSAGES, getDeleteFail());
		}
		return model;
	}
	
	/**
	 * 
	 * @Title: updateSys
	 * @Description: TODO(修改系统)
	 * @param @param logisystem
	 * @param @param file
	 * @param @param liststr
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/updateSys",method=RequestMethod.POST)
	public ModelAndView updateSys(LogiSystem logisystem,@RequestParam("imgs") CommonsMultipartFile file){
		
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			if(!file.isEmpty())
			logisystem.setSysIcon(cutImg(48,48 ,file.getInputStream()));
			logisystem.setStatus(AppConst.STATUS_ENABLE);
			
			Integer ub = sysService.updateSys(logisystem);
			if(ub>0)
			{
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
	 * 分页展示模糊查询
	 * @return
	 */
	@RequestMapping(value = "/findLogiSystemByPage",method=RequestMethod.POST)  
	public @ResponseBody EasyUIPage getlogiSystemBycondition(EasyUIPage page,@RequestParam("rows")Integer rows,String condtionname,String condition)
	{
		HashMap<String,Object> _map= new HashMap<String, Object>();
		_map.put("status",AppConst.DISABLE);
		_map.put(condtionname,condition);
		page.setPagePara(rows);
		return sysService.getLogiSystemBycondition(page, _map);
	}
	
	/**
	 * 获取系统logo
	 * @Title: getSysLogo
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param id
	 * @param @return    设定文件
	 * @return byte[]    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getSysLogo", method=RequestMethod.GET)
	public void getSysLogo(@RequestParam("id")Integer id,HttpServletResponse response)
	{		
		try {
			Map map = (Map) sysService.getSysLogo(id);
			byte [] b = (byte[]) map.get("sysLogo");
			response.reset() ;
			ByteArrayInputStream bis = new ByteArrayInputStream(b); 
			BufferedImage buff = ImageIO.read(bis);
			response.getOutputStream().write(b);
			
			ImageIO.write(buff, "", response.getOutputStream()) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 获取系统已获取的树结构
	 * @Title: getSysUsedTreeList
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param id
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getSysUsedTreeList", method= RequestMethod.GET)
	public @ResponseBody List<TreeNode> getSysUsedTreeList(@RequestParam("id")Integer id)
	{
		List<TreeNode> list = sysService.getSysUsedTreeList(id);
		return list;
	}
	
	/**
	 * 
	 * @Title: getlogiSystem
	 * @Description: TODO(查询对应系统信息，跳转对应界面)
	 * @param @param sysId
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/getLogiSystemDetail",method=RequestMethod.GET)
	public ModelAndView getlogiSystem()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("sysManager/addSys");
		return mv;
	}
	
	//系统预览
	@RequestMapping(value="/seeSys")
	public ModelAndView seeSys(@RequestParam(value="id")Integer sysid){
		ModelMap map = new ModelMap();
		map.put("sysid", sysid);
		return new ModelAndView("sysManager/seeSys",map);
	}
	
	/**
	 * @Title: getLogiSystemUpdate
	 * @Description: TODO(修改系统)
	 * @param @param id
	 * @param @return    设定文件
	 * @return Model    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/getLogiSystemUpdate",method=RequestMethod.GET)
	public ModelAndView getLogiSystemUpdate(@RequestParam("id")String id)
	{
		ModelAndView mv = new ModelAndView();
		LogiSystem logs = sysService.getSysMod(id);
		mv.addObject("LogiSystem", logs);
		mv.setViewName("sysManager/updateSys");
		return mv;
	}
	
	/**lp3
	 * @Title: getSysTree
	 * @Description: TODO(获取当前登录用户的系统权限树(添加角色用)  zTree)
	 * @return List
	 * @throws
	 */
	@RequestMapping(value="/getSysTree")
	public @ResponseBody List<Ztree> getSysTree(){
		List<Ztree> list = treeService.getUserSysTree(getSessionBean());
		return list;
	}
	
	/**lp3
	 * @Title: getSysTreeByid
	 * @Description: TODO(根据系统id获取系统权限树)
	 * @param sysid
	 * @return List<Ztree>
	 * @throws
	 */
	@RequestMapping(value="/getSysTreeByid",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<Ztree> getSysTreeByid(@RequestParam("sysid")String sysid){
		List<Ztree> sysZtrees = treeService.getSysTreeById(Integer.parseInt(sysid));
		return sysZtrees;
	}
	
	@PrivilegeAnnotation
	@RequestMapping(value="/getModelsForUpdate",method=RequestMethod.GET)
	public ModelAndView getModelsForUpdate(@RequestParam("id")String id)
	{
		ModelAndView mv = new ModelAndView(); 
		LogiSystem system = this.sysService.getSysMod(id);
		mv.addObject("id", id);
		mv.addObject("sysName", system.getSysName());
		mv.setViewName("sysManager/assignModels");
		return mv;
	}
	
	@RequestMapping(value="/getAssignModels")
	public @ResponseBody List<Ztree> getAssignModels(@RequestParam("id")String id){
		
		int sysId = Integer.parseInt(id);
		List<Ztree> assignModels = this.sysService.getAssignModels(sysId);
	
		return assignModels;
	}
	
	@RequestMapping(value="/getModelsWithChecked",method=RequestMethod.POST)  
	public @ResponseBody EasyUIPage getModelsWithChecked(EasyUIPage page,@RequestParam("rows")Integer rows,String modName){
		
		HashMap<String,Object> map= new HashMap<String, Object>();
		map.put("modName",modName);
		map.put("status",AppConst.DISABLE);
		page.setPagePara(rows);
		return sysService.getModelsWithChecked(page, map);
	}
	
	@RequestMapping(value="/saveAssignModels",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> saveAssignModels(@RequestParam(value="mods")String mods,String id){
		Map<String,Object> model = new HashMap<String,Object>();
		List<Ztree> ztrees = JSON.parseArray(mods,Ztree.class);
		String sysId = id;
		try{
			int i = this.sysService.saveAssignModels(ztrees, sysId);
			model.put(AppConst.STATUS, AppConst.SUCCESS);
			model.put(AppConst.MESSAGES, getUpdateSuccess());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage(), e);
			model.put(AppConst.MESSAGES,getUpdateFail());
			model.put(AppConst.STATUS, AppConst.FAIL);
		}
		return model;
	}
	
	@RequestMapping(value="/getModelsBySysId")
	public @ResponseBody List<Ztree> getModelsBySysId(@RequestParam("id")String id){
		
		int sysId = Integer.parseInt(id);
		List<Ztree> assignModels = this.sysService.getAssignModels(sysId);
	
		return assignModels;
	}
	
	@RequestMapping(value="/getFuncsForUpdate",method=RequestMethod.GET)
	public ModelAndView getFuncsForUpdate(@RequestParam("modId")String modId,@RequestParam("sysId")String sysId)
	{
		ModelAndView mv = new ModelAndView(); 
		Model model = this.sysService.getModelById(modId);
		mv.addObject("modId", modId);
		mv.addObject("sysId", sysId);
		mv.addObject("modName", model.getModName());
		mv.setViewName("sysManager/assignFuncs");
		return mv;
	}
	
	@RequestMapping(value="/delSysAndMod",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> delSysAndMod(@RequestParam("modId")String modId,@RequestParam("sysId")String sysId)
	{
		Map<String,Object> model = new HashMap<String,Object>();
		try{
			sysService.deleteSysMod(Integer.parseInt(modId), Integer.parseInt(sysId));
			model.put(AppConst.STATUS, AppConst.SUCCESS);
			model.put(AppConst.MESSAGES, getDeleteSuccess());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage(), e);
			model.put(AppConst.MESSAGES,getDeleteFail());
			model.put(AppConst.STATUS, AppConst.FAIL);
		}
		return model;
	}
	
	@RequestMapping(value="/getAssignFucs")
	public @ResponseBody List<Ztree> getAssignFucs(@RequestParam("modId")String modId,@RequestParam("sysId")String sysId){
		
		List<Ztree> assignFucs = this.sysService.getAssignFuncs(modId,sysId);
	
		return assignFucs;
	}
	
	@RequestMapping(value="/getFuncs",method=RequestMethod.POST)  
	public @ResponseBody EasyUIPage getFuncsWithChecked(EasyUIPage page,@RequestParam("rows")Integer rows,String funcName){
		
		HashMap<String,Object> map= new HashMap<String, Object>();
		map.put("funcName",funcName);
		map.put("status",AppConst.DISABLE);
		page.setPagePara(rows);
		return sysService.getFuncs(page, map);
	}
	
	@RequestMapping(value="/saveAssignFuncs",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> saveAssignFuncs(@RequestParam(value="funcs")String funcs,String modId,String sysId){
		Map<String,Object> model = new HashMap<String,Object>();
		List<Ztree> ztrees = JSON.parseArray(funcs,Ztree.class);
		try{
			int i = this.sysService.saveAssignFuncs(ztrees,modId ,sysId);
			model.put(AppConst.STATUS, AppConst.SUCCESS);
			model.put(AppConst.MESSAGES, getUpdateSuccess());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage(), e);
			model.put(AppConst.MESSAGES,getUpdateFail());
			model.put(AppConst.STATUS, AppConst.FAIL);
		}
		return model;
	}
	
}
