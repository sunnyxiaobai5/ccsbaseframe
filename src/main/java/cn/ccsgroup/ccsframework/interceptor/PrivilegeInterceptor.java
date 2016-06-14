package cn.ccsgroup.ccsframework.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ccsgroup.ccsframework.annotation.PrivilegeAnnotation;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.exception.PrivilegeException;
import cn.ccsgroup.ccsframework.httpService.HttpClientResultBean;
import cn.ccsgroup.ccsframework.httpService.SSOAuthHttpClient;
import cn.ccsgroup.ccsframework.httpService.CCSHttpClient;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.interceptor.PrivilegeInterceptor.java]  
 * @ClassName:    [PrivilegeInterceptor]   
 * @Description:  [权限拦截控制]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2014-1-7 下午6:57:53]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-7 下午6:57:53，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Aspect
public class PrivilegeInterceptor {

	private static final Log log = LogFactory.getLog(PrivilegeInterceptor.class);

	@Autowired
	protected HttpServletRequest request;
	

	@Pointcut("within(cn.ccsgroup.ccsframework..controller.*Controller)")
	public void with() {
	}

	@Pointcut("execution(public * *(..))")
	public void any() {
	}

	@Around("with() && any()")
	public Object beforeController(ProceedingJoinPoint pjp) throws Throwable {
		Signature signature = pjp.getSignature();
		String ssoAuthClassFullName = "cn.ccsgroup.ccsframework.sso.SSOAuth";
		String userPowerControllerClassFullName = "cn.ccsgroup.ccsframework.sysManager.controller.UserPowerController";
		boolean isSSOAuthClassNotFound = isClassNotFound(ssoAuthClassFullName);
		boolean userPowerControllerClassNotFound = isClassNotFound(userPowerControllerClassFullName);
		if(isSSOAuthClassNotFound&&userPowerControllerClassNotFound){
			privilegeCheck(signature);
		}else{
			if (!Class.forName(signature.getDeclaringTypeName()).equals(Class.forName(ssoAuthClassFullName))&&!Class.forName(signature.getDeclaringTypeName()).equals(Class.forName(userPowerControllerClassFullName))) {
				privilegeCheck(signature);
			}
		}
		return pjp.proceed();
	}
	
	public boolean isClassNotFound(String classFullName){
		boolean flag = false;
		try {
			Class.forName(classFullName);
		} catch (ClassNotFoundException e) {
			flag = true;
		}
		return flag;
	}
	
	public void privilegeCheck(Signature signature) throws Throwable{
		Method method = ((MethodSignature) signature).getMethod();
		boolean flag = method.isAnnotationPresent(PrivilegeAnnotation.class);	
		if (flag) {
			Class<?> cls = Class.forName(signature.getDeclaringTypeName());
			RequestMapping controllerNameAnnotation = cls.getAnnotation(RequestMapping.class);
			RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
			String controllerUrl = "";
			if(controllerNameAnnotation.value() != null)
				controllerUrl = controllerNameAnnotation.value()[0];
			String url = requestMapping.value()[0]; 
			String basePath = request.getScheme() + "://" + request.getServerName() + (request.getServerPort()==80 ?"":(":" + request.getServerPort())) + request.getContextPath() ;
			Cookie[] cookie = request.getCookies();
			String sessionKey = "";
			for (int i = 0; i < cookie.length; i++) {
				if(cookie[i].getName().equals(AppConst.SESSION_KEY)){
					sessionKey = cookie[i].getValue();
					break;
				}
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("sessionId", sessionKey);
			map.put("url", controllerUrl+url);
			map.put("baseUrl", basePath+controllerUrl+url);
			
			Object[] arr = new Object[]{"S002",map};
			HttpClientResultBean result = new SSOAuthHttpClient().getResult(arr, CCSHttpClient.POST);
			if(result.isSuccess()){
				if("3".equals(result.getResult())){
					log.info("No Privilege");
					throw new PrivilegeException();
				}
			}else{
				throw new Exception(result.getErrorStatus()+":"+result.getErrorMsg());
			}
		}
	}
}
