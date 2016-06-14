/**
 * 
 */
package cn.ccsgroup.ccsframework.sysparamManager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import cn.ccsgroup.ccsframework.annotation.PrivilegeAnnotation;
import cn.ccsgroup.ccsframework.annotation.TokenAnnotation;
import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.RegionCode;
import cn.ccsgroup.ccsframework.sysparamManager.service.RegionManageService;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysManager.controller.RegionManageController.java]  
 * @ClassName:    [RegionManageController]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [tuliangyu]   
 * @CreateDate:   [2013-12-12 上午11:37:05]   
 * @UpdateUser:   [tuliangyu(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-12 上午11:37:05，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@Controller
@Scope("prototype")
@RequestMapping("/RegionManageController")
public class RegionManageController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name="RegionManageService")
	private RegionManageService regionManageService;
	/**
	 * 初始化组织机构
	 * @return
	 */
	@RequestMapping(value = "/initRegionCode") 
	public  ModelAndView initRegionCode()
	{
		ModelAndView mv = new ModelAndView("regionManage/regionManage");
		return mv;
	}
	
	/**
	 * @Title: getAllRegionCode
	 * @Description: TODO(获取所有有效的区域)
	 * @param @return    设定文件
	 * @return List<RegionCode>    返回所有有效区域的树菜单list对象
	 * @throws
	 */
	@RequestMapping(value = "/findAllRegionCode",method=RequestMethod.GET) 
	public @ResponseBody List<TreeNode> findAllRegionCode()
	{
		List<TreeNode> regionCodeList =regionManageService.findAllRegionCode(getSessionBean().getRegionCode());
		return regionCodeList;
	}
	
	/******
	 * 修改区域管理
	 * @param regionCode
	 * @return
	 */
	public @ResponseBody Map<String, Object> updateRegionCode(RegionCode regionCode)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		String flag = regionManageService.updateRegionCode(regionCode);
		if(flag=="success") // 修改成功
		{
			map.put("erTxt", "修改操作成功！");
		}else //修改失败
		{
			map.put("erTxt", "修改操作失败！");
		}
		return map;
	}
	
	public @ResponseBody Map<String, Object> updateSubRegionCode(RegionCode regionCode)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		String flag = regionManageService.updateSubRegionCode(regionCode);
		if(flag=="success") // 修改成功
		{
			map.put("erTxt", "修改操作成功！");
		}else //修改失败
		{
			map.put("erTxt", "修改操作失败！");
		}
		return map;
	}
	
	@RequestMapping(value="/findRegionCode",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> findRegionCode(@RequestParam(value="id")String id,@RequestParam(value="regionCode")String regionCode){
		List<RegionCode> regionCodeList=regionManageService.findRegionCode(id,regionCode);
		Map<String,Object> map = new HashMap<String,Object>();
		if(regionCodeList.size()==0){
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES, "");
		}else{
			map.put(AppConst.MESSAGES,"该区域编码已经存在！请修正！");
			map.put(AppConst.STATUS, AppConst.FAIL);
		}
		return map;
	}
	
	@PrivilegeAnnotation()//进行拦截
	@TokenAnnotation(remove=true)
	@RequestMapping(value="/saveRegionCode",method = RequestMethod.POST)
	public ModelAndView saveRegionCode(RegionCode regionCode)
	{
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> map = new HashMap<String,Object>();
			regionCode.setStatus(1);
				boolean addbool=regionManageService.saveRegionCode(regionCode);
				if(addbool){
					map.put(AppConst.STATUS, AppConst.SUCCESS);
					map.put(AppConst.MESSAGES, getAddSuccess());
				}else{
					map.put(AppConst.MESSAGES,getAddFail());
					map.put(AppConst.STATUS, AppConst.FAIL);
				}
				mv.addObject(AppConst.MESSAGES, new Gson().toJson(map));
				return mv;
	}
	
	@PrivilegeAnnotation()//进行拦截
	@TokenAnnotation(remove=true)
	@RequestMapping(value="/updRegionCode",method = RequestMethod.POST)
	public ModelAndView updRegionCode(RegionCode regionCode)
	{
		ModelAndView mv = new ModelAndView(AppConst.FORWORD);
		Map<String,Object> map = new HashMap<String,Object>();
		regionCode.setStatus(1);
		if(updateRegCodRec(regionCode)){
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES, getUpdateSuccess());
		}else{
			map.put(AppConst.STATUS, AppConst.FAIL);
			map.put(AppConst.MESSAGES, getUpdateFail());
		}
		mv.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mv;
	}
	
	
	/**
	 * @Title: updateRegionCode
	 * @Description: TODO(递归修改regionCode)
	 * @param     设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public boolean updateRegCodRec(RegionCode regionCode)
	{
		boolean bool=false;
		/*RegionCode regionCodeData= regionManageService.getRegionCode(regionCode.getId());
		if(!regionCodeData.getRegionCode().equalsIgnoreCase(regionCode.getRegionCode())){
			List<RegionCode> subRegionCodeList=regionManageService.getAllSubRegionCode(regionCode.getId());//获取所有子区域
			if(subRegionCodeList.size()>0){
				//--regionManageService
				for(int b=0;b<subRegionCodeList.size();b++){
						RegionCode subRegionCode=subRegionCodeList.get(b);
						String lastStr=subRegionCode.getRegionCode().substring(regionCodeData.getRegionCode().length());
						subRegionCode.setRegionCode(regionCode.getRegionCode()+lastStr);
						bool=true;
						updateRegCodRec(subRegionCode);
						regionManageService.updateRegionCode(subRegionCode);
				}
			}
		}*/
		try {
			regionManageService.updateRegionCode(regionCode);
			bool=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bool;
	}
	
	
	/**
	 * @Title: deleteRegionCode
	 * @Description: TODO(删除regionCode)
	 * @param     设定文件
	 * @return void    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation()//进行拦截
	@RequestMapping(value="/deleteRegionCode",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteRegionCode(@RequestParam(value="id")String id){
//		RegionCode regionCode=regionManageService.getRegionCode(id);
//		if(regionCode!=null){
			/********************************************注释部分为级联删除的东西*****************************************/
			//List<RegionCode> subRegionCodeList=regionManageService.getAllSubRegionCode(regionCode.getId());//获取所有子区域
			/*	if(subRegionCodeList.size()>0){
					for (int i=0;i<subRegionCodeList.size();i++){
						RegionCode subRegionCode=subRegionCodeList.get(i);						
						subRegionCode.setStatus(0);//设置失效
						regionManageService.updateSubRegionCode(subRegionCode);
						bool=true;
						delRegCodRec(subRegionCode.getId());
					}
				}*/
				/********************************************注释部分为级联删除的东西*****************************************/
//				regionCode.setStatus(0);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
				int i =regionManageService.deleteRegionCodeById(id);
				if(i > 0){
					map.put(AppConst.STATUS, AppConst.SUCCESS);
					map.put(AppConst.MESSAGES, getDeleteSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getLocalizedMessage(), e);
				map.put(AppConst.MESSAGES,getDeleteFail());
				map.put(AppConst.STATUS, AppConst.FAIL);
			}
//		}
		return map;
	}
	
	/**
	 * @Title: deleteRegionCode
	 * @Description: TODO(删除区域regiCode/**内含原有递归算法删除区域regionCode的注释代码)
	 * @param     设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public boolean delRegCodRec(String id)
	{
		boolean bool=false;
		RegionCode regionCode=regionManageService.getRegionCode(id);
		if(regionCode!=null){
			/********************************************注释部分为级联删除的东西*****************************************/
			//List<RegionCode> subRegionCodeList=regionManageService.getAllSubRegionCode(regionCode.getId());//获取所有子区域
			try {
			/*	if(subRegionCodeList.size()>0){
					for (int i=0;i<subRegionCodeList.size();i++){
						RegionCode subRegionCode=subRegionCodeList.get(i);						
						subRegionCode.setStatus(0);//设置失效
						regionManageService.updateSubRegionCode(subRegionCode);
						bool=true;
						delRegCodRec(subRegionCode.getId());
					}
				}*/
				/********************************************注释部分为级联删除的东西*****************************************/
				regionCode.setStatus(0);
				regionManageService.updateRegionCode(regionCode);
				bool=true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bool;
	}
}


