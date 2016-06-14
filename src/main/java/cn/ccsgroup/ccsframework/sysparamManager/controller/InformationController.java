package cn.ccsgroup.ccsframework.sysparamManager.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.base.entity.Dict;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Annex;
import cn.ccsgroup.ccsframework.entity.Information;
import cn.ccsgroup.ccsframework.sysparamManager.service.InformationService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysparamManager.controller.InformationController.java]  
 * @ClassName:    [InformationController]   
 * @Description:  [公告管理Controller]   
 * @Author:       [lenovo]   
 * @CreateDate:   [2014-1-6 上午11:49:23]   
 * @UpdateUser:   [lenovo(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-6 上午11:49:23，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@Controller
@RequestMapping("/InformationController")
@Transactional
public class InformationController extends BaseController{

	@Resource(name="InformationService")
	private InformationService informationService;
	public void setInformationService(InformationService informationService) {
		this.informationService = informationService;
	}

	/**
	 * @Title: viewPostion
	 * @Description: TODO(公告界面入口)
	 *  @return   设定文件
	 * @return String    返回类型
	 * @throws
	 */
	@RequestMapping(value="/viewInformation")
	public String viewInformation(){
		return "informationManager/information";
	}
	
	/**
	 * @Title: getInformations
	 * @Description: TODO(获取公告列表带翻页)
	 *  @param rows
	 *  @param page
	 *  @return   
	 * @return EasyUIPage    
	 * @throws
	 */
	@RequestMapping(value="/getInformationsList")
	public @ResponseBody EasyUIPage getInformations(@RequestParam(value="rows")Integer rows,
			EasyUIPage page){
		page.setPagePara(rows);
		page = informationService.getInformationList(page);
		return page;
	}
	
	/**
	 * @Title: getAddInfoMationView
	 * @Description: TODO(获取新增界面)
	 * @return   
	 * @return String    
	 * @throws
	 */
	@RequestMapping(value="/create")
	public String getAddInfoMationView(){
		return "informationManager/addInformation";
	}
	/**
	 * @Title: getUpdateInfoMationView
	 * @Description: TODO(获取修改界面)
	 *  @param id
	 *  @return 
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/update")
	public ModelAndView getUpdateInfoMationView(@RequestParam(value="id")Integer id){
		ModelMap map = new ModelMap();
		map.put("information", informationService.getInformationsById(id));
		return new ModelAndView("informationManager/updateInformation",map);
	}
	/**
	 * @Title: getupdateAnnexList
	 * @Description: TODO(获取附件列表)
	 *  @param id 公告ID
	 *  @return   
	 * @return List<Annex> 
	 * @throws
	 */
	@RequestMapping(value="/getAnnexList")
	public @ResponseBody List<Annex> getupdateAnnexList(@RequestParam(value="id")Integer id){
		return informationService.getAnnextListById(id);
	}
	/**
	 * @Title: addInformation
	 * @Description: TODO(新增公告)
	 *  @param information
	 *  @param appids
	 *  @param fileids
	 *  @return
	 *  @throws IOException   设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/addInformation",method=RequestMethod.POST)
	public ModelAndView addInformation(Information information,String[] appids,String[] fileids) throws IOException
	{
		ModelAndView mode = new ModelAndView(AppConst.FORWORD);
		Map<String, String> map = new HashMap<String, String>();
		map.put(AppConst.STATUS, AppConst.SUCCESS);
		map.put(AppConst.MESSAGES,super.getAddSuccess());
		information.setCreateruser(getSessionBean().getUserName());
		int id = informationService.saveInformation(information,appids);
		
		List<Annex> list = new ArrayList<Annex>();
		for(int i= 0 ; i < fileids.length ; i++){
			String fileid = fileids[i];
			 for (String key : fileMap.keySet()) {
				 if(fileid.equals(key)){
					 fileMap.get(key).setInfoid(id);
					 list.add(fileMap.get(key));
				 }
			 }
		}
		fileMap.clear();
		informationService.saveAnnex(list);
		mode.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mode;
	}
	/**
	 * @Title: updateInformation
	 * @Description: TODO(修改公告)
	 *  @param information
	 *  @param appids
	 *  @param fileids
	 *  @return
	 *  @throws IOException   设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/updateInformation",method=RequestMethod.POST)
	public ModelAndView updateInformation(Information information,String[] appids,String[] fileids) throws IOException
	{
		ModelAndView mode = new ModelAndView(AppConst.FORWORD);
		Map<String, String> map = new HashMap<String, String>();
		map.put(AppConst.STATUS, AppConst.SUCCESS);
		map.put(AppConst.MESSAGES,super.getUpdateSuccess());
		mode.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		information.setCreateruser(getSessionBean().getUserName());
		informationService.updateInformation(information);
		informationService.saveInformationMapping(information.getId(),appids,information.getCreateruser());
		List<Annex> annexList = informationService.getAnnextListById(information.getId());
		List<Annex> list = new ArrayList<Annex>();
		for(int i = 0 ; i < fileids.length ; i++){
			boolean isNum = fileids[i].matches("[0-9]+");
			if(isNum){//原有附件及删除的附件
				for(int j = 0 ; j < annexList.size() ; j++){
					if(annexList.get(j).getId().equals(Integer.valueOf(fileids[i]))){
						annexList.get(j).setStatus(1);
					}
				}
			}else{//新增的附件
				 for (String key : fileMap.keySet()) {
					 if(fileids[i].equals(key)){
						 fileMap.get(key).setInfoid(information.getId());
						 list.add(fileMap.get(key));
					 }
				 }
			}
		}
		List<Annex> deleteAnnexList = new ArrayList<Annex>();
		for(int i = 0 ; i <annexList.size() ;i++){
			if(annexList.get(i).getStatus()==null){
				deleteAnnexList.add(annexList.get(i));
			}
		}
		informationService.saveAnnex(list);
		informationService.deleteAnnex(deleteAnnexList);
		return mode;
	}
	
	/**
	 * @Title: delInformation
	 * @Description: TODO(删除公告)
	 *  @param id
	 *  @return   设定文件
	 * @return Map<String,String>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public @ResponseBody Map<String,String> delInformation(@RequestParam(value="id")Integer id)
	{
		Map<String, String> map = new HashMap<String, String>();
		boolean b = informationService.deleteInformation(id);
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
	 * @Title: getSendAppList
	 * @Description: TODO(获取已发送的系统)
	 *  @param infoid
	 *  @return   设定文件
	 * @return List    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getSendAppList")
	public @ResponseBody List getSendAppList(@RequestParam(value="infoid")Integer infoid){
		List appList = informationService.getSendAppList(infoid);
		return appList;
	}
	
	
	HashMap<String, Annex> fileMap = new HashMap<String, Annex>();
	/**
	 * @Title: doFile
	 * @Description: TODO(上传附件)
	 *  @param file
	 *  @param fileid
	 *  @return
	 *  @throws IOException   设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/doFile",method=RequestMethod.POST)
	public ModelAndView doFile(MultipartFile  file,String fileid) throws IOException{
		 Annex annex = new Annex();
		 annex.setFilecontent(FileCopyUtils.copyToByteArray(file.getInputStream()));
		 annex.setFilename(file.getOriginalFilename());
		 annex.setFilesize(file.getSize());
		 annex.setFileformat(file.getContentType());
		 fileMap.put(fileid, annex);
		 
		 ModelAndView mode = new ModelAndView(AppConst.FORWORD);
		 Map<String, String> map = new HashMap<String, String>();
		 map.put(AppConst.STATUS, AppConst.SUCCESS);
		 map.put(AppConst.MESSAGES,super.getAddSuccess());
		 mode.addObject(AppConst.MESSAGES, new Gson().toJson(map));
		return mode;
	}
	/**
	 * @Title: getAppList
	 * @Description: TODO(获取需发送系统列表)
	 *  @param id
	 *  @return   设定文件
	 * @return List    返回类型
	 * @throws
	 */
	@RequestMapping(value="/getAppList")
	public @ResponseBody List getAppList(@RequestParam(value="id",required = false)Integer id){
		List appList = new ArrayList();
		if(id == null){
			appList = informationService.getAppList();
		}else{
			List allList = informationService.getAppList();
			List sendList = informationService.getSendAppList(id);
			for(int i = 0 ; i < allList.size() ; i++){
				HashMap allMap = (HashMap)allList.get(i);
				for(int j = 0 ; j < sendList.size(); j++){
					HashMap sendMap = (HashMap)sendList.get(j);
					if(allMap.get("id").equals(sendMap.get("id"))){
						allMap.put("status", "1");
					}
				}
				appList.add(allMap);
			}
		}
		return appList;
	}
	
	
	
	/**
	 * @Title: viewNotice
	 * @Description: TODO(首页公告界面数据获取)
	 *  @param id
	 *  @return   设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/notice")
	public ModelAndView viewNotice(@RequestParam(value="id",required = false)Integer id)
	{
		SessionBean sessionBean = this.getSessionBean();
		List sysList =  sessionBean.getSystems();
		 Integer[] sysid = new Integer[sysList.size()];
		for(int i = 0 ; i < sysList.size() ; i++){
			sysid[i] = Integer.parseInt(((Dict)sysList.get(i)).getDm());
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Information> list = informationService.queryNoticeList(sysid);
		Information f = null ;
		if(id == null){
			if(list.size()>0){
				f = list.get(0);
			}
		}else{
			for(int i = 0 ; i < list.size() ; i++){
				if(list.get(i).getId().equals(id)){
					f = list.get(i);
				}
			}
		}
		map.put("Info", list);
		map.put("finfo", f);
		return new ModelAndView("welcome",map);
	}
	
	/**
	 * @Title: downloadFile
	 * @Description: TODO(附件下载)
	 *  @param id
	 *  @param response
	 *  @throws IOException   设定文件
	 * @return void    返回类型
	 * @throws
	 */
	@RequestMapping(value="/downloadFile")
	public void downloadFile(Integer id,HttpServletResponse response) throws IOException
	{
		Annex file = informationService.queryAnnexById(id);
		byte[] data = file.getFilecontent();
		response.reset();   
		response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"");   
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(data);   
		outputStream.flush();
		outputStream.close();
	}
	
	/**
	 * @Title: getviewInfoMationView
	 * @Description: 双击查看
	 * @param @param id
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping(value="/view")
	public ModelAndView getviewInfoMationView(@RequestParam(value="id")Integer id){
		ModelMap map = new ModelMap();
		map.put("information", informationService.getInformationsById(id));
		return new ModelAndView("informationManager/viewInformation",map);
	}
	
}
