package cn.ccsgroup.ccsframework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.annotation.RestPrivilegeAnnotation.java]  
 * @ClassName:    [RestPrivilegeAnnotation]   
 * @Description:  [Rest接口权限拦截注释类]   
 * @Author:       [wei]   
 * @CreateDate:   [2014-5-23 下午3:46:30]   
 * @UpdateUser:   [wei(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-5-23 下午3:46:30，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestPrivilegeAnnotation {

	String value() default"";

}
