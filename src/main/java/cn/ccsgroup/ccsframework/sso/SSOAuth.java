package cn.ccsgroup.ccsframework.sso;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TryCatchFinally;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.utils.XmlSessionBeanUtils;
import org.omg.CORBA.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.base.entity.SessionEntity;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.httpService.HttpClientResultBean;
import cn.ccsgroup.ccsframework.httpService.SSOAuthHttpClient;
import cn.ccsgroup.ccsframework.httpService.CCSHttpClient;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sso.SSOAuth.java]  
 * @ClassName:    [SSOAuth]   
 * @Description:  [单点登录服务器]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2013-11-27 下午03:28:46]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2013-11-27 下午03:28:46]   
 * @UpdateRemark: [第一次创建]  
 * @Version:      [v1.0] 
 */
@Controller
@RequestMapping("/SSOAuth")
public class SSOAuth extends BaseController{

	private static String loginView = "login";

	private static ConcurrentMap<String,String> SSOIDs = new ConcurrentHashMap<String,String>();

	@RequestMapping(value = "/login")  
	public ModelAndView login(@RequestParam(value="action" ,required = false)String action,UserManager user,
			@RequestParam(value=AppConst.SESSION_KEY,required=false)String cookiename, HttpServletResponse response,ModelMap model) throws IOException{ 
		if (action == null) {
			if(getSession().getAttribute(AppConst.SESSION_KEY)!=null){
				model.addAttribute(getSession().getAttribute(AppConst.SESSION_KEY));
				return new ModelAndView("redirect:/ccs/menu", model);
			}
		}else if (action.equals("login")) {
			CCSHttpClient http = new SSOAuthHttpClient();
			Map<String,String> param = new HashMap<String,String>();
			param.put("loginid", user.getLoginid());
			param.put("password", user.getPassword());
			Object[] arr = new Object[]{"L001",param};
			HttpClientResultBean result = http.getResult(arr, CCSHttpClient.POST);
			if(result.isSuccess()){
				SessionEntity SessionEntity = (SessionEntity) result.getResult();
				if(SessionEntity.getStatus() == 0){
                    SessionBean sessionBean = SessionEntity.getSessionBean();
                    HttpServletRequest request = this.getRequest();

                    sessionBean.setUserIp(getIpAddr(request));
					getSession().setAttribute(AppConst.USER_SESSION, sessionBean);
                    Map<String,Object> attrubute = new HashMap<String,Object>();
                    attrubute.put("userIp",sessionBean.getUserIp());
                    try {
                        String sessionUserIp = XmlSessionBeanUtils.createXML(attrubute);
                        param.put("json",sessionUserIp);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    param.put("sessionId",http.getSessionCookie().getValue());
                    Object[] params = new Object[]{"S003",param};

                    HttpClientResultBean updateResult = http.getResult(params, CCSHttpClient.POST);

                    if(updateResult.isSuccess()){
                        if(updateResult.getResult().equals("-1")){
                            throw new RuntimeException("更新memcach server上的sessionbean异常，请重新登录");
                        }
                    }

					if(http.getSessionCookie() != null){
						Cookie cookie = new Cookie(http.getSessionCookie().getName(), http.getSessionCookie().getValue());
						cookie.setMaxAge(-1);
						cookie.setPath("/");
						//cookie.setDomain(http.getSessionCookie().getDomain());
						response.addCookie(cookie);
					}
					return new ModelAndView("redirect:/ccs/menu");
				}else if(SessionEntity.getStatus() == 1){
					model.addAttribute(AppConst.ERRORS, getLoginUserNotExist());
				}else if(SessionEntity.getStatus() == 2){
					model.addAttribute(AppConst.ERRORS, getLoginWrongPassowrd());
				}else if(SessionEntity.getStatus() == 3){
					model.addAttribute(AppConst.ERRORS, getMessage("systemAuthority.fail"));
				}
			}else{
				if( result.getErrorStatus() == 404)
					model.addAttribute(AppConst.ERRORS,	getMessage("memcached.fail"));
				else
					model.addAttribute(AppConst.ERRORS,	result.getErrorMsg());
			}
			/*if(loginUser == null){
				model.addAttribute(AppConst.ERRORS, getLoginUserNotExist());
			}else if(!user.getPassword().equals(loginUser.getPassword())){
				model.addAttribute(AppConst.ERRORS, getLoginWrongPassowrd());
			}else{
				List<Role> list = userPowerService.getUserRole(loginUser.getId());
				if(list == null || list.isEmpty()){
					model.addAttribute(AppConst.ERRORS, getMessage("systemAuthority.fail"));
				}else{
					*//**1.取用户所有区域regionCodes，如果多个 根据cookie选，如果没有cookie，默认regionCodes的第一个*//*
					List<String> regionCodes = userPowerService.countUserOrgRegionCode(loginUser.getId());
					String defaultRegionCode = getUserDefaultRegionCode(getRequest().getCookies(), loginUser.getLoginid());


					 *//** 设置单点登录key *//*
					Date now = new Date();
					long time = now.getTime();
					String newID = time + loginUser.getLoginid();
					Cookie cookie = new Cookie( getSession().getServletContext().getInitParameter("cookiename"), newID);
					cookie.setMaxAge(-1);
					cookie.setPath("/");
					response.addCookie(cookie);
					getSession().setAttribute(AppConst.SESSION_KEY, newID);
					SSOIDs.put(AppConst.SESSION_KEY, newID);
					
					*//**组装sessionBEAN 对象 begin *//*
					SessionBean sessionBean = new SessionBean();
					List<Integer> roleIds = new ArrayList<Integer>();
					for (int j = 0; j < list.size(); j++) {
						roleIds.add(list.get(j).getId());
					}
					sessionBean.setRoleId(roleIds);
					sessionBean.setId(loginUser.getId());
					sessionBean.setLoginid(loginUser.getLoginid());
					sessionBean.setUserName(loginUser.getUserName());
					sessionBean.setUserIp(getIpAddr());
					sessionBean.setRegionCode(StringUtil.isBlank(defaultRegionCode)?regionCodes.get(0):defaultRegionCode);
					sessionBean.setRegionCodes(regionCodes);
					sessionBean.setOrganization(userPowerService.getUserOrganization(loginUser.getId()));
					sessionBean.setSystems(userPowerService.getUserSystem(roleIds));
					sessionBean.setSystem(sessionBean.getSystems().get(0));
					*//**组装sessionBEAN 对象  end*//*
					
					getSession().setAttribute(AppConst.USER_SESSION, sessionBean);
					return new ModelAndView("redirect:/ccs/menu");
				}
			}*/
			return new ModelAndView(loginView, model);
		}else if (action.equals("authcookie")) {
			String result = null;
			if(cookiename == null){
				result = null;
			}else{
				if(cookiename.equals((String)SSOIDs.get(AppConst.SESSION_KEY))){
					result = (String)SSOIDs.get(AppConst.SESSION_KEY);
				}else{
					result = null;
				}
			}
			PrintWriter printout = response.getWriter();
			printout.print(result);
			printout.close();
		}else if (action.equals("logout")) {
			Cookie [] cookies = this.getRequest().getCookies();
			String sessionid = "";
			for(Cookie cookie:cookies){
				if(cookie.getName().equals(AppConst.SESSION_KEY)){
					sessionid = cookie.getValue();
				}
			}
			if(cookiename==null||"".equals(cookiename)){
				cookiename = sessionid;
			}
			CCSHttpClient http = new SSOAuthHttpClient();
			Object[] arr = new Object[]{"L002","sessionId="+cookiename};
			http.getResult(arr, CCSHttpClient.GET);
			getSession().invalidate();
			Cookie cookie = new Cookie(AppConst.SESSION_KEY,null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return new ModelAndView(loginView);
	}  


	/**
	 * 
	 * @Title: getUserDefaultRegionCode
	 * @Description: TODO(取cookie里面默认登录区域)
	 * @param @param cookies
	 * @param @param loginid
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
/*	private String getUserDefaultRegionCode(Cookie[] cookies , String loginid){
		if (cookies != null){
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(loginid))  
					return cookies[i].getValue();
			}
		}
		return "";
	}*/

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
