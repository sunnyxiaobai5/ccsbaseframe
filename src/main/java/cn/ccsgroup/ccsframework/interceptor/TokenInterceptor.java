package cn.ccsgroup.ccsframework.interceptor;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.ccsgroup.ccsframework.annotation.TokenAnnotation;


/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.interceptor.TokenInterceptor.java]  
 * @ClassName:    [TokenInterceptor]   
 * @Description:  [表单提交拦截控制]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2014-1-23 下午5:28:12]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-23 下午5:28:12，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class TokenInterceptor  extends HandlerInterceptorAdapter  {

	private static Map<String,Long> tokens = new HashMap<String,Long>();
	private String uuid; 
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();  
			if(method.isAnnotationPresent(TokenAnnotation.class)){
				TokenAnnotation token = method.getAnnotation(TokenAnnotation.class);
				if(token.save()){
					uuid = UUID.randomUUID().toString();
					request.setAttribute("token", uuid);
					tokens.put(uuid, new Date().getTime());
				}

				if(token.remove()){

					String clinetToken = request.getParameter("token");
					if(isRepeatSubmit(clinetToken)){
						ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method, ResponseBody.class);
						if(responseBodyAnn!=null){  
							response.setStatus(308);
						}else{  
							//采用js方式，先提示
							response.setCharacterEncoding("utf-8");   
							response.setContentType("text/html; charset=utf-8");   
							PrintWriter out = response.getWriter();  
							StringBuilder builder = new StringBuilder();  
							builder.append("<script type=\"text/javascript\" >");  
							builder.append("try{window.parent.Cmessager.alert('提交提醒','数据处理中或已保存,请勿重复提交！', 'error');");
							builder.append("}catch (e){alert('数据处理中或已保存,请勿重复提交！')}");
							builder.append("</script>");
							out.print(builder.toString());  
							out.close();
						}
						return false;
					}
					String key ;
					Set<String> set = tokens.keySet();  
					Iterator<String> it = set.iterator();  
					while (it.hasNext()) {  
						key = it.next();  
						//清理1小时之前的token 与当前提交的token
						if (new Date().getTime() - tokens.get(key) > 60*60*1000 || key.equals(clinetToken))
							it.remove();  
					}  
				}
			}
		}
		return true;
	}

	private boolean isRepeatSubmit(String clinetToken ) {
		if (clinetToken == null) {
			return true;
		}
		if (!tokens.containsKey(clinetToken)) {
			return true;
		}
		return false;
	}
}
