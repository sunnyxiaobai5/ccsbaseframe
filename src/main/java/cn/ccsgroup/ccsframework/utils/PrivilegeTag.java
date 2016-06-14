package cn.ccsgroup.ccsframework.utils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.components.AppConst;
/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.utils.ButtonTag.java]  
 * @ClassName:    [ButtonTag]   
 * @Description:  [页面按钮权限]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2014-1-8 下午1:50:31]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-8 下午1:50:31，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class PrivilegeTag extends TagSupport {

	private static final long serialVersionUID = 5922538640050474732L;
	private static Logger log = Logger.getLogger(PrivilegeTag.class);
	private String opsUrl;

	@Override 
	public int doStartTag() throws JspException { 
//		this.setRoleManagerService();
		try { 
			if(opsUrl!=null){ 
				SessionBean bean = (SessionBean) pageContext.getAttribute(AppConst.USER_SESSION,3);
				return bean.getPowerUrls().containsValue(opsUrl) ?EVAL_BODY_INCLUDE:SKIP_BODY;
//				int i  = roleManagerService.queryPriOps(opsUrl);
//				return i > 0 ? EVAL_BODY_INCLUDE:SKIP_BODY; 
			} 
		} catch (Exception e) { 
			e.printStackTrace();
			log.info("ActionTag:" + e); 
			return SKIP_BODY; 
		} 
		return EVAL_BODY_INCLUDE; 
	} 

	@Override 
	public int doAfterBody() throws JspException { 
		return super.doAfterBody(); 
	}

	public String getOpsUrl() {
		return opsUrl;
	}

	public void setOpsUrl(String opsUrl) {
		this.opsUrl = opsUrl;
	}

//	private void setRoleManagerService() {
//		ServletContext servletContext = pageContext.getServletContext();
//		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
//		this.roleManagerService = (RoleManagerService) wac.getBean("roleManagerService");
//	}

}
