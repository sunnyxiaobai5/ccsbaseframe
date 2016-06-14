package cn.ccsgroup.ccsframework.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccsgroup.ccsframework.annotation.RestPrivilegeAnnotation;
import cn.ccsgroup.ccsframework.authentication.service.IAuthenticationService;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.exception.PrivilegeException;
import cn.ccsgroup.ccsframework.httpService.HttpRequestBody;
import cn.ccsgroup.ccsframework.httpService.HttpResponseBody;
import cn.ccsgroup.ccsframework.utils.EncryptUtil;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.interceptor.RestPrivilegeInterceptor.java]  
 * @ClassName:    [RestPrivilegeInterceptor]   
 * @Description:  [rest接口权限拦截控制]   
 * @Author:       [wei]   
 * @CreateDate:   [2014-5-23 下午3:11:15]   
 * @UpdateUser:   [wei(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-5-23 下午3:11:15，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Aspect
public class RestPrivilegeInterceptor {
	
	@Resource(name="authenticationService")
	private IAuthenticationService authenticationService;

	private static final Log log = LogFactory.getLog(RestPrivilegeInterceptor.class);

	@Autowired
	protected HttpServletRequest request;
	

	@Pointcut("within(cn.ccsgroup.ccsframework..ws.*Service)")
	public void with() {
	}

	@Pointcut("execution(public * *(..))")
	public void any() {
	}

	@Around("with() && any() && @annotation(rl)")
	public Object beforeController(ProceedingJoinPoint pjp,RestPrivilegeAnnotation rl) throws Throwable {
		HttpRequestBody httpRequestBody = null;
		HttpResponseBody httpResponseBody = new HttpResponseBody();
		Signature signature = pjp.getSignature();
		Method method = ((MethodSignature) signature).getMethod();
		boolean flag = method.isAnnotationPresent(RestPrivilegeAnnotation.class);	
		if(flag){
			httpRequestBody = (HttpRequestBody) pjp.getArgs()[0];
			httpRequestBody = decryptAuthenticationKey(httpRequestBody);
		}
		boolean isValid = authenticationService.validLoginIdPassword(httpRequestBody);
		if(!isValid){
			httpResponseBody.setStatus(AppConst.FAIL);
			httpResponseBody.setMessage("账户或者密码错误");
			return httpResponseBody;
		}
		boolean isvalidSignature = authenticationService.validSignature(httpRequestBody);
		if(!isvalidSignature){
			httpResponseBody.setStatus(AppConst.FAIL);
			httpResponseBody.setMessage("签名错误");
			return httpResponseBody;
		}
		return pjp.proceed();
	}
	
	/**
	 * 
	 * @Title: decryptAuthenticationKey
	 * @Description: TODO(将授权key解密后把账号密码设置到httpRequestBody)
	 * @param httpRequestBody
	 * @return
	 * @throws Exception HttpRequestBody
	 * @throws
	 */
	public HttpRequestBody decryptAuthenticationKey(HttpRequestBody httpRequestBody) throws Exception{
		String authenticationKey = EncryptUtil.decryptBASE64(httpRequestBody.getAuthenticationKey());
		String[] lpt = authenticationKey.split(":");
		httpRequestBody.setLoginid(lpt[0]);
		httpRequestBody.setPassword(lpt[1]);
		httpRequestBody.setTimestamp(lpt[2]);
		return httpRequestBody;
	}
}
