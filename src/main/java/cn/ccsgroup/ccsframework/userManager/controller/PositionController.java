package cn.ccsgroup.ccsframework.userManager.controller;

import java.util.HashMap;
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
import cn.ccsgroup.ccsframework.annotation.TokenAnnotation;
import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Position;
import cn.ccsgroup.ccsframework.userManager.service.PositionService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.userManager.controller.PositionControlleer.java]  
 * @ClassName:    [PositionControlleer]   
 * @Description:  [职位控制器]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2013-12-30 下午02:40:21]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2013-12-30 下午02:40:21]   
 * @UpdateRemark: [新建]  
 * @Version:      [v1.0] 
 */
@Controller
@RequestMapping("/PositionController")
public class PositionController extends BaseController {

	@Resource(name="PositionService")
	private PositionService positionService;
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}
	
	/**
	 * @Title: viewPostion
	 * @Description: TODO(职位界面入口)
	 *  @return   设定文件
	 * @return String    返回类型
	 * @throws
	 */
	@RequestMapping(value="/viewPosition")
	public String viewPostion(){
		return "positionManager/position";
	}
	/**
	 * @Title: getAddPositionView
	 * @Description: TODO(获取新增职位界面元素)
	 *  @param orgid 组织机构ID
	 *  @param orgname 组织机构名称
	 *  @return   设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/create")
	public ModelAndView getAddPositionView(@RequestParam(value="orgid")Integer orgid,@RequestParam(value="orgname")String orgname){
		ModelMap map = new ModelMap();
		map.put("orgid", orgid);
		map.put("orgname", orgname);
		return new ModelAndView("positionManager/addPosition",map);
	}
	/**
	 * @Title: getUpdatePositionView
	 * @Description: TODO(获取修改界面元素)
	 *  @param id 职位ID
	 *  @return   设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/update")
	public ModelAndView getUpdatePositionView(@RequestParam(value="id")Integer id){
		ModelMap map = new ModelMap();
		Position p = positionService.queryPosition(id);
		map.put("position", p);
		return new ModelAndView("positionManager/updatePosition",map);
	}
	/**
	 * @Title: getCopyPositionView
	 * @Description: TODO(获取克隆职位界面元素)
	 *  @param id 职位ID
	 *  @return   设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@TokenAnnotation(save=true)
	@RequestMapping(value="/copy")
	public ModelAndView getCopyPositionView(@RequestParam(value="id")Integer id){
		ModelMap map = new ModelMap();
		Position p = positionService.queryPosition(id);
		map.put("position", p);
		return new ModelAndView("positionManager/copyPosition",map);
	}
	/**
	 * @Title: getPositionList
	 * @Description: TODO(通过组织机构ID获取职位列表)
	 *  @param id 组织机构ID
	 *  @param rows 每页显示条数
	 *  @param page 翻页对象
	 *  @return   设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getPositionList",method=RequestMethod.GET)
	public @ResponseBody EasyUIPage  getPositionList(@RequestParam(value="id",required=false)Integer id,
			@RequestParam(value="rows")Integer rows,
			EasyUIPage page
			){
		page.setPagePara(rows);
		if(id!= null){
			page = positionService.queryPositions(id,page);
		}else{
			return null;
		}
		return page;
	}
	/**
	 * @Title: addPosition
	 * @Description: TODO(新增职位)
	 *  @param position
	 *  @return   设定文件
	 * @return Map<String,String>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/addPosition",method=RequestMethod.POST)
	public ModelAndView addPosition(Position position)
	{
		ModelAndView mode = new ModelAndView(AppConst.FORWORD);
		Map<String, String> map = new HashMap<String, String>();
		map.put(AppConst.STATUS, AppConst.SUCCESS);
		map.put(AppConst.MESSAGES,super.getAddSuccess());
		positionService.savePosition(position);
		mode.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mode;
	}
	
	/**
	 * @Title: copyPosition
	 * @Description: TODO(克隆职位)
	 *  @param position
	 *  @return   设定文件
	 * @return Map<String,String>    返回类型
	 * @throws
	 */
	@TokenAnnotation(remove=true)
	@RequestMapping(value="/copyPosition",method=RequestMethod.POST)
	public ModelAndView copyPosition(Position position)
	{
		ModelAndView mode = new ModelAndView(AppConst.FORWORD);
		Map<String, String> map = new HashMap<String, String>();
		map.put(AppConst.STATUS, AppConst.SUCCESS);
		map.put(AppConst.MESSAGES,super.getAddSuccess());
		positionService.savePosition(position);
		mode.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mode;
	}
	
	/**
	 * @Title: updatePosition
	 * @Description: TODO(修改职位)
	 *  @param position
	 *  @return   设定文件
	 * @return Map<String,String>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/updatePosition",method=RequestMethod.POST)
	public ModelAndView updatePosition(Position position)
	{
		ModelAndView mode = new ModelAndView(AppConst.FORWORD);
		Map<String, String> map = new HashMap<String, String>();
		boolean b = positionService.updatePosition(position);
		if(b){
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES,super.getUpdateSuccess());
		}else{
			map.put(AppConst.STATUS, AppConst.ERRORS);
			map.put(AppConst.MESSAGES,super.getUpdateFail());
		}
		mode.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mode;
	}
	/**
	 * @Title: delPosition
	 * @Description: TODO(删除职位)
	 *  @param id
	 *  @return   设定文件
	 * @return Map<String,String>    返回类型
	 * @throws
	 */
	@PrivilegeAnnotation
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public @ResponseBody Map<String,String> delPosition(@RequestParam(value="id")Integer id)
	{
		Map<String, String> map = new HashMap<String, String>();
		boolean b = positionService.deletePosition(id);
		if(b){
			map.put(AppConst.STATUS, AppConst.SUCCESS);
			map.put(AppConst.MESSAGES,super.getDeleteSuccess());
		}else{
			map.put(AppConst.STATUS, AppConst.ERRORS);
			map.put(AppConst.MESSAGES,super.getDeleteFail());
		}
		return map;
	}
	
	/**
	 * @Title: getUpdatePositionView
	 * @Description: 双击查看
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/view")
	public ModelAndView getViewPositionView(@RequestParam(value="id")Integer id){
		ModelMap map = new ModelMap();
		Position p = positionService.queryPosition(id);
		map.put("position", p);
		return new ModelAndView("positionManager/viewPosition",map);
	}
}
