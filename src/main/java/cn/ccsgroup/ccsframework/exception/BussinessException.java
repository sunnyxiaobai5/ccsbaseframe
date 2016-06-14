package cn.ccsgroup.ccsframework.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.exception.BussinessException.java]  
 * @ClassName:    [BussinessException]   
 * @Description:  [业务操作异常]   
 * @Author:       [dl]   
 * @CreateDate:   [2014-1-19 下午03:14:58]   
 * @UpdateUser:   [dl(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-19 下午03:14:58，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class BussinessException extends Exception{
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID =12311L;

	public BussinessException()
	{
		super();
	}
	
	public BussinessException(String key)
	{
		super(getMessage(key));
	}
	
	public BussinessException(String key, Throwable cause)
	{
		super(getMessage(key), cause);
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
}
