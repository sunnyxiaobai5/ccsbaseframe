package cn.ccsgroup.ccsframework.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.httpService.HttpClientResultBean;
import cn.ccsgroup.ccsframework.httpService.SSOAuthHttpClient;
import cn.ccsgroup.ccsframework.httpService.CCSHttpClient;
/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.interceptor.SessionInterceptor.java]  
 * @ClassName:    [SessionInterceptor]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2014-1-7 下午6:51:42]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-7 下午6:51:42，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class SessionInterceptor extends HandlerInterceptorAdapter{

	private List<String> allowUrls;
	
	private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();  

	public List<String> getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(List<String> allowUrls) {
		this.allowUrls = allowUrls;
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String requestUrl = request.getRequestURI();  
		for(String url : allowUrls) {  
			if(Pattern.matches(url, requestUrl)) {  
				return true;  
			}  
		} 
		
		boolean flag = false;
		Cookie tempCookie = null;
		
		Cookie[] cookie = request.getCookies();
		if(cookie!=null){
			for (int i = 0; i < cookie.length; i++) {
				if(cookie[i].getName().equals(AppConst.SESSION_KEY)){
					flag = true;
					tempCookie = cookie[i];
					if(threadLocal.get() == null){
						threadLocal.set(tempCookie.getValue());
					}
					break;
				}
			}
		}
		String sessionid = request.getParameter("sessionid");
		if(sessionid!=null){
			if(!flag||(!sessionid.equals(tempCookie.getValue()))){
				if(threadLocal.get() == null){
					threadLocal.set(sessionid);
				}
				tempCookie = new Cookie(AppConst.SESSION_KEY, sessionid);
				tempCookie.setPath("/");
				response.setHeader("P3P","CP=CAO PSA OUR");
				response.addCookie(tempCookie);
			}
		}
		
		if(tempCookie==null){
			response.sendRedirect(request.getContextPath()+"/SSOAuth/login");
			return false;
		}
		
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("sessionId", tempCookie.getValue());
		map.put("url", "");
		map.put("baseUrl", "");
		
		Object[] arr = new Object[]{"S002",map};
		HttpClientResultBean result = new SSOAuthHttpClient().getResult(arr, CCSHttpClient.POST);
		String path = request.getContextPath();
		if(result.isSuccess()){
			if("0".equals(result.getResult())){
				if(request.getSession().getAttribute(AppConst.USER_SESSION)==null||!threadLocal.get().equals(tempCookie.getValue())){
					threadLocal.set(tempCookie.getValue());
					Object[] arr2 = new Object[]{"S001","sessionId="+tempCookie.getValue()};
					HttpClientResultBean result2 = new SSOAuthHttpClient().getResult(arr2, CCSHttpClient.GET);
					if(result2.isSuccess()){
						request.getSession().setAttribute(AppConst.USER_SESSION,result2.getResult());
					}
				}
				return true ;
			}else if("-1".equals(result.getResult())){
				result.setErrorMsg("用户操作超时,请重新登录！");
				result.setErrorStatus(418);
			}
		}
		if(request.getHeader("x-requested-with")!=null   
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){  
			response.setStatus(result.getErrorStatus());
			request.setAttribute("messages", result.getErrorMsg());
		}else{  
			//采用js方式，先提示
			response.setCharacterEncoding("utf-8");   
			response.setContentType("text/html; charset=utf-8"); 
			PrintWriter out = response.getWriter();  
			StringBuilder builder = new StringBuilder();  
			builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");  
			builder.append("try{window.parent.Cmessager.alert('温馨提醒', '"+result.getErrorMsg()+"', 'error',function(){");
			builder.append("window.top.location.href=\"");  
			builder.append(path+"/SSOAuth/login\"});\n");
			builder.append("}catch (e){alert('"+result.getErrorMsg()+"')}");
			builder.append("window.top.location.href=\"");  
			builder.append(path+"/SSOAuth/login\" ;\n;</script>");
			out.print(builder.toString());  
			out.close();
		}
		return false; 
	}
}
