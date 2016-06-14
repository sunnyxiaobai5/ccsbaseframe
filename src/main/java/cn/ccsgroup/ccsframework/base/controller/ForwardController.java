package cn.ccsgroup.ccsframework.base.controller;

import java.util.Enumeration;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.base.controller.ForwardController.java]  
 * @ClassName:    [ForwardController]   
 * @Description:  [公共页面跳转控制器]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-2 下午4:19:59]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-2 下午4:19:59，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@RequestMapping("/forward")
@Scope("prototype")
public class ForwardController extends BaseController{
	
	/**
	 * 跳转到页面
	 * @param pageName
	 * @return
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST }, value="/{pageName}")
	public  String toPage(@PathVariable(value="pageName")String pageName){
			//将parameters以attributes存入request
			Enumeration<?> parameterNames = super.getRequest().getParameterNames();
			Object parameterName = null;
			while(parameterNames.hasMoreElements()){
				parameterName = parameterNames.nextElement();
				super.getRequest().setAttribute((String)parameterName,super.getRequest().getParameter((String)parameterName));
			}
		return pageName.replace("|", "/");
	}	
	
}
