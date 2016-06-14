package cn.ccsgroup.ccsframework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.utils.PropertitiesManagement.java]  
 * @ClassName:    [PropertitiesManagement]   
 * @Description:  [系统配置propertities文件管理方法]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-2 下午5:19:14]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-2 下午5:19:14，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class PropertitiesManagement {
	
	private static Logger log = Logger.getLogger(PropertitiesManagement.class);

	public PropertitiesManagement(){}
	
	public static InputStream getResouceInputStream(){
		return PropertitiesManagement.class.getClassLoader().getResourceAsStream("/system.properties");
	}
	
	public static String getResouceString(String id){
		Properties pro = new Properties();
		
		try {
			pro.load(getResouceInputStream());
		} catch (IOException e) {
			log.error("系统资源文件读取出错!"+e.getMessage(), e);
		}
        System.out.println(pro);
        return pro.getProperty(id);
		
	}
}
