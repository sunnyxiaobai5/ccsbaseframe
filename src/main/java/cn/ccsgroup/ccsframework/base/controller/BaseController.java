package cn.ccsgroup.ccsframework.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.exception.PrivilegeException;
import cn.ccsgroup.ccsframework.exception.SessionTimeoutException;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.base.controller.BaseController.java]  
 * @ClassName:    [BaseController]   
 * @Description:  [标题:基础控制器, 异常统一处理]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-2 下午4:16:08]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-6 下午4:16:08，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [增加对messages.properties消息提取公共方法]  
 * @Version:      [v1.01]
 */
public class BaseController {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private HttpServletRequest request;
	/**
	 * 异常个性化Handler
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleException(Exception ex, HttpServletRequest request) {
		logger.error(ex.getMessage(), ex);
		ModelAndView mv = new ModelAndView();
		if(ex.getCause() instanceof PrivilegeException){
			return privilegeException(new PrivilegeException(),request);
		}
		mv.addObject(AppConst.MESSAGES,ex.getMessage());
		mv.addObject(AppConst.ERRORS, ex.getMessage());
		mv.addObject(AppConst.STATUS, AppConst.FAIL);
		return mv;
	}
	/**
	 * 
	 * @Title: sessionTimeOutException
	 * @Description: TODO(自定义用户超时异常处理)
	 * @param @param exception
	 * @param @param request
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@ExceptionHandler(SessionTimeoutException.class)
	@ResponseStatus(value=HttpStatus.I_AM_A_TEAPOT)
	public ModelAndView sessionTimeOutException(SessionTimeoutException exception, HttpServletRequest request) {
		logger.error(exception.getMessage(), exception);
		ModelAndView mv = new ModelAndView("login");
		mv.addObject(AppConst.MESSAGES,getMessage("session.timeout"));
		mv.addObject(AppConst.ERRORS, getMessage("session.timeout"));
		mv.addObject(AppConst.STATUS, AppConst.FAIL);
		return mv;
	}
	/**
	 * 
	 * @Title: privilegeException
	 * @Description: TODO(权限验证异常)
	 * @param @param exception
	 * @param @param request
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@ExceptionHandler(PrivilegeException.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView privilegeException(PrivilegeException exception, HttpServletRequest request) {
		logger.error(exception.getMessage(), exception);
		ModelAndView mv = new ModelAndView("noPrivilege");
		mv.addObject(AppConst.MESSAGES,getMessage("authority.fail"));
		mv.addObject(AppConst.ERRORS, getMessage("authority.fail"));
		mv.addObject(AppConst.STATUS, AppConst.FAIL);
		return mv;
	}
	/**
	 * 
	 * @Title: getRequest
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return HttpServletRequest    返回类型
	 * @throws
	 */
	protected HttpServletRequest getRequest(){
		return this.request;
	}
	
	protected HttpSession getSession(){
		return this.request.getSession();
	}

	private static MessageSourceAccessor messages = null;

	private static MessageSourceAccessor getMessageSourceAccessor() {
		if (messages == null) {
			WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
			MessageSource messageSource = (MessageSource) webAppContext.getBean("messageSource");
			return new MessageSourceAccessor(messageSource);
		}
		return messages;
	}

	/**
	 * 
	 * @Title: getMessage
	 * @Description: TODO(根据KEY取messaes.properties对应的值)
	 * @param @param key
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	protected static String getMessage(String key){
		return getMessageSourceAccessor().getMessage(key);
	}

	/**
	 * 
	 * @Title: getIpAddr
	 * @Description: TODO(获取用户真实IP)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	protected String getIpAddr() {
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
	
	
	/**
	 * 
	 * @Title: getAddSuccess
	 * @Description: TODO(添加成功)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	protected static String getAddSuccess(){
		return getMessageSourceAccessor().getMessage("add.success");
	}
	/**
	 * 
	 * @Title: getAddFail
	 * @Description: TODO(添加失败)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	protected static String getAddFail(){
		return getMessageSourceAccessor().getMessage("add.fail");
	}
	/**
	 * 
	 * @Title: getUpdateSuccess
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	protected static String getUpdateSuccess(){
		return getMessageSourceAccessor().getMessage("update.success");
	}
	/**
	 * 
	 * @Title: getUpdateFail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	protected static String getUpdateFail(){
		return getMessageSourceAccessor().getMessage("update.fail");
	}
	/**
	 * 
	 * @Title: getDeleteSuccess
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	protected static String getDeleteSuccess(){
		return getMessageSourceAccessor().getMessage("delete.success");
	}
	/**
	 * 
	 * @Title: getDeleteFail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	protected static String getDeleteFail(){
		return getMessageSourceAccessor().getMessage("delete.fail");
	}
	/**
	 * 
	 * @Title: getLoginUserNotExist
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	protected static String getLoginUserNotExist(){
		return getMessageSourceAccessor().getMessage("login.userNotExist");
	}
	/**
	 * 
	 * @Title: getLoginWrongPassowrd
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	protected static String getLoginWrongPassowrd(){
		return getMessageSourceAccessor().getMessage("login.wrongPassword");
	}
	
	/**
	 * 
	 * @Title: getSessionBean
	 * @Description: TODO(获取Session的JavaBean)
	 * @param @return    设定文件
	 * @return SessionBean    返回类型
	 * @throws
	 */
	protected SessionBean getSessionBean() {
		return (SessionBean) WebUtils.getSessionAttribute(request, AppConst.USER_SESSION);
	}
}
