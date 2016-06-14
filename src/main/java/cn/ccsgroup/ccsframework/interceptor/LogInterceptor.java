package cn.ccsgroup.ccsframework.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import com.google.gson.Gson;

import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.MethodAnnotation.logType;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.operationLog.service.OperationLogService;


/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.interceptor.LogInterceptor.java]  
 * @ClassName:    [LogInterceptor]   
 * @Description:  [操作日志拦截器类]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-3 下午1:33:39]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-6 下午1:33:39，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [增加传入参数解析，map list array string BaseBean]  
 * @Version:      [v1.0]
 */
@Aspect
public class LogInterceptor {

	private static final Log log = LogFactory.getLog(LogInterceptor.class);

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	private OperationLogService operationLogService;

	@Pointcut("within(cn.ccsgroup.ccsframework..service.*Service)")
	public void withService() {
	}

	@Pointcut("execution( public * save*(..))")
	public void saveObject() {
	}

	@Pointcut("execution(public * update*(..))")
	public void updateObject() {
	}

	@Pointcut("execution(public * disable*(..))")
	public void disableObject() {
	}

	@Pointcut("execution(public * enable*(..))")
	public void enableObject() {
	}

	@Pointcut("execution(public * delete*(..))")
	public void deleteObject() {
	}

	@Pointcut("execution(public * import*(..))")
	public void importObject() {
	}

	@Pointcut("execution(public * export*(..))")
	public void exportObject() {
	}

	@Pointcut("execution(public * downLoad*(..))")
	public void downLoadObject() {
	}
	
	@Pointcut("execution(public * query_*(..))")
	public void queryObject() {
	}


	@Pointcut("@within(org.springframework.stereotype.Service)")
	public void ObjectserviceImpl() {
	}

	/**
	 * 
	 * @Title: afterReturning
	 * @Description: TODO(Service的方法执行结束后的AOP)
	 * @param @param jp 切点
	 * @param @throws Throwable    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	@AfterReturning(value = "ObjectserviceImpl() && (saveObject() || updateObject() || disableObject() || enableObject() || deleteObject() || importObject() || exportObject() || downLoadObject() || queryObject())")
	public void afterReturning(JoinPoint jp ) throws Throwable{
		logService(jp,true,null);
	}

	@AfterThrowing(value = "ObjectserviceImpl() && (saveObject() || updateObject() || disableObject() || enableObject() || deleteObject() || importObject() || exportObject() || downLoadObject() || queryObject())",throwing="excep")	
	public void afterThrowing(JoinPoint jp,Exception excep) throws Throwable{
		logService(jp,false, excep);
	}

	/**
	 * 
	 * @Title: logService
	 * @Description: TODO(记录日志的方法调用)
	 * @param @param jp 切点
	 * @param @param result 操作成功标记 
	 * @param @param ex 异常
	 * @param @throws ClassNotFoundException
	 * @param @throws SecurityException
	 * @param @throws NoSuchMethodException    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void logService(JoinPoint jp,Boolean result, Exception ex) throws ClassNotFoundException, SecurityException, NoSuchMethodException {
		String moduleId = null; // 模块ID
		String operationId = null; // 操作ID
		String operationType = null;//操作类型
		String paras = "";//传入参数
		Signature signature = jp.getSignature();

		// 获取模块ID
		log.debug("Module Class Name: " + signature.getDeclaringTypeName());
		Class<?> cls = Class.forName(signature.getDeclaringTypeName());
		boolean flag = cls.isAnnotationPresent(TypeAnnotation.class);
		if (flag) {
			TypeAnnotation moduleNameAnnotation = cls.getAnnotation(TypeAnnotation.class);
			moduleId = moduleNameAnnotation.value();
		} else {
			return;
		}
		// 获取操作ID
		log.debug("Operation Method Name: " + signature.getName());
		Method method = ((MethodSignature) signature).getMethod();
		flag = method.isAnnotationPresent(MethodAnnotation.class);
		MethodAnnotation operationNameAnnotation = null;
		if (flag) {
			operationNameAnnotation = method.getAnnotation(MethodAnnotation.class);
			operationType = operationNameAnnotation.type().name();
			operationId = operationNameAnnotation.value();
		} else {
			return;
		}		
		if (moduleId != null && operationId != null) {
			// 获取方法的参数
			if (jp.getArgs() != null && jp.getArgs().length > 0) {
				Object[] args = jp.getArgs();
				try {
					paras += new Gson().toJson(args);
				} catch (Exception e) {
					log.error("日志记录解析参数出错!"+e.getMessage(), e);
				}
			}
			SessionBean sessionBean =(SessionBean) WebUtils.getSessionAttribute(request, AppConst.USER_SESSION);
			
			Integer logId = operationLogService.insertOperationLog(moduleId, operationId,operationType, paras.length()<1000?paras:paras.substring(0, 1000), result, sessionBean,null==ex?null:ex.getMessage());
			if(operationType.equals(logType.query.name()))
				WebUtils.setSessionAttribute(request,AppConst.QUERY_ID, logId);
		}
	}

}
