/**
 * 
 */
package cn.ccsgroup.ccsframework.sysManager.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.comet4j.core.CometContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.base.entity.Dict;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Func;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.entity.SMS;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.httpService.CCSHttpClient;
import cn.ccsgroup.ccsframework.httpService.HttpClientResultBean;
import cn.ccsgroup.ccsframework.httpService.SSOAuthHttpClient;
import cn.ccsgroup.ccsframework.sysManager.service.SmsService;
import cn.ccsgroup.ccsframework.sysManager.service.UserPowerService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
import cn.ccsgroup.ccsframework.utils.XmlSessionBeanUtils;

/**
 * @Package:      [cn.ccsgroup.ccsframework.sysManager.controller]  
 * @ClassName:    [UserPowerController]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-12-18]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-18，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [版本号]
 */
@Controller
@RequestMapping("/ccs")
public class UserPowerController extends BaseController{
	
	@Resource(name="userPowerService")
	private UserPowerService userPowerService;
	@Resource(name="smsService")
	private SmsService smsService;
	
	@RequestMapping(value="/menu")
	public ModelAndView menu(){
		String serverName = this.getRequest().getServerName();
		String port = this.getRequest().getServerPort()==80 ?"":Integer.toString(this.getRequest().getServerPort());
		
		ModelAndView mv = new ModelAndView("main");
		SessionBean sessionBean = getSessionBean();
		List<LogiSystem> list = userPowerService.getRolSys(sessionBean);
		
		List<Model> models = userPowerService.getRoleMods(sessionBean,list.isEmpty()?0:list.get(0).getId());
		models = changeUrl(models,serverName,port);

		mv.addObject("userTree",models); // 用户树
		Map<Integer,Object> map = new HashMap<Integer,Object> ();
		for (Iterator<LogiSystem> iterator = list.iterator(); iterator.hasNext();) {
			LogiSystem logiSystem = iterator.next();
			if(map.containsKey(logiSystem.getId()))
				list.remove(logiSystem);
			else
				map.put(logiSystem.getId(), logiSystem);
		}
		String loginid = sessionBean.getLoginid();

		//登陆后注册comet4j通信通道
//		CometContext cc = CometContext.getInstance();
//		if(!cc.getAppModules().contains(loginid)){
//			cc.registChannel(loginid);//注册应用的channel
//		}

		Dict system = sessionBean.getSystem();
		int smsCount = smsService.getSMSCountByLoginidAndSysName(loginid, system.getMc());
		String regionName = userPowerService.getRegionNameByCode(sessionBean.getRegionCode());
		mv.addObject("smsCount",smsCount);
		mv.addObject("sysPower", list ); // 用户系统树
		mv.addObject("regionCodes", sessionBean.getRegionCodes());
		mv.addObject("regionCode", sessionBean.getRegionCode());
		mv.addObject("regionName", regionName);
		return mv;
	}

	@RequestMapping(value="/editPassword")
	public ModelAndView checkUser(){
		return new ModelAndView("editPassword");
	} 
	
	@RequestMapping(value="/checkUser",method = RequestMethod.POST)
	public void checkUser(@RequestParam("oldpassword")String oldpassword,
			@RequestParam("newpassword")String newpassword,
			@RequestParam("repeatpassword")String repeatpassword,HttpServletResponse response){
		
		SessionBean sessionBean = getSessionBean();
		ObjectMapper om = new ObjectMapper();
		try {
			if("".equals(newpassword) && newpassword.trim().length() == 0){
				om.writeValue(response.getWriter(), "密码输入有误");
			}else{
				if(newpassword.equals(repeatpassword)){
					UserManager user = userPowerService.checkUser(sessionBean.getLoginid(), oldpassword);//数据库验证原密码是否正确
					if(user != null){
						Boolean flag = userPowerService.updateUserPwd(newpassword,user.getId());
						if(flag){
							om.writeValue(response.getWriter(), 1);
						}else{
							om.writeValue(response.getWriter(), "修改失败");
						}
					}else{
						om.writeValue(response.getWriter(), "原密码有误");
					}
				}else{
					om.writeValue(response.getWriter(), "两次新密码输入不相同");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取用户系统权限
	 * @return
	 */
	@RequestMapping(value="/findUserPower")
	public ModelAndView findUserPower()
	{
		ModelMap model = new ModelMap();
		// 获取所有功能
		List<Map<String, Object>> vList = userPowerService.getUserMods("DE-CC");
		model.put("userTree", getUserMods(vList)); // 用户树
		model.put("sysPower", getUserSysPower()); // 用户系统树
		model.put(AppConst.USER_SESSION, super.getSessionBean());
		return new ModelAndView("main",model);
	}
	
	/**
	 * 切换系统list
	 * @return
	 */
//	@RequestMapping(value="/changeSysList")
//	public @ResponseBody Map<String,Object> changeSysList(String briefName)
//	{
//		Map<String, Object> map = new HashMap<String, Object>();
//		List<Map<String, Object>> vList = userPowerService.getUserMods(briefName);
//		map.put("userTree", getUserMods(vList));
//		return map;
//	}
	

	/**
	 * 切换系统list
	 * @return
	 */
	@RequestMapping(value="/changeSysList")
	public @ResponseBody Map<String,Object> changeSysList(@CookieValue(AppConst.SESSION_KEY) String sessionKey,Integer briefName)
	{
		String serverName = this.getRequest().getServerName();
		String port = this.getRequest().getServerPort()==80 ?"":Integer.toString(this.getRequest().getServerPort());
		
		Map<String, Object> map = new HashMap<String, Object>();
		CCSHttpClient http = new SSOAuthHttpClient();
		Map<String,Object> param = new HashMap<String,Object>();
		Dict system = userPowerService.getSystem(briefName);
		param.put("system", system);
		Object[] arr = null;
		try {
			param.put("json",XmlSessionBeanUtils.createXML(param));
			param.remove("system");
			param.put("sessionId", sessionKey);
			arr = new Object[]{"S003",param};
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("系统参数错误！");
		}
		HttpClientResultBean result = http.getResult(arr, CCSHttpClient.POST);
		if(result.isSuccess()){
			if("0".equals(result.getResult())){
				SessionBean sessionBean = getSessionBean();
				sessionBean.setSystem(system);
				List<Model> models = userPowerService.getRoleMods(sessionBean,briefName);
				models = changeUrl(models,serverName,port);
				map.put("userTree", models); // 用户树
			}else if("-1".equals(result.getResult())){
				throw new RuntimeException("系统参数错误！");
			}
		}
		return map;
	}
	
	/**
	 * 返回用户可用系统树
	 * @return
	 */
	public List<LogiSystem> getUserSysPower()
	{
		List<LogiSystem> vList = new ArrayList<LogiSystem>();
		List<LogiSystem> lList = userPowerService.getUserSysPower();
		for(int index=0;index<lList.size();index++)
		{
			LogiSystem ls = lList.get(index);
			LogiSystem vL = new LogiSystem();
			vL.setBriefName(ls.getBriefName());
			vL.setSysUrl(ls.getSysUrl());
			vL.setSysName(ls.getSysName());
			vL.setId(ls.getId());
			vList.add(vL);
		}
		return vList;
	}
	
	/**
	 * 获取用户系统模块列表
	 * @return
	 */
	public List<Model> getUserMods(List<Map<String, Object>> vList)
	{
		List<Model> mList = new ArrayList<Model>();
		// 父子类那关系
		Map<String, List<Func>> tmpMap = new HashMap<String, List<Func>>();
		for(int index = 0;index < vList.size(); index ++)
		{
			Map<String, Object> map = vList.get(index);
			if(!tmpMap.containsKey(String.valueOf(map.get("modId")))) //  存在节点
			{
				List<Func> childList = new ArrayList<Func>();
				childList.add(createFunc(map));
				tmpMap.put(String.valueOf(map.get("modId")), childList);
			}else
			{
				tmpMap.get(String.valueOf(map.get("modId"))).add(createFunc(map));
			}
		}
		List<String> usedKey = new ArrayList<String>();
		// 开始解析对应关系
//		int index = 0;
		for(String key : tmpMap.keySet())
		{
//			index ++;
			for(Map<String, Object> vMap : vList)
			{
				// 如果是存在该模块
				if(key.equalsIgnoreCase(String.valueOf(vMap.get("modId"))))
				{
					if(!usedKey.contains(key))  // 已添加
					{
						usedKey.add(key);
						Model m = createModel(vMap); // 创建model
						m.setfList(tmpMap.get(key));
						m.setSortNo(Integer.parseInt(String.valueOf(vMap.get("sortNo"))));
						mList.add(m); // 不存在列
					}
				}
			}
		}
		return mList;
	}
	
	/**
	 * 获取简单func对象
	 * @param vMap
	 * @return
	 */
	private static Func createFunc(Map<String, Object> vMap)
	{
		Func f = new Func();
		f.setFuncName(String.valueOf(vMap.get("funcName")));
		f.setFuncUrl(String.valueOf(vMap.get("funcUrl")));
		return f;
	}
	
	/**
	 * 获取简单的model对象
	 * @param vMap
	 * @return
	 */
	private static Model createModel(Map<String, Object> vMap)
	{
		Model m = new Model(); 
		m.setModName(String.valueOf(vMap.get("modName")));
		m.setId(String.valueOf(vMap.get("modId")));
		return m;
	}
	
	private List<Model> changeUrl(List<Model> models, String serverName,String port) {
		Cookie [] cookies = this.getRequest().getCookies();
		String sessionid = "";
		for(Cookie cookie:cookies){
			if(cookie.getName().equals(AppConst.SESSION_KEY)){
				sessionid = cookie.getValue();
			}
		}
		String regex = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)|(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
		for (Model model : models) {
			List<Func> fList = model.getfList();
			for (Func func : fList) {
				if (func.getFuncUrl() != null) {
					String funcUrl = func.getFuncUrl();
					
					URL urlObj = null;
					String host = null;
					int getPort = 0;
					try
					{
						urlObj = new URL(funcUrl);
						host = urlObj.getHost();
						getPort = urlObj.getPort();
					}
					catch (MalformedURLException e)
					{
						e.printStackTrace();
					}
					
					if("".equals(port)){
						if(getPort==-1){
							funcUrl = funcUrl.replaceFirst(regex, serverName);
						}else{
							funcUrl = funcUrl.replaceFirst(regex, serverName);
							funcUrl = funcUrl.replaceFirst(":"+getPort, "");
						}
					}else{
						if(getPort==-1){
							funcUrl = funcUrl.replaceFirst(regex, serverName + ":" + port);
						}else{
							funcUrl = funcUrl.replaceFirst(regex, serverName);
							//funcUrl = funcUrl.replaceFirst(Integer.toString(getPort), port);
						}
						
					}
					/*if(funcUrl.indexOf("?")>-1){
						funcUrl += "&sessionid="+sessionid;
					}else{
						funcUrl += "?sessionid="+sessionid;
					}*/
					func.setFuncUrl(funcUrl);
				}
			}
		}
		return models;
	}
}
